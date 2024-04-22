/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.builtins.timestamp.State;
import org.partiql.lang.eval.builtins.timestamp.StateType;
import org.partiql.lang.eval.builtins.timestamp.TableState;
import org.partiql.lang.eval.builtins.timestamp.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000*\u0001\u000f\b\u0002\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0001J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0010\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/TableState;", "Lorg/partiql/lang/eval/builtins/timestamp/State;", "tokenType", "Lorg/partiql/lang/eval/builtins/timestamp/TokenType;", "stateType", "Lorg/partiql/lang/eval/builtins/timestamp/StateType;", "delegate", "(Lorg/partiql/lang/eval/builtins/timestamp/TokenType;Lorg/partiql/lang/eval/builtins/timestamp/StateType;Lorg/partiql/lang/eval/builtins/timestamp/State;)V", "getDelegate", "()Lorg/partiql/lang/eval/builtins/timestamp/State;", "getStateType", "()Lorg/partiql/lang/eval/builtins/timestamp/StateType;", "getTokenType", "()Lorg/partiql/lang/eval/builtins/timestamp/TokenType;", "transitionTable", "org/partiql/lang/eval/builtins/timestamp/TableState$transitionTable$1", "Lorg/partiql/lang/eval/builtins/timestamp/TableState$transitionTable$1;", "nextFor", "cp", "", "transitionTo", "", "codePoint", "next", "characters", "", "lang"})
final class TableState
implements State {
    private final transitionTable.1 transitionTable;
    @Nullable
    private final TokenType tokenType;
    @NotNull
    private final StateType stateType;
    @Nullable
    private final State delegate;

    public final void transitionTo(@NotNull String characters, @NotNull State next) {
        Intrinsics.checkParameterIsNotNull(characters, "characters");
        Intrinsics.checkParameterIsNotNull(next, "next");
        CharSequence $this$forEach$iv = characters;
        boolean $i$f$forEach = false;
        CharSequence charSequence = $this$forEach$iv;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element$iv;
            char it = element$iv = charSequence.charAt(i);
            boolean bl = false;
            char cp = it;
            this.transitionTo(cp, next);
        }
    }

    public final void transitionTo(int codePoint, @NotNull State next) {
        Intrinsics.checkParameterIsNotNull(next, "next");
        this.transitionTable.set(codePoint, next);
    }

    @Override
    @NotNull
    public State nextFor(int cp) {
        State state = this.transitionTable.get(cp);
        if (state == null) {
            State state2 = this.delegate;
            state = state2 != null ? state2.nextFor(cp) : null;
        }
        if (state == null) {
            throw (Throwable)new IllegalStateException("Unknown transition");
        }
        return state;
    }

    @Override
    @Nullable
    public TokenType getTokenType() {
        return this.tokenType;
    }

    @Override
    @NotNull
    public StateType getStateType() {
        return this.stateType;
    }

    @Nullable
    public final State getDelegate() {
        return this.delegate;
    }

    public TableState(@Nullable TokenType tokenType, @NotNull StateType stateType, @Nullable State delegate2) {
        Intrinsics.checkParameterIsNotNull((Object)stateType, "stateType");
        this.tokenType = tokenType;
        this.stateType = stateType;
        this.delegate = delegate2;
        this.transitionTable = new Object(){
            @NotNull
            private final State[] backingArray;

            @NotNull
            public final State[] getBackingArray() {
                return this.backingArray;
            }

            @Nullable
            public final State get(int codePoint) {
                return codePoint < 122 ? this.backingArray[codePoint] : null;
            }

            public final void set(int codePoint, @NotNull State next) {
                Intrinsics.checkParameterIsNotNull(next, "next");
                this.backingArray[codePoint] = next;
            }
            {
                State[] stateArray;
                int n = 122;
                transitionTable.1 var6_2 = this;
                State[] stateArray2 = new State[n];
                int n2 = 0;
                while (n2 < n) {
                    int n3 = n2;
                    int n4 = n2++;
                    stateArray = stateArray2;
                    boolean bl = false;
                    Object var9_9 = null;
                    stateArray[n4] = var9_9;
                }
                stateArray = stateArray2;
                var6_2.backingArray = stateArray;
            }
        };
    }

    public /* synthetic */ TableState(TokenType tokenType, StateType stateType, State state, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            state = null;
        }
        this(tokenType, stateType, state);
    }
}

