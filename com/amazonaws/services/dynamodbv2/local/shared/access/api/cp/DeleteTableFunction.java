/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.DeleteTableRequest
 *  com.amazonaws.services.dynamodbv2.model.DeleteTableResult
 *  com.amazonaws.services.dynamodbv2.model.TableDescription
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.cp;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.ControlPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

public class DeleteTableFunction
extends ControlPlaneFunction<DeleteTableRequest, DeleteTableResult> {
    public DeleteTableFunction(LocalDBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public DeleteTableResult apply(DeleteTableRequest deleteTableRequest) {
        final String tableName = deleteTableRequest.getTableName();
        this.validateTableName(tableName);
        TableDescription description = this.getTableDescriptionHelper(tableName);
        description = this.fillDescriptionHelper(description);
        if (description.getDeletionProtectionEnabled().booleanValue()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DELETE_PROTECTION_ENABLED.getMessage());
        }
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                DeleteTableFunction.this.dbAccess.deleteTable(tableName);
            }
        }.execute();
        return new DeleteTableResult().withTableDescription(description);
    }
}

