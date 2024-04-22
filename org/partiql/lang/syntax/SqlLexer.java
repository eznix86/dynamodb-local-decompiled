/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.syntax;

import com.amazon.ion.IonException;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.syntax.Lexer;
import org.partiql.lang.syntax.LexerConstantsKt;
import org.partiql.lang.syntax.LexerException;
import org.partiql.lang.syntax.SourcePosition;
import org.partiql.lang.syntax.SourceSpan;
import org.partiql.lang.syntax.SqlLexer;
import org.partiql.lang.syntax.SqlLexer$WhenMappings;
import org.partiql.lang.syntax.Token;
import org.partiql.lang.syntax.TokenType;
import org.partiql.lang.util.IonValueExtensionsKt;
import org.partiql.lang.util.NumberExtensionsKt;
import org.partiql.lang.util.StringExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010!\n\u0002\b\t\u0018\u0000 \u00162\u00020\u0001:\u0007\u0016\u0017\u0018\u0019\u001a\u001b\u001cB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\bH\u0016J\u001a\u0010\u0012\u001a\u00020\u0013*\b\u0012\u0004\u0012\u00020\u00100\u00142\u0006\u0010\u0015\u001a\u00020\u0010H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lorg/partiql/lang/syntax/SqlLexer;", "Lorg/partiql/lang/syntax/Lexer;", "ion", "Lcom/amazon/ion/IonSystem;", "(Lcom/amazon/ion/IonSystem;)V", "makePropertyBag", "Lorg/partiql/lang/errors/PropertyValueMap;", "tokenString", "", "tracker", "Lorg/partiql/lang/syntax/SqlLexer$PositionTracker;", "repr", "codePoint", "", "tokenize", "", "Lorg/partiql/lang/syntax/Token;", "source", "addOrMerge", "", "", "token", "Companion", "LexType", "PositionTracker", "RepeatingState", "State", "StateType", "TableState", "lang"})
public final class SqlLexer
implements Lexer {
    private final IonSystem ion;
    private static final int CR = 13;
    private static final int LF = 10;
    private static final int EOF = -1;
    private static final int TABLE_SIZE = 127;
    private static final int REPLACE_SAME = -1;
    private static final int REPLACE_NOTHING = -2;
    private static final RepeatingState EOF_STATE;
    private static final RepeatingState ERROR_STATE;
    private static final TableState INITIAL_STATE;
    public static final Companion Companion;

    private final String repr(int codePoint) {
        String string;
        if (codePoint == EOF) {
            string = "<EOF>";
        } else if (codePoint < EOF) {
            string = "" + '<' + codePoint + '>';
        } else {
            StringBuilder stringBuilder = new StringBuilder().append('\'');
            char[] cArray = Character.toChars(codePoint);
            Intrinsics.checkExpressionValueIsNotNull(cArray, "Character.toChars(codePoint)");
            char[] cArray2 = cArray;
            StringBuilder stringBuilder2 = stringBuilder;
            boolean bl = false;
            String string2 = new String(cArray2);
            string = stringBuilder2.append(string2).append("' [U+").append(Integer.toHexString(codePoint)).append(']').toString();
        }
        return string;
    }

    private final PropertyValueMap makePropertyBag(String tokenString, PositionTracker tracker) {
        PropertyValueMap pvmap = new PropertyValueMap(null, 1, null);
        pvmap.set(Property.LINE_NUMBER, tracker.getLine());
        pvmap.set(Property.COLUMN_NUMBER, tracker.getCol());
        pvmap.set(Property.TOKEN_STRING, tokenString);
        return pvmap;
    }

    @Override
    @NotNull
    public List<Token> tokenize(@NotNull String source) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        Sequence<Integer> codePoints = SequencesKt.plus(StringExtensionsKt.codePointSequence(source), EOF);
        ArrayList<Token> tokens = new ArrayList<Token>();
        PositionTracker tracker = new PositionTracker();
        int parameterCt = 0;
        SourcePosition currPos = tracker.getPosition();
        long tokenCodePointCount = 0L;
        State curr = INITIAL_STATE;
        StringBuilder buffer = new StringBuilder();
        Iterator<Integer> iterator2 = codePoints.iterator();
        while (iterator2.hasNext()) {
            int cp = ((Number)iterator2.next()).intValue();
            long l = tokenCodePointCount;
            tokenCodePointCount = l + 1L;
            Function0 $fun$errInvalidChar$1 = new Function0(this, cp, tracker){
                final /* synthetic */ SqlLexer this$0;
                final /* synthetic */ int $cp;
                final /* synthetic */ PositionTracker $tracker;

                @NotNull
                public final Void invoke() {
                    throw (Throwable)new LexerException(null, ErrorCode.LEXER_INVALID_CHAR, SqlLexer.access$makePropertyBag(this.this$0, SqlLexer.access$repr(this.this$0, this.$cp), this.$tracker), null, 9, null);
                }
                {
                    this.this$0 = sqlLexer;
                    this.$cp = n;
                    this.$tracker = positionTracker;
                    super(0);
                }
            };
            Function1 $fun$errInvalidOperator$2 = new Function1(this, tracker){
                final /* synthetic */ SqlLexer this$0;
                final /* synthetic */ PositionTracker $tracker;

                @NotNull
                public final Void invoke(@NotNull String operator) {
                    Intrinsics.checkParameterIsNotNull(operator, "operator");
                    throw (Throwable)new LexerException(null, ErrorCode.LEXER_INVALID_OPERATOR, SqlLexer.access$makePropertyBag(this.this$0, operator, this.$tracker), null, 9, null);
                }
                {
                    this.this$0 = sqlLexer;
                    this.$tracker = positionTracker;
                    super(1);
                }
            };
            Function1 $fun$errInvalidLiteral$3 = new Function1(this, tracker){
                final /* synthetic */ SqlLexer this$0;
                final /* synthetic */ PositionTracker $tracker;

                @NotNull
                public final Void invoke(@NotNull String literal) {
                    Intrinsics.checkParameterIsNotNull(literal, "literal");
                    throw (Throwable)new LexerException(null, ErrorCode.LEXER_INVALID_LITERAL, SqlLexer.access$makePropertyBag(this.this$0, literal, this.$tracker), null, 9, null);
                }
                {
                    this.this$0 = sqlLexer;
                    this.$tracker = positionTracker;
                    super(1);
                }
            };
            Function2 $fun$errInvalidIonLiteral$4 = new Function2(this, tracker){
                final /* synthetic */ SqlLexer this$0;
                final /* synthetic */ PositionTracker $tracker;

                @NotNull
                public final Void invoke(@NotNull String literal, @NotNull IonException cause) {
                    Intrinsics.checkParameterIsNotNull(literal, "literal");
                    Intrinsics.checkParameterIsNotNull(cause, "cause");
                    throw (Throwable)new LexerException(null, ErrorCode.LEXER_INVALID_ION_LITERAL, SqlLexer.access$makePropertyBag(this.this$0, literal, this.$tracker), cause, 1, null);
                }
                {
                    this.this$0 = sqlLexer;
                    this.$tracker = positionTracker;
                    super(2);
                }
            };
            tracker.advance(cp);
            int n = cp;
            State next = n == EOF ? (State)EOF_STATE : curr.get(cp);
            StateType currType = curr.getStateType();
            StateType nextType = next.getStateType();
            if (nextType == StateType.ERROR) {
                Object object = $fun$errInvalidChar$1.invoke();
                throw null;
            }
            if (nextType.getBeginsToken()) {
                if (currType != StateType.INITIAL && !currType.getEndsToken()) {
                    Object object = $fun$errInvalidChar$1.invoke();
                    throw null;
                }
                if (currType.getEndsToken() && curr.getLexType() != LexType.WHITESPACE) {
                    IonValue ionValue2;
                    TokenType tokenType;
                    Intrinsics.checkExpressionValueIsNotNull(buffer.toString(), "buffer.toString()");
                    if (curr.getTokenType() == null) {
                        Intrinsics.throwNpe();
                    }
                    block2 : switch (SqlLexer$WhenMappings.$EnumSwitchMapping$1[tokenType.ordinal()]) {
                        case 1: {
                            String unaliased;
                            String text;
                            String string = LexerConstantsKt.OPERATOR_ALIASES.get(text);
                            if (string == null) {
                                string = text;
                            }
                            String string2 = unaliased = string;
                            if (!LexerConstantsKt.ALL_OPERATORS.contains(string2)) {
                                Void void_ = $fun$errInvalidOperator$2.invoke(unaliased);
                                throw null;
                            }
                            ionValue2 = this.ion.newSymbol(unaliased);
                            break;
                        }
                        case 2: {
                            String lower;
                            String text;
                            String string2 = text;
                            boolean bl = false;
                            String string = string2;
                            if (string == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }
                            Intrinsics.checkExpressionValueIsNotNull(string.toLowerCase(), "(this as java.lang.String).toLowerCase()");
                            if (curr.getLexType() == LexType.DQ_STRING) {
                                ionValue2 = this.ion.newSymbol(text);
                                break;
                            }
                            if (LexerConstantsKt.ALL_SINGLE_LEXEME_OPERATORS.contains(lower)) {
                                tokenType = TokenType.OPERATOR;
                                ionValue2 = this.ion.newSymbol(lower);
                                break;
                            }
                            if (Intrinsics.areEqual(lower, "as")) {
                                tokenType = TokenType.AS;
                                ionValue2 = this.ion.newSymbol(lower);
                                break;
                            }
                            if (Intrinsics.areEqual(lower, "at")) {
                                tokenType = TokenType.AT;
                                ionValue2 = this.ion.newSymbol(lower);
                                break;
                            }
                            if (Intrinsics.areEqual(lower, "by")) {
                                tokenType = TokenType.BY;
                                ionValue2 = this.ion.newSymbol(lower);
                                break;
                            }
                            if (Intrinsics.areEqual(lower, "null")) {
                                tokenType = TokenType.NULL;
                                ionValue2 = this.ion.newNull();
                                break;
                            }
                            if (Intrinsics.areEqual(lower, "missing")) {
                                tokenType = TokenType.MISSING;
                                ionValue2 = this.ion.newNull();
                                break;
                            }
                            if (Intrinsics.areEqual(lower, "for")) {
                                tokenType = TokenType.FOR;
                                ionValue2 = this.ion.newSymbol(lower);
                                break;
                            }
                            if (Intrinsics.areEqual(lower, "asc")) {
                                tokenType = TokenType.ASC;
                                ionValue2 = this.ion.newSymbol(lower);
                                break;
                            }
                            if (Intrinsics.areEqual(lower, "desc")) {
                                tokenType = TokenType.DESC;
                                ionValue2 = this.ion.newSymbol(lower);
                                break;
                            }
                            if (LexerConstantsKt.BOOLEAN_KEYWORDS.contains(lower)) {
                                tokenType = TokenType.LITERAL;
                                ionValue2 = this.ion.newBool(Intrinsics.areEqual(lower, "true"));
                                break;
                            }
                            if (LexerConstantsKt.KEYWORDS.contains(lower)) {
                                tokenType = TokenType.KEYWORD;
                                String string3 = LexerConstantsKt.KEYWORD_ALIASES.get(lower);
                                if (string3 == null) {
                                    string3 = lower;
                                }
                                ionValue2 = this.ion.newSymbol(string3);
                                break;
                            }
                            ionValue2 = this.ion.newSymbol(text);
                            break;
                        }
                        case 3: {
                            IonValue ionValue3;
                            String text;
                            switch (SqlLexer$WhenMappings.$EnumSwitchMapping$0[curr.getLexType().ordinal()]) {
                                case 1: {
                                    ionValue2 = this.ion.newString(text);
                                    break block2;
                                }
                                case 2: {
                                    ionValue2 = this.ion.newInt(new BigInteger(text, 10));
                                    break block2;
                                }
                                case 3: {
                                    try {
                                        ionValue3 = this.ion.newDecimal(NumberExtensionsKt.bigDecimalOf$default(text, null, 2, null));
                                    } catch (NumberFormatException e) {
                                        Void void_ = $fun$errInvalidLiteral$3.invoke(text);
                                        throw null;
                                    }
                                    ionValue2 = ionValue3;
                                    break block2;
                                }
                            }
                            Void void_ = $fun$errInvalidLiteral$3.invoke(text);
                            throw null;
                        }
                        case 4: {
                            IonValue ionValue3;
                            String text;
                            try {
                                ionValue3 = this.ion.singleValue(text);
                            } catch (IonException e) {
                                Void void_ = $fun$errInvalidIonLiteral$4.invoke(text, e);
                                throw null;
                            }
                            ionValue2 = ionValue3;
                            break;
                        }
                        case 5: {
                            ionValue2 = this.ion.newInt(++parameterCt);
                            break;
                        }
                        default: {
                            String text;
                            ionValue2 = this.ion.newSymbol(text);
                        }
                    }
                    Intrinsics.checkExpressionValueIsNotNull(ionValue2, "when (tokenType) {\n     \u2026                        }");
                    IonValue ionValue4 = IonValueExtensionsKt.seal(ionValue2);
                    this.addOrMerge((List<Token>)tokens, new Token(tokenType, ionValue4, new SourceSpan(currPos.getLine(), currPos.getColumn(), tokenCodePointCount)));
                }
                buffer.setLength(0);
                currPos = tracker.getPosition();
                tokenCodePointCount = 0L;
            }
            int replacement = next.getReplacement();
            if (cp != EOF && replacement != REPLACE_NOTHING) {
                int n2 = replacement;
                buffer.appendCodePoint(n2 == REPLACE_SAME ? cp : replacement);
            }
            if (next.getStateType() == StateType.END) {
                tokens.add(new Token(TokenType.EOF, this.ion.newSymbol("EOF"), new SourceSpan(currPos.getLine(), currPos.getColumn(), 0L)));
            }
            curr = next;
        }
        return tokens;
    }

    /*
     * WARNING - void declaration
     */
    private final void addOrMerge(@NotNull List<Token> $this$addOrMerge, Token token) {
        Token newToken = token;
        int n = LexerConstantsKt.MULTI_LEXEME_MAX_LENGTH;
        int n2 = LexerConstantsKt.MULTI_LEXEME_MIN_LENGTH;
        if (n >= n2) {
            while (true) {
                void i;
                void prefixLength;
                if ((prefixLength = i - true) <= $this$addOrMerge.size()) {
                    List keywords2 = SequencesKt.toList(SequencesKt.map(SequencesKt.plus(CollectionsKt.asSequence((Iterable)$this$addOrMerge.subList($this$addOrMerge.size() - prefixLength, $this$addOrMerge.size())), newToken), addOrMerge.keywords.1.INSTANCE));
                    Map<List<String>, Pair<String, TokenType>> map2 = LexerConstantsKt.MULTI_LEXEME_TOKEN_MAP;
                    boolean bl = false;
                    if (map2.get(keywords2) == null) {
                    } else {
                        void keyword;
                        Pair<String, TokenType> lexemeMapping;
                        SourceSpan newPos = newToken.getSpan();
                        bl = true;
                        void var11_13 = prefixLength;
                        if (bl <= var11_13) {
                            while (true) {
                                void count;
                                newPos = $this$addOrMerge.remove($this$addOrMerge.size() - 1).getSpan();
                                if (count == var11_13) break;
                                ++count;
                            }
                        }
                        Pair<String, TokenType> pair = lexemeMapping;
                        String count = pair.component1();
                        TokenType type = pair.component2();
                        newToken = new Token(type, this.ion.newSymbol((String)keyword), newPos);
                    }
                }
                if (i == n2) break;
                --i;
            }
        }
        $this$addOrMerge.add(newToken);
    }

    public SqlLexer(@NotNull IonSystem ion) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        this.ion = ion;
    }

    static {
        Companion = new Companion(null);
        CR = 13;
        LF = 10;
        EOF = -1;
        TABLE_SIZE = 127;
        REPLACE_SAME = -1;
        REPLACE_NOTHING = -2;
        EOF_STATE = new RepeatingState(StateType.END);
        ERROR_STATE = new RepeatingState(StateType.ERROR);
        INITIAL_STATE = new TableState(StateType.INITIAL, null, null, 0, null, Companion.INITIAL_STATE.1.INSTANCE, 30, null);
    }

    public static final /* synthetic */ PropertyValueMap access$makePropertyBag(SqlLexer $this, String tokenString, PositionTracker tracker) {
        return $this.makePropertyBag(tokenString, tracker);
    }

    public static final /* synthetic */ String access$repr(SqlLexer $this, int codePoint) {
        return $this.repr(codePoint);
    }

    public static final /* synthetic */ int access$getREPLACE_NOTHING$cp() {
        return REPLACE_NOTHING;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001b\b\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f\u00a8\u0006\u0010"}, d2={"Lorg/partiql/lang/syntax/SqlLexer$StateType;", "", "beginsToken", "", "endsToken", "(Ljava/lang/String;IZZ)V", "getBeginsToken", "()Z", "getEndsToken", "INITIAL", "ERROR", "END", "INCOMPLETE", "START", "START_AND_TERMINAL", "TERMINAL", "lang"})
    public static final class StateType
    extends Enum<StateType> {
        public static final /* enum */ StateType INITIAL;
        public static final /* enum */ StateType ERROR;
        public static final /* enum */ StateType END;
        public static final /* enum */ StateType INCOMPLETE;
        public static final /* enum */ StateType START;
        public static final /* enum */ StateType START_AND_TERMINAL;
        public static final /* enum */ StateType TERMINAL;
        private static final /* synthetic */ StateType[] $VALUES;
        private final boolean beginsToken;
        private final boolean endsToken;

        static {
            StateType[] stateTypeArray = new StateType[7];
            StateType[] stateTypeArray2 = stateTypeArray;
            stateTypeArray[0] = INITIAL = new StateType("INITIAL", 0, false, false, 3, null);
            stateTypeArray[1] = ERROR = new StateType("ERROR", 1, false, false, 3, null);
            stateTypeArray[2] = END = new StateType("END", 2, true, false, 2, null);
            stateTypeArray[3] = INCOMPLETE = new StateType("INCOMPLETE", 3, false, false, 3, null);
            stateTypeArray[4] = START = new StateType("START", 4, true, false, 2, null);
            stateTypeArray[5] = START_AND_TERMINAL = new StateType(true, true);
            stateTypeArray[6] = TERMINAL = new StateType("TERMINAL", 6, false, true, 1, null);
            $VALUES = stateTypeArray;
        }

        public final boolean getBeginsToken() {
            return this.beginsToken;
        }

        public final boolean getEndsToken() {
            return this.endsToken;
        }

        private StateType(boolean beginsToken, boolean endsToken) {
            this.beginsToken = beginsToken;
            this.endsToken = endsToken;
        }

        /* synthetic */ StateType(String string, int n, boolean bl, boolean bl2, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 1) != 0) {
                bl = false;
            }
            if ((n2 & 2) != 0) {
                bl2 = false;
            }
            this(bl, bl2);
        }

        public static StateType[] values() {
            return (StateType[])$VALUES.clone();
        }

        public static StateType valueOf(String string) {
            return Enum.valueOf(StateType.class, string);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\t\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/syntax/SqlLexer$LexType;", "", "(Ljava/lang/String;I)V", "NONE", "INTEGER", "DECIMAL", "SQ_STRING", "DQ_STRING", "ION_LITERAL", "WHITESPACE", "lang"})
    public static final class LexType
    extends Enum<LexType> {
        public static final /* enum */ LexType NONE;
        public static final /* enum */ LexType INTEGER;
        public static final /* enum */ LexType DECIMAL;
        public static final /* enum */ LexType SQ_STRING;
        public static final /* enum */ LexType DQ_STRING;
        public static final /* enum */ LexType ION_LITERAL;
        public static final /* enum */ LexType WHITESPACE;
        private static final /* synthetic */ LexType[] $VALUES;

        static {
            LexType[] lexTypeArray = new LexType[7];
            LexType[] lexTypeArray2 = lexTypeArray;
            lexTypeArray[0] = NONE = new LexType();
            lexTypeArray[1] = INTEGER = new LexType();
            lexTypeArray[2] = DECIMAL = new LexType();
            lexTypeArray[3] = SQ_STRING = new LexType();
            lexTypeArray[4] = DQ_STRING = new LexType();
            lexTypeArray[5] = ION_LITERAL = new LexType();
            lexTypeArray[6] = WHITESPACE = new LexType();
            $VALUES = lexTypeArray;
        }

        public static LexType[] values() {
            return (LexType[])$VALUES.clone();
        }

        public static LexType valueOf(String string) {
            return Enum.valueOf(LexType.class, string);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b`\u0018\u00002\u00020\u0001J\u0011\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0007H\u00a6\u0002R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lorg/partiql/lang/syntax/SqlLexer$State;", "", "lexType", "Lorg/partiql/lang/syntax/SqlLexer$LexType;", "getLexType", "()Lorg/partiql/lang/syntax/SqlLexer$LexType;", "replacement", "", "getReplacement", "()I", "stateType", "Lorg/partiql/lang/syntax/SqlLexer$StateType;", "getStateType", "()Lorg/partiql/lang/syntax/SqlLexer$StateType;", "tokenType", "Lorg/partiql/lang/syntax/TokenType;", "getTokenType", "()Lorg/partiql/lang/syntax/TokenType;", "get", "next", "lang"})
    public static interface State {
        @NotNull
        public StateType getStateType();

        @Nullable
        public TokenType getTokenType();

        @NotNull
        public LexType getLexType();

        public int getReplacement();

        @NotNull
        public State get(int var1);

        @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
        public static final class DefaultImpls {
            @Nullable
            public static TokenType getTokenType(State $this) {
                return null;
            }

            @NotNull
            public static LexType getLexType(State $this) {
                return LexType.NONE;
            }

            public static int getReplacement(State $this) {
                return REPLACE_SAME;
            }
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\tH\u0096\u0002R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/syntax/SqlLexer$RepeatingState;", "Lorg/partiql/lang/syntax/SqlLexer$State;", "stateType", "Lorg/partiql/lang/syntax/SqlLexer$StateType;", "(Lorg/partiql/lang/syntax/SqlLexer$StateType;)V", "getStateType", "()Lorg/partiql/lang/syntax/SqlLexer$StateType;", "get", "next", "", "lang"})
    public static final class RepeatingState
    implements State {
        @NotNull
        private final StateType stateType;

        @Override
        @NotNull
        public State get(int next) {
            return this;
        }

        @Override
        @NotNull
        public StateType getStateType() {
            return this.stateType;
        }

        public RepeatingState(@NotNull StateType stateType) {
            Intrinsics.checkParameterIsNotNull((Object)stateType, "stateType");
            this.stateType = stateType;
        }

        @Override
        @Nullable
        public TokenType getTokenType() {
            return State.DefaultImpls.getTokenType(this);
        }

        @Override
        @NotNull
        public LexType getLexType() {
            return State.DefaultImpls.getLexType(this);
        }

        @Override
        public int getReplacement() {
            return State.DefaultImpls.getReplacement(this);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u00002\u00020\u0001BR\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0001\u0012\u0019\b\u0002\u0010\u000b\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\u0002\b\u000e\u00a2\u0006\u0002\u0010\u000fJa\u0010!\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00012\u001f\b\u0002\u0010\u000b\u001a\u0019\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\r0$\u00a2\u0006\u0002\b\u000eJ\u0011\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020\tH\u0096\u0002J\u0012\u0010'\u001a\u0004\u0018\u00010\u00012\u0006\u0010&\u001a\u00020\tH\u0002J$\u0010(\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007J*\u0010)\u001a\u00020\r*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001b2\u0006\u0010\"\u001a\u00020#2\u0006\u0010*\u001a\u00020\u0001H\u0086\u0002\u00a2\u0006\u0002\u0010+R\u001a\u0010\n\u001a\u00020\u0001X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001b\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 \u00a8\u0006,"}, d2={"Lorg/partiql/lang/syntax/SqlLexer$TableState;", "Lorg/partiql/lang/syntax/SqlLexer$State;", "stateType", "Lorg/partiql/lang/syntax/SqlLexer$StateType;", "tokenType", "Lorg/partiql/lang/syntax/TokenType;", "lexType", "Lorg/partiql/lang/syntax/SqlLexer$LexType;", "replacement", "", "delegate", "setup", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "(Lorg/partiql/lang/syntax/SqlLexer$StateType;Lorg/partiql/lang/syntax/TokenType;Lorg/partiql/lang/syntax/SqlLexer$LexType;ILorg/partiql/lang/syntax/SqlLexer$State;Lkotlin/jvm/functions/Function1;)V", "getDelegate", "()Lorg/partiql/lang/syntax/SqlLexer$State;", "setDelegate", "(Lorg/partiql/lang/syntax/SqlLexer$State;)V", "getLexType", "()Lorg/partiql/lang/syntax/SqlLexer$LexType;", "getReplacement", "()I", "getStateType", "()Lorg/partiql/lang/syntax/SqlLexer$StateType;", "table", "", "getTable", "()[Lorg/partiql/lang/syntax/SqlLexer$State;", "[Lorg/partiql/lang/syntax/SqlLexer$State;", "getTokenType", "()Lorg/partiql/lang/syntax/TokenType;", "delta", "chars", "", "Lkotlin/Function2;", "get", "next", "getFromTable", "selfRepeatingDelegate", "set", "new", "([Lorg/partiql/lang/syntax/SqlLexer$State;Ljava/lang/String;Lorg/partiql/lang/syntax/SqlLexer$State;)V", "lang"})
    public static final class TableState
    implements State {
        @NotNull
        private final State[] table;
        @NotNull
        private final StateType stateType;
        @Nullable
        private final TokenType tokenType;
        @NotNull
        private final LexType lexType;
        private final int replacement;
        @NotNull
        private State delegate;

        @NotNull
        public final State[] getTable() {
            return this.table;
        }

        public final void set(@NotNull State[] $this$set, @NotNull String chars, @NotNull State state) {
            Intrinsics.checkParameterIsNotNull($this$set, "$this$set");
            Intrinsics.checkParameterIsNotNull(chars, "chars");
            Intrinsics.checkParameterIsNotNull(state, "new");
            CharSequence $this$forEach$iv = chars;
            boolean $i$f$forEach = false;
            CharSequence charSequence = $this$forEach$iv;
            for (int i = 0; i < charSequence.length(); ++i) {
                char element$iv;
                char it = element$iv = charSequence.charAt(i);
                boolean bl = false;
                char cp = it;
                State old = $this$set[cp];
                State state2 = old;
                if (state2 != null) {
                    throw (Throwable)new IllegalStateException("Cannot replace existing state " + old + " with " + state);
                }
                $this$set[cp] = state;
            }
        }

        private final State getFromTable(int next) {
            return next < TABLE_SIZE ? this.table[next] : null;
        }

        @Override
        @NotNull
        public State get(int next) {
            State state = this.getFromTable(next);
            if (state == null) {
                state = this.delegate.get(next);
            }
            return state;
        }

        public final void selfRepeatingDelegate(@NotNull StateType stateType, @Nullable TokenType tokenType, @NotNull LexType lexType) {
            Intrinsics.checkParameterIsNotNull((Object)stateType, "stateType");
            Intrinsics.checkParameterIsNotNull((Object)lexType, "lexType");
            this.delegate = new State(this, stateType, tokenType, lexType){
                @NotNull
                private final StateType stateType;
                @Nullable
                private final TokenType tokenType;
                @NotNull
                private final LexType lexType;
                final /* synthetic */ TableState this$0;
                final /* synthetic */ StateType $stateType;
                final /* synthetic */ TokenType $tokenType;
                final /* synthetic */ LexType $lexType;

                @NotNull
                public StateType getStateType() {
                    return this.stateType;
                }

                @Nullable
                public TokenType getTokenType() {
                    return this.tokenType;
                }

                @NotNull
                public LexType getLexType() {
                    return this.lexType;
                }

                @NotNull
                public State get(int next) {
                    State state = TableState.access$getFromTable(this.this$0, next);
                    if (state == null) {
                        state = this;
                    }
                    return state;
                }
                {
                    this.this$0 = $outer;
                    this.$stateType = $captured_local_variable$1;
                    this.$tokenType = $captured_local_variable$2;
                    this.$lexType = $captured_local_variable$3;
                    this.stateType = $captured_local_variable$1;
                    this.tokenType = $captured_local_variable$2;
                    this.lexType = $captured_local_variable$3;
                }

                public int getReplacement() {
                    return State.DefaultImpls.getReplacement(this);
                }
            };
        }

        public static /* synthetic */ void selfRepeatingDelegate$default(TableState tableState, StateType stateType, TokenType tokenType, LexType lexType, int n, Object object) {
            if ((n & 2) != 0) {
                tokenType = null;
            }
            if ((n & 4) != 0) {
                lexType = LexType.NONE;
            }
            tableState.selfRepeatingDelegate(stateType, tokenType, lexType);
        }

        @NotNull
        public final TableState delta(@NotNull String chars, @NotNull StateType stateType, @Nullable TokenType tokenType, @NotNull LexType lexType, int replacement, @NotNull State delegate2, @NotNull Function2<? super TableState, ? super String, Unit> setup) {
            Intrinsics.checkParameterIsNotNull(chars, "chars");
            Intrinsics.checkParameterIsNotNull((Object)stateType, "stateType");
            Intrinsics.checkParameterIsNotNull((Object)lexType, "lexType");
            Intrinsics.checkParameterIsNotNull(delegate2, "delegate");
            Intrinsics.checkParameterIsNotNull(setup, "setup");
            TableState child2 = new TableState(stateType, tokenType, lexType, replacement, delegate2, (Function1<? super TableState, Unit>)new Function1<TableState, Unit>(setup, chars){
                final /* synthetic */ Function2 $setup;
                final /* synthetic */ String $chars;

                public final void invoke(@NotNull TableState $receiver) {
                    Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                    this.$setup.invoke($receiver, this.$chars);
                }
                {
                    this.$setup = function2;
                    this.$chars = string;
                    super(1);
                }
            });
            this.set(this.table, chars, child2);
            return child2;
        }

        public static /* synthetic */ TableState delta$default(TableState tableState, String string, StateType stateType, TokenType tokenType, LexType lexType, int n, State state, Function2 function2, int n2, Object object) {
            if ((n2 & 4) != 0) {
                tokenType = null;
            }
            if ((n2 & 8) != 0) {
                lexType = LexType.NONE;
            }
            if ((n2 & 0x10) != 0) {
                n = REPLACE_SAME;
            }
            if ((n2 & 0x20) != 0) {
                state = tableState;
            }
            if ((n2 & 0x40) != 0) {
                function2 = delta.1.INSTANCE;
            }
            return tableState.delta(string, stateType, tokenType, lexType, n, state, function2);
        }

        @Override
        @NotNull
        public StateType getStateType() {
            return this.stateType;
        }

        @Override
        @Nullable
        public TokenType getTokenType() {
            return this.tokenType;
        }

        @Override
        @NotNull
        public LexType getLexType() {
            return this.lexType;
        }

        @Override
        public int getReplacement() {
            return this.replacement;
        }

        @NotNull
        public final State getDelegate() {
            return this.delegate;
        }

        public final void setDelegate(@NotNull State state) {
            Intrinsics.checkParameterIsNotNull(state, "<set-?>");
            this.delegate = state;
        }

        public TableState(@NotNull StateType stateType, @Nullable TokenType tokenType, @NotNull LexType lexType, int replacement, @NotNull State delegate2, @NotNull Function1<? super TableState, Unit> setup) {
            State[] stateArray;
            Intrinsics.checkParameterIsNotNull((Object)stateType, "stateType");
            Intrinsics.checkParameterIsNotNull((Object)lexType, "lexType");
            Intrinsics.checkParameterIsNotNull(delegate2, "delegate");
            Intrinsics.checkParameterIsNotNull(setup, "setup");
            this.stateType = stateType;
            this.tokenType = tokenType;
            this.lexType = lexType;
            this.replacement = replacement;
            this.delegate = delegate2;
            int n = TABLE_SIZE;
            TableState tableState = this;
            State[] stateArray2 = new State[n];
            int n2 = 0;
            while (n2 < n) {
                int n3 = n2;
                int n4 = n2++;
                stateArray = stateArray2;
                boolean bl = false;
                Object var15_15 = null;
                stateArray[n4] = var15_15;
            }
            stateArray = stateArray2;
            tableState.table = stateArray;
            setup.invoke(this);
        }

        public /* synthetic */ TableState(StateType stateType, TokenType tokenType, LexType lexType, int n, State state, Function1 function1, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 2) != 0) {
                tokenType = null;
            }
            if ((n2 & 4) != 0) {
                lexType = LexType.NONE;
            }
            if ((n2 & 8) != 0) {
                n = REPLACE_SAME;
            }
            if ((n2 & 0x10) != 0) {
                state = ERROR_STATE;
            }
            if ((n2 & 0x20) != 0) {
                function1 = 1.INSTANCE;
            }
            this(stateType, tokenType, lexType, n, state, function1);
        }

        public static final /* synthetic */ State access$getFromTable(TableState $this, int next) {
            return $this.getFromTable(next);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0006\u0010\u001a\u001a\u00020\u0017J\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u0011\u0010\f\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001d"}, d2={"Lorg/partiql/lang/syntax/SqlLexer$PositionTracker;", "", "()V", "col", "", "getCol", "()J", "setCol", "(J)V", "line", "getLine", "setLine", "position", "Lorg/partiql/lang/syntax/SourcePosition;", "getPosition", "()Lorg/partiql/lang/syntax/SourcePosition;", "sawCR", "", "getSawCR", "()Z", "setSawCR", "(Z)V", "advance", "", "next", "", "newline", "toString", "", "lang"})
    public static final class PositionTracker {
        private long line = 1L;
        private long col;
        private boolean sawCR;

        public final long getLine() {
            return this.line;
        }

        public final void setLine(long l) {
            this.line = l;
        }

        public final long getCol() {
            return this.col;
        }

        public final void setCol(long l) {
            this.col = l;
        }

        public final boolean getSawCR() {
            return this.sawCR;
        }

        public final void setSawCR(boolean bl) {
            this.sawCR = bl;
        }

        public final void newline() {
            long l = this.line;
            this.line = l + 1L;
            this.col = 0L;
        }

        public final void advance(int next) {
            int n = next;
            if (n == CR) {
                if (this.sawCR) {
                    this.newline();
                } else {
                    this.sawCR = true;
                }
            } else if (n == LF) {
                this.newline();
                this.sawCR = false;
            } else {
                if (this.sawCR) {
                    this.newline();
                    this.sawCR = false;
                }
                long l = this.col;
                this.col = l + 1L;
            }
        }

        @NotNull
        public final SourcePosition getPosition() {
            return new SourcePosition(this.line, this.col);
        }

        @NotNull
        public String toString() {
            return this.getPosition().toString();
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/syntax/SqlLexer$Companion;", "", "()V", "CR", "", "EOF", "EOF_STATE", "Lorg/partiql/lang/syntax/SqlLexer$RepeatingState;", "ERROR_STATE", "INITIAL_STATE", "Lorg/partiql/lang/syntax/SqlLexer$TableState;", "LF", "REPLACE_NOTHING", "REPLACE_SAME", "TABLE_SIZE", "lang"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

