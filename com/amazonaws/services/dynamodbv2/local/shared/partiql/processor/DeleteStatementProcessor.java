/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemResult
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.processor;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PartiQLStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.StatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.DeleteStatementTranslator;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import java.util.ArrayList;
import java.util.Map;
import org.partiql.lang.ast.DataManipulation;

public class DeleteStatementProcessor
extends StatementProcessor<DataManipulation> {
    private final DeleteStatementTranslator translator;

    public DeleteStatementProcessor(DeleteStatementTranslator translator, LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, PartiQLStatementFunction partiQLStatementFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, partiQLStatementFunction, documentFactory);
        this.translator = translator;
    }

    @Override
    public ExecuteStatementResult execute(ParsedPartiQLRequest<DataManipulation> request) {
        TranslatedPartiQLOperation translatedPartiQLOperation = this.translator.translate(request);
        return this.invokePartiqlDeleteItem(translatedPartiQLOperation);
    }

    public ExecuteStatementResult invokePartiqlDeleteItem(TranslatedPartiQLOperation translatedPartiQLOperation) {
        DeleteItemResult deleteItemResult = this.partiqlDeleteItem(translatedPartiQLOperation.getTableName(), translatedPartiQLOperation.getReturnValue(), translatedPartiQLOperation.getItem(), translatedPartiQLOperation.getConditionExpressionWrapper(), translatedPartiQLOperation.getReturnValuesOnConditionCheckFailure());
        ArrayList<Map> returnItems = new ArrayList<Map>();
        if (deleteItemResult.getAttributes() != null) {
            returnItems.add(deleteItemResult.getAttributes());
        }
        return new ExecuteStatementResult().withItems(returnItems);
    }

    private DeleteItemResult partiqlDeleteItem(final String tableName, final ReturnValue returnVals, final Map<String, AttributeValue> primaryKey, ExpressionWrapper conditionExpressionWrapper, final ReturnValuesOnConditionCheckFailure returnValuesOnConditionCheckFailure) {
        this.partiQLStatementFunction.validateTableName(tableName);
        TableInfo tableInfo = this.partiQLStatementFunction.validateTableExists(tableName);
        this.partiQLStatementFunction.validateReturnType(returnVals.name(), false);
        if (primaryKey == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.MISSING_KEY.getMessage());
        }
        this.partiQLStatementFunction.validateGetKey((Map)primaryKey, tableInfo);
        if (conditionExpressionWrapper == null) {
            final DeleteItemResult result = new DeleteItemResult();
            new LocalDBAccess.ReadLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

                @Override
                public void criticalSection() {
                    Map<String, AttributeValue> oldItem = DeleteStatementProcessor.this.dbAccess.getRecord(tableName, primaryKey);
                    if (oldItem != null && (returnVals == ReturnValue.ALL_OLD || returnVals == ReturnValue.UPDATED_OLD)) {
                        result.setAttributes(DeleteStatementProcessor.this.partiQLStatementFunction.localDBOutputConverter.internalToExternalAttributes(oldItem));
                    }
                    DeleteStatementProcessor.this.dbAccess.deleteRecord(tableName, primaryKey, false);
                }
            }.execute();
            return result;
        }
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(this.dbAccess.getTableInfo(tableName), conditionExpressionWrapper, this.partiQLStatementFunction.awsExceptionFactory);
        final Expression conditionExpression = conditionExpressionWrapper.getExpression();
        final DeleteItemResult deleteResult = new DeleteItemResult();
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                Map<String, AttributeValue> oldItem = DeleteStatementProcessor.this.dbAccess.getRecord(tableName, primaryKey);
                if (!DeleteStatementProcessor.this.partiQLStatementFunction.doesItemMatchConditionExpression((Map)oldItem, conditionExpression)) {
                    if (DeleteStatementProcessor.this.getReturnValuesOnConditionCheckFailure(returnValuesOnConditionCheckFailure).equals((Object)ReturnValuesOnConditionCheckFailure.ALL_OLD)) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage(), oldItem);
                    }
                    DeleteStatementProcessor.this.partiQLStatementFunction.awsExceptionFactory.CONDITIONAL_CHECK_FAILED.throwAsException();
                }
                DeleteStatementProcessor.this.dbAccess.deleteRecord(tableName, primaryKey, false);
                if (oldItem != null && returnVals == ReturnValue.ALL_OLD) {
                    deleteResult.setAttributes(DeleteStatementProcessor.this.partiQLStatementFunction.localDBOutputConverter.internalToExternalAttributes(oldItem));
                }
            }
        }.execute();
        return deleteResult;
    }
}

