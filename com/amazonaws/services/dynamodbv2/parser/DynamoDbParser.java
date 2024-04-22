/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.ANTLRErrorListener
 *  org.antlr.v4.runtime.tree.ParseTree
 */
package com.amazonaws.services.dynamodbv2.parser;

import com.amazon.dynamodb.grammar.DynamoDbExpressionParser;
import com.amazon.dynamodb.grammar.exceptions.RedundantParenthesesException;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.ParameterMap;
import com.amazonaws.services.dynamodbv2.dbenv.DbConfig;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import com.amazonaws.services.dynamodbv2.parser.ASTListener;
import com.amazonaws.services.dynamodbv2.parser.ExpressionErrorListener;
import com.amazonaws.services.dynamodbv2.parser.ParameterMapErrorListener;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.UpdateExpressionWrapper;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;

public class DynamoDbParser {
    public static ProjectionExpressionWrapper parseProjectionExpression(String expressionString, Map<String, String> attributeNameParameters, DbEnv dbEnv, DocumentFactory factory) {
        return DynamoDbParser.parseProjectionExpression(expressionString, attributeNameParameters, null, dbEnv, factory);
    }

    public static ProjectionExpressionWrapper parseProjectionExpression(String expressionString, Map<String, String> attributeNameParameters, Map<String, DocumentNode> literalParameters, DbEnv dbEnv, DocumentFactory factory) {
        return DynamoDbParser.parseProjectionExpression(expressionString, attributeNameParameters, literalParameters, dbEnv, factory, true);
    }

    public static ProjectionExpressionWrapper parseProjectionExpression(String expressionString, Map<String, String> attributeNameParameters, Map<String, DocumentNode> literalParameters, DbEnv dbEnv, DocumentFactory factory, boolean allowValueParameterInExpressionDocPath) {
        String TRACE_HEADER = "parseProjectionExpression";
        dbEnv.dbAssert(expressionString != null, "parseProjectionExpression", "expression string should not be null", new Object[0]);
        if (expressionString.length() <= 0) {
            dbEnv.throwValidationError(DbValidationError.EMPTY_EXPRESSION, new Object[0]);
        }
        if (expressionString.length() > dbEnv.getConfigInt(DbConfig.MAX_EXPRESSION_SIZE)) {
            dbEnv.throwValidationError(DbValidationError.EXPRESSION_SIZE_EXCEEDED, new Object[0]);
        }
        ExpressionErrorListener errorListener = new ExpressionErrorListener(dbEnv);
        ParseTree tree = null;
        try {
            tree = DynamoDbExpressionParser.parseProjection(expressionString, (ANTLRErrorListener)errorListener);
        } catch (RedundantParenthesesException e) {
            dbEnv.dbAssert(false, "parseProjectionExpression", "did not expect any parentheses in a ProjectionExpression!!", "projectionExpression", expressionString);
        }
        dbEnv.dbAssert(tree != null, "parseProjectionExpression", "Tree is null after projection parsing", new Object[0]);
        List pathList = (List)ASTListener.translateTree(tree, expressionString, dbEnv);
        ParameterMap parameterMap = new ParameterMap(attributeNameParameters, literalParameters, factory);
        ExpressionValidator validator = new ExpressionValidator(dbEnv, parameterMap, false, allowValueParameterInExpressionDocPath);
        return new ProjectionExpressionWrapper(pathList, validator);
    }

    public static UpdateExpressionWrapper parseUpdateExpression(String string, Map<String, String> attributeNameParameters, Map<String, DocumentNode> literalParameters, DbEnv dbEnv, DocumentFactory factory) {
        return DynamoDbParser.parseUpdateExpression(string, attributeNameParameters, literalParameters, dbEnv, factory, true);
    }

    public static UpdateExpressionWrapper parseUpdateExpression(String string, Map<String, String> attributeNameParameters, Map<String, DocumentNode> literalParameters, DbEnv dbEnv, DocumentFactory factory, boolean allowValueParameterInExpressionDocPath) {
        String TRACE_HEADER = "parseUpdateExpression";
        dbEnv.dbAssert(string != null, "parseUpdateExpression", "string should not be null", new Object[0]);
        if (string.length() <= 0) {
            dbEnv.throwValidationError(DbValidationError.EMPTY_EXPRESSION, new Object[0]);
        }
        if (string.length() > dbEnv.getConfigInt(DbConfig.MAX_EXPRESSION_SIZE)) {
            dbEnv.throwValidationError(DbValidationError.EXPRESSION_SIZE_EXCEEDED, new Object[0]);
        }
        ExpressionErrorListener errorListener = new ExpressionErrorListener(dbEnv);
        ParseTree tree = null;
        try {
            tree = DynamoDbExpressionParser.parseUpdate(string, (ANTLRErrorListener)errorListener);
        } catch (RedundantParenthesesException e) {
            dbEnv.throwValidationError(DbValidationError.REDUNDANT_PARENTHESES, new Object[0]);
        }
        List updateList = (List)ASTListener.translateTree(tree, string, dbEnv);
        ParameterMap parameters = new ParameterMap(attributeNameParameters, literalParameters, factory);
        ExpressionValidator validator = new ExpressionValidator(dbEnv, parameters, false, allowValueParameterInExpressionDocPath);
        return new UpdateExpressionWrapper(updateList, validator);
    }

