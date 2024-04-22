/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.impl.bin.Symbols;
import java.io.IOException;
import java.util.Iterator;

abstract class AbstractSymbolTable
implements SymbolTable {
    private final String name;
    private final int version;

    public AbstractSymbolTable(String name, int version) {
        this.name = name;
        this.version = version;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final int getVersion() {
        return this.version;
    }

    @Override
    public final String getIonVersionId() {
        return "$ion_1_0";
    }

    @Override
    public final int findSymbol(String name) {
        SymbolToken token = this.find(name);
        if (token == null) {
            return -1;
        }
        return token.getSid();
    }

    @Override
    public final void writeTo(IonWriter writer) throws IOException {
        SymbolTable[] imports;
        if (this.isSharedTable()) {
            writer.setTypeAnnotationSymbols(Symbols.systemSymbol(9));
        } else if (this.isLocalTable()) {
            writer.setTypeAnnotationSymbols(Symbols.systemSymbol(3));
        } else {
            throw new IllegalStateException("Invalid symbol table, neither shared nor local");
        }
        writer.stepIn(IonType.STRUCT);
        if (this.isSharedTable()) {
            writer.setFieldNameSymbol(Symbols.systemSymbol(4));
            writer.writeString(this.name);
            writer.setFieldNameSymbol(Symbols.systemSymbol(5));
            writer.writeInt(this.version);
        }
        if ((imports = this.getImportedTables()) != null && imports.length > 0) {
            writer.setFieldNameSymbol(Symbols.systemSymbol(6));
            writer.stepIn(IonType.LIST);
            for (SymbolTable st : imports) {
                writer.stepIn(IonType.STRUCT);
                writer.setFieldNameSymbol(Symbols.systemSymbol(4));
                writer.writeString(st.getName());
                writer.setFieldNameSymbol(Symbols.systemSymbol(5));
                writer.writeInt(st.getVersion());
                writer.setFieldNameSymbol(Symbols.systemSymbol(8));
                writer.writeInt(st.getMaxId());
                writer.stepOut();
            }
            writer.stepOut();
        }
        writer.setFieldNameSymbol(Symbols.systemSymbol(7));
        writer.stepIn(IonType.LIST);
        Iterator<String> iter = this.iterateDeclaredSymbolNames();
        while (iter.hasNext()) {
            writer.writeString(iter.next());
        }
        writer.stepOut();
        writer.stepOut();
    }

    @Override
    public void makeReadOnly() {
    }
}

