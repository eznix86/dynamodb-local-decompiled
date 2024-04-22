/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast.passes;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.SqlException;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.PropertyValueMap;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/ast/passes/SemanticException;", "Lorg/partiql/lang/SqlException;", "message", "", "errorCode", "Lorg/partiql/lang/errors/ErrorCode;", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "cause", "", "(Ljava/lang/String;Lorg/partiql/lang/errors/ErrorCode;Lorg/partiql/lang/errors/PropertyValueMap;Ljava/lang/Throwable;)V", "lang"})
public final class SemanticException
extends SqlException {
    public SemanticException(@NotNull String message, @NotNull ErrorCode errorCode, @Nullable PropertyValueMap errorContext, @Nullable Throwable cause) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
        super(message, errorCode, errorContext, cause);
    }

    public /* synthetic */ SemanticException(String string, ErrorCode errorCode, PropertyValueMap propertyValueMap, Throwable throwable, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = "";
        }
        if ((n & 8) != 0) {
            throwable = null;
        }
        this(string, errorCode, propertyValueMap, throwable);
    }
}

