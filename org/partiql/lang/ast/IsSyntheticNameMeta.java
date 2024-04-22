/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.InternalMeta;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2={"Lorg/partiql/lang/ast/IsSyntheticNameMeta;", "Lorg/partiql/lang/ast/InternalMeta;", "()V", "Companion", "lang"})
public final class IsSyntheticNameMeta
extends InternalMeta {
    @NotNull
    public static final String TAG = "$is_synthetic_name";
    @NotNull
    private static final IsSyntheticNameMeta instance;
    public static final Companion Companion;

    private IsSyntheticNameMeta() {
        super(TAG);
    }

    static {
        Companion = new Companion(null);
        instance = new IsSyntheticNameMeta();
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/ast/IsSyntheticNameMeta$Companion;", "", "()V", "TAG", "", "instance", "Lorg/partiql/lang/ast/IsSyntheticNameMeta;", "getInstance", "()Lorg/partiql/lang/ast/IsSyntheticNameMeta;", "lang"})
    public static final class Companion {
        @NotNull
        public final IsSyntheticNameMeta getInstance() {
            return instance;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

