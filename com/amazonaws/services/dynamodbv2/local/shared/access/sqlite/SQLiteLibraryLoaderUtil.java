/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.ProcessRunner;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteLibraryLoader;
import java.util.HashMap;
import java.util.Locale;

public class SQLiteLibraryLoaderUtil {
    public static final String NATIVE_LIB_BASE_NAME = "sqlite4java";
    public static final String X86 = "x86";
    public static final String X86_64 = "x86_64";
    static final HashMap<String, String> archMapping = new HashMap();
    protected static ProcessRunner processRunner = new ProcessRunner();

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static String getNativeLibName() {
        return System.mapLibraryName(NATIVE_LIB_BASE_NAME);
    }

    static String translateArchNameToFolderName(String archName) {
        return archName.replaceAll("\\W", "");
    }

    static String translateOSNameToFolderName(String osName) {
        if (osName.contains("Windows")) {
            return "Windows";
        }
        if (osName.contains("Mac") || osName.contains("Darwin")) {
            return "Mac";
        }
        if (osName.contains("Linux")) {
            return "Linux";
        }
        return osName.replaceAll("\\W", "");
    }

    public static String getOSName() {
        return SQLiteLibraryLoaderUtil.translateOSNameToFolderName(System.getProperty("os.name"));
    }

    public static String getNativeLibFolderPathForCurrentOS() {
        return SQLiteLibraryLoaderUtil.getOSName() + "/" + SQLiteLibraryLoaderUtil.getArchName();
    }

    public static String getArchName() {
        String override = System.getProperty("org.sqlite.osinfo.architecture");
        if (override != null) {
            return override;
        }
        String osArch = System.getProperty("os.arch");
        if (osArch.startsWith("arm")) {
            osArch = SQLiteLibraryLoaderUtil.resolveArmArchType();
        } else {
            String lc = osArch.toLowerCase(Locale.US);
            if (archMapping.containsKey(lc)) {
                return archMapping.get(lc);
            }
        }
        return SQLiteLibraryLoaderUtil.translateArchNameToFolderName(osArch);
    }

    static String resolveArmArchType() {
        String armType;
        if (System.getProperty("os.name").contains("Linux") && (armType = SQLiteLibraryLoaderUtil.getHardwareName()).startsWith("aarch64")) {
            return "aarch64";
        }
        return "arm";
    }

    static String getHardwareName() {
        try {
            return processRunner.runAndWaitFor("uname -m");
        } catch (Throwable e) {
            System.err.println("Error while running uname -m: " + e.getMessage());
            return "unknown";
        }
    }

    public static boolean hasNativeLib(String path, String libraryName) {
        return SQLiteLibraryLoader.class.getResource(path + "/" + libraryName) != null;
    }

    static String getNativeLibResourcePath() {
        String packagePath = SQLiteLibraryLoader.class.getPackage().getName().replace(".", "/");
        String resourcePath = String.format("/%s/native/%s", packagePath, SQLiteLibraryLoaderUtil.getNativeLibFolderPathForCurrentOS());
        return resourcePath;
    }

    static {
        archMapping.put(X86, X86);
        archMapping.put("i386", X86);
        archMapping.put("i486", X86);
        archMapping.put("i586", X86);
        archMapping.put("i686", X86);
        archMapping.put("pentium", X86);
        archMapping.put(X86_64, X86_64);
        archMapping.put("amd64", X86_64);
        archMapping.put("em64t", X86_64);
        archMapping.put("universal", X86_64);
    }
}

