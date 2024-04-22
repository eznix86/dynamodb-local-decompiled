/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.util;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonBlob;
import com.amazon.ion.IonBool;
import com.amazon.ion.IonClob;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonList;
import com.amazon.ion.IonNull;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonString;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl._Private_IonSymbol;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.util.AbstractValueVisitor;
import com.amazon.ion.util.IonTextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Iterator;

@Deprecated
public class Printer {
    protected Options myOptions = new Options();

    public Printer() {
        this.myOptions = new Options();
    }

    public Printer(Options options) {
        this.myOptions = options.clone();
    }

    public synchronized boolean getSkipSystemValues() {
        return this.myOptions.skipSystemValues;
    }

    public synchronized void setSkipSystemValues(boolean skip) {
        this.myOptions.skipSystemValues = skip;
    }

    public synchronized boolean getSkipAnnotations() {
        return this.myOptions.skipAnnotations;
    }

    public synchronized void setSkipAnnotations(boolean skip) {
        this.myOptions.skipAnnotations = skip;
    }

    public synchronized boolean getPrintBlobAsString() {
        return this.myOptions.blobAsString;
    }

    public synchronized void setPrintBlobAsString(boolean blobAsString) {
        this.myOptions.blobAsString = blobAsString;
    }

    public synchronized boolean getPrintClobAsString() {
        return this.myOptions.clobAsString;
    }

    public synchronized void setPrintClobAsString(boolean clobAsString) {
        this.myOptions.clobAsString = clobAsString;
    }

    public synchronized boolean getPrintDatagramAsList() {
        return this.myOptions.datagramAsList;
    }

    public synchronized void setPrintDatagramAsList(boolean datagramAsList) {
        this.myOptions.datagramAsList = datagramAsList;
    }

    public synchronized boolean getPrintDecimalAsFloat() {
        return this.myOptions.decimalAsFloat;
    }

    public synchronized void setPrintDecimalAsFloat(boolean decimalAsFloat) {
        this.myOptions.decimalAsFloat = decimalAsFloat;
    }

    public synchronized boolean getPrintSexpAsList() {
        return this.myOptions.sexpAsList;
    }

    public synchronized void setPrintSexpAsList(boolean sexpAsList) {
        this.myOptions.sexpAsList = sexpAsList;
    }

    public synchronized boolean getPrintStringAsJson() {
        return this.myOptions.stringAsJson;
    }

    public synchronized void setPrintStringAsJson(boolean stringAsJson) {
        this.myOptions.stringAsJson = stringAsJson;
    }

    public synchronized boolean getPrintSymbolAsString() {
        return this.myOptions.symbolAsString;
    }

    public synchronized void setPrintSymbolAsString(boolean symbolAsString) {
        this.myOptions.symbolAsString = symbolAsString;
    }

    public synchronized boolean getPrintTimestampAsMillis() {
        return this.myOptions.timestampAsMillis;
    }

    public synchronized void setPrintTimestampAsMillis(boolean timestampAsMillis) {
        this.myOptions.timestampAsMillis = timestampAsMillis;
    }

    public synchronized boolean getPrintTimestampAsString() {
        return this.myOptions.timestampAsString;
    }

    public synchronized void setPrintTimestampAsString(boolean timestampAsString) {
        this.myOptions.timestampAsString = timestampAsString;
    }

    public synchronized boolean getPrintUntypedNulls() {
        return this.myOptions.untypedNulls;
    }

    public synchronized void setPrintUntypedNulls(boolean untypedNulls) {
        this.myOptions.untypedNulls = untypedNulls;
    }

