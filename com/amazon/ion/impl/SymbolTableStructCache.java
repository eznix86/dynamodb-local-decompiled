/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonList;
import com.amazon.ion.IonString;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.ValueFactory;
import java.util.Iterator;

class SymbolTableStructCache {
    private final SymbolTable symbolTable;
    private final SymbolTable[] importedTables;
    private final int firstLocalSid;
    private IonStruct image;

    SymbolTableStructCache(SymbolTable symbolTable, SymbolTable[] importedTables, IonStruct image) {
        this.symbolTable = symbolTable;
        this.importedTables = importedTables;
        this.firstLocalSid = symbolTable.getImportedMaxId() + 1;
        this.image = image;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IonStruct getIonRepresentation(ValueFactory factory) {
        SymbolTableStructCache symbolTableStructCache = this;
        synchronized (symbolTableStructCache) {
            if (this.image == null) {
                this.makeIonRepresentation(factory);
            }
            return this.image;
        }
    }

    public boolean hasStruct() {
        return this.image != null;
    }

    private void makeIonRepresentation(ValueFactory factory) {
        this.image = factory.newEmptyStruct();
        this.image.addTypeAnnotation("$ion_symbol_table");
        if (this.importedTables.length > 0) {
            int i;
            int n = i = this.importedTables[0].isSystemTable() ? 1 : 0;
            if (i < this.importedTables.length) {
                IonList importsList = factory.newEmptyList();
                while (i < this.importedTables.length) {
                    SymbolTable importedTable = this.importedTables[i];
                    IonStruct importStruct = factory.newEmptyStruct();
                    importStruct.add("name", (IonValue)factory.newString(importedTable.getName()));
                    importStruct.add("version", (IonValue)factory.newInt(importedTable.getVersion()));
                    importStruct.add("max_id", (IonValue)factory.newInt(importedTable.getMaxId()));
                    importsList.add(importStruct);
                    ++i;
                }
                this.image.add("imports", (IonValue)importsList);
            }
        }
        if (this.symbolTable.getMaxId() > this.symbolTable.getImportedMaxId()) {
            Iterator<String> localSymbolIterator = this.symbolTable.iterateDeclaredSymbolNames();
            int sid = this.symbolTable.getImportedMaxId() + 1;
            while (localSymbolIterator.hasNext()) {
                this.addSymbol(localSymbolIterator.next(), sid);
                ++sid;
            }
        }
    }

    void addSymbol(String symbolName, int sid) {
        assert (sid >= this.firstLocalSid);
        IonSystem sys = this.image.getSystem();
        IonValue syms = this.image.get("symbols");
        while (syms != null && syms.getType() != IonType.LIST) {
            this.image.remove(syms);
            syms = this.image.get("symbols");
        }
        if (syms == null) {
            syms = sys.newEmptyList();
            this.image.put("symbols", syms);
        }
        int thisOffset = sid - this.firstLocalSid;
        IonString name = sys.newString(symbolName);
        ((IonList)syms).add(thisOffset, name);
    }
}

