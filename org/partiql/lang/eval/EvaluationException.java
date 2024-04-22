/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.SqlException;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.PropertyValueMap;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B/\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nB9\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\rR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0010"}, d2={"Lorg/partiql/lang/eval/EvaluationException;", "Lorg/partiql/lang/SqlException;", "cause", "", "errorCode", "Lorg/partiql/lang/errors/ErrorCode;", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "internal", "", "(Ljava/lang/Throwable;Lorg/partiql/lang/errors/ErrorCode;Lorg/partiql/lang/errors/PropertyValueMap;Z)V", "message", "", "(Ljava/lang/String;Lorg/partiql/lang/errors/ErrorCode;Lorg/partiql/lang/errors/PropertyValueMap;Ljava/lang/Throwable;Z)V", "getInternal", "()Z", "lang"})
public class EvaluationException
extends SqlException {
    private final boolean internal;

    public final boolean getInternal() {
        return this.internal;
    }

    public EvaluationException(@NotNull String message, @Nullable ErrorCode errorCode, @Nullable PropertyValueMap errorContext, @Nullable Throwable cause, boolean internal) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        super(message, errorCode, errorContext, cause);
        this.internal = internal;
    }

    public /* synthetic */ EvaluationException(String string, ErrorCode errorCode, PropertyValueMap propertyValueMap, Throwable throwable, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            errorCode = null;
        }
        if ((n & 4) != 0) {
            propertyValueMap = null;
        }
        if ((n & 8) != 0) {
            throwable = null;
        }
        this(string, errorCode, propertyValueMap, throwable, bl);
    }

    public EvaluationException(@NotNull Throwable cause, @Nullable ErrorCode errorCode, @Nullable PropertyValueMap errorContext, boolean internal) {
        Intrinsics.checkParameterIsNotNull(cause, "cause");
        String string = cause.getMessage();
        if (string == null) {
            string = "<NO MESSAGE>";
        }
        Throwable throwable = cause;
        boolean bl = internal;
        this(string, errorCode, errorContext, throwable, bl);
    }

    public /* synthetic */ EvaluationException(Throwable throwable, ErrorCode errorCode, PropertyValueMap propertyValueMap, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            errorCode = null;
        }
        if ((n & 4) != 0) {
            propertyValueMap = null;
        }
        this(throwable, errorCode, propertyValueMap, bl);
    }
}

