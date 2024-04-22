/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchGetItemResult
 *  com.amazonaws.services.dynamodbv2.model.BatchStatementError
 *  com.amazonaws.services.dynamodbv2.model.BatchStatementRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchStatementResponse
 *  com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult
 *  com.amazonaws.services.dynamodbv2.model.BillingModeSummary
 *  com.amazonaws.services.dynamodbv2.model.CancellationReason
 *  com.amazonaws.services.dynamodbv2.model.Capacity
 *  com.amazonaws.services.dynamodbv2.model.Condition
 *  com.amazonaws.services.dynamodbv2.model.ConditionCheck
 *  com.amazonaws.services.dynamodbv2.model.ConditionalOperator
 *  com.amazonaws.services.dynamodbv2.model.ConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.CreateGlobalSecondaryIndexAction
 *  com.amazonaws.services.dynamodbv2.model.CreateTableRequest
 *  com.amazonaws.services.dynamodbv2.model.CreateTableResult
 *  com.amazonaws.services.dynamodbv2.model.Delete
 *  com.amazonaws.services.dynamodbv2.model.DeleteGlobalSecondaryIndexAction
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemResult
 *  com.amazonaws.services.dynamodbv2.model.DeleteRequest
 *  com.amazonaws.services.dynamodbv2.model.DeleteTableRequest
 *  com.amazonaws.services.dynamodbv2.model.DeleteTableResult
 *  com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeLimitsResult
 *  com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeStreamResult
 *  com.amazonaws.services.dynamodbv2.model.DescribeTableRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeTableResult
 *  com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveResult
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.ExecuteTransactionRequest
 *  com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult
 *  com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue
 *  com.amazonaws.services.dynamodbv2.model.Get
 *  com.amazonaws.services.dynamodbv2.model.GetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.GetItemResult
 *  com.amazonaws.services.dynamodbv2.model.GetRecordsRequest
 *  com.amazonaws.services.dynamodbv2.model.GetRecordsResult
 *  com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest
 *  com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexUpdate
 *  com.amazonaws.services.dynamodbv2.model.Identity
 *  com.amazonaws.services.dynamodbv2.model.ItemCollectionMetrics
 *  com.amazonaws.services.dynamodbv2.model.ItemResponse
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.KeysAndAttributes
 *  com.amazonaws.services.dynamodbv2.model.ListStreamsRequest
 *  com.amazonaws.services.dynamodbv2.model.ListStreamsResult
 *  com.amazonaws.services.dynamodbv2.model.ListTablesRequest
 *  com.amazonaws.services.dynamodbv2.model.ListTablesResult
 *  com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceRequest
 *  com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceResult
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.ParameterizedStatement
 *  com.amazonaws.services.dynamodbv2.model.Projection
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription
 *  com.amazonaws.services.dynamodbv2.model.Put
 *  com.amazonaws.services.dynamodbv2.model.PutItemRequest
 *  com.amazonaws.services.dynamodbv2.model.PutItemResult
 *  com.amazonaws.services.dynamodbv2.model.PutRequest
 *  com.amazonaws.services.dynamodbv2.model.QueryRequest
 *  com.amazonaws.services.dynamodbv2.model.QueryResult
 *  com.amazonaws.services.dynamodbv2.model.Record
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 *  com.amazonaws.services.dynamodbv2.model.ScanRequest
 *  com.amazonaws.services.dynamodbv2.model.ScanResult
 *  com.amazonaws.services.dynamodbv2.model.SequenceNumberRange
 *  com.amazonaws.services.dynamodbv2.model.Shard
 *  com.amazonaws.services.dynamodbv2.model.Stream
 *  com.amazonaws.services.dynamodbv2.model.StreamDescription
 *  com.amazonaws.services.dynamodbv2.model.StreamRecord
 *  com.amazonaws.services.dynamodbv2.model.StreamSpecification
 *  com.amazonaws.services.dynamodbv2.model.StreamViewType
 *  com.amazonaws.services.dynamodbv2.model.TableDescription
 *  com.amazonaws.services.dynamodbv2.model.Tag
 *  com.amazonaws.services.dynamodbv2.model.TagResourceRequest
 *  com.amazonaws.services.dynamodbv2.model.TagResourceResult
 *  com.amazonaws.services.dynamodbv2.model.TimeToLiveDescription
 *  com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItem
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItemsRequest
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItemsResult
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItem
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItemsResult
 *  com.amazonaws.services.dynamodbv2.model.UntagResourceRequest
 *  com.amazonaws.services.dynamodbv2.model.UntagResourceResult
 *  com.amazonaws.services.dynamodbv2.model.Update
 *  com.amazonaws.services.dynamodbv2.model.UpdateGlobalSecondaryIndexAction
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemResult
 *  com.amazonaws.services.dynamodbv2.model.UpdateTableRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateTableResult
 *  com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveResult
 *  com.amazonaws.services.dynamodbv2.model.WriteRequest
 */
package com.amazonaws.services.dynamodbv2.local.shared.mapper;

