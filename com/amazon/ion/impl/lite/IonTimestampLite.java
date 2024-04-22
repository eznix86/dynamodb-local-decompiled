/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.NullValueException;
import com.amazon.ion.Timestamp;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonValueLite;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

final class IonTimestampLite
extends IonValueLite
implements IonTimestamp {
    public static final Integer UTC_OFFSET = Timestamp.UTC_OFFSET;
    private static final int BIT_FLAG_YEAR = 1;
    private static final int BIT_FLAG_MONTH = 2;
    private static final int BIT_FLAG_DAY = 4;
    private static final int BIT_FLAG_MINUTE = 8;
    private static final int BIT_FLAG_SECOND = 16;
    private static final int BIT_FLAG_FRACTION = 32;
    private static final int HASH_SIGNATURE = IonType.TIMESTAMP.toString().hashCode();
    private Timestamp _timestamp_value;

    IonTimestampLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonTimestampLite(IonTimestampLite existing, IonContext context) {
        super(existing, context);
        this._timestamp_value = existing._timestamp_value;
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonTimestampLite(this, context);
    }

    @Override
    public IonTimestampLite clone() {
        return (IonTimestampLite)this.shallowClone(ContainerlessContext.wrap(this.getSystem()));
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    int scalarHashCode() {
        int result = HASH_SIGNATURE ^ this._timestamp_value.hashCode();
        return this.hashTypeAnnotations(result);
    }

    @Override
    public IonType getType() {
        return IonType.TIMESTAMP;
    }

    @Override
    public Timestamp timestampValue() {
        if (this.isNullValue()) {
            return null;
        }
        return this._timestamp_value;
    }

    @Override
    public Date dateValue() {
        if (this._isNullValue()) {
            return null;
        }
        return this._timestamp_value.dateValue();
    }

    @Override
    public Integer getLocalOffset() throws NullValueException {
        if (this._isNullValue()) {
            throw new NullValueException();
        }
        return this._timestamp_value.getLocalOffset();
    }

    private Integer getInternalLocalOffset() {
        if (this._isNullValue()) {
            return null;
        }
        return this._timestamp_value.getLocalOffset();
    }

    @Override
    public void setValue(Timestamp timestamp) {
        this.checkForLock();
        this._timestamp_value = timestamp;
        this._isNullValue(timestamp == null);
    }

    @Override
    public void setValue(BigDecimal millis, Integer localOffset) {
        this.setValue(new Timestamp(millis, localOffset));
    }

    @Override
    public void setValue(long millis, Integer localOffset) {
        this.setValue(new Timestamp(millis, localOffset));
    }

    @Override
    public void setTime(Date value) {
        if (value == null) {
            this.makeNull();
        } else {
            this.setMillis(value.getTime());
        }
    }

    @Override
    public BigDecimal getDecimalMillis() {
        if (this._isNullValue()) {
            return null;
        }
        return this._timestamp_value.getDecimalMillis();
    }

    @Override
    public void setDecimalMillis(BigDecimal millis) {
        Integer offset = this.getInternalLocalOffset();
        this.setValue(millis, offset);
    }

    @Override
    public long getMillis() {
        if (this._isNullValue()) {
            throw new NullValueException();
        }
        return this._timestamp_value.getMillis();
    }

    @Override
    public void setMillis(long millis) {
        Integer offset = this.getInternalLocalOffset();
        this.setValue(millis, offset);
    }

    @Override
    public void setMillisUtc(long millis) {
        this.setValue(millis, UTC_OFFSET);
    }

    @Override
    public void setCurrentTime() {
        long millis = System.currentTimeMillis();
        this.setMillis(millis);
    }

    @Override
    public void setCurrentTimeUtc() {
        long millis = System.currentTimeMillis();
        this.setMillisUtc(millis);
    }

    @Override
    public void setLocalOffset(int minutes) throws NullValueException {
        this.setLocalOffset((Integer)minutes);
    }

    @Override
    public void setLocalOffset(Integer minutes) throws NullValueException {
        this.validateThisNotNull();
        assert (this._timestamp_value != null);
        this.setValue(this._timestamp_value.getDecimalMillis(), minutes);
    }

    @Override
    public void makeNull() {
        this.checkForLock();
        this._timestamp_value = null;
        this._isNullValue(true);
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        writer.writeTimestamp(this._timestamp_value);
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

