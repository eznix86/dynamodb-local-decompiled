/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.TagDefinition;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b[\b\u0082\u0001\u0018\u0000 ]2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001]B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=j\u0002\b>j\u0002\b?j\u0002\b@j\u0002\bAj\u0002\bBj\u0002\bCj\u0002\bDj\u0002\bEj\u0002\bFj\u0002\bGj\u0002\bHj\u0002\bIj\u0002\bJj\u0002\bKj\u0002\bLj\u0002\bMj\u0002\bNj\u0002\bOj\u0002\bPj\u0002\bQj\u0002\bRj\u0002\bSj\u0002\bTj\u0002\bUj\u0002\bVj\u0002\bWj\u0002\bXj\u0002\bYj\u0002\bZj\u0002\b[j\u0002\b\\\u00a8\u0006^"}, d2={"Lorg/partiql/lang/ast/NodeTag;", "", "definition", "Lorg/partiql/lang/ast/TagDefinition;", "(Ljava/lang/String;ILorg/partiql/lang/ast/TagDefinition;)V", "getDefinition", "()Lorg/partiql/lang/ast/TagDefinition;", "META", "LIT", "MISSING", "ID", "SELECT", "PIVOT", "CREATE", "DROP_TABLE", "DROP_INDEX", "DATA_MANIPULATION", "PATH", "CALL_AGG", "CALL_AGG_WILDCARD", "STRUCT", "LIST", "BAG", "SEXP", "UNPIVOT", "SIMPLE_CASE", "SEARCHED_CASE", "WHEN", "ELSE", "PARAMETER", "NARY_NOT", "NARY_ADD", "NARY_SUB", "NARY_MUL", "NARY_DIV", "NARY_MOD", "NARY_GT", "NARY_GTE", "NARY_LT", "NARY_LTE", "NARY_EQ", "NARY_IN", "NARY_NOT_IN", "NARY_NE", "NARY_AND", "NARY_OR", "NARY_LIKE", "NARY_NOT_LIKE", "NARY_BETWEEN", "NARY_NOT_BETWEEN", "NARY_CALL", "NARY_STRING_CONCAT", "NARY_UNION", "NARY_UNION_ALL", "NARY_EXCEPT", "NARY_EXCEPT_ALL", "NARY_INTERSECT", "NARY_INTERSECT_ALL", "TYPED_IS", "TYPED_IS_NOT", "TYPED_CAST", "TABLE", "INDEX", "KEYS", "INSERT", "INSERT_VALUE", "SET", "REMOVE", "DELETE", "ASSIGNMENT", "PROJECT", "PROJECT_ALL", "PROJECT_DISTINCT", "VALUE", "FROM", "WHERE", "HAVING", "LIMIT", "GROUP", "BY", "NAME", "GROUP_PARTIAL", "MEMBER", "AS", "AT", "SCOPE_QUALIFIER", "INNER_JOIN", "LEFT_JOIN", "OUTER_JOIN", "RIGHT_JOIN", "TYPE", "CASE_INSENSITIVE", "CASE_SENSITIVE", "Companion", "lang"})
final class NodeTag
extends Enum<NodeTag> {
    public static final /* enum */ NodeTag META;
    public static final /* enum */ NodeTag LIT;
    public static final /* enum */ NodeTag MISSING;
    public static final /* enum */ NodeTag ID;
    public static final /* enum */ NodeTag SELECT;
    public static final /* enum */ NodeTag PIVOT;
    public static final /* enum */ NodeTag CREATE;
    public static final /* enum */ NodeTag DROP_TABLE;
    public static final /* enum */ NodeTag DROP_INDEX;
    public static final /* enum */ NodeTag DATA_MANIPULATION;
    public static final /* enum */ NodeTag PATH;
    public static final /* enum */ NodeTag CALL_AGG;
    public static final /* enum */ NodeTag CALL_AGG_WILDCARD;
    public static final /* enum */ NodeTag STRUCT;
    public static final /* enum */ NodeTag LIST;
    public static final /* enum */ NodeTag BAG;
    public static final /* enum */ NodeTag SEXP;
    public static final /* enum */ NodeTag UNPIVOT;
    public static final /* enum */ NodeTag SIMPLE_CASE;
    public static final /* enum */ NodeTag SEARCHED_CASE;
    public static final /* enum */ NodeTag WHEN;
    public static final /* enum */ NodeTag ELSE;
    public static final /* enum */ NodeTag PARAMETER;
    public static final /* enum */ NodeTag NARY_NOT;
    public static final /* enum */ NodeTag NARY_ADD;
    public static final /* enum */ NodeTag NARY_SUB;
    public static final /* enum */ NodeTag NARY_MUL;
    public static final /* enum */ NodeTag NARY_DIV;
    public static final /* enum */ NodeTag NARY_MOD;
    public static final /* enum */ NodeTag NARY_GT;
    public static final /* enum */ NodeTag NARY_GTE;
    public static final /* enum */ NodeTag NARY_LT;
    public static final /* enum */ NodeTag NARY_LTE;
    public static final /* enum */ NodeTag NARY_EQ;
    public static final /* enum */ NodeTag NARY_IN;
    public static final /* enum */ NodeTag NARY_NOT_IN;
    public static final /* enum */ NodeTag NARY_NE;
    public static final /* enum */ NodeTag NARY_AND;
    public static final /* enum */ NodeTag NARY_OR;
    public static final /* enum */ NodeTag NARY_LIKE;
    public static final /* enum */ NodeTag NARY_NOT_LIKE;
    public static final /* enum */ NodeTag NARY_BETWEEN;
    public static final /* enum */ NodeTag NARY_NOT_BETWEEN;
    public static final /* enum */ NodeTag NARY_CALL;
    public static final /* enum */ NodeTag NARY_STRING_CONCAT;
    public static final /* enum */ NodeTag NARY_UNION;
    public static final /* enum */ NodeTag NARY_UNION_ALL;
    public static final /* enum */ NodeTag NARY_EXCEPT;
    public static final /* enum */ NodeTag NARY_EXCEPT_ALL;
    public static final /* enum */ NodeTag NARY_INTERSECT;
    public static final /* enum */ NodeTag NARY_INTERSECT_ALL;
    public static final /* enum */ NodeTag TYPED_IS;
    public static final /* enum */ NodeTag TYPED_IS_NOT;
    public static final /* enum */ NodeTag TYPED_CAST;
    public static final /* enum */ NodeTag TABLE;
    public static final /* enum */ NodeTag INDEX;
    public static final /* enum */ NodeTag KEYS;
    public static final /* enum */ NodeTag INSERT;
    public static final /* enum */ NodeTag INSERT_VALUE;
    public static final /* enum */ NodeTag SET;
    public static final /* enum */ NodeTag REMOVE;
    public static final /* enum */ NodeTag DELETE;
    public static final /* enum */ NodeTag ASSIGNMENT;
    public static final /* enum */ NodeTag PROJECT;
    public static final /* enum */ NodeTag PROJECT_ALL;
    public static final /* enum */ NodeTag PROJECT_DISTINCT;
    public static final /* enum */ NodeTag VALUE;
    public static final /* enum */ NodeTag FROM;
    public static final /* enum */ NodeTag WHERE;
    public static final /* enum */ NodeTag HAVING;
    public static final /* enum */ NodeTag LIMIT;
    public static final /* enum */ NodeTag GROUP;
    public static final /* enum */ NodeTag BY;
    public static final /* enum */ NodeTag NAME;
    public static final /* enum */ NodeTag GROUP_PARTIAL;
    public static final /* enum */ NodeTag MEMBER;
    public static final /* enum */ NodeTag AS;
    public static final /* enum */ NodeTag AT;
    public static final /* enum */ NodeTag SCOPE_QUALIFIER;
    public static final /* enum */ NodeTag INNER_JOIN;
    public static final /* enum */ NodeTag LEFT_JOIN;
    public static final /* enum */ NodeTag OUTER_JOIN;
    public static final /* enum */ NodeTag RIGHT_JOIN;
    public static final /* enum */ NodeTag TYPE;
    public static final /* enum */ NodeTag CASE_INSENSITIVE;
    public static final /* enum */ NodeTag CASE_SENSITIVE;
    private static final /* synthetic */ NodeTag[] $VALUES;
    @NotNull
    private final TagDefinition definition;
    private static final Map<String, NodeTag> tagLookup;
    public static final Companion Companion;

    /*
     * WARNING - void declaration
     */
    static {
        void var3_4;
        Collection<Pair<String, void>> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        META = new NodeTag(new TagDefinition("meta", 2, 2));
        LIT = new NodeTag(new TagDefinition("lit", 1, 1));
        MISSING = new NodeTag(new TagDefinition("missing", 0, 0));
        ID = new NodeTag(new TagDefinition("id", 2, 3));
        SELECT = new NodeTag(new TagDefinition("select", 2, 5));
        PIVOT = new NodeTag(new TagDefinition("pivot", 2, 5));
        CREATE = new NodeTag(new TagDefinition("create", AstVersion.V0, 2, 2));
        DROP_TABLE = new NodeTag(new TagDefinition("drop_table", 1, 1));
        DROP_INDEX = new NodeTag(new TagDefinition("drop_index", 2, 2));
        DATA_MANIPULATION = new NodeTag(new TagDefinition("dml", 1, 3));
        PATH = new NodeTag(new TagDefinition("path", 2, Integer.MAX_VALUE));
        CALL_AGG = new NodeTag(new TagDefinition("call_agg", 3, 3));
        CALL_AGG_WILDCARD = new NodeTag(new TagDefinition("call_agg_wildcard", 1, 1));
        STRUCT = new NodeTag(new TagDefinition("struct", 0, Integer.MAX_VALUE));
        LIST = new NodeTag(new TagDefinition("list", 0, Integer.MAX_VALUE));
        BAG = new NodeTag(new TagDefinition("bag", 0, Integer.MAX_VALUE));
        SEXP = new NodeTag(new TagDefinition("sexp", 0, Integer.MAX_VALUE));
        UNPIVOT = new NodeTag(new TagDefinition("unpivot", 1, 4));
        SIMPLE_CASE = new NodeTag(new TagDefinition("simple_case", 0, Integer.MAX_VALUE));
        SEARCHED_CASE = new NodeTag(new TagDefinition("searched_case", 1, Integer.MAX_VALUE));
        WHEN = new NodeTag(new TagDefinition("when", AstVersion.V0, 1, 2));
        ELSE = new NodeTag(new TagDefinition("else", AstVersion.V0, 1, 0, 8, null));
        PARAMETER = new NodeTag(new TagDefinition("parameter", 1, 0, 4, null));
        NARY_NOT = new NodeTag(new TagDefinition("not", 1, Integer.MAX_VALUE));
        NARY_ADD = new NodeTag(new TagDefinition("+", AstVersion.V0, 1, Integer.MAX_VALUE));
        NARY_SUB = new NodeTag(new TagDefinition("-", AstVersion.V0, 1, Integer.MAX_VALUE));
        NARY_MUL = new NodeTag(new TagDefinition("*", 0, Integer.MAX_VALUE));
        NARY_DIV = new NodeTag(new TagDefinition("/", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_MOD = new NodeTag(new TagDefinition("%", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_GT = new NodeTag(new TagDefinition(">", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_GTE = new NodeTag(new TagDefinition(">=", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_LT = new NodeTag(new TagDefinition("<", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_LTE = new NodeTag(new TagDefinition("<=", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_EQ = new NodeTag(new TagDefinition("=", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_IN = new NodeTag(new TagDefinition("in", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_NOT_IN = new NodeTag(new TagDefinition("not_in", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_NE = new NodeTag(new TagDefinition("<>", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_AND = new NodeTag(new TagDefinition("and", 2, Integer.MAX_VALUE));
        NARY_OR = new NodeTag(new TagDefinition("or", 2, Integer.MAX_VALUE));
        NARY_LIKE = new NodeTag(new TagDefinition("like", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_NOT_LIKE = new NodeTag(new TagDefinition("not_like", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_BETWEEN = new NodeTag(new TagDefinition("between", 3, Integer.MAX_VALUE));
        NARY_NOT_BETWEEN = new NodeTag(new TagDefinition("not_between", AstVersion.V0, 3, Integer.MAX_VALUE));
        NARY_CALL = new NodeTag(new TagDefinition("call", 1, Integer.MAX_VALUE));
        NARY_STRING_CONCAT = new NodeTag(new TagDefinition("||", AstVersion.V0, 1, Integer.MAX_VALUE));
        NARY_UNION = new NodeTag(new TagDefinition("union", 2, Integer.MAX_VALUE));
        NARY_UNION_ALL = new NodeTag(new TagDefinition("union_all", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_EXCEPT = new NodeTag(new TagDefinition("except", 2, Integer.MAX_VALUE));
        NARY_EXCEPT_ALL = new NodeTag(new TagDefinition("except_all", AstVersion.V0, 2, Integer.MAX_VALUE));
        NARY_INTERSECT = new NodeTag(new TagDefinition("intersect", 2, Integer.MAX_VALUE));
        NARY_INTERSECT_ALL = new NodeTag(new TagDefinition("intersect_all", AstVersion.V0, 2, Integer.MAX_VALUE));
        TYPED_IS = new NodeTag(new TagDefinition("is", AstVersion.V0, 2, 0, 8, null));
        TYPED_IS_NOT = new NodeTag(new TagDefinition("is_not", AstVersion.V0, 2, 0, 8, null));
        TYPED_CAST = new NodeTag(new TagDefinition("cast", 2, 0, 4, null));
        TABLE = new NodeTag(new TagDefinition("table", 0, 1));
        INDEX = new NodeTag(new TagDefinition("index", AstVersion.V0, 2, 2));
        KEYS = new NodeTag(new TagDefinition("keys", 1, Integer.MAX_VALUE));
        INSERT = new NodeTag(new TagDefinition("insert", 2, 2));
        INSERT_VALUE = new NodeTag(new TagDefinition("insert_value", 2, 3));
        SET = new NodeTag(new TagDefinition("set", 1, Integer.MAX_VALUE));
        REMOVE = new NodeTag(new TagDefinition("remove", 1, 1));
        DELETE = new NodeTag(new TagDefinition("delete", 0, 0));
        ASSIGNMENT = new NodeTag(new TagDefinition("assignment", 2, 2));
        PROJECT = new NodeTag(new TagDefinition("project", 1, 0, 4, null));
        PROJECT_ALL = new NodeTag(new TagDefinition("project_all", 0, 1));
        PROJECT_DISTINCT = new NodeTag(new TagDefinition("project_distinct", AstVersion.V0, 1, 0, 8, null));
        VALUE = new NodeTag(new TagDefinition("value", AstVersion.V0, 1, 0, 8, null));
        FROM = new NodeTag(new TagDefinition("from", 1, 0, 4, null));
        WHERE = new NodeTag(new TagDefinition("where", 1, 0, 4, null));
        HAVING = new NodeTag(new TagDefinition("having", 1, 0, 4, null));
        LIMIT = new NodeTag(new TagDefinition("limit", 1, 0, 4, null));
        GROUP = new NodeTag(new TagDefinition("group", 1, 2));
        BY = new NodeTag(new TagDefinition("by", 1, Integer.MAX_VALUE));
        NAME = new NodeTag(new TagDefinition("name", 1, 0, 4, null));
        GROUP_PARTIAL = new NodeTag(new TagDefinition("group_partial", 2, 0, 4, null));
        MEMBER = new NodeTag(new TagDefinition("member", 2, 0, 4, null));
        AS = new NodeTag(new TagDefinition("as", 2, 0, 4, null));
        AT = new NodeTag(new TagDefinition("at", 2, 0, 4, null));
        SCOPE_QUALIFIER = new NodeTag(new TagDefinition("@", AstVersion.V0, 1, 0, 8, null));
        INNER_JOIN = new NodeTag(new TagDefinition("inner_join", 2, 3));
        LEFT_JOIN = new NodeTag(new TagDefinition("left_join", 2, 3));
        OUTER_JOIN = new NodeTag(new TagDefinition("outer_join", 2, 3));
        RIGHT_JOIN = new NodeTag(new TagDefinition("right_join", 2, 3));
        TYPE = new NodeTag(new TagDefinition("type", AstVersion.V0, 1, 3));
        CASE_INSENSITIVE = new NodeTag(new TagDefinition("case_insensitive", 0, 1));
        CASE_SENSITIVE = new NodeTag(new TagDefinition("case_sensitive", 0, 1));
        $VALUES = new NodeTag[]{META, LIT, MISSING, ID, SELECT, PIVOT, CREATE, DROP_TABLE, DROP_INDEX, DATA_MANIPULATION, PATH, CALL_AGG, CALL_AGG_WILDCARD, STRUCT, LIST, BAG, SEXP, UNPIVOT, SIMPLE_CASE, SEARCHED_CASE, WHEN, ELSE, PARAMETER, NARY_NOT, NARY_ADD, NARY_SUB, NARY_MUL, NARY_DIV, NARY_MOD, NARY_GT, NARY_GTE, NARY_LT, NARY_LTE, NARY_EQ, NARY_IN, NARY_NOT_IN, NARY_NE, NARY_AND, NARY_OR, NARY_LIKE, NARY_NOT_LIKE, NARY_BETWEEN, NARY_NOT_BETWEEN, NARY_CALL, NARY_STRING_CONCAT, NARY_UNION, NARY_UNION_ALL, NARY_EXCEPT, NARY_EXCEPT_ALL, NARY_INTERSECT, NARY_INTERSECT_ALL, TYPED_IS, TYPED_IS_NOT, TYPED_CAST, TABLE, INDEX, KEYS, INSERT, INSERT_VALUE, SET, REMOVE, DELETE, ASSIGNMENT, PROJECT, PROJECT_ALL, PROJECT_DISTINCT, VALUE, FROM, WHERE, HAVING, LIMIT, GROUP, BY, NAME, GROUP_PARTIAL, MEMBER, AS, AT, SCOPE_QUALIFIER, INNER_JOIN, LEFT_JOIN, OUTER_JOIN, RIGHT_JOIN, TYPE, CASE_INSENSITIVE, CASE_SENSITIVE};
        Companion = new Companion(null);
        NodeTag[] nodeTagArray = NodeTag.values();
        NodeTag[] nodeTagArray2 = $VALUES;
        boolean $i$f$map = false;
        void var2_3 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        void var5_6 = $this$mapTo$iv$iv;
        int n = ((void)var5_6).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv$iv;
            void var9_10 = item$iv$iv = var5_6[i];
            collection = destination$iv$iv;
            boolean bl = false;
            Pair<String, void> pair = new Pair<String, void>(it.definition.getTagText(), it);
            collection.add(pair);
        }
        collection = (List)var3_4;
        NodeTag[] nodeTagArray3 = nodeTagArray2;
        tagLookup = MapsKt.toMap((Iterable)collection);
    }

    @NotNull
    public final TagDefinition getDefinition() {
        return this.definition;
    }

    private NodeTag(TagDefinition definition) {
        this.definition = definition;
    }

    public static NodeTag[] values() {
        return (NodeTag[])$VALUES.clone();
    }

    public static NodeTag valueOf(String string) {
        return Enum.valueOf(NodeTag.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/ast/NodeTag$Companion;", "", "()V", "tagLookup", "", "", "Lorg/partiql/lang/ast/NodeTag;", "fromTagText", "tagText", "lang"})
    public static final class Companion {
        @Nullable
        public final NodeTag fromTagText(@NotNull String tagText) {
            Intrinsics.checkParameterIsNotNull(tagText, "tagText");
            return (NodeTag)((Object)tagLookup.get(tagText));
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

