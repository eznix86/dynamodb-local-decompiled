/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.ReadOnlyValueException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.impl.IonReaderTreeSystem;
import com.amazon.ion.impl.SymbolTableReader;
import com.amazon.ion.impl.SymbolTokenImpl;
import com.amazon.ion.impl._Private_Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class SharedSymbolTable
implements SymbolTable {
    private static final String[] SYSTEM_SYMBOLS = new String[]{"$ion", "$ion_1_0", "$ion_symbol_table", "name", "version", "imports", "symbols", "max_id", "$ion_shared_symbol_table"};
    private static final SymbolTable ION_1_0_SYSTEM_SYMTAB;
    private final String myName;
    private final int myVersion;
    private final String[] mySymbolNames;
    private final Map<String, Integer> mySymbolsMap;

    private SharedSymbolTable(String name, int version, List<String> symbolsList, Map<String, Integer> symbolsMap) {
        this.myName = name;
        this.myVersion = version;
        this.mySymbolsMap = symbolsMap;
        this.mySymbolNames = symbolsList.toArray(new String[symbolsList.size()]);
    }

    private SharedSymbolTable(String name, int version, String[] symbolNames, Map<String, Integer> symbolsMap) {
        this.myName = name;
        this.myVersion = version;
        this.mySymbolsMap = symbolsMap;
        this.mySymbolNames = symbolNames;
    }

    static SymbolTable newSharedSymbolTable(String name, int version, SymbolTable priorSymtab, Iterator<String> symbols) {
        if (name == null || name.length() < 1) {
            throw new IllegalArgumentException("name must be non-empty");
        }
        if (version < 1) {
            throw new IllegalArgumentException("version must be at least 1");
        }
        ArrayList<String> symbolsList = new ArrayList<String>();
        HashMap<String, Integer> symbolsMap = new HashMap<String, Integer>();
        assert (version == (priorSymtab == null ? 1 : priorSymtab.getVersion() + 1));
        SharedSymbolTable.prepSymbolsListAndMap(priorSymtab, symbols, symbolsList, symbolsMap);
        return new SharedSymbolTable(name, version, symbolsList, symbolsMap);
    }

    static SymbolTable newSharedSymbolTable(IonStruct ionRep) {
        IonReaderTreeSystem reader = new IonReaderTreeSystem(ionRep);
        return SharedSymbolTable.newSharedSymbolTable(reader, false);
    }

    static SymbolTable newSharedSymbolTable(IonReader reader, boolean isOnStruct) {
        IonType t;
        if (!isOnStruct && (t = reader.next()) != IonType.STRUCT) {
            throw new IonException("invalid symbol table image passed into reader, " + (Object)((Object)t) + " encountered when a struct was expected");
        }
        String name = null;
        int version = -1;
        ArrayList<String> symbolsList = new ArrayList<String>();
        reader.stepIn();
        IonType fieldType = null;
        while ((fieldType = reader.next()) != null) {
            if (reader.isNullValue()) continue;
            SymbolToken symTok = reader.getFieldNameSymbol();
            int sid = symTok.getSid();
            if (sid == -1) {
                String fieldName = reader.getFieldName();
                sid = _Private_Utils.getSidForSymbolTableField(fieldName);
            }
            switch (sid) {
                case 5: {
                    if (fieldType != IonType.INT) break;
                    version = reader.intValue();
                    break;
                }
                case 4: {
                    if (fieldType != IonType.STRING) break;
                    name = reader.stringValue();
                    break;
                }
                case 7: {
                    IonType t2;
                    if (fieldType != IonType.LIST) break;
                    reader.stepIn();
                    while ((t2 = reader.next()) != null) {
                        String text = null;
                        if (t2 == IonType.STRING && !reader.isNullValue() && (text = reader.stringValue()).length() == 0) {
                            text = null;
                        }
                        symbolsList.add(text);
                    }
                    reader.stepOut();
                    break;
                }
            }
        }
        reader.stepOut();
        if (name == null || name.length() == 0) {
            String message = "shared symbol table is malformed: field 'name' must be a non-empty string.";
            throw new IonException(message);
        }
        version = version < 1 ? 1 : version;
        Map<String, Integer> symbolsMap = null;
        if (!symbolsList.isEmpty()) {
            symbolsMap = new HashMap();
            SharedSymbolTable.transferNonExistingSymbols(symbolsList, symbolsMap);
        } else {
            symbolsMap = Collections.emptyMap();
        }
        return new SharedSymbolTable(name, version, symbolsList, symbolsMap);
    }

    static SymbolTable getSystemSymbolTable(int version) {
        if (version != 1) {
            throw new IllegalArgumentException("only Ion 1.0 system symbols are supported");
        }
        return ION_1_0_SYSTEM_SYMTAB;
    }

    private static void putToMapIfNotThere(Map<String, Integer> symbolsMap, String text, int sid) {
        Integer extantSid = symbolsMap.put(text, sid);
        if (extantSid != null) {
            assert (extantSid < sid);
            symbolsMap.put(text, extantSid);
        }
    }

    private static void prepSymbolsListAndMap(SymbolTable priorSymtab, Iterator<String> symbols, List<String> symbolsList, Map<String, Integer> symbolsMap) {
        int sid = 1;
        if (priorSymtab != null) {
            Iterator<String> priorSymbols = priorSymtab.iterateDeclaredSymbolNames();
            while (priorSymbols.hasNext()) {
                String text = priorSymbols.next();
                if (text != null) {
                    assert (text.length() > 0);
                    SharedSymbolTable.putToMapIfNotThere(symbolsMap, text, sid);
                }
                symbolsList.add(text);
                ++sid;
            }
        }
        while (symbols.hasNext()) {
            String text = symbols.next();
            if (symbolsMap.get(text) != null) continue;
            SharedSymbolTable.putToMapIfNotThere(symbolsMap, text, sid);
            symbolsList.add(text);
            ++sid;
        }
    }

    private static void transferNonExistingSymbols(List<String> symbolsList, Map<String, Integer> symbolsMap) {
        int sid = 1;
        for (String text : symbolsList) {
            assert (text == null || text.length() > 0);
            if (text != null) {
                SharedSymbolTable.putToMapIfNotThere(symbolsMap, text, sid);
            }
            ++sid;
        }
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
    public boolean isLocalTable() {
        return false;
    }

    @Override
    public boolean isSharedTable() {
        return true;
    }

    @Override
    public boolean isSubstitute() {
        return false;
    }

    @Override
    public boolean isSystemTable() {
        return "$ion".equals(this.myName);
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
        if (this.isSystemTable()) {
            return this;
        }
        return null;
    }

    @Override
    public String getIonVersionId() {
        if (this.isSystemTable()) {
            int id = this.getVersion();
            if (id != 1) {
                throw new IonException("unrecognized system version encountered: " + id);
            }
            return "$ion_1_0";
        }
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
        return this.mySymbolNames.length;
    }

    @Override
    public SymbolToken intern(String text) {
        SymbolToken symTok = this.find(text);
        if (symTok == null) {
            throw new ReadOnlyValueException(SymbolTable.class);
        }
        return symTok;
    }

    @Override
    public SymbolToken find(String text) {
        text.getClass();
        Integer sid = this.mySymbolsMap.get(text);
        if (sid != null) {
            assert (sid != -1);
            int offset = sid - 1;
            String internedText = this.mySymbolNames[offset];
            assert (internedText != null);
            return new SymbolTokenImpl(internedText, sid);
        }
        return null;
    }

    @Override
    public int findSymbol(String name) {
        Integer sid = this.mySymbolsMap.get(name);
        if (sid != null) {
            return sid;
        }
        return -1;
    }

    @Override
    public String findKnownSymbol(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("symbol IDs must be >= 0");
        }
        int offset = id - 1;
        if (id != 0 && offset < this.mySymbolNames.length) {
            return this.mySymbolNames[offset];
        }
        return null;
    }

    @Override
    public Iterator<String> iterateDeclaredSymbolNames() {
        return Collections.unmodifiableList(Arrays.asList(this.mySymbolNames)).iterator();
    }

    @Override
    public void writeTo(IonWriter writer) throws IOException {
        SymbolTableReader reader = new SymbolTableReader(this);
        writer.writeValues(reader);
    }

    static {
        HashMap<String, Integer> systemSymbolsMap = new HashMap<String, Integer>();
        for (int i = 0; i < SYSTEM_SYMBOLS.length; ++i) {
            systemSymbolsMap.put(SYSTEM_SYMBOLS[i], i + 1);
        }
        ION_1_0_SYSTEM_SYMTAB = new SharedSymbolTable("$ion", 1, SYSTEM_SYMBOLS, systemSymbolsMap);
    }
}

