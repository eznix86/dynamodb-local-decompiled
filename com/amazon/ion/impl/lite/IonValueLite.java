/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import com.amazon.ion.NullValueException;
import com.amazon.ion.ReadOnlyValueException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl.SymbolTokenImpl;
import com.amazon.ion.impl._Private_IonTextWriterBuilder;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl._Private_IonWriter;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContainerLite;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonDatagramLite;
import com.amazon.ion.impl.lite.IonStructLite;
import com.amazon.ion.impl.lite.IonSystemLite;
import com.amazon.ion.impl.lite.TopLevelContext;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.amazon.ion.util.Equivalence;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

abstract class IonValueLite
implements _Private_IonValue {
    private static final int TYPE_ANNOTATION_HASH_SIGNATURE = "TYPE ANNOTATION".hashCode();
    protected static final int IS_LOCKED = 1;
    protected static final int IS_SYSTEM_VALUE = 2;
    protected static final int IS_NULL_VALUE = 4;
    protected static final int IS_BOOL_TRUE = 8;
    protected static final int IS_IVM = 16;
    protected static final int IS_AUTO_CREATED = 32;
    protected static final int IS_SYMBOL_PRESENT = 64;
    protected static final int IS_SYMBOL_ID_PRESENT = 128;
    private static final int ELEMENT_MASK = 255;
    protected static final int ELEMENT_SHIFT = 8;
    static final int CONTAINER_STACK_INITIAL_CAPACITY = 16;
    private static final IonTextWriterBuilder DEFAULT_TO_STRING_WRITER_BUILDER = ((_Private_IonTextWriterBuilder)IonTextWriterBuilder.standard()).withInvalidSidsAllowed(true).withCharsetAscii().immutable();
    private int _flags;
    private int _fieldId = -1;
    protected IonContext _context;
    protected String _fieldName;
    protected SymbolToken[] _annotations;
    private static final int sidHashSalt = 127;
    private static final int textHashSalt = 31;
    private static final int nameHashSalt = 16777619;
    private static final int valueHashSalt = 8191;

    protected final int _getMetadata(int mask, int shift) {
        return (this._flags & mask) >>> shift;
    }

    protected final void _setMetadata(int metadata, int mask, int shift) {
        assert (mask <= 255);
        this._flags &= ~mask;
        this._flags |= metadata << shift & mask;
    }

    protected final void _elementid(int elementid) {
        this._flags &= 0xFF;
        this._flags |= elementid << 8;
        assert (this._elementid() == elementid);
    }

    protected final int _elementid() {
        int elementid = this._flags >>> 8;
        return elementid;
    }

    private final boolean is_true(int flag_bit) {
        return (this._flags & flag_bit) != 0;
    }

    private final void set_flag(int flag_bit) {
        assert (flag_bit != 0);
        this._flags |= flag_bit;
    }

    private final void clear_flag(int flag_bit) {
        assert (flag_bit != 0);
        this._flags &= ~flag_bit;
    }

    protected final boolean _isLocked() {
        return this.is_true(1);
    }

    protected final boolean _isLocked(boolean flag) {
        if (flag) {
            this.set_flag(1);
        } else {
            this.clear_flag(1);
        }
        return flag;
    }

    protected final boolean _isSystemValue() {
        return this.is_true(2);
    }

    protected final boolean _isSystemValue(boolean flag) {
        if (flag) {
            this.set_flag(2);
        } else {
            this.clear_flag(2);
        }
        return flag;
    }

    protected final boolean _isNullValue() {
        return this.is_true(4);
    }

    protected final boolean _isNullValue(boolean flag) {
        if (flag) {
            this.set_flag(4);
        } else {
            this.clear_flag(4);
        }
        return flag;
    }

    protected final boolean _isBoolTrue() {
        return this.is_true(8);
    }

    protected final boolean _isBoolTrue(boolean flag) {
        if (flag) {
            this.set_flag(8);
        } else {
            this.clear_flag(8);
        }
        return flag;
    }

    protected final boolean _isIVM() {
        return this.is_true(16);
    }

    protected final boolean _isIVM(boolean flag) {
        if (flag) {
            this.set_flag(16);
        } else {
            this.clear_flag(16);
        }
        return flag;
    }

    protected final boolean _isAutoCreated() {
        return this.is_true(32);
    }

    protected final boolean _isAutoCreated(boolean flag) {
        if (flag) {
            this.set_flag(32);
        } else {
            this.clear_flag(32);
        }
        return flag;
    }

    protected final boolean _isSymbolPresent() {
        return this.is_true(64);
    }

    protected final boolean _isSymbolPresent(boolean flag) {
        if (flag) {
            this.set_flag(64);
        } else {
            this.clear_flag(64);
        }
        return flag;
    }

    protected final boolean _isSymbolIdPresent() {
        return this.is_true(128);
    }

    protected final boolean _isSymbolIdPresent(boolean flag) {
        if (flag) {
            this.set_flag(128);
        } else {
            this.clear_flag(128);
        }
        return flag;
    }

    IonValueLite(ContainerlessContext context, boolean isNull) {
        assert (context != null);
        this._context = context;
        if (isNull) {
            this.set_flag(4);
        }
    }

    IonValueLite(IonValueLite existing, IonContext context) {
        this._flags = existing._flags & 0xFFFFFFFE & 0xFFFFFF7F;
        if (null == existing._annotations) {
            this._annotations = null;
        } else {
            SymbolToken existingToken;
            int size = existing._annotations.length;
            this._annotations = new SymbolToken[size];
            for (int i = 0; i < size && (existingToken = existing._annotations[i]) != null; ++i) {
                String text = existingToken.getText();
                int sid = existingToken.getSid();
                if (text == null || sid == -1) {
                    this._annotations[i] = existingToken;
                    if (sid <= -1) continue;
                    this._flags |= 0x80;
                    continue;
                }
                this._annotations[i] = _Private_Utils.newSymbolToken(text, -1);
            }
        }
        this._context = context;
    }

    @Override
    public abstract void accept(ValueVisitor var1) throws Exception;

    @Override
    public void addTypeAnnotation(String annotation) {
        int old_len;
        this.checkForLock();
        if (annotation == null || annotation.length() < 1) {
            throw new IllegalArgumentException("a user type annotation must be a non-empty string");
        }
        if (this.hasTypeAnnotation(annotation)) {
            return;
        }
        SymbolTokenImpl sym = _Private_Utils.newSymbolToken(annotation, -1);
        int n = old_len = this._annotations == null ? 0 : this._annotations.length;
        if (old_len > 0) {
            for (int ii = 0; ii < old_len; ++ii) {
                if (this._annotations[ii] != null) continue;
                this._annotations[ii] = sym;
                return;
            }
        }
        int new_len = old_len == 0 ? 1 : old_len * 2;
        SymbolToken[] temp = new SymbolToken[new_len];
        if (old_len > 0) {
            System.arraycopy(this._annotations, 0, temp, 0, old_len);
        }
        this._annotations = temp;
        this._annotations[old_len] = sym;
    }

    @Override
    public final void clearTypeAnnotations() {
        int old_len;
        this.checkForLock();
        int n = old_len = this._annotations == null ? 0 : this._annotations.length;
        if (old_len > 0) {
            for (int ii = 0; ii < old_len && this._annotations[ii] != null; ++ii) {
                this._annotations[ii] = null;
            }
        }
    }

    final void copyFieldName(IonValueLite original) {
        if (original._fieldName == null) {
            this.setFieldNameSymbol(original.getKnownFieldNameSymbol());
        } else {
            this._fieldName = original._fieldName;
        }
    }

    @Override
    public abstract IonValue clone();

    abstract IonValueLite shallowClone(IonContext var1);

    abstract int hashSignature();

    @Override
    public int hashCode() {
        if ((this._flags & 4) != 0) {
            return this.hashTypeAnnotations(this.hashSignature());
        }
        if (this instanceof IonContainerLite) {
            return this.containerHashCode();
        }
        return this.scalarHashCode();
    }

    private int containerHashCode() {
        HashHolder[] hashStack = new HashHolder[16];
        int hashStackIndex = 0;
        hashStack[hashStackIndex] = new HashHolder();
        IonValueLite value = this;
        while (true) {
            HashHolder hashHolder = hashStack[hashStackIndex];
            if ((value._flags & 4) != 0) {
                hashHolder.update(value.hashTypeAnnotations(value.hashSignature()), value);
            } else if (!(value instanceof IonContainerLite)) {
                hashHolder.update(value.scalarHashCode(), value);
            } else {
                if (++hashStackIndex >= hashStack.length) {
                    hashStack = Arrays.copyOf(hashStack, hashStack.length * 2);
                }
                if ((hashHolder = hashStack[hashStackIndex]) == null) {
                    hashStack[hashStackIndex] = hashHolder = new HashHolder();
                }
                IonContainerLite ionContainerLite = hashHolder.parent = (IonContainerLite)value;
                ionContainerLite.getClass();
                hashHolder.iterator = ionContainerLite.new IonContainerLite.SequenceContentIterator(0, true);
                hashHolder.valueHash = value.hashSignature();
            }
            do {
                if (hashHolder.parent == null) {
                    return hashHolder.valueHash;
                }
                value = hashHolder.iterator.nextOrNull();
                if (value != null) continue;
                hashHolder = hashStack[hashStackIndex--];
                IonContainerLite container = hashHolder.parent;
                int containerHash = container.hashTypeAnnotations(hashHolder.valueHash);
                hashHolder.parent = null;
                hashHolder.iterator = null;
                hashHolder = hashStack[hashStackIndex];
                hashHolder.update(containerHash, container);
            } while (value == null);
        }
    }

    abstract int scalarHashCode();

    @Override
    public IonContainerLite getContainer() {
        return this._context.getContextContainer();
    }

    @Override
    public IonValueLite topLevelValue() {
        IonContainerLite c;
        assert (!(this instanceof IonDatagram));
        IonValueLite value = this;
        while ((c = value._context.getContextContainer()) != null && !(c instanceof IonDatagram)) {
            value = c;
        }
        return value;
    }

    @Override
    public final int getElementId() {
        return this._elementid();
    }

    @Override
    public final int getFieldId() {
        if (this._fieldId != -1 || this._fieldName == null) {
            return this._fieldId;
        }
        SymbolToken tok = this.getSymbolTable().find(this._fieldName);
        return tok != null ? tok.getSid() : -1;
    }

    @Override
    public SymbolToken getFieldNameSymbol() {
        return this.getFieldNameSymbol(new LazySymbolTableProvider(this));
    }

    @Override
    public final SymbolToken getFieldNameSymbol(_Private_IonValue.SymbolTableProvider symbolTableProvider) {
        int sid = this._fieldId;
        String text = this._fieldName;
        if (text != null) {
            SymbolToken tok;
            if (sid == -1 && (tok = symbolTableProvider.getSymbolTable().find(text)) != null) {
                return tok;
            }
        } else if (sid > 0) {
            text = symbolTableProvider.getSymbolTable().findKnownSymbol(sid);
        } else if (sid != 0) {
            return null;
        }
        return _Private_Utils.newSymbolToken(text, sid);
    }

    public final SymbolToken getKnownFieldNameSymbol() {
        SymbolToken token = this.getFieldNameSymbol();
        if (token.getText() == null && token.getSid() != 0) {
            throw new UnknownSymbolException(this._fieldId);
        }
        return token;
    }

    private boolean clearSymbolIDsIterative(boolean readOnlyMode) {
        ClearSymbolIDsHolder[] stack = new ClearSymbolIDsHolder[16];
        int stackIndex = 0;
        stack[stackIndex] = new ClearSymbolIDsHolder();
        IonValueLite value = this;
        while (true) {
            ClearSymbolIDsHolder holder = stack[stackIndex];
            if (!(value instanceof IonContainerLite)) {
                boolean bl = holder.allSIDsClear = value.scalarClearSymbolIDValues() && holder.allSIDsClear;
                if (readOnlyMode) {
                    value._isLocked(true);
                }
            } else if (value._isSymbolIdPresent() || readOnlyMode) {
                if (++stackIndex >= stack.length) {
                    stack = Arrays.copyOf(stack, stack.length * 2);
                }
                if ((holder = stack[stackIndex]) == null) {
                    stack[stackIndex] = holder = new ClearSymbolIDsHolder();
                }
                IonContainerLite ionContainerLite = holder.parent = (IonContainerLite)value;
                ionContainerLite.getClass();
                holder.iterator = ionContainerLite.new IonContainerLite.SequenceContentIterator(0, true);
                holder.allSIDsClear = value.attemptClearSymbolIDValues();
            }
            do {
                if (holder.parent == null) {
                    return holder.allSIDsClear;
                }
                value = holder.iterator.nextOrNull();
                if (value != null) continue;
                boolean allChildSidsClear = holder.allSIDsClear;
                if (allChildSidsClear) {
                    holder.parent._isSymbolIdPresent(false);
                }
                if (readOnlyMode) {
                    holder.parent.forceMaterializationOfLazyState();
                    holder.parent._isLocked(true);
                }
                holder.parent = null;
                holder.iterator = null;
                holder = stack[--stackIndex];
                holder.allSIDsClear &= allChildSidsClear;
            } while (value == null);
        }
    }

    private boolean scalarClearSymbolIDValues() {
        if (!this._isSymbolIdPresent()) {
            return true;
        }
        boolean allSIDsRemoved = this.attemptClearSymbolIDValues();
        if (allSIDsRemoved) {
            this._isSymbolIdPresent(false);
        }
        return allSIDsRemoved;
    }

    final boolean clearSymbolIDValues() {
        if (!this._isSymbolIdPresent()) {
            return true;
        }
        if (this instanceof IonContainerLite) {
            return this.clearSymbolIDsIterative(false);
        }
        return this.scalarClearSymbolIDValues();
    }

    boolean attemptClearSymbolIDValues() {
        boolean sidsRemain = false;
        if (this._fieldName != null) {
            this._fieldId = -1;
        } else if (this._fieldId > -1) {
            sidsRemain = true;
        }
        if (this._annotations != null) {
            SymbolToken annotation;
            for (int i = 0; i < this._annotations.length && (annotation = this._annotations[i]) != null; ++i) {
                String text = annotation.getText();
                if (text == null || annotation.getSid() == -1) continue;
                this._annotations[i] = _Private_Utils.newSymbolToken(text, -1);
            }
        }
        return !sidsRemain;
    }

    protected void cascadeSIDPresentToContextRoot() {
        for (IonValueLite node = this; null != node && !node._isSymbolIdPresent(); node = node.getContainer()) {
            node._isSymbolIdPresent(true);
        }
    }

    final void setFieldName(String name) {
        assert (this.getContainer() instanceof IonStructLite);
        assert (this._fieldId == -1 && this._fieldName == null);
        this._fieldName = name;
    }

    final void setFieldNameSymbol(SymbolToken name) {
        assert (this._fieldId == -1 && this._fieldName == null);
        this._fieldName = name.getText();
        this._fieldId = name.getSid();
        if (-1 != this._fieldId && !this._isSymbolIdPresent()) {
            this.cascadeSIDPresentToContextRoot();
        }
    }

    @Override
    public final String getFieldName() {
        if (this._fieldName != null) {
            return this._fieldName;
        }
        if (this._fieldId <= 0) {
            return null;
        }
        throw new UnknownSymbolException(this._fieldId);
    }

    public final int getFieldNameId() {
        return this.getFieldId();
    }

    @Override
    public SymbolTable getSymbolTable() {
        assert (!(this instanceof IonDatagram));
        SymbolTable symbols = this.topLevelValue()._context.getContextSymbolTable();
        if (symbols != null) {
            return symbols;
        }
        return this.getSystem().getSystemSymbolTable();
    }

    @Override
    public SymbolTable getAssignedSymbolTable() {
        assert (!(this instanceof IonDatagram));
        SymbolTable symbols = this._context.getContextSymbolTable();
        return symbols;
    }

    @Override
    public IonSystemLite getSystem() {
        return this._context.getSystem();
    }

    @Override
    public IonType getType() {
        throw new UnsupportedOperationException("this type " + this.getClass().getSimpleName() + " should not be instantiated, there is not IonType associated with it");
    }

    @Override
    public SymbolToken[] getTypeAnnotationSymbols() {
        return this.getTypeAnnotationSymbols(new LazySymbolTableProvider(this));
    }

    @Override
    public final SymbolToken[] getTypeAnnotationSymbols(_Private_IonValue.SymbolTableProvider symbolTableProvider) {
        int count = 0;
        if (this._annotations != null) {
            for (int i = 0; i < this._annotations.length; ++i) {
                if (this._annotations[i] == null) continue;
                ++count;
            }
        }
        if (count == 0) {
            return SymbolToken.EMPTY_ARRAY;
        }
        SymbolToken[] users_copy = new SymbolToken[count];
        for (int i = 0; i < count; ++i) {
            SymbolToken interned;
            SymbolToken token = this._annotations[i];
            String text = token.getText();
            if (text != null && token.getSid() == -1 && (interned = symbolTableProvider.getSymbolTable().find(text)) != null) {
                token = interned;
            }
            users_copy[i] = token;
        }
        return users_copy;
    }

    protected void checkAnnotationsForSids() {
        if (!this._isSymbolIdPresent()) {
            for (SymbolToken token : this._annotations) {
                if (null == token || -1 == token.getSid()) continue;
                this.cascadeSIDPresentToContextRoot();
                break;
            }
        }
    }

    @Override
    public void setTypeAnnotationSymbols(SymbolToken ... annotations) {
        this.checkForLock();
        if (annotations == null || annotations.length == 0) {
            this._annotations = SymbolToken.EMPTY_ARRAY;
        } else {
            this._annotations = (SymbolToken[])annotations.clone();
            this.checkAnnotationsForSids();
        }
    }

    @Override
    public final String[] getTypeAnnotations() {
        int count = 0;
        if (this._annotations != null) {
            int ii = 0;
            while (ii < this._annotations.length && this._annotations[ii] != null) {
                count = ++ii;
            }
        }
        if (count == 0) {
            return _Private_Utils.EMPTY_STRING_ARRAY;
        }
        return _Private_Utils.toStrings(this._annotations, count);
    }

    @Override
    public void setTypeAnnotations(String ... annotations) {
        this.checkForLock();
        this._annotations = _Private_Utils.newSymbolTokens(this.getSymbolTable(), annotations);
    }

    @Override
    public final boolean hasTypeAnnotation(String annotation) {
        int pos;
        return annotation != null && annotation.length() > 0 && (pos = this.findTypeAnnotation(annotation)) >= 0;
    }

    @Override
    public final int findTypeAnnotation(String annotation) {
        assert (annotation != null && annotation.length() > 0);
        if (this._annotations != null) {
            SymbolToken a;
            for (int ii = 0; ii < this._annotations.length && (a = this._annotations[ii]) != null; ++ii) {
                if (!annotation.equals(a.getText())) continue;
                return ii;
            }
        }
        return -1;
    }

    protected int hashTypeAnnotations(int original) {
        int count;
        SymbolToken[] tokens = this._annotations == null ? SymbolToken.EMPTY_ARRAY : this._annotations;
        for (count = 0; count < tokens.length && tokens[count] != null; ++count) {
        }
        if (count == 0) {
            return original;
        }
        int result = 8191 * original + count;
        for (int i = 0; i < count; ++i) {
            SymbolToken token = tokens[i];
            String text = token.getText();
            int tokenHashCode = text == null ? token.getSid() * 127 : text.hashCode() * 31;
            tokenHashCode ^= tokenHashCode << 19 ^ tokenHashCode >> 13;
            result = 8191 * result + tokenHashCode;
            result ^= result << 25 ^ result >> 7;
        }
        return result;
    }

    @Override
    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof IonValue) {
            return Equivalence.ionEquals(this, (IonValue)other);
        }
        return false;
    }

    @Override
    public final boolean isNullValue() {
        return this._isNullValue();
    }

    @Override
    public final boolean isReadOnly() {
        return this._isLocked();
    }

    @Override
    public void makeReadOnly() {
        if (!this._isLocked()) {
            this.makeReadOnlyInternal();
        }
    }

    void makeReadOnlyInternal() {
        if (this instanceof IonContainerLite) {
            this.clearSymbolIDsIterative(true);
        } else {
            this.scalarClearSymbolIDValues();
            this._isLocked(true);
        }
    }

    final void checkForLock() throws ReadOnlyValueException {
        if (this._isLocked()) {
            throw new ReadOnlyValueException();
        }
    }

    @Override
    public boolean removeFromContainer() {
        this.checkForLock();
        boolean removed = false;
        IonContainerLite parent = this._context.getContextContainer();
        if (parent != null) {
            removed = parent.remove(this);
        }
        return removed;
    }

    @Override
    public void removeTypeAnnotation(String annotation) {
        this.checkForLock();
        if (annotation != null && annotation.length() > 0) {
            SymbolToken a;
            int ii;
            int pos = this.findTypeAnnotation(annotation);
            if (pos < 0) {
                return;
            }
            for (ii = pos; ii < this._annotations.length - 1 && (a = this._annotations[ii + 1]) != null; ++ii) {
                this._annotations[ii] = a;
            }
            if (ii < this._annotations.length) {
                this._annotations[ii] = null;
            }
        }
    }

    @Override
    public String toString() {
        return this.toString(DEFAULT_TO_STRING_WRITER_BUILDER);
    }

    @Override
    public String toString(IonTextWriterBuilder writerBuilder) {
        StringBuilder buf = new StringBuilder(1024);
        try {
            IonWriter writer = writerBuilder.build(buf);
            this.writeTo(writer);
            writer.finish();
        } catch (IOException e) {
            throw new IonException(e);
        }
        return buf.toString();
    }

    @Override
    public String toPrettyString() {
        return this.toString(IonTextWriterBuilder.pretty());
    }

    @Override
    public void writeTo(IonWriter writer) {
        this.writeTo(writer, new LazySymbolTableProvider(this));
    }

    private static void writeFieldNameAndAnnotations(IonWriter writer, IonValueLite value, _Private_IonValue.SymbolTableProvider symbolTableProvider) {
        if (writer.isInStruct() && !((_Private_IonWriter)writer).isFieldNameSet()) {
            if (value._fieldName != null) {
                writer.setFieldName(value._fieldName);
            } else {
                SymbolToken tok = value.getFieldNameSymbol(symbolTableProvider);
                if (tok == null) {
                    throw new IllegalStateException("Field name not set");
                }
                writer.setFieldNameSymbol(tok);
            }
        }
        writer.setTypeAnnotationSymbols(value._annotations);
    }

    private void writeToIterative(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        IonContainerLite.SequenceContentIterator[] iteratorStack = new IonContainerLite.SequenceContentIterator[16];
        int iteratorStackIndex = -1;
        IonContainerLite.SequenceContentIterator currentIterator = null;
        IonValueLite value = this;
        while (true) {
            IonValueLite.writeFieldNameAndAnnotations(writer, value, symbolTableProvider);
            if ((value._flags & 4) != 0) {
                writer.writeNull(value.getType());
            } else if (!(value instanceof IonContainerLite)) {
                value.writeBodyTo(writer, symbolTableProvider);
            } else {
                if (++iteratorStackIndex >= iteratorStack.length) {
                    iteratorStack = Arrays.copyOf(iteratorStack, iteratorStack.length * 2);
                }
                IonContainerLite ionContainerLite = (IonContainerLite)value;
                ionContainerLite.getClass();
                iteratorStack[iteratorStackIndex] = currentIterator = ionContainerLite.new IonContainerLite.SequenceContentIterator(0, true);
                writer.stepIn(value.getType());
            }
            do {
                if (currentIterator == null) {
                    return;
                }
                value = currentIterator.nextOrNull();
                if (value != null) continue;
                writer.stepOut();
                iteratorStack[iteratorStackIndex] = null;
                IonContainerLite.SequenceContentIterator sequenceContentIterator = currentIterator = iteratorStackIndex == 0 ? null : iteratorStack[--iteratorStackIndex];
            } while (value == null);
        }
    }

    final void writeTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) {
        try {
            if ((this._flags & 4) != 0) {
                IonValueLite.writeFieldNameAndAnnotations(writer, this, symbolTableProvider);
                writer.writeNull(this.getType());
            } else if (this instanceof IonContainerLite) {
                this.writeToIterative(writer, symbolTableProvider);
            } else {
                IonValueLite.writeFieldNameAndAnnotations(writer, this, symbolTableProvider);
                this.writeBodyTo(writer, symbolTableProvider);
            }
        } catch (IOException e) {
            throw new IonException(e);
        }
    }

    abstract void writeBodyTo(IonWriter var1, _Private_IonValue.SymbolTableProvider var2) throws IOException;

    @Override
    public void setSymbolTable(SymbolTable symbols) {
        if (this.getContext() instanceof TopLevelContext) {
            IonDatagramLite datagram = (IonDatagramLite)this.getContainer();
            datagram.setSymbolTableAtIndex(this._elementid(), symbols);
        } else if (this.topLevelValue() == this) {
            this.setContext(ContainerlessContext.wrap(this.getContext().getSystem(), symbols));
        } else {
            throw new UnsupportedOperationException("can't set the symboltable of a child value");
        }
    }

    final void setContext(IonContext context) {
        assert (context != null);
        this.checkForLock();
        this.clearSymbolIDValues();
        this._context = context;
    }

    IonContext getContext() {
        return this._context;
    }

    final void validateThisNotNull() throws NullValueException {
        if (this._isNullValue()) {
            throw new NullValueException();
        }
    }

    final void detachFromContainer() {
        this.checkForLock();
        this.clearSymbolIDValues();
        this._context = ContainerlessContext.wrap(this.getSystem());
        this._fieldName = null;
        this._fieldId = -1;
        this._elementid(0);
    }

    @Override
    public void dump(PrintWriter out) {
        out.println(this);
    }

    @Override
    public String validate() {
        return null;
    }

    private static class ClearSymbolIDsHolder {
        boolean allSIDsClear = true;
        IonContainerLite parent = null;
        IonContainerLite.SequenceContentIterator iterator = null;

        private ClearSymbolIDsHolder() {
        }
    }

    private static class HashHolder {
        int valueHash = 0;
        IonContainerLite parent = null;
        IonContainerLite.SequenceContentIterator iterator = null;

        private HashHolder() {
        }

        private int hashStructField(int partial, IonValueLite value) {
            String text = value._fieldName;
            int nameHashCode = text == null ? value._fieldId * 127 : text.hashCode() * 31;
            nameHashCode ^= nameHashCode << 17 ^ nameHashCode >> 15;
            int fieldHashCode = this.parent.hashSignature();
            fieldHashCode = 8191 * fieldHashCode + partial;
            fieldHashCode = 16777619 * fieldHashCode + nameHashCode;
            fieldHashCode ^= fieldHashCode << 19 ^ fieldHashCode >> 13;
            return fieldHashCode;
        }

        void update(int partial, IonValueLite value) {
            if (this.parent == null) {
                this.valueHash = partial;
            } else if (this.parent instanceof IonStructLite) {
                this.valueHash += this.hashStructField(partial, value);
            } else {
                this.valueHash = 8191 * this.valueHash + partial;
                this.valueHash ^= this.valueHash << 29 ^ this.valueHash >> 3;
            }
        }
    }

    static class LazySymbolTableProvider
    implements _Private_IonValue.SymbolTableProvider {
        SymbolTable symtab = null;
        final IonValueLite value;

        LazySymbolTableProvider(IonValueLite value) {
            this.value = value;
        }

        @Override
        public SymbolTable getSymbolTable() {
            if (this.symtab == null) {
                this.symtab = this.value.getSymbolTable();
            }
            return this.symtab;
        }
    }
}

