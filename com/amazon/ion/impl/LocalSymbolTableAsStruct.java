/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonStruct;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.ValueFactory;
import com.amazon.ion.impl.IonReaderTreeSystem;
import com.amazon.ion.impl.LocalSymbolTable;
import com.amazon.ion.impl.LocalSymbolTableImports;
import com.amazon.ion.impl.SymbolTableAsStruct;
import com.amazon.ion.impl.SymbolTableStructCache;
import com.amazon.ion.impl._Private_LocalSymbolTableFactory;
import java.util.ArrayList;
import java.util.List;

@Deprecated
class LocalSymbolTableAsStruct
extends LocalSymbolTable
implements SymbolTableAsStruct {
    private final SymbolTableStructCache structCache;

    private LocalSymbolTableAsStruct(LocalSymbolTableImports imports, List<String> symbolsList, IonStruct image) {
        super(imports, symbolsList);
        this.structCache = new SymbolTableStructCache(this, imports.getImportedTablesNoCopy(), image);
    }

    @Override
    int putSymbol(String symbolName) {
        int sid = super.putSymbol(symbolName);
        if (this.structCache.hasStruct()) {
            this.structCache.addSymbol(symbolName, sid);
        }
        return sid;
    }

    @Override
    public IonStruct getIonRepresentation(ValueFactory factory) {
        return this.structCache.getIonRepresentation(factory);
    }

    static class Factory
    implements _Private_LocalSymbolTableFactory {
        private final ValueFactory imageFactory;

        public Factory(ValueFactory imageFactory) {
            this.imageFactory = imageFactory;
        }

        @Override
        public SymbolTable newLocalSymtab(IonCatalog catalog, IonReader reader, boolean alreadyInStruct) {
            ArrayList<String> symbolsList = new ArrayList<String>();
            SymbolTable currentSymbolTable = reader.getSymbolTable();
            LocalSymbolTableImports imports = LocalSymbolTable.readLocalSymbolTable(reader, catalog, alreadyInStruct, symbolsList, currentSymbolTable);
            if (imports == null) {
                return currentSymbolTable;
            }
            return new LocalSymbolTableAsStruct(imports, symbolsList, null);
        }

        @Override
        public SymbolTable newLocalSymtab(SymbolTable defaultSystemSymtab, SymbolTable ... imports) {
            LocalSymbolTableImports unifiedSymtabImports = new LocalSymbolTableImports(defaultSystemSymtab, imports);
            return new LocalSymbolTableAsStruct(unifiedSymtabImports, null, null);
        }

        public SymbolTable newLocalSymtab(IonCatalog catalog, IonStruct ionRep) {
            assert (this.imageFactory == ionRep.getSystem());
            IonReaderTreeSystem reader = new IonReaderTreeSystem(ionRep);
            ArrayList<String> symbolsList = new ArrayList<String>();
            LocalSymbolTableImports imports = LocalSymbolTable.readLocalSymbolTable(reader, catalog, false, symbolsList, ionRep.getSymbolTable());
            return new LocalSymbolTableAsStruct(imports, symbolsList, ionRep);
        }
    }
}

