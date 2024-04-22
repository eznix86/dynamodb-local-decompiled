/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 *  org.eclipse.jetty.server.Handler
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.server.handler.ContextHandler
 *  org.eclipse.jetty.server.handler.ContextHandlerCollection
 *  software.amazon.awssdk.services.pinpoint.model.PinpointException
 */
package com.amazonaws.services.dynamodbv2.local.server;

import com.amazonaws.services.dynamodbv2.local.monitoring.Telemetry;
import com.amazonaws.services.dynamodbv2.local.monitoring.TelemetryUtil;
import com.amazonaws.services.dynamodbv2.local.server.AbstractLocalDynamoDBServerHandler;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import java.net.SocketException;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import software.amazon.awssdk.services.pinpoint.model.PinpointException;

public class DynamoDBProxyServer {
    protected static Logger logger = LogManager.getLogger(DynamoDBProxyServer.class);
    private final int port;
    private final Server server;
    private final AbstractLocalDynamoDBServerHandler serverHandler;

    public DynamoDBProxyServer(int port, AbstractLocalDynamoDBServerHandler a) {
        this.port = port;
        this.server = new Server(this.port);
        this.server.setHandler((Handler)this.setUpHandler(a));
        this.serverHandler = a;
    }

    public ContextHandlerCollection setUpHandler(AbstractLocalDynamoDBServerHandler a) {
        ContextHandler contextDDB = new ContextHandler();
        contextDDB.setHandler((Handler)a);
        ContextHandlerCollection handler = new ContextHandlerCollection(new ContextHandler[0]);
        handler.addHandler((Handler)contextDDB);
        return handler;
    }

    private void sendServerStartTelemetry(TelemetryUtil.EVENT_TYPE eventType) {
        Optional<Telemetry> telemetry = Telemetry.getTelemetry();
        if (telemetry.isPresent()) {
            telemetry.get().emitEvent(eventType);
        }
    }

    public void start() throws Exception {
        block7: {
            try {
                this.server.start();
                try {
                    this.sendServerStartTelemetry(TelemetryUtil.EVENT_TYPE.SERVER_STARTUP);
                } catch (PinpointException pinpointException) {
                    if (pinpointException.statusCode() != 403 || !pinpointException.getMessage().contains("The security token included in the request is expired")) break block7;
                    try {
                        this.sendServerStartTelemetry(TelemetryUtil.EVENT_TYPE.SERVER_STARTUP);
                    } catch (Exception e) {
                        logger.debug("Failed to emit telemetry event", (Throwable)e);
                    }
                } catch (Exception e) {
                    logger.debug("Failed to emit telemetry event", (Throwable)e);
                }
            } catch (SocketException e) {
                System.err.printf("Could not start server on port %d: %s%n", this.port, e.getMessage());
                this.server.stop();
            }
        }
    }

    public void safeStart() throws Exception {
        this.server.start();
    }

    public void join() throws Exception {
        this.server.join();
    }

    public void stop() throws Exception {
        this.server.stop();
        this.serverHandler.close();
    }
}

