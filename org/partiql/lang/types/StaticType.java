/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.types.SingleType;
import org.partiql.lang.types.TaggedUnion;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH&J\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u0082\u0001\u0002\u0011\u0012\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/types/StaticType;", "", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "typeDomain", "", "Lorg/partiql/lang/eval/ExprValueType;", "getTypeDomain", "()Ljava/util/Set;", "isOfType", "", "exprValueType", "toString", "Companion", "Lorg/partiql/lang/types/SingleType;", "Lorg/partiql/lang/types/TaggedUnion;", "lang"})
public abstract class StaticType {
    @NotNull
    private final String name;
    private static final Map<ExprValueType, SingleType> EXPR_VALUE_TYPE_MAP;
    @JvmField
    @NotNull
    public static final StaticType MISSING;
    @JvmField
    @NotNull
    public static final StaticType BOOL;
    @JvmField
    @NotNull
    public static final StaticType NULL;
    @JvmField
    @NotNull
    public static final StaticType INT;
    @JvmField
    @NotNull
    public static final StaticType FLOAT;
    @JvmField
    @NotNull
    public static final StaticType DECIMAL;
    @JvmField
    @NotNull
    public static final StaticType TIMESTAMP;
    @JvmField
    @NotNull
    public static final StaticType SYMBOL;
    @JvmField
    @NotNull
    public static final StaticType STRING;
    @JvmField
    @NotNull
    public static final StaticType CLOB;
    @JvmField
    @NotNull
    public static final StaticType BLOB;
    @JvmField
    @NotNull
    public static final StaticType LIST;
    @JvmField
    @NotNull
    public static final StaticType SEXP;
    @JvmField
    @NotNull
    public static final StaticType STRUCT;
    @JvmField
    @NotNull
    public static final StaticType BAG;
    @JvmField
    @NotNull
    public static final StaticType DATE;
    @JvmField
    @NotNull
    public static final StaticType TIME;
    private static final String NUMERIC_NAME = "NUMERIC";
    private static final String ANY_NAME = "ANY";
    private static final String NOTHING_NAME = "NOTHING";
    @JvmField
    @NotNull
    public static final StaticType NUMERIC;
    @JvmField
    @NotNull
    public static final StaticType ANY;
    @JvmField
    @NotNull
    public static final StaticType NOTHING;
    private static final List<StaticType> STANDARD_TYPES;
    private static final Map<String, StaticType> NAME_STANDARD_STATIC_TYPE_MAP;
    public static final Companion Companion;

    public abstract boolean isOfType(@NotNull ExprValueType var1);

    @NotNull
    public abstract Set<ExprValueType> getTypeDomain();

