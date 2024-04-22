/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins.timestamp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.builtins.timestamp.State;
import org.partiql.lang.eval.builtins.timestamp.StateType;
import org.partiql.lang.eval.builtins.timestamp.TableState;
import org.partiql.lang.eval.builtins.timestamp.TextState;
import org.partiql.lang.eval.builtins.timestamp.TimestampFormatPatternLexer;
import org.partiql.lang.eval.builtins.timestamp.Token;
import org.partiql.lang.eval.builtins.timestamp.TokenType;
import org.partiql.lang.util.PropertyMapHelpersKt;
import org.partiql.lang.util.StringExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\u000e*\u00060\u000fj\u0002`\u0010H\u0002\u00a8\u0006\u0012"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/TimestampFormatPatternLexer;", "", "()V", "tokenEnd", "", "current", "Lorg/partiql/lang/eval/builtins/timestamp/State;", "next", "tokenize", "", "Lorg/partiql/lang/eval/builtins/timestamp/Token;", "source", "", "reset", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "Companion", "lang"})
public final class TimestampFormatPatternLexer {
    private static final State ERROR_STATE;
    private static final TableState INITIAL_STATE;
    public static final Companion Companion;

    private final void reset(@NotNull StringBuilder $this$reset) {
        $this$reset.setLength(0);
    }

    private final boolean tokenEnd(State current, State next) {
        return current.getStateType() == StateType.INITIAL ? false : (current.getTokenType() == next.getTokenType() && next.getStateType().getBeginsToken() ? true : current.getTokenType() != next.getTokenType());
    }

    @NotNull
    public final List<Token> tokenize(@NotNull String source) {
        Sequence<Integer> codePoints;
        Intrinsics.checkParameterIsNotNull(source, "source");
        boolean bl = false;
        List tokens = new ArrayList();
        StringBuilder buffer = new StringBuilder();
        CharSequence charSequence = source;
        boolean bl2 = false;
        if (charSequence.length() == 0) {
            boolean bl3 = false;
            return CollectionsKt.emptyList();
        }
        Function1<TokenType, Unit> $fun$flushToken$1 = new Function1<TokenType, Unit>(this, tokens, buffer){
            final /* synthetic */ TimestampFormatPatternLexer this$0;
            final /* synthetic */ List $tokens;
            final /* synthetic */ StringBuilder $buffer;

            public final void invoke(@NotNull TokenType tokenType) {
                Intrinsics.checkParameterIsNotNull((Object)((Object)tokenType), "tokenType");
                String string = this.$buffer.toString();
                Intrinsics.checkExpressionValueIsNotNull(string, "buffer.toString()");
                this.$tokens.add(new Token(tokenType, string));
                TimestampFormatPatternLexer.access$reset(this.this$0, this.$buffer);
            }
            {
                this.this$0 = timestampFormatPatternLexer;
                this.$tokens = list;
                this.$buffer = stringBuilder;
                super(1);
            }
        };
        State current = INITIAL_STATE;
        Sequence<Integer> $this$forEach$iv = codePoints = StringExtensionsKt.codePointSequence(source);
        boolean $i$f$forEach = false;
        Iterator<Integer> iterator2 = $this$forEach$iv.iterator();
        while (iterator2.hasNext()) {
            Integer element$iv = iterator2.next();
            int cp = ((Number)element$iv).intValue();
            boolean bl4 = false;
            State next = current.nextFor(cp);
            if (next.getStateType() == StateType.ERROR) {
                throw (Throwable)new EvaluationException("Invalid token in timestamp format pattern", ErrorCode.EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_TOKEN, PropertyMapHelpersKt.propertyValueMapOf(PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN, source)), null, false, 8, null);
            }
            if (this.tokenEnd(current, next)) {
                TokenType tokenType = current.getTokenType();
                if (tokenType == null) {
                    Intrinsics.throwNpe();
                }
                $fun$flushToken$1.invoke(tokenType);
            }
            current = next;
            buffer.appendCodePoint(cp);
        }
        if (!current.getStateType().getEndsToken()) {
            throw (Throwable)new EvaluationException("Unterminated token in timestamp format pattern", ErrorCode.EVALUATOR_UNTERMINATED_TIMESTAMP_FORMAT_PATTERN_TOKEN, PropertyMapHelpersKt.propertyValueMapOf(PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN, source)), null, false, 8, null);
        }
        TokenType tokenType = current.getTokenType();
        if (tokenType == null) {
            Intrinsics.throwNpe();
        }
        $fun$flushToken$1.invoke(tokenType);
        return tokens;
    }

    static {
        Companion = new Companion(null);
        ERROR_STATE = new State(){
            @Nullable
            private final Void tokenType;
            @NotNull
            private final StateType stateType;

            @Nullable
            public Void getTokenType() {
                return this.tokenType;
            }

            @NotNull
            public StateType getStateType() {
                return this.stateType;
            }

            @NotNull
            public State nextFor(int cp) {
                return this;
            }
            {
                this.stateType = StateType.ERROR;
            }
        };
        INITIAL_STATE = new TableState(null, StateType.INITIAL, ERROR_STATE);
        TableState startEscapedText = new TableState(TokenType.TEXT, StateType.START_AND_TERMINAL, INITIAL_STATE);
        TableState inNonEscapedText = new TableState(TokenType.TEXT, StateType.TERMINAL, INITIAL_STATE);
        startEscapedText.transitionTo(" /-,:.", (State)inNonEscapedText);
        inNonEscapedText.transitionTo(" /-,:.", (State)inNonEscapedText);
        TextState startQuotedText2 = new TextState(StateType.START){
            @NotNull
            private final Companion.startQuotedText.1 startQuotedText;
            @NotNull
            private final Companion.startQuotedText.endQuotedState.1 endQuotedState;
            @NotNull
            private final Companion.startQuotedText.inQuotedState.1 inQuotedState;

            @NotNull
            public final Companion.startQuotedText.1 getStartQuotedText() {
                return this.startQuotedText;
            }

            @NotNull
            public final Companion.startQuotedText.endQuotedState.1 getEndQuotedState() {
                return this.endQuotedState;
            }

            @NotNull
            public final Companion.startQuotedText.inQuotedState.1 getInQuotedState() {
                return this.inQuotedState;
            }

            @NotNull
            public State nextFor(int cp) {
                State state;
                switch (cp) {
                    case 39: {
                        state = this.endQuotedState;
                        break;
                    }
                    default: {
                        state = this.inQuotedState;
                    }
                }
                return state;
            }
            {
                this.startQuotedText = this;
                this.endQuotedState = new TextState(this, StateType.TERMINAL){
                    final /* synthetic */ Companion.startQuotedText.1 this$0;

                    @NotNull
                    public State nextFor(int cp) {
                        State state;
                        switch (cp) {
                            case 39: {
                                state = this.this$0.getStartQuotedText();
                                break;
                            }
                            default: {
                                state = TimestampFormatPatternLexer.access$getINITIAL_STATE$cp().nextFor(cp);
                            }
                        }
                        return state;
                    }
                    {
                        this.this$0 = $outer;
                        super($super_call_param$1);
                    }
                };
                this.inQuotedState = new TextState(this, StateType.INCOMPLETE){
                    final /* synthetic */ Companion.startQuotedText.1 this$0;

                    @NotNull
                    public State nextFor(int cp) {
                        State state;
                        switch (cp) {
                            case 39: {
                                state = this.this$0.getEndQuotedState();
                                break;
                            }
                            default: {
                                state = this;
                            }
                        }
                        return state;
                    }
                    {
                        this.this$0 = $outer;
                        super($super_call_param$1);
                    }
                };
            }
        };
        INITIAL_STATE.transitionTo(" /-,:.", (State)startEscapedText);
        INITIAL_STATE.transitionTo(39, (State)startQuotedText2);
        "yMdahHmsSXxn".codePoints().forEach(Companion.1.INSTANCE);
    }

    public static final /* synthetic */ void access$reset(TimestampFormatPatternLexer $this, StringBuilder $this$access_u24reset) {
        $this.reset($this$access_u24reset);
    }

    public static final /* synthetic */ TableState access$getINITIAL_STATE$cp() {
        return INITIAL_STATE;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/TimestampFormatPatternLexer$Companion;", "", "()V", "ERROR_STATE", "Lorg/partiql/lang/eval/builtins/timestamp/State;", "INITIAL_STATE", "Lorg/partiql/lang/eval/builtins/timestamp/TableState;", "lang"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

