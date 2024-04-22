/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.misc.NotNull
 *  org.antlr.v4.runtime.tree.ParseTree
 *  org.antlr.v4.runtime.tree.ParseTreeListener
 *  org.antlr.v4.runtime.tree.ParseTreeWalker
 */
package com.amazonaws.services.dynamodbv2.parser;

import com.amazon.dynamodb.grammar.DynamoDbGrammarBaseListener;
import com.amazon.dynamodb.grammar.DynamoDbGrammarParser;
import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathListElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateAction;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateActionType;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateListNode;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import com.amazonaws.services.dynamodbv2.parser.ReservedKeywords;
import com.google.common.collect.Sets;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

class ASTListener
extends DynamoDbGrammarBaseListener {
    private final DbEnv dbEnv;
    private final Stack<Object> stack = new Stack();
    private boolean setSectionEncountered = false;
    private boolean addSectionEncountered = false;
    private boolean deleteSectionEncountered = false;
    private boolean removeSectionEncountered = false;
    private final String expression;
    private RuleCategory ruleCategory = null;
    private int nestingLevel = 0;
    private static final Set<Operator> NESTABLE_OPERATORS_IN_CONDITION = Sets.newHashSet(Operator.size, Operator.convert);
    private static final Set<Operator> NESTABLE_OPERATORS_IN_UPDATE = Sets.newHashSet(Operator.if_not_exists, Operator.convert);

    private ASTListener(DbEnv dbEnv, String expression) {
        this.dbEnv = dbEnv;
        this.expression = expression;
    }

    public static Object translateTree(ParseTree tree, String expression, DbEnv dbEnv) {
        ASTListener listener = new ASTListener(dbEnv, expression);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk((ParseTreeListener)listener, tree);
        dbEnv.dbAssert(listener.stack.size() == 1, "getResult", "Stack size should be 1. Parsing/walking went wrong.", "stackSize", listener.stack.size(), "expression", expression);
        return listener.stack.pop();
    }

    @Override
    public void enterCondition_(@NotNull DynamoDbGrammarParser.Condition_Context ctx) {
        this.dbEnv.dbAssert(this.ruleCategory == null, "enterCondition_", "Root CONDITION rule can not be nested.", new Object[]{"ruleCategory", this.ruleCategory});
        this.ruleCategory = RuleCategory.CONDITION;
    }

    @Override
    public void enterUpdate_(@NotNull DynamoDbGrammarParser.Update_Context ctx) {
        this.dbEnv.dbAssert(this.ruleCategory == null, "enterUpdate_", "Root UPDATE rule can not be nested.", new Object[]{"ruleCategory", this.ruleCategory});
        this.ruleCategory = RuleCategory.UPDATE;
    }

    @Override
    public void exitProjection(@NotNull DynamoDbGrammarParser.ProjectionContext ctx) {
        ArrayList<DocPath> paths = new ArrayList<DocPath>();
        int numProjections = (ctx.getChildCount() + 1) / 2;
        for (int i = 0; i < numProjections; ++i) {
            paths.add((DocPath)this.stack.pop());
        }
        this.stack.push(paths);
    }

    @Override
    public void enterComparator(@NotNull DynamoDbGrammarParser.ComparatorContext ctx) {
        ++this.nestingLevel;
    }

    @Override
    public void exitComparator(@NotNull DynamoDbGrammarParser.ComparatorContext ctx) {
        ExprTreeNode op2 = (ExprTreeNode)this.stack.pop();
        Operator operator = (Operator)((Object)this.stack.pop());
        ExprTreeNode op1 = (ExprTreeNode)this.stack.pop();
        List<ExprTreeNode> operands = Arrays.asList(op1, op2);
        this.dbEnv.dbAssert(Operator.SYMBOL_COMPARATORS.containsKey(operator.getOperatorName()), "exitComparator", "Invalid comparator", new Object[]{"operator", operator});
        this.stack.push(new ExprTreeOpNode(operands, operator));
        --this.nestingLevel;
    }

    @Override
    public void enterIn(@NotNull DynamoDbGrammarParser.InContext ctx) {
        ++this.nestingLevel;
    }

    @Override
    public void exitIn(@NotNull DynamoDbGrammarParser.InContext ctx) {
        LinkedList<ExprTreeNode> operands = new LinkedList<ExprTreeNode>();
        int numOperands = 1 + (ctx.getChildCount() - 3) / 2;
        for (int i = 0; i < numOperands; ++i) {
            operands.add(0, (ExprTreeNode)this.stack.pop());
        }
        this.stack.push(new ExprTreeOpNode(operands, Operator.IN));
        --this.nestingLevel;
    }

    @Override
    public void enterBetween(@NotNull DynamoDbGrammarParser.BetweenContext ctx) {
        ++this.nestingLevel;
    }

    @Override
    public void exitBetween(@NotNull DynamoDbGrammarParser.BetweenContext ctx) {
        ExprTreeNode op3 = (ExprTreeNode)this.stack.pop();
        ExprTreeNode op2 = (ExprTreeNode)this.stack.pop();
        ExprTreeNode op1 = (ExprTreeNode)this.stack.pop();
        List<ExprTreeNode> operands = Arrays.asList(op1, op2, op3);
        this.stack.push(new ExprTreeOpNode(operands, Operator.BETWEEN));
        --this.nestingLevel;
    }

    @Override
    public void exitNegation(@NotNull DynamoDbGrammarParser.NegationContext ctx) {
        ExprTreeOpNode op = (ExprTreeOpNode)this.stack.pop();
        List<ExprTreeNode> operands = Arrays.asList(op);
        this.stack.push(new ExprTreeOpNode(operands, Operator.NOT));
    }

    @Override
    public void exitAnd(@NotNull DynamoDbGrammarParser.AndContext ctx) {
        ExprTreeOpNode op2 = (ExprTreeOpNode)this.stack.pop();
        ExprTreeOpNode op1 = (ExprTreeOpNode)this.stack.pop();
        List<ExprTreeNode> operands = Arrays.asList(op1, op2);
        this.stack.push(new ExprTreeOpNode(operands, Operator.AND));
    }

    @Override
    public void exitOr(@NotNull DynamoDbGrammarParser.OrContext ctx) {
        ExprTreeOpNode op2 = (ExprTreeOpNode)this.stack.pop();
        ExprTreeOpNode op1 = (ExprTreeOpNode)this.stack.pop();
        List<ExprTreeNode> operands = Arrays.asList(op1, op2);
        this.stack.push(new ExprTreeOpNode(operands, Operator.OR));
    }

    @Override
    public void exitComparator_symbol(@NotNull DynamoDbGrammarParser.Comparator_symbolContext ctx) {
        this.stack.push((Object)ASTListener.exitComparator_symbolInternal(ctx, this.dbEnv, this.expression, Operator.SYMBOL_COMPARATORS));
    }

    protected static Operator exitComparator_symbolInternal(DynamoDbGrammarParser.Comparator_symbolContext ctx, DbEnv dbEnv, String expression, Map<String, Operator> symbolComparators) {
        Operator operator;
        String symbol = ctx.getText();
        dbEnv.dbAssert(symbol != null, "exitComparator_symbol", "Null symbol found", "expression", expression);
        Operator operatorFromMap = symbolComparators.get(symbol);
        if (operatorFromMap == null) {
            ASTListener.logExitComparatorMapLookupFailure(ctx, dbEnv, expression, symbol, symbolComparators);
        }
        dbEnv.dbAssert((operator = Operator.getSymbolComparator(symbol)) != null, "exitComparator_symbol", "Invalid symbol", "symbol", symbol, "Operator bytes HEX value", ASTListener.bytesToHex(symbol.getBytes()), "Operator length", symbol.length(), "Expression", expression);
        return operator;
    }

    protected static void logExitComparatorMapLookupFailure(DynamoDbGrammarParser.Comparator_symbolContext ctx, DbEnv dbEnv, String expression, String symbol, Map<String, Operator> symbolComparators) {
        StringBuilder mapDetail = new StringBuilder();
        for (String s : symbolComparators.keySet()) {
            mapDetail.append(s).append("(").append(s.hashCode()).append("):").append((Object)Operator.getSymbolComparator(s)).append(" ");
        }
        long jvmUpTime = ManagementFactory.getRuntimeMXBean().getUptime();
        dbEnv.logError("exitComparator_symbol", "Invalid symbol", "symbol", symbol, "symbol length", symbol.length(), "symbol bytes HEX value", ASTListener.bytesToHex(symbol.getBytes()), "symbol hash code", symbol.hashCode(), "comparator map", mapDetail.toString(), "expression", expression, "jvm up time (ms) : ", jvmUpTime);
    }

    @Override
    public void exitUpdate(@NotNull DynamoDbGrammarParser.UpdateContext ctx) {
        ArrayList<UpdateListNode> updates = new ArrayList<UpdateListNode>();
        while (!this.stack.empty() && this.stack.peek() instanceof UpdateListNode) {
            updates.add((UpdateListNode)this.stack.pop());
        }
        this.stack.push(updates);
    }

    @Override
    public void exitSet_section(@NotNull DynamoDbGrammarParser.Set_sectionContext ctx) {
        if (this.setSectionEncountered) {
            this.dbEnv.throwValidationError(DbValidationError.DUPLICATE_SET_SECTIONS, new Object[0]);
        } else {
            this.setSectionEncountered = true;
        }
    }

    @Override
    public void exitSet_action(@NotNull DynamoDbGrammarParser.Set_actionContext ctx) {
        ExprTreeNode value = (ExprTreeNode)this.stack.pop();
        DocPath path = (DocPath)this.stack.pop();
        this.stack.push(new UpdateListNode(path, new UpdateAction(UpdateActionType.SET, value)));
    }

    @Override
    public void exitOperandValue(@NotNull DynamoDbGrammarParser.OperandValueContext ctx) {
        ExprTreeNode value = (ExprTreeNode)this.stack.peek();
        if (value instanceof ExprTreeOpNode) {
            Operator.validateValueFunction(((ExprTreeOpNode)value).getOperator(), this.dbEnv);
        }
    }

    @Override
    public void enterPlusMinus(@NotNull DynamoDbGrammarParser.PlusMinusContext ctx) {
        ++this.nestingLevel;
    }

    @Override
    public void exitPlusMinus(@NotNull DynamoDbGrammarParser.PlusMinusContext ctx) {
        ExprTreeNode op2 = (ExprTreeNode)this.stack.pop();
        ExprTreeNode op1 = (ExprTreeNode)this.stack.pop();
        List<ExprTreeNode> children = Arrays.asList(op1, op2);
        Operator operator = Operator.getMathOperator(ctx.getChild(1).getText());
        this.dbEnv.dbAssert(operator == Operator.ADDITION || operator == Operator.SUBTRACTION, "exitPlusMinus", "expected operator to be ADDITION or SUBTRACTION", new Object[]{"operator", operator, "operator text", ctx.getChild(1).getText()});
        this.stack.push(new ExprTreeOpNode(children, operator));
        --this.nestingLevel;
    }

    @Override
    public void exitAdd_section(@NotNull DynamoDbGrammarParser.Add_sectionContext ctx) {
        if (this.addSectionEncountered) {
            this.dbEnv.throwValidationError(DbValidationError.DUPLICATE_ADD_SECTIONS, new Object[0]);
        } else {
            this.addSectionEncountered = true;
        }
    }

    @Override
    public void exitAdd_action(@NotNull DynamoDbGrammarParser.Add_actionContext ctx) {
        ExprTreeNode value = (ExprTreeNode)this.stack.pop();
        this.dbEnv.dbAssert(value instanceof ExprTreeValueNode, "exitAdd_action", "add must have a literal rhs", "value", value);
        DocPath path = (DocPath)this.stack.pop();
        this.stack.push(new UpdateListNode(path, new UpdateAction(UpdateActionType.ADD, value)));
    }

    @Override
    public void exitDelete_section(@NotNull DynamoDbGrammarParser.Delete_sectionContext ctx) {
        if (this.deleteSectionEncountered) {
            this.dbEnv.throwValidationError(DbValidationError.DUPLICATE_DELETE_SECTIONS, new Object[0]);
        } else {
            this.deleteSectionEncountered = true;
        }
    }

    @Override
    public void exitDelete_action(@NotNull DynamoDbGrammarParser.Delete_actionContext ctx) {
        ExprTreeNode value = (ExprTreeNode)this.stack.pop();
        this.dbEnv.dbAssert(value instanceof ExprTreeValueNode, "exitDelete_action", "delete must have a literal rhs", "value", value);
        DocPath path = (DocPath)this.stack.pop();
        this.stack.push(new UpdateListNode(path, new UpdateAction(UpdateActionType.DELETE, value)));
    }

    @Override
    public void exitRemove_section(@NotNull DynamoDbGrammarParser.Remove_sectionContext ctx) {
        if (this.removeSectionEncountered) {
            this.dbEnv.throwValidationError(DbValidationError.DUPLICATE_REMOVE_SECTIONS, new Object[0]);
        } else {
            this.removeSectionEncountered = true;
        }
    }

    @Override
    public void exitRemove_action(@NotNull DynamoDbGrammarParser.Remove_actionContext ctx) {
        this.stack.push(new UpdateListNode((DocPath)this.stack.pop(), new UpdateAction(UpdateActionType.DELETE, null)));
    }

    @Override
    public void enterFunctionCall(@NotNull DynamoDbGrammarParser.FunctionCallContext ctx) {
        String functionName = ctx.ID().getText();
        Operator operator = Operator.getFunctionOperator(functionName, this.dbEnv);
        if (operator == null) {
            this.dbEnv.throwValidationError(DbValidationError.INVALID_FUNCTION_NAME, "function", functionName);
        }
        this.dbEnv.dbAssert(this.ruleCategory != null, "enterFunctionCall", "Function call in null rule category", "function", functionName);
        switch (this.ruleCategory) {
            case UPDATE: {
                Operator.validateValueFunction(operator, this.dbEnv);
                if (this.nestingLevel <= 0 || NESTABLE_OPERATORS_IN_UPDATE.contains((Object)operator)) break;
                this.dbEnv.throwValidationError(DbValidationError.INVALID_FUNCTION_CONTEXT, "function", functionName);
                break;
            }
            case CONDITION: {
                Operator.validateConditionFunction(operator, this.dbEnv);
                if (this.nestingLevel == 0 && !operator.getReturnType().contains(DocumentNodeType.BOOLEAN)) {
                    this.dbEnv.throwValidationError(DbValidationError.INVALID_FUNCTION_CONTEXT, "function", functionName);
                    break;
                }
                if (this.nestingLevel <= 0 || NESTABLE_OPERATORS_IN_CONDITION.contains((Object)operator)) break;
                this.dbEnv.throwValidationError(DbValidationError.INVALID_FUNCTION_CONTEXT, "function", functionName);
                break;
            }
            default: {
                this.dbEnv.dbAssert(false, "enterFunctionCall", "Unsupported rule category", new Object[]{"ruleCategory", this.ruleCategory, "function", functionName});
            }
        }
        ++this.nestingLevel;
    }

    @Override
    public void exitFunctionCall(@NotNull DynamoDbGrammarParser.FunctionCallContext ctx) {
        LinkedList<ExprTreeNode> operands = new LinkedList<ExprTreeNode>();
        int numOperands = (ctx.getChildCount() - 2) / 2;
        for (int i = 0; i < numOperands; ++i) {
            if (this.stack.peek() instanceof ExprTreeNode) {
                operands.add(0, (ExprTreeNode)this.stack.pop());
                continue;
            }
            this.dbEnv.dbAssert(false, "exitFunctionCall", "Operand was not an ExprTreeNode", "node", this.stack.peek());
        }
        String functionName = ctx.ID().getText();
        Operator operator = Operator.getFunctionOperator(functionName, this.dbEnv);
        this.dbEnv.dbAssert(operator != null, "exitFunctionCall", "Invalid function name, this should already been validated", "function", functionName);
        this.stack.push(new ExprTreeOpNode(operands, operator));
        --this.nestingLevel;
    }

    @Override
    public void exitPathOperand(@NotNull DynamoDbGrammarParser.PathOperandContext ctx) {
        this.stack.push(new ExprTreePathNode((DocPath)this.stack.pop()));
    }

    @Override
    public void exitPath(@NotNull DynamoDbGrammarParser.PathContext ctx) {
        LinkedList<DocPathElement> elements = new LinkedList<DocPathElement>();
        for (int i = 0; i < ctx.getChildCount() - 1; ++i) {
            elements.add(0, (DocPathElement)this.stack.pop());
        }
        String id = (String)this.stack.pop();
        elements.add(0, new DocPathMapElement(id));
        this.stack.push(new DocPath(elements));
    }

    @Override
    public void exitId(@NotNull DynamoDbGrammarParser.IdContext ctx) {
        String id = ctx.getText();
        ReservedKeywords.validateId(id, this.dbEnv);
        this.stack.push(id);
    }

    @Override
    public void exitListAccess(@NotNull DynamoDbGrammarParser.ListAccessContext ctx) {
        String value = ctx.getText();
        try {
            int index = Integer.valueOf(value.substring(1, value.length() - 1));
            this.stack.push(new DocPathListElement(index));
        } catch (NumberFormatException ex) {
            this.dbEnv.throwValidationError(DbValidationError.INVALID_LIST_INDEX, "index", value);
        }
    }

    @Override
    public void exitMapAccess(@NotNull DynamoDbGrammarParser.MapAccessContext ctx) {
        String id = (String)this.stack.pop();
        this.stack.push(new DocPathMapElement(id));
    }

    @Override
    public void exitScalarMapAccess(@NotNull DynamoDbGrammarParser.ScalarMapAccessContext ctx) {
        String value = ctx.getText().substring(1);
        this.stack.push(new DocPathMapElement(value));
    }

    @Override
    public void exitLiteralSub(@NotNull DynamoDbGrammarParser.LiteralSubContext ctx) {
        this.stack.push(new ExprTreeValueNode(ctx.getText()));
    }

    private static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }

    private static enum RuleCategory {
        UPDATE,
        CONDITION;

    }
}

