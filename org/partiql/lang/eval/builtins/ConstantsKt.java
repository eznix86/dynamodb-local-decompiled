/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0010\f\n\u0002\b\u0003\"\u001a\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0005"}, d2={"TIMESTAMP_FORMAT_SYMBOLS", "", "", "getTIMESTAMP_FORMAT_SYMBOLS", "()Ljava/util/Set;", "lang"})
public final class ConstantsKt {
    @NotNull
    private static final Set<Character> TIMESTAMP_FORMAT_SYMBOLS = SetsKt.setOf(Character.valueOf('y'), Character.valueOf('M'), Character.valueOf('L'), Character.valueOf('d'), Character.valueOf('a'), Character.valueOf('h'), Character.valueOf('H'), Character.valueOf('m'), Character.valueOf('s'), Character.valueOf('S'), Character.valueOf('n'), Character.valueOf('X'), Character.valueOf('x'), Character.valueOf('O'), Character.valueOf('Z'));

    @NotNull
    public static final Set<Character> getTIMESTAMP_FORMAT_SYMBOLS() {
        return TIMESTAMP_FORMAT_SYMBOLS;
    }
}

