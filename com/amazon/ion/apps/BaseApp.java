/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.apps;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonSystem;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.system.IonSystemBuilder;
import com.amazon.ion.system.SimpleCatalog;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

abstract class BaseApp {
    protected SimpleCatalog myCatalog = new SimpleCatalog();
    protected IonSystem mySystem = IonSystemBuilder.standard().withCatalog(this.myCatalog).build();

    BaseApp() {
    }

    protected static byte[] loadAsByteArray(InputStream in) throws IOException {
        int cnt;
        byte[] buf = new byte[4096];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((cnt = in.read(buf)) != -1) {
            bos.write(buf, 0, cnt);
        }
        return bos.toByteArray();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static byte[] loadAsByteArray(File file) throws FileNotFoundException, IOException {
        long len = file.length();
        if (len < 0L || len > 0x7FFFFFF7L) {
            throw new IllegalArgumentException("File too long: " + file);
        }
        byte[] buffer = new byte[(int)len];
        try (FileInputStream in = new FileInputStream(file);){
            int readBytesCount = in.read(buffer);
            if ((long)readBytesCount != len || in.read() != -1) {
                System.err.println("Read the wrong number of bytes from " + file);
                byte[] byArray = null;
                return byArray;
            }
        }
        return buffer;
    }

    public void doMain(String[] args2) {
        int firstFileIndex = this.processOptions(args2);
        int fileCount = args2.length - firstFileIndex;
        String[] files = new String[fileCount];
        System.arraycopy(args2, firstFileIndex, files, 0, fileCount);
        if (this.optionsAreValid(files)) {
            this.processFiles(files);
        }
    }

    protected int processOptions(String[] args2) {
        return 0;
    }

    protected boolean optionsAreValid(String[] filePaths) {
        return true;
    }

    protected void processFiles(String[] filePaths) {
        if (filePaths.length == 0) {
            this.processStdIn();
        } else {
            for (int i = 0; i < filePaths.length; ++i) {
                String filePath = filePaths[i];
                this.processFile(filePath);
            }
        }
    }

    protected boolean processFile(String path) {
        File file = new File(path);
        if (file.canRead() && file.isFile()) {
            try {
                this.process(file);
                return true;
            } catch (IonException e) {
                System.err.println("An error occurred while processing " + path);
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println("An error occurred while processing " + path);
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("Skipping unreadable file: " + path);
        }
        return false;
    }

    protected void processStdIn() {
        try {
            byte[] buffer = BaseApp.loadAsByteArray(System.in);
            IonReader reader = this.mySystem.newReader(buffer);
            this.process(reader);
        } catch (IonException e) {
            System.err.println("An error occurred while processing stdin");
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("An error occurred while processing stdin");
            System.err.println(e.getMessage());
        }
    }

    protected void process(File file) throws IOException, IonException {
        byte[] buffer = BaseApp.loadAsByteArray(file);
        IonReader reader = this.mySystem.newReader(buffer);
        this.process(file, reader);
    }

    protected void process(File inputFile, IonReader reader) throws IOException, IonException {
        this.process(reader);
    }

    protected void process(IonReader reader) throws IOException, IonException {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void loadCatalog(String catalogPath) {
        System.err.println("Loading catalog from " + catalogPath);
        File catalogFile = new File(catalogPath);
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(catalogFile));){
            IonReader reader = this.mySystem.newReader(in);
            while (reader.next() != null) {
                SymbolTable symtab = this.mySystem.newSharedSymbolTable(reader, true);
                this.myCatalog.putTable(symtab);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading catalog from " + catalogPath + ": " + e.getMessage(), e);
        }
        IonCatalog catalog = this.mySystem.getCatalog();
        assert (this.myCatalog == catalog);
    }

    protected SymbolTable getLatestSharedSymtab(String name) {
        IonCatalog catalog = this.mySystem.getCatalog();
        SymbolTable table2 = catalog.getTable(name);
        if (table2 == null) {
            String message = "There's no symbol table in the catalog named " + name;
            throw new RuntimeException(message);
        }
        this.logDebug("Found shared symbol table " + name + "@" + table2.getVersion());
        return table2;
    }

    protected void logDebug(String message) {
        System.err.println(message);
    }
}

