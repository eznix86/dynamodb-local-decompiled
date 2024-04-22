/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathListElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentCollectionType;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeHelper;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.datamodel.OperatorExecutor;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateAction;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateActionType;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateTreeNode;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbExecutionError;
import com.amazonaws.services.dynamodbv2.dbenv.DbInternalError;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class ExpressionExecutor
extends OperatorExecutor {
    private final DocumentNode userDocTreeRoot;
    private final DocumentNode systemDocTreeRoot;

    public ExpressionExecutor(DocumentNode userDoc, DocumentNode systemDoc, DbEnv dbEnv, DocumentFactory factory) {
        super(dbEnv, factory);
        this.userDocTreeRoot = userDoc;
        this.systemDocTreeRoot = systemDoc;
        this.assertValidTopLevelDocument(this.userDocTreeRoot);
    }

    public DocumentNode projectUserDoc(ProjectionTreeNode projTreeNode) {
        return this.project(projTreeNode, this.userDocTreeRoot);
    }

    public DocumentNode projectAndUnnestUserDoc(ProjectionTreeNode projTreeNode) {
        return this.projectAndUnnest(projTreeNode, this.userDocTreeRoot);
    }

    public DocumentNode evaluateExpression(ExprTreeNode treeNode) {
        DocumentNode result = this.evalExprInternal(treeNode);
        if (result == null) {
            this.dbEnv.throwExecutionError(DbExecutionError.ATTRIBUTE_NOT_FOUND, new Object[0]);
        }
        return result;
    }

    public DocumentNode getUserDocNew(UpdateTreeNode treeRoot) {
        Stack<DocPathElement> docPathForReporting = new Stack<DocPathElement>();
        DocumentNode docNew = this.createNewDocumentInternal(treeRoot, this.userDocTreeRoot, docPathForReporting);
        DocumentNodeHelper.resetDocLevels(docNew, this.dbEnv);
        return docNew;
    }

    public DocumentNode getUpdatedOldDocument(UpdateTreeNode treeRoot) {
        return this.projectUserDoc(treeRoot);
    }

    public DocumentNode getUpdatedNewDocument(UpdateTreeNode treeRoot, DocumentNode newDoc) {
        return this.project(treeRoot, newDoc);
    }

    public DocumentNode getUserDoc() {
        return this.userDocTreeRoot;
    }

    public DocumentNode getSystemDoc() {
        return this.systemDocTreeRoot;
    }

    private DocumentNode project(ProjectionTreeNode projTreeNode, DocumentNode docTreeNode) {
        this.dbEnv.dbAssert(projTreeNode.getChildMap().size() > 0, "ExpressionExecutor.project", "project tree node empty", new Object[0]);
        if (!DocumentNodeHelper.isMapOrList(docTreeNode)) {
            return null;
        }
        TreeMap<DocPathElement, DocumentNode> nodeMap = new TreeMap<DocPathElement, DocumentNode>();
        for (Map.Entry<DocPathElement, ProjectionTreeNode> entry : projTreeNode.getChildMap().entrySet()) {
            ProjectionTreeNode childProjectionNode;
            DocumentNode docNode;
            DocumentNode childNode = DocumentNodeHelper.getChild(docTreeNode, entry.getKey());
            if (childNode == null || (docNode = (childProjectionNode = entry.getValue()) == null ? childNode : this.project(childProjectionNode, childNode)) == null) continue;
            nodeMap.put(entry.getKey(), docNode);
        }
        if (nodeMap.size() == 0) {
            return null;
        }
        DocumentCollectionType collectionType = ((DocPathElement)nodeMap.entrySet().iterator().next().getKey()).getCollectionType();
        try {
            return this.docFactory.makeCollection(collectionType, nodeMap);
        } catch (IllegalArgumentException e) {
            this.dbEnv.throwInternalError(DbInternalError.UNEXPECTED_PATH_ELEMENT_TYPE, new Object[0]);
            return null;
        }
    }

    private DocumentNode projectAndUnnest(ProjectionTreeNode projTreeNode, DocumentNode docTreeNode) {
        Map<DocPathElement, DocumentNode> pathToNodeMap = this.projectAndUnnestToNodeMap(projTreeNode, docTreeNode, "");
        if (pathToNodeMap == null || pathToNodeMap.size() == 0) {
            return null;
        }
        DocumentCollectionType collectionType = pathToNodeMap.entrySet().iterator().next().getKey().getCollectionType();
        try {
            return this.docFactory.makeCollection(collectionType, pathToNodeMap);
        } catch (IllegalArgumentException e) {
            this.dbEnv.throwInternalError(DbInternalError.UNEXPECTED_PATH_ELEMENT_TYPE, new Object[0]);
            return null;
        }
    }

    private Map<DocPathElement, DocumentNode> projectAndUnnestToNodeMap(ProjectionTreeNode projTreeNode, DocumentNode docTreeNode, String parentDocPathString) {
        this.dbEnv.dbAssert(projTreeNode.getChildMap().size() > 0, "ExpressionExecutor.projectAndUnnestToNodeMap", "project tree node empty", new Object[0]);
        if (!DocumentNodeHelper.isMapOrList(docTreeNode)) {
            return null;
        }
        LinkedHashMap<DocPathElement, DocumentNode> nodeMap = new LinkedHashMap<DocPathElement, DocumentNode>();
        for (Map.Entry<DocPathElement, ProjectionTreeNode> entry : projTreeNode.getChildMap().entrySet()) {
            DocumentNode childNode = DocumentNodeHelper.getChild(docTreeNode, entry.getKey());
            if (childNode == null) continue;
            String currentDocPathString = this.concatenateDocPathElement(parentDocPathString, entry.getKey());
            ProjectionTreeNode childProjectionNode = entry.getValue();
            if (childProjectionNode == null) {
                nodeMap.put(new DocPathMapElement(currentDocPathString), childNode);
                continue;
            }
            Map<DocPathElement, DocumentNode> childMap = this.projectAndUnnestToNodeMap(childProjectionNode, childNode, currentDocPathString);
            if (childMap == null) continue;
            nodeMap.putAll(childMap);
        }
        return nodeMap;
    }

    private void assertValidTopLevelDocument(DocumentNode docTreeRoot) {
        this.dbEnv.dbAssert(docTreeRoot.isMap(), "ExpressionExecutor.assertValidTopLevelDocument", "not map", new Object[0]);
        this.assertValidDocument(docTreeRoot);
    }

    private void assertValidDocument(DocumentNode docTreeNode) {
        this.dbEnv.dbAssert(docTreeNode != null, "ExpressionExecutor.assertValidDocument", "doc node null", new Object[0]);
        if (!DocumentNodeHelper.isMapOrList(docTreeNode)) {
            return;
        }
        for (DocPathElement e : docTreeNode.getChildren()) {
            this.assertValidDocument(docTreeNode.getChild(e));
        }
    }

    private DocumentNode evalExprInternal(ExprTreeNode treeNode) {
        if (treeNode instanceof ExprTreeOpNode) {
            ArrayList<DocumentNode> docList = new ArrayList<DocumentNode>();
            for (ExprTreeNode node : treeNode.getChildren()) {
                docList.add(this.evalExprInternal(node));
            }
            return this.executeOperator(docList, (ExprTreeOpNode)treeNode);
        }
        if (treeNode instanceof ExprTreePathNode) {
            return this.evaluatePathNode((ExprTreePathNode)treeNode);
        }
        this.dbEnv.dbAssert(treeNode instanceof ExprTreeValueNode, "ExpressionExecutor.evaluateExpression", "bad tree node", new Object[0]);
        ExprTreeValueNode valueNode = (ExprTreeValueNode)treeNode;
        this.dbEnv.dbAssert(valueNode.getValue() != null, "ExpressionExecutor.evaluateExpression", "no value", new Object[0]);
        return valueNode.getValue();
    }

    private DocumentNode evaluatePathNode(ExprTreePathNode treeNode) {
        return this.evaluateDocPath(treeNode.getDocPath());
    }

    DocumentNode evaluateDocPath(DocPath path) {
        return this.evaluateDocPathInternal(path.getElements().iterator(), this.userDocTreeRoot);
    }

    private DocumentNode evaluateDocPathInternal(Iterator<DocPathElement> iterator2, DocumentNode docTreeNode) {
        this.dbEnv.dbAssert(iterator2.hasNext(), "ExpressionExecutor.evalPath", "iterator invalid", new Object[0]);
        this.dbEnv.dbAssert(docTreeNode != null, "ExpressionExecutor.evalPath", "docTree invalid", new Object[0]);
        if (!DocumentNodeHelper.isMapOrList(docTreeNode)) {
            return null;
        }
        DocumentNode child2 = DocumentNodeHelper.getChild(docTreeNode, iterator2.next());
        if (!iterator2.hasNext() || child2 == null) {
            return child2;
        }
        return this.evaluateDocPathInternal(iterator2, child2);
    }

    private DocumentNode executeOperator(List<DocumentNode> docList, ExprTreeOpNode treeNode) {
        Operator op = treeNode.getOperator();
        this.dbEnv.dbAssert(op.numOperandsOk(docList.size()), "executeOperator", "invalid number of operands", new Object[]{"operator", op, "operands", docList});
        for (DocumentNode d : docList) {
            if (!op.allowNullOperands() && d == null) {
                this.dbEnv.throwExecutionError(DbExecutionError.ATTRIBUTE_NOT_FOUND, new Object[]{"operator or function", op});
            }
            if (d == null || op.getOperandType().contains(d.getNodeType())) continue;
            switch (op.getTypeMismatchBehavior()) {
                case RETURN_FALSE: {
                    return this.docFactory.makeBoolean(false);
                }
                case RETURN_MISSING: {
                    return null;
                }
                case THROW_ERROR: {
                    this.dbEnv.throwExecutionError(DbExecutionError.OPERAND_TYPE_INVALID, new Object[]{"operator or function", op, "operand type", d.getNodeType()});
                }
            }
            this.dbEnv.dbAssert(false, "executeOperator", "Unsupported typeMismatchBehavior found", new Object[]{"typeMismatchBehavior", op.getTypeMismatchBehavior()});
        }
        return op.evaluate(docList, this);
    }

    private DocumentNode createNewDocumentInternal(UpdateTreeNode updTreeNode, DocumentNode docTreeNode, Stack<DocPathElement> docPathForReporting) {
        this.dbEnv.dbAssert(docTreeNode != null && updTreeNode != null, "updateDoc", "node null", new Object[0]);
        if (!DocumentNodeHelper.isMapOrList(docTreeNode)) {
            this.dbEnv.throwExecutionError(DbExecutionError.DOC_PATH_NOT_VALID_FOR_UPDATE, "path", docPathForReporting);
        }
        TreeMap<DocPathElement, DocumentNode> resultNodeMap = new TreeMap<DocPathElement, DocumentNode>();
        for (Map.Entry<DocPathElement, ProjectionTreeNode> entry : updTreeNode.getChildMap().entrySet()) {
            DocPathElement path = entry.getKey();
            if (!path.isCompatibleWithDocumentNode(docTreeNode)) {
                docPathForReporting.push(path);
                this.dbEnv.throwExecutionError(DbExecutionError.DOC_PATH_NOT_VALID_FOR_UPDATE, "path", docPathForReporting);
            }
            DocumentNode docChildNode = docTreeNode.getChild(path);
            UpdateTreeNode childUpdTreeNode = (UpdateTreeNode)entry.getValue();
            DocumentNode resultDocNode = null;
            if (childUpdTreeNode == null) {
                this.dbEnv.dbAssert(updTreeNode.getUpdateMap().containsKey(path), "ExpressionExecutor.getDocNew", "bad updateMap", new Object[0]);
                resultDocNode = this.getUpdateActionResult(updTreeNode, path, docChildNode);
            } else if (docChildNode != null) {
                docPathForReporting.push(path);
                resultDocNode = this.createNewDocumentInternal(childUpdTreeNode, docChildNode, docPathForReporting);
                docPathForReporting.pop();
                this.dbEnv.dbAssert(resultDocNode != null, "ExpressionExecutor.getDocNew", "sub-document update cannot be null", new Object[0]);
            } else {
                docPathForReporting.push(path);
                this.dbEnv.throwExecutionError(DbExecutionError.DOC_PATH_NOT_VALID_FOR_UPDATE, "path", docPathForReporting);
            }
            if (resultDocNode == null) continue;
            this.uniqueMapPut(resultNodeMap, path, resultDocNode);
        }
        for (DocPathElement docChildPath : docTreeNode.getChildren()) {
            if (updTreeNode.getChildMap().containsKey(docChildPath)) continue;
            this.uniqueMapPut(resultNodeMap, docChildPath, docTreeNode.getChild(docChildPath));
        }
        switch (docTreeNode.getNodeType()) {
            case MAP: {
                return this.docFactory.makeMap(resultNodeMap);
            }
            case DICT: {
                return this.docFactory.makeDict(resultNodeMap);
            }
            case LIST: {
                return this.docFactory.makeList(resultNodeMap);
            }
        }
        return null;
    }

    private DocumentNode getUpdateActionResult(UpdateTreeNode updTreeNode, DocPathElement path, DocumentNode docNode) {
        String TRACE_HEADER = "ExpressionExecutor.getUpdateActionResult";
        DocumentNode resultDocNode = null;
        UpdateAction action = updTreeNode.getUpdateMap().get(path);
        ExprTreeNode expr = action.getExprTree();
        UpdateActionType actionType = action.getActionType();
        if (actionType == UpdateActionType.SET) {
            this.dbEnv.dbAssert(expr != null, "ExpressionExecutor.getUpdateActionResult", "missing expr for SET action", new Object[0]);
            resultDocNode = this.evaluateExpression(expr);
        } else if (actionType == UpdateActionType.DELETE) {
            resultDocNode = this.execDeleteAction(action, docNode);
        } else {
            this.dbEnv.dbAssert(actionType == UpdateActionType.ADD, "ExpressionExecutor.getUpdateActionResult", "unknown action type", new Object[0]);
            resultDocNode = this.execAddAction(action, docNode);
            this.dbEnv.dbAssert(resultDocNode != null, "ExpressionExecutor.getUpdateActionResult", "result null", new Object[0]);
        }
        return resultDocNode;
    }

    private DocumentNode execDeleteAction(UpdateAction action, DocumentNode docNode) {
        String TRACE_HEADER = "ExpressionExecutor.processDeleteAction";
        if (action.getExprTree() == null) {
            return null;
        }
        this.dbEnv.dbAssert(action.getExprTree() instanceof ExprTreeValueNode, "ExpressionExecutor.processDeleteAction", "not value node", new Object[0]);
        DocumentNode left = docNode;
        DocumentNode right = ((ExprTreeValueNode)action.getExprTree()).getValue();
        this.dbEnv.dbAssert(TypeSet.SET.contains(right.getNodeType()), "ExpressionExecutor.processDeleteAction", "bad type", new Object[0]);
        if (left == null) {
            return null;
        }
        this.validateDeleteActionDocumentNodeType(left, right);
        if (left.getNodeType() == DocumentNodeType.DICT) {
            return this.collectionRemoval(left, right);
        }
        return this.setDiff(left, right, false);
    }

    private DocumentNode execAddAction(UpdateAction action, DocumentNode docNode) {
        String TRACE_HEADER = "ExpressionExecutor.processAddAction";
        this.dbEnv.dbAssert(action.getExprTree() instanceof ExprTreeValueNode, "ExpressionExecutor.processAddAction", "not value node", new Object[0]);
        DocumentNode left = docNode;
        DocumentNode right = ((ExprTreeValueNode)action.getExprTree()).getValue();
        this.dbEnv.dbAssert(TypeSet.ALLOWED_FOR_ADD_OPERAND.contains(right.getNodeType()), "ExpressionExecutor.processAddAction", "bad type", new Object[0]);
        if (left == null) {
            return right;
        }
        this.validateAddActionDocumentNodeType(left, right);
        if (right.getNodeType() == DocumentNodeType.NUMBER) {
            return this.addition(left, right);
        }
        if (DocumentNodeType.DICT.equals((Object)right.getNodeType())) {
            return this.collectionUnion(left, right);
        }
        return this.setUnion(left, right);
    }

    private void validateDeleteActionDocumentNodeType(DocumentNode left, DocumentNode right) {
        if (DocumentNodeType.DICT.equals((Object)left.getNodeType())) {
            if (!TypeSet.SET.contains(right.getNodeType())) {
                this.dbEnv.throwExecutionError(DbExecutionError.DICTIONARY_OPERAND_TYPE_MISMATCH, new Object[]{"update action", UpdateActionType.DELETE, "operand one", left.getNodeType(), "operand two", right.getNodeType()});
            } else {
                return;
            }
        }
        if (right.getNodeType() != left.getNodeType()) {
            this.dbEnv.throwExecutionError(DbExecutionError.OPERAND_TYPE_MISMATCH, new Object[]{"update action", UpdateActionType.DELETE, "operand one", left.getNodeType(), "operand two", right.getNodeType()});
        }
    }

    private void validateAddActionDocumentNodeType(DocumentNode left, DocumentNode right) {
        if (right.getNodeType() != left.getNodeType()) {
            this.dbEnv.throwExecutionError(DbExecutionError.OPERAND_TYPE_MISMATCH, new Object[]{"update action", UpdateActionType.ADD, "operand one", left.getNodeType(), "operand two", right.getNodeType()});
        }
    }

    private void uniqueMapPut(TreeMap<DocPathElement, DocumentNode> resultNodeMap, DocPathElement path, DocumentNode resultDocNode) {
        DocumentNode prev = resultNodeMap.put(path, resultDocNode);
        this.dbEnv.dbAssert(prev == null, "ExpressionExecutor.safeMapPut", "duplicate entry", new Object[0]);
    }

    private String concatenateDocPathElement(String currentDocPath, DocPathElement element) {
        if (element instanceof DocPathMapElement) {
            return element.toString();
        }
        if (element instanceof DocPathListElement) {
            return currentDocPath + element.toString();
        }
        this.dbEnv.dbAssert(false, "projectAndUnnestToNodeMap", "Dict type in unnesting projection", new Object[0]);
        return null;
    }
}

