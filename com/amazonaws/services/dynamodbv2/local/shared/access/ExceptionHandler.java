/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.ExpressionExecutionException;
import com.amazonaws.services.dynamodbv2.local.shared.validate.ErrorFactory;

public class ExceptionHandler {
    public static void processExecutionExceptions(ExpressionExecutionContext expressionExecutionContext, ExpressionExecutionException eee, AWSExceptionFactory errorFactory) {
        switch (expressionExecutionContext) {
            case EXPECTED_EXPRESSION: {
                ExceptionHandler.convertExpectedExpressionContext(eee, errorFactory);
                break;
            }
            case UPDATE_EXPRESSION: {
                ExceptionHandler.convertUpdateExpressionContext(eee, errorFactory);
            }
        }
    }

    private static void convertExpectedExpressionContext(ExpressionExecutionException eee, ErrorFactory errorFactory) {
        switch (eee.getErrorCode()) {
            case DOCUMENT_TOO_DEEP: 
            case DOC_PATH_NOT_VALID_FOR_UPDATE: {
                errorFactory.INTERNAL_FAILURE.throwAsException();
            }
        }
        errorFactory.CONDITIONAL_CHECK_FAILED.throwAsException();
    }

    private static void convertUpdateExpressionContext(ExpressionExecutionException eee, ErrorFactory errorFactory) {
        switch (eee.getErrorCode()) {
            case DOCUMENT_TOO_DEEP: {
                errorFactory.ITEM_NESTING_LEVELS_LIMIT_EXCEEDED.throwAsException();
                break;
            }
            case DOC_PATH_NOT_VALID_FOR_UPDATE: {
                errorFactory.DOCUMENT_PATH_INVALID_FOR_UPDATE.throwAsException();
                break;
            }
            case OPERAND_TYPE_INVALID: 
            case OPERAND_TYPE_MISMATCH: {
                errorFactory.UPDATE_EXPRESSION_TYPE_MISMATCH.throwAsException();
                break;
            }
            case ATTRIBUTE_NOT_FOUND: {
                errorFactory.ATTRIBUTE_NOT_FOUND_IN_ITEM.throwAsException();
                break;
            }
            default: {
                errorFactory.INTERNAL_FAILURE.throwAsException();
            }
        }
    }

    public static void processFilterExpressionExecutionException(ExpressionExecutionException eee, ErrorFactory errorFactory) {
        switch (eee.getErrorCode()) {
            case DOCUMENT_TOO_DEEP: 
            case DOC_PATH_NOT_VALID_FOR_UPDATE: {
                errorFactory.INTERNAL_FAILURE.throwAsException();
            }
            case OPERAND_TYPE_INVALID: 
            case OPERAND_TYPE_MISMATCH: {
                errorFactory.INTERNAL_FAILURE.throwAsException();
            }
            case ATTRIBUTE_NOT_FOUND: {
                errorFactory.INTERNAL_FAILURE.throwAsException();
                break;
            }
            default: {
                errorFactory.INTERNAL_FAILURE.throwAsException();
            }
        }
    }

    public static enum ExpressionExecutionContext {
        EXPECTED_EXPRESSION,
        UPDATE_EXPRESSION;

    }
}

