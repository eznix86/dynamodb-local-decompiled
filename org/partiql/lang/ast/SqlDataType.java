/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b \b\u0086\u0001\u0018\u0000 $2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001$B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#\u00a8\u0006%"}, d2={"Lorg/partiql/lang/ast/SqlDataType;", "", "typeName", "", "arityRange", "Lkotlin/ranges/IntRange;", "(Ljava/lang/String;ILjava/lang/String;Lkotlin/ranges/IntRange;)V", "getArityRange", "()Lkotlin/ranges/IntRange;", "getTypeName", "()Ljava/lang/String;", "MISSING", "NULL", "BOOLEAN", "SMALLINT", "INTEGER", "FLOAT", "REAL", "DOUBLE_PRECISION", "DECIMAL", "NUMERIC", "TIMESTAMP", "CHARACTER", "CHARACTER_VARYING", "STRING", "SYMBOL", "CLOB", "BLOB", "STRUCT", "TUPLE", "LIST", "SEXP", "DATE", "TIME", "TIME_WITH_TIME_ZONE", "BAG", "Companion", "lang"})
public final class SqlDataType
extends Enum<SqlDataType> {
    public static final /* enum */ SqlDataType MISSING;
    public static final /* enum */ SqlDataType NULL;
    public static final /* enum */ SqlDataType BOOLEAN;
    public static final /* enum */ SqlDataType SMALLINT;
    public static final /* enum */ SqlDataType INTEGER;
    public static final /* enum */ SqlDataType FLOAT;
    public static final /* enum */ SqlDataType REAL;
    public static final /* enum */ SqlDataType DOUBLE_PRECISION;
    public static final /* enum */ SqlDataType DECIMAL;
    public static final /* enum */ SqlDataType NUMERIC;
    public static final /* enum */ SqlDataType TIMESTAMP;
    public static final /* enum */ SqlDataType CHARACTER;
    public static final /* enum */ SqlDataType CHARACTER_VARYING;
    public static final /* enum */ SqlDataType STRING;
    public static final /* enum */ SqlDataType SYMBOL;
    public static final /* enum */ SqlDataType CLOB;
    public static final /* enum */ SqlDataType BLOB;
    public static final /* enum */ SqlDataType STRUCT;
    public static final /* enum */ SqlDataType TUPLE;
    public static final /* enum */ SqlDataType LIST;
    public static final /* enum */ SqlDataType SEXP;
    public static final /* enum */ SqlDataType DATE;
    public static final /* enum */ SqlDataType TIME;
    public static final /* enum */ SqlDataType TIME_WITH_TIME_ZONE;
    public static final /* enum */ SqlDataType BAG;
    private static final /* synthetic */ SqlDataType[] $VALUES;
    @NotNull
    private final String typeName;
    @NotNull
    private final IntRange arityRange;
    private static final Map<String, SqlDataType> DATA_TYPE_NAME_TO_TYPE_LOOKUP;
    public static final Companion Companion;

    /*
     * WARNING - void declaration
     */
    static {
        void var3_5;
        Collection<Pair<String, void>> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        SqlDataType[] sqlDataTypeArray = new SqlDataType[25];
        int n = 0;
        sqlDataTypeArray[0] = MISSING = new SqlDataType("missing", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[1] = NULL = new SqlDataType("null", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[2] = BOOLEAN = new SqlDataType("boolean", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[3] = SMALLINT = new SqlDataType("smallint", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[4] = INTEGER = new SqlDataType("integer", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[5] = FLOAT = new SqlDataType("float", new IntRange(n, 1));
        n = 0;
        sqlDataTypeArray[6] = REAL = new SqlDataType("real", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[7] = DOUBLE_PRECISION = new SqlDataType("double_precision", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[8] = DECIMAL = new SqlDataType("decimal", new IntRange(n, 2));
        n = 0;
        sqlDataTypeArray[9] = NUMERIC = new SqlDataType("numeric", new IntRange(n, 2));
        n = 0;
        sqlDataTypeArray[10] = TIMESTAMP = new SqlDataType("timestamp", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[11] = CHARACTER = new SqlDataType("character", new IntRange(n, 1));
        n = 0;
        sqlDataTypeArray[12] = CHARACTER_VARYING = new SqlDataType("character_varying", new IntRange(n, 1));
        n = 0;
        sqlDataTypeArray[13] = STRING = new SqlDataType("string", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[14] = SYMBOL = new SqlDataType("symbol", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[15] = CLOB = new SqlDataType("clob", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[16] = BLOB = new SqlDataType("blob", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[17] = STRUCT = new SqlDataType("struct", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[18] = TUPLE = new SqlDataType("tuple", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[19] = LIST = new SqlDataType("list", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[20] = SEXP = new SqlDataType("sexp", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[21] = DATE = new SqlDataType("date", new IntRange(n, 0));
        n = 0;
        sqlDataTypeArray[22] = TIME = new SqlDataType("time", new IntRange(n, 1));
        n = 0;
        sqlDataTypeArray[23] = TIME_WITH_TIME_ZONE = new SqlDataType("time_with_time_zone", new IntRange(n, 1));
        n = 0;
        sqlDataTypeArray[24] = BAG = new SqlDataType("bag", new IntRange(n, 0));
        $VALUES = sqlDataTypeArray;
        Companion = new Companion(null);
        SqlDataType[] sqlDataTypeArray2 = SqlDataType.values();
        SqlDataType[] sqlDataTypeArray3 = sqlDataTypeArray;
        boolean $i$f$map = false;
        void var2_4 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        void var5_7 = $this$mapTo$iv$iv;
        int n2 = ((void)var5_7).length;
        for (int i = 0; i < n2; ++i) {
            void it;
            void item$iv$iv;
            void var9_11 = item$iv$iv = var5_7[i];
            collection = destination$iv$iv;
            boolean bl = false;
            Pair<String, void> pair = new Pair<String, void>(it.typeName, it);
            collection.add(pair);
        }
        collection = (List)var3_5;
        SqlDataType[] sqlDataTypeArray4 = sqlDataTypeArray3;
        DATA_TYPE_NAME_TO_TYPE_LOOKUP = MapsKt.toMap((Iterable)collection);
    }

    @NotNull
    public final String getTypeName() {
        return this.typeName;
    }

    @NotNull
    public final IntRange getArityRange() {
        return this.arityRange;
    }

    private SqlDataType(String typeName, IntRange arityRange) {
        this.typeName = typeName;
        this.arityRange = arityRange;
    }

    public static SqlDataType[] values() {
        return (SqlDataType[])$VALUES.clone();
    }

    public static SqlDataType valueOf(String string) {
        return Enum.valueOf(SqlDataType.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/ast/SqlDataType$Companion;", "", "()V", "DATA_TYPE_NAME_TO_TYPE_LOOKUP", "", "", "Lorg/partiql/lang/ast/SqlDataType;", "forTypeName", "typeName", "lang"})
    public static final class Companion {
        @Nullable
        public final SqlDataType forTypeName(@NotNull String typeName) {
            Intrinsics.checkParameterIsNotNull(typeName, "typeName");
            return (SqlDataType)((Object)DATA_TYPE_NAME_TO_TYPE_LOOKUP.get(typeName));
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

