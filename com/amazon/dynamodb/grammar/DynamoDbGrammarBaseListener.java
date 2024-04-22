/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.ParserRuleContext
 *  org.antlr.v4.runtime.tree.ErrorNode
 *  org.antlr.v4.runtime.tree.TerminalNode
 */
package com.amazon.dynamodb.grammar;

import com.amazon.dynamodb.grammar.DynamoDbGrammarListener;
import com.amazon.dynamodb.grammar.DynamoDbGrammarParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class DynamoDbGrammarBaseListener
implements DynamoDbGrammarListener {
    @Override
    public void enterProjection_(DynamoDbGrammarParser.Projection_Context ctx) {
    }

    @Override
    public void exitProjection_(DynamoDbGrammarParser.Projection_Context ctx) {
    }

    @Override
    public void enterProjection(DynamoDbGrammarParser.ProjectionContext ctx) {
    }

    @Override
    public void exitProjection(DynamoDbGrammarParser.ProjectionContext ctx) {
    }

    @Override
    public void enterCondition_(DynamoDbGrammarParser.Condition_Context ctx) {
    }

    @Override
    public void exitCondition_(DynamoDbGrammarParser.Condition_Context ctx) {
    }

    @Override
    public void enterOr(DynamoDbGrammarParser.OrContext ctx) {
    }

    @Override
    public void exitOr(DynamoDbGrammarParser.OrContext ctx) {
    }

    @Override
    public void enterNegation(DynamoDbGrammarParser.NegationContext ctx) {
    }

    @Override
    public void exitNegation(DynamoDbGrammarParser.NegationContext ctx) {
    }

    @Override
    public void enterIn(DynamoDbGrammarParser.InContext ctx) {
    }

    @Override
    public void exitIn(DynamoDbGrammarParser.InContext ctx) {
    }

    @Override
    public void enterAnd(DynamoDbGrammarParser.AndContext ctx) {
    }

    @Override
    public void exitAnd(DynamoDbGrammarParser.AndContext ctx) {
    }

    @Override
    public void enterBetween(DynamoDbGrammarParser.BetweenContext ctx) {
    }

    @Override
    public void exitBetween(DynamoDbGrammarParser.BetweenContext ctx) {
    }

    @Override
    public void enterFunctionCondition(DynamoDbGrammarParser.FunctionConditionContext ctx) {
    }

    @Override
    public void exitFunctionCondition(DynamoDbGrammarParser.FunctionConditionContext ctx) {
    }

    @Override
    public void enterComparator(DynamoDbGrammarParser.ComparatorContext ctx) {
    }

    @Override
    public void exitComparator(DynamoDbGrammarParser.ComparatorContext ctx) {
    }

    @Override
    public void enterConditionGrouping(DynamoDbGrammarParser.ConditionGroupingContext ctx) {
    }

    @Override
    public void exitConditionGrouping(DynamoDbGrammarParser.ConditionGroupingContext ctx) {
    }

    @Override
    public void enterComparator_symbol(DynamoDbGrammarParser.Comparator_symbolContext ctx) {
    }

    @Override
    public void exitComparator_symbol(DynamoDbGrammarParser.Comparator_symbolContext ctx) {
    }

    @Override
    public void enterUpdate_(DynamoDbGrammarParser.Update_Context ctx) {
    }

    @Override
    public void exitUpdate_(DynamoDbGrammarParser.Update_Context ctx) {
    }

    @Override
    public void enterUpdate(DynamoDbGrammarParser.UpdateContext ctx) {
    }

    @Override
    public void exitUpdate(DynamoDbGrammarParser.UpdateContext ctx) {
    }

    @Override
    public void enterSet_section(DynamoDbGrammarParser.Set_sectionContext ctx) {
    }

    @Override
    public void exitSet_section(DynamoDbGrammarParser.Set_sectionContext ctx) {
    }

    @Override
    public void enterSet_action(DynamoDbGrammarParser.Set_actionContext ctx) {
    }

    @Override
    public void exitSet_action(DynamoDbGrammarParser.Set_actionContext ctx) {
    }

    @Override
    public void enterAdd_section(DynamoDbGrammarParser.Add_sectionContext ctx) {
    }

    @Override
    public void exitAdd_section(DynamoDbGrammarParser.Add_sectionContext ctx) {
    }

    @Override
    public void enterAdd_action(DynamoDbGrammarParser.Add_actionContext ctx) {
    }

    @Override
    public void exitAdd_action(DynamoDbGrammarParser.Add_actionContext ctx) {
    }

    @Override
    public void enterDelete_section(DynamoDbGrammarParser.Delete_sectionContext ctx) {
    }

    @Override
    public void exitDelete_section(DynamoDbGrammarParser.Delete_sectionContext ctx) {
    }

    @Override
    public void enterDelete_action(DynamoDbGrammarParser.Delete_actionContext ctx) {
    }

    @Override
    public void exitDelete_action(DynamoDbGrammarParser.Delete_actionContext ctx) {
    }

    @Override
    public void enterRemove_section(DynamoDbGrammarParser.Remove_sectionContext ctx) {
    }

    @Override
    public void exitRemove_section(DynamoDbGrammarParser.Remove_sectionContext ctx) {
    }

    @Override
    public void enterRemove_action(DynamoDbGrammarParser.Remove_actionContext ctx) {
    }

    @Override
    public void exitRemove_action(DynamoDbGrammarParser.Remove_actionContext ctx) {
    }

    @Override
    public void enterOperandValue(DynamoDbGrammarParser.OperandValueContext ctx) {
    }

    @Override
    public void exitOperandValue(DynamoDbGrammarParser.OperandValueContext ctx) {
    }

    @Override
    public void enterArithmeticValue(DynamoDbGrammarParser.ArithmeticValueContext ctx) {
    }

    @Override
    public void exitArithmeticValue(DynamoDbGrammarParser.ArithmeticValueContext ctx) {
    }

    @Override
    public void enterPlusMinus(DynamoDbGrammarParser.PlusMinusContext ctx) {
    }

    @Override
    public void exitPlusMinus(DynamoDbGrammarParser.PlusMinusContext ctx) {
    }

    @Override
    public void enterArithmeticParens(DynamoDbGrammarParser.ArithmeticParensContext ctx) {
    }

    @Override
    public void exitArithmeticParens(DynamoDbGrammarParser.ArithmeticParensContext ctx) {
    }

    @Override
    public void enterPathOperand(DynamoDbGrammarParser.PathOperandContext ctx) {
    }

    @Override
    public void exitPathOperand(DynamoDbGrammarParser.PathOperandContext ctx) {
    }

    @Override
    public void enterLiteralOperand(DynamoDbGrammarParser.LiteralOperandContext ctx) {
    }

    @Override
    public void exitLiteralOperand(DynamoDbGrammarParser.LiteralOperandContext ctx) {
    }

    @Override
    public void enterFunctionOperand(DynamoDbGrammarParser.FunctionOperandContext ctx) {
    }

    @Override
    public void exitFunctionOperand(DynamoDbGrammarParser.FunctionOperandContext ctx) {
    }

    @Override
    public void enterParenOperand(DynamoDbGrammarParser.ParenOperandContext ctx) {
    }

    @Override
    public void exitParenOperand(DynamoDbGrammarParser.ParenOperandContext ctx) {
    }

    @Override
    public void enterFunctionCall(DynamoDbGrammarParser.FunctionCallContext ctx) {
    }

    @Override
    public void exitFunctionCall(DynamoDbGrammarParser.FunctionCallContext ctx) {
    }

    @Override
    public void enterPath(DynamoDbGrammarParser.PathContext ctx) {
    }

    @Override
    public void exitPath(DynamoDbGrammarParser.PathContext ctx) {
    }

    @Override
    public void enterId(DynamoDbGrammarParser.IdContext ctx) {
    }

    @Override
    public void exitId(DynamoDbGrammarParser.IdContext ctx) {
    }

    @Override
    public void enterMapAccess(DynamoDbGrammarParser.MapAccessContext ctx) {
    }

    @Override
    public void exitMapAccess(DynamoDbGrammarParser.MapAccessContext ctx) {
    }

    @Override
    public void enterListAccess(DynamoDbGrammarParser.ListAccessContext ctx) {
    }

    @Override
    public void exitListAccess(DynamoDbGrammarParser.ListAccessContext ctx) {
    }

    @Override
    public void enterScalarMapAccess(DynamoDbGrammarParser.ScalarMapAccessContext ctx) {
    }

    @Override
    public void exitScalarMapAccess(DynamoDbGrammarParser.ScalarMapAccessContext ctx) {
    }

    @Override
    public void enterLiteralSub(DynamoDbGrammarParser.LiteralSubContext ctx) {
    }

    @Override
    public void exitLiteralSub(DynamoDbGrammarParser.LiteralSubContext ctx) {
    }

    @Override
    public void enterExpression_attr_names_sub(DynamoDbGrammarParser.Expression_attr_names_subContext ctx) {
    }

    @Override
    public void exitExpression_attr_names_sub(DynamoDbGrammarParser.Expression_attr_names_subContext ctx) {
    }

    @Override
    public void enterExpression_attr_values_sub(DynamoDbGrammarParser.Expression_attr_values_subContext ctx) {
    }

    @Override
    public void exitExpression_attr_values_sub(DynamoDbGrammarParser.Expression_attr_values_subContext ctx) {
    }

    @Override
    public void enterUnknown(DynamoDbGrammarParser.UnknownContext ctx) {
    }

    @Override
    public void exitUnknown(DynamoDbGrammarParser.UnknownContext ctx) {
    }

    public void enterEveryRule(ParserRuleContext ctx) {
    }

    public void exitEveryRule(ParserRuleContext ctx) {
    }

    public void visitTerminal(TerminalNode node) {
    }

    public void visitErrorNode(ErrorNode node) {
    }
}

