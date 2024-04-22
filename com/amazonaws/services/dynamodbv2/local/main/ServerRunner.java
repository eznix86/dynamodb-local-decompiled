/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.commons.cli.ParseException
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.main;

import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.main.CommandLineInput;
import com.amazonaws.services.dynamodbv2.local.monitoring.Telemetry;
import com.amazonaws.services.dynamodbv2.local.monitoring.TelemetryUtil;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.local.server.LocalDynamoDBRequestHandler;
import com.amazonaws.services.dynamodbv2.local.server.LocalDynamoDBServerHandler;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import java.io.File;
import java.io.FilenameFilter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.ParseException;

public class ServerRunner {
    public static final int DEFAULT_PORT = 8000;
    public static final String FATAL_ERROR = "A fatal error occured";
    private static final String WARN = "WARN";
    private static final String ORG_ECLIPSE_JETTY_LEVEL = "org.eclipse.jetty.LEVEL";
    private static final String ORG_ECLIPSE_JETTY_UTIL_LOG_ANNOUNCE = "org.eclipse.jetty.util.log.announce";
    private static final String CLI_ERROR = "Error processing the supplied command line arguments";
    protected static org.apache.logging.log4j.Logger logger = LogManager.getLogger(ServerRunner.class);

    public static void main(String[] args2) throws Exception {
        CommandLineInput cli = null;
        String DOCKER_ENV_FILE_NAME = "/.dockerenv";
        String MAVEN_POM_FILE_NAME = "pom.xml";
        try {
            cli = new CommandLineInput(args2);
            if (!cli.init()) {
                return;
            }
        } catch (ParseException e) {
            System.err.print(CLI_ERROR);
            return;
        }
        try {
            DynamoDBProxyServer server = ServerRunner.createServerFromCommandLineArgs(args2);
            try {
                File dockerEnvfile = new File("/.dockerenv");
                File mavenPomfile = new File("pom.xml");
                Telemetry.configureTelemetry(TelemetryUtil.getSetupType(dockerEnvfile, mavenPomfile), TelemetryUtil.getSetupMode(cli), TelemetryUtil.isTelemetryEnabled(cli.isDisableTelemetry()));
            } catch (Exception e) {
                logger.debug("Error setting up telemetry", (Throwable)e);
            }
            server.start();
        } catch (DynamoDBLocalServiceException e) {
            System.err.print(FATAL_ERROR + e);
        } catch (ParseException e) {
            System.err.print(CLI_ERROR);
        } catch (IllegalArgumentException e) {
            System.err.print(CLI_ERROR);
        }
    }

    public static DynamoDBProxyServer createServerFromCommandLineArgs(String[] args2) throws DynamoDBLocalServiceException, ParseException {
        CommandLineInput cli = new CommandLineInput(args2);
        if (!cli.init()) {
            throw new IllegalArgumentException("CLI was not initialized");
        }
        if (cli.shouldOptimizeDBBeforeStartup()) {
            File[] allDBFiles;
            for (File dbFile : allDBFiles = new File(cli.getDbPath()).listFiles(new FilenameFilter(){

                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".db");
                }
            })) {
                if (!dbFile.isFile()) continue;
                SQLiteDBAccess dbAccess = new SQLiteDBAccess(dbFile);
                dbAccess.optimizeDBBeforeStartup();
                dbAccess.close();
            }
        }
        String sb = "Initializing DynamoDB Local with the following configuration:\nPort:\t" + cli.getPort() + "\nInMemory:\t" + cli.isInMemory() + "\nVersion:\t" + cli.getVersion() + "\nDbPath:\t" + cli.getDbPath() + "\nSharedDb:\t" + cli.getSharedDb() + "\nshouldDelayTransientStatuses:\t" + cli.shouldDelayTransientStatuses() + "\nCorsParams:\t" + cli.getCorsParams() + "\n";
        System.out.println(sb);
        return ServerRunner.createServer(cli);
    }

    public static DynamoDBProxyServer createServer(CommandLineInput cli) {
        try {
            Telemetry.configureTelemetry(TelemetryUtil.SETUP_TYPE.MAVEN.name(), TelemetryUtil.getSetupMode(cli), TelemetryUtil.isTelemetryEnabled(cli.isDisableTelemetry()));
        } catch (Exception e) {
            logger.debug("Error setting up telemetry", (Throwable)e);
        }
        return new DynamoDBProxyServer(cli.getPort(), new LocalDynamoDBServerHandler(new LocalDynamoDBRequestHandler(0, cli.isInMemory(), cli.getDbPath(), cli.getSharedDb(), cli.shouldDelayTransientStatuses()), cli.getCorsParams()));
    }

    static {
        System.setProperty(ORG_ECLIPSE_JETTY_UTIL_LOG_ANNOUNCE, "false");
        System.setProperty(ORG_ECLIPSE_JETTY_LEVEL, WARN);
        Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.WARNING);
    }
}

