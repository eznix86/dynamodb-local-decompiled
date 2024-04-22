/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.apps;

import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonWriter;
import com.amazon.ion.apps.BaseApp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PrintApp
extends BaseApp {
    private File myOutputDir;
    private String myOutputFile;

    public static void main(String[] args2) {
        PrintApp app = new PrintApp();
        app.doMain(args2);
    }

    @Override
    protected int processOptions(String[] args2) {
        for (int i = 0; i < args2.length; ++i) {
            String path;
            String arg = args2[i];
            if ("--catalog".equals(arg)) {
                String symtabPath = args2[++i];
                this.loadCatalog(symtabPath);
                continue;
            }
            if ("--output-dir".equals(arg)) {
                path = args2[++i];
                this.myOutputDir = new File(path);
                if (this.myOutputDir.isDirectory() && this.myOutputDir.canWrite()) continue;
                throw new RuntimeException("Not a writeable directory: " + path);
            }
            if ("--output".equals(arg)) {
                this.myOutputFile = path = args2[++i];
                this.myOutputDir = new File(path).getParentFile();
                if (this.myOutputDir.isDirectory() && this.myOutputDir.canWrite()) continue;
                throw new RuntimeException("Not a writeable directory: " + path);
            }
            return i;
        }
        return args2.length;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void process(File inputFile, IonReader reader) throws IOException, IonException {
        if (this.myOutputDir == null) {
            this.process(reader, System.out);
        } else {
            String fileName = inputFile.getName();
            File outputFile = new File(this.myOutputDir, fileName);
            try (FileOutputStream out = new FileOutputStream(outputFile);){
                this.process(reader, out);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void process(IonReader reader) throws IOException, IonException {
        if (this.myOutputDir == null) {
            this.process(reader, System.out);
        } else {
            File outputFile = new File(this.myOutputFile);
            try (FileOutputStream out = new FileOutputStream(outputFile);){
                this.process(reader, out);
            }
        }
    }

    protected void process(IonReader reader, OutputStream out) throws IOException, IonException {
        IonSystem system = this.mySystem;
        IonWriter writer = system.newTextWriter(out);
        writer.writeValues(reader);
        out.write(10);
        out.flush();
    }
}

