/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonSequenceLite;
import com.amazon.ion.impl.lite.IonValueLite;
import java.util.Collection;

final class IonSexpLite
extends IonSequenceLite
implements IonSexp {
    private static final int HASH_SIGNATURE = IonType.SEXP.toString().hashCode();

    IonSexpLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonSexpLite(IonSexpLite existing, IonContext context) {
        super(existing, context);
    }

    IonSexpLite(ContainerlessContext context, Collection<? extends IonValue> elements) throws ContainedValueException {
        super(context, elements);
    }

    @Override
    public IonSexpLite clone() {
        return (IonSexpLite)this.deepClone(false);
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonSexpLite(this, context);
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    public IonType getType() {
        return IonType.SEXP;
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

