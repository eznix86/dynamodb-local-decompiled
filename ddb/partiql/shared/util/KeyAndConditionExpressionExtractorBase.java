/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.util;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import ddb.partiql.shared.dbenv.DataAccessModelFactory;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.exceptions.InvalidQueryException;
import ddb.partiql.shared.model.ExtractedKeyAndConditionExprTree;
import ddb.partiql.shared.util.ExprTreeNodeUtils;
import ddb.partiql.shared.util.OperatorMappingsBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class KeyAndConditionExpressionExtractorBase<N, V extends DocumentNode, C, CO, S> {
    private final PartiQLDbEnv dbEnv;
    private final DataAccessModelFactory dataAccessModelFactory;
    private final OperatorMappingsBase<CO> operatorMappings;
    private final ExprTreeNodeUtils exprTreeNodeUtils;
    private static final String MULTIPLE_CONDITIONS_ON_RANGE_KEY = "Found multiple conditions on range key; key: %s";

    public KeyAndConditionExpressionExtractorBase(PartiQLDbEnv dbEnv, DataAccessModelFactory dataAccessModelFactory, OperatorMappingsBase<CO> operatorMappings) {
        this.dbEnv = dbEnv;
        this.dataAccessModelFactory = dataAccessModelFactory;
        this.operatorMappings = operatorMappings;
        this.exprTreeNodeUtils = new ExprTreeNodeUtils(dbEnv, dataAccessModelFactory);
    }

    protected abstract List<N> getKeyAttributeNames(S var1);

    protected abstract C makeCondition(Operator var1, List<V> var2);

    protected abstract C makeCondition(CO var1, List<V> var2);

    public ExtractedKeyAndConditionExprTree<N, C> extractKeyFromExprTreeNode(ExprTreeNode node, S schema, ExpressionType expressionType) {
        this.dbEnv.dbPqlAssert(expressionType != null, "KeyAndConditionExpressionExtractor", "Expression type cannot be null");
        HashMap extractedKeyAndConditions = new HashMap();
        List<N> keyAttributeNames = this.getKeyAttributeNames(schema);
        ExprTreeNode conditionExpressionTree = this.extractKeyFromExprTreeNode(node, keyAttributeNames, extractedKeyAndConditions, expressionType);
        if (expressionType == ExpressionType.DATA_MANIPULATION) {
            if (!extractedKeyAndConditions.keySet().containsAll(keyAttributeNames)) {
                throw this.dbEnv.createValidationError("Where clause does not contain a mandatory equality on all key attributes");
            }
        } else if (expressionType == ExpressionType.SELECT) {
            if (!extractedKeyAndConditions.containsKey(keyAttributeNames.get(0))) {
                throw new InvalidQueryException(String.format("Only single equality condition should be present on hash key %s", keyAttributeNames.get(0)));
            }
        } else if (expressionType == ExpressionType.CONDITION_CHECK) {
            if (!extractedKeyAndConditions.keySet().containsAll(keyAttributeNames)) {
                throw this.dbEnv.createValidationError("EXISTS() must contain a single item read with additional condition");
            }
        } else {
            this.dbEnv.dbPqlAssert(false, "extractKeyFromExprTreeNode", "unknown expression type", "expressionType", (Object)expressionType);
        }
        return new ExtractedKeyAndConditionExprTree(extractedKeyAndConditions, conditionExpressionTree);
    }

    public ExprTreeNode extractKeyFromExprTreeNode(ExprTreeNode node, List<N> keyAttributeNames, Map<N, C> extractedKeyAndConditions, ExpressionType expressionType) {
        this.dbEnv.dbPqlAssert(node != null, "KeyAndConditionExpressionExtractor", "ExprTreeNode should not contain null nodes");
        if (KeyAndConditionExpressionExtractorBase.isTerminalOpNode(node)) {
            if (this.checkAndExtractKeyCondition(node, keyAttributeNames, extractedKeyAndConditions, expressionType)) {
                return null;
            }
            return node;
        }
        this.dbEnv.dbPqlAssert(node instanceof ExprTreeOpNode, "KeyAndConditionExpressionExtractor", "Missing operator between operands");
        Operator dynamoOperator = ((ExprTreeOpNode)node).getOperator();
        if (dynamoOperator == Operator.NOT) {
            return this.handleNotOperator(node, keyAttributeNames, expressionType);
        }
        if (dynamoOperator == Operator.OR || dynamoOperator == Operator.IN || dynamoOperator == Operator.BETWEEN) {
            return node;
        }
        this.dbEnv.dbPqlAssert(node.getChildren().size() == 2, "KeyAndConditionExpressionExtractor", "Operator should have two operands");
        ExprTreeNode leftChild = this.extractKeyFromExprTreeNode(node.getChildren().get(0), keyAttributeNames, extractedKeyAndConditions, expressionType);
        ExprTreeNode rightChild = this.extractKeyFromExprTreeNode(node.getChildren().get(1), keyAttributeNames, extractedKeyAndConditions, expressionType);
        if (leftChild != null && rightChild != null) {
            node.getChildren().set(0, leftChild);
            node.getChildren().set(1, rightChild);
            return node;
        }
        return leftChild != null ? leftChild : rightChild;
    }

    private ExprTreeNode handleNotOperator(ExprTreeNode node, List<N> keyAttributeNames, ExpressionType expressionType) {
        switch (expressionType) {
            case SELECT: {
                N attributeName;
                ExprTreePathNode pathNode = ExprTreeNodeUtils.getPathNodeChild(node.getChildren().get(0));
                if (pathNode == null || (attributeName = this.exprTreeNodeUtils.getKeyAttributeName(pathNode, keyAttributeNames)) == null) break;
                throw new InvalidQueryException("Key attributes associated with a NOT operator");
            }
        }
        return node;
    }

    public static boolean isTerminalOpNode(ExprTreeNode node) {
        if (node.getChildren() != null) {
            for (ExprTreeNode childNode : node.getChildren()) {
                if (!(childNode instanceof ExprTreeOpNode)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean checkAndExtractKeyCondition(ExprTreeNode node, List<N> keyAttributeNames, Map<N, C> extractedKeyConditions, ExpressionType expressionType) {
        if (!(node instanceof ExprTreeOpNode)) {
            return false;
        }
        ExprTreePathNode pathNode = ExprTreeNodeUtils.getPathNodeChild(node);
        List<ExprTreeValueNode> valueNodes = ExprTreeNodeUtils.getValueNodeChildren(node);
        Operator operator = ((ExprTreeOpNode)node).getOperator();
        if (pathNode == null) {
            return false;
        }
        switch (expressionType) {
            case DATA_MANIPULATION: {
                return this.checkAndExtractKeyConditionForDML(pathNode, valueNodes, operator, keyAttributeNames, extractedKeyConditions);
            }
            case SELECT: {
                return this.checkAndExtractKeyConditionForSelect(pathNode, valueNodes, operator, keyAttributeNames, extractedKeyConditions);
            }
            case CONDITION_CHECK: {
                return this.checkAndExtractKeyConditionForConditionCheck(pathNode, valueNodes, operator, keyAttributeNames, extractedKeyConditions);
            }
        }
        return false;
    }

    private boolean checkAndExtractKeyConditionForSelect(ExprTreePathNode pathNode, List<ExprTreeValueNode> valueNodes, Operator operator, List<N> keyAttributeNames, Map<N, C> extractedKeyAndConditions) {
        N attributeName = this.exprTreeNodeUtils.getKeyAttributeName(pathNode, keyAttributeNames);
        if (attributeName == null) {
            return false;
        }
        if (attributeName.equals(keyAttributeNames.get(0))) {
            if (operator == Operator.EQ) {
                if (valueNodes.isEmpty()) {
                    return false;
                }
                DocumentNode attributeValue = valueNodes.get(0).getValue();
                C condition = this.makeCondition((CO)((Object)Operator.EQ), (List<V>)Collections.singletonList(attributeValue));
                if (this.checkMultipleConditionsOnSameKey(attributeName, condition, extractedKeyAndConditions)) {
                    throw new InvalidQueryException(String.format("Only single equality condition should be present on hash key %s", attributeName));
                }
                extractedKeyAndConditions.put(attributeName, condition);
                return true;
            }
            throw new InvalidQueryException(String.format("Only single equality condition should be present on hash key %s", attributeName));
        }
        if (keyAttributeNames.size() == 2 && attributeName.equals(keyAttributeNames.get(1))) {
            CO comparisonOperator = this.operatorMappings.getComparisonOperator(operator);
            if (comparisonOperator == null) {
                throw new InvalidQueryException(String.format("Condition on range key: %s, operator: %s is not valid condition", new Object[]{attributeName, operator}));
            }
            if (!this.validateValueNodeNumOnOperator(operator, valueNodes)) {
                return false;
            }
            ArrayList<DocumentNode> attributeValues = new ArrayList<DocumentNode>();
            for (ExprTreeValueNode valueNode : valueNodes) {
                attributeValues.add(valueNode.getValue());
            }
            C condition = this.makeCondition(comparisonOperator, attributeValues);
            if (this.checkMultipleConditionsOnSameKey(attributeName, condition, extractedKeyAndConditions)) {
                throw new InvalidQueryException(String.format(MULTIPLE_CONDITIONS_ON_RANGE_KEY, attributeName));
            }
            extractedKeyAndConditions.put(attributeName, condition);
            return true;
        }
        return false;
    }

    private boolean checkAndExtractKeyConditionForDML(ExprTreePathNode pathNode, List<ExprTreeValueNode> valueNodes, Operator operator, List<N> keyAttributeNames, Map<N, C> extractedKeyAndConditions) {
        if (valueNodes.isEmpty()) {
            return false;
        }
        N attributeName = this.exprTreeNodeUtils.getKeyAttributeName(pathNode, keyAttributeNames);
        if (Operator.EQ != operator || attributeName == null) {
            return false;
        }
        DocumentNode attributeValue = valueNodes.get(0).getValue();
        C condition = this.makeCondition((CO)((Object)Operator.EQ), (List<V>)Collections.singletonList(attributeValue));
        if (this.checkMultipleConditionsOnSameKey(attributeName, condition, extractedKeyAndConditions)) {
            throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Multiple conditions on same key %s. Only single item Update/Insert/Delete are supported").build(attributeName));
        }
        extractedKeyAndConditions.put(attributeName, this.makeCondition((CO)((Object)Operator.EQ), (List<V>)Collections.singletonList(attributeValue)));
        return true;
    }

    private boolean checkAndExtractKeyConditionForConditionCheck(ExprTreePathNode pathNode, List<ExprTreeValueNode> valueNodes, Operator operator, List<N> keyAttributeNames, Map<N, C> extractedKeyAndConditions) {
        N attributeName = this.exprTreeNodeUtils.getKeyAttributeName(pathNode, keyAttributeNames);
        if (attributeName == null || Operator.EQ != operator || valueNodes.isEmpty()) {
            return false;
        }
        DocumentNode attributeValue = valueNodes.get(0).getValue();
        if (extractedKeyAndConditions.containsKey(attributeName)) {
            return false;
        }
        extractedKeyAndConditions.put(attributeName, this.makeCondition((CO)((Object)Operator.EQ), (List<V>)Collections.singletonList(attributeValue)));
        return true;
    }

    private boolean checkMultipleConditionsOnSameKey(N attributeName, C condition, Map<N, C> extractedKeyAndConditions) {
        return extractedKeyAndConditions.containsKey(attributeName) && !extractedKeyAndConditions.get(attributeName).equals(condition);
    }

    private boolean validateValueNodeNumOnOperator(Operator operator, List<ExprTreeValueNode> valueNodes) {
        if (valueNodes.isEmpty()) {
            return false;
        }
        return operator != Operator.BETWEEN || valueNodes.size() == 2;
    }

    public static enum ExpressionType {
        DATA_MANIPULATION,
        SELECT,
        CONDITION_CHECK;

    }
}

