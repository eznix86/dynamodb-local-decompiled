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

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\"\b\u0086\u0001\u0018\u0000 &2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001&B!\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%\u00a8\u0006'"}, d2={"Lorg/partiql/lang/ast/NAryOp;", "", "arityRange", "Lkotlin/ranges/IntRange;", "symbol", "", "textName", "(Ljava/lang/String;ILkotlin/ranges/IntRange;Ljava/lang/String;Ljava/lang/String;)V", "getArityRange", "()Lkotlin/ranges/IntRange;", "getSymbol", "()Ljava/lang/String;", "getTextName", "ADD", "SUB", "MUL", "DIV", "MOD", "EQ", "LT", "LTE", "GT", "GTE", "NE", "LIKE", "BETWEEN", "IN", "NOT", "AND", "OR", "STRING_CONCAT", "CALL", "INTERSECT", "INTERSECT_ALL", "EXCEPT", "EXCEPT_ALL", "UNION", "UNION_ALL", "Companion", "lang"})
public final class NAryOp
extends Enum<NAryOp> {
    public static final /* enum */ NAryOp ADD;
    public static final /* enum */ NAryOp SUB;
    public static final /* enum */ NAryOp MUL;
    public static final /* enum */ NAryOp DIV;
    public static final /* enum */ NAryOp MOD;
    public static final /* enum */ NAryOp EQ;
    public static final /* enum */ NAryOp LT;
    public static final /* enum */ NAryOp LTE;
    public static final /* enum */ NAryOp GT;
    public static final /* enum */ NAryOp GTE;
    public static final /* enum */ NAryOp NE;
    public static final /* enum */ NAryOp LIKE;
    public static final /* enum */ NAryOp BETWEEN;
    public static final /* enum */ NAryOp IN;
    public static final /* enum */ NAryOp NOT;
    public static final /* enum */ NAryOp AND;
    public static final /* enum */ NAryOp OR;
    public static final /* enum */ NAryOp STRING_CONCAT;
    public static final /* enum */ NAryOp CALL;
    public static final /* enum */ NAryOp INTERSECT;
    public static final /* enum */ NAryOp INTERSECT_ALL;
    public static final /* enum */ NAryOp EXCEPT;
    public static final /* enum */ NAryOp EXCEPT_ALL;
    public static final /* enum */ NAryOp UNION;
    public static final /* enum */ NAryOp UNION_ALL;
    private static final /* synthetic */ NAryOp[] $VALUES;
    @NotNull
    private final IntRange arityRange;
    @NotNull
    private final String symbol;
    @NotNull
    private final String textName;
    private static final Map<String, NAryOp> OP_SYMBOL_TO_OP_LOOKUP;
    public static final Companion Companion;

    /*
     * WARNING - void declaration
     */
    static {
        void var3_5;
        Collection<Pair<String, void>> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        NAryOp[] nAryOpArray = new NAryOp[25];
        int n = 1;
        nAryOpArray[0] = ADD = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "+", "plus");
        n = 1;
        nAryOpArray[1] = SUB = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "-", "minus");
        n = 2;
        nAryOpArray[2] = MUL = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "*", "times");
        n = 2;
        nAryOpArray[3] = DIV = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "/", "divide");
        n = 2;
        nAryOpArray[4] = MOD = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "%", "modulo");
        n = 2;
        nAryOpArray[5] = EQ = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "=", "eq");
        n = 2;
        nAryOpArray[6] = LT = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "<", "lt");
        n = 2;
        nAryOpArray[7] = LTE = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "<=", "lte");
        n = 2;
        nAryOpArray[8] = GT = new NAryOp(new IntRange(n, Integer.MAX_VALUE), ">", "gt");
        n = 2;
        nAryOpArray[9] = GTE = new NAryOp(new IntRange(n, Integer.MAX_VALUE), ">=", "gte");
        n = 2;
        nAryOpArray[10] = NE = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "<>", "ne");
        n = 2;
        nAryOpArray[11] = LIKE = new NAryOp("LIKE", 11, new IntRange(n, 3), "like", null, 4, null);
        n = 3;
        nAryOpArray[12] = BETWEEN = new NAryOp("BETWEEN", 12, new IntRange(n, 3), "between", null, 4, null);
        n = 2;
        nAryOpArray[13] = IN = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "in", "in_collection");
        n = 1;
        nAryOpArray[14] = NOT = new NAryOp("NOT", 14, new IntRange(n, 1), "not", null, 4, null);
        n = 2;
        nAryOpArray[15] = AND = new NAryOp("AND", 15, new IntRange(n, Integer.MAX_VALUE), "and", null, 4, null);
        n = 2;
        nAryOpArray[16] = OR = new NAryOp("OR", 16, new IntRange(n, Integer.MAX_VALUE), "or", null, 4, null);
        n = 2;
        nAryOpArray[17] = STRING_CONCAT = new NAryOp(new IntRange(n, Integer.MAX_VALUE), "||", "concat");
        n = 0;
        nAryOpArray[18] = CALL = new NAryOp("CALL", 18, new IntRange(n, Integer.MAX_VALUE), "call", null, 4, null);
        n = 2;
        nAryOpArray[19] = INTERSECT = new NAryOp("INTERSECT", 19, new IntRange(n, Integer.MAX_VALUE), "intersect", null, 4, null);
        n = 2;
        nAryOpArray[20] = INTERSECT_ALL = new NAryOp("INTERSECT_ALL", 20, new IntRange(n, Integer.MAX_VALUE), "intersect_all", null, 4, null);
        n = 2;
        nAryOpArray[21] = EXCEPT = new NAryOp("EXCEPT", 21, new IntRange(n, Integer.MAX_VALUE), "except", null, 4, null);
        n = 2;
        nAryOpArray[22] = EXCEPT_ALL = new NAryOp("EXCEPT_ALL", 22, new IntRange(n, Integer.MAX_VALUE), "except_all", null, 4, null);
        n = 2;
        nAryOpArray[23] = UNION = new NAryOp("UNION", 23, new IntRange(n, Integer.MAX_VALUE), "union", null, 4, null);
        n = 2;
        nAryOpArray[24] = UNION_ALL = new NAryOp("UNION_ALL", 24, new IntRange(n, Integer.MAX_VALUE), "union_all", null, 4, null);
        $VALUES = nAryOpArray;
        Companion = new Companion(null);
        NAryOp[] nAryOpArray2 = NAryOp.values();
        NAryOp[] nAryOpArray3 = nAryOpArray;
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
            Pair<String, void> pair = new Pair<String, void>(it.symbol, it);
            collection.add(pair);
        }
        collection = (List)var3_5;
        NAryOp[] nAryOpArray4 = nAryOpArray3;
        OP_SYMBOL_TO_OP_LOOKUP = MapsKt.toMap((Iterable)collection);
    }

    @NotNull
    public final IntRange getArityRange() {
        return this.arityRange;
    }

    @NotNull
    public final String getSymbol() {
        return this.symbol;
    }

    @NotNull
    public final String getTextName() {
        return this.textName;
    }

    private NAryOp(IntRange arityRange, String symbol, String textName) {
        this.arityRange = arityRange;
        this.symbol = symbol;
        this.textName = textName;
    }

    /* synthetic */ NAryOp(String string, int n, IntRange intRange, String string2, String string3, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 4) != 0) {
            string3 = string2;
        }
        this(intRange, string2, string3);
    }

    public static NAryOp[] values() {
        return (NAryOp[])$VALUES.clone();
    }

    public static NAryOp valueOf(String string) {
        return Enum.valueOf(NAryOp.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/ast/NAryOp$Companion;", "", "()V", "OP_SYMBOL_TO_OP_LOOKUP", "", "", "Lorg/partiql/lang/ast/NAryOp;", "forSymbol", "symbol", "lang"})
    public static final class Companion {
        @Nullable
        public final NAryOp forSymbol(@NotNull String symbol) {
            Intrinsics.checkParameterIsNotNull(symbol, "symbol");
            return (NAryOp)((Object)OP_SYMBOL_TO_OP_LOOKUP.get(symbol));
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

