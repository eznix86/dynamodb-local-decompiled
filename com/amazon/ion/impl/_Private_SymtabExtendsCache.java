/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl._Private_Utils;

public final class _Private_SymtabExtendsCache {
    private SymbolTable myWriterSymtab;
    private SymbolTable myReaderSymtab;
    private int myWriterSymtabMaxId;
    private int myReaderSymtabMaxId;
    private boolean myResult;

    public boolean symtabsCompat(SymbolTable writerSymtab, SymbolTable readerSymtab) {
        assert (writerSymtab != null && readerSymtab != null) : "writer's and reader's current symtab cannot be null";
        if (this.myWriterSymtab == writerSymtab && this.myReaderSymtab == readerSymtab && this.myWriterSymtabMaxId == writerSymtab.getMaxId() && this.myReaderSymtabMaxId == readerSymtab.getMaxId()) {
            return this.myResult;
        }
        this.myResult = _Private_Utils.symtabExtends(writerSymtab, readerSymtab);
        this.myWriterSymtab = writerSymtab;
        this.myReaderSymtab = readerSymtab;
        this.myWriterSymtabMaxId = writerSymtab.getMaxId();
        this.myReaderSymtabMaxId = readerSymtab.getMaxId();
        return this.myResult;
    }
}

