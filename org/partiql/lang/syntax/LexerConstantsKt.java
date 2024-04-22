/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.syntax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.syntax.DatePart;
import org.partiql.lang.syntax.OperatorPrecedenceGroups;
import org.partiql.lang.syntax.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u00006\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010$\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0010\u00102\u001a\u00020\u00022\u0006\u00103\u001a\u00020\u0002H\u0002\"\u0016\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u0005\u001a\u00020\u00028\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u000e\u0010\r\u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u000f\u001a\u00020\u00028\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u0011\u001a\u00020\u00028\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u0012\u001a\u00020\u00028\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u001c\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00158\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u00178\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u0018\u001a\u00020\u00198\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u001a\u001a\u00020\u00198\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\".\u0010\u001b\u001a \u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u0017\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u001d0\u001c0\u00158\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u001f\u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010 \u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010!\u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u001c\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00158\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010#\u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u001c\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00190\u00158\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010%\u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010'\u001a\u00020\u0002X\u0080T\u00a2\u0006\u0002\n\u0000\"\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u001c\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00158\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u001c\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002000\u00158\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u00101\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2={"ALL_OPERATORS", "", "", "ALL_SINGLE_LEXEME_OPERATORS", "ALL_WHITESPACE_CHARS", "ALPHA_CHARS", "BACKTICK_CHARS", "BASE_DML_KEYWORDS", "BINARY_OPERATORS", "BOOLEAN_KEYWORDS", "DATE_PART_KEYWORDS", "getDATE_PART_KEYWORDS", "()Ljava/util/Set;", "DIGIT_CHARS", "DOUBLE_QUOTE_CHARS", "E_NOTATION_CHARS", "FUNCTION_NAME_KEYWORDS", "IDENT_CONTINUE_CHARS", "IDENT_START_CHARS", "KEYWORDS", "KEYWORD_ALIASES", "", "MULTI_LEXEME_BINARY_OPERATORS", "", "MULTI_LEXEME_MAX_LENGTH", "", "MULTI_LEXEME_MIN_LENGTH", "MULTI_LEXEME_TOKEN_MAP", "Lkotlin/Pair;", "Lorg/partiql/lang/syntax/TokenType;", "NL_WHITESPACE_CHARS", "NON_NL_WHITESPACE_CHARS", "NON_OVERLOADED_OPERATOR_CHARS", "NON_ZERO_DIGIT_CHARS", "OPERATOR_ALIASES", "OPERATOR_CHARS", "OPERATOR_PRECEDENCE", "SIGN_CHARS", "SINGLE_LEXEME_BINARY_OPERATORS", "SINGLE_QUOTE_CHARS", "SPECIAL_INFIX_OPERATORS", "SPECIAL_OPERATORS", "SQL92_KEYWORDS", "SQLPP_KEYWORDS", "STANDARD_AGGREGATE_FUNCTIONS", "TRIM_SPECIFICATION_KEYWORDS", "TYPE_ALIASES", "TYPE_NAME_ARITY_MAP", "Lkotlin/ranges/IntRange;", "UNARY_OPERATORS", "allCase", "chars", "lang"})
public final class LexerConstantsKt {
    @JvmField
    @NotNull
    public static final Set<String> TRIM_SPECIFICATION_KEYWORDS;
    @NotNull
    private static final Set<String> DATE_PART_KEYWORDS;
    @JvmField
    @NotNull
    public static final Set<String> SQL92_KEYWORDS;
    @JvmField
    @NotNull
    public static final Set<String> SQLPP_KEYWORDS;
    @JvmField
    @NotNull
    public static final Set<String> KEYWORDS;
    @JvmField
    @NotNull
    public static final Map<String, String> TYPE_ALIASES;
    @JvmField
    @NotNull
    public static final Map<String, String> KEYWORD_ALIASES;
    @JvmField
    @NotNull
    public static final Map<String, IntRange> TYPE_NAME_ARITY_MAP;
    @JvmField
    @NotNull
    public static final Set<String> FUNCTION_NAME_KEYWORDS;
    @JvmField
    @NotNull
    public static final Set<String> STANDARD_AGGREGATE_FUNCTIONS;
    @JvmField
    @NotNull
    public static final Set<String> BASE_DML_KEYWORDS;
    @JvmField
    @NotNull
    public static final Set<String> BOOLEAN_KEYWORDS;
    @JvmField
    @NotNull
    public static final Map<String, String> OPERATOR_ALIASES;
    @JvmField
    @NotNull
    public static final Set<String> SPECIAL_INFIX_OPERATORS;
    @JvmField
    @NotNull
    public static final Set<String> SINGLE_LEXEME_BINARY_OPERATORS;
    @JvmField
    @NotNull
    public static final Map<List<String>, Pair<String, TokenType>> MULTI_LEXEME_TOKEN_MAP;
    @JvmField
    public static final int MULTI_LEXEME_MIN_LENGTH;
    @JvmField
    public static final int MULTI_LEXEME_MAX_LENGTH;
    @JvmField
    @NotNull
    public static final List<String> MULTI_LEXEME_BINARY_OPERATORS;
    @JvmField
    @NotNull
    public static final Set<String> BINARY_OPERATORS;
    @JvmField
    @NotNull
    public static final Set<String> UNARY_OPERATORS;
    @JvmField
    @NotNull
    public static final Set<String> SPECIAL_OPERATORS;
    @JvmField
    @NotNull
    public static final Set<String> ALL_SINGLE_LEXEME_OPERATORS;
    @JvmField
    @NotNull
    public static final Set<String> ALL_OPERATORS;
    @JvmField
    @NotNull
    public static final Map<String, Integer> OPERATOR_PRECEDENCE;
    @NotNull
    public static final String SIGN_CHARS = "+-";
    @NotNull
    public static final String NON_ZERO_DIGIT_CHARS = "123456789";
    @NotNull
    public static final String DIGIT_CHARS = "0123456789";
    @JvmField
    @NotNull
    public static final String E_NOTATION_CHARS;
    @NotNull
    public static final String NON_OVERLOADED_OPERATOR_CHARS = "^%=@+";
    @NotNull
    public static final String OPERATOR_CHARS = "^%=@+-*/<>|!";
    @JvmField
    @NotNull
    public static final String ALPHA_CHARS;
    @JvmField
    @NotNull
    public static final String IDENT_START_CHARS;
    @JvmField
    @NotNull
    public static final String IDENT_CONTINUE_CHARS;
    @NotNull
    public static final String NL_WHITESPACE_CHARS = "\r\n";
    @NotNull
    public static final String NON_NL_WHITESPACE_CHARS = "\t\u000b\f ";
    @NotNull
    public static final String ALL_WHITESPACE_CHARS = "\r\n\t\u000b\f ";
    @NotNull
    public static final String DOUBLE_QUOTE_CHARS = "\"";
    @NotNull
    public static final String SINGLE_QUOTE_CHARS = "'";
    @NotNull
    public static final String BACKTICK_CHARS = "`";

