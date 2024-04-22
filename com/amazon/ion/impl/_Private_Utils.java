/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SubstituteSymbolTableException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.ValueFactory;
import com.amazon.ion.impl.Base64Encoder;
import com.amazon.ion.impl.IonBinary;
import com.amazon.ion.impl.IonIteratorImpl;
import com.amazon.ion.impl.IonUTF8;
import com.amazon.ion.impl.LocalSymbolTable;
import com.amazon.ion.impl.LocalSymbolTableAsStruct;
import com.amazon.ion.impl.SharedSymbolTable;
import com.amazon.ion.impl.SubstituteSymbolTable;
import com.amazon.ion.impl.SymbolTableAsStruct;
import com.amazon.ion.impl.SymbolTokenImpl;
import com.amazon.ion.impl._Private_IonConstants;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl._Private_LocalSymbolTable;
import com.amazon.ion.impl._Private_LocalSymbolTableFactory;
import com.amazon.ion.util.IonStreamUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.TimeZone;

public final class _Private_Utils {
    public static final boolean READER_HASNEXT_REMOVED = true;
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final int MAX_LOOKAHEAD_UTF16 = 11;
    public static final String ASCII_CHARSET_NAME = "US-ASCII";
    public static final Charset ASCII_CHARSET = Charset.forName("US-ASCII");
    public static final String UTF8_CHARSET_NAME = "UTF-8";
    public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    public static final ListIterator<?> EMPTY_ITERATOR = new ListIterator(){

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        public Object previous() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new IllegalStateException();
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return -1;
        }

        public void add(Object o2) {
            throw new UnsupportedOperationException();
        }

