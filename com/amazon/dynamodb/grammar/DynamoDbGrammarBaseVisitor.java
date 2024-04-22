/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.tree.AbstractParseTreeVisitor
 *  org.antlr.v4.runtime.tree.RuleNode
 */
package com.amazon.dynamodb.grammar;

import com.amazon.dynamodb.grammar.DynamoDbGrammarParser;
import com.amazon.dynamodb.grammar.DynamoDbGrammarVisitor;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.RuleNode;

public class DynamoDbGrammarBaseVisitor<T>
extends AbstractParseTreeVisitor<T>
implements DynamoDbGrammarVisitor<T> {
    @Override
    public T visitProjection_(DynamoDbGrammarParser.Projection_Context ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitProjection(DynamoDbGrammarParser.ProjectionContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitCondition_(DynamoDbGrammarParser.Condition_Context ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitOr(DynamoDbGrammarParser.OrContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitNegation(DynamoDbGrammarParser.NegationContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitIn(DynamoDbGrammarParser.InContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitAnd(DynamoDbGrammarParser.AndContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitBetween(DynamoDbGrammarParser.BetweenContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitFunctionCondition(DynamoDbGrammarParser.FunctionConditionContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitComparator(DynamoDbGrammarParser.ComparatorContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitConditionGrouping(DynamoDbGrammarParser.ConditionGroupingContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitComparator_symbol(DynamoDbGrammarParser.Comparator_symbolContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitUpdate_(DynamoDbGrammarParser.Update_Context ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitUpdate(DynamoDbGrammarParser.UpdateContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitSet_section(DynamoDbGrammarParser.Set_sectionContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitSet_action(DynamoDbGrammarParser.Set_actionContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitAdd_section(DynamoDbGrammarParser.Add_sectionContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitAdd_action(DynamoDbGrammarParser.Add_actionContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitDelete_section(DynamoDbGrammarParser.Delete_sectionContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitDelete_action(DynamoDbGrammarParser.Delete_actionContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitRemove_section(DynamoDbGrammarParser.Remove_sectionContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitRemove_action(DynamoDbGrammarParser.Remove_actionContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitOperandValue(DynamoDbGrammarParser.OperandValueContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitArithmeticValue(DynamoDbGrammarParser.ArithmeticValueContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitPlusMinus(DynamoDbGrammarParser.PlusMinusContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitArithmeticParens(DynamoDbGrammarParser.ArithmeticParensContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitPathOperand(DynamoDbGrammarParser.PathOperandContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitLiteralOperand(DynamoDbGrammarParser.LiteralOperandContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitFunctionOperand(DynamoDbGrammarParser.FunctionOperandContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitParenOperand(DynamoDbGrammarParser.ParenOperandContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitFunctionCall(DynamoDbGrammarParser.FunctionCallContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitPath(DynamoDbGrammarParser.PathContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitId(DynamoDbGrammarParser.IdContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitMapAccess(DynamoDbGrammarParser.MapAccessContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitListAccess(DynamoDbGrammarParser.ListAccessContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitScalarMapAccess(DynamoDbGrammarParser.ScalarMapAccessContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitLiteralSub(DynamoDbGrammarParser.LiteralSubContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitExpression_attr_names_sub(DynamoDbGrammarParser.Expression_attr_names_subContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitExpression_attr_values_sub(DynamoDbGrammarParser.Expression_attr_values_subContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }

    @Override
    public T visitUnknown(DynamoDbGrammarParser.UnknownContext ctx) {
        return (T)this.visitChildren((RuleNode)ctx);
    }
}

