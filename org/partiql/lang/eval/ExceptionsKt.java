/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.EvaluationException;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u00004\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0000\u001a\u0014\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0000\u001a\u0018\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0000\u001a\u0010\u0010\f\u001a\u00020\u00072\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u001a\u0010\u0010\f\u001a\u00020\u00072\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u001a\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e\u001a\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a8\u0006\u0013"}, d2={"err", "", "message", "", "errorCode", "Lorg/partiql/lang/errors/ErrorCode;", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "internal", "", "errIntOverflow", "errNoContext", "errorContextFrom", "metaContainer", "Lorg/partiql/lang/ast/MetaContainer;", "location", "Lorg/partiql/lang/ast/SourceLocationMeta;", "fillErrorContext", "", "lang"})
public final class ExceptionsKt {
    @NotNull
    public static final Void errNoContext(@NotNull String message, boolean internal) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        Void void_ = ExceptionsKt.err(message, null, internal);
        throw null;
    }

    @NotNull
    public static final Void err(@NotNull String message, @Nullable PropertyValueMap errorContext, boolean internal) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        throw (Throwable)new EvaluationException(message, null, errorContext, null, internal, 10, null);
    }

    @NotNull
    public static final Void err(@NotNull String message, @NotNull ErrorCode errorCode, @Nullable PropertyValueMap errorContext, boolean internal) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
        throw (Throwable)new EvaluationException(message, errorCode, errorContext, null, internal, 8, null);
    }

    @NotNull
    public static final Void errIntOverflow(@Nullable PropertyValueMap errorContext) {
        throw (Throwable)new EvaluationException("Int overflow or underflow", ErrorCode.EVALUATOR_INTEGER_OVERFLOW, errorContext, null, false, 8, null);
    }

    public static /* synthetic */ Void errIntOverflow$default(PropertyValueMap propertyValueMap, int n, Object object) {
        if ((n & 1) != 0) {
            propertyValueMap = null;
        }
        return ExceptionsKt.errIntOverflow(propertyValueMap);
    }

    @NotNull
    public static final PropertyValueMap errorContextFrom(@Nullable SourceLocationMeta location) {
        PropertyValueMap errorContext = new PropertyValueMap(null, 1, null);
        if (location != null) {
            ExceptionsKt.fillErrorContext(errorContext, location);
        }
        return errorContext;
    }

    public static final void fillErrorContext(@NotNull PropertyValueMap errorContext, @Nullable SourceLocationMeta location) {
        Intrinsics.checkParameterIsNotNull(errorContext, "errorContext");
        if (location != null) {
            errorContext.set(Property.LINE_NUMBER, location.getLineNum());
            errorContext.set(Property.COLUMN_NUMBER, location.getCharOffset());
        }
    }

    public static final void fillErrorContext(@NotNull PropertyValueMap errorContext, @NotNull MetaContainer metaContainer) {
        SourceLocationMeta location;
        Intrinsics.checkParameterIsNotNull(errorContext, "errorContext");
        Intrinsics.checkParameterIsNotNull(metaContainer, "metaContainer");
        Meta meta = metaContainer.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        if ((location = (SourceLocationMeta)meta) != null) {
            ExceptionsKt.fillErrorContext(errorContext, location);
        }
    }

    @NotNull
    public static final PropertyValueMap errorContextFrom(@Nullable MetaContainer metaContainer) {
        SourceLocationMeta location;
        if (metaContainer == null) {
            return new PropertyValueMap(null, 1, null);
        }
        Meta meta = metaContainer.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        return (location = (SourceLocationMeta)meta) != null ? ExceptionsKt.errorContextFrom(location) : new PropertyValueMap(null, 1, null);
    }
}

