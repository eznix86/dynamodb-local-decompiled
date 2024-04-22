/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.impl.SymbolTokenImpl;
import java.util.Arrays;
import java.util.List;

final class LocalSymbolTableImports {
    private final SymbolTable[] myImports;
    private final int myMaxId;
    private final int[] myBaseSids;

    LocalSymbolTableImports(List<SymbolTable> importTables) {
        int importTablesSize = importTables.size();
        this.myImports = importTables.toArray(new SymbolTable[importTablesSize]);
        this.myBaseSids = new int[importTablesSize];
        this.myMaxId = LocalSymbolTableImports.prepBaseSids(this.myBaseSids, this.myImports);
    }

    LocalSymbolTableImports(SymbolTable defaultSystemSymtab, SymbolTable ... imports) {
        assert (defaultSystemSymtab.isSystemTable()) : "defaultSystemSymtab isn't a system symtab";
        if (imports != null && imports.length > 0) {
            if (imports[0].isSystemTable()) {
                this.myImports = (SymbolTable[])imports.clone();
            } else {
                this.myImports = new SymbolTable[imports.length + 1];
                this.myImports[0] = defaultSystemSymtab;
                System.arraycopy(imports, 0, this.myImports, 1, imports.length);
            }
        } else {
            this.myImports = new SymbolTable[]{defaultSystemSymtab};
        }
        this.myBaseSids = new int[this.myImports.length];
        this.myMaxId = LocalSymbolTableImports.prepBaseSids(this.myBaseSids, this.myImports);
    }

    private static int prepBaseSids(int[] baseSids, SymbolTable[] imports) {
        SymbolTable firstImport = imports[0];
        assert (firstImport.isSystemTable()) : "first symtab must be a system symtab";
        baseSids[0] = 0;
        int total = firstImport.getMaxId();
        for (int i = 1; i < imports.length; ++i) {
            SymbolTable importedTable = imports[i];
            if (importedTable.isLocalTable() || importedTable.isSystemTable()) {
                String message = "only non-system shared tables can be imported";
                throw new IllegalArgumentException(message);
            }
            baseSids[i] = total;
            total += imports[i].getMaxId();
        }
        return total;
    }

    String findKnownSymbol(int sid) {
        String name = null;
        if (sid <= this.myMaxId) {
            int baseSid;
            int i;
            int previousBaseSid = 0;
            for (i = 1; i < this.myImports.length && sid > (baseSid = this.myBaseSids[i]); ++i) {
                previousBaseSid = baseSid;
            }
            int importScopedSid = sid - previousBaseSid;
            name = this.myImports[i - 1].findKnownSymbol(importScopedSid);
        }
        return name;
    }

    int findSymbol(String name) {
        SymbolToken tok = this.find(name);
        return tok == null ? -1 : tok.getSid();
    }

    SymbolToken find(String text) {
        for (int i = 0; i < this.myImports.length; ++i) {
            SymbolTable importedTable = this.myImports[i];
            SymbolToken tok = importedTable.find(text);
            if (tok == null) continue;
            int sid = tok.getSid() + this.myBaseSids[i];
            text = tok.getText();
            assert (text != null);
            return new SymbolTokenImpl(text, sid);
        }
        return null;
    }

    int getMaxId() {
        return this.myMaxId;
    }

    SymbolTable getSystemSymbolTable() {
        assert (this.myImports[0].isSystemTable());
        return this.myImports[0];
    }

    SymbolTable[] getImportedTables() {
        int count = this.myImports.length - 1;
        SymbolTable[] imports = new SymbolTable[count];
        if (count > 0) {
            System.arraycopy(this.myImports, 1, imports, 0, count);
        }
        return imports;
    }

    SymbolTable[] getImportedTablesNoCopy() {
        return this.myImports;
    }

    public String toString() {
        return Arrays.toString(this.myImports);
    }

    boolean equalImports(LocalSymbolTableImports other) {
        return Arrays.equals(this.myImports, other.myImports);
    }
}

