/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.BaseErrorListener
 *  org.antlr.v4.runtime.RecognitionException
 *  org.antlr.v4.runtime.Recognizer
 *  org.antlr.v4.runtime.Token
 */
package com.amazonaws.services.dynamodbv2.parser;

import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

public class ParameterMapErrorListener
extends BaseErrorListener {
    private final DbEnv dbEnv;
    private final String key;

    public ParameterMapErrorListener(DbEnv dbEnv, String key) {
        this.dbEnv = dbEnv;
        this.key = key;
    }

    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        this.dbEnv.dbAssert(offendingSymbol != null && offendingSymbol instanceof Token, "syntaxError", "offendingSymbol should be a Token", "offendingSymbol", offendingSymbol);
        this.dbEnv.throwValidationError(DbValidationError.SYNTAX_ERROR, "key", "\"" + this.key + "\"");
    }
}

