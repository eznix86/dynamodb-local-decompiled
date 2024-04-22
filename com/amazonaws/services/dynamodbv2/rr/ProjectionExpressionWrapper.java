/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.rr;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionTreeNode;
import com.amazonaws.services.dynamodbv2.rr.ExpressionsWrapperBase;
import java.util.List;

public class ProjectionExpressionWrapper
extends ExpressionsWrapperBase {
    private final ProjectionExpression projection;

    public ProjectionExpression getProjection() {
        return this.projection;
    }

    public ProjectionExpressionWrapper(List<DocPath> projectionList, ExpressionValidator validator) {
        this.projection = new ProjectionExpression(validator.buildProjectionTree(projectionList, () -> new ProjectionTreeNode(), this.getNameParameterUsage(), this.getTopLevelFieldsWithNestedAccess(), this.getMaxPathDepthCounter()));
    }
}

