/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl.BlockedBuffer;
import com.amazon.ion.impl.IonBinary;
import com.amazon.ion.impl.IonWriterSystem;
import com.amazon.ion.impl._Private_IonConstants;
import com.amazon.ion.impl._Private_ListWriter;
import com.amazon.ion.impl.lite._Private_LiteDomTrampoline;
import com.amazon.ion.system.IonWriterBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;

final class IonWriterSystemBinary
extends IonWriterSystem
implements _Private_ListWriter {
    PatchedValues _patch = new PatchedValues();
    private static final int TID_ANNOTATION_PATCH = 17;
    private static final int TID_SYMBOL_TABLE_PATCH = 18;
    private static final int TID_RAW = 19;
    static final int UNKNOWN_LENGTH = -1;
    IonBinary.BufferManager _manager;
    IonBinary.Writer _writer;
    private final OutputStream _user_output_stream;
    private final boolean _auto_flush;
    boolean _in_struct;
    private boolean _closed;
    private static final int TID_FOR_SYMBOL_TABLE_PATCH = 17;
    private static final int DEFAULT_PATCH_COUNT = 10;
    private static final int DEFAULT_PATCH_DEPTH = 10;
    private static final int NOT_A_SYMBOL_TABLE_IDX = -1;
    int _patch_count = 0;
    int[] _patch_lengths = new int[10];
    int[] _patch_offsets = new int[10];
    int[] _patch_table_idx = new int[10];
    int[] _patch_types = new int[10];
    boolean[] _patch_in_struct = new boolean[10];
    int _patch_symbol_table_count = 0;
    SymbolTable[] _patch_symbol_tables = new SymbolTable[10];
    int _top;
    int[] _patch_stack = new int[10];
    private int _user_depth;

    IonWriterSystemBinary(SymbolTable defaultSystemSymtab, OutputStream out, boolean autoFlush, boolean ensureInitialIvm) {
        super(defaultSystemSymtab, ensureInitialIvm ? IonWriterBuilder.InitialIvmHandling.ENSURE : null, IonWriterBuilder.IvmMinimizing.ADJACENT, true);
        out.getClass();
        this._user_output_stream = out;
        this._manager = new IonBinary.BufferManager();
        this._writer = this._manager.openWriter();
        this._auto_flush = autoFlush;
    }

    private void writeAllBufferedData() throws IOException {
        this.writeBytes(this._user_output_stream);
        this.clearFieldName();
        this.clearAnnotations();
        this._in_struct = false;
        this._patch_count = 0;
        this._patch_symbol_table_count = 0;
        this._top = 0;
        try {
            this._writer.setPosition(0);
            this._writer.truncate();
        } catch (IOException e) {
            throw new IonException(e);
        }
    }

    @Override
    public void finish() throws IOException {
        if (this.getDepth() != 0) {
            throw new IllegalStateException("IonWriter.finish() can only be called at top-level.");
        }
        this.writeAllBufferedData();
        super.finish();
    }

    final OutputStream getOutputStream() {
        return this._user_output_stream;
    }

    @Override
    public final boolean isInStruct() {
        return this._in_struct;
    }

    private final boolean topInStruct() {
        if (this._top == 0) {
            return false;
        }
        boolean in_struct = this._patch_in_struct[this._patch_stack[this._top - 1]];
        return in_struct;
    }

    protected final boolean atDatagramLevel() {
        return this.topType() == 16;
    }

    @Override
    public final int getDepth() {
        return this._user_depth;
    }

    protected final IonType getContainer() {
        IonType type;
        int tid = this.parentType();
        switch (tid) {
            case 11: {
                type = IonType.LIST;
                break;
            }
            case 12: {
                type = IonType.SEXP;
                break;
            }
            case 13: {
                type = IonType.STRUCT;
                break;
            }
            case 16: {
                type = IonType.DATAGRAM;
                break;
            }
            default: {
                throw new IonException("unexpected parent type " + tid + " does not represent a container");
            }
        }
        return type;
    }

    @Override
    final SymbolTable inject_local_symbol_table() throws IOException {
        SymbolTable symbols = super.inject_local_symbol_table();
        PatchedValues top = this._patch;
        while (top.getParent() != null) {
            top = top.getParent();
        }
        super.startValue();
        top.injectSymbolTable(symbols, this._patch.getParent() != null);
        super.endValue();
        return symbols;
    }

    private final int topLength() {
        return this._patch_lengths[this._patch_stack[this._top - 1]];
    }

    private final int topType() {
        if (this._top == 0) {
            return 16;
        }
        return this._patch_types[this._patch_stack[this._top - 1]];
    }

    private final int parentType() {
        for (int ii = this._top - 2; ii >= 0; --ii) {
            int type = this._patch_types[this._patch_stack[ii]];
            if (type == 14) continue;
            return type;
        }
        return 16;
    }

    private final void startValue(int value_type) throws IOException {
        super.startValue();
        int[] sids = null;
        int sid_count = this.annotationCount();
        if (sid_count > 0) {
            sids = super.internAnnotationsAndGetSids();
            this._patch.startPatch(14, this._writer.position());
        } else {
            this._patch.startPatch(value_type, this._writer.position());
        }
        if (this._in_struct) {
            if (!this.isFieldNameSet()) {
                throw new IllegalStateException("IonWriter.setFieldName() must be called before writing a value into a struct.");
            }
            int sid = super.getFieldId();
            if (sid < 0) {
                throw new UnsupportedOperationException("symbol resolution must be handled by the user writer");
            }
            int fieldNameLength = this._writer.writeVarUIntValue(sid, true);
            this._patch.patchFieldName(fieldNameLength);
            this.clearFieldName();
        }
        if (sid_count > 0) {
            this._patch = this._patch.addChild();
            this._patch.startPatch(17, this._writer.position());
            int len = 0;
            for (int ii = 0; ii < sid_count; ++ii) {
                len += this._writer.writeVarUIntValue(sids[ii], true);
            }
            this._patch.patchValue(len);
            this._patch.endPatch();
            this.clearAnnotations();
            this._patch.startPatch(value_type, this._writer.position());
        }
    }

    private final void closeValue() throws IOException {
        super.endValue();
        this._patch.endPatch();
        if (this._patch.getParent() != null && this._patch.getParent().getType() == 14) {
            this._patch = this._patch.getParent();
            this._patch.endPatch();
            assert (this._patch != null);
        }
    }

    @Override
    public final void flush() throws IOException {
        if (!this._closed) {
            SymbolTable symtab;
            if (this.atDatagramLevel() && !this.hasAnnotations() && (symtab = this.getSymbolTable()) != null && symtab.isReadOnly() && symtab.isLocalTable()) {
                this.writeAllBufferedData();
            }
            this._user_output_stream.flush();
        }
    }

    @Override
    public final void close() throws IOException {
        if (!this._closed) {
            try {
                if (this.getDepth() == 0) {
                    this.finish();
                }
            } finally {
                this._closed = true;
                this._user_output_stream.close();
            }
        }
    }

    @Override
    void writeIonVersionMarkerAsIs(SymbolTable systemSymtab) throws IOException {
        if (this._user_depth != 0) {
            throw new IllegalStateException("IVM not on top-level");
        }
        super.startValue();
        this._patch.startPatch(19, this._writer.position());
        this._patch.patchValue(4);
        this._writer.write(_Private_IonConstants.BINARY_VERSION_MARKER_1_0);
        this._patch.endPatch();
        super.endValue();
    }

    @Override
    void writeLocalSymtab(SymbolTable symbols) throws IOException {
        PatchedValues top = this._patch;
        while (top.getParent() != null) {
            top = top.getParent();
        }
        super.startValue();
        top.injectSymbolTable(symbols, this._patch.getParent() != null);
        super.endValue();
        super.writeLocalSymtab(symbols);
    }

    @Override
    public final void stepIn(IonType containerType) throws IOException {
        int tid;
        switch (containerType) {
            case LIST: {
                tid = 11;
                break;
            }
            case SEXP: {
                tid = 12;
                break;
            }
            case STRUCT: {
                tid = 13;
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        this.startValue(tid);
        this._patch = this._patch.addChild();
        this._in_struct = tid == 13;
        ++this._user_depth;
    }

    @Override
    public final void stepOut() throws IOException {
        if (this._patch.getParent() == null) {
            throw new IllegalStateException("Cannot stepOut any further, already at top level.");
        }
        this._patch = this._patch.getParent();
        this.closeValue();
        if (this._patch.getParent() == null) {
            this._in_struct = false;
            if (this._auto_flush) {
                this.flush();
            }
        } else {
            this._in_struct = this._patch.getParent().getType() == 13;
        }
        --this._user_depth;
    }

    @Override
    public void writeNull(IonType type) throws IOException {
        int tid;
        switch (type) {
            case NULL: {
                tid = 0;
                break;
            }
            case BOOL: {
                tid = 1;
                break;
            }
            case INT: {
                tid = 2;
                break;
            }
            case FLOAT: {
                tid = 4;
                break;
            }
            case DECIMAL: {
                tid = 5;
                break;
            }
            case TIMESTAMP: {
                tid = 6;
                break;
            }
            case SYMBOL: {
                tid = 7;
                break;
            }
            case STRING: {
                tid = 8;
                break;
            }
            case BLOB: {
                tid = 10;
                break;
            }
            case CLOB: {
                tid = 9;
                break;
            }
            case SEXP: {
                tid = 12;
                break;
            }
            case LIST: {
                tid = 11;
                break;
            }
            case STRUCT: {
                tid = 13;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid type: " + (Object)((Object)type));
            }
        }
        this.startValue(19);
        this._writer.write(tid << 4 | 0xF);
        this._patch.patchValue(1);
        this.closeValue();
    }

    @Override
    public void writeBool(boolean value) throws IOException {
        int ln = value ? 1 : 0;
        this.startValue(19);
        this._writer.write(0x10 | ln);
        this._patch.patchValue(1);
        this.closeValue();
    }

    @Override
    public void writeInt(long value) throws IOException {
        int len;
        if (value < 0L) {
            this.startValue(3);
            len = this._writer.writeUIntValue(-value);
        } else {
            this.startValue(2);
            len = this._writer.writeUIntValue(value);
        }
        this._patch.patchValue(len);
        this.closeValue();
    }

    @Override
    public void writeInt(BigInteger value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.INT);
            return;
        }
        boolean is_negative = value.signum() < 0;
        BigInteger positive = value;
        if (is_negative) {
            positive = value.negate();
        }
        int len = IonBinary.lenIonInt(positive);
        this.startValue(is_negative ? 3 : 2);
        this._writer.writeUIntValue(positive, len);
        this._patch.patchValue(len);
        this.closeValue();
    }

    @Override
    public void writeFloat(double value) throws IOException {
        int len = IonBinary.lenIonFloat(value);
        this.startValue(4);
        len = this._writer.writeFloatValue(value);
        this._patch.patchValue(len);
        this.closeValue();
    }

    @Override
    public void writeDecimal(BigDecimal value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.DECIMAL);
            return;
        }
        this.startValue(5);
        int len = this._writer.writeDecimalContent(value);
        this._patch.patchValue(len);
        this.closeValue();
    }

    @Override
    public void writeTimestamp(Timestamp value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.TIMESTAMP);
            return;
        }
        this.startValue(6);
        int len = this._writer.writeTimestamp(value);
        this._patch.patchValue(len);
        this.closeValue();
    }

    @Override
    public void writeString(String value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.STRING);
            return;
        }
        this.startValue(8);
        int len = this._writer.writeStringData(value);
        this._patch.patchValue(len);
        this.closeValue();
    }

    @Override
    void writeSymbolAsIs(int symbolId) throws IOException {
        this.startValue(7);
        int len = this._writer.writeUIntValue(symbolId);
        this._patch.patchValue(len);
        this.closeValue();
    }

    @Override
    public void writeSymbolAsIs(String value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.SYMBOL);
            return;
        }
        int sid = this.add_symbol(value);
        this.writeSymbolAsIs(sid);
    }

    @Override
    public void writeClob(byte[] value, int start, int len) throws IOException {
        if (value == null) {
            this.writeNull(IonType.CLOB);
            return;
        }
        if (start < 0 || len < 0 || start + len > value.length) {
            throw new IllegalArgumentException("the start and len must be contained in the byte array");
        }
        this.startValue(9);
        this._writer.write(value, start, len);
        this._patch.patchValue(len);
        this.closeValue();
    }

    @Override
    public void writeBlob(byte[] value, int start, int len) throws IOException {
        if (value == null) {
            this.writeNull(IonType.BLOB);
            return;
        }
        if (start < 0 || len < 0 || start + len > value.length) {
            throw new IllegalArgumentException("the start and len must be contained in the byte array");
        }
        this.startValue(10);
        this._writer.write(value, start, len);
        this._patch.patchValue(len);
        this.closeValue();
    }

    public void writeRaw(byte[] value, int start, int len) throws IOException {
        this.startValue(19);
        this._writer.write(value, start, len);
        this._patch.patchValue(len);
        this.closeValue();
    }

    @Override
    public void writeBoolList(boolean[] values2) throws IOException {
        this.stepIn(IonType.LIST);
        for (boolean b : values2) {
            this.writeBool(b);
        }
        this.stepOut();
    }

    @Override
    public void writeIntList(byte[] values2) throws IOException {
        this.stepIn(IonType.LIST);
        for (byte b : values2) {
            this.writeInt(b);
        }
        this.stepOut();
    }

    @Override
    public void writeIntList(short[] values2) throws IOException {
        this.stepIn(IonType.LIST);
        for (short s : values2) {
            this.writeInt(s);
        }
        this.stepOut();
    }

    @Override
    public void writeIntList(int[] values2) throws IOException {
        this.stepIn(IonType.LIST);
        for (int i : values2) {
            this.writeInt(i);
        }
        this.stepOut();
    }

    @Override
    public void writeIntList(long[] values2) throws IOException {
        this.stepIn(IonType.LIST);
        for (long l : values2) {
            this.writeInt(l);
        }
        this.stepOut();
    }

    @Override
    public void writeFloatList(float[] values2) throws IOException {
        this.stepIn(IonType.LIST);
        for (float f : values2) {
            this.writeFloat(f);
        }
        this.stepOut();
    }

    @Override
    public void writeFloatList(double[] values2) throws IOException {
        this.stepIn(IonType.LIST);
        for (double d : values2) {
            this.writeFloat(d);
        }
        this.stepOut();
    }

    @Override
    public void writeStringList(String[] values2) throws IOException {
        this.stepIn(IonType.LIST);
        for (String s : values2) {
            this.writeString(s);
        }
        this.stepOut();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    int writeBytes(OutputStream userstream) throws IOException {
        if (this._patch.getParent() != null) {
            throw new IllegalStateException("Tried to flush while not on top-level");
        }
        try {
            int size;
            BlockedBuffer.BlockedByteInputStream datastream = new BlockedBuffer.BlockedByteInputStream(this._manager.buffer());
            int n = size = this.writeRecursive(datastream, userstream, this._patch);
            return n;
        } finally {
            this._patch.reset();
        }
    }

    int writeRecursive(BlockedBuffer.BlockedByteInputStream datastream, OutputStream userstream, PatchedValues p) throws IOException {
        int totalSize = 0;
        block8: for (int i = 0; i <= p._freePos; ++i) {
            int type = p._types[i];
            int pos = p._positions[i];
            int fnlen = (int)(p._lengths[i] >> 32);
            int vallen = (int)(p._lengths[i] & 0xFFFFFFFFFFFFFFFFL);
            if (p.getParent() == null) {
                if (pos > totalSize) {
                    datastream.writeTo(userstream, pos - totalSize);
                    totalSize = pos;
                }
                totalSize += fnlen + vallen;
            }
            if (fnlen > 0) {
                datastream.writeTo(userstream, fnlen);
            }
            switch (type) {
                case 17: {
                    IonBinary.writeVarUInt(userstream, vallen);
                    datastream.writeTo(userstream, vallen);
                    continue block8;
                }
                case 18: {
                    SymbolTable symtab = p._symtabs.remove();
                    if (symtab.isSystemTable()) continue block8;
                    byte[] symtabBytes = _Private_LiteDomTrampoline.reverseEncode(1024, symtab);
                    userstream.write(symtabBytes);
                    totalSize += symtabBytes.length;
                    continue block8;
                }
                case 19: {
                    datastream.writeTo(userstream, vallen);
                    continue block8;
                }
                default: {
                    int typeByte;
                    if (vallen >= 14) {
                        typeByte = type << 4 | 0xE;
                        userstream.write(typeByte);
                        IonBinary.writeVarUInt(userstream, vallen);
                    } else {
                        typeByte = type << 4 | vallen;
                        userstream.write(typeByte);
                    }
                    switch (type) {
                        case 11: 
                        case 12: 
                        case 13: 
                        case 14: {
                            assert (p._children != null);
                            this.writeRecursive(datastream, userstream, p._children.remove());
                            continue block8;
                        }
                    }
                    datastream.writeTo(userstream, vallen);
                }
            }
        }
        return totalSize;
    }

    protected int write_symbol_table(OutputStream userstream, SymbolTable symtab) throws IOException {
        CountingStream cs = new CountingStream(userstream);
        IonWriterSystemBinary writer = new IonWriterSystemBinary(this._default_system_symbol_table, cs, false, false);
        symtab.writeTo(writer);
        writer.finish();
        int symtab_len = cs.getBytesWritten();
        return symtab_len;
    }

    protected int XXX_get_pending_length_with_no_symbol_tables() {
        int buffer_length = this._manager.buffer().size();
        int patch_amount = 0;
        for (int patch_idx = 0; patch_idx < this._patch_count; ++patch_idx) {
            int vlen = this._patch_lengths[patch_idx];
            if (vlen < 14) continue;
            int ln = IonBinary.lenVarUInt(vlen);
            patch_amount += ln;
        }
        int symbol_table_length = 0;
        int total_length = 0;
        return total_length += buffer_length + patch_amount + symbol_table_length;
    }

    static class CountingStream
    extends OutputStream {
        private final OutputStream _wrapped;
        private int _written;

        CountingStream(OutputStream userstream) {
            this._wrapped = userstream;
        }

        public int getBytesWritten() {
            return this._written;
        }

        @Override
        public void write(int b) throws IOException {
            this._wrapped.write(b);
            ++this._written;
        }

        @Override
        public void write(byte[] bytes) throws IOException {
            this._wrapped.write(bytes);
            this._written += bytes.length;
        }

        @Override
        public void write(byte[] bytes, int off, int len) throws IOException {
            this._wrapped.write(bytes, off, len);
            this._written += len;
        }
    }

    static class PatchedValues {
        private static final int DEFAULT_PATCH_COUNT = 10;
        int _freePos = -1;
        int[] _types = new int[10];
        int[] _positions = new int[10];
        long[] _lengths = new long[10];
        PatchedValues _parent;
        Queue<PatchedValues> _children;
        Queue<SymbolTable> _symtabs;

        PatchedValues() {
        }

        void reset() {
            this._freePos = -1;
            this._children = null;
            this._symtabs = null;
        }

        PatchedValues addChild() {
            PatchedValues pv = new PatchedValues();
            pv._parent = this;
            if (this._children == null) {
                this._children = new LinkedList<PatchedValues>();
            }
            this._children.add(pv);
            return pv;
        }

        void injectSymbolTable(SymbolTable st, boolean injectBeforeCurrent) {
            if (this._parent != null) {
                throw new IllegalStateException("Cannot inject a symbol table when not on top-level");
            }
            if (this._symtabs == null) {
                this._symtabs = new LinkedList<SymbolTable>();
            }
            ++this._freePos;
            if (this._freePos == this._positions.length) {
                this.grow();
            }
            if (injectBeforeCurrent) {
                this._types[this._freePos] = this._types[this._freePos - 1];
                this._lengths[this._freePos] = this._lengths[this._freePos - 1];
                this._types[this._freePos - 1] = 18;
                this._lengths[this._freePos - 1] = 0L;
            } else {
                this._types[this._freePos] = 18;
                this._lengths[this._freePos] = 0L;
            }
            this._symtabs.add(st);
        }

        int getType() {
            return this._types[this._freePos];
        }

        PatchedValues getParent() {
            return this._parent;
        }

        void startPatch(int type, int pos) {
            ++this._freePos;
            if (this._freePos == this._positions.length) {
                this.grow();
            }
            this._types[this._freePos] = type;
            this._lengths[this._freePos] = 0L;
            this._positions[this._freePos] = pos;
        }

        void patchFieldName(int fieldNameLength) {
            this._lengths[this._freePos] = (long)fieldNameLength << 32;
        }

        void patchValue(int len) {
            long memLen = this._lengths[this._freePos] & 0xFFFFFFFF00000000L;
            long curLen = this._lengths[this._freePos] & 0xFFFFFFFFL;
            this._lengths[this._freePos] = memLen | curLen + (long)len;
        }

        void endPatch() {
            if (this._parent != null) {
                int memberLen = (int)(this._lengths[this._freePos] >> 32);
                int valueLen = (int)(this._lengths[this._freePos] & 0xFFFFFFFFFFFFFFFFL);
                int totalLen = memberLen + valueLen;
                switch (this._types[this._freePos]) {
                    case 18: 
                    case 19: {
                        break;
                    }
                    case 17: {
                        totalLen += IonBinary.lenVarUInt(valueLen);
                        break;
                    }
                    default: {
                        ++totalLen;
                        if (valueLen < 14) break;
                        totalLen += IonBinary.lenVarUInt(valueLen);
                    }
                }
                this._parent.patchValue(totalLen);
            }
        }

        private void grow() {
            int newSize = this._positions.length * 2;
            this._types = PatchedValues.growOne(this._types, newSize);
            this._positions = PatchedValues.growOne(this._positions, newSize);
            this._lengths = PatchedValues.growOne(this._lengths, newSize);
        }

        static int[] growOne(int[] source, int newSize) {
            int[] dest = new int[newSize];
            System.arraycopy(source, 0, dest, 0, source.length);
            return dest;
        }

        static long[] growOne(long[] source, int newSize) {
            long[] dest = new long[newSize];
            System.arraycopy(source, 0, dest, 0, source.length);
            return dest;
        }
    }
}

