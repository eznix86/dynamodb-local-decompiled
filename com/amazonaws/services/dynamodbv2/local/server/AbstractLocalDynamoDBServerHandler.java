/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 *  org.eclipse.jetty.server.handler.AbstractHandler
 */
package com.amazonaws.services.dynamodbv2.local.server;

import com.amazonaws.services.dynamodbv2.local.server.DynamoDBRequestHandler;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.amazonaws.services.dynamodbv2.local.shared.mapper.DynamoDBObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.handler.AbstractHandler;

public abstract class AbstractLocalDynamoDBServerHandler
extends AbstractHandler {
    static final Logger logger = LogManager.getLogger(AbstractLocalDynamoDBServerHandler.class);
    protected final DynamoDBObjectMapper jsonMapper = new DynamoDBObjectMapper();
    protected final DynamoDBRequestHandler primaryHandler;
    protected final Map<String, DynamoDBRequestHandler> secondaryHandlers;

    public AbstractLocalDynamoDBServerHandler(DynamoDBRequestHandler pHandler) {
        this.primaryHandler = pHandler;
        this.secondaryHandlers = new HashMap<String, DynamoDBRequestHandler>();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addHandler(String name, DynamoDBRequestHandler handler) {
        Map<String, DynamoDBRequestHandler> map2 = this.secondaryHandlers;
        synchronized (map2) {
            if (this.secondaryHandlers.containsKey(name)) {
                throw new IllegalArgumentException(name + " is already in use.");
            }
            this.secondaryHandlers.put(name, handler);
        }
    }

    public AbstractLocalDynamoDBServerHandler withHandler(String name, DynamoDBRequestHandler handler) {
        this.secondaryHandlers.put(name, handler);
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DynamoDBRequestHandler removeHandler(String name) {
        Map<String, DynamoDBRequestHandler> map2 = this.secondaryHandlers;
        synchronized (map2) {
            return this.secondaryHandlers.remove(name);
        }
    }

    public void close() {
        try {
            if (this.primaryHandler != null) {
                this.primaryHandler.shutdown();
            }
        } catch (Exception e) {
            logger.error("Primary handler shutdown gave an Exception.", (Throwable)e);
        }
        for (DynamoDBRequestHandler h : this.secondaryHandlers.values()) {
            try {
                h.shutdown();
            } catch (Exception e) {
                logger.error("Secondary handler shutdown gave an Exception.", (Throwable)e);
            }
        }
    }
}

