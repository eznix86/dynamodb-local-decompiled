/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.StreamRecord
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RecordInfo {
    private static final String EVENT_ID = "EventID";
    private static final String EVENT_VERSION = "version";
    private static final String DYNAMODB = "Dynamodb";
    private String eventID;
    private String version;
    private StreamRecord dynamodb;

    public RecordInfo() {
    }

    public RecordInfo(String eventID, String eventVersion, StreamRecord dynamodb) {
        this.eventID = eventID;
        this.version = eventVersion;
        this.dynamodb = dynamodb;
    }

    @JsonProperty(value="EventID")
    public String getEventID() {
        return this.eventID;
    }

    @JsonProperty(value="EventID")
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    @JsonProperty(value="version")
    public String getVersion() {
        return this.version;
    }

    @JsonProperty(value="version")
    public void setEventVersion(String version) {
        this.version = version;
    }

    @JsonProperty(value="Dynamodb")
    public StreamRecord getDynamodb() {
        return this.dynamodb;
    }

    @JsonProperty(value="Dynamodb")
    public void setDynamodb(StreamRecord dynamodb) {
        this.dynamodb = dynamodb;
    }
}

