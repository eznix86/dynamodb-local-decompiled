/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.rr;

import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.rr.ExpressionsWrapperBase;

public class ExpressionWrapper
extends ExpressionsWrapperBase {
    private final Expression expression;

    public Expression getExpression() {
        return this.expression;
    }

    public ExpressionWrapper(ExprTreeNode exprTree, ExpressionValidator validator) {
        validator.validateExpression(exprTree, this.getNameParameterUsage(), this.getValueParameterUsage(), this.getTopLevelFieldsWithNestedAccess(), this.getOperatorCounter(), this.getNodeCounter(), this.getMaxPathDepthCounter());
        this.expression = new Expression(exprTree);
    }
}

