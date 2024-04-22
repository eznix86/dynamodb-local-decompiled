/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.BaseErrorListener
 *  org.antlr.v4.runtime.CommonTokenStream
 *  org.antlr.v4.runtime.IntStream
 *  org.antlr.v4.runtime.RecognitionException
 *  org.antlr.v4.runtime.Recognizer
 *  org.antlr.v4.runtime.Token
 */
package com.amazonaws.services.dynamodbv2.parser;

import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

public class ExpressionErrorListener
extends BaseErrorListener {
    private final DbEnv dbEnv;

    public ExpressionErrorListener(DbEnv dbEnv) {
        this.dbEnv = dbEnv;
    }

    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        List hiddenRight;
        String TRACE_HEADER = "syntaxError";
        IntStream inputStream = recognizer.getInputStream();
        this.dbEnv.dbAssert(inputStream instanceof CommonTokenStream, "syntaxError", "input stream should be an IntStream", "inputStream", inputStream);
        CommonTokenStream tokenStream = (CommonTokenStream)inputStream;
        tokenStream.seek(((Token)offendingSymbol).getTokenIndex());
        Token currToken = tokenStream.LT(1);
        this.dbEnv.dbAssert(offendingSymbol != null && offendingSymbol instanceof Token, "syntaxError", "offendingSymbol should be a Token", "offendingSymbol", offendingSymbol);
        this.dbEnv.dbAssert(currToken.equals(offendingSymbol), "syntaxError", "current token does not match offending token", "currToken", currToken, "offendingSymbol", offendingSymbol);
        Token prevToken = tokenStream.LT(-1);
        Token nextToken = tokenStream.LT(2);
        List hiddenLeft = tokenStream.getHiddenTokensToLeft(tokenStream.index());
        hiddenRight = currToken.getType() != -1 ? (hiddenRight = tokenStream.getHiddenTokensToRight(tokenStream.index())) : null;
        StringBuilder sb = new StringBuilder();
        sb.append(this.tokenToString(prevToken, false));
        sb.append(this.tokenListToString(hiddenLeft));
        sb.append(this.tokenToString(currToken, false));
        sb.append(this.tokenListToString(hiddenRight));
        sb.append(this.tokenToString(nextToken, false));
        this.dbEnv.throwValidationError(DbValidationError.SYNTAX_ERROR, "token", "\"" + this.tokenToString(currToken, true) + "\"", "near", "\"" + sb.toString() + "\"");
    }

    private String tokenToString(Token token, boolean printEOF) {
        if (token != null) {
            return token.getType() == -1 && !printEOF ? "" : token.getText();
        }
        return "";
    }

    private String tokenListToString(List<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        if (tokens != null) {
            for (Token token : tokens) {
                sb.append(this.tokenToString(token, false));
            }
        }
        return sb.toString();
    }
}

