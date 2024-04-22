/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.builtins.timestamp.State;
import org.partiql.lang.eval.builtins.timestamp.StateType;
import org.partiql.lang.eval.builtins.timestamp.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/TextState;", "Lorg/partiql/lang/eval/builtins/timestamp/State;", "stateType", "Lorg/partiql/lang/eval/builtins/timestamp/StateType;", "(Lorg/partiql/lang/eval/builtins/timestamp/StateType;)V", "getStateType", "()Lorg/partiql/lang/eval/builtins/timestamp/StateType;", "tokenType", "Lorg/partiql/lang/eval/builtins/timestamp/TokenType;", "getTokenType", "()Lorg/partiql/lang/eval/builtins/timestamp/TokenType;", "lang"})
abstract class TextState
implements State {
    @NotNull
    private final TokenType tokenType;
    @NotNull
    private final StateType stateType;

    @Override
    @NotNull
    public TokenType getTokenType() {
        return this.tokenType;
    }

    @Override
    @NotNull
    public StateType getStateType() {
        return this.stateType;
    }

    public TextState(@NotNull StateType stateType) {
        Intrinsics.checkParameterIsNotNull((Object)stateType, "stateType");
        this.stateType = stateType;
        this.tokenType = TokenType.TEXT;
    }
}

