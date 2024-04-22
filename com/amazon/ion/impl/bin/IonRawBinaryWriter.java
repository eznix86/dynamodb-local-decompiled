/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl._Private_RecyclingStack;
import com.amazon.ion.impl.bin.AbstractIonWriter;
import com.amazon.ion.impl.bin.BlockAllocator;
import com.amazon.ion.impl.bin.BlockAllocatorProvider;
import com.amazon.ion.impl.bin.IntList;
import com.amazon.ion.impl.bin.Symbols;
import com.amazon.ion.impl.bin.WriteBuffer;
import com.amazon.ion.impl.bin._Private_IonRawWriter;
import com.amazon.ion.impl.bin.utf8.Utf8StringEncoder;
import com.amazon.ion.impl.bin.utf8.Utf8StringEncoderPool;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class IonRawBinaryWriter
extends AbstractIonWriter
implements _Private_IonRawWriter {
    private static final byte[] IVM = IonRawBinaryWriter.bytes(224, 1, 0, 234);
    private static final byte[] NULLS;
    private static final byte NULL_NULL;
    private static final byte BOOL_FALSE = 16;
    private static final byte BOOL_TRUE = 17;
    private static final byte INT_ZERO = 32;
    private static final byte POS_INT_TYPE = 32;
    private static final byte NEG_INT_TYPE = 48;
    private static final byte FLOAT_TYPE = 64;
    private static final byte DECIMAL_TYPE = 80;
    private static final byte TIMESTAMP_TYPE = 96;
    private static final byte SYMBOL_TYPE = 112;
    private static final byte STRING_TYPE = -128;
    private static final byte CLOB_TYPE = -112;
    private static final byte BLOB_TYPE = -96;
    private static final byte DECIMAL_POS_ZERO = 80;
    private static final byte DECIMAL_NEGATIVE_ZERO_MANTISSA = -128;
    private static final BigInteger BIG_INT_LONG_MAX_VALUE;
    private static final BigInteger BIG_INT_LONG_MIN_VALUE;
    private static final byte VARINT_NEG_ZERO = -64;
    final Utf8StringEncoder utf8StringEncoder = (Utf8StringEncoder)Utf8StringEncoderPool.getInstance().getOrCreate();
    private static final byte STRING_TYPE_EXTENDED_LENGTH = -114;
    private static final byte[] STRING_TYPED_PREALLOCATED_2;
    private static final byte[] STRING_TYPED_PREALLOCATED_3;
    private static final int MAX_ANNOTATION_LENGTH = 127;
    private static final int SID_UNASSIGNED = -1;
    private final BlockAllocator allocator;
    private final OutputStream out;
    private final StreamCloseMode streamCloseMode;
    private final StreamFlushMode streamFlushMode;
    private final PreallocationMode preallocationMode;
    private final boolean isFloatBinary32Enabled;
    private final WriteBuffer buffer;
    private final PatchList patchPoints;
    private final _Private_RecyclingStack<ContainerInfo> containers;
    private int depth;
    private boolean hasWrittenValuesSinceFinished;
    private boolean hasWrittenValuesSinceConstructed;
    private int currentFieldSid;
    private final IntList currentAnnotationSids;
    private boolean hasTopLevelSymbolTableAnnotation;
    private boolean closed;

    private static byte[] bytes(int ... vals) {
        byte[] octets = new byte[vals.length];
        for (int i = 0; i < vals.length; ++i) {
            octets[i] = (byte)vals[i];
        }
        return octets;
    }

    private static final byte[] makeTypedPreallocatedBytes(int typeDesc, int length) {
        byte[] bytes = new byte[length];
        bytes[0] = (byte)typeDesc;
        if (length > 1) {
            bytes[length - 1] = -128;
        }
        return bytes;
    }

    private static byte[][] makeContainerTypedPreallocatedTable(int length) {
        IonType[] types = IonType.values();
        byte[][] extendedSizes = new byte[types.length][];
        extendedSizes[IonType.LIST.ordinal()] = IonRawBinaryWriter.makeTypedPreallocatedBytes(190, length);
        extendedSizes[IonType.SEXP.ordinal()] = IonRawBinaryWriter.makeTypedPreallocatedBytes(206, length);
        extendedSizes[IonType.STRUCT.ordinal()] = IonRawBinaryWriter.makeTypedPreallocatedBytes(222, length);
        return extendedSizes;
    }

    IonRawBinaryWriter(BlockAllocatorProvider provider, int blockSize, OutputStream out, AbstractIonWriter.WriteValueOptimization optimization, StreamCloseMode streamCloseMode, StreamFlushMode streamFlushMode, PreallocationMode preallocationMode, boolean isFloatBinary32Enabled) throws IOException {
        super(optimization);
        if (out == null) {
            throw new NullPointerException();
        }
        this.allocator = provider.vendAllocator(blockSize);
        this.out = out;
        this.streamCloseMode = streamCloseMode;
        this.streamFlushMode = streamFlushMode;
        this.preallocationMode = preallocationMode;
        this.isFloatBinary32Enabled = isFloatBinary32Enabled;
        this.buffer = new WriteBuffer(this.allocator);
        this.patchPoints = new PatchList();
        this.containers = new _Private_RecyclingStack<ContainerInfo>(10, new _Private_RecyclingStack.ElementFactory<ContainerInfo>(){

            @Override
            public ContainerInfo newElement() {
                return new ContainerInfo();
            }
        });
        this.depth = 0;
        this.hasWrittenValuesSinceFinished = false;
        this.hasWrittenValuesSinceConstructed = false;
        this.currentFieldSid = -1;
        this.currentAnnotationSids = new IntList();
        this.hasTopLevelSymbolTableAnnotation = false;
        this.closed = false;
    }

    @Override
    public SymbolTable getSymbolTable() {
        return Symbols.systemSymbolTable();
    }

    @Override
    public void setFieldName(String name) {
        throw new UnsupportedOperationException("Cannot set field name on a low-level binary writer via string");
    }

    @Override
    public void setFieldNameSymbol(SymbolToken name) {
        this.setFieldNameSymbol(name.getSid());
    }

    @Override
    public void setFieldNameSymbol(int sid) {
        if (!this.isInStruct()) {
            throw new IonException("Cannot set field name outside of struct context");
        }
        this.currentFieldSid = sid;
    }

    @Override
    public void setTypeAnnotations(String ... annotations) {
        throw new UnsupportedOperationException("Cannot set annotations on a low-level binary writer via string");
    }

    private void clearAnnotations() {
        this.currentAnnotationSids.clear();
        this.hasTopLevelSymbolTableAnnotation = false;
    }

    @Override
    public void setTypeAnnotationSymbols(SymbolToken ... annotations) {
        this.clearAnnotations();
        if (annotations != null) {
            for (SymbolToken annotation : annotations) {
                if (annotation == null) break;
                this.addTypeAnnotationSymbol(annotation.getSid());
            }
        }
    }

    @Override
    public void setTypeAnnotationSymbols(int ... sids) {
        this.clearAnnotations();
        if (sids != null) {
            for (int sid : sids) {
                this.addTypeAnnotationSymbol(sid);
            }
        }
    }

    @Override
    public void addTypeAnnotation(String annotation) {
        throw new UnsupportedOperationException("Cannot add annotations on a low-level binary writer via string");
    }

    void addTypeAnnotationSymbol(SymbolToken annotation) {
        this.addTypeAnnotationSymbol(annotation.getSid());
    }

    @Override
    public void addTypeAnnotationSymbol(int sid) {
        if (this.depth == 0 && sid == 3) {
            this.hasTopLevelSymbolTableAnnotation = true;
        }
        this.currentAnnotationSids.add(sid);
    }

    boolean hasAnnotations() {
        return !this.currentAnnotationSids.isEmpty();
    }

    boolean hasWrittenValuesSinceFinished() {
        return this.hasWrittenValuesSinceFinished;
    }

    boolean hasWrittenValuesSinceConstructed() {
        return this.hasWrittenValuesSinceConstructed;
    }

    boolean hasTopLevelSymbolTableAnnotation() {
        return this.hasTopLevelSymbolTableAnnotation;
    }

    int getFieldId() {
        return this.currentFieldSid;
    }

    @Override
    public IonCatalog getCatalog() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isFieldNameSet() {
        return this.currentFieldSid > -1;
    }

    @Override
    public void writeIonVersionMarker() throws IOException {
        this.buffer.writeBytes(IVM);
    }

    @Override
    public int getDepth() {
        return this.depth;
    }

    private void updateLength(long length) {
        if (this.containers.isEmpty()) {
            return;
        }
        this.containers.peek().length += length;
    }

    private void pushContainer(ContainerType type) {
        this.containers.push().initialize(type, this.buffer.position() + 1L);
    }

    private void addPatchPoint(long position, int oldLength, long value) {
        int patchLength = WriteBuffer.varUIntLength(value);
        PatchPoint patch = new PatchPoint(position, oldLength, value);
        if (this.containers.isEmpty()) {
            this.patchPoints.append(patch);
        } else {
            this.containers.peek().appendPatch(patch);
        }
        this.updateLength(patchLength - oldLength);
    }

    private void extendPatchPoints(PatchList patches) {
        if (this.containers.isEmpty()) {
            this.patchPoints.extend(patches);
        } else {
            this.containers.peek().extendPatches(patches);
        }
    }

    private ContainerInfo popContainer() {
        ContainerInfo currentContainer = this.containers.pop();
        if (currentContainer == null) {
            throw new IllegalStateException("Tried to pop container state without said container");
        }
        long length = currentContainer.length;
        if (currentContainer.type != ContainerType.VALUE) {
            long positionOfFirstLengthByte = currentContainer.position;
            if (length <= 13L) {
                int numberOfBytesToShiftBy = this.preallocationMode.numberOfLengthBytes();
                int lengthOfSliceToShift = (int)length;
                this.buffer.shiftBytesLeft(lengthOfSliceToShift, numberOfBytesToShiftBy);
                long typeDescriptorPosition = positionOfFirstLengthByte - 1L;
                long type = (long)(this.buffer.getUInt8At(typeDescriptorPosition) & 0xF0) | length;
                this.buffer.writeUInt8At(typeDescriptorPosition, type);
                length -= (long)numberOfBytesToShiftBy;
            } else if (currentContainer.length <= (long)this.preallocationMode.contentMaxLength) {
                this.preallocationMode.patchLength(this.buffer, positionOfFirstLengthByte, length);
            } else {
                this.addPatchPoint(positionOfFirstLengthByte, this.preallocationMode.numberOfLengthBytes(), length);
            }
        }
        if (currentContainer.patches != null) {
            this.extendPatchPoints(currentContainer.patches);
        }
        this.updateLength(length);
        return currentContainer;
    }

    private void writeVarUInt(long value) {
        if (value < 0L) {
            throw new IonException("Cannot write negative value as unsigned");
        }
        int len = this.buffer.writeVarUInt(value);
        this.updateLength(len);
    }

    private void writeVarInt(long value) {
        int len = this.buffer.writeVarInt(value);
        this.updateLength(len);
    }

    private static void checkSid(int sid) {
        if (sid < 0) {
            throw new IllegalArgumentException("Invalid symbol with SID: " + sid);
        }
    }

    private void prepareValue() {
        if (this.isInStruct() && this.currentFieldSid <= -1) {
            throw new IllegalStateException("IonWriter.setFieldName() must be called before writing a value into a struct.");
        }
        if (this.currentFieldSid > -1) {
            IonRawBinaryWriter.checkSid(this.currentFieldSid);
            this.writeVarUInt(this.currentFieldSid);
            this.currentFieldSid = -1;
        }
        if (!this.currentAnnotationSids.isEmpty()) {
            this.updateLength(this.preallocationMode.typedLength);
            this.pushContainer(ContainerType.ANNOTATION);
            this.buffer.writeBytes(this.preallocationMode.annotationsTypedPreallocatedBytes);
            long annotationsLengthPosition = this.buffer.position();
            this.buffer.writeVarUInt(0L);
            int annotationsLength = 0;
            for (int m = 0; m < this.currentAnnotationSids.size(); ++m) {
                int symbol = this.currentAnnotationSids.get(m);
                IonRawBinaryWriter.checkSid(symbol);
                int symbolLength = this.buffer.writeVarUInt(symbol);
                annotationsLength += symbolLength;
            }
            if (annotationsLength > 127) {
                throw new IonException("Annotations too large: " + this.currentAnnotationSids);
            }
            this.updateLength(1 + annotationsLength);
            this.buffer.writeVarUIntDirect1At(annotationsLengthPosition, annotationsLength);
            this.currentAnnotationSids.clear();
            this.hasTopLevelSymbolTableAnnotation = false;
        }
    }

    private void finishValue() {
        if (!this.containers.isEmpty() && this.containers.peek().type == ContainerType.ANNOTATION) {
            this.popContainer();
        }
        this.hasWrittenValuesSinceFinished = true;
        this.hasWrittenValuesSinceConstructed = true;
    }

    @Override
    public void stepIn(IonType containerType) throws IOException {
        if (!IonType.isContainer(containerType)) {
            throw new IonException("Cannot step into " + (Object)((Object)containerType));
        }
        this.prepareValue();
        this.updateLength(this.preallocationMode.typedLength);
        this.pushContainer(containerType == IonType.STRUCT ? ContainerType.STRUCT : ContainerType.SEQUENCE);
        ++this.depth;
        this.buffer.writeBytes(this.preallocationMode.containerTypedPreallocatedBytes[containerType.ordinal()]);
    }

    @Override
    public void stepOut() throws IOException {
        if (this.currentFieldSid > -1) {
            throw new IonException("Cannot step out with field name set");
        }
        if (!this.currentAnnotationSids.isEmpty()) {
            throw new IonException("Cannot step out with field name set");
        }
        if (this.containers.isEmpty() || !this.containers.peek().type.allowedInStepOut) {
            throw new IonException("Cannot step out when not in container");
        }
        this.popContainer();
        --this.depth;
        this.finishValue();
    }

    @Override
    public boolean isInStruct() {
        return !this.containers.isEmpty() && this.containers.peek().type == ContainerType.STRUCT;
    }

    @Override
    public void writeNull() throws IOException {
        this.prepareValue();
        this.updateLength(1L);
        this.buffer.writeByte(NULL_NULL);
        this.finishValue();
    }

    @Override
    public void writeNull(IonType type) throws IOException {
        byte data = NULL_NULL;
        if (type != null && (data = NULLS[type.ordinal()]) == 0) {
            throw new IllegalArgumentException("Cannot write a null for: " + (Object)((Object)type));
        }
        this.prepareValue();
        this.updateLength(1L);
        this.buffer.writeByte(data);
        this.finishValue();
    }

    @Override
    public void writeBool(boolean value) throws IOException {
        this.prepareValue();
        this.updateLength(1L);
        if (value) {
            this.buffer.writeByte((byte)17);
        } else {
            this.buffer.writeByte((byte)16);
        }
        this.finishValue();
    }

    private void writeTypedUInt(int type, long value) {
        if (value <= 255L) {
            this.updateLength(2L);
            this.buffer.writeUInt8(type | 1);
            this.buffer.writeUInt8(value);
        } else if (value <= 65535L) {
            this.updateLength(3L);
            this.buffer.writeUInt8(type | 2);
            this.buffer.writeUInt16(value);
        } else if (value <= 0xFFFFFFL) {
            this.updateLength(4L);
            this.buffer.writeUInt8(type | 3);
            this.buffer.writeUInt24(value);
        } else if (value <= 0xFFFFFFFFL) {
            this.updateLength(5L);
            this.buffer.writeUInt8(type | 4);
            this.buffer.writeUInt32(value);
        } else if (value <= 0xFFFFFFFFFFL) {
            this.updateLength(6L);
            this.buffer.writeUInt8(type | 5);
            this.buffer.writeUInt40(value);
        } else if (value <= 0xFFFFFFFFFFFFL) {
            this.updateLength(7L);
            this.buffer.writeUInt8(type | 6);
            this.buffer.writeUInt48(value);
        } else if (value <= 0xFFFFFFFFFFFFFFL) {
            this.updateLength(8L);
            this.buffer.writeUInt8(type | 7);
            this.buffer.writeUInt56(value);
        } else {
            this.updateLength(9L);
            this.buffer.writeUInt8(type | 8);
            this.buffer.writeUInt64(value);
        }
    }

    @Override
    public void writeInt(long value) throws IOException {
        this.prepareValue();
        if (value == 0L) {
            this.updateLength(1L);
            this.buffer.writeByte((byte)32);
        } else {
            int type = 32;
            if (value < 0L) {
                type = 48;
                if (value == Long.MIN_VALUE) {
                    this.updateLength(9L);
                    this.buffer.writeUInt8(56L);
                    this.buffer.writeUInt64(value);
                } else {
                    value = -value;
                    this.writeTypedUInt(type, value);
                }
            } else {
                this.writeTypedUInt(type, value);
            }
        }
        this.finishValue();
    }

    private void writeTypedBytes(int type, byte[] data, int offset, int length) {
        int totalLength = 1 + length;
        if (length < 14) {
            this.buffer.writeUInt8(type | length);
        } else {
            this.buffer.writeUInt8(type | 0xE);
            int sizeLength = this.buffer.writeVarUInt(length);
            totalLength += sizeLength;
        }
        this.updateLength(totalLength);
        this.buffer.writeBytes(data, offset, length);
    }

    @Override
    public void writeInt(BigInteger value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.INT);
            return;
        }
        if (value.compareTo(BIG_INT_LONG_MIN_VALUE) >= 0 && value.compareTo(BIG_INT_LONG_MAX_VALUE) <= 0) {
            this.writeInt(value.longValue());
            return;
        }
        this.prepareValue();
        int type = 32;
        if (value.signum() < 0) {
            type = 48;
            value = value.negate();
        }
        byte[] magnitude = value.toByteArray();
        this.writeTypedBytes(type, magnitude, 0, magnitude.length);
        this.finishValue();
    }

    @Override
    public void writeFloat(double value) throws IOException {
        this.prepareValue();
        if (this.isFloatBinary32Enabled && value == (double)((float)value)) {
            this.updateLength(5L);
            this.buffer.writeUInt8(68L);
            this.buffer.writeUInt32(Float.floatToRawIntBits((float)value));
        } else {
            this.updateLength(9L);
            this.buffer.writeUInt8(72L);
            this.buffer.writeUInt64(Double.doubleToRawLongBits(value));
        }
        this.finishValue();
    }

    private void writeDecimalValue(BigDecimal value) {
        boolean isNegZero = Decimal.isNegativeZero(value);
        int signum = value.signum();
        int exponent = -value.scale();
        this.writeVarInt(exponent);
        BigInteger mantissaBigInt = value.unscaledValue();
        if (mantissaBigInt.compareTo(BIG_INT_LONG_MIN_VALUE) >= 0 && mantissaBigInt.compareTo(BIG_INT_LONG_MAX_VALUE) <= 0) {
            long mantissa = mantissaBigInt.longValue();
            if (signum != 0 || isNegZero) {
                if (isNegZero) {
                    this.updateLength(1L);
                    this.buffer.writeByte((byte)-128);
                } else if (mantissa == Long.MIN_VALUE) {
                    this.updateLength(9L);
                    this.buffer.writeUInt8(128L);
                    this.buffer.writeUInt64(mantissa);
                } else if (mantissa >= -127L && mantissa <= 127L) {
                    this.updateLength(1L);
                    this.buffer.writeInt8(mantissa);
                } else if (mantissa >= -32767L && mantissa <= 32767L) {
                    this.updateLength(2L);
                    this.buffer.writeInt16(mantissa);
                } else if (mantissa >= -8388607L && mantissa <= 0x7FFFFFL) {
                    this.updateLength(3L);
                    this.buffer.writeInt24(mantissa);
                } else if (mantissa >= -2147483647L && mantissa <= Integer.MAX_VALUE) {
                    this.updateLength(4L);
                    this.buffer.writeInt32(mantissa);
                } else if (mantissa >= -549755813887L && mantissa <= 0x7FFFFFFFFFL) {
                    this.updateLength(5L);
                    this.buffer.writeInt40(mantissa);
                } else if (mantissa >= -140737488355327L && mantissa <= 0x7FFFFFFFFFFFL) {
                    this.updateLength(6L);
                    this.buffer.writeInt48(mantissa);
                } else if (mantissa >= -36028797018963967L && mantissa <= 0x7FFFFFFFFFFFFFL) {
                    this.updateLength(7L);
                    this.buffer.writeInt56(mantissa);
                } else {
                    this.updateLength(8L);
                    this.buffer.writeInt64(mantissa);
                }
            }
        } else {
            BigInteger magnitude = signum > 0 ? mantissaBigInt : mantissaBigInt.negate();
            byte[] bits = magnitude.toByteArray();
            if (signum < 0) {
                if ((bits[0] & 0x80) == 0) {
                    bits[0] = (byte)(bits[0] | 0x80);
                } else {
                    this.updateLength(1L);
                    this.buffer.writeUInt8(128L);
                }
            }
            this.updateLength(bits.length);
            this.buffer.writeBytes(bits);
        }
    }

    private void patchSingleByteTypedOptimisticValue(byte type, ContainerInfo info) {
        if (info.length <= 13L) {
            this.buffer.writeUInt8At(info.position - 1L, (long)type | info.length);
        } else {
            this.buffer.writeUInt8At(info.position - 1L, type | 0xE);
            this.addPatchPoint(info.position, 0, info.length);
        }
    }

    @Override
    public void writeDecimal(BigDecimal value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.DECIMAL);
            return;
        }
        this.prepareValue();
        if (value.signum() == 0 && value.scale() == 0 && !Decimal.isNegativeZero(value)) {
            this.updateLength(1L);
            this.buffer.writeUInt8(80L);
        } else {
            this.updateLength(1L);
            this.pushContainer(ContainerType.VALUE);
            this.buffer.writeByte((byte)80);
            this.writeDecimalValue(value);
            ContainerInfo info = this.popContainer();
            this.patchSingleByteTypedOptimisticValue((byte)80, info);
        }
        this.finishValue();
    }

    @Override
    public void writeTimestamp(Timestamp value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.TIMESTAMP);
            return;
        }
        this.prepareValue();
        this.updateLength(1L);
        this.pushContainer(ContainerType.VALUE);
        this.buffer.writeByte((byte)96);
        Integer offset = value.getLocalOffset();
        if (offset == null) {
            this.updateLength(1L);
            this.buffer.writeByte((byte)-64);
        } else {
            this.writeVarInt(offset.intValue());
        }
        int year2 = value.getZYear();
        this.writeVarUInt(year2);
        int precision = value.getPrecision().ordinal();
        if (precision >= Timestamp.Precision.MONTH.ordinal()) {
            int month = value.getZMonth();
            this.writeVarUInt(month);
        }
        if (precision >= Timestamp.Precision.DAY.ordinal()) {
            int day = value.getZDay();
            this.writeVarUInt(day);
        }
        if (precision >= Timestamp.Precision.MINUTE.ordinal()) {
            int hour = value.getZHour();
            this.writeVarUInt(hour);
            int minute = value.getZMinute();
            this.writeVarUInt(minute);
        }
        if (precision >= Timestamp.Precision.SECOND.ordinal()) {
            int second = value.getZSecond();
            this.writeVarUInt(second);
            BigDecimal fraction = value.getZFractionalSecond();
            if (fraction != null) {
                BigInteger mantissaBigInt = fraction.unscaledValue();
                int exponent = -fraction.scale();
                if (!mantissaBigInt.equals(BigInteger.ZERO) || exponent <= -1) {
                    this.writeDecimalValue(fraction);
                }
            }
        }
        ContainerInfo info = this.popContainer();
        this.patchSingleByteTypedOptimisticValue((byte)96, info);
        this.finishValue();
    }

    @Override
    public void writeSymbol(String content) throws IOException {
        throw new UnsupportedOperationException("Symbol writing via string is not supported in low-level binary writer");
    }

    @Override
    public void writeSymbolToken(SymbolToken content) throws IOException {
        if (content == null) {
            this.writeNull(IonType.SYMBOL);
            return;
        }
        this.writeSymbolToken(content.getSid());
    }

    boolean isIVM(int sid) {
        return this.depth == 0 && sid == 2 && !this.hasAnnotations();
    }

    @Override
    public void writeSymbolToken(int sid) throws IOException {
        if (this.isIVM(sid)) {
            throw new IonException("Direct writing of IVM is not supported in low-level binary writer");
        }
        IonRawBinaryWriter.checkSid(sid);
        this.prepareValue();
        this.writeTypedUInt(112, sid);
        this.finishValue();
    }

    @Override
    public void writeString(String value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.STRING);
            return;
        }
        this.prepareValue();
        Utf8StringEncoder.Result encoderResult = this.utf8StringEncoder.encode(value);
        int utf8Length = encoderResult.getEncodedLength();
        byte[] utf8Buffer = encoderResult.getBuffer();
        long previousPosition = this.buffer.position();
        if (utf8Length <= 13) {
            this.buffer.writeUInt8(0xFFFFFF80 | utf8Length);
        } else {
            this.buffer.writeUInt8(-114L);
            this.buffer.writeVarUInt(utf8Length);
        }
        this.buffer.writeBytes(utf8Buffer, 0, utf8Length);
        long bytesWritten = this.buffer.position() - previousPosition;
        this.updateLength(bytesWritten);
        this.finishValue();
    }

    @Override
    public void writeClob(byte[] data) throws IOException {
        if (data == null) {
            this.writeNull(IonType.CLOB);
            return;
        }
        this.writeClob(data, 0, data.length);
    }

    @Override
    public void writeClob(byte[] data, int offset, int length) throws IOException {
        if (data == null) {
            this.writeNull(IonType.CLOB);
            return;
        }
        this.prepareValue();
        this.writeTypedBytes(-112, data, offset, length);
        this.finishValue();
    }

    @Override
    public void writeBlob(byte[] data) throws IOException {
        if (data == null) {
            this.writeNull(IonType.BLOB);
            return;
        }
        this.writeBlob(data, 0, data.length);
    }

    @Override
    public void writeBlob(byte[] data, int offset, int length) throws IOException {
        if (data == null) {
            this.writeNull(IonType.BLOB);
            return;
        }
        this.prepareValue();
        this.writeTypedBytes(-96, data, offset, length);
        this.finishValue();
    }

    @Override
    public void writeString(byte[] data, int offset, int length) throws IOException {
        if (data == null) {
            this.writeNull(IonType.STRING);
            return;
        }
        this.prepareValue();
        this.writeTypedBytes(-128, data, offset, length);
        this.finishValue();
    }

    @Override
    public void writeBytes(byte[] data, int offset, int length) throws IOException {
        this.prepareValue();
        this.updateLength(length);
        this.buffer.writeBytes(data, offset, length);
        this.finishValue();
    }

    long position() {
        return this.buffer.position();
    }

    void truncate(long position) {
        this.buffer.truncate(position);
        this.patchPoints.truncate(position);
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void finish() throws IOException {
        if (this.closed) {
            return;
        }
        if (!this.containers.isEmpty() || this.depth > 0) {
            throw new IllegalStateException("Cannot finish within container: " + this.containers);
        }
        if (this.patchPoints.isEmpty()) {
            this.buffer.writeTo(this.out);
        } else {
            long bufferPosition = 0L;
            for (PatchPoint patch : this.patchPoints) {
                long bufferLength = patch.oldPosition - bufferPosition;
                this.buffer.writeTo(this.out, bufferPosition, bufferLength);
                WriteBuffer.writeVarUIntTo(this.out, patch.length);
                bufferPosition = patch.oldPosition;
                bufferPosition += (long)patch.oldLength;
            }
            this.buffer.writeTo(this.out, bufferPosition, this.buffer.position() - bufferPosition);
        }
        this.patchPoints.clear();
        this.buffer.reset();
        if (this.streamFlushMode == StreamFlushMode.FLUSH) {
            this.out.flush();
        }
        this.hasWrittenValuesSinceFinished = false;
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        try {
            try {
                this.finish();
            } catch (IllegalStateException illegalStateException) {
                // empty catch block
            }
            this.buffer.close();
            this.allocator.close();
            this.utf8StringEncoder.close();
        } finally {
            this.closed = true;
            if (this.streamCloseMode == StreamCloseMode.CLOSE) {
                this.out.close();
            }
        }
    }

    static {
        IonType[] types = IonType.values();
        NULLS = new byte[types.length];
        IonRawBinaryWriter.NULLS[IonType.NULL.ordinal()] = 15;
        IonRawBinaryWriter.NULLS[IonType.BOOL.ordinal()] = 31;
        IonRawBinaryWriter.NULLS[IonType.INT.ordinal()] = 47;
        IonRawBinaryWriter.NULLS[IonType.FLOAT.ordinal()] = 79;
        IonRawBinaryWriter.NULLS[IonType.DECIMAL.ordinal()] = 95;
        IonRawBinaryWriter.NULLS[IonType.TIMESTAMP.ordinal()] = 111;
        IonRawBinaryWriter.NULLS[IonType.SYMBOL.ordinal()] = 127;
        IonRawBinaryWriter.NULLS[IonType.STRING.ordinal()] = -113;
        IonRawBinaryWriter.NULLS[IonType.CLOB.ordinal()] = -97;
        IonRawBinaryWriter.NULLS[IonType.BLOB.ordinal()] = -81;
        IonRawBinaryWriter.NULLS[IonType.LIST.ordinal()] = -65;
        IonRawBinaryWriter.NULLS[IonType.SEXP.ordinal()] = -49;
        IonRawBinaryWriter.NULLS[IonType.STRUCT.ordinal()] = -33;
        NULL_NULL = NULLS[IonType.NULL.ordinal()];
        BIG_INT_LONG_MAX_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
        BIG_INT_LONG_MIN_VALUE = BigInteger.valueOf(Long.MIN_VALUE);
        STRING_TYPED_PREALLOCATED_2 = IonRawBinaryWriter.makeTypedPreallocatedBytes(142, 2);
        STRING_TYPED_PREALLOCATED_3 = IonRawBinaryWriter.makeTypedPreallocatedBytes(142, 3);
    }

    static enum StreamFlushMode {
        NO_FLUSH,
        FLUSH;

    }

    static enum StreamCloseMode {
        NO_CLOSE,
        CLOSE;

    }

    private static class PatchList
    implements Iterable<PatchPoint> {
        private Node head = null;
        private Node tail = null;

        public boolean isEmpty() {
            return this.head == null && this.tail == null;
        }

        public void clear() {
            this.head = null;
            this.tail = null;
        }

        public void append(PatchPoint patch) {
            Node node = new Node(patch);
            if (this.head == null) {
                this.head = node;
                this.tail = node;
            } else {
                this.tail.next = node;
                this.tail = node;
            }
        }

        public void extend(PatchList end) {
            if (end != null) {
                if (this.head == null) {
                    if (end.head != null) {
                        this.head = end.head;
                        this.tail = end.tail;
                    }
                } else {
                    this.tail.next = end.head;
                    this.tail = end.tail;
                }
            }
        }

        public PatchPoint truncate(long oldPosition) {
            Node prev = null;
            Node curr = this.head;
            while (curr != null) {
                PatchPoint patch = curr.value;
                if (patch.oldPosition >= oldPosition) {
                    this.tail = prev;
                    if (this.tail == null) {
                        this.head = null;
                    } else {
                        this.tail.next = null;
                    }
                    return patch;
                }
                prev = curr;
                curr = curr.next;
            }
            return null;
        }

        @Override
        public Iterator<PatchPoint> iterator() {
            return new Iterator<PatchPoint>(){
                Node curr;
                {
                    this.curr = head;
                }

                @Override
                public boolean hasNext() {
                    return this.curr != null;
                }

                @Override
                public PatchPoint next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    PatchPoint value = this.curr.value;
                    this.curr = this.curr.next;
                    return value;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        public String toString() {
            StringBuilder buf = new StringBuilder();
            buf.append("(PATCHES");
            for (PatchPoint patch : this) {
                buf.append(" ");
                buf.append(patch);
            }
            buf.append(")");
            return buf.toString();
        }

        private static class Node {
            public final PatchPoint value;
            public Node next;

            public Node(PatchPoint value) {
                this.value = value;
            }
        }
    }

    private static class PatchPoint {
        public final long oldPosition;
        public final int oldLength;
        public final long length;

        public PatchPoint(long oldPosition, int oldLength, long patchLength) {
            this.oldPosition = oldPosition;
            this.oldLength = oldLength;
            this.length = patchLength;
        }

        public String toString() {
            return "(PP old::(" + this.oldPosition + " " + this.oldLength + ") patch::(" + this.length + ")";
        }
    }

    private static class ContainerInfo {
        public ContainerType type = null;
        public long position = -1L;
        public long length = -1L;
        public PatchList patches = null;

        public void appendPatch(PatchPoint patch) {
            if (this.patches == null) {
                this.patches = new PatchList();
            }
            this.patches.append(patch);
        }

        public void extendPatches(PatchList newPatches) {
            if (this.patches == null) {
                this.patches = newPatches;
            } else {
                this.patches.extend(newPatches);
            }
        }

        public void initialize(ContainerType type, long offset) {
            this.type = type;
            this.position = offset;
            this.patches = null;
            this.length = 0L;
        }

        public String toString() {
            return "(CI " + (Object)((Object)this.type) + " pos:" + this.position + " len:" + this.length + ")";
        }
    }

    private static enum ContainerType {
        SEQUENCE(true),
        STRUCT(true),
        VALUE(false),
        ANNOTATION(false);

        public final boolean allowedInStepOut;

        private ContainerType(boolean allowedInStepOut) {
            this.allowedInStepOut = allowedInStepOut;
        }
    }

    static enum PreallocationMode {
        PREALLOCATE_0(0, 1){

            @Override
            void patchLength(WriteBuffer buffer, long position, long lengthValue) {
                throw new IllegalStateException("Cannot patch in PREALLOCATE 0 mode");
            }
        }
        ,
        PREALLOCATE_1(127, 2){

            @Override
            void patchLength(WriteBuffer buffer, long position, long lengthValue) {
                buffer.writeVarUIntDirect1At(position, lengthValue);
            }
        }
        ,
        PREALLOCATE_2(16383, 3){

            @Override
            void patchLength(WriteBuffer buffer, long position, long lengthValue) {
                buffer.writeVarUIntDirect2At(position, lengthValue);
            }
        };

        private final int contentMaxLength;
        private final int typedLength;
        private final byte[][] containerTypedPreallocatedBytes;
        private final byte[] annotationsTypedPreallocatedBytes;

        private PreallocationMode(int contentMaxLength, int typedLength) {
            this.contentMaxLength = contentMaxLength;
            this.typedLength = typedLength;
            this.containerTypedPreallocatedBytes = IonRawBinaryWriter.makeContainerTypedPreallocatedTable(typedLength);
            this.annotationsTypedPreallocatedBytes = IonRawBinaryWriter.makeTypedPreallocatedBytes(238, typedLength);
        }

        abstract void patchLength(WriteBuffer var1, long var2, long var4);

        int numberOfLengthBytes() {
            return this.typedLength - 1;
        }

        static PreallocationMode withPadSize(int pad) {
            switch (pad) {
                case 0: {
                    return PREALLOCATE_0;
                }
                case 1: {
                    return PREALLOCATE_1;
                }
                case 2: {
                    return PREALLOCATE_2;
                }
            }
            throw new IllegalArgumentException("No such preallocation mode for: " + pad);
        }
    }
}

