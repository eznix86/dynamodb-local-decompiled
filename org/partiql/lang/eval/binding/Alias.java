/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.binding;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J+\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b\u00a8\u0006\u0015"}, d2={"Lorg/partiql/lang/eval/binding/Alias;", "", "asName", "", "atName", "byName", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAsName", "()Ljava/lang/String;", "getAtName", "getByName", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "lang"})
public final class Alias {
    @NotNull
    private final String asName;
    @Nullable
    private final String atName;
    @Nullable
    private final String byName;

    @NotNull
    public final String getAsName() {
        return this.asName;
    }

    @Nullable
    public final String getAtName() {
        return this.atName;
    }

    @Nullable
    public final String getByName() {
        return this.byName;
    }

    public Alias(@NotNull String asName, @Nullable String atName, @Nullable String byName) {
        Intrinsics.checkParameterIsNotNull(asName, "asName");
        this.asName = asName;
        this.atName = atName;
        this.byName = byName;
    }

    @NotNull
    public final String component1() {
        return this.asName;
    }

    @Nullable
    public final String component2() {
        return this.atName;
    }

    @Nullable
    public final String component3() {
        return this.byName;
    }

    @NotNull
    public final Alias copy(@NotNull String asName, @Nullable String atName, @Nullable String byName) {
        Intrinsics.checkParameterIsNotNull(asName, "asName");
        return new Alias(asName, atName, byName);
    }

    public static /* synthetic */ Alias copy$default(Alias alias, String string, String string2, String string3, int n, Object object) {
        if ((n & 1) != 0) {
            string = alias.asName;
        }
        if ((n & 2) != 0) {
            string2 = alias.atName;
        }
        if ((n & 4) != 0) {
            string3 = alias.byName;
        }
        return alias.copy(string, string2, string3);
    }

    @NotNull
    public String toString() {
        return "Alias(asName=" + this.asName + ", atName=" + this.atName + ", byName=" + this.byName + ")";
    }

    public int hashCode() {
        String string = this.asName;
        String string2 = this.atName;
        String string3 = this.byName;
        return ((string != null ? string.hashCode() : 0) * 31 + (string2 != null ? string2.hashCode() : 0)) * 31 + (string3 != null ? string3.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Alias)) break block3;
                Alias alias = (Alias)object;
                if (!Intrinsics.areEqual(this.asName, alias.asName) || !Intrinsics.areEqual(this.atName, alias.atName) || !Intrinsics.areEqual(this.byName, alias.byName)) break block3;
            }
            return true;
        }
        return false;
    }
}

