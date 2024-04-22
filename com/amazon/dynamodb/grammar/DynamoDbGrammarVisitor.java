/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.tree.ParseTreeVisitor
 */
package com.amazon.dynamodb.grammar;

import com.amazon.dynamodb.grammar.DynamoDbGrammarParser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

public interface DynamoDbGrammarVisitor<T>
extends ParseTreeVisitor<T> {
    public T visitProjection_(DynamoDbGrammarParser.Projection_Context var1);

    public T visitProjection(DynamoDbGrammarParser.ProjectionContext var1);

    public T visitCondition_(DynamoDbGrammarParser.Condition_Context var1);

    public T visitOr(DynamoDbGrammarParser.OrContext var1);

    public T visitNegation(DynamoDbGrammarParser.NegationContext var1);

    public T visitIn(DynamoDbGrammarParser.InContext var1);

    public T visitAnd(DynamoDbGrammarParser.AndContext var1);

    public T visitBetween(DynamoDbGrammarParser.BetweenContext var1);

    public T visitFunctionCondition(DynamoDbGrammarParser.FunctionConditionContext var1);

    public T visitComparator(DynamoDbGrammarParser.ComparatorContext var1);

    public T visitConditionGrouping(DynamoDbGrammarParser.ConditionGroupingContext var1);

    public T visitComparator_symbol(DynamoDbGrammarParser.Comparator_symbolContext var1);

    public T visitUpdate_(DynamoDbGrammarParser.Update_Context var1);

    public T visitUpdate(DynamoDbGrammarParser.UpdateContext var1);

    public T visitSet_section(DynamoDbGrammarParser.Set_sectionContext var1);

    public T visitSet_action(DynamoDbGrammarParser.Set_actionContext var1);

    public T visitAdd_section(DynamoDbGrammarParser.Add_sectionContext var1);

    public T visitAdd_action(DynamoDbGrammarParser.Add_actionContext var1);

    public T visitDelete_section(DynamoDbGrammarParser.Delete_sectionContext var1);

    public T visitDelete_action(DynamoDbGrammarParser.Delete_actionContext var1);

    public T visitRemove_section(DynamoDbGrammarParser.Remove_sectionContext var1);

    public T visitRemove_action(DynamoDbGrammarParser.Remove_actionContext var1);

    public T visitOperandValue(DynamoDbGrammarParser.OperandValueContext var1);

    public T visitArithmeticValue(DynamoDbGrammarParser.ArithmeticValueContext var1);

    public T visitPlusMinus(DynamoDbGrammarParser.PlusMinusContext var1);

    public T visitArithmeticParens(DynamoDbGrammarParser.ArithmeticParensContext var1);

    public T visitPathOperand(DynamoDbGrammarParser.PathOperandContext var1);

    public T visitLiteralOperand(DynamoDbGrammarParser.LiteralOperandContext var1);

    public T visitFunctionOperand(DynamoDbGrammarParser.FunctionOperandContext var1);

    public T visitParenOperand(DynamoDbGrammarParser.ParenOperandContext var1);

    public T visitFunctionCall(DynamoDbGrammarParser.FunctionCallContext var1);

    public T visitPath(DynamoDbGrammarParser.PathContext var1);

    public T visitId(DynamoDbGrammarParser.IdContext var1);

    public T visitMapAccess(DynamoDbGrammarParser.MapAccessContext var1);

    public T visitListAccess(DynamoDbGrammarParser.ListAccessContext var1);

    public T visitScalarMapAccess(DynamoDbGrammarParser.ScalarMapAccessContext var1);

    public T visitLiteralSub(DynamoDbGrammarParser.LiteralSubContext var1);

    public T visitExpression_attr_names_sub(DynamoDbGrammarParser.Expression_attr_names_subContext var1);

    public T visitExpression_attr_values_sub(DynamoDbGrammarParser.Expression_attr_values_subContext var1);

    public T visitUnknown(DynamoDbGrammarParser.UnknownContext var1);
}

