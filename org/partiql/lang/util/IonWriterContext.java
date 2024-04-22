/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.util;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import java.io.Closeable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000eJ\u0016\u0010\r\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u000eJ'\u0010\u000f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b0\u0011\u00a2\u0006\u0002\b\u0012J\u001f\u0010\u000f\u001a\u00020\b2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b0\u0011\u00a2\u0006\u0002\b\u0012J\u0006\u0010\u0013\u001a\u00020\bJ\u000e\u0010\u0013\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u0014\u001a\u00020\bJ\u000e\u0010\u0014\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u0015\u001a\u00020\bJ\u000e\u0010\u0015\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u0016\u001a\u00020\bJ\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u0017\u001a\u00020\bJ\u000e\u0010\u0017\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u0018\u001a\u00020\bJ\u000e\u0010\u0018\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u0019\u001a\u00020\bJ\u000e\u0010\u0019\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u001a\u001a\u00020\bJ\u000e\u0010\u001a\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u001b\u001a\u00020\bJ\u000e\u0010\u001b\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u001c\u001a\u00020\bJ\u000e\u0010\u001c\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u001d\u001a\u00020\bJ\u000e\u0010\u001d\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u001e\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ'\u0010\u001f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b0\u0011\u00a2\u0006\u0002\b\u0012J\u001f\u0010\u001f\u001a\u00020\b2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b0\u0011\u00a2\u0006\u0002\b\u0012J\u000e\u0010 \u001a\u00020\b2\u0006\u0010!\u001a\u00020\fJ\u0016\u0010 \u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010!\u001a\u00020\fJ'\u0010\"\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b0\u0011\u00a2\u0006\u0002\b\u0012J\u001f\u0010\"\u001a\u00020\b2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b0\u0011\u00a2\u0006\u0002\b\u0012J\u0016\u0010#\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010!\u001a\u00020\fJ\u0010\u0010#\u001a\u00020\b2\b\u0010!\u001a\u0004\u0018\u00010\fJ\u0006\u0010$\u001a\u00020\bJ\u000e\u0010$\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\t\u001a\u00020\b2\u0006\u0010\t\u001a\u00020%J\u0016\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020%R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006&"}, d2={"Lorg/partiql/lang/util/IonWriterContext;", "", "writer", "Lcom/amazon/ion/IonWriter;", "(Lcom/amazon/ion/IonWriter;)V", "getWriter", "()Lcom/amazon/ion/IonWriter;", "bool", "", "value", "", "fieldName", "", "int", "", "list", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "nullBlob", "nullBool", "nullClob", "nullDecimal", "nullFloat", "nullInt", "nullList", "nullSexp", "nullString", "nullStruct", "nullSymbol", "setNextFieldName", "sexp", "string", "str", "struct", "symbol", "untypedNull", "Lcom/amazon/ion/IonValue;", "lang"})
public final class IonWriterContext {
    @NotNull
    private final IonWriter writer;

    public final void setNextFieldName(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        if (!this.writer.isInStruct()) {
            throw (Throwable)new IllegalStateException("Cannot set field name while not in a struct");
        }
        this.writer.setFieldName(fieldName);
    }

    public final void sexp(@NotNull Function1<? super IonWriterContext, Unit> block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        this.writer.stepIn(IonType.SEXP);
        block.invoke(this);
        this.writer.stepOut();
    }

