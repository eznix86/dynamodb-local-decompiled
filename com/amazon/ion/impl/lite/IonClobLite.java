/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonClob;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonLobLite;
import com.amazon.ion.impl.lite.IonValueLite;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

final class IonClobLite
extends IonLobLite
implements IonClob {
    private static final int HASH_SIGNATURE = IonType.CLOB.toString().hashCode();

    IonClobLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonClobLite(IonClobLite existing, IonContext context) {
        super(existing, context);
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonClobLite(this, context);
    }

    @Override
    public IonClobLite clone() {
        return (IonClobLite)this.shallowClone(ContainerlessContext.wrap(this.getSystem()));
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    int scalarHashCode() {
        return this.lobHashCode(HASH_SIGNATURE);
    }

    @Override
    public IonType getType() {
        return IonType.CLOB;
    }

    @Override
    public Reader newReader(Charset cs) {
        InputStream in = this.newInputStream();
        if (in == null) {
            return null;
        }
        return new InputStreamReader(in, cs);
    }

    @Override
    public String stringValue(Charset cs) {
        byte[] bytes = this.getBytes();
        if (bytes == null) {
            return null;
        }
        return _Private_Utils.decode(bytes, cs);
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        writer.writeClob(this.getBytesNoCopy());
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

