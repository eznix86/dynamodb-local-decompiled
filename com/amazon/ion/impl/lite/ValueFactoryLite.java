/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.Decimal;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl._Private_LocalSymbolTableFactory;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl._Private_ValueFactory;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonBlobLite;
import com.amazon.ion.impl.lite.IonBoolLite;
import com.amazon.ion.impl.lite.IonClobLite;
import com.amazon.ion.impl.lite.IonDecimalLite;
import com.amazon.ion.impl.lite.IonFloatLite;
import com.amazon.ion.impl.lite.IonIntLite;
import com.amazon.ion.impl.lite.IonListLite;
import com.amazon.ion.impl.lite.IonNullLite;
import com.amazon.ion.impl.lite.IonSexpLite;
import com.amazon.ion.impl.lite.IonStringLite;
import com.amazon.ion.impl.lite.IonStructLite;
import com.amazon.ion.impl.lite.IonSymbolLite;
import com.amazon.ion.impl.lite.IonSystemLite;
import com.amazon.ion.impl.lite.IonTimestampLite;
import com.amazon.ion.impl.lite.IonValueLite;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

abstract class ValueFactoryLite
implements _Private_ValueFactory {
    protected final _Private_LocalSymbolTableFactory _lstFactory = _Private_Utils.newLocalSymbolTableAsStructFactory(this);
    private ContainerlessContext _context;

    ValueFactoryLite() {
    }

    protected void set_system(IonSystemLite system) {
        this._context = ContainerlessContext.wrap(system);
    }

    @Override
    public IonBlobLite newBlob(byte[] value) {
        IonBlobLite ionValue2 = this.newBlob(value, 0, value == null ? 0 : value.length);
        return ionValue2;
    }

    @Override
    public IonBlobLite newBlob(byte[] value, int offset, int length) {
        IonBlobLite ionValue2 = new IonBlobLite(this._context, value == null);
        ionValue2.setBytes(value, offset, length);
        return ionValue2;
    }

    @Override
    public IonBoolLite newBool(boolean value) {
        IonBoolLite ionValue2 = new IonBoolLite(this._context, false);
        ionValue2.setValue(value);
        return ionValue2;
    }

    @Override
    public IonBoolLite newBool(Boolean value) {
        IonBoolLite ionValue2 = new IonBoolLite(this._context, value == null);
        ionValue2.setValue(value);
        return ionValue2;
    }

    @Override
    public IonClobLite newClob(byte[] value) {
        IonClobLite ionValue2 = this.newClob(value, 0, value == null ? 0 : value.length);
        return ionValue2;
    }

    @Override
    public IonClobLite newClob(byte[] value, int offset, int length) {
        IonClobLite ionValue2 = new IonClobLite(this._context, value == null);
        ionValue2.setBytes(value, offset, length);
        return ionValue2;
    }

    @Override
    public IonDecimalLite newDecimal(long value) {
        IonDecimalLite ionValue2 = new IonDecimalLite(this._context, false);
        ionValue2.setValue(value);
        return ionValue2;
    }

    @Override
    public IonDecimalLite newDecimal(double value) {
        IonDecimalLite ionValue2 = new IonDecimalLite(this._context, false);
        ionValue2.setValue(value);
        return ionValue2;
    }

    @Override
    public IonDecimalLite newDecimal(BigInteger value) {
        boolean isNull = value == null;
        IonDecimalLite ionValue2 = new IonDecimalLite(this._context, isNull);
        if (value != null) {
            ionValue2.setValue(Decimal.valueOf(value));
        }
        return ionValue2;
    }

    @Override
    public IonDecimalLite newDecimal(BigDecimal value) {
        boolean isNull = value == null;
        IonDecimalLite ionValue2 = new IonDecimalLite(this._context, isNull);
        if (value != null) {
            ionValue2.setValue(value);
        }
        return ionValue2;
    }

    @Override
    public IonListLite newEmptyList() {
        IonListLite ionValue2 = new IonListLite(this._context, false);
        return ionValue2;
    }

    @Override
    public IonSexpLite newEmptySexp() {
        IonSexpLite ionValue2 = new IonSexpLite(this._context, false);
        return ionValue2;
    }

    @Override
    public IonStructLite newEmptyStruct() {
        IonStructLite ionValue2 = new IonStructLite(this._context, false);
        return ionValue2;
    }

    @Override
    public IonFloatLite newFloat(long value) {
        IonFloatLite ionValue2 = new IonFloatLite(this._context, false);
        ionValue2.setValue(value);
        return ionValue2;
    }

    @Override
    public IonFloatLite newFloat(double value) {
        IonFloatLite ionValue2 = new IonFloatLite(this._context, false);
        ionValue2.setValue(value);
        return ionValue2;
    }

    @Override
    public IonIntLite newInt(int value) {
        IonIntLite ionValue2 = new IonIntLite(this._context, false);
        ionValue2.setValue(value);
        return ionValue2;
    }

    @Override
    public IonIntLite newInt(long value) {
        IonIntLite ionValue2 = new IonIntLite(this._context, false);
        ionValue2.setValue(value);
        return ionValue2;
    }

    @Override
    public IonIntLite newInt(Number value) {
        boolean isNull = value == null;
        IonIntLite ionValue2 = new IonIntLite(this._context, isNull);
        if (value != null) {
            ionValue2.setValue(value);
        }
        return ionValue2;
    }

    @Override
    public IonListLite newList(Collection<? extends IonValue> values2) throws ContainedValueException, NullPointerException {
        IonListLite ionValue2 = this.newEmptyList();
        if (values2 == null) {
            ionValue2.makeNull();
        } else {
            ionValue2.addAll(values2);
        }
        return ionValue2;
    }

    @Override
    public IonListLite newList(IonSequence child2) throws ContainedValueException, NullPointerException {
        IonListLite ionValue2 = this.newEmptyList();
        ionValue2.add(child2);
        return ionValue2;
    }

    @Override
    public IonListLite newList(IonValue ... values2) throws ContainedValueException, NullPointerException {
        List<IonValue> e = values2 == null ? null : Arrays.asList(values2);
        IonListLite ionValue2 = this.newEmptyList();
        if (e == null) {
            ionValue2.makeNull();
        } else {
            ionValue2.addAll((Collection<? extends IonValue>)e);
        }
        return ionValue2;
    }

    @Override
    public IonListLite newList(int[] values2) {
        ArrayList<IonIntLite> e = this.newInts(values2);
        return this.newList(e);
    }

    @Override
    public IonListLite newList(long[] values2) {
        ArrayList<IonIntLite> e = this.newInts(values2);
        return this.newList(e);
    }

    @Override
    public IonNullLite newNull() {
        IonNullLite ionValue2 = new IonNullLite(this._context);
        return ionValue2;
    }

    @Override
    public IonValueLite newNull(IonType type) {
        switch (type) {
            case NULL: {
                return this.newNull();
            }
            case BOOL: {
                return this.newNullBool();
            }
            case INT: {
                return this.newNullInt();
            }
            case FLOAT: {
                return this.newNullFloat();
            }
            case DECIMAL: {
                return this.newNullDecimal();
            }
            case TIMESTAMP: {
                return this.newNullTimestamp();
            }
            case SYMBOL: {
                return this.newNullSymbol();
            }
            case STRING: {
                return this.newNullString();
            }
            case CLOB: {
                return this.newNullClob();
            }
            case BLOB: {
                return this.newNullBlob();
            }
            case LIST: {
                return this.newNullList();
            }
            case SEXP: {
                return this.newNullSexp();
            }
            case STRUCT: {
                return this.newNullStruct();
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public IonBlobLite newNullBlob() {
        IonBlobLite ionValue2 = new IonBlobLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonBoolLite newNullBool() {
        IonBoolLite ionValue2 = new IonBoolLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonClobLite newNullClob() {
        IonClobLite ionValue2 = new IonClobLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonDecimalLite newNullDecimal() {
        IonDecimalLite ionValue2 = new IonDecimalLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonFloatLite newNullFloat() {
        IonFloatLite ionValue2 = new IonFloatLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonIntLite newNullInt() {
        IonIntLite ionValue2 = new IonIntLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonListLite newNullList() {
        IonListLite ionValue2 = new IonListLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonSexpLite newNullSexp() {
        IonSexpLite ionValue2 = new IonSexpLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonStringLite newNullString() {
        IonStringLite ionValue2 = new IonStringLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonStructLite newNullStruct() {
        IonStructLite ionValue2 = new IonStructLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonSymbolLite newNullSymbol() {
        IonSymbolLite ionValue2 = new IonSymbolLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonTimestampLite newNullTimestamp() {
        IonTimestampLite ionValue2 = new IonTimestampLite(this._context, true);
        return ionValue2;
    }

    @Override
    public IonSexpLite newSexp(Collection<? extends IonValue> values2) throws ContainedValueException, NullPointerException {
        IonSexpLite ionValue2 = this.newEmptySexp();
        if (values2 == null) {
            ionValue2.makeNull();
        } else {
            ionValue2.addAll(values2);
        }
        return ionValue2;
    }

    @Override
    public IonSexpLite newSexp(IonSequence child2) throws ContainedValueException, NullPointerException {
        IonSexpLite ionValue2 = this.newEmptySexp();
        ionValue2.add(child2);
        return ionValue2;
    }

    @Override
    public IonSexp newSexp(IonValue ... values2) throws ContainedValueException, NullPointerException {
        List<IonValue> e = values2 == null ? null : Arrays.asList(values2);
        IonSexpLite ionValue2 = this.newEmptySexp();
        if (e == null) {
            ionValue2.makeNull();
        } else {
            ionValue2.addAll((Collection<? extends IonValue>)e);
        }
        return ionValue2;
    }

    @Override
    public IonSexpLite newSexp(int[] values2) {
        ArrayList<IonIntLite> e = this.newInts(values2);
        return this.newSexp(e);
    }

    @Override
    public IonSexpLite newSexp(long[] values2) {
        ArrayList<IonIntLite> e = this.newInts(values2);
        return this.newSexp(e);
    }

    @Override
    public IonStringLite newString(String value) {
        boolean isNull = value == null;
        IonStringLite ionValue2 = new IonStringLite(this._context, isNull);
        if (value != null) {
            ionValue2.setValue(value);
        }
        return ionValue2;
    }

    @Override
    public IonSymbolLite newSymbol(String value) {
        boolean isNull = value == null;
        IonSymbolLite ionValue2 = new IonSymbolLite(this._context, isNull);
        if (value != null) {
            ionValue2.setValue(value);
        }
        return ionValue2;
    }

    @Override
    public IonSymbolLite newSymbol(SymbolToken value) {
        return new IonSymbolLite(this._context, value);
    }

    @Override
    public IonTimestampLite newTimestamp(Timestamp value) {
        boolean isNull = value == null;
        IonTimestampLite ionValue2 = new IonTimestampLite(this._context, isNull);
        if (value != null) {
            ionValue2.setValue(value);
        }
        return ionValue2;
    }

    private ArrayList<IonIntLite> newInts(int[] elements) {
        ArrayList<IonIntLite> e = null;
        if (elements != null) {
            e = new ArrayList<IonIntLite>(elements.length);
            for (int i = 0; i < elements.length; ++i) {
                int value = elements[i];
                e.add(this.newInt(value));
            }
        }
        return e;
    }

    private ArrayList<IonIntLite> newInts(long[] elements) {
        ArrayList<IonIntLite> e = null;
        if (elements != null) {
            e = new ArrayList<IonIntLite>(elements.length);
            for (int i = 0; i < elements.length; ++i) {
                long value = elements[i];
                e.add(this.newInt(value));
            }
        }
        return e;
    }

    @Override
    public _Private_LocalSymbolTableFactory getLstFactory() {
        return this._lstFactory;
    }
}

