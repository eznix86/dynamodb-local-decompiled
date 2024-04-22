/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateAction;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateActionType;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateExpression;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateTreeNode;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExpressionHelper {
    public static Set<DocPathElement> getConditionExpressionTopLevelDocPathElements(Expression conditionExpression, DbEnv dbEnv) {
        dbEnv.dbAssert(conditionExpression != null, "getConditionExpressionTopLevelDocPathElements", "conditionExpression should not be null", "conditionExpression", conditionExpression);
        HashSet<DocPathElement> topLevelAttrCondExpr = new HashSet<DocPathElement>();
        ExpressionHelper.addExprTreeNodeTopLevelDocPathElements(conditionExpression.getExprTree(), topLevelAttrCondExpr, dbEnv);
        return topLevelAttrCondExpr;
    }

    private static void addExprTreeNodeTopLevelDocPathElements(ExprTreeNode exprTreeNode, Set<DocPathElement> expressionDocPathElements, DbEnv dbEnv) {
        String TRACE_HEADER = "addExprTreeNodeTopLevelDocPathElements";
        if (exprTreeNode instanceof ExprTreeOpNode) {
            for (ExprTreeNode node : exprTreeNode.getChildren()) {
                ExpressionHelper.addExprTreeNodeTopLevelDocPathElements(node, expressionDocPathElements, dbEnv);
            }
        }
        if (exprTreeNode instanceof ExprTreePathNode) {
            DocPath docPath = ((ExprTreePathNode)exprTreeNode).getDocPath();
            dbEnv.dbAssert(docPath != null, "addExprTreeNodeTopLevelDocPathElements", "Document Path should not be null", new Object[0]);
            if (!docPath.getElements().isEmpty()) {
                DocPathElement docPathElement = docPath.getElements().get(0);
                dbEnv.dbAssert(docPathElement != null, "addExprTreeNodeTopLevelDocPathElements", "DocumentPathElement should not be null", new Object[0]);
                expressionDocPathElements.add(docPathElement);
            }
        }
    }

    public static Set<DocPathElement> getProjectionExpressionTopLevelDocPathElements(ProjectionExpression projectionExpression, DbEnv dbEnv) {
        String TRACE_HEADER = "getProjectionExpressionTopLevelDocPathElements";
        dbEnv.dbAssert(projectionExpression != null, "getProjectionExpressionTopLevelDocPathElements", "projectionExpression should not be null", "projectionExpression", projectionExpression);
        ProjectionTreeNode projTreeNode = projectionExpression.getTreeRoot();
        dbEnv.dbAssert(projTreeNode != null, "getProjectionExpressionTopLevelDocPathElements", "projTreeNode should not be null", "projectionExpression", projectionExpression);
        return projTreeNode.getChildMap().keySet();
    }

    public static Set<DocPathElement> getUpdateExpressionTopLevelDocPathElements(UpdateExpression updateExpression, DbEnv dbEnv) {
        String TRACE_HEADER = "getUpdateExpressionTopLevelDocPathElements";
        dbEnv.dbAssert(updateExpression != null, "getUpdateExpressionTopLevelDocPathElements", "updateExpression should not be null", "updateExpression", updateExpression);
        UpdateTreeNode updateTreeNode = updateExpression.getTreeRoot();
        dbEnv.dbAssert(updateTreeNode != null, "getUpdateExpressionTopLevelDocPathElements", "updateTreeNode should not be null", "updateExpression", updateExpression);
        HashSet<DocPathElement> updTopLevelDocPathElements = new HashSet<DocPathElement>();
        updTopLevelDocPathElements.addAll(updateTreeNode.getChildMap().keySet());
        ExpressionHelper.addUpdateTreeNodeTopLevelDocPathElements(updateTreeNode, updTopLevelDocPathElements, dbEnv);
        return updTopLevelDocPathElements;
    }

    private static void addUpdateTreeNodeTopLevelDocPathElements(UpdateTreeNode updateTreeNode, Set<DocPathElement> updateTopLevelDocPathElements, DbEnv dbEnv) {
        if (updateTreeNode.getUpdateMap() != null) {
            for (Map.Entry<DocPathElement, Object> entry : updateTreeNode.getUpdateMap().entrySet()) {
                ExpressionHelper.addExprTreeNodeTopLevelDocPathElements(((UpdateAction)entry.getValue()).getExprTree(), updateTopLevelDocPathElements, dbEnv);
            }
        }
        if (updateTreeNode.getChildMap() != null) {
            for (Map.Entry<DocPathElement, Object> entry : updateTreeNode.getChildMap().entrySet()) {
                ProjectionTreeNode childNode = (ProjectionTreeNode)entry.getValue();
                if (childNode == null) continue;
                dbEnv.dbAssert(childNode instanceof UpdateTreeNode, "getUpdateTreeNodeTopLevelDocPathElements", "children of an UpdateTreeNode should be of type UpdateTreeNode", "childNode", childNode);
                ExpressionHelper.addUpdateTreeNodeTopLevelDocPathElements((UpdateTreeNode)childNode, updateTopLevelDocPathElements, dbEnv);
            }
        }
    }

    public static Set<DocPathElement> getTopLevelFieldsForAttributesBeingUpdated(UpdateExpression updateExpression, DbEnv dbEnv) {
        String TRACE_HEADER = "getTopLevelFieldsForAttributesBeingUpdated";
        dbEnv.dbAssert(updateExpression.getTreeRoot() != null, "getTopLevelFieldsForAttributesBeingUpdated", "updateTreeNode should not be null", "updateExpression", updateExpression);
        UpdateTreeNode updateTreeNode = updateExpression.getTreeRoot();
        dbEnv.dbAssert(updateTreeNode != null, "getTopLevelFieldsForAttributesBeingUpdated", "updateTreeNode should not be null", "updateExpression", updateExpression);
        return updateTreeNode.getChildMap().keySet();
    }

    public static Collection<DocumentNodeType> getResultingUpdateType(UpdateExpression updateExpression, DocPathElement field, DbEnv dbEnv) {
        String TRACE_HEADER = "getResultingUpdateType";
        dbEnv.dbAssert(updateExpression != null, "getResultingUpdateType", "UpdateExpression should not be null", "updateExpression", updateExpression);
        dbEnv.dbAssert(field != null, "getResultingUpdateType", "DocPathElement should not be null", "field", field);
        dbEnv.dbAssert(field instanceof DocPathMapElement, "getResultingUpdateType", "should be a DocPathMapElement", "field", field);
        ExprTreeNode exprTreeNode = ExpressionHelper.getUpdateValueExprTreeNode(updateExpression, field, dbEnv);
        if (exprTreeNode != null) {
            if (exprTreeNode instanceof ExprTreeValueNode) {
                ExprTreeValueNode valueNode = (ExprTreeValueNode)exprTreeNode;
                return Arrays.asList(valueNode.getValue().getNodeType());
            }
            if (exprTreeNode instanceof ExprTreeOpNode) {
                ExprTreeOpNode opNode = (ExprTreeOpNode)exprTreeNode;
                TypeSet returnType = opNode.getOperator().getReturnType();
                return returnType.getDocumentNodeTypes();
            }
        }
        return TypeSet.ALL_TYPES.getDocumentNodeTypes();
    }

    public static ExprTreeNode getUpdateValueExprTreeNode(UpdateExpression updateExpression, DocPathElement docPathElement, DbEnv dbEnv) {
        String TRACE_HEADER = "getAttributeUpdateExprTreeNode";
        dbEnv.dbAssert(updateExpression.getTreeRoot() != null, "getAttributeUpdateExprTreeNode", "updateTreeNode should not be null", "updateExpression", updateExpression);
        dbEnv.dbAssert(docPathElement != null, "getAttributeUpdateExprTreeNode", "DocPathElement should not be null", "docPathElement", docPathElement);
        dbEnv.dbAssert(docPathElement instanceof DocPathMapElement, "getAttributeUpdateExprTreeNode", "should be a DocPathMapElement", "docPathElement", docPathElement);
        UpdateTreeNode updateTreeNode = updateExpression.getTreeRoot();
        Map<DocPathElement, UpdateAction> updateMap = updateTreeNode.getUpdateMap();
        if (updateMap == null || !updateMap.containsKey(docPathElement)) {
            return null;
        }
        UpdateAction action = updateMap.get(docPathElement);
        UpdateActionType actionType = action.getActionType();
        ExprTreeNode value = action.getExprTree();
        if (value == null) {
            dbEnv.dbAssert(actionType == UpdateActionType.DELETE, "getAttributeUpdateExprTreeNode", "null exprTree with non-delete action", "updateExpression", updateExpression, "docPathElement", docPathElement);
            return null;
        }
        return value;
    }

    public static boolean attributeHasNestedUpdate(UpdateExpression updateExpression, DocPathElement docPathElement, DbEnv dbEnv) {
        String TRACE_HEADER = "attributeHasNestedUpdate";
        dbEnv.dbAssert(updateExpression != null, "attributeHasNestedUpdate", "UpdateExpression should not be null", "updateExpression", updateExpression);
        dbEnv.dbAssert(docPathElement != null, "attributeHasNestedUpdate", "DocPathElement should not be null", "docPathElement", docPathElement);
        dbEnv.dbAssert(docPathElement instanceof DocPathMapElement, "attributeHasNestedUpdate", "should be a DocPathMapElement", "docPathElement", docPathElement);
        dbEnv.dbAssert(updateExpression.getTreeRoot() != null, "attributeHasNestedUpdate", "updateTreeNode should not be null", "updateExpression", updateExpression);
        UpdateTreeNode updateTreeNode = updateExpression.getTreeRoot();
        return updateTreeNode.getChildMap().get(docPathElement) != null;
    }
}

