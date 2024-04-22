/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.OperatorExecutor;
import com.amazonaws.services.dynamodbv2.datamodel.OperatorValidator;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import com.amazonaws.services.dynamodbv2.dbenv.DbConfig;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public enum Operator {
    ADDITION(TypeSet.NUMERIC, "+", 2, false, TypeSet.NUMERIC, false, TypeMismatchBehavior.THROW_ERROR){

        @Override
        public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
            operatorValidator.validateNumericMath(this, children);
        }

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.addition(operands.get(0), operands.get(1));
        }
    }
    ,
    SUBTRACTION(TypeSet.NUMERIC, "-", 2, false, TypeSet.NUMERIC, false, TypeMismatchBehavior.THROW_ERROR){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.subtraction(operands.get(0), operands.get(1));
        }
    }
    ,
    set_union(TypeSet.SET, "set_union", 2, false, TypeSet.SET, false, TypeMismatchBehavior.THROW_ERROR){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.setUnion(operands.get(0), operands.get(1));
        }
    }
    ,
    set_diff(TypeSet.SET, "set_diff", 2, false, TypeSet.SET, false, TypeMismatchBehavior.THROW_ERROR){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.setDiff(operands.get(0), operands.get(1), true);
        }
    }
    ,
    list_append(TypeSet.LIST, "list_append", 2, false, TypeSet.LIST, false, TypeMismatchBehavior.THROW_ERROR){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.listAppend(operands.get(0), operands.get(1));
        }
    }
    ,
    list_delete(TypeSet.LIST, "list_delete", 2, false, TypeSet.LIST, false, TypeMismatchBehavior.THROW_ERROR){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.listDelete(operands.get(0), operands.get(1));
        }
    }
    ,
    if_not_exists(TypeSet.ALL_TYPES, "if_not_exists", 2, false, TypeSet.ALL_TYPES){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.ifNotExist(operands.get(0), operands.get(1));
        }

        @Override
        public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
            operatorValidator.validateIfNotExists(this, children);
        }
    }
    ,
    EQ(TypeSet.BOOLEAN, "=", 2, false, TypeSet.ALL_TYPES){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(OperatorExecutor.eval_EQ(operands.get(0), operands.get(1)));
        }
    }
    ,
    NE(TypeSet.BOOLEAN, "<>", 2, false, TypeSet.ALL_TYPES){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(!OperatorExecutor.eval_EQ(operands.get(0), operands.get(1)));
        }
    }
    ,
    LT(TypeSet.BOOLEAN, "<"){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(OperatorExecutor.eval_LT(operands.get(0), operands.get(1)));
        }
    }
    ,
    GT(TypeSet.BOOLEAN, ">"){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(OperatorExecutor.eval_GT(operands.get(0), operands.get(1)));
        }
    }
    ,
    LE(TypeSet.BOOLEAN, "<="){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(OperatorExecutor.eval_LE(operands.get(0), operands.get(1)));
        }
    }
    ,
    GE(TypeSet.BOOLEAN, ">="){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(OperatorExecutor.eval_GE(operands.get(0), operands.get(1)));
        }
    }
    ,
    BETWEEN(TypeSet.BOOLEAN, "BETWEEN", 3, false){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(OperatorExecutor.eval_BETWEEN(operands.get(0), operands.get(1), operands.get(2)));
        }

        @Override
        public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
            operatorValidator.validateBetween(children);
        }
    }
    ,
    IN(TypeSet.BOOLEAN, "IN", 1, true, TypeSet.ALL_TYPES){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(executor.eval_IN(operands));
        }

        @Override
        public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
            operatorValidator.validateIn(children);
        }
    }
    ,
    attribute_exists(TypeSet.BOOLEAN, "attribute_exists", 1, false, TypeSet.ALL_TYPES){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(operands.get(0) != null);
        }

        @Override
        public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
            operatorValidator.validateAttributeExistsAndNotExists(this, children);
        }
    }
    ,
    attribute_not_exists(TypeSet.BOOLEAN, "attribute_not_exists", 1, false, TypeSet.ALL_TYPES){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(operands.get(0) == null);
        }

        @Override
        public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
            operatorValidator.validateAttributeExistsAndNotExists(this, children);
        }
    }
    ,
    attribute_type(TypeSet.BOOLEAN, "attribute_type", 2, false, TypeSet.ALL_TYPES, true, TypeMismatchBehavior.RETURN_FALSE){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(executor.attributeType(operands.get(0), operands.get(1)));
        }

        @Override
        public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
            operatorValidator.validateAttributeType(this, children);
        }
    }
    ,
    contains(TypeSet.BOOLEAN, "contains", 2, false, TypeSet.ALL_TYPES){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(executor.contains(operands.get(0), operands.get(1)));
        }
    }
    ,
    contains_key(TypeSet.BOOLEAN, "contains_key", 2, false, TypeSet.ALL_TYPES){

        @Override
        public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
            operatorValidator.validateContainsKey(this, children);
        }

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(executor.contains_key(operands.get(0), operands.get(1)));
        }
    }
    ,
    contains_value(TypeSet.BOOLEAN, "contains_value", 2, false, TypeSet.ALL_TYPES){

        @Override
        public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
            operatorValidator.validateContainsValue(this, children);
        }

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(executor.contains_value(operands.get(0), operands.get(1)));
        }
    }
    ,
    begins_with(TypeSet.BOOLEAN, "begins_with", 2, false, TypeSet.STRING_BINARY){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(executor.beginsWith(operands.get(0), operands.get(1)));
        }
    }
    ,
    size(TypeSet.NUMBER, "size", 1, false, TypeSet.ITERABLE, true, TypeMismatchBehavior.RETURN_MISSING){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.eval_SIZE(operands.get(0));
        }
    }
    ,
    NOT(TypeSet.BOOLEAN, "NOT", 1, false, TypeSet.BOOLEAN, false, TypeMismatchBehavior.THROW_ERROR){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(executor.eval_NOT(operands.get(0)));
        }
    }
    ,
    AND(TypeSet.BOOLEAN, "AND", 2, false, TypeSet.BOOLEAN, false, TypeMismatchBehavior.THROW_ERROR){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(executor.eval_AND(operands.get(0), operands.get(1)));
        }
    }
    ,
    OR(TypeSet.BOOLEAN, "OR", 2, false, TypeSet.BOOLEAN, false, TypeMismatchBehavior.THROW_ERROR){

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.getDocFactory().makeBoolean(executor.eval_OR(operands.get(0), operands.get(1)));
        }
    }
    ,
    convert(TypeSet.ALL_TYPES, "convert", 2, false, TypeSet.ALL_TYPES, false, TypeMismatchBehavior.THROW_ERROR){

        @Override
        public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
            operatorValidator.validateConvert(this, children);
        }

        @Override
        public DocumentNode evaluate(List<DocumentNode> operands, OperatorExecutor executor) {
            return executor.convert(operands.get(0), operands.get(1));
        }
    };

    private final String operatorName;
    private final int requiredOperands;
    private final boolean hasMoreOperands;
    private final TypeSet operandType;
    private final boolean allowNullOperands;
    private final TypeSet returnType;
    private final TypeMismatchBehavior typeMismatchBehavior;
    public static final Map<String, Operator> NOT_SUPPORTED_OPERATORS;
    public static final Map<String, Operator> CONDITION_FUNCTIONS;
    public static final Map<String, Operator> VALUE_FUNCTIONS;
    public static final Map<String, Operator> SYMBOL_COMPARATORS;
    public static final Map<String, Operator> MATH_OPERATORS;

    public abstract DocumentNode evaluate(List<DocumentNode> var1, OperatorExecutor var2);

    private Operator(TypeSet returnType, String opName) {
        this(returnType, opName, 2, false);
    }

    private Operator(TypeSet returnType, String opName, int requiredOperands, boolean additionalOperands) {
        this(returnType, opName, requiredOperands, additionalOperands, TypeSet.ORDERED_TYPES);
    }

    private Operator(TypeSet returnType, String opName, int requiredOperands, boolean additionalOperands, TypeSet typeSupport) {
        this(returnType, opName, requiredOperands, additionalOperands, typeSupport, true, TypeMismatchBehavior.RETURN_FALSE);
    }

    private Operator(TypeSet returnType, String opName, int requiredOperands, boolean additionalOperands, TypeSet operandTypeSupport, boolean allowNullOperands, TypeMismatchBehavior escalatingTypeMismatch) {
        this.returnType = returnType;
        this.operatorName = opName;
        this.requiredOperands = requiredOperands;
        this.hasMoreOperands = additionalOperands;
        this.operandType = operandTypeSupport;
        this.allowNullOperands = allowNullOperands;
        this.typeMismatchBehavior = escalatingTypeMismatch;
    }

    public boolean isAdditionalOperands() {
        return this.hasMoreOperands;
    }

    public TypeSet getOperandType() {
        return this.operandType;
    }

    public TypeSet getReturnType() {
        return this.returnType;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public int getRequiredOperands() {
        return this.requiredOperands;
    }

    public boolean hasAdditionalOperands() {
        return this.hasMoreOperands;
    }

    public void validate(List<ExprTreeNode> children, OperatorValidator operatorValidator) {
    }

    public boolean numOperandsOk(int numOperands) {
        if (this.hasMoreOperands) {
            return numOperands >= this.requiredOperands;
        }
        return numOperands == this.requiredOperands;
    }

    public boolean allowNullOperands() {
        return this.allowNullOperands;
    }

    public TypeMismatchBehavior getTypeMismatchBehavior() {
        return this.typeMismatchBehavior;
    }

    public static Operator getFunctionOperator(String function, DbEnv dbEnv) {
        Operator operator = CONDITION_FUNCTIONS.get(function);
        if (operator == null) {
            operator = VALUE_FUNCTIONS.get(function);
        }
        if (operator == null || dbEnv.getConfigStringCollection(DbConfig.DISABLED_FUNCTIONS).contains(operator.operatorName)) {
            return null;
        }
        return operator;
    }

    public static Operator getSymbolComparator(String symbol) {
        if (symbol.equals("=")) {
            return EQ;
        }
        if (symbol.equals("<>")) {
            return NE;
        }
        if (symbol.equals("<")) {
            return LT;
        }
        if (symbol.equals("<=")) {
            return LE;
        }
        if (symbol.equals(">")) {
            return GT;
        }
        if (symbol.equals(">=")) {
            return GE;
        }
        return null;
    }

    public static Operator getMathOperator(String symbol) {
        return MATH_OPERATORS.get(symbol);
    }

    public static void validateConditionFunction(Operator function, DbEnv dbEnv) {
        if (CONDITION_FUNCTIONS.containsKey(function.getOperatorName())) {
            return;
        }
        dbEnv.dbAssert(Operator.getFunctionOperator(function.getOperatorName(), dbEnv) != null, "validateConditionFunction", "A non function operator was passed into this validator", new Object[]{"operator", function});
        dbEnv.throwValidationError(DbValidationError.INVALID_CONDITION_FUNCTION, "function", function.getOperatorName());
    }

    public static void validateValueFunction(Operator function, DbEnv dbEnv) {
        if (VALUE_FUNCTIONS.containsKey(function.getOperatorName())) {
            return;
        }
        dbEnv.dbAssert(Operator.getFunctionOperator(function.getOperatorName(), dbEnv) != null, "validateConditionFunction", "A non function operator was passed into this validator", new Object[]{"operator", function});
        dbEnv.throwValidationError(DbValidationError.INVALID_VALUE_FUNCTION, "function", function.getOperatorName());
    }

    static {
        NOT_SUPPORTED_OPERATORS = new HashMap<String, Operator>();
        NOT_SUPPORTED_OPERATORS.put(set_union.name(), set_union);
        NOT_SUPPORTED_OPERATORS.put(set_diff.name(), set_diff);
        CONDITION_FUNCTIONS = new HashMap<String, Operator>();
        CONDITION_FUNCTIONS.put(Operator.attribute_exists.operatorName, attribute_exists);
        CONDITION_FUNCTIONS.put(Operator.attribute_not_exists.operatorName, attribute_not_exists);
        CONDITION_FUNCTIONS.put(Operator.attribute_type.operatorName, attribute_type);
        CONDITION_FUNCTIONS.put(Operator.contains.operatorName, contains);
        CONDITION_FUNCTIONS.put(Operator.begins_with.operatorName, begins_with);
        CONDITION_FUNCTIONS.put(Operator.size.operatorName, size);
        CONDITION_FUNCTIONS.put(Operator.convert.operatorName, convert);
        CONDITION_FUNCTIONS.put(Operator.contains_key.operatorName, contains_key);
        CONDITION_FUNCTIONS.put(Operator.contains_value.operatorName, contains_value);
        VALUE_FUNCTIONS = new HashMap<String, Operator>();
        VALUE_FUNCTIONS.put(Operator.list_append.operatorName, list_append);
        VALUE_FUNCTIONS.put(Operator.list_delete.operatorName, list_delete);
        VALUE_FUNCTIONS.put(Operator.if_not_exists.operatorName, if_not_exists);
        VALUE_FUNCTIONS.put(Operator.convert.operatorName, convert);
        SYMBOL_COMPARATORS = new HashMap<String, Operator>();
        SYMBOL_COMPARATORS.put(Operator.EQ.operatorName, EQ);
        SYMBOL_COMPARATORS.put(Operator.NE.operatorName, NE);
        SYMBOL_COMPARATORS.put(Operator.LT.operatorName, LT);
        SYMBOL_COMPARATORS.put(Operator.LE.operatorName, LE);
        SYMBOL_COMPARATORS.put(Operator.GT.operatorName, GT);
        SYMBOL_COMPARATORS.put(Operator.GE.operatorName, GE);
        MATH_OPERATORS = new HashMap<String, Operator>();
        MATH_OPERATORS.put(Operator.ADDITION.operatorName, ADDITION);
        MATH_OPERATORS.put(Operator.SUBTRACTION.operatorName, SUBTRACTION);
    }

    public static enum TypeMismatchBehavior {
        RETURN_FALSE,
        RETURN_MISSING,
        THROW_ERROR;

    }
}

