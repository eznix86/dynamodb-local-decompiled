/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.system;

import com.amazon.ion.IonBufferConfiguration;
import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonTextReader;
import com.amazon.ion.IonValue;
import com.amazon.ion.impl._Private_IonReaderBuilder;
import com.amazon.ion.system.SimpleCatalog;
import java.io.InputStream;
import java.io.Reader;

public abstract class IonReaderBuilder {
    private IonCatalog catalog = null;
    private boolean isIncrementalReadingEnabled = false;
    private IonBufferConfiguration bufferConfiguration = IonBufferConfiguration.DEFAULT;

    protected IonReaderBuilder() {
    }

    protected IonReaderBuilder(IonReaderBuilder that) {
        this.catalog = that.catalog;
        this.isIncrementalReadingEnabled = that.isIncrementalReadingEnabled;
        this.bufferConfiguration = that.bufferConfiguration;
    }

    public static IonReaderBuilder standard() {
        return new _Private_IonReaderBuilder.Mutable();
    }

    public IonReaderBuilder copy() {
        return new _Private_IonReaderBuilder.Mutable(this);
    }

    public IonReaderBuilder immutable() {
        return this;
    }

    public IonReaderBuilder mutable() {
        return this.copy();
    }

    protected void mutationCheck() {
        throw new UnsupportedOperationException("This builder is immutable");
    }

    public IonReaderBuilder withCatalog(IonCatalog catalog) {
        IonReaderBuilder b = this.mutable();
        b.setCatalog(catalog);
        return b;
    }

    public void setCatalog(IonCatalog catalog) {
        this.mutationCheck();
        this.catalog = catalog;
    }

    public IonCatalog getCatalog() {
        return this.catalog;
    }

    protected IonCatalog validateCatalog() {
        return this.catalog != null ? this.catalog : new SimpleCatalog();
    }

    public IonReaderBuilder withIncrementalReadingEnabled(boolean isEnabled) {
        IonReaderBuilder b = this.mutable();
        if (isEnabled) {
            b.setIncrementalReadingEnabled();
        } else {
            b.setIncrementalReadingDisabled();
        }
        return b;
    }

    public void setIncrementalReadingEnabled() {
        this.mutationCheck();
        this.isIncrementalReadingEnabled = true;
    }

    public void setIncrementalReadingDisabled() {
        this.mutationCheck();
        this.isIncrementalReadingEnabled = false;
    }

    public boolean isIncrementalReadingEnabled() {
        return this.isIncrementalReadingEnabled;
    }

    public IonReaderBuilder withBufferConfiguration(IonBufferConfiguration configuration) {
        IonReaderBuilder b = this.mutable();
        b.setBufferConfiguration(configuration);
        return b;
    }

    public void setBufferConfiguration(IonBufferConfiguration configuration) {
        this.mutationCheck();
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration must not be null. To use the default configuration, provide IonBufferConfiguration.DEFAULT.");
        }
        this.bufferConfiguration = configuration;
    }

    public IonBufferConfiguration getBufferConfiguration() {
        return this.bufferConfiguration;
    }

    public IonReader build(byte[] ionData) {
        return this.build(ionData, 0, ionData.length);
    }

    public abstract IonReader build(byte[] var1, int var2, int var3);

    public abstract IonReader build(InputStream var1);

    public abstract IonReader build(Reader var1);

    public abstract IonReader build(IonValue var1);

    public abstract IonTextReader build(String var1);
}