    @NotNull
    public String toString() {
        return this.name;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    private StaticType(String name) {
        this.name = name;
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var3_3;
        Pair<Object, Object> pair;
        Collection collection;
        Object $this$mapTo$iv$iv;
        Companion = new Companion(null);
        Object $this$map$iv = ExprValueType.values();
        boolean $i$f$map = false;
        ExprValueType[] exprValueTypeArray = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((ExprValueType[])$this$map$iv).length);
        boolean $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv$iv;
        int n = ((void)iterator2).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv$iv;
            void var9_12 = item$iv$iv = iterator2[i];
            collection = destination$iv$iv;
            boolean bl = false;
            pair = TuplesKt.to(it, new SingleType(it.name(), (ExprValueType)it));
            collection.add(pair);
        }
        EXPR_VALUE_TYPE_MAP = MapsKt.toMap((List)destination$iv$iv);
        MISSING = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.MISSING);
        BOOL = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.BOOL);
        NULL = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.NULL);
        INT = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.INT);
        FLOAT = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.FLOAT);
        DECIMAL = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.DECIMAL);
        TIMESTAMP = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.TIMESTAMP);
        SYMBOL = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.SYMBOL);
        STRING = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.STRING);
        CLOB = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.CLOB);
        BLOB = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.BLOB);
        LIST = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.LIST);
        SEXP = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.SEXP);
        STRUCT = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.STRUCT);
        BAG = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.BAG);
        DATE = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.DATE);
        TIME = MapsKt.getValue(EXPR_VALUE_TYPE_MAP, ExprValueType.TIME);
        NUMERIC_NAME = NUMERIC_NAME;
        ANY_NAME = ANY_NAME;
        NOTHING_NAME = NOTHING_NAME;
        NUMERIC = Companion.unionOf(NUMERIC_NAME, INT, FLOAT, DECIMAL);
        ANY = Companion.unionOf(ANY_NAME, MISSING, BOOL, NULL, INT, FLOAT, DECIMAL, TIMESTAMP, SYMBOL, STRING, CLOB, BLOB, LIST, SEXP, STRUCT, BAG);
        NOTHING = Companion.unionOf(NOTHING_NAME, new StaticType[0]);
        STANDARD_TYPES = CollectionsKt.plus(EXPR_VALUE_TYPE_MAP.values(), (Iterable)CollectionsKt.listOf(NUMERIC, ANY, NOTHING));
        $this$map$iv = STANDARD_TYPES;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        iterator2 = $this$mapTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            void it;
            Object item$iv$iv = iterator2.next();
            StaticType staticType = (StaticType)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            pair = TuplesKt.to(it.name, it);
            collection.add(pair);
        }
        NAME_STANDARD_STATIC_TYPE_MAP = MapsKt.toMap((List)var3_3);
    }

    public /* synthetic */ StaticType(String name, DefaultConstructorMarker $constructor_marker) {
        this(name);
    }

    @JvmStatic
    @NotNull
    public static final StaticType unionOf(@NotNull String name, @NotNull StaticType ... types) {
        return Companion.unionOf(name, types);
    }

    @JvmStatic
    @NotNull
    public static final StaticType unionOf(@NotNull String name, @NotNull Set<? extends StaticType> types) {
        return Companion.unionOf(name, types);
    }

    @JvmStatic
    @NotNull
    public static final StaticType fromTypeName(@NotNull String name) {
        return Companion.fromTypeName(name);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\"\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u0006H\u0007J)\u0010%\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u00062\u0012\u0010&\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040'\"\u00020\u0004H\u0007\u00a2\u0006\u0002\u0010(J\u001e\u0010%\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u00062\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00040)H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00040\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2={"Lorg/partiql/lang/types/StaticType$Companion;", "", "()V", "ANY", "Lorg/partiql/lang/types/StaticType;", "ANY_NAME", "", "BAG", "BLOB", "BOOL", "CLOB", "DATE", "DECIMAL", "EXPR_VALUE_TYPE_MAP", "", "Lorg/partiql/lang/eval/ExprValueType;", "Lorg/partiql/lang/types/SingleType;", "FLOAT", "INT", "LIST", "MISSING", "NAME_STANDARD_STATIC_TYPE_MAP", "NOTHING", "NOTHING_NAME", "NULL", "NUMERIC", "NUMERIC_NAME", "SEXP", "STANDARD_TYPES", "", "STRING", "STRUCT", "SYMBOL", "TIME", "TIMESTAMP", "fromTypeName", "name", "unionOf", "types", "", "(Ljava/lang/String;[Lorg/partiql/lang/types/StaticType;)Lorg/partiql/lang/types/StaticType;", "", "lang"})
    public static final class Companion {
        @JvmStatic
        @NotNull
        public final StaticType unionOf(@NotNull String name, @NotNull StaticType ... types) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(types, "types");
            return this.unionOf(name, (Set<? extends StaticType>)ArraysKt.toHashSet(types));
        }

        @JvmStatic
        @NotNull
        public final StaticType unionOf(@NotNull String name, @NotNull Set<? extends StaticType> types) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(types, "types");
            return new TaggedUnion(name, types);
        }

        @JvmStatic
        @NotNull
        public final StaticType fromTypeName(@NotNull String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            StaticType staticType = (StaticType)NAME_STANDARD_STATIC_TYPE_MAP.get(name);
            if (staticType == null) {
                throw (Throwable)new IllegalArgumentException("No such built in type named: " + name);
            }
            return staticType;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

