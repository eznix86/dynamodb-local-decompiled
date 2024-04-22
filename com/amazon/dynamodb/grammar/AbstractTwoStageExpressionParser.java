/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.ANTLRErrorListener
 *  org.antlr.v4.runtime.ANTLRErrorStrategy
 *  org.antlr.v4.runtime.ANTLRInputStream
 *  org.antlr.v4.runtime.BailErrorStrategy
 *  org.antlr.v4.runtime.CharStream
 *  org.antlr.v4.runtime.CommonTokenStream
 *  org.antlr.v4.runtime.DefaultErrorStrategy
 *  org.antlr.v4.runtime.TokenSource
 *  org.antlr.v4.runtime.TokenStream
 *  org.antlr.v4.runtime.atn.ParserATNSimulator
 *  org.antlr.v4.runtime.atn.PredictionMode
 *  org.antlr.v4.runtime.misc.ParseCancellationException
 *  org.antlr.v4.runtime.tree.ParseTree
 */
package com.amazon.dynamodb.grammar;

import com.amazon.dynamodb.grammar.DynamoDbGrammarLexer;
import com.amazon.dynamodb.grammar.DynamoDbGrammarParser;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

public abstract class AbstractTwoStageExpressionParser {
    public ParseTree parse(String expression, ANTLRErrorListener errorListener) {
        DynamoDbGrammarLexer lexer = new DynamoDbGrammarLexer((CharStream)new ANTLRInputStream(expression));
        CommonTokenStream tokens = new CommonTokenStream((TokenSource)lexer);
        DynamoDbGrammarParser parser = new DynamoDbGrammarParser((TokenStream)tokens);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        parser.removeErrorListeners();
        parser.setErrorHandler((ANTLRErrorStrategy)new BailErrorStrategy());
        ((ParserATNSimulator)parser.getInterpreter()).setPredictionMode(PredictionMode.SLL);
        try {
            return this.parseStub(parser);
        } catch (ParseCancellationException e) {
            tokens.reset();
            parser.reset();
            parser.addErrorListener(errorListener);
            parser.setErrorHandler((ANTLRErrorStrategy)new DefaultErrorStrategy());
            ((ParserATNSimulator)parser.getInterpreter()).setPredictionMode(PredictionMode.LL);
            return this.parseStub(parser);
        }
    }

    protected abstract ParseTree parseStub(DynamoDbGrammarParser var1);
}

