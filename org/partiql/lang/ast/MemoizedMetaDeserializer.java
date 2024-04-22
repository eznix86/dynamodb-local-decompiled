/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonSexp;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaDeserializer;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000e"}, d2={"Lorg/partiql/lang/ast/MemoizedMetaDeserializer;", "Lorg/partiql/lang/ast/MetaDeserializer;", "tag", "", "instance", "Lorg/partiql/lang/ast/Meta;", "(Ljava/lang/String;Lorg/partiql/lang/ast/Meta;)V", "getInstance", "()Lorg/partiql/lang/ast/Meta;", "getTag", "()Ljava/lang/String;", "deserialize", "sexp", "Lcom/amazon/ion/IonSexp;", "lang"})
public final class MemoizedMetaDeserializer
implements MetaDeserializer {
    @NotNull
    private final String tag;
    @NotNull
    private final Meta instance;

    @Override
    @NotNull
    public Meta deserialize(@NotNull IonSexp sexp) {
        Intrinsics.checkParameterIsNotNull(sexp, "sexp");
        return this.instance;
    }

    @Override
    @NotNull
    public String getTag() {
        return this.tag;
    }

    @NotNull
    public final Meta getInstance() {
        return this.instance;
    }

    public MemoizedMetaDeserializer(@NotNull String tag, @NotNull Meta instance) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(instance, "instance");
        this.tag = tag;
        this.instance = instance;
    }
}

