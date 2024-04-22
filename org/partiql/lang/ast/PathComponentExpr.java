/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonType;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.CaseSensitivity;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.PathComponent;
import org.partiql.lang.util.IonValueExtensionsKt;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001c"}, d2={"Lorg/partiql/lang/ast/PathComponentExpr;", "Lorg/partiql/lang/ast/PathComponent;", "expr", "Lorg/partiql/lang/ast/ExprNode;", "case", "Lorg/partiql/lang/ast/CaseSensitivity;", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/CaseSensitivity;)V", "getCase", "()Lorg/partiql/lang/ast/CaseSensitivity;", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getExpr", "()Lorg/partiql/lang/ast/ExprNode;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "Companion", "lang"})
public final class PathComponentExpr
extends PathComponent {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode expr;
    @NotNull
    private final CaseSensitivity case;
    public static final Companion Companion = new Companion(null);

    /*
     * WARNING - void declaration
     */
    public boolean equals(@Nullable Object other) {
        boolean bl;
        PathComponentExpr pathComponentExpr;
        Object object = other;
        if (!(object instanceof PathComponentExpr)) {
            object = null;
        }
        if ((pathComponentExpr = (PathComponentExpr)object) == null) {
            bl = false;
        } else {
            String myStringValue = PathComponentExpr.Companion.getStringValueIfCaseInsensitiveLiteral(this);
            String otherStringValue = PathComponentExpr.Companion.getStringValueIfCaseInsensitiveLiteral((PathComponentExpr)other);
            if (myStringValue == null && otherStringValue == null) {
                void otherExpr;
                PathComponentExpr pathComponentExpr2 = (PathComponentExpr)other;
                ExprNode exprNode = pathComponentExpr2.component1();
                CaseSensitivity otherCase = pathComponentExpr2.component2();
                bl = this.expr.equals(otherExpr) && this.case == otherCase;
            } else {
                bl = myStringValue == null || otherStringValue == null ? false : StringsKt.equals(myStringValue, otherStringValue, true);
            }
        }
        return bl;
    }

    public int hashCode() {
        Object object;
        Object[] objectArray;
        block5: {
            block4: {
                Object[] objectArray2 = new Object[2];
                Object[] objectArray3 = objectArray2;
                objectArray = objectArray2;
                int n = 0;
                object = PathComponentExpr.Companion.getStringValueIfCaseInsensitiveLiteral(this);
                if (object == null) break block4;
                String string = object;
                int n2 = n;
                Object[] objectArray4 = objectArray3;
                Object[] objectArray5 = objectArray;
                boolean bl = false;
                String string2 = string;
                if (string2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string3 = string2.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
                String string4 = string3;
                objectArray = objectArray5;
                objectArray3 = objectArray4;
                n = n2;
                object = string4;
                if (object != null) break block5;
            }
            object = this.expr;
        }
        objectArray3[n] = object;
        objectArray[1] = this.case;
        return Arrays.hashCode(objectArray);
    }

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getExpr() {
        return this.expr;
    }

    @NotNull
    public final CaseSensitivity getCase() {
        return this.case;
    }

    public PathComponentExpr(@NotNull ExprNode expr, @NotNull CaseSensitivity caseSensitivity) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull((Object)caseSensitivity, "case");
        super(null);
        this.expr = expr;
        this.case = caseSensitivity;
        this.children = CollectionsKt.listOf(this.expr);
    }

    @NotNull
    public final ExprNode component1() {
        return this.expr;
    }

    @NotNull
    public final CaseSensitivity component2() {
        return this.case;
    }

    @NotNull
    public final PathComponentExpr copy(@NotNull ExprNode expr, @NotNull CaseSensitivity caseSensitivity) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull((Object)caseSensitivity, "case");
        return new PathComponentExpr(expr, caseSensitivity);
    }

    public static /* synthetic */ PathComponentExpr copy$default(PathComponentExpr pathComponentExpr, ExprNode exprNode, CaseSensitivity caseSensitivity, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = pathComponentExpr.expr;
        }
        if ((n & 2) != 0) {
            caseSensitivity = pathComponentExpr.case;
        }
        return pathComponentExpr.copy(exprNode, caseSensitivity);
    }

    @NotNull
    public String toString() {
        return "PathComponentExpr(expr=" + this.expr + ", case=" + (Object)((Object)this.case) + ")";
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/ast/PathComponentExpr$Companion;", "", "()V", "getStringValueIfCaseInsensitiveLiteral", "", "component", "Lorg/partiql/lang/ast/PathComponentExpr;", "lang"})
    public static final class Companion {
        private final String getStringValueIfCaseInsensitiveLiteral(PathComponentExpr component) {
            return component.getCase() == CaseSensitivity.INSENSITIVE && component.getExpr() instanceof Literal && ((Literal)component.getExpr()).getIonValue().getType() == IonType.STRING ? IonValueExtensionsKt.stringValue(((Literal)component.getExpr()).getIonValue()) : null;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

