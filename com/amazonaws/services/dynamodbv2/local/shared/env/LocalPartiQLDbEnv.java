/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.shared.env;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalDBEnv;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.dbenv.PartiQLLogger;
import java.util.function.BiFunction;
import org.apache.logging.log4j.Logger;

public class LocalPartiQLDbEnv
extends LocalDBEnv
implements PartiQLDbEnv {
    @Override
    public void dbPqlAssert(boolean check, String origin, String message) {
        this.dbPqlAssert(check, origin, message, null);
    }

    @Override
    public void dbPqlAssert(boolean check, String origin, String message, Object object1) {
        this.dbPqlAssert(check, origin, message, object1, null);
    }

    @Override
    public void dbPqlAssert(boolean check, String origin, String message, Object object1, Object object2) {
        if (!check) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DynamoDBPartiQL - ").append(origin).append(" - ").append(message).append(";");
            if (object1 != null) {
                stringBuilder.append(" ").append(object1);
            }
            if (object2 != null) {
                stringBuilder.append(": ").append(object2);
            }
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, stringBuilder.toString());
        }
    }

    @Override
    public PartiQLLogger createPartiQLLogger() {
        return new DynamoDBPartiQLLogger(LocalPartiQLDbEnv.class);
    }

    @Override
    public RuntimeException createValidationError(String msg, BiFunction<String, Object[], String> messageBuilderFunction, Object ... keyValuePairs) {
        String errorMessage = messageBuilderFunction.apply(msg, keyValuePairs);
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, errorMessage);
    }

    @Override
    public RuntimeException createValidationError(String msg) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, msg);
    }

    @Override
    public RuntimeException createInternalServerError(String msg) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, msg);
    }

    @Override
    public RuntimeException createInternalServerError(String msg, String specificMessage) {
        if (specificMessage == null || specificMessage.length() == 0) {
            return this.createInternalServerError(msg);
        }
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, String.format("%s: %s", msg, specificMessage));
    }

    public static class DynamoDBPartiQLLogger
    implements PartiQLLogger {
        private final Logger logger;

        public DynamoDBPartiQLLogger(Class<?> clazz) {
            this.logger = LogManager.getLogger(clazz);
        }

        @Override
        public void fatal(String name, String txt, Object ... keyValuePairs) {
            this.logger.fatal(name, (Object)txt, (Object)keyValuePairs);
        }
    }
}

