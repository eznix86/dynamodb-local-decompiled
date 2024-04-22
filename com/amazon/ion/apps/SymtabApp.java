/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.apps;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.apps.BaseApp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SymtabApp
extends BaseApp {
    private ArrayList<SymbolTable> myImports = new ArrayList();
    private ArrayList<String> mySymbols = new ArrayList();
    private String mySymtabName;
    private int mySymtabVersion;

    public static void main(String[] args2) {
        if (args2.length < 1) {
            System.err.println("Need one file to build symtab");
            return;
        }
        SymtabApp app = new SymtabApp();
        app.doMain(args2);
    }

    @Override
    protected int processOptions(String[] args2) {
        for (int i = 0; i < args2.length; ++i) {
            String arg = args2[i];
            if ("--catalog".equals(arg)) {
                String symtabPath = args2[++i];
                this.loadCatalog(symtabPath);
                continue;
            }
            if ("--import".equals(arg)) {
                String name = args2[++i];
                IonCatalog catalog = this.mySystem.getCatalog();
                SymbolTable table2 = catalog.getTable(name);
                if (table2 == null) {
                    String message = "There's no symbol table in the catalog named " + name;
                    throw new RuntimeException(message);
                }
                this.myImports.add(table2);
                this.logDebug("Imported symbol table " + name + "@" + table2.getVersion());
                continue;
            }
            if ("--name".equals(arg)) {
                if (this.mySymtabName != null) {
                    throw new RuntimeException("Multiple names");
                }
                this.mySymtabName = args2[++i];
                if (this.mySymtabName.length() != 0) continue;
                throw new RuntimeException("Name must not be empty");
            }
            if ("--version".equals(arg)) {
                if (this.mySymtabVersion != 0) {
                    throw new RuntimeException("Multiple versions");
                }
                int version = Integer.parseInt(arg);
                if (version < 1) {
                    throw new RuntimeException("Version must be at least 1");
                }
                if (version != 1) {
                    String message = "Symtab extension not implemented";
                    throw new UnsupportedOperationException(message);
                }
                this.mySymtabVersion = version;
                continue;
            }
            return i;
        }
        return args2.length;
    }

    @Override
    protected boolean optionsAreValid(String[] filePaths) {
        if (this.mySymtabName == null) {
            throw new RuntimeException("Must provide --name");
        }
        if (this.mySymtabVersion == 0) {
            this.mySymtabVersion = 1;
        }
        if (filePaths.length == 0) {
            System.err.println("Must provide list of files to provide symbols");
            return false;
        }
        return true;
    }

    @Override
    public void processFiles(String[] filePaths) {
        super.processFiles(filePaths);
        SymbolTable[] importArray = new SymbolTable[this.myImports.size()];
        this.myImports.toArray(importArray);
        SymbolTable mySymtab = this.mySystem.newSharedSymbolTable(this.mySymtabName, this.mySymtabVersion, this.mySymbols.iterator(), importArray);
        IonWriter w = this.mySystem.newTextWriter(System.out);
        try {
            mySymtab.writeTo(w);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void process(IonReader reader) throws IonException {
        while (reader.hasNext()) {
            IonType type = reader.next();
            String fieldName = reader.getFieldName();
            this.intern(fieldName);
            this.internAnnotations(reader);
            switch (type) {
                case SYMBOL: {
                    String text = reader.stringValue();
                    this.intern(text);
                    break;
                }
                case LIST: 
                case SEXP: 
                case STRUCT: {
                    reader.stepIn();
                    break;
                }
            }
            while (!reader.hasNext() && reader.getDepth() > 0) {
                reader.stepOut();
            }
        }
    }

    private void internAnnotations(IonReader reader) {
        Iterator<String> i = reader.iterateTypeAnnotations();
        assert (i != null);
        while (i.hasNext()) {
            String ann = i.next();
            this.intern(ann);
        }
    }

    private void intern(String text) {
        if (text != null) {
            if (text.equals("$ion") || text.startsWith("$ion_")) {
                return;
            }
            this.mySymbols.add(text);
        }
    }
}

