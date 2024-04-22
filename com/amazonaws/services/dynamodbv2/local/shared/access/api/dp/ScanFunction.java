/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.ConditionalOperator
 *  com.amazonaws.services.dynamodbv2.model.Projection
 *  com.amazonaws.services.dynamodbv2.model.ScanRequest
 *  com.amazonaws.services.dynamodbv2.model.ScanResult
 *  com.amazonaws.services.dynamodbv2.model.Select
 *  com.amazonaws.util.CollectionUtils
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.QueryResultInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PaginatingFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.ConsumedCapacityUtils;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.validate.RangeQueryExpressionsWrapper;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.Select;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import com.amazonaws.util.CollectionUtils;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class ScanFunction
extends PaginatingFunction<ScanRequest, ScanResult> {
    public ScanFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory) {
        super(dbAccess, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory);
    }

    private static byte[] getSegmentBeginningHashKey(int totalSegments, int segment) {
        BigInteger delta2 = LocalDBUtils.MAX_HASH_KEY.divide(BigInteger.valueOf(totalSegments));
        delta2 = delta2.multiply(BigInteger.valueOf(segment));
        return ScanFunction.bigIntegerToSHA1Bytes(delta2);
    }

    private static byte[] getSegmentEndHashKey(int totalSegments, int segment) {
        if (segment + 1 == totalSegments) {
            return ScanFunction.bigIntegerToSHA1Bytes(LocalDBUtils.MAX_HASH_KEY);
        }
        BigInteger delta2 = LocalDBUtils.MAX_HASH_KEY.divide(BigInteger.valueOf(totalSegments));
        delta2 = delta2.multiply(BigInteger.valueOf(segment + 1));
        return ScanFunction.bigIntegerToSHA1Bytes(delta2.subtract(BigInteger.valueOf(1L)));
    }

    private static byte[] bigIntegerToSHA1Bytes(BigInteger number) {
        byte[] numberBytes = number.toByteArray();
        if (numberBytes.length == 20) {
            return numberBytes;
        }
        byte[] bytes = new byte[20];
        int i = bytes.length - 1;
        for (int j = i + numberBytes.length - bytes.length; i >= 0 && j >= 0; --i, --j) {
            bytes[i] = numberBytes[j];
        }
        while (i >= 0) {
            bytes[i] = 0;
            --i;
        }
        return bytes;
    }

    @Override
    public ScanResult apply(ScanRequest scanRequest) {
        boolean isGsiIndex;
        String tableName = scanRequest.getTableName();
        this.validateTableName(tableName);
        TableInfo info = this.validateTableExists(tableName);
        String indexName = scanRequest.getIndexName();
        if (indexName != null && !info.hasIndex(indexName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.SECONDARY_INDEXES_NOT_FOUND.getMessage(), indexName));
        }
        boolean bl = isGsiIndex = indexName != null && info.isGSIIndex(indexName);
        if (indexName != null && info.isGSIIndex(indexName) && scanRequest.getConsistentRead() != null && scanRequest.getConsistentRead().booleanValue()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.CONSISTENT_GSI_SCAN.getMessage());
        }
        LocalDBValidatorUtils.validateExpressions(scanRequest, this.inputConverter);
        HashMap<String, Condition> scanFilter = this.inputConverter.externalToInternalConditions(scanRequest.getScanFilter());
        scanFilter = scanFilter == null ? new HashMap<String, Condition>() : scanFilter;
        this.validateConditions(scanFilter, scanRequest.getConditionalOperator());
        long limit2 = this.validateLimitValue(scanRequest.getLimit());
        RangeQueryExpressionsWrapper rangeQueryExpressionsWrapper = this.inputConverter.externalToInternalExpressions(scanRequest.getFilterExpression(), scanRequest.getProjectionExpression(), null, scanRequest.getExpressionAttributeNames(), scanRequest.getExpressionAttributeValues());
        ExpressionWrapper filterExpressionWrapper = rangeQueryExpressionsWrapper == null ? null : rangeQueryExpressionsWrapper.getFilterExpressionWrapper();
        Expression filterExpression = filterExpressionWrapper == null ? null : filterExpressionWrapper.getExpression();
        ProjectionExpressionWrapper projectionExpressionWrapper = rangeQueryExpressionsWrapper == null ? null : rangeQueryExpressionsWrapper.getProjectionExpressionWrapper();
        ProjectionExpression projectionExpression = projectionExpressionWrapper == null ? null : projectionExpressionWrapper.getProjection();
        Map exclusiveStartKey = null;
        if (scanRequest.getExclusiveStartKey() != null) {
            exclusiveStartKey = (Map)this.inputConverter.externalToInternalAttributes(scanRequest.getExclusiveStartKey());
        }
        List keyDefs = this.getKeyAttributes(info, indexName);
        this.validateExclusiveStartKey(exclusiveStartKey, keyDefs);
        this.validateExclusiveStartKeyForEmptyAttributeValue(exclusiveStartKey, info, indexName, isGsiIndex);
        Select select = this.validateSelect(scanRequest.getSelect(), scanRequest.getAttributesToGet(), projectionExpression, indexName, info);
        String projectionExpressionString = scanRequest.getProjectionExpression();
        List<String> attributesToGet = this.determineAttributesToGetForScan(scanRequest, info, indexName, select);
        byte[] beginHash = null;
        byte[] endHash = null;
        if (scanRequest.getSegment() != null | scanRequest.getTotalSegments() != null) {
            this.validateParallelScanRequest(scanRequest.getSegment(), scanRequest.getTotalSegments());
            beginHash = ScanFunction.getSegmentBeginningHashKey(scanRequest.getTotalSegments(), scanRequest.getSegment());
            endHash = ScanFunction.getSegmentEndHashKey(scanRequest.getTotalSegments(), scanRequest.getSegment());
            this.validateParallelScanExclusiveStartKey(beginHash, endHash, exclusiveStartKey, info.getHashKey());
        }
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(info, filterExpressionWrapper, this.awsExceptionFactory);
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(info, projectionExpressionWrapper, this.awsExceptionFactory);
        if (!(select == Select.COUNT || StringUtils.isEmpty(projectionExpressionString) && CollectionUtils.isNullOrEmpty((Collection)scanRequest.getAttributesToGet()))) {
            this.validateAttributesToGetAndProjExpr((List)attributesToGet, projectionExpression, indexName, info);
        }
        QueryResultInfo results = this.dbAccess.queryRecords(tableName, indexName, null, exclusiveStartKey, limit2, true, beginHash, endHash, true, isGsiIndex);
        ScanResult scanResult = new ScanResult();
        int scannedItemCount = 0;
        long totalSize = 0L;
        List<Map<String, AttributeValue>> dbRecords = results.getReturnedRecords();
        ConditionalOperator conditionalOperator = this.conditionalOperatorFrom(scanRequest.getConditionalOperator());
        ArrayList<Map<String, AttributeValue>> dbRecordsAfterFiltering = new ArrayList<Map<String, AttributeValue>>();
        ArrayList<Map<String, AttributeValue>> chargeableDbRecords = new ArrayList<Map<String, AttributeValue>>();
        for (Map<String, AttributeValue> item : dbRecords) {
            if (this.doesItemMatchConditionalOperator(item, scanFilter, conditionalOperator) && this.doesItemMatchFilterExpression((Map)item, filterExpression)) {
                if (select != Select.COUNT) {
                    Map<String, AttributeValue> filteredItem = null;
                    filteredItem = projectionExpressionString == null && attributesToGet != null ? LocalDBUtils.projectAttributes(item, attributesToGet) : (projectionExpressionString != null ? LocalDBUtils.projectAttributes(item, projectionExpression) : item);
                    if (filteredItem != null) {
                        dbRecordsAfterFiltering.add(filteredItem);
                    }
                } else {
                    dbRecordsAfterFiltering.add(item);
                }
            }
            chargeableDbRecords.add(item);
            long itemSize = LocalDBUtils.getItemSizeBytes(item);
            if (totalSize + itemSize >= 0x100000L) {
                ++scannedItemCount;
                break;
            }
            totalSize += itemSize;
            ++scannedItemCount;
        }
        if (select != Select.COUNT) {
            scanResult.setItems(this.localDBOutputConverter.internalToExternalItemList(dbRecordsAfterFiltering));
        }
        scanResult.setCount(Integer.valueOf(dbRecordsAfterFiltering.size()));
        scanResult.setScannedCount(Integer.valueOf(scannedItemCount));
        Map<String, AttributeValue> lastItem = scannedItemCount > 0 && scannedItemCount < results.getReturnedRecords().size() ? results.getReturnedRecords().get(scannedItemCount - 1) : results.getLastEvaluatedItem();
        Map<String, AttributeValue> lastKey = LocalDBUtils.projectAttributes(lastItem, this.getAttributeNames(keyDefs));
        scanResult.setLastEvaluatedKey(this.localDBOutputConverter.internalToExternalAttributes(lastKey));
        scanResult.withConsumedCapacity(ConsumedCapacityUtils.computeConsumedCapacity(chargeableDbRecords, isGsiIndex, !isGsiIndex && indexName != null, tableName, indexName, false, scanRequest.getConsistentRead() != null && scanRequest.getConsistentRead() != false, this.transactionsMode, this.convertReturnConsumedCapacity(scanRequest.getReturnConsumedCapacity())));
        return scanResult;
    }

    private void validateParallelScanExclusiveStartKey(byte[] beginHash, byte[] endHash, Map<String, AttributeValue> exclusiveStartKey, AttributeDefinition hashKeyDef) {
        if (exclusiveStartKey == null) {
            return;
        }
        byte[] exclusiveStartHashVal = LocalDBUtils.getHashValue(exclusiveStartKey.get(hashKeyDef.getAttributeName()));
        if (LocalDBUtils.compareUnsignedByteArrays(exclusiveStartHashVal, beginHash) < 0 || LocalDBUtils.compareUnsignedByteArrays(exclusiveStartHashVal, endHash) > 0) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_EXCLUSIVE_START_KEY_FOR_SCAN.getMessage());
        }
    }

    private void validateParallelScanRequest(Integer segment, Integer totalSegments) {
        if (segment == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.SEGMENT_NOT_SET.getMessage());
        }
        if (totalSegments == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOTAL_SEGMENTS_NOT_SET.getMessage());
        }
        if (segment < 0 || segment >= totalSegments) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + segment + "' at 'segment' failed to satisfy constraint: Member must have value less than or equal to " + (totalSegments - 1));
        }
        if (totalSegments <= 0 || totalSegments > 1000000) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + totalSegments + "' at 'totalSegments' failed to satisfy constraint: Member must have value less than or equal to 1000000");
        }
    }

    private List<String> determineAttributesToGetForScan(ScanRequest scanRequest, TableInfo tableInfo, String indexName, Select select) {
        switch (select) {
            case SPECIFIC_ATTRIBUTES: {
                return scanRequest.getAttributesToGet();
            }
            case ALL_PROJECTED_ATTRIBUTES: {
                Projection indexProjection = tableInfo.getProjection(indexName);
                return this.determineAttributesToGetWhenSelectingAllProjectedAttributes(tableInfo, indexName, indexProjection.getProjectionType(), indexProjection.getNonKeyAttributes());
            }
            case ALL_ATTRIBUTES: {
                return null;
            }
            case COUNT: {
                return null;
            }
        }
        LocalDBUtils.ldClientFail(LocalDBClientExceptionType.UNREACHABLE_CODE);
        return null;
    }
}

