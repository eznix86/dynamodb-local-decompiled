/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl.AppendableFastAppendable;
import com.amazon.ion.impl.IonWriterSystemText;
import com.amazon.ion.impl.IonWriterSystemTextMarkup;
import com.amazon.ion.impl.IonWriterUser;
import com.amazon.ion.impl.OutputStreamFastAppendable;
import com.amazon.ion.impl._Private_CallbackBuilder;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl._Private_ValueFactory;
import com.amazon.ion.system.IonSystemBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.amazon.ion.system.SimpleCatalog;
import com.amazon.ion.util._Private_FastAppendable;
import java.io.OutputStream;

public class _Private_IonTextWriterBuilder
extends IonTextWriterBuilder {
    private static final CharSequence SPACE_CHARACTER = " ";
    public static _Private_IonTextWriterBuilder STANDARD = _Private_IonTextWriterBuilder.standard().immutable();
    private boolean _pretty_print;
    public boolean _blob_as_string;
    public boolean _clob_as_string;
    public boolean _decimal_as_float;
    public boolean _float_nan_and_inf_as_null;
    public boolean _sexp_as_list;
    public boolean _skip_annotations;
    public boolean _string_as_json;
    public boolean _symbol_as_string;
    public boolean _timestamp_as_millis;
    public boolean _timestamp_as_string;
    public boolean _untyped_nulls;
    public boolean _allow_invalid_sids;
    private _Private_CallbackBuilder _callback_builder;

    public static _Private_IonTextWriterBuilder standard() {
        return new Mutable();
    }

    private _Private_IonTextWriterBuilder() {
    }

    private _Private_IonTextWriterBuilder(_Private_IonTextWriterBuilder that) {
        super(that);
        this._callback_builder = that._callback_builder;
        this._pretty_print = that._pretty_print;
        this._blob_as_string = that._blob_as_string;
        this._clob_as_string = that._clob_as_string;
        this._decimal_as_float = that._decimal_as_float;
        this._float_nan_and_inf_as_null = that._float_nan_and_inf_as_null;
        this._sexp_as_list = that._sexp_as_list;
        this._skip_annotations = that._skip_annotations;
        this._string_as_json = that._string_as_json;
        this._symbol_as_string = that._symbol_as_string;
        this._timestamp_as_millis = that._timestamp_as_millis;
        this._timestamp_as_string = that._timestamp_as_string;
        this._untyped_nulls = that._untyped_nulls;
        this._allow_invalid_sids = that._allow_invalid_sids;
    }

    @Override
    public final _Private_IonTextWriterBuilder copy() {
        return new Mutable(this);
    }

    @Override
    public _Private_IonTextWriterBuilder immutable() {
        return this;
    }

    @Override
    public _Private_IonTextWriterBuilder mutable() {
        return this.copy();
    }

    @Override
    public final IonTextWriterBuilder withPrettyPrinting() {
        _Private_IonTextWriterBuilder b = this.mutable();
        b._pretty_print = true;
        return b;
    }

    @Override
    public final IonTextWriterBuilder withJsonDowngrade() {
        _Private_IonTextWriterBuilder b = this.mutable();
        b.withMinimalSystemData();
        this._blob_as_string = true;
        this._clob_as_string = true;
        this._decimal_as_float = true;
        this._float_nan_and_inf_as_null = true;
        this._sexp_as_list = true;
        this._skip_annotations = true;
        this._string_as_json = true;
        this._symbol_as_string = true;
        this._timestamp_as_string = true;
        this._timestamp_as_millis = false;
        this._untyped_nulls = true;
        return b;
    }

    public final _Private_IonTextWriterBuilder withInvalidSidsAllowed(boolean allowInvalidSids) {
        _Private_IonTextWriterBuilder b = this.mutable();
        b._allow_invalid_sids = allowInvalidSids;
        return b;
    }

    final boolean isPrettyPrintOn() {
        return this._pretty_print;
    }

    final CharSequence lineSeparator() {
        if (this._pretty_print) {
            return this.getNewLineType().getCharSequence();
        }
        return SPACE_CHARACTER;
    }

    final CharSequence topLevelSeparator() {
        return this.getWriteTopLevelValuesOnNewLines() ? this.getNewLineType().getCharSequence() : this.lineSeparator();
    }

    private _Private_IonTextWriterBuilder fillDefaults() {
        _Private_IonTextWriterBuilder b = this.copy();
        if (b.getCatalog() == null) {
            b.setCatalog(new SimpleCatalog());
        }
        if (b.getCharset() == null) {
            b.setCharset(UTF8);
        }
        if (b.getNewLineType() == null) {
            b.setNewLineType(IonTextWriterBuilder.NewLineType.PLATFORM_DEPENDENT);
        }
        return (_Private_IonTextWriterBuilder)((IonTextWriterBuilder)b).immutable();
    }

    private IonWriter build(_Private_FastAppendable appender) {
        IonCatalog catalog = this.getCatalog();
        SymbolTable[] imports = this.getImports();
        IonSystem system = IonSystemBuilder.standard().withCatalog(catalog).build();
        SymbolTable defaultSystemSymtab = system.getSystemSymbolTable();
        IonWriterSystemText systemWriter = this.getCallbackBuilder() == null ? new IonWriterSystemText(defaultSystemSymtab, this, appender) : new IonWriterSystemTextMarkup(defaultSystemSymtab, this, appender);
        SymbolTable initialSymtab = _Private_Utils.initialSymtab(((_Private_ValueFactory)((Object)system)).getLstFactory(), defaultSystemSymtab, imports);
        return new IonWriterUser(catalog, system, systemWriter, initialSymtab, !this._allow_invalid_sids);
    }

    @Override
    public final IonWriter build(Appendable out) {
        _Private_IonTextWriterBuilder b = this.fillDefaults();
        AppendableFastAppendable fast = new AppendableFastAppendable(out);
        return b.build(fast);
    }

    @Override
    public final IonWriter build(OutputStream out) {
        _Private_IonTextWriterBuilder b = this.fillDefaults();
        OutputStreamFastAppendable fast = new OutputStreamFastAppendable(out);
        return b.build(fast);
    }

    public final _Private_CallbackBuilder getCallbackBuilder() {
        return this._callback_builder;
    }

    public void setCallbackBuilder(_Private_CallbackBuilder builder) {
        this.mutationCheck();
        this._callback_builder = builder;
    }

    public final _Private_IonTextWriterBuilder withCallbackBuilder(_Private_CallbackBuilder builder) {
        _Private_IonTextWriterBuilder b = this.mutable();
        b.setCallbackBuilder(builder);
        return b;
    }

    private static final class Mutable
    extends _Private_IonTextWriterBuilder {
        private Mutable() {
        }

        private Mutable(_Private_IonTextWriterBuilder that) {
            super(that);
        }

        @Override
        public _Private_IonTextWriterBuilder immutable() {
            return new _Private_IonTextWriterBuilder(this);
        }

        @Override
        public _Private_IonTextWriterBuilder mutable() {
            return this;
        }

        @Override
        protected void mutationCheck() {
        }
    }
}