import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.local.shared.model.DeleteRequest;
import com.amazonaws.services.dynamodbv2.local.shared.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.WriteRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.BatchStatementError;
import com.amazonaws.services.dynamodbv2.model.BatchStatementRequest;
import com.amazonaws.services.dynamodbv2.model.BatchStatementResponse;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
import com.amazonaws.services.dynamodbv2.model.BillingModeSummary;
import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import com.amazonaws.services.dynamodbv2.model.Capacity;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ConditionCheck;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.CreateGlobalSecondaryIndexAction;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.Delete;
import com.amazonaws.services.dynamodbv2.model.DeleteGlobalSecondaryIndexAction;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsResult;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveResult;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionRequest;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult;
import com.amazonaws.services.dynamodbv2.model.Get;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.GetRecordsRequest;
import com.amazonaws.services.dynamodbv2.model.GetRecordsResult;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexUpdate;
import com.amazonaws.services.dynamodbv2.model.Identity;
import com.amazonaws.services.dynamodbv2.model.ItemCollectionMetrics;
import com.amazonaws.services.dynamodbv2.model.ItemResponse;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.ListStreamsRequest;
import com.amazonaws.services.dynamodbv2.model.ListStreamsResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceRequest;
import com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceResult;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.ParameterizedStatement;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import com.amazonaws.services.dynamodbv2.model.Put;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.PutRequest;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.SequenceNumberRange;
import com.amazonaws.services.dynamodbv2.model.Shard;
import com.amazonaws.services.dynamodbv2.model.Stream;
import com.amazonaws.services.dynamodbv2.model.StreamDescription;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.Tag;
import com.amazonaws.services.dynamodbv2.model.TagResourceRequest;
import com.amazonaws.services.dynamodbv2.model.TagResourceResult;
import com.amazonaws.services.dynamodbv2.model.TimeToLiveDescription;
import com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification;
import com.amazonaws.services.dynamodbv2.model.TransactGetItem;
import com.amazonaws.services.dynamodbv2.model.TransactGetItemsRequest;
import com.amazonaws.services.dynamodbv2.model.TransactGetItemsResult;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItem;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItemsResult;
import com.amazonaws.services.dynamodbv2.model.UntagResourceRequest;
import com.amazonaws.services.dynamodbv2.model.UntagResourceResult;
import com.amazonaws.services.dynamodbv2.model.Update;
import com.amazonaws.services.dynamodbv2.model.UpdateGlobalSecondaryIndexAction;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveResult;
import com.amazonaws.services.dynamodbv2.rr.ExpressionsWrapperBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DynamoDBObjectMapper
extends ObjectMapper {
    public static final TypeReference<Map<String, AttributeValue>> ITEM_TYPE = new TypeReference<Map<String, AttributeValue>>(){};
    private static final long serialVersionUID = -5398424300102369986L;
    private static final String MODULE = "custom";

    public DynamoDBObjectMapper() {
        SimpleModule module = new SimpleModule(MODULE, Version.unknownVersion());
        module.addSerializer(ByteBuffer.class, new ByteBufferSerializer());
        module.addDeserializer(ByteBuffer.class, new ByteBufferDeserializer());
        module.addSerializer(Date.class, new DateSerializer());
        module.addDeserializer(Date.class, new DateDeserializer());
        super.registerModule(module);
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.configOverride(Map.class).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.ALWAYS));
        this.addMixInAnnotations(AttributeDefinition.class, AttributeDefinitionMixIn.class);
        this.addMixInAnnotations(com.amazonaws.services.dynamodbv2.model.AttributeValue.class, AttributeValueMixIn.class);
        this.addMixInAnnotations(com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate.class, AttributeValueUpdateMixIn.class);
        this.addMixInAnnotations(BatchGetItemRequest.class, BatchGetItemRequestMixIn.class);
        this.addMixInAnnotations(BatchGetItemResult.class, BatchGetItemResultMixIn.class);
        this.addMixInAnnotations(BatchWriteItemRequest.class, BatchWriteItemRequestMixIn.class);
        this.addMixInAnnotations(BatchWriteItemResult.class, BatchWriteItemResultMixIn.class);
        this.addMixInAnnotations(CancellationReason.class, CancellationReasonMixIn.class);
        this.addMixInAnnotations(Capacity.class, CapacityMixIn.class);
        this.addMixInAnnotations(Condition.class, ConditionMixIn.class);
        this.addMixInAnnotations(ConditionCheck.class, ConditionCheckMixIn.class);
        this.addMixInAnnotations(ConsumedCapacity.class, ConsumedCapacityMixIn.class);
        this.addMixInAnnotations(CreateTableRequest.class, CreateTableRequestMixIn.class);
        this.addMixInAnnotations(CreateTableResult.class, CreateTableResultMixIn.class);
        this.addMixInAnnotations(Delete.class, DeleteMixIn.class);
        this.addMixInAnnotations(DeleteItemRequest.class, DeleteItemRequestMixIn.class);
        this.addMixInAnnotations(DeleteItemResult.class, DeleteItemResultMixIn.class);
        this.addMixInAnnotations(com.amazonaws.services.dynamodbv2.model.DeleteRequest.class, DeleteRequestMixIn.class);
        this.addMixInAnnotations(DeleteTableRequest.class, DeleteTableRequestMixIn.class);
        this.addMixInAnnotations(DeleteTableResult.class, DeleteTableResultMixIn.class);
        this.addMixInAnnotations(DescribeStreamRequest.class, DescribeStreamRequestMixIn.class);
        this.addMixInAnnotations(DescribeStreamResult.class, DescribeStreamResultMixIn.class);
        this.addMixInAnnotations(DescribeTableRequest.class, DescribeTableRequestMixIn.class);
        this.addMixInAnnotations(DescribeTableResult.class, DescribeTableResultMixIn.class);
        this.addMixInAnnotations(DescribeLimitsResult.class, DescribeLimitsResultMixIn.class);
        this.addMixInAnnotations(DescribeLimitsRequest.class, DescribeLimitsRequestMixIn.class);
        this.addMixInAnnotations(DescribeTimeToLiveRequest.class, DescribeTimeToLiveRequestMixIn.class);
        this.addMixInAnnotations(DescribeTimeToLiveResult.class, DescribeTimeToLiveResultMixIn.class);
        this.addMixInAnnotations(com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue.class, ExpectedAttributeValueMixIn.class);
        this.addMixInAnnotations(Get.class, GetMixIn.class);
        this.addMixInAnnotations(GetItemRequest.class, GetItemRequestMixIn.class);
        this.addMixInAnnotations(GetItemResult.class, GetItemResultMixIn.class);
        this.addMixInAnnotations(GetRecordsRequest.class, GetRecordsRequestMixIn.class);
        this.addMixInAnnotations(GetRecordsResult.class, GetRecordsResultMixIn.class);
        this.addMixInAnnotations(GetShardIteratorRequest.class, GetShardIteratorRequestMixIn.class);
        this.addMixInAnnotations(GetShardIteratorResult.class, GetShardIteratorResultMixIn.class);
        this.addMixInAnnotations(GlobalSecondaryIndex.class, GlobalSecondaryIndexMixIn.class);
        this.addMixInAnnotations(GlobalSecondaryIndexDescription.class, GlobalSecondaryIndexDescriptionMixIn.class);
        this.addMixInAnnotations(GlobalSecondaryIndexUpdate.class, GlobalSecondaryIndexUpdateMixIn.class);
        this.addMixInAnnotations(Identity.class, IdentityMixIn.class);
        this.addMixInAnnotations(ItemCollectionMetrics.class, ItemCollectionMetricsMixIn.class);
        this.addMixInAnnotations(ItemResponse.class, ItemResponseMixIn.class);
        this.addMixInAnnotations(KeysAndAttributes.class, KeysAndAttributesMixIn.class);
        this.addMixInAnnotations(KeySchemaElement.class, KeySchemaElementMixIn.class);
        this.addMixInAnnotations(ListStreamsRequest.class, ListStreamsRequestMixIn.class);
        this.addMixInAnnotations(ListStreamsResult.class, ListStreamsResultMixIn.class);
        this.addMixInAnnotations(ListTablesRequest.class, ListTablesRequestMixIn.class);
        this.addMixInAnnotations(ListTablesResult.class, ListTablesResultMixIn.class);
        this.addMixInAnnotations(ListTagsOfResourceRequest.class, ListTagsOfResourceRequestMixIn.class);
        this.addMixInAnnotations(ListTagsOfResourceResult.class, ListTagsOfResourceResultMixIn.class);
        this.addMixInAnnotations(LocalSecondaryIndexDescription.class, LocalSecondaryIndexDescriptionMixIn.class);
        this.addMixInAnnotations(LocalSecondaryIndex.class, LocalSecondaryIndexMixIn.class);
        this.addMixInAnnotations(Projection.class, ProjectionMixIn.class);
        this.addMixInAnnotations(ProvisionedThroughputDescription.class, ProvisionedThroughputDescriptionMixIn.class);
        this.addMixInAnnotations(ProvisionedThroughput.class, ProvisionedThroughputMixIn.class);
        this.addMixInAnnotations(Put.class, PutMixIn.class);
        this.addMixInAnnotations(PutItemRequest.class, PutItemRequestMixIn.class);
        this.addMixInAnnotations(PutItemResult.class, PutItemResultMixIn.class);
        this.addMixInAnnotations(PutRequest.class, PutRequestMixIn.class);
        this.addMixInAnnotations(QueryRequest.class, QueryRequestMixIn.class);
        this.addMixInAnnotations(QueryResult.class, QueryResultMixIn.class);
        this.addMixInAnnotations(ScanRequest.class, ScanRequestMixIn.class);
        this.addMixInAnnotations(ScanResult.class, ScanResultMixIn.class);
        this.addMixInAnnotations(SequenceNumberRange.class, SequenceNumberRangeMixIn.class);
        this.addMixInAnnotations(StreamDescription.class, StreamDescriptionMixIn.class);
        this.addMixInAnnotations(Shard.class, ShardMixIn.class);
        this.addMixInAnnotations(TableDescription.class, TableDescriptionMixIn.class);
        this.addMixInAnnotations(BillingModeSummary.class, BillingModeSummaryMixIn.class);
        this.addMixInAnnotations(UpdateGlobalSecondaryIndexAction.class, UpdateGlobalSecondaryIndexActionMixIn.class);
        this.addMixInAnnotations(CreateGlobalSecondaryIndexAction.class, CreateGlobalSecondaryIndexActionMixIn.class);
        this.addMixInAnnotations(DeleteGlobalSecondaryIndexAction.class, DeleteGlobalSecondaryIndexActionMixIn.class);
        this.addMixInAnnotations(UpdateItemRequest.class, UpdateItemRequestMixIn.class);
        this.addMixInAnnotations(UpdateItemResult.class, UpdateItemResultMixIn.class);
        this.addMixInAnnotations(StreamRecord.class, StreamRecordMixIn.class);
        this.addMixInAnnotations(StreamSpecification.class, StreamSpecificationMixIn.class);
        this.addMixInAnnotations(Tag.class, TagMixIn.class);
        this.addMixInAnnotations(TagResourceRequest.class, TagResourceRequestMixIn.class);
        this.addMixInAnnotations(TagResourceResult.class, TagResourceResultMixIn.class);
        this.addMixInAnnotations(TimeToLiveDescription.class, TimeToLiveDescriptionMixIn.class);
        this.addMixInAnnotations(TimeToLiveSpecification.class, TimeToLiveSpecificationMixIn.class);
        this.addMixInAnnotations(TransactGetItem.class, TransactGetItemMixIn.class);
        this.addMixInAnnotations(TransactGetItemsRequest.class, TransactGetItemsRequestMixIn.class);
        this.addMixInAnnotations(TransactWriteItem.class, TransactWriteItemMixIn.class);
        this.addMixInAnnotations(TransactGetItemsResult.class, TransactGetItemsResultMixIn.class);
        this.addMixInAnnotations(TransactWriteItemsRequest.class, TransactWriteItemsRequestMixIn.class);
        this.addMixInAnnotations(TransactWriteItemsResult.class, TransactWriteItemsResultMixIn.class);
        this.addMixInAnnotations(UntagResourceRequest.class, UntagResourceRequestMixIn.class);
        this.addMixInAnnotations(UntagResourceResult.class, UntagResourceResultMixIn.class);
        this.addMixInAnnotations(Update.class, UpdateMixIn.class);
        this.addMixInAnnotations(UpdateTableRequest.class, UpdateTableRequestMixIn.class);
        this.addMixInAnnotations(UpdateTableResult.class, UpdateTableResultMixIn.class);
        this.addMixInAnnotations(UpdateTimeToLiveRequest.class, UpdateTimeToLiveRequestMixIn.class);
        this.addMixInAnnotations(UpdateTimeToLiveResult.class, UpdateTimeToLiveResultMixIn.class);
        this.addMixInAnnotations(com.amazonaws.services.dynamodbv2.model.WriteRequest.class, WriteRequestMixIn.class);
        this.addMixInAnnotations(Stream.class, StreamMixIn.class);
        this.addMixInAnnotations(ExecuteStatementRequest.class, ExecuteStatementRequestMixIn.class);
        this.addMixInAnnotations(ExecuteStatementResult.class, ExecuteStatementResultMixIn.class);
        this.addMixInAnnotations(BatchExecuteStatementRequest.class, BatchExecuteStatementRequestMixIn.class);
        this.addMixInAnnotations(BatchExecuteStatementResult.class, BatchExecuteStatementResultMixIn.class);
        this.addMixInAnnotations(BatchStatementRequest.class, BatchStatementRequestMixIn.class);
        this.addMixInAnnotations(BatchStatementResponse.class, BatchStatementResponseMixIn.class);
        this.addMixInAnnotations(BatchStatementError.class, BatchStatementErrorMixIn.class);
        this.addMixInAnnotations(ExecuteTransactionRequest.class, ExecuteTransactionRequestMixIn.class);
        this.addMixInAnnotations(ExecuteTransactionResult.class, ExecuteTransactionResultMixIn.class);
        this.addMixInAnnotations(ParameterizedStatement.class, ParameterizedStatementMixIn.class);
        this.addMixInAnnotations(ExpressionsWrapperBase.class, ExpressionsWrapperBaseMixIn.class);
        this.addMixInAnnotations(ExpressionsWrapperBase.OperatorCounter.class, OperatorCounterMixIn.class);
        this.addMixInAnnotations(ExpressionsWrapperBase.NodeCounter.class, NodeCounterMixIn.class);
        this.addMixInAnnotations(ExpressionsWrapperBase.MaxPathDepthCounter.class, MaxPathDepthCounterMixIn.class);
        this.addMixInAnnotations(AttributeValue.class, LocalAttributeValueMixIn.class);
        this.addMixInAnnotations(AttributeValueUpdate.class, LocalAttributeValueUpdateMixIn.class);
        this.addMixInAnnotations(com.amazonaws.services.dynamodbv2.local.shared.model.Condition.class, LocalConditionMixIn.class);
        this.addMixInAnnotations(DeleteRequest.class, LocalDeleteRequestMixIn.class);
        this.addMixInAnnotations(ExpectedAttributeValue.class, LocalExpectedAttributeValueMixIn.class);
        this.addMixInAnnotations(com.amazonaws.services.dynamodbv2.local.shared.model.KeysAndAttributes.class, LocalKeysAndAttributesMixIn.class);
        this.addMixInAnnotations(com.amazonaws.services.dynamodbv2.local.shared.model.PutRequest.class, LocalPutRequestMixIn.class);
        this.addMixInAnnotations(WriteRequest.class, LocalWriteRequestMixIn.class);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static class ByteBufferSerializer
    extends JsonSerializer<ByteBuffer> {
        private ByteBufferSerializer() {
        }

        @Override
        public void serialize(ByteBuffer value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeBinary(LocalDBUtils.readAllBytesFromByteBuffer(value));
        }
    }

    private static class ByteBufferDeserializer
    extends JsonDeserializer<ByteBuffer> {
        private ByteBufferDeserializer() {
        }

        @Override
        public ByteBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return ByteBuffer.wrap(jp.getBinaryValue());
        }
    }

    private static class DateSerializer
    extends JsonSerializer<Date> {
        private DateSerializer() {
        }

        @Override
        public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeNumber(BigDecimal.valueOf((double)value.getTime() / 1000.0).setScale(3, RoundingMode.HALF_UP));
        }
    }

    private static class DateDeserializer
    extends JsonDeserializer<Date> {
        private DateDeserializer() {
        }

        @Override
        public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(jp.getValueAsLong() * 1000L);
            return calendar.getTime();
        }
    }

    private static abstract class AttributeDefinitionMixIn {
        private AttributeDefinitionMixIn() {
        }

        @JsonProperty(value="AttributeName")
        public abstract String getAttributeName();

        @JsonProperty(value="AttributeName")
        public abstract void setAttributeName(String var1);

        @JsonProperty(value="AttributeType")
        public abstract String getAttributeType();

        @JsonProperty(value="AttributeType")
        public abstract void setAttributeType(String var1);
    }

    private static abstract class AttributeValueMixIn {
        private AttributeValueMixIn() {
        }

        @JsonProperty(value="S")
        public abstract String getS();

        @JsonProperty(value="S")
        public abstract void setS(String var1);

        @JsonProperty(value="N")
        public abstract String getN();

        @JsonProperty(value="N")
        public abstract void setN(String var1);

        @JsonProperty(value="B")
        public abstract ByteBuffer getB();

        @JsonProperty(value="B")
        public abstract void setB(ByteBuffer var1);

        @JsonProperty(value="NULL")
        public abstract Boolean isNULL();

        @JsonProperty(value="NULL")
        public abstract void setNULL(Boolean var1);

        @JsonProperty(value="BOOL")
        public abstract Boolean getBOOL();

        @JsonProperty(value="BOOL")
        public abstract void setBOOL(Boolean var1);

        @JsonProperty(value="SS")
        public abstract List<String> getSS();

        @JsonProperty(value="SS")
        public abstract void setSS(List<String> var1);

        @JsonProperty(value="NS")
        public abstract List<String> getNS();

        @JsonProperty(value="NS")
        public abstract void setNS(List<String> var1);

        @JsonProperty(value="BS")
        public abstract List<String> getBS();

        @JsonProperty(value="BS")
        public abstract void setBS(List<String> var1);

        @JsonProperty(value="M")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getM();

        @JsonProperty(value="M")
        public abstract void setM(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="L")
        public abstract List<com.amazonaws.services.dynamodbv2.model.AttributeValue> getL();

        @JsonProperty(value="L")
        public abstract void setL(List<com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);
    }

    private static abstract class AttributeValueUpdateMixIn {
        private AttributeValueUpdateMixIn() {
        }

        @JsonProperty(value="Action")
        public abstract String getAction();

        @JsonProperty(value="Action")
        public abstract void setAction(String var1);

        @JsonProperty(value="Value")
        public abstract com.amazonaws.services.dynamodbv2.model.AttributeValue getValue();

        @JsonProperty(value="Value")
        public abstract void setValue(com.amazonaws.services.dynamodbv2.model.AttributeValue var1);
    }

    private static abstract class BatchGetItemRequestMixIn {
        private BatchGetItemRequestMixIn() {
        }

        @JsonProperty(value="RequestItems")
        public abstract Map<String, KeysAndAttributes> getRequestItems();

        @JsonProperty(value="RequestItems")
        public abstract void setRequestItems(Map<String, KeysAndAttributes> var1);

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract String getReturnConsumedCapacity();

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract void setReturnConsumedCapacity(String var1);
    }

    private static abstract class BatchGetItemResultMixIn {
        private BatchGetItemResultMixIn() {
        }

        @JsonProperty(value="ConsumedCapacity")
        public abstract List<ConsumedCapacity> getConsumedCapacity();

        @JsonProperty(value="ConsumedCapacity")
        public abstract void setConsumedCapacity(List<ConsumedCapacity> var1);

        @JsonProperty(value="Responses")
        public abstract Map<String, List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>>> getResponses();

        @JsonProperty(value="Responses")
        public abstract void setResponses(Map<String, List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>>> var1);

        @JsonProperty(value="UnprocessedKeys")
        public abstract Map<String, KeysAndAttributes> getUnprocessedKeys();

        @JsonProperty(value="UnprocessedKeys")
        public abstract void setUnprocessedKeys(Map<String, KeysAndAttributes> var1);
    }

    private static abstract class BatchWriteItemRequestMixIn {
        private BatchWriteItemRequestMixIn() {
        }

        @JsonProperty(value="RequestItems")
        public abstract Map<String, List<com.amazonaws.services.dynamodbv2.model.WriteRequest>> getRequestItems();

        @JsonProperty(value="RequestItems")
        public abstract void setRequestItems(Map<String, List<com.amazonaws.services.dynamodbv2.model.WriteRequest>> var1);

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract String getReturnConsumedCapacity();

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract void setReturnConsumedCapacity(String var1);

        @JsonProperty(value="ReturnItemCollectionMetrics")
        public abstract String getReturnItemCollectionMetrics();

        @JsonProperty(value="ReturnItemCollectionMetrics")
        public abstract void setReturnItemCollectionMetrics(String var1);
    }

    private static abstract class BatchWriteItemResultMixIn {
        private BatchWriteItemResultMixIn() {
        }

        @JsonProperty(value="ConsumedCapacity")
        public abstract List<ConsumedCapacity> getConsumedCapacity();

        @JsonProperty(value="ConsumedCapacity")
        public abstract void setConsumedCapacity(List<ConsumedCapacity> var1);

        @JsonProperty(value="ItemCollectionMetrics")
        public abstract Map<String, List<ItemCollectionMetrics>> getItemCollectionMetrics();

        @JsonProperty(value="ItemCollectionMetrics")
        public abstract void setItemCollectionMetrics(Map<String, List<ItemCollectionMetrics>> var1);

        @JsonProperty(value="UnprocessedItems")
        public abstract Map<String, List<com.amazonaws.services.dynamodbv2.model.WriteRequest>> getUnprocessedItems();

        @JsonProperty(value="UnprocessedItems")
        public abstract void setUnprocessedItems(Map<String, List<com.amazonaws.services.dynamodbv2.model.WriteRequest>> var1);
    }

    private static abstract class CancellationReasonMixIn {
        private CancellationReasonMixIn() {
        }

        @JsonProperty(value="Code")
        public abstract String getCode();

        @JsonProperty(value="Code")
        public abstract void setCode(String var1);

        @JsonProperty(value="Item")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getItem();

        @JsonProperty(value="Item")
        public abstract void setItem(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="Message")
        public abstract String getMessage();

        @JsonProperty(value="Message")
        public abstract void setMessage(String var1);
    }

    private static abstract class CapacityMixIn {
        private CapacityMixIn() {
        }

        @JsonProperty(value="ReadCapacityUnits")
        public abstract Double getReadCapacityUnits();

        @JsonProperty(value="ReadCapacityUnits")
        public abstract void setReadCapacityUnits(Double var1);

        @JsonProperty(value="WriteCapacityUnits")
        public abstract Double getWriteCapacityUnits();

        @JsonProperty(value="WriteCapacityUnits")
        public abstract void setWriteCapacityUnits(Double var1);

        @JsonProperty(value="CapacityUnits")
        public abstract Double getCapacityUnits();

        @JsonProperty(value="CapacityUnits")
        public abstract void setCapacityUnits(Double var1);
    }

    private static abstract class ConditionMixIn {
        private ConditionMixIn() {
        }

        @JsonProperty(value="AttributeValueList")
        public abstract List<com.amazonaws.services.dynamodbv2.model.AttributeValue> getAttributeValueList();

        @JsonProperty(value="AttributeValueList")
        public abstract void setAttributeValueList(List<com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ComparisonOperator")
        public abstract String getComparisonOperator();

        @JsonProperty(value="ComparisonOperator")
        public abstract void setComparisonOperator(String var1);
    }

    private static abstract class ConditionCheckMixIn {
        private ConditionCheckMixIn() {
        }

        @JsonProperty(value="ConditionExpression")
        public abstract String getConditionExpression();

        @JsonProperty(value="ConditionExpression")
        public abstract void setConditionExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void getExpressionAttributeValues();

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void setExpressionAttributeValues(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="Key")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getKey();

        @JsonProperty(value="Key")
        public abstract void setKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract ReturnValuesOnConditionCheckFailure getReturnValuesOnConditionCheckFailure();

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract void setReturnValuesOnConditionCheckFailure(ReturnValuesOnConditionCheckFailure var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);
    }

    private static abstract class ConsumedCapacityMixIn {
        private ConsumedCapacityMixIn() {
        }

        @JsonProperty(value="CapacityUnits")
        public abstract Double getCapacityUnits();

        @JsonProperty(value="CapacityUnits")
        public abstract void setCapacityUnits(Double var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="Table")
        public abstract Capacity getTable();

        @JsonProperty(value="Table")
        public abstract void setTable(Capacity var1);

        @JsonProperty(value="LocalSecondaryIndexes")
        public abstract Map<String, Capacity> getLocalSecondaryIndexes();

        @JsonProperty(value="LocalSecondaryIndexes")
        public abstract void setLocalSecondaryIndexes(Map<String, Capacity> var1);

        @JsonProperty(value="GlobalSecondaryIndexes")
        public abstract Map<String, Capacity> getGlobalSecondaryIndexes();

        @JsonProperty(value="GlobalSecondaryIndexes")
        public abstract void setGlobalSecondaryIndexes(Map<String, Capacity> var1);

        @JsonProperty(value="ReadCapacityUnits")
        public abstract Double getReadCapacityUnits();

        @JsonProperty(value="ReadCapacityUnits")
        public abstract void setReadCapacityUnits(Double var1);

        @JsonProperty(value="WriteCapacityUnits")
        public abstract Double getWriteCapacityUnits();

        @JsonProperty(value="WriteCapacityUnits")
        public abstract void setWriteCapacityUnits(Double var1);
    }

    private static abstract class CreateTableRequestMixIn {
        private CreateTableRequestMixIn() {
        }

        @JsonProperty(value="AttributeDefinitions")
        public abstract List<AttributeDefinition> getAttributeDefinitions();

        @JsonProperty(value="AttributeDefinitions")
        public abstract void setAttributeDefinitions(List<AttributeDefinition> var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="KeySchema")
        public abstract List<KeySchemaElement> getKeySchema();

        @JsonProperty(value="KeySchema")
        public abstract void setKeySchema(List<KeySchemaElement> var1);

        @JsonProperty(value="LocalSecondaryIndexes")
        public abstract List<LocalSecondaryIndex> getLocalSecondaryIndexes();

        @JsonProperty(value="LocalSecondaryIndexes")
        public abstract void setLocalSecondaryIndexes(List<LocalSecondaryIndex> var1);

        @JsonProperty(value="GlobalSecondaryIndexes")
        public abstract List<GlobalSecondaryIndex> getGlobalSecondaryIndexes();

        @JsonProperty(value="GlobalSecondaryIndexes")
        public abstract void setGlobalSecondaryIndexes(List<GlobalSecondaryIndex> var1);

        @JsonProperty(value="ProvisionedThroughput")
        public abstract ProvisionedThroughput getProvisionedThroughput();

        @JsonProperty(value="ProvisionedThroughput")
        public abstract void setProvisionedThroughput(ProvisionedThroughput var1);

        @JsonProperty(value="BillingMode")
        public abstract String getBillingMode();

        @JsonProperty(value="BillingMode")
        public abstract void setBillingMode(String var1);

        @JsonProperty(value="DeletionProtectionEnabled")
        public abstract Boolean getDeletionProtectionEnabled();

        @JsonProperty(value="DeletionProtectionEnabled")
        public abstract void setDeletionProtectionEnabled(Boolean var1);

        @JsonProperty(value="StreamSpecification")
        public abstract StreamSpecification getStreamSpecification();

        @JsonProperty(value="StreamSpecification")
        public abstract void setStreamSpecification(StreamSpecification var1);
    }

    private static abstract class CreateTableResultMixIn {
        private CreateTableResultMixIn() {
        }

        @JsonProperty(value="TableDescription")
        public abstract TableDescription getTableDescription();

        @JsonProperty(value="TableDescription")
        public abstract void setTableDescription(TableDescription var1);
    }

    private static abstract class DeleteMixIn {
        private DeleteMixIn() {
        }

        @JsonProperty(value="ConditionExpression")
        public abstract String getConditionExpression();

        @JsonProperty(value="ConditionExpression")
        public abstract void setConditionExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void getExpressionAttributeValues();

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void setExpressionAttributeValues(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="Key")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getKey();

        @JsonProperty(value="Key")
        public abstract void setKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract ReturnValuesOnConditionCheckFailure getReturnValuesOnConditionCheckFailure();

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract void setReturnValuesOnConditionCheckFailure(ReturnValuesOnConditionCheckFailure var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);
    }

    private static abstract class DeleteItemRequestMixIn {
        private DeleteItemRequestMixIn() {
        }

        @JsonProperty(value="ReturnValues")
        public abstract String getReturnValues();

        @JsonProperty(value="ReturnValues")
        public abstract void setReturnValues(String var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="ReturnItemCollectionMetrics")
        public abstract String getReturnItemCollectionMetrics();

        @JsonProperty(value="ReturnItemCollectionMetrics")
        public abstract void setReturnItemCollectionMetrics(String var1);

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract String getReturnConsumedCapacity();

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract void setReturnConsumedCapacity(String var1);

        @JsonProperty(value="Key")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getKey();

        @JsonProperty(value="Key")
        public abstract void setKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="Expected")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue> getExpected();

        @JsonProperty(value="Expected")
        public abstract void setExpected(Map<String, com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue> var1);

        @JsonProperty(value="ConditionalOperator")
        public abstract ConditionalOperator getConditionalOperator();

        @JsonProperty(value="ConditionalOperator")
        public abstract void setConditionalOperator(ConditionalOperator var1);

        @JsonProperty(value="ConditionExpression")
        public abstract String getConditionExpression();

        @JsonProperty(value="ConditionExpression")
        public abstract void setConditionExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void getExpressionAttributeValues();

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void setExpressionAttributeValues(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract void setReturnValuesOnConditionCheckFailure(String var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract String getReturnValuesOnConditionCheckFailure();
    }

    private static abstract class DeleteItemResultMixIn {
        private DeleteItemResultMixIn() {
        }

        @JsonProperty(value="Attributes")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getAttributes();

        @JsonProperty(value="Attributes")
        public abstract void setAttributes(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ItemCollectionMetrics")
        public abstract ItemCollectionMetrics getItemCollectionMetrics();

        @JsonProperty(value="ItemCollectionMetrics")
        public abstract void setItemCollectionMetrics(ItemCollectionMetrics var1);

        @JsonProperty(value="ConsumedCapacity")
        public abstract ConsumedCapacity getConsumedCapacity();

        @JsonProperty(value="ConsumedCapacity")
        public abstract void setConsumedCapacity(ConsumedCapacity var1);
    }

    private static abstract class DeleteRequestMixIn {
        private DeleteRequestMixIn() {
        }

        @JsonProperty(value="Key")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getKey();

        @JsonProperty(value="Key")
        public abstract void setKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);
    }

    private static abstract class DeleteTableRequestMixIn {
        private DeleteTableRequestMixIn() {
        }

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);
    }

    private static abstract class DeleteTableResultMixIn {
        private DeleteTableResultMixIn() {
        }

        @JsonProperty(value="TableDescription")
        public abstract TableDescription getTableDescription();

        @JsonProperty(value="TableDescription")
        public abstract void setTableDescription(TableDescription var1);
    }

    private static abstract class DescribeStreamRequestMixIn {
        private DescribeStreamRequestMixIn() {
        }

        @JsonProperty(value="StreamArn")
        public abstract String getStreamArn();

        @JsonProperty(value="StreamArn")
        public abstract void setStreamArn(String var1);

        @JsonProperty(value="Limit")
        public abstract Integer getLimit();

        @JsonProperty(value="Limit")
        public abstract void setLimit(Integer var1);

        @JsonProperty(value="ExclusiveStartShardId")
        public abstract String getExclusiveStartShardId();

        @JsonProperty(value="ExclusiveStartShardId")
        public abstract void setExclusiveStartShardId(String var1);
    }

    private static abstract class DescribeStreamResultMixIn {
        private DescribeStreamResultMixIn() {
        }

        @JsonProperty(value="StreamDescription")
        public abstract StreamDescription getStreamDescription();

        @JsonProperty(value="StreamDescription")
        public abstract void setStreamDescription(StreamDescription var1);
    }

    private static abstract class DescribeTableRequestMixIn {
        private DescribeTableRequestMixIn() {
        }

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);
    }

    private static abstract class DescribeTableResultMixIn {
        private DescribeTableResultMixIn() {
        }

        @JsonProperty(value="Table")
        public abstract TableDescription getTable();

        @JsonProperty(value="Table")
        public abstract void setTable(TableDescription var1);
    }

    private static abstract class DescribeLimitsResultMixIn {
        private DescribeLimitsResultMixIn() {
        }

        @JsonProperty(value="TableMaxReadCapacityUnits")
        public abstract Long getTableMaxReadCapacityUnits();

        @JsonProperty(value="TableMaxWriteCapacityUnits")
        public abstract Long getTableMaxWriteCapacityUnits();

        @JsonProperty(value="AccountMaxReadCapacityUnits")
        public abstract Long getAccountMaxReadCapacityUnits();

        @JsonProperty(value="AccountMaxWriteCapacityUnits")
        public abstract Long getAccountMaxWriteCapacityUnits();
    }

    private static abstract class DescribeLimitsRequestMixIn {
        private DescribeLimitsRequestMixIn() {
        }
    }

    private static abstract class DescribeTimeToLiveRequestMixIn {
        private DescribeTimeToLiveRequestMixIn() {
        }

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);
    }

    private static abstract class DescribeTimeToLiveResultMixIn {
        private DescribeTimeToLiveResultMixIn() {
        }

        @JsonProperty(value="TimeToLiveDescription")
        public abstract TimeToLiveDescription getTimeToLiveDescription();

        @JsonProperty(value="TimeToLiveDescription")
        public abstract void setTimeToLiveDescription(TimeToLiveDescription var1);
    }

    private static abstract class ExpectedAttributeValueMixIn {
        private ExpectedAttributeValueMixIn() {
        }

        @JsonProperty(value="Exists")
        public abstract Boolean getExists();

        @JsonProperty(value="Exists")
        public abstract void setExists(Boolean var1);

        @JsonProperty(value="Value")
        public abstract com.amazonaws.services.dynamodbv2.model.AttributeValue getValue();

        @JsonProperty(value="Value")
        public abstract void setValue(com.amazonaws.services.dynamodbv2.model.AttributeValue var1);

        @JsonProperty(value="ConditionalOperator")
        public abstract ConditionalOperator getConditionalOperator();

        @JsonProperty(value="ConditionalOperator")
        public abstract void setConditionalOperator(ConditionalOperator var1);

        @JsonProperty(value="ComparisonOperator")
        public abstract String getComparisonOperator();

        @JsonProperty(value="ComparisonOperator")
        public abstract void setComparisonOperator(String var1);

        @JsonProperty(value="AttributeValueList")
        public abstract List<com.amazonaws.services.dynamodbv2.model.AttributeValue> getAttributeValueList();

        @JsonProperty(value="AttributeValueList")
        public abstract void setAttributeValueList(List<com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);
    }

    private static abstract class GetMixIn {
        private GetMixIn() {
        }

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);

        @JsonProperty(value="Key")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getKey();

        @JsonProperty(value="Key")
        public abstract void setKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ProjectionExpression")
        public abstract void getProjectionExpression();

        @JsonProperty(value="ProjectionExpression")
        public abstract void setProjectionExpression(String var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);
    }

    private static abstract class GetItemRequestMixIn {
        private GetItemRequestMixIn() {
        }

        @JsonProperty(value="AttributesToGet")
        public abstract List<String> getAttributesToGet();

        @JsonProperty(value="AttributesToGet")
        public abstract void setAttributesToGet(List<String> var1);

        @JsonProperty(value="ConsistentRead")
        public abstract Boolean getConsistentRead();

        @JsonProperty(value="ConsistentRead")
        public abstract void setConsistentRead(Boolean var1);

        @JsonProperty(value="Key")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getKey();

        @JsonProperty(value="Key")
        public abstract void setKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract String getReturnConsumedCapacity();

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract void setReturnConsumedCapacity(String var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="ProjectionExpression")
        public abstract void getProjectionExpression();

        @JsonProperty(value="ProjectionExpression")
        public abstract void setProjectionExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);
    }

    private static abstract class GetItemResultMixIn {
        private GetItemResultMixIn() {
        }

        @JsonProperty(value="Item")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getItem();

        @JsonProperty(value="Item")
        public abstract void setItem(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ConsumedCapacity")
        public abstract ConsumedCapacity getConsumedCapacity();

        @JsonProperty(value="ConsumedCapacity")
        public abstract void setConsumedCapacity(ConsumedCapacity var1);
    }

    private static abstract class GetRecordsRequestMixIn {
        private GetRecordsRequestMixIn() {
        }

        @JsonProperty(value="ShardIterator")
        public abstract String getShardIterator();

        @JsonProperty(value="ShardIterator")
        public abstract void setShardIterator(String var1);

        @JsonProperty(value="Limit")
        public abstract Integer getLimit();

        @JsonProperty(value="Limit")
        public abstract void setLimit(Integer var1);
    }

    private static abstract class GetRecordsResultMixIn {
        private GetRecordsResultMixIn() {
        }

        @JsonProperty(value="Records")
        public abstract Collection<Record> getRecords();

        @JsonProperty(value="Records")
        public abstract void setRecords(Collection<Record> var1);

        @JsonProperty(value="NextShardIterator")
        public abstract String getNextShardIterator();

        @JsonProperty(value="NextShardIterator")
        public abstract void setNextShardIterator(String var1);
    }

    private static abstract class GetShardIteratorRequestMixIn {
        private GetShardIteratorRequestMixIn() {
        }

        @JsonProperty(value="StreamArn")
        public abstract String getStreamArn();

        @JsonProperty(value="StreamArn")
        public abstract void setStreamArn(String var1);

        @JsonProperty(value="ShardId")
        public abstract String getShardId();

        @JsonProperty(value="ShardId")
        public abstract void setShardId(String var1);

        @JsonProperty(value="ShardIteratorType")
        public abstract String getShardIteratorType();

        @JsonProperty(value="ShardIteratorType")
        public abstract void setShardIteratorType(String var1);

        @JsonProperty(value="SequenceNumber")
        public abstract String getSequenceNumber();

        @JsonProperty(value="SequenceNumber")
        public abstract void setSequenceNumber(String var1);
    }

    private static abstract class GetShardIteratorResultMixIn {
        private GetShardIteratorResultMixIn() {
        }

        @JsonProperty(value="ShardIterator")
        public abstract String getShardIterator();

        @JsonProperty(value="ShardIterator")
        public abstract void setShardIterator(String var1);
    }

    private static abstract class GlobalSecondaryIndexMixIn {
        private GlobalSecondaryIndexMixIn() {
        }

        @JsonProperty(value="IndexName")
        public abstract String getIndexName();

        @JsonProperty(value="IndexName")
        public abstract void setIndexName(String var1);

        @JsonProperty(value="KeySchema")
        public abstract List<KeySchemaElement> getKeySchema();

        @JsonProperty(value="KeySchema")
        public abstract void setKeySchema(List<KeySchemaElement> var1);

        @JsonProperty(value="Projection")
        public abstract Projection getProjection();

        @JsonProperty(value="Projection")
        public abstract void setProjection(Projection var1);

        @JsonProperty(value="ProvisionedThroughput")
        public abstract ProvisionedThroughput getProvisionedThroughput();

        @JsonProperty(value="ProvisionedThroughput")
        public abstract void setProvisionedThroughput(ProvisionedThroughput var1);
    }

    private static abstract class GlobalSecondaryIndexDescriptionMixIn {
        private GlobalSecondaryIndexDescriptionMixIn() {
        }

        @JsonProperty(value="IndexName")
        public abstract String getIndexName();

        @JsonProperty(value="IndexName")
        public abstract void setIndexName(String var1);

        @JsonProperty(value="KeySchema")
        public abstract List<KeySchemaElement> getKeySchema();

        @JsonProperty(value="KeySchema")
        public abstract void setKeySchema(List<KeySchemaElement> var1);

        @JsonProperty(value="Projection")
        public abstract Projection getProjection();

        @JsonProperty(value="Projection")
        public abstract void setProjection(Projection var1);

        @JsonProperty(value="IndexStatus")
        public abstract String getIndexStatus();

        @JsonProperty(value="IndexStatus")
        public abstract void setIndexStatus(String var1);

        @JsonIgnore
        public abstract Boolean isBackfilling();

        @JsonProperty(value="Backfilling")
        public abstract Boolean getBackfilling();

        @JsonProperty(value="Backfilling")
        public abstract void setBackfilling(Boolean var1);

        @JsonProperty(value="ProvisionedThroughput")
        public abstract ProvisionedThroughput getProvisionedThroughput();

        @JsonProperty(value="ProvisionedThroughput")
        public abstract void setProvisionedThroughput(ProvisionedThroughput var1);

        @JsonProperty(value="IndexSizeBytes")
        public abstract Long getIndexSizeBytes();

        @JsonProperty(value="IndexSizeBytes")
        public abstract void setIndexSizeBytes(Long var1);

        @JsonProperty(value="ItemCount")
        public abstract Long getItemCount();

        @JsonProperty(value="ItemCount")
        public abstract void setItemCount(Long var1);

        @JsonProperty(value="IndexArn")
        public abstract String getIndexArn();

        @JsonProperty(value="IndexArn")
        public abstract String setIndexArn(String var1);
    }

    private static abstract class GlobalSecondaryIndexUpdateMixIn {
        private GlobalSecondaryIndexUpdateMixIn() {
        }

        @JsonProperty(value="Update")
        public abstract UpdateGlobalSecondaryIndexAction getUpdate();

        @JsonProperty(value="Update")
        public abstract void setUpdate(UpdateGlobalSecondaryIndexAction var1);

        @JsonProperty(value="Delete")
        public abstract void setUpdate(DeleteGlobalSecondaryIndexAction var1);

        @JsonProperty(value="Create")
        public abstract CreateGlobalSecondaryIndexAction getCreate();

        @JsonProperty(value="Create")
        public abstract void setCreate(CreateGlobalSecondaryIndexAction var1);

        @JsonProperty(value="Delete")
        public abstract DeleteGlobalSecondaryIndexAction getDelete();
    }

    private static abstract class IdentityMixIn {
        private IdentityMixIn() {
        }

        @JsonProperty(value="PrincipalId")
        public abstract String getPrincipalId();

        @JsonProperty(value="PrincipalId")
        public abstract void setPrincipalId(String var1);

        @JsonProperty(value="Type")
        public abstract String getType();

        @JsonProperty(value="Type")
        public abstract void setType(String var1);
    }

    private static abstract class ItemCollectionMetricsMixIn {
        private ItemCollectionMetricsMixIn() {
        }

        @JsonProperty(value="ItemCollectionKey")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getItemCollectionKey();

        @JsonProperty(value="ItemCollectionKey")
        public abstract void setItemCollectionKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="SizeEstimateRangeGB")
        public abstract List<Double> getSizeEstimateRangeGB();

        @JsonProperty(value="SizeEstimateRangeGB")
        public abstract void setSizeEstimateRangeGB(List<Double> var1);
    }

    private static abstract class ItemResponseMixIn {
        private ItemResponseMixIn() {
        }

        @JsonProperty(value="Item")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getItem();

        @JsonProperty(value="Item")
        public abstract void setItem(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);
    }

    private static abstract class KeysAndAttributesMixIn {
        private KeysAndAttributesMixIn() {
        }

        @JsonProperty(value="AttributesToGet")
        public abstract List<String> getAttributesToGet();

        @JsonProperty(value="AttributesToGet")
        public abstract void setAttributesToGet(List<String> var1);

        @JsonProperty(value="ConsistentRead")
        public abstract Boolean getConsistentRead();

        @JsonProperty(value="ConsistentRead")
        public abstract void setConsistentRead(Boolean var1);

        @JsonProperty(value="Keys")
        public abstract List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> getKeys();

        @JsonProperty(value="Keys")
        public abstract void setKeys(List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> var1);

        @JsonProperty(value="ProjectionExpression")
        public abstract void getProjectionExpression();

        @JsonProperty(value="ProjectionExpression")
        public abstract void setProjectionExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);
    }

    private static abstract class KeySchemaElementMixIn {
        private KeySchemaElementMixIn() {
        }

        @JsonProperty(value="AttributeName")
        public abstract String getAttributeName();

        @JsonProperty(value="AttributeName")
        public abstract void setAttributeName(String var1);

        @JsonProperty(value="KeyType")
        public abstract String getKeyType();

        @JsonProperty(value="KeyType")
        public abstract void setKeyType(String var1);
    }

    private static abstract class ListStreamsRequestMixIn {
        private ListStreamsRequestMixIn() {
        }

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="Limit")
        public abstract Integer getLimit();

        @JsonProperty(value="Limit")
        public abstract void setLimit(Integer var1);

        @JsonProperty(value="ExclusiveStartStreamArn")
        public abstract String getExclusiveStartStreamArn();

        @JsonProperty(value="ExclusiveStartStreamArn")
        public abstract void setExclusiveStartStreamArn(String var1);
    }

    private static abstract class ListStreamsResultMixIn {
        private ListStreamsResultMixIn() {
        }

        @JsonProperty(value="Streams")
        public abstract List<Stream> getStreams();

        @JsonProperty(value="Streams")
        public abstract void setStreams(List<Stream> var1);

        @JsonProperty(value="LastEvaluatedStreamArn")
        public abstract String getLastEvaluatedStreamArn();

        @JsonProperty(value="LastEvaluatedStreamArn")
        public abstract void setLastEvaluatedStreamArn(String var1);
    }

    private static abstract class ListTablesRequestMixIn {
        private ListTablesRequestMixIn() {
        }

        @JsonProperty(value="Limit")
        public abstract Integer getLimit();

        @JsonProperty(value="Limit")
        public abstract void setLimit(Integer var1);

        @JsonProperty(value="ExclusiveStartTableName")
        public abstract String getExclusiveStartTableName();

        @JsonProperty(value="ExclusiveStartTableName")
        public abstract void setExclusiveStartTableName(String var1);
    }

    private static abstract class ListTablesResultMixIn {
        private ListTablesResultMixIn() {
        }

        @JsonProperty(value="LastEvaluatedTableName")
        public abstract String getLastEvaluatedTableName();

        @JsonProperty(value="LastEvaluatedTableName")
        public abstract void setLastEvaluatedTableName(String var1);

        @JsonProperty(value="TableNames")
        public abstract List<String> getTableNames();

        @JsonProperty(value="TableNames")
        public abstract void setTableNames(List<String> var1);
    }

    private static abstract class ListTagsOfResourceRequestMixIn {
        private ListTagsOfResourceRequestMixIn() {
        }

        @JsonProperty(value="ResourceArn")
        public abstract String getResourceArn();

        @JsonProperty(value="ResourceArn")
        public abstract void setResourceArn(String var1);
    }

    private static abstract class ListTagsOfResourceResultMixIn {
        private ListTagsOfResourceResultMixIn() {
        }

        @JsonProperty(value="Tags")
        public abstract Collection<Tag> getTags();

        @JsonProperty(value="Tags")
        public abstract void setTags(Collection<Tag> var1);
    }

    private static abstract class LocalSecondaryIndexDescriptionMixIn {
        private LocalSecondaryIndexDescriptionMixIn() {
        }

        @JsonProperty(value="IndexName")
        public abstract String getIndexName();

        @JsonProperty(value="IndexName")
        public abstract void setIndexName(String var1);

        @JsonProperty(value="IndexSizeBytes")
        public abstract Long getIndexSizeBytes();

        @JsonProperty(value="IndexSizeBytes")
        public abstract void setIndexSizeBytes(Long var1);

        @JsonProperty(value="ItemCount")
        public abstract Long getItemCount();

        @JsonProperty(value="ItemCount")
        public abstract void setItemCount(Long var1);

        @JsonProperty(value="KeySchema")
        public abstract List<KeySchemaElement> getKeySchema();

        @JsonProperty(value="KeySchema")
        public abstract void setKeySchema(List<KeySchemaElement> var1);

        @JsonProperty(value="Projection")
        public abstract Projection getProjection();

        @JsonProperty(value="Projection")
        public abstract void setProjection(Projection var1);

        @JsonProperty(value="IndexArn")
        public abstract String getIndexArn();

        @JsonProperty(value="IndexArn")
        public abstract String setIndexArn(String var1);
    }

    private static abstract class LocalSecondaryIndexMixIn {
        private LocalSecondaryIndexMixIn() {
        }

        @JsonProperty(value="IndexName")
        public abstract String getIndexName();

        @JsonProperty(value="IndexName")
        public abstract void setIndexName(String var1);

        @JsonProperty(value="KeySchema")
        public abstract List<KeySchemaElement> getKeySchema();

        @JsonProperty(value="KeySchema")
        public abstract void setKeySchema(List<KeySchemaElement> var1);

        @JsonProperty(value="Projection")
        public abstract Projection getProjection();

        @JsonProperty(value="Projection")
        public abstract void setProjection(Projection var1);
    }

    private static abstract class ProjectionMixIn {
        private ProjectionMixIn() {
        }

        @JsonProperty(value="NonKeyAttributes")
        public abstract List<String> getNonKeyAttributes();

        @JsonProperty(value="NonKeyAttributes")
        public abstract void setNonKeyAttributes(List<String> var1);

        @JsonProperty(value="ProjectionType")
        public abstract String getProjectionType();

        @JsonProperty(value="ProjectionType")
        public abstract void setProjectionType(String var1);
    }

    private static abstract class ProvisionedThroughputDescriptionMixIn {
        private ProvisionedThroughputDescriptionMixIn() {
        }

        @JsonProperty(value="LastDecreaseDateTime")
        public abstract Date getLastDecreaseDateTime();

        @JsonProperty(value="LastDecreaseDateTime")
        public abstract void setLastDecreaseDateTime(Date var1);

        @JsonProperty(value="LastIncreaseDateTime")
        public abstract Date getLastIncreaseDateTime();

        @JsonProperty(value="LastIncreaseDateTime")
        public abstract void setLastIncreaseDateTime(Date var1);

        @JsonProperty(value="NumberOfDecreasesToday")
        public abstract Long getNumberOfDecreasesToday();

        @JsonProperty(value="NumberOfDecreasesToday")
        public abstract void setNumberOfDecreasesToday(Long var1);

        @JsonProperty(value="ReadCapacityUnits")
        public abstract Long getReadCapacityUnits();

        @JsonProperty(value="ReadCapacityUnits")
        public abstract void setReadCapacityUnits(Long var1);

        @JsonProperty(value="WriteCapacityUnits")
        public abstract Long getWriteCapacityUnits();

        @JsonProperty(value="WriteCapacityUnits")
        public abstract void setWriteCapacityUnits(Long var1);
    }

    private static abstract class ProvisionedThroughputMixIn {
        private ProvisionedThroughputMixIn() {
        }

        @JsonProperty(value="ReadCapacityUnits")
        public abstract Long getReadCapacityUnits();

        @JsonProperty(value="ReadCapacityUnits")
        public abstract void setReadCapacityUnits(Long var1);

        @JsonProperty(value="WriteCapacityUnits")
        public abstract Long getWriteCapacityUnits();

        @JsonProperty(value="WriteCapacityUnits")
        public abstract void setWriteCapacityUnits(Long var1);
    }

    private static abstract class PutMixIn {
        private PutMixIn() {
        }

        @JsonProperty(value="ConditionExpression")
        public abstract String getConditionExpression();

        @JsonProperty(value="ConditionExpression")
        public abstract void setConditionExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void getExpressionAttributeValues();

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void setExpressionAttributeValues(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="Item")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getItem();

        @JsonProperty(value="Item")
        public abstract void setItem(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract ReturnValuesOnConditionCheckFailure getReturnValuesOnConditionCheckFailure();

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract void setReturnValuesOnConditionCheckFailure(ReturnValuesOnConditionCheckFailure var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);
    }

    private static abstract class PutItemRequestMixIn {
        private PutItemRequestMixIn() {
        }

        @JsonProperty(value="Expected")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue> getExpected();

        @JsonProperty(value="Expected")
        public abstract void setExpected(Map<String, com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue> var1);

        @JsonProperty(value="Item")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getItem();

        @JsonProperty(value="Item")
        public abstract void setItem(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract String getReturnConsumedCapacity();

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract void setReturnConsumedCapacity(String var1);

        @JsonProperty(value="ReturnItemCollectionMetrics")
        public abstract String getReturnItemCollectionMetrics();

        @JsonProperty(value="ReturnItemCollectionMetrics")
        public abstract void setReturnItemCollectionMetrics(String var1);

        @JsonProperty(value="ReturnValues")
        public abstract String getReturnValues();

        @JsonProperty(value="ReturnValues")
        public abstract void setReturnValues(String var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="ConditionalOperator")
        public abstract ConditionalOperator getConditionalOperator();

        @JsonProperty(value="ConditionalOperator")
        public abstract void setConditionalOperator(ConditionalOperator var1);

        @JsonProperty(value="ConditionExpression")
        public abstract String getConditionExpression();

        @JsonProperty(value="ConditionExpression")
        public abstract void setConditionExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void getExpressionAttributeValues();

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void setExpressionAttributeValues(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract void setReturnValuesOnConditionCheckFailure(String var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract String getReturnValuesOnConditionCheckFailure();
    }

    private static abstract class PutItemResultMixIn {
        private PutItemResultMixIn() {
        }

        @JsonProperty(value="Attributes")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getAttributes();

        @JsonProperty(value="Attributes")
        public abstract void setAttributes(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ConsumedCapacity")
        public abstract ConsumedCapacity getConsumedCapacity();

        @JsonProperty(value="ConsumedCapacity")
        public abstract void setConsumedCapacity(ConsumedCapacity var1);

        @JsonProperty(value="ItemCollectionMetrics")
        public abstract ItemCollectionMetrics getItemCollectionMetrics();

        @JsonProperty(value="ItemCollectionMetrics")
        public abstract void setItemCollectionMetrics(ItemCollectionMetrics var1);
    }

    private static abstract class PutRequestMixIn {
        private PutRequestMixIn() {
        }

        @JsonProperty(value="Item")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getItem();

        @JsonProperty(value="Item")
        public abstract void setItem(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);
    }

    private static abstract class QueryRequestMixIn {
        private QueryRequestMixIn() {
        }

        @JsonProperty(value="ConsistentRead")
        public abstract Boolean getConsistentRead();

        @JsonProperty(value="ConsistentRead")
        public abstract void setConsistentRead(Boolean var1);

        @JsonProperty(value="IndexName")
        public abstract String getIndexName();

        @JsonProperty(value="IndexName")
        public abstract void setIndexName(String var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract String getReturnConsumedCapacity();

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract void setReturnConsumedCapacity(String var1);

        @JsonProperty(value="ExclusiveStartKey")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getExclusiveStartKey();

        @JsonProperty(value="ExclusiveStartKey")
        public abstract void setExclusiveStartKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="AttributesToGet")
        public abstract List<String> getAttributesToGet();

        @JsonProperty(value="AttributesToGet")
        public abstract void setAttributesToGet(List<String> var1);

        @JsonProperty(value="Limit")
        public abstract Integer getLimit();

        @JsonProperty(value="Limit")
        public abstract void setLimit(Integer var1);

        @JsonProperty(value="KeyConditions")
        public abstract Map<String, Condition> getKeyConditions();

        @JsonProperty(value="KeyConditions")
        public abstract void setKeyConditions(Map<String, Condition> var1);

        @JsonProperty(value="QueryFilter")
        public abstract Map<String, Condition> getQueryFilter();

        @JsonProperty(value="QueryFilter")
        public abstract void setQueryFilter(Map<String, Condition> var1);

        @JsonProperty(value="ConditionalOperator")
        public abstract ConditionalOperator getConditionalOperator();

        @JsonProperty(value="ConditionalOperator")
        public abstract void setConditionalOperator(ConditionalOperator var1);

        @JsonProperty(value="ScanIndexForward")
        public abstract Boolean getScanIndexForward();

        @JsonProperty(value="ScanIndexForward")
        public abstract void setScanIndexForward(Boolean var1);

        @JsonProperty(value="Select")
        public abstract String getSelect();

        @JsonProperty(value="Select")
        public abstract void setSelect(String var1);

        @JsonProperty(value="ProjectionExpression")
        public abstract void getProjectionExpression();

        @JsonProperty(value="ProjectionExpression")
        public abstract void setProjectionExpression(String var1);

        @JsonProperty(value="FilterExpression")
        public abstract String getFilterExpression();

        @JsonProperty(value="FilterExpression")
        public abstract void setFilterExpression(String var1);

        @JsonProperty(value="KeyConditionExpression")
        public abstract String getKeyConditionExpression();

        @JsonProperty(value="KeyConditionExpression")
        public abstract void setKeyConditionExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void getExpressionAttributeValues();

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void setExpressionAttributeValues(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);
    }

    private static abstract class QueryResultMixIn {
        private QueryResultMixIn() {
        }

        @JsonProperty(value="Count")
        public abstract Integer getCount();

        @JsonProperty(value="Count")
        public abstract void setCount(Integer var1);

        @JsonProperty(value="ScannedCount")
        public abstract Integer getScannedCount();

        @JsonProperty(value="ScannedCount")
        public abstract void setScannedCount(Integer var1);

        @JsonProperty(value="Items")
        public abstract List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> getItems();

        @JsonProperty(value="Items")
        public abstract void setItems(List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> var1);

        @JsonProperty(value="LastEvaluatedKey")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getLastEvaluatedKey();

        @JsonProperty(value="LastEvaluatedKey")
        public abstract void setLastEvaluatedKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ConsumedCapacity")
        public abstract ConsumedCapacity getConsumedCapacity();

        @JsonProperty(value="ConsumedCapacity")
        public abstract void setConsumedCapacity(ConsumedCapacity var1);
    }

    private static abstract class ScanRequestMixIn {
        private ScanRequestMixIn() {
        }

        @JsonProperty(value="ScanFilter")
        public abstract Map<String, Condition> getScanFilter();

        @JsonProperty(value="ScanFilter")
        public abstract void setScanFilter(Map<String, Condition> var1);

        @JsonProperty(value="ConditionalOperator")
        public abstract ConditionalOperator getConditionalOperator();

        @JsonProperty(value="ConditionalOperator")
        public abstract void setConditionalOperator(ConditionalOperator var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="IndexName")
        public abstract String getIndexName();

        @JsonProperty(value="IndexName")
        public abstract void setIndexName(String var1);

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract String getReturnConsumedCapacity();

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract void setReturnConsumedCapacity(String var1);

        @JsonProperty(value="ExclusiveStartKey")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getExclusiveStartKey();

        @JsonProperty(value="ExclusiveStartKey")
        public abstract void setExclusiveStartKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="AttributesToGet")
        public abstract List<String> getAttributesToGet();

        @JsonProperty(value="AttributesToGet")
        public abstract void setAttributesToGet(List<String> var1);

        @JsonProperty(value="Limit")
        public abstract Integer getLimit();

        @JsonProperty(value="Limit")
        public abstract void setLimit(Integer var1);

        @JsonProperty(value="TotalSegments")
        public abstract Integer getTotalSegments();

        @JsonProperty(value="TotalSegments")
        public abstract void setTotalSegments(Integer var1);

        @JsonProperty(value="Segment")
        public abstract Integer getSegment();

        @JsonProperty(value="Segment")
        public abstract void setSegment(Integer var1);

        @JsonProperty(value="Select")
        public abstract String getSelect();

        @JsonProperty(value="Select")
        public abstract void setSelect(String var1);

        @JsonProperty(value="ProjectionExpression")
        public abstract void getProjectionExpression();

        @JsonProperty(value="ProjectionExpression")
        public abstract void setProjectionExpression(String var1);

        @JsonProperty(value="FilterExpression")
        public abstract String getFilterExpression();

        @JsonProperty(value="FilterExpression")
        public abstract void setFilterExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void getExpressionAttributeValues();

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void setExpressionAttributeValues(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ConsistentRead")
        public abstract Boolean getConsistentRead();

        @JsonProperty(value="ConsistentRead")
        public abstract void setConsistentRead(Boolean var1);
    }

    private static abstract class ScanResultMixIn {
        private ScanResultMixIn() {
        }

        @JsonProperty(value="Count")
        public abstract Integer getCount();

        @JsonProperty(value="Count")
        public abstract void setCount(Integer var1);

        @JsonProperty(value="ScannedCount")
        public abstract Integer getScannedCount();

        @JsonProperty(value="ScannedCount")
        public abstract void setScannedCount(Integer var1);

        @JsonProperty(value="Items")
        public abstract List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> getItems();

        @JsonProperty(value="Items")
        public abstract void setItems(List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> var1);

        @JsonProperty(value="LastEvaluatedKey")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getLastEvaluatedKey();

        @JsonProperty(value="LastEvaluatedKey")
        public abstract void setLastEvaluatedKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ConsumedCapacity")
        public abstract ConsumedCapacity getConsumedCapacity();

        @JsonProperty(value="ConsumedCapacity")
        public abstract void setConsumedCapacity(ConsumedCapacity var1);
    }

    private static abstract class SequenceNumberRangeMixIn {
        private SequenceNumberRangeMixIn() {
        }

        @JsonProperty(value="StartingSequenceNumber")
        public abstract String getStartingSequenceNumber();

        @JsonProperty(value="StartingSequenceNumber")
        public abstract void setStartingSequenceNumber(String var1);

        @JsonProperty(value="EndingSequenceNumber")
        public abstract String getEndingSequenceNumber();

        @JsonProperty(value="EndingSequenceNumber")
        public abstract void setEndingSequenceNumber(String var1);
    }

    private static abstract class StreamDescriptionMixIn {
        private StreamDescriptionMixIn() {
        }

        @JsonProperty(value="StreamArn")
        public abstract String getStreamArn();

        @JsonProperty(value="StreamArn")
        public abstract void setStreamArn(String var1);

        @JsonProperty(value="StreamLabel")
        public abstract String getStreamLabel();

        @JsonProperty(value="StreamLabel")
        public abstract void setStreamLabel(String var1);

        @JsonProperty(value="StreamStatus")
        public abstract String getStreamStatus();

        @JsonProperty(value="StreamStatus")
        public abstract void setStreamStatus(String var1);

        @JsonProperty(value="StreamViewType")
        public abstract String getStreamViewType();

        @JsonProperty(value="StreamViewType")
        public abstract void setStreamViewType(String var1);

        @JsonProperty(value="CreationRequestDateTime")
        public abstract Date getCreationRequestDateTime();

        @JsonProperty(value="CreationRequestDateTime")
        public abstract void setCreationRequestDateTime(Date var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="KeySchema")
        public abstract List<KeySchemaElement> getKeySchema();

        @JsonProperty(value="KeySchema")
        public abstract void setKeySchema(List<KeySchemaElement> var1);

        @JsonProperty(value="Shards")
        public abstract List<Shard> getShards();

        @JsonProperty(value="Shards")
        public abstract void setShards(List<Shard> var1);

        @JsonProperty(value="LastEvaluatedShardId")
        public abstract String getLastEvaluatedShardId();

        @JsonProperty(value="LastEvaluatedShardId")
        public abstract void setLastEvaluatedShardId(String var1);
    }

    private static abstract class ShardMixIn {
        private ShardMixIn() {
        }

        @JsonProperty(value="ShardId")
        public abstract String getShardId();

        @JsonProperty(value="ShardId")
        public abstract void setShardId(String var1);

        @JsonProperty(value="SequenceNumberRange")
        public abstract SequenceNumberRange getSequenceNumberRange();

        @JsonProperty(value="SequenceNumberRange")
        public abstract void setSequenceNumberRange(SequenceNumberRange var1);

        @JsonProperty(value="ParentShardId")
        public abstract String getParentShardId();

        @JsonProperty(value="ParentShardId")
        public abstract void setParentShardId(String var1);
    }

    private static abstract class TableDescriptionMixIn {
        private TableDescriptionMixIn() {
        }

        @JsonProperty(value="AttributeDefinitions")
        public abstract List<AttributeDefinition> getAttributeDefinitions();

        @JsonProperty(value="AttributeDefinitions")
        public abstract void setAttributeDefinitions(List<AttributeDefinition> var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="KeySchema")
        public abstract List<KeySchemaElement> getKeySchema();

        @JsonProperty(value="KeySchema")
        public abstract void setKeySchema(List<KeySchemaElement> var1);

        @JsonProperty(value="TableStatus")
        public abstract String getTableStatus();

        @JsonProperty(value="TableStatus")
        public abstract void setTableStatus(String var1);

        @JsonProperty(value="CreationDateTime")
        public abstract Date getCreationDateTime();

        @JsonProperty(value="CreationDateTime")
        public abstract void setCreationDateTime(Date var1);

        @JsonProperty(value="ProvisionedThroughput")
        public abstract ProvisionedThroughputDescription getProvisionedThroughput();

        @JsonProperty(value="ProvisionedThroughput")
        public abstract void setProvisionedThroughput(ProvisionedThroughputDescription var1);

        @JsonProperty(value="BillingModeSummary")
        public abstract BillingModeSummary getBillingModeSummary();

        @JsonProperty(value="BillingModeSummary")
        public abstract void setBillingModeSummary(BillingModeSummary var1);

        @JsonProperty(value="DeletionProtectionEnabled")
        public abstract Boolean getDeletionProtectionEnabled();

        @JsonProperty(value="DeletionProtectionEnabled")
        public abstract void setDeletionProtectionEnabled(Boolean var1);

        @JsonProperty(value="TableSizeBytes")
        public abstract Long getTableSizeBytes();

        @JsonProperty(value="TableSizeBytes")
        public abstract void setTableSizeBytes(Long var1);

        @JsonProperty(value="ItemCount")
        public abstract Long getItemCount();

        @JsonProperty(value="ItemCount")
        public abstract void setItemCount(Long var1);

        @JsonProperty(value="TableArn")
        public abstract Long getTableArn();

        @JsonProperty(value="TableArn")
        public abstract void setTableArn(String var1);

        @JsonProperty(value="LocalSecondaryIndexes")
        public abstract List<LocalSecondaryIndexDescription> getLocalSecondaryIndexes();

        @JsonProperty(value="LocalSecondaryIndexes")
        public abstract void setLocalSecondaryIndexes(List<LocalSecondaryIndexDescription> var1);

        @JsonProperty(value="GlobalSecondaryIndexes")
        public abstract List<GlobalSecondaryIndexDescription> getGlobalSecondaryIndexes();

        @JsonProperty(value="GlobalSecondaryIndexes")
        public abstract void setGlobalSecondaryIndexes(List<GlobalSecondaryIndexDescription> var1);

        @JsonProperty(value="StreamSpecification")
        public abstract StreamSpecification getStreamSpecification();

        @JsonProperty(value="StreamSpecification")
        public abstract void setStreamSpecification(StreamSpecification var1);

        @JsonProperty(value="TimeToLiveSpecification")
        public abstract TimeToLiveSpecification getTimeToLiveSpecification();

        @JsonProperty(value="TimeToLiveSpecification")
        public abstract void setTimeToLiveSpecification(TimeToLiveSpecification var1);

        @JsonProperty(value="LatestStreamLabel")
        public abstract String getLatestStreamLabel();

        @JsonProperty(value="LatestStreamLabel")
        public abstract void getLatestStreamLabel(String var1);

        @JsonProperty(value="LatestStreamArn")
        public abstract String getLatestStreamArn();

        @JsonProperty(value="LatestStreamArn")
        public abstract void setLatestStreamArn(String var1);
    }

    private static abstract class BillingModeSummaryMixIn {
        private BillingModeSummaryMixIn() {
        }

        @JsonProperty(value="BillingMode")
        public abstract String getBillingMode();

        @JsonProperty(value="BillingMode")
        public abstract void setBillingMode(String var1);

        @JsonProperty(value="LastUpdateToPayPerRequestDateTime")
        public abstract Date getLastUpdateToPayPerRequestDateTime();

        @JsonProperty(value="LastUpdateToPayPerRequestDateTime")
        public abstract void setLastUpdateToPayPerRequestDateTime(Date var1);
    }

    private static abstract class UpdateGlobalSecondaryIndexActionMixIn {
        private UpdateGlobalSecondaryIndexActionMixIn() {
        }

        @JsonProperty(value="IndexName")
        public abstract String getIndexName();

        @JsonProperty(value="IndexName")
        public abstract String setIndexName();

        @JsonProperty(value="ProvisionedThroughput")
        public abstract ProvisionedThroughput getProvisionedThroughput();

        @JsonProperty(value="ProvisionedThroughput")
        public abstract void setProvisionedThroughput(ProvisionedThroughput var1);

        @JsonProperty(value="BillingMode")
        public abstract String getBillingMode();

        @JsonProperty(value="BillingMode")
        public abstract void setBillingMode(String var1);
    }

    private static abstract class CreateGlobalSecondaryIndexActionMixIn {
        private CreateGlobalSecondaryIndexActionMixIn() {
        }

        @JsonProperty(value="IndexName")
        public abstract String getIndexName();

        @JsonProperty(value="IndexName")
        public abstract void setIndexName(String var1);

        @JsonProperty(value="KeySchema")
        public abstract List<KeySchemaElement> getKeySchema();

        @JsonProperty(value="KeySchema")
        public abstract void setKeySchema(List<KeySchemaElement> var1);

        @JsonProperty(value="Projection")
        public abstract Projection getProjection();

        @JsonProperty(value="Projection")
        public abstract void setProjection(Projection var1);

        @JsonProperty(value="ProvisionedThroughput")
        public abstract ProvisionedThroughput getProvisionedThroughput();

        @JsonProperty(value="ProvisionedThroughput")
        public abstract void setProvisionedThroughput(ProvisionedThroughput var1);

        @JsonProperty(value="BillingMode")
        public abstract String getBillingMode();

        @JsonProperty(value="BillingMode")
        public abstract void setBillingMode(String var1);
    }

    private static abstract class DeleteGlobalSecondaryIndexActionMixIn {
        private DeleteGlobalSecondaryIndexActionMixIn() {
        }

        @JsonProperty(value="IndexName")
        public abstract String getIndexName();

        @JsonProperty(value="IndexName")
        public abstract void setIndexName(String var1);
    }

    private static abstract class UpdateItemRequestMixIn {
        private UpdateItemRequestMixIn() {
        }

        @JsonProperty(value="AttributeUpdates")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate> getAttributeUpdates();

        @JsonProperty(value="AttributeUpdates")
        public abstract void setAttributeUpdates(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate> var1);

        @JsonProperty(value="ReturnValues")
        public abstract String getReturnValues();

        @JsonProperty(value="ReturnValues")
        public abstract void setReturnValues(String var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="ReturnItemCollectionMetrics")
        public abstract String getReturnItemCollectionMetrics();

        @JsonProperty(value="ReturnItemCollectionMetrics")
        public abstract void setReturnItemCollectionMetrics(String var1);

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract String getReturnConsumedCapacity();

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract void setReturnConsumedCapacity(String var1);

        @JsonProperty(value="Key")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getKey();

        @JsonProperty(value="Key")
        public abstract void setKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="Expected")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue> getExpected();

        @JsonProperty(value="Expected")
        public abstract void setExpected(Map<String, com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue> var1);

        @JsonProperty(value="ConditionalOperator")
        public abstract ConditionalOperator getConditionalOperator();

        @JsonProperty(value="ConditionalOperator")
        public abstract void setConditionalOperator(ConditionalOperator var1);

        @JsonProperty(value="ConditionExpression")
        public abstract String getConditionExpression();

        @JsonProperty(value="ConditionExpression")
        public abstract void setConditionExpression(String var1);

        @JsonProperty(value="UpdateExpression")
        public abstract String getUpdateExpression();

        @JsonProperty(value="UpdateExpression")
        public abstract void setUpdateExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void getExpressionAttributeValues();

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void setExpressionAttributeValues(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract void setReturnValuesOnConditionCheckFailure(String var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract String getReturnValuesOnConditionCheckFailure();
    }

    private static abstract class UpdateItemResultMixIn {
        private UpdateItemResultMixIn() {
        }

        @JsonProperty(value="Attributes")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getAttributes();

        @JsonProperty(value="Attributes")
        public abstract void setAttributes(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ItemCollectionMetrics")
        public abstract ItemCollectionMetrics getItemCollectionMetrics();

        @JsonProperty(value="ItemCollectionMetrics")
        public abstract void setItemCollectionMetrics(ItemCollectionMetrics var1);

        @JsonProperty(value="ConsumedCapacity")
        public abstract ConsumedCapacity getConsumedCapacity();

        @JsonProperty(value="ConsumedCapacity")
        public abstract void setConsumedCapacity(ConsumedCapacity var1);
    }

    private static abstract class StreamRecordMixIn {
        private StreamRecordMixIn() {
        }

        @JsonProperty(value="ApproximateCreationDateTime")
        public abstract Date getApproximateCreationDateTime();

        @JsonProperty(value="ApproximateCreationDateTime")
        public abstract void setApproximateCreationDateTime(Date var1);

        @JsonProperty(value="Keys")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getKeys();

        @JsonProperty(value="Keys")
        public abstract void setKeys(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="NewImage")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getNewImage();

        @JsonProperty(value="NewImage")
        public abstract void setNewImage(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="OldImage")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getOldImage();

        @JsonProperty(value="OldImage")
        public abstract void setOldImage(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="SequenceNumber")
        public abstract String getSequenceNumber();

        @JsonProperty(value="SequenceNumber")
        public abstract void setSequenceNumber(String var1);

        @JsonProperty(value="SizeBytes")
        public abstract Long getSizeBytes();

        @JsonProperty(value="SizeBytes")
        public abstract void setSizeBytes(Long var1);

        @JsonProperty(value="StreamViewType")
        public abstract StreamViewType getStreamViewType();

        @JsonProperty(value="StreamViewType")
        public abstract void setStreamViewType(StreamViewType var1);
    }

    private static abstract class StreamSpecificationMixIn {
        private StreamSpecificationMixIn() {
        }

        @JsonProperty(value="StreamEnabled")
        public abstract Boolean getStreamEnabled();

        @JsonProperty(value="StreamEnabled")
        public abstract void setStreamEnabled(Boolean var1);

        @JsonProperty(value="StreamViewType")
        public abstract String getStreamViewType();

        @JsonProperty(value="StreamViewType")
        public abstract void setStreamViewType(String var1);
    }

    private static abstract class TagMixIn {
        private TagMixIn() {
        }

        @JsonProperty(value="Key")
        public abstract String getKey();

        @JsonProperty(value="Key")
        public abstract void setKey(String var1);

        @JsonProperty(value="Value")
        public abstract String getValue();

        @JsonProperty(value="Value")
        public abstract void setValue(String var1);
    }

    private static abstract class TagResourceRequestMixIn {
        private TagResourceRequestMixIn() {
        }

        @JsonProperty(value="ResourceArn")
        public abstract String getResourceArn();

        @JsonProperty(value="ResourceArn")
        public abstract void setResourceArn(String var1);

        @JsonProperty(value="Tags")
        public abstract Collection<Tag> getTags();

        @JsonProperty(value="Tags")
        public abstract void setTags(Collection<Tag> var1);
    }

    private static abstract class TagResourceResultMixIn {
        private TagResourceResultMixIn() {
        }
    }

    private static abstract class TimeToLiveDescriptionMixIn {
        private TimeToLiveDescriptionMixIn() {
        }

        @JsonProperty(value="TimeToLiveStatus")
        public abstract String getTimeToLiveStatus();

        @JsonProperty(value="TimeToLiveStatus")
        public abstract void setTimeToLiveStatus(String var1);

        @JsonProperty(value="AttributeName")
        public abstract String getAttributeName();

        @JsonProperty(value="AttributeName")
        public abstract void setAttributeName(String var1);
    }

    private static abstract class TimeToLiveSpecificationMixIn {
        private TimeToLiveSpecificationMixIn() {
        }

        @JsonProperty(value="Enabled")
        public abstract Boolean getEnabled();

        @JsonProperty(value="Enabled")
        public abstract void setEnabled(Boolean var1);

        @JsonProperty(value="AttributeName")
        public abstract String getAttributeName();

        @JsonProperty(value="AttributeName")
        public abstract void setAttributeName(String var1);
    }

    private static abstract class TransactGetItemMixIn {
        private TransactGetItemMixIn() {
        }

        @JsonProperty(value="Get")
        public abstract Get getGet();

        @JsonProperty(value="Get")
        public abstract void setGet(Get var1);
    }

    private static abstract class TransactGetItemsRequestMixIn {
        private TransactGetItemsRequestMixIn() {
        }

        @JsonProperty(value="TransactItems")
        public abstract List<TransactGetItem> getTransactItems();

        @JsonProperty(value="TransactItems")
        public abstract void setTransactItems(List<TransactGetItem> var1);

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract String getReturnConsumedCapacity();

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract void setReturnConsumedCapacity(String var1);
    }

    private static abstract class TransactWriteItemMixIn {
        private TransactWriteItemMixIn() {
        }

        @JsonProperty(value="ConditionCheck")
        public abstract ConditionCheck getConditionCheck();

        @JsonProperty(value="ConditionCheck")
        public abstract void setConditionCheck(ConditionCheck var1);

        @JsonProperty(value="Delete")
        public abstract Delete getDelete();

        @JsonProperty(value="Delete")
        public abstract void setDelete(Delete var1);

        @JsonProperty(value="Put")
        public abstract Put getPut();

        @JsonProperty(value="Put")
        public abstract void setPut(Put var1);

        @JsonProperty(value="Update")
        public abstract Update getUpdate();

        @JsonProperty(value="Update")
        public abstract void setUpdate(Update var1);
    }

    private static abstract class TransactGetItemsResultMixIn {
        private TransactGetItemsResultMixIn() {
        }

        @JsonProperty(value="Responses")
        public abstract List<ItemResponse> getResponses();

        @JsonProperty(value="Responses")
        public abstract void setResponses(List<ItemResponse> var1);

        @JsonProperty(value="ConsumedCapacity")
        public abstract ConsumedCapacity getConsumedCapacity();

        @JsonProperty(value="ConsumedCapacity")
        public abstract void setConsumedCapacity(ConsumedCapacity var1);
    }

    private static abstract class TransactWriteItemsRequestMixIn {
        private TransactWriteItemsRequestMixIn() {
        }

        @JsonProperty(value="TransactItems")
        public abstract List<TransactWriteItem> getTransactItems();

        @JsonProperty(value="TransactItems")
        public abstract void setTransactItems(List<TransactWriteItem> var1);

        @JsonProperty(value="ClientRequestToken")
        public abstract String getClientRequestToken();

        @JsonProperty(value="ClientRequestToken")
        public abstract void setClientRequestToken(String var1);

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract String getReturnConsumedCapacity();

        @JsonProperty(value="ReturnConsumedCapacity")
        public abstract void setReturnConsumedCapacity(String var1);
    }

    private static abstract class TransactWriteItemsResultMixIn {
        private TransactWriteItemsResultMixIn() {
        }

        @JsonProperty(value="ItemCollectionMetrics")
        public abstract ItemCollectionMetrics getItemCollectionMetrics();

        @JsonProperty(value="ItemCollectionMetrics")
        public abstract void setItemCollectionMetrics(ItemCollectionMetrics var1);

        @JsonProperty(value="ConsumedCapacity")
        public abstract ConsumedCapacity getConsumedCapacity();

        @JsonProperty(value="ConsumedCapacity")
        public abstract void setConsumedCapacity(ConsumedCapacity var1);
    }

    private static abstract class UntagResourceRequestMixIn {
        private UntagResourceRequestMixIn() {
        }

        @JsonProperty(value="ResourceArn")
        public abstract String getResourceArn();

        @JsonProperty(value="ResourceArn")
        public abstract void setResourceArn(String var1);
    }

    private static abstract class UntagResourceResultMixIn {
        private UntagResourceResultMixIn() {
        }
    }

    private static abstract class UpdateMixIn {
        private UpdateMixIn() {
        }

        @JsonProperty(value="ConditionExpression")
        public abstract String getConditionExpression();

        @JsonProperty(value="ConditionExpression")
        public abstract void setConditionExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void getExpressionAttributeValues();

        @JsonProperty(value="ExpressionAttributeValues")
        public abstract void setExpressionAttributeValues(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="Key")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getKey();

        @JsonProperty(value="Key")
        public abstract void setKey(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract ReturnValuesOnConditionCheckFailure getReturnValuesOnConditionCheckFailure();

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract void setReturnValuesOnConditionCheckFailure(ReturnValuesOnConditionCheckFailure var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="UpdateExpression")
        public abstract String getUpdateExpression();

        @JsonProperty(value="UpdateExpression")
        public abstract void setUpdateExpression(String var1);
    }

    private static abstract class UpdateTableRequestMixIn {
        private UpdateTableRequestMixIn() {
        }

        @JsonProperty(value="AttributeDefinitions")
        public abstract List<AttributeDefinition> getAttributeDefinitions();

        @JsonProperty(value="AttributeDefinitions")
        public abstract void setAttributeDefinitions(List<AttributeDefinition> var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="ProvisionedThroughput")
        public abstract ProvisionedThroughput getProvisionedThroughput();

        @JsonProperty(value="ProvisionedThroughput")
        public abstract void setProvisionedThroughput(ProvisionedThroughput var1);

        @JsonProperty(value="BillingMode")
        public abstract String getBillingMode();

        @JsonProperty(value="BillingMode")
        public abstract void setBillingMode(String var1);

        @JsonProperty(value="DeletionProtectionEnabled")
        public abstract Boolean getDeletionProtectionEnabled();

        @JsonProperty(value="DeletionProtectionEnabled")
        public abstract void setDeletionProtectionEnabled(Boolean var1);

        @JsonProperty(value="GlobalSecondaryIndexUpdates")
        public abstract List<GlobalSecondaryIndexUpdate> getGlobalSecondaryIndexUpdates();

        @JsonProperty(value="GlobalSecondaryIndexUpdates")
        public abstract void setGlobalSecondaryIndexUpdates(List<GlobalSecondaryIndexUpdate> var1);

        @JsonProperty(value="StreamSpecification")
        public abstract StreamSpecification getStreamSpecification();

        @JsonProperty(value="StreamSpecification")
        public abstract void setStreamSpecification(StreamSpecification var1);
    }

    private static abstract class UpdateTableResultMixIn {
        private UpdateTableResultMixIn() {
        }

        @JsonProperty(value="TableDescription")
        public abstract TableDescription getTableDescription();

        @JsonProperty(value="TableDescription")
        public abstract void setTableDescription(TableDescription var1);
    }

    private static abstract class UpdateTimeToLiveRequestMixIn {
        private UpdateTimeToLiveRequestMixIn() {
        }

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="TimeToLiveSpecification")
        public abstract TimeToLiveSpecification getTimeToLiveSpecification();

        @JsonProperty(value="TimeToLiveSpecification")
        public abstract void setTimeToLiveSpecification(TimeToLiveSpecification var1);
    }

    private static abstract class UpdateTimeToLiveResultMixIn {
        private UpdateTimeToLiveResultMixIn() {
        }

        @JsonProperty(value="TimeToLiveSpecification")
        public abstract TimeToLiveSpecification getTimeToLiveSpecification();

        @JsonProperty(value="TimeToLiveSpecification")
        public abstract void setTimeToLiveSpecification(TimeToLiveSpecification var1);
    }

    private static abstract class WriteRequestMixIn {
        private WriteRequestMixIn() {
        }

        @JsonProperty(value="DeleteRequest")
        public abstract com.amazonaws.services.dynamodbv2.model.DeleteRequest getDeleteRequest();

        @JsonProperty(value="DeleteRequest")
        public abstract void setDeleteRequest(com.amazonaws.services.dynamodbv2.model.DeleteRequest var1);

        @JsonProperty(value="PutRequest")
        public abstract PutRequest getPutRequest();

        @JsonProperty(value="PutRequest")
        public abstract void setPutRequest(PutRequest var1);
    }

    private static abstract class StreamMixIn {
        private StreamMixIn() {
        }

        @JsonProperty(value="StreamArn")
        public abstract String getStreamArn();

        @JsonProperty(value="StreamArn")
        public abstract void setStreamArn(String var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="StreamLabel")
        public abstract String getStreamLabel();

        @JsonProperty(value="StreamLabel")
        public abstract void setStreamLabel(String var1);
    }

    private static abstract class ExecuteStatementRequestMixIn {
        private ExecuteStatementRequestMixIn() {
        }

        @JsonProperty(value="Statement")
        public abstract String getStatement();

        @JsonProperty(value="Statement")
        public abstract void setStatement(String var1);

        @JsonProperty(value="Parameters")
        public abstract List<com.amazonaws.services.dynamodbv2.model.AttributeValue> getParameters();

        @JsonProperty(value="Parameters")
        public abstract void setParameters(Collection<com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ConsistentRead")
        public abstract Boolean getConsistentRead();

        @JsonProperty(value="ConsistentRead")
        public abstract void setConsistentRead(Boolean var1);

        @JsonProperty(value="NextToken")
        public abstract String getNextToken();

        @JsonProperty(value="NextToken")
        public abstract void setNextToken(String var1);

        @JsonProperty(value="Limit")
        public abstract Integer getLimit();

        @JsonProperty(value="Limit")
        public abstract void setLimit(Integer var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract void setReturnValuesOnConditionCheckFailure(String var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract String getReturnValuesOnConditionCheckFailure();
    }

    private static abstract class ExecuteStatementResultMixIn {
        private ExecuteStatementResultMixIn() {
        }

        @JsonProperty(value="Items")
        public abstract List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> getItems();

        @JsonProperty(value="Items")
        public abstract void setItems(Collection<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> var1);

        @JsonProperty(value="NextToken")
        public abstract String getNextToken();

        @JsonProperty(value="NextToken")
        public abstract void setNextToken(String var1);
    }

    private static abstract class BatchExecuteStatementRequestMixIn {
        private BatchExecuteStatementRequestMixIn() {
        }

        @JsonProperty(value="Statements")
        public abstract List<BatchStatementRequest> getStatements();

        @JsonProperty(value="Statements")
        public abstract void setStatements(Collection<BatchStatementRequest> var1);
    }

    private static abstract class BatchExecuteStatementResultMixIn {
        private BatchExecuteStatementResultMixIn() {
        }

        @JsonProperty(value="Responses")
        public abstract List<BatchStatementResponse> getResponses();

        @JsonProperty(value="Responses")
        public abstract void setResponses(Collection<BatchStatementResponse> var1);
    }

    private static abstract class BatchStatementRequestMixIn {
        private BatchStatementRequestMixIn() {
        }

        @JsonProperty(value="Statement")
        public abstract String getStatement();

        @JsonProperty(value="Statement")
        public abstract void setStatement(String var1);

        @JsonProperty(value="Parameters")
        public abstract List<com.amazonaws.services.dynamodbv2.model.AttributeValue> getParameters();

        @JsonProperty(value="Parameters")
        public abstract void setParameters(Collection<com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ConsistentRead")
        public abstract Boolean getConsistentRead();

        @JsonProperty(value="ConsistentRead")
        public abstract void setConsistentRead(Boolean var1);
    }

    private static abstract class BatchStatementResponseMixIn {
        private BatchStatementResponseMixIn() {
        }

        @JsonProperty(value="Error")
        public abstract BatchStatementError getError();

        @JsonProperty(value="Error")
        public abstract void setError(BatchStatementError var1);

        @JsonProperty(value="TableName")
        public abstract String getTableName();

        @JsonProperty(value="TableName")
        public abstract void setTableName(String var1);

        @JsonProperty(value="Item")
        public abstract Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getItem();

        @JsonProperty(value="Item")
        public abstract void setItem(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);
    }

    private static abstract class BatchStatementErrorMixIn {
        private BatchStatementErrorMixIn() {
        }

        @JsonProperty(value="Code")
        public abstract String getCode();

        @JsonProperty(value="Code")
        public abstract void setCode(String var1);

        @JsonProperty(value="Message")
        public abstract String getMessage();

        @JsonProperty(value="Message")
        public abstract void setMessage(String var1);
    }

    private static abstract class ExecuteTransactionRequestMixIn {
        private ExecuteTransactionRequestMixIn() {
        }

        @JsonProperty(value="TransactStatements")
        public abstract List<ParameterizedStatement> getTransactStatements();

        @JsonProperty(value="TransactStatements")
        public abstract void setTransactStatements(Collection<ParameterizedStatement> var1);

        @JsonProperty(value="ClientRequestToken")
        public abstract String getClientRequestToken();

        @JsonProperty(value="ClientRequestToken")
        public abstract void setClientRequestToken(String var1);
    }

    private static abstract class ExecuteTransactionResultMixIn {
        private ExecuteTransactionResultMixIn() {
        }

        @JsonProperty(value="Responses")
        public abstract List<ItemResponse> getResponses();

        @JsonProperty(value="Responses")
        public abstract void setResponses(Collection<ItemResponse> var1);
    }

    private static abstract class ParameterizedStatementMixIn {
        private ParameterizedStatementMixIn() {
        }

        @JsonProperty(value="Statement")
        public abstract String getStatement();

        @JsonProperty(value="Statement")
        public abstract void setStatement(String var1);

        @JsonProperty(value="Parameters")
        public abstract List<com.amazonaws.services.dynamodbv2.model.AttributeValue> getParameters();

        @JsonProperty(value="Parameters")
        public abstract void setParameters(Collection<com.amazonaws.services.dynamodbv2.model.AttributeValue> var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract void setReturnValuesOnConditionCheckFailure(String var1);

        @JsonProperty(value="ReturnValuesOnConditionCheckFailure")
        public abstract String getReturnValuesOnConditionCheckFailure();
    }

    private static abstract class ExpressionsWrapperBaseMixIn {
        private ExpressionsWrapperBaseMixIn() {
        }

        @JsonProperty(value="NameParameterUsage")
        public abstract Map<String, Integer> getNameParameterUsage();

        @JsonProperty(value="ValueParameterUsage")
        public abstract Map<String, Integer> getValueParameterUsage();

        @JsonProperty(value="ExpressionSize")
        public abstract int validateCombinedExpressionSize(int var1, Map<String, Integer> var2, Map<String, Integer> var3, DbEnv var4);

        @JsonProperty(value="ParameterUsageSize")
        public abstract int getParameterUsageSize(Map<String, Integer> var1, Map<String, Integer> var2, DbEnv var3);

        @JsonProperty(value="TopLevelFields")
        public abstract Set<String> getTopLevelFieldsWithNestedAccess();

        @JsonProperty(value="OperatorCounter")
        public abstract ExpressionsWrapperBase.OperatorCounter getOperatorCounter();

        @JsonProperty(value="NodeCounter")
        public abstract ExpressionsWrapperBase.NodeCounter getNodeCounter();

        @JsonProperty(value="MaxPathDepthCounter")
        public abstract ExpressionsWrapperBase.MaxPathDepthCounter getMaxPathDepthCounter();

        @JsonProperty(value="CumulativeSize")
        public abstract int getCumulativeSize();
    }

    private static abstract class OperatorCounterMixIn {
        private OperatorCounterMixIn() {
        }

        @JsonProperty(value="Count")
        public abstract int value();

        @JsonProperty(value="Count")
        public abstract int set(int var1);

        @JsonProperty(value="IncrementedCounter")
        public abstract int increment();
    }

    private static abstract class NodeCounterMixIn {
        private NodeCounterMixIn() {
        }

        @JsonProperty(value="Count")
        public abstract int value();

        @JsonProperty(value="Count")
        public abstract int set(int var1);

        @JsonProperty(value="IncrementedCounter")
        public abstract int increment();
    }

    private static abstract class MaxPathDepthCounterMixIn {
        private MaxPathDepthCounterMixIn() {
        }

        @JsonProperty(value="Count")
        public abstract int value();

        @JsonProperty(value="Count")
        public abstract int set(int var1);

        @JsonProperty(value="IncrementedCounter")
        public abstract int increment();
    }

    private static abstract class LocalAttributeValueMixIn {
        private LocalAttributeValueMixIn() {
        }

        @JsonProperty(value="S")
        public abstract String getS();

        @JsonProperty(value="S")
        public abstract void setS(String var1);

        @JsonProperty(value="N")
        public abstract String getN();

        @JsonProperty(value="N")
        public abstract void setN(String var1);

        @JsonProperty(value="B")
        public abstract ByteBuffer getB();

        @JsonProperty(value="B")
        public abstract void setB(ByteBuffer var1);

        @JsonProperty(value="NULL")
        public abstract Boolean getNULL();

        @JsonProperty(value="NULL")
        public abstract void setNULL(Boolean var1);

        @JsonProperty(value="BOOL")
        public abstract Boolean getBOOL();

        @JsonProperty(value="BOOL")
        public abstract void setBOOL(Boolean var1);

        @JsonProperty(value="SS")
        public abstract List<String> getSS();

        @JsonProperty(value="SS")
        public abstract void setSS(List<String> var1);

        @JsonProperty(value="NS")
        public abstract List<String> getNS();

        @JsonProperty(value="NS")
        public abstract void setNS(List<String> var1);

        @JsonProperty(value="BS")
        public abstract List<String> getBS();

        @JsonProperty(value="BS")
        public abstract void setBS(List<String> var1);

        @JsonProperty(value="M")
        public abstract Map<String, AttributeValue> getM();

        @JsonProperty(value="M")
        public abstract void setM(Map<String, AttributeValue> var1);

        @JsonProperty(value="L")
        public abstract List<AttributeValue> getL();

        @JsonProperty(value="L")
        public abstract void setL(List<AttributeValue> var1);

        @JsonIgnore
        public abstract String getType();

        @JsonIgnore
        public abstract void setType(String var1);

        @JsonIgnore
        public abstract Boolean isNull();
    }

    private static abstract class LocalAttributeValueUpdateMixIn {
        private LocalAttributeValueUpdateMixIn() {
        }

        @JsonProperty(value="Action")
        public abstract String getAction();

        @JsonProperty(value="Action")
        public abstract void setAction(String var1);

        @JsonProperty(value="Value")
        public abstract AttributeValue getValue();

        @JsonProperty(value="Value")
        public abstract void setValue(AttributeValue var1);
    }

    private static abstract class LocalConditionMixIn {
        private LocalConditionMixIn() {
        }

        @JsonProperty(value="AttributeValueList")
        public abstract List<AttributeValue> getAttributeValueList();

        @JsonProperty(value="AttributeValueList")
        public abstract void setAttributeValueList(List<AttributeValue> var1);

        @JsonProperty(value="ComparisonOperator")
        public abstract String getComparisonOperator();

        @JsonProperty(value="ComparisonOperator")
        public abstract void setComparisonOperator(String var1);
    }

    private static abstract class LocalDeleteRequestMixIn {
        private LocalDeleteRequestMixIn() {
        }

        @JsonProperty(value="Key")
        public abstract Map<String, AttributeValue> getKey();

        @JsonProperty(value="Key")
        public abstract void setKey(Map<String, AttributeValue> var1);
    }

    private static abstract class LocalExpectedAttributeValueMixIn {
        private LocalExpectedAttributeValueMixIn() {
        }

        @JsonProperty(value="Exists")
        public abstract Boolean getExists();

        @JsonProperty(value="Exists")
        public abstract void setExists(Boolean var1);

        @JsonProperty(value="Value")
        public abstract AttributeValue getValue();

        @JsonProperty(value="Value")
        public abstract void setValue(AttributeValue var1);

        @JsonProperty(value="ConditionalOperator")
        public abstract ConditionalOperator getConditionalOperator();

        @JsonProperty(value="ConditionalOperator")
        public abstract void setConditionalOperator(ConditionalOperator var1);

        @JsonProperty(value="ComparisonOperator")
        public abstract String getComparisonOperator();

        @JsonProperty(value="ComparisonOperator")
        public abstract void setComparisonOperator(String var1);

        @JsonProperty(value="AttributeValueList")
        public abstract List<AttributeValue> getAttributeValueList();

        @JsonProperty(value="AttributeValueList")
        public abstract void setAttributeValueList(List<AttributeValue> var1);
    }

    private static abstract class LocalKeysAndAttributesMixIn {
        private LocalKeysAndAttributesMixIn() {
        }

        @JsonProperty(value="AttributesToGet")
        public abstract List<String> getAttributesToGet();

        @JsonProperty(value="AttributesToGet")
        public abstract void setAttributesToGet(List<String> var1);

        @JsonProperty(value="ConsistentRead")
        public abstract Boolean getConsistentRead();

        @JsonProperty(value="ConsistentRead")
        public abstract void setConsistentRead(Boolean var1);

        @JsonProperty(value="Keys")
        public abstract List<Map<String, AttributeValue>> getKeys();

        @JsonProperty(value="Keys")
        public abstract void setKeys(List<Map<String, AttributeValue>> var1);

        @JsonProperty(value="ProjectionExpression")
        public abstract void getProjectionExpression();

        @JsonProperty(value="ProjectionExpression")
        public abstract void setProjectionExpression(String var1);

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void getExpressionAttributeNames();

        @JsonProperty(value="ExpressionAttributeNames")
        public abstract void setExpressionAttributeNames(Map<String, String> var1);
    }

    private static abstract class LocalPutRequestMixIn {
        private LocalPutRequestMixIn() {
        }

        @JsonProperty(value="Item")
        public abstract Map<String, AttributeValue> getItem();

        @JsonProperty(value="Item")
        public abstract void setItem(Map<String, AttributeValue> var1);
    }

    private static abstract class LocalWriteRequestMixIn {
        private LocalWriteRequestMixIn() {
        }

        @JsonProperty(value="DeleteRequest")
        public abstract DeleteRequest getDeleteRequest();

        @JsonProperty(value="DeleteRequest")
        public abstract void setDeleteRequest(DeleteRequest var1);

        @JsonProperty(value="PutRequest")
        public abstract com.amazonaws.services.dynamodbv2.local.shared.model.PutRequest getPutRequest();

        @JsonProperty(value="PutRequest")
        public abstract void setPutRequest(com.amazonaws.services.dynamodbv2.local.shared.model.PutRequest var1);
    }
}

