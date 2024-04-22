/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.model;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonBool;
import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonString;
import com.amazon.ion.IonType;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.exceptions.PartiQLBigBirdDataTypeErrorCode;
import ddb.partiql.shared.exceptions.PartiQLBigBirdDataTypeException;
import ddb.partiql.shared.util.PartiQLToAttributeValueConverter;
import ddb.partiql.shared.util.PathTranslator;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.Seq;
import org.partiql.lang.ast.SeqType;
import org.partiql.lang.ast.Struct;

public abstract class PartiQLToAttributeValueConverterBase<E, R, V extends DocumentNode>
implements PartiQLToAttributeValueConverter<E, R, V> {
    protected final PartiQLDbEnv dbEnv;
    protected final DocumentFactory documentFactory;

    public PartiQLToAttributeValueConverterBase(PartiQLDbEnv dbEnv, DocumentFactory documentFactory) {
        this.dbEnv = dbEnv;
        this.documentFactory = documentFactory;
    }

    protected abstract boolean isBigBirdDataTypeException(RuntimeException var1);

    protected abstract String buildBigBirdDataTypeErrorMessage(RuntimeException var1);

    protected abstract void validateNestedLevel(int var1);

    protected abstract int validateAndReturnItemSize(V var1, int var2, int var3, E var4);

    protected abstract R makeAttributeName(String var1);

    protected abstract DocumentNode structToInternalAttributes(Struct var1, int var2, int var3, String var4, E var5);

    protected abstract DocumentNode listToInternalAttributes(Seq var1, int var2, int var3, String var4, E var5);

    protected abstract DocumentNode stringListToInternalAttributes(List<String> var1);

    protected abstract DocumentNode numberListToInternalAttributes(List<BigDecimal> var1);

    @Override
    public R exprNodeToInternalAttributeNames(ExprNode node, String descriptivePathPrefix) {
        if (node instanceof Literal) {
            Literal literal = (Literal)node;
            if (literal.getMetas().hasMeta("$is_ion_literal")) {
                throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Ion Literals are not supported: %s under key %s", node).build(this.getExprNodeTypeAsString(node), descriptivePathPrefix));
            }
            if (!IonType.STRING.equals((Object)literal.getIonValue().getType())) {
                throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Tuple keys must be of type String. Actual type: %s under %s", node).build(this.getExprNodeTypeAsString(node), descriptivePathPrefix));
            }
            String id = ((IonString)literal.getIonValue()).stringValue();
            PathTranslator.checkForEmptyPathString(id, this.dbEnv);
            return this.makeAttributeName(id);
        }
        throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Tuple keys must be of type String. Actual type: %s under %s", node).build(this.getExprNodeTypeAsString(node), descriptivePathPrefix));
    }

    @Override
    public V exprNodeToInternalAttributes(ExprNode node, int maxItemSize, E opContext) {
        V retVal = this.safeExprNodeToInternalAttributes(node, maxItemSize, 0, PartiQLToAttributeValueConverterBase.initDescriptivePath(), opContext);
        this.validateAndReturnItemSize(retVal, maxItemSize, opContext);
        return retVal;
    }

    protected int validateAndReturnItemSize(V attributeValue, int maxItemSize, E opContext) {
        return this.validateAndReturnItemSize(attributeValue, 0, maxItemSize, opContext);
    }

    protected V safeExprNodeToInternalAttributes(ExprNode node, int maxItemSize, int depth, String descriptivePathPrefix, E opContext) {
        try {
            return (V)this.unsafeExprNodeToInternalAttributes(node, maxItemSize, depth, descriptivePathPrefix, opContext);
        } catch (RuntimeException rte) {
            if (this.isBigBirdDataTypeException(rte)) {
                String msg = this.buildBigBirdDataTypeErrorMessage(rte);
                if (msg.endsWith(".")) {
                    throw this.dbEnv.createValidationError(msg);
                }
                throw this.dbEnv.createValidationError(msg + " under " + descriptivePathPrefix);
            }
            throw rte;
        }
    }

    private DocumentNode unsafeExprNodeToInternalAttributes(ExprNode node, int maxItemSize, int depth, String descriptivePathPrefix, E opContext) {
        this.validateNestedLevel(depth);
        DocumentNodeType dataType = this.toDynamoDBDataType(node, descriptivePathPrefix);
        switch (dataType) {
            case STRING: {
                return this.documentFactory.makeString(((IonString)((Literal)node).getIonValue()).stringValue());
            }
            case NUMBER: {
                return this.documentFactory.makeNumber(this.literalToBigDecimal((Literal)node, descriptivePathPrefix));
            }
            case BOOLEAN: {
                return this.documentFactory.makeBoolean(((IonBool)((Literal)node).getIonValue()).booleanValue());
            }
            case NULL: {
                return this.documentFactory.makeNull();
            }
            case MAP: {
                return this.structToInternalAttributes((Struct)node, maxItemSize, depth, descriptivePathPrefix, opContext);
            }
            case LIST: {
                return this.listToInternalAttributes((Seq)node, maxItemSize, depth, descriptivePathPrefix, opContext);
            }
            case NUMBER_SET: {
                List<BigDecimal> numberList = this.bagToHomogeneousList((Seq)node, elem -> this.exprNodeToBigDecimal((ExprNode)elem, descriptivePathPrefix));
                return this.numberListToInternalAttributes(numberList);
            }
            case STRING_SET: {
                List<String> stringList = this.bagToHomogeneousList((Seq)node, elem -> this.ionValueToString((ExprNode)elem, descriptivePathPrefix));
                return this.stringListToInternalAttributes(stringList);
            }
        }
        throw this.throwUnsupportedDataTypeException(node, descriptivePathPrefix);
    }

    private DocumentNodeType toDynamoDBDataType(ExprNode node, String descriptivePathPrefix) {
        if (node instanceof Seq) {
            switch (((Seq)node).getType()) {
                case BAG: {
                    return this.getSetTypeFromBag((Seq)node);
                }
                case LIST: {
                    return DocumentNodeType.LIST;
                }
            }
            throw this.throwUnsupportedDataTypeException(node, descriptivePathPrefix);
        }
        if (node instanceof Struct) {
            return DocumentNodeType.MAP;
        }
        if (node instanceof Literal) {
            Literal literal = (Literal)node;
            if (literal.getMetas().hasMeta("$is_ion_literal")) {
                throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Ion Literals are not supported: %s under key %s", node).build(this.getExprNodeTypeAsString(node), descriptivePathPrefix));
            }
            switch (((Literal)node).getIonValue().getType()) {
                case INT: 
                case DECIMAL: 
                case FLOAT: {
                    return DocumentNodeType.NUMBER;
                }
                case STRING: {
                    return DocumentNodeType.STRING;
                }
                case NULL: {
                    return DocumentNodeType.NULL;
                }
                case BOOL: {
                    return DocumentNodeType.BOOLEAN;
                }
            }
            throw this.throwUnsupportedDataTypeException(node, descriptivePathPrefix);
        }
        throw this.throwUnsupportedDataTypeException(node, descriptivePathPrefix);
    }

    private <T> List<T> bagToHomogeneousList(Seq seq2, Function<ExprNode, T> exprNodeToJavaValueMapper) {
        this.dbEnv.dbPqlAssert(seq2.getType().equals((Object)SeqType.BAG), "bagToHomogeneousList", "only Bags may be converted to sets", "seqType", (Object)seq2.getType());
        LinkedList<T> collection = new LinkedList<T>();
        for (ExprNode node : seq2.getValues()) {
            collection.add(exprNodeToJavaValueMapper.apply(node));
        }
        return collection;
    }

    private BigDecimal literalToBigDecimal(Literal literal, String descriptivePathPrefix) {
        switch (literal.getIonValue().getType()) {
            case INT: {
                return new BigDecimal(((IonInt)literal.getIonValue()).bigIntegerValue());
            }
            case DECIMAL: {
                Decimal decimal = ((IonDecimal)literal.getIonValue()).decimalValue();
                if (decimal.isNegativeZero()) {
                    throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Negative zero decimal is not supported under key %s", literal).build(descriptivePathPrefix));
                }
                return decimal.bigDecimalValue();
            }
            case FLOAT: {
                return BigDecimal.valueOf(((IonFloat)literal.getIonValue()).doubleValue());
            }
        }
        throw this.throwUnsupportedDataTypeException(literal, descriptivePathPrefix);
    }

    private DocumentNodeType getSetTypeFromBag(Seq seq2) {
        this.dbEnv.dbPqlAssert(seq2.getType().equals((Object)SeqType.BAG), "getSetTypeFromBag", "only Bags may be converted to sets", "seqType", (Object)seq2.getType());
        List<ExprNode> nodes = seq2.getValues();
        if (nodes.isEmpty()) {
            throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Empty bags are not supported", seq2).build(new Object[0]));
        }
        ExprNode firstNode = nodes.get(0);
        if (!(firstNode instanceof Literal)) {
            throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported data type in Bag. DynamoDB only supports either numbers or strings in bags", firstNode).build(new Object[0]));
        }
        Literal node = (Literal)firstNode;
        switch (node.getIonValue().getType()) {
            case INT: 
            case DECIMAL: 
            case FLOAT: {
                return DocumentNodeType.NUMBER_SET;
            }
            case STRING: {
                return DocumentNodeType.STRING_SET;
            }
        }
        throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported data type in Bag. DynamoDB only supports either numbers or strings in bags", node).build(new Object[0]));
    }

    private BigDecimal exprNodeToBigDecimal(ExprNode node, String descriptivePathPrefix) {
        DocumentNodeType dynamoDBDataType = this.toDynamoDBDataType(node, descriptivePathPrefix);
        if (dynamoDBDataType == DocumentNodeType.NUMBER) {
            return this.literalToBigDecimal((Literal)node, descriptivePathPrefix);
        }
        if (dynamoDBDataType == DocumentNodeType.NULL) {
            throw new PartiQLBigBirdDataTypeException(PartiQLBigBirdDataTypeErrorCode.INVALID_PARAMETER_VALUE, "Numeric sets may not contain null");
        }
        throw this.dbEnv.createValidationError("Unsupported data type in Bag. DynamoDB only supports either numbers or strings in bags");
    }

    private String ionValueToString(ExprNode node, String descriptivePathPrefix) {
        DocumentNodeType dynamoDBDataType = this.toDynamoDBDataType(node, descriptivePathPrefix);
        if (dynamoDBDataType == DocumentNodeType.STRING) {
            return ((IonString)((Literal)node).getIonValue()).stringValue();
        }
        if (dynamoDBDataType == DocumentNodeType.NULL) {
            return null;
        }
        throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported data type in Bag. DynamoDB only supports either numbers or strings in bags", node).build(new Object[0]));
    }

    private RuntimeException throwUnsupportedDataTypeException(ExprNode node, String descriptivePathPrefix) {
        throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported data type: %s under key %s", node).build(this.getExprNodeTypeAsString(node), descriptivePathPrefix));
    }

    private String getExprNodeTypeAsString(ExprNode node) {
        if (node instanceof Literal) {
            return ((Literal)node).getIonValue().getType().toString();
        }
        return node.getClass().getSimpleName();
    }

    public static String initDescriptivePath() {
        return "root";
    }

    protected static String addToDescriptivePath(String path, int ind) {
        return path + "[" + ind + "]";
    }

    protected static String addToDescriptivePath(String path, String key) {
        return path + "." + key;
    }
}

