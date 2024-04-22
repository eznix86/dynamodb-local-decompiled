/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.rr;

import com.amazonaws.services.dynamodbv2.dbenv.DbConfig;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ExpressionsWrapperBase {
    private final Map<String, Integer> nameParameterUsage = new HashMap<String, Integer>();
    private final Map<String, Integer> valueParameterUsage = new HashMap<String, Integer>();
    private final Set<String> topLevelFieldsWithNestedAccess = new HashSet<String>();
    private OperatorCounter operatorCounter = new OperatorCounter();
    private NodeCounter nodeCounter = new NodeCounter();
    private MaxPathDepthCounter maxPathDepthCounter = new MaxPathDepthCounter();
    private int cumulativeSize;

    public Map<String, Integer> getNameParameterUsage() {
        return this.nameParameterUsage;
    }

    public Map<String, Integer> getValueParameterUsage() {
        return this.valueParameterUsage;
    }

    public int validateCombinedExpressionSize(int expressionSize, Map<String, Integer> nameParameterSizes, Map<String, Integer> valueParameterSizes, DbEnv dbEnv) {
        String TRACE_HEADER = "validateCombinedExpressionSize";
        dbEnv.dbAssert(this.nameParameterUsage.size() == 0 || nameParameterSizes != null, "validateCombinedExpressionSize", "names size map should not be null if name parameters are used", "nameParameterUsage", this.nameParameterUsage, "nameParameterSizes", nameParameterSizes);
        dbEnv.dbAssert(this.valueParameterUsage.size() == 0 || valueParameterSizes != null, "validateCombinedExpressionSize", "values size map should not be null if value parameters are used", "valueParameterUsage", this.valueParameterUsage, "valueParameterSizes", valueParameterSizes);
        this.cumulativeSize = expressionSize;
        this.cumulativeSize += ExpressionsWrapperBase.getParameterUsageSize(this.nameParameterUsage, nameParameterSizes, dbEnv);
        this.cumulativeSize += ExpressionsWrapperBase.getParameterUsageSize(this.valueParameterUsage, valueParameterSizes, dbEnv);
        if (this.cumulativeSize > dbEnv.getConfigInt(DbConfig.MAX_EXPRESSION_TREE_SIZE)) {
            dbEnv.throwValidationError(DbValidationError.EXPRESSION_SIZE_EXCEEDED, new Object[0]);
        }
        return this.cumulativeSize;
    }

    public static int getParameterUsageSize(Map<String, Integer> parameterUsage, Map<String, Integer> parameterSizes, DbEnv dbEnv) {
        String TRACE_HEADER = "getParameterUsageSize";
        int cumulativeSize = 0;
        for (Map.Entry<String, Integer> entry : parameterUsage.entrySet()) {
            String key = entry.getKey();
            Integer count = entry.getValue();
            dbEnv.dbAssert(key != null && count != null, "getParameterUsageSize", "key nor count should be null", "key", key, "count", count);
            Integer size = parameterSizes.get(key);
            dbEnv.dbAssert(size != null, "getParameterUsageSize", "parameter missing error should have already been thrown if undefined", "key", key);
            cumulativeSize += count * size;
        }
        return cumulativeSize;
    }

    public Set<String> getTopLevelFieldsWithNestedAccess() {
        return this.topLevelFieldsWithNestedAccess;
    }

    public OperatorCounter getOperatorCounter() {
        return this.operatorCounter;
    }

    public NodeCounter getNodeCounter() {
        return this.nodeCounter;
    }

    public MaxPathDepthCounter getMaxPathDepthCounter() {
        return this.maxPathDepthCounter;
    }

    public int getCumulativeSize() {
        return this.cumulativeSize;
    }

    public static class OperatorCounter
    extends Counter {
    }

    public static class NodeCounter
    extends Counter {
    }

    public static class MaxPathDepthCounter
    extends Counter {
    }

    private static abstract class Counter {
        protected int value = 0;

        private Counter() {
        }

        public int value() {
            return this.value;
        }

        public int set(int value) {
            this.value = value;
            return this.value;
        }

        public int increment() {
            return ++this.value;
        }
    }
}

