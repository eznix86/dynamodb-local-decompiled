/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.processor;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PartiQLStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.PartiQLProcessor;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import org.partiql.lang.ast.ExprNode;

public abstract class StatementProcessor<T extends ExprNode>
extends PartiQLProcessor {
    protected StatementProcessor(LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, PartiQLStatementFunction partiQLStatementFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, partiQLStatementFunction, documentFactory);
    }

    public abstract ExecuteStatementResult execute(ParsedPartiQLRequest<T> var1);

    ReturnValuesOnConditionCheckFailure getReturnValuesOnConditionCheckFailure(ReturnValuesOnConditionCheckFailure returnValuesOnConditionCheckFailure) {
        if (returnValuesOnConditionCheckFailure == null) {
            returnValuesOnConditionCheckFailure = ReturnValuesOnConditionCheckFailure.NONE;
        }
        return ReturnValuesOnConditionCheckFailure.fromValue((String)returnValuesOnConditionCheckFailure.toString());
    }
}

