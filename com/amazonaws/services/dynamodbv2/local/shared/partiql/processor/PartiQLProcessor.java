/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.processor;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PartiQLStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.TableNameAndPrimaryKey;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import java.util.HashMap;

public abstract class PartiQLProcessor {
    protected final LocalDBAccess dbAccess;
    protected final LocalPartiQLDbEnv localPartiQLDbEnv;
    protected final PartiQLStatementFunction partiQLStatementFunction;
    protected final DocumentFactory documentFactory;

    protected PartiQLProcessor(LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, PartiQLStatementFunction partiQLStatementFunction, DocumentFactory documentFactory) {
        this.dbAccess = dbAccess;
        this.localPartiQLDbEnv = localPartiQLDbEnv;
        this.partiQLStatementFunction = partiQLStatementFunction;
        this.documentFactory = documentFactory;
    }

    protected long getPartiQLOperationPayloadSize(TranslatedPartiQLOperation translatedPartiQLOperation) {
        long transactionPayloadSizeBytes = 0L;
        if (translatedPartiQLOperation.getItem() != null) {
            transactionPayloadSizeBytes += LocalDBUtils.getItemSizeBytes(translatedPartiQLOperation.getItem());
        }
        if (translatedPartiQLOperation.getUpdateExpressionWrapper() != null) {
            transactionPayloadSizeBytes += (long)translatedPartiQLOperation.getUpdateExpressionWrapper().getCumulativeSize();
        }
        if (translatedPartiQLOperation.getConditionExpressionWrapper() != null) {
            transactionPayloadSizeBytes += (long)translatedPartiQLOperation.getConditionExpressionWrapper().getCumulativeSize();
        }
        return transactionPayloadSizeBytes;
    }

    protected TableNameAndPrimaryKey generateTableNameAndPrimaryKey(TranslatedPartiQLOperation translatedPartiQLOperation) {
        String tableName = translatedPartiQLOperation.getTableName();
        TableInfo tableInfo = this.partiQLStatementFunction.validateTableExists(tableName);
        HashMap<String, AttributeValue> primaryKey = new HashMap<String, AttributeValue>();
        for (String keyName : tableInfo.getBaseTableKeyNames()) {
            primaryKey.put(keyName, translatedPartiQLOperation.getItem().get(keyName));
        }
        return new TableNameAndPrimaryKey(tableName, primaryKey);
    }
}

