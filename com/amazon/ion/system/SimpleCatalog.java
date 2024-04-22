/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.system;

import com.amazon.ion.IonMutableCatalog;
import com.amazon.ion.SymbolTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SimpleCatalog
implements IonMutableCatalog,
Iterable<SymbolTable> {
    private Map<String, TreeMap<Integer, SymbolTable>> myTablesByName = new HashMap<String, TreeMap<Integer, SymbolTable>>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SymbolTable getTable(String name) {
        TreeMap<Integer, SymbolTable> versions;
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name is empty");
        }
        Map<String, TreeMap<Integer, SymbolTable>> map2 = this.myTablesByName;
        synchronized (map2) {
            versions = this.myTablesByName.get(name);
        }
        if (versions == null) {
            return null;
        }
        map2 = versions;
        synchronized (map2) {
            Integer highestVersion = versions.lastKey();
            return versions.get(highestVersion);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SymbolTable getTable(String name, int version) {
        TreeMap<Integer, SymbolTable> versions;
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name is empty");
        }
        if (version < 1) {
            throw new IllegalArgumentException("version is < 1");
        }
        Map<String, TreeMap<Integer, SymbolTable>> map2 = this.myTablesByName;
        synchronized (map2) {
            versions = this.myTablesByName.get(name);
        }
        if (versions == null) {
            return null;
        }
        map2 = versions;
        synchronized (map2) {
            SymbolTable st = versions.get(version);
            if (st == null) {
                assert (!versions.isEmpty());
                Integer ibest = SimpleCatalog.bestMatch(version, versions.keySet());
                assert (ibest != null);
                st = versions.get(ibest);
                assert (st != null);
            }
            return st;
        }
    }

    static Integer bestMatch(int requestedVersion, Iterable<Integer> availableVersions) {
        int best = requestedVersion;
        Integer ibest = null;
        for (Integer available : availableVersions) {
            assert (available != requestedVersion);
            int v = available;
            if (requestedVersion < best) {
                if (requestedVersion >= v || v >= best) continue;
                best = v;
                ibest = available;
                continue;
            }
            if (best < requestedVersion) {
                if (best >= v) continue;
                best = v;
                ibest = available;
                continue;
            }
            best = v;
            ibest = available;
        }
        return ibest;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void putTable(SymbolTable table2) {
        if (table2.isLocalTable() || table2.isSystemTable() || table2.isSubstitute()) {
            throw new IllegalArgumentException("table cannot be local or system or substitute table");
        }
        String name = table2.getName();
        int version = table2.getVersion();
        assert (version >= 0);
        Map<String, TreeMap<Integer, SymbolTable>> map2 = this.myTablesByName;
        synchronized (map2) {
            TreeMap<Integer, SymbolTable> versions = this.myTablesByName.get(name);
            if (versions == null) {
                versions = new TreeMap();
                this.myTablesByName.put(name, versions);
            }
            TreeMap<Integer, SymbolTable> treeMap = versions;
            synchronized (treeMap) {
                versions.put(version, table2);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SymbolTable removeTable(String name, int version) {
        SymbolTable removed = null;
        Map<String, TreeMap<Integer, SymbolTable>> map2 = this.myTablesByName;
        synchronized (map2) {
            TreeMap<Integer, SymbolTable> versions = this.myTablesByName.get(name);
            if (versions != null) {
                TreeMap<Integer, SymbolTable> treeMap = versions;
                synchronized (treeMap) {
                    removed = versions.remove(version);
                    if (versions.isEmpty()) {
                        this.myTablesByName.remove(name);
                    }
                }
            }
        }
        return removed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Iterator<SymbolTable> iterator() {
        ArrayList<SymbolTable> tables;
        Map<String, TreeMap<Integer, SymbolTable>> map2 = this.myTablesByName;
        synchronized (map2) {
            tables = new ArrayList<SymbolTable>(this.myTablesByName.size());
            Collection<TreeMap<Integer, SymbolTable>> symtabNames = this.myTablesByName.values();
            Iterator<TreeMap<Integer, SymbolTable>> iterator2 = symtabNames.iterator();
            while (iterator2.hasNext()) {
                TreeMap<Integer, SymbolTable> versions;
                TreeMap<Integer, SymbolTable> treeMap = versions = iterator2.next();
                synchronized (treeMap) {
                    tables.addAll(versions.values());
                }
            }
        }
        return tables.iterator();
    }
}

