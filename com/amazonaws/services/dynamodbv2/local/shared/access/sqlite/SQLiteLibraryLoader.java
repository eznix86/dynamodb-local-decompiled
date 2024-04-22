/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.almworks.sqlite4java.SQLite
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

import com.almworks.sqlite4java.SQLite;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.NativeSQLiteLibraryNotFoundException;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteLibraryLoaderUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.StringJoiner;
import java.util.UUID;

public class SQLiteLibraryLoader {
    private static SQLiteLibraryLoader instance;
    private static boolean extracted;

    public static synchronized void initialize() throws Exception {
        if (instance == null) {
            instance = new SQLiteLibraryLoader();
        }
        instance.loadSQLiteNativeLibrary();
    }

    private static InputStream getResourceAsStream(String name) {
        String resolvedName = name.substring(1);
        ClassLoader cl = SQLiteLibraryLoader.class.getClassLoader();
        URL url = cl.getResource(resolvedName);
        if (url == null) {
            return null;
        }
        try {
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean loadNativeLibrary(String path, String name) {
        File libPath = new File(path, name);
        if (libPath.exists()) {
            try {
                System.load(new File(path, name).getAbsolutePath());
                return true;
            } catch (UnsatisfiedLinkError e) {
                System.err.println("Failed to load native library:" + name + ". osinfo: " + SQLiteLibraryLoaderUtil.getNativeLibFolderPathForCurrentOS());
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private static boolean contentsEquals(InputStream in1, InputStream in2) throws IOException {
        int ch2;
        if (!(in1 instanceof BufferedInputStream)) {
            in1 = new BufferedInputStream(in1);
        }
        if (!(in2 instanceof BufferedInputStream)) {
            in2 = new BufferedInputStream(in2);
        }
        int ch1 = in1.read();
        while (ch1 != -1) {
            ch2 = in2.read();
            if (ch1 != ch2) {
                return false;
            }
            ch1 = in1.read();
        }
        ch2 = in2.read();
        return ch2 == -1;
    }

    private static boolean loadNativeLibraryJdk() {
        try {
            System.loadLibrary("sqlite4java");
            return true;
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Failed to load native library through System.loadLibrary");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean extractAndLoadLibraryFile(String libFolderForCurrentOS, String libraryFileName, String targetFolder) {
        String nativeLibraryFilePath = libFolderForCurrentOS + "/" + libraryFileName;
        String uuid = UUID.randomUUID().toString();
        String extractedLibFileName = String.format("sqlite-%s-%s-%s", SQLite.getLibraryVersion(), uuid, libraryFileName);
        Path extractedLibFile = Paths.get(targetFolder, extractedLibFileName);
        try {
            try (InputStream reader = SQLiteLibraryLoader.getResourceAsStream(nativeLibraryFilePath);){
                if (reader != null) {
                    Files.copy(reader, extractedLibFile, StandardCopyOption.REPLACE_EXISTING);
                    extractedLibFile.toFile().setReadable(true);
                    extractedLibFile.toFile().setWritable(true, true);
                    extractedLibFile.toFile().setExecutable(true);
                }
            } finally {
                extractedLibFile.toFile().deleteOnExit();
            }
            try (InputStream nativeIn = SQLiteLibraryLoader.getResourceAsStream(nativeLibraryFilePath);
                 InputStream extractedLibIn = Files.newInputStream(extractedLibFile, new OpenOption[0]);){
                if (!SQLiteLibraryLoader.contentsEquals(nativeIn, extractedLibIn)) {
                    throw new RuntimeException(String.format("Failed to write a native library file at %s", extractedLibFile));
                }
            }
            return SQLiteLibraryLoader.loadNativeLibrary(targetFolder, extractedLibFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadSQLiteNativeLibrary() throws Exception {
        boolean hasNativeLib;
        if (extracted) {
            return;
        }
        StringJoiner stringJoiner = new StringJoiner(File.pathSeparator);
        String sqliteNativeLibraryPath = System.getProperty("org.sqlite.lib.path");
        String sqliteNativeLibraryName = System.getProperty("org.sqlite.lib.name");
        if (sqliteNativeLibraryName == null) {
            sqliteNativeLibraryName = SQLiteLibraryLoaderUtil.getNativeLibName();
        }
        if (sqliteNativeLibraryPath != null) {
            if (SQLiteLibraryLoader.loadNativeLibrary(sqliteNativeLibraryPath, sqliteNativeLibraryName)) {
                extracted = true;
                return;
            }
            stringJoiner.add(sqliteNativeLibraryPath);
        }
        if (hasNativeLib = SQLiteLibraryLoaderUtil.hasNativeLib(sqliteNativeLibraryPath = SQLiteLibraryLoaderUtil.getNativeLibResourcePath(), sqliteNativeLibraryName)) {
            String tempFolder = this.getTempDir().getAbsolutePath();
            if (SQLiteLibraryLoader.extractAndLoadLibraryFile(sqliteNativeLibraryPath, sqliteNativeLibraryName, tempFolder)) {
                extracted = true;
                return;
            }
            stringJoiner.add(sqliteNativeLibraryPath);
        }
        String javaLibraryPath = System.getProperty("java.library.path", "");
        for (String ldPath : javaLibraryPath.split(File.pathSeparator)) {
            if (ldPath.isEmpty()) continue;
            if (SQLiteLibraryLoader.loadNativeLibrary(ldPath, sqliteNativeLibraryName)) {
                extracted = true;
                return;
            }
            stringJoiner.add(ldPath);
        }
        if (SQLiteLibraryLoader.loadNativeLibraryJdk()) {
            extracted = true;
            return;
        }
        throw new NativeSQLiteLibraryNotFoundException(String.format("No native library found for os.name=%s, os.arch=%s, paths=[%s], Please set the system property org.sqlite.lib.path or java.library.path to point to the sqlite native library file.", SQLiteLibraryLoaderUtil.getOSName(), SQLiteLibraryLoaderUtil.getArchName(), stringJoiner));
    }

    protected File getTempDir() {
        return new File(System.getProperty("org.sqlite.tmpdir", System.getProperty("java.io.tmpdir")));
    }

    static {
        extracted = false;
    }
}

