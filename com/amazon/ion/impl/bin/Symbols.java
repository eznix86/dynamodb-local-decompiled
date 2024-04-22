/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.IonException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.bin.AbstractSymbolTable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

class Symbols {
    private static final List<SymbolToken> SYSTEM_TOKENS = Collections.unmodifiableList(Arrays.asList(Symbols.symbol("$ion", 1), Symbols.symbol("$ion_1_0", 2), Symbols.symbol("$ion_symbol_table", 3), Symbols.symbol("name", 4), Symbols.symbol("version", 5), Symbols.symbol("imports", 6), Symbols.symbol("symbols", 7), Symbols.symbol("max_id", 8), Symbols.symbol("$ion_shared_symbol_table", 9)));
    private static final Map<String, SymbolToken> SYSTEM_TOKEN_MAP;
    private static SymbolTable SYSTEM_SYMBOL_TABLE;

    private Symbols() {
    }

    public static SymbolToken symbol(String name, int val) {
        if (name == null) {
            throw new NullPointerException();
        }
        if (val <= 0) {
            throw new IllegalArgumentException("Symbol value must be positive: " + val);
        }
        return _Private_Utils.newSymbolToken(name, val);
    }

    public static Iterator<String> symbolNameIterator(final Iterator<SymbolToken> tokenIter) {
        return new Iterator<String>(){

            @Override
            public boolean hasNext() {
                return tokenIter.hasNext();
            }

            @Override
            public String next() {
                return ((SymbolToken)tokenIter.next()).getText();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static SymbolToken systemSymbol(int sid) {
        if (sid < 1 || sid > 9) {
            throw new IllegalArgumentException("No such system SID: " + sid);
        }
        return SYSTEM_TOKENS.get(sid - 1);
    }

    public static SymbolTable systemSymbolTable() {
        return SYSTEM_SYMBOL_TABLE;
    }

    public static Collection<SymbolToken> systemSymbols() {
        return SYSTEM_TOKENS;
    }

    public static SymbolTable unknownSharedSymbolTable(final String name, final int version, final int maxId) {
        return new AbstractSymbolTable(name, version){

            @Override
            public Iterator<String> iterateDeclaredSymbolNames() {
                return new Iterator<String>(){
                    int id = 1;

                    @Override
                    public boolean hasNext() {
                        return this.id <= maxId;
                    }

                    @Override
                    public String next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        ++this.id;
                        return null;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            @Override
            public boolean isSystemTable() {
                return false;
            }

            @Override
            public boolean isSubstitute() {
                return true;
            }

            @Override
            public boolean isSharedTable() {
                return true;
            }

            @Override
            public boolean isReadOnly() {
                return true;
            }

            @Override
            public boolean isLocalTable() {
                return false;
            }

            @Override
            public SymbolToken intern(String text) {
                throw new UnsupportedOperationException("Cannot intern into substitute unknown shared symbol table: " + name + " version " + version);
            }

            @Override
            public SymbolTable getSystemSymbolTable() {
                return Symbols.systemSymbolTable();
            }

            @Override
            public int getMaxId() {
                return maxId;
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
            public String findKnownSymbol(int id) {
                return null;
            }

            @Override
            public SymbolToken find(String text) {
                return null;
            }
        };
    }

    static {
        HashMap<String, SymbolToken> symbols = new HashMap<String, SymbolToken>();
        for (SymbolToken token : SYSTEM_TOKENS) {
            symbols.put(token.getText(), token);
        }
        SYSTEM_TOKEN_MAP = Collections.unmodifiableMap(symbols);
        SYSTEM_SYMBOL_TABLE = new AbstractSymbolTable("$ion", 1){

            @Override
            public SymbolTable[] getImportedTables() {
                return null;
            }

            @Override
            public int getImportedMaxId() {
                return 0;
            }

            @Override
            public boolean isSystemTable() {
                return true;
            }

            @Override
            public boolean isSubstitute() {
                return false;
            }

            @Override
            public boolean isSharedTable() {
                return true;
            }

            @Override
            public boolean isReadOnly() {
                return true;
            }

            @Override
            public boolean isLocalTable() {
                return false;
            }

            @Override
            public SymbolToken intern(String text) {
                SymbolToken token = (SymbolToken)SYSTEM_TOKEN_MAP.get(text);
                if (token == null) {
                    throw new IonException("Cannot intern new symbol into system symbol table");
                }
                return token;
            }

            @Override
            public String findKnownSymbol(int id) {
                if (id < 1) {
                    throw new IllegalArgumentException("SID cannot be less than 1: " + id);
                }
                if (id > 9) {
                    return null;
                }
                return ((SymbolToken)SYSTEM_TOKENS.get(id - 1)).getText();
            }

            @Override
            public SymbolToken find(String text) {
                return (SymbolToken)SYSTEM_TOKEN_MAP.get(text);
            }

            @Override
            public SymbolTable getSystemSymbolTable() {
                return this;
            }

            @Override
            public int getMaxId() {
                return 9;
            }

            @Override
            public Iterator<String> iterateDeclaredSymbolNames() {
                return Symbols.symbolNameIterator(SYSTEM_TOKENS.iterator());
            }
        };
    }
}

