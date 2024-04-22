/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.monitoring;

import com.amazonaws.services.dynamodbv2.local.main.CommandLineInput;
import java.io.File;

public class TelemetryUtil {
    public static boolean isTelemetryEnabled(boolean disableTelemetry) {
        if (disableTelemetry) {
            return false;
        }
        String ddbLocalTelemetry = System.getenv("DDB_LOCAL_TELEMETRY");
        if (ddbLocalTelemetry != null) {
            return !ddbLocalTelemetry.equals("0");
        }
        return true;
    }

    public static boolean ifDocker(File dockerEnvfile) {
        return dockerEnvfile.exists();
    }

    public static boolean ifRunningAsMaven(File mavenPomfile) {
        if (System.out.getClass().getName().contains("maven")) {
            return true;
        }
        return mavenPomfile.exists();
    }

    public static String getSetupMode(CommandLineInput cli) {
        if (!cli.isInMemory() && !cli.getSharedDb()) {
            return SETUP_MODE.REGIONAL_DB.name();
        }
        if (cli.isInMemory() && cli.getSharedDb()) {
            return SETUP_MODE.IN_MEMORY_SHARED_DB.name();
        }
        if (cli.isInMemory() && !cli.getSharedDb()) {
            return SETUP_MODE.IN_MEMORY.name();
        }
        return SETUP_MODE.SHARED_DB.name();
    }

    public static String getSetupType(File dockerEnvfile, File mavenPomfile) {
        if (TelemetryUtil.ifDocker(dockerEnvfile)) {
            return SETUP_TYPE.DOCKER.name();
        }
        if (TelemetryUtil.ifRunningAsMaven(mavenPomfile)) {
            return SETUP_TYPE.MAVEN.name();
        }
        return SETUP_TYPE.STAND_ALONE_JAR.name();
    }

    public static enum SETUP_MODE {
        REGIONAL_DB,
        IN_MEMORY,
        IN_MEMORY_SHARED_DB,
        SHARED_DB;

    }

    public static enum SETUP_TYPE {
        STAND_ALONE_JAR,
        DOCKER,
        MAVEN;

    }

    public static enum EVENT_TYPE {
        SERVER_STARTUP,
        SERVER_SHUTDOWN,
        SERVER_RESTART,
        SERVER_ERROR;

    }
}

