/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.util;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathListElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentCollectionType;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeHelper;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionTreeNode;
import com.amazonaws.services.dynamodbv2.dbenv.DbInternalError;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.LocalDocumentFactory;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SelectProjectionUnnester {
    private final LocalPartiQLDbEnv localPartiQLDbEnv;

    public SelectProjectionUnnester(LocalPartiQLDbEnv localPartiQLDbEnv) {
        this.localPartiQLDbEnv = localPartiQLDbEnv;
    }

    public Map<String, AttributeValue> unnestProjection(Map<String, AttributeValue> item, ProjectionExpression projectionExpression) {
        if (item == null || projectionExpression == null) {
            return item;
        }
        AttributeValue unnestedDoc = (AttributeValue)this.unnestUserDoc(new AttributeValue().withM(item), projectionExpression.getTreeRoot());
        if (unnestedDoc == null) {
            return new HashMap<String, AttributeValue>();
        }
        return unnestedDoc.getM();
    }

    private DocumentNode unnestUserDoc(DocumentNode docTreeNode, ProjectionTreeNode projTreeNode) {
        Map<DocPathElement, DocumentNode> pathToNodeMap = this.unnestToNodeMap(docTreeNode, projTreeNode, "");
        if (pathToNodeMap == null || pathToNodeMap.size() == 0) {
            return null;
        }
        DocumentCollectionType collectionType = pathToNodeMap.entrySet().iterator().next().getKey().getCollectionType();
        try {
            return new LocalDocumentFactory().makeCollection(collectionType, pathToNodeMap);
        } catch (IllegalArgumentException e) {
            throw this.localPartiQLDbEnv.createInternalServerError(DbInternalError.UNEXPECTED_PATH_ELEMENT_TYPE.getMessage());
        }
    }

    private Map<DocPathElement, DocumentNode> unnestToNodeMap(DocumentNode docTreeNode, ProjectionTreeNode projTreeNode, String parentDocPathString) {
        if (!DocumentNodeHelper.isMapOrList(docTreeNode)) {
            return null;
        }
        List sortedListElements = projTreeNode.getChildMap().keySet().stream().filter(element -> element instanceof DocPathListElement).map(element -> (DocPathListElement)element).sorted().collect(Collectors.toList());
        List docPathMapElements = projTreeNode.getChildMap().keySet().stream().filter(element -> element instanceof DocPathMapElement).map(element -> (DocPathMapElement)element).collect(Collectors.toList());
        LinkedHashMap<DocPathElement, DocumentNode> nodeMap = new LinkedHashMap<DocPathElement, DocumentNode>();
        for (int i = 0; i < sortedListElements.size(); ++i) {
            DocPathListElement childKey = new DocPathListElement(i);
            DocPathListElement childName = (DocPathListElement)sortedListElements.get(i);
            this.unnestToNodeMap(docTreeNode, childKey, childName, projTreeNode, parentDocPathString, nodeMap);
        }
        for (DocPathMapElement docPathMapElement : docPathMapElements) {
            this.unnestToNodeMap(docTreeNode, docPathMapElement, docPathMapElement, projTreeNode, parentDocPathString, nodeMap);
        }
        return nodeMap;
    }

    private void unnestToNodeMap(DocumentNode docTreeNode, DocPathElement childKey, DocPathElement childName, ProjectionTreeNode projTreeNode, String parentDocPathString, Map<DocPathElement, DocumentNode> nodeMap) {
        DocumentNode childNode = DocumentNodeHelper.getChild(docTreeNode, childKey);
        if (childNode != null) {
            String currentDocPathString = this.concatenateDocPathElement(parentDocPathString, childName);
            ProjectionTreeNode childProjectionNode = projTreeNode.getChildMap().get(childKey);
            if (childProjectionNode == null) {
                nodeMap.put(new DocPathMapElement(currentDocPathString), childNode);
            } else {
                Map<DocPathElement, DocumentNode> childMap = this.unnestToNodeMap(childNode, childProjectionNode, currentDocPathString);
                if (childMap != null) {
                    nodeMap.putAll(childMap);
                }
            }
        }
    }

    private String concatenateDocPathElement(String currentDocPath, DocPathElement element) {
        if (element instanceof DocPathMapElement) {
            return element.toString();
        }
        if (element instanceof DocPathListElement) {
            return currentDocPath + element;
        }
        this.localPartiQLDbEnv.dbAssert(false, "projectAndUnnestToNodeMap", "Dict type in unnesting projection", new Object[0]);
        return null;
    }
}

