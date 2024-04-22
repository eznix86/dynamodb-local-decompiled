/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValue;
import org.partiql.lang.errors.PropertyValueMap;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0010\b\u0016\u0018\u00002\u00060\u0001j\u0002`\u0002B#\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tB1\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\rJ\u0012\u0010\u0016\u001a\u00020\u000b2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0002J\u0012\u0010\u0017\u001a\u00020\u000b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u001c\u0010\u0018\u001a\u00020\u000b2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0006\u0010\u0019\u001a\u00020\u000bJ\b\u0010\u001a\u001a\u00020\u000bH\u0016R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\n\u001a\u00020\u000bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001b"}, d2={"Lorg/partiql/lang/SqlException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "errorCode", "Lorg/partiql/lang/errors/ErrorCode;", "propertyValueMap", "Lorg/partiql/lang/errors/PropertyValueMap;", "cause", "", "(Lorg/partiql/lang/errors/ErrorCode;Lorg/partiql/lang/errors/PropertyValueMap;Ljava/lang/Throwable;)V", "message", "", "errorContext", "(Ljava/lang/String;Lorg/partiql/lang/errors/ErrorCode;Lorg/partiql/lang/errors/PropertyValueMap;Ljava/lang/Throwable;)V", "getErrorCode", "()Lorg/partiql/lang/errors/ErrorCode;", "getErrorContext", "()Lorg/partiql/lang/errors/PropertyValueMap;", "getMessage", "()Ljava/lang/String;", "setMessage", "(Ljava/lang/String;)V", "errorCategory", "errorLocation", "errorMessage", "generateMessage", "toString", "lang"})
public class SqlException
extends RuntimeException {
    @NotNull
    private String message;
    @Nullable
    private final ErrorCode errorCode;
    @Nullable
    private final PropertyValueMap errorContext;

    @NotNull
    public final String generateMessage() {
        return this.errorCategory(this.errorCode) + ": " + this.errorLocation(this.errorContext) + ": " + this.errorMessage(this.errorCode, this.errorContext);
    }

    private final String errorMessage(ErrorCode errorCode, PropertyValueMap propertyValueMap) {
        Object object = errorCode;
        if (object == null || (object = object.getErrorMessage(propertyValueMap)) == null) {
            object = "<UNKNOWN>";
        }
        return object;
    }

    private final String errorLocation(PropertyValueMap propertyValueMap) {
        Object object = propertyValueMap;
        Long lineNo = object != null && (object = ((PropertyValueMap)object).get(Property.LINE_NUMBER)) != null ? Long.valueOf(((PropertyValue)object).longValue()) : null;
        Object object2 = propertyValueMap;
        Long columnNo = object2 != null && (object2 = ((PropertyValueMap)object2).get(Property.COLUMN_NUMBER)) != null ? Long.valueOf(((PropertyValue)object2).longValue()) : null;
        StringBuilder stringBuilder = new StringBuilder().append("at line ");
        Object object3 = lineNo;
        if (object3 == null) {
            object3 = "<UNKNOWN>";
        }
        StringBuilder stringBuilder2 = stringBuilder.append(object3).append(", column ");
        Object object4 = columnNo;
        if (object4 == null) {
            object4 = "<UNKNOWN>";
        }
        return stringBuilder2.append(object4).toString();
    }

    private final String errorCategory(ErrorCode errorCode) {
        Object object = errorCode;
        if (object == null || (object = object.errorCategory()) == null) {
            object = "<UNKNOWN>";
        }
        return object;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public String toString() {
        CharSequence charSequence;
        boolean bl;
        CharSequence charSequence2 = this.getMessage();
        boolean bl2 = false;
        boolean bl3 = bl = !StringsKt.isBlank(charSequence2);
        if (bl) {
            void var3_3;
            String msg = this.getMessage();
            this.setMessage(this.getMessage() + "\n\t" + this.generateMessage() + '\n');
            String result = super.toString();
            this.setMessage(msg);
            charSequence = var3_3;
        } else {
            this.setMessage(this.generateMessage() + '\n');
            String result = super.toString();
            this.setMessage("");
            charSequence = charSequence2;
        }
        return charSequence;
    }

    @Override
    @NotNull
    public String getMessage() {
        return this.message;
    }

    public void setMessage(@NotNull String string) {
        Intrinsics.checkParameterIsNotNull(string, "<set-?>");
        this.message = string;
    }

    @Nullable
    public final ErrorCode getErrorCode() {
        return this.errorCode;
    }

    @Nullable
    public final PropertyValueMap getErrorContext() {
        return this.errorContext;
    }

    public SqlException(@NotNull String message, @Nullable ErrorCode errorCode, @Nullable PropertyValueMap errorContext, @Nullable Throwable cause) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        super(message, cause);
        this.message = message;
        this.errorCode = errorCode;
        this.errorContext = errorContext;
    }

    public /* synthetic */ SqlException(String string, ErrorCode errorCode, PropertyValueMap propertyValueMap, Throwable throwable, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            errorCode = null;
        }
        if ((n & 4) != 0) {
            propertyValueMap = null;
        }
        if ((n & 8) != 0) {
            throwable = null;
        }
        this(string, errorCode, propertyValueMap, throwable);
    }

    public SqlException(@NotNull ErrorCode errorCode, @NotNull PropertyValueMap propertyValueMap, @Nullable Throwable cause) {
        Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
        Intrinsics.checkParameterIsNotNull(propertyValueMap, "propertyValueMap");
        this("", errorCode, propertyValueMap, cause);
    }

    public /* synthetic */ SqlException(ErrorCode errorCode, PropertyValueMap propertyValueMap, Throwable throwable, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            throwable = null;
        }
        this(errorCode, propertyValueMap, throwable);
    }
}

