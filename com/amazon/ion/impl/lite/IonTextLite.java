/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonText;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonValueLite;

abstract class IonTextLite
extends IonValueLite
implements IonText {
    protected String _text_value;

    protected IonTextLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonTextLite(IonTextLite existing, IonContext context) {
        super(existing, context);
        this._text_value = existing._text_value;
    }

    @Override
    public abstract IonTextLite clone();

    @Override
    public void setValue(String value) {
        this.checkForLock();
        this._set_value(value);
    }

    protected final String _get_value() {
        return this._text_value;
    }

    @Override
    public String stringValue() {
        return this._text_value;
    }

    protected final void _set_value(String value) {
        this._text_value = value;
        this._isNullValue(value == null);
    }
}

