/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCursor;
import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.OffsetSpan;
import com.amazon.ion.OversizedValueException;
import com.amazon.ion.RawValueSpanProvider;
import com.amazon.ion.SeekableReader;
import com.amazon.ion.Span;
import com.amazon.ion.SpanProvider;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl.DowncastingFaceted;
import com.amazon.ion.impl.IonReaderContinuableApplicationBinary;
import com.amazon.ion.impl._Private_LocalSymbolTable;
import com.amazon.ion.impl._Private_ReaderWriter;
import com.amazon.ion.system.IonReaderBuilder;
import java.io.InputStream;

final class IonReaderContinuableTopLevelBinary
extends IonReaderContinuableApplicationBinary
implements IonReader,
_Private_ReaderWriter {
    private final boolean isNonContinuable;
    private final boolean isFillRequired;
    private boolean isFillingValue = false;
    private IonType type = null;
    private SymbolTable symbolTableLastTransferred = null;

    IonReaderContinuableTopLevelBinary(IonReaderBuilder builder, InputStream inputStream, byte[] alreadyRead, int alreadyReadOff, int alreadyReadLen) {
        super(builder, inputStream, alreadyRead, alreadyReadOff, alreadyReadLen);
        this.isFillRequired = this.isNonContinuable = !builder.isIncrementalReadingEnabled();
    }

    IonReaderContinuableTopLevelBinary(IonReaderBuilder builder, byte[] data, int offset, int length) {
        super(builder, data, offset, length);
        this.isNonContinuable = !builder.isIncrementalReadingEnabled();
        this.isFillRequired = false;
    }

    @Override
    public SymbolTable pop_passed_symbol_table() {
        SymbolTable currentSymbolTable = this.getSymbolTable();
        if (currentSymbolTable == this.symbolTableLastTransferred) {
            return null;
        }
        this.symbolTableLastTransferred = currentSymbolTable;
        if (this.symbolTableLastTransferred.isLocalTable()) {
            return ((_Private_LocalSymbolTable)this.symbolTableLastTransferred).makeCopy();
        }
        return this.symbolTableLastTransferred;
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("Not implemented");
    }

    private void nextAndFill() {
        do {
            if (!this.isFillingValue && this.nextValue() == IonCursor.Event.NEEDS_DATA) {
                return;
            }
            this.isFillingValue = true;
            if (this.fillValue() == IonCursor.Event.NEEDS_DATA) {
                return;
            }
            this.isFillingValue = false;
        } while (this.event == IonCursor.Event.NEEDS_INSTRUCTION);
        this.type = super.getType();
    }

    private void handleIncompleteValue() {
        if (this.event == IonCursor.Event.NEEDS_DATA) {
            if (this.isNonContinuable) {
                this.endStream();
            }
        } else if (this.isNonContinuable) {
            this.isValueIncomplete = false;
            if (this.nextValue() == IonCursor.Event.NEEDS_DATA) {
                this.isValueIncomplete = true;
                this.endStream();
            } else {
                this.type = super.getType();
            }
        }
    }

    @Override
    public IonType next() {
        this.type = null;
        if (this.isValueIncomplete) {
            this.handleIncompleteValue();
        } else if (!this.isSlowMode || this.isNonContinuable || this.parent != null) {
            if (this.nextValue() == IonCursor.Event.NEEDS_DATA) {
                if (this.isNonContinuable) {
                    this.endStream();
                }
            } else if (this.isValueIncomplete && !this.isNonContinuable) {
                this.event = IonCursor.Event.NEEDS_DATA;
            } else {
                this.isFillingValue = false;
                this.type = super.getType();
            }
        } else {
            this.nextAndFill();
        }
        return this.type;
    }

    @Override
    public void stepIn() {
        super.stepIntoContainer();
        this.type = null;
    }

    @Override
    public void stepOut() {
        super.stepOutOfContainer();
        this.type = null;
    }

    @Override
    public IonType getType() {
        return this.type;
    }

    @Override
    void prepareScalar() {
        if (!this.isValueIncomplete) {
            if (!this.isSlowMode || this.event == IonCursor.Event.VALUE_READY) {
                super.prepareScalar();
                return;
            }
            if (this.isFillRequired) {
                if (this.fillValue() == IonCursor.Event.VALUE_READY) {
                    super.prepareScalar();
                    return;
                }
                if (this.event == IonCursor.Event.NEEDS_INSTRUCTION) {
                    throw new OversizedValueException();
                }
            }
        }
        throw new IonException("Unexpected EOF.");
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        if (facetType == SpanProvider.class) {
            return facetType.cast(new SpanProviderFacet());
        }
        if (this.isByteBacked()) {
            if (facetType == SeekableReader.class) {
                return facetType.cast(new SeekableReaderFacet());
            }
            if (facetType == RawValueSpanProvider.class) {
                return facetType.cast(new RawValueSpanProviderFacet());
            }
        }
        return null;
    }

    @Override
    public void close() {
        if (!this.isNonContinuable) {
            this.endStream();
        }
        super.close();
    }

    private class SeekableReaderFacet
    extends SpanProviderFacet
    implements SeekableReader {
        private SeekableReaderFacet() {
        }

        @Override
        public void hoist(Span span) {
            if (!(span instanceof IonReaderBinarySpan)) {
                throw new IllegalArgumentException("Span isn't compatible with this reader.");
            }
            IonReaderBinarySpan binarySpan = (IonReaderBinarySpan)span;
            if (binarySpan.symbolTable == null) {
                throw new IllegalArgumentException("Span is not seekable.");
            }
            IonReaderContinuableTopLevelBinary.this.restoreSymbolTable(binarySpan.symbolTable);
            IonReaderContinuableTopLevelBinary.this.slice(binarySpan.bufferOffset, binarySpan.bufferLimit, binarySpan.symbolTable.getIonVersionId());
            IonReaderContinuableTopLevelBinary.this.type = null;
        }
    }

    private class RawValueSpanProviderFacet
    implements RawValueSpanProvider {
        private RawValueSpanProviderFacet() {
        }

        @Override
        public Span valueSpan() {
            if (IonReaderContinuableTopLevelBinary.this.type == null) {
                throw new IllegalStateException("IonReader isn't positioned on a value");
            }
            return new IonReaderBinarySpan(IonReaderContinuableTopLevelBinary.this.valueMarker.startIndex, IonReaderContinuableTopLevelBinary.this.valueMarker.endIndex, IonReaderContinuableTopLevelBinary.this.valueMarker.startIndex, null);
        }

        @Override
        public byte[] buffer() {
            return IonReaderContinuableTopLevelBinary.this.buffer;
        }
    }

    private class SpanProviderFacet
    implements SpanProvider {
        private SpanProviderFacet() {
        }

        @Override
        public Span currentSpan() {
            if (IonReaderContinuableTopLevelBinary.this.type == null) {
                throw new IllegalStateException("IonReader isn't positioned on a value");
            }
            return new IonReaderBinarySpan(IonReaderContinuableTopLevelBinary.this.valuePreHeaderIndex, IonReaderContinuableTopLevelBinary.this.valueMarker.endIndex, IonReaderContinuableTopLevelBinary.this.getTotalOffset(), IonReaderContinuableTopLevelBinary.this.getSymbolTable());
        }
    }

    private static class IonReaderBinarySpan
    extends DowncastingFaceted
    implements OffsetSpan,
    Span {
        final long bufferOffset;
        final long bufferLimit;
        final long totalOffset;
        final SymbolTable symbolTable;

        IonReaderBinarySpan(long bufferOffset, long bufferLimit, long totalOffset, SymbolTable symbolTable) {
            this.bufferOffset = bufferOffset;
            this.bufferLimit = bufferLimit;
            this.totalOffset = totalOffset;
            this.symbolTable = symbolTable;
        }

        @Override
        public long getStartOffset() {
            return this.totalOffset;
        }

        @Override
        public long getFinishOffset() {
            return this.totalOffset + (this.bufferLimit - this.bufferOffset);
        }
    }
}

