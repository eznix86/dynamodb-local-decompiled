/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.builtins.timestamp.StateType;
import org.partiql.lang.eval.builtins.timestamp.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\bb\u0018\u00002\u00020\u0001J\u0010\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\fH&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\r"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/State;", "", "stateType", "Lorg/partiql/lang/eval/builtins/timestamp/StateType;", "getStateType", "()Lorg/partiql/lang/eval/builtins/timestamp/StateType;", "tokenType", "Lorg/partiql/lang/eval/builtins/timestamp/TokenType;", "getTokenType", "()Lorg/partiql/lang/eval/builtins/timestamp/TokenType;", "nextFor", "cp", "", "lang"})
interface State {
    @Nullable
    public TokenType getTokenType();

    @NotNull
    public StateType getStateType();

    @NotNull
    public State nextFor(int var1);
}

