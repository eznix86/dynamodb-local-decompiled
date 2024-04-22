/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonBool;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.NullValueException;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonValueLite;
import java.io.IOException;

final class IonBoolLite
extends IonValueLite
implements IonBool {
    private static final int HASH_SIGNATURE = IonType.BOOL.toString().hashCode();
    protected static final int TRUE_HASH = HASH_SIGNATURE ^ 16777619 * Boolean.TRUE.hashCode();
    protected static final int FALSE_HASH = HASH_SIGNATURE ^ 16777619 * Boolean.FALSE.hashCode();

    IonBoolLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonBoolLite(IonBoolLite existing, IonContext context) {
        super(existing, context);
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonBoolLite(this, context);
    }

    @Override
    public IonBoolLite clone() {
        return (IonBoolLite)this.shallowClone(ContainerlessContext.wrap(this.getSystem()));
    }

    @Override
    public IonType getType() {
        return IonType.BOOL;
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    int scalarHashCode() {
        int result = this._isBoolTrue() ? TRUE_HASH : FALSE_HASH;
        return this.hashTypeAnnotations(result);
    }

    @Override
    public boolean booleanValue() throws NullValueException {
        this.validateThisNotNull();
        return this._isBoolTrue();
    }

    @Override
    public void setValue(boolean b) {
        this.setValue((Boolean)b);
    }

    @Override
    public void setValue(Boolean b) {
        this.checkForLock();
        if (b == null) {
            this._isBoolTrue(false);
            this._isNullValue(true);
        } else {
            this._isBoolTrue(b);
            this._isNullValue(false);
        }
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        writer.writeBool(this._isBoolTrue());
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

