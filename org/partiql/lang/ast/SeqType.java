/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/ast/SeqType;", "", "typeName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getTypeName", "()Ljava/lang/String;", "LIST", "SEXP", "BAG", "lang"})
public final class SeqType
extends Enum<SeqType> {
    public static final /* enum */ SeqType LIST;
    public static final /* enum */ SeqType SEXP;
    public static final /* enum */ SeqType BAG;
    private static final /* synthetic */ SeqType[] $VALUES;
    @NotNull
    private final String typeName;

    static {
        SeqType[] seqTypeArray = new SeqType[3];
        SeqType[] seqTypeArray2 = seqTypeArray;
        seqTypeArray[0] = LIST = new SeqType("list");
        seqTypeArray[1] = SEXP = new SeqType("sexp");
        seqTypeArray[2] = BAG = new SeqType("bag");
        $VALUES = seqTypeArray;
    }

    @NotNull
    public final String getTypeName() {
        return this.typeName;
    }

    private SeqType(String typeName) {
        this.typeName = typeName;
    }

    public static SeqType[] values() {
        return (SeqType[])$VALUES.clone();
    }

    public static SeqType valueOf(String string) {
        return Enum.valueOf(SeqType.class, string);
    }
}

