/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.util;

import com.amazon.ion.IonException;
import com.amazon.ion.Timestamp;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class JarInfo {
    private String ourProjectVersion;
    private Timestamp ourBuildTime;

    public JarInfo() throws IonException {
        this.loadBuildProperties();
    }

    public String getProjectVersion() {
        return this.ourProjectVersion;
    }

    public Timestamp getBuildTime() {
        return this.ourBuildTime;
    }

    private static String nonEmptyProperty(Properties props, String name) {
        String value = props.getProperty(name, "");
        if (value.length() == 0) {
            value = null;
        }
        return value;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadBuildProperties() throws IonException {
        String file = "/ion-java.properties";
        try {
            Properties props = new Properties();
            InputStream in = this.getClass().getResourceAsStream(file);
            if (in != null) {
                try {
                    props.load(in);
                } finally {
                    in.close();
                }
            }
            this.ourProjectVersion = JarInfo.nonEmptyProperty(props, "build.version");
            String time = JarInfo.nonEmptyProperty(props, "build.time");
            if (time != null) {
                try {
                    this.ourBuildTime = Timestamp.valueOf(time);
                } catch (IllegalArgumentException illegalArgumentException) {}
            }
        } catch (IOException e) {
            throw new IonException("Unable to load " + file, e);
        }
    }
}