    public static ExpressionWrapper parseExpression(String string, Map<String, String> attributeNameParameters, Map<String, DocumentNode> literalParameters, DbEnv dbEnv, DocumentFactory factory) {
        return DynamoDbParser.parseExpression(string, attributeNameParameters, literalParameters, dbEnv, factory, false);
    }

    public static ExpressionWrapper parseExpression(String string, Map<String, String> attributeNameParameters, Map<String, DocumentNode> literalParameters, DbEnv dbEnv, DocumentFactory factory, boolean areIonNumericTypesAllowed) {
        return DynamoDbParser.parseExpression(string, attributeNameParameters, literalParameters, dbEnv, factory, areIonNumericTypesAllowed, true);
    }

    public static ExpressionWrapper parseExpression(String string, Map<String, String> attributeNameParameters, Map<String, DocumentNode> literalParameters, DbEnv dbEnv, DocumentFactory factory, boolean areIonNumericTypesAllowed, boolean allowValueParameterInExpressionDocPath) {
        String TRACE_HEADER = "parseExpression";
        dbEnv.dbAssert(string != null, "parseExpression", "string should not be null", new Object[0]);
        if (string.length() <= 0) {
            dbEnv.throwValidationError(DbValidationError.EMPTY_EXPRESSION, new Object[0]);
        }
        if (string.length() > dbEnv.getConfigInt(DbConfig.MAX_EXPRESSION_SIZE)) {
            dbEnv.throwValidationError(DbValidationError.EXPRESSION_SIZE_EXCEEDED, "expression size", string.length());
        }
        ExpressionErrorListener errorListener = new ExpressionErrorListener(dbEnv);
        ParseTree tree = null;
        try {
            tree = DynamoDbExpressionParser.parseCondition(string, (ANTLRErrorListener)errorListener);
        } catch (RedundantParenthesesException e) {
            dbEnv.throwValidationError(DbValidationError.REDUNDANT_PARENTHESES, new Object[0]);
        }
        ExprTreeNode conditions = (ExprTreeNode)ASTListener.translateTree(tree, string, dbEnv);
        ParameterMap parameters = new ParameterMap(attributeNameParameters, literalParameters, factory);
        ExpressionValidator validator = new ExpressionValidator(dbEnv, parameters, areIonNumericTypesAllowed, allowValueParameterInExpressionDocPath);
        return new ExpressionWrapper(conditions, validator);
    }

    public static void validateAttributeNameParameter(Map<String, String> attributeNameParameters, DbEnv dbEnv) {
        if (attributeNameParameters != null) {
            for (Map.Entry<String, String> attributeNameParameter : attributeNameParameters.entrySet()) {
                String key = attributeNameParameter.getKey();
                if (key == null) {
                    dbEnv.throwValidationError(DbValidationError.PARAMETER_MAP_NULL_KEY, new Object[0]);
                }
                if (key.length() == 0) {
                    dbEnv.throwValidationError(DbValidationError.PARAMETER_MAP_EMPTY_KEY, new Object[0]);
                }
                if (key.length() > dbEnv.getConfigInt(DbConfig.MAX_PARAMETER_KEY_SIZE)) {
                    dbEnv.throwValidationError(DbValidationError.PARAMETER_MAP_KEY_SIZE_EXCEEDED, "size of key", key.length());
                }
                ParameterMapErrorListener errorListener = new ParameterMapErrorListener(dbEnv, key);
                DynamoDbExpressionParser.parseAttributeNamesMapKeys(key, (ANTLRErrorListener)errorListener);
            }
        }
    }

    public static void validateLiteralParametersKeys(Set<String> keys2, DbEnv dbEnv) {
        if (keys2 != null) {
            for (String key : keys2) {
                if (key == null) {
                    dbEnv.throwValidationError(DbValidationError.PARAMETER_MAP_NULL_KEY, new Object[0]);
                }
                if (key.length() == 0) {
                    dbEnv.throwValidationError(DbValidationError.PARAMETER_MAP_EMPTY_KEY, new Object[0]);
                }
                if (key.length() > dbEnv.getConfigInt(DbConfig.MAX_PARAMETER_KEY_SIZE)) {
                    dbEnv.throwValidationError(DbValidationError.PARAMETER_MAP_KEY_SIZE_EXCEEDED, new Object[0]);
                }
                ParameterMapErrorListener errorListener = new ParameterMapErrorListener(dbEnv, key);
                DynamoDbExpressionParser.parseAttributeValuesMapKeys(key, (ANTLRErrorListener)errorListener);
            }
        }
    }
}

