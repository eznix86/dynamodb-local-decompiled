/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.BatchStatementRequest
 *  com.amazonaws.util.StringUtils
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PartiQLStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.BatchGetProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.BatchProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.BatchWriteProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.ObjectExceptionPair;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.BatchStatementRequest;
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
import org.partiql.lang.ast.Select;

public class BatchExecuteStatementFunction
extends PartiQLStatementFunction<BatchExecuteStatementRequest, BatchExecuteStatementResult> {
    public static final String PARTIQL_BATCH_EMPTY_STATEMENT = "The statement was empty.";
    public static final String PARTIQL_BATCH_MIXED_READ_WRITE = "Read and write requests together in the same batch is not supported.";
    public static final String PARTIQL_BATCH_GET_INVALID_SELECT = "Only single item select is supported";
    public static final String PARTIQL_BATCH_INVALID_OPERATION = "Invalid operation. Only select, insert, update and delete operations are valid";
    public static final String EXISTS_UNSUPPORTED_ERR_MSG = "EXISTS can only be used in ExecuteTransaction write requests.";
    public static final String BATCH_EXECUTE_GET_TOO_MANY_REQUESTS = "BatchGetItem cannot fetch more than 25 items";
    protected final Map<BatchType, BatchProcessor> batchProcessors = this.populateBatchProcessors();

    public BatchExecuteStatementFunction(LocalDBAccess dbAccess, PartiQLDbEnv partiQLDbEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory, TransactionsEnabledMode transactionsEnabledMode) {
        super(dbAccess, partiQLDbEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, transactionsEnabledMode);
    }

    @Override
    public BatchExecuteStatementResult apply(BatchExecuteStatementRequest input) {
        AbstractMap.SimpleEntry<BatchType, List<ObjectExceptionPair<ParsedPartiQLRequest>>> parseResult = this.parse(input);
        BatchType batchType = parseResult.getKey();
        List<ObjectExceptionPair<ParsedPartiQLRequest>> parsedPartiQLRequestExceptionPairs = parseResult.getValue();
        return this.batchProcessors.get((Object)batchType).execute(parsedPartiQLRequestExceptionPairs);
    }

    @VisibleForTesting
    public AbstractMap.SimpleEntry<BatchType, List<ObjectExceptionPair<ParsedPartiQLRequest>>> parse(BatchExecuteStatementRequest request) {
        BatchType batchType;
        ArrayList<ObjectExceptionPair<Object>> parsedPartiQLRequestExceptionPairs = new ArrayList<ObjectExceptionPair<Object>>();
        List batchStatementRequests = request.getStatements();
        if (batchStatementRequests == null || batchStatementRequests.isEmpty()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, PARTIQL_BATCH_EMPTY_STATEMENT);
        }
        int index = 0;
        boolean isBatchGet = false;
        for (BatchStatementRequest batchStatementRequest : batchStatementRequests) {
            Class<?> opClass;
            ExprNode parsedExprNode;
            if (batchStatementRequest == null || StringUtils.isNullOrEmpty((String)batchStatementRequest.getStatement())) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, PARTIQL_BATCH_EMPTY_STATEMENT);
            }
            String statement = batchStatementRequest.getStatement();
            if (statement.getBytes().length > 8192) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Member must have length less than or equal to 8192");
            }
            try {
                parsedExprNode = this.parseStatement(statement);
                opClass = parsedExprNode.getClass();
                if (parsedExprNode instanceof NAry) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, EXISTS_UNSUPPORTED_ERR_MSG);
                }
                if (parsedExprNode instanceof DataManipulation) {
                    List<DataManipulationOperation> ops = ((DataManipulation)parsedExprNode).getDmlOperations().getOps();
                    opClass = ops.get(0).getClass();
                }
                if (!this.statementProcessors.containsKey(opClass)) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, PARTIQL_BATCH_INVALID_OPERATION);
                }
            } catch (DynamoDBLocalServiceException e) {
                parsedPartiQLRequestExceptionPairs.add(new ObjectExceptionPair<Object>(null, e));
                continue;
            }
            if (index == 0) {
                isBatchGet = opClass.equals(Select.class);
            } else if (isBatchGet && !opClass.equals(Select.class) || !isBatchGet && opClass.equals(Select.class)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, PARTIQL_BATCH_MIXED_READ_WRITE);
            }
            ++index;
            ParsedPartiQLRequest parsedRequest = ParsedPartiQLRequest.builder().exprNode(parsedExprNode).parameters(this.inputConverter.externalToInternalAttributeValues(batchStatementRequest.getParameters())).isConsistentRead(batchStatementRequest.isConsistentRead() != null && batchStatementRequest.isConsistentRead() != false).build();
            parsedPartiQLRequestExceptionPairs.add(new ObjectExceptionPair<ParsedPartiQLRequest>(parsedRequest, null));
        }
        if (isBatchGet) {
            if (batchStatementRequests.size() > 25) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, BATCH_EXECUTE_GET_TOO_MANY_REQUESTS);
            }
            batchType = BatchType.BATCH_GET;
        } else {
            if (batchStatementRequests.size() > 25) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BATCH_WRITE_TOO_MANY_REQUESTS.getMessage());
            }
            batchType = BatchType.BATCH_WRITE;
        }
        return new AbstractMap.SimpleEntry<BatchType, List<ObjectExceptionPair<ParsedPartiQLRequest>>>(batchType, parsedPartiQLRequestExceptionPairs);
    }

    private Map<BatchType, BatchProcessor> populateBatchProcessors() {
        HashMap<BatchType, BatchProcessor> batchProcessors = new HashMap<BatchType, BatchProcessor>();
        BatchGetProcessor batchGetProcessor = new BatchGetProcessor(this.dbAccess, (LocalPartiQLDbEnv)this.localDBEnv, this, this.documentFactory);
        BatchWriteProcessor batchWriteProcessor = new BatchWriteProcessor(this.dbAccess, (LocalPartiQLDbEnv)this.localDBEnv, this, this.documentFactory);
        batchProcessors.put(BatchType.BATCH_GET, batchGetProcessor);
        batchProcessors.put(BatchType.BATCH_WRITE, batchWriteProcessor);
        return batchProcessors;
    }

    public static enum BatchType {
        BATCH_GET,
        BATCH_WRITE;

    }
}

