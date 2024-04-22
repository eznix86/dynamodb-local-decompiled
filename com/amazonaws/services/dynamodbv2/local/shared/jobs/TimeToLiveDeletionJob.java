/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.ScanRequest
 *  com.amazonaws.services.dynamodbv2.model.ScanResult
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.shared.jobs;

import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv1.client.LocalAmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.TableSchemaInfo;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.NamedJob;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

public class TimeToLiveDeletionJob
extends NamedJob {
    private static final int MIN_YEARS_FOR_ITEM_EXPIRY = 5;
    private static final int SCAN_LIMIT = 20;
    private static final String START_TIME_TOKEN = ":startValue";
    private static final String END_TIME_TOKEN = ":endValue";
    private static final String REPLACEMENT_TOKEN = "#IT";
    private static final String FILTER_EXPRESSION = "#IT BETWEEN :startValue AND :endValue";
    private static final Logger LOG = LogManager.getLogger(TimeToLiveDeletionJob.class);
    private final String tableName;
    private final String timeToLiveAttributeName;
    private final LocalAmazonDynamoDB localAmazonDynamoDB;
    private final LocalDBAccess dbAccess;
    private final TableSchemaInfo tableSchemaInfo;
    private final LocalDBInputConverter inputConverter;

    public TimeToLiveDeletionJob(String tableName, TableSchemaInfo tableSchemaInfo, String timeToLiveAttributeName, LocalAmazonDynamoDB localAmazonDynamoDB, LocalDBInputConverter inputConverter, LocalDBAccess dbAccess, JobsRegister jobs) {
        super(jobs);
        this.tableName = tableName;
        this.tableSchemaInfo = tableSchemaInfo;
        this.timeToLiveAttributeName = timeToLiveAttributeName;
        this.localAmazonDynamoDB = localAmazonDynamoDB;
        this.dbAccess = dbAccess;
        this.inputConverter = inputConverter;
    }

    public static String jobName(String tableName, String timeToLiveAttributeName) {
        return LocalDBUtils.getTimeToLiveThreadName(tableName, timeToLiveAttributeName);
    }

    private long getCurrentTimeInSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    private TimeToLiveExpression getTimeToLiveFiltersForScan() {
        HashMap<String, String> attributeNameMap = new HashMap<String, String>();
        attributeNameMap.put(REPLACEMENT_TOKEN, this.timeToLiveAttributeName);
        DateTime currentTimeDateTime = new DateTime(System.currentTimeMillis());
        long minTimeMillis = currentTimeDateTime.minusYears(5).getMillis();
        HashMap<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> expressionAttributeValues = new HashMap<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>();
        expressionAttributeValues.put(START_TIME_TOKEN, new com.amazonaws.services.dynamodbv2.model.AttributeValue().withN(Long.toString(TimeUnit.MILLISECONDS.toSeconds(minTimeMillis))));
        expressionAttributeValues.put(END_TIME_TOKEN, new com.amazonaws.services.dynamodbv2.model.AttributeValue().withN(Long.toString(this.getCurrentTimeInSeconds())));
        return new TimeToLiveExpression(FILTER_EXPRESSION, attributeNameMap, expressionAttributeValues);
    }

    private ScanRequest constructScanRequest() {
        ScanRequest scanRequest = new ScanRequest();
        scanRequest.setTableName(this.tableName);
        TimeToLiveExpression timeToLiveExpression = this.getTimeToLiveFiltersForScan();
        scanRequest.setFilterExpression(timeToLiveExpression.getFilterExpression());
        scanRequest.setExpressionAttributeValues(timeToLiveExpression.getExpressionAttributeValues());
        scanRequest.setExpressionAttributeNames(timeToLiveExpression.getExpressionAttributeNames());
        scanRequest.setLimit(Integer.valueOf(20));
        return scanRequest;
    }

    private Map<String, AttributeValue> getKeyFromItem(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> item) {
        HashMap<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> key = new HashMap<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>();
        for (AttributeDefinition attributeDefinition : this.tableSchemaInfo.getAttributes()) {
            String attributeName = attributeDefinition.getAttributeName();
            key.put(attributeName, item.get(attributeName));
        }
        return (Map)this.inputConverter.externalToInternalAttributes(key);
    }

    @Override
    protected void doJob() {
        final ScanResultWrapper scanResultWrapper = new ScanResultWrapper();
        final AtomicBoolean shouldRepeat = new AtomicBoolean(true);
        do {
            final ScanRequest scanRequest = this.constructScanRequest();
            scanRequest.setExclusiveStartKey(scanResultWrapper.exclusiveScanStartKey);
            new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(this.tableName), 10){

                @Override
                public void criticalSection() {
                    TableInfo tableInfo = TimeToLiveDeletionJob.this.dbAccess.getTableInfo(TimeToLiveDeletionJob.this.tableName);
                    if (tableInfo == null || tableInfo.getTimeToLiveSpecification() == null || !tableInfo.getTimeToLiveSpecification().isEnabled().booleanValue()) {
                        shouldRepeat.set(false);
                        return;
                    }
                    ScanResult scanResult = null;
                    try {
                        scanResult = TimeToLiveDeletionJob.this.localAmazonDynamoDB.scan(scanRequest);
                    } catch (Exception e) {
                        LOG.error("Error during scan in TimeToLiveDeletionJob", (Throwable)e);
                        return;
                    }
                    if (scanResult.getItems() != null) {
                        for (Map item : scanResult.getItems()) {
                            boolean deleteSuccess = TimeToLiveDeletionJob.this.dbAccess.deleteRecord(TimeToLiveDeletionJob.this.tableName, TimeToLiveDeletionJob.this.getKeyFromItem(item), true);
                            if (deleteSuccess) continue;
                            LOG.error("Error during delete item in TimeToLiveDeletionJob");
                            shouldRepeat.set(false);
                            return;
                        }
                    }
                    scanResultWrapper.exclusiveScanStartKey = scanResult.getLastEvaluatedKey();
                }
            }.execute();
            if (!shouldRepeat.get()) continue;
            this.sleepFor(LocalDBUtils.ITEM_EXPIRY_DELAY_BETWEEN_DELETION);
        } while (scanResultWrapper.exclusiveScanStartKey != null && shouldRepeat.get());
    }

    @Override
    public String name() {
        return LocalDBUtils.getTimeToLiveThreadName(this.tableName, this.timeToLiveAttributeName);
    }

    @Override
    public void cancel() {
    }

    private static class TimeToLiveExpression {
        private final String filterExpression;
        private final Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> expressionAttributeValues;
        private final Map<String, String> expressionAttributeNames;

        TimeToLiveExpression(String filterExpression, Map<String, String> expressionAttributeNames, Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> expressionAttributeValues) {
            this.filterExpression = filterExpression;
            this.expressionAttributeNames = expressionAttributeNames;
            this.expressionAttributeValues = expressionAttributeValues;
        }

        String getFilterExpression() {
            return this.filterExpression;
        }

        Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getExpressionAttributeValues() {
            return this.expressionAttributeValues;
        }

        Map<String, String> getExpressionAttributeNames() {
            return this.expressionAttributeNames;
        }
    }

    private static class ScanResultWrapper {
        Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> exclusiveScanStartKey;

        private ScanResultWrapper() {
        }
    }
}

