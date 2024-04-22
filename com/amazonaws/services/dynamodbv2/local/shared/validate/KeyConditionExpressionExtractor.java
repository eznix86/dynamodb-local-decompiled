/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 */
package com.amazonaws.services.dynamodbv2.local.shared.validate;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.validate.ErrorFactory;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeyConditionExpressionExtractor {
    final DbEnv dbEnv;
    final ErrorFactory errorFactory;

    public KeyConditionExpressionExtractor(DbEnv dbEnv, ErrorFactory errorFactory) {
        this.dbEnv = dbEnv;
        this.errorFactory = errorFactory;
    }

    public Map<String, Condition> extractKeyConditions(Expression keyConditionExpression) {
        this.dbEnv.dbAssert(keyConditionExpression != null, "KeyConditionExpressionExtractor", "KeyConditionExpression must not be null", new Object[0]);
        ExprTreeNode root = keyConditionExpression.getExprTree();
        HashMap<String, Condition> extractedConditions = new HashMap<String, Condition>();
        this.extractConditionFromExprTreeNode(root, extractedConditions);
        return extractedConditions;
    }

    private void extractConditionFromExprTreeNode(ExprTreeNode currentNode, Map<String, Condition> extractedConditions) {
        this.dbEnv.dbAssert(currentNode != null, "KeyConditionExpressionExtractor", "Expression tree should not contain null nodes", new Object[0]);
        if (!(currentNode instanceof ExprTreeOpNode)) {
            this.errorFactory.KEY_CONDITION_EXPRESSION_INVALID_CONDITION.throwAsException("No operator specified for condition");
        } else {
            ExprTreeOpNode currentOpNode = (ExprTreeOpNode)currentNode;
            Operator currentOperator = currentOpNode.getOperator();
            if (Operator.AND == currentOperator) {
                for (ExprTreeNode childExprTreeNode : currentOpNode.getChildren()) {
                    this.extractConditionFromExprTreeNode(childExprTreeNode, extractedConditions);
                }
            } else {
                this.getConditionFromOperatorNode(currentOpNode, extractedConditions, currentOperator);
            }
        }
    }

    private void getConditionFromOperatorNode(ExprTreeOpNode currentOpNode, Map<String, Condition> extractedConditions, Operator nodeOperator) {
        String keyAttrName = null;
        ArrayList<AttributeValue> keyAttrValues = new ArrayList<AttributeValue>();
        ComparisonOperator comparisonOperator = this.getComparisonOperatorForOperator(nodeOperator);
        if (comparisonOperator == null) {
            this.errorFactory.KEY_CONDITION_EXPRESSION_INVALID_COMPARISON_OPERATOR.throwAsException(nodeOperator.getOperatorName());
        }
        for (ExprTreeNode child2 : currentOpNode.getChildren()) {
            if (child2 instanceof ExprTreePathNode) {
                DocPath current;
                if (keyAttrName != null) {
                    this.errorFactory.KEY_CONDITION_EXPRESSION_INVALID_CONDITION.throwAsException("Multiple attribute names used in one condition");
                }
                if ((current = ((ExprTreePathNode)child2).getDocPath()).getElements().size() > 1) {
                    this.errorFactory.KEY_CONDITION_EXPRESSION_NESTED_PATH_FOUND.throwAsException();
                }
                DocPathElement element = current.getElements().get(0);
                this.dbEnv.dbAssert(element.isMap(), "KeyConditionExpressionExtractor", "First element of ExprTreePathNode should always be a map. Element type: " + element.getClass() + ", Element: " + element, new Object[0]);
                keyAttrName = element.getFieldName();
                continue;
            }
            if (child2 instanceof ExprTreeValueNode) {
                if (keyAttrName == null) {
                    if (comparisonOperator == ComparisonOperator.BETWEEN || comparisonOperator == ComparisonOperator.BEGINS_WITH) {
                        this.errorFactory.KEY_CONDITION_EXPRESSION_INVALID_CONDITION.throwAsException(nodeOperator.getOperatorName() + " operator must have the key attribute as its first operand");
                    } else {
                        comparisonOperator = this.getInverseComparisonOperator(comparisonOperator);
                    }
                }
                keyAttrValues.add((AttributeValue)((ExprTreeValueNode)child2).getValue());
                continue;
            }
            if (child2 instanceof ExprTreeOpNode) {
                this.errorFactory.KEY_CONDITION_EXPRESSION_NESTED_OPERATOR.throwAsException();
                continue;
            }
            this.errorFactory.KEY_CONDITION_EXPRESSION_INVALID_CONDITION.throwAsException("Unsupported expression tree node type found");
        }
        if (keyAttrName != null) {
            if (extractedConditions.get(keyAttrName) != null) {
                this.errorFactory.KEY_CONDITION_EXPRESSION_DUPLICATE_KEY.throwAsException();
            }
            LocalDBValidatorUtils.validateArgumentsForComparisonOperator(comparisonOperator, keyAttrValues, this.errorFactory);
            Condition condition = new Condition();
            condition.setComparisonOperator(comparisonOperator);
            condition.setAttributeValueList(keyAttrValues);
            extractedConditions.put(keyAttrName, condition);
        } else {
            this.errorFactory.KEY_CONDITION_EXPRESSION_INVALID_CONDITION.throwAsException("No key attribute specified");
        }
    }

    private ComparisonOperator getComparisonOperatorForOperator(Operator currentOperator) {
        switch (currentOperator) {
            case EQ: {
                return ComparisonOperator.EQ;
            }
            case LT: {
                return ComparisonOperator.LT;
            }
            case GT: {
                return ComparisonOperator.GT;
            }
            case LE: {
                return ComparisonOperator.LE;
            }
            case GE: {
                return ComparisonOperator.GE;
            }
            case begins_with: {
                return ComparisonOperator.BEGINS_WITH;
            }
            case BETWEEN: {
                return ComparisonOperator.BETWEEN;
            }
        }
        return null;
    }

    private ComparisonOperator getInverseComparisonOperator(ComparisonOperator operator) {
        switch (operator) {
            case LT: {
                return ComparisonOperator.GT;
            }
            case GT: {
                return ComparisonOperator.LT;
            }
            case LE: {
                return ComparisonOperator.GE;
            }
            case GE: {
                return ComparisonOperator.LE;
            }
        }
        return operator;
    }
}