    public synchronized void setJsonMode() {
        this.myOptions.blobAsString = true;
        this.myOptions.clobAsString = true;
        this.myOptions.datagramAsList = true;
        this.myOptions.decimalAsFloat = true;
        this.myOptions.sexpAsList = true;
        this.myOptions.skipAnnotations = true;
        this.myOptions.skipSystemValues = true;
        this.myOptions.stringAsJson = true;
        this.myOptions.symbolAsString = true;
        this.myOptions.timestampAsString = false;
        this.myOptions.timestampAsMillis = true;
        this.myOptions.untypedNulls = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void print(IonValue value, Appendable out) throws IOException {
        Options options;
        Printer printer = this;
        synchronized (printer) {
            options = this.myOptions.clone();
        }
        this._print(value, this.makeVisitor(options, out));
    }

    private void _print(IonValue value, PrinterVisitor pv) throws IOException {
        try {
            if (!(value instanceof IonDatagram)) {
                pv.setSymbolTableProvider(new BasicSymbolTableProvider(value.getSymbolTable()));
            }
            value.accept(pv);
        } catch (IOException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected PrinterVisitor makeVisitor(Options options, Appendable out) {
        return new PrinterVisitor(options, out);
    }

    public static class PrinterVisitor
    extends AbstractValueVisitor {
        protected final Options myOptions;
        protected final Appendable myOut;
        private boolean myQuoteOperators = true;
        private _Private_IonValue.SymbolTableProvider mySymbolTableProvider = null;

        public PrinterVisitor(Options options, Appendable out) {
            this.myOptions = options;
            this.myOut = out;
        }

        void setSymbolTableProvider(_Private_IonValue.SymbolTableProvider symbolTableProvider) {
            this.mySymbolTableProvider = symbolTableProvider;
        }

        protected void writeChild(IonValue value, boolean quoteOperators) throws Exception {
            boolean oldQuoteOperators = this.myQuoteOperators;
            this.myQuoteOperators = quoteOperators;
            value.accept(this);
            this.myQuoteOperators = oldQuoteOperators;
        }

        public void writeAnnotations(IonValue value) throws IOException {
            SymbolToken[] anns;
            if (!this.myOptions.skipAnnotations && (anns = ((_Private_IonValue)value).getTypeAnnotationSymbols(this.mySymbolTableProvider)) != null) {
                for (SymbolToken ann : anns) {
                    String text = ann.getText();
                    if (text == null) {
                        this.myOut.append('$');
                        this.myOut.append(Integer.toString(ann.getSid()));
                    } else {
                        IonTextUtils.printSymbol(this.myOut, text);
                    }
                    this.myOut.append("::");
                }
            }
        }

        public void writeNull(String type) throws IOException {
            if (this.myOptions.untypedNulls) {
                this.myOut.append("null");
            } else {
                this.myOut.append("null.");
                this.myOut.append(type);
            }
        }

        public void writeSequenceContent(IonSequence value, boolean quoteOperators, char open, char separator, char close) throws IOException, Exception {
            this.myOut.append(open);
            boolean hitOne = false;
            for (IonValue child2 : value) {
                if (hitOne) {
                    this.myOut.append(separator);
                }
                hitOne = true;
                this.writeChild(child2, quoteOperators);
            }
            this.myOut.append(close);
        }

        public void writeSymbolToken(SymbolToken sym) throws IOException {
            String text = sym.getText();
            if (text != null) {
                this.writeSymbol(text);
            } else {
                int sid = sym.getSid();
                if (sid < 0) {
                    throw new IllegalArgumentException("Bad SID " + sid);
                }
                text = "$" + sym.getSid();
                if (this.myOptions.symbolAsString) {
                    this.writeString(text);
                } else {
                    this.myOut.append(text);
                }
            }
        }

        public void writeSymbol(String text) throws IOException {
            if (this.myOptions.symbolAsString) {
                this.writeString(text);
            } else {
                IonTextUtils.SymbolVariant variant = IonTextUtils.symbolVariant(text);
                switch (variant) {
                    case IDENTIFIER: {
                        this.myOut.append(text);
                        break;
                    }
                    case OPERATOR: {
                        if (!this.myQuoteOperators) {
                            this.myOut.append(text);
                            break;
                        }
                    }
                    case QUOTED: {
                        IonTextUtils.printQuotedSymbol(this.myOut, text);
                    }
                }
            }
        }

        public void writeString(String text) throws IOException {
            if (this.myOptions.stringAsJson) {
                IonTextUtils.printJsonString(this.myOut, text);
            } else {
                IonTextUtils.printString(this.myOut, text);
            }
        }

        @Override
        protected void defaultVisit(IonValue value) {
            String message = "cannot print " + value.getClass().getName();
            throw new UnsupportedOperationException(message);
        }

        @Override
        public void visit(IonBlob value) throws IOException {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("blob");
            } else {
                this.myOut.append(this.myOptions.blobAsString ? "\"" : "{{");
                value.printBase64(this.myOut);
                this.myOut.append(this.myOptions.blobAsString ? "\"" : "}}");
            }
        }

        @Override
        public void visit(IonBool value) throws IOException {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("bool");
            } else {
                this.myOut.append(value.booleanValue() ? "true" : "false");
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void visit(IonClob value) throws IOException {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("clob");
            } else {
                if (!this.myOptions.clobAsString) {
                    this.myOut.append("{{");
                }
                this.myOut.append('\"');
                try (InputStream byteStream = value.newInputStream();){
                    if (this.myOptions.stringAsJson) {
                        int c;
                        while ((c = byteStream.read()) != -1) {
                            IonTextUtils.printJsonCodePoint(this.myOut, c);
                        }
                    } else {
                        int c;
                        while ((c = byteStream.read()) != -1) {
                            IonTextUtils.printStringCodePoint(this.myOut, c);
                        }
                    }
                }
                this.myOut.append('\"');
                if (!this.myOptions.clobAsString) {
                    this.myOut.append("}}");
                }
            }
        }

        @Override
        public void visit(IonDatagram value) throws IOException, Exception {
            Iterator<IonValue> i = this.myOptions.skipSystemValues ? value.iterator() : value.systemIterator();
            boolean asList2 = this.myOptions.datagramAsList;
            if (asList2) {
                this.myOut.append('[');
            }
            boolean hitOne = false;
            boolean simplify_system_values = this.myOptions.simplifySystemValues && !this.myOptions.skipSystemValues;
            SymbolTable previous_symbols = null;
            while (i.hasNext()) {
                IonValue child2 = i.next();
                SymbolTable childSymbolTable = child2.getSymbolTable();
                this.mySymbolTableProvider = new BasicSymbolTableProvider(childSymbolTable);
                if (simplify_system_values) {
                    child2 = this.simplify(child2, previous_symbols);
                    previous_symbols = childSymbolTable;
                }
                if (child2 == null) continue;
                if (hitOne) {
                    this.myOut.append(asList2 ? (char)',' : ' ');
                }
                this.writeChild(child2, true);
                hitOne = true;
            }
            if (asList2) {
                this.myOut.append(']');
            }
        }

        private final IonValue simplify(IonValue child2, SymbolTable previous_symbols) {
            IonType t = child2.getType();
            switch (t) {
                case STRUCT: {
                    if (!child2.hasTypeAnnotation("$ion_symbol_table")) break;
                    if (PrinterVisitor.symbol_table_struct_has_imports(child2)) {
                        return ((IonStruct)child2).cloneAndRemove("symbols");
                    }
                    return null;
                }
                case SYMBOL: {
                    if (((IonSymbol)child2).getSymbolId() != 2 || previous_symbols == null || !previous_symbols.isSystemTable()) break;
                    return null;
                }
            }
            return child2;
        }

        private static final boolean symbol_table_struct_has_imports(IonValue child2) {
            IonStruct struct = (IonStruct)child2;
            IonValue imports = struct.get("imports");
            if (imports instanceof IonList) {
                return ((IonList)imports).size() != 0;
            }
            return false;
        }

        @Override
        public void visit(IonDecimal value) throws IOException {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("decimal");
            } else {
                Decimal decimal = value.decimalValue();
                BigInteger unscaled = decimal.unscaledValue();
                int signum = decimal.signum();
                if (signum < 0) {
                    this.myOut.append('-');
                    unscaled = unscaled.negate();
                } else if (signum == 0 && decimal.isNegativeZero()) {
                    this.myOut.append('-');
                }
                String unscaledText = unscaled.toString();
                int significantDigits = unscaledText.length();
                int scale = decimal.scale();
                int exponent = -scale;
                if (this.myOptions.decimalAsFloat) {
                    this.myOut.append(unscaledText);
                    this.myOut.append('e');
                    this.myOut.append(Integer.toString(exponent));
                } else if (exponent == 0) {
                    this.myOut.append(unscaledText);
                    this.myOut.append('.');
                } else if (0 < scale) {
                    int remainingScale;
                    int wholeDigits;
                    if (significantDigits > scale) {
                        wholeDigits = significantDigits - scale;
                        remainingScale = 0;
                    } else {
                        wholeDigits = 1;
                        remainingScale = scale - significantDigits + 1;
                    }
                    this.myOut.append(unscaledText, 0, wholeDigits);
                    if (wholeDigits < significantDigits) {
                        this.myOut.append('.');
                        this.myOut.append(unscaledText, wholeDigits, significantDigits);
                    }
                    if (remainingScale != 0) {
                        this.myOut.append("d-");
                        this.myOut.append(Integer.toString(remainingScale));
                    }
                } else {
                    this.myOut.append(unscaledText);
                    this.myOut.append('d');
                    this.myOut.append(Integer.toString(exponent));
                }
            }
        }

        @Override
        public void visit(IonFloat value) throws IOException {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("float");
            } else {
                double real = value.doubleValue();
                IonTextUtils.printFloat(this.myOut, real);
            }
        }

        @Override
        public void visit(IonInt value) throws IOException {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("int");
            } else {
                this.myOut.append(value.bigIntegerValue().toString(10));
            }
        }

