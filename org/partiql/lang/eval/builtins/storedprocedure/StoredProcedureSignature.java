/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins.storedprocedure;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0007H\u00c6\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0015"}, d2={"Lorg/partiql/lang/eval/builtins/storedprocedure/StoredProcedureSignature;", "", "name", "", "arity", "", "(Ljava/lang/String;I)V", "Lkotlin/ranges/IntRange;", "(Ljava/lang/String;Lkotlin/ranges/IntRange;)V", "getArity", "()Lkotlin/ranges/IntRange;", "getName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "lang"})
public final class StoredProcedureSignature {
    @NotNull
    private final String name;
    @NotNull
    private final IntRange arity;

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final IntRange getArity() {
        return this.arity;
    }

    public StoredProcedureSignature(@NotNull String name, @NotNull IntRange arity) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(arity, "arity");
        this.name = name;
        this.arity = arity;
    }

    public StoredProcedureSignature(@NotNull String name, int arity) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        int n = arity;
        this(name, new IntRange(n, arity));
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final IntRange component2() {
        return this.arity;
    }

    @NotNull
    public final StoredProcedureSignature copy(@NotNull String name, @NotNull IntRange arity) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(arity, "arity");
        return new StoredProcedureSignature(name, arity);
    }

    public static /* synthetic */ StoredProcedureSignature copy$default(StoredProcedureSignature storedProcedureSignature, String string, IntRange intRange, int n, Object object) {
        if ((n & 1) != 0) {
            string = storedProcedureSignature.name;
        }
        if ((n & 2) != 0) {
            intRange = storedProcedureSignature.arity;
        }
        return storedProcedureSignature.copy(string, intRange);
    }

    @NotNull
    public String toString() {
        return "StoredProcedureSignature(name=" + this.name + ", arity=" + this.arity + ")";
    }

    public int hashCode() {
        String string = this.name;
        IntRange intRange = this.arity;
        return (string != null ? string.hashCode() : 0) * 31 + (intRange != null ? ((Object)intRange).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof StoredProcedureSignature)) break block3;
                StoredProcedureSignature storedProcedureSignature = (StoredProcedureSignature)object;
                if (!Intrinsics.areEqual(this.name, storedProcedureSignature.name) || !Intrinsics.areEqual(this.arity, storedProcedureSignature.arity)) break block3;
            }
            return true;
        }
        return false;
    }
}

