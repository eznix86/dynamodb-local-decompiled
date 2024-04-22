/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

public abstract class BufferConfiguration<Configuration extends BufferConfiguration<Configuration>> {
    private final int initialBufferSize;
    private final int maximumBufferSize;
    private final OversizedValueHandler oversizedValueHandler;
    private final DataHandler dataHandler;

    protected BufferConfiguration(Builder<Configuration, ?> builder) {
        this.initialBufferSize = builder.getInitialBufferSize();
        this.maximumBufferSize = builder.getMaximumBufferSize();
        if (this.initialBufferSize > this.maximumBufferSize) {
            throw new IllegalArgumentException("Initial buffer size may not exceed the maximum buffer size.");
        }
        if (this.maximumBufferSize < builder.getMinimumMaximumBufferSize()) {
            throw new IllegalArgumentException(String.format("Maximum buffer size must be at least %d bytes.", builder.getMinimumMaximumBufferSize()));
        }
        if (builder.getOversizedValueHandler() == null) {
            this.requireMaximumBufferSize();
            this.oversizedValueHandler = builder.getThrowingOversizedValueHandler();
        } else {
            this.oversizedValueHandler = builder.getOversizedValueHandler();
        }
        this.dataHandler = builder.getDataHandler() == null ? builder.getNoOpDataHandler() : builder.getDataHandler();
    }

    protected void requireMaximumBufferSize() {
        if (this.maximumBufferSize < 0x7FFFFFF7) {
            throw new IllegalArgumentException("Must specify an OversizedValueHandler when a custom maximum buffer size is specified.");
        }
    }

    public final int getInitialBufferSize() {
        return this.initialBufferSize;
    }

    public final int getMaximumBufferSize() {
        return this.maximumBufferSize;
    }

    public final OversizedValueHandler getOversizedValueHandler() {
        return this.oversizedValueHandler;
    }

    public final DataHandler getDataHandler() {
        return this.dataHandler;
    }

    public static abstract class Builder<Configuration extends BufferConfiguration<Configuration>, BuilderType extends Builder<Configuration, BuilderType>> {
        static final int DEFAULT_INITIAL_BUFFER_SIZE = 32768;
        private int initialBufferSize = 32768;
        private int maximumBufferSize = 0x7FFFFFF7;
        private OversizedValueHandler oversizedValueHandler = null;
        private DataHandler dataHandler = null;

        public final BuilderType withInitialBufferSize(int initialBufferSizeInBytes) {
            this.initialBufferSize = initialBufferSizeInBytes;
            return (BuilderType)this;
        }

        public final int getInitialBufferSize() {
            return this.initialBufferSize;
        }

        public final BuilderType onOversizedValue(OversizedValueHandler handler) {
            this.oversizedValueHandler = handler;
            return (BuilderType)this;
        }

        public final BuilderType onData(DataHandler handler) {
            this.dataHandler = handler;
            return (BuilderType)this;
        }

        public final OversizedValueHandler getOversizedValueHandler() {
            return this.oversizedValueHandler;
        }

        public final DataHandler getDataHandler() {
            return this.dataHandler;
        }

        public final BuilderType withMaximumBufferSize(int maximumBufferSizeInBytes) {
            this.maximumBufferSize = maximumBufferSizeInBytes;
            return (BuilderType)this;
        }

        public int getMaximumBufferSize() {
            return this.maximumBufferSize;
        }

        public abstract int getMinimumMaximumBufferSize();

        public abstract OversizedValueHandler getNoOpOversizedValueHandler();

        public abstract OversizedValueHandler getThrowingOversizedValueHandler();

        public abstract DataHandler getNoOpDataHandler();

        public abstract Configuration build();
    }

    @FunctionalInterface
    public static interface DataHandler {
        public void onData(int var1);
    }

    @FunctionalInterface
    public static interface OversizedValueHandler {
        public void onOversizedValue();
    }
}

