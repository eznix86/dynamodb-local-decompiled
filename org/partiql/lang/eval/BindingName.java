/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BindingCase;
import org.partiql.lang.util.BindingHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\u0010\u0010\u0017\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\u00038FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/eval/BindingName;", "", "name", "", "bindingCase", "Lorg/partiql/lang/eval/BindingCase;", "(Ljava/lang/String;Lorg/partiql/lang/eval/BindingCase;)V", "getBindingCase", "()Lorg/partiql/lang/eval/BindingCase;", "loweredName", "getLoweredName", "()Ljava/lang/String;", "loweredName$delegate", "Lkotlin/Lazy;", "getName", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "isEquivalentTo", "otherName", "toString", "lang"})
public final class BindingName {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final Lazy loweredName$delegate;
    @NotNull
    private final String name;
    @NotNull
    private final BindingCase bindingCase;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(BindingName.class), "loweredName", "getLoweredName()Ljava/lang/String;"))};
    }

    @NotNull
    public final String getLoweredName() {
        Lazy lazy = this.loweredName$delegate;
        BindingName bindingName = this;
        KProperty kProperty = $$delegatedProperties[0];
        boolean bl = false;
        return (String)lazy.getValue();
    }

    public final boolean isEquivalentTo(@Nullable String otherName) {
        return otherName != null && BindingHelpersKt.isBindingNameEquivalent(this.name, otherName, this.bindingCase);
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final BindingCase getBindingCase() {
        return this.bindingCase;
    }

    public BindingName(@NotNull String name, @NotNull BindingCase bindingCase) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull((Object)bindingCase, "bindingCase");
        this.name = name;
        this.bindingCase = bindingCase;
        this.loweredName$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)new Function0<String>(this){
            final /* synthetic */ BindingName this$0;

            @NotNull
            public final String invoke() {
                String string = this.this$0.getName();
                boolean bl = false;
                String string2 = string;
                if (string2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string3 = string2.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
                return string3;
            }
            {
                this.this$0 = bindingName;
                super(0);
            }
        });
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final BindingCase component2() {
        return this.bindingCase;
    }

    @NotNull
    public final BindingName copy(@NotNull String name, @NotNull BindingCase bindingCase) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull((Object)bindingCase, "bindingCase");
        return new BindingName(name, bindingCase);
    }

    public static /* synthetic */ BindingName copy$default(BindingName bindingName, String string, BindingCase bindingCase, int n, Object object) {
        if ((n & 1) != 0) {
            string = bindingName.name;
        }
        if ((n & 2) != 0) {
            bindingCase = bindingName.bindingCase;
        }
        return bindingName.copy(string, bindingCase);
    }

    @NotNull
    public String toString() {
        return "BindingName(name=" + this.name + ", bindingCase=" + (Object)((Object)this.bindingCase) + ")";
    }

    public int hashCode() {
        String string = this.name;
        BindingCase bindingCase = this.bindingCase;
        return (string != null ? string.hashCode() : 0) * 31 + (bindingCase != null ? ((Object)((Object)bindingCase)).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof BindingName)) break block3;
                BindingName bindingName = (BindingName)object;
                if (!Intrinsics.areEqual(this.name, bindingName.name) || !Intrinsics.areEqual((Object)this.bindingCase, (Object)bindingName.bindingCase)) break block3;
            }
            return true;
        }
        return false;
    }
}

