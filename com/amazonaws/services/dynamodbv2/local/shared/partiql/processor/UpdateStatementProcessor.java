/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemResult
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.processor;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateExpression;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PartiQLStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.StatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.UpdateStatementTranslator;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.UpdateExpressionWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.partiql.lang.ast.DataManipulation;

public class UpdateStatementProcessor
extends StatementProcessor<DataManipulation> {
    private final UpdateStatementTranslator translator;

    public UpdateStatementProcessor(UpdateStatementTranslator translator, LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, PartiQLStatementFunction partiQLStatementFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, partiQLStatementFunction, documentFactory);
        this.translator = translator;
    }

    @Override
    public ExecuteStatementResult execute(ParsedPartiQLRequest<DataManipulation> request) {
        TranslatedPartiQLOperation translatedPartiQLOperation = this.translator.translate(request);
        return this.invokePartiqlUpdateItem(translatedPartiQLOperation);
    }

    public ExecuteStatementResult invokePartiqlUpdateItem(TranslatedPartiQLOperation translatedPartiQLOperation) {
        Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> returnedAttributes = this.partiqlUpdateItem(translatedPartiQLOperation.getTableName(), translatedPartiQLOperation.getReturnValue(), translatedPartiQLOperation.getItem(), translatedPartiQLOperation.getConditionExpressionWrapper(), translatedPartiQLOperation.getUpdateExpressionWrapper(), translatedPartiQLOperation.getReturnValuesOnConditionCheckFailure());
        return new ExecuteStatementResult().withItems(returnedAttributes == null ? new ArrayList() : Collections.singletonList(returnedAttributes));
    }

    public Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> partiqlUpdateItem(final String tableName, final ReturnValue returnVals, final Map<String, AttributeValue> key, final ExpressionWrapper conditionExpressionWrapper, final UpdateExpressionWrapper updateExpressionWrapper, final ReturnValuesOnConditionCheckFailure returnValuesOnConditionCheckFailure) {
        this.partiQLStatementFunction.validateTableName(tableName);
        final TableInfo tableInfo = this.partiQLStatementFunction.validateTableExists(tableName);
        this.partiQLStatementFunction.validateGetKey((Map)key, tableInfo);
        final UpdateItemResult updateItemResult = new UpdateItemResult();
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                HashMap<String, AttributeValue> oldItem;
                Map<String, AttributeValue> item = UpdateStatementProcessor.this.dbAccess.getRecord(tableName, key);
                if (item == null) {
                    oldItem = null;
                    item = new HashMap<String, AttributeValue>(key);
                } else {
                    oldItem = new HashMap<String, AttributeValue>();
                    for (Map.Entry<String, AttributeValue> entry : item.entrySet()) {
                        oldItem.put(entry.getKey(), entry.getValue());
                    }
                }
                Expression conditionExpression = conditionExpressionWrapper == null ? null : conditionExpressionWrapper.getExpression();
                LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, conditionExpressionWrapper, UpdateStatementProcessor.this.partiQLStatementFunction.awsExceptionFactory);
                if (!UpdateStatementProcessor.this.partiQLStatementFunction.doesItemMatchConditionExpression(oldItem, conditionExpression)) {
                    if (UpdateStatementProcessor.this.getReturnValuesOnConditionCheckFailure(returnValuesOnConditionCheckFailure).equals((Object)ReturnValuesOnConditionCheckFailure.ALL_OLD)) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage(), oldItem);
                    }
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage());
                }
                HashMap itemChangesForExpr = new HashMap();
                UpdateExpression updateExpression = updateExpressionWrapper == null ? null : updateExpressionWrapper.getUpdateExpr();
                LocalDBValidatorUtils.validateThatKeyAttributesNotUpdated(tableInfo, updateExpression, UpdateStatementProcessor.this.partiQLStatementFunction.awsExceptionFactory);
                LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, updateExpressionWrapper, UpdateStatementProcessor.this.partiQLStatementFunction.awsExceptionFactory);
                UpdateStatementProcessor.this.partiQLStatementFunction.doUpdates(updateExpression, (Map)item, (Map)itemChangesForExpr, (ReturnValue)(ReturnValue.UPDATED_NEW.equals((Object)returnVals) || ReturnValue.UPDATED_OLD.equals((Object)returnVals) ? returnVals : null));
                if (LocalDBUtils.getItemSizeBytes(item) > 409600L) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.ITEM_UPD_TOO_LARGE.getMessage());
                }
                AttributeValue rangeKey = null;
                if (tableInfo.hasRangeKey()) {
                    rangeKey = (AttributeValue)key.get(tableInfo.getRangeKey().getAttributeName());
                }
                UpdateStatementProcessor.this.partiQLStatementFunction.validateIndexKeyAttributeValuesBeforePuttingFinalRecordToDB(tableInfo, (Map)item, true);
                UpdateStatementProcessor.this.dbAccess.putRecord(tableName, item, (AttributeValue)key.get(tableInfo.getHashKey().getAttributeName()), rangeKey, true);
                updateItemResult.setAttributes(UpdateStatementProcessor.this.partiQLStatementFunction.localDBOutputConverter.internalToExternalAttributes(UpdateStatementProcessor.this.partiQLStatementFunction.getReturnedValsFromUpdate(returnVals, (Map)itemChangesForExpr, oldItem, (Map)item)));
            }
        }.execute();
        return updateItemResult.getAttributes();
    }
}

