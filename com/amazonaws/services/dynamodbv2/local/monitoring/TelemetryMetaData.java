/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TelemetryMetaData {
    @JsonProperty(value="installationId")
    private String installationId;
    @JsonProperty(value="telemetryEnabled")
    private String telemetryEnabled;

    public TelemetryMetaData() {
        this.installationId = "";
        this.telemetryEnabled = "";
    }

    public TelemetryMetaData(String installationId, String telemetryEnabled) {
        this.installationId = installationId;
        this.telemetryEnabled = telemetryEnabled;
    }

    public String getInstallationId() {
        return this.installationId;
    }

    public String toString() {
        return "TelemetryMetaData{installationId='" + this.installationId + "', telemetryEnabled='" + this.telemetryEnabled + "'}";
    }
}

