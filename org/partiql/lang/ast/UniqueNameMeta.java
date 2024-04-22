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
import org.partiql.lang.ast.InternalMeta;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2={"Lorg/partiql/lang/ast/UniqueNameMeta;", "Lorg/partiql/lang/ast/InternalMeta;", "uniqueName", "", "(Ljava/lang/String;)V", "getUniqueName", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "Companion", "lang"})
public final class UniqueNameMeta
extends InternalMeta {
    @NotNull
    private final String uniqueName;
    @NotNull
    public static final String TAG = "$unique_name";
    public static final Companion Companion = new Companion(null);

    @NotNull
    public final String getUniqueName() {
        return this.uniqueName;
    }

    public UniqueNameMeta(@NotNull String uniqueName) {
        Intrinsics.checkParameterIsNotNull(uniqueName, "uniqueName");
        super(TAG);
        this.uniqueName = uniqueName;
    }

    @NotNull
    public final String component1() {
        return this.uniqueName;
    }

    @NotNull
    public final UniqueNameMeta copy(@NotNull String uniqueName) {
        Intrinsics.checkParameterIsNotNull(uniqueName, "uniqueName");
        return new UniqueNameMeta(uniqueName);
    }

    public static /* synthetic */ UniqueNameMeta copy$default(UniqueNameMeta uniqueNameMeta, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = uniqueNameMeta.uniqueName;
        }
        return uniqueNameMeta.copy(string);
    }

    @NotNull
    public String toString() {
        return "UniqueNameMeta(uniqueName=" + this.uniqueName + ")";
    }

    public int hashCode() {
        String string = this.uniqueName;
        return string != null ? string.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof UniqueNameMeta)) break block3;
                UniqueNameMeta uniqueNameMeta = (UniqueNameMeta)object;
                if (!Intrinsics.areEqual(this.uniqueName, uniqueNameMeta.uniqueName)) break block3;
            }
            return true;
        }
        return false;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/ast/UniqueNameMeta$Companion;", "", "()V", "TAG", "", "lang"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

