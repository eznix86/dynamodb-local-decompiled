/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonWriter;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.MemoizedMetaDeserializer;
import org.partiql.lang.ast.Meta;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/ast/IsCountStarMeta;", "Lorg/partiql/lang/ast/Meta;", "()V", "tag", "", "getTag", "()Ljava/lang/String;", "Companion", "lang"})
public final class IsCountStarMeta
implements Meta {
    @NotNull
    private final String tag = "$is_count_star";
    @NotNull
    public static final String TAG = "$is_count_star";
    @NotNull
    private static final IsCountStarMeta instance;
    @NotNull
    private static final MemoizedMetaDeserializer deserializer;
    public static final Companion Companion;

    @Override
    @NotNull
    public String getTag() {
        return this.tag;
    }

    private IsCountStarMeta() {
    }

    static {
        Companion = new Companion(null);
        instance = new IsCountStarMeta();
        deserializer = new MemoizedMetaDeserializer(TAG, instance);
    }

    @Override
    public void serialize(@NotNull IonWriter writer) {
        Intrinsics.checkParameterIsNotNull(writer, "writer");
        Meta.DefaultImpls.serialize(this, writer);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2={"Lorg/partiql/lang/ast/IsCountStarMeta$Companion;", "", "()V", "TAG", "", "deserializer", "Lorg/partiql/lang/ast/MemoizedMetaDeserializer;", "getDeserializer", "()Lorg/partiql/lang/ast/MemoizedMetaDeserializer;", "instance", "Lorg/partiql/lang/ast/IsCountStarMeta;", "getInstance", "()Lorg/partiql/lang/ast/IsCountStarMeta;", "lang"})
    public static final class Companion {
        @NotNull
        public final IsCountStarMeta getInstance() {
            return instance;
        }

        @NotNull
        public final MemoizedMetaDeserializer getDeserializer() {
            return deserializer;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

