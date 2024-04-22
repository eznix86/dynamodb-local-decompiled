/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.tree.ParseTree
 */
package com.amazon.dynamodb.grammar;

import com.amazon.dynamodb.grammar.AbstractTwoStageExpressionParser;
import com.amazon.dynamodb.grammar.DynamoDbGrammarParser;
import org.antlr.v4.runtime.tree.ParseTree;

public class UpdateExpressionParser
extends AbstractTwoStageExpressionParser {
    @Override
    public ParseTree parseStub(DynamoDbGrammarParser parser) {
        return parser.update_();
    }
}

