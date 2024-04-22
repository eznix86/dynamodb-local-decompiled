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
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.datamodel.OperatorExecutor;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateAction;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateActionType;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateExpression;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateTreeNode;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbInternalError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExpressionSerializer
extends OperatorExecutor {
    protected static final DocPathElement PROJECTION_EXPR_KEY = new DocPathMapElement(Keys.PROJECTION_EXPR.getName());
    protected static final DocPathElement UPDATE_EXPR_KEY = new DocPathMapElement(Keys.UPDATE_EXPR.getName());
    protected static final DocPathElement CONDITION_EXPR_KEY = new DocPathMapElement(Keys.CONDITION_EXPR.getName());
    protected static final DocPathElement IS_MAP_KEY = new DocPathMapElement(Keys.IS_MAP.getName());
    protected static final DocPathElement ELEMENT_MAP_KEY = new DocPathMapElement(Keys.ELEMENT_MAP.getName());
    protected static final DocPathElement DOC_PATH_KEY = new DocPathMapElement(Keys.DOC_PATH.getName());
    protected static final DocPathElement LITERAL_KEY = new DocPathMapElement(Keys.LITERAL.getName());
    protected static final DocPathElement OPERATOR_KEY = new DocPathMapElement(Keys.OPERATOR.getName());
    protected static final DocPathElement OPERANDS_KEY = new DocPathMapElement(Keys.OPERANDS.getName());
    protected static final DocPathElement NUM_OPERANDS_KEY = new DocPathMapElement(Keys.NUM_OPERANDS.getName());
    protected static final DocPathElement UPDATE_ACTION_KEY = new DocPathMapElement(Keys.UPDATE_ACTION.getName());
    protected static final DocPathElement UPDATE_VALUE_KEY = new DocPathMapElement(Keys.UPDATE_VALUE.getName());

    public ExpressionSerializer(DbEnv dbEnv, DocumentFactory factory) {
        super(dbEnv, factory);
    }

    public DocumentNode packProjection(ProjectionExpression proj) {
        HashMap<DocPathElement, DocumentNode> map2 = new HashMap<DocPathElement, DocumentNode>();
        DocumentNode prev = map2.put(PROJECTION_EXPR_KEY, this.packProjectionNode(proj.getTreeRoot()));
        this.dbEnv.dbAssert(prev == null, "packProjection", "prev == null", new Object[0]);
        return this.docFactory.makeMap(map2);
    }

    private DocumentNode packProjectionNode(ProjectionTreeNode root) {
        HashMap<DocPathElement, DocumentNode> map2 = new HashMap<DocPathElement, DocumentNode>();
        HashMap<DocPathElement, DocumentNode> innerMap = new HashMap<DocPathElement, DocumentNode>();
        this.dbEnv.dbAssert(root.getChildMap().size() > 0, "packProjectionNode", "root.getChildMap not empty", new Object[0]);
        boolean isMap = root.getChildMap().keySet().iterator().next().isMap();
        for (Map.Entry<DocPathElement, ProjectionTreeNode> entry : root.getChildMap().entrySet()) {
            ProjectionTreeNode projNode;
            DocPathElement pathElement;
            DocPathElement key = pathElement = entry.getKey();
            this.dbEnv.dbAssert(isMap == pathElement.isMap(), "packProjectionNode", "all elements must be the same type: map or list", new Object[0]);
            if (!isMap) {
                key = new DocPathMapElement(String.valueOf(pathElement.getListIndex()));
            }
            if ((projNode = entry.getValue()) != null) {
                DocumentNode prev = innerMap.put(key, this.packProjectionNode(projNode));
                this.dbEnv.dbAssert(prev == null, "packProjection", "prev == null for innerMap", new Object[0]);
                continue;
            }
            this.packLeafNode(root, innerMap, pathElement, key);
        }
        this.putIsMapKey(map2, isMap);
        DocumentCollectionType collectionType = ((DocPathElement)innerMap.entrySet().iterator().next().getKey()).getCollectionType();
        DocumentNode prev = map2.put(ELEMENT_MAP_KEY, this.docFactory.makeCollection(collectionType, innerMap));
        this.dbEnv.dbAssert(prev == null, "packProjection", "prev == null for map", new Object[0]);
        return this.docFactory.makeMap(map2);
    }

    private void packLeafNode(ProjectionTreeNode root, Map<DocPathElement, DocumentNode> innerMap, DocPathElement pathElement, DocPathElement key) {
        if (root instanceof UpdateTreeNode) {
            Map<DocPathElement, UpdateAction> updateMap = ((UpdateTreeNode)root).getUpdateMap();
            this.dbEnv.dbAssert(updateMap != null, "packLeafNode", "updateMap missing", "node", root);
            DocumentNode prev = innerMap.put(key, this.packUpdateSubTree(updateMap.get(pathElement)));
            this.dbEnv.dbAssert(prev == null, "packLeafNode", "prev == null after packUpdateSubTree", new Object[0]);
        } else {
            DocumentNode prev = innerMap.put(key, this.docFactory.makeNull());
            this.dbEnv.dbAssert(prev == null, "packLeafNode", "prev == null when makeNull()", new Object[0]);
        }
    }

    private DocumentNode packUpdateSubTree(UpdateAction action) {
        ExprTreeNode actionExprTree = action.getExprTree();
        DocumentNode exprTree = actionExprTree != null ? this.packExpression(actionExprTree) : this.docFactory.makeNull();
        HashMap<DocPathElement, DocumentNode> updateDocMap = new HashMap<DocPathElement, DocumentNode>();
        DocumentNode prev = updateDocMap.put(UPDATE_ACTION_KEY, this.docFactory.makeString(action.getActionType().toString()));
        this.dbEnv.dbAssert(prev == null, "packUpdateSubTree", "prev == null after UPDATE_ACTION_KEY", new Object[0]);
        prev = updateDocMap.put(UPDATE_VALUE_KEY, exprTree);
        this.dbEnv.dbAssert(prev == null, "packUpdateSubTree", "prev == null after UPDATE_VALUE_KEY", new Object[0]);
        return this.docFactory.makeMap(updateDocMap);
    }

    public ProjectionExpression unpackProjection(DocumentNode proj) {
        DocumentNode expNode = this.unpackMapElement(proj, PROJECTION_EXPR_KEY, proj);
        return new ProjectionExpression(this.buildProjection(expNode, (NodeFactory<? extends ProjectionTreeNode>)new NodeFactory<ProjectionTreeNode>(){

            @Override
            public ProjectionTreeNode makeNode() {
                return new ProjectionTreeNode();
            }
        }, proj));
    }

    private DocumentNode unpackMapElement(DocumentNode subDoc, DocPathElement key, DocumentNode topLevelDoc) {
        this.assumeMapType(subDoc, topLevelDoc);
        return this.unpackSubDocumentAssumedMap(subDoc, key, topLevelDoc);
    }

    private DocumentNode unpackSubDocumentAssumedMap(DocumentNode doc, DocPathElement key, DocumentNode topLevelDoc) {
        DocumentNode childNode = doc.getChild(key);
        if (childNode == null) {
            this.dbEnv.throwInternalError(DbInternalError.MAP_KEY_EXPECTED, "key", key, "subDoc", doc, "topLevelDoc", topLevelDoc);
        }
        return childNode;
    }

    private void assumeMapType(DocumentNode subDoc, DocumentNode topLevelDoc) {
        if (subDoc.getNodeType() != DocumentNodeType.MAP && subDoc.getNodeType() != DocumentNodeType.DICT) {
            this.dbEnv.throwInternalError(DbInternalError.MAP_TYPE_EXPECTED, new Object[]{"subDoc", subDoc, "subdoc type", subDoc.getNodeType(), "topLevelDoc", topLevelDoc});
        }
    }

    private boolean unpackBoolean(DocumentNode node, DocPathElement key, DocumentNode topLevelDoc) {
        this.assumeMapType(node, topLevelDoc);
        DocumentNode childNode = node.getChild(key);
        if (childNode == null) {
            this.dbEnv.throwInternalError(DbInternalError.MAP_KEY_EXPECTED, "key", key, "subDoc", node, "topLevelDoc", topLevelDoc);
        }
        if (childNode.getNodeType() != DocumentNodeType.BOOLEAN) {
            this.dbEnv.throwInternalError(DbInternalError.BOOL_TYPE_EXPECTED, "doc", childNode, "topLevelDoc", topLevelDoc);
        }
        return childNode.getBooleanValue();
    }

    private int strToIntSafe(String s, DocumentNode topLevelDoc) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            this.dbEnv.throwInternalError(DbInternalError.INT_EXPECTED, "str", s, "topLevel", topLevelDoc);
            this.dbEnv.dbAssert(false, "strToIntSafe", "should not reach here", new Object[0]);
            throw new RuntimeException("not reachable");
        }
    }

    private ProjectionTreeNode buildProjection(DocumentNode docNode, NodeFactory<? extends ProjectionTreeNode> factory, DocumentNode topLevelDoc) {
        ProjectionTreeNode projNode = factory.makeNode();
        Map<DocPathElement, ProjectionTreeNode> projNodeMap = projNode.getChildMap();
        boolean isMap = this.unpackBoolean(docNode, IS_MAP_KEY, topLevelDoc);
        DocumentNode child2 = this.unpackSubDocumentAssumedMap(docNode, ELEMENT_MAP_KEY, topLevelDoc);
        this.assumeMapType(child2, topLevelDoc);
        for (DocPathElement docPathElement : child2.getChildren()) {
            ProjectionTreeNode prev;
            DocPathElement element = isMap ? docPathElement : new DocPathListElement(this.strToIntSafe(docPathElement.getFieldName(), topLevelDoc));
            DocumentNode docNodeChild = this.unpackSubDocumentAssumedMap(child2, docPathElement, topLevelDoc);
            if (docNodeChild.getNodeType() == DocumentNodeType.NULL) {
                prev = projNodeMap.put(element, null);
                this.dbEnv.dbAssert(prev == null, "buildProjection", "prev == null after DocumentNodeType.NULL", new Object[0]);
                continue;
            }
            this.assumeMapType(docNodeChild, topLevelDoc);
            if (docNodeChild.getChild(IS_MAP_KEY) == null) {
                projNodeMap.put(element, null);
                this.buildUpdateSubTree((UpdateTreeNode)projNode, element, docNodeChild, topLevelDoc);
                continue;
            }
            prev = projNodeMap.put(element, this.buildProjection(docNodeChild, factory, topLevelDoc));
            this.dbEnv.dbAssert(prev == null, "buildProjection", "prev == null after IS_MAP_KEY not null", new Object[0]);
        }
        return projNode;
    }

    private void buildUpdateSubTree(UpdateTreeNode updateTreeNode, DocPathElement element, DocumentNode docNodeChild, DocumentNode topLevelDoc) {
        DocumentNode exprTree;
        DocumentNode actionTypeNode = this.unpackMapElement(docNodeChild, UPDATE_ACTION_KEY, topLevelDoc);
        this.assumeStringType(actionTypeNode, topLevelDoc);
        UpdateActionType action = this.unpackActionType(actionTypeNode.getSValue(), topLevelDoc);
        Map<DocPathElement, UpdateAction> updateMap = updateTreeNode.getUpdateMap();
        if (updateMap == null) {
            updateMap = new HashMap<DocPathElement, UpdateAction>();
        }
        if ((exprTree = this.unpackMapElement(docNodeChild, UPDATE_VALUE_KEY, topLevelDoc)).getNodeType() == DocumentNodeType.NULL) {
            UpdateAction prev = updateMap.put(element, new UpdateAction(action, null));
            this.dbEnv.dbAssert(prev == null, "buildUpdateSubTree", "prev == null after DocumentNodeType.NULL", new Object[0]);
        } else {
            UpdateAction prev = updateMap.put(element, new UpdateAction(action, this.buildExpression(exprTree, topLevelDoc)));
            this.dbEnv.dbAssert(prev == null, "buildUpdateSubTree", "prev == null after buildExpression", new Object[0]);
        }
        updateTreeNode.setUpdateMap(updateMap);
    }

    private void assumeStringType(DocumentNode subDoc, DocumentNode topLevelDoc) {
        if (subDoc.getNodeType() != DocumentNodeType.STRING) {
            this.dbEnv.throwInternalError(DbInternalError.STR_EXPECTED, "subDoc", subDoc, "topLevel", topLevelDoc);
        }
    }

    private UpdateActionType unpackActionType(String actionTypeStr, DocumentNode topLevelDoc) {
        try {
            UpdateActionType action = UpdateActionType.valueOf(actionTypeStr);
            return action;
        } catch (Exception e) {
            this.dbEnv.throwInternalError(DbInternalError.ACTION_TYPE_EXPECTED, "str", actionTypeStr, "topLevel", topLevelDoc);
            this.dbEnv.dbAssert(false, "strToIntSafe", "should not reach here", new Object[0]);
            throw new RuntimeException("not reachable");
        }
    }

    public DocumentNode packUpdateExpression(UpdateExpression updateExpr) {
        HashMap<DocPathElement, DocumentNode> map2 = new HashMap<DocPathElement, DocumentNode>();
        DocumentNode prev = map2.put(UPDATE_EXPR_KEY, this.packProjectionNode(updateExpr.getTreeRoot()));
        this.dbEnv.dbAssert(prev == null, "packUpdateExpression", "prev == null", new Object[0]);
        return this.docFactory.makeMap(map2);
    }

    public UpdateExpression unpackUpdateExpression(DocumentNode updateNode) {
        DocumentNode updExpNode = this.unpackMapElement(updateNode, UPDATE_EXPR_KEY, updateNode);
        UpdateTreeNode node = (UpdateTreeNode)this.buildProjection(updExpNode, () -> new UpdateTreeNode(), updateNode);
        return new UpdateExpression(node);
    }

    public DocumentNode packCondition(Expression exp) {
        HashMap<DocPathElement, DocumentNode> map2 = new HashMap<DocPathElement, DocumentNode>();
        DocumentNode prev = map2.put(CONDITION_EXPR_KEY, this.packExpression(exp.getExprTree()));
        this.dbEnv.dbAssert(prev == null, "packUpdateExpression", "prev == null", new Object[0]);
        return this.docFactory.makeMap(map2);
    }

    private DocumentNode packExpression(ExprTreeNode root) {
        if (root instanceof ExprTreeOpNode) {
            return this.packOpNode((ExprTreeOpNode)root);
        }
        if (root instanceof ExprTreePathNode) {
            HashMap<DocPathElement, DocumentNode> pathMap = new HashMap<DocPathElement, DocumentNode>();
            DocPath docPath = ((ExprTreePathNode)root).getDocPath();
            DocumentNode prev = pathMap.put(DOC_PATH_KEY, this.packDocPath(docPath.getElements().iterator()));
            this.dbEnv.dbAssert(prev == null, "packExpression", "prev == null", new Object[0]);
            return this.docFactory.makeMap(pathMap);
        }
        this.dbEnv.dbAssert(root instanceof ExprTreeValueNode, "packExpression", "bad expr tree node type", "node", root);
        return this.packLiteral((ExprTreeValueNode)root);
    }

    private DocumentNode packLiteral(ExprTreeValueNode root) {
        HashMap<DocPathElement, DocumentNode> map2 = new HashMap<DocPathElement, DocumentNode>();
        DocumentNode prev = map2.put(LITERAL_KEY, root.getValue());
        this.dbEnv.dbAssert(prev == null, "packLiteral", "prev == null", new Object[0]);
        return this.docFactory.makeMap(map2);
    }

    private DocumentNode packOpNode(ExprTreeOpNode opRoot) {
        HashMap<DocPathElement, DocumentNode> map2 = new HashMap<DocPathElement, DocumentNode>();
        DocumentNode prev = map2.put(OPERATOR_KEY, this.docFactory.makeString(opRoot.getOperator().name()));
        this.dbEnv.dbAssert(prev == null, "packOpNode", "prev == null after OPERATOR_KEY", new Object[0]);
        HashMap<DocPathElement, DocumentNode> operandsMap = new HashMap<DocPathElement, DocumentNode>();
        prev = operandsMap.put(NUM_OPERANDS_KEY, this.docFactory.makeString(String.valueOf(opRoot.getChildren().size())));
        this.dbEnv.dbAssert(prev == null, "packOpNode", "prev == null after NUM_OPERANDS_KEY", new Object[0]);
        int index = 0;
        for (ExprTreeNode childNode : opRoot.getChildren()) {
            operandsMap.put(new DocPathMapElement(String.valueOf(index)), this.packExpression(childNode));
            ++index;
        }
        prev = map2.put(OPERANDS_KEY, this.docFactory.makeMap(operandsMap));
        this.dbEnv.dbAssert(prev == null, "packOpNode", "prev == null after OPERANDS_KEY", new Object[0]);
        return this.docFactory.makeMap(map2);
    }

    private DocumentNode packDocPath(Iterator<DocPathElement> it) {
        DocumentNode prev;
        if (!it.hasNext()) {
            return this.docFactory.makeNull();
        }
        HashMap<DocPathElement, DocumentNode> map2 = new HashMap<DocPathElement, DocumentNode>();
        HashMap<DocPathElement, DocumentNode> innerMap = new HashMap<DocPathElement, DocumentNode>();
        DocPathElement element = it.next();
        this.putIsMapKey(map2, element.isMap());
        if (!element.isMap()) {
            element = new DocPathMapElement(String.valueOf(element.getListIndex()));
        }
        this.dbEnv.dbAssert((prev = innerMap.put(element, this.packDocPath(it))) == null, "packDocPath", "prev == null after innerMap", new Object[0]);
        this.dbEnv.dbAssert(element.isMap(), "packDocPath", "element has to be a map or dictionary", new Object[0]);
        prev = map2.put(ELEMENT_MAP_KEY, this.docFactory.makeCollection(element.getCollectionType(), innerMap));
        this.dbEnv.dbAssert(prev == null, "packDocPath", "prev == null after map", new Object[0]);
        return this.docFactory.makeMap(map2);
    }

    public Expression unpackCondition(DocumentNode exprNode) {
        DocumentNode expNode = this.unpackMapElement(exprNode, CONDITION_EXPR_KEY, exprNode);
        return new Expression(this.buildExpression(expNode, exprNode));
    }

    private ExprTreeNode buildExpression(DocumentNode exprNode, DocumentNode topLevel) {
        this.assumeMapType(exprNode, topLevel);
        if (exprNode.getChild(OPERATOR_KEY) != null) {
            return this.buildOpNode(exprNode, topLevel);
        }
        if (exprNode.getChild(DOC_PATH_KEY) != null) {
            ArrayList<DocPathElement> elements = new ArrayList<DocPathElement>();
            this.buildDocPath(elements, exprNode.getChild(DOC_PATH_KEY), topLevel);
            return new ExprTreePathNode(new DocPath(elements));
        }
        if (exprNode.getChild(LITERAL_KEY) == null) {
            this.dbEnv.throwInternalError(DbInternalError.LITERAL_EXPECTED, "subDoc", exprNode, "topLevel", topLevel);
        }
        return new ExprTreeValueNode(exprNode.getChild(LITERAL_KEY));
    }

    private Operator unpackOperator(String operatorTypeStr, DocumentNode topLevelDoc) {
        Operator operator = null;
        try {
            operator = Operator.valueOf(operatorTypeStr);
        } catch (Exception e) {
            this.dbEnv.throwInternalError(DbInternalError.OPERATOR_EXPECTED, "str", operatorTypeStr, "topLevel", topLevelDoc);
        }
        if (Operator.NOT_SUPPORTED_OPERATORS.containsKey(operatorTypeStr)) {
            this.dbEnv.throwInternalError(DbInternalError.NOT_SUPPORTED_OPERATOR, "operator", operatorTypeStr);
        }
        this.dbEnv.dbAssert(operator != null, "unpackOperator", "operator not null", new Object[0]);
        return operator;
    }

    private ExprTreeNode buildOpNode(DocumentNode exprNode, DocumentNode topLevel) {
        this.assumeStringType(exprNode.getChild(OPERATOR_KEY), topLevel);
        Operator operator = this.unpackOperator(exprNode.getChild(OPERATOR_KEY).getSValue(), topLevel);
        DocumentNode operandsNode = this.unpackSubDocumentAssumedMap(exprNode, OPERANDS_KEY, topLevel);
        DocumentNode numOperandsNode = this.unpackMapElement(operandsNode, NUM_OPERANDS_KEY, topLevel);
        this.assumeStringType(numOperandsNode, topLevel);
        int numOperands = this.strToIntSafe(numOperandsNode.getSValue(), topLevel);
        if (!operator.numOperandsOk(numOperands)) {
            this.dbEnv.throwInternalError(DbInternalError.NUM_OPERANDS_ERROR, "operator", operator.getOperatorName(), "number of operands", numOperands);
        }
        ArrayList<ExprTreeNode> children = new ArrayList<ExprTreeNode>();
        for (int i = 0; i < numOperands; ++i) {
            DocumentNode child2 = this.unpackSubDocumentAssumedMap(operandsNode, new DocPathMapElement(String.valueOf(i)), topLevel);
            children.add(this.buildExpression(child2, topLevel));
        }
        return new ExprTreeOpNode(children, operator);
    }

    private void buildDocPath(List<DocPathElement> elements, DocumentNode exprNode, DocumentNode topLevel) {
        if (exprNode.getNodeType() == DocumentNodeType.NULL) {
            return;
        }
        boolean isMap = this.unpackBoolean(exprNode, IS_MAP_KEY, topLevel);
        DocumentNode elementsDoc = this.unpackSubDocumentAssumedMap(exprNode, ELEMENT_MAP_KEY, topLevel);
        this.assumeMapType(elementsDoc, topLevel);
        if (elementsDoc.getChildren().size() < 1) {
            this.dbEnv.throwInternalError(DbInternalError.NON_EMPTY_MAP_EXPECTED, "subDoc", exprNode, "topLevel", topLevel);
        }
        DocPathElement element = elementsDoc.getChildren().get(0);
        DocumentNode child2 = elementsDoc.getChild(element);
        element = isMap ? element : new DocPathListElement(this.strToIntSafe(element.getFieldName(), topLevel));
        elements.add(element);
        if (child2.getNodeType() != DocumentNodeType.NULL) {
            this.buildDocPath(elements, child2, topLevel);
        }
    }

    private void putIsMapKey(Map<DocPathElement, DocumentNode> map2, boolean isMap) {
        if (isMap) {
            DocumentNode prev = map2.put(IS_MAP_KEY, this.docFactory.makeBoolean(true));
            this.dbEnv.dbAssert(prev == null, "putIsMapKey", "prev == null for IS_MAP_KEY true", new Object[0]);
        } else {
            DocumentNode prev = map2.put(IS_MAP_KEY, this.docFactory.makeBoolean(false));
            this.dbEnv.dbAssert(prev == null, "putIsMapKey", "prev == null for IS_MAP_KEY false", new Object[0]);
        }
    }

    private static interface NodeFactory<T extends ProjectionTreeNode> {
        public T makeNode();
    }

    protected static enum Keys {
        PROJECTION_EXPR("_PRJ"),
        UPDATE_EXPR("_UPD"),
        CONDITION_EXPR("_CND"),
        IS_MAP("_m"),
        ELEMENT_MAP("_e"),
        DOC_PATH("_p"),
        LITERAL("_l"),
        OPERATOR("_o"),
        OPERANDS("_r"),
        NUM_OPERANDS("_n"),
        UPDATE_ACTION("_a"),
        UPDATE_VALUE("_v");

        private final String name;

        public String getName() {
            return this.name;
        }

        private Keys(String name) {
            this.name = name;
        }
    }
}

