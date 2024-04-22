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
import org.partiql.lang.util.IonValueExtensionsKt;
import org.partiql.lang.util.IonWriterContext;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\fH\u0016R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\fX\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001f"}, d2={"Lorg/partiql/lang/ast/SourceLocationMeta;", "Lorg/partiql/lang/ast/Meta;", "lineNum", "", "charOffset", "length", "(JJJ)V", "getCharOffset", "()J", "getLength", "getLineNum", "tag", "", "getTag", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "serialize", "", "writer", "Lcom/amazon/ion/IonWriter;", "toString", "Companion", "lang"})
public final class SourceLocationMeta
implements Meta {
    @NotNull
    private final String tag = "$source_location";
    private final long lineNum;
    private final long charOffset;
    private final long length;
    @NotNull
    public static final String TAG = "$source_location";
    @NotNull
    private static final MetaDeserializer deserializer;
    public static final Companion Companion;

    @NotNull
    public String toString() {
        return "" + this.lineNum + ':' + this.charOffset + ':' + (this.length > 0L ? String.valueOf(this.length) : "<unknown>");
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
            final /* synthetic */ SourceLocationMeta this$0;
            {
                this.this$0 = sourceLocationMeta;
                super(1);
            }

            public final void invoke(@NotNull IonWriterContext $this$struct) {
                Intrinsics.checkParameterIsNotNull($this$struct, "$receiver");
                $this$struct.int("line_num", this.this$0.getLineNum());
                $this$struct.int("char_offset", this.this$0.getCharOffset());
                $this$struct.int("length", this.this$0.getLength());
            }
        });
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SourceLocationMeta)) {
            return false;
        }
        if (this.lineNum != ((SourceLocationMeta)other).lineNum) {
            return false;
        }
        if (this.charOffset != ((SourceLocationMeta)other).charOffset) {
            return false;
        }
        return this.length <= 0L || ((SourceLocationMeta)other).length <= 0L || this.length == ((SourceLocationMeta)other).length;
    }

    public int hashCode() {
        int result = Long.hashCode(this.lineNum);
        result = 31 * result + Long.hashCode(this.charOffset);
        if (this.length > 0L) {
            result = 31 * result + Long.hashCode(this.length);
        }
        return result;
    }

    public final long getLineNum() {
        return this.lineNum;
    }

    public final long getCharOffset() {
        return this.charOffset;
    }

    public final long getLength() {
        return this.length;
    }

    public SourceLocationMeta(long lineNum, long charOffset, long length) {
        this.lineNum = lineNum;
        this.charOffset = charOffset;
        this.length = length;
        this.tag = TAG;
    }

    public /* synthetic */ SourceLocationMeta(long l, long l2, long l3, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            l3 = -1L;
        }
        this(l, l2, l3);
    }

    static {
        Companion = new Companion(null);
        deserializer = new MetaDeserializer(){
            @NotNull
            private final String tag = "$source_location";

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
                long lineNum = IonValueExtensionsKt.longValue(IonValueExtensionsKt.field(struct, "line_num"));
                long charOffset = IonValueExtensionsKt.longValue(IonValueExtensionsKt.field(struct, "char_offset"));
                long length = IonValueExtensionsKt.longValue(IonValueExtensionsKt.field(struct, "length"));
                return new SourceLocationMeta(lineNum, charOffset, length);
            }
            {
                this.tag = "$source_location";
            }
        };
    }

    public final long component1() {
        return this.lineNum;
    }

    public final long component2() {
        return this.charOffset;
    }

    public final long component3() {
        return this.length;
    }

    @NotNull
    public final SourceLocationMeta copy(long lineNum, long charOffset, long length) {
        return new SourceLocationMeta(lineNum, charOffset, length);
    }

    public static /* synthetic */ SourceLocationMeta copy$default(SourceLocationMeta sourceLocationMeta, long l, long l2, long l3, int n, Object object) {
        if ((n & 1) != 0) {
            l = sourceLocationMeta.lineNum;
        }
        if ((n & 2) != 0) {
            l2 = sourceLocationMeta.charOffset;
        }
        if ((n & 4) != 0) {
            l3 = sourceLocationMeta.length;
        }
        return sourceLocationMeta.copy(l, l2, l3);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/ast/SourceLocationMeta$Companion;", "", "()V", "TAG", "", "deserializer", "Lorg/partiql/lang/ast/MetaDeserializer;", "getDeserializer", "()Lorg/partiql/lang/ast/MetaDeserializer;", "lang"})
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

