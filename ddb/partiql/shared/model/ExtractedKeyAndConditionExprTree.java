/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.model;

import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import java.util.Collections;
import java.util.Map;

public class ExtractedKeyAndConditionExprTree<N, C> {
    private final Map<N, C> extractedKeyConditions;
    private final ExprTreeNode conditionExpressionTreeNode;

    public ExtractedKeyAndConditionExprTree(Map<N, C> extractedKey, ExprTreeNode conditionExpressionTreeNode) {
        this.extractedKeyConditions = Collections.unmodifiableMap(extractedKey);
        this.conditionExpressionTreeNode = conditionExpressionTreeNode;
    }

    public Map<N, C> getExtractedKeyConditions() {
        return this.extractedKeyConditions;
    }

    public ExprTreeNode getConditionExpressionTreeNode() {
        return this.conditionExpressionTreeNode;
    }
}

