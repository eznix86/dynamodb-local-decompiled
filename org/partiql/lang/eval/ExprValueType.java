/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.SqlDataType;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.ExprValueType$Companion$WhenMappings;
import org.partiql.lang.syntax.LexerConstantsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b!\b\u0086\u0001\u0018\u0000 &2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001&BQ\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012\b\b\u0002\u0010\n\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0000J\b\u0010\u0014\u001a\u00020\u0006H\u0007R\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\rR\u0011\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\rR\u0011\u0010\n\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\rR\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%\u00a8\u0006'"}, d2={"Lorg/partiql/lang/eval/ExprValueType;", "", "typeNames", "", "", "isUnknown", "", "isNumber", "isText", "isLob", "isSequence", "isRangedFrom", "(Ljava/lang/String;ILjava/util/List;ZZZZZZ)V", "()Z", "sqlTextNames", "getSqlTextNames", "()Ljava/util/List;", "getTypeNames", "isDirectlyComparableTo", "other", "isNull", "MISSING", "NULL", "BOOL", "INT", "FLOAT", "DECIMAL", "DATE", "TIMESTAMP", "TIME", "SYMBOL", "STRING", "CLOB", "BLOB", "LIST", "SEXP", "STRUCT", "BAG", "Companion", "lang"})
public final class ExprValueType
extends Enum<ExprValueType> {
    public static final /* enum */ ExprValueType MISSING;
    public static final /* enum */ ExprValueType NULL;
    public static final /* enum */ ExprValueType BOOL;
    public static final /* enum */ ExprValueType INT;
    public static final /* enum */ ExprValueType FLOAT;
    public static final /* enum */ ExprValueType DECIMAL;
    public static final /* enum */ ExprValueType DATE;
    public static final /* enum */ ExprValueType TIMESTAMP;
    public static final /* enum */ ExprValueType TIME;
    public static final /* enum */ ExprValueType SYMBOL;
    public static final /* enum */ ExprValueType STRING;
    public static final /* enum */ ExprValueType CLOB;
    public static final /* enum */ ExprValueType BLOB;
    public static final /* enum */ ExprValueType LIST;
    public static final /* enum */ ExprValueType SEXP;
    public static final /* enum */ ExprValueType STRUCT;
    public static final /* enum */ ExprValueType BAG;
    private static final /* synthetic */ ExprValueType[] $VALUES;
    @NotNull
    private final List<String> sqlTextNames;
    @NotNull
    private final List<String> typeNames;
    private final boolean isUnknown;
    private final boolean isNumber;
    private final boolean isText;
    private final boolean isLob;
    private final boolean isSequence;
    private final boolean isRangedFrom;
    private static final Map<IonType, ExprValueType> ION_TYPE_MAP;
    private static final Map<String, ExprValueType> LEX_TYPE_MAP;
    public static final Companion Companion;

    /*
     * WARNING - void declaration
     */
    static {
        void $this$toTypedArray$iv;
        void var3_10;
        void $this$mapTo$iv$iv;
        Collection $this$map$iv;
        boolean bl;
        void $this$forEach$iv;
        Iterator $this$flatMapTo$iv$iv22;
        Iterable $this$flatMap$iv;
        Collection collection;
        MISSING = new ExprValueType("MISSING", 0, CollectionsKt.listOf("missing"), true, false, false, false, false, false, 124, null);
        $VALUES = new ExprValueType[]{MISSING, NULL = new ExprValueType("NULL", 1, CollectionsKt.listOf("null"), true, false, false, false, false, false, 124, null), BOOL = new ExprValueType("BOOL", 2, CollectionsKt.listOf("bool", "boolean"), false, false, false, false, false, false, 126, null), INT = new ExprValueType("INT", 3, CollectionsKt.listOf("int", "smallint", "integer"), false, true, false, false, false, false, 122, null), FLOAT = new ExprValueType("FLOAT", 4, CollectionsKt.listOf("float", "real", "double_precision"), false, true, false, false, false, false, 122, null), DECIMAL = new ExprValueType("DECIMAL", 5, CollectionsKt.listOf("dec", "decimal", "numeric"), false, true, false, false, false, false, 122, null), DATE = new ExprValueType("DATE", 6, CollectionsKt.listOf("date"), false, false, false, false, false, false, 126, null), TIMESTAMP = new ExprValueType("TIMESTAMP", 7, CollectionsKt.listOf("timestamp"), false, false, false, false, false, false, 126, null), TIME = new ExprValueType("TIME", 8, CollectionsKt.listOf("time"), false, false, false, false, false, false, 126, null), SYMBOL = new ExprValueType("SYMBOL", 9, CollectionsKt.listOf("symbol"), false, false, true, false, false, false, 118, null), STRING = new ExprValueType("STRING", 10, CollectionsKt.listOf("string", "char", "varchar", "character", "character_varying"), false, false, true, false, false, false, 118, null), CLOB = new ExprValueType("CLOB", 11, CollectionsKt.listOf("clob"), false, false, false, true, false, false, 110, null), BLOB = new ExprValueType("BLOB", 12, CollectionsKt.listOf("blob"), false, false, false, true, false, false, 110, null), LIST = new ExprValueType("LIST", 13, CollectionsKt.listOf("list"), false, false, false, false, true, true, 30, null), SEXP = new ExprValueType("SEXP", 14, CollectionsKt.listOf("sexp"), false, false, false, false, true, false, 94, null), STRUCT = new ExprValueType("STRUCT", 15, CollectionsKt.listOf("struct", "tuple"), false, false, false, false, false, false, 126, null), BAG = new ExprValueType("BAG", 16, CollectionsKt.listOf("bag"), false, false, false, false, true, true, 30, null)};
        Companion = new Companion(null);
        Iterable<Object> lexerTypeNames = CollectionsKt.union((Iterable)LexerConstantsKt.TYPE_NAME_ARITY_MAP.keySet(), (Iterable)LexerConstantsKt.TYPE_ALIASES.keySet());
        ExprValueType[] exprValueTypeArray = $VALUES;
        boolean bl2 = false;
        Set declaredTypeNames = collection = (Set)new LinkedHashSet();
        ExprValueType[] exprValueTypeArray2 = ExprValueType.values();
        boolean $i$f$flatMap = false;
        void var4_11 = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        void var7_17 = $this$flatMapTo$iv$iv22;
        int n = ((void)var7_17).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = var7_17[i];
            boolean bl3 = false;
            Iterable list$iv$iv = it.typeNames;
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
        }
        collection = (List)destination$iv$iv;
        $this$flatMap$iv = collection;
        boolean $i$f$forEach22 = false;
        for (Object element$iv : $this$forEach$iv) {
            String it = (String)element$iv;
            boolean bl4 = false;
            if (!lexerTypeNames.contains(it)) {
                throw (Throwable)new IllegalStateException("Declared type name does not exist in lexer: " + it);
            }
            if (declaredTypeNames.contains(it)) {
                throw (Throwable)new IllegalStateException("Duplicate declaration for " + it);
            }
            declaredTypeNames.add(it);
        }
        Set undeclaredTypeNames = SetsKt.minus(lexerTypeNames, declaredTypeNames);
        Collection $i$f$forEach22 = undeclaredTypeNames;
        boolean $this$flatMapTo$iv$iv22 = false;
        boolean bl5 = bl = !$i$f$forEach22.isEmpty();
        if (bl) {
            throw (Throwable)new IllegalStateException("Undeclared type names: " + undeclaredTypeNames);
        }
        Object object = IonType.values();
        ION_TYPE_MAP = MapsKt.toMap(SequencesKt.map(ArraysKt.asSequence(object), Companion.ION_TYPE_MAP.1.INSTANCE));
        lexerTypeNames = LexerConstantsKt.TYPE_NAME_ARITY_MAP.keySet();
        boolean $i$f$map = false;
        undeclaredTypeNames = $this$map$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            ExprValueType exprValueType;
            void it;
            String bl4 = (String)item$iv$iv;
            object = destination$iv$iv2;
            boolean bl6 = false;
            try {
                exprValueType = it;
                boolean element$iv$iv = false;
                void v1 = exprValueType;
                if (v1 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string = v1.toUpperCase();
                Intrinsics.checkExpressionValueIsNotNull(string, "(this as java.lang.String).toUpperCase()");
                exprValueType = ExprValueType.valueOf(string);
            } catch (IllegalArgumentException e) {
                ExprValueType exprValueType2;
                switch (it) {
                    case "boolean": {
                        exprValueType2 = BOOL;
                        break;
                    }
                    case "smallint": 
                    case "integer": {
                        exprValueType2 = INT;
                        break;
                    }
                    case "double_precision": 
                    case "real": {
                        exprValueType2 = FLOAT;
                        break;
                    }
                    case "numeric": {
                        exprValueType2 = DECIMAL;
                        break;
                    }
                    case "character_varying": 
                    case "character": {
                        exprValueType2 = STRING;
                        break;
                    }
                    case "tuple": {
                        exprValueType2 = STRUCT;
                        break;
                    }
                    default: {
                        throw (Throwable)new IllegalStateException("No ExprValueType handler for " + (String)it);
                    }
                }
                exprValueType = exprValueType2;
            }
            ExprValueType type = exprValueType;
            Pair<void, ExprValueType> pair = new Pair<void, ExprValueType>(it, type);
            object.add(pair);
        }
        object = (List)var3_10;
        $this$map$iv = (Collection)object;
        boolean $i$f$toTypedArray = false;
        void thisCollection$iv = $this$toTypedArray$iv;
        Pair[] pairArray = thisCollection$iv.toArray(new Pair[0]);
        if (pairArray == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        object = pairArray;
        ExprValueType[] exprValueTypeArray3 = exprValueTypeArray;
        Pair[] pairArray2 = (Pair[])object;
        LEX_TYPE_MAP = MapsKt.mapOf(Arrays.copyOf(pairArray2, pairArray2.length));
    }

    @Deprecated(message="Please use isUnknown instead", replaceWith=@ReplaceWith(imports={}, expression="isUnknown"))
    public final boolean isNull() {
        return this.isUnknown;
    }

    @NotNull
    public final List<String> getSqlTextNames() {
        return this.sqlTextNames;
    }

    public final boolean isDirectlyComparableTo(@NotNull ExprValueType other) {
        Intrinsics.checkParameterIsNotNull((Object)other, "other");
        return this == other || this.isNumber && other.isNumber || this.isText && other.isText || this.isLob && other.isLob;
    }

    @NotNull
    public final List<String> getTypeNames() {
        return this.typeNames;
    }

    public final boolean isUnknown() {
        return this.isUnknown;
    }

    public final boolean isNumber() {
        return this.isNumber;
    }

    public final boolean isText() {
        return this.isText;
    }

    public final boolean isLob() {
        return this.isLob;
    }

    public final boolean isSequence() {
        return this.isSequence;
    }

    public final boolean isRangedFrom() {
        return this.isRangedFrom;
    }

    /*
     * WARNING - void declaration
     */
    private ExprValueType(List<String> typeNames, boolean isUnknown, boolean isNumber, boolean isText, boolean isLob, boolean isSequence, boolean isRangedFrom) {
        Collection<String> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        this.typeNames = typeNames;
        this.isUnknown = isUnknown;
        this.isNumber = isNumber;
        this.isText = isText;
        this.isLob = isLob;
        this.isSequence = isSequence;
        this.isRangedFrom = isRangedFrom;
        Iterable iterable = this.typeNames;
        ExprValueType exprValueType = this;
        boolean $i$f$map = false;
        void var12_13 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            String string = (String)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            void var19_20 = it;
            boolean bl2 = false;
            void v0 = var19_20;
            if (v0 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String string2 = v0.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).toUpperCase()");
            String string3 = StringsKt.replace$default(string2, "_", " ", false, 4, null);
            collection.add(string3);
        }
        collection = (List)destination$iv$iv;
        exprValueType.sqlTextNames = collection;
    }

    /* synthetic */ ExprValueType(String string, int n, List list, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) != 0) {
            bl2 = false;
        }
        if ((n2 & 8) != 0) {
            bl3 = false;
        }
        if ((n2 & 0x10) != 0) {
            bl4 = false;
        }
        if ((n2 & 0x20) != 0) {
            bl5 = false;
        }
        if ((n2 & 0x40) != 0) {
            bl6 = false;
        }
        this(list, bl, bl2, bl3, bl4, bl5, bl6);
    }

    public static ExprValueType[] values() {
        return (ExprValueType[])$VALUES.clone();
    }

    public static ExprValueType valueOf(String string) {
        return Enum.valueOf(ExprValueType.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0005J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\bR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lorg/partiql/lang/eval/ExprValueType$Companion;", "", "()V", "ION_TYPE_MAP", "", "Lcom/amazon/ion/IonType;", "Lorg/partiql/lang/eval/ExprValueType;", "LEX_TYPE_MAP", "", "fromIonType", "ionType", "fromSqlDataType", "sqlDataType", "Lorg/partiql/lang/ast/SqlDataType;", "fromTypeName", "name", "lang"})
    public static final class Companion {
        @NotNull
        public final ExprValueType fromIonType(@NotNull IonType ionType) {
            Intrinsics.checkParameterIsNotNull((Object)ionType, "ionType");
            Object v = ION_TYPE_MAP.get((Object)ionType);
            if (v == null) {
                Intrinsics.throwNpe();
            }
            return (ExprValueType)((Object)v);
        }

        @NotNull
        public final ExprValueType fromTypeName(@NotNull String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            ExprValueType exprValueType = (ExprValueType)((Object)LEX_TYPE_MAP.get(name));
            if (exprValueType == null) {
                throw (Throwable)new EvaluationException("No such value type for " + name, null, null, null, true, 14, null);
            }
            return exprValueType;
        }

        @NotNull
        public final ExprValueType fromSqlDataType(@NotNull SqlDataType sqlDataType) {
            ExprValueType exprValueType;
            Intrinsics.checkParameterIsNotNull((Object)sqlDataType, "sqlDataType");
            switch (ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[sqlDataType.ordinal()]) {
                case 1: {
                    exprValueType = BOOL;
                    break;
                }
                case 2: {
                    exprValueType = MISSING;
                    break;
                }
                case 3: {
                    exprValueType = NULL;
                    break;
                }
                case 4: {
                    exprValueType = INT;
                    break;
                }
                case 5: {
                    exprValueType = INT;
                    break;
                }
                case 6: {
                    exprValueType = FLOAT;
                    break;
                }
                case 7: {
                    exprValueType = FLOAT;
                    break;
                }
                case 8: {
                    exprValueType = FLOAT;
                    break;
                }
                case 9: {
                    exprValueType = DECIMAL;
                    break;
                }
                case 10: {
                    exprValueType = DECIMAL;
                    break;
                }
                case 11: {
                    exprValueType = TIMESTAMP;
                    break;
                }
                case 12: {
                    exprValueType = STRING;
                    break;
                }
                case 13: {
                    exprValueType = STRING;
                    break;
                }
                case 14: {
                    exprValueType = STRING;
                    break;
                }
                case 15: {
                    exprValueType = SYMBOL;
                    break;
                }
                case 16: {
                    exprValueType = CLOB;
                    break;
                }
                case 17: {
                    exprValueType = BLOB;
                    break;
                }
                case 18: {
                    exprValueType = STRUCT;
                    break;
                }
                case 19: {
                    exprValueType = STRUCT;
                    break;
                }
                case 20: {
                    exprValueType = LIST;
                    break;
                }
                case 21: {
                    exprValueType = SEXP;
                    break;
                }
                case 22: {
                    exprValueType = BAG;
                    break;
                }
                case 23: {
                    exprValueType = DATE;
                    break;
                }
                case 24: 
                case 25: {
                    exprValueType = TIME;
                    break;
                }
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            return exprValueType;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

