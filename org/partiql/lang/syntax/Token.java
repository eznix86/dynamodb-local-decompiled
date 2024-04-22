/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.syntax;

import com.amazon.ion.IonValue;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.syntax.LexerConstantsKt;
import org.partiql.lang.syntax.SourceSpan;
import org.partiql.lang.syntax.Token$WhenMappings;
import org.partiql.lang.syntax.TokenType;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0015\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0007H\u00c6\u0003J)\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010$\u001a\u00020\u000e2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\nH\u00d6\u0001J\t\u0010'\u001a\u00020\u0013H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0011\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u000fR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u00138F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\n8F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u00138F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006("}, d2={"Lorg/partiql/lang/syntax/Token;", "", "type", "Lorg/partiql/lang/syntax/TokenType;", "value", "Lcom/amazon/ion/IonValue;", "span", "Lorg/partiql/lang/syntax/SourceSpan;", "(Lorg/partiql/lang/syntax/TokenType;Lcom/amazon/ion/IonValue;Lorg/partiql/lang/syntax/SourceSpan;)V", "infixPrecedence", "", "getInfixPrecedence", "()I", "isBinaryOperator", "", "()Z", "isSpecialOperator", "isUnaryOperator", "keywordText", "", "getKeywordText", "()Ljava/lang/String;", "prefixPrecedence", "getPrefixPrecedence", "getSpan", "()Lorg/partiql/lang/syntax/SourceSpan;", "text", "getText", "getType", "()Lorg/partiql/lang/syntax/TokenType;", "getValue", "()Lcom/amazon/ion/IonValue;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "toString", "lang"})
public final class Token {
    @NotNull
    private final TokenType type;
    @Nullable
    private final IonValue value;
    @NotNull
    private final SourceSpan span;

    @Nullable
    public final String getText() {
        IonValue ionValue2 = this.value;
        return ionValue2 != null ? IonValueExtensionsKt.stringValue(ionValue2) : null;
    }

    @Nullable
    public final String getKeywordText() {
        String string;
        switch (Token$WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                string = this.getText();
                break;
            }
            case 5: {
                string = "missing";
                break;
            }
            case 6: {
                string = "null";
                break;
            }
            default: {
                string = null;
            }
        }
        return string;
    }

    public final boolean isSpecialOperator() {
        boolean bl;
        switch (Token$WhenMappings.$EnumSwitchMapping$1[this.type.ordinal()]) {
            case 1: {
                bl = CollectionsKt.contains((Iterable)LexerConstantsKt.SPECIAL_OPERATORS, this.getText());
                break;
            }
            default: {
                bl = false;
            }
        }
        return bl;
    }

    public final boolean isBinaryOperator() {
        boolean bl;
        switch (Token$WhenMappings.$EnumSwitchMapping$2[this.type.ordinal()]) {
            case 1: 
            case 2: {
                bl = CollectionsKt.contains((Iterable)LexerConstantsKt.BINARY_OPERATORS, this.getText());
                break;
            }
            case 3: {
                bl = true;
                break;
            }
            default: {
                bl = false;
            }
        }
        return bl;
    }

    public final boolean isUnaryOperator() {
        boolean bl;
        switch (Token$WhenMappings.$EnumSwitchMapping$3[this.type.ordinal()]) {
            case 1: 
            case 2: {
                bl = CollectionsKt.contains((Iterable)LexerConstantsKt.UNARY_OPERATORS, this.getText());
                break;
            }
            default: {
                bl = false;
            }
        }
        return bl;
    }

    public final int getPrefixPrecedence() {
        int n;
        if (this.isUnaryOperator()) {
            Map<String, Integer> map2 = LexerConstantsKt.OPERATOR_PRECEDENCE;
            String string = this.getText();
            boolean bl = false;
            Integer n2 = map2.get(string);
            n = n2 != null ? n2 : 0;
        } else {
            n = 0;
        }
        return n;
    }

    public final int getInfixPrecedence() {
        int n;
        if (this.isBinaryOperator() || this.isSpecialOperator()) {
            Map<String, Integer> map2 = LexerConstantsKt.OPERATOR_PRECEDENCE;
            String string = this.getText();
            boolean bl = false;
            Integer n2 = map2.get(string);
            n = n2 != null ? n2 : 0;
        } else {
            n = 0;
        }
        return n;
    }

    @NotNull
    public final TokenType getType() {
        return this.type;
    }

    @Nullable
    public final IonValue getValue() {
        return this.value;
    }

    @NotNull
    public final SourceSpan getSpan() {
        return this.span;
    }

    public Token(@NotNull TokenType type, @Nullable IonValue value, @NotNull SourceSpan span) {
        Intrinsics.checkParameterIsNotNull((Object)type, "type");
        Intrinsics.checkParameterIsNotNull(span, "span");
        this.type = type;
        this.value = value;
        this.span = span;
    }

    public /* synthetic */ Token(TokenType tokenType, IonValue ionValue2, SourceSpan sourceSpan, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            ionValue2 = null;
        }
        this(tokenType, ionValue2, sourceSpan);
    }

    @NotNull
    public final TokenType component1() {
        return this.type;
    }

    @Nullable
    public final IonValue component2() {
        return this.value;
    }

    @NotNull
    public final SourceSpan component3() {
        return this.span;
    }

    @NotNull
    public final Token copy(@NotNull TokenType type, @Nullable IonValue value, @NotNull SourceSpan span) {
        Intrinsics.checkParameterIsNotNull((Object)type, "type");
        Intrinsics.checkParameterIsNotNull(span, "span");
        return new Token(type, value, span);
    }

    public static /* synthetic */ Token copy$default(Token token, TokenType tokenType, IonValue ionValue2, SourceSpan sourceSpan, int n, Object object) {
        if ((n & 1) != 0) {
            tokenType = token.type;
        }
        if ((n & 2) != 0) {
            ionValue2 = token.value;
        }
        if ((n & 4) != 0) {
            sourceSpan = token.span;
        }
        return token.copy(tokenType, ionValue2, sourceSpan);
    }

    @NotNull
    public String toString() {
        return "Token(type=" + (Object)((Object)this.type) + ", value=" + this.value + ", span=" + this.span + ")";
    }

    public int hashCode() {
        TokenType tokenType = this.type;
        IonValue ionValue2 = this.value;
        SourceSpan sourceSpan = this.span;
        return ((tokenType != null ? ((Object)((Object)tokenType)).hashCode() : 0) * 31 + (ionValue2 != null ? ((Object)ionValue2).hashCode() : 0)) * 31 + (sourceSpan != null ? ((Object)sourceSpan).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Token)) break block3;
                Token token = (Token)object;
                if (!Intrinsics.areEqual((Object)this.type, (Object)token.type) || !Intrinsics.areEqual(this.value, token.value) || !Intrinsics.areEqual(this.span, token.span)) break block3;
            }
            return true;
        }
        return false;
    }
}

