/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ExecuteTransactionRequest
 *  com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult
 *  com.amazonaws.services.dynamodbv2.model.ParameterizedStatement
 *  com.amazonaws.util.StringUtils
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PartiQLStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.TransactionGetProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.TransactionProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.TransactionWriteProcessor;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionRequest;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult;
import com.amazonaws.services.dynamodbv2.model.ParameterizedStatement;
import com.amazonaws.util.StringUtils;
import com.google.common.annotations.VisibleForTesting;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.Select;
import org.partiql.lang.ast.VariableReference;

public class ExecuteTransactionFunction
extends PartiQLStatementFunction<ExecuteTransactionRequest, ExecuteTransactionResult> {
    public static final String TOO_LARGE_STATEMENT_LENGTH_ERR_MSG = "Member must have length less than or equal to 8192";
    public static final String PARTIQL_TRANSACT_EMPTY_STATEMENT = "ExecutePartiQLTransaction can not have empty statement.";
    public static final String PARTIQL_TRANSACT_REQUEST_MIXED_OPERATION = "ExecuteTransaction API does not support both read and write operations in the same request.";
    protected final Map<TransactionType, TransactionProcessor> transactionProcessors = this.populateTransactionProcessors();

    public ExecuteTransactionFunction(LocalDBAccess dbAccess, PartiQLDbEnv partiQLDbEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory) {
        super(dbAccess, partiQLDbEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, TransactionsEnabledMode.TRANSACTIONS_ENABLED);
    }

    @Override
    public ExecuteTransactionResult apply(ExecuteTransactionRequest input) {
        AbstractMap.SimpleEntry<TransactionType, List<ParsedPartiQLRequest<DataManipulation>>> parseResult = this.parse(input);
        TransactionType transactionType = parseResult.getKey();
        List<ParsedPartiQLRequest<DataManipulation>> parsedPartiQLRequests = parseResult.getValue();
        return this.transactionProcessors.get((Object)transactionType).execute(input.getClientRequestToken(), parsedPartiQLRequests);
    }

    @VisibleForTesting
    public AbstractMap.SimpleEntry<TransactionType, List<ParsedPartiQLRequest<DataManipulation>>> parse(ExecuteTransactionRequest request) {
        ArrayList<ParsedPartiQLRequest> parsedPartiQLRequests = new ArrayList<ParsedPartiQLRequest>();
        if (request.getTransactStatements() == null || request.getTransactStatements().isEmpty()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, PARTIQL_TRANSACT_EMPTY_STATEMENT);
        }
        if (request.getTransactStatements().size() > 100) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_TOO_MANY_REQUESTS.getMessage());
        }
        int index = 0;
        boolean isTransactGet = false;
        for (ParameterizedStatement transactionStatement : request.getTransactStatements()) {
            if (transactionStatement == null || StringUtils.isNullOrEmpty((String)transactionStatement.getStatement())) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, PARTIQL_TRANSACT_EMPTY_STATEMENT);
            }
            String statement = transactionStatement.getStatement();
            if (statement.getBytes().length > 8192) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, TOO_LARGE_STATEMENT_LENGTH_ERR_MSG);
            }
            ExprNode parsedExprNode = this.parseStatement(statement);
            Class<?> opClass = null;
            boolean isExistsFunction = false;
            if (parsedExprNode instanceof NAry) {
                List<ExprNode> args2 = ((NAry)parsedExprNode).getArgs();
                if (((NAry)parsedExprNode).getOp() != NAryOp.CALL || args2.size() != 2 || args2.get(0).getClass() != VariableReference.class || !"exists".equals(((VariableReference)args2.get(0)).getId()) || args2.get(1).getClass() != Select.class) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Unsupported operation in transaction.");
                }
                isExistsFunction = true;
            } else {
                opClass = parsedExprNode.getClass();
                if (parsedExprNode instanceof DataManipulation) {
                    List<DataManipulationOperation> ops = ((DataManipulation)parsedExprNode).getDmlOperations().getOps();
                    opClass = ops.get(0).getClass();
                }
                this.verifySupportedOperation(opClass);
            }
            if (index == 0) {
                isTransactGet = !isExistsFunction && opClass.equals(Select.class);
            } else if (isTransactGet && (isExistsFunction || !opClass.equals(Select.class)) || !isTransactGet && !isExistsFunction && opClass.equals(Select.class)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, PARTIQL_TRANSACT_REQUEST_MIXED_OPERATION);
            }
            ++index;
            ParsedPartiQLRequest parsedRequest = ParsedPartiQLRequest.builder().exprNode(parsedExprNode).parameters(this.inputConverter.externalToInternalAttributeValues(transactionStatement.getParameters())).returnValuesOnConditionCheckFailure(transactionStatement.getReturnValuesOnConditionCheckFailure()).continuationToken(request.getClientRequestToken()).build();
            parsedPartiQLRequests.add(parsedRequest);
        }
        TransactionType transactionType = isTransactGet ? TransactionType.TRANSACTION_GET : TransactionType.TRANSACTION_WRITE;
        return new AbstractMap.SimpleEntry<TransactionType, List<ParsedPartiQLRequest<DataManipulation>>>(transactionType, parsedPartiQLRequests);
    }

    private Map<TransactionType, TransactionProcessor> populateTransactionProcessors() {
        HashMap<TransactionType, TransactionProcessor> transactionProcessors = new HashMap<TransactionType, TransactionProcessor>();
        TransactionGetProcessor transactionGetProcessor = new TransactionGetProcessor(this.dbAccess, (LocalPartiQLDbEnv)this.localDBEnv, this, this.documentFactory);
        TransactionWriteProcessor transactionWriteProcessor = new TransactionWriteProcessor(this.dbAccess, (LocalPartiQLDbEnv)this.localDBEnv, this, this.documentFactory);
        transactionProcessors.put(TransactionType.TRANSACTION_GET, transactionGetProcessor);
        transactionProcessors.put(TransactionType.TRANSACTION_WRITE, transactionWriteProcessor);
        return transactionProcessors;
    }

    public static enum TransactionType {
        TRANSACTION_GET,
        TRANSACTION_WRITE;

    }
}

