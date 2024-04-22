/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.NullValueException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl._Private_IonSymbol;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonTextLite;
import com.amazon.ion.impl.lite.IonValueLite;
import java.io.IOException;

final class IonSymbolLite
extends IonTextLite
implements _Private_IonSymbol {
    private static final int HASH_SIGNATURE = IonType.SYMBOL.toString().hashCode();
    private int _sid = -1;

    IonSymbolLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonSymbolLite(IonSymbolLite existing, IonContext context) throws UnknownSymbolException {
        super(existing, context);
    }

    IonSymbolLite(ContainerlessContext context, SymbolToken sym) {
        super(context, sym == null);
        if (sym != null) {
            String text = sym.getText();
            int sid = sym.getSid();
            assert (text != null || sid >= 0);
            if (text != null) {
                super.setValue(text);
            } else {
                this._sid = sid;
                this._isSymbolIdPresent(true);
            }
        }
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        IonSymbolLite clone = new IonSymbolLite(this, context);
        if (this._sid == 0) {
            clone._sid = 0;
        }
        return clone;
    }

    @Override
    public IonSymbolLite clone() throws UnknownSymbolException {
        if (!this.isNullValue() && this._sid != -1 && this._sid != 0 && this._stringValue() == null) {
            throw new UnknownSymbolException(this._sid);
        }
        return (IonSymbolLite)this.shallowClone(ContainerlessContext.wrap(this.getSystem()));
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    int scalarHashCode() {
        int sidHashSalt = 127;
        int textHashSalt = 31;
        int result = HASH_SIGNATURE;
        int tokenHashCode = this._text_value == null ? this._sid * 127 : this._text_value.hashCode() * 31;
        tokenHashCode ^= tokenHashCode << 29 ^ tokenHashCode >> 3;
        return this.hashTypeAnnotations(result ^= tokenHashCode);
    }

    @Override
    public IonType getType() {
        return IonType.SYMBOL;
    }

    @Override
    @Deprecated
    public int getSymbolId() throws NullValueException {
        return this.getSymbolId(null);
    }

    private int getSymbolId(_Private_IonValue.SymbolTableProvider symbolTableProvider) throws NullValueException {
        SymbolToken tok;
        SymbolTable symtab;
        this.validateThisNotNull();
        if (this._sid != -1 || this.isReadOnly()) {
            return this._sid;
        }
        SymbolTable symbolTable = symtab = symbolTableProvider != null ? symbolTableProvider.getSymbolTable() : this.getSymbolTable();
        if (symtab == null) {
            symtab = this.getSystem().getSystemSymbolTable();
        }
        assert (symtab != null);
        String name = this._get_value();
        if (!symtab.isLocalTable()) {
            this.setSID(symtab.findSymbol(name));
            if (this._sid > 0 || this.isReadOnly()) {
                return this._sid;
            }
        }
        if ((tok = symtab.find(name)) != null) {
            this.setSID(tok.getSid());
            this._set_value(tok.getText());
        }
        return this._sid;
    }

    private void setSID(int sid) {
        this._sid = sid;
        if (this._sid > 0) {
            this.cascadeSIDPresentToContextRoot();
        }
    }

    private String _stringValue() {
        return this._stringValue(new IonValueLite.LazySymbolTableProvider(this));
    }

    private String _stringValue(_Private_IonValue.SymbolTableProvider symbolTableProvider) {
        String name = this._get_value();
        if (name == null) {
            assert (this._sid >= 0);
            SymbolTable symbols = symbolTableProvider.getSymbolTable();
            name = symbols.findKnownSymbol(this._sid);
            if (name != null && !this._isLocked()) {
                this._set_value(name);
            }
        }
        return name;
    }

    @Override
    public SymbolToken symbolValue() {
        return this.symbolValue(new IonValueLite.LazySymbolTableProvider(this));
    }

    @Override
    public SymbolToken symbolValue(_Private_IonValue.SymbolTableProvider symbolTableProvider) {
        if (this.isNullValue()) {
            return null;
        }
        int sid = this.getSymbolId(symbolTableProvider);
        String text = this._stringValue(symbolTableProvider);
        return _Private_Utils.newSymbolToken(text, sid);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        this._sid = -1;
    }

    protected boolean isIonVersionMarker() {
        return this._isIVM();
    }

    @Override
    boolean attemptClearSymbolIDValues() {
        boolean allSymbolIDsCleared = super.attemptClearSymbolIDValues();
        if (!this.isNullValue() && this._sid != -1) {
            if (this._stringValue() != null) {
                this._sid = -1;
            } else {
                allSymbolIDsCleared = false;
            }
        }
        return allSymbolIDsCleared;
    }

    protected void setIsIonVersionMarker(boolean isIVM) {
        assert ("$ion_1_0".equals(this._get_value()) == isIVM);
        this._isIVM(isIVM);
        this._isSystemValue(isIVM);
        this._sid = 2;
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        String text = this._stringValue(symbolTableProvider);
        if (text != null) {
            writer.writeSymbol(text);
        } else {
            writer.writeSymbolToken(_Private_Utils.newSymbolToken(this.getSymbolId(symbolTableProvider)));
        }
    }

    @Override
    public String stringValue() throws UnknownSymbolException {
        return this.stringValue(new IonValueLite.LazySymbolTableProvider(this));
    }

    private String stringValue(_Private_IonValue.SymbolTableProvider symbolTableProvider) throws UnknownSymbolException {
        if (this.isNullValue()) {
            return null;
        }
        String name = this._stringValue(symbolTableProvider);
        if (name == null) {
            assert (this._sid >= 0);
            throw new UnknownSymbolException(this._sid);
        }
        return name;
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

