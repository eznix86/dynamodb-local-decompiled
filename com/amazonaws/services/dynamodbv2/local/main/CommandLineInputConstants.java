/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.main;

public final class CommandLineInputConstants {
    public static final String CORS_SUPPORT_OPTION = "cors";
    public static final String DB_FILE_PATH_OPTION = "dbPath";
    public static final String DELAY_TRANSIENT_STATUSES_OPTION = "delayTransientStatuses";
    public static final String HELP_OPTION = "help";
    public static final String IN_MEMORY_OPTION = "inMemory";
    public static final String TELEMETRY_OPTION = "disableTelemetry";
    public static final String VERSION_OPTION = "version";
    public static final String OPTIMIZE_DB_BEFORE_STARTUP_OPTION = "optimizeDbBeforeStartup";
    public static final String PORT_OPTION = "port";
    public static final String SHARED_DB_OPTION = "sharedDb";
    public static final String TEST_CONTROL_OPTION = "testControl";
    public static final String PORT_OPTION_ARG = "port-no.";
    public static final String DB_FILE_PATH_OPTION_ARG = "path";
    public static final String CORS_SUPPORT_ARG = "allow-list";
    public static final String CORS_ARGUMENT_SEPERATOR = ",";
    public static final String CORS_SUPPORT_OPTION_DESC = "Enable CORS support for javascript against a specific allow-list list the domains separated by , use '*' for public access (default is '*')";
    public static final String DB_FILE_PATH_OPTION_DESC = "Specify the location of your database file. Default is the current directory.";
    public static final String DELAY_TRANSIENT_STATUSES_OPTION_DESC = "When specified, DynamoDB Local will introduce delays to hold various transient table and index statuses so that it simulates actual service more closely. Currently works only for CREATING and DELETING online index statuses.";
    public static final String HELP_OPTION_DESC = "Display DynamoDB Local usage and options.";
    public static final String IN_MEMORY_OPTION_DESC = "When specified, DynamoDB Local will run in memory.";
    public static final String TELEMETRY_OPTION_DESC = "When specified, DynamoDB Local will not send any telemetry.";
    public static final String VERSION_OPTION_DESC = "Display DynamoDB Local Version.";
    public static final String OPTIMIZE_DB_BEFORE_STARTUP_DESC = "Optimize the underlying backing store database tables before starting up the server";
    public static final String PORT_OPTION_DESC = "Specify a port number. Default is 8000";
    public static final String SHARED_DB_OPTION_DESC = "When specified, DynamoDB Local will use a single database instead of separate databases for each credential and region. As a result, all clients will interact with the same set of tables, regardless of their region and credential configuration. (Useful for interacting with Local through the JS Shell in addition to other SDKs)";
    public static final String INVALID_PORT_ERR = "Invalid Port. Port number must lie between 1 and 65535.";
    public static final String DB_PATH_IN_MEMORY_CONFLICT_ERR = "dbPath and inMemory cannot be set at the same time.";
    public static final String INVALID_DIRECTORY_ERR = "Invalid directory for database creation.";
    public static final String PARSING_ERR = "Error while parsing options.";
    public static final String OPTIMIZE_DB_BEFORE_STARTUP_USAGE_ERR = "optimizeDbBeforeStartup option must be used with dbPath to determine the location of database files to optimize.";
    public static final String DDB_LOCAL_USAGE_STRING = "java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar [-port <port-no.>] [-inMemory] [-delayTransientStatuses] [-dbPath <path>][-sharedDb][-disableTelemetry] [-[-version] [-cors <allow-list>]";
    public static final int ZERO_OPTION = 0;
    public static final int ONE_OPTION = 1;
    public static final int PORT_MAX = 65535;
    public static final int PORT_MIN = 1;
}

