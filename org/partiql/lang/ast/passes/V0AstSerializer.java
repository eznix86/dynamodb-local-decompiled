/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast.passes;

import com.amazon.ion.IonSexp;
import com.amazon.ion.IonSystem;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.AstSerializer;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.ExprNode;

@Deprecated(message="Please use PartiqlAst class instead")
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2={"Lorg/partiql/lang/ast/passes/V0AstSerializer;", "", "()V", "Companion", "lang"})
public final class V0AstSerializer {
    public static final Companion Companion = new Companion(null);

    @JvmStatic
    @NotNull
    public static final IonSexp serialize(@NotNull ExprNode expr, @NotNull IonSystem ion) {
        return Companion.serialize(expr, ion);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/ast/passes/V0AstSerializer$Companion;", "", "()V", "serialize", "Lcom/amazon/ion/IonSexp;", "expr", "Lorg/partiql/lang/ast/ExprNode;", "ion", "Lcom/amazon/ion/IonSystem;", "lang"})
    public static final class Companion {
        @JvmStatic
        @NotNull
        public final IonSexp serialize(@NotNull ExprNode expr, @NotNull IonSystem ion) {
            Intrinsics.checkParameterIsNotNull(expr, "expr");
            Intrinsics.checkParameterIsNotNull(ion, "ion");
            return AstSerializer.Companion.serialize(expr, AstVersion.V0, ion);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

