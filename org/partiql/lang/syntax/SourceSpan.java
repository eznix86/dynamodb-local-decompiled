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

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b\u00a8\u0006\u0016"}, d2={"Lorg/partiql/lang/syntax/SourceSpan;", "", "line", "", "column", "length", "(JJJ)V", "getColumn", "()J", "getLength", "getLine", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "lang"})
public final class SourceSpan {
    private final long line;
    private final long column;
    private final long length;

    public final long getLine() {
        return this.line;
    }

    public final long getColumn() {
        return this.column;
    }

    public final long getLength() {
        return this.length;
    }

    public SourceSpan(long line, long column, long length) {
        this.line = line;
        this.column = column;
        this.length = length;
    }

    public final long component1() {
        return this.line;
    }

    public final long component2() {
        return this.column;
    }

    public final long component3() {
        return this.length;
    }

    @NotNull
    public final SourceSpan copy(long line, long column, long length) {
        return new SourceSpan(line, column, length);
    }

    public static /* synthetic */ SourceSpan copy$default(SourceSpan sourceSpan, long l, long l2, long l3, int n, Object object) {
        if ((n & 1) != 0) {
            l = sourceSpan.line;
        }
        if ((n & 2) != 0) {
            l2 = sourceSpan.column;
        }
        if ((n & 4) != 0) {
            l3 = sourceSpan.length;
        }
        return sourceSpan.copy(l, l2, l3);
    }

    @NotNull
    public String toString() {
        return "SourceSpan(line=" + this.line + ", column=" + this.column + ", length=" + this.length + ")";
    }

    public int hashCode() {
        return (Long.hashCode(this.line) * 31 + Long.hashCode(this.column)) * 31 + Long.hashCode(this.length);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SourceSpan)) break block3;
                SourceSpan sourceSpan = (SourceSpan)object;
                if (this.line != sourceSpan.line || this.column != sourceSpan.column || this.length != sourceSpan.length) break block3;
            }
            return true;
        }
        return false;
    }
}

