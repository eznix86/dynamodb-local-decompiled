/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.binding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BindingName;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.binding.Alias;
import org.partiql.lang.eval.binding.LocalsBinder;
import org.partiql.lang.eval.binding.LocalsBinderKt;
import org.partiql.lang.eval.binding.LocalsBinderKt$WhenMappings;
import org.partiql.lang.util.BindingHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005\u00a8\u0006\u0006"}, d2={"localsBinder", "Lorg/partiql/lang/eval/binding/LocalsBinder;", "", "Lorg/partiql/lang/eval/binding/Alias;", "missingValue", "Lorg/partiql/lang/eval/ExprValue;", "lang"})
public final class LocalsBinderKt {
    @NotNull
    public static final LocalsBinder localsBinder(@NotNull List<Alias> $this$localsBinder, @NotNull ExprValue missingValue) {
        Function1 function1;
        Intrinsics.checkParameterIsNotNull($this$localsBinder, "$this$localsBinder");
        Intrinsics.checkParameterIsNotNull(missingValue, "missingValue");
        Function1<Function1<? super String, ? extends String>, Map<String, ? extends Function1<? super List<? extends ExprValue>, ? extends ExprValue>>> $fun$compileBindings$1 = new Function1<Function1<? super String, ? extends String>, Map<String, ? extends Function1<? super List<? extends ExprValue>, ? extends ExprValue>>>($this$localsBinder, missingValue){
            final /* synthetic */ List $this_localsBinder;
            final /* synthetic */ ExprValue $missingValue;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Map<String, Function1<List<? extends ExprValue>, ExprValue>> invoke(@NotNull Function1<? super String, String> keyMangler) {
                @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00001\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002*\u0001\u0000\b\u008a\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\u0010\u0004\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\u0002\u0010\bJ\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\u001b\u0010\u000e\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\u00070\u0005H\u00c6\u0003J4\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u001a\b\u0002\u0010\u0004\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\u00070\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0010J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0003H\u00d6\u0001R#\u0010\u0004\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0017"}, d2={"org/partiql/lang/eval/binding/LocalsBinderKt$localsBinder$1$Binder", "", "name", "", "func", "Lkotlin/Function1;", "", "Lorg/partiql/lang/eval/ExprValue;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "getFunc", "()Lkotlin/jvm/functions/Function1;", "getName", "()Ljava/lang/String;", "component1", "component2", "copy", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Lorg/partiql/lang/eval/binding/LocalsBinderKt$localsBinder$1$Binder;", "equals", "", "other", "hashCode", "", "toString", "lang"})
                public final class Binder {
                    @NotNull
                    private final String name;
                    @NotNull
                    private final Function1<List<? extends ExprValue>, ExprValue> func;

                    @NotNull
                    public final String getName() {
                        return this.name;
                    }

                    @NotNull
                    public final Function1<List<? extends ExprValue>, ExprValue> getFunc() {
                        return this.func;
                    }

                    public Binder(@NotNull String name, @NotNull Function1<? super List<? extends ExprValue>, ? extends ExprValue> func) {
                        Intrinsics.checkParameterIsNotNull(name, "name");
                        Intrinsics.checkParameterIsNotNull(func, "func");
                        this.name = name;
                        this.func = func;
                    }

                    @NotNull
                    public final String component1() {
                        return this.name;
                    }

                    @NotNull
                    public final Function1<List<? extends ExprValue>, ExprValue> component2() {
                        return this.func;
                    }

                    @NotNull
                    public final Binder copy(@NotNull String name, @NotNull Function1<? super List<? extends ExprValue>, ? extends ExprValue> func) {
                        Intrinsics.checkParameterIsNotNull(name, "name");
                        Intrinsics.checkParameterIsNotNull(func, "func");
                        return new Binder(name, func);
                    }

                    public static /* synthetic */ Binder copy$default(Binder binder, String string, Function1 function1, int n, Object object) {
                        if ((n & 1) != 0) {
                            string = binder.name;
                        }
                        if ((n & 2) != 0) {
                            function1 = binder.func;
                        }
                        return binder.copy(string, function1);
                    }

                    @NotNull
                    public String toString() {
                        return "Binder(name=" + this.name + ", func=" + this.func + ")";
                    }

                    public int hashCode() {
                        String string = this.name;
                        Function1<List<? extends ExprValue>, ExprValue> function1 = this.func;
                        return (string != null ? string.hashCode() : 0) * 31 + (function1 != null ? function1.hashCode() : 0);
                    }

                    public boolean equals(@Nullable Object object) {
                        block3: {
                            block2: {
                                if (this == object) break block2;
                                if (!(object instanceof Binder)) break block3;
                                Binder binder = (Binder)object;
                                if (!Intrinsics.areEqual(this.name, binder.name) || !Intrinsics.areEqual(this.func, binder.func)) break block3;
                            }
                            return true;
                        }
                        return false;
                    }
                }
                void $this$mapValuesTo$iv$iv;
                Object key$iv$iv;
                Object $this$groupByTo$iv$iv;
                Object object;
                Object object2;
                Alias alias;
                Sequence<T> $this$mapIndexedTo$iv$iv;
                Intrinsics.checkParameterIsNotNull(keyMangler, "keyMangler");
                Iterable $this$mapIndexed$iv = this.$this_localsBinder;
                boolean $i$f$mapIndexed = false;
                Iterable iterable = $this$mapIndexed$iv;
                Object destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
                boolean $i$f$mapIndexedTo = false;
                int index$iv$iv22 = 0;
                Iterator<T> iterator2 = $this$mapIndexedTo$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    void index;
                    void alias2;
                    T item$iv$iv = iterator2.next();
                    int n = index$iv$iv22++;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    if (n < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    int n2 = n;
                    alias = (Alias)item$iv$iv;
                    int n3 = n2;
                    object2 = collection;
                    boolean bl2 = false;
                    object = SequencesKt.sequenceOf(new Binder(alias2.getAsName(), (Function1<? super List<? extends ExprValue>, ? extends ExprValue>)new Function1<List<? extends ExprValue>, ExprValue>((int)index){
                        final /* synthetic */ int $index;

                        @NotNull
                        public final ExprValue invoke(@NotNull List<? extends ExprValue> it) {
                            Intrinsics.checkParameterIsNotNull(it, "it");
                            return it.get(this.$index);
                        }
                        {
                            this.$index = n;
                            super(1);
                        }
                    }), alias2.getAtName() == null ? null : new Binder(alias2.getAtName(), (Function1<? super List<? extends ExprValue>, ? extends ExprValue>)new Function1<List<? extends ExprValue>, ExprValue>((int)index, this){
                        final /* synthetic */ int $index;
                        final /* synthetic */ localsBinder.1 this$0;
                        {
                            this.$index = n;
                            this.this$0 = var2_2;
                            super(1);
                        }

                        @NotNull
                        public final ExprValue invoke(@NotNull List<? extends ExprValue> it) {
                            Intrinsics.checkParameterIsNotNull(it, "it");
                            ExprValue exprValue2 = ExprValueExtensionsKt.getName(it.get(this.$index));
                            if (exprValue2 == null) {
                                exprValue2 = this.this$0.$missingValue;
                            }
                            return exprValue2;
                        }
                    }), alias2.getByName() == null ? null : new Binder(alias2.getByName(), (Function1<? super List<? extends ExprValue>, ? extends ExprValue>)new Function1<List<? extends ExprValue>, ExprValue>((int)index, this){
                        final /* synthetic */ int $index;
                        final /* synthetic */ localsBinder.1 this$0;
                        {
                            this.$index = n;
                            this.this$0 = var2_2;
                            super(1);
                        }

                        @NotNull
                        public final ExprValue invoke(@NotNull List<? extends ExprValue> it) {
                            Intrinsics.checkParameterIsNotNull(it, "it");
                            ExprValue exprValue2 = ExprValueExtensionsKt.getAddress(it.get(this.$index));
                            if (exprValue2 == null) {
                                exprValue2 = this.this$0.$missingValue;
                            }
                            return exprValue2;
                        }
                    }));
                    object2.add(object);
                }
                Sequence<T> $this$groupBy$iv = SequencesKt.filterNotNull(SequencesKt.flatten(CollectionsKt.asSequence((List)destination$iv$iv)));
                boolean $i$f$groupBy = false;
                $this$mapIndexedTo$iv$iv = $this$groupBy$iv;
                destination$iv$iv = new LinkedHashMap<K, V>();
                boolean $i$f$groupByTo = false;
                Iterator<T> index$iv$iv22 = $this$groupByTo$iv$iv.iterator();
                while (index$iv$iv22.hasNext()) {
                    Object object3;
                    T element$iv$iv = index$iv$iv22.next();
                    Binder it = (Binder)element$iv$iv;
                    boolean bl = false;
                    key$iv$iv = keyMangler.invoke(it.getName());
                    Object $this$getOrPut$iv$iv$iv = destination$iv$iv;
                    boolean $i$f$getOrPut = false;
                    V value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
                    if (value$iv$iv$iv == null) {
                        boolean bl3 = false;
                        ArrayList<E> answer$iv$iv$iv = new ArrayList<E>();
                        $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                        object3 = answer$iv$iv$iv;
                    } else {
                        object3 = value$iv$iv$iv;
                    }
                    List list$iv$iv = (List)object3;
                    list$iv$iv.add(element$iv$iv);
                }
                Object $this$mapValues$iv = destination$iv$iv;
                boolean $i$f$mapValues = false;
                $this$groupByTo$iv$iv = $this$mapValues$iv;
                destination$iv$iv = new LinkedHashMap<K, V>(MapsKt.mapCapacity($this$mapValues$iv.size()));
                boolean $i$f$mapValuesTo = false;
                Iterable $this$associateByTo$iv$iv$iv = $this$mapValuesTo$iv$iv.entrySet();
                boolean $i$f$associateByTo = false;
                for (T element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
                    Function1 function1;
                    void $dstr$name$binders;
                    void it$iv$iv;
                    key$iv$iv = (Map.Entry)element$iv$iv$iv;
                    Object object4 = destination$iv$iv;
                    boolean bl = false;
                    alias = it$iv$iv.getKey();
                    Map.Entry answer$iv$iv$iv = (Map.Entry)element$iv$iv$iv;
                    object = alias;
                    object2 = object4;
                    boolean bl4 = false;
                    void var17_29 = $dstr$name$binders;
                    boolean bl5 = false;
                    String name = (String)var17_29.getKey();
                    var17_29 = $dstr$name$binders;
                    bl5 = false;
                    List binders = (List)var17_29.getValue();
                    switch (binders.size()) {
                        case 1: {
                            function1 = ((Binder)binders.get(0)).getFunc();
                            break;
                        }
                        default: {
                            function1 = new Function1(name, binders){
                                final /* synthetic */ String $name;
                                final /* synthetic */ List $binders;

                                /*
                                 * WARNING - void declaration
                                 */
                                @NotNull
                                public final Void invoke(@NotNull List<? extends ExprValue> $noName_0) {
                                    Collection<String> collection;
                                    void $this$mapTo$iv$iv;
                                    void $this$map$iv;
                                    Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
                                    Iterable iterable = this.$binders;
                                    String string = this.$name;
                                    boolean $i$f$map = false;
                                    void var4_5 = $this$map$iv;
                                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                                    boolean $i$f$mapTo = false;
                                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                                        void it;
                                        Binder binder = (Binder)item$iv$iv;
                                        collection = destination$iv$iv;
                                        boolean bl = false;
                                        String string2 = it.getName();
                                        collection.add(string2);
                                    }
                                    collection = (List)destination$iv$iv;
                                    Void void_ = BindingHelpersKt.errAmbiguousBinding(string, (List<String>)collection);
                                    throw null;
                                }
                                {
                                    this.$name = string;
                                    this.$binders = list;
                                    super(1);
                                }
                            };
                        }
                    }
                    Function1 function12 = function1;
                    object2.put(object, function12);
                }
                return destination$iv$iv;
            }

            public static /* synthetic */ Map invoke$default(localsBinder.1 var0, Function1 function1, int n, Object object) {
                if ((n & 1) != 0) {
                    function1 = localsBinder.1.INSTANCE;
                }
                return var0.invoke(function1);
            }
            {
                this.$this_localsBinder = list;
                this.$missingValue = exprValue2;
                super(1);
            }
        };
        Collection collection = $this$localsBinder;
        boolean bl = false;
        switch (collection.size()) {
            case 0: {
                function1 = localsBinder.dynamicLocalsBinder.1.INSTANCE;
                break;
            }
            case 1: {
                function1 = localsBinder.dynamicLocalsBinder.2.INSTANCE;
                break;
            }
            default: {
                function1 = localsBinder.dynamicLocalsBinder.3.INSTANCE;
            }
        }
        Function1 dynamicLocalsBinder2 = function1;
        return new LocalsBinder(dynamicLocalsBinder2, $fun$compileBindings$1){
            @NotNull
            private final Map<String, Function1<List<? extends ExprValue>, ExprValue>> caseSensitiveBindings;
            @NotNull
            private final Map<String, Function1<List<? extends ExprValue>, ExprValue>> caseInsensitiveBindings;
            final /* synthetic */ Function1 $dynamicLocalsBinder;
            final /* synthetic */ localsBinder.1 $compileBindings$1;

            @NotNull
            public final Map<String, Function1<List<? extends ExprValue>, ExprValue>> getCaseSensitiveBindings() {
                return this.caseSensitiveBindings;
            }

            @NotNull
            public final Map<String, Function1<List<? extends ExprValue>, ExprValue>> getCaseInsensitiveBindings() {
                return this.caseInsensitiveBindings;
            }

            @NotNull
            public Function1<List<? extends ExprValue>, ExprValue> binderForName(@NotNull BindingName bindingName) {
                Function1<List<? extends ExprValue>, ExprValue> function1;
                Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
                switch (LocalsBinderKt$WhenMappings.$EnumSwitchMapping$0[bindingName.getBindingCase().ordinal()]) {
                    case 1: {
                        String string = bindingName.getName();
                        Map<String, Function1<List<? extends ExprValue>, ExprValue>> map2 = this.caseInsensitiveBindings;
                        boolean bl = false;
                        String string2 = string;
                        if (string2 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string3 = string2.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
                        String string4 = string3;
                        function1 = map2.get(string4);
                        break;
                    }
                    case 2: {
                        function1 = this.caseSensitiveBindings.get(bindingName.getName());
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                Function1<List<? extends ExprValue>, ExprValue> function12 = function1;
                if (function1 == null) {
                    function12 = (Function1<List<? extends ExprValue>, ExprValue>)this.$dynamicLocalsBinder.invoke(bindingName);
                }
                return function12;
            }
            {
                this.$dynamicLocalsBinder = $captured_local_variable$0;
                this.$compileBindings$1 = $captured_local_variable$1;
                this.caseSensitiveBindings = localsBinder.1.invoke$default($captured_local_variable$1, null, 1, null);
                this.caseInsensitiveBindings = $captured_local_variable$1.invoke(localsBinder.caseInsensitiveBindings.1.INSTANCE);
            }
        };
    }
}

