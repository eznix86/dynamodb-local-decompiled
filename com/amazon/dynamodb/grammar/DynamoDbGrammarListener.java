/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.tree.ParseTreeListener
 */
package com.amazon.dynamodb.grammar;

import com.amazon.dynamodb.grammar.DynamoDbGrammarParser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface DynamoDbGrammarListener
extends ParseTreeListener {
    public void enterProjection_(DynamoDbGrammarParser.Projection_Context var1);

    public void exitProjection_(DynamoDbGrammarParser.Projection_Context var1);

    public void enterProjection(DynamoDbGrammarParser.ProjectionContext var1);

    public void exitProjection(DynamoDbGrammarParser.ProjectionContext var1);

    public void enterCondition_(DynamoDbGrammarParser.Condition_Context var1);

    public void exitCondition_(DynamoDbGrammarParser.Condition_Context var1);

    public void enterOr(DynamoDbGrammarParser.OrContext var1);

    public void exitOr(DynamoDbGrammarParser.OrContext var1);

    public void enterNegation(DynamoDbGrammarParser.NegationContext var1);

    public void exitNegation(DynamoDbGrammarParser.NegationContext var1);

    public void enterIn(DynamoDbGrammarParser.InContext var1);

    public void exitIn(DynamoDbGrammarParser.InContext var1);

    public void enterAnd(DynamoDbGrammarParser.AndContext var1);

    public void exitAnd(DynamoDbGrammarParser.AndContext var1);

    public void enterBetween(DynamoDbGrammarParser.BetweenContext var1);

    public void exitBetween(DynamoDbGrammarParser.BetweenContext var1);

    public void enterFunctionCondition(DynamoDbGrammarParser.FunctionConditionContext var1);

    public void exitFunctionCondition(DynamoDbGrammarParser.FunctionConditionContext var1);

    public void enterComparator(DynamoDbGrammarParser.ComparatorContext var1);

    public void exitComparator(DynamoDbGrammarParser.ComparatorContext var1);

    public void enterConditionGrouping(DynamoDbGrammarParser.ConditionGroupingContext var1);

    public void exitConditionGrouping(DynamoDbGrammarParser.ConditionGroupingContext var1);

    public void enterComparator_symbol(DynamoDbGrammarParser.Comparator_symbolContext var1);

    public void exitComparator_symbol(DynamoDbGrammarParser.Comparator_symbolContext var1);

    public void enterUpdate_(DynamoDbGrammarParser.Update_Context var1);

    public void exitUpdate_(DynamoDbGrammarParser.Update_Context var1);

    public void enterUpdate(DynamoDbGrammarParser.UpdateContext var1);

    public void exitUpdate(DynamoDbGrammarParser.UpdateContext var1);

    public void enterSet_section(DynamoDbGrammarParser.Set_sectionContext var1);

    public void exitSet_section(DynamoDbGrammarParser.Set_sectionContext var1);

    public void enterSet_action(DynamoDbGrammarParser.Set_actionContext var1);

    public void exitSet_action(DynamoDbGrammarParser.Set_actionContext var1);

    public void enterAdd_section(DynamoDbGrammarParser.Add_sectionContext var1);

    public void exitAdd_section(DynamoDbGrammarParser.Add_sectionContext var1);

    public void enterAdd_action(DynamoDbGrammarParser.Add_actionContext var1);

    public void exitAdd_action(DynamoDbGrammarParser.Add_actionContext var1);

    public void enterDelete_section(DynamoDbGrammarParser.Delete_sectionContext var1);

    public void exitDelete_section(DynamoDbGrammarParser.Delete_sectionContext var1);

    public void enterDelete_action(DynamoDbGrammarParser.Delete_actionContext var1);

    public void exitDelete_action(DynamoDbGrammarParser.Delete_actionContext var1);

    public void enterRemove_section(DynamoDbGrammarParser.Remove_sectionContext var1);

    public void exitRemove_section(DynamoDbGrammarParser.Remove_sectionContext var1);

    public void enterRemove_action(DynamoDbGrammarParser.Remove_actionContext var1);

    public void exitRemove_action(DynamoDbGrammarParser.Remove_actionContext var1);

    public void enterOperandValue(DynamoDbGrammarParser.OperandValueContext var1);

    public void exitOperandValue(DynamoDbGrammarParser.OperandValueContext var1);

    public void enterArithmeticValue(DynamoDbGrammarParser.ArithmeticValueContext var1);

    public void exitArithmeticValue(DynamoDbGrammarParser.ArithmeticValueContext var1);

    public void enterPlusMinus(DynamoDbGrammarParser.PlusMinusContext var1);

    public void exitPlusMinus(DynamoDbGrammarParser.PlusMinusContext var1);

    public void enterArithmeticParens(DynamoDbGrammarParser.ArithmeticParensContext var1);

    public void exitArithmeticParens(DynamoDbGrammarParser.ArithmeticParensContext var1);

    public void enterPathOperand(DynamoDbGrammarParser.PathOperandContext var1);

    public void exitPathOperand(DynamoDbGrammarParser.PathOperandContext var1);

    public void enterLiteralOperand(DynamoDbGrammarParser.LiteralOperandContext var1);

    public void exitLiteralOperand(DynamoDbGrammarParser.LiteralOperandContext var1);

    public void enterFunctionOperand(DynamoDbGrammarParser.FunctionOperandContext var1);

    public void exitFunctionOperand(DynamoDbGrammarParser.FunctionOperandContext var1);

    public void enterParenOperand(DynamoDbGrammarParser.ParenOperandContext var1);

    public void exitParenOperand(DynamoDbGrammarParser.ParenOperandContext var1);

    public void enterFunctionCall(DynamoDbGrammarParser.FunctionCallContext var1);

    public void exitFunctionCall(DynamoDbGrammarParser.FunctionCallContext var1);

    public void enterPath(DynamoDbGrammarParser.PathContext var1);

    public void exitPath(DynamoDbGrammarParser.PathContext var1);

    public void enterId(DynamoDbGrammarParser.IdContext var1);

    public void exitId(DynamoDbGrammarParser.IdContext var1);

    public void enterMapAccess(DynamoDbGrammarParser.MapAccessContext var1);

    public void exitMapAccess(DynamoDbGrammarParser.MapAccessContext var1);

    public void enterListAccess(DynamoDbGrammarParser.ListAccessContext var1);

    public void exitListAccess(DynamoDbGrammarParser.ListAccessContext var1);

    public void enterScalarMapAccess(DynamoDbGrammarParser.ScalarMapAccessContext var1);

    public void exitScalarMapAccess(DynamoDbGrammarParser.ScalarMapAccessContext var1);

    public void enterLiteralSub(DynamoDbGrammarParser.LiteralSubContext var1);

    public void exitLiteralSub(DynamoDbGrammarParser.LiteralSubContext var1);

    public void enterExpression_attr_names_sub(DynamoDbGrammarParser.Expression_attr_names_subContext var1);

    public void exitExpression_attr_names_sub(DynamoDbGrammarParser.Expression_attr_names_subContext var1);

    public void enterExpression_attr_values_sub(DynamoDbGrammarParser.Expression_attr_values_subContext var1);

    public void exitExpression_attr_values_sub(DynamoDbGrammarParser.Expression_attr_values_subContext var1);

    public void enterUnknown(DynamoDbGrammarParser.UnknownContext var1);

    public void exitUnknown(DynamoDbGrammarParser.UnknownContext var1);
}

