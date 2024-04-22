/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.parser;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.google.common.annotations.VisibleForTesting;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.dbenv.PartiQLLogger;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.util.EmptyAttributeValueBehavior;
import ddb.partiql.shared.util.EmptyAttributeValueValidator;
import ddb.partiql.shared.util.ExprNodeTranslators;
import ddb.partiql.shared.util.OperatorMappingsBase;
import ddb.partiql.shared.util.PartiQLToAttributeValueConverter;
import ddb.partiql.shared.util.PathTranslator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.Parameter;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.Seq;
import org.partiql.lang.ast.SqlDataType;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.TypedOp;
import org.partiql.lang.ast.VariableReference;

public abstract class ExpressionParser<E, R, V extends DocumentNode> {
    private static final Map<SqlDataType, String> SQL_TO_DYNAMO_TYPE = new HashMap<SqlDataType, String>();
    protected final ExpressionValidator validator;
    protected final PartiQLDbEnv dbEnv;
    protected final PartiQLToAttributeValueConverter<E, R, V> converter;
    protected final EmptyAttributeValueValidator<V> emptyAttributeValueValidator;
    protected final DocumentFactory documentFactory;
    protected final PartiQLLogger logger;

    public ExpressionParser(PartiQLToAttributeValueConverter<E, R, V> converter, PartiQLDbEnv dbEnv, ExpressionValidator validator, DocumentFactory documentFactory) {
        this(converter, dbEnv, validator, null, documentFactory);
    }

    public ExpressionParser(PartiQLToAttributeValueConverter<E, R, V> converter, PartiQLDbEnv dbEnv, ExpressionValidator validator, EmptyAttributeValueValidator<V> emptyAttributeValueValidator, DocumentFactory documentFactory) {
        this.dbEnv = dbEnv;
        this.validator = validator;
        this.converter = converter;
        this.emptyAttributeValueValidator = emptyAttributeValueValidator;
        this.documentFactory = documentFactory;
        this.logger = dbEnv.createPartiQLLogger();
    }

    @VisibleForTesting
    public List<ExprTreeNode> convertExprNodeToExprTreeNode(List<ExprNode> nodes, List<V> parameters, ExpressionType type, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        ArrayList<ExprTreeNode> exprTreeNodes = new ArrayList<ExprTreeNode>(nodes.size());
        for (ExprNode node : nodes) {
            if (node instanceof Literal || node instanceof Seq || node instanceof Struct) {
                V attributeValue = this.converter.exprNodeToInternalAttributes(node, maxItemSize, opContext);
                if (this.emptyAttributeValueValidator != null) {
                    this.emptyAttributeValueValidator.validateAttributeValue(attributeValue, emptyAttributeValueBehavior);
                }
                exprTreeNodes.add(new ExprTreeValueNode((DocumentNode)attributeValue));
                continue;
            }
            if (node instanceof VariableReference) {
                exprTreeNodes.add(new ExprTreePathNode(PathTranslator.translateToDocPath((VariableReference)node, this.dbEnv)));
                continue;
            }
            if (node instanceof Path) {
                exprTreeNodes.add(new ExprTreePathNode(PathTranslator.translateToDocPath((Path)node, this.dbEnv)));
                continue;
            }
            if (node instanceof NAry) {
                exprTreeNodes.add(this.parseNAryOp((NAry)node, parameters, type, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext));
                continue;
            }
            if (node instanceof Parameter) {
                int position = ((Parameter)node).getPosition();
                if (parameters == null || position > parameters.size()) {
                    throw this.dbEnv.createValidationError("Number of parameters in request and statement don't match.");
                }
                DocumentNode attributeValue = (DocumentNode)parameters.get(position - 1);
                exprTreeNodes.add(new ExprTreeValueNode(attributeValue));
                continue;
            }
            if (node instanceof Typed && ((Typed)node).getOp().equals((Object)TypedOp.IS)) {
                if (type == ExpressionType.UPDATE) {
                    throw this.dbEnv.createValidationError("IS operator is not supported in update expressions");
                }
                exprTreeNodes.add(this.handleISOperator((Typed)node, parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext));
                continue;
            }
            if (node instanceof LiteralMissing) {
                throw this.dbEnv.createValidationError("MISSING must be compared using IS operator");
            }
            throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported token in expression: %s", node).build(ExprNodeTranslators.extractExprIdentifierAsString(node)));
        }
        return exprTreeNodes;
    }

