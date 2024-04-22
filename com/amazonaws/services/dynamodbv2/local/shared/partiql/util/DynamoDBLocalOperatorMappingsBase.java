/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.util;

import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import ddb.partiql.shared.util.OperatorMappingsBase;

public class DynamoDBLocalOperatorMappingsBase
extends OperatorMappingsBase<ComparisonOperator> {
    protected final LocalPartiQLDbEnv localPartiQLDbEnv;

    public DynamoDBLocalOperatorMappingsBase(LocalPartiQLDbEnv localPartiQLDbEnv) {
        this.localPartiQLDbEnv = localPartiQLDbEnv;
    }

    @Override
    public ComparisonOperator getComparisonOperator(Operator operator) {
        switch (operator) {
            case EQ: {
                return ComparisonOperator.EQ;
            }
            case LE: {
                return ComparisonOperator.LE;
            }
            case LT: {
                return ComparisonOperator.LT;
            }
            case GT: {
                return ComparisonOperator.GT;
            }
            case GE: {
                return ComparisonOperator.GE;
            }
            case BETWEEN: {
                return ComparisonOperator.BETWEEN;
            }
            case begins_with: {
                return ComparisonOperator.BEGINS_WITH;
            }
            case IN: 
            case NE: 
            case NOT: 
            case attribute_exists: 
            case attribute_not_exists: 
            case attribute_type: 
            case contains: 
            case size: {
                return null;
            }
        }
        this.localPartiQLDbEnv.dbPqlAssert(false, "OperatorMappings", "Unknown Operator: ", (Object)operator);
        return null;
    }
}

