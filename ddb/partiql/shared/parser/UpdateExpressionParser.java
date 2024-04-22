/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.parser;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateAction;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateActionType;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateListNode;
import com.amazonaws.services.dynamodbv2.rr.UpdateExpressionWrapper;
import com.google.common.collect.ImmutableMap;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.dbenv.PartiQLLogger;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.parser.ExpressionParser;
import ddb.partiql.shared.util.EmptyAttributeValueBehavior;
import ddb.partiql.shared.util.ExprNodeTranslators;
import ddb.partiql.shared.util.PartiQLToAttributeValueConverter;
import ddb.partiql.shared.util.PathTranslator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.partiql.lang.ast.Assignment;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.DmlOpList;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.RemoveOp;
import org.partiql.lang.ast.VariableReference;

public class UpdateExpressionParser<E, R, V extends DocumentNode>
extends ExpressionParser<E, R, V> {
    private static final Map<String, UpdateActionType> STRING_UPDATE_ACTION_TYPE_MAP = new ImmutableMap.Builder<String, UpdateActionType>().put("set_add", UpdateActionType.ADD).put("set_delete", UpdateActionType.DELETE).build();
    protected final PartiQLLogger logger;

    public UpdateExpressionParser(PartiQLToAttributeValueConverter<E, R, V> converter, PartiQLDbEnv dbEnv, ExpressionValidator validator, DocumentFactory documentFactory) {
        super(converter, dbEnv, validator, documentFactory);
        this.logger = dbEnv.createPartiQLLogger();
    }

    public UpdateExpressionWrapper getUpdateExpression(DmlOpList dmlOpList, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        return new UpdateExpressionWrapper(this.translateDmlOperations(dmlOpList.getOps(), parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext), this.validator);
    }

    private List<UpdateListNode> translateDmlOperation(DataManipulationOperation dataManipulationOperation, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        if (dataManipulationOperation instanceof AssignmentOp) {
            return this.translateAssignmentOp((AssignmentOp)dataManipulationOperation, parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
        }
        if (dataManipulationOperation instanceof RemoveOp) {
            return this.translateRemoveOp((RemoveOp)dataManipulationOperation);
        }
        throw this.dbEnv.createValidationError("Only SET and REMOVE are supported in update expressions at this time");
    }

    private List<UpdateListNode> translateDmlOperations(List<DataManipulationOperation> dmlOps, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        ArrayList<UpdateListNode> nodeList = new ArrayList<UpdateListNode>();
        for (DataManipulationOperation dmlOp : dmlOps) {
            nodeList.addAll(this.translateDmlOperation(dmlOp, parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext));
        }
        return nodeList;
    }

    private List<UpdateListNode> translateAssignmentOp(AssignmentOp assignmentOp, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        ArrayList<UpdateListNode> nodeList = new ArrayList<UpdateListNode>();
        Assignment assignment = assignmentOp.getAssignment();
        DocPath docPath = this.parseUpdateTarget(assignment.getLvalue());
        ExprNode rValue = assignment.getRvalue();
        UpdateActionType dynamoUpdateActionType = this.getDynamoUpdateActionType(rValue);
        UpdateAction updateAction = null;
        switch (dynamoUpdateActionType) {
            case SET: {
                updateAction = this.handleSet(rValue, parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
                break;
            }
            case ADD: 
            case DELETE: {
                updateAction = this.handleAddAndDelete(dynamoUpdateActionType, docPath, rValue, parameters, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
                break;
            }
            default: {
                this.logger.fatal("translateAssignmentOp", "Unrecognized UpdateActionType: ", new Object[]{dynamoUpdateActionType});
                throw this.dbEnv.createInternalServerError("Internal server error");
            }
        }
        nodeList.add(new UpdateListNode(docPath, updateAction));
        return nodeList;
    }

    private List<UpdateListNode> translateRemoveOp(RemoveOp removeOp) {
        DocPath docPath = this.parseUpdateTarget(removeOp.getLvalue());
        UpdateAction updateAction = new UpdateAction(UpdateActionType.DELETE, null);
        return Collections.singletonList(new UpdateListNode(docPath, updateAction));
    }

    private DocPath parseUpdateTarget(ExprNode exprNode) {
        if (exprNode instanceof VariableReference) {
            return PathTranslator.translateToDocPath((VariableReference)exprNode, this.dbEnv);
        }
        if (exprNode instanceof Path) {
            return PathTranslator.translateToDocPath((Path)exprNode, this.dbEnv);
        }
        throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Unrecognized assignment target: %s", exprNode).build(ExprNodeTranslators.extractExprIdentifierAsString(exprNode)));
    }

    private UpdateAction handleSet(ExprNode rValue, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        List<ExprTreeNode> exprTreeNodes = this.convertExprNodeToExprTreeNode(Collections.singletonList(rValue), parameters, ExpressionParser.ExpressionType.UPDATE, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
        return new UpdateAction(UpdateActionType.SET, exprTreeNodes.get(0));
    }

    private UpdateAction handleAddAndDelete(UpdateActionType type, DocPath lValue, ExprNode rValue, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        if (((NAry)rValue).getArgs().size() != 3) {
            throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("SET_%s must have exactly two arguments", rValue).build(type.name()));
        }
        List<ExprTreeNode> exprTreeNodes = this.convertExprNodeToExprTreeNode(((NAry)rValue).getArgs().subList(1, 3), parameters, ExpressionParser.ExpressionType.UPDATE, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
        if (!(exprTreeNodes.get(0) instanceof ExprTreePathNode) || !((ExprTreePathNode)exprTreeNodes.get(0)).getDocPath().equals(lValue)) {
            throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("The first argument to SET_%s must equal the assignment value", ((NAry)rValue).getArgs().get(0)).build(type.name()));
        }
        if (!(exprTreeNodes.get(1) instanceof ExprTreeValueNode) || !TypeSet.SET.contains(((ExprTreeValueNode)exprTreeNodes.get(1)).getValue().getNodeType())) {
            throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("The second argument to SET_%s must be a value with type SET", ((NAry)rValue).getArgs().get(1)).build(type.name()));
        }
        return new UpdateAction(type, exprTreeNodes.get(1));
    }

    private UpdateActionType getDynamoUpdateActionType(ExprNode node) {
        UpdateActionType dynamoUpdateActionType = null;
        if (node instanceof NAry && ((NAry)node).getOp().equals((Object)NAryOp.CALL)) {
            String functionName = ((VariableReference)((NAry)node).getArgs().get(0)).getId();
            dynamoUpdateActionType = STRING_UPDATE_ACTION_TYPE_MAP.get(functionName.toLowerCase());
        }
        return dynamoUpdateActionType == null ? UpdateActionType.SET : dynamoUpdateActionType;
    }
}

