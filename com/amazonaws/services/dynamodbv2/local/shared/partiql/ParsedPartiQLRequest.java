/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql;

import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import java.util.Collections;
import java.util.List;
import org.partiql.lang.ast.ExprNode;

public final class ParsedPartiQLRequest<T extends ExprNode> {
    private final T exprNode;
    private final String subscriberId;
    private final List<AttributeValue> parameters;
    private final Integer maxItemSize;
    private final boolean isConsistentRead;
    private final String continuationToken;
    private final String requestHash;
    private final long smallRequestLogicalIOThreshold;
    private final boolean areIonNumericTypesAllowed;
    private final String returnValuesOnConditionCheckFailure;
    private final Integer limit;

    private ParsedPartiQLRequest(Builder<T> builder) {
        this.exprNode = builder.exprNode;
        this.subscriberId = builder.subscriberId;
        this.parameters = builder.parameters == null ? Collections.emptyList() : Collections.unmodifiableList(builder.parameters);
        this.maxItemSize = builder.maxItemSize;
        this.isConsistentRead = builder.isConsistentRead;
        this.continuationToken = builder.continuationToken;
        this.requestHash = builder.requestHash;
        this.smallRequestLogicalIOThreshold = builder.smallRequestLogicalIOThreshold;
        this.areIonNumericTypesAllowed = builder.areIonNumericTypesAllowed;
        this.returnValuesOnConditionCheckFailure = builder.returnValuesOnConditionCheckFailure;
        this.limit = builder.limit;
    }

    public static Builder builder() {
        return new Builder();
    }

    public T getExprNode() {
        return this.exprNode;
    }

    public String getSubscriberId() {
        return this.subscriberId;
    }

    public List<AttributeValue> getParameters() {
        return this.parameters;
    }

    public Integer getMaxItemSize() {
        return this.maxItemSize;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public boolean getIsConsistentRead() {
        return this.isConsistentRead;
    }

    public String getContinuationToken() {
        return this.continuationToken;
    }

    public String getRequestHash() {
        return this.requestHash;
    }

    public long getSmallRequestLogicalIOThreshold() {
        return this.smallRequestLogicalIOThreshold;
    }

    public boolean getAreIonNumericTypesAllowed() {
        return this.areIonNumericTypesAllowed;
    }

    public String getReturnValuesOnConditionCheckFailure() {
        return this.returnValuesOnConditionCheckFailure;
    }

    public static final class Builder<T extends ExprNode> {
        private T exprNode;
        private String subscriberId;
        private List<AttributeValue> parameters;
        private Integer maxItemSize;
        private String returnValuesOnConditionCheckFailure;
        private Integer limit;
        private boolean isConsistentRead;
        private String continuationToken;
        private String requestHash;
        private long smallRequestLogicalIOThreshold;
        private boolean areIonNumericTypesAllowed;

        private Builder() {
        }

        public Builder exprNode(T exprNode) {
            this.exprNode = exprNode;
            return this;
        }

        public Builder subscriberId(String subscriberId) {
            this.subscriberId = subscriberId;
            return this;
        }

        public Builder parameters(List<AttributeValue> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder maxItemSize(Integer maxItemSize) {
            this.maxItemSize = maxItemSize;
            return this;
        }

        public Builder limit(Integer limit2) {
            this.limit = limit2;
            return this;
        }

        public Builder isConsistentRead(boolean isConsistentRead) {
            this.isConsistentRead = isConsistentRead;
            return this;
        }

        public Builder continuationToken(String continuationToken) {
            this.continuationToken = continuationToken;
            return this;
        }

        public Builder requestHash(String requestHash) {
            this.requestHash = requestHash;
            return this;
        }

        public Builder smallRequestLogicalIOThreshold(long smallRequestLogicalIOThreshold) {
            this.smallRequestLogicalIOThreshold = smallRequestLogicalIOThreshold;
            return this;
        }

        public Builder areIonNumericTypesAllowed(boolean areIonNumericTypesAllowed) {
            this.areIonNumericTypesAllowed = areIonNumericTypesAllowed;
            return this;
        }

        public Builder returnValuesOnConditionCheckFailure(String returnValuesOnConditionCheckFailure) {
            this.returnValuesOnConditionCheckFailure = returnValuesOnConditionCheckFailure;
            return this;
        }

        public ParsedPartiQLRequest build() {
            return new ParsedPartiQLRequest(this);
        }
    }
}

