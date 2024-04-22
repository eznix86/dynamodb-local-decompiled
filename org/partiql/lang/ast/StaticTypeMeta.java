/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonSexp;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaDeserializer;
import org.partiql.lang.types.StaticType;
import org.partiql.lang.util.IonValueExtensionsKt;
import org.partiql.lang.util.IonWriterContext;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0019"}, d2={"Lorg/partiql/lang/ast/StaticTypeMeta;", "Lorg/partiql/lang/ast/Meta;", "type", "Lorg/partiql/lang/types/StaticType;", "(Lorg/partiql/lang/types/StaticType;)V", "tag", "", "getTag", "()Ljava/lang/String;", "getType", "()Lorg/partiql/lang/types/StaticType;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "serialize", "", "writer", "Lcom/amazon/ion/IonWriter;", "toString", "Companion", "lang"})
public final class StaticTypeMeta
implements Meta {
    @NotNull
    private final String tag = "$static_type";
    @NotNull
    private final StaticType type;
    @NotNull
    public static final String TAG = "$static_type";
    @NotNull
    private static final MetaDeserializer deserializer;
    public static final Companion Companion;

    @NotNull
    public String toString() {
        return this.type.toString();
    }

    @Override
    @NotNull
    public String getTag() {
        return this.tag;
    }

    @Override
    public void serialize(@NotNull IonWriter writer) {
        Intrinsics.checkParameterIsNotNull(writer, "writer");
        IonWriterContext ionWriterContext = new IonWriterContext(writer);
        boolean bl = false;
        boolean bl2 = false;
        IonWriterContext $this$apply = ionWriterContext;
        boolean bl3 = false;
        $this$apply.struct((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
            final /* synthetic */ StaticTypeMeta this$0;
            {
                this.this$0 = staticTypeMeta;
                super(1);
            }

            public final void invoke(@NotNull IonWriterContext $this$struct) {
                Intrinsics.checkParameterIsNotNull($this$struct, "$receiver");
                $this$struct.string("name", this.this$0.getType().getName());
            }
        });
    }

    @NotNull
    public final StaticType getType() {
        return this.type;
    }

    public StaticTypeMeta(@NotNull StaticType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        this.type = type;
        this.tag = TAG;
    }

    static {
        Companion = new Companion(null);
        deserializer = new MetaDeserializer(){
            @NotNull
            private final String tag = "$static_type";

            @NotNull
            public String getTag() {
                return this.tag;
            }

            @NotNull
            public Meta deserialize(@NotNull IonSexp sexp) {
                Intrinsics.checkParameterIsNotNull(sexp, "sexp");
                T t = CollectionsKt.first(sexp);
                Intrinsics.checkExpressionValueIsNotNull(t, "sexp.first()");
                IonStruct struct = IonValueExtensionsKt.asIonStruct((IonValue)t);
                String string = IonValueExtensionsKt.stringValue(IonValueExtensionsKt.field(struct, "name"));
                if (string == null) {
                    Intrinsics.throwNpe();
                }
                String typeName = string;
                StaticType staticType = StaticType.Companion.fromTypeName(typeName);
                return new StaticTypeMeta(staticType);
            }
            {
                this.tag = "$static_type";
            }
        };
    }

    @NotNull
    public final StaticType component1() {
        return this.type;
    }

    @NotNull
    public final StaticTypeMeta copy(@NotNull StaticType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return new StaticTypeMeta(type);
    }

    public static /* synthetic */ StaticTypeMeta copy$default(StaticTypeMeta staticTypeMeta, StaticType staticType, int n, Object object) {
        if ((n & 1) != 0) {
            staticType = staticTypeMeta.type;
        }
        return staticTypeMeta.copy(staticType);
    }

    public int hashCode() {
        StaticType staticType = this.type;
        return staticType != null ? staticType.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof StaticTypeMeta)) break block3;
                StaticTypeMeta staticTypeMeta = (StaticTypeMeta)object;
                if (!Intrinsics.areEqual(this.type, staticTypeMeta.type)) break block3;
            }
            return true;
        }
        return false;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/ast/StaticTypeMeta$Companion;", "", "()V", "TAG", "", "deserializer", "Lorg/partiql/lang/ast/MetaDeserializer;", "getDeserializer", "()Lorg/partiql/lang/ast/MetaDeserializer;", "lang"})
    public static final class Companion {
        @NotNull
        public final MetaDeserializer getDeserializer() {
            return deserializer;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

