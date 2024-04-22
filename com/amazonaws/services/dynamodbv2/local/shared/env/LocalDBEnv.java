/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.shared.env;

import com.amazonaws.services.dynamodbv2.dbenv.DbConfig;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbExecutionError;
import com.amazonaws.services.dynamodbv2.dbenv.DbInternalError;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.ExpressionExecutionException;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class LocalDBEnv
implements DbEnv {
    private static final Map<DbConfig, Object> configMap;
    protected Logger logger = LogManager.getLogger(LocalDBEnv.class);

    @Override
    public void dbAssert(boolean check, String origin, String message, Object ... keyValuePairs) {
        if (!check) {
            new AWSExceptionFactory().INTERNAL_FAILURE.throwAsException();
        }
    }

    @Override
    public void logError(String name, String txt, Object ... keyValuePairs) {
    }

    @Override
    public int getConfigInt(DbConfig configKey) {
        return (Integer)configMap.get((Object)configKey);
    }

    @Override
    public void throwInternalError(DbInternalError e, Object ... keyValuePairs) {
        if (e != null) {
            new AWSExceptionFactory().INTERNAL_FAILURE.throwAsException();
        }
    }

    @Override
    public void throwValidationError(DbValidationError e, Object ... keyValuePairs) {
        if (e != null) {
            String errorMessage = this.validationErrorStringBuilder(e.getMessage(), keyValuePairs);
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, errorMessage);
        }
    }

    @Override
    public void throwExecutionError(DbExecutionError e, Object ... keyValuePairs) {
        if (e != null) {
            String errorMessage = this.validationErrorStringBuilder(e.getMessage(), keyValuePairs);
            throw new ExpressionExecutionException(e, errorMessage);
        }
    }

    private String validationErrorStringBuilder(String error, Object ... keyValuePairs) {
        int n = keyValuePairs.length;
        StringBuilder stringBuilder = new StringBuilder(error.length() + 40 * n);
        stringBuilder.append(error).append(";");
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(" ").append(keyValuePairs[i]);
            if (++i >= n) continue;
            stringBuilder.append(": ").append(keyValuePairs[i]);
        }
        return stringBuilder.toString();
    }

    @Override
    public Collection<String> getConfigStringCollection(DbConfig configKey) {
        String configKeyValue = configMap.get((Object)configKey).toString();
        if (configKeyValue.isEmpty()) {
            return Collections.emptyList();
        }
        String[] segments = configKeyValue.split(",");
        ArrayList<String> finalSegmentList = new ArrayList<String>();
        for (String segment : segments) {
            String trimmed = segment.trim();
            if (trimmed.isEmpty()) continue;
            finalSegmentList.add(trimmed);
        }
        return finalSegmentList;
    }

    static {
        HashMap<DbConfig, Object> modifiableConfigMap = new HashMap<DbConfig, Object>(DbConfig.values().length);
        modifiableConfigMap.put(DbConfig.MAX_DOC_PATH_DEPTH, 32);
        modifiableConfigMap.put(DbConfig.MAX_OPERATOR_COUNT, 300);
        modifiableConfigMap.put(DbConfig.MAX_EXPRESSION_SIZE, 4096);
        modifiableConfigMap.put(DbConfig.MAX_EXPRESSION_TREE_SIZE, 0x100000);
        modifiableConfigMap.put(DbConfig.MAX_PARAMETER_KEY_SIZE, 255);
        modifiableConfigMap.put(DbConfig.MAX_NUM_OPERANDS_FOR_IN, 100);
        modifiableConfigMap.put(DbConfig.DISABLED_FUNCTIONS, "");
        configMap = Collections.unmodifiableMap(modifiableConfigMap);
    }
}

