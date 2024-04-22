/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.spi.ExtendedLogger
 */
package com.amazonaws.services.dynamodbv2.local.shared.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ExtendedLogger;

public class LogManager {
    private static final boolean LOGGING_IS_ENABLED = true;
    private static final ExtendedLogger NO_OP_LOGGER = (ExtendedLogger)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{ExtendedLogger.class}, new InvocationHandler(){

        @Override
        public Object invoke(Object proxy, Method method, Object[] args2) {
            String name = method.getName();
            if (name.equals("getLevel")) {
                return Level.OFF;
            }
            if (name.equals("getName")) {
                return "NullLogger";
            }
            if (name.equals("isEnabled")) {
                return false;
            }
            return null;
        }
    });

    public static Logger getLogger(Class<?> clazz) {
        return org.apache.logging.log4j.LogManager.getLogger(clazz);
    }
}

