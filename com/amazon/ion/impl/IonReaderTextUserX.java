/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonType;
import com.amazon.ion.OffsetSpan;
import com.amazon.ion.SeekableReader;
import com.amazon.ion.Span;
import com.amazon.ion.SpanProvider;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.TextSpan;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.UnsupportedIonVersionException;
import com.amazon.ion.impl.DowncastingFaceted;
import com.amazon.ion.impl.IonReaderTextSystemX;
import com.amazon.ion.impl.UnifiedDataPageX;
import com.amazon.ion.impl.UnifiedInputStreamX;
import com.amazon.ion.impl._Private_LocalSymbolTableFactory;
import com.amazon.ion.impl._Private_ReaderWriter;
import java.util.regex.Pattern;

class IonReaderTextUserX
extends IonReaderTextSystemX
implements _Private_ReaderWriter {
    private static final Pattern ION_VERSION_MARKER_REGEX = Pattern.compile("^\\$ion_[0-9]+_[0-9]+$");
    private final int _physical_start_offset;
    private final _Private_LocalSymbolTableFactory _lstFactory;
    IonCatalog _catalog;
    SymbolTable _symbols;
    private int _symbol_table_top = 0;
    private SymbolTable[] _symbol_table_stack = new SymbolTable[3];

    protected IonReaderTextUserX(IonCatalog catalog, _Private_LocalSymbolTableFactory lstFactory, UnifiedInputStreamX uis, int physicalStartOffset) {
        super(uis);
        this._symbols = this._system_symtab;
        this._physical_start_offset = physicalStartOffset;
        this._catalog = catalog;
        this._lstFactory = lstFactory;
    }

    protected IonReaderTextUserX(IonCatalog catalog, _Private_LocalSymbolTableFactory lstFactory, UnifiedInputStreamX uis) {
        this(catalog, lstFactory, uis, 0);
    }

    @Override
    public boolean hasNext() {
        boolean has_next = this.has_next_user_value();
        return has_next;
    }

    private final boolean has_next_user_value() {
        this.clear_system_value_stack();
        block4: while (!this._has_next_called) {
            this.has_next_raw_value();
            if (this._value_type == null || this.isNullValue() || !IonType.DATAGRAM.equals((Object)this.getContainerType())) continue;
            switch (this._value_type) {
                case STRUCT: {
                    if (this._annotation_count <= 0 || !"$ion_symbol_table".equals(this._annotations[0].getText())) continue block4;
                    this._symbols = this._lstFactory.newLocalSymtab(this._catalog, this, true);
                    this.push_symbol_table(this._symbols);
                    this._has_next_called = false;
                    continue block4;
                }
                case SYMBOL: {
                    String version;
                    if (this._annotation_count != 0 || !IonReaderTextUserX.isIonVersionMarker(version = this.symbolValue().getText())) continue block4;
                    if ("$ion_1_0".equals(version)) {
                        if (this._value_keyword != 17) {
                            this.symbol_table_reset();
                            this.push_symbol_table(this._system_symtab);
                        }
                        this._has_next_called = false;
                        continue block4;
                    }
                    throw new UnsupportedIonVersionException(version);
                }
            }
        }
        return !this._eof;
    }

    private static boolean isIonVersionMarker(String text) {
        return text != null && ION_VERSION_MARKER_REGEX.matcher(text).matches();
    }

    private final void symbol_table_reset() {
        IonType t = this.next();
        assert (IonType.SYMBOL.equals((Object)t));
        this._symbols = this._system_symtab;
    }

    private void validateSymbolToken(SymbolToken symbol) {
        if (symbol != null && symbol.getText() == null && symbol.getSid() > this.getSymbolTable().getMaxId()) {
            throw new UnknownSymbolException(symbol.getSid());
        }
    }

    @Override
    public SymbolToken[] getTypeAnnotationSymbols() {
        SymbolToken[] annotations;
        for (SymbolToken annotation : annotations = super.getTypeAnnotationSymbols()) {
            this.validateSymbolToken(annotation);
        }
        return annotations;
    }

    @Override
    public final SymbolToken getFieldNameSymbol() {
        SymbolToken fieldName = super.getFieldNameSymbol();
        this.validateSymbolToken(fieldName);
        return fieldName;
    }

    @Override
    public final SymbolToken symbolValue() {
        SymbolToken symbol = super.symbolValue();
        this.validateSymbolToken(symbol);
        return symbol;
    }

    @Override
    public SymbolTable getSymbolTable() {
        return this._symbols;
    }

    private void clear_system_value_stack() {
        while (this._symbol_table_top > 0) {
            --this._symbol_table_top;
            this._symbol_table_stack[this._symbol_table_top] = null;
        }
    }

    private void push_symbol_table(SymbolTable symbols) {
        assert (symbols != null);
        if (this._symbol_table_top >= this._symbol_table_stack.length) {
            int new_len = this._symbol_table_stack.length * 2;
            SymbolTable[] temp = new SymbolTable[new_len];
            System.arraycopy(this._symbol_table_stack, 0, temp, 0, this._symbol_table_stack.length);
            this._symbol_table_stack = temp;
        }
        this._symbol_table_stack[this._symbol_table_top++] = symbols;
    }

    @Override
    public SymbolTable pop_passed_symbol_table() {
        if (this._symbol_table_top <= 0) {
            return null;
        }
        --this._symbol_table_top;
        SymbolTable symbols = this._symbol_table_stack[this._symbol_table_top];
        this._symbol_table_stack[this._symbol_table_top] = null;
        return symbols;
    }

    public Span currentSpanImpl() {
        if (this.getType() == null) {
            throw new IllegalStateException("must be on a value");
        }
        IonReaderTextSpan pos = new IonReaderTextSpan(this);
        return pos;
    }

    private void hoistImpl(Span span) {
        UnifiedInputStreamX iis;
        if (!(span instanceof IonReaderTextSpan)) {
            throw new IllegalArgumentException("position must match the reader");
        }
        IonReaderTextSpan text_span = (IonReaderTextSpan)span;
        UnifiedInputStreamX current_stream = this._scanner.getSourceStream();
        UnifiedDataPageX curr_page = text_span.getDataPage();
        int array_offset = (int)text_span._start_offset + this._physical_start_offset;
        int page_limit = curr_page._page_limit;
        int array_length = page_limit - array_offset;
        assert (text_span.getStartOffset() <= Integer.MAX_VALUE);
        if (current_stream._is_byte_data) {
            byte[] bytes = current_stream.getByteArray();
            assert (bytes != null);
            iis = UnifiedInputStreamX.makeStream(bytes, array_offset, array_length);
        } else {
            char[] chars = current_stream.getCharArray();
            assert (chars != null);
            iis = UnifiedInputStreamX.makeStream(chars, array_offset, array_length);
        }
        IonType container = text_span.getContainerType();
        this.re_init(iis, container, text_span._start_line, text_span._start_column);
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        if (facetType == SpanProvider.class) {
            return facetType.cast(new SpanProviderFacet());
        }
        if (facetType == SeekableReader.class && this._scanner.isBufferedInput()) {
            return facetType.cast(new SeekableReaderFacet());
        }
        return super.asFacet(facetType);
    }

    private final class SeekableReaderFacet
    extends SpanProviderFacet
    implements SeekableReader {
        private SeekableReaderFacet() {
        }

        @Override
        public void hoist(Span span) {
            IonReaderTextUserX.this.hoistImpl(span);
        }
    }

    private class SpanProviderFacet
    implements SpanProvider {
        private SpanProviderFacet() {
        }

        @Override
        public Span currentSpan() {
            return IonReaderTextUserX.this.currentSpanImpl();
        }
    }

    private static final class IonReaderTextSpan
    extends DowncastingFaceted
    implements OffsetSpan,
    Span,
    TextSpan {
        private final UnifiedDataPageX _data_page;
        private final IonType _container_type;
        private final long _start_offset;
        private final long _start_line;
        private final long _start_column;

        IonReaderTextSpan(IonReaderTextUserX reader) {
            UnifiedInputStreamX current_stream = reader._scanner.getSourceStream();
            this._data_page = current_stream._buffer.getCurrentPage();
            this._container_type = reader.getContainerType();
            this._start_offset = reader._value_start_offset - (long)reader._physical_start_offset;
            this._start_line = reader._value_start_line;
            this._start_column = reader._value_start_column;
        }

        @Override
        public long getStartLine() {
            if (this._start_line < 1L) {
                throw new IllegalStateException("not positioned on a reader");
            }
            return this._start_line;
        }

        @Override
        public long getStartColumn() {
            if (this._start_column < 0L) {
                throw new IllegalStateException("not positioned on a reader");
            }
            return this._start_column;
        }

        @Override
        public long getFinishLine() {
            return -1L;
        }

        @Override
        public long getFinishColumn() {
            return -1L;
        }

        @Override
        public long getStartOffset() {
            return this._start_offset;
        }

        @Override
        public long getFinishOffset() {
            return -1L;
        }

        IonType getContainerType() {
            return this._container_type;
        }

        UnifiedDataPageX getDataPage() {
            return this._data_page;
        }
    }
}

