/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonSexp;
import com.amazon.ion.IonSystem;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.AstDeserializer;
import org.partiql.lang.ast.AstDeserializerInternal;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.IsCountStarMeta;
import org.partiql.lang.ast.IsImplictJoinMeta;
import org.partiql.lang.ast.LegacyLogicalNotMeta;
import org.partiql.lang.ast.MetaDeserializer;
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.ast.StaticTypeMeta;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/ast/AstDeserializerBuilder;", "", "ion", "Lcom/amazon/ion/IonSystem;", "(Lcom/amazon/ion/IonSystem;)V", "getIon", "()Lcom/amazon/ion/IonSystem;", "metaDeserializers", "", "", "Lorg/partiql/lang/ast/MetaDeserializer;", "build", "Lorg/partiql/lang/ast/AstDeserializer;", "withMetaDeserializer", "deserializer", "lang"})
public final class AstDeserializerBuilder {
    private final Map<String, MetaDeserializer> metaDeserializers;
    @NotNull
    private final IonSystem ion;

    @NotNull
    public final AstDeserializerBuilder withMetaDeserializer(@NotNull MetaDeserializer deserializer2) {
        Intrinsics.checkParameterIsNotNull(deserializer2, "deserializer");
        this.metaDeserializers.put(deserializer2.getTag(), deserializer2);
        return this;
    }

    @NotNull
    public final AstDeserializer build() {
        return new AstDeserializer(this){
            final /* synthetic */ AstDeserializerBuilder this$0;

            @NotNull
            public ExprNode deserialize(@NotNull IonSexp sexp, @NotNull AstVersion astVersion) {
                Intrinsics.checkParameterIsNotNull(sexp, "sexp");
                Intrinsics.checkParameterIsNotNull((Object)((Object)astVersion), "astVersion");
                return new AstDeserializerInternal(astVersion, this.this$0.getIon(), MapsKt.toMap(AstDeserializerBuilder.access$getMetaDeserializers$p(this.this$0))).deserialize(sexp);
            }
            {
                this.this$0 = $outer;
            }
        };
    }

    @NotNull
    public final IonSystem getIon() {
        return this.ion;
    }

    public AstDeserializerBuilder(@NotNull IonSystem ion) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        this.ion = ion;
        this.metaDeserializers = MapsKt.mutableMapOf(TuplesKt.to(SourceLocationMeta.Companion.getDeserializer().getTag(), SourceLocationMeta.Companion.getDeserializer()), TuplesKt.to(StaticTypeMeta.Companion.getDeserializer().getTag(), StaticTypeMeta.Companion.getDeserializer()), TuplesKt.to(LegacyLogicalNotMeta.Companion.getDeserializer().getTag(), LegacyLogicalNotMeta.Companion.getDeserializer()), TuplesKt.to(IsImplictJoinMeta.Companion.getDeserializer().getTag(), IsImplictJoinMeta.Companion.getDeserializer()), TuplesKt.to(IsCountStarMeta.Companion.getDeserializer().getTag(), IsCountStarMeta.Companion.getDeserializer()));
    }

    public static final /* synthetic */ Map access$getMetaDeserializers$p(AstDeserializerBuilder $this) {
        return $this.metaDeserializers;
    }
}

