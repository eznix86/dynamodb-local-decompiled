/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonWriter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/ast/Meta;", "", "tag", "", "getTag", "()Ljava/lang/String;", "serialize", "", "writer", "Lcom/amazon/ion/IonWriter;", "lang"})
public interface Meta {
    @NotNull
    public String getTag();

    public void serialize(@NotNull IonWriter var1);

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
    public static final class DefaultImpls {
        public static void serialize(Meta $this, @NotNull IonWriter writer) {
            Intrinsics.checkParameterIsNotNull(writer, "writer");
        }
    }
}

