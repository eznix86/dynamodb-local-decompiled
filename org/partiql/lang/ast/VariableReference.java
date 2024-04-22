/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
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
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.ScopeQualifier;
import org.partiql.lang.ast.VariableReference$WhenMappings;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\tH\u00c6\u0003J1\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0096\u0002J\b\u0010!\u001a\u00020\"H\u0016J\t\u0010#\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006$"}, d2={"Lorg/partiql/lang/ast/VariableReference;", "Lorg/partiql/lang/ast/ExprNode;", "id", "", "case", "Lorg/partiql/lang/ast/CaseSensitivity;", "scopeQualifier", "Lorg/partiql/lang/ast/ScopeQualifier;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Ljava/lang/String;Lorg/partiql/lang/ast/CaseSensitivity;Lorg/partiql/lang/ast/ScopeQualifier;Lorg/partiql/lang/ast/MetaContainer;)V", "getCase", "()Lorg/partiql/lang/ast/CaseSensitivity;", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getId", "()Ljava/lang/String;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getScopeQualifier", "()Lorg/partiql/lang/ast/ScopeQualifier;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "lang"})
public final class VariableReference
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final String id;
    @NotNull
    private final CaseSensitivity case;
    @NotNull
    private final ScopeQualifier scopeQualifier;
    @NotNull
    private final MetaContainer metas;

    public boolean equals(@Nullable Object other) {
        return !(other instanceof VariableReference) ? false : StringsKt.compareTo(this.id, ((VariableReference)other).id, this.case == CaseSensitivity.INSENSITIVE) == 0 && this.case == ((VariableReference)other).case && this.scopeQualifier == ((VariableReference)other).scopeQualifier && Intrinsics.areEqual(this.getMetas(), ((VariableReference)other).getMetas());
    }

    public int hashCode() {
        String string;
        Object[] objectArray = new Object[4];
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = objectArray;
        int n = 0;
        switch (VariableReference$WhenMappings.$EnumSwitchMapping$0[this.case.ordinal()]) {
            case 1: {
                string = this.id;
                break;
            }
            case 2: {
                String string2 = this.id;
                int n2 = n;
                Object[] objectArray4 = objectArray2;
                Object[] objectArray5 = objectArray3;
                boolean bl = false;
                String string3 = string2;
                if (string3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string4 = string3.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(string4, "(this as java.lang.String).toLowerCase()");
                String string5 = string4;
                objectArray3 = objectArray5;
                objectArray2 = objectArray4;
                n = n2;
                string = string5;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        objectArray2[n] = string;
        objectArray3[1] = this.case;
        objectArray3[2] = this.scopeQualifier;
        objectArray3[3] = this.getMetas();
        return Arrays.hashCode(objectArray3);
    }

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final CaseSensitivity getCase() {
        return this.case;
    }

    @NotNull
    public final ScopeQualifier getScopeQualifier() {
        return this.scopeQualifier;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public VariableReference(@NotNull String id, @NotNull CaseSensitivity caseSensitivity, @NotNull ScopeQualifier scopeQualifier, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(id, "id");
        Intrinsics.checkParameterIsNotNull((Object)caseSensitivity, "case");
        Intrinsics.checkParameterIsNotNull((Object)scopeQualifier, "scopeQualifier");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.id = id;
        this.case = caseSensitivity;
        this.scopeQualifier = scopeQualifier;
        this.metas = metas;
        VariableReference variableReference = this;
        boolean bl = false;
        List list = CollectionsKt.emptyList();
        variableReference.children = list;
    }

    public /* synthetic */ VariableReference(String string, CaseSensitivity caseSensitivity, ScopeQualifier scopeQualifier, MetaContainer metaContainer, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            scopeQualifier = ScopeQualifier.UNQUALIFIED;
        }
        this(string, caseSensitivity, scopeQualifier, metaContainer);
    }

    @NotNull
    public final String component1() {
        return this.id;
    }

    @NotNull
    public final CaseSensitivity component2() {
        return this.case;
    }

    @NotNull
    public final ScopeQualifier component3() {
        return this.scopeQualifier;
    }

    @NotNull
    public final MetaContainer component4() {
        return this.getMetas();
    }

    @NotNull
    public final VariableReference copy(@NotNull String id, @NotNull CaseSensitivity caseSensitivity, @NotNull ScopeQualifier scopeQualifier, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(id, "id");
        Intrinsics.checkParameterIsNotNull((Object)caseSensitivity, "case");
        Intrinsics.checkParameterIsNotNull((Object)scopeQualifier, "scopeQualifier");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new VariableReference(id, caseSensitivity, scopeQualifier, metas);
    }

    public static /* synthetic */ VariableReference copy$default(VariableReference variableReference, String string, CaseSensitivity caseSensitivity, ScopeQualifier scopeQualifier, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            string = variableReference.id;
        }
        if ((n & 2) != 0) {
            caseSensitivity = variableReference.case;
        }
        if ((n & 4) != 0) {
            scopeQualifier = variableReference.scopeQualifier;
        }
        if ((n & 8) != 0) {
            metaContainer = variableReference.getMetas();
        }
        return variableReference.copy(string, caseSensitivity, scopeQualifier, metaContainer);
    }

    @NotNull
    public String toString() {
        return "VariableReference(id=" + this.id + ", case=" + (Object)((Object)this.case) + ", scopeQualifier=" + (Object)((Object)this.scopeQualifier) + ", metas=" + this.getMetas() + ")";
    }
}

