/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.util;

import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBComparisonOperator;
import ddb.partiql.shared.util.OperatorMappingsBase;

public class OperatorMappings
extends OperatorMappingsBase<LocalDBComparisonOperator> {
    @Override
    public LocalDBComparisonOperator getComparisonOperator(Operator operator) {
        switch (operator) {
            case EQ: {
                return LocalDBComparisonOperator.EQ;
            }
            case LE: {
                return LocalDBComparisonOperator.LE;
            }
            case LT: {
                return LocalDBComparisonOperator.LT;
            }
            case GT: {
                return LocalDBComparisonOperator.GT;
            }
            case GE: {
                return LocalDBComparisonOperator.GE;
            }
            case BETWEEN: {
                return LocalDBComparisonOperator.BETWEEN;
            }
            case begins_with: {
                return LocalDBComparisonOperator.BEGINS_WITH;
            }
            case IN: {
                return LocalDBComparisonOperator.IN;
            }
            case NE: {
                return LocalDBComparisonOperator.NE;
            }
            case contains: {
                return LocalDBComparisonOperator.CONTAINS;
            }
            case NOT: 
            case attribute_exists: 
            case attribute_not_exists: 
            case attribute_type: 
            case size: {
                return null;
            }
        }
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, String.format("%s: %s %s", new Object[]{"OperatorMappings", "Unknown Operator: ", operator}));
    }
}

