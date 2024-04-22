/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonLob;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonValueLite;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.CRC32;

abstract class IonLobLite
extends IonValueLite
implements IonLob {
    private byte[] _lob_value;

    protected IonLobLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonLobLite(IonLobLite existing, IonContext context) {
        super(existing, context);
        if (null != existing._lob_value) {
            int size = existing._lob_value.length;
            this._lob_value = new byte[size];
            System.arraycopy(existing._lob_value, 0, this._lob_value, 0, size);
        }
    }

    @Override
    public abstract IonLobLite clone();

    protected int lobHashCode(int seed) {
        int result = seed;
        CRC32 crc = new CRC32();
        crc.update(this._lob_value);
        return this.hashTypeAnnotations(result ^= (int)crc.getValue());
    }

    protected final void copyBytesFrom(byte[] source, int offset, int length) {
        if (source == null) {
            this._lob_value = null;
            this._isNullValue(true);
        } else {
            if (this._lob_value == null || this._lob_value.length != length) {
                this._lob_value = new byte[length];
            }
            System.arraycopy(source, offset, this._lob_value, 0, length);
            this._isNullValue(false);
        }
    }

    protected byte[] getBytesNoCopy() {
        return this._lob_value;
    }

    @Override
    public final InputStream newInputStream() {
        if (this._isNullValue()) {
            return null;
        }
        return new ByteArrayInputStream(this._lob_value);
    }

    @Override
    public final byte[] getBytes() {
        byte[] user_copy = this._isNullValue() ? null : (byte[])this._lob_value.clone();
        return user_copy;
    }

    @Override
    public final void setBytes(byte[] bytes) {
        this.setBytes(bytes, 0, bytes == null ? 0 : bytes.length);
    }

    @Override
    public final void setBytes(byte[] bytes, int offset, int length) {
        this.checkForLock();
        this.copyBytesFrom(bytes, offset, length);
    }

    @Override
    public final int byteSize() {
        this.validateThisNotNull();
        return this._lob_value.length;
    }
}

