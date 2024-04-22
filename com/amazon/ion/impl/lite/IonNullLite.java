/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonNull;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonValueLite;
import java.io.IOException;

final class IonNullLite
extends IonValueLite
implements IonNull {
    private static final int HASH_SIGNATURE = IonType.NULL.toString().hashCode();

    protected IonNullLite(ContainerlessContext context) {
        super(context, true);
    }

    IonNullLite(IonNullLite existing, IonContext context) {
        super(existing, context);
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonNullLite(this, context);
    }

    @Override
    public IonNullLite clone() {
        return (IonNullLite)this.shallowClone(ContainerlessContext.wrap(this.getSystem()));
    }

    @Override
    public IonType getType() {
        return IonType.NULL;
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        writer.writeNull();
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    public int scalarHashCode() {
        return this.hashTypeAnnotations(HASH_SIGNATURE);
    }
}

