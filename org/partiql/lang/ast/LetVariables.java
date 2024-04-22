/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.SymbolicName;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0006J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J-\u0010\u0011\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\f8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\r\u00a8\u0006\u0018"}, d2={"Lorg/partiql/lang/ast/LetVariables;", "", "asName", "Lorg/partiql/lang/ast/SymbolicName;", "atName", "byName", "(Lorg/partiql/lang/ast/SymbolicName;Lorg/partiql/lang/ast/SymbolicName;Lorg/partiql/lang/ast/SymbolicName;)V", "getAsName", "()Lorg/partiql/lang/ast/SymbolicName;", "getAtName", "getByName", "isAnySpecified", "", "()Z", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "", "lang"})
public final class LetVariables {
    @Nullable
    private final SymbolicName asName;
    @Nullable
    private final SymbolicName atName;
    @Nullable
    private final SymbolicName byName;

    public final boolean isAnySpecified() {
        return this.asName != null || this.atName != null || this.byName != null;
    }

    @Nullable
    public final SymbolicName getAsName() {
        return this.asName;
    }

    @Nullable
    public final SymbolicName getAtName() {
        return this.atName;
    }

    @Nullable
    public final SymbolicName getByName() {
        return this.byName;
    }

    public LetVariables(@Nullable SymbolicName asName, @Nullable SymbolicName atName, @Nullable SymbolicName byName) {
        this.asName = asName;
        this.atName = atName;
        this.byName = byName;
    }

    public /* synthetic */ LetVariables(SymbolicName symbolicName, SymbolicName symbolicName2, SymbolicName symbolicName3, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            symbolicName = null;
        }
        if ((n & 2) != 0) {
            symbolicName2 = null;
        }
        if ((n & 4) != 0) {
            symbolicName3 = null;
        }
        this(symbolicName, symbolicName2, symbolicName3);
    }

    public LetVariables() {
        this(null, null, null, 7, null);
    }

    @Nullable
    public final SymbolicName component1() {
        return this.asName;
    }

    @Nullable
    public final SymbolicName component2() {
        return this.atName;
    }

    @Nullable
    public final SymbolicName component3() {
        return this.byName;
    }

    @NotNull
    public final LetVariables copy(@Nullable SymbolicName asName, @Nullable SymbolicName atName, @Nullable SymbolicName byName) {
        return new LetVariables(asName, atName, byName);
    }

    public static /* synthetic */ LetVariables copy$default(LetVariables letVariables, SymbolicName symbolicName, SymbolicName symbolicName2, SymbolicName symbolicName3, int n, Object object) {
        if ((n & 1) != 0) {
            symbolicName = letVariables.asName;
        }
        if ((n & 2) != 0) {
            symbolicName2 = letVariables.atName;
        }
        if ((n & 4) != 0) {
            symbolicName3 = letVariables.byName;
        }
        return letVariables.copy(symbolicName, symbolicName2, symbolicName3);
    }

    @NotNull
    public String toString() {
        return "LetVariables(asName=" + this.asName + ", atName=" + this.atName + ", byName=" + this.byName + ")";
    }

    public int hashCode() {
        SymbolicName symbolicName = this.asName;
        SymbolicName symbolicName2 = this.atName;
        SymbolicName symbolicName3 = this.byName;
        return ((symbolicName != null ? ((Object)symbolicName).hashCode() : 0) * 31 + (symbolicName2 != null ? ((Object)symbolicName2).hashCode() : 0)) * 31 + (symbolicName3 != null ? ((Object)symbolicName3).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof LetVariables)) break block3;
                LetVariables letVariables = (LetVariables)object;
                if (!Intrinsics.areEqual(this.asName, letVariables.asName) || !Intrinsics.areEqual(this.atName, letVariables.atName) || !Intrinsics.areEqual(this.byName, letVariables.byName)) break block3;
            }
            return true;
        }
        return false;
    }
}

