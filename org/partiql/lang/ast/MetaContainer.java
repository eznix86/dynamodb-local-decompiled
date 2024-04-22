/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonWriter;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.Meta;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0002H&J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00022\u0006\u0010\n\u001a\u00020\u000bH&J\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u00a6\u0002J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H&R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0012"}, d2={"Lorg/partiql/lang/ast/MetaContainer;", "", "Lorg/partiql/lang/ast/Meta;", "shouldSerialize", "", "getShouldSerialize", "()Z", "add", "meta", "find", "tagName", "", "get", "hasMeta", "serialize", "", "writer", "Lcom/amazon/ion/IonWriter;", "lang"})
public interface MetaContainer
extends Iterable<Meta>,
KMappedMarker {
    @NotNull
    public MetaContainer add(@NotNull Meta var1);

    public boolean hasMeta(@NotNull String var1);

    @NotNull
    public Meta get(@NotNull String var1);

    @Nullable
    public Meta find(@NotNull String var1);

    public boolean getShouldSerialize();

    public void serialize(@NotNull IonWriter var1);
}

