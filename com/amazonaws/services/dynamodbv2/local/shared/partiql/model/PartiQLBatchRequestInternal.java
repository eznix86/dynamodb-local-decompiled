/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.model;

import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import java.util.List;

public class PartiQLBatchRequestInternal {
    private final List<TranslatedPartiQLRequest> translatedPartiQLRequests;
    private final boolean isBatchGet;

    public PartiQLBatchRequestInternal(List<TranslatedPartiQLRequest> translatedPartiQLRequests, boolean isBatchGet) {
        this.translatedPartiQLRequests = translatedPartiQLRequests;
        this.isBatchGet = isBatchGet;
    }

    public List<TranslatedPartiQLRequest> getTranslatedPartiQLRequests() {
        return this.translatedPartiQLRequests;
    }

    public boolean isBatchGet() {
        return this.isBatchGet;
    }

    public static class TranslatedPartiQLRequest {
        private final WriteRequestInternal writeRequest;
        private final Exception error;

        public TranslatedPartiQLRequest(WriteRequestInternal writeRequest) {
            this.writeRequest = writeRequest;
            this.error = null;
        }

        public TranslatedPartiQLRequest(Exception error) {
            this.error = error;
            this.writeRequest = null;
        }

        public WriteRequestInternal getWriteRequest() {
            return this.writeRequest;
        }

        public Exception getError() {
            return this.error;
        }
    }

    public static class WriteRequestInternal {
        private final String tableName;
        private final ParsedPartiQLRequest parsedPartiQLRequest;
        private final TranslatedPartiQLOperation translatedPartiQLOperation;

        public WriteRequestInternal(String tableName, ParsedPartiQLRequest parsedPartiQLRequest, TranslatedPartiQLOperation translatedPartiQLOperation) {
            this.tableName = tableName;
            this.parsedPartiQLRequest = parsedPartiQLRequest;
            this.translatedPartiQLOperation = translatedPartiQLOperation;
        }

        public String getTableName() {
            return this.tableName;
        }

        public ParsedPartiQLRequest getParsedPartiQLRequest() {
            return this.parsedPartiQLRequest;
        }

        public TranslatedPartiQLOperation getTranslatedPartiQLOperation() {
            return this.translatedPartiQLOperation;
        }
    }
}

