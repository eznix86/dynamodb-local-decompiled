/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathDictElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.OperatorValidator;
import com.amazonaws.services.dynamodbv2.datamodel.ParameterMap;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateAction;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateActionType;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateListNode;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateTreeNode;
import com.amazonaws.services.dynamodbv2.dbenv.DbConfig;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import com.amazonaws.services.dynamodbv2.rr.ExpressionsWrapperBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExpressionValidator
extends OperatorValidator {
    public static final boolean DEFAULT_ALLOW_VALUE_PARAMETER_IN_EXPRESSION_DOC_PATH = true;
    private final boolean allowValueParameterInExpressionDocPath;
    private final ParameterMap pMap;

    public ExpressionValidator(DbEnv dbEnv, ParameterMap pMap) {
        this(dbEnv, pMap, false, true);
    }

    public ExpressionValidator(DbEnv dbEnv, ParameterMap pMap, boolean areIonNumericTypesAllowed) {
        this(dbEnv, pMap, areIonNumericTypesAllowed, true);
    }

    public ExpressionValidator(DbEnv dbEnv, ParameterMap pMap, boolean areIonNumericTypesAllowed, boolean allowValueParameterInExpressionDocPath) {
        super(dbEnv, areIonNumericTypesAllowed);
        this.pMap = pMap;
        this.allowValueParameterInExpressionDocPath = allowValueParameterInExpressionDocPath;
    }

    public ProjectionTreeNode buildProjectionTree(List<DocPath> projectionList, ProjectionExpression.TreeNodeFactory nodeFactory, Map<String, Integer> nameParameterUsage, Set<String> topLevelFieldsWithNestedAccess, ExpressionsWrapperBase.MaxPathDepthCounter maxPathDepthCounter) {
        this.dbEnv.dbAssert(projectionList != null && projectionList.size() > 0, "buildProjectionTree", "projectionList should not be null nor empty", "projectionList", projectionList);
        ProjectionTreeNode root = nodeFactory.newNode();
        for (DocPath path : projectionList) {
            this.validateDocPath(path, nameParameterUsage, topLevelFieldsWithNestedAccess, maxPathDepthCounter);
            this.buildProjTreeInternal(root, path.getElements().iterator(), nodeFactory, path, 0);
        }
        this.assertTreeNodeNotEmpty(root);
        return root;
    }

    public void attachUpdateExpression(UpdateTreeNode treeRoot, List<UpdateListNode> nodeList, Map<String, Integer> nameParameterUsage, Map<String, Integer> valueParameterUsage, Map<String, Integer> valueParameterUsageForUpdateSize, Set<DocPath> pathsForUpdateSize, Set<String> topLevelFieldsWithNestedAccess, ExpressionsWrapperBase.OperatorCounter operatorCounter, ExpressionsWrapperBase.NodeCounter nodeCounter, ExpressionsWrapperBase.MaxPathDepthCounter maxPathDepthCounter) {
        for (UpdateListNode node : nodeList) {
            HashMap<String, Integer> currValueParameterUsage;
            boolean includeInUpdateRequestSize = node.getAction().getActionType().includeInUpdateRequestSize();
            HashMap<String, Integer> hashMap = currValueParameterUsage = includeInUpdateRequestSize ? new HashMap<String, Integer>() : valueParameterUsage;
            if (node.getExprTree() != null) {
                this.validateExpression(node.getExprTree(), nameParameterUsage, currValueParameterUsage, topLevelFieldsWithNestedAccess, operatorCounter, nodeCounter, maxPathDepthCounter);
            }
            this.validateUpdateAction(node);
            this.attachUpdateExpr(node, treeRoot, node.getDocPath().getElements().iterator());
            if (!includeInUpdateRequestSize) continue;
            pathsForUpdateSize.add(node.getDocPath());
            ExpressionValidator.addAllToParameterUsageMap(currValueParameterUsage, valueParameterUsage);
            ExpressionValidator.addAllToParameterUsageMap(currValueParameterUsage, valueParameterUsageForUpdateSize);
        }
        this.assertAllExprAttached(treeRoot);
    }

    public void validateExpression(ExprTreeNode treeNode, Map<String, Integer> nameParameterUsage, Map<String, Integer> valueParameterUsage, Set<String> topLevelFieldsWithNestedAccess, ExpressionsWrapperBase.OperatorCounter operatorCounter, ExpressionsWrapperBase.NodeCounter nodeCounter, ExpressionsWrapperBase.MaxPathDepthCounter maxPathDepthCounter) {
        String TRACE_HEADER = "ExpressionValidator.validExpression";
        this.dbEnv.dbAssert(treeNode != null, "ExpressionValidator.validExpression", "null node", new Object[0]);
        nodeCounter.increment();
        if (treeNode instanceof ExprTreeOpNode) {
            for (ExprTreeNode node : treeNode.getChildren()) {
                this.validateExpression(node, nameParameterUsage, valueParameterUsage, topLevelFieldsWithNestedAccess, operatorCounter, nodeCounter, maxPathDepthCounter);
            }
            this.validateOperator((ExprTreeOpNode)treeNode);
            this.incAndValidateOperatorCounter(operatorCounter);
            return;
        }
        if (treeNode instanceof ExprTreeValueNode) {
            ExprTreeValueNode valNode = (ExprTreeValueNode)treeNode;
            if (valNode.getLiteralSub() != null) {
                this.dbEnv.dbAssert(valNode.getLiteralSub().startsWith(":"), "ExpressionValidator.validExpression", "invalid literal substituion", new Object[0]);
                ExpressionValidator.incParameterUsageCount(valueParameterUsage, valNode.getLiteralSub());
                DocumentNode literalVal = this.getpMap().getMap().get(valNode.getLiteralSub());
                if (literalVal == null) {
                    this.dbEnv.throwValidationError(DbValidationError.LITERAL_PARAMETER_MISSING, "attribute value", valNode.getLiteralSub());
                }
                valNode.setValue(literalVal);
            } else {
                this.dbEnv.dbAssert(valNode.getValue() != null, "ExpressionValidator.validExpression", "no value", new Object[0]);
            }
            return;
        }
        this.dbEnv.dbAssert(treeNode instanceof ExprTreePathNode, "ExpressionValidator.validExpression", "bad tree node type", new Object[0]);
        ExprTreePathNode pathNode = (ExprTreePathNode)treeNode;
        this.validateDocPath(pathNode.getDocPath(), nameParameterUsage, topLevelFieldsWithNestedAccess, maxPathDepthCounter);
    }

    public ParameterMap getpMap() {
        return this.pMap;
    }

    private void buildProjTreeInternal(ProjectionTreeNode treeNode, Iterator<DocPathElement> pathElementIterator, ProjectionExpression.TreeNodeFactory nodeFactory, DocPath path, int depth) {
        String TRACE_HEADER = "ExpressionValidator.buildProjTreeInternal";
        this.dbEnv.dbAssert(pathElementIterator.hasNext(), "ExpressionValidator.buildProjTreeInternal", "pathElementIterator has next", new Object[0]);
        Map<DocPathElement, ProjectionTreeNode> childMap = treeNode.getChildMap();
        DocPathElement pathElement = pathElementIterator.next();
        this.dbEnv.dbAssert(pathElement != null, "ExpressionValidator.buildProjTreeInternal", "path is null", new Object[0]);
        if (!childMap.containsKey(pathElement)) {
            this.checkPathConflict(treeNode, pathElement, path, depth);
            childMap.put(pathElement, ExpressionValidator.convertPathToTree(pathElementIterator, nodeFactory));
            return;
        }
        ProjectionTreeNode childNode = childMap.get(pathElement);
        if (childNode != null && pathElementIterator.hasNext()) {
            this.buildProjTreeInternal(childNode, pathElementIterator, nodeFactory, path, depth + 1);
            return;
        }
        this.dbEnv.throwValidationError(DbValidationError.DOC_PATH_OVERLAP, "path one", path, "path two", this.buildPathToProjectionTreeLeaf(path, depth, treeNode));
    }

    private static ProjectionTreeNode convertPathToTree(Iterator<DocPathElement> iter, ProjectionExpression.TreeNodeFactory nodeFactory) {
        if (iter.hasNext()) {
            ProjectionTreeNode childNode = nodeFactory.newNode();
            childNode.getChildMap().put(iter.next(), ExpressionValidator.convertPathToTree(iter, nodeFactory));
            return childNode;
        }
        return null;
    }

    private void checkPathConflict(ProjectionTreeNode treeNode, DocPathElement pathElement, DocPath path, int depth) {
        if (treeNode.getChildMap().size() > 0) {
            DocPathElement treeNodeFirstElement = treeNode.getChildMap().keySet().iterator().next();
            if (pathElement.getCollectionType() != treeNodeFirstElement.getCollectionType()) {
                this.dbEnv.throwValidationError(DbValidationError.DOC_PATH_CONFLICT, "path one", path, "path two", this.buildPathToProjectionTreeLeaf(path, depth, treeNode));
            }
        }
    }

    private DocPath buildPathToProjectionTreeLeaf(DocPath path, int depth, ProjectionTreeNode treeNode) {
        DocPathElement e;
        ArrayList<DocPathElement> pathElements = new ArrayList<DocPathElement>();
        for (int i = 0; i < depth; ++i) {
            pathElements.add(path.getElements().get(i));
        }
        ProjectionTreeNode childNode = treeNode;
        do {
            this.dbEnv.dbAssert(childNode.getChildMap().size() > 0, "buildPathToProjectionTreeLeaf", "childNode.getChildMap().size() > 0", new Object[0]);
            e = childNode.getChildMap().keySet().iterator().next();
            pathElements.add(e);
        } while ((childNode = childNode.getChildMap().get(e)) != null && childNode.getChildMap().size() > 0);
        return new DocPath(pathElements);
    }

    private void validateDocPath(DocPath docPath, Map<String, Integer> nameParameterUsage, Set<String> topLevelFieldsWithNestedAccess, ExpressionsWrapperBase.MaxPathDepthCounter maxPathDepthCounter) {
        DocPathElement pathHead;
        List<DocPathElement> pathElements = docPath.getElements();
        if (pathElements == null || pathElements.size() < 1) {
            this.dbEnv.throwValidationError(DbValidationError.DOC_PATH_EMPTY, new Object[0]);
        }
        int maxDepth = this.dbEnv.getConfigInt(DbConfig.MAX_DOC_PATH_DEPTH);
        int pathDepth = pathElements.size();
        if (pathDepth > maxDepth) {
            this.dbEnv.throwValidationError(DbValidationError.DOC_PATH_TOO_DEEP, "nesting levels", pathDepth);
        }
        if (pathDepth > maxPathDepthCounter.value()) {
            maxPathDepthCounter.set(pathDepth);
        }
        if (!(pathHead = pathElements.get(0)).isMap()) {
            this.dbEnv.throwValidationError(DbValidationError.DOC_PATH_NOT_BEGIN_WITH_MAP, "path", docPath);
        }
        int size = pathElements.size();
        block4: for (int i = 0; i < size; ++i) {
            DocPathElement pathElement = pathElements.get(i);
            if (!pathElement.parameterized()) continue;
            if (pathElement.getFieldName().startsWith(":") && !this.allowValueParameterInExpressionDocPath) {
                this.dbEnv.throwValidationError(DbValidationError.DOC_PATH_CONTAINS_VALUE_PARAMETER, "attribute value", pathElement.getFieldName());
            }
            ExpressionValidator.incParameterUsageCount(nameParameterUsage, pathElement.getFieldName());
            DocumentNode element = this.getpMap().getMap().get(pathElement.getFieldName());
            if (element == null) {
                this.dbEnv.throwValidationError(DbValidationError.DOC_PATH_PARAMETER_MISSING, "attribute name", pathElement.getFieldName());
            }
            switch (element.getNodeType()) {
                case STRING: {
                    if (pathElement.getFieldName().startsWith("#")) {
                        pathElements.set(i, new DocPathMapElement(element.getSValue()));
                        continue block4;
                    }
                    if (pathElement.getFieldName().startsWith(":")) {
                        pathElements.set(i, new DocPathDictElement(element));
                        continue block4;
                    }
                    this.dbEnv.throwValidationError(DbValidationError.INVALID_ATTRIBUTE_TYPE_NAME, "attribute name", pathElement.getFieldName());
                    continue block4;
                }
                case NUMBER: 
                case INT: 
                case DECIMAL: 
                case FLOAT: 
                case DOUBLE: 
                case HELENUS_DECIMAL: 
                case BINARY: 
                case BOOLEAN: {
                    pathElements.set(i, new DocPathDictElement(element));
                }
            }
        }
        if (size > 1) {
            topLevelFieldsWithNestedAccess.add(pathElements.get(0).getFieldName());
        }
    }

    private void assertTreeNodeNotEmpty(ProjectionTreeNode node) {
        this.dbEnv.dbAssert(node.getChildMap().size() > 0, "ExpressionValidator.assertTreeNodeNotEmpty", "", new Object[0]);
        for (ProjectionTreeNode child2 : node.getChildMap().values()) {
            if (child2 == null) continue;
            this.assertTreeNodeNotEmpty(child2);
        }
    }

    private void assertAllExprAttached(UpdateTreeNode treeNode) {
        for (Map.Entry<DocPathElement, ProjectionTreeNode> e : treeNode.getChildMap().entrySet()) {
            if (e.getValue() == null) {
                this.dbEnv.dbAssert(treeNode.getUpdateMap().containsKey(e.getKey()), "ExpressionValidator.assertAllExprAttached", "missing action", new Object[0]);
                continue;
            }
            this.assertAllExprAttached((UpdateTreeNode)e.getValue());
        }
    }

    private void attachUpdateExpr(UpdateListNode listNode, UpdateTreeNode treeNode, Iterator<DocPathElement> iterator2) {
        String TRACE_HEADER = "ExpressionValidator.attachUpdateExpr";
        this.dbEnv.dbAssert(iterator2.hasNext(), "ExpressionValidator.attachUpdateExpr", "doc path is empty", new Object[0]);
        DocPathElement path = iterator2.next();
        this.dbEnv.dbAssert(treeNode.getChildMap().containsKey(path), "ExpressionValidator.attachUpdateExpr", "doc path lost", new Object[0]);
        UpdateTreeNode childNode = (UpdateTreeNode)treeNode.getChildMap().get(path);
        if (childNode == null) {
            UpdateAction prev;
            if (treeNode.getUpdateMap() == null) {
                treeNode.setUpdateMap(new HashMap<DocPathElement, UpdateAction>());
            }
            this.dbEnv.dbAssert((prev = treeNode.getUpdateMap().put(path, listNode.getAction())) == null, "ExpressionValidator.attachUpdateExpr", "prev not null", prev);
            return;
        }
        this.attachUpdateExpr(listNode, childNode, iterator2);
    }

    private void validateUpdateAction(UpdateListNode node) {
        UpdateActionType actionType = node.getAction().getActionType();
        ExprTreeNode expr = node.getExprTree();
        if (actionType == UpdateActionType.DELETE) {
            if (expr != null) {
                this.validateAddDeleteAction(actionType, TypeSet.ALLOWED_FOR_DELETE_OPERAND, expr);
            }
        } else if (actionType == UpdateActionType.ADD) {
            this.validateAddDeleteAction(actionType, TypeSet.ALLOWED_FOR_ADD_OPERAND, expr);
        }
    }

    private void validateAddDeleteAction(UpdateActionType actionType, TypeSet typeSet, ExprTreeNode expr) {
        this.dbEnv.dbAssert(expr instanceof ExprTreeValueNode, "ExpressionValidator.validateAddDeleteAction", "bad expr node type", new Object[0]);
        DocumentNodeType type = ((ExprTreeValueNode)expr).getValue().getNodeType();
        if (!typeSet.contains(type)) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_TYPE_CHECK_FAIL, new Object[]{"operator", actionType, "operand type", type, "typeSet", typeSet});
        }
    }

    private void incAndValidateOperatorCounter(ExpressionsWrapperBase.OperatorCounter operatorCounter) {
        int count = operatorCounter.increment();
        if (count > this.dbEnv.getConfigInt(DbConfig.MAX_OPERATOR_COUNT)) {
            this.dbEnv.throwValidationError(DbValidationError.MAX_OPERATORS_EXCEEDED, "operator count", count);
        }
    }

    private static void incParameterUsageCount(Map<String, Integer> usageMap, String parameter) {
        int n;
        Integer count = usageMap.get(parameter);
        if (count != null) {
            count = count + 1;
            n = count;
        } else {
            n = 1;
        }
        usageMap.put(parameter, n);
    }

    private static void addAllToParameterUsageMap(Map<String, Integer> source, Map<String, Integer> destination) {
        for (Map.Entry<String, Integer> entry : source.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Integer oldValue = destination.get(key);
            destination.put(key, oldValue != null ? oldValue + value : value);
        }
    }
}