    private ExprTreeNode parseNAryOp(NAry nAry, List<V> parameters, ExpressionType type, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        NAryOp nAryOp = nAry.getOp();
        List<ExprNode> args2 = nAry.getArgs();
        if (nAryOp.equals((Object)NAryOp.CALL)) {
            return new ExprTreeOpNode(this.convertExprNodeToExprTreeNode(args2.subList(1, args2.size()), parameters, type, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext), ExprNodeTranslators.translateCustomFunctionToDynamoOperator((VariableReference)args2.get(0), type, this.dbEnv));
        }
        switch (type) {
            case CONDITION: {
                return this.getOpNodeForConditionExpression(nAryOp, args2, parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
            }
            case UPDATE: {
                return this.getOpNodeForUpdateExpression(nAryOp, args2, parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
            }
        }
        this.logger.fatal("parseNAryOp", "Unrecognized Expression Type: ", type.name());
        throw this.dbEnv.createInternalServerError("Internal server error");
    }

    private ExprTreeOpNode getOpNodeForConditionExpression(NAryOp nAryOp, List<ExprNode> args2, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        Operator dynamoOperator = this.getOperatorForConditionExpression(nAryOp);
        if (dynamoOperator != null) {
            if (nAryOp.equals((Object)NAryOp.IN)) {
                return this.handleINOperator(args2, parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
            }
            if (nAryOp.equals((Object)NAryOp.NOT)) {
                return this.handleNOTOperator(args2, parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
            }
            if (nAryOp.equals((Object)NAryOp.AND) || nAryOp.equals((Object)NAryOp.OR)) {
                return this.handleAndOrOperators(args2, parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext, dynamoOperator);
            }
            return new ExprTreeOpNode(this.convertExprNodeToExprTreeNode(args2, parameters, ExpressionType.CONDITION, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext), dynamoOperator);
        }
        throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported operator in Condition Expression. Operator: %s").build(nAryOp.getSymbol()));
    }

    private ExprTreeOpNode handleINOperator(List<ExprNode> args2, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        if (args2.size() != 2 || !(args2.get(1) instanceof Seq)) {
            throw this.dbEnv.createValidationError("IN operator must have a left hand argument of type Variable Reference and right hand argument of type Seq with at least one member");
        }
        Seq rightArg = (Seq)args2.get(1);
        if (rightArg.getValues().isEmpty()) {
            throw this.dbEnv.createValidationError("IN operator must have a left hand argument of type Variable Reference and right hand argument of type Seq with at least one member");
        }
        ArrayList<ExprNode> argsList = new ArrayList<ExprNode>(1 + rightArg.getValues().size());
        argsList.add(args2.get(0));
        argsList.addAll(rightArg.getValues());
        return new ExprTreeOpNode(this.convertExprNodeToExprTreeNode(argsList, parameters, ExpressionType.CONDITION, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext), Operator.IN);
    }

    private ExprTreeOpNode handleISOperator(Typed node, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        SqlDataType dataType = node.getType().getSqlDataType();
        ExprNode childNode = node.getExpr();
        if (!(childNode instanceof VariableReference || childNode instanceof Literal || childNode instanceof Path)) {
            throw this.dbEnv.createValidationError("The left hand side argument to IS must be a Variable Reference, Literal, or Path");
        }
        ArrayList<ExprTreeNode> args2 = new ArrayList<ExprTreeNode>(this.convertExprNodeToExprTreeNode(Collections.singletonList(childNode), parameters, ExpressionType.CONDITION, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext));
        switch (dataType) {
            case MISSING: {
                return new ExprTreeOpNode(args2, Operator.attribute_not_exists);
            }
            case NULL: {
                args2.add(new ExprTreeValueNode(this.documentFactory.makeNull()));
                return new ExprTreeOpNode(args2, Operator.EQ);
            }
            case BOOLEAN: 
            case NUMERIC: 
            case STRING: 
            case BLOB: 
            case STRUCT: 
            case TUPLE: 
            case LIST: {
                args2.add(new ExprTreeValueNode(this.documentFactory.makeString(SQL_TO_DYNAMO_TYPE.get((Object)dataType))));
                return new ExprTreeOpNode(args2, Operator.attribute_type);
            }
        }
        throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported data type for IS operator: %s. Supported data types are [MISSING, NULL, BOOLEAN, BLOB, STRING, NUMERIC, STRUCT, TUPLE, LIST]", node).build(new Object[]{dataType}));
    }

    private ExprTreeOpNode handleNOTOperator(List<ExprNode> args2, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        List<ExprTreeNode> childNodes = this.convertExprNodeToExprTreeNode(args2, parameters, ExpressionType.CONDITION, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
        if (childNodes.size() != 1 || !(childNodes.get(0) instanceof ExprTreeOpNode)) {
            throw this.dbEnv.createValidationError("The argument to NOT must be a Type or Expression");
        }
        return new ExprTreeOpNode(childNodes, Operator.NOT);
    }

    private ExprTreeOpNode handleAndOrOperators(List<ExprNode> args2, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext, Operator operator) {
        List<ExprTreeNode> childNodes = this.convertExprNodeToExprTreeNode(args2, parameters, ExpressionType.CONDITION, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
        if (childNodes.size() != 2 || !(childNodes.get(0) instanceof ExprTreeOpNode) || !(childNodes.get(1) instanceof ExprTreeOpNode)) {
            throw this.dbEnv.createValidationError("The arguments to AND/OR must be Expressions");
        }
        return new ExprTreeOpNode(childNodes, operator);
    }

    private ExprTreeOpNode getOpNodeForUpdateExpression(NAryOp nAryOp, List<ExprNode> args2, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        if (nAryOp == NAryOp.EQ) {
            return new ExprTreeOpNode(this.convertExprNodeToExprTreeNode(args2, parameters, ExpressionType.UPDATE, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext), Operator.EQ);
        }
        Operator dynamoOperator = Operator.getMathOperator(nAryOp.getSymbol());
        if (dynamoOperator != null) {
            return new ExprTreeOpNode(this.convertExprNodeToExprTreeNode(args2, parameters, ExpressionType.UPDATE, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext), dynamoOperator);
        }
        throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported operator in Update Expression. Operator: %s").build(nAryOp.getSymbol()));
    }

    private Operator getOperatorForConditionExpression(NAryOp nAryOp) {
        Operator comparator = OperatorMappingsBase.getComparator(nAryOp);
        if (comparator == null) {
            return OperatorMappingsBase.getLogicalOperator(nAryOp);
        }
        return comparator;
    }

    static {
        SQL_TO_DYNAMO_TYPE.put(SqlDataType.STRING, "S");
        SQL_TO_DYNAMO_TYPE.put(SqlDataType.NUMERIC, "N");
        SQL_TO_DYNAMO_TYPE.put(SqlDataType.BLOB, "B");
        SQL_TO_DYNAMO_TYPE.put(SqlDataType.BOOLEAN, "BOOL");
        SQL_TO_DYNAMO_TYPE.put(SqlDataType.STRUCT, "M");
        SQL_TO_DYNAMO_TYPE.put(SqlDataType.TUPLE, "M");
        SQL_TO_DYNAMO_TYPE.put(SqlDataType.LIST, "L");
    }

    public static enum ExpressionType {
        CONDITION,
        UPDATE;

    }
}

