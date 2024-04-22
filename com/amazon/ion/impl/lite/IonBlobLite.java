/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonBlob;
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

final class IonBlobLite
extends IonLobLite
implements IonBlob {
    private static final int HASH_SIGNATURE = IonType.BLOB.toString().hashCode();

    IonBlobLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonBlobLite(IonBlobLite existing, IonContext context) {
        super(existing, context);
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonBlobLite(this, context);
    }

    @Override
    public IonBlobLite clone() {
        return (IonBlobLite)this.shallowClone(ContainerlessContext.wrap(this.getSystem()));
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
        return IonType.BLOB;
    }

    @Override
    public void printBase64(Appendable out) throws IOException {
        this.validateThisNotNull();
        try (InputStream byteStream = this.newInputStream();){
            _Private_Utils.writeAsBase64(byteStream, out);
        }
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        writer.writeBlob(this.getBytesNoCopy());
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

