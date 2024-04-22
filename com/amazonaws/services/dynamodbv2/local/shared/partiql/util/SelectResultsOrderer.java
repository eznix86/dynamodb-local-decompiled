/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.util;

import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.OrderingStatus;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.util.ExprNodeTranslators;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.OrderBy;
import org.partiql.lang.ast.SortSpec;
import org.partiql.lang.ast.VariableReference;

public class SelectResultsOrderer {
    private final LocalPartiQLDbEnv localPartiQLDbEnv;

    public SelectResultsOrderer(LocalPartiQLDbEnv localPartiQLDbEnv) {
        this.localPartiQLDbEnv = localPartiQLDbEnv;
    }

    public OrderingStatus getOrderingStatus(OrderBy orderBy, TableInfo tableInfo, String indexName) {
        String hashKeyAttributeName;
        if (orderBy == null) {
            return null;
        }
        HashSet<String> keyAttributeNames = new HashSet<String>();
        if (indexName != null) {
            hashKeyAttributeName = tableInfo.isGSIIndex(indexName) ? tableInfo.getGSIHashKey(indexName).getAttributeName() : tableInfo.getHashKey().getAttributeName();
            keyAttributeNames.addAll(tableInfo.getIndexKeyNames(indexName));
        } else {
            hashKeyAttributeName = tableInfo.getHashKey().getAttributeName();
            keyAttributeNames.addAll(tableInfo.getBaseTableKeyNames());
        }
        HashSet<String> attributeNameSet = new HashSet<String>();
        OrderingStatus.OrderingSpec hashKeyOrderingSpec = null;
        OrderingStatus.OrderingSpec rangeKeyOrderingSpec = null;
        for (SortSpec sortSpec : orderBy.getSortSpecItems()) {
            ExprNode exprNode = sortSpec.getExpr();
            if (exprNode instanceof VariableReference) {
                OrderingStatus.OrderingSpec ordering;
                String attributeName = ((VariableReference)exprNode).getId();
                if (attributeNameSet.contains(attributeName)) continue;
                if (!keyAttributeNames.contains(attributeName)) {
                    throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Variable reference %s in ORDER BY clause must be part of the primary key").build(attributeName));
                }
                String orderingSpec = sortSpec.getOrderingSpec().name();
                OrderingStatus.OrderingSpec orderingSpec2 = ordering = OrderingStatus.OrderingSpec.ASC.orderingKeyword.equals(orderingSpec.toUpperCase()) ? OrderingStatus.OrderingSpec.ASC : OrderingStatus.OrderingSpec.DESC;
                if (hashKeyAttributeName.equals(attributeName)) {
                    hashKeyOrderingSpec = ordering;
                } else {
                    rangeKeyOrderingSpec = ordering;
                }
                attributeNameSet.add(attributeName);
                continue;
            }
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Argument to ORDER BY must be of type VariableReference. Type: %s").build(ExprNodeTranslators.extractExprIdentifierAsString(exprNode)));
        }
        return new OrderingStatus(hashKeyAttributeName, hashKeyOrderingSpec, rangeKeyOrderingSpec);
    }

    public List<Map<String, AttributeValue>> orderRecords(List<Map<String, AttributeValue>> dbRecordsAfterFiltering, OrderingStatus orderingStatus) {
        String hashKeyName = orderingStatus.getHashKeyName();
        boolean hashKeyDescending = OrderingStatus.OrderingSpec.DESC.equals((Object)orderingStatus.getHashKeyOrder());
        boolean rangeKeyDescending = OrderingStatus.OrderingSpec.DESC.equals((Object)orderingStatus.getRangeKeyOrder());
        if (hashKeyDescending || rangeKeyDescending) {
            LinkedHashMap<String, ArrayList<Map<String, AttributeValue>>> hashValueToItem = new LinkedHashMap<String, ArrayList<Map<String, AttributeValue>>>();
            for (Map<String, AttributeValue> item : dbRecordsAfterFiltering) {
                String hashValue = item.get(hashKeyName).toString();
                if (hashValueToItem.containsKey(hashValue)) {
                    ((List)hashValueToItem.get(hashValue)).add(item);
                    continue;
                }
                hashValueToItem.put(hashValue, new ArrayList<Map<String, AttributeValue>>(Collections.singletonList(item)));
            }
            ArrayList allValues = new ArrayList(hashValueToItem.values());
            Collections.reverse(allValues);
            dbRecordsAfterFiltering = allValues.stream().flatMap(Collection::stream).collect(Collectors.toList());
            if (rangeKeyDescending) {
                Collections.reverse(dbRecordsAfterFiltering);
            }
        }
        return dbRecordsAfterFiltering;
    }
}

