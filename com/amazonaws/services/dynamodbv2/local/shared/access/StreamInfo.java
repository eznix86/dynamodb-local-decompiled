/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.StreamViewType
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import java.util.Collection;

public class StreamInfo {
    private StreamViewType streamViewType;
    private Collection<KeySchemaElement> keySchema;

    public StreamInfo() {
    }

    public StreamInfo(StreamViewType streamViewType, Collection<KeySchemaElement> keySchema) {
        this.streamViewType = streamViewType;
        this.keySchema = keySchema;
    }

    public StreamViewType getStreamViewType() {
        return this.streamViewType;
    }

    public void setStreamViewType(StreamViewType streamViewType) {
        this.streamViewType = streamViewType;
    }

    public Collection<KeySchemaElement> getKeySchema() {
        return this.keySchema;
    }

    public void setKeySchema(Collection<KeySchemaElement> keySchema) {
        this.keySchema = keySchema;
    }
}