    public final void sexp(@NotNull String fieldName, @NotNull Function1<? super IonWriterContext, Unit> block) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        Intrinsics.checkParameterIsNotNull(block, "block");
        this.setNextFieldName(fieldName);
        this.sexp(block);
    }

    public final void list(@NotNull Function1<? super IonWriterContext, Unit> block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        this.writer.stepIn(IonType.LIST);
        block.invoke(this);
        this.writer.stepOut();
    }

    public final void list(@NotNull String fieldName, @NotNull Function1<? super IonWriterContext, Unit> block) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        Intrinsics.checkParameterIsNotNull(block, "block");
        this.setNextFieldName(fieldName);
        this.list(block);
    }

    public final void struct(@NotNull Function1<? super IonWriterContext, Unit> block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        this.writer.stepIn(IonType.STRUCT);
        block.invoke(this);
        this.writer.stepOut();
    }

    public final void struct(@NotNull String fieldName, @NotNull Function1<? super IonWriterContext, Unit> block) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        Intrinsics.checkParameterIsNotNull(block, "block");
        this.setNextFieldName(fieldName);
        this.struct(block);
    }

    public final void string(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        this.writer.writeString(str);
    }

    public final void string(@NotNull String fieldName, @NotNull String str) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        Intrinsics.checkParameterIsNotNull(str, "str");
        this.setNextFieldName(fieldName);
        this.string(str);
    }

    public final void symbol(@Nullable String str) {
        this.writer.writeSymbol(str);
    }

    public final void symbol(@NotNull String fieldName, @NotNull String str) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        Intrinsics.checkParameterIsNotNull(str, "str");
        this.setNextFieldName(fieldName);
        this.symbol(str);
    }

    public final void int(long value) {
        this.writer.writeInt(value);
    }

    public final void int(@NotNull String fieldName, long value) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.int(value);
    }

    public final void bool(boolean value) {
        this.writer.writeBool(value);
    }

    public final void bool(@NotNull String fieldName, boolean value) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.bool(value);
    }

    public final void untypedNull() {
        this.writer.writeNull();
    }

    public final void untypedNull(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.writer.writeNull();
    }

    public final void nullBool() {
        this.writer.writeNull(IonType.BOOL);
    }

    public final void nullBool(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullBool();
    }

    public final void nullInt() {
        this.writer.writeNull(IonType.INT);
    }

    public final void nullInt(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullInt();
    }

    public final void nullFloat() {
        this.writer.writeNull(IonType.FLOAT);
    }

    public final void nullFloat(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullFloat();
    }

    public final void nullDecimal() {
        this.writer.writeNull(IonType.DECIMAL);
    }

    public final void nullDecimal(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullDecimal();
    }

    public final void nullSymbol() {
        this.writer.writeNull(IonType.SYMBOL);
    }

    public final void nullSymbol(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullSymbol();
    }

    public final void nullString() {
        this.writer.writeNull(IonType.STRING);
    }

    public final void nullString(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullString();
    }

    public final void nullClob() {
        this.writer.writeNull(IonType.CLOB);
    }

    public final void nullClob(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullClob();
    }

    public final void nullBlob() {
        this.writer.writeNull(IonType.BLOB);
    }

    public final void nullBlob(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullBlob();
    }

    public final void nullList() {
        this.writer.writeNull(IonType.LIST);
    }

    public final void nullList(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullList();
    }

    public final void nullSexp() {
        this.writer.writeNull(IonType.SEXP);
    }

    public final void nullSexp(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullSexp();
    }

    public final void nullStruct() {
        this.writer.writeNull(IonType.STRUCT);
    }

    public final void nullStruct(@NotNull String fieldName) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        this.setNextFieldName(fieldName);
        this.nullStruct();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void value(@NotNull IonValue value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        Closeable closeable = value.getSystem().newReader(value);
        boolean bl = false;
        Throwable throwable = null;
        try {
            IonReader it = (IonReader)closeable;
            boolean bl2 = false;
            it.next();
            this.writer.writeValue(it);
            Unit unit = Unit.INSTANCE;
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    public final void value(@NotNull String fieldName, @NotNull IonValue value) {
        Intrinsics.checkParameterIsNotNull(fieldName, "fieldName");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.setNextFieldName(fieldName);
        this.value(value);
    }

    @NotNull
    public final IonWriter getWriter() {
        return this.writer;
    }

    public IonWriterContext(@NotNull IonWriter writer) {
        Intrinsics.checkParameterIsNotNull(writer, "writer");
        this.writer = writer;
    }
}

