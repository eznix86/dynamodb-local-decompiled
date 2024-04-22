/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeHelper;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import com.amazonaws.services.dynamodbv2.dbenv.DbConfig;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import java.util.List;

public class OperatorValidator {
    protected final DbEnv dbEnv;
    private boolean areIonNumericTypesAllowed;

    public OperatorValidator(DbEnv dbEnv) {
        this(dbEnv, false);
    }

    public OperatorValidator(DbEnv dbEnv, boolean areIonNumericTypesAllowed) {
        this.dbEnv = dbEnv;
        this.areIonNumericTypesAllowed = areIonNumericTypesAllowed;
    }

    public DbEnv getDbEnv() {
        return this.dbEnv;
    }

    public void validateAttributeExistsAndNotExists(Operator operator, List<ExprTreeNode> operands) {
        this.dbEnv.dbAssert(operator == Operator.attribute_exists || operator == Operator.attribute_not_exists, "validateAttributeExistsAndNotExists", "operator must be attribute_exists or attribute_not_exists", new Object[]{"operator", operator});
        ExprTreeNode operand = operands.get(0);
        if (!(operand instanceof ExprTreePathNode)) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_EXPECTED_PATH_TYPE, "operator or function", operator.getOperatorName());
        }
    }

    public void validateAttributeType(Operator operator, List<ExprTreeNode> operands) {
        TypeSet allowedTypes;
        String name;
        DocumentNode documentNode;
        this.dbEnv.dbAssert(operator == Operator.attribute_type, "validateAttributeType", "operator must be attribute_type", new Object[]{"operator", operator});
        ExprTreeNode treeNode = operands.get(1);
        if (!(treeNode instanceof ExprTreeValueNode)) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_TYPE_CHECK_FAIL, "operator or function", operator.getOperatorName(), "operand type", DocumentNodeHelper.createTypeSymbolsString(treeNode.getPossibleReturnTypes()));
        }
        if ((documentNode = ((ExprTreeValueNode)treeNode).getValue()).getNodeType() != DocumentNodeType.STRING) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_TYPE_CHECK_FAIL, "operator or function", operator.getOperatorName(), "operand type", DocumentNodeHelper.createTypeSymbolsString(treeNode.getPossibleReturnTypes()));
        }
        this.dbEnv.dbAssert((name = documentNode.getSValue()) != null, "validateAttributeType", "expected attribute type name should not be null", new Object[]{"operator", operator});
        TypeSet typeSet = allowedTypes = this.areIonNumericTypesAllowed ? TypeSet.ALL_TYPES_NO_HELENUS : TypeSet.ALL_TYPES_NO_HELENUS_NO_ION;
        if (!allowedTypes.getSymbolSet().contains(name)) {
            this.dbEnv.throwValidationError(DbValidationError.INVALID_ATTRIBUTE_TYPE_NAME, "type", name, "valid types", allowedTypes.getSymbolStr());
        }
    }

    public void validateIfNotExists(Operator operator, List<ExprTreeNode> operands) {
        this.dbEnv.dbAssert(operator == Operator.if_not_exists, "validateIfNotExists", "operator must be if_not_exists", new Object[]{"operator", operator});
        ExprTreeNode operand = operands.get(0);
        if (!(operand instanceof ExprTreePathNode)) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_EXPECTED_PATH_TYPE, "operator or function", operator.getOperatorName());
        }
    }

    public void validateOperator(ExprTreeOpNode treeNode) {
        ExprTreeNode firstOperand;
        int numOperands;
        Operator op = treeNode.getOperator();
        if (!op.numOperandsOk(numOperands = treeNode.getChildren().size())) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_COUNT_INCORRECT, "operator or function", op.getOperatorName(), "number of operands", numOperands);
        }
        for (ExprTreeNode child2 : treeNode.getChildren()) {
            if (child2.isReturnTypeSupported(op.getOperandType())) continue;
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_TYPE_CHECK_FAIL, "operator or function", op.getOperatorName(), "operand type", DocumentNodeHelper.createTypeSymbolsString(child2.getPossibleReturnTypes()));
        }
        if (op.allowNullOperands() && numOperands > 1 && (firstOperand = treeNode.getChildren().get(0)) instanceof ExprTreePathNode) {
            for (int i = 1; i < treeNode.getChildren().size(); ++i) {
                ExprTreeNode laterOperand = treeNode.getChildren().get(i);
                if (!(laterOperand instanceof ExprTreePathNode) || !this.exprTreePathNodeMatch((ExprTreePathNode)firstOperand, (ExprTreePathNode)laterOperand)) continue;
                this.dbEnv.throwValidationError(DbValidationError.REPEAT_DOC_PATH_NOT_ALLOWED, "operator", op.getOperatorName(), "first operand", ((ExprTreePathNode)firstOperand).getDocPath());
            }
        }
        treeNode.getOperator().validate(treeNode.getChildren(), this);
    }

    public void validateBetween(List<ExprTreeNode> children) {
        this.dbEnv.dbAssert(children != null, "validateBetween", "BETWEEN operator children list can not be null", new Object[0]);
        this.dbEnv.dbAssert(children.size() == 3, "validateBetween", "BETWEEN operator must have 3 children", "children", children);
        ExprTreeNode lowerBoundOperand = children.get(1);
        ExprTreeNode upperBoundOperand = children.get(2);
        if (lowerBoundOperand instanceof ExprTreeValueNode && upperBoundOperand instanceof ExprTreeValueNode) {
            ExprTreeValueNode lowerBound = (ExprTreeValueNode)lowerBoundOperand;
            ExprTreeValueNode upperBound = (ExprTreeValueNode)upperBoundOperand;
            if (lowerBound.getValue().getNodeType() != upperBound.getValue().getNodeType()) {
                this.dbEnv.throwValidationError(DbValidationError.BETWEEN_DIFFERENT_TYPE_OPERANDS, "lower bound operand", lowerBound.getValue(), "upper bound operand", upperBound.getValue());
            }
            if (lowerBound.getValue().compare(upperBound.getValue()) > 0) {
                this.dbEnv.throwValidationError(DbValidationError.BETWEEN_LBOUND_BIGGER_THAN_UBOUND, "lower bound operand", lowerBound.getValue(), "upper bound operand", upperBound.getValue());
            }
        }
    }

    private boolean exprTreePathNodeMatch(ExprTreePathNode firstOperand, ExprTreePathNode laterOperand) {
        return firstOperand.getDocPath().equals(laterOperand.getDocPath());
    }

    public void validateIn(List<ExprTreeNode> children) {
        this.dbEnv.dbAssert(children != null, "validateIn", "IN operator must have children", new Object[0]);
        this.dbEnv.dbAssert(children.size() >= 2, "validateIn", "IN operator must have at least 2 children", "children", children);
        int maxOperand = this.dbEnv.getConfigInt(DbConfig.MAX_NUM_OPERANDS_FOR_IN);
        if (children.size() > maxOperand + 1) {
            this.dbEnv.throwValidationError(DbValidationError.TOO_MANY_OPERANDS_FOR_IN, "number of operands", children.size() - 1);
        }
    }

    void validateContainsKey(Operator operator, List<ExprTreeNode> children) {
        ExprTreeNode treeNode2;
        ExprTreeNode treeNode1;
        String FUNC_NAME = "validateContainsKey";
        this.dbEnv.dbAssert(operator == Operator.contains_key, "validateContainsKey", "operator must be contains_key", new Object[]{"operator", operator});
        this.dbEnv.dbAssert(children != null, "validateContainsKey", "contains_key operator children list cannot be null", new Object[0]);
        if (children.size() != 2) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_COUNT_INCORRECT, "number of operands", children.size());
        }
        if (!((treeNode1 = children.get(0)) instanceof ExprTreeValueNode) && !(treeNode1 instanceof ExprTreePathNode)) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_TYPE_CHECK_FAIL, "function", operator.getOperatorName(), "operand type", DocumentNodeHelper.createTypeSymbolsString(treeNode1.getPossibleReturnTypes()));
        }
        if (!((treeNode2 = children.get(1)) instanceof ExprTreeValueNode)) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_TYPE_CHECK_FAIL, "function", operator.getOperatorName(), "operand type", DocumentNodeHelper.createTypeSymbolsString(treeNode2.getPossibleReturnTypes()));
        }
    }

    void validateContainsValue(Operator operator, List<ExprTreeNode> children) {
        ExprTreeNode treeNode2;
        ExprTreeNode treeNode1;
        String FUNC_NAME = "validateContainsValue";
        this.dbEnv.dbAssert(operator == Operator.contains_value, "validateContainsValue", "operator must be contains_value", new Object[]{"operator", operator});
        this.dbEnv.dbAssert(children != null, "validateContainsValue", "contains_value operator children list cannot be null", new Object[0]);
        if (children.size() != 2) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_COUNT_INCORRECT, "number of operands", children.size());
        }
        if (!((treeNode1 = children.get(0)) instanceof ExprTreeValueNode) && !(treeNode1 instanceof ExprTreePathNode)) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_TYPE_CHECK_FAIL, "function", operator.getOperatorName(), "operand type", DocumentNodeHelper.createTypeSymbolsString(treeNode1.getPossibleReturnTypes()));
        }
        if (!((treeNode2 = children.get(1)) instanceof ExprTreeValueNode)) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_TYPE_CHECK_FAIL, "function", operator.getOperatorName(), "operand type", DocumentNodeHelper.createTypeSymbolsString(treeNode2.getPossibleReturnTypes()));
        }
    }

    public void validateConvert(Operator operator, List<ExprTreeNode> children) {
        String name;
        DocumentNode documentNode;
        ExprTreeNode treeNode;
        String FUNC_NAME = "validateConvert";
        this.dbEnv.dbAssert(operator == Operator.convert, "validateConvert", "operator must be convert", new Object[]{"operator", operator});
        this.dbEnv.dbAssert(children != null, "validateConvert", "convert operator children list cannot be null", new Object[0]);
        if (children.size() != 2) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_COUNT_INCORRECT, "number of operands", children.size());
        }
        if (!((treeNode = children.get(1)) instanceof ExprTreeValueNode)) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_TYPE_CHECK_FAIL, "function", operator.getOperatorName(), "operand type", DocumentNodeHelper.createTypeSymbolsString(treeNode.getPossibleReturnTypes()));
        }
        if ((documentNode = ((ExprTreeValueNode)treeNode).getValue()) == null || !DocumentNodeType.STRING.equals((Object)documentNode.getNodeType())) {
            this.dbEnv.throwValidationError(DbValidationError.OPERAND_TYPE_CHECK_FAIL, "operator or function", operator.getOperatorName(), "operand type", DocumentNodeHelper.createTypeSymbolsString(treeNode.getPossibleReturnTypes()));
        }
        this.dbEnv.dbAssert((name = documentNode.getSValue()) != null, "validateAttributeType", "expected attribute type name should not be null", new Object[]{"operator", operator});
        if (!TypeSet.ALL_TYPES.getSymbolSet().contains(name)) {
            this.dbEnv.throwValidationError(DbValidationError.INVALID_ATTRIBUTE_TYPE_NAME, "type", name, "valid types", TypeSet.ALL_TYPES.getSymbolStr());
        }
    }

    public void validateNumericMath(Operator operator, List<ExprTreeNode> children) {
        String FUNC_NAME = "validateNumericMath";
        this.dbEnv.dbAssert(Operator.ADDITION.equals((Object)operator) || Operator.SUBTRACTION.equals((Object)Operator.SUBTRACTION), "validateNumericMath", "operator must be ADDITION or SUBTRACTION", new Object[]{"operator", operator});
        this.dbEnv.dbAssert(children != null, "validateNumericMath", "arithmetic operator children list cannot be null", new Object[0]);
        this.dbEnv.dbAssert(children.size() == 2, "validateNumericMath", "arithmetic operator must have 2 children", "children", children);
    }
}

