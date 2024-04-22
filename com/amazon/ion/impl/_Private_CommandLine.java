/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.amazon.ion.util.JarInfo;
import java.io.IOException;

public final class _Private_CommandLine {
    static final int argid_VERSION = 2;
    static final int argid_HELP = 3;
    static final int argid_INVALID = -1;
    static JarInfo info = null;
    static boolean printHelp = false;
    static boolean printVersion = false;
    static String errorMessage = null;

    public static void main(String[] args2) throws IOException {
        _Private_CommandLine.process_command_line(args2);
        info = new JarInfo();
        if (printVersion) {
            _Private_CommandLine.doPrintVersion();
        }
        if (printHelp) {
            _Private_CommandLine.doPrintHelp();
        }
    }

    private static void process_command_line(String[] args2) {
        if (args2.length == 0) {
            printHelp = true;
        }
        block4: for (int ii = 0; ii < args2.length; ++ii) {
            String arg = args2[ii];
            if (arg == null || arg.length() < 1) continue;
            int argid = _Private_CommandLine.getArgumentId(arg);
            switch (argid) {
                case 2: {
                    printVersion = true;
                    continue block4;
                }
                case 3: {
                    printHelp = true;
                    continue block4;
                }
                default: {
                    _Private_CommandLine.invalid_arg(ii, arg);
                }
            }
        }
    }

    private static int getArgumentId(String arg) {
        if (arg.startsWith("-") && arg.length() == 2) {
            switch (arg.charAt(1)) {
                case '?': 
                case 'h': {
                    return 3;
                }
                case 'v': {
                    return 2;
                }
            }
            return -1;
        }
        if (arg.startsWith("--") && arg.length() > 2) {
            if (arg.equals("--help")) {
                return 3;
            }
            if (arg.equals("--version")) {
                return 2;
            }
        }
        return -1;
    }

    private static void invalid_arg(int ii, String arg) {
        errorMessage = errorMessage + "\narg[" + ii + "] \"" + arg + "\" is unrecognized or invalid.";
        printHelp = true;
    }

    private static void doPrintHelp() {
        System.out.println("ion-java -- Copyright (c) 2007-" + info.getBuildTime().getYear() + " Amazon.com");
        System.out.println("usage: java -jar <jar> <options>");
        System.out.println("options:");
        System.out.println("version\t\tprints current version entry");
        System.out.println("help\t\tprints this helpful message");
        if (errorMessage != null) {
            System.out.println();
            System.out.println(errorMessage);
        }
    }

    private static void doPrintVersion() throws IOException {
        IonTextWriterBuilder b = IonTextWriterBuilder.pretty();
        b.setCharset(IonTextWriterBuilder.ASCII);
        IonWriter w = b.build(System.out);
        w.stepIn(IonType.STRUCT);
        w.setFieldName("version");
        w.writeString(info.getProjectVersion());
        w.setFieldName("build_time");
        w.writeTimestamp(info.getBuildTime());
        w.stepOut();
        w.finish();
        System.out.println();
    }
}

