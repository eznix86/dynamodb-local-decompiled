/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonSexp;
import com.amazon.ion.IonSystem;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.AstSerializerImpl;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.ExprNode;

@Deprecated(message="Please use PartiqlAst")
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/ast/AstSerializer;", "", "serialize", "Lcom/amazon/ion/IonSexp;", "exprNode", "Lorg/partiql/lang/ast/ExprNode;", "Companion", "lang"})
public interface AstSerializer {
    public static final Companion Companion = org.partiql.lang.ast.AstSerializer$Companion.$$INSTANCE;

    @NotNull
    public IonSexp serialize(@NotNull ExprNode var1);

    @Deprecated(message="Please use PartiqlAst")
    @JvmStatic
    @NotNull
    public static IonSexp serialize(@NotNull ExprNode exprNode, @NotNull AstVersion astVersion, @NotNull IonSystem ion) {
        return Companion.serialize(exprNode, astVersion, ion);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/ast/AstSerializer$Companion;", "", "()V", "serialize", "Lcom/amazon/ion/IonSexp;", "exprNode", "Lorg/partiql/lang/ast/ExprNode;", "astVersion", "Lorg/partiql/lang/ast/AstVersion;", "ion", "Lcom/amazon/ion/IonSystem;", "lang"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        @Deprecated(message="Please use PartiqlAst")
        @JvmStatic
        @NotNull
        public final IonSexp serialize(@NotNull ExprNode exprNode, @NotNull AstVersion astVersion, @NotNull IonSystem ion) {
            Intrinsics.checkParameterIsNotNull(exprNode, "exprNode");
            Intrinsics.checkParameterIsNotNull((Object)astVersion, "astVersion");
            Intrinsics.checkParameterIsNotNull(ion, "ion");
            return new AstSerializerImpl(astVersion, ion).serialize(exprNode);
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
        }
    }
}

