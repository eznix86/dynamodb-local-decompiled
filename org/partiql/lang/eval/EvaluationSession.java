/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.Timestamp;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.ExprValue;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u00112\u00020\u0001:\u0002\u0010\u0011B+\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lorg/partiql/lang/eval/EvaluationSession;", "", "globals", "Lorg/partiql/lang/eval/Bindings;", "Lorg/partiql/lang/eval/ExprValue;", "parameters", "", "now", "Lcom/amazon/ion/Timestamp;", "(Lorg/partiql/lang/eval/Bindings;Ljava/util/List;Lcom/amazon/ion/Timestamp;)V", "getGlobals", "()Lorg/partiql/lang/eval/Bindings;", "getNow", "()Lcom/amazon/ion/Timestamp;", "getParameters", "()Ljava/util/List;", "Builder", "Companion", "lang"})
public final class EvaluationSession {
    @NotNull
    private final Bindings<ExprValue> globals;
    @NotNull
    private final List<ExprValue> parameters;
    @NotNull
    private final Timestamp now;
    public static final Companion Companion = new Companion(null);

    @NotNull
    public final Bindings<ExprValue> getGlobals() {
        return this.globals;
    }

    @NotNull
    public final List<ExprValue> getParameters() {
        return this.parameters;
    }

    @NotNull
    public final Timestamp getNow() {
        return this.now;
    }

    private EvaluationSession(Bindings<ExprValue> globals, List<? extends ExprValue> parameters, Timestamp now) {
        this.globals = globals;
        this.parameters = parameters;
        this.now = now;
    }

    public /* synthetic */ EvaluationSession(Bindings globals, List parameters, Timestamp now, DefaultConstructorMarker $constructor_marker) {
        this(globals, parameters, now);
    }

    @JvmStatic
    @NotNull
    public static final Builder builder() {
        return Companion.builder();
    }

    @JvmStatic
    @NotNull
    public static final EvaluationSession standard() {
        return Companion.standard();
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bJ\u0014\u0010\u0003\u001a\u00020\u00002\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004J\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0007J\u0014\u0010\b\u001a\u00020\u00002\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\tJ\f\u0010\r\u001a\u00020\u0007*\u00020\u0007H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lorg/partiql/lang/eval/EvaluationSession$Builder;", "", "()V", "globals", "Lorg/partiql/lang/eval/Bindings;", "Lorg/partiql/lang/eval/ExprValue;", "now", "Lcom/amazon/ion/Timestamp;", "parameters", "", "build", "Lorg/partiql/lang/eval/EvaluationSession;", "value", "toUtc", "lang"})
    public static final class Builder {
        private Timestamp now;
        private Bindings<ExprValue> globals = Bindings.Companion.empty();
        private List<? extends ExprValue> parameters;

        private final Timestamp toUtc(@NotNull Timestamp $this$toUtc) {
            Timestamp timestamp = $this$toUtc.withLocalOffset(0);
            if (timestamp == null) {
                Intrinsics.throwNpe();
            }
            return timestamp;
        }

        @NotNull
        public final Builder now(@NotNull Timestamp value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            this.now = this.toUtc(value);
            return this;
        }

        @NotNull
        public final Builder globals(@NotNull Bindings<ExprValue> value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            this.globals = value;
            return this;
        }

        @NotNull
        public final Builder parameters(@NotNull List<? extends ExprValue> value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            this.parameters = value;
            return this;
        }

        @NotNull
        public final EvaluationSession build() {
            Timestamp timestamp = this.now;
            if (timestamp == null) {
                Timestamp timestamp2 = Timestamp.nowZ();
                timestamp = timestamp2;
                Intrinsics.checkExpressionValueIsNotNull(timestamp2, "Timestamp.nowZ()");
            }
            Bindings<ExprValue> bindings2 = this.globals;
            List<? extends ExprValue> list = this.parameters;
            Timestamp timestamp3 = timestamp;
            return new EvaluationSession(bindings2, list, timestamp3, null);
        }

        public Builder() {
            Builder builder = this;
            boolean bl = false;
            List list = CollectionsKt.emptyList();
            builder.parameters = list;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\tJ\b\u0010\n\u001a\u00020\u0007H\u0007J\b\u0010\u000b\u001a\u00020\u0004H\u0007\u00a8\u0006\f"}, d2={"Lorg/partiql/lang/eval/EvaluationSession$Companion;", "", "()V", "build", "Lorg/partiql/lang/eval/EvaluationSession;", "block", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/EvaluationSession$Builder;", "", "Lkotlin/ExtensionFunctionType;", "builder", "standard", "lang"})
    public static final class Companion {
        @JvmStatic
        @NotNull
        public final Builder builder() {
            return new Builder();
        }

        @NotNull
        public final EvaluationSession build(@NotNull Function1<? super Builder, Unit> block) {
            Intrinsics.checkParameterIsNotNull(block, "block");
            Builder builder = new Builder();
            boolean bl = false;
            boolean bl2 = false;
            block.invoke(builder);
            return builder.build();
        }

        @JvmStatic
        @NotNull
        public final EvaluationSession standard() {
            return this.builder().build();
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

