/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.translator;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.DynamoDBLocalSharedOpContext;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.PartiQLToAttributeValueConverter;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.StatementTranslator;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.UpdateExpressionWrapper;
import ddb.partiql.shared.model.ExtractedKeyAndConditionExprTree;
import ddb.partiql.shared.parser.UpdateExpressionParser;
import ddb.partiql.shared.util.OperationName;
import java.util.Map;
import org.partiql.lang.ast.DataManipulation;

public class UpdateStatementTranslator
extends StatementTranslator<DataManipulation> {
    private static final OperationName OPERATION_NAME = OperationName.UPDATE;
    private final UpdateExpressionParser<DynamoDBLocalSharedOpContext, String, AttributeValue> updateParser;

    public UpdateStatementTranslator(LocalDBAccess dbAccess, PartiQLToAttributeValueConverter converter, LocalPartiQLDbEnv localPartiQLDbEnv, ExpressionValidator validator, DocumentFactory documentFactory) {
        super(dbAccess, converter, localPartiQLDbEnv, validator, documentFactory);
        this.updateParser = new UpdateExpressionParser<DynamoDBLocalSharedOpContext, String, AttributeValue>(converter, localPartiQLDbEnv, validator, documentFactory);
    }

    @Override
    public TranslatedPartiQLOperation translate(ParsedPartiQLRequest<DataManipulation> request) {
        DataManipulation dataManipulation = request.getExprNode();
        ExpressionWrapper filterExpressionWrapper = this.filterParser.getFilterExpression(dataManipulation.getWhere(), request.getParameters(), 409600, request.getAreIonNumericTypesAllowed(), null, this.opContext);
        UpdateExpressionWrapper updateExpressionWrapper = this.updateParser.getUpdateExpression(dataManipulation.getDmlOperations(), request.getParameters(), 409600, request.getAreIonNumericTypesAllowed(), null, this.opContext);
        String tableName = this.tableNameExtractor.getTableName(dataManipulation.getFrom(), this.opContext);
        ExtractedKeyAndConditionExprTree<String, Condition> extractedKeyAndConditionExprTree = this.extractKey(tableName, filterExpressionWrapper);
        ExprTreeNode conditionExprTreeNode = extractedKeyAndConditionExprTree.getConditionExpressionTreeNode();
        ExprTreeNode modifiedConditionExprTreeNode = this.addUnaryHashKeyCondition(this.getTableInfo(tableName), conditionExprTreeNode, Operator.attribute_exists);
        ExpressionWrapper conditionWrapper = new ExpressionWrapper(modifiedConditionExprTreeNode, this.validator);
        Map<String, AttributeValue> key = this.getKeyForDMLStatement(extractedKeyAndConditionExprTree.getExtractedKeyConditions());
        this.validateKeyForSchemaMismatch(key, this.getTableInfo(tableName));
        ReturnValue returnValue = this.getReturnValue(dataManipulation.getReturning());
        this.validateReturnValueForDMLOperation(returnValue, OPERATION_NAME);
        return TranslatedPartiQLOperation.builder().operationName(OPERATION_NAME).tableName(tableName).item(key).conditionExpressionWrapper(conditionWrapper).updateExpressionWrapper(updateExpressionWrapper).returnValue(returnValue).returnValuesOnConditionCheckFailure(this.getReturnValuesOnConditionCheckFailure(request.getReturnValuesOnConditionCheckFailure())).build();
    }

    @Override
    public OperationName getOperationName() {
        return OPERATION_NAME;
    }
}

