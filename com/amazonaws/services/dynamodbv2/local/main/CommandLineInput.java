/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.commons.cli.BasicParser
 *  org.apache.commons.cli.CommandLine
 *  org.apache.commons.cli.HelpFormatter
 *  org.apache.commons.cli.Option
 *  org.apache.commons.cli.Options
 *  org.apache.commons.cli.ParseException
 */
package com.amazonaws.services.dynamodbv2.local.main;

import java.io.File;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineInput {
    private static Options cliOptions;
    private final CommandLine cmd;
    protected boolean optimizeDBBeforeStartup;
    protected boolean delayTransientStatuses;
    private int port;
    private boolean inMemory;
    private boolean disableTelemetry;
    private String dbPath;
    private boolean sharedDb;
    private String corsParams;
    private String version = "2.4.0";
    private boolean testControl = false;

    public CommandLineInput(String[] args2) throws ParseException {
        cliOptions = new Options();
        Option portOption = new Option("port", "Specify a port number. Default is 8000");
        portOption.setArgName("port-no.");
        portOption.setArgs(1);
        portOption.setRequired(false);
        cliOptions.addOption(portOption);
        Option dbFilePathOption = new Option("dbPath", "Specify the location of your database file. Default is the current directory.");
        dbFilePathOption.setArgName("path");
        dbFilePathOption.setArgs(1);
        dbFilePathOption.setRequired(false);
        cliOptions.addOption(dbFilePathOption);
        Option dbSharedOption = new Option("sharedDb", "When specified, DynamoDB Local will use a single database instead of separate databases for each credential and region. As a result, all clients will interact with the same set of tables, regardless of their region and credential configuration. (Useful for interacting with Local through the JS Shell in addition to other SDKs)");
        dbSharedOption.setArgs(0);
        dbSharedOption.setRequired(false);
        cliOptions.addOption(dbSharedOption);
        Option inMemoryOption = new Option("inMemory", "When specified, DynamoDB Local will run in memory.");
        inMemoryOption.setArgs(0);
        inMemoryOption.setRequired(false);
        cliOptions.addOption(inMemoryOption);
        Option telemetryOption = new Option("disableTelemetry", "When specified, DynamoDB Local will not send any telemetry.");
        telemetryOption.setArgs(0);
        telemetryOption.setRequired(false);
        cliOptions.addOption(telemetryOption);
        Option versionOption = new Option("version", "Display DynamoDB Local Version.");
        telemetryOption.setArgs(0);
        telemetryOption.setRequired(false);
        cliOptions.addOption(versionOption);
        Option helpOption = new Option("help", "Display DynamoDB Local usage and options.");
        helpOption.setArgs(0);
        helpOption.setRequired(false);
        cliOptions.addOption(helpOption);
        Option corsOption = new Option("cors", "Enable CORS support for javascript against a specific allow-list list the domains separated by , use '*' for public access (default is '*')");
        corsOption.setArgs(1);
        corsOption.setRequired(false);
        cliOptions.addOption(corsOption);
        Option optimizeDBBeforeStartupOption = new Option("optimizeDbBeforeStartup", "Optimize the underlying backing store database tables before starting up the server");
        optimizeDBBeforeStartupOption.setArgs(0);
        optimizeDBBeforeStartupOption.setRequired(false);
        cliOptions.addOption(optimizeDBBeforeStartupOption);
        Option delayOnlineGSIActions = new Option("delayTransientStatuses", "When specified, DynamoDB Local will introduce delays to hold various transient table and index statuses so that it simulates actual service more closely. Currently works only for CREATING and DELETING online index statuses.");
        delayOnlineGSIActions.setArgs(0);
        delayOnlineGSIActions.setRequired(false);
        cliOptions.addOption(delayOnlineGSIActions);
        BasicParser parser = new BasicParser();
        try {
            this.cmd = parser.parse(cliOptions, args2);
        } catch (ParseException pe) {
            System.err.println("Error while parsing options. " + pe.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar [-port <port-no.>] [-inMemory] [-delayTransientStatuses] [-dbPath <path>][-sharedDb][-disableTelemetry] [-[-version] [-cors <allow-list>]", cliOptions);
            throw pe;
        }
    }

    public boolean init() {
        File dbFileDir;
        if (this.cmd.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar [-port <port-no.>] [-inMemory] [-delayTransientStatuses] [-dbPath <path>][-sharedDb][-disableTelemetry] [-[-version] [-cors <allow-list>]", cliOptions);
            return false;
        }
        if (this.cmd.hasOption("version")) {
            System.out.println("2.4.0");
            return false;
        }
        this.setOptimizeDBBeforeStartup(this.cmd.hasOption("optimizeDbBeforeStartup"));
        String portStr = this.cmd.getOptionValue("port");
        this.setPort(8000);
        if (portStr != null) {
            try {
                this.setPort(Integer.parseInt(portStr));
            } catch (NumberFormatException nfe) {
                System.err.print("Invalid Port. Port number must lie between 1 and 65535.");
                return false;
            }
        }
        if (this.port <= 1 || this.port > 65535) {
            System.err.print("Invalid Port. Port number must lie between 1 and 65535.");
            return false;
        }
        this.setInMemory(this.cmd.hasOption("inMemory"));
        this.setDisableTelemetry(this.cmd.hasOption("disableTelemetry"));
        this.setDelayTransientStatuses(this.cmd.hasOption("delayTransientStatuses"));
        this.setDbPath(this.cmd.getOptionValue("dbPath"));
        if (this.optimizeDBBeforeStartup && this.dbPath == null) {
            System.err.print("optimizeDbBeforeStartup option must be used with dbPath to determine the location of database files to optimize.");
            return false;
        }
        if (this.inMemory && this.dbPath != null) {
            System.err.print("dbPath and inMemory cannot be set at the same time.");
            return false;
        }
        if (this.dbPath != null && !(dbFileDir = new File(this.dbPath)).isDirectory()) {
            System.err.print("Invalid directory for database creation.");
            return false;
        }
        this.setSharedDb(this.cmd.hasOption("sharedDb"));
        this.setCorsParams(this.cmd.getOptionValue("cors"));
        return true;
    }

    public int getPort() {
        return this.port;
    }

    private void setPort(int port) {
        this.port = port;
    }

    public boolean isInMemory() {
        return this.inMemory;
    }

    private void setInMemory(boolean inMemory) {
        this.inMemory = inMemory;
    }

    public boolean isDisableTelemetry() {
        return this.disableTelemetry;
    }

    public void setDisableTelemetry(boolean disableTelemetry) {
        this.disableTelemetry = disableTelemetry;
    }

    public String getDbPath() {
        return this.dbPath;
    }

    private void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public boolean getSharedDb() {
        return this.sharedDb;
    }

    private void setSharedDb(boolean sharedDb) {
        this.sharedDb = sharedDb;
    }

    public String getCorsParams() {
        return this.corsParams;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private void setCorsParams(String corsParams) {
        this.corsParams = corsParams;
    }

    private void setDelayTransientStatuses(boolean delayTransientStatuses) {
        this.delayTransientStatuses = delayTransientStatuses;
    }

    public boolean shouldOptimizeDBBeforeStartup() {
        return this.optimizeDBBeforeStartup;
    }

    public void setOptimizeDBBeforeStartup(boolean optimizeDBBeforeStartup) {
        this.optimizeDBBeforeStartup = optimizeDBBeforeStartup;
    }

    void setTestControl(boolean testControlEnabled) {
        this.testControl = testControlEnabled;
    }

    public boolean shouldDelayTransientStatuses() {
        return this.delayTransientStatuses;
    }

    boolean isTestControlEnabled() {
        return this.testControl;
    }
}

