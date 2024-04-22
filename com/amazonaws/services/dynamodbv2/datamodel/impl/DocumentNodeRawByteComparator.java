/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel.impl;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import java.util.Comparator;

public class DocumentNodeRawByteComparator
implements Comparator<byte[]> {
    private final DocumentFactory docFactory;

    public DocumentNodeRawByteComparator(DocumentFactory docFactory) {
        this.docFactory = docFactory;
    }

    @Override
    public int compare(byte[] arg0, byte[] arg1) {
        DocumentNode doc0 = this.docFactory.makeBinary(arg0);
        DocumentNode doc1 = this.docFactory.makeBinary(arg1);
        return doc0.compare(doc1);
    }
}

