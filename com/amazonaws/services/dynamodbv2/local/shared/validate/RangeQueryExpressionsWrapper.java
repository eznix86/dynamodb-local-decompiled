/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.validate;

import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;

public class RangeQueryExpressionsWrapper {
    private ExpressionWrapper filterExpressionWrapper;
    private ProjectionExpressionWrapper projectionExpressionWrapper;
    private ExpressionWrapper keyConditionExpressionWrapper;

    public ProjectionExpressionWrapper getProjectionExpressionWrapper() {
        return this.projectionExpressionWrapper;
    }

    public void setProjectionExpressionWrapper(ProjectionExpressionWrapper projectionExpressionWrapper) {
        this.projectionExpressionWrapper = projectionExpressionWrapper;
    }

    public ExpressionWrapper getFilterExpressionWrapper() {
        return this.filterExpressionWrapper;
    }

    public void setFilterExpressionWrapper(ExpressionWrapper filterExpressionWrapper) {
        this.filterExpressionWrapper = filterExpressionWrapper;
    }

    public ExpressionWrapper getKeyConditionExpressionWrapper() {
        return this.keyConditionExpressionWrapper;
    }

    public void setKeyConditionExpressionWrapper(ExpressionWrapper keyConditionExpressionWrapper) {
        this.keyConditionExpressionWrapper = keyConditionExpressionWrapper;
    }
}

