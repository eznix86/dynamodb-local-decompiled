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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.InternalMeta;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2={"Lorg/partiql/lang/ast/AggregateRegisterIdMeta;", "Lorg/partiql/lang/ast/InternalMeta;", "registerId", "", "(I)V", "getRegisterId", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "Companion", "lang"})
public final class AggregateRegisterIdMeta
extends InternalMeta {
    private final int registerId;
    @NotNull
    public static final String TAG = "$aggregate_register_id";
    public static final Companion Companion = new Companion(null);

    public final int getRegisterId() {
        return this.registerId;
    }

    public AggregateRegisterIdMeta(int registerId) {
        super(TAG);
        this.registerId = registerId;
    }

    public final int component1() {
        return this.registerId;
    }

    @NotNull
    public final AggregateRegisterIdMeta copy(int registerId) {
        return new AggregateRegisterIdMeta(registerId);
    }

    public static /* synthetic */ AggregateRegisterIdMeta copy$default(AggregateRegisterIdMeta aggregateRegisterIdMeta, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = aggregateRegisterIdMeta.registerId;
        }
        return aggregateRegisterIdMeta.copy(n);
    }

    @NotNull
    public String toString() {
        return "AggregateRegisterIdMeta(registerId=" + this.registerId + ")";
    }

    public int hashCode() {
        return Integer.hashCode(this.registerId);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof AggregateRegisterIdMeta)) break block3;
                AggregateRegisterIdMeta aggregateRegisterIdMeta = (AggregateRegisterIdMeta)object;
                if (this.registerId != aggregateRegisterIdMeta.registerId) break block3;
            }
            return true;
        }
        return false;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/ast/AggregateRegisterIdMeta$Companion;", "", "()V", "TAG", "", "lang"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

