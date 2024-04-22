/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.translator;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.DDBType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.DynamoDBLocalSharedOpContext;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.PartiQLToAttributeValueConverter;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.DynamoDBLocalDataAccessModelFactory;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.KeyAndConditionExpressionExtractor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.TableNameExtractor;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.model.ExtractedKeyAndConditionExprTree;
import ddb.partiql.shared.parser.FilterExpressionParser;
import ddb.partiql.shared.util.KeyAndConditionExpressionExtractorBase;
import ddb.partiql.shared.util.OperationName;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.ReturningElem;
import org.partiql.lang.ast.ReturningExpr;
import org.partiql.lang.ast.ReturningWildcard;

public abstract class StatementTranslator<T extends ExprNode> {
    private static final Map<ReturnValue, String> RETURN_VALUE_STRING_MAP = new HashMap<ReturnValue, String>();
    protected final LocalDBAccess dbAccess;
    protected final LocalPartiQLDbEnv localPartiQLDbEnv;
    protected final PartiQLToAttributeValueConverter converter;
    protected final ExpressionValidator validator;
    protected final FilterExpressionParser<DynamoDBLocalSharedOpContext, String, AttributeValue> filterParser;
    protected final TableNameExtractor tableNameExtractor;
    protected final KeyAndConditionExpressionExtractor keyAndConditionExpressionExtractor;
    protected final DynamoDBLocalSharedOpContext opContext;

    public StatementTranslator(LocalDBAccess dbAccess, PartiQLToAttributeValueConverter converter, LocalPartiQLDbEnv localPartiQLDbEnv, ExpressionValidator validator, DocumentFactory documentFactory) {
        this.dbAccess = dbAccess;
        this.localPartiQLDbEnv = localPartiQLDbEnv;
        this.converter = converter;
        this.validator = validator;
        this.filterParser = new FilterExpressionParser<DynamoDBLocalSharedOpContext, String, AttributeValue>(converter, localPartiQLDbEnv, validator, null, documentFactory);
        this.tableNameExtractor = new TableNameExtractor(3.0, 255.0, localPartiQLDbEnv);
        this.keyAndConditionExpressionExtractor = new KeyAndConditionExpressionExtractor(localPartiQLDbEnv, new DynamoDBLocalDataAccessModelFactory());
        this.opContext = new DynamoDBLocalSharedOpContext();
    }

    public abstract TranslatedPartiQLOperation translate(ParsedPartiQLRequest<T> var1);

    public abstract OperationName getOperationName();

    protected ExtractedKeyAndConditionExprTree<String, Condition> extractKey(String tableName, ExpressionWrapper filterExpressionWrapper) {
        if (filterExpressionWrapper == null) {
            throw this.localPartiQLDbEnv.createValidationError("Where clause does not contain a mandatory equality on all key attributes");
        }
        return this.keyAndConditionExpressionExtractor.extractKeyFromExprTreeNode(filterExpressionWrapper.getExpression().getExprTree(), this.getTableInfo(tableName), KeyAndConditionExpressionExtractorBase.ExpressionType.DATA_MANIPULATION);
    }

    protected ExprTreeNode addUnaryHashKeyCondition(TableInfo tableInfo, ExprTreeNode conditionExprTreeNode, Operator operator) {
        String hashKeyAttributeName = tableInfo.getHashKey().getAttributeName();
        ExprTreePathNode childNode = new ExprTreePathNode(new DocPath(Collections.singletonList(new DocPathMapElement(hashKeyAttributeName))));
        ExprTreeOpNode attrExistsExprTreeNode = new ExprTreeOpNode(Collections.singletonList(childNode), operator);
        ExprTreeOpNode modifiedConditionExprTreeNode = conditionExprTreeNode == null ? attrExistsExprTreeNode : new ExprTreeOpNode(Arrays.asList(attrExistsExprTreeNode, conditionExprTreeNode), Operator.AND);
        return modifiedConditionExprTreeNode;
    }

    protected ReturnValue getReturnValue(ReturningExpr returningExpr) {
        if (returningExpr != null) {
            if (returningExpr.getReturningElems().size() > 1) {
                throw this.localPartiQLDbEnv.createValidationError("Invalid syntax with multiple returning elements in RETURNING clause");
            }
            ReturningElem returningElem = returningExpr.getReturningElems().get(0);
            if (!(returningElem.getColumnComponent() instanceof ReturningWildcard)) {
                throw this.localPartiQLDbEnv.createValidationError("Expected '*' after ( MODIFIED | ALL ) ( NEW | OLD ) in RETURNING clause");
            }
            switch (returningElem.getReturningMapping()) {
                case MODIFIED_OLD: {
                    return ReturnValue.UPDATED_OLD;
                }
                case MODIFIED_NEW: {
                    return ReturnValue.UPDATED_NEW;
                }
                case ALL_OLD: {
                    return ReturnValue.ALL_OLD;
                }
                case ALL_NEW: {
                    return ReturnValue.ALL_NEW;
                }
            }
            return ReturnValue.NONE;
        }
        return ReturnValue.NONE;
    }

