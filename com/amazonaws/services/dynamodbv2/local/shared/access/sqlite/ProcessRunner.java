/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProcessRunner {
    static String getProcessOutput(Process process) throws IOException {
        try (InputStream in = process.getInputStream();){
            int readLen;
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            byte[] buf = new byte[32];
            while ((readLen = in.read(buf, 0, buf.length)) >= 0) {
                b.write(buf, 0, readLen);
            }
            String string = b.toString();
            return string;
        }
    }

    String runAndWaitFor(String command) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        return ProcessRunner.getProcessOutput(p);
    }
}

