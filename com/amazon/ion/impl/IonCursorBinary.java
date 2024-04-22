/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.BufferConfiguration;
import com.amazon.ion.IonBufferConfiguration;
import com.amazon.ion.IonCursor;
import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.IvmNotificationConsumer;
import com.amazon.ion.impl.IonTypeID;
import com.amazon.ion.impl.Marker;
import com.amazon.ion.util.IonStreamUtils;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

class IonCursorBinary
implements IonCursor {
    private static final int LOWER_SEVEN_BITS_BITMASK = 127;
    private static final int HIGHEST_BIT_BITMASK = 128;
    private static final int VALUE_BITS_PER_VARUINT_BYTE = 7;
    private static final int MAXIMUM_SUPPORTED_VAR_UINT_BYTES = 9;
    private static final int IVM_START_BYTE = 224;
    private static final int IVM_FINAL_BYTE = 234;
    private static final int IVM_REMAINING_LENGTH = 3;
    private static final int SINGLE_BYTE_MASK = 255;
    private static final int LIST_TYPE_ORDINAL = IonType.LIST.ordinal();
    private static final IvmNotificationConsumer NO_OP_IVM_NOTIFICATION_CONSUMER = (x, y) -> {};
    private static final int CONTAINER_STACK_INITIAL_CAPACITY = 8;
    private static final int DELIMITED_MARKER = -1;
    Marker[] containerStack = new Marker[8];
    int containerIndex = -1;
    Marker parent = null;
    private final long startOffset;
    private long offset;
    long limit;
    ByteBuffer byteBuffer;
    private final BufferConfiguration.DataHandler dataHandler;
    final Marker annotationSequenceMarker = new Marker(-1, 0);
    boolean hasAnnotations = false;
    final Marker valueMarker = new Marker(-1, 0);
    long valuePreHeaderIndex = 0L;
    IonTypeID valueTid = null;
    private IvmNotificationConsumer ivmConsumer = NO_OP_IVM_NOTIFICATION_CONSUMER;
    IonCursor.Event event = IonCursor.Event.NEEDS_DATA;
    byte[] buffer;
    private int majorVersion = -1;
    int minorVersion = 0;
    int fieldSid = -1;
    private long checkpoint;
    private long peekIndex;
    private IonTypeID[] typeIds = IonTypeID.TYPE_IDS_NO_IVM;
    private final RefillableState refillableState;
    private CheckpointLocation checkpointLocation = CheckpointLocation.BEFORE_UNANNOTATED_TYPE_ID;
    boolean isSlowMode;
    boolean isValueIncomplete = false;
    private long lastReportedByteTotal = 0L;
    private static final IonBufferConfiguration[] FIXED_SIZE_CONFIGURATIONS;

    private static BufferConfiguration.DataHandler getDataHandler(IonBufferConfiguration configuration) {
        BufferConfiguration.DataHandler dataHandler = configuration.getDataHandler();
        return dataHandler == IonBufferConfiguration.DEFAULT.getDataHandler() ? null : dataHandler;
    }

    IonCursorBinary(IonBufferConfiguration configuration, byte[] buffer, int offset, int length) {
        this.dataHandler = IonCursorBinary.getDataHandler(configuration);
        this.peekIndex = offset;
        this.valuePreHeaderIndex = offset;
        this.checkpoint = this.peekIndex;
        for (int i = 0; i < 8; ++i) {
            this.containerStack[i] = new Marker(-1, -1);
        }
        this.buffer = buffer;
        this.startOffset = offset;
        this.offset = offset;
        this.limit = offset + length;
        this.byteBuffer = ByteBuffer.wrap(buffer, offset, length);
        this.isSlowMode = false;
        this.refillableState = null;
    }

    private static int logBase2(int value) {
        return 32 - Integer.numberOfLeadingZeros(value == 0 ? 0 : value - 1);
    }

    static int nextPowerOfTwo(int value) {
        long highBit = Integer.toUnsignedLong(Integer.highestOneBit(value));
        return (int)(highBit == (long)value ? (long)value : highBit << 1);
    }

    private static void validate(IonBufferConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("Buffer configuration must not be null.");
        }
        if (configuration.getInitialBufferSize() < 1) {
            throw new IllegalArgumentException("Initial buffer size must be at least 1.");
        }
        if (configuration.getMaximumBufferSize() < configuration.getInitialBufferSize()) {
            throw new IllegalArgumentException("Maximum buffer size cannot be less than the initial buffer size.");
        }
    }

    private static IonBufferConfiguration getFixedSizeConfigurationFor(ByteArrayInputStream inputStream, int alreadyReadLen) {
        int fixedBufferSize = Math.max(0, inputStream.available());
        if (alreadyReadLen > 0) {
            fixedBufferSize += alreadyReadLen;
        }
        if (IonBufferConfiguration.DEFAULT.getInitialBufferSize() > fixedBufferSize) {
            return FIXED_SIZE_CONFIGURATIONS[IonCursorBinary.logBase2(fixedBufferSize)];
        }
        return IonBufferConfiguration.DEFAULT;
    }

    IonCursorBinary(IonBufferConfiguration configuration, InputStream inputStream, byte[] alreadyRead, int alreadyReadOff, int alreadyReadLen) {
        if (configuration == IonBufferConfiguration.DEFAULT) {
            this.dataHandler = null;
            if (inputStream instanceof ByteArrayInputStream) {
                configuration = IonCursorBinary.getFixedSizeConfigurationFor((ByteArrayInputStream)inputStream, alreadyReadLen);
            }
        } else {
            IonCursorBinary.validate(configuration);
            this.dataHandler = IonCursorBinary.getDataHandler(configuration);
        }
        this.peekIndex = 0L;
        this.checkpoint = 0L;
        for (int i = 0; i < 8; ++i) {
            this.containerStack[i] = new Marker(-1, -1);
        }
        this.buffer = new byte[configuration.getInitialBufferSize()];
        this.startOffset = 0L;
        this.offset = 0L;
        this.limit = 0L;
        if (alreadyReadLen > 0) {
            System.arraycopy(alreadyRead, alreadyReadOff, this.buffer, 0, alreadyReadLen);
            this.limit = alreadyReadLen;
        }
        this.byteBuffer = ByteBuffer.wrap(this.buffer, 0, configuration.getInitialBufferSize());
        this.isSlowMode = true;
        this.refillableState = new RefillableState(inputStream, configuration.getInitialBufferSize(), configuration.getMaximumBufferSize());
        this.registerOversizedValueHandler(configuration.getOversizedValueHandler());
    }

    private long availableAt(long index) {
        return this.limit - index;
    }

    private boolean ensureCapacity(long minimumNumberOfBytesRequired) {
        if (this.freeSpaceAt(this.offset) >= minimumNumberOfBytesRequired) {
            return true;
        }
        int maximumFreeSpace = this.refillableState.maximumBufferSize;
        int startOffset = (int)this.offset;
        if (minimumNumberOfBytesRequired > (long)maximumFreeSpace) {
            this.refillableState.isSkippingCurrentValue = true;
            return false;
        }
        long shortfall = minimumNumberOfBytesRequired - this.refillableState.capacity;
        if (shortfall > 0L) {
            int newSize = (int)Math.min(Math.max(this.refillableState.capacity * 2L, (long)IonCursorBinary.nextPowerOfTwo((int)(this.refillableState.capacity + shortfall))), (long)maximumFreeSpace);
            byte[] newBuffer = new byte[newSize];
            this.moveBytesToStartOfBuffer(newBuffer, startOffset);
            this.refillableState.capacity = newSize;
            this.buffer = newBuffer;
            this.byteBuffer = ByteBuffer.wrap(this.buffer, (int)this.offset, (int)this.refillableState.capacity);
        } else {
            this.moveBytesToStartOfBuffer(this.buffer, startOffset);
        }
        return true;
    }

    private boolean fillAt(long index, long numberOfBytes) {
        long shortfall = numberOfBytes - this.availableAt(index);
        if (shortfall > 0L) {
            this.refillableState.bytesRequested = numberOfBytes + (index - this.offset);
            shortfall = this.ensureCapacity(this.refillableState.bytesRequested) ? this.refill(this.refillableState.bytesRequested) : 0L;
        }
        if (shortfall <= 0L) {
            this.refillableState.bytesRequested = 0L;
            this.refillableState.state = State.READY;
            return true;
        }
        this.refillableState.state = State.FILL;
        return false;
    }

    private void moveBytesToStartOfBuffer(byte[] destinationBuffer, int fromIndex) {
        long size = this.availableAt(fromIndex);
        if (size > 0L) {
            System.arraycopy(this.buffer, fromIndex, destinationBuffer, 0, (int)size);
        }
        if (fromIndex > 0) {
            this.shiftIndicesLeft(fromIndex);
        }
        this.offset = 0L;
        this.limit = size;
    }

    private long freeSpaceAt(long index) {
        return this.refillableState.capacity - index;
    }

    private int readByteWithoutBuffering() {
        int b = -1;
        try {
            b = this.refillableState.inputStream.read();
        } catch (EOFException eOFException) {
        } catch (IOException e) {
            IonStreamUtils.throwAsIonException(e);
        }
        if (b >= 0) {
            ++this.refillableState.individualBytesSkippedWithoutBuffering;
        }
        return b;
    }

    private int slowPeekByte() {
        if (this.refillableState.isSkippingCurrentValue) {
            return this.readByteWithoutBuffering();
        }
        return this.buffer[(int)this.peekIndex++] & 0xFF;
    }

    private int slowReadByte() {
        if (this.refillableState.isSkippingCurrentValue) {
            return this.readByteWithoutBuffering();
        }
        if (!this.fillAt(this.peekIndex, 1L)) {
            return -1;
        }
        return this.slowPeekByte();
    }

    private void shiftContainerEnds(long shiftAmount) {
        for (int i = this.containerIndex; i >= 0; --i) {
            if (this.containerStack[i].endIndex <= 0L) continue;
            this.containerStack[i].endIndex -= shiftAmount;
        }
    }

    private void shiftIndicesLeft(int shiftAmount) {
        this.peekIndex = Math.max(this.peekIndex - (long)shiftAmount, 0L);
        this.valuePreHeaderIndex -= (long)shiftAmount;
        this.valueMarker.startIndex -= (long)shiftAmount;
        this.valueMarker.endIndex -= (long)shiftAmount;
        this.checkpoint -= (long)shiftAmount;
        if (this.annotationSequenceMarker.startIndex > -1L) {
            this.annotationSequenceMarker.startIndex -= (long)shiftAmount;
            this.annotationSequenceMarker.endIndex -= (long)shiftAmount;
        }
        this.shiftContainerEnds(shiftAmount);
        this.refillableState.totalDiscardedBytes += (long)shiftAmount;
    }

    private long refill(long minimumNumberOfBytesRequired) {
        long shortfall;
        int numberOfBytesFilled = -1;
        do {
            try {
                numberOfBytesFilled = this.refillableState.inputStream.read(this.buffer, (int)this.limit, (int)this.freeSpaceAt(this.limit));
            } catch (EOFException e) {
                numberOfBytesFilled = -1;
            } catch (IOException e) {
                IonStreamUtils.throwAsIonException(e);
            }
            if (numberOfBytesFilled <= 0) continue;
            this.limit += (long)numberOfBytesFilled;
        } while ((shortfall = minimumNumberOfBytesRequired - this.availableAt(this.offset)) > 0L && numberOfBytesFilled >= 0);
        return shortfall;
    }

    private boolean slowSeek(long numberOfBytes) {
        long shortfall;
        long size = this.availableAt(this.offset);
        long unbufferedBytesToSkip = numberOfBytes - size;
        if (unbufferedBytesToSkip <= 0L) {
            this.offset += numberOfBytes;
            this.refillableState.bytesRequested = 0L;
            this.refillableState.state = State.READY;
            return false;
        }
        this.offset = this.limit;
        long skipped = 0L;
        do {
            try {
                skipped = this.refillableState.inputStream.skip(unbufferedBytesToSkip);
            } catch (EOFException e) {
                skipped = 0L;
            } catch (IOException e) {
                IonStreamUtils.throwAsIonException(e);
            }
            this.refillableState.totalDiscardedBytes += skipped;
            this.shiftContainerEnds(skipped);
            unbufferedBytesToSkip = shortfall = unbufferedBytesToSkip - skipped;
        } while (shortfall > 0L && skipped > 0L);
        if (shortfall <= 0L) {
            this.refillableState.bytesRequested = 0L;
            this.refillableState.state = State.READY;
            return false;
        }
        this.refillableState.bytesRequested = shortfall;
        this.refillableState.state = State.SEEK;
        return true;
    }

    private long uncheckedReadVarUInt_1_0(byte currentByte) {
        long result = currentByte & 0x7F;
        do {
            if (this.peekIndex >= this.limit) {
                throw new IonException("Malformed data: declared length exceeds the number of bytes remaining in the stream.");
            }
            currentByte = this.buffer[(int)this.peekIndex++];
            result = result << 7 | (long)(currentByte & 0x7F);
        } while (currentByte >= 0);
        return result;
    }

    private long slowReadVarUInt_1_0() {
        long value = 0L;
        for (int numberOfBytesRead = 0; numberOfBytesRead < 9; ++numberOfBytesRead) {
            int currentByte = this.slowReadByte();
            if (currentByte < 0) {
                return -1L;
            }
            value = value << 7 | (long)(currentByte & 0x7F);
            if ((currentByte & 0x80) == 0) continue;
            return value;
        }
        throw new IonException("Found a VarUInt that was too large to fit in a `long`");
    }

    private boolean uncheckedReadAnnotationWrapperHeader_1_0(IonTypeID valueTid) {
        long endIndex;
        byte b;
        if (valueTid.variableLength) {
            if (this.peekIndex >= this.limit) {
                throw new IonException("Malformed data: declared length exceeds the number of bytes remaining in the stream.");
            }
            endIndex = (b = this.buffer[(int)this.peekIndex++]) < 0 ? (long)(b & 0x7F) : this.uncheckedReadVarUInt_1_0(b);
        } else {
            endIndex = valueTid.length;
        }
        this.setMarker(endIndex += this.peekIndex, this.valueMarker);
        if (endIndex > this.limit) {
            throw new IonException("Malformed data: declared length exceeds the number of bytes remaining in the stream.");
        }
        int annotationsLength = (b = this.buffer[(int)this.peekIndex++]) < 0 ? b & 0x7F : (int)this.uncheckedReadVarUInt_1_0(b);
        this.annotationSequenceMarker.startIndex = this.peekIndex;
        this.peekIndex = this.annotationSequenceMarker.endIndex = this.annotationSequenceMarker.startIndex + (long)annotationsLength;
        if (this.peekIndex >= endIndex) {
            throw new IonException("Annotation wrapper must wrap a value.");
        }
        return false;
    }

    private boolean slowReadAnnotationWrapperHeader_1_0(IonTypeID valueTid) {
        long valueLength;
        if (valueTid.variableLength) {
            if (!this.fillAt(this.peekIndex, 4L)) {
                return true;
            }
            valueLength = this.slowReadVarUInt_1_0();
            if (valueLength < 0L) {
                return true;
            }
        } else {
            if (!this.fillAt(this.peekIndex, 3L)) {
                return true;
            }
            valueLength = valueTid.length;
        }
        this.setMarker(this.peekIndex + valueLength, this.valueMarker);
        int annotationsLength = (int)this.slowReadVarUInt_1_0();
        if (annotationsLength < 0) {
            return true;
        }
        if (!this.fillAt(this.peekIndex, annotationsLength)) {
            return true;
        }
        if (this.refillableState.isSkippingCurrentValue) {
            return true;
        }
        this.annotationSequenceMarker.typeId = valueTid;
        this.annotationSequenceMarker.startIndex = this.peekIndex;
        this.peekIndex = this.annotationSequenceMarker.endIndex = this.annotationSequenceMarker.startIndex + (long)annotationsLength;
        if (this.peekIndex >= this.valueMarker.endIndex) {
            throw new IonException("Annotation wrapper must wrap a value.");
        }
        return false;
    }

    private void prohibitEmptyOrderedStruct_1_0(Marker marker) {
        if (marker.typeId.type == IonType.STRUCT && marker.typeId.lowerNibble == 1 && marker.endIndex == this.peekIndex) {
            throw new IonException("Ordered struct must not be empty.");
        }
    }

    private long calculateEndIndex_1_0(IonTypeID valueTid, boolean isAnnotated) {
        long endIndex;
        if (valueTid.variableLength) {
            byte b;
            if (this.peekIndex >= this.limit) {
                throw new IonException("Malformed data: declared length exceeds the number of bytes remaining in the stream.");
            }
            if ((b = this.buffer[(int)this.peekIndex++]) < 0) {
                endIndex = (long)(b & 0x7F) + this.peekIndex;
            } else {
                endIndex = this.uncheckedReadVarUInt_1_0(b) + this.peekIndex;
                if (endIndex < 0L) {
                    throw new IonException("Unsupported value: declared length is too long.");
                }
            }
        } else {
            endIndex = (long)valueTid.length + this.peekIndex;
        }
        if (valueTid.type != null && valueTid.type.ordinal() >= LIST_TYPE_ORDINAL) {
            this.event = IonCursor.Event.START_CONTAINER;
        } else if (valueTid.isNopPad) {
            this.uncheckedSeekPastNopPad(endIndex, isAnnotated);
        } else {
            this.event = IonCursor.Event.START_SCALAR;
        }
        return endIndex;
    }

    private boolean slowReadFieldName_1_0() {
        if (!this.fillAt(this.peekIndex, 2L)) {
            return true;
        }
        this.fieldSid = (int)this.slowReadVarUInt_1_0();
        return this.fieldSid < 0;
    }

    private long uncheckedReadVarUInt_1_1() {
        throw new UnsupportedOperationException();
    }

    private long slowReadVarUInt_1_1() {
        throw new UnsupportedOperationException();
    }

    private boolean uncheckedReadAnnotationWrapperHeader_1_1(IonTypeID valueTid) {
        throw new UnsupportedOperationException();
    }

    private boolean slowReadAnnotationWrapperHeader_1_1(IonTypeID valueTid) {
        throw new UnsupportedOperationException();
    }

    private long calculateEndIndex_1_1(IonTypeID valueTid, boolean isAnnotated) {
        throw new UnsupportedOperationException();
    }

    private void uncheckedReadFieldName_1_1() {
        throw new UnsupportedOperationException();
    }

    private boolean slowReadFieldName_1_1() {
        throw new UnsupportedOperationException();
    }

    private boolean uncheckedIsDelimitedEnd_1_1() {
        throw new UnsupportedOperationException();
    }

    private boolean slowIsDelimitedEnd_1_1() {
        throw new UnsupportedOperationException();
    }

    boolean skipRemainingDelimitedContainerElements_1_1() {
        throw new UnsupportedOperationException();
    }

    private void seekPastDelimitedContainer_1_1() {
        throw new UnsupportedOperationException();
    }

    private boolean slowFindDelimitedEnd_1_1() {
        throw new UnsupportedOperationException();
    }

    private boolean slowSeekToDelimitedEnd_1_1() {
        throw new UnsupportedOperationException();
    }

    private boolean slowFillDelimitedContainer_1_1() {
        throw new UnsupportedOperationException();
    }

    private boolean slowSkipRemainingDelimitedContainerElements_1_1() {
        throw new UnsupportedOperationException();
    }

    private boolean slowMakeBufferReady() {
        boolean isReady;
        switch (this.refillableState.state) {
            case SEEK: {
                isReady = !this.slowSeek(this.refillableState.bytesRequested);
                break;
            }
            case FILL: {
                isReady = this.fillAt(this.offset, this.refillableState.bytesRequested);
                break;
            }
            case FILL_DELIMITED: {
                this.refillableState.state = State.READY;
                isReady = this.slowFindDelimitedEnd_1_1();
                break;
            }
            case SEEK_DELIMITED: {
                isReady = this.slowSeekToDelimitedEnd_1_1();
                break;
            }
            case TERMINATED: {
                isReady = false;
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        if (!isReady) {
            this.event = IonCursor.Event.NEEDS_DATA;
        }
        return isReady;
    }

    private void setCheckpoint(CheckpointLocation location) {
        this.checkpointLocation = location;
        this.checkpoint = this.peekIndex;
    }

    private void setCheckpointBeforeUnannotatedTypeId() {
        this.reset();
        this.offset = this.peekIndex;
        this.checkpointLocation = CheckpointLocation.BEFORE_UNANNOTATED_TYPE_ID;
        this.checkpoint = this.peekIndex;
    }

    private void setMarker(long endIndex, Marker markerToSet) {
        if (this.parent != null && endIndex > this.parent.endIndex && this.parent.endIndex > -1L) {
            throw new IonException("Value exceeds the length of its parent container.");
        }
        markerToSet.startIndex = this.peekIndex;
        markerToSet.endIndex = endIndex;
    }

    private boolean checkContainerEnd() {
        if (this.parent.endIndex > this.peekIndex) {
            return false;
        }
        if (this.parent.endIndex == -1L) {
            return this.isSlowMode ? this.slowIsDelimitedEnd_1_1() : this.uncheckedIsDelimitedEnd_1_1();
        }
        if (this.parent.endIndex == this.peekIndex) {
            this.event = IonCursor.Event.END_CONTAINER;
            this.valueTid = null;
            this.fieldSid = -1;
            return true;
        }
        throw new IonException("Contained values overflowed the parent container length.");
    }

    private void reset() {
        this.valueMarker.startIndex = -1L;
        this.valueMarker.endIndex = -1L;
        this.fieldSid = -1;
        this.hasAnnotations = false;
    }

    private void readIvm() {
        this.majorVersion = this.buffer[(int)this.peekIndex++];
        this.minorVersion = this.buffer[(int)this.peekIndex++];
        if ((this.buffer[(int)this.peekIndex++] & 0xFF) != 234) {
            throw new IonException("Invalid Ion version marker.");
        }
        if (this.majorVersion != 1) {
            throw new IonException(String.format("Unsupported Ion version: %d.%d", this.majorVersion, this.minorVersion));
        }
        if (this.minorVersion != 0) {
            throw new IonException(String.format("Unsupported Ion version: %d.%d", this.majorVersion, this.minorVersion));
        }
        this.typeIds = IonTypeID.TYPE_IDS_1_0;
        this.ivmConsumer.ivmEncountered(this.majorVersion, this.minorVersion);
    }

    private void uncheckedSeekPastNopPad(long endIndex, boolean isAnnotated) {
        if (isAnnotated) {
            throw new IonException("Invalid annotation wrapper: NOP pad may not occur inside an annotation wrapper.");
        }
        if (endIndex > this.limit) {
            throw new IonException("Invalid NOP pad.");
        }
        this.peekIndex = endIndex;
        if (this.parent != null) {
            this.checkContainerEnd();
        }
    }

    private boolean slowSeekPastNopPad(long valueLength, boolean isAnnotated) {
        if (isAnnotated) {
            throw new IonException("Invalid annotation wrapper: NOP pad may not occur inside an annotation wrapper.");
        }
        if (this.slowSeek(this.peekIndex + valueLength - this.offset)) {
            this.event = IonCursor.Event.NEEDS_DATA;
            return true;
        }
        this.peekIndex = this.offset;
        this.setCheckpointBeforeUnannotatedTypeId();
        if (this.parent != null) {
            this.checkContainerEnd();
        }
        return false;
    }

    private void validateAnnotationWrapperEndIndex(long endIndex) {
        if (this.valueMarker.endIndex >= 0L && endIndex != this.valueMarker.endIndex) {
            throw new IonException("Annotation wrapper length does not match the length of the wrapped value.");
        }
    }

    private boolean uncheckedReadHeader(int typeIdByte, boolean isAnnotated, Marker markerToSet) {
        long endIndex;
        IonTypeID valueTid = this.typeIds[typeIdByte];
        if (!valueTid.isValid) {
            throw new IonException("Invalid type ID.");
        }
        if (valueTid.type == IonTypeID.ION_TYPE_ANNOTATION_WRAPPER) {
            if (isAnnotated) {
                throw new IonException("Nested annotation wrappers are invalid.");
            }
            if (this.minorVersion == 0 ? this.uncheckedReadAnnotationWrapperHeader_1_0(valueTid) : this.uncheckedReadAnnotationWrapperHeader_1_1(valueTid)) {
                return true;
            }
            this.hasAnnotations = true;
            return this.uncheckedReadHeader(this.buffer[(int)this.peekIndex++] & 0xFF, true, this.valueMarker);
        }
        long l = endIndex = this.minorVersion == 0 ? this.calculateEndIndex_1_0(valueTid, isAnnotated) : this.calculateEndIndex_1_1(valueTid, isAnnotated);
        if (isAnnotated) {
            this.validateAnnotationWrapperEndIndex(endIndex);
        }
        this.setMarker(endIndex, markerToSet);
        if (endIndex > this.limit) {
            this.isValueIncomplete = true;
        }
        markerToSet.typeId = valueTid;
        if (this.event == IonCursor.Event.START_CONTAINER) {
            if (this.minorVersion == 0) {
                this.prohibitEmptyOrderedStruct_1_0(markerToSet);
            }
            return true;
        }
        return this.event == IonCursor.Event.START_SCALAR;
    }

    private boolean slowReadHeader(int typeIdByte, boolean isAnnotated, Marker markerToSet) {
        IonTypeID valueTid = this.typeIds[typeIdByte];
        if (!valueTid.isValid) {
            throw new IonException("Invalid type ID.");
        }
        if (valueTid.type == IonTypeID.ION_TYPE_ANNOTATION_WRAPPER) {
            if (isAnnotated) {
                throw new IonException("Nested annotation wrappers are invalid.");
            }
            this.hasAnnotations = true;
            if (this.minorVersion == 0 ? this.slowReadAnnotationWrapperHeader_1_0(valueTid) : this.slowReadAnnotationWrapperHeader_1_1(valueTid)) {
                return true;
            }
            this.setCheckpoint(CheckpointLocation.BEFORE_ANNOTATED_TYPE_ID);
        } else if (this.slowReadValueHeader(valueTid, isAnnotated, markerToSet)) {
            if (this.refillableState.isSkippingCurrentValue) {
                markerToSet.typeId = valueTid;
            }
            return true;
        }
        markerToSet.typeId = valueTid;
        if (this.checkpointLocation == CheckpointLocation.AFTER_SCALAR_HEADER) {
            return true;
        }
        if (this.checkpointLocation == CheckpointLocation.AFTER_CONTAINER_HEADER) {
            if (this.minorVersion == 0) {
                this.prohibitEmptyOrderedStruct_1_0(markerToSet);
            }
            return true;
        }
        return false;
    }

    private boolean slowReadValueHeader(IonTypeID valueTid, boolean isAnnotated, Marker markerToSet) {
        long valueLength = 0L;
        long endIndex = 0L;
        if (valueTid.isDelimited) {
            endIndex = -1L;
        } else if (valueTid.variableLength) {
            if (!this.fillAt(this.peekIndex, 2L)) {
                return true;
            }
            long l = valueLength = this.minorVersion == 0 ? this.slowReadVarUInt_1_0() : this.slowReadVarUInt_1_1();
            if (valueLength < 0L) {
                return true;
            }
        } else {
            valueLength = valueTid.length;
        }
        if (valueTid.type != null && valueTid.type.ordinal() >= LIST_TYPE_ORDINAL) {
            this.setCheckpoint(CheckpointLocation.AFTER_CONTAINER_HEADER);
            this.event = IonCursor.Event.START_CONTAINER;
        } else if (valueTid.isNopPad) {
            if (this.slowSeekPastNopPad(valueLength, isAnnotated)) {
                return true;
            }
            valueLength = 0L;
        } else {
            this.setCheckpoint(CheckpointLocation.AFTER_SCALAR_HEADER);
            this.event = IonCursor.Event.START_SCALAR;
        }
        if (endIndex != -1L && (endIndex = this.refillableState.isSkippingCurrentValue ? this.peekIndex + valueLength + (long)this.refillableState.individualBytesSkippedWithoutBuffering : this.peekIndex + valueLength) < 0L) {
            throw new IonException("Unsupported value: declared length is too long.");
        }
        if (isAnnotated) {
            this.validateAnnotationWrapperEndIndex(endIndex);
        }
        this.setMarker(endIndex, markerToSet);
        return false;
    }

    private void growContainerStack() {
        Marker[] newStack = new Marker[this.containerStack.length * 2];
        System.arraycopy(this.containerStack, 0, newStack, 0, this.containerStack.length);
        for (int i = this.containerStack.length; i < newStack.length; ++i) {
            newStack[i] = new Marker(-1, -1);
        }
        this.containerStack = newStack;
    }

    private void pushContainer() {
        if (++this.containerIndex >= this.containerStack.length) {
            this.growContainerStack();
        }
        this.parent = this.containerStack[this.containerIndex];
    }

    private void uncheckedStepIntoContainer() {
        if (this.valueTid == null || this.valueTid.type.ordinal() < LIST_TYPE_ORDINAL) {
            throw new IllegalStateException("Must be positioned on a container to step in.");
        }
        this.pushContainer();
        this.parent.typeId = this.valueTid;
        this.parent.endIndex = this.valueTid.isDelimited ? -1L : this.valueMarker.endIndex;
        this.valueTid = null;
        this.event = IonCursor.Event.NEEDS_INSTRUCTION;
        this.reset();
    }

    private IonCursor.Event slowStepIntoContainer() {
        if (this.refillableState.state != State.READY && !this.slowMakeBufferReady()) {
            return this.event;
        }
        if (this.checkpointLocation != CheckpointLocation.AFTER_CONTAINER_HEADER) {
            throw new IllegalStateException("Must be positioned on a container to step in.");
        }
        this.pushContainer();
        if (this.containerIndex == this.refillableState.fillDepth) {
            this.isSlowMode = false;
        }
        this.parent.typeId = this.valueMarker.typeId;
        this.parent.endIndex = this.valueTid.isDelimited ? -1L : this.valueMarker.endIndex;
        this.setCheckpointBeforeUnannotatedTypeId();
        this.valueTid = null;
        this.hasAnnotations = false;
        this.event = IonCursor.Event.NEEDS_INSTRUCTION;
        return this.event;
    }

    @Override
    public IonCursor.Event stepIntoContainer() {
        if (this.isSlowMode) {
            if (this.containerIndex != this.refillableState.fillDepth - 1) {
                if (this.valueMarker.endIndex > -1L && this.valueMarker.endIndex <= this.limit) {
                    this.refillableState.fillDepth = this.containerIndex + 1;
                } else {
                    return this.slowStepIntoContainer();
                }
            }
            this.isSlowMode = false;
        }
        this.uncheckedStepIntoContainer();
        return this.event;
    }

    private void resumeSlowMode() {
        this.refillableState.fillDepth = -1;
        this.isSlowMode = true;
    }

    @Override
    public IonCursor.Event stepOutOfContainer() {
        if (this.isSlowMode) {
            return this.slowStepOutOfContainer();
        }
        if (this.parent == null) {
            throw new IllegalStateException("Cannot step out at top level.");
        }
        if (this.parent.endIndex == -1L) {
            if (this.skipRemainingDelimitedContainerElements_1_1()) {
                return this.event;
            }
        } else {
            this.peekIndex = this.parent.endIndex;
        }
        if (!this.isSlowMode) {
            this.setCheckpointBeforeUnannotatedTypeId();
        }
        if (--this.containerIndex >= 0) {
            this.parent = this.containerStack[this.containerIndex];
            if (this.refillableState != null && this.containerIndex < this.refillableState.fillDepth) {
                this.resumeSlowMode();
            }
        } else {
            this.parent = null;
            this.containerIndex = -1;
            if (this.refillableState != null) {
                this.resumeSlowMode();
            }
        }
        this.valueTid = null;
        this.event = IonCursor.Event.NEEDS_INSTRUCTION;
        return this.event;
    }

    private IonCursor.Event slowStepOutOfContainer() {
        if (this.parent == null) {
            throw new IllegalStateException("Cannot step out at top level.");
        }
        if (this.refillableState.state != State.READY && !this.slowMakeBufferReady()) {
            return this.event;
        }
        this.event = IonCursor.Event.NEEDS_DATA;
        if (this.parent.endIndex == -1L) {
            if (this.slowSkipRemainingDelimitedContainerElements_1_1()) {
                return this.event;
            }
        } else {
            if (this.slowSeek(this.parent.endIndex - this.offset)) {
                return this.event;
            }
            this.peekIndex = this.offset;
        }
        this.setCheckpointBeforeUnannotatedTypeId();
        if (--this.containerIndex >= 0) {
            this.parent = this.containerStack[this.containerIndex];
        } else {
            this.parent = null;
            this.containerIndex = -1;
        }
        this.event = IonCursor.Event.NEEDS_INSTRUCTION;
        this.valueTid = null;
        this.hasAnnotations = false;
        return this.event;
    }

    private boolean uncheckedNextContainedToken() {
        if (this.parent.endIndex == -1L) {
            return this.uncheckedIsDelimitedEnd_1_1();
        }
        if (this.parent.endIndex == this.peekIndex) {
            this.event = IonCursor.Event.END_CONTAINER;
            return true;
        }
        if (this.parent.endIndex < this.peekIndex) {
            throw new IonException("Contained values overflowed the parent container length.");
        }
        if (this.parent.typeId.type == IonType.STRUCT) {
            if (this.minorVersion == 0) {
                byte b;
                this.fieldSid = (b = this.buffer[(int)this.peekIndex++]) < 0 ? b & 0x7F : (int)this.uncheckedReadVarUInt_1_0(b);
            } else {
                this.uncheckedReadFieldName_1_1();
            }
        }
        this.valuePreHeaderIndex = this.peekIndex;
        return false;
    }

    private void reportConsumedData() {
        long totalNumberOfBytesRead = this.getTotalOffset() + (this.peekIndex - this.valuePreHeaderIndex);
        this.dataHandler.onData((int)(totalNumberOfBytesRead - this.lastReportedByteTotal));
        this.lastReportedByteTotal = totalNumberOfBytesRead;
    }

    private boolean uncheckedNextToken() {
        int b;
        if (this.peekIndex < this.valueMarker.endIndex) {
            this.peekIndex = this.valueMarker.endIndex;
        } else if (this.valueTid != null && this.valueTid.isDelimited) {
            this.seekPastDelimitedContainer_1_1();
        }
        this.valueTid = null;
        if (this.dataHandler != null) {
            this.reportConsumedData();
        }
        if (this.peekIndex >= this.limit) {
            this.setCheckpointBeforeUnannotatedTypeId();
            if (this.parent != null && this.parent.endIndex == this.peekIndex) {
                this.event = IonCursor.Event.END_CONTAINER;
            }
            return false;
        }
        this.reset();
        if (this.parent == null) {
            this.valuePreHeaderIndex = this.peekIndex;
            if ((b = this.buffer[(int)this.peekIndex++] & 0xFF) == 224) {
                this.readIvm();
                return true;
            }
        } else {
            if (this.uncheckedNextContainedToken()) {
                return false;
            }
            if (this.peekIndex >= this.limit) {
                throw new IonException("Malformed data: declared length exceeds the number of bytes remaining in the container.");
            }
            b = this.buffer[(int)this.peekIndex++] & 0xFF;
        }
        if (this.uncheckedReadHeader(b, false, this.valueMarker)) {
            this.valueTid = this.valueMarker.typeId;
            return false;
        }
        this.valueTid = this.valueMarker.typeId;
        return true;
    }

    private void slowNextToken() {
        this.peekIndex = this.checkpoint;
        this.event = IonCursor.Event.NEEDS_DATA;
        while (!(this.refillableState.state != State.READY && !this.slowMakeBufferReady() || this.parent != null && this.checkContainerEnd())) {
            switch (this.checkpointLocation) {
                case BEFORE_UNANNOTATED_TYPE_ID: {
                    if (this.dataHandler != null) {
                        this.reportConsumedData();
                    }
                    this.valueTid = null;
                    this.hasAnnotations = false;
                    if (this.parent != null && this.parent.typeId.type == IonType.STRUCT && (this.minorVersion == 0 ? this.slowReadFieldName_1_0() : this.slowReadFieldName_1_1())) {
                        return;
                    }
                    this.valuePreHeaderIndex = this.peekIndex;
                    int b = this.slowReadByte();
                    if (b < 0) {
                        return;
                    }
                    if (b == 224 && this.parent == null) {
                        if (!this.fillAt(this.peekIndex, 3L)) {
                            return;
                        }
                        this.readIvm();
                        this.setCheckpointBeforeUnannotatedTypeId();
                        break;
                    }
                    if (this.slowReadHeader(b, false, this.valueMarker)) {
                        this.valueTid = this.valueMarker.typeId;
                        return;
                    }
                    this.valueTid = this.valueMarker.typeId;
                    break;
                }
                case BEFORE_ANNOTATED_TYPE_ID: {
                    this.valueTid = null;
                    int b = this.slowReadByte();
                    if (b < 0) {
                        return;
                    }
                    this.slowReadHeader(b, true, this.valueMarker);
                    this.valueTid = this.valueMarker.typeId;
                    return;
                }
                case AFTER_SCALAR_HEADER: 
                case AFTER_CONTAINER_HEADER: {
                    if (!this.slowSkipRemainingValueBytes()) break;
                    return;
                }
            }
        }
        return;
    }

    private boolean slowSkipRemainingValueBytes() {
        if (this.valueMarker.endIndex == -1L && this.valueTid != null && this.valueTid.isDelimited) {
            this.seekPastDelimitedContainer_1_1();
            if (this.event == IonCursor.Event.NEEDS_DATA) {
                return true;
            }
        } else if (this.limit >= this.valueMarker.endIndex) {
            this.offset = this.valueMarker.endIndex;
        } else if (this.slowSeek(this.valueMarker.endIndex - this.offset)) {
            return true;
        }
        this.valuePreHeaderIndex = this.peekIndex = this.offset;
        if (this.refillableState.fillDepth > this.containerIndex) {
            this.refillableState.fillDepth = -1;
        }
        this.setCheckpointBeforeUnannotatedTypeId();
        return false;
    }

    private IonCursor.Event slowOverflowableNextToken() {
        while (true) {
            this.slowNextToken();
            if (!this.refillableState.isSkippingCurrentValue) break;
            this.seekPastOversizedValue();
        }
        return this.event;
    }

    private void seekPastOversizedValue() {
        this.refillableState.oversizedValueHandler.onOversizedValue();
        if (this.refillableState.state != State.TERMINATED) {
            this.slowSeek(this.valueMarker.endIndex - this.offset - (long)this.refillableState.individualBytesSkippedWithoutBuffering);
            this.refillableState.totalDiscardedBytes += (long)this.refillableState.individualBytesSkippedWithoutBuffering;
            this.peekIndex = this.offset;
            this.shiftContainerEnds(this.refillableState.individualBytesSkippedWithoutBuffering);
            this.setCheckpointBeforeUnannotatedTypeId();
        }
        this.refillableState.isSkippingCurrentValue = false;
        this.refillableState.individualBytesSkippedWithoutBuffering = 0;
    }

    @Override
    public IonCursor.Event nextValue() {
        if (this.isSlowMode) {
            return this.slowNextValue();
        }
        this.event = IonCursor.Event.NEEDS_DATA;
        while (this.uncheckedNextToken()) {
        }
        return this.event;
    }

    private IonCursor.Event slowNextValue() {
        if (this.refillableState.fillDepth > this.containerIndex) {
            this.refillableState.fillDepth = -1;
            this.peekIndex = this.valueMarker.endIndex;
            this.setCheckpointBeforeUnannotatedTypeId();
            this.slowNextToken();
            return this.event;
        }
        return this.slowOverflowableNextToken();
    }

    @Override
    public IonCursor.Event fillValue() {
        this.event = IonCursor.Event.VALUE_READY;
        if (this.isSlowMode && this.refillableState.fillDepth <= this.containerIndex) {
            this.slowFillValue();
            if (this.refillableState.isSkippingCurrentValue) {
                this.seekPastOversizedValue();
            }
        }
        return this.event;
    }

    private IonCursor.Event slowFillValue() {
        if (this.refillableState.state != State.READY && !this.slowMakeBufferReady()) {
            return this.event;
        }
        if (this.checkpointLocation != CheckpointLocation.AFTER_SCALAR_HEADER && this.checkpointLocation != CheckpointLocation.AFTER_CONTAINER_HEADER) {
            throw new IllegalStateException();
        }
        this.event = IonCursor.Event.NEEDS_DATA;
        if (this.valueMarker.endIndex == -1L && this.slowFillDelimitedContainer_1_1()) {
            return this.event;
        }
        if (this.limit >= this.valueMarker.endIndex || this.fillAt(this.peekIndex, this.valueMarker.endIndex - this.valueMarker.startIndex)) {
            if (this.refillableState.isSkippingCurrentValue) {
                this.event = IonCursor.Event.NEEDS_INSTRUCTION;
            } else {
                if (this.checkpointLocation == CheckpointLocation.AFTER_CONTAINER_HEADER) {
                    this.refillableState.fillDepth = this.containerIndex + 1;
                }
                this.event = IonCursor.Event.VALUE_READY;
            }
        }
        return this.event;
    }

    @Override
    public IonCursor.Event getCurrentEvent() {
        return this.event;
    }

    public int getIonMajorVersion() {
        return this.majorVersion;
    }

    public int getIonMinorVersion() {
        return this.minorVersion;
    }

    public boolean hasAnnotations() {
        return this.hasAnnotations;
    }

    Marker getValueMarker() {
        return this.valueMarker;
    }

    void slice(long offset, long limit2, String ionVersionId) {
        this.peekIndex = offset;
        this.limit = limit2;
        this.setCheckpointBeforeUnannotatedTypeId();
        this.valueMarker.endIndex = -1L;
        this.event = IonCursor.Event.NEEDS_DATA;
        this.valueTid = null;
        this.parent = null;
        this.containerIndex = -1;
        if (!"$ion_1_0".equals(ionVersionId)) {
            throw new IonException(String.format("Attempted to seek using an unsupported Ion version %s.", ionVersionId));
        }
        this.typeIds = IonTypeID.TYPE_IDS_1_0;
        this.majorVersion = 1;
        this.minorVersion = 0;
    }

    long getTotalOffset() {
        return this.valuePreHeaderIndex + (this.refillableState == null ? -this.startOffset : this.refillableState.totalDiscardedBytes);
    }

    boolean isByteBacked() {
        return this.refillableState == null;
    }

    public void registerIvmNotificationConsumer(IvmNotificationConsumer ivmConsumer) {
        this.ivmConsumer = ivmConsumer;
    }

    void registerOversizedValueHandler(BufferConfiguration.OversizedValueHandler oversizedValueHandler) {
        if (this.refillableState != null) {
            this.refillableState.oversizedValueHandler = oversizedValueHandler;
        }
    }

    @Override
    public IonCursor.Event endStream() {
        if (this.isValueIncomplete || this.isAwaitingMoreData()) {
            throw new IonException("Unexpected EOF.");
        }
        return IonCursor.Event.NEEDS_DATA;
    }

    private boolean isAwaitingMoreData() {
        if (this.isSlowMode) {
            return this.slowIsAwaitingMoreData();
        }
        return this.valueMarker.endIndex > this.limit;
    }

    private boolean slowIsAwaitingMoreData() {
        return this.refillableState.state != State.TERMINATED && (this.refillableState.state == State.SEEK || this.refillableState.state == State.SEEK_DELIMITED || this.refillableState.bytesRequested > 1L || this.peekIndex > this.checkpoint);
    }

    void terminate() {
        this.refillableState.state = State.TERMINATED;
    }

    @Override
    public void close() {
        if (this.refillableState != null) {
            try {
                this.refillableState.inputStream.close();
            } catch (IOException e) {
                IonStreamUtils.throwAsIonException(e);
            }
        }
        this.buffer = null;
        this.containerStack = null;
        this.byteBuffer = null;
    }

    static {
        int maxBufferSizeExponent = IonCursorBinary.logBase2(IonBufferConfiguration.DEFAULT.getInitialBufferSize());
        FIXED_SIZE_CONFIGURATIONS = new IonBufferConfiguration[maxBufferSizeExponent + 1];
        for (int i = 0; i <= maxBufferSizeExponent; ++i) {
            int size = Math.max(8, (int)Math.pow(2.0, i));
            IonCursorBinary.FIXED_SIZE_CONFIGURATIONS[i] = ((IonBufferConfiguration.Builder)((IonBufferConfiguration.Builder)IonBufferConfiguration.Builder.from(IonBufferConfiguration.DEFAULT).withInitialBufferSize(size)).withMaximumBufferSize(size)).build();
        }
    }

    private static class RefillableState {
        int fillDepth = -1;
        long capacity;
        long totalDiscardedBytes = 0L;
        State state = State.READY;
        long bytesRequested = 0L;
        final int maximumBufferSize;
        final InputStream inputStream;
        BufferConfiguration.OversizedValueHandler oversizedValueHandler;
        boolean isSkippingCurrentValue = false;
        int individualBytesSkippedWithoutBuffering = 0;

        RefillableState(InputStream inputStream, int capacity, int maximumBufferSize) {
            this.inputStream = inputStream;
            this.capacity = capacity;
            this.maximumBufferSize = maximumBufferSize;
        }
    }

    private static enum State {
        FILL,
        FILL_DELIMITED,
        SEEK,
        SEEK_DELIMITED,
        READY,
        TERMINATED;

    }

    private static enum CheckpointLocation {
        BEFORE_UNANNOTATED_TYPE_ID,
        BEFORE_ANNOTATED_TYPE_ID,
        AFTER_SCALAR_HEADER,
        AFTER_CONTAINER_HEADER;

    }
}

