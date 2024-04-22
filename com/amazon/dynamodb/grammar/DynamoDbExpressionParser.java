/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.ANTLRErrorListener
 *  org.antlr.v4.runtime.tree.ParseTree
 */
package com.amazon.dynamodb.grammar;

import com.amazon.dynamodb.grammar.AttributeNamesMapKeysParser;
import com.amazon.dynamodb.grammar.AttributeValuesMapKeysParser;
import com.amazon.dynamodb.grammar.ConditionExpressionParser;
import com.amazon.dynamodb.grammar.ProjectionExpressionParser;
import com.amazon.dynamodb.grammar.UpdateExpressionParser;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;

public class DynamoDbExpressionParser {
    public static ParseTree parseProjection(String expression, ANTLRErrorListener errorListener) {
        return new ProjectionExpressionParser().parse(expression, errorListener);
    }

    public static ParseTree parseCondition(String expression, ANTLRErrorListener errorListener) {
        return new ConditionExpressionParser().parse(expression, errorListener);
    }

    public static ParseTree parseUpdate(String expression, ANTLRErrorListener errorListener) {
        return new UpdateExpressionParser().parse(expression, errorListener);
    }

    public static void parseAttributeNamesMapKeys(String attrMapKey, ANTLRErrorListener errorListener) {
        new AttributeNamesMapKeysParser().parse(attrMapKey, errorListener);
    }

    public static void parseAttributeValuesMapKeys(String attrMapKey, ANTLRErrorListener errorListener) {
        new AttributeValuesMapKeysParser().parse(attrMapKey, errorListener);
    }
}

