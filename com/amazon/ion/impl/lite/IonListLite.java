/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.IonList;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonSequenceLite;
import com.amazon.ion.impl.lite.IonValueLite;
import java.util.Collection;

final class IonListLite
extends IonSequenceLite
implements IonList {
    private static final int HASH_SIGNATURE = IonType.LIST.toString().hashCode();

    IonListLite(ContainerlessContext context, boolean makeNull) {
        super(context, makeNull);
    }

    IonListLite(IonListLite existing, IonContext context) {
        super(existing, context);
    }

    IonListLite(ContainerlessContext context, Collection<? extends IonValue> elements) throws ContainedValueException {
        super(context, elements);
    }

    @Override
    public IonListLite clone() {
        return (IonListLite)this.deepClone(false);
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonListLite(this, context);
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    public IonType getType() {
        return IonType.LIST;
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

