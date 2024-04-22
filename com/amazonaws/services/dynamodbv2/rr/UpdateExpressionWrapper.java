/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.rr;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateExpression;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateListNode;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateTreeNode;
import com.amazonaws.services.dynamodbv2.rr.ExpressionsWrapperBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UpdateExpressionWrapper
extends ExpressionsWrapperBase {
    private final UpdateExpression updateExpr;
    private final Set<DocPath> pathsForUpdateSize = new HashSet<DocPath>();
    private final Map<String, Integer> valueParameterUsageForUpdateSize = new HashMap<String, Integer>();

    public UpdateExpressionWrapper(List<UpdateListNode> nodeList, ExpressionValidator validator) {
        ArrayList<DocPath> projectionList = new ArrayList<DocPath>();
        for (UpdateListNode node : nodeList) {
            projectionList.add(node.getDocPath());
        }
        UpdateTreeNode treeRoot = (UpdateTreeNode)validator.buildProjectionTree(projectionList, () -> new UpdateTreeNode(), this.getNameParameterUsage(), this.getTopLevelFieldsWithNestedAccess(), this.getMaxPathDepthCounter());
        validator.attachUpdateExpression(treeRoot, nodeList, this.getNameParameterUsage(), this.getValueParameterUsage(), this.getValueParameterUsageForUpdateSize(), this.getPathsForUpdateSize(), this.getTopLevelFieldsWithNestedAccess(), this.getOperatorCounter(), this.getNodeCounter(), this.getMaxPathDepthCounter());
        this.updateExpr = new UpdateExpression(treeRoot);
    }

    public UpdateExpression getUpdateExpr() {
        return this.updateExpr;
    }

    public Set<DocPath> getPathsForUpdateSize() {
        return this.pathsForUpdateSize;
    }

    public Map<String, Integer> getValueParameterUsageForUpdateSize() {
        return this.valueParameterUsageForUpdateSize;
    }
}

