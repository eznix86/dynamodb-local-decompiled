/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.syntax;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/syntax/SourcePosition;", "", "line", "", "column", "(JJ)V", "getColumn", "()J", "getLine", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "lang"})
public final class SourcePosition {
    private final long line;
    private final long column;

    @NotNull
    public String toString() {
        return "line " + this.line + ", column " + this.column;
    }

    public final long getLine() {
        return this.line;
    }

    public final long getColumn() {
        return this.column;
    }

    public SourcePosition(long line, long column) {
        this.line = line;
        this.column = column;
    }

    public final long component1() {
        return this.line;
    }

    public final long component2() {
        return this.column;
    }

    @NotNull
    public final SourcePosition copy(long line, long column) {
        return new SourcePosition(line, column);
    }

    public static /* synthetic */ SourcePosition copy$default(SourcePosition sourcePosition, long l, long l2, int n, Object object) {
        if ((n & 1) != 0) {
            l = sourcePosition.line;
        }
        if ((n & 2) != 0) {
            l2 = sourcePosition.column;
        }
        return sourcePosition.copy(l, l2);
    }

    public int hashCode() {
        return Long.hashCode(this.line) * 31 + Long.hashCode(this.column);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SourcePosition)) break block3;
                SourcePosition sourcePosition = (SourcePosition)object;
                if (this.line != sourcePosition.line || this.column != sourcePosition.column) break block3;
            }
            return true;
        }
        return false;
    }
}

