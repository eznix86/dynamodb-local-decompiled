/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification
 *  com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveResult
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.ttl;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.DynamoDbApiFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveResult;
import org.apache.commons.lang3.StringUtils;

public class UpdateTimeToLiveFunction
extends DynamoDbApiFunction<UpdateTimeToLiveRequest, UpdateTimeToLiveResult> {
    public UpdateTimeToLiveFunction(LocalDBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public UpdateTimeToLiveResult apply(UpdateTimeToLiveRequest updateTimeToLiveRequest) {
        final String tableName = updateTimeToLiveRequest.getTableName();
        this.validateTableName(tableName);
        TableInfo tableInfo = this.validateTableExists(tableName);
        final TimeToLiveSpecification timeToLiveSpecification = updateTimeToLiveRequest.getTimeToLiveSpecification();
        if (timeToLiveSpecification == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NULL_TIME_TO_LIVE_SPECIFICATION.getMessage());
        }
        if (timeToLiveSpecification.getEnabled() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NULL_ENABLE_FLAG_IN_TIME_TO_LIVE_SPECIFICATION.getMessage());
        }
        final String timeToLiveAttributeName = timeToLiveSpecification.getAttributeName();
        if (timeToLiveAttributeName == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NULL_TIME_TO_LIVE_ATTRIBUTE_NAME.getMessage());
        }
        if (timeToLiveSpecification.getEnabled().booleanValue()) {
            this.validateAttributeName(timeToLiveAttributeName);
            if (tableInfo.getTimeToLiveSpecification() != null) {
                if (tableInfo.getTimeToLiveSpecification().getAttributeName() != null && !StringUtils.equals(tableInfo.getTimeToLiveSpecification().getAttributeName(), timeToLiveAttributeName)) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TIME_TO_LIVE_CONFLICTING_ATTRIBUTE_NAME.getMessage());
                }
                if (tableInfo.getTimeToLiveSpecification().isEnabled().booleanValue()) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TIME_TO_LIVE_ALREADY_ENABLED.getMessage());
                }
            }
        } else {
            if (tableInfo.getTimeToLiveSpecification() == null || !tableInfo.getTimeToLiveSpecification().isEnabled().booleanValue()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TIME_TO_LIVE_ALREADY_DISABLED.getMessage());
            }
            if (tableInfo.getTimeToLiveSpecification().getAttributeName() != null && !StringUtils.equals(tableInfo.getTimeToLiveSpecification().getAttributeName(), timeToLiveAttributeName)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TIME_TO_LIVE_CONFLICTING_ATTRIBUTE_NAME.getMessage());
            }
        }
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                if (!timeToLiveSpecification.getEnabled().booleanValue()) {
                    UpdateTimeToLiveFunction.this.dbAccess.updateTable(tableName, null);
                } else {
                    UpdateTimeToLiveFunction.this.dbAccess.updateTable(tableName, timeToLiveAttributeName);
                }
            }
        }.execute();
        return new UpdateTimeToLiveResult().withTimeToLiveSpecification(updateTimeToLiveRequest.getTimeToLiveSpecification());
    }
}