    @NotNull
    public static final Set<String> getDATE_PART_KEYWORDS() {
        return DATE_PART_KEYWORDS;
    }

    private static final String allCase(String chars) {
        String string = chars;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        String string2 = string;
        if (string2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String string3 = string2.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
        String string4 = string3;
        string = chars;
        stringBuilder = stringBuilder.append(string4);
        bl = false;
        String string5 = string;
        if (string5 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String string6 = string5.toUpperCase();
        Intrinsics.checkExpressionValueIsNotNull(string6, "(this as java.lang.String).toUpperCase()");
        string4 = string6;
        return stringBuilder.append(string4).toString();
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var3_5;
        Iterable $this$filterTo$iv$iv;
        Object object;
        Collection collection;
        Iterable $this$mapTo$iv$iv;
        TRIM_SPECIFICATION_KEYWORDS = SetsKt.setOf("both", "leading", "trailing");
        DatePart[] $this$map$iv22 = DatePart.values();
        boolean $i$f$map = false;
        DatePart[] datePartArray = $this$map$iv22;
        Collection destination$iv$iv = new ArrayList($this$map$iv22.length);
        boolean $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv$iv;
        int n = ((void)iterator2).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv$iv;
            void var9_23 = item$iv$iv = iterator2[i];
            collection = destination$iv$iv;
            boolean bl = false;
            String string = it.toString();
            boolean bl2 = false;
            String string2 = string;
            if (string2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            Intrinsics.checkExpressionValueIsNotNull(string2.toLowerCase(), "(this as java.lang.String).toLowerCase()");
            collection.add(object);
        }
        DATE_PART_KEYWORDS = CollectionsKt.toSet((List)destination$iv$iv);
        SQL92_KEYWORDS = SetsKt.setOf("absolute", "action", "add", "all", "allocate", "alter", "and", "any", "are", "as", "asc", "assertion", "at", "authorization", "avg", "begin", "between", "bit", "bit_length", "by", "cascade", "cascaded", "case", "cast", "catalog", "char", "character", "character_length", "char_length", "check", "close", "coalesce", "collate", "collation", "column", "commit", "connect", "connection", "constraint", "constraints", "continue", "convert", "corresponding", "count", "create", "cross", "current", "current_date", "current_time", "current_timestamp", "current_user", "cursor", "date", "deallocate", "dec", "decimal", "declare", "default", "deferrable", "deferred", "delete", "desc", "describe", "descriptor", "diagnostics", "disconnect", "distinct", "domain", "double", "drop", "else", "end", "end-exec", "escape", "except", "exception", "exec", "execute", "exists", "external", "extract", "date_add", "date_diff", "false", "fetch", "first", "float", "for", "foreign", "found", "from", "full", "get", "global", "go", "goto", "grant", "group", "having", "identity", "immediate", "in", "indicator", "initially", "inner", "input", "insensitive", "insert", "int", "integer", "intersect", "interval", "into", "is", "isolation", "join", "key", "language", "last", "left", "level", "like", "local", "lower", "match", "max", "min", "module", "names", "national", "natural", "nchar", "next", "no", "not", "null", "nullif", "numeric", "octet_length", "of", "on", "only", "open", "option", "or", "order", "outer", "output", "overlaps", "pad", "partial", "position", "precision", "prepare", "preserve", "primary", "prior", "privileges", "procedure", "public", "read", "real", "references", "relative", "restrict", "revoke", "right", "rollback", "rows", "schema", "scroll", "section", "select", "session", "session_user", "set", "size", "smallint", "some", "space", "sql", "sqlcode", "sqlerror", "sqlstate", "substring", "sum", "system_user", "table", "temporary", "then", "time", "timestamp", "to", "transaction", "translate", "translation", "trim", "true", "union", "unique", "unknown", "update", "upper", "usage", "user", "using", "value", "values", "varchar", "varying", "view", "when", "whenever", "where", "with", "work", "write", "zone");
        SQLPP_KEYWORDS = SetsKt.setOf("missing", "pivot", "unpivot", "limit", "tuple", "remove", "index", "conflict", "do", "nothing", "returning", "modified", "all", "new", "old", "let", "bool", "boolean", "string", "symbol", "clob", "blob", "struct", "list", "sexp", "bag");
        KEYWORDS = CollectionsKt.union((Iterable)SQL92_KEYWORDS, (Iterable)SQLPP_KEYWORDS);
        TYPE_ALIASES = MapsKt.mapOf(TuplesKt.to("varchar", "character_varying"), TuplesKt.to("char", "character"), TuplesKt.to("dec", "decimal"), TuplesKt.to("int", "integer"), TuplesKt.to("bool", "boolean"));
        KEYWORD_ALIASES = TYPE_ALIASES;
        Pair[] pairArray = new Pair[24];
        int $this$map$iv22 = 0;
        pairArray[0] = TuplesKt.to("missing", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[1] = TuplesKt.to("null", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[2] = TuplesKt.to("boolean", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[3] = TuplesKt.to("smallint", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[4] = TuplesKt.to("integer", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[5] = TuplesKt.to("float", new IntRange($this$map$iv22, 1));
        $this$map$iv22 = 0;
        pairArray[6] = TuplesKt.to("real", new IntRange($this$map$iv22, 1));
        $this$map$iv22 = 0;
        pairArray[7] = TuplesKt.to("double_precision", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[8] = TuplesKt.to("decimal", new IntRange($this$map$iv22, 2));
        $this$map$iv22 = 0;
        pairArray[9] = TuplesKt.to("numeric", new IntRange($this$map$iv22, 2));
        $this$map$iv22 = 0;
        pairArray[10] = TuplesKt.to("timestamp", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[11] = TuplesKt.to("date", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[12] = TuplesKt.to("time", new IntRange($this$map$iv22, 1));
        $this$map$iv22 = 0;
        pairArray[13] = TuplesKt.to("character", new IntRange($this$map$iv22, 1));
        $this$map$iv22 = 0;
        pairArray[14] = TuplesKt.to("character_varying", new IntRange($this$map$iv22, 1));
        $this$map$iv22 = 0;
        pairArray[15] = TuplesKt.to("string", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[16] = TuplesKt.to("symbol", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[17] = TuplesKt.to("clob", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[18] = TuplesKt.to("blob", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[19] = TuplesKt.to("struct", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[20] = TuplesKt.to("tuple", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[21] = TuplesKt.to("list", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[22] = TuplesKt.to("sexp", new IntRange($this$map$iv22, 0));
        $this$map$iv22 = 0;
        pairArray[23] = TuplesKt.to("bag", new IntRange($this$map$iv22, 0));
        TYPE_NAME_ARITY_MAP = MapsKt.mapOf(pairArray);
        FUNCTION_NAME_KEYWORDS = SetsKt.setOf("exists", "count", "avg", "max", "min", "sum", "substring", "char_length", "character_length", "octet_length", "bit_length", "upper", "lower", "size", "nullif", "coalesce", "sexp", "list", "bag");
        STANDARD_AGGREGATE_FUNCTIONS = SetsKt.setOf("count", "avg", "max", "min", "sum");
        BASE_DML_KEYWORDS = SetsKt.setOf("insert_into", "set", "remove");
        BOOLEAN_KEYWORDS = SetsKt.setOf("true", "false");
        OPERATOR_ALIASES = MapsKt.mapOf(TuplesKt.to("!=", "<>"));
        SPECIAL_INFIX_OPERATORS = SetsKt.setOf("between", "not_between", "like", "not_like");
        SINGLE_LEXEME_BINARY_OPERATORS = SetsKt.setOf("+", "-", "/", "%", "*", "<", "<=", ">", ">=", "=", "<>", "||", "and", "or", "is", "in", "union", "except", "intersect");
        MULTI_LEXEME_TOKEN_MAP = MapsKt.mapOf(TuplesKt.to(CollectionsKt.listOf("not", "in"), TuplesKt.to("not_in", TokenType.OPERATOR)), TuplesKt.to(CollectionsKt.listOf("is", "not"), TuplesKt.to("is_not", TokenType.OPERATOR)), TuplesKt.to(CollectionsKt.listOf("not", "between"), TuplesKt.to("not_between", TokenType.OPERATOR)), TuplesKt.to(CollectionsKt.listOf("intersect", "all"), TuplesKt.to("intersect_all", TokenType.OPERATOR)), TuplesKt.to(CollectionsKt.listOf("except", "all"), TuplesKt.to("except_all", TokenType.OPERATOR)), TuplesKt.to(CollectionsKt.listOf("union", "all"), TuplesKt.to("union_all", TokenType.OPERATOR)), TuplesKt.to(CollectionsKt.listOf("character", "varying"), TuplesKt.to("character_varying", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("double", "precision"), TuplesKt.to("double_precision", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("not", "like"), TuplesKt.to("not_like", TokenType.OPERATOR)), TuplesKt.to(CollectionsKt.listOf("cross", "join"), TuplesKt.to("cross_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("inner", "join"), TuplesKt.to("inner_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("inner", "cross", "join"), TuplesKt.to("cross_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("left", "join"), TuplesKt.to("left_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("left", "outer", "join"), TuplesKt.to("left_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("left", "cross", "join"), TuplesKt.to("left_cross_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("left", "outer", "cross", "join"), TuplesKt.to("left_cross_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("right", "join"), TuplesKt.to("right_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("right", "outer", "join"), TuplesKt.to("right_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("right", "cross", "join"), TuplesKt.to("right_cross_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("right", "outer", "cross", "join"), TuplesKt.to("right_cross_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("full", "join"), TuplesKt.to("outer_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("outer", "join"), TuplesKt.to("outer_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("full", "outer", "join"), TuplesKt.to("outer_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("full", "cross", "join"), TuplesKt.to("outer_cross_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("outer", "cross", "join"), TuplesKt.to("outer_cross_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("full", "outer", "cross", "join"), TuplesKt.to("outer_cross_join", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("insert", "into"), TuplesKt.to("insert_into", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("on", "conflict"), TuplesKt.to("on_conflict", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("do", "nothing"), TuplesKt.to("do_nothing", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("modified", "old"), TuplesKt.to("modified_old", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("modified", "new"), TuplesKt.to("modified_new", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("all", "old"), TuplesKt.to("all_old", TokenType.KEYWORD)), TuplesKt.to(CollectionsKt.listOf("all", "new"), TuplesKt.to("all_new", TokenType.KEYWORD)));
        Iterable $this$map$iv = MULTI_LEXEME_TOKEN_MAP.keySet();
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            List list = (List)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            object = it.size();
            collection.add(object);
        }
        Comparable comparable = CollectionsKt.min((List)destination$iv$iv);
        if (comparable == null) {
            Intrinsics.throwNpe();
        }
        MULTI_LEXEME_MIN_LENGTH = ((Number)((Object)comparable)).intValue();
        $this$map$iv = MULTI_LEXEME_TOKEN_MAP.keySet();
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            List it = (List)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            object = it.size();
            collection.add(object);
        }
        Comparable comparable2 = CollectionsKt.max((List)destination$iv$iv);
        if (comparable2 == null) {
            Intrinsics.throwNpe();
        }
        MULTI_LEXEME_MAX_LENGTH = ((Number)((Object)comparable2)).intValue();
        Iterable $this$filter$iv = MULTI_LEXEME_TOKEN_MAP.values();
        boolean $i$f$filter = false;
        $this$mapTo$iv$iv = $this$filter$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Pair it = (Pair)element$iv$iv;
            boolean bl = false;
            if (!((TokenType)((Object)it.getSecond()) == TokenType.OPERATOR && !SPECIAL_INFIX_OPERATORS.contains(it.getFirst()))) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$map$iv = (List)destination$iv$iv;
        $i$f$map = false;
        $this$filterTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            Pair it = (Pair)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            object = (String)it.getFirst();
            collection.add(object);
        }
        MULTI_LEXEME_BINARY_OPERATORS = (List)var3_5;
        BINARY_OPERATORS = SetsKt.plus(SINGLE_LEXEME_BINARY_OPERATORS, (Iterable)MULTI_LEXEME_BINARY_OPERATORS);
        UNARY_OPERATORS = SetsKt.setOf("+", "-", "not");
        SPECIAL_OPERATORS = SetsKt.plus(SPECIAL_INFIX_OPERATORS, (Iterable)SetsKt.setOf("@"));
        ALL_SINGLE_LEXEME_OPERATORS = SetsKt.plus(SetsKt.plus(SINGLE_LEXEME_BINARY_OPERATORS, (Iterable)UNARY_OPERATORS), (Iterable)SPECIAL_OPERATORS);
        ALL_OPERATORS = SetsKt.plus(SetsKt.plus(BINARY_OPERATORS, (Iterable)UNARY_OPERATORS), (Iterable)SPECIAL_OPERATORS);
        OPERATOR_PRECEDENCE = MapsKt.mapOf(TuplesKt.to("intersect", OperatorPrecedenceGroups.SET.getPrecedence()), TuplesKt.to("intersect_all", OperatorPrecedenceGroups.SET.getPrecedence()), TuplesKt.to("except", OperatorPrecedenceGroups.SET.getPrecedence()), TuplesKt.to("except_all", OperatorPrecedenceGroups.SET.getPrecedence()), TuplesKt.to("union", OperatorPrecedenceGroups.SET.getPrecedence()), TuplesKt.to("union_all", OperatorPrecedenceGroups.SET.getPrecedence()), TuplesKt.to("or", OperatorPrecedenceGroups.LOGICAL_OR.getPrecedence()), TuplesKt.to("and", OperatorPrecedenceGroups.LOGICAL_AND.getPrecedence()), TuplesKt.to("not", OperatorPrecedenceGroups.LOGICAL_NOT.getPrecedence()), TuplesKt.to("=", OperatorPrecedenceGroups.EQUITY.getPrecedence()), TuplesKt.to("<>", OperatorPrecedenceGroups.EQUITY.getPrecedence()), TuplesKt.to("is", OperatorPrecedenceGroups.EQUITY.getPrecedence()), TuplesKt.to("is_not", OperatorPrecedenceGroups.EQUITY.getPrecedence()), TuplesKt.to("in", OperatorPrecedenceGroups.EQUITY.getPrecedence()), TuplesKt.to("not_in", OperatorPrecedenceGroups.EQUITY.getPrecedence()), TuplesKt.to("<", OperatorPrecedenceGroups.COMPARISON.getPrecedence()), TuplesKt.to("<=", OperatorPrecedenceGroups.COMPARISON.getPrecedence()), TuplesKt.to(">", OperatorPrecedenceGroups.COMPARISON.getPrecedence()), TuplesKt.to(">=", OperatorPrecedenceGroups.COMPARISON.getPrecedence()), TuplesKt.to("between", OperatorPrecedenceGroups.COMPARISON.getPrecedence()), TuplesKt.to("not_between", OperatorPrecedenceGroups.COMPARISON.getPrecedence()), TuplesKt.to("like", OperatorPrecedenceGroups.COMPARISON.getPrecedence()), TuplesKt.to("not_like", OperatorPrecedenceGroups.COMPARISON.getPrecedence()), TuplesKt.to("+", OperatorPrecedenceGroups.ADDITION.getPrecedence()), TuplesKt.to("-", OperatorPrecedenceGroups.ADDITION.getPrecedence()), TuplesKt.to("||", OperatorPrecedenceGroups.ADDITION.getPrecedence()), TuplesKt.to("*", OperatorPrecedenceGroups.MULTIPLY.getPrecedence()), TuplesKt.to("/", OperatorPrecedenceGroups.MULTIPLY.getPrecedence()), TuplesKt.to("%", OperatorPrecedenceGroups.MULTIPLY.getPrecedence()));
        E_NOTATION_CHARS = LexerConstantsKt.allCase("E");
        ALPHA_CHARS = LexerConstantsKt.allCase("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        IDENT_START_CHARS = "_$" + ALPHA_CHARS;
        IDENT_CONTINUE_CHARS = IDENT_START_CHARS + DIGIT_CHARS;
    }
}

