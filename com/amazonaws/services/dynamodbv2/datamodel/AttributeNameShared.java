/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;

public interface AttributeNameShared {
    public byte[] getBytes();

    public int getLengthInUTF8Bytes();

    public DocPathElement toDocPathElement();
}

