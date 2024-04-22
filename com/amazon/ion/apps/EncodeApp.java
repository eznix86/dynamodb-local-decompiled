/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.apps;

import com.amazon.ion.IonBinaryWriter;
import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.apps.BaseApp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EncodeApp
extends BaseApp {
    private SymbolTable[] myImports;
    private File myOutputDir;
    private String myOutputFile;

    public static void main(String[] args2) {
        EncodeApp app = new EncodeApp();
        app.doMain(args2);
    }

    @Override
    protected int processOptions(String[] args2) {
        int i;
        ArrayList<SymbolTable> imports = new ArrayList<SymbolTable>();
        for (i = 0; i < args2.length; ++i) {
            String path;
            String arg = args2[i];
            if ("--catalog".equals(arg)) {
                String symtabPath = args2[++i];
                this.loadCatalog(symtabPath);
                continue;
            }
            if ("--import".equals(arg)) {
                String name = args2[++i];
                SymbolTable symtab = this.getLatestSharedSymtab(name);
                imports.add(symtab);
                continue;
            }
            if ("--output-dir".equals(arg)) {
                path = args2[++i];
                this.myOutputDir = new File(path);
                if (this.myOutputDir.isDirectory() && this.myOutputDir.canWrite()) continue;
                throw new RuntimeException("Not a writeable directory: " + path);
            }
            if (!"--output".equals(arg)) break;
            this.myOutputFile = path = args2[++i];
            this.myOutputDir = new File(path).getParentFile();
            if (this.myOutputDir.isDirectory() && this.myOutputDir.canWrite()) continue;
            throw new RuntimeException("Not a writeable directory: " + path);
        }
        this.myImports = imports.toArray(new SymbolTable[0]);
        return i;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void process(File inputFile, IonReader reader) throws IOException, IonException {
        IonBinaryWriter writer = this.mySystem.newBinaryWriter(this.myImports);
        writer.writeValues(reader);
        byte[] binaryBytes = writer.getBytes();
        if (this.myOutputDir != null) {
            String fileName = inputFile.getName();
            File outputFile = new File(this.myOutputDir, fileName);
            try (FileOutputStream out = new FileOutputStream(outputFile);){
                out.write(binaryBytes);
            }
        } else {
            System.out.write(binaryBytes);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void process(IonReader reader) throws IOException, IonException {
        IonBinaryWriter writer = this.mySystem.newBinaryWriter(this.myImports);
        writer.writeValues(reader);
        byte[] binaryBytes = writer.getBytes();
        if (this.myOutputDir != null) {
            File outputFile = new File(this.myOutputFile);
            try (FileOutputStream out = new FileOutputStream(outputFile);){
                out.write(binaryBytes);
            }
        } else {
            System.out.write(binaryBytes);
        }
    }
}

