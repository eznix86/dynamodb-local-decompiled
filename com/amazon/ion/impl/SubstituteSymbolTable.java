/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonWriter;
import com.amazon.ion.ReadOnlyValueException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.impl.SymbolTableReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

final class SubstituteSymbolTable
implements SymbolTable {
    private final SymbolTable myOriginalSymTab;
    private final String myName;
    private final int myVersion;
    private final int myMaxId;

    SubstituteSymbolTable(String name, int version, int maxId) {
        this.myOriginalSymTab = null;
        this.myName = name;
        this.myVersion = version;
        this.myMaxId = maxId;
    }

    SubstituteSymbolTable(SymbolTable original, int version, int maxId) {
        assert (original.isSharedTable() && !original.isSystemTable());
        assert (original.getVersion() != version || original.getMaxId() != maxId);
        this.myOriginalSymTab = original;
        this.myName = original.getName();
        this.myVersion = version;
        this.myMaxId = maxId;
    }

    @Override
    public String getName() {
        return this.myName;
    }

    @Override
    public int getVersion() {
        return this.myVersion;
    }

    @Override
    public boolean isSubstitute() {
        return true;
    }

    @Override
    public boolean isLocalTable() {
        return false;
    }

    @Override
    public boolean isSharedTable() {
        return true;
    }

    @Override
    public boolean isSystemTable() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public void makeReadOnly() {
    }

    @Override
    public SymbolTable getSystemSymbolTable() {
        return null;
    }

    @Override
    public String getIonVersionId() {
        return null;
    }

    @Override
    public SymbolTable[] getImportedTables() {
        return null;
    }

    @Override
    public int getImportedMaxId() {
        return 0;
    }

    @Override
    public int getMaxId() {
        return this.myMaxId;
    }

    @Override
    public SymbolToken intern(String text) {
        SymbolToken tok = this.find(text);
        if (tok == null) {
            throw new ReadOnlyValueException(SymbolTable.class);
        }
        return tok;
    }

    @Override
    public SymbolToken find(String text) {
        SymbolToken tok = null;
        if (this.myOriginalSymTab != null && (tok = this.myOriginalSymTab.find(text)) != null && tok.getSid() > this.myMaxId) {
            tok = null;
        }
        return tok;
    }

    @Override
    public int findSymbol(String text) {
        int sid = -1;
        if (this.myOriginalSymTab != null && (sid = this.myOriginalSymTab.findSymbol(text)) > this.myMaxId) {
            sid = -1;
        }
        return sid;
    }

    @Override
    public String findKnownSymbol(int id) {
        if (id > this.myMaxId || this.myOriginalSymTab == null) {
            return null;
        }
        return this.myOriginalSymTab.findKnownSymbol(id);
    }

    @Override
    public Iterator<String> iterateDeclaredSymbolNames() {
        Iterator<String> originalIterator = this.myOriginalSymTab != null ? this.myOriginalSymTab.iterateDeclaredSymbolNames() : Collections.EMPTY_LIST.iterator();
        return new SymbolIterator(originalIterator);
    }

    @Override
    public void writeTo(IonWriter writer) throws IOException {
        SymbolTableReader reader = new SymbolTableReader(this);
        writer.writeValues(reader);
    }

    private final class SymbolIterator
    implements Iterator<String> {
        private Iterator<String> myOriginalIterator;
        private int myIndex = 0;

        SymbolIterator(Iterator<String> originalIterator) {
            this.myOriginalIterator = originalIterator;
        }

        @Override
        public boolean hasNext() {
            return this.myIndex < SubstituteSymbolTable.this.myMaxId;
        }

        @Override
        public String next() {
            if (this.myIndex < SubstituteSymbolTable.this.myMaxId) {
                String name = null;
                if (this.myOriginalIterator.hasNext()) {
                    name = this.myOriginalIterator.next();
                }
                ++this.myIndex;
                return name;
            }
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

