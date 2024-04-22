/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonString;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonTextLite;
import com.amazon.ion.impl.lite.IonValueLite;
import java.io.IOException;

final class IonStringLite
extends IonTextLite
implements IonString {
    private static final int HASH_SIGNATURE = IonType.STRING.toString().hashCode();

    IonStringLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonStringLite(IonStringLite existing, IonContext context) {
        super(existing, context);
    }

    @Override
    IonValueLite shallowClone(IonContext parentContext) {
        return new IonStringLite(this, parentContext);
    }

    @Override
    public IonStringLite clone() {
        return (IonStringLite)this.shallowClone(ContainerlessContext.wrap(this.getSystem()));
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    int scalarHashCode() {
        int result = HASH_SIGNATURE;
        return this.hashTypeAnnotations(result ^= this._text_value.hashCode());
    }

    @Override
    public IonType getType() {
        return IonType.STRING;
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        writer.writeString(this._text_value);
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

