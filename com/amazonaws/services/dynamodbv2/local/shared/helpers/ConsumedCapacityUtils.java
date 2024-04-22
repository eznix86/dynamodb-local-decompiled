/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.Capacity
 *  com.amazonaws.services.dynamodbv2.model.ConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
 */
package com.amazonaws.services.dynamodbv2.local.shared.helpers;

import com.amazonaws.services.dynamodbv2.local.google.Function;
import com.amazonaws.services.dynamodbv2.local.google.Preconditions;
import com.amazonaws.services.dynamodbv2.local.google.Sets;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Capacity;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConsumedCapacityUtils {
    protected static final Set<ReturnConsumedCapacity> COMPUTE_CONSUMED_CAPACITY_TYPES = Sets.newHashSet(ReturnConsumedCapacity.INDEXES, ReturnConsumedCapacity.TOTAL);
    private static final Capacity ZERO = new Capacity().withCapacityUnits(Double.valueOf(0.0));

    public static boolean doNotRequireConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity) {
        return returnConsumedCapacity == null || ReturnConsumedCapacity.NONE == returnConsumedCapacity;
    }

    public static ConsumedCapacity computeConsumedCapacity(List<Map<String, AttributeValue>> items, boolean readFromGsi, boolean readFromLsi, String tableName, String indexName, boolean random, boolean stronglyConsistent, TransactionsEnabledMode transaction, ReturnConsumedCapacity returnConsumedCapacity) {
        if (ConsumedCapacityUtils.doNotRequireConsumedCapacity(returnConsumedCapacity)) {
            return null;
        }
        Preconditions.checkArgument(!readFromGsi || !readFromLsi);
        double cuDouble = random ? ConsumedCapacityUtils.computeCapacityRandom(items, stronglyConsistent) : ConsumedCapacityUtils.computeCapacityContiguous(items, stronglyConsistent);
        if (transaction == TransactionsEnabledMode.TRANSACTIONS_ENABLED) {
            cuDouble *= 2.0;
        }
        Capacity cu = new Capacity().withCapacityUnits(Double.valueOf(cuDouble));
        ConsumedCapacity consumedCapacity = new ConsumedCapacity().withTableName(tableName);
        if (readFromGsi || readFromLsi) {
            consumedCapacity.withCapacityUnits(Double.valueOf(cuDouble));
            if (ReturnConsumedCapacity.INDEXES == returnConsumedCapacity) {
                consumedCapacity.withTable(ZERO);
                if (readFromLsi) {
                    consumedCapacity.withLocalSecondaryIndexes(Collections.singletonMap(indexName, cu));
                } else {
                    consumedCapacity.withGlobalSecondaryIndexes(Collections.singletonMap(indexName, cu));
                }
            }
        } else {
            if (!ReturnConsumedCapacity.TOTAL.equals((Object)returnConsumedCapacity)) {
                consumedCapacity.withTable(cu);
            }
            consumedCapacity.withCapacityUnits(Double.valueOf(cuDouble));
        }
        return consumedCapacity;
    }

    public static List<ConsumedCapacity> mergeAllConsumedCapacities(List<ConsumedCapacity> capacities, ReturnConsumedCapacity returnConsumedCapacity) {
        if (returnConsumedCapacity == null || returnConsumedCapacity == ReturnConsumedCapacity.NONE) {
            return null;
        }
        HashMap consumedCapacitiesByTable = new HashMap(capacities.size());
        for (ConsumedCapacity consumedCapacity : capacities) {
            String tableName = consumedCapacity.getTableName();
            if (!consumedCapacitiesByTable.containsKey(tableName)) {
                consumedCapacitiesByTable.put(tableName, new LinkedHashSet());
            }
            ((Collection)consumedCapacitiesByTable.get(tableName)).add(consumedCapacity);
        }
        ArrayList<ConsumedCapacity> result = new ArrayList<ConsumedCapacity>(consumedCapacitiesByTable.size());
        for (Map.Entry entry : consumedCapacitiesByTable.entrySet()) {
            result.add(ConsumedCapacityUtils.mergeConsumedCapacities((String)entry.getKey(), (Collection)entry.getValue(), returnConsumedCapacity));
        }
        return result;
    }

    public static ConsumedCapacity mergeConsumedCapacities(String tableName, Collection<ConsumedCapacity> capacities, ReturnConsumedCapacity returnConsumedCapacity) {
        if (returnConsumedCapacity == null || returnConsumedCapacity == ReturnConsumedCapacity.NONE) {
            return null;
        }
        ConsumedCapacity aggregate = new ConsumedCapacity().withCapacityUnits(Double.valueOf(0.0)).withTableName(tableName);
        if (ReturnConsumedCapacity.INDEXES == returnConsumedCapacity) {
            aggregate.withTable(new Capacity().withCapacityUnits(Double.valueOf(0.0)));
            aggregate.withLocalSecondaryIndexes(new HashMap());
            aggregate.withGlobalSecondaryIndexes(new HashMap());
        }
        for (ConsumedCapacity consumedCapacity : capacities) {
            aggregate.setCapacityUnits(Double.valueOf(aggregate.getCapacityUnits() + consumedCapacity.getCapacityUnits()));
            aggregate.setReadCapacityUnits(ConsumedCapacityUtils.safelyAdd(aggregate.getReadCapacityUnits(), consumedCapacity.getReadCapacityUnits()));
            aggregate.setWriteCapacityUnits(ConsumedCapacityUtils.safelyAdd(aggregate.getWriteCapacityUnits(), consumedCapacity.getWriteCapacityUnits()));
            if (ReturnConsumedCapacity.INDEXES != returnConsumedCapacity) continue;
            aggregate.setTable(ConsumedCapacityUtils.addCapacities(aggregate.getTable(), consumedCapacity.getTable()));
            ConsumedCapacityUtils.mergeIndexCapacityMapsLeft(aggregate.getLocalSecondaryIndexes(), consumedCapacity.getLocalSecondaryIndexes());
            ConsumedCapacityUtils.mergeIndexCapacityMapsLeft(aggregate.getGlobalSecondaryIndexes(), consumedCapacity.getGlobalSecondaryIndexes());
        }
        if (ReturnConsumedCapacity.INDEXES == returnConsumedCapacity) {
            aggregate.withLocalSecondaryIndexes(aggregate.getLocalSecondaryIndexes().isEmpty() ? null : aggregate.getLocalSecondaryIndexes()).withGlobalSecondaryIndexes(aggregate.getGlobalSecondaryIndexes().isEmpty() ? null : aggregate.getGlobalSecondaryIndexes());
        }
        return aggregate;
    }

    public static void doubleAndCopyToWriteConsumedCapacity(ConsumedCapacity consumedCapacity) {
        if (consumedCapacity == null) {
            return;
        }
        ConsumedCapacityUtils.applyToAllCapacities(consumedCapacity, new Function<Capacity, Void>(){

            @Override
            public Void apply(Capacity input) {
                input.setCapacityUnits(Double.valueOf(input.getCapacityUnits() * 2.0));
                input.setWriteCapacityUnits(input.getCapacityUnits());
                return null;
            }
        });
        consumedCapacity.setCapacityUnits(Double.valueOf(consumedCapacity.getCapacityUnits() * 2.0));
        consumedCapacity.setWriteCapacityUnits(consumedCapacity.getCapacityUnits());
    }

    public static void copyToReadConsumedCapacity(ConsumedCapacity consumedCapacity) {
        if (consumedCapacity == null) {
            return;
        }
        ConsumedCapacityUtils.applyToAllCapacities(consumedCapacity, new Function<Capacity, Void>(){

            @Override
            public Void apply(Capacity input) {
                input.setReadCapacityUnits(input.getCapacityUnits());
                return null;
            }
        });
        consumedCapacity.setReadCapacityUnits(consumedCapacity.getCapacityUnits());
    }

    public static void applyToAllConsumedCapacities(List<ConsumedCapacity> consumedCapacity, Function<ConsumedCapacity, Void> func) {
        if (consumedCapacity == null) {
            return;
        }
        for (ConsumedCapacity cap : consumedCapacity) {
            func.apply(cap);
        }
    }

    public static void applyToAllCapacities(ConsumedCapacity consumedCapacity, Function<Capacity, Void> func) {
        if (consumedCapacity == null) {
            return;
        }
        if (consumedCapacity.getTable() != null) {
            func.apply(consumedCapacity.getTable());
        }
        if (consumedCapacity.getGlobalSecondaryIndexes() != null) {
            for (Capacity cap : consumedCapacity.getGlobalSecondaryIndexes().values()) {
                func.apply(cap);
            }
        }
        if (consumedCapacity.getLocalSecondaryIndexes() != null) {
            for (Capacity cap : consumedCapacity.getLocalSecondaryIndexes().values()) {
                func.apply(cap);
            }
        }
    }

    private static void mergeIndexCapacityMapsLeft(Map<String, Capacity> mergeInto, Map<String, Capacity> mergeFrom) {
        Preconditions.checkNotNull(mergeInto, "merge into map must not be null");
        if (mergeFrom == null) {
            return;
        }
        for (Map.Entry<String, Capacity> entry : mergeFrom.entrySet()) {
            String indexName = entry.getKey();
            Capacity indexCapacity = entry.getValue();
            if (mergeInto.containsKey(indexName)) {
                mergeInto.put(indexName, ConsumedCapacityUtils.addCapacities(mergeInto.get(indexName), indexCapacity));
                continue;
            }
            mergeInto.put(indexName, indexCapacity);
        }
    }

    private static Double safelyAdd(Double left, Double right) {
        if (right == null) {
            return left;
        }
        if (left == null) {
            return right;
        }
        return left + right;
    }

    private static Capacity addCapacities(Capacity left, Capacity right) {
        return new Capacity().withCapacityUnits(Double.valueOf(left.getCapacityUnits() + right.getCapacityUnits())).withReadCapacityUnits(ConsumedCapacityUtils.safelyAdd(left.getReadCapacityUnits(), right.getReadCapacityUnits())).withWriteCapacityUnits(ConsumedCapacityUtils.safelyAdd(left.getWriteCapacityUnits(), right.getWriteCapacityUnits()));
    }

    private static double computeCapacityContiguous(List<Map<String, AttributeValue>> itemsRead, boolean stronglyConsistent) {
        double bytes = 0.0;
        for (Map<String, AttributeValue> item : itemsRead) {
            bytes += (double)LocalDBUtils.getItemSizeBytes(item);
        }
        return stronglyConsistent ? Math.ceil(bytes / 4096.0) : Math.ceil(bytes / 4096.0) / 2.0;
    }

    private static double computeCapacityRandom(List<Map<String, AttributeValue>> itemsRead, boolean stronglyConsistent) {
        double capacityUnits = 0.0;
        if (itemsRead != null) {
            for (Map<String, AttributeValue> item : itemsRead) {
                double bytes = LocalDBUtils.getItemSizeBytes(item);
                bytes = Math.max(bytes, 1.0);
                capacityUnits += stronglyConsistent ? Math.ceil(bytes / 4096.0) : Math.ceil(bytes / 4096.0) / 2.0;
            }
        }
        return capacityUnits;
    }
}

