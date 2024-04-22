/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.builtins.timestamp.FormatItem;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/TextItem;", "Lorg/partiql/lang/eval/builtins/timestamp/FormatItem;", "raw", "", "(Ljava/lang/String;)V", "getRaw", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "lang"})
public final class TextItem
extends FormatItem {
    @NotNull
    private final String raw;

    @NotNull
    public final String getRaw() {
        return this.raw;
    }

    public TextItem(@NotNull String raw) {
        Intrinsics.checkParameterIsNotNull(raw, "raw");
        super(null);
        this.raw = raw;
    }

    @NotNull
    public final String component1() {
        return this.raw;
    }

    @NotNull
    public final TextItem copy(@NotNull String raw) {
        Intrinsics.checkParameterIsNotNull(raw, "raw");
        return new TextItem(raw);
    }

    public static /* synthetic */ TextItem copy$default(TextItem textItem, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = textItem.raw;
        }
        return textItem.copy(string);
    }

    @NotNull
    public String toString() {
        return "TextItem(raw=" + this.raw + ")";
    }

    public int hashCode() {
        String string = this.raw;
        return string != null ? string.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof TextItem)) break block3;
                TextItem textItem = (TextItem)object;
                if (!Intrinsics.areEqual(this.raw, textItem.raw)) break block3;
            }
            return true;
        }
        return false;
    }
}

