/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.antlr.v4.runtime.FailedPredicateException
 *  org.antlr.v4.runtime.NoViableAltException
 *  org.antlr.v4.runtime.Parser
 *  org.antlr.v4.runtime.ParserRuleContext
 *  org.antlr.v4.runtime.RecognitionException
 *  org.antlr.v4.runtime.RuleContext
 *  org.antlr.v4.runtime.RuntimeMetaData
 *  org.antlr.v4.runtime.TokenStream
 *  org.antlr.v4.runtime.Vocabulary
 *  org.antlr.v4.runtime.VocabularyImpl
 *  org.antlr.v4.runtime.atn.ATN
 *  org.antlr.v4.runtime.atn.ATNDeserializer
 *  org.antlr.v4.runtime.atn.ParserATNSimulator
 *  org.antlr.v4.runtime.atn.PredictionContextCache
 *  org.antlr.v4.runtime.dfa.DFA
 *  org.antlr.v4.runtime.tree.ParseTreeListener
 *  org.antlr.v4.runtime.tree.ParseTreeVisitor
 *  org.antlr.v4.runtime.tree.RuleNode
 *  org.antlr.v4.runtime.tree.TerminalNode
 */
package com.amazon.dynamodb.grammar;

import com.amazon.dynamodb.grammar.DynamoDbGrammarListener;
import com.amazon.dynamodb.grammar.DynamoDbGrammarVisitor;
import com.amazon.dynamodb.grammar.exceptions.RedundantParenthesesException;
import java.util.List;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class DynamoDbGrammarParser
extends Parser {
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache;
    public static final int T__0 = 1;
    public static final int T__1 = 2;
    public static final int T__2 = 3;
    public static final int T__3 = 4;
    public static final int T__4 = 5;
    public static final int T__5 = 6;
    public static final int WS = 7;
    public static final int EQ = 8;
    public static final int NE = 9;
    public static final int LT = 10;
    public static final int LE = 11;
    public static final int GT = 12;
    public static final int GE = 13;
    public static final int PLUS = 14;
    public static final int MINUS = 15;
    public static final int IN = 16;
    public static final int BETWEEN = 17;
    public static final int NOT = 18;
    public static final int AND = 19;
    public static final int OR = 20;
    public static final int SET = 21;
    public static final int ADD = 22;
    public static final int DELETE = 23;
    public static final int REMOVE = 24;
    public static final int INDEX = 25;
    public static final int ID = 26;
    public static final int ATTRIBUTE_NAME_SUB = 27;
    public static final int LITERAL_SUB = 28;
    public static final int UNKNOWN = 29;
    public static final int RULE_projection_ = 0;
    public static final int RULE_projection = 1;
    public static final int RULE_condition_ = 2;
    public static final int RULE_condition = 3;
    public static final int RULE_comparator_symbol = 4;
    public static final int RULE_update_ = 5;
    public static final int RULE_update = 6;
    public static final int RULE_set_section = 7;
    public static final int RULE_set_action = 8;
    public static final int RULE_add_section = 9;
    public static final int RULE_add_action = 10;
    public static final int RULE_delete_section = 11;
    public static final int RULE_delete_action = 12;
    public static final int RULE_remove_section = 13;
    public static final int RULE_remove_action = 14;
    public static final int RULE_set_value = 15;
    public static final int RULE_arithmetic = 16;
    public static final int RULE_operand = 17;
    public static final int RULE_function = 18;
    public static final int RULE_path = 19;
    public static final int RULE_id = 20;
    public static final int RULE_dereference = 21;
    public static final int RULE_literal = 22;
    public static final int RULE_expression_attr_names_sub = 23;
    public static final int RULE_expression_attr_values_sub = 24;
    public static final int RULE_unknown = 25;
    public static final String[] ruleNames;
    private static final String[] _LITERAL_NAMES;
    private static final String[] _SYMBOLIC_NAMES;
    public static final Vocabulary VOCABULARY;
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN = "\u0004\u0001\u001d\u00f1\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001;\b\u0001\n\u0001\f\u0001>\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003N\b\u0003\n\u0003\f\u0003Q\t\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003c\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003k\b\u0003\n\u0003\f\u0003n\t\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0004\u0006y\b\u0006\u000b\u0006\f\u0006z\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u0081\b\u0007\n\u0007\f\u0007\u0084\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0005\t\u008e\b\t\n\t\f\t\u0091\t\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u009a\b\u000b\n\u000b\f\u000b\u009d\t\u000b\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u00a6\b\r\n\r\f\r\u00a9\t\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0003\u000f\u00af\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00ba\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u00c4\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u00cb\b\u0012\n\u0012\f\u0012\u00ce\t\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0005\u0013\u00d4\b\u0013\n\u0013\f\u0013\u00d7\t\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u00e2\b\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0004\u0019\u00ed\b\u0019\u000b\u0019\f\u0019\u00ee\u0001\u0019\u0000\u0001\u0006\u001a\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02\u0000\u0003\u0001\u0000\b\r\u0001\u0000\u000e\u000f\u0001\u0000\u001a\u001b\u00f1\u00004\u0001\u0000\u0000\u0000\u00027\u0001\u0000\u0000\u0000\u0004?\u0001\u0000\u0000\u0000\u0006b\u0001\u0000\u0000\u0000\bo\u0001\u0000\u0000\u0000\nq\u0001\u0000\u0000\u0000\fx\u0001\u0000\u0000\u0000\u000e|\u0001\u0000\u0000\u0000\u0010\u0085\u0001\u0000\u0000\u0000\u0012\u0089\u0001\u0000\u0000\u0000\u0014\u0092\u0001\u0000\u0000\u0000\u0016\u0095\u0001\u0000\u0000\u0000\u0018\u009e\u0001\u0000\u0000\u0000\u001a\u00a1\u0001\u0000\u0000\u0000\u001c\u00aa\u0001\u0000\u0000\u0000\u001e\u00ae\u0001\u0000\u0000\u0000 \u00b9\u0001\u0000\u0000\u0000\"\u00c3\u0001\u0000\u0000\u0000$\u00c5\u0001\u0000\u0000\u0000&\u00d1\u0001\u0000\u0000\u0000(\u00d8\u0001\u0000\u0000\u0000*\u00e1\u0001\u0000\u0000\u0000,\u00e3\u0001\u0000\u0000\u0000.\u00e5\u0001\u0000\u0000\u00000\u00e8\u0001\u0000\u0000\u00002\u00ec\u0001\u0000\u0000\u000045\u0003\u0002\u0001\u000056\u0005\u0000\u0000\u00016\u0001\u0001\u0000\u0000\u00007<\u0003&\u0013\u000089\u0005\u0001\u0000\u00009;\u0003&\u0013\u0000:8\u0001\u0000\u0000\u0000;>\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=\u0003\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000?@\u0003\u0006\u0003\u0000@A\u0005\u0000\u0000\u0001A\u0005\u0001\u0000\u0000\u0000BC\u0006\u0003\uffff\uffff\u0000CD\u0003\"\u0011\u0000DE\u0003\b\u0004\u0000EF\u0003\"\u0011\u0000Fc\u0001\u0000\u0000\u0000GH\u0003\"\u0011\u0000HI\u0005\u0010\u0000\u0000IJ\u0005\u0002\u0000\u0000JO\u0003\"\u0011\u0000KL\u0005\u0001\u0000\u0000LN\u0003\"\u0011\u0000MK\u0001\u0000\u0000\u0000NQ\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000PR\u0001\u0000\u0000\u0000QO\u0001\u0000\u0000\u0000RS\u0005\u0003\u0000\u0000Sc\u0001\u0000\u0000\u0000TU\u0003\"\u0011\u0000UV\u0005\u0011\u0000\u0000VW\u0003\"\u0011\u0000WX\u0005\u0013\u0000\u0000XY\u0003\"\u0011\u0000Yc\u0001\u0000\u0000\u0000Zc\u0003$\u0012\u0000[\\\u0005\u0002\u0000\u0000\\]\u0003\u0006\u0003\u0000]^\u0005\u0003\u0000\u0000^_\u0006\u0003\uffff\uffff\u0000_c\u0001\u0000\u0000\u0000`a\u0005\u0012\u0000\u0000ac\u0003\u0006\u0003\u0003bB\u0001\u0000\u0000\u0000bG\u0001\u0000\u0000\u0000bT\u0001\u0000\u0000\u0000bZ\u0001\u0000\u0000\u0000b[\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000cl\u0001\u0000\u0000\u0000de\n\u0002\u0000\u0000ef\u0005\u0013\u0000\u0000fk\u0003\u0006\u0003\u0002gh\n\u0001\u0000\u0000hi\u0005\u0014\u0000\u0000ik\u0003\u0006\u0003\u0001jd\u0001\u0000\u0000\u0000jg\u0001\u0000\u0000\u0000kn\u0001\u0000\u0000\u0000lj\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000m\u0007\u0001\u0000\u0000\u0000nl\u0001\u0000\u0000\u0000op\u0007\u0000\u0000\u0000p\t\u0001\u0000\u0000\u0000qr\u0003\f\u0006\u0000rs\u0005\u0000\u0000\u0001s\u000b\u0001\u0000\u0000\u0000ty\u0003\u000e\u0007\u0000uy\u0003\u0012\t\u0000vy\u0003\u0016\u000b\u0000wy\u0003\u001a\r\u0000xt\u0001\u0000\u0000\u0000xu\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000xw\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{\r\u0001\u0000\u0000\u0000|}\u0005\u0015\u0000\u0000}\u0082\u0003\u0010\b\u0000~\u007f\u0005\u0001\u0000\u0000\u007f\u0081\u0003\u0010\b\u0000\u0080~\u0001\u0000\u0000\u0000\u0081\u0084\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u000f\u0001\u0000\u0000\u0000\u0084\u0082\u0001\u0000\u0000\u0000\u0085\u0086\u0003&\u0013\u0000\u0086\u0087\u0005\b\u0000\u0000\u0087\u0088\u0003\u001e\u000f\u0000\u0088\u0011\u0001\u0000\u0000\u0000\u0089\u008a\u0005\u0016\u0000\u0000\u008a\u008f\u0003\u0014\n\u0000\u008b\u008c\u0005\u0001\u0000\u0000\u008c\u008e\u0003\u0014\n\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008e\u0091\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090\u0013\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0092\u0093\u0003&\u0013\u0000\u0093\u0094\u0003,\u0016\u0000\u0094\u0015\u0001\u0000\u0000\u0000\u0095\u0096\u0005\u0017\u0000\u0000\u0096\u009b\u0003\u0018\f\u0000\u0097\u0098\u0005\u0001\u0000\u0000\u0098\u009a\u0003\u0018\f\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u009a\u009d\u0001\u0000\u0000\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c\u0017\u0001\u0000\u0000\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009e\u009f\u0003&\u0013\u0000\u009f\u00a0\u0003,\u0016\u0000\u00a0\u0019\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005\u0018\u0000\u0000\u00a2\u00a7\u0003\u001c\u000e\u0000\u00a3\u00a4\u0005\u0001\u0000\u0000\u00a4\u00a6\u0003\u001c\u000e\u0000\u00a5\u00a3\u0001\u0000\u0000\u0000\u00a6\u00a9\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8\u001b\u0001\u0000\u0000\u0000\u00a9\u00a7\u0001\u0000\u0000\u0000\u00aa\u00ab\u0003&\u0013\u0000\u00ab\u001d\u0001\u0000\u0000\u0000\u00ac\u00af\u0003\"\u0011\u0000\u00ad\u00af\u0003 \u0010\u0000\u00ae\u00ac\u0001\u0000\u0000\u0000\u00ae\u00ad\u0001\u0000\u0000\u0000\u00af\u001f\u0001\u0000\u0000\u0000\u00b0\u00b1\u0003\"\u0011\u0000\u00b1\u00b2\u0007\u0001\u0000\u0000\u00b2\u00b3\u0003\"\u0011\u0000\u00b3\u00ba\u0001\u0000\u0000\u0000\u00b4\u00b5\u0005\u0002\u0000\u0000\u00b5\u00b6\u0003 \u0010\u0000\u00b6\u00b7\u0005\u0003\u0000\u0000\u00b7\u00b8\u0006\u0010\uffff\uffff\u0000\u00b8\u00ba\u0001\u0000\u0000\u0000\u00b9\u00b0\u0001\u0000\u0000\u0000\u00b9\u00b4\u0001\u0000\u0000\u0000\u00ba!\u0001\u0000\u0000\u0000\u00bb\u00c4\u0003&\u0013\u0000\u00bc\u00c4\u0003,\u0016\u0000\u00bd\u00c4\u0003$\u0012\u0000\u00be\u00bf\u0005\u0002\u0000\u0000\u00bf\u00c0\u0003\"\u0011\u0000\u00c0\u00c1\u0005\u0003\u0000\u0000\u00c1\u00c2\u0006\u0011\uffff\uffff\u0000\u00c2\u00c4\u0001\u0000\u0000\u0000\u00c3\u00bb\u0001\u0000\u0000\u0000\u00c3\u00bc\u0001\u0000\u0000\u0000\u00c3\u00bd\u0001\u0000\u0000\u0000\u00c3\u00be\u0001\u0000\u0000\u0000\u00c4#\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005\u001a\u0000\u0000\u00c6\u00c7\u0005\u0002\u0000\u0000\u00c7\u00cc\u0003\"\u0011\u0000\u00c8\u00c9\u0005\u0001\u0000\u0000\u00c9\u00cb\u0003\"\u0011\u0000\u00ca\u00c8\u0001\u0000\u0000\u0000\u00cb\u00ce\u0001\u0000\u0000\u0000\u00cc\u00ca\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000\u0000\u0000\u00cd\u00cf\u0001\u0000\u0000\u0000\u00ce\u00cc\u0001\u0000\u0000\u0000\u00cf\u00d0\u0005\u0003\u0000\u0000\u00d0%\u0001\u0000\u0000\u0000\u00d1\u00d5\u0003(\u0014\u0000\u00d2\u00d4\u0003*\u0015\u0000\u00d3\u00d2\u0001\u0000\u0000\u0000\u00d4\u00d7\u0001\u0000\u0000\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d5\u00d6\u0001\u0000\u0000\u0000\u00d6'\u0001\u0000\u0000\u0000\u00d7\u00d5\u0001\u0000\u0000\u0000\u00d8\u00d9\u0007\u0002\u0000\u0000\u00d9)\u0001\u0000\u0000\u0000\u00da\u00db\u0005\u0004\u0000\u0000\u00db\u00e2\u0003(\u0014\u0000\u00dc\u00dd\u0005\u0005\u0000\u0000\u00dd\u00de\u0005\u0019\u0000\u0000\u00de\u00e2\u0005\u0006\u0000\u0000\u00df\u00e0\u0005\u0004\u0000\u0000\u00e0\u00e2\u0005\u001c\u0000\u0000\u00e1\u00da\u0001\u0000\u0000\u0000\u00e1\u00dc\u0001\u0000\u0000\u0000\u00e1\u00df\u0001\u0000\u0000\u0000\u00e2+\u0001\u0000\u0000\u0000\u00e3\u00e4\u0005\u001c\u0000\u0000\u00e4-\u0001\u0000\u0000\u0000\u00e5\u00e6\u0005\u001b\u0000\u0000\u00e6\u00e7\u0005\u0000\u0000\u0001\u00e7/\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005\u001c\u0000\u0000\u00e9\u00ea\u0005\u0000\u0000\u0001\u00ea1\u0001\u0000\u0000\u0000\u00eb\u00ed\u0005\u001d\u0000\u0000\u00ec\u00eb\u0001\u0000\u0000\u0000\u00ed\u00ee\u0001\u0000\u0000\u0000\u00ee\u00ec\u0001\u0000\u0000\u0000\u00ee\u00ef\u0001\u0000\u0000\u0000\u00ef3\u0001\u0000\u0000\u0000\u0012<Objlxz\u0082\u008f\u009b\u00a7\u00ae\u00b9\u00c3\u00cc\u00d5\u00e1\u00ee";
    public static final ATN _ATN;

    private static String[] makeRuleNames() {
        return new String[]{"projection_", "projection", "condition_", "condition", "comparator_symbol", "update_", "update", "set_section", "set_action", "add_section", "add_action", "delete_section", "delete_action", "remove_section", "remove_action", "set_value", "arithmetic", "operand", "function", "path", "id", "dereference", "literal", "expression_attr_names_sub", "expression_attr_values_sub", "unknown"};
    }

    private static String[] makeLiteralNames() {
        return new String[]{null, "','", "'('", "')'", "'.'", "'['", "']'", null, "'='", "'<>'", "'<'", "'<='", "'>'", "'>='", "'+'", "'-'"};
    }

    private static String[] makeSymbolicNames() {
        return new String[]{null, null, null, null, null, null, null, "WS", "EQ", "NE", "LT", "LE", "GT", "GE", "PLUS", "MINUS", "IN", "BETWEEN", "NOT", "AND", "OR", "SET", "ADD", "DELETE", "REMOVE", "INDEX", "ID", "ATTRIBUTE_NAME_SUB", "LITERAL_SUB", "UNKNOWN"};
    }

    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    public String getGrammarFileName() {
        return "DynamoDbGrammar.g4";
    }

    public String[] getRuleNames() {
        return ruleNames;
    }

    public String getSerializedATN() {
        return _serializedATN;
    }

    public ATN getATN() {
        return _ATN;
    }

    private static void validateRedundantParentheses(boolean redundantParens) {
        if (redundantParens) {
            throw new RedundantParenthesesException();
        }
    }

    public DynamoDbGrammarParser(TokenStream input) {
        super(input);
        this._interp = new ParserATNSimulator((Parser)this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public final Projection_Context projection_() throws RecognitionException {
        Projection_Context _localctx = new Projection_Context(this._ctx, this.getState());
        this.enterRule(_localctx, 0, 0);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(52);
            this.projection();
            this.setState(53);
            this.match(-1);
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ProjectionContext projection() throws RecognitionException {
        ProjectionContext _localctx = new ProjectionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 2, 1);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(55);
            this.path();
            this.setState(60);
            this._errHandler.sync((Parser)this);
            int _la = this._input.LA(1);
            while (_la == 1) {
                this.setState(56);
                this.match(1);
                this.setState(57);
                this.path();
                this.setState(62);
                this._errHandler.sync((Parser)this);
                _la = this._input.LA(1);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public final Condition_Context condition_() throws RecognitionException {
        Condition_Context _localctx = new Condition_Context(this._ctx, this.getState());
        this.enterRule(_localctx, 4, 2);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(63);
            this.condition(0);
            this.setState(64);
            this.match(-1);
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public final ConditionContext condition() throws RecognitionException {
        return this.condition(0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ConditionContext condition(int _p) throws RecognitionException {
        ConditionContext _localctx;
        ParserRuleContext _parentctx = this._ctx;
        int _parentState = this.getState();
        ConditionContext _prevctx = _localctx = new ConditionContext(this._ctx, _parentState);
        int _startState = 6;
        this.enterRecursionRule(_localctx, 6, 3, _p);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(98);
            this._errHandler.sync((Parser)this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 2, this._ctx)) {
                case 1: {
                    _localctx = new ComparatorContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(67);
                    this.operand();
                    this.setState(68);
                    this.comparator_symbol();
                    this.setState(69);
                    this.operand();
                    break;
                }
                case 2: {
                    _localctx = new InContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(71);
                    this.operand();
                    this.setState(72);
                    this.match(16);
                    this.setState(73);
                    this.match(2);
                    this.setState(74);
                    this.operand();
                    this.setState(79);
                    this._errHandler.sync((Parser)this);
                    int _la = this._input.LA(1);
                    while (_la == 1) {
                        this.setState(75);
                        this.match(1);
                        this.setState(76);
                        this.operand();
                        this.setState(81);
                        this._errHandler.sync((Parser)this);
                        _la = this._input.LA(1);
                    }
                    this.setState(82);
                    this.match(3);
                    break;
                }
                case 3: {
                    _localctx = new BetweenContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(84);
                    this.operand();
                    this.setState(85);
                    this.match(17);
                    this.setState(86);
                    this.operand();
                    this.setState(87);
                    this.match(19);
                    this.setState(88);
                    this.operand();
                    break;
                }
                case 4: {
                    _localctx = new FunctionConditionContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(90);
                    this.function();
                    break;
                }
                case 5: {
                    _localctx = new ConditionGroupingContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(91);
                    this.match(2);
                    this.setState(92);
                    ((ConditionGroupingContext)_localctx).c = this.condition(0);
                    this.setState(93);
                    this.match(3);
                    DynamoDbGrammarParser.validateRedundantParentheses(((ConditionGroupingContext)_localctx).c.hasOuterParens);
                    ((ConditionGroupingContext)_localctx).hasOuterParens = true;
                    break;
                }
                case 6: {
                    _localctx = new NegationContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(96);
                    this.match(18);
                    this.setState(97);
                    this.condition(3);
                }
            }
            this._ctx.stop = this._input.LT(-1);
            this.setState(108);
            this._errHandler.sync((Parser)this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 4, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    if (this._parseListeners != null) {
                        this.triggerExitRuleEvent();
                    }
                    _prevctx = _localctx;
                    this.setState(106);
                    this._errHandler.sync((Parser)this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 3, this._ctx)) {
                        case 1: {
                            _localctx = new AndContext(new ConditionContext(_parentctx, _parentState));
                            this.pushNewRecursionContext(_localctx, _startState, 3);
                            this.setState(100);
                            if (!this.precpred((RuleContext)this._ctx, 2)) {
                                throw new FailedPredicateException((Parser)this, "precpred(_ctx, 2)");
                            }
                            this.setState(101);
                            this.match(19);
                            this.setState(102);
                            this.condition(2);
                            break;
                        }
                        case 2: {
                            _localctx = new OrContext(new ConditionContext(_parentctx, _parentState));
                            this.pushNewRecursionContext(_localctx, _startState, 3);
                            this.setState(103);
                            if (!this.precpred((RuleContext)this._ctx, 1)) {
                                throw new FailedPredicateException((Parser)this, "precpred(_ctx, 1)");
                            }
                            this.setState(104);
                            this.match(20);
                            this.setState(105);
                            this.condition(1);
                        }
                    }
                }
                this.setState(110);
                this._errHandler.sync((Parser)this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 4, this._ctx);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final Comparator_symbolContext comparator_symbol() throws RecognitionException {
        Comparator_symbolContext _localctx = new Comparator_symbolContext(this._ctx, this.getState());
        this.enterRule(_localctx, 8, 4);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(111);
            int _la = this._input.LA(1);
            if ((_la & 0xFFFFFFC0) != 0 || (1L << _la & 0x3F00L) == 0L) {
                this._errHandler.recoverInline((Parser)this);
            } else {
                if (this._input.LA(1) == -1) {
                    this.matchedEOF = true;
                }
                this._errHandler.reportMatch((Parser)this);
                this.consume();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public final Update_Context update_() throws RecognitionException {
        Update_Context _localctx = new Update_Context(this._ctx, this.getState());
        this.enterRule(_localctx, 10, 5);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(113);
            this.update();
            this.setState(114);
            this.match(-1);
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final UpdateContext update() throws RecognitionException {
        UpdateContext _localctx = new UpdateContext(this._ctx, this.getState());
        this.enterRule(_localctx, 12, 6);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(120);
            this._errHandler.sync((Parser)this);
            int _la = this._input.LA(1);
            do {
                this.setState(120);
                this._errHandler.sync((Parser)this);
                switch (this._input.LA(1)) {
                    case 21: {
                        this.setState(116);
                        this.set_section();
                        break;
                    }
                    case 22: {
                        this.setState(117);
                        this.add_section();
                        break;
                    }
                    case 23: {
                        this.setState(118);
                        this.delete_section();
                        break;
                    }
                    case 24: {
                        this.setState(119);
                        this.remove_section();
                        break;
                    }
                    default: {
                        throw new NoViableAltException((Parser)this);
                    }
                }
                this.setState(122);
                this._errHandler.sync((Parser)this);
            } while (((_la = this._input.LA(1)) & 0xFFFFFFC0) == 0 && (1L << _la & 0x1E00000L) != 0L);
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final Set_sectionContext set_section() throws RecognitionException {
        Set_sectionContext _localctx = new Set_sectionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 14, 7);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(124);
            this.match(21);
            this.setState(125);
            this.set_action();
            this.setState(130);
            this._errHandler.sync((Parser)this);
            int _la = this._input.LA(1);
            while (_la == 1) {
                this.setState(126);
                this.match(1);
                this.setState(127);
                this.set_action();
                this.setState(132);
                this._errHandler.sync((Parser)this);
                _la = this._input.LA(1);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public final Set_actionContext set_action() throws RecognitionException {
        Set_actionContext _localctx = new Set_actionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 16, 8);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(133);
            this.path();
            this.setState(134);
            this.match(8);
            this.setState(135);
            this.set_value();
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final Add_sectionContext add_section() throws RecognitionException {
        Add_sectionContext _localctx = new Add_sectionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 18, 9);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(137);
            this.match(22);
            this.setState(138);
            this.add_action();
            this.setState(143);
            this._errHandler.sync((Parser)this);
            int _la = this._input.LA(1);
            while (_la == 1) {
                this.setState(139);
                this.match(1);
                this.setState(140);
                this.add_action();
                this.setState(145);
                this._errHandler.sync((Parser)this);
                _la = this._input.LA(1);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public final Add_actionContext add_action() throws RecognitionException {
        Add_actionContext _localctx = new Add_actionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 20, 10);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(146);
            this.path();
            this.setState(147);
            this.literal();
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final Delete_sectionContext delete_section() throws RecognitionException {
        Delete_sectionContext _localctx = new Delete_sectionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 22, 11);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(149);
            this.match(23);
            this.setState(150);
            this.delete_action();
            this.setState(155);
            this._errHandler.sync((Parser)this);
            int _la = this._input.LA(1);
            while (_la == 1) {
                this.setState(151);
                this.match(1);
                this.setState(152);
                this.delete_action();
                this.setState(157);
                this._errHandler.sync((Parser)this);
                _la = this._input.LA(1);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public final Delete_actionContext delete_action() throws RecognitionException {
        Delete_actionContext _localctx = new Delete_actionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 24, 12);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(158);
            this.path();
            this.setState(159);
            this.literal();
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final Remove_sectionContext remove_section() throws RecognitionException {
        Remove_sectionContext _localctx = new Remove_sectionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 26, 13);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(161);
            this.match(24);
            this.setState(162);
            this.remove_action();
            this.setState(167);
            this._errHandler.sync((Parser)this);
            int _la = this._input.LA(1);
            while (_la == 1) {
                this.setState(163);
                this.match(1);
                this.setState(164);
                this.remove_action();
                this.setState(169);
                this._errHandler.sync((Parser)this);
                _la = this._input.LA(1);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public final Remove_actionContext remove_action() throws RecognitionException {
        Remove_actionContext _localctx = new Remove_actionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 28, 14);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(170);
            this.path();
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Set_valueContext set_value() throws RecognitionException {
        Set_valueContext _localctx = new Set_valueContext(this._ctx, this.getState());
        this.enterRule(_localctx, 30, 15);
        try {
            this.setState(174);
            this._errHandler.sync((Parser)this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 11, this._ctx)) {
                case 1: {
                    _localctx = new OperandValueContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(172);
                    this.operand();
                    return _localctx;
                }
                case 2: {
                    _localctx = new ArithmeticValueContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(173);
                    this.arithmetic();
                    return _localctx;
                }
            }
            return _localctx;
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
            return _localctx;
        } finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final ArithmeticContext arithmetic() throws RecognitionException {
        ArithmeticContext _localctx = new ArithmeticContext(this._ctx, this.getState());
        this.enterRule(_localctx, 32, 16);
        try {
            this.setState(185);
            this._errHandler.sync((Parser)this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 12, this._ctx)) {
                case 1: {
                    _localctx = new PlusMinusContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(176);
                    this.operand();
                    this.setState(177);
                    int _la = this._input.LA(1);
                    if (_la != 14 && _la != 15) {
                        this._errHandler.recoverInline((Parser)this);
                    } else {
                        if (this._input.LA(1) == -1) {
                            this.matchedEOF = true;
                        }
                        this._errHandler.reportMatch((Parser)this);
                        this.consume();
                    }
                    this.setState(178);
                    this.operand();
                    return _localctx;
                }
                case 2: {
                    _localctx = new ArithmeticParensContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(180);
                    this.match(2);
                    this.setState(181);
                    ((ArithmeticParensContext)_localctx).a = this.arithmetic();
                    this.setState(182);
                    this.match(3);
                    DynamoDbGrammarParser.validateRedundantParentheses(((ArithmeticParensContext)_localctx).a.hasOuterParens);
                    ((ArithmeticParensContext)_localctx).hasOuterParens = true;
                    return _localctx;
                }
            }
            return _localctx;
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
            return _localctx;
        } finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final OperandContext operand() throws RecognitionException {
        OperandContext _localctx = new OperandContext(this._ctx, this.getState());
        this.enterRule(_localctx, 34, 17);
        try {
            this.setState(195);
            this._errHandler.sync((Parser)this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 13, this._ctx)) {
                case 1: {
                    _localctx = new PathOperandContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(187);
                    this.path();
                    return _localctx;
                }
                case 2: {
                    _localctx = new LiteralOperandContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(188);
                    this.literal();
                    return _localctx;
                }
                case 3: {
                    _localctx = new FunctionOperandContext(_localctx);
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(189);
                    this.function();
                    return _localctx;
                }
                case 4: {
                    _localctx = new ParenOperandContext(_localctx);
                    this.enterOuterAlt(_localctx, 4);
                    this.setState(190);
                    this.match(2);
                    this.setState(191);
                    ((ParenOperandContext)_localctx).o = this.operand();
                    this.setState(192);
                    this.match(3);
                    DynamoDbGrammarParser.validateRedundantParentheses(((ParenOperandContext)_localctx).o.hasOuterParens);
                    ((ParenOperandContext)_localctx).hasOuterParens = true;
                    return _localctx;
                }
            }
            return _localctx;
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
            return _localctx;
        } finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final FunctionContext function() throws RecognitionException {
        FunctionContext _localctx = new FunctionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 36, 18);
        try {
            _localctx = new FunctionCallContext(_localctx);
            this.enterOuterAlt(_localctx, 1);
            this.setState(197);
            this.match(26);
            this.setState(198);
            this.match(2);
            this.setState(199);
            this.operand();
            this.setState(204);
            this._errHandler.sync((Parser)this);
            int _la = this._input.LA(1);
            while (_la == 1) {
                this.setState(200);
                this.match(1);
                this.setState(201);
                this.operand();
                this.setState(206);
                this._errHandler.sync((Parser)this);
                _la = this._input.LA(1);
            }
            this.setState(207);
            this.match(3);
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public final PathContext path() throws RecognitionException {
        PathContext _localctx = new PathContext(this._ctx, this.getState());
        this.enterRule(_localctx, 38, 19);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(209);
            this.id();
            this.setState(213);
            this._errHandler.sync((Parser)this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 15, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(210);
                    this.dereference();
                }
                this.setState(215);
                this._errHandler.sync((Parser)this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 15, this._ctx);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final IdContext id() throws RecognitionException {
        IdContext _localctx = new IdContext(this._ctx, this.getState());
        this.enterRule(_localctx, 40, 20);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(216);
            int _la = this._input.LA(1);
            if (_la != 26 && _la != 27) {
                this._errHandler.recoverInline((Parser)this);
            } else {
                if (this._input.LA(1) == -1) {
                    this.matchedEOF = true;
                }
                this._errHandler.reportMatch((Parser)this);
                this.consume();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final DereferenceContext dereference() throws RecognitionException {
        DereferenceContext _localctx = new DereferenceContext(this._ctx, this.getState());
        this.enterRule(_localctx, 42, 21);
        try {
            this.setState(225);
            this._errHandler.sync((Parser)this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 16, this._ctx)) {
                case 1: {
                    _localctx = new MapAccessContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(218);
                    this.match(4);
                    this.setState(219);
                    this.id();
                    return _localctx;
                }
                case 2: {
                    _localctx = new ListAccessContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(220);
                    this.match(5);
                    this.setState(221);
                    this.match(25);
                    this.setState(222);
                    this.match(6);
                    return _localctx;
                }
                case 3: {
                    _localctx = new ScalarMapAccessContext(_localctx);
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(223);
                    this.match(4);
                    this.setState(224);
                    this.match(28);
                    return _localctx;
                }
            }
            return _localctx;
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
            return _localctx;
        } finally {
            this.exitRule();
        }
    }

    public final LiteralContext literal() throws RecognitionException {
        LiteralContext _localctx = new LiteralContext(this._ctx, this.getState());
        this.enterRule(_localctx, 44, 22);
        try {
            _localctx = new LiteralSubContext(_localctx);
            this.enterOuterAlt(_localctx, 1);
            this.setState(227);
            this.match(28);
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public final Expression_attr_names_subContext expression_attr_names_sub() throws RecognitionException {
        Expression_attr_names_subContext _localctx = new Expression_attr_names_subContext(this._ctx, this.getState());
        this.enterRule(_localctx, 46, 23);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(229);
            this.match(27);
            this.setState(230);
            this.match(-1);
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public final Expression_attr_values_subContext expression_attr_values_sub() throws RecognitionException {
        Expression_attr_values_subContext _localctx = new Expression_attr_values_subContext(this._ctx, this.getState());
        this.enterRule(_localctx, 48, 24);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(232);
            this.match(28);
            this.setState(233);
            this.match(-1);
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final UnknownContext unknown() throws RecognitionException {
        UnknownContext _localctx = new UnknownContext(this._ctx, this.getState());
        this.enterRule(_localctx, 50, 25);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(236);
            this._errHandler.sync((Parser)this);
            int _la = this._input.LA(1);
            do {
                this.setState(235);
                this.match(29);
                this.setState(238);
                this._errHandler.sync((Parser)this);
            } while ((_la = this._input.LA(1)) == 29);
        } catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError((Parser)this, re);
            this._errHandler.recover((Parser)this, re);
        } finally {
            this.exitRule();
        }
        return _localctx;
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 3: {
                return this.condition_sempred((ConditionContext)_localctx, predIndex);
            }
        }
        return true;
    }

    private boolean condition_sempred(ConditionContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0: {
                return this.precpred((RuleContext)this._ctx, 2);
            }
            case 1: {
                return this.precpred((RuleContext)this._ctx, 1);
            }
        }
        return true;
    }

    static {
        int i;
        RuntimeMetaData.checkVersion((String)"4.10.1", (String)"4.10.1");
        _sharedContextCache = new PredictionContextCache();
        ruleNames = DynamoDbGrammarParser.makeRuleNames();
        _LITERAL_NAMES = DynamoDbGrammarParser.makeLiteralNames();
        _SYMBOLIC_NAMES = DynamoDbGrammarParser.makeSymbolicNames();
        VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (i = 0; i < tokenNames.length; ++i) {
            DynamoDbGrammarParser.tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                DynamoDbGrammarParser.tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }
            if (tokenNames[i] != null) continue;
            DynamoDbGrammarParser.tokenNames[i] = "<INVALID>";
        }
        _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (i = 0; i < _ATN.getNumberOfDecisions(); ++i) {
            DynamoDbGrammarParser._decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public static class Projection_Context
    extends ParserRuleContext {
        public ProjectionContext projection() {
            return (ProjectionContext)this.getRuleContext(ProjectionContext.class, 0);
        }

        public TerminalNode EOF() {
            return this.getToken(-1, 0);
        }

        public Projection_Context(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 0;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterProjection_(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitProjection_(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitProjection_(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class ProjectionContext
    extends ParserRuleContext {
        public List<PathContext> path() {
            return this.getRuleContexts(PathContext.class);
        }

        public PathContext path(int i) {
            return (PathContext)this.getRuleContext(PathContext.class, i);
        }

        public ProjectionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 1;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterProjection(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitProjection(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitProjection(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class PathContext
    extends ParserRuleContext {
        public IdContext id() {
            return (IdContext)this.getRuleContext(IdContext.class, 0);
        }

        public List<DereferenceContext> dereference() {
            return this.getRuleContexts(DereferenceContext.class);
        }

        public DereferenceContext dereference(int i) {
            return (DereferenceContext)this.getRuleContext(DereferenceContext.class, i);
        }

        public PathContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 19;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterPath(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitPath(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitPath(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Condition_Context
    extends ParserRuleContext {
        public ConditionContext condition() {
            return (ConditionContext)this.getRuleContext(ConditionContext.class, 0);
        }

        public TerminalNode EOF() {
            return this.getToken(-1, 0);
        }

        public Condition_Context(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 2;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterCondition_(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitCondition_(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitCondition_(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class ConditionContext
    extends ParserRuleContext {
        public boolean hasOuterParens = false;

        public ConditionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 3;
        }

        public ConditionContext() {
        }

        public void copyFrom(ConditionContext ctx) {
            super.copyFrom((ParserRuleContext)ctx);
            this.hasOuterParens = ctx.hasOuterParens;
        }
    }

    public static class ComparatorContext
    extends ConditionContext {
        public List<OperandContext> operand() {
            return this.getRuleContexts(OperandContext.class);
        }

        public OperandContext operand(int i) {
            return (OperandContext)this.getRuleContext(OperandContext.class, i);
        }

        public Comparator_symbolContext comparator_symbol() {
            return (Comparator_symbolContext)this.getRuleContext(Comparator_symbolContext.class, 0);
        }

        public ComparatorContext(ConditionContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterComparator(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitComparator(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitComparator(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class OperandContext
    extends ParserRuleContext {
        public boolean hasOuterParens = false;

        public OperandContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 17;
        }

        public OperandContext() {
        }

        public void copyFrom(OperandContext ctx) {
            super.copyFrom((ParserRuleContext)ctx);
            this.hasOuterParens = ctx.hasOuterParens;
        }
    }

    public static class Comparator_symbolContext
    extends ParserRuleContext {
        public TerminalNode EQ() {
            return this.getToken(8, 0);
        }

        public TerminalNode NE() {
            return this.getToken(9, 0);
        }

        public TerminalNode LT() {
            return this.getToken(10, 0);
        }

        public TerminalNode LE() {
            return this.getToken(11, 0);
        }

        public TerminalNode GT() {
            return this.getToken(12, 0);
        }

        public TerminalNode GE() {
            return this.getToken(13, 0);
        }

        public Comparator_symbolContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 4;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterComparator_symbol(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitComparator_symbol(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitComparator_symbol(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class InContext
    extends ConditionContext {
        public List<OperandContext> operand() {
            return this.getRuleContexts(OperandContext.class);
        }

        public OperandContext operand(int i) {
            return (OperandContext)this.getRuleContext(OperandContext.class, i);
        }

        public TerminalNode IN() {
            return this.getToken(16, 0);
        }

        public InContext(ConditionContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterIn(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitIn(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitIn(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class BetweenContext
    extends ConditionContext {
        public List<OperandContext> operand() {
            return this.getRuleContexts(OperandContext.class);
        }

        public OperandContext operand(int i) {
            return (OperandContext)this.getRuleContext(OperandContext.class, i);
        }

        public TerminalNode BETWEEN() {
            return this.getToken(17, 0);
        }

        public TerminalNode AND() {
            return this.getToken(19, 0);
        }

        public BetweenContext(ConditionContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterBetween(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitBetween(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitBetween(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class FunctionConditionContext
    extends ConditionContext {
        public FunctionContext function() {
            return (FunctionContext)this.getRuleContext(FunctionContext.class, 0);
        }

        public FunctionConditionContext(ConditionContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterFunctionCondition(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitFunctionCondition(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitFunctionCondition(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class FunctionContext
    extends ParserRuleContext {
        public FunctionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 18;
        }

        public FunctionContext() {
        }

        public void copyFrom(FunctionContext ctx) {
            super.copyFrom((ParserRuleContext)ctx);
        }
    }

    public static class ConditionGroupingContext
    extends ConditionContext {
        public ConditionContext c;

        public ConditionContext condition() {
            return (ConditionContext)this.getRuleContext(ConditionContext.class, 0);
        }

        public ConditionGroupingContext(ConditionContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterConditionGrouping(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitConditionGrouping(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitConditionGrouping(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class NegationContext
    extends ConditionContext {
        public TerminalNode NOT() {
            return this.getToken(18, 0);
        }

        public ConditionContext condition() {
            return (ConditionContext)this.getRuleContext(ConditionContext.class, 0);
        }

        public NegationContext(ConditionContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterNegation(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitNegation(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitNegation(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class AndContext
    extends ConditionContext {
        public List<ConditionContext> condition() {
            return this.getRuleContexts(ConditionContext.class);
        }

        public ConditionContext condition(int i) {
            return (ConditionContext)this.getRuleContext(ConditionContext.class, i);
        }

        public TerminalNode AND() {
            return this.getToken(19, 0);
        }

        public AndContext(ConditionContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterAnd(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitAnd(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitAnd(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class OrContext
    extends ConditionContext {
        public List<ConditionContext> condition() {
            return this.getRuleContexts(ConditionContext.class);
        }

        public ConditionContext condition(int i) {
            return (ConditionContext)this.getRuleContext(ConditionContext.class, i);
        }

        public TerminalNode OR() {
            return this.getToken(20, 0);
        }

        public OrContext(ConditionContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterOr(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitOr(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitOr(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Update_Context
    extends ParserRuleContext {
        public UpdateContext update() {
            return (UpdateContext)this.getRuleContext(UpdateContext.class, 0);
        }

        public TerminalNode EOF() {
            return this.getToken(-1, 0);
        }

        public Update_Context(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 5;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterUpdate_(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitUpdate_(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitUpdate_(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class UpdateContext
    extends ParserRuleContext {
        public List<Set_sectionContext> set_section() {
            return this.getRuleContexts(Set_sectionContext.class);
        }

        public Set_sectionContext set_section(int i) {
            return (Set_sectionContext)this.getRuleContext(Set_sectionContext.class, i);
        }

        public List<Add_sectionContext> add_section() {
            return this.getRuleContexts(Add_sectionContext.class);
        }

        public Add_sectionContext add_section(int i) {
            return (Add_sectionContext)this.getRuleContext(Add_sectionContext.class, i);
        }

        public List<Delete_sectionContext> delete_section() {
            return this.getRuleContexts(Delete_sectionContext.class);
        }

        public Delete_sectionContext delete_section(int i) {
            return (Delete_sectionContext)this.getRuleContext(Delete_sectionContext.class, i);
        }

        public List<Remove_sectionContext> remove_section() {
            return this.getRuleContexts(Remove_sectionContext.class);
        }

        public Remove_sectionContext remove_section(int i) {
            return (Remove_sectionContext)this.getRuleContext(Remove_sectionContext.class, i);
        }

        public UpdateContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 6;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterUpdate(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitUpdate(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitUpdate(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Set_sectionContext
    extends ParserRuleContext {
        public TerminalNode SET() {
            return this.getToken(21, 0);
        }

        public List<Set_actionContext> set_action() {
            return this.getRuleContexts(Set_actionContext.class);
        }

        public Set_actionContext set_action(int i) {
            return (Set_actionContext)this.getRuleContext(Set_actionContext.class, i);
        }

        public Set_sectionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 7;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterSet_section(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitSet_section(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitSet_section(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Add_sectionContext
    extends ParserRuleContext {
        public TerminalNode ADD() {
            return this.getToken(22, 0);
        }

        public List<Add_actionContext> add_action() {
            return this.getRuleContexts(Add_actionContext.class);
        }

        public Add_actionContext add_action(int i) {
            return (Add_actionContext)this.getRuleContext(Add_actionContext.class, i);
        }

        public Add_sectionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 9;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterAdd_section(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitAdd_section(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitAdd_section(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Delete_sectionContext
    extends ParserRuleContext {
        public TerminalNode DELETE() {
            return this.getToken(23, 0);
        }

        public List<Delete_actionContext> delete_action() {
            return this.getRuleContexts(Delete_actionContext.class);
        }

        public Delete_actionContext delete_action(int i) {
            return (Delete_actionContext)this.getRuleContext(Delete_actionContext.class, i);
        }

        public Delete_sectionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 11;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterDelete_section(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitDelete_section(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitDelete_section(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Remove_sectionContext
    extends ParserRuleContext {
        public TerminalNode REMOVE() {
            return this.getToken(24, 0);
        }

        public List<Remove_actionContext> remove_action() {
            return this.getRuleContexts(Remove_actionContext.class);
        }

        public Remove_actionContext remove_action(int i) {
            return (Remove_actionContext)this.getRuleContext(Remove_actionContext.class, i);
        }

        public Remove_sectionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 13;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterRemove_section(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitRemove_section(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitRemove_section(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Set_actionContext
    extends ParserRuleContext {
        public PathContext path() {
            return (PathContext)this.getRuleContext(PathContext.class, 0);
        }

        public TerminalNode EQ() {
            return this.getToken(8, 0);
        }

        public Set_valueContext set_value() {
            return (Set_valueContext)this.getRuleContext(Set_valueContext.class, 0);
        }

        public Set_actionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 8;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterSet_action(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitSet_action(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitSet_action(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Set_valueContext
    extends ParserRuleContext {
        public Set_valueContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 15;
        }

        public Set_valueContext() {
        }

        public void copyFrom(Set_valueContext ctx) {
            super.copyFrom((ParserRuleContext)ctx);
        }
    }

    public static class Add_actionContext
    extends ParserRuleContext {
        public PathContext path() {
            return (PathContext)this.getRuleContext(PathContext.class, 0);
        }

        public LiteralContext literal() {
            return (LiteralContext)this.getRuleContext(LiteralContext.class, 0);
        }

        public Add_actionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 10;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterAdd_action(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitAdd_action(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitAdd_action(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class LiteralContext
    extends ParserRuleContext {
        public LiteralContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 22;
        }

        public LiteralContext() {
        }

        public void copyFrom(LiteralContext ctx) {
            super.copyFrom((ParserRuleContext)ctx);
        }
    }

    public static class Delete_actionContext
    extends ParserRuleContext {
        public PathContext path() {
            return (PathContext)this.getRuleContext(PathContext.class, 0);
        }

        public LiteralContext literal() {
            return (LiteralContext)this.getRuleContext(LiteralContext.class, 0);
        }

        public Delete_actionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 12;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterDelete_action(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitDelete_action(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitDelete_action(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Remove_actionContext
    extends ParserRuleContext {
        public PathContext path() {
            return (PathContext)this.getRuleContext(PathContext.class, 0);
        }

        public Remove_actionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 14;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterRemove_action(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitRemove_action(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitRemove_action(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class OperandValueContext
    extends Set_valueContext {
        public OperandContext operand() {
            return (OperandContext)this.getRuleContext(OperandContext.class, 0);
        }

        public OperandValueContext(Set_valueContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterOperandValue(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitOperandValue(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitOperandValue(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class ArithmeticValueContext
    extends Set_valueContext {
        public ArithmeticContext arithmetic() {
            return (ArithmeticContext)this.getRuleContext(ArithmeticContext.class, 0);
        }

        public ArithmeticValueContext(Set_valueContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterArithmeticValue(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitArithmeticValue(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitArithmeticValue(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class ArithmeticContext
    extends ParserRuleContext {
        public boolean hasOuterParens = false;

        public ArithmeticContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 16;
        }

        public ArithmeticContext() {
        }

        public void copyFrom(ArithmeticContext ctx) {
            super.copyFrom((ParserRuleContext)ctx);
            this.hasOuterParens = ctx.hasOuterParens;
        }
    }

    public static class PlusMinusContext
    extends ArithmeticContext {
        public List<OperandContext> operand() {
            return this.getRuleContexts(OperandContext.class);
        }

        public OperandContext operand(int i) {
            return (OperandContext)this.getRuleContext(OperandContext.class, i);
        }

        public TerminalNode PLUS() {
            return this.getToken(14, 0);
        }

        public TerminalNode MINUS() {
            return this.getToken(15, 0);
        }

        public PlusMinusContext(ArithmeticContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterPlusMinus(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitPlusMinus(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitPlusMinus(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class ArithmeticParensContext
    extends ArithmeticContext {
        public ArithmeticContext a;

        public ArithmeticContext arithmetic() {
            return (ArithmeticContext)this.getRuleContext(ArithmeticContext.class, 0);
        }

        public ArithmeticParensContext(ArithmeticContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterArithmeticParens(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitArithmeticParens(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitArithmeticParens(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class PathOperandContext
    extends OperandContext {
        public PathContext path() {
            return (PathContext)this.getRuleContext(PathContext.class, 0);
        }

        public PathOperandContext(OperandContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterPathOperand(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitPathOperand(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitPathOperand(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class LiteralOperandContext
    extends OperandContext {
        public LiteralContext literal() {
            return (LiteralContext)this.getRuleContext(LiteralContext.class, 0);
        }

        public LiteralOperandContext(OperandContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterLiteralOperand(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitLiteralOperand(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitLiteralOperand(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class FunctionOperandContext
    extends OperandContext {
        public FunctionContext function() {
            return (FunctionContext)this.getRuleContext(FunctionContext.class, 0);
        }

        public FunctionOperandContext(OperandContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterFunctionOperand(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitFunctionOperand(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitFunctionOperand(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class ParenOperandContext
    extends OperandContext {
        public OperandContext o;

        public OperandContext operand() {
            return (OperandContext)this.getRuleContext(OperandContext.class, 0);
        }

        public ParenOperandContext(OperandContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterParenOperand(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitParenOperand(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitParenOperand(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class FunctionCallContext
    extends FunctionContext {
        public TerminalNode ID() {
            return this.getToken(26, 0);
        }

        public List<OperandContext> operand() {
            return this.getRuleContexts(OperandContext.class);
        }

        public OperandContext operand(int i) {
            return (OperandContext)this.getRuleContext(OperandContext.class, i);
        }

        public FunctionCallContext(FunctionContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterFunctionCall(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitFunctionCall(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitFunctionCall(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class IdContext
    extends ParserRuleContext {
        public TerminalNode ID() {
            return this.getToken(26, 0);
        }

        public TerminalNode ATTRIBUTE_NAME_SUB() {
            return this.getToken(27, 0);
        }

        public IdContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 20;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterId(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitId(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitId(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class DereferenceContext
    extends ParserRuleContext {
        public DereferenceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 21;
        }

        public DereferenceContext() {
        }

        public void copyFrom(DereferenceContext ctx) {
            super.copyFrom((ParserRuleContext)ctx);
        }
    }

    public static class MapAccessContext
    extends DereferenceContext {
        public IdContext id() {
            return (IdContext)this.getRuleContext(IdContext.class, 0);
        }

        public MapAccessContext(DereferenceContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterMapAccess(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitMapAccess(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitMapAccess(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class ListAccessContext
    extends DereferenceContext {
        public TerminalNode INDEX() {
            return this.getToken(25, 0);
        }

        public ListAccessContext(DereferenceContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterListAccess(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitListAccess(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitListAccess(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class ScalarMapAccessContext
    extends DereferenceContext {
        public TerminalNode LITERAL_SUB() {
            return this.getToken(28, 0);
        }

        public ScalarMapAccessContext(DereferenceContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterScalarMapAccess(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitScalarMapAccess(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitScalarMapAccess(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class LiteralSubContext
    extends LiteralContext {
        public TerminalNode LITERAL_SUB() {
            return this.getToken(28, 0);
        }

        public LiteralSubContext(LiteralContext ctx) {
            this.copyFrom(ctx);
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterLiteralSub(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitLiteralSub(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitLiteralSub(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Expression_attr_names_subContext
    extends ParserRuleContext {
        public TerminalNode ATTRIBUTE_NAME_SUB() {
            return this.getToken(27, 0);
        }

        public TerminalNode EOF() {
            return this.getToken(-1, 0);
        }

        public Expression_attr_names_subContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 23;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterExpression_attr_names_sub(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitExpression_attr_names_sub(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitExpression_attr_names_sub(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class Expression_attr_values_subContext
    extends ParserRuleContext {
        public TerminalNode LITERAL_SUB() {
            return this.getToken(28, 0);
        }

        public TerminalNode EOF() {
            return this.getToken(-1, 0);
        }

        public Expression_attr_values_subContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 24;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterExpression_attr_values_sub(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitExpression_attr_values_sub(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitExpression_attr_values_sub(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }

    public static class UnknownContext
    extends ParserRuleContext {
        public List<TerminalNode> UNKNOWN() {
            return this.getTokens(29);
        }

        public TerminalNode UNKNOWN(int i) {
            return this.getToken(29, i);
        }

        public UnknownContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public int getRuleIndex() {
            return 25;
        }

        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).enterUnknown(this);
            }
        }

        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof DynamoDbGrammarListener) {
                ((DynamoDbGrammarListener)listener).exitUnknown(this);
            }
        }

        public <T> T accept(ParseTreeVisitor<? extends T> visitor2) {
            if (visitor2 instanceof DynamoDbGrammarVisitor) {
                return ((DynamoDbGrammarVisitor)visitor2).visitUnknown(this);
            }
            return (T)visitor2.visitChildren((RuleNode)this);
        }
    }
}

