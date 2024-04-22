/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.builtins.timestamp.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/Token;", "", "tokenType", "Lorg/partiql/lang/eval/builtins/timestamp/TokenType;", "value", "", "(Lorg/partiql/lang/eval/builtins/timestamp/TokenType;Ljava/lang/String;)V", "getTokenType", "()Lorg/partiql/lang/eval/builtins/timestamp/TokenType;", "getValue", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "lang"})
public final class Token {
    @NotNull
    private final TokenType tokenType;
    @NotNull
    private final String value;

    @NotNull
    public final TokenType getTokenType() {
        return this.tokenType;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }

    public Token(@NotNull TokenType tokenType, @NotNull String value) {
        Intrinsics.checkParameterIsNotNull((Object)tokenType, "tokenType");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.tokenType = tokenType;
        this.value = value;
    }

    @NotNull
    public final TokenType component1() {
        return this.tokenType;
    }

    @NotNull
    public final String component2() {
        return this.value;
    }

    @NotNull
    public final Token copy(@NotNull TokenType tokenType, @NotNull String value) {
        Intrinsics.checkParameterIsNotNull((Object)tokenType, "tokenType");
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new Token(tokenType, value);
    }

    public static /* synthetic */ Token copy$default(Token token, TokenType tokenType, String string, int n, Object object) {
        if ((n & 1) != 0) {
            tokenType = token.tokenType;
        }
        if ((n & 2) != 0) {
            string = token.value;
        }
        return token.copy(tokenType, string);
    }

    @NotNull
    public String toString() {
        return "Token(tokenType=" + (Object)((Object)this.tokenType) + ", value=" + this.value + ")";
    }

    public int hashCode() {
        TokenType tokenType = this.tokenType;
        String string = this.value;
        return (tokenType != null ? ((Object)((Object)tokenType)).hashCode() : 0) * 31 + (string != null ? string.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Token)) break block3;
                Token token = (Token)object;
                if (!Intrinsics.areEqual((Object)this.tokenType, (Object)token.tokenType) || !Intrinsics.areEqual(this.value, token.value)) break block3;
            }
            return true;
        }
        return false;
    }
}

