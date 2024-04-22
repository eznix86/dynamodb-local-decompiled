/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.dbenv.DbConfig;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbExecutionError;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DocumentNodeHelper {
    public static final int DOC_ROOT_LEVEL = 0;

    public static boolean isMapOrList(DocumentNode n) {
        DocumentNodeType typ = n.getNodeType();
        return typ == DocumentNodeType.MAP || typ == DocumentNodeType.LIST || typ == DocumentNodeType.DICT;
    }

    public static DocumentNode getChild(DocumentNode node, DocPathElement key) {
        if (key.isCompatibleWithDocumentNode(node)) {
            return node.getChild(key);
        }
        return null;
    }

    public static void resetDocLevels(DocumentNode root, DbEnv dbEnv) {
        DocumentNodeHelper.setDocumentLevel(root, 0, dbEnv);
    }

    private static void setDocumentLevel(DocumentNode docTreeNode, int level, DbEnv dbEnv) {
        dbEnv.dbAssert(docTreeNode != null, "ExpressionExecutor.setDocumentLevel", "doc node null", new Object[0]);
        dbEnv.dbAssert(level >= 0, "ExpressionExecutor.setDocumentLevel", "bad level", new Object[0]);
        if (!DocumentNodeHelper.isMapOrList(docTreeNode)) {
            return;
        }
        int maxLevel = dbEnv.getConfigInt(DbConfig.MAX_DOC_PATH_DEPTH);
        if (level >= maxLevel) {
            dbEnv.throwExecutionError(DbExecutionError.DOCUMENT_TOO_DEEP, "nesting level", level);
        }
        docTreeNode.setLevel(level);
        for (DocPathElement e : docTreeNode.getChildren()) {
            DocumentNodeHelper.setDocumentLevel(docTreeNode.getChild(e), level + 1, dbEnv);
        }
    }

    public static String createTypeSymbolsString(Collection<DocumentNodeType> types) {
        if (types == null || types.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (DocumentNodeType nodeType : types) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(nodeType.getAbbrName());
        }
        Object str = sb.toString();
        if (types.size() > 1) {
            str = "{" + (String)str + "}";
        }
        return str;
    }

    public static Set<String> createTypeSymbolsSet(Collection<DocumentNodeType> types) {
        if (types == null || types.isEmpty()) {
            return new HashSet<String>();
        }
        HashSet<String> typeSymbols = new HashSet<String>();
        for (DocumentNodeType nodeType : types) {
            typeSymbols.add(nodeType.getAbbrName());
        }
        return typeSymbols;
    }
}