        @Override
        public void visit(IonList value) throws IOException, Exception {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("list");
            } else {
                this.writeSequenceContent(value, true, '[', ',', ']');
            }
        }

        @Override
        public void visit(IonNull value) throws IOException {
            this.writeAnnotations(value);
            this.myOut.append("null");
        }

        @Override
        public void visit(IonSexp value) throws IOException, Exception {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("sexp");
            } else if (this.myOptions.sexpAsList) {
                this.writeSequenceContent(value, true, '[', ',', ']');
            } else {
                this.writeSequenceContent(value, false, '(', ' ', ')');
            }
        }

        @Override
        public void visit(IonString value) throws IOException {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("string");
            } else {
                this.writeString(value.stringValue());
            }
        }

        @Override
        public void visit(IonStruct value) throws IOException, Exception {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("struct");
            } else {
                this.myOut.append('{');
                boolean hitOne = false;
                for (IonValue child2 : value) {
                    if (hitOne) {
                        this.myOut.append(',');
                    }
                    hitOne = true;
                    SymbolToken sym = ((_Private_IonValue)child2).getFieldNameSymbol(this.mySymbolTableProvider);
                    this.writeSymbolToken(sym);
                    this.myOut.append(':');
                    this.writeChild(child2, true);
                }
                this.myOut.append('}');
            }
        }

        @Override
        public void visit(IonSymbol value) throws IOException {
            this.writeAnnotations(value);
            SymbolToken is = ((_Private_IonSymbol)value).symbolValue(this.mySymbolTableProvider);
            if (is == null) {
                this.writeNull("symbol");
            } else {
                this.writeSymbolToken(is);
            }
        }

        @Override
        public void visit(IonTimestamp value) throws IOException {
            this.writeAnnotations(value);
            if (value.isNullValue()) {
                this.writeNull("timestamp");
            } else if (this.myOptions.timestampAsMillis) {
                this.myOut.append(Long.toString(value.getMillis()));
            } else {
                Timestamp ts = value.timestampValue();
                if (this.myOptions.timestampAsString) {
                    this.myOut.append('\"');
                    ts.print(this.myOut);
                    this.myOut.append('\"');
                } else {
                    ts.print(this.myOut);
                }
            }
        }
    }

    private static class BasicSymbolTableProvider
    implements _Private_IonValue.SymbolTableProvider {
        private final SymbolTable symbolTable;

        public BasicSymbolTableProvider(SymbolTable symbolTable) {
            this.symbolTable = symbolTable;
        }

        @Override
        public SymbolTable getSymbolTable() {
            return this.symbolTable;
        }
    }

    public class Options
    implements Cloneable {
        public boolean blobAsString;
        public boolean clobAsString;
        public boolean datagramAsList;
        public boolean decimalAsFloat;
        public boolean sexpAsList;
        public boolean skipAnnotations;
        public boolean skipSystemValues;
        public boolean simplifySystemValues;
        public boolean stringAsJson;
        public boolean symbolAsString;
        public boolean timestampAsString;
        public boolean timestampAsMillis;
        public boolean untypedNulls;

        public Options clone() {
            try {
                return (Options)super.clone();
            } catch (CloneNotSupportedException e) {
                throw new InternalError();
            }
        }
    }
}