        public void set(Object o2) {
            throw new UnsupportedOperationException();
        }
    };

    public static final <T> ListIterator<T> emptyIterator() {
        return EMPTY_ITERATOR;
    }

    public static boolean safeEquals(Object a, Object b) {
        return a != null ? a.equals(b) : b == null;
    }

    public static byte[] copyOf(byte[] original, int newLength) {
        byte[] result = new byte[newLength];
        System.arraycopy(original, 0, result, 0, Math.min(newLength, original.length));
        return result;
    }

    public static String[] copyOf(String[] original, int newLength) {
        String[] result = new String[newLength];
        System.arraycopy(original, 0, result, 0, Math.min(newLength, original.length));
        return result;
    }

    public static <T> void addAll(Collection<T> dest, Iterator<T> src) {
        if (src != null) {
            while (src.hasNext()) {
                T value = src.next();
                dest.add(value);
            }
        }
    }

    public static <T> void addAllNonNull(Collection<T> dest, Iterator<T> src) {
        if (src != null) {
            while (src.hasNext()) {
                T value = src.next();
                if (value == null) continue;
                dest.add(value);
            }
        }
    }

    public static SymbolTokenImpl newSymbolToken(String text, int sid) {
        return new SymbolTokenImpl(text, sid);
    }

    public static SymbolTokenImpl newSymbolToken(int sid) {
        return new SymbolTokenImpl(sid);
    }

    public static SymbolToken newSymbolToken(SymbolTable symtab, String text) {
        SymbolToken tok;
        text.getClass();
        SymbolToken symbolToken = tok = symtab == null ? null : symtab.find(text);
        if (tok == null) {
            tok = new SymbolTokenImpl(text, -1);
        }
        return tok;
    }

    public static SymbolToken newSymbolToken(SymbolTable symtab, int sid) {
        if (sid < 1) {
            throw new IllegalArgumentException();
        }
        String text = symtab == null ? null : symtab.findKnownSymbol(sid);
        return new SymbolTokenImpl(text, sid);
    }

    public static SymbolToken[] newSymbolTokens(SymbolTable symtab, String ... text) {
        int count;
        if (text != null && (count = text.length) != 0) {
            SymbolToken[] result = new SymbolToken[count];
            for (int i = 0; i < count; ++i) {
                String s = text[i];
                result[i] = _Private_Utils.newSymbolToken(symtab, s);
            }
            return result;
        }
        return SymbolToken.EMPTY_ARRAY;
    }

    public static SymbolToken[] newSymbolTokens(SymbolTable symtab, int ... syms) {
        if (syms != null) {
            int count = syms.length;
            if (syms.length != 0) {
                SymbolToken[] result = new SymbolToken[count];
                for (int i = 0; i < count; ++i) {
                    int s = syms[i];
                    result[i] = _Private_Utils.newSymbolToken(symtab, s);
                }
                return result;
            }
        }
        return SymbolToken.EMPTY_ARRAY;
    }

    public static SymbolToken localize(SymbolTable symtab, SymbolToken sym) {
        String text = sym.getText();
        int sid = sym.getSid();
        if (symtab != null) {
            if (text == null) {
                text = symtab.findKnownSymbol(sid);
                if (text != null) {
                    sym = new SymbolTokenImpl(text, sid);
                }
            } else {
                SymbolToken newSym = symtab.find(text);
                if (newSym != null) {
                    sym = newSym;
                } else if (sid >= 0) {
                    sym = new SymbolTokenImpl(text, -1);
                }
            }
        } else if (text != null && sid >= 0) {
            sym = new SymbolTokenImpl(text, -1);
        }
        return sym;
    }

    public static void localize(SymbolTable symtab, SymbolToken[] syms, int count) {
        for (int i = 0; i < count; ++i) {
            SymbolToken sym = syms[i];
            SymbolToken updated = _Private_Utils.localize(symtab, sym);
            if (updated == sym) continue;
            syms[i] = updated;
        }
    }

    public static void localize(SymbolTable symtab, SymbolToken[] syms) {
        _Private_Utils.localize(symtab, syms, syms.length);
    }

    public static String[] toStrings(SymbolToken[] symbols, int count) {
        if (count == 0) {
            return EMPTY_STRING_ARRAY;
        }
        String[] annotations = new String[count];
        for (int i = 0; i < count; ++i) {
            SymbolToken tok = symbols[i];
            String text = tok.getText();
            if (text == null) {
                throw new UnknownSymbolException(tok.getSid());
            }
            annotations[i] = text;
        }
        return annotations;
    }

    public static int[] toSids(SymbolToken[] symbols, int count) {
        if (count == 0) {
            return EMPTY_INT_ARRAY;
        }
        int[] sids = new int[count];
        for (int i = 0; i < count; ++i) {
            sids[i] = symbols[i].getSid();
        }
        return sids;
    }

    public static byte[] encode(String s, Charset charset) {
        CharsetEncoder encoder = charset.newEncoder();
        try {
            ByteBuffer buffer = encoder.encode(CharBuffer.wrap(s));
            byte[] bytes = buffer.array();
            int limit2 = buffer.limit();
            if (limit2 < bytes.length) {
                bytes = _Private_Utils.copyOf(bytes, limit2);
            }
            return bytes;
        } catch (CharacterCodingException e) {
            throw new IonException("Invalid string data", e);
        }
    }

    public static String decode(byte[] bytes, Charset charset) {
        CharsetDecoder decoder = charset.newDecoder();
        try {
            CharBuffer buffer = decoder.decode(ByteBuffer.wrap(bytes));
            return buffer.toString();
        } catch (CharacterCodingException e) {
            String message = "Input is not valid " + charset.displayName() + " data";
            throw new IonException(message, e);
        }
    }

    public static byte[] utf8(String s) {
        return _Private_Utils.encode(s, UTF8_CHARSET);
    }

    public static String utf8(byte[] bytes) {
        return _Private_Utils.decode(bytes, UTF8_CHARSET);
    }

    public static byte[] convertUtf16UnitsToUtf8(String text) {
        byte[] data = new byte[4 * text.length()];
        int limit2 = 0;
        for (int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            limit2 += IonUTF8.convertToUTF8Bytes(c, data, limit2, data.length - limit2);
        }
        byte[] result = new byte[limit2];
        System.arraycopy(data, 0, result, 0, limit2);
        return result;
    }

    public static int readFully(InputStream in, byte[] buf) throws IOException {
        return _Private_Utils.readFully(in, buf, 0, buf.length);
    }

    public static int readFully(InputStream in, byte[] buf, int offset, int length) throws IOException {
        int readBytes = 0;
        while (readBytes < length) {
            int amount = in.read(buf, offset, length - readBytes);
            if (amount < 0) {
                return readBytes;
            }
            readBytes += amount;
            offset += amount;
        }
        return readBytes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] loadFileBytes(File file) throws IOException {
        long len = file.length();
        if (len < 0L || len > 0x7FFFFFF7L) {
            throw new IllegalArgumentException("File too long: " + file);
        }
        byte[] buf = new byte[(int)len];
        try (FileInputStream in = new FileInputStream(file);){
            int readBytesCount = in.read(buf);
            if ((long)readBytesCount != len || in.read() != -1) {
                throw new IOException("Read the wrong number of bytes from " + file);
            }
        }
        return buf;
    }

    public static String utf8FileToString(File file) throws IonException, IOException {
        byte[] utf8Bytes = _Private_Utils.loadFileBytes(file);
        String s = _Private_Utils.utf8(utf8Bytes);
        return s;
    }

    public static byte[] loadStreamBytes(InputStream in) throws IOException {
        IonBinary.BufferManager buffer = new IonBinary.BufferManager(in);
        IonBinary.Reader bufReader = buffer.reader();
        bufReader.sync();
        bufReader.setPosition(0);
        byte[] bytes = bufReader.getBytes();
        return bytes;
    }

    public static String loadReader(Reader in) throws IOException {
        int len;
        StringBuilder buf = new StringBuilder(2048);
        char[] chars = new char[2048];
        while ((len = in.read(chars)) != -1) {
            buf.append(chars, 0, len);
        }
        return buf.toString();
    }

    public static boolean streamIsIonBinary(PushbackInputStream pushback) throws IonException, IOException {
        boolean isBinary = false;
        byte[] cookie = new byte[_Private_IonConstants.BINARY_VERSION_MARKER_SIZE];
        int len = _Private_Utils.readFully(pushback, cookie);
        if (len == _Private_IonConstants.BINARY_VERSION_MARKER_SIZE) {
            isBinary = IonStreamUtils.isIonBinary(cookie);
        }
        if (len > 0) {
            pushback.unread(cookie, 0, len);
        }
        return isBinary;
    }

    public static Iterator<IonValue> iterate(ValueFactory valueFactory, IonReader input) {
        return new IonIteratorImpl(valueFactory, input);
    }

    public static boolean valueIsLocalSymbolTable(_Private_IonValue v) {
        return v instanceof IonStruct && v.findTypeAnnotation("$ion_symbol_table") == 0;
    }

    public static final boolean symtabIsSharedNotSystem(SymbolTable symtab) {
        return symtab != null && symtab.isSharedTable() && !symtab.isSystemTable();
    }

    public static boolean symtabIsLocalAndNonTrivial(SymbolTable symtab) {
        if (symtab == null) {
            return false;
        }
        if (!symtab.isLocalTable()) {
            return false;
        }
        if (symtab.getImportedTables().length > 0) {
            return true;
        }
        return symtab.getImportedMaxId() < symtab.getMaxId();
    }

    public static boolean isTrivialTable(SymbolTable table2) {
        if (table2 == null) {
            return true;
        }
        if (table2.isSystemTable()) {
            return true;
        }
        return table2.isLocalTable() && table2.getMaxId() == table2.getSystemSymbolTable().getMaxId();
    }

    public static SymbolTable systemSymtab(int version) {
        return SharedSymbolTable.getSystemSymbolTable(version);
    }

    public static SymbolTable newSharedSymtab(IonStruct ionRep) {
        return SharedSymbolTable.newSharedSymbolTable(ionRep);
    }

    public static SymbolTable newSharedSymtab(IonReader reader, boolean alreadyInStruct) {
        return SharedSymbolTable.newSharedSymbolTable(reader, alreadyInStruct);
    }

    public static SymbolTable newSharedSymtab(String name, int version, SymbolTable priorSymtab, Iterator<String> symbols) {
        return SharedSymbolTable.newSharedSymbolTable(name, version, priorSymtab, symbols);
    }

    public static SymbolTable copyLocalSymbolTable(SymbolTable symtab) throws SubstituteSymbolTableException {
        if (!symtab.isLocalTable()) {
            String message = "symtab should be a local symtab";
            throw new IllegalArgumentException(message);
        }
        SymbolTable[] imports = ((_Private_LocalSymbolTable)symtab).getImportedTablesNoCopy();
        for (int i = 0; i < imports.length; ++i) {
            if (!imports[i].isSubstitute()) continue;
            String message = "local symtabs with substituted symtabs for imports (indicating no exact match within the catalog) cannot be copied";
            throw new SubstituteSymbolTableException(message);
        }
        return ((_Private_LocalSymbolTable)symtab).makeCopy();
    }

    @Deprecated
    public static _Private_LocalSymbolTableFactory newLocalSymbolTableAsStructFactory(ValueFactory imageFactory) {
        return new LocalSymbolTableAsStruct.Factory(imageFactory);
    }

    public static SymbolTable initialSymtab(_Private_LocalSymbolTableFactory lstFactory, SymbolTable defaultSystemSymtab, SymbolTable ... imports) {
        if (imports == null || imports.length == 0) {
            return defaultSystemSymtab;
        }
        if (imports.length == 1 && imports[0].isSystemTable()) {
            return imports[0];
        }
        return lstFactory.newLocalSymtab(defaultSystemSymtab, imports);
    }

    public static IonStruct symtabTree(SymbolTable symtab, ValueFactory valueFactory) {
        SymbolTableAsStruct localSymbolTableAsStruct;
        if (symtab instanceof SymbolTableAsStruct) {
            localSymbolTableAsStruct = (SymbolTableAsStruct)((Object)symtab);
        } else {
            LocalSymbolTableAsStruct table2 = (LocalSymbolTableAsStruct)new LocalSymbolTableAsStruct.Factory(valueFactory).newLocalSymtab(symtab.getSystemSymbolTable(), symtab.getImportedTables());
            Iterator<String> localSymbolsIterator = symtab.iterateDeclaredSymbolNames();
            while (localSymbolsIterator.hasNext()) {
                String localSymbol = localSymbolsIterator.next();
                if (localSymbol == null) continue;
                table2.intern(localSymbol);
            }
            localSymbolTableAsStruct = table2;
        }
        return localSymbolTableAsStruct.getIonRepresentation(valueFactory);
    }

    private static boolean localSymtabExtends(SymbolTable superset, SymbolTable subset) {
        SymbolTable[] subsetImports;
        if (subset.getMaxId() > superset.getMaxId()) {
            return false;
        }
        SymbolTable[] supersetImports = superset.getImportedTables();
        if (supersetImports.length != (subsetImports = subset.getImportedTables()).length) {
            return false;
        }
        for (int i = 0; i < supersetImports.length; ++i) {
            SymbolTable supersetImport = supersetImports[i];
            SymbolTable subsetImport = subsetImports[i];
            if (supersetImport.getName().equals(subsetImport.getName()) && supersetImport.getVersion() == subsetImport.getVersion()) continue;
            return false;
        }
        Iterator<String> supersetIter = superset.iterateDeclaredSymbolNames();
        Iterator<String> subsetIter = subset.iterateDeclaredSymbolNames();
        while (subsetIter.hasNext()) {
            String nextSupersetSymbol;
            String nextSubsetSymbol = subsetIter.next();
            if (nextSubsetSymbol.equals(nextSupersetSymbol = supersetIter.next())) continue;
            return false;
        }
        return true;
    }

    public static boolean symtabExtends(SymbolTable superset, SymbolTable subset) {
        assert (superset.isSystemTable() || superset.isLocalTable());
        assert (subset.isSystemTable() || subset.isLocalTable());
        if (superset == subset) {
            return true;
        }
        if (subset.isSystemTable()) {
            return true;
        }
        if (superset.isLocalTable()) {
            if (superset instanceof LocalSymbolTable && subset instanceof LocalSymbolTable) {
                return ((LocalSymbolTable)superset).symtabExtends(subset);
            }
            return _Private_Utils.localSymtabExtends(superset, subset);
        }
        return subset.getMaxId() == superset.getMaxId();
    }

    public static boolean isNonSymbolScalar(IonType type) {
        return !IonType.isContainer(type) && !type.equals((Object)IonType.SYMBOL);
    }

    public static final int getSidForSymbolTableField(String text) {
        int shortestFieldNameLength = 4;
        if (text != null && text.length() >= 4) {
            char c = text.charAt(0);
            switch (c) {
                case 'v': {
                    if (!"version".equals(text)) break;
                    return 5;
                }
                case 'n': {
                    if (!"name".equals(text)) break;
                    return 4;
                }
                case 's': {
                    if (!"symbols".equals(text)) break;
                    return 7;
                }
                case 'i': {
                    if (!"imports".equals(text)) break;
                    return 6;
                }
                case 'm': {
                    if (!"max_id".equals(text)) break;
                    return 8;
                }
            }
        }
        return -1;
    }

    public static final Iterator<String> stringIterator(String ... values2) {
        if (values2 == null || values2.length == 0) {
            return _Private_Utils.emptyIterator();
        }
        return new StringIterator(values2, values2.length);
    }

    public static final Iterator<String> stringIterator(String[] values2, int len) {
        if (values2 == null || values2.length == 0 || len == 0) {
            return _Private_Utils.emptyIterator();
        }
        return new StringIterator(values2, len);
    }

    public static final Iterator<Integer> intIterator(int ... values2) {
        if (values2 == null || values2.length == 0) {
            return _Private_Utils.emptyIterator();
        }
        return new IntIterator(values2);
    }

    public static final Iterator<Integer> intIterator(int[] values2, int len) {
        if (values2 == null || values2.length == 0 || len == 0) {
            return _Private_Utils.emptyIterator();
        }
        return new IntIterator(values2, 0, len);
    }

    public static void writeAsBase64(InputStream byteStream, Appendable out) throws IOException {
        int c;
        Base64Encoder.TextStream ts = new Base64Encoder.TextStream(byteStream);
        while ((c = ts.read()) != -1) {
            out.append((char)c);
        }
    }

    public static SymbolTable newSubstituteSymtab(SymbolTable original, int version, int maxId) {
        return new SubstituteSymbolTable(original, version, maxId);
    }

    private static final class IntIterator
    implements Iterator<Integer> {
        private final int[] _values;
        private int _pos;
        private final int _len;

        IntIterator(int[] values2) {
            this(values2, 0, values2.length);
        }

        IntIterator(int[] values2, int off, int len) {
            this._values = values2;
            this._len = len;
            this._pos = off;
        }

        @Override
        public boolean hasNext() {
            return this._pos < this._len;
        }

        @Override
        public Integer next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            int value = this._values[this._pos++];
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class StringIterator
    implements Iterator<String> {
        private final String[] _values;
        private int _pos;
        private final int _len;

        StringIterator(String[] values2, int len) {
            this._values = values2;
            this._len = len;
        }

        @Override
        public boolean hasNext() {
            return this._pos < this._len;
        }

        @Override
        public String next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this._values[this._pos++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

