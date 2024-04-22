/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException
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
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.InsertStatementTranslator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import java.util.Collections;
import java.util.Map;
import org.partiql.lang.ast.DataManipulation;

public class InsertStatementProcessor
extends StatementProcessor<DataManipulation> {
    private final InsertStatementTranslator translator;

    public InsertStatementProcessor(InsertStatementTranslator translator, LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, PartiQLStatementFunction partiQLStatementFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, partiQLStatementFunction, documentFactory);
        this.translator = translator;
    }

    @Override
    public ExecuteStatementResult execute(ParsedPartiQLRequest<DataManipulation> request) {
        TranslatedPartiQLOperation translatedPartiQLOperation = this.translator.translate(request);
        return this.invokePartiqlInsertItem(translatedPartiQLOperation);
    }

    public ExecuteStatementResult invokePartiqlInsertItem(TranslatedPartiQLOperation translatedPartiQLOperation) {
        try {
            this.partiqlInsertItem(translatedPartiQLOperation.getTableName(), translatedPartiQLOperation.getReturnValue(), translatedPartiQLOperation.getItem(), translatedPartiQLOperation.getConditionExpressionWrapper(), translatedPartiQLOperation.getReturnValuesOnConditionCheckFailure());
        } catch (ConditionalCheckFailedException e) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.DUPLICATE_ITEM_EXCEPTION, new ExceptionMessageBuilder("Duplicate primary key exists in table").build(new Object[0]));
        }
        return new ExecuteStatementResult().withItems(Collections.emptyList());
    }

    private void partiqlInsertItem(final String tableName, ReturnValue returnVals, final Map<String, AttributeValue> record, ExpressionWrapper conditionExpressionWrapper, final ReturnValuesOnConditionCheckFailure returnValuesOnConditionCheckFailure) {
        this.partiQLStatementFunction.validateTableName(tableName);
        final TableInfo tableInfo = this.partiQLStatementFunction.validateTableExists(tableName);
        final Map key = this.partiQLStatementFunction.validatePutItem((Map)record, tableInfo);
        final Expression conditionExpression = conditionExpressionWrapper.getExpression();
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, conditionExpressionWrapper, this.partiQLStatementFunction.awsExceptionFactory);
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                Map<String, AttributeValue> oldItem = InsertStatementProcessor.this.dbAccess.getRecord(tableName, key);
                if (!LocalDBUtils.doesItemMatchCondition(oldItem, conditionExpression, InsertStatementProcessor.this.localPartiQLDbEnv, InsertStatementProcessor.this.documentFactory)) {
                    if (InsertStatementProcessor.this.getReturnValuesOnConditionCheckFailure(returnValuesOnConditionCheckFailure).equals((Object)ReturnValuesOnConditionCheckFailure.ALL_OLD)) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage(), oldItem);
                    }
                    throw new ConditionalCheckFailedException("The conditional request failed");
                }
                AttributeValue rangeKey = null;
                if (tableInfo.hasRangeKey()) {
                    rangeKey = (AttributeValue)key.get(tableInfo.getRangeKey().getAttributeName());
                }
                InsertStatementProcessor.this.dbAccess.putRecord(tableName, record, (AttributeValue)key.get(tableInfo.getHashKey().getAttributeName()), rangeKey, false);
            }
        }.execute();
    }
}

