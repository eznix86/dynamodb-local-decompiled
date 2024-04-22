/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.model;

import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.UpdateExpressionWrapper;
import ddb.partiql.shared.util.OperationName;
import java.util.Map;
import org.partiql.lang.ast.OrderBy;

public final class TranslatedPartiQLOperation {
    private final OperationName operationName;
    private final String tableName;
    private final String indexName;
    private final Map<String, AttributeValue> item;
    private final Map<String, Condition> keyConditions;
    private final ExpressionWrapper conditionExpressionWrapper;
    private final UpdateExpressionWrapper updateExpressionWrapper;
    private final ProjectionExpressionWrapper projectionExpressionWrapper;
    private final ReturnValue returnValue;
    private final OrderBy orderBy;
    private final boolean isConsistentRead;
    private final int maxItemSize;
    private final ReturnValuesOnConditionCheckFailure returnValuesOnConditionCheckFailure;
    private final Integer limit;

    private TranslatedPartiQLOperation(Builder builder) {
        this.operationName = builder.operationName;
        this.tableName = builder.tableName;
        this.item = builder.item;
        this.keyConditions = builder.keyConditions;
        this.conditionExpressionWrapper = builder.conditionExpressionWrapper;
        this.updateExpressionWrapper = builder.updateExpressionWrapper;
        this.projectionExpressionWrapper = builder.projectionExpressionWrapper;
        this.returnValue = builder.returnValue;
        this.indexName = builder.indexName;
        this.orderBy = builder.orderBy;
        this.isConsistentRead = builder.isConsistentRead;
        this.maxItemSize = builder.maxItemSize;
        this.returnValuesOnConditionCheckFailure = builder.returnValuesOnConditionCheckFailure;
        this.limit = builder.limit;
    }

    public static Builder builder() {
        return new Builder();
    }

    public OperationName getOperationName() {
        return this.operationName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public Map<String, AttributeValue> getItem() {
        return this.item;
    }

    public Map<String, Condition> getKeyConditions() {
        return this.keyConditions;
    }

    public ExpressionWrapper getConditionExpressionWrapper() {
        return this.conditionExpressionWrapper;
    }

    public UpdateExpressionWrapper getUpdateExpressionWrapper() {
        return this.updateExpressionWrapper;
    }

    public ProjectionExpressionWrapper getProjectionExpressionWrapper() {
        return this.projectionExpressionWrapper;
    }

    public ReturnValue getReturnValue() {
        return this.returnValue;
    }

    public OrderBy getOrderBy() {
        return this.orderBy;
    }

    public boolean isConsistentRead() {
        return this.isConsistentRead;
    }

    public int getMaxItemSize() {
        return this.maxItemSize;
    }

    public ReturnValuesOnConditionCheckFailure getReturnValuesOnConditionCheckFailure() {
        return this.returnValuesOnConditionCheckFailure;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public static final class Builder {
        private OperationName operationName;
        private String tableName;
        private String indexName;
        private Map<String, AttributeValue> item;
        private Map<String, Condition> keyConditions;
        private ExpressionWrapper conditionExpressionWrapper;
        private UpdateExpressionWrapper updateExpressionWrapper;
        private ProjectionExpressionWrapper projectionExpressionWrapper;
        private ReturnValue returnValue;
        private OrderBy orderBy;
        private boolean isConsistentRead;
        private int maxItemSize;
        private ReturnValuesOnConditionCheckFailure returnValuesOnConditionCheckFailure;
        private Integer limit;

        private Builder() {
        }

        public Builder operationName(OperationName operationName) {
            this.operationName = operationName;
            return this;
        }

        public Builder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder indexName(String indexName) {
            this.indexName = indexName;
            return this;
        }

        public Builder item(Map<String, AttributeValue> item) {
            this.item = item;
            return this;
        }

        public Builder keyConditions(Map<String, Condition> keyConditions) {
            this.keyConditions = keyConditions;
            return this;
        }

        public Builder conditionExpressionWrapper(ExpressionWrapper conditionExpressionWrapper) {
            this.conditionExpressionWrapper = conditionExpressionWrapper;
            return this;
        }

        public Builder updateExpressionWrapper(UpdateExpressionWrapper updateExpressionWrapper) {
            this.updateExpressionWrapper = updateExpressionWrapper;
            return this;
        }

        public Builder projectionExpressionWrapper(ProjectionExpressionWrapper projectionExpressionWrapper) {
            this.projectionExpressionWrapper = projectionExpressionWrapper;
            return this;
        }

        public Builder returnValue(ReturnValue returnValue) {
            this.returnValue = returnValue;
            return this;
        }

        public Builder orderBy(OrderBy orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public Builder isConsistentRead(boolean isConsistentRead) {
            this.isConsistentRead = isConsistentRead;
            return this;
        }

        public Builder maxItemSize(int maxItemSize) {
            this.maxItemSize = maxItemSize;
            return this;
        }

        public Builder returnValuesOnConditionCheckFailure(ReturnValuesOnConditionCheckFailure returnValuesOnConditionCheckFailure) {
            this.returnValuesOnConditionCheckFailure = returnValuesOnConditionCheckFailure;
            return this;
        }

        public Builder limit(Integer limit2) {
            this.limit = limit2;
            return this;
        }

        public TranslatedPartiQLOperation build() {
            return new TranslatedPartiQLOperation(this);
        }
    }
}

