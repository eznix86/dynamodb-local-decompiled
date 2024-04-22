/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonSystem;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.BindingsExtensionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.StructExprValue;
import org.partiql.lang.eval.StructOrdering;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\u0002\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR!\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0010\u0010\u000eR\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00060\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/eval/GroupKeyExprValue;", "Lorg/partiql/lang/eval/StructExprValue;", "ion", "Lcom/amazon/ion/IonSystem;", "sequence", "Lkotlin/sequences/Sequence;", "Lorg/partiql/lang/eval/ExprValue;", "uniqueNames", "", "", "(Lcom/amazon/ion/IonSystem;Lkotlin/sequences/Sequence;Ljava/util/Map;)V", "bindings", "Lorg/partiql/lang/eval/Bindings;", "getBindings", "()Lorg/partiql/lang/eval/Bindings;", "keyBindings", "getKeyBindings", "keyBindings$delegate", "Lkotlin/Lazy;", "lang"})
public final class GroupKeyExprValue
extends StructExprValue {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final Lazy keyBindings$delegate;
    private final Map<String, ExprValue> uniqueNames;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(GroupKeyExprValue.class), "keyBindings", "getKeyBindings()Lorg/partiql/lang/eval/Bindings;"))};
    }

    private final Bindings<ExprValue> getKeyBindings() {
        Lazy lazy = this.keyBindings$delegate;
        GroupKeyExprValue groupKeyExprValue = this;
        KProperty kProperty = $$delegatedProperties[0];
        boolean bl = false;
        return (Bindings)lazy.getValue();
    }

    @Override
    @NotNull
    public Bindings<ExprValue> getBindings() {
        return this.getKeyBindings();
    }

    public GroupKeyExprValue(@NotNull IonSystem ion, @NotNull Sequence<? extends ExprValue> sequence, @NotNull Map<String, ? extends ExprValue> uniqueNames) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        Intrinsics.checkParameterIsNotNull(uniqueNames, "uniqueNames");
        super(ion, StructOrdering.UNORDERED, sequence);
        this.uniqueNames = uniqueNames;
        this.keyBindings$delegate = LazyKt.lazy((Function0)new Function0<Bindings<ExprValue>>(this){
            final /* synthetic */ GroupKeyExprValue this$0;

            @NotNull
            public final Bindings<ExprValue> invoke() {
                return MapsKt.any(GroupKeyExprValue.access$getUniqueNames$p(this.this$0)) ? BindingsExtensionsKt.delegate(Bindings.Companion.ofMap(GroupKeyExprValue.access$getUniqueNames$p(this.this$0)), GroupKeyExprValue.access$getBindings$p$s1712052444(this.this$0)) : GroupKeyExprValue.access$getBindings$p$s1712052444(this.this$0);
            }
            {
                this.this$0 = groupKeyExprValue;
                super(0);
            }
        });
    }

    public static final /* synthetic */ Map access$getUniqueNames$p(GroupKeyExprValue $this) {
        return $this.uniqueNames;
    }

    public static final /* synthetic */ Bindings access$getBindings$p$s1712052444(GroupKeyExprValue $this) {
        return super.getBindings();
    }
}

