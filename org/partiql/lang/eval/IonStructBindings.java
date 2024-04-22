/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonStruct;
import com.amazon.ion.IonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BindingName;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.IonStructBindings$WhenMappings;
import org.partiql.lang.util.BindingHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0017\u001a\u00020\nH\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0017\u001a\u00020\nH\u0002J\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001a\u001a\u00020\u001bH\u0096\u0002J \u0010\u001c\u001a\u0004\u0018\u00010\f2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\f0\u001e2\u0006\u0010\u0017\u001a\u00020\nH\u0002RW\u0010\b\u001a>\u0012\u0004\u0012\u00020\n\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\r0\tj\u001e\u0012\u0004\u0012\u00020\n\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\r`\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010RW\u0010\u0013\u001a>\u0012\u0004\u0012\u00020\n\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\r0\tj\u001e\u0012\u0004\u0012\u00020\n\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\r`\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0012\u001a\u0004\b\u0014\u0010\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2={"Lorg/partiql/lang/eval/IonStructBindings;", "Lorg/partiql/lang/eval/Bindings;", "Lorg/partiql/lang/eval/ExprValue;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "myStruct", "Lcom/amazon/ion/IonStruct;", "(Lorg/partiql/lang/eval/ExprValueFactory;Lcom/amazon/ion/IonStruct;)V", "caseInsensitiveFieldMap", "Ljava/util/HashMap;", "", "Ljava/util/ArrayList;", "Lcom/amazon/ion/IonValue;", "Lkotlin/collections/ArrayList;", "Lkotlin/collections/HashMap;", "getCaseInsensitiveFieldMap", "()Ljava/util/HashMap;", "caseInsensitiveFieldMap$delegate", "Lkotlin/Lazy;", "caseSensitiveFieldMap", "getCaseSensitiveFieldMap", "caseSensitiveFieldMap$delegate", "caseInsensitiveLookup", "fieldName", "caseSensitiveLookup", "get", "bindingName", "Lorg/partiql/lang/eval/BindingName;", "handleMatches", "entries", "", "lang"})
public final class IonStructBindings
implements Bindings<ExprValue> {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final Lazy caseInsensitiveFieldMap$delegate;
    private final Lazy caseSensitiveFieldMap$delegate;
    private final ExprValueFactory valueFactory;
    private final IonStruct myStruct;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(IonStructBindings.class), "caseInsensitiveFieldMap", "getCaseInsensitiveFieldMap()Ljava/util/HashMap;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(IonStructBindings.class), "caseSensitiveFieldMap", "getCaseSensitiveFieldMap()Ljava/util/HashMap;"))};
    }

    private final HashMap<String, ArrayList<IonValue>> getCaseInsensitiveFieldMap() {
        Lazy lazy = this.caseInsensitiveFieldMap$delegate;
        IonStructBindings ionStructBindings = this;
        KProperty kProperty = $$delegatedProperties[0];
        boolean bl = false;
        return (HashMap)lazy.getValue();
    }

    private final HashMap<String, ArrayList<IonValue>> getCaseSensitiveFieldMap() {
        Lazy lazy = this.caseSensitiveFieldMap$delegate;
        IonStructBindings ionStructBindings = this;
        KProperty kProperty = $$delegatedProperties[1];
        boolean bl = false;
        return (HashMap)lazy.getValue();
    }

    private final IonValue caseSensitiveLookup(String fieldName) {
        IonValue ionValue2;
        ArrayList<IonValue> arrayList = this.getCaseSensitiveFieldMap().get(fieldName);
        if (arrayList != null) {
            ArrayList<IonValue> arrayList2 = arrayList;
            boolean bl = false;
            boolean bl2 = false;
            ArrayList<IonValue> entries = arrayList2;
            boolean bl3 = false;
            ArrayList<IonValue> arrayList3 = entries;
            Intrinsics.checkExpressionValueIsNotNull(arrayList3, "entries");
            ionValue2 = this.handleMatches((List<? extends IonValue>)arrayList3, fieldName);
        } else {
            ionValue2 = null;
        }
        return ionValue2;
    }

    private final IonValue caseInsensitiveLookup(String fieldName) {
        IonValue ionValue2;
        Object object = fieldName;
        HashMap<String, ArrayList<IonValue>> hashMap = this.getCaseInsensitiveFieldMap();
        boolean bl = false;
        String string = object;
        if (string == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String string2 = string.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).toLowerCase()");
        String string3 = string2;
        ArrayList<IonValue> arrayList = hashMap.get(string3);
        if (arrayList != null) {
            object = arrayList;
            bl = false;
            boolean bl2 = false;
            Object entries = object;
            boolean bl3 = false;
            Object object2 = entries;
            Intrinsics.checkExpressionValueIsNotNull(object2, "entries");
            ionValue2 = this.handleMatches((List)object2, fieldName);
        } else {
            ionValue2 = null;
        }
        return ionValue2;
    }

    /*
     * WARNING - void declaration
     */
    private final IonValue handleMatches(List<? extends IonValue> entries, String fieldName) {
        IonValue ionValue2;
        switch (entries.size()) {
            case 0: {
                ionValue2 = null;
                break;
            }
            case 1: {
                ionValue2 = entries.get(0);
                break;
            }
            default: {
                Collection<String> collection;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                Iterable iterable = entries;
                String string = fieldName;
                boolean $i$f$map = false;
                void var5_6 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    IonValue ionValue3 = (IonValue)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    String string2 = it.getFieldName();
                    collection.add(string2);
                }
                collection = (List)destination$iv$iv;
                Void void_ = BindingHelpersKt.errAmbiguousBinding(string, (List<String>)collection);
                throw null;
            }
        }
        return ionValue2;
    }

    @Override
    @Nullable
    public ExprValue get(@NotNull BindingName bindingName) {
        ExprValue exprValue2;
        IonValue ionValue2;
        Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
        switch (IonStructBindings$WhenMappings.$EnumSwitchMapping$0[bindingName.getBindingCase().ordinal()]) {
            case 1: {
                ionValue2 = this.caseSensitiveLookup(bindingName.getName());
                break;
            }
            case 2: {
                ionValue2 = this.caseInsensitiveLookup(bindingName.getName());
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        if (ionValue2 != null) {
            IonValue ionValue3 = ionValue2;
            boolean bl = false;
            boolean bl2 = false;
            IonValue it = ionValue3;
            boolean bl3 = false;
            exprValue2 = this.valueFactory.newFromIonValue(it);
        } else {
            exprValue2 = null;
        }
        return exprValue2;
    }

    public IonStructBindings(@NotNull ExprValueFactory valueFactory, @NotNull IonStruct myStruct) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        Intrinsics.checkParameterIsNotNull(myStruct, "myStruct");
        this.valueFactory = valueFactory;
        this.myStruct = myStruct;
        this.caseInsensitiveFieldMap$delegate = LazyKt.lazy((Function0)new Function0<HashMap<String, ArrayList<IonValue>>>(this){
            final /* synthetic */ IonStructBindings this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final HashMap<String, ArrayList<IonValue>> invoke() {
                HashMap<String, ArrayList<IonValue>> hashMap = new HashMap<String, ArrayList<IonValue>>();
                boolean bl = false;
                boolean bl2 = false;
                HashMap<String, ArrayList<IonValue>> $this$apply = hashMap;
                boolean bl3 = false;
                for (IonValue field : IonStructBindings.access$getMyStruct$p(this.this$0)) {
                    Object object;
                    String key$iv;
                    void $this$getOrPut$iv;
                    String string;
                    Map map2 = $this$apply;
                    IonValue ionValue2 = field;
                    Intrinsics.checkExpressionValueIsNotNull(ionValue2, "field");
                    Intrinsics.checkExpressionValueIsNotNull(ionValue2.getFieldName(), "field.fieldName");
                    boolean bl4 = false;
                    String string2 = string;
                    if (string2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    Intrinsics.checkExpressionValueIsNotNull(string2.toLowerCase(), "(this as java.lang.String).toLowerCase()");
                    boolean $i$f$getOrPut = false;
                    V value$iv = $this$getOrPut$iv.get(key$iv);
                    if (value$iv == null) {
                        boolean bl5 = false;
                        ArrayList<E> answer$iv = new ArrayList<E>(1);
                        $this$getOrPut$iv.put(key$iv, answer$iv);
                        object = answer$iv;
                    } else {
                        object = value$iv;
                    }
                    ArrayList entries = (ArrayList)object;
                    entries.add(field);
                }
                return hashMap;
            }
            {
                this.this$0 = ionStructBindings;
                super(0);
            }
        });
        this.caseSensitiveFieldMap$delegate = LazyKt.lazy((Function0)new Function0<HashMap<String, ArrayList<IonValue>>>(this){
            final /* synthetic */ IonStructBindings this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final HashMap<String, ArrayList<IonValue>> invoke() {
                HashMap<String, ArrayList<IonValue>> hashMap = new HashMap<String, ArrayList<IonValue>>();
                boolean bl = false;
                boolean bl2 = false;
                HashMap<String, ArrayList<IonValue>> $this$apply = hashMap;
                boolean bl3 = false;
                for (IonValue field : IonStructBindings.access$getMyStruct$p(this.this$0)) {
                    Object object;
                    String key$iv;
                    void $this$getOrPut$iv;
                    Map map2 = $this$apply;
                    IonValue ionValue2 = field;
                    Intrinsics.checkExpressionValueIsNotNull(ionValue2, "field");
                    Intrinsics.checkExpressionValueIsNotNull(ionValue2.getFieldName(), "field.fieldName");
                    boolean $i$f$getOrPut = false;
                    V value$iv = $this$getOrPut$iv.get(key$iv);
                    if (value$iv == null) {
                        boolean bl4 = false;
                        ArrayList<E> answer$iv = new ArrayList<E>(1);
                        $this$getOrPut$iv.put(key$iv, answer$iv);
                        object = answer$iv;
                    } else {
                        object = value$iv;
                    }
                    ArrayList entries = (ArrayList)object;
                    entries.add(field);
                }
                return hashMap;
            }
            {
                this.this$0 = ionStructBindings;
                super(0);
            }
        });
    }

    public static final /* synthetic */ IonStruct access$getMyStruct$p(IonStructBindings $this) {
        return $this.myStruct;
    }
}

