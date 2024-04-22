/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.validate;

import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.UpdateExpressionWrapper;

public class UpdateItemExpressionsWrapper {
    private UpdateExpressionWrapper updateExpressionWrapper;
    private ExpressionWrapper conditionExpressionWrapper;

    public UpdateExpressionWrapper getUpdateExpressionWrapper() {
        return this.updateExpressionWrapper;
    }

    public void setUpdateExpressionWrapper(UpdateExpressionWrapper updateExpressionWrapper) {
        this.updateExpressionWrapper = updateExpressionWrapper;
    }

    public ExpressionWrapper getConditionExpressionWrapper() {
        return this.conditionExpressionWrapper;
    }

    public void setConditionExpressionWrapper(ExpressionWrapper conditionExpressionWrapper) {
        this.conditionExpressionWrapper = conditionExpressionWrapper;
    }
}

