/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.BufferConfiguration;
import com.amazon.ion.IonException;

public final class IonBufferConfiguration
extends BufferConfiguration<IonBufferConfiguration> {
    public static final IonBufferConfiguration DEFAULT = Builder.standard().build();
    private final OversizedSymbolTableHandler oversizedSymbolTableHandler;

    private IonBufferConfiguration(Builder builder) {
        super(builder);
        if (builder.getOversizedSymbolTableHandler() == null) {
            this.requireMaximumBufferSize();
            this.oversizedSymbolTableHandler = builder.getThrowingOversizedSymbolTableHandler();
        } else {
            this.oversizedSymbolTableHandler = builder.getOversizedSymbolTableHandler();
        }
    }

    public OversizedSymbolTableHandler getOversizedSymbolTableHandler() {
        return this.oversizedSymbolTableHandler;
    }

    public static final class Builder
    extends BufferConfiguration.Builder<IonBufferConfiguration, Builder> {
        private static final int MINIMUM_MAX_VALUE_SIZE = 5;
        private static final BufferConfiguration.OversizedValueHandler THROWING_OVERSIZED_VALUE_HANDLER = Builder::throwDueToUnexpectedOversizedValue;
        private static final OversizedSymbolTableHandler THROWING_OVERSIZED_SYMBOL_TABLE_HANDLER = Builder::throwDueToUnexpectedOversizedValue;
        private static final BufferConfiguration.OversizedValueHandler NO_OP_OVERSIZED_VALUE_HANDLER = () -> {};
        private static final BufferConfiguration.DataHandler NO_OP_DATA_HANDLER = bytes -> {};
        private static final OversizedSymbolTableHandler NO_OP_OVERSIZED_SYMBOL_TABLE_HANDLER = () -> {};
        private OversizedSymbolTableHandler oversizedSymbolTableHandler = null;

        private static void throwDueToUnexpectedOversizedValue() {
            throw new IonException("An oversized value was found even though no maximum size was configured. This is either due to data corruption or encountering a scalar larger than 2 GB. In the latter case, an IonBufferConfiguration can be configured to allow the reader to skip the oversized scalar.");
        }

        private Builder() {
        }

        public static Builder standard() {
            return new Builder();
        }

        public static Builder from(IonBufferConfiguration existingConfiguration) {
            return (Builder)((Builder)((Builder)((Builder)Builder.standard().onData(existingConfiguration.getDataHandler())).onOversizedValue(existingConfiguration.getOversizedValueHandler())).onOversizedSymbolTable(existingConfiguration.getOversizedSymbolTableHandler()).withInitialBufferSize(existingConfiguration.getInitialBufferSize())).withMaximumBufferSize(existingConfiguration.getMaximumBufferSize());
        }

        public Builder onOversizedSymbolTable(OversizedSymbolTableHandler handler) {
            this.oversizedSymbolTableHandler = handler;
            return this;
        }

        public OversizedSymbolTableHandler getOversizedSymbolTableHandler() {
            return this.oversizedSymbolTableHandler;
        }

        @Override
        public int getMinimumMaximumBufferSize() {
            return 5;
        }

        @Override
        public BufferConfiguration.OversizedValueHandler getNoOpOversizedValueHandler() {
            return NO_OP_OVERSIZED_VALUE_HANDLER;
        }

        @Override
        public BufferConfiguration.OversizedValueHandler getThrowingOversizedValueHandler() {
            return THROWING_OVERSIZED_VALUE_HANDLER;
        }

        @Override
        public BufferConfiguration.DataHandler getNoOpDataHandler() {
            return NO_OP_DATA_HANDLER;
        }

        public OversizedSymbolTableHandler getNoOpOversizedSymbolTableHandler() {
            return NO_OP_OVERSIZED_SYMBOL_TABLE_HANDLER;
        }

        public OversizedSymbolTableHandler getThrowingOversizedSymbolTableHandler() {
            return THROWING_OVERSIZED_SYMBOL_TABLE_HANDLER;
        }

        @Override
        public IonBufferConfiguration build() {
            return new IonBufferConfiguration(this);
        }
    }

    @FunctionalInterface
    public static interface OversizedSymbolTableHandler {
        public void onOversizedSymbolTable();
    }
}

