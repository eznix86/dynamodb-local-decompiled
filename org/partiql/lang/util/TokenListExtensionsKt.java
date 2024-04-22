/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.util;

import com.amazon.ion.IonValue;
import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.syntax.ParserException;
import org.partiql.lang.syntax.SourceSpan;
import org.partiql.lang.syntax.SqlParser;
import org.partiql.lang.syntax.Token;
import org.partiql.lang.syntax.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0002\u001a\u001c\u0010\u0005\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u0000\u001a,\u0010\u000b\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0002\u001a\u00020\u0001H\u0000\u001a(\u0010\u000b\u001a\u00020\f*\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0002\u001a\u00020\u0001H\u0000\u001a\u0016\u0010\u0011\u001a\u00020\f*\u0004\u0018\u00010\b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\u001a\u0012\u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u0007H\u0000\u001a \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0007*\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0017\u001a\u00020\u000eH\u0000\u001a \u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u0007*\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0019\u001a\u00020\u0013H\u0000\u00a8\u0006\u001a"}, d2={"populateLineAndColumn", "Lorg/partiql/lang/errors/PropertyValueMap;", "errorContext", "sourceSpan", "Lorg/partiql/lang/syntax/SourceSpan;", "atomFromHead", "Lorg/partiql/lang/syntax/SqlParser$ParseNode;", "", "Lorg/partiql/lang/syntax/Token;", "parseType", "Lorg/partiql/lang/syntax/SqlParser$ParseType;", "err", "", "message", "", "errorCode", "Lorg/partiql/lang/errors/ErrorCode;", "errExpectedTokenType", "expectedType", "Lorg/partiql/lang/syntax/TokenType;", "onlyEndOfStatement", "", "tailExpectedKeyword", "keyword", "tailExpectedToken", "tokenType", "lang"})
public final class TokenListExtensionsKt {
    public static final boolean onlyEndOfStatement(@NotNull List<Token> $this$onlyEndOfStatement) {
        Intrinsics.checkParameterIsNotNull($this$onlyEndOfStatement, "$this$onlyEndOfStatement");
        return $this$onlyEndOfStatement.size() == 1 && $this$onlyEndOfStatement.get(0).getType() == TokenType.EOF || $this$onlyEndOfStatement.size() == 2 && $this$onlyEndOfStatement.get(0).getType() == TokenType.SEMICOLON && $this$onlyEndOfStatement.get(1).getType() == TokenType.EOF;
    }

    /*
     * WARNING - void declaration
     */
    private static final PropertyValueMap populateLineAndColumn(PropertyValueMap errorContext, SourceSpan sourceSpan) {
        void line;
        SourceSpan sourceSpan2 = sourceSpan;
        if (sourceSpan2 == null) {
            return errorContext;
        }
        SourceSpan sourceSpan3 = sourceSpan;
        long l = sourceSpan3.component1();
        long col = sourceSpan3.component2();
        errorContext.set(Property.LINE_NUMBER, (long)line);
        errorContext.set(Property.COLUMN_NUMBER, col);
        return errorContext;
    }

    @NotNull
    public static final Void err(@Nullable Token $this$err, @NotNull String message, @NotNull ErrorCode errorCode, @NotNull PropertyValueMap errorContext) {
        PropertyValueMap pvmap;
        block1: {
            Intrinsics.checkParameterIsNotNull(message, "message");
            Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
            Intrinsics.checkParameterIsNotNull(errorContext, "errorContext");
            Token token = $this$err;
            if (token == null) {
                throw (Throwable)new ParserException(null, errorCode, errorContext, null, 9, null);
            }
            pvmap = TokenListExtensionsKt.populateLineAndColumn(errorContext, $this$err.getSpan());
            pvmap.set(Property.TOKEN_TYPE, $this$err.getType());
            IonValue ionValue2 = $this$err.getValue();
            if (ionValue2 == null) break block1;
            IonValue ionValue3 = ionValue2;
            boolean bl = false;
            boolean bl2 = false;
            IonValue it = ionValue3;
            boolean bl3 = false;
            pvmap.set(Property.TOKEN_VALUE, it);
        }
        throw (Throwable)new ParserException(message, errorCode, pvmap, null, 8, null);
    }

    public static /* synthetic */ Void err$default(Token token, String string, ErrorCode errorCode, PropertyValueMap propertyValueMap, int n, Object object) {
        if ((n & 4) != 0) {
            propertyValueMap = new PropertyValueMap(null, 1, null);
        }
        return TokenListExtensionsKt.err(token, string, errorCode, propertyValueMap);
    }

    @NotNull
    public static final Void errExpectedTokenType(@Nullable Token $this$errExpectedTokenType, @NotNull TokenType expectedType) {
        Intrinsics.checkParameterIsNotNull((Object)expectedType, "expectedType");
        PropertyValueMap pvmap = new PropertyValueMap(null, 1, null);
        pvmap.set(Property.EXPECTED_TOKEN_TYPE, expectedType);
        Void void_ = TokenListExtensionsKt.err($this$errExpectedTokenType, "Expected " + (Object)((Object)expectedType), ErrorCode.PARSE_EXPECTED_TOKEN_TYPE, pvmap);
        throw null;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final SqlParser.ParseNode atomFromHead(@NotNull List<Token> $this$atomFromHead, @NotNull SqlParser.ParseType parseType) {
        List<Token> list;
        List<Token> list2;
        void $this$tail$iv;
        List<Token> $this$head$iv;
        Intrinsics.checkParameterIsNotNull($this$atomFromHead, "$this$atomFromHead");
        Intrinsics.checkParameterIsNotNull((Object)parseType, "parseType");
        List<Token> list3 = $this$atomFromHead;
        SqlParser.ParseType parseType2 = parseType;
        boolean $i$f$getHead = false;
        Object object = CollectionsKt.firstOrNull($this$head$iv);
        $this$head$iv = $this$atomFromHead;
        List<SqlParser.ParseNode> list4 = CollectionsKt.emptyList();
        object = (Token)object;
        boolean $i$f$getTail = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                List list5 = Collections.emptyList();
                list2 = list5;
                Intrinsics.checkExpressionValueIsNotNull(list5, "emptyList()");
                break;
            }
            default: {
                list2 = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        List<Token> list6 = list = list2;
        List<SqlParser.ParseNode> list7 = list4;
        Object object2 = object;
        SqlParser.ParseType parseType3 = parseType2;
        return new SqlParser.ParseNode(parseType3, (Token)object2, list7, list6);
    }

    public static /* synthetic */ SqlParser.ParseNode atomFromHead$default(List list, SqlParser.ParseType parseType, int n, Object object) {
        if ((n & 1) != 0) {
            parseType = SqlParser.ParseType.ATOM;
        }
        return TokenListExtensionsKt.atomFromHead(list, parseType);
    }

    @NotNull
    public static final Void err(@NotNull List<Token> $this$err, @NotNull String message, @NotNull ErrorCode errorCode, @NotNull PropertyValueMap errorContext) {
        Intrinsics.checkParameterIsNotNull($this$err, "$this$err");
        Intrinsics.checkParameterIsNotNull(message, "message");
        Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
        Intrinsics.checkParameterIsNotNull(errorContext, "errorContext");
        List<Token> $this$head$iv = $this$err;
        boolean $i$f$getHead = false;
        Void void_ = TokenListExtensionsKt.err(CollectionsKt.firstOrNull($this$head$iv), message, errorCode, errorContext);
        throw null;
    }

    public static /* synthetic */ Void err$default(List list, String string, ErrorCode errorCode, PropertyValueMap propertyValueMap, int n, Object object) {
        if ((n & 4) != 0) {
            propertyValueMap = new PropertyValueMap(null, 1, null);
        }
        return TokenListExtensionsKt.err(list, string, errorCode, propertyValueMap);
    }

    @NotNull
    public static final List<Token> tailExpectedKeyword(@NotNull List<Token> $this$tailExpectedKeyword, @NotNull String keyword) {
        String string;
        Intrinsics.checkParameterIsNotNull($this$tailExpectedKeyword, "$this$tailExpectedKeyword");
        Intrinsics.checkParameterIsNotNull(keyword, "keyword");
        List<Token> $this$head$iv = $this$tailExpectedKeyword;
        boolean $i$f$getHead = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        String string2 = string = token != null ? token.getKeywordText() : null;
        if (Intrinsics.areEqual(string, keyword)) {
            List<Token> list;
            List<Token> $this$tail$iv = $this$tailExpectedKeyword;
            boolean $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    List list2 = Collections.emptyList();
                    list = list2;
                    Intrinsics.checkExpressionValueIsNotNull(list2, "emptyList()");
                    break;
                }
                default: {
                    list = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            return list;
        }
        PropertyValueMap pvmap = new PropertyValueMap(null, 1, null);
        String string3 = keyword;
        Object object = Property.KEYWORD;
        Object object2 = pvmap;
        boolean bl = false;
        String string4 = string3.toUpperCase();
        Intrinsics.checkExpressionValueIsNotNull(string4, "(this as java.lang.String).toUpperCase()");
        String string5 = string4;
        ((PropertyValueMap)object2).set((Property)((Object)object), string5);
        string3 = keyword;
        object = new StringBuilder().append("Expected ");
        object2 = $this$tailExpectedKeyword;
        bl = false;
        String string6 = string3.toUpperCase();
        Intrinsics.checkExpressionValueIsNotNull(string6, "(this as java.lang.String).toUpperCase()");
        string5 = string6;
        Void void_ = TokenListExtensionsKt.err((List<Token>)object2, ((StringBuilder)object).append(string5).append(" keyword").toString(), ErrorCode.PARSE_EXPECTED_KEYWORD, pvmap);
        throw null;
    }

    @NotNull
    public static final List<Token> tailExpectedToken(@NotNull List<Token> $this$tailExpectedToken, @NotNull TokenType tokenType) {
        List<Token> list;
        TokenType tokenType2;
        Intrinsics.checkParameterIsNotNull($this$tailExpectedToken, "$this$tailExpectedToken");
        Intrinsics.checkParameterIsNotNull((Object)tokenType, "tokenType");
        List<Token> $this$head$iv = $this$tailExpectedToken;
        boolean $i$f$getHead = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        TokenType tokenType3 = tokenType2 = token != null ? token.getType() : null;
        if (tokenType2 == tokenType) {
            List<Token> $this$tail$iv = $this$tailExpectedToken;
            boolean $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    List list2 = Collections.emptyList();
                    list = list2;
                    Intrinsics.checkExpressionValueIsNotNull(list2, "emptyList()");
                    break;
                }
                default: {
                    list = $this$tail$iv.subList(1, $this$tail$iv.size());
                    break;
                }
            }
        } else {
            $this$head$iv = $this$tailExpectedToken;
            $i$f$getHead = false;
            Void void_ = TokenListExtensionsKt.errExpectedTokenType(CollectionsKt.firstOrNull($this$head$iv), tokenType);
            throw null;
        }
        return list;
    }
}