    protected ReturnValuesOnConditionCheckFailure getReturnValuesOnConditionCheckFailure(String returnValuesOnConditionCheckFailureStr) {
        if (returnValuesOnConditionCheckFailureStr == null) {
            return ReturnValuesOnConditionCheckFailure.NONE;
        }
        return ReturnValuesOnConditionCheckFailure.valueOf((String)returnValuesOnConditionCheckFailureStr);
    }

    protected void validateReturnValueForDMLOperation(ReturnValue returnValue, OperationName operationName) {
        if (OperationName.UPDATE.equals((Object)operationName) || returnValue == ReturnValue.NONE) {
            return;
        }
        if (OperationName.INSERT.equals((Object)operationName)) {
            throw this.localPartiQLDbEnv.createValidationError("RETURNING is not supported in INSERT");
        }
        if (OperationName.DELETE.equals((Object)operationName) && returnValue != ReturnValue.ALL_OLD) {
            throw this.localPartiQLDbEnv.createValidationError(String.format("Invalid returning clause: RETURNING %s. Only RETURNING ALL OLD * is allowed in DELETE statements.", RETURN_VALUE_STRING_MAP.get(returnValue)));
        }
    }

    protected TableInfo getTableInfo(String tableName) {
        TableInfo tableInfo = this.dbAccess.getTableInfo(tableName);
        if (tableInfo == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_NOT_FOUND_EXCEPTION, LocalDBClientExceptionMessage.TABLE_DOES_NOT_EXIST.getMessage());
        }
        return tableInfo;
    }

    Map<String, AttributeValue> getKeyForDMLStatement(Map<String, Condition> keyConditions) {
        HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        for (Map.Entry<String, Condition> keyCondition : keyConditions.entrySet()) {
            this.localPartiQLDbEnv.dbPqlAssert(keyCondition.getValue().getComparisonOperator().equals(ComparisonOperator.EQ.name()), "StatementProcessor", "The operator in keyCondition must be EQ.");
            this.localPartiQLDbEnv.dbPqlAssert(keyCondition.getValue().getAttributeValueList().size() == 1, "StatementProcessor", "The attribute value list must contain single attribute value.");
            String attributeName = keyCondition.getKey();
            AttributeValue attributeValue = keyCondition.getValue().getAttributeValueList().get(0);
            if (!DDBType.SortableScalarTypeSet.contains((Object)attributeValue.getType())) {
                throw this.localPartiQLDbEnv.createValidationError(String.format("Key value must be of type S, N, or B. Key name: %s, Key type: %s", new Object[]{attributeName, attributeValue.getType()}));
            }
            key.put(attributeName, attributeValue);
        }
        return key;
    }

    protected void validateKeyForSchemaMismatch(Map<String, AttributeValue> key, TableInfo tableInfo) {
        for (Map.Entry<String, AttributeValue> attribute : key.entrySet()) {
            this.validateSchemaMismatch(attribute.getKey(), attribute.getValue(), tableInfo);
        }
    }

    protected void validateKeyConditionsForSchemaMismatch(Map<String, Condition> keyConditions, TableInfo tableInfo) {
        for (Map.Entry<String, Condition> keyCondition : keyConditions.entrySet()) {
            this.validateSchemaMismatch(keyCondition.getKey(), keyCondition.getValue().getAttributeValueList().get(0), tableInfo);
        }
    }

    private void validateSchemaMismatch(String attributeName, AttributeValue attributeValue, TableInfo tableInfo) {
        if (!attributeValue.getType().toString().equals(tableInfo.indexedAttributeType(attributeName).toString())) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Key attribute's data type should match its data type in table's schema: Key %s").build(attributeName));
        }
    }

    protected Map.Entry<TableInfo, ExtractedKeyAndConditionExprTree<String, Condition>> getTableAndExtractKey(String tableName, ExpressionWrapper filterExpressionWrapper) {
        TableInfo table2 = this.getTableInfo(tableName);
        ExtractedKeyAndConditionExprTree keyAndConditionExprTree = this.keyAndConditionExpressionExtractor.extractKeyFromExprTreeNode(filterExpressionWrapper.getExpression().getExprTree(), table2, KeyAndConditionExpressionExtractorBase.ExpressionType.DATA_MANIPULATION);
        return new AbstractMap.SimpleEntry<TableInfo, ExtractedKeyAndConditionExprTree<String, Condition>>(table2, keyAndConditionExprTree);
    }

    static {
        RETURN_VALUE_STRING_MAP.put(ReturnValue.ALL_OLD, "ALL OLD *");
        RETURN_VALUE_STRING_MAP.put(ReturnValue.ALL_NEW, "ALL NEW *");
        RETURN_VALUE_STRING_MAP.put(ReturnValue.UPDATED_OLD, "MODIFIED OLD *");
        RETURN_VALUE_STRING_MAP.put(ReturnValue.UPDATED_NEW, "MODIFIED NEW *");
    }
}

