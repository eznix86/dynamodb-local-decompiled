/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.StaticTypeMeta;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0005"}, d2={"staticType", "Lorg/partiql/lang/ast/StaticTypeMeta;", "Lorg/partiql/lang/ast/MetaContainer;", "getStaticType", "(Lorg/partiql/lang/ast/MetaContainer;)Lorg/partiql/lang/ast/StaticTypeMeta;", "lang"})
public final class StaticTypeMetaKt {
    @Nullable
    public static final StaticTypeMeta getStaticType(@NotNull MetaContainer $this$staticType) {
        Intrinsics.checkParameterIsNotNull($this$staticType, "$this$staticType");
        return (StaticTypeMeta)$this$staticType.find("$static_type");
    }
}

