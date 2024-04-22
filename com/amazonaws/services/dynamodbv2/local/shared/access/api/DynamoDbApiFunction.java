/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.AbstractDynamoDbApiFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import org.apache.commons.lang3.StringUtils;

public abstract class DynamoDbApiFunction<I, O>
extends AbstractDynamoDbApiFunction<I, O> {
    protected final LocalDBAccess dbAccess;

    protected DynamoDbApiFunction(LocalDBAccess dbAccess) {
        this.dbAccess = dbAccess;
    }

    protected void validateAttributeName(String attrName) {
        if (attrName == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_ATTRIBUTE_NAME.getMessage());
        }
        int length = attrName.getBytes(LocalDBUtils.UTF8).length;
        if (length < 1 || length > 255) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.ATTRIBUTE_NAME_TOO_LONG.getMessage());
        }
    }

    public void validateTableName(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BAD_TABLE_NAME.getMessage());
        }
        if (tableName.length() < 3 || tableName.length() > 255) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BAD_TABLE_NAME.getMessage());
        }
        if (!tableName.matches("[-a-zA-Z0-9._]*")) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BAD_TABLE_NAME.getMessage());
        }
    }

    public TableInfo validateTableExists(String tableName) {
        TableInfo tableInfo = this.dbAccess.getTableInfo(tableName);
        if (tableInfo == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_NOT_FOUND_EXCEPTION, LocalDBClientExceptionMessage.TABLE_DOES_NOT_EXIST.getMessage());
        }
        return tableInfo;
    }

    protected long validateLimitValue(Integer limit2) {
        if (limit2 == null) {
            return -1L;
        }
        if (limit2 < 1) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_LIMIT_VALUE.getMessage());
        }
        return limit2.intValue();
    }
}

