/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.util;

import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.DynamoDBLocalOperatorMappingsBase;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import ddb.partiql.shared.dbenv.DataAccessModelFactory;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.util.KeyAndConditionExpressionExtractorBase;
import java.util.List;

public class KeyAndConditionExpressionExtractor
extends KeyAndConditionExpressionExtractorBase<String, AttributeValue, Condition, ComparisonOperator, TableInfo> {
    public KeyAndConditionExpressionExtractor(PartiQLDbEnv dbEnv, DataAccessModelFactory dataAccessModelFactory) {
        super(dbEnv, dataAccessModelFactory, new DynamoDBLocalOperatorMappingsBase((LocalPartiQLDbEnv)dbEnv));
    }

    @Override
    protected List<String> getKeyAttributeNames(TableInfo schema) {
        return schema.getBaseTableKeyNames();
    }

    @Override
    protected Condition makeCondition(Operator operator, List<AttributeValue> attributeValues) {
        return new Condition().withAttributeValueList(attributeValues).withComparisonOperator(operator.toString());
    }

    @Override
    protected Condition makeCondition(ComparisonOperator conditionalOperator, List<AttributeValue> attributeValues) {
        return new Condition().withAttributeValueList(attributeValues).withComparisonOperator(conditionalOperator);
    }
}

