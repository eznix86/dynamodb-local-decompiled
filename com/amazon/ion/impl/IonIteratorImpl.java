/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.ValueFactory;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.shaded_.do_not_use.kotlin._Assertions;
import com.amazon.ion.shaded_.do_not_use.kotlin.jvm.functions.Function1;
import com.amazon.ion.shaded_.do_not_use.kotlin.jvm.internal.Intrinsics;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class IonIteratorImpl
implements Iterator<IonValue> {
    private final ValueFactory _valueFactory;
    private final IonReader _reader;
    private boolean _at_eof;
    private IonValue _next;

    public IonIteratorImpl(ValueFactory _valueFactory, IonReader _reader) {
        Intrinsics.checkNotNullParameter(_valueFactory, "_valueFactory");
        Intrinsics.checkNotNullParameter(_reader, "_reader");
        this._valueFactory = _valueFactory;
        this._reader = _reader;
    }

    @Override
    public boolean hasNext() {
        if (this._at_eof) {
            return false;
        }
        return this._next != null ? true : this.prefetch() != null;
    }

    private final IonValue prefetch() {
        boolean bl;
        boolean bl2 = bl = !this._at_eof && this._next == null;
        if (_Assertions.ENABLED && !bl) {
            String string = "Assertion failed";
            throw new AssertionError((Object)string);
        }
        IonType type = this._reader.next();
        if (type == null) {
            this._at_eof = true;
        } else {
            this._next = this.readValue();
        }
        return this._next;
    }

    private final IonValue readValue() {
        IonValue ionValue2;
        SymbolToken[] annotations = this._reader.getTypeAnnotationSymbols();
        if (this._reader.isNullValue()) {
            ionValue2 = this._valueFactory.newNull(this._reader.getType());
        } else {
            IonType ionType = this._reader.getType();
            switch (ionType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[ionType.ordinal()]) {
                case 1: {
                    throw new IllegalStateException();
                }
                case 2: {
                    ionValue2 = this._valueFactory.newBool(this._reader.booleanValue());
                    break;
                }
                case 3: {
                    ionValue2 = this._valueFactory.newInt(this._reader.bigIntegerValue());
                    break;
                }
                case 4: {
                    ionValue2 = this._valueFactory.newFloat(this._reader.doubleValue());
                    break;
                }
                case 5: {
                    ionValue2 = this._valueFactory.newDecimal(this._reader.decimalValue());
                    break;
                }
                case 6: {
                    ionValue2 = this._valueFactory.newTimestamp(this._reader.timestampValue());
                    break;
                }
                case 7: {
                    ionValue2 = this._valueFactory.newString(this._reader.stringValue());
                    break;
                }
                case 8: {
                    ionValue2 = this._valueFactory.newSymbol(this._reader.symbolValue());
                    break;
                }
                case 9: {
                    IonValue ionValue3;
                    IonValue $this$readValue_u24lambda_u240 = ionValue3 = this._valueFactory.newNullBlob();
                    boolean bl = false;
                    $this$readValue_u24lambda_u240.setBytes(this._reader.newBytes());
                    ionValue2 = ionValue3;
                    break;
                }
                case 10: {
                    IonValue ionValue3;
                    IonValue $this$readValue_u24lambda_u241 = ionValue3 = this._valueFactory.newNullClob();
                    boolean bl = false;
                    $this$readValue_u24lambda_u241.setBytes(this._reader.newBytes());
                    ionValue2 = ionValue3;
                    break;
                }
                case 11: {
                    IonValue ionValue3;
                    IonValue $this$readValue_u24lambda_u243 = ionValue3 = this._valueFactory.newEmptyStruct();
                    boolean bl = false;
                    IonIteratorImpl ionIteratorImpl = this;
                    IonReader $this$forEachInContainer$iv = this._reader;
                    boolean $i$f$forEachInContainer = false;
                    $this$forEachInContainer$iv.stepIn();
                    while ($this$forEachInContainer$iv.next() != null) {
                        IonReader $this$readValue_u24lambda_u243_u24lambda_u242 = $this$forEachInContainer$iv;
                        boolean bl2 = false;
                        $this$readValue_u24lambda_u243.add($this$readValue_u24lambda_u243_u24lambda_u242.getFieldNameSymbol(), this.readValue());
                    }
                    $this$forEachInContainer$iv.stepOut();
                    ionValue2 = ionValue3;
                    break;
                }
                case 12: {
                    IonValue ionValue3;
                    IonValue $this$readValue_u24lambda_u245 = ionValue3 = this._valueFactory.newEmptyList();
                    boolean bl = false;
                    IonIteratorImpl this_$iv = this;
                    IonReader $this$forEachInContainer$iv = this._reader;
                    boolean $i$f$forEachInContainer = false;
                    $this$forEachInContainer$iv.stepIn();
                    while ($this$forEachInContainer$iv.next() != null) {
                        IonReader $this$readValue_u24lambda_u245_u24lambda_u244 = $this$forEachInContainer$iv;
                        boolean bl3 = false;
                        $this$readValue_u24lambda_u245.add(this.readValue());
                    }
                    $this$forEachInContainer$iv.stepOut();
                    ionValue2 = ionValue3;
                    break;
                }
                case 13: {
                    IonValue ionValue3;
                    IonValue $this$readValue_u24lambda_u247 = ionValue3 = this._valueFactory.newEmptySexp();
                    boolean bl = false;
                    IonIteratorImpl this_$iv = this;
                    IonReader $this$forEachInContainer$iv = this._reader;
                    boolean $i$f$forEachInContainer = false;
                    $this$forEachInContainer$iv.stepIn();
                    while ($this$forEachInContainer$iv.next() != null) {
                        IonReader $this$readValue_u24lambda_u247_u24lambda_u246 = $this$forEachInContainer$iv;
                        boolean bl4 = false;
                        $this$readValue_u24lambda_u247.add(this.readValue());
                    }
                    $this$forEachInContainer$iv.stepOut();
                    ionValue2 = ionValue3;
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        }
        IonValue v = ionValue2;
        Intrinsics.checkNotNull(v, "null cannot be cast to non-null type com.amazon.ion.impl._Private_IonValue");
        ((_Private_IonValue)v).setSymbolTable(this._reader.getSymbolTable());
        Intrinsics.checkNotNull(annotations);
        if (!(annotations.length == 0)) {
            v.setTypeAnnotationSymbols(Arrays.copyOf(annotations, annotations.length));
        }
        return v;
    }

    @Override
    public IonValue next() {
        if (this._at_eof) {
            throw new NoSuchElementException();
        }
        IonValue ionValue2 = this._next;
        if (ionValue2 == null) {
            ionValue2 = this.prefetch();
        }
        IonValue value = ionValue2;
        this._next = null;
        IonValue ionValue3 = value;
        if (ionValue3 == null) {
            throw new NoSuchElementException();
        }
        return ionValue3;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private final void forEachInContainer(IonReader $this$forEachInContainer, Function1<? super IonReader, Object> block) {
        boolean $i$f$forEachInContainer = false;
        $this$forEachInContainer.stepIn();
        while ($this$forEachInContainer.next() != null) {
            block.invoke($this$forEachInContainer);
        }
        $this$forEachInContainer.stepOut();
    }

    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[IonType.values().length];
            try {
                nArray[IonType.NULL.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.BOOL.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.INT.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.FLOAT.ordinal()] = 4;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.DECIMAL.ordinal()] = 5;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.TIMESTAMP.ordinal()] = 6;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.STRING.ordinal()] = 7;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.SYMBOL.ordinal()] = 8;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.BLOB.ordinal()] = 9;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.CLOB.ordinal()] = 10;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.STRUCT.ordinal()] = 11;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.LIST.ordinal()] = 12;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IonType.SEXP.ordinal()] = 13;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

