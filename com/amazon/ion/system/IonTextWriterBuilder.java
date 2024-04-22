/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.system;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl._Private_IonTextWriterBuilder;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.system.IonWriterBuilder;
import com.amazon.ion.system.IonWriterBuilderBase;
import java.nio.charset.Charset;

public abstract class IonTextWriterBuilder
extends IonWriterBuilderBase<IonTextWriterBuilder> {
    public static final Charset ASCII = _Private_Utils.ASCII_CHARSET;
    public static final Charset UTF8 = _Private_Utils.UTF8_CHARSET;
    private Charset myCharset;
    private IonWriterBuilder.InitialIvmHandling myInitialIvmHandling;
    private IonWriterBuilder.IvmMinimizing myIvmMinimizing;
    private LstMinimizing myLstMinimizing;
    private int myLongStringThreshold;
    private NewLineType myNewLineType;
    private boolean myTopLevelValuesOnNewLines;

    public static IonTextWriterBuilder standard() {
        return _Private_IonTextWriterBuilder.standard();
    }

    public static IonTextWriterBuilder minimal() {
        return IonTextWriterBuilder.standard().withMinimalSystemData();
    }

    public static IonTextWriterBuilder pretty() {
        return IonTextWriterBuilder.standard().withPrettyPrinting();
    }

    public static IonTextWriterBuilder json() {
        return IonTextWriterBuilder.standard().withJsonDowngrade();
    }

    protected IonTextWriterBuilder() {
    }

    protected IonTextWriterBuilder(IonTextWriterBuilder that) {
        super(that);
        this.myCharset = that.myCharset;
        this.myInitialIvmHandling = that.myInitialIvmHandling;
        this.myIvmMinimizing = that.myIvmMinimizing;
        this.myLstMinimizing = that.myLstMinimizing;
        this.myLongStringThreshold = that.myLongStringThreshold;
        this.myNewLineType = that.myNewLineType;
        this.myTopLevelValuesOnNewLines = that.myTopLevelValuesOnNewLines;
    }

    @Override
    public abstract IonTextWriterBuilder copy();

    @Override
    public abstract IonTextWriterBuilder immutable();

    @Override
    public abstract IonTextWriterBuilder mutable();

    @Override
    public final IonTextWriterBuilder withCatalog(IonCatalog catalog) {
        return (IonTextWriterBuilder)super.withCatalog(catalog);
    }

    @Override
    public final IonTextWriterBuilder withImports(SymbolTable ... imports) {
        return (IonTextWriterBuilder)super.withImports(imports);
    }

    public final Charset getCharset() {
        return this.myCharset;
    }

    public void setCharset(Charset charset) {
        this.mutationCheck();
        if (charset != null && !charset.equals(ASCII) && !charset.equals(UTF8)) {
            throw new IllegalArgumentException("Unsupported Charset " + charset);
        }
        this.myCharset = charset;
    }

    public final IonTextWriterBuilder withCharset(Charset charset) {
        IonTextWriterBuilder b = this.mutable();
        b.setCharset(charset);
        return b;
    }

    public final IonTextWriterBuilder withCharsetAscii() {
        return this.withCharset(ASCII);
    }

    public final IonTextWriterBuilder withMinimalSystemData() {
        IonTextWriterBuilder b = this.mutable();
        b.setInitialIvmHandling(IonWriterBuilder.InitialIvmHandling.SUPPRESS);
        b.setIvmMinimizing(IonWriterBuilder.IvmMinimizing.DISTANT);
        b.setLstMinimizing(LstMinimizing.EVERYTHING);
        return b;
    }

    public abstract IonTextWriterBuilder withPrettyPrinting();

    public abstract IonTextWriterBuilder withJsonDowngrade();

    @Override
    public final IonWriterBuilder.InitialIvmHandling getInitialIvmHandling() {
        return this.myInitialIvmHandling;
    }

    public void setInitialIvmHandling(IonWriterBuilder.InitialIvmHandling handling) {
        this.mutationCheck();
        this.myInitialIvmHandling = handling;
    }

    public final IonTextWriterBuilder withInitialIvmHandling(IonWriterBuilder.InitialIvmHandling handling) {
        IonTextWriterBuilder b = this.mutable();
        b.setInitialIvmHandling(handling);
        return b;
    }

    @Override
    public final IonWriterBuilder.IvmMinimizing getIvmMinimizing() {
        return this.myIvmMinimizing;
    }

    public void setIvmMinimizing(IonWriterBuilder.IvmMinimizing minimizing) {
        this.mutationCheck();
        this.myIvmMinimizing = minimizing;
    }

    public final IonTextWriterBuilder withIvmMinimizing(IonWriterBuilder.IvmMinimizing minimizing) {
        IonTextWriterBuilder b = this.mutable();
        b.setIvmMinimizing(minimizing);
        return b;
    }

    public final LstMinimizing getLstMinimizing() {
        return this.myLstMinimizing;
    }

    public void setLstMinimizing(LstMinimizing minimizing) {
        this.mutationCheck();
        this.myLstMinimizing = minimizing;
    }

    public final IonTextWriterBuilder withLstMinimizing(LstMinimizing minimizing) {
        IonTextWriterBuilder b = this.mutable();
        b.setLstMinimizing(minimizing);
        return b;
    }

    public final int getLongStringThreshold() {
        return this.myLongStringThreshold;
    }

    public void setLongStringThreshold(int threshold) {
        this.mutationCheck();
        this.myLongStringThreshold = threshold;
    }

    public final IonTextWriterBuilder withLongStringThreshold(int threshold) {
        IonTextWriterBuilder b = this.mutable();
        b.setLongStringThreshold(threshold);
        return b;
    }

    public final NewLineType getNewLineType() {
        return this.myNewLineType;
    }

    public void setNewLineType(NewLineType newLineType) {
        this.mutationCheck();
        this.myNewLineType = newLineType;
    }

    public final IonTextWriterBuilder withNewLineType(NewLineType newLineType) {
        IonTextWriterBuilder b = this.mutable();
        b.setNewLineType(newLineType);
        return b;
    }

    public final boolean getWriteTopLevelValuesOnNewLines() {
        return this.myTopLevelValuesOnNewLines;
    }

    public void setWriteTopLevelValuesOnNewLines(boolean writeTopLevelValuesOnNewLines) {
        this.mutationCheck();
        this.myTopLevelValuesOnNewLines = writeTopLevelValuesOnNewLines;
    }

    public final IonTextWriterBuilder withWriteTopLevelValuesOnNewLines(boolean writeTopLevelValuesOnNewLines) {
        IonTextWriterBuilder b = this.mutable();
        b.setWriteTopLevelValuesOnNewLines(writeTopLevelValuesOnNewLines);
        return b;
    }

    public abstract IonWriter build(Appendable var1);

    public static enum NewLineType {
        CRLF("\r\n"),
        LF("\n"),
        PLATFORM_DEPENDENT(System.getProperty("line.separator"));

        private final CharSequence charSequence;

        private NewLineType(CharSequence cs) {
            this.charSequence = cs;
        }

        public CharSequence getCharSequence() {
            return this.charSequence;
        }
    }

    public static enum LstMinimizing {
        LOCALS,
        EVERYTHING;

    }
}

