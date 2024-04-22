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
import org.partiql.lang.ast.Meta;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/ast/InternalMeta;", "Lorg/partiql/lang/ast/Meta;", "tag", "", "(Ljava/lang/String;)V", "getTag", "()Ljava/lang/String;", "serialize", "", "writer", "Lcom/amazon/ion/IonWriter;", "lang"})
public class InternalMeta
implements Meta {
    @NotNull
    private final String tag;

    @Override
    public void serialize(@NotNull IonWriter writer) {
        Intrinsics.checkParameterIsNotNull(writer, "writer");
        throw (Throwable)new UnsupportedOperationException(this.getClass() + " is meant for internal use only and cannot be serialized.");
    }

    @Override
    @NotNull
    public String getTag() {
        return this.tag;
    }

    public InternalMeta(@NotNull String tag) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        this.tag = tag;
    }
}

