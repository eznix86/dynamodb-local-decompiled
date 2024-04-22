/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PartiQLStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.StatementProcessor;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import java.util.List;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.NAry;

public class ExecuteStatementFunction
extends PartiQLStatementFunction<ExecuteStatementRequest, ExecuteStatementResult> {
    public ExecuteStatementFunction(LocalDBAccess dbAccess, PartiQLDbEnv partiQLDbEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory, TransactionsEnabledMode transactionsEnabledMode) {
        super(dbAccess, partiQLDbEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, transactionsEnabledMode);
    }

    @Override
    public ExecuteStatementResult apply(ExecuteStatementRequest input) {
        ParsedPartiQLRequest<DataManipulation> parsedRequest = this.parse(input);
        DataManipulation parsedExprNode = parsedRequest.getExprNode();
        Class<?> opClass = parsedExprNode instanceof DataManipulation ? parsedExprNode.getDmlOperations().getOps().get(0).getClass() : parsedExprNode.getClass();
        return ((StatementProcessor)this.statementProcessors.get(opClass)).execute(parsedRequest);
    }

    public ParsedPartiQLRequest<DataManipulation> parse(ExecuteStatementRequest request) {
        String statement = request.getStatement();
        if (request.getLimit() != null) {
            LocalDBValidatorUtils.validateLimitValueExecuteStatement(request.getLimit(), this.awsExceptionFactory);
        }
        if (statement.getBytes().length > 8192) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Member must have length less than or equal to 8192");
        }
        String returnValuesOnConditionCheckFailure = LocalDBValidatorUtils.validateReturnValuesOnConditionCheckFailure(request.getReturnValuesOnConditionCheckFailure());
        ExprNode parsedExprNode = this.parseStatement(statement);
        if (parsedExprNode instanceof NAry) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "EXISTS can only be used in ExecuteTransaction write requests.");
        }
        Class<?> opClass = parsedExprNode.getClass();
        if (parsedExprNode instanceof DataManipulation) {
            List<DataManipulationOperation> ops = ((DataManipulation)parsedExprNode).getDmlOperations().getOps();
            opClass = ops.get(0).getClass();
        }
        this.verifySupportedOperation(opClass);
        ParsedPartiQLRequest parsedRequest = ParsedPartiQLRequest.builder().exprNode(parsedExprNode).parameters(this.inputConverter.externalToInternalAttributeValues(request.getParameters())).isConsistentRead(request.isConsistentRead() != null ? request.isConsistentRead() : false).continuationToken(request.getNextToken()).returnValuesOnConditionCheckFailure(returnValuesOnConditionCheckFailure).limit(request.getLimit()).build();
        return parsedRequest;
    }
}

