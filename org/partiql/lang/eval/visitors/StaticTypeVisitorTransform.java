/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazon.ionelement.api.IonElement
 *  com.amazon.ionelement.api.IonMeta
 *  com.amazon.ionelement.api.IonUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.partiql.pig.runtime.SymbolPrimitive
 */
package org.partiql.lang.eval.visitors;

import com.amazon.ion.IonString;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import com.amazon.ionelement.api.IonElement;
import com.amazon.ionelement.api.IonMeta;
import com.amazon.ionelement.api.IonUtils;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.StaticTypeMeta;
import org.partiql.lang.ast.passes.SemanticException;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.domains.UtilKt;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.BindingCase;
import org.partiql.lang.eval.BindingName;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.BindingsExtensionsKt;
import org.partiql.lang.eval.visitors.StaticTypeVisitorTransform;
import org.partiql.lang.eval.visitors.StaticTypeVisitorTransform$VisitorTransform$WhenMappings;
import org.partiql.lang.eval.visitors.StaticTypeVisitorTransformConstraints;
import org.partiql.lang.eval.visitors.VisitorTransformBase;
import org.partiql.lang.types.StaticType;
import org.partiql.lang.util.PropertyMapHelpersKt;
import org.partiql.pig.runtime.SymbolPrimitive;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0005\u0017\u0018\u0019\u001a\u001bB+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform;", "Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "ion", "Lcom/amazon/ion/IonSystem;", "globalBindings", "Lorg/partiql/lang/eval/Bindings;", "Lorg/partiql/lang/types/StaticType;", "constraints", "", "Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransformConstraints;", "(Lcom/amazon/ion/IonSystem;Lorg/partiql/lang/eval/Bindings;Ljava/util/Set;)V", "globalEnv", "Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$TypeAndDepth;", "preventGlobalsExceptInFrom", "", "preventGlobalsInNestedQueries", "transformStatement", "Lorg/partiql/lang/domains/PartiqlAst$Statement;", "node", "wrapBindings", "bindings", "depth", "", "BindingScope", "ScopeSearchOrder", "TypeAndDepth", "TypeAndScope", "VisitorTransform", "lang"})
public final class StaticTypeVisitorTransform
extends VisitorTransformBase {
    private final Bindings<TypeAndDepth> globalEnv;
    private final boolean preventGlobalsExceptInFrom;
    private final boolean preventGlobalsInNestedQueries;
    private final IonSystem ion;

    @Override
    @NotNull
    public PartiqlAst.Statement transformStatement(@NotNull PartiqlAst.Statement node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new VisitorTransform(this.wrapBindings(Bindings.Companion.empty(), 1), 0).transformStatement(node);
    }

    private final Bindings<TypeAndDepth> wrapBindings(Bindings<StaticType> bindings2, int depth) {
        return Bindings.Companion.over((Function1)new Function1<BindingName, TypeAndDepth>(bindings2, depth){
            final /* synthetic */ Bindings $bindings;
            final /* synthetic */ int $depth;

            @Nullable
            public final TypeAndDepth invoke(@NotNull BindingName name) {
                TypeAndDepth typeAndDepth;
                Intrinsics.checkParameterIsNotNull(name, "name");
                StaticType staticType = (StaticType)this.$bindings.get(name);
                if (staticType != null) {
                    StaticType staticType2 = staticType;
                    boolean bl = false;
                    boolean bl2 = false;
                    StaticType bind = staticType2;
                    boolean bl3 = false;
                    typeAndDepth = new TypeAndDepth(bind, this.$depth);
                } else {
                    typeAndDepth = null;
                }
                return typeAndDepth;
            }
            {
                this.$bindings = bindings2;
                this.$depth = n;
                super(1);
            }
        });
    }

    public StaticTypeVisitorTransform(@NotNull IonSystem ion, @NotNull Bindings<StaticType> globalBindings, @NotNull Set<? extends StaticTypeVisitorTransformConstraints> constraints) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        Intrinsics.checkParameterIsNotNull(globalBindings, "globalBindings");
        Intrinsics.checkParameterIsNotNull(constraints, "constraints");
        this.ion = ion;
        this.globalEnv = this.wrapBindings(globalBindings, 0);
        this.preventGlobalsExceptInFrom = constraints.contains((Object)StaticTypeVisitorTransformConstraints.PREVENT_GLOBALS_EXCEPT_IN_FROM);
        this.preventGlobalsInNestedQueries = constraints.contains((Object)StaticTypeVisitorTransformConstraints.PREVENT_GLOBALS_IN_NESTED_QUERIES);
    }

    public /* synthetic */ StaticTypeVisitorTransform(IonSystem ionSystem, Bindings bindings2, Set set2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            boolean bl = false;
            set2 = SetsKt.emptySet();
        }
        this(ionSystem, bindings2, set2);
    }

    public static final /* synthetic */ IonSystem access$getIon$p(StaticTypeVisitorTransform $this) {
        return $this.ion;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2={"Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$TypeAndDepth;", "", "type", "Lorg/partiql/lang/types/StaticType;", "depth", "", "(Lorg/partiql/lang/types/StaticType;I)V", "getDepth", "()I", "getType", "()Lorg/partiql/lang/types/StaticType;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "lang"})
    private static final class TypeAndDepth {
        @NotNull
        private final StaticType type;
        private final int depth;

        @NotNull
        public final StaticType getType() {
            return this.type;
        }

        public final int getDepth() {
            return this.depth;
        }

        public TypeAndDepth(@NotNull StaticType type, int depth) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            this.type = type;
            this.depth = depth;
        }

        @NotNull
        public final StaticType component1() {
            return this.type;
        }

        public final int component2() {
            return this.depth;
        }

        @NotNull
        public final TypeAndDepth copy(@NotNull StaticType type, int depth) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            return new TypeAndDepth(type, depth);
        }

        public static /* synthetic */ TypeAndDepth copy$default(TypeAndDepth typeAndDepth, StaticType staticType, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                staticType = typeAndDepth.type;
            }
            if ((n2 & 2) != 0) {
                n = typeAndDepth.depth;
            }
            return typeAndDepth.copy(staticType, n);
        }

        @NotNull
        public String toString() {
            return "TypeAndDepth(type=" + this.type + ", depth=" + this.depth + ")";
        }

        public int hashCode() {
            StaticType staticType = this.type;
            return (staticType != null ? staticType.hashCode() : 0) * 31 + Integer.hashCode(this.depth);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof TypeAndDepth)) break block3;
                    TypeAndDepth typeAndDepth = (TypeAndDepth)object;
                    if (!Intrinsics.areEqual(this.type, typeAndDepth.type) || this.depth != typeAndDepth.depth) break block3;
                }
                return true;
            }
            return false;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$BindingScope;", "", "(Ljava/lang/String;I)V", "LOCAL", "LEXICAL", "GLOBAL", "lang"})
    private static final class BindingScope
    extends Enum<BindingScope> {
        public static final /* enum */ BindingScope LOCAL;
        public static final /* enum */ BindingScope LEXICAL;
        public static final /* enum */ BindingScope GLOBAL;
        private static final /* synthetic */ BindingScope[] $VALUES;

        static {
            BindingScope[] bindingScopeArray = new BindingScope[3];
            BindingScope[] bindingScopeArray2 = bindingScopeArray;
            bindingScopeArray[0] = LOCAL = new BindingScope();
            bindingScopeArray[1] = LEXICAL = new BindingScope();
            bindingScopeArray[2] = GLOBAL = new BindingScope();
            $VALUES = bindingScopeArray;
        }

        public static BindingScope[] values() {
            return (BindingScope[])$VALUES.clone();
        }

        public static BindingScope valueOf(String string) {
            return Enum.valueOf(BindingScope.class, string);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2={"Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$TypeAndScope;", "", "type", "Lorg/partiql/lang/types/StaticType;", "scope", "Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$BindingScope;", "(Lorg/partiql/lang/types/StaticType;Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$BindingScope;)V", "getScope", "()Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$BindingScope;", "getType", "()Lorg/partiql/lang/types/StaticType;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "lang"})
    private static final class TypeAndScope {
        @NotNull
        private final StaticType type;
        @NotNull
        private final BindingScope scope;

        @NotNull
        public final StaticType getType() {
            return this.type;
        }

        @NotNull
        public final BindingScope getScope() {
            return this.scope;
        }

        public TypeAndScope(@NotNull StaticType type, @NotNull BindingScope scope) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull((Object)scope, "scope");
            this.type = type;
            this.scope = scope;
        }

        @NotNull
        public final StaticType component1() {
            return this.type;
        }

        @NotNull
        public final BindingScope component2() {
            return this.scope;
        }

        @NotNull
        public final TypeAndScope copy(@NotNull StaticType type, @NotNull BindingScope scope) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull((Object)scope, "scope");
            return new TypeAndScope(type, scope);
        }

        public static /* synthetic */ TypeAndScope copy$default(TypeAndScope typeAndScope, StaticType staticType, BindingScope bindingScope, int n, Object object) {
            if ((n & 1) != 0) {
                staticType = typeAndScope.type;
            }
            if ((n & 2) != 0) {
                bindingScope = typeAndScope.scope;
            }
            return typeAndScope.copy(staticType, bindingScope);
        }

        @NotNull
        public String toString() {
            return "TypeAndScope(type=" + this.type + ", scope=" + (Object)((Object)this.scope) + ")";
        }

        public int hashCode() {
            StaticType staticType = this.type;
            BindingScope bindingScope = this.scope;
            return (staticType != null ? staticType.hashCode() : 0) * 31 + (bindingScope != null ? ((Object)((Object)bindingScope)).hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof TypeAndScope)) break block3;
                    TypeAndScope typeAndScope = (TypeAndScope)object;
                    if (!Intrinsics.areEqual(this.type, typeAndScope.type) || !Intrinsics.areEqual((Object)this.scope, (Object)typeAndScope.scope)) break block3;
                }
                return true;
            }
            return false;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$ScopeSearchOrder;", "", "(Ljava/lang/String;I)V", "LEXICAL", "GLOBALS_THEN_LEXICAL", "lang"})
    private static final class ScopeSearchOrder
    extends Enum<ScopeSearchOrder> {
        public static final /* enum */ ScopeSearchOrder LEXICAL;
        public static final /* enum */ ScopeSearchOrder GLOBALS_THEN_LEXICAL;
        private static final /* synthetic */ ScopeSearchOrder[] $VALUES;

        static {
            ScopeSearchOrder[] scopeSearchOrderArray = new ScopeSearchOrder[2];
            ScopeSearchOrder[] scopeSearchOrderArray2 = scopeSearchOrderArray;
            scopeSearchOrderArray[0] = LEXICAL = new ScopeSearchOrder();
            scopeSearchOrderArray[1] = GLOBALS_THEN_LEXICAL = new ScopeSearchOrder();
            $VALUES = scopeSearchOrderArray;
        }

        public static ScopeSearchOrder[] values() {
            return (ScopeSearchOrder[])$VALUES.clone();
        }

        public static ScopeSearchOrder valueOf(String string) {
            return Enum.valueOf(ScopeSearchOrder.class, string);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u00d6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J0\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u000f2\u0016\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001a0\u0019j\u0002`\u001bH\u0002J\f\u0010\u001c\u001a\u00060\u0000R\u00020\u001dH\u0002J(\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u000e2\u0016\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001a0\u0019j\u0002`\u001bH\u0002J(\u0010 \u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u000e2\u0016\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001a0\u0019j\u0002`\u001bH\u0002J(\u0010!\u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u000e2\u0016\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001a0\u0019j\u0002`\u001bH\u0002J.\u0010\"\u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u000e2\u001c\b\u0002\u0010\u0018\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0019j\u0004\u0018\u0001`\u001bH\u0002J\u001a\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0002J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u000e2\u0006\u0010,\u001a\u00020-H\u0002J(\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u00020\u000e2\u0016\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001a0\u0019j\u0002`\u001bH\u0002J\u0010\u00100\u001a\u0002012\u0006\u0010,\u001a\u000202H\u0016J\u0010\u00103\u001a\u0002012\u0006\u0010,\u001a\u000204H\u0016J\u0010\u00105\u001a\u0002062\u0006\u0010,\u001a\u000207H\u0016J\u0010\u00108\u001a\u0002062\u0006\u0010,\u001a\u00020-H\u0016J\u0010\u00109\u001a\u0002062\u0006\u0010,\u001a\u00020*H\u0016J\u0010\u0010:\u001a\u0002062\u0006\u0010,\u001a\u00020;H\u0016J\u0010\u0010<\u001a\u00020=2\u0006\u0010,\u001a\u00020>H\u0016J\u0010\u0010?\u001a\u00020=2\u0006\u0010,\u001a\u00020@H\u0016J\u0010\u0010A\u001a\u0002062\u0006\u0010,\u001a\u00020@H\u0016J\u0010\u0010B\u001a\u00020=2\u0006\u0010,\u001a\u00020CH\u0016J\u0010\u0010D\u001a\u0002062\u0006\u0010,\u001a\u00020CH\u0016J\u0010\u0010E\u001a\u00020F2\u0006\u0010,\u001a\u00020FH\u0016J\u0010\u0010G\u001a\u00020H2\u0006\u0010,\u001a\u00020IH\u0016J\u001c\u0010J\u001a\u0004\u0018\u00010$*\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010%\u001a\u00020&H\u0002J\f\u0010K\u001a\u00020L*\u00020-H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006M"}, d2={"Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$VisitorTransform;", "Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "parentEnv", "Lorg/partiql/lang/eval/Bindings;", "Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$TypeAndDepth;", "currentScopeDepth", "", "(Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform;Lorg/partiql/lang/eval/Bindings;I)V", "containsJoin", "", "currentEnv", "fromVisited", "localsMap", "", "", "Lorg/partiql/lang/types/StaticType;", "localsOnlyEnv", "scopeOrder", "Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$ScopeSearchOrder;", "singleFromSourceName", "addLocal", "", "name", "type", "metas", "", "", "Lcom/amazon/ionelement/api/MetaContainer;", "createTransformerForNestedScope", "Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform;", "errAmbiguousName", "", "errIllegalGlobalVariableAccess", "errUnboundName", "errUnimplementedFeature", "findBind", "Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransform$TypeAndScope;", "bindingName", "Lorg/partiql/lang/eval/BindingName;", "scopeQualifier", "Lorg/partiql/lang/domains/PartiqlAst$ScopeQualifier;", "makePathIntoFromSource", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Path;", "fromSourceAlias", "node", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Id;", "singleFromSourceRef", "sourceName", "transformDdlOpCreateIndex", "Lorg/partiql/lang/domains/PartiqlAst$DdlOp;", "Lorg/partiql/lang/domains/PartiqlAst$DdlOp$CreateIndex;", "transformDdlOpDropIndex", "Lorg/partiql/lang/domains/PartiqlAst$DdlOp$DropIndex;", "transformExprCallAgg", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "Lorg/partiql/lang/domains/PartiqlAst$Expr$CallAgg;", "transformExprId", "transformExprPath", "transformExprSelect", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Select;", "transformFromSourceJoin", "Lorg/partiql/lang/domains/PartiqlAst$FromSource;", "Lorg/partiql/lang/domains/PartiqlAst$FromSource$Join;", "transformFromSourceScan", "Lorg/partiql/lang/domains/PartiqlAst$FromSource$Scan;", "transformFromSourceScan_expr", "transformFromSourceUnpivot", "Lorg/partiql/lang/domains/PartiqlAst$FromSource$Unpivot;", "transformFromSourceUnpivot_expr", "transformGroupBy", "Lorg/partiql/lang/domains/PartiqlAst$GroupBy;", "transformStatementDml", "Lorg/partiql/lang/domains/PartiqlAst$Statement;", "Lorg/partiql/lang/domains/PartiqlAst$Statement$Dml;", "lookupBinding", "toPathExpr", "Lorg/partiql/lang/domains/PartiqlAst$PathStep$PathExpr;", "lang"})
    private final class VisitorTransform
    extends VisitorTransformBase {
        private ScopeSearchOrder scopeOrder;
        private final Map<String, StaticType> localsMap;
        private Bindings<TypeAndDepth> localsOnlyEnv;
        private final Bindings<TypeAndDepth> currentEnv;
        private boolean containsJoin;
        private boolean fromVisited;
        private String singleFromSourceName;
        private final Bindings<TypeAndDepth> parentEnv;
        private final int currentScopeDepth;

        private final PartiqlAst.Expr.Id singleFromSourceRef(String sourceName, Map<String, ? extends Object> metas) {
            TypeAndDepth typeAndDepth = this.currentEnv.get(new BindingName(sourceName, BindingCase.SENSITIVE));
            if (typeAndDepth == null) {
                throw (Throwable)new IllegalArgumentException("Could not find type for single FROM source variable");
            }
            TypeAndDepth sourceType = typeAndDepth;
            return (PartiqlAst.Expr.Id)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Expr.Id>(sourceName, metas, sourceType){
                final /* synthetic */ String $sourceName;
                final /* synthetic */ Map $metas;
                final /* synthetic */ TypeAndDepth $sourceType;

                @NotNull
                public final PartiqlAst.Expr.Id invoke(@NotNull PartiqlAst.Builder $this$build) {
                    Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                    return $this$build.id(this.$sourceName, PartiqlAst.Builder.DefaultImpls.caseSensitive$default($this$build, null, 1, null), PartiqlAst.Builder.DefaultImpls.localsFirst$default($this$build, null, 1, null), MapsKt.plus(this.$metas, IonMeta.metaContainerOf((Pair[])new Pair[]{TuplesKt.to("$static_type", new StaticTypeMeta(this.$sourceType.getType()))})));
                }
                {
                    this.$sourceName = string;
                    this.$metas = map2;
                    this.$sourceType = typeAndDepth;
                    super(1);
                }
            });
        }

        private final PartiqlAst.PathStep.PathExpr toPathExpr(@NotNull PartiqlAst.Expr.Id $this$toPathExpr) {
            return (PartiqlAst.PathStep.PathExpr)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.PathStep.PathExpr>(this, $this$toPathExpr){
                final /* synthetic */ VisitorTransform this$0;
                final /* synthetic */ PartiqlAst.Expr.Id $this_toPathExpr;

                @NotNull
                public final PartiqlAst.PathStep.PathExpr invoke(@NotNull PartiqlAst.Builder $this$build) {
                    Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                    IonString ionString = StaticTypeVisitorTransform.access$getIon$p(this.this$0.StaticTypeVisitorTransform.this).newString(this.$this_toPathExpr.getName().getText());
                    Intrinsics.checkExpressionValueIsNotNull(ionString, "ion.newString(name.text)");
                    return PartiqlAst.Builder.DefaultImpls.pathExpr$default($this$build, $this$build.lit((IonElement)IonUtils.toIonElement((IonValue)ionString), UtilKt.extractSourceLocation(this.$this_toPathExpr)), this.$this_toPathExpr.getCase(), null, 4, null);
                }
                {
                    this.this$0 = visitorTransform;
                    this.$this_toPathExpr = id;
                    super(1);
                }
            });
        }

        private final Void errUnboundName(String name, Map<String, ? extends Object> metas) {
            throw (Throwable)new SemanticException("No such variable named '" + name + '\'', ErrorCode.SEMANTIC_UNBOUND_BINDING, UtilKt.addSourceLocation(PropertyMapHelpersKt.propertyValueMapOf(TuplesKt.to(Property.BINDING_NAME, name)), metas), null, 8, null);
        }

        private final Void errIllegalGlobalVariableAccess(String name, Map<String, ? extends Object> metas) {
            throw (Throwable)new SemanticException("Global variable access is illegal in this context", ErrorCode.SEMANTIC_ILLEGAL_GLOBAL_VARIABLE_ACCESS, UtilKt.addSourceLocation(PropertyMapHelpersKt.propertyValueMapOf(TuplesKt.to(Property.BINDING_NAME, name)), metas), null, 8, null);
        }

        private final Void errAmbiguousName(String name, Map<String, ? extends Object> metas) {
            throw (Throwable)new SemanticException("A variable named '" + name + "' was already defined in this scope", ErrorCode.SEMANTIC_AMBIGUOUS_BINDING, UtilKt.addSourceLocation(PropertyMapHelpersKt.propertyValueMapOf(TuplesKt.to(Property.BINDING_NAME, name)), metas), null, 8, null);
        }

        private final Void errUnimplementedFeature(String name, Map<String, ? extends Object> metas) {
            PropertyValueMap propertyValueMap = PropertyMapHelpersKt.propertyValueMapOf(TuplesKt.to(Property.FEATURE_NAME, name));
            ErrorCode errorCode = ErrorCode.UNIMPLEMENTED_FEATURE;
            String string = "Feature not implemented yet";
            boolean bl = false;
            boolean bl2 = false;
            PropertyValueMap it = propertyValueMap;
            boolean bl3 = false;
            if (metas != null) {
                UtilKt.addSourceLocation(it, metas);
            }
            PropertyValueMap propertyValueMap2 = propertyValueMap;
            DefaultConstructorMarker defaultConstructorMarker = null;
            int n = 8;
            Throwable throwable = null;
            PropertyValueMap propertyValueMap3 = propertyValueMap2;
            ErrorCode errorCode2 = errorCode;
            String string2 = string;
            throw (Throwable)new SemanticException(string2, errorCode2, propertyValueMap3, throwable, n, defaultConstructorMarker);
        }

        static /* synthetic */ Void errUnimplementedFeature$default(VisitorTransform visitorTransform, String string, Map map2, int n, Object object) {
            if ((n & 2) != 0) {
                map2 = null;
            }
            return visitorTransform.errUnimplementedFeature(string, map2);
        }

        private final void addLocal(String name, StaticType type, Map<String, ? extends Object> metas) {
            TypeAndDepth existing = this.localsOnlyEnv.get(new BindingName(name, BindingCase.INSENSITIVE));
            if (existing != null) {
                Void void_ = this.errAmbiguousName(name, metas);
                throw null;
            }
            this.localsMap.put(name, type);
            this.localsOnlyEnv = StaticTypeVisitorTransform.this.wrapBindings(Bindings.Companion.ofMap(this.localsMap), this.currentScopeDepth);
        }

        @Override
        @NotNull
        public PartiqlAst.Expr transformExprCallAgg(@NotNull PartiqlAst.Expr.CallAgg node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            return (PartiqlAst.Expr)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Expr.CallAgg>(this, node){
                final /* synthetic */ VisitorTransform this$0;
                final /* synthetic */ PartiqlAst.Expr.CallAgg $node;

                @NotNull
                public final PartiqlAst.Expr.CallAgg invoke(@NotNull PartiqlAst.Builder $this$build) {
                    Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                    return $this$build.callAgg_(this.$node.getSetq(), this.$node.getFuncName(), this.this$0.transformExpr(this.$node.getArg()), this.this$0.transformMetas(this.$node.getMetas()));
                }
                {
                    this.this$0 = visitorTransform;
                    this.$node = callAgg;
                    super(1);
                }
            });
        }

        /*
         * WARNING - void declaration
         */
        private final TypeAndScope lookupBinding(@NotNull Bindings<TypeAndDepth> $this$lookupBinding, BindingName bindingName) {
            TypeAndScope typeAndScope;
            TypeAndDepth match = $this$lookupBinding.get(bindingName);
            if (match == null) {
                typeAndScope = null;
            } else {
                void type;
                BindingScope bindingScope;
                TypeAndDepth typeAndDepth = match;
                StaticType staticType = typeAndDepth.component1();
                int depth = typeAndDepth.component2();
                if (depth == 0) {
                    bindingScope = BindingScope.GLOBAL;
                } else if (depth < this.currentScopeDepth) {
                    bindingScope = BindingScope.LEXICAL;
                } else if (depth == this.currentScopeDepth) {
                    bindingScope = BindingScope.LOCAL;
                } else {
                    String string = "Unexpected: depth should never be > currentScopeDepth";
                    boolean bl = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                BindingScope scope = bindingScope;
                typeAndScope = new TypeAndScope((StaticType)type, scope);
            }
            return typeAndScope;
        }

        private final TypeAndScope findBind(BindingName bindingName, PartiqlAst.ScopeQualifier scopeQualifier) {
            List<Bindings> list;
            ScopeSearchOrder scopeSearchOrder;
            PartiqlAst.ScopeQualifier scopeQualifier2 = scopeQualifier;
            if (scopeQualifier2 instanceof PartiqlAst.ScopeQualifier.LocalsFirst) {
                scopeSearchOrder = ScopeSearchOrder.LEXICAL;
            } else if (scopeQualifier2 instanceof PartiqlAst.ScopeQualifier.Unqualified) {
                scopeSearchOrder = this.scopeOrder;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            ScopeSearchOrder overridenScopeSearchOrder = scopeSearchOrder;
            switch (StaticTypeVisitorTransform$VisitorTransform$WhenMappings.$EnumSwitchMapping$0[overridenScopeSearchOrder.ordinal()]) {
                case 1: {
                    list = CollectionsKt.listOf(StaticTypeVisitorTransform.this.globalEnv, this.currentEnv);
                    break;
                }
                case 2: {
                    list = CollectionsKt.listOf(this.currentEnv, StaticTypeVisitorTransform.this.globalEnv);
                    break;
                }
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            List<Bindings> scopes = list;
            return (TypeAndScope)SequencesKt.firstOrNull(SequencesKt.mapNotNull(CollectionsKt.asSequence((Iterable)scopes), (Function1)new Function1<Bindings<TypeAndDepth>, TypeAndScope>(this, bindingName){
                final /* synthetic */ VisitorTransform this$0;
                final /* synthetic */ BindingName $bindingName;

                @Nullable
                public final TypeAndScope invoke(@NotNull Bindings<TypeAndDepth> it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    return VisitorTransform.access$lookupBinding(this.this$0, it, this.$bindingName);
                }
                {
                    this.this$0 = visitorTransform;
                    this.$bindingName = bindingName;
                    super(1);
                }
            }));
        }

        @Override
        @NotNull
        public PartiqlAst.Expr transformExprId(@NotNull PartiqlAst.Expr.Id node) {
            PartiqlAst.ScopeQualifier scopeQualifier;
            Intrinsics.checkParameterIsNotNull(node, "node");
            BindingName bindingName = new BindingName(node.getName().getText(), UtilKt.toBindingCase(node.getCase()));
            TypeAndScope found = this.findBind(bindingName, node.getQualifier());
            String singleBinding = this.singleFromSourceName;
            if (found == null) {
                if (singleBinding == null) {
                    Void void_ = this.errUnboundName(node.getName().getText(), node.getMetas());
                    throw null;
                }
                return this.makePathIntoFromSource(singleBinding, node);
            }
            if (found.getScope() == BindingScope.GLOBAL) {
                if (singleBinding != null) {
                    return this.makePathIntoFromSource(singleBinding, node);
                }
                if (StaticTypeVisitorTransform.this.preventGlobalsExceptInFrom && this.fromVisited) {
                    Void void_ = this.errIllegalGlobalVariableAccess(bindingName.getName(), node.getMetas());
                    throw null;
                }
                if (StaticTypeVisitorTransform.this.preventGlobalsInNestedQueries && this.currentScopeDepth > 1) {
                    Void void_ = this.errIllegalGlobalVariableAccess(bindingName.getName(), node.getMetas());
                    throw null;
                }
            }
            switch (StaticTypeVisitorTransform$VisitorTransform$WhenMappings.$EnumSwitchMapping$1[found.getScope().ordinal()]) {
                case 1: 
                case 2: {
                    scopeQualifier = (PartiqlAst.ScopeQualifier)PartiqlAst.Companion.build(transformExprId.newScopeQualifier.1.INSTANCE);
                    break;
                }
                case 3: {
                    scopeQualifier = (PartiqlAst.ScopeQualifier)PartiqlAst.Companion.build(transformExprId.newScopeQualifier.2.INSTANCE);
                    break;
                }
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            PartiqlAst.ScopeQualifier newScopeQualifier2 = scopeQualifier;
            return (PartiqlAst.Expr)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Expr.Id>(node, newScopeQualifier2, found){
                final /* synthetic */ PartiqlAst.Expr.Id $node;
                final /* synthetic */ PartiqlAst.ScopeQualifier $newScopeQualifier;
                final /* synthetic */ TypeAndScope $found;

                @NotNull
                public final PartiqlAst.Expr.Id invoke(@NotNull PartiqlAst.Builder $this$build) {
                    Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                    return $this$build.id_(this.$node.getName(), this.$node.getCase(), this.$newScopeQualifier, MapsKt.plus(this.$node.getMetas(), IonMeta.metaContainerOf((Pair[])new Pair[]{TuplesKt.to("$static_type", new StaticTypeMeta(this.$found.getType()))})));
                }
                {
                    this.$node = id;
                    this.$newScopeQualifier = scopeQualifier;
                    this.$found = typeAndScope;
                    super(1);
                }
            });
        }

        private final PartiqlAst.Expr.Path makePathIntoFromSource(String fromSourceAlias, PartiqlAst.Expr.Id node) {
            return (PartiqlAst.Expr.Path)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Expr.Path>(this, fromSourceAlias, node){
                final /* synthetic */ VisitorTransform this$0;
                final /* synthetic */ String $fromSourceAlias;
                final /* synthetic */ PartiqlAst.Expr.Id $node;

                @NotNull
                public final PartiqlAst.Expr.Path invoke(@NotNull PartiqlAst.Builder $this$build) {
                    Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                    return $this$build.path(VisitorTransform.access$singleFromSourceRef(this.this$0, this.$fromSourceAlias, UtilKt.extractSourceLocation(this.$node)), CollectionsKt.listOf(VisitorTransform.access$toPathExpr(this.this$0, this.$node)), UtilKt.extractSourceLocation(this.$node));
                }
                {
                    this.this$0 = visitorTransform;
                    this.$fromSourceAlias = string;
                    this.$node = id;
                    super(1);
                }
            });
        }

        @Override
        @NotNull
        public PartiqlAst.Expr transformExprPath(@NotNull PartiqlAst.Expr.Path node) {
            PartiqlAst.Expr expr;
            Intrinsics.checkParameterIsNotNull(node, "node");
            PartiqlAst.Expr expr2 = node.getRoot();
            if (expr2 instanceof PartiqlAst.Expr.Id) {
                PartiqlAst.Expr.Path path;
                PartiqlAst.Expr expr3 = super.transformExprPath(node);
                boolean bl = false;
                boolean bl2 = false;
                PartiqlAst.Expr it = expr3;
                boolean bl3 = false;
                PartiqlAst.Expr expr4 = it;
                if (expr4 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.domains.PartiqlAst.Expr.Path");
                }
                PartiqlAst.Expr.Path cfr_ignored_0 = (PartiqlAst.Expr.Path)expr4;
                PartiqlAst.Expr expr5 = ((PartiqlAst.Expr.Path)it).getRoot();
                if (expr5 instanceof PartiqlAst.Expr.Path) {
                    PartiqlAst.Expr childPath = ((PartiqlAst.Expr.Path)it).getRoot();
                    path = (PartiqlAst.Expr.Path)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Expr.Path>(childPath, it){
                        final /* synthetic */ PartiqlAst.Expr $childPath;
                        final /* synthetic */ PartiqlAst.Expr $it;

                        @NotNull
                        public final PartiqlAst.Expr.Path invoke(@NotNull PartiqlAst.Builder $this$build) {
                            Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                            return $this$build.path(((PartiqlAst.Expr.Path)this.$childPath).getRoot(), CollectionsKt.plus((Collection)((PartiqlAst.Expr.Path)this.$childPath).getSteps(), (Iterable)((PartiqlAst.Expr.Path)this.$it).getSteps()), this.$it.getMetas());
                        }
                        {
                            this.$childPath = expr;
                            this.$it = expr2;
                            super(1);
                        }
                    });
                } else {
                    path = (PartiqlAst.Expr.Path)it;
                }
                expr = path;
            } else {
                expr = super.transformExprPath(node);
            }
            return expr;
        }

        @Override
        @NotNull
        public PartiqlAst.FromSource transformFromSourceScan(@NotNull PartiqlAst.FromSource.Scan node) {
            SymbolPrimitive it;
            boolean bl;
            boolean bl2;
            SymbolPrimitive symbolPrimitive;
            Intrinsics.checkParameterIsNotNull(node, "node");
            PartiqlAst.FromSource from2 = super.transformFromSourceScan(node);
            SymbolPrimitive symbolPrimitive2 = node.getAtAlias();
            if (symbolPrimitive2 != null) {
                symbolPrimitive = symbolPrimitive2;
                bl2 = false;
                bl = false;
                it = symbolPrimitive;
                boolean bl3 = false;
                this.addLocal(it.getText(), StaticType.ANY, it.getMetas());
            }
            SymbolPrimitive symbolPrimitive3 = node.getByAlias();
            if (symbolPrimitive3 != null) {
                symbolPrimitive = symbolPrimitive3;
                bl2 = false;
                bl = false;
                it = symbolPrimitive;
                boolean bl4 = false;
                this.addLocal(it.getText(), StaticType.ANY, it.getMetas());
            }
            SymbolPrimitive symbolPrimitive4 = node.getAsAlias();
            if (symbolPrimitive4 == null) {
                String string = "fromSourceLet.variables.asName is null.  This wouldn't be the case if FromSourceAliasVisitorTransform was executed first.";
                bl = false;
                throw (Throwable)new IllegalStateException(string.toString());
            }
            SymbolPrimitive asSymbolicName = symbolPrimitive4;
            this.addLocal(asSymbolicName.getText(), StaticType.ANY, asSymbolicName.getMetas());
            if (!this.containsJoin) {
                this.fromVisited = true;
                if (this.currentScopeDepth == 1) {
                    this.singleFromSourceName = asSymbolicName.getText();
                }
            }
            return from2;
        }

        @Override
        @NotNull
        public PartiqlAst.FromSource transformFromSourceUnpivot(@NotNull PartiqlAst.FromSource.Unpivot node) {
            SymbolPrimitive it;
            boolean bl;
            boolean bl2;
            SymbolPrimitive symbolPrimitive;
            Intrinsics.checkParameterIsNotNull(node, "node");
            PartiqlAst.FromSource from2 = super.transformFromSourceUnpivot(node);
            SymbolPrimitive symbolPrimitive2 = node.getAtAlias();
            if (symbolPrimitive2 != null) {
                symbolPrimitive = symbolPrimitive2;
                bl2 = false;
                bl = false;
                it = symbolPrimitive;
                boolean bl3 = false;
                this.addLocal(it.getText(), StaticType.ANY, it.getMetas());
            }
            SymbolPrimitive symbolPrimitive3 = node.getByAlias();
            if (symbolPrimitive3 != null) {
                symbolPrimitive = symbolPrimitive3;
                bl2 = false;
                bl = false;
                it = symbolPrimitive;
                boolean bl4 = false;
                this.addLocal(it.getText(), StaticType.ANY, it.getMetas());
            }
            SymbolPrimitive symbolPrimitive4 = node.getAsAlias();
            if (symbolPrimitive4 == null) {
                String string = "fromSourceLet.variables.asName is null.  This wouldn't be the case if FromSourceAliasVisitorTransform was executed first.";
                bl = false;
                throw (Throwable)new IllegalStateException(string.toString());
            }
            SymbolPrimitive asSymbolicName = symbolPrimitive4;
            this.addLocal(asSymbolicName.getText(), StaticType.ANY, asSymbolicName.getMetas());
            if (!this.containsJoin) {
                this.fromVisited = true;
                if (this.currentScopeDepth == 1) {
                    this.singleFromSourceName = asSymbolicName.getText();
                }
            }
            return from2;
        }

        @Override
        @NotNull
        public PartiqlAst.FromSource transformFromSourceJoin(@NotNull PartiqlAst.FromSource.Join node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            boolean outermostJoin = !this.containsJoin;
            this.containsJoin = true;
            PartiqlAst.FromSource fromSource = super.transformFromSourceJoin(node);
            boolean bl = false;
            boolean bl2 = false;
            PartiqlAst.FromSource it = fromSource;
            boolean bl3 = false;
            if (outermostJoin) {
                this.fromVisited = true;
                this.singleFromSourceName = null;
            }
            return fromSource;
        }

        @Override
        @NotNull
        public PartiqlAst.Expr transformFromSourceScan_expr(@NotNull PartiqlAst.FromSource.Scan node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            this.scopeOrder = ScopeSearchOrder.GLOBALS_THEN_LEXICAL;
            PartiqlAst.Expr expr = this.transformExpr(node.getExpr());
            boolean bl = false;
            boolean bl2 = false;
            PartiqlAst.Expr it = expr;
            boolean bl3 = false;
            this.scopeOrder = ScopeSearchOrder.LEXICAL;
            return expr;
        }

        @Override
        @NotNull
        public PartiqlAst.Expr transformFromSourceUnpivot_expr(@NotNull PartiqlAst.FromSource.Unpivot node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            this.scopeOrder = ScopeSearchOrder.GLOBALS_THEN_LEXICAL;
            PartiqlAst.Expr expr = this.transformExpr(node.getExpr());
            boolean bl = false;
            boolean bl2 = false;
            PartiqlAst.Expr it = expr;
            boolean bl3 = false;
            this.scopeOrder = ScopeSearchOrder.LEXICAL;
            return expr;
        }

        @Override
        @NotNull
        public PartiqlAst.GroupBy transformGroupBy(@NotNull PartiqlAst.GroupBy node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            Void void_ = VisitorTransform.errUnimplementedFeature$default(this, "GROUP BY", null, 2, null);
            throw null;
        }

        @Override
        @NotNull
        public PartiqlAst.Expr transformExprSelect(@NotNull PartiqlAst.Expr.Select node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            return this.createTransformerForNestedScope().transformExprSelectEvaluationOrder(node);
        }

        @Override
        @NotNull
        public PartiqlAst.Statement transformStatementDml(@NotNull PartiqlAst.Statement.Dml node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            return this.createTransformerForNestedScope().transformDataManipulationEvaluationOrder(node);
        }

        private final VisitorTransform createTransformerForNestedScope() {
            return new VisitorTransform(this.currentEnv, this.currentScopeDepth + 1);
        }

        @Override
        @NotNull
        public PartiqlAst.DdlOp transformDdlOpCreateIndex(@NotNull PartiqlAst.DdlOp.CreateIndex node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            return (PartiqlAst.DdlOp)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.DdlOp.CreateIndex>(this, node){
                final /* synthetic */ VisitorTransform this$0;
                final /* synthetic */ PartiqlAst.DdlOp.CreateIndex $node;

                @NotNull
                public final PartiqlAst.DdlOp.CreateIndex invoke(@NotNull PartiqlAst.Builder $this$build) {
                    Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                    return $this$build.createIndex(this.$node.getIndexName(), this.$node.getFields(), this.this$0.transformMetas(this.$node.getMetas()));
                }
                {
                    this.this$0 = visitorTransform;
                    this.$node = createIndex;
                    super(1);
                }
            });
        }

        @Override
        @NotNull
        public PartiqlAst.DdlOp transformDdlOpDropIndex(@NotNull PartiqlAst.DdlOp.DropIndex node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            return (PartiqlAst.DdlOp)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.DdlOp.DropIndex>(this, node){
                final /* synthetic */ VisitorTransform this$0;
                final /* synthetic */ PartiqlAst.DdlOp.DropIndex $node;

                @NotNull
                public final PartiqlAst.DdlOp.DropIndex invoke(@NotNull PartiqlAst.Builder $this$build) {
                    Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                    return $this$build.dropIndex(this.$node.getTable(), this.$node.getKeys(), this.this$0.transformMetas(this.$node.getMetas()));
                }
                {
                    this.this$0 = visitorTransform;
                    this.$node = dropIndex;
                    super(1);
                }
            });
        }

        public VisitorTransform(Bindings<TypeAndDepth> parentEnv, int currentScopeDepth) {
            Map map2;
            Intrinsics.checkParameterIsNotNull(parentEnv, "parentEnv");
            this.parentEnv = parentEnv;
            this.currentScopeDepth = currentScopeDepth;
            this.scopeOrder = ScopeSearchOrder.LEXICAL;
            VisitorTransform visitorTransform = this;
            boolean bl = false;
            visitorTransform.localsMap = map2 = (Map)new LinkedHashMap();
            this.localsOnlyEnv = StaticTypeVisitorTransform.this.wrapBindings(Bindings.Companion.ofMap(this.localsMap), this.currentScopeDepth);
            this.currentEnv = BindingsExtensionsKt.delegate(Bindings.Companion.over((Function1)new Function1<BindingName, TypeAndDepth>(this){
                final /* synthetic */ VisitorTransform this$0;

                @Nullable
                public final TypeAndDepth invoke(@NotNull BindingName it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    return (TypeAndDepth)VisitorTransform.access$getLocalsOnlyEnv$p(this.this$0).get(it);
                }
                {
                    this.this$0 = visitorTransform;
                    super(1);
                }
            }), this.parentEnv);
        }

        public static final /* synthetic */ TypeAndScope access$lookupBinding(VisitorTransform $this, Bindings $this$access_u24lookupBinding, BindingName bindingName) {
            return $this.lookupBinding($this$access_u24lookupBinding, bindingName);
        }

        public static final /* synthetic */ PartiqlAst.Expr.Id access$singleFromSourceRef(VisitorTransform $this, String sourceName, Map metas) {
            return $this.singleFromSourceRef(sourceName, metas);
        }

        public static final /* synthetic */ PartiqlAst.PathStep.PathExpr access$toPathExpr(VisitorTransform $this, PartiqlAst.Expr.Id $this$access_u24toPathExpr) {
            return $this.toPathExpr($this$access_u24toPathExpr);
        }

        public static final /* synthetic */ Bindings access$getLocalsOnlyEnv$p(VisitorTransform $this) {
            return $this.localsOnlyEnv;
        }

        public static final /* synthetic */ void access$setLocalsOnlyEnv$p(VisitorTransform $this, Bindings bindings2) {
            $this.localsOnlyEnv = bindings2;
        }
    }
}

