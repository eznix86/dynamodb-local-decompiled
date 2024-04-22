/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.syntax;

import com.amazon.ion.IonBool;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.Assignment;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.AstKt;
import org.partiql.lang.ast.AstSerializer;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.CallAgg;
import org.partiql.lang.ast.CaseSensitivity;
import org.partiql.lang.ast.ColumnComponent;
import org.partiql.lang.ast.ConflictAction;
import org.partiql.lang.ast.CreateIndex;
import org.partiql.lang.ast.CreateTable;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.DataType;
import org.partiql.lang.ast.DateTimeType;
import org.partiql.lang.ast.DmlOpList;
import org.partiql.lang.ast.DropIndex;
import org.partiql.lang.ast.DropTable;
import org.partiql.lang.ast.Exec;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.ExprNodeToStatementKt;
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.FromSourceExpr;
import org.partiql.lang.ast.FromSourceJoin;
import org.partiql.lang.ast.FromSourceUnpivot;
import org.partiql.lang.ast.GroupBy;
import org.partiql.lang.ast.GroupByItem;
import org.partiql.lang.ast.GroupingStrategy;
import org.partiql.lang.ast.InsertOp;
import org.partiql.lang.ast.InsertReturning;
import org.partiql.lang.ast.InsertValueOp;
import org.partiql.lang.ast.IsImplictJoinMeta;
import org.partiql.lang.ast.IsIonLiteralMeta;
import org.partiql.lang.ast.JoinOp;
import org.partiql.lang.ast.LegacyLogicalNotMeta;
import org.partiql.lang.ast.LetBinding;
import org.partiql.lang.ast.LetSource;
import org.partiql.lang.ast.LetVariables;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.MetaKt;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.OnConflict;
import org.partiql.lang.ast.OrderBy;
import org.partiql.lang.ast.OrderingSpec;
import org.partiql.lang.ast.Parameter;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.PathComponentExpr;
import org.partiql.lang.ast.PathComponentUnpivot;
import org.partiql.lang.ast.PathComponentWildcard;
import org.partiql.lang.ast.RemoveOp;
import org.partiql.lang.ast.ReturningColumn;
import org.partiql.lang.ast.ReturningElem;
import org.partiql.lang.ast.ReturningExpr;
import org.partiql.lang.ast.ReturningMapping;
import org.partiql.lang.ast.ReturningWildcard;
import org.partiql.lang.ast.ScopeQualifier;
import org.partiql.lang.ast.SearchedCase;
import org.partiql.lang.ast.SearchedCaseWhen;
import org.partiql.lang.ast.Select;
import org.partiql.lang.ast.SelectListItem;
import org.partiql.lang.ast.SelectListItemExpr;
import org.partiql.lang.ast.SelectListItemProjectAll;
import org.partiql.lang.ast.SelectListItemStar;
import org.partiql.lang.ast.SelectProjectionList;
import org.partiql.lang.ast.SelectProjectionPivot;
import org.partiql.lang.ast.SelectProjectionValue;
import org.partiql.lang.ast.Seq;
import org.partiql.lang.ast.SeqType;
import org.partiql.lang.ast.SetQuantifier;
import org.partiql.lang.ast.SimpleCase;
import org.partiql.lang.ast.SimpleCaseWhen;
import org.partiql.lang.ast.SortSpec;
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.ast.SqlDataType;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.StructField;
import org.partiql.lang.ast.SymbolicName;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.TypedOp;
import org.partiql.lang.ast.UtilKt;
import org.partiql.lang.ast.VariableReference;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.syntax.LexerConstantsKt;
import org.partiql.lang.syntax.OperatorPrecedenceGroups;
import org.partiql.lang.syntax.Parser;
import org.partiql.lang.syntax.ParserException;
import org.partiql.lang.syntax.SourceSpan;
import org.partiql.lang.syntax.SqlLexer;
import org.partiql.lang.syntax.SqlParser;
import org.partiql.lang.syntax.SqlParser$ParseNode$WhenMappings;
import org.partiql.lang.syntax.SqlParser$WhenMappings;
import org.partiql.lang.syntax.Token;
import org.partiql.lang.syntax.TokenType;
import org.partiql.lang.util.IonValueExtensionsKt;
import org.partiql.lang.util.NumberExtensionsKt;
import org.partiql.lang.util.ThreadInterruptUtilsKt;
import org.partiql.lang.util.TimeExtensionsKt;
import org.partiql.lang.util.TokenListExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0090\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\f\u00a4\u0001\u00a5\u0001\u00a6\u0001\u00a7\u0001\u00a8\u0001\u00a9\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\fH\u0002J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\fH\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0007H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0016\u001a\u00020\u0007H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u0007H\u0016J\u0018\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\fH\u0002J(\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%H\u0002J$\u0010'\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0004\u0012\u00020%0(*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0014\u0010)\u001a\u00020 *\u00020\f2\u0006\u0010*\u001a\u00020\u0007H\u0002J\u001a\u0010+\u001a\u00020 *\u00020\f2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\f0\nH\u0002J,\u0010-\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\b\b\u0002\u00102\u001a\u00020#H\u0002J\u0012\u00103\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u00104\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u00105\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u001a\u00106\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u00107\u001a\u00020%H\u0002J\u0012\u00108\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u00109\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010:\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J2\u0010;\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u001d\u0010<\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0004\u0012\u00020\f0\t\u00a2\u0006\u0002\b\rH\u0082\bJ\u001a\u0010=\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010>\u001a\u00020\u000bH\u0002J\u0012\u0010?\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010@\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010A\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010B\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u001a\u0010C\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010D\u001a\u00020\u000bH\u0002J\u0012\u0010E\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u001a\u0010F\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010D\u001a\u00020\u000bH\u0002Jj\u0010G\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u001f\u0010H\u001a\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\t\u00a2\u0006\u0002\b\r24\u0010<\u001a0\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0015\u0012\u0013\u0018\u00010\f\u00a2\u0006\f\bJ\u0012\b\bD\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\f0I\u00a2\u0006\u0002\b\rH\u0082\bJ\u0012\u0010L\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010M\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010N\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010O\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J!\u0010P\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u00102\u001a\u00020#H\u0000\u00a2\u0006\u0002\bQJ\u001a\u0010R\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010D\u001a\u00020\u000bH\u0002J\u0012\u0010S\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J&\u0010T\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u00102\u001a\u00020#2\b\b\u0002\u0010U\u001a\u00020%H\u0002J\u001c\u0010V\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u00102\u001a\u00020#H\u0002J\u001a\u0010W\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010D\u001a\u00020\u000bH\u0002J2\u0010X\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u001d\u0010Y\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0004\u0012\u00020\f0\t\u00a2\u0006\u0002\b\rH\u0082\bJ\u0012\u0010Z\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010[\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J2\u0010\\\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010]\u001a\u00020\f2\u0006\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020%2\u0006\u0010a\u001a\u00020\u001dH\u0002J\u001a\u0010b\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010]\u001a\u00020\fH\u0002J\u001a\u0010c\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010]\u001a\u00020\fH\u0002J\u001a\u0010d\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010]\u001a\u00020\fH\u0002J\u0014\u0010e\u001a\u0004\u0018\u00010\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010f\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0014\u0010g\u001a\u0004\u0018\u00010\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0014\u0010h\u001a\u0004\u0018\u00010\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010i\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u001c\u0010j\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010k\u001a\u00020lH\u0002J\u0012\u0010m\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010n\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010o\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010p\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010q\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010r\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u001a\u0010s\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010t\u001a\u00020\u001dH\u0002J\u0012\u0010u\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u001a\u0010v\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010D\u001a\u00020\u000bH\u0002J\u0012\u0010w\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010x\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010y\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u001a\u0010z\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010D\u001a\u00020\u000bH\u0002J\u0012\u0010{\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010|\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0012\u0010}\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\f\u0010~\u001a\u00020\u007f*\u00020\fH\u0002J\u0018\u0010\u0080\u0001\u001a\u00030\u0081\u0001*\u00020\f2\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001H\u0002J\u000e\u0010\u0084\u0001\u001a\u00030\u0085\u0001*\u00020\fH\u0002J\u0014\u0010\u0086\u0001\u001a\t\u0012\u0005\u0012\u00030\u0087\u00010\n*\u00020\fH\u0002J\r\u0010\u0088\u0001\u001a\u00020\u001a*\u00020\fH\u0002J\u000e\u0010\u0089\u0001\u001a\u00030\u008a\u0001*\u00020\fH\u0002J\u000e\u0010\u008b\u0001\u001a\u00030\u008c\u0001*\u00020\fH\u0002J\u000e\u0010\u008d\u0001\u001a\u00030\u008e\u0001*\u00020\fH\u0002J\u000e\u0010\u008f\u0001\u001a\u00030\u0090\u0001*\u00020\fH\u0002J\u000e\u0010\u0091\u0001\u001a\u00030\u0092\u0001*\u00020\fH\u0002J\u000e\u0010\u0093\u0001\u001a\u00030\u0094\u0001*\u00020\fH\u0002J\u000e\u0010\u0095\u0001\u001a\u00030\u0096\u0001*\u00020\fH\u0002J\u000e\u0010\u0097\u0001\u001a\u00030\u0098\u0001*\u00020\fH\u0002J\u000e\u0010\u0099\u0001\u001a\u00030\u009a\u0001*\u00020\u000bH\u0002J\u0010\u0010\u009b\u0001\u001a\u00030\u0083\u0001*\u0004\u0018\u00010\u000bH\u0002J\u000e\u0010\u009c\u0001\u001a\u00030\u009d\u0001*\u00020\fH\u0002J&\u0010\u009e\u0001\u001a\u000f\u0012\u0005\u0012\u00030\u009f\u0001\u0012\u0004\u0012\u00020\f0(*\u00020\f2\n\b\u0002\u0010\u00a0\u0001\u001a\u00030\u009f\u0001H\u0002J\u000e\u0010\u00a1\u0001\u001a\u00030\u008a\u0001*\u00020\fH\u0002J\u000e\u0010\u00a2\u0001\u001a\u00030\u00a3\u0001*\u00020\fH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R'\u0010\b\u001a\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\t\u00a2\u0006\u0002\b\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R'\u0010\u000e\u001a\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\t\u00a2\u0006\u0002\b\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u00aa\u0001\u00b2\u0006\u0011\u0010\u00ab\u0001\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u008a\u0084\u0002"}, d2={"Lorg/partiql/lang/syntax/SqlParser;", "Lorg/partiql/lang/syntax/Parser;", "ion", "Lcom/amazon/ion/IonSystem;", "(Lcom/amazon/ion/IonSystem;)V", "IN_OP_NORMAL_EVAL_KEYWORDS", "", "", "parseCommaDelim", "Lkotlin/Function1;", "", "Lorg/partiql/lang/syntax/Token;", "Lorg/partiql/lang/syntax/SqlParser$ParseNode;", "Lkotlin/ExtensionFunctionType;", "parseJoinDelim", "trueValue", "Lcom/amazon/ion/IonBool;", "inspectColumnPathExpression", "pathNode", "inspectPathExpression", "parse", "Lcom/amazon/ion/IonSexp;", "source", "parseAstStatement", "Lorg/partiql/lang/domains/PartiqlAst$Statement;", "parseExprNode", "Lorg/partiql/lang/ast/ExprNode;", "parseSelectAfterProjection", "selectType", "Lorg/partiql/lang/syntax/SqlParser$ParseType;", "projection", "validateTopLevelNodes", "", "node", "level", "", "topLevelTokenSeen", "", "dmlListTokenSeen", "checkForOptionalTimeZone", "Lkotlin/Pair;", "expectEof", "statementType", "malformedIfNotEmpty", "unconsumedChildren", "parseArgList", "aliasSupportType", "Lorg/partiql/lang/syntax/SqlParser$AliasSupportType;", "mode", "Lorg/partiql/lang/syntax/SqlParser$ArgListMode;", "precedence", "parseBagLiteral", "parseBaseDml", "parseBaseDmls", "parseCase", "isSimple", "parseCaseBody", "parseCast", "parseColumn", "parseCommaList", "parseItem", "parseConflictAction", "token", "parseCreate", "parseCreateIndex", "parseCreateTable", "parseDate", "parseDateAddOrDateDiff", "name", "parseDatePart", "parseDelete", "parseDelimitedList", "parseDelim", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "delim", "parseDrop", "parseDropIndex", "parseDropTable", "parseExec", "parseExpression", "parseExpression$lang", "parseExtract", "parseFrom", "parseFromSource", "parseRemaining", "parseFromSourceList", "parseFunctionCall", "parseLegacyDml", "parseDmlOp", "parseLet", "parseListLiteral", "parseOptionalAlias", "child", "keywordTokenType", "Lorg/partiql/lang/syntax/TokenType;", "keywordIsOptional", "parseNodeType", "parseOptionalAsAlias", "parseOptionalAtAlias", "parseOptionalByAlias", "parseOptionalOnConflict", "parseOptionalPrecision", "parseOptionalReturning", "parseOptionalWhere", "parseOrderByArgList", "parsePathTerm", "pathMode", "Lorg/partiql/lang/syntax/SqlParser$PathMode;", "parsePivot", "parseReturning", "parseReturningElems", "parseReturningMapping", "parseSelect", "parseSelectList", "parseSetAssignments", "type", "parseStructLiteral", "parseSubstring", "parseTableValues", "parseTerm", "parseTime", "parseTrim", "parseType", "parseUnaryTerm", "parseUpdate", "throwTopLevelParserError", "", "toColumnComponent", "Lorg/partiql/lang/ast/ColumnComponent;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "toDataType", "Lorg/partiql/lang/ast/DataType;", "toDmlOperation", "Lorg/partiql/lang/ast/DataManipulationOperation;", "toExprNode", "toFromSource", "Lorg/partiql/lang/ast/FromSource;", "toInsertReturning", "Lorg/partiql/lang/ast/InsertReturning;", "toLetBinding", "Lorg/partiql/lang/ast/LetBinding;", "toLetSource", "Lorg/partiql/lang/ast/LetSource;", "toOrderingSpec", "Lorg/partiql/lang/ast/OrderingSpec;", "toReturningExpr", "Lorg/partiql/lang/ast/ReturningExpr;", "toReturningMapping", "Lorg/partiql/lang/ast/ReturningMapping;", "toSelectListItem", "Lorg/partiql/lang/ast/SelectListItem;", "toSourceLocation", "Lorg/partiql/lang/ast/SourceLocationMeta;", "toSourceLocationMetaContainer", "toSymbolicName", "Lorg/partiql/lang/ast/SymbolicName;", "unwrapAliases", "Lorg/partiql/lang/ast/LetVariables;", "variables", "unwrapAliasesAndUnpivot", "unwrapAsAlias", "Lorg/partiql/lang/syntax/SqlParser$AsAlias;", "AliasSupportType", "ArgListMode", "AsAlias", "ParseNode", "ParseType", "PathMode", "lang", "memoizedTail"})
public final class SqlParser
implements Parser {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final IonBool trueValue;
    private final Set<String> IN_OP_NORMAL_EVAL_KEYWORDS;
    private final Function1<List<Token>, ParseNode> parseCommaDelim;
    private final Function1<List<Token>, ParseNode> parseJoinDelim;
    private final IonSystem ion;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinClass(SqlParser.class), "memoizedTail", "<v#0>"))};
    }

    private final SourceLocationMeta toSourceLocation(@NotNull Token $this$toSourceLocation) {
        return new SourceLocationMeta($this$toSourceLocation.getSpan().getLine(), $this$toSourceLocation.getSpan().getColumn(), $this$toSourceLocation.getSpan().getLength());
    }

    private final MetaContainer toSourceLocationMetaContainer(@Nullable Token $this$toSourceLocationMetaContainer) {
        return $this$toSourceLocationMetaContainer == null ? MetaKt.metaContainerOf(new Meta[0]) : MetaKt.metaContainerOf(this.toSourceLocation($this$toSourceLocationMetaContainer));
    }

    private final SymbolicName toSymbolicName(@NotNull ParseNode $this$toSymbolicName) {
        if ($this$toSymbolicName.getToken() == null) {
            Void void_ = $this$toSymbolicName.errMalformedParseTree("Expected ParseNode to have a token");
            throw null;
        }
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$0[$this$toSymbolicName.getToken().getType().ordinal()]) {
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                String string = $this$toSymbolicName.getToken().getText();
                if (string == null) {
                    Void void_ = $this$toSymbolicName.errMalformedParseTree("Expected ParseNode.token to have text");
                    throw null;
                }
                String tokenText = string;
                return new SymbolicName(tokenText, this.toSourceLocationMetaContainer($this$toSymbolicName.getToken()));
            }
        }
        Void void_ = $this$toSymbolicName.errMalformedParseTree("TokenType." + (Object)((Object)$this$toSymbolicName.getToken().getType()) + " cannot be converted to a SymbolicName");
        throw null;
    }

    private final void malformedIfNotEmpty(@NotNull ParseNode $this$malformedIfNotEmpty, List<ParseNode> unconsumedChildren) {
        if (!unconsumedChildren.isEmpty()) {
            Void void_ = $this$malformedIfNotEmpty.errMalformedParseTree("Unprocessed components remaining");
            throw null;
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ExprNode toExprNode(@NotNull ParseNode $this$toExprNode) {
        metas = this.toSourceLocationMetaContainer($this$toExprNode.getToken());
        block0 : switch (SqlParser$WhenMappings.$EnumSwitchMapping$9[$this$toExprNode.getType().ordinal()]) {
            case 1: {
                v0 = $this$toExprNode.getToken();
                v1 = v0 != null ? v0.getType() : null;
                if (v1 != null) {
                    switch (SqlParser$WhenMappings.$EnumSwitchMapping$1[v1.ordinal()]) {
                        case 1: 
                        case 2: 
                        case 3: 
                        case 4: {
                            v2 = $this$toExprNode.getToken().getValue();
                            if (v2 == null) {
                                Intrinsics.throwNpe();
                            }
                            v3 = new Literal(v2, metas);
                            break block0;
                        }
                        case 5: {
                            v4 = $this$toExprNode.getToken().getValue();
                            if (v4 == null) {
                                Intrinsics.throwNpe();
                            }
                            v3 = new Literal(v4, metas.add(IsIonLiteralMeta.Companion.getInstance()));
                            break block0;
                        }
                        case 6: {
                            v3 = new LiteralMissing(metas);
                            break block0;
                        }
                        case 7: {
                            v5 = $this$toExprNode.getToken().getText();
                            if (v5 == null) {
                                Intrinsics.throwNpe();
                            }
                            v3 = new VariableReference(v5, CaseSensitivity.SENSITIVE, null, metas, 4, null);
                            break block0;
                        }
                        case 8: {
                            v6 = $this$toExprNode.getToken().getText();
                            if (v6 == null) {
                                Intrinsics.throwNpe();
                            }
                            v3 = new VariableReference(v6, CaseSensitivity.INSENSITIVE, null, metas, 4, null);
                            break block0;
                        }
                    }
                }
                v7 = $this$toExprNode.getToken();
                v8 = $this$toExprNode.errMalformedParseTree("Unsupported atom token type " + (Object)(v7 != null ? v7.getType() : null));
                throw null;
            }
            case 2: {
                var3_3 = $this$toExprNode.getChildren();
                var39_27 = SeqType.LIST;
                $i$f$map = false;
                var5_56 = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    var10_160 = (ParseNode)item$iv$iv;
                    var40_127 = destination$iv$iv;
                    $i$a$-map-SqlParser$toExprNode$1 = false;
                    var41_134 = this.toExprNode((ParseNode)it);
                    var40_127.add(var41_134);
                }
                var40_127 = (List)destination$iv$iv;
                var43_195 = metas;
                var44_196 = var40_127;
                var45_197 = var39_27;
                v3 = new Seq(var45_197, var44_196, var43_195);
                break;
            }
            case 3: {
                $this$map$iv = $this$toExprNode.getChildren();
                var39_28 = SeqType.BAG;
                $i$f$map = false;
                $this$mapTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (ParseNode)item$iv$iv;
                    var40_128 = destination$iv$iv;
                    $i$a$-map-SqlParser$toExprNode$2 = false;
                    var41_135 = this.toExprNode(it);
                    var40_128.add(var41_135);
                }
                var40_128 = (List)destination$iv$iv;
                var46_198 = metas;
                var47_199 = var40_128;
                var48_200 = var39_28;
                v3 = new Seq(var48_200, var47_199, var46_198);
                break;
            }
            case 4: {
                $this$map$iv = $this$toExprNode.getChildren();
                $i$f$map = false;
                destination$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    $i$a$-map-SqlParser$toExprNode$2 = (ParseNode)item$iv$iv;
                    var37_201 = destination$iv$iv;
                    $i$a$-map-SqlParser$toExprNode$fields$1 = false;
                    if (it.getType() != ParseType.MEMBER) {
                        v9 = $this$toExprNode.errMalformedParseTree("Expected MEMBER node as direct descendant of a STRUCT node but instead found " + (Object)it.getType());
                        throw null;
                    }
                    if (it.getChildren().size() != 2) {
                        v10 = $this$toExprNode.errMalformedParseTree("Expected MEMBER node to have 2 children but found " + it.getChildren().size());
                        throw null;
                    }
                    keyExpr = this.toExprNode(it.getChildren().get(0));
                    valueExpr = this.toExprNode(it.getChildren().get(1));
                    var38_206 = new StructField(keyExpr, valueExpr);
                    var37_201.add(var38_206);
                }
                fields = (List)destination$iv$iv;
                v3 = new Struct(fields, metas);
                break;
            }
            case 5: 
            case 6: 
            case 7: {
                v11 = $this$toExprNode.getToken();
                if (v11 == null) {
                    Intrinsics.throwNpe();
                }
                v12 = v11.getText();
                if (v12 != null) {
                    fields = v12;
                    switch (fields.hashCode()) {
                        case -1179762114: {
                            if (!fields.equals("is_not")) ** break;
                            break;
                        }
                        case 3370: {
                            if (!fields.equals("is")) ** break;
                            v3 = new Typed(TypedOp.IS, this.toExprNode($this$toExprNode.getChildren().get(0)), this.toDataType($this$toExprNode.getChildren().get(1)), metas);
                            break block0;
                        }
                    }
                    v3 = new NAry(NAryOp.NOT, CollectionsKt.listOf(new Typed(TypedOp.IS, this.toExprNode($this$toExprNode.getChildren().get(0)), this.toDataType($this$toExprNode.getChildren().get(1)), metas)), metas.add(LegacyLogicalNotMeta.Companion.getInstance()));
                    break;
                }
                v13 = $this$toExprNode.getToken().getText();
                if (v13 == null) ** GOTO lbl141
                destination$iv$iv = v13;
                switch (destination$iv$iv.hashCode()) {
                    case -1039699439: {
                        if (!destination$iv$iv.equals("not_in")) ** break;
                        ** GOTO lbl139
                    }
                    case 1576307075: {
                        if (!destination$iv$iv.equals("not_like")) ** break;
                        break;
                    }
                    case -1799013732: {
                        if (!destination$iv$iv.equals("not_between")) ** break;
                        v14 = new Pair<String, Boolean>("between", true);
                        ** GOTO lbl145
                    }
                }
                v14 = new Pair<String, Boolean>("like", true);
                ** GOTO lbl145
lbl139:
                // 1 sources

                v14 = new Pair<String, Boolean>("in", true);
                ** GOTO lbl145
lbl141:
                // 5 sources

                v15 = $this$toExprNode.getToken().getText();
                if (v15 == null) {
                    Intrinsics.throwNpe();
                }
                v14 = new Pair<String, Boolean>(v15, false);
lbl145:
                // 4 sources

                $this$mapTo$iv$iv = v14;
                $this$map$iv = $this$mapTo$iv$iv.component1();
                wrapInNot = $this$mapTo$iv$iv.component2();
                $this$mapTo$iv$iv = opName;
                switch ($this$mapTo$iv$iv.hashCode()) {
                    case 64: {
                        if (!$this$mapTo$iv$iv.equals("@")) break;
                        childNode = $this$toExprNode.getChildren().get(0);
                        v16 = childNode.getToken();
                        if (v16 == null) {
                            v17 = $this$toExprNode.errMalformedParseTree("@ node does not have a token");
                            throw null;
                        }
                        childToken = v16;
                        switch (SqlParser$WhenMappings.$EnumSwitchMapping$2[childToken.getType().ordinal()]) {
                            case 1: {
                                v18 = childNode.getToken().getText();
                                if (v18 == null) {
                                    Intrinsics.throwNpe();
                                }
                                v19 = new VariableReference(v18, CaseSensitivity.SENSITIVE, ScopeQualifier.LEXICAL, this.toSourceLocationMetaContainer(childToken));
                                break;
                            }
                            case 2: {
                                v20 = childNode.getToken().getText();
                                if (v20 == null) {
                                    Intrinsics.throwNpe();
                                }
                                v19 = new VariableReference(v20, CaseSensitivity.INSENSITIVE, ScopeQualifier.LEXICAL, this.toSourceLocationMetaContainer(childToken));
                                break;
                            }
                            default: {
                                v21 = $this$toExprNode.errMalformedParseTree("Unexpected child node token type of @ operator node " + childToken);
                                throw null;
                            }
                        }
                        v3 = v19;
                        break block0;
                    }
                }
                v22 = NAryOp.Companion.forSymbol((String)opName);
                if (v22 == null) {
                    v23 = $this$toExprNode.errMalformedParseTree("Unsupported operator: " + (String)opName);
                    throw null;
                }
                op = v22;
                item$iv$iv = $this$toExprNode.getChildren();
                var39_29 = op;
                $i$f$map = false;
                it = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    var16_266 = (ParseNode)item$iv$iv;
                    var40_129 = destination$iv$iv;
                    $i$a$-map-SqlParser$toExprNode$exprNode$1 = false;
                    var41_136 = this.toExprNode((ParseNode)it);
                    var40_129.add(var41_136);
                }
                var40_129 = (List)destination$iv$iv;
                var49_286 = metas;
                var50_287 = var40_129;
                var51_288 = var39_29;
                exprNode = new NAry(var51_288, var50_287, var49_286);
                v3 = wrapInNot == false ? exprNode : new NAry(NAryOp.NOT, CollectionsKt.listOf(exprNode), metas.add(LegacyLogicalNotMeta.Companion.getInstance()));
                break;
            }
            case 8: {
                funcExpr = this.toExprNode($this$toExprNode.getChildren().get(0));
                dataType = this.toDataType($this$toExprNode.getChildren().get(1));
                v3 = new Typed(TypedOp.CAST, funcExpr, dataType, metas);
                break;
            }
            case 9: {
                v24 = $this$toExprNode.getToken();
                v25 = v24 != null ? v24.getText() : null;
                if (v25 == null) {
                    Intrinsics.throwNpe();
                }
                dataType = v25;
                wrapInNot = false;
                v26 = dataType;
                if (v26 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                v27 = v26.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(v27, "(this as java.lang.String).toLowerCase()");
                dataType = funcName = v27;
                switch (dataType.hashCode()) {
                    case 97288: {
                        if (!dataType.equals("bag")) break;
                        ** GOTO lbl229
                    }
                    case 3322014: {
                        if (!dataType.equals("list")) break;
                        ** GOTO lbl229
                    }
                    case 3526858: {
                        if (!dataType.equals("sexp")) break;
lbl229:
                        // 3 sources

                        $this$firstOrNull$iv /* !! */  = SeqType.values();
                        $i$f$firstOrNull = false;
                        for (SeqType element$iv : $this$firstOrNull$iv /* !! */ ) {
                            it = element$iv;
                            $i$a$-firstOrNull-SqlParser$toExprNode$seqType$1 = false;
                            if (!Intrinsics.areEqual(it.getTypeName(), funcName)) continue;
                            v28 = element$iv;
                            ** GOTO lbl238
                        }
                        v28 = null;
lbl238:
                        // 2 sources

                        if (v28 == null) {
                            v29 = $this$toExprNode.errMalformedParseTree("Cannot construct Seq node for functional call");
                            throw null;
                        }
                        seqType = v28;
                        $this$firstOrNull$iv /* !! */  = $this$toExprNode.getChildren();
                        var39_30 = seqType;
                        $i$f$map = false;
                        exprNode = $this$map$iv;
                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        $i$f$mapTo = false;
                        for (E item$iv$iv : $this$mapTo$iv$iv) {
                            $i$a$-firstOrNull-SqlParser$toExprNode$seqType$1 = (ParseNode)item$iv$iv;
                            var40_130 = destination$iv$iv;
                            $i$a$-map-SqlParser$toExprNode$3 = false;
                            var41_137 = this.toExprNode((ParseNode)it);
                            var40_130.add(var41_137);
                        }
                        var40_130 = (List)destination$iv$iv;
                        var52_289 = metas;
                        var53_290 = var40_130;
                        var54_291 = var39_30;
                        v3 = new Seq(var54_291, var53_290, var52_289);
                        break block0;
                    }
                }
                funcExpr = new VariableReference(funcName, CaseSensitivity.INSENSITIVE, null, MetaKt.metaContainerOf(new Meta[0]), 4, null);
                $this$map$iv = $this$toExprNode.getChildren();
                var40_131 = CollectionsKt.listOf(funcExpr);
                var39_31 = NAryOp.CALL;
                $i$f$map = false;
                $this$mapTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (ParseNode)item$iv$iv;
                    var41_138 = destination$iv$iv;
                    $i$a$-map-SqlParser$toExprNode$4 = false;
                    var42_292 = this.toExprNode(it);
                    var41_138.add(var42_292);
                }
                var41_138 = (List)destination$iv$iv;
                var55_293 = metas;
                var56_294 = CollectionsKt.plus(var40_131, (Iterable)var41_138);
                var57_295 = var39_31;
                v3 = new NAry(var57_295, var56_294, var55_293);
                break;
            }
            case 10: {
                v30 = $this$toExprNode.getToken();
                v31 = v30 != null ? v30.getText() : null;
                if (v31 == null) {
                    Intrinsics.throwNpe();
                }
                dataType = v31;
                funcExpr = false;
                v32 = dataType;
                if (v32 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                v33 = v32.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(v33, "(this as java.lang.String).toLowerCase()");
                var39_32 = v33;
                var58_296 = this.toSourceLocationMetaContainer($this$toExprNode.getToken());
                var59_297 = var39_32;
                procedureName = new SymbolicName(var59_297, var58_296);
                dataType = $this$toExprNode.getChildren();
                var39_32 = procedureName;
                $i$f$map = false;
                $this$map$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    element$iv = (ParseNode)item$iv$iv;
                    var40_132 = destination$iv$iv;
                    $i$a$-map-SqlParser$toExprNode$5 = false;
                    var41_139 = this.toExprNode((ParseNode)it);
                    var40_132.add(var41_139);
                }
                var40_132 = (List)destination$iv$iv;
                var60_298 = metas;
                var61_299 = var40_132;
                var62_300 = var39_32;
                v3 = new Exec((SymbolicName)var62_300, var61_299, var60_298);
                break;
            }
            case 11: {
                v34 = $this$toExprNode.getToken();
                v35 = v34 != null ? v34.getText() : null;
                if (v35 == null) {
                    Intrinsics.throwNpe();
                }
                $this$map$iv = v35;
                $i$f$map = false;
                v36 = $this$map$iv;
                if (v36 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                v37 = v36.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(v37, "(this as java.lang.String).toLowerCase()");
                var39_33 = v37;
                var63_301 = null;
                var64_302 = 4;
                var65_303 = MetaKt.metaContainerOf(new Meta[0]);
                var66_304 = null;
                var67_305 = CaseSensitivity.INSENSITIVE;
                var68_306 = var39_33;
                funcExpr = new VariableReference(var68_306, var67_305, var66_304, var65_303, var64_302, var63_301);
                v3 = new CallAgg(funcExpr, SetQuantifier.ALL, this.toExprNode(CollectionsKt.first($this$toExprNode.getChildren())), metas);
                break;
            }
            case 12: {
                v38 = $this$toExprNode.getToken();
                v39 = v38 != null ? v38.getText() : null;
                if (v39 == null) {
                    Intrinsics.throwNpe();
                }
                $this$map$iv = v39;
                $i$f$map = false;
                v40 = $this$map$iv;
                if (v40 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                v41 = v40.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(v41, "(this as java.lang.String).toLowerCase()");
                var39_34 = v41;
                var69_307 = null;
                var70_308 = 4;
                var71_309 = MetaKt.metaContainerOf(new Meta[0]);
                var72_310 = null;
                var73_311 = CaseSensitivity.INSENSITIVE;
                var74_312 = var39_34;
                funcExpr = new VariableReference(var74_312, var73_311, var72_310, var71_309, var70_308, var69_307);
                v3 = new CallAgg(funcExpr, SetQuantifier.DISTINCT, this.toExprNode(CollectionsKt.first($this$toExprNode.getChildren())), metas);
                break;
            }
            case 13: {
                v42 = $this$toExprNode.getToken();
                if (v42 == null) {
                    Intrinsics.throwNpe();
                }
                if (v42.getType() != TokenType.KEYWORD || Intrinsics.areEqual($this$toExprNode.getToken().getKeywordText(), "count") ^ true) {
                    v43 = $this$toExprNode.errMalformedParseTree("only COUNT can be used with a wildcard");
                    throw null;
                }
                countStar = UtilKt.createCountStar(this.ion, metas);
                v3 = countStar;
                break;
            }
            case 14: {
                rootExpr = this.toExprNode($this$toExprNode.getChildren().get(0));
                $this$map$iv = CollectionsKt.drop((Iterable)$this$toExprNode.getChildren(), 1);
                $i$f$map = false;
                destination$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    $i$a$-map-SqlParser$toExprNode$5 = (ParseNode)item$iv$iv;
                    var37_202 = destination$iv$iv;
                    $i$a$-map-SqlParser$toExprNode$pathComponents$1 = false;
                    block56 : switch (SqlParser$WhenMappings.$EnumSwitchMapping$4[it.getType().ordinal()]) {
                        case 1: {
                            if (it.getChildren().size() != 1) {
                                v44 = $this$toExprNode.errMalformedParseTree("Unexpected number of child elements in PATH_DOT ParseNode");
                                throw null;
                            }
                            atomParseNode = CollectionsKt.first(it.getChildren());
                            atomMetas = this.toSourceLocationMetaContainer(atomParseNode.getToken());
                            switch (SqlParser$WhenMappings.$EnumSwitchMapping$3[atomParseNode.getType().ordinal()]) {
                                case 1: 
                                case 2: {
                                    sensitivity = atomParseNode.getType() == ParseType.CASE_SENSITIVE_ATOM ? CaseSensitivity.SENSITIVE : CaseSensitivity.INSENSITIVE;
                                    v45 = atomParseNode.getToken();
                                    v46 = v45 != null ? v45.getText() : null;
                                    if (v46 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    v47 = this.ion.newString(v46);
                                    Intrinsics.checkExpressionValueIsNotNull(v47, "ion.newString(atomParseNode.token?.text!!)");
                                    v48 = new PathComponentExpr(new Literal(v47, atomMetas), sensitivity);
                                    break block56;
                                }
                                case 3: {
                                    v48 = new PathComponentUnpivot(atomMetas);
                                    break block56;
                                }
                            }
                            v49 = $this$toExprNode.errMalformedParseTree("Unsupported child path node of PATH_DOT");
                            throw null;
                        }
                        case 2: {
                            if (it.getChildren().size() != 1) {
                                v50 = $this$toExprNode.errMalformedParseTree("Unexpected number of child elements in PATH_SQB ParseNode");
                                throw null;
                            }
                            child = CollectionsKt.first(it.getChildren());
                            childMetas = this.toSourceLocationMetaContainer(child.getToken());
                            if (child.getType() == ParseType.PATH_WILDCARD) {
                                v48 = new PathComponentWildcard(childMetas);
                                break;
                            }
                            v48 = new PathComponentExpr(this.toExprNode(child), CaseSensitivity.SENSITIVE);
                            break;
                        }
                        default: {
                            v51 = $this$toExprNode.errMalformedParseTree("Unsupported path component: " + (Object)it.getType());
                            throw null;
                        }
                    }
                    var38_207 = v48;
                    var37_202.add(var38_207);
                }
                pathComponents = (List)destination$iv$iv;
                v3 = new Path(rootExpr, pathComponents, metas);
                break;
            }
            case 15: {
                v52 = $this$toExprNode.getToken();
                if (v52 == null) {
                    Intrinsics.throwNpe();
                }
                v53 = v52.getValue();
                if (v53 == null) {
                    Intrinsics.throwNpe();
                }
                v3 = new Parameter(IonValueExtensionsKt.asIonInt(v53).intValue(), metas);
                break;
            }
            case 16: {
                switch ($this$toExprNode.getChildren().size()) {
                    case 1: {
                        branches = new ArrayList<SearchedCaseWhen>();
                        elseExpr = null;
                        $this$forEach$iv = $this$toExprNode.getChildren().get(0).getChildren();
                        $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            it = (ParseNode)element$iv;
                            $i$a$-forEach-SqlParser$toExprNode$6 = false;
                            switch (SqlParser$WhenMappings.$EnumSwitchMapping$5[it.getType().ordinal()]) {
                                case 1: {
                                    branches.add(new SearchedCaseWhen(this.toExprNode(it.getChildren().get(0)), this.toExprNode(it.getChildren().get(1))));
                                    break;
                                }
                                case 2: {
                                    elseExpr = this.toExprNode(it.getChildren().get(0));
                                    break;
                                }
                                default: {
                                    v54 = $this$toExprNode.errMalformedParseTree("CASE clause must be WHEN or ELSE");
                                    throw null;
                                }
                            }
                        }
                        v3 = new SearchedCase((List<SearchedCaseWhen>)branches, elseExpr, metas);
                        break block0;
                    }
                    case 2: {
                        valueExpr = this.toExprNode($this$toExprNode.getChildren().get(0));
                        branches = new ArrayList<SimpleCaseWhen>();
                        elseExpr = null;
                        $this$forEach$iv = $this$toExprNode.getChildren().get(1).getChildren();
                        $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            it = (ParseNode)element$iv;
                            $i$a$-forEach-SqlParser$toExprNode$7 = false;
                            switch (SqlParser$WhenMappings.$EnumSwitchMapping$6[it.getType().ordinal()]) {
                                case 1: {
                                    branches.add(new SimpleCaseWhen(this.toExprNode(it.getChildren().get(0)), this.toExprNode(it.getChildren().get(1))));
                                    break;
                                }
                                case 2: {
                                    elseExpr = this.toExprNode(it.getChildren().get(0));
                                    break;
                                }
                                default: {
                                    v55 = $this$toExprNode.errMalformedParseTree("CASE clause must be WHEN or ELSE");
                                    throw null;
                                }
                            }
                        }
                        v3 = new SimpleCase(valueExpr, (List<SimpleCaseWhen>)branches, elseExpr, metas);
                        break block0;
                    }
                }
                v56 = $this$toExprNode.errMalformedParseTree("CASE must be searched or simple");
                throw null;
            }
            case 17: {
                operation = this.toExprNode($this$toExprNode.getChildren().get(0));
                elseExpr = $this$toExprNode.getChildren().get(1);
                var37_203 = this;
                $this$forEach$iv = false;
                $i$f$forEach = false;
                it = elseExpr;
                $i$a$-also-SqlParser$toExprNode$fromSource$1 = false;
                if (it.getType() != ParseType.FROM_CLAUSE) {
                    v57 = $this$toExprNode.errMalformedParseTree("Invalid second child of FROM");
                    throw null;
                }
                if (it.getChildren().size() != 1) {
                    v58 = $this$toExprNode.errMalformedParseTree("Invalid FROM clause children length");
                    throw null;
                }
                var38_208 = elseExpr;
                fromSource = var37_203.toFromSource(var38_208.getChildren().get(0));
                unconsumedChildren = CollectionsKt.toMutableList((Collection)CollectionsKt.drop((Iterable)$this$toExprNode.getChildren(), 2));
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                $i$a$-also-SqlParser$toExprNode$fromSource$1 = $this$firstOrNull$iv.iterator();
                while ($i$a$-also-SqlParser$toExprNode$fromSource$1.hasNext()) {
                    element$iv = $i$a$-also-SqlParser$toExprNode$fromSource$1.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toExprNode$where$1 = false;
                    if (!(it.getType() == ParseType.WHERE)) continue;
                    v59 = element$iv;
                    ** GOTO lbl517
                }
                v59 = null;
lbl517:
                // 2 sources

                v60 = v59;
                if (v60 != null) {
                    $this$firstOrNull$iv = v60;
                    $i$f$firstOrNull = false;
                    $i$a$-also-SqlParser$toExprNode$fromSource$1 = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toExprNode$where$2 = false;
                    unconsumedChildren.remove(it);
                    v61 = this.toExprNode(it.getChildren().get(0));
                } else {
                    v61 = null;
                }
                where = v61;
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                it = $this$firstOrNull$iv.iterator();
                while (it.hasNext()) {
                    element$iv = it.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toExprNode$returning$1 = false;
                    if (!(it.getType() == ParseType.RETURNING)) continue;
                    v62 = element$iv;
                    ** GOTO lbl541
                }
                v62 = null;
lbl541:
                // 2 sources

                v63 = v62;
                if (v63 != null) {
                    $this$firstOrNull$iv = v63;
                    $i$f$firstOrNull = false;
                    it = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toExprNode$returning$2 = false;
                    unconsumedChildren.remove(it);
                    v64 = this.toReturningExpr((ParseNode)it);
                } else {
                    v64 = null;
                }
                returning = v64;
                this.malformedIfNotEmpty($this$toExprNode, unconsumedChildren);
                $this$firstOrNull$iv = operation;
                if (!($this$firstOrNull$iv instanceof DataManipulation)) {
                    v65 = $this$toExprNode.errMalformedParseTree("Unsupported operation for FROM expression");
                    throw null;
                }
                v3 = DataManipulation.copy$default((DataManipulation)operation, null, fromSource, where, returning, metas, 1, null);
                break;
            }
            case 18: 
            case 19: {
                insertReturning = this.toInsertReturning($this$toExprNode);
                v3 = new DataManipulation(new DmlOpList(insertReturning.getOps()), null, null, insertReturning.getReturning(), metas, 6, null);
                break;
            }
            case 20: 
            case 21: 
            case 22: 
            case 23: {
                v3 = new DataManipulation(new DmlOpList(this.toDmlOperation($this$toExprNode)), null, null, null, metas, 14, null);
                break;
            }
            case 24: {
                $this$flatMap$iv = $this$toExprNode.getChildren();
                $i$f$flatMap = false;
                where = $this$flatMap$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$flatMapTo = false;
                for (T element$iv$iv : $this$flatMapTo$iv$iv) {
                    it = (ParseNode)element$iv$iv;
                    $i$a$-flatMap-SqlParser$toExprNode$dmlops$1 = false;
                    list$iv$iv = this.toDmlOperation(it);
                    CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
                }
                dmlops = CollectionsKt.toList((List)destination$iv$iv);
                v3 = new DataManipulation(new DmlOpList(dmlops), null, null, null, metas, 14, null);
                break;
            }
            case 25: 
            case 26: 
            case 27: {
                setQuantifier = SetQuantifier.ALL;
                selectList = $this$toExprNode.getChildren().get(0);
                fromList = $this$toExprNode.getChildren().get(1);
                unconsumedChildren = CollectionsKt.toMutableList((Collection)CollectionsKt.drop((Iterable)$this$toExprNode.getChildren(), 2));
                if (selectList.getType() == ParseType.DISTINCT) {
                    selectList = selectList.getChildren().get(0);
                    setQuantifier = SetQuantifier.DISTINCT;
                }
                switch (SqlParser$WhenMappings.$EnumSwitchMapping$7[$this$toExprNode.getType().ordinal()]) {
                    case 1: {
                        $this$map$iv = selectList.getChildren();
                        $i$f$map = false;
                        list$iv$iv = $this$map$iv;
                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            sensitivity = (ParseNode)item$iv$iv;
                            var37_204 = destination$iv$iv;
                            $i$a$-map-SqlParser$toExprNode$projection$selectListItems$1 = false;
                            var38_209 = this.toSelectListItem((ParseNode)it);
                            var37_204.add(var38_209);
                        }
                        selectListItems = (List)destination$iv$iv;
                        v66 = new SelectProjectionList(selectListItems);
                        break;
                    }
                    case 2: {
                        v66 = new SelectProjectionValue(this.toExprNode(selectList));
                        break;
                    }
                    case 3: {
                        member = $this$toExprNode.getChildren().get(0);
                        asExpr = this.toExprNode(member.getChildren().get(0));
                        atExpr = this.toExprNode(member.getChildren().get(1));
                        v66 = new SelectProjectionPivot(asExpr, atExpr);
                        break;
                    }
                    default: {
                        throw (Throwable)new IllegalStateException("This can never happen!");
                    }
                }
                projection = v66;
                if (fromList.getType() != ParseType.FROM_CLAUSE) {
                    v67 = $this$toExprNode.errMalformedParseTree("Invalid second child of SELECT_LIST");
                    throw null;
                }
                if (fromList.getChildren().size() != 1) {
                    v68 = $this$toExprNode.errMalformedParseTree("Invalid FROM clause children length");
                    throw null;
                }
                fromSource = this.toFromSource(fromList.getChildren().get(0));
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                destination$iv$iv = $this$firstOrNull$iv.iterator();
                while (destination$iv$iv.hasNext()) {
                    element$iv = destination$iv$iv.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toExprNode$fromLet$1 = false;
                    if (!(it.getType() == ParseType.LET)) continue;
                    v69 = element$iv;
                    ** GOTO lbl640
                }
                v69 = null;
lbl640:
                // 2 sources

                v70 = v69;
                if (v70 != null) {
                    $this$firstOrNull$iv = v70;
                    $i$f$firstOrNull = false;
                    destination$iv$iv = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toExprNode$fromLet$2 = false;
                    unconsumedChildren.remove(it);
                    v71 = this.toLetSource((ParseNode)it);
                } else {
                    v71 = null;
                }
                fromLet = v71;
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                it = $this$firstOrNull$iv.iterator();
                while (it.hasNext()) {
                    element$iv = it.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toExprNode$whereExpr$1 = false;
                    if (!(it.getType() == ParseType.WHERE)) continue;
                    v72 = element$iv;
                    ** GOTO lbl664
                }
                v72 = null;
lbl664:
                // 2 sources

                v73 = v72;
                if (v73 != null) {
                    $this$firstOrNull$iv = v73;
                    $i$f$firstOrNull = false;
                    it = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toExprNode$whereExpr$2 = false;
                    unconsumedChildren.remove(it);
                    v74 = this.toExprNode(it.getChildren().get(0));
                } else {
                    v74 = null;
                }
                whereExpr = v74;
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                it = $this$firstOrNull$iv.iterator();
                while (it.hasNext()) {
                    element$iv = it.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toExprNode$orderBy$1 = false;
                    if (!(it.getType() == ParseType.ORDER_BY)) continue;
                    v75 = element$iv;
                    ** GOTO lbl688
                }
                v75 = null;
lbl688:
                // 2 sources

                v76 = v75;
                if (v76 != null) {
                    $this$firstOrNull$iv = v76;
                    $i$f$firstOrNull = false;
                    it = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toExprNode$orderBy$2 = false;
                    unconsumedChildren.remove(it);
                    $this$map$iv = it.getChildren().get(0).getChildren();
                    $i$f$map = false;
                    var21_316 /* !! */  = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        var26_322 = (ParseNode)item$iv$iv;
                        var27_323 = destination$iv$iv;
                        $i$a$-map-SqlParser$toExprNode$orderBy$2$1 = false;
                        switch (it.getChildren().size()) {
                            case 1: {
                                v77 = new SortSpec(this.toExprNode(it.getChildren().get(0)), OrderingSpec.ASC);
                                break;
                            }
                            case 2: {
                                v77 = new SortSpec(this.toExprNode(it.getChildren().get(0)), this.toOrderingSpec(it.getChildren().get(1)));
                                break;
                            }
                            default: {
                                v78 = $this$toExprNode.errMalformedParseTree("Invalid ordering expressions syntax");
                                throw null;
                            }
                        }
                        var29_327 = v77;
                        var27_323.add(var29_327);
                    }
                    var27_323 = (List)destination$iv$iv;
                    var75_328 = var27_323;
                    v79 = new OrderBy(var75_328);
                } else {
                    v79 = null;
                }
                orderBy = v79;
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                it = $this$firstOrNull$iv.iterator();
                while (it.hasNext()) {
                    element$iv = it.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toExprNode$groupBy$1 = false;
                    if (!(it.getType() == ParseType.GROUP || it.getType() == ParseType.GROUP_PARTIAL)) continue;
                    v80 = element$iv;
                    ** GOTO lbl738
                }
                v80 = null;
lbl738:
                // 2 sources

                v81 = v80;
                if (v81 != null) {
                    $this$firstOrNull$iv = v81;
                    $i$f$firstOrNull = false;
                    it = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toExprNode$groupBy$2 = false;
                    unconsumedChildren.remove(it);
                    switch (SqlParser$WhenMappings.$EnumSwitchMapping$8[it.getType().ordinal()]) {
                        case 1: {
                            v82 = GroupingStrategy.FULL;
                            break;
                        }
                        default: {
                            v82 = GroupingStrategy.PARTIAL;
                        }
                    }
                    groupingStrategy = v82;
                    groupAsName = it.getChildren().size() > 1 ? this.toSymbolicName(it.getChildren().get(1)) : null;
                    $i$f$map = it.getChildren().get(0).getChildren();
                    var21_316 /* !! */  = groupingStrategy;
                    $i$f$map = false;
                    item$iv$iv = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        var30_339 = (ParseNode)item$iv$iv;
                        var31_340 = destination$iv$iv;
                        $i$a$-map-SqlParser$toExprNode$groupBy$2$1 = false;
                        var33_342 = this.unwrapAsAlias((ParseNode)it);
                        var34_343 = var33_342.component1();
                        groupByItemNode = var33_342.component2();
                        var36_345 = new GroupByItem(this.toExprNode(groupByItemNode), (SymbolicName)alias);
                        var31_340.add(var36_345);
                    }
                    var31_340 = (List)destination$iv$iv;
                    var76_346 = groupAsName;
                    var77_347 = var31_340;
                    var78_348 /* !! */  = var21_316 /* !! */ ;
                    v83 = new GroupBy((GroupingStrategy)var78_348 /* !! */ , var77_347, var76_346);
                } else {
                    v83 = null;
                }
                groupBy = v83;
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                it = $this$firstOrNull$iv.iterator();
                while (it.hasNext()) {
                    element$iv = it.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toExprNode$havingExpr$1 = false;
                    if (!(it.getType() == ParseType.HAVING)) continue;
                    v84 = element$iv;
                    ** GOTO lbl793
                }
                v84 = null;
lbl793:
                // 2 sources

                v85 = v84;
                if (v85 != null) {
                    $this$firstOrNull$iv = v85;
                    $i$f$firstOrNull = false;
                    it = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toExprNode$havingExpr$2 = false;
                    unconsumedChildren.remove(it);
                    v86 = this.toExprNode(it.getChildren().get(0));
                } else {
                    v86 = null;
                }
                havingExpr = v86;
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                for (T element$iv : $this$firstOrNull$iv) {
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toExprNode$limitExpr$1 = false;
                    if (!(it.getType() == ParseType.LIMIT)) continue;
                    v87 = element$iv;
                    ** GOTO lbl815
                }
                v87 = null;
lbl815:
                // 2 sources

                v88 = v87;
                if (v88 != null) {
                    var15_265 = v88;
                    var16_276 = false;
                    var17_285 = false;
                    it = var15_265;
                    $i$a$-let-SqlParser$toExprNode$limitExpr$2 = false;
                    unconsumedChildren.remove(it);
                    v89 = this.toExprNode(it.getChildren().get(0));
                } else {
                    v89 = limitExpr = null;
                }
                if (!unconsumedChildren.isEmpty()) {
                    v90 = $this$toExprNode.errMalformedParseTree("Unprocessed query components remaining");
                    throw null;
                }
                v3 = new Select(setQuantifier, projection, fromSource, fromLet, whereExpr, groupBy, havingExpr, orderBy, limitExpr, metas);
                break;
            }
            case 28: {
                v91 = CollectionsKt.first($this$toExprNode.getChildren()).getToken();
                if (v91 == null) {
                    Intrinsics.throwNpe();
                }
                v92 = v91.getText();
                if (v92 == null) {
                    Intrinsics.throwNpe();
                }
                name = v92;
                v3 = new CreateTable(name, metas);
                break;
            }
            case 29: {
                v93 = CollectionsKt.first($this$toExprNode.getChildren()).getToken();
                if (v93 == null) {
                    Intrinsics.throwNpe();
                }
                v94 = v93.getText();
                if (v94 == null) {
                    Intrinsics.throwNpe();
                }
                name = v94;
                v3 = new DropTable(name, metas);
                break;
            }
            case 30: {
                v95 = $this$toExprNode.getChildren().get(0).getToken();
                if (v95 == null) {
                    Intrinsics.throwNpe();
                }
                v96 = v95.getText();
                if (v96 == null) {
                    Intrinsics.throwNpe();
                }
                tableName = v96;
                $this$map$iv = $this$toExprNode.getChildren().get(1).getChildren();
                $i$f$map = false;
                projection = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    groupBy = (ParseNode)item$iv$iv;
                    var37_205 = destination$iv$iv;
                    $i$a$-map-SqlParser$toExprNode$keys$1 = false;
                    var38_210 = this.toExprNode((ParseNode)it);
                    var37_205.add(var38_210);
                }
                keys = (List)destination$iv$iv;
                v3 = new CreateIndex(tableName, keys, metas);
                break;
            }
            case 31: {
                v97 = this.toExprNode($this$toExprNode.getChildren().get(0));
                if (v97 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.ast.VariableReference");
                }
                identifier = (VariableReference)v97;
                v98 = $this$toExprNode.getChildren().get(1).getToken();
                if (v98 == null) {
                    Intrinsics.throwNpe();
                }
                v99 = v98.getText();
                if (v99 == null) {
                    Intrinsics.throwNpe();
                }
                tableName = v99;
                v3 = new DropIndex(tableName, identifier, metas);
                break;
            }
            case 32: {
                v100 = $this$toExprNode.getToken();
                if (v100 == null) {
                    Intrinsics.throwNpe();
                }
                v101 = v100.getText();
                if (v101 == null) {
                    Intrinsics.throwNpe();
                }
                dateString = v101;
                var7_107 = StringsKt.split$default((CharSequence)dateString, new String[]{"-"}, false, 0, 6, null);
                var8_125 = var7_107;
                var9_159 = false;
                tableName = (String)var8_125.get(0);
                var8_125 = var7_107;
                var9_159 = false;
                $this$map$iv = (String)var8_125.get(1);
                var8_125 = var7_107;
                var9_159 = false;
                day = (String)var8_125.get(2);
                var7_107 = year;
                var8_126 = false;
                var39_35 = Integer.parseInt((String)var7_107);
                var7_107 = month;
                var8_126 = false;
                var40_133 = Integer.parseInt((String)var7_107);
                var7_107 = day;
                var8_126 = false;
                var41_140 = Integer.parseInt((String)var7_107);
                var79_349 = metas;
                var80_350 = var41_140;
                var81_351 = var40_133;
                var82_352 = var39_35;
                v3 = new DateTimeType.Date(var82_352, var81_351, var80_350, var79_349);
                break;
            }
            case 33: {
                v102 = $this$toExprNode.getToken();
                if (v102 == null) {
                    Intrinsics.throwNpe();
                }
                v103 = v102.getText();
                if (v103 == null) {
                    Intrinsics.throwNpe();
                }
                timeString = v103;
                v104 = $this$toExprNode.getChildren().get(0).getToken();
                if (v104 == null) {
                    Intrinsics.throwNpe();
                }
                v105 = v104.getValue();
                if (v105 == null) {
                    Intrinsics.throwNpe();
                }
                precision = IonValueExtensionsKt.numberValue(v105).intValue();
                v106 = time = LocalTime.parse(timeString, DateTimeFormatter.ISO_TIME);
                Intrinsics.checkExpressionValueIsNotNull(v106, "time");
                v3 = new DateTimeType.Time(v106.getHour(), time.getMinute(), time.getSecond(), time.getNano(), precision, null, metas);
                break;
            }
            case 34: {
                v107 = $this$toExprNode.getToken();
                if (v107 == null) {
                    Intrinsics.throwNpe();
                }
                v108 = v107.getText();
                if (v108 == null) {
                    Intrinsics.throwNpe();
                }
                timeString = v108;
                v109 = $this$toExprNode.getChildren().get(0).getToken();
                precision = v109 != null && (v109 = v109.getValue()) != null && (v109 = IonValueExtensionsKt.numberValue((IonValue)v109)) != null ? Integer.valueOf(v109.intValue()) : null;
                v110 = time = OffsetTime.parse(timeString);
                Intrinsics.checkExpressionValueIsNotNull(v110, "time");
                v111 = v110.getHour();
                v112 = time.getMinute();
                v113 = time.getSecond();
                v114 = time.getNano();
                v115 = precision;
                if (v115 == null) {
                    Intrinsics.throwNpe();
                }
                v116 = v115;
                v117 = time.getOffset();
                Intrinsics.checkExpressionValueIsNotNull(v117, "time.offset");
                v3 = new DateTimeType.Time(v111, v112, v113, v114, v116, v117.getTotalSeconds() / 60, metas);
                break;
            }
            default: {
                v118 = ParseNode.unsupported$default($this$toExprNode, "Unsupported syntax for " + (Object)$this$toExprNode.getType(), ErrorCode.PARSE_UNSUPPORTED_SYNTAX, null, 4, null);
                throw null;
            }
        }
        return v3;
    }

    /*
     * Unable to fully structure code
     */
    private final List<DataManipulationOperation> toDmlOperation(@NotNull ParseNode $this$toDmlOperation) {
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$10[$this$toDmlOperation.getType().ordinal()]) {
            case 1: {
                v0 = CollectionsKt.listOf(new InsertOp(this.toExprNode($this$toDmlOperation.getChildren().get(0)), this.toExprNode($this$toDmlOperation.getChildren().get(1))));
                break;
            }
            case 2: {
                $fun$getOnConflictExprNode$1 = new Function1<List<? extends ParseNode>, OnConflict>(this, $this$toDmlOperation){
                    final /* synthetic */ SqlParser this$0;
                    final /* synthetic */ ParseNode $this_toDmlOperation;

                    @NotNull
                    public final OnConflict invoke(@NotNull List<ParseNode> onConflictChildren) {
                        Intrinsics.checkParameterIsNotNull(onConflictChildren, "onConflictChildren");
                        ParseNode parseNode = CollectionsKt.getOrNull(onConflictChildren, 0);
                        if (parseNode != null) {
                            ParseNode parseNode2 = parseNode;
                            boolean bl = false;
                            boolean bl2 = false;
                            ParseNode it = parseNode2;
                            boolean bl3 = false;
                            ExprNode condition = SqlParser.access$toExprNode(this.this$0, it);
                            ParseNode parseNode3 = CollectionsKt.getOrNull(onConflictChildren, 1);
                            if (parseNode3 != null) {
                                ParseNode parseNode4 = parseNode3;
                                boolean bl4 = false;
                                boolean bl5 = false;
                                ParseNode it2 = parseNode4;
                                boolean bl6 = false;
                                if (ParseType.CONFLICT_ACTION == it2.getType()) {
                                    Token token = it2.getToken();
                                    if (Intrinsics.areEqual("do_nothing", token != null ? token.getKeywordText() : null)) {
                                        return new OnConflict(condition, ConflictAction.DO_NOTHING);
                                    }
                                }
                            }
                        }
                        Void void_ = this.$this_toDmlOperation.errMalformedParseTree("invalid ON CONFLICT syntax");
                        throw null;
                    }
                    {
                        this.this$0 = sqlParser;
                        this.$this_toDmlOperation = parseNode;
                        super(1);
                    }
                };
                lvalue = this.toExprNode($this$toDmlOperation.getChildren().get(0));
                value = this.toExprNode($this$toDmlOperation.getChildren().get(1));
                unconsumedChildren = CollectionsKt.toMutableList((Collection)CollectionsKt.drop((Iterable)$this$toDmlOperation.getChildren(), 2));
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                var9_15 = $this$firstOrNull$iv.iterator();
                while (var9_15.hasNext()) {
                    element$iv = var9_15.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toDmlOperation$position$1 = false;
                    if (!(it.getType() != ParseType.ON_CONFLICT && it.getType() != ParseType.RETURNING)) continue;
                    v1 = element$iv;
                    ** GOTO lbl21
                }
                v1 = null;
lbl21:
                // 2 sources

                v2 = v1;
                if (v2 != null) {
                    $this$firstOrNull$iv = v2;
                    $i$f$firstOrNull = false;
                    var9_16 = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toDmlOperation$position$2 = false;
                    unconsumedChildren.remove(it);
                    v3 = this.toExprNode((ParseNode)it);
                } else {
                    v3 = null;
                }
                position = v3;
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                for (T element$iv : $this$firstOrNull$iv) {
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toDmlOperation$onConflict$1 = false;
                    if (!(it.getType() == ParseType.ON_CONFLICT)) continue;
                    v4 = element$iv;
                    ** GOTO lbl43
                }
                v4 = null;
lbl43:
                // 2 sources

                v5 = v4;
                if (v5 != null) {
                    var8_13 = v5;
                    $i$f$firstOrNull = false;
                    it = false;
                    it = var8_13;
                    $i$a$-let-SqlParser$toDmlOperation$onConflict$2 = false;
                    unconsumedChildren.remove(it);
                    v6 = $fun$getOnConflictExprNode$1.invoke(it.getChildren());
                } else {
                    v6 = null;
                }
                onConflict = v6;
                this.malformedIfNotEmpty($this$toDmlOperation, unconsumedChildren);
                v0 = CollectionsKt.listOf(new InsertValueOp(lvalue, value, position, onConflict));
                break;
            }
            case 3: 
            case 4: {
                $this$map$iv = $this$toDmlOperation.getChildren();
                $i$f$map = false;
                unconsumedChildren = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (ParseNode)item$iv$iv;
                    var14_33 = destination$iv$iv;
                    $i$a$-map-SqlParser$toDmlOperation$assignments$1 = false;
                    var15_34 = new AssignmentOp(new Assignment(this.toExprNode(it.getChildren().get(0)), this.toExprNode(it.getChildren().get(1))));
                    var14_33.add(var15_34);
                }
                assignments = CollectionsKt.toList((List)destination$iv$iv);
                v0 = var2_3;
                break;
            }
            case 5: {
                v0 = CollectionsKt.listOf(new RemoveOp(this.toExprNode($this$toDmlOperation.getChildren().get(0))));
                break;
            }
            case 6: {
                v0 = CollectionsKt.listOf(AstKt.DeleteOp());
                break;
            }
            default: {
                v7 = ParseNode.unsupported$default($this$toDmlOperation, "Unsupported syntax for " + (Object)$this$toDmlOperation.getType(), ErrorCode.PARSE_UNSUPPORTED_SYNTAX, null, 4, null);
                throw null;
            }
        }
        return v0;
    }

    /*
     * Unable to fully structure code
     */
    private final InsertReturning toInsertReturning(@NotNull ParseNode $this$toInsertReturning) {
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$11[$this$toInsertReturning.getType().ordinal()]) {
            case 1: {
                ops = CollectionsKt.listOf(new InsertOp(this.toExprNode($this$toInsertReturning.getChildren().get(0)), this.toExprNode($this$toInsertReturning.getChildren().get(1))));
                unconsumedChildren = CollectionsKt.toMutableList((Collection)CollectionsKt.drop((Iterable)$this$toInsertReturning.getChildren(), 2));
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                var7_10 = $this$firstOrNull$iv.iterator();
                while (var7_10.hasNext()) {
                    element$iv = var7_10.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toInsertReturning$returning$1 = false;
                    if (!(it.getType() == ParseType.RETURNING)) continue;
                    v0 = element$iv;
                    ** GOTO lbl16
                }
                v0 = null;
lbl16:
                // 2 sources

                v1 = v0;
                if (v1 != null) {
                    $this$firstOrNull$iv = v1;
                    $i$f$firstOrNull = false;
                    var7_11 = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toInsertReturning$returning$2 = false;
                    unconsumedChildren.remove(it);
                    v2 = this.toReturningExpr((ParseNode)it);
                } else {
                    v2 = null;
                }
                returning = v2;
                this.malformedIfNotEmpty($this$toInsertReturning, unconsumedChildren);
                v3 = new InsertReturning(ops, returning);
                break;
            }
            case 2: {
                $fun$getOnConflictExprNode$1 = new Function1<List<? extends ParseNode>, OnConflict>(this, $this$toInsertReturning){
                    final /* synthetic */ SqlParser this$0;
                    final /* synthetic */ ParseNode $this_toInsertReturning;

                    @NotNull
                    public final OnConflict invoke(@NotNull List<ParseNode> onConflictChildren) {
                        Intrinsics.checkParameterIsNotNull(onConflictChildren, "onConflictChildren");
                        ParseNode parseNode = CollectionsKt.getOrNull(onConflictChildren, 0);
                        if (parseNode != null) {
                            ParseNode parseNode2 = parseNode;
                            boolean bl = false;
                            boolean bl2 = false;
                            ParseNode it = parseNode2;
                            boolean bl3 = false;
                            ExprNode condition = SqlParser.access$toExprNode(this.this$0, it);
                            ParseNode parseNode3 = CollectionsKt.getOrNull(onConflictChildren, 1);
                            if (parseNode3 != null) {
                                ParseNode parseNode4 = parseNode3;
                                boolean bl4 = false;
                                boolean bl5 = false;
                                ParseNode it2 = parseNode4;
                                boolean bl6 = false;
                                if (ParseType.CONFLICT_ACTION == it2.getType()) {
                                    Token token = it2.getToken();
                                    if (Intrinsics.areEqual("do_nothing", token != null ? token.getKeywordText() : null)) {
                                        return new OnConflict(condition, ConflictAction.DO_NOTHING);
                                    }
                                }
                            }
                        }
                        Void void_ = this.$this_toInsertReturning.errMalformedParseTree("invalid ON CONFLICT syntax");
                        throw null;
                    }
                    {
                        this.this$0 = sqlParser;
                        this.$this_toInsertReturning = parseNode;
                        super(1);
                    }
                };
                lvalue = this.toExprNode($this$toInsertReturning.getChildren().get(0));
                value = this.toExprNode($this$toInsertReturning.getChildren().get(1));
                unconsumedChildren = CollectionsKt.toMutableList((Collection)CollectionsKt.drop((Iterable)$this$toInsertReturning.getChildren(), 2));
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                $i$a$-let-SqlParser$toInsertReturning$returning$2 = $this$firstOrNull$iv.iterator();
                while ($i$a$-let-SqlParser$toInsertReturning$returning$2.hasNext()) {
                    element$iv = $i$a$-let-SqlParser$toInsertReturning$returning$2.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toInsertReturning$position$1 = false;
                    if (!(it.getType() != ParseType.ON_CONFLICT && it.getType() != ParseType.RETURNING)) continue;
                    v4 = element$iv;
                    ** GOTO lbl48
                }
                v4 = null;
lbl48:
                // 2 sources

                v5 = v4;
                if (v5 != null) {
                    $this$firstOrNull$iv = v5;
                    $i$f$firstOrNull = false;
                    $i$a$-let-SqlParser$toInsertReturning$returning$2 = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toInsertReturning$position$2 = false;
                    unconsumedChildren.remove(it);
                    v6 = this.toExprNode((ParseNode)it);
                } else {
                    v6 = null;
                }
                position = v6;
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                it = $this$firstOrNull$iv.iterator();
                while (it.hasNext()) {
                    element$iv = it.next();
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toInsertReturning$onConflict$1 = false;
                    if (!(it.getType() == ParseType.ON_CONFLICT)) continue;
                    v7 = element$iv;
                    ** GOTO lbl72
                }
                v7 = null;
lbl72:
                // 2 sources

                v8 = v7;
                if (v8 != null) {
                    $this$firstOrNull$iv = v8;
                    $i$f$firstOrNull = false;
                    it = false;
                    it = $this$firstOrNull$iv;
                    $i$a$-let-SqlParser$toInsertReturning$onConflict$2 = false;
                    unconsumedChildren.remove(it);
                    v9 = $fun$getOnConflictExprNode$1.invoke(it.getChildren());
                } else {
                    v9 = null;
                }
                onConflict = v9;
                ops = CollectionsKt.listOf(new InsertValueOp(lvalue, value, position, onConflict));
                $this$firstOrNull$iv = unconsumedChildren;
                $i$f$firstOrNull = false;
                for (T element$iv : $this$firstOrNull$iv) {
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$toInsertReturning$returning$3 = false;
                    if (!(it.getType() == ParseType.RETURNING)) continue;
                    v10 = element$iv;
                    ** GOTO lbl95
                }
                v10 = null;
lbl95:
                // 2 sources

                v11 = v10;
                if (v11 != null) {
                    var10_25 = v11;
                    var11_32 = false;
                    var12_37 = false;
                    it = var10_25;
                    $i$a$-let-SqlParser$toInsertReturning$returning$4 = false;
                    unconsumedChildren.remove(it);
                    v12 = this.toReturningExpr(it);
                } else {
                    v12 = null;
                }
                returning = v12;
                this.malformedIfNotEmpty($this$toInsertReturning, unconsumedChildren);
                v3 = new InsertReturning(ops, returning);
                break;
            }
            default: {
                v13 = ParseNode.unsupported$default($this$toInsertReturning, "Unsupported syntax for " + (Object)$this$toInsertReturning.getType(), ErrorCode.PARSE_UNSUPPORTED_SYNTAX, null, 4, null);
                throw null;
            }
        }
        return v3;
    }

    private final AsAlias unwrapAsAlias(@NotNull ParseNode $this$unwrapAsAlias) {
        AsAlias asAlias;
        if ($this$unwrapAsAlias.getType() == ParseType.AS_ALIAS) {
            Token token = $this$unwrapAsAlias.getToken();
            if (token == null) {
                Intrinsics.throwNpe();
            }
            String string = token.getText();
            if (string == null) {
                Intrinsics.throwNpe();
            }
            asAlias = new AsAlias(new SymbolicName(string, this.toSourceLocationMetaContainer($this$unwrapAsAlias.getToken())), $this$unwrapAsAlias.getChildren().get(0));
        } else {
            asAlias = new AsAlias(null, $this$unwrapAsAlias);
        }
        return asAlias;
    }

    /*
     * WARNING - void declaration
     */
    private final SelectListItem toSelectListItem(@NotNull ParseNode $this$toSelectListItem) {
        SelectListItem selectListItem;
        MetaContainer metas = this.toSourceLocationMetaContainer($this$toSelectListItem.getToken());
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$12[$this$toSelectListItem.getType().ordinal()]) {
            case 1: {
                if ($this$toSelectListItem.getChildren().isEmpty()) {
                    selectListItem = new SelectListItemStar(metas);
                    break;
                }
                ExprNode expr = this.toExprNode($this$toSelectListItem.getChildren().get(0));
                selectListItem = new SelectListItemProjectAll(expr);
                break;
            }
            default: {
                void asAliasSymbol;
                AsAlias asAlias = this.unwrapAsAlias($this$toSelectListItem);
                SymbolicName expr = asAlias.component1();
                ParseNode parseNode = asAlias.component2();
                selectListItem = new SelectListItemExpr(this.toExprNode(parseNode), (SymbolicName)asAliasSymbol);
            }
        }
        return selectListItem;
    }

    private final Pair<LetVariables, ParseNode> unwrapAliases(@NotNull ParseNode $this$unwrapAliases, LetVariables variables) {
        Pair<LetVariables, ParseNode> pair;
        MetaContainer metas = this.toSourceLocationMetaContainer($this$unwrapAliases.getToken());
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$13[$this$unwrapAliases.getType().ordinal()]) {
            case 1: {
                if (variables.getAsName() != null) {
                    String string = "Invalid parse tree: AS_ALIAS encountered more than once in FROM source";
                    boolean bl = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                Token token = $this$unwrapAliases.getToken();
                if (token == null) {
                    Intrinsics.throwNpe();
                }
                String string = token.getText();
                if (string == null) {
                    Intrinsics.throwNpe();
                }
                pair = this.unwrapAliases($this$unwrapAliases.getChildren().get(0), LetVariables.copy$default(variables, new SymbolicName(string, metas), null, null, 6, null));
                break;
            }
            case 2: {
                if (variables.getAtName() != null) {
                    String string = "Invalid parse tree: AT_ALIAS encountered more than once in FROM source";
                    boolean bl = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                Token token = $this$unwrapAliases.getToken();
                if (token == null) {
                    Intrinsics.throwNpe();
                }
                String string = token.getText();
                if (string == null) {
                    Intrinsics.throwNpe();
                }
                pair = this.unwrapAliases($this$unwrapAliases.getChildren().get(0), LetVariables.copy$default(variables, null, new SymbolicName(string, metas), null, 5, null));
                break;
            }
            case 3: {
                if (variables.getByName() != null) {
                    String string = "Invalid parse tree: BY_ALIAS encountered more than once in FROM source";
                    boolean bl = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                Token token = $this$unwrapAliases.getToken();
                if (token == null) {
                    Intrinsics.throwNpe();
                }
                String string = token.getText();
                if (string == null) {
                    Intrinsics.throwNpe();
                }
                pair = this.unwrapAliases($this$unwrapAliases.getChildren().get(0), LetVariables.copy$default(variables, null, null, new SymbolicName(string, metas), 3, null));
                break;
            }
            default: {
                return new Pair<LetVariables, ParseNode>(variables, $this$unwrapAliases);
            }
        }
        return pair;
    }

    static /* synthetic */ Pair unwrapAliases$default(SqlParser sqlParser, ParseNode parseNode, LetVariables letVariables, int n, Object object) {
        if ((n & 1) != 0) {
            letVariables = new LetVariables(null, null, null, 7, null);
        }
        return sqlParser.unwrapAliases(parseNode, letVariables);
    }

    /*
     * Unable to fully structure code
     */
    private final FromSource toFromSource(@NotNull ParseNode $this$toFromSource) {
        block18: {
            head = $this$toFromSource;
            if (head.getType() != ParseType.FROM_SOURCE_JOIN) break block18;
            v0 = head.getToken();
            v1 = v0 != null && (v0 = v0.getKeywordText()) != null ? StringsKt.contains$default((CharSequence)v0, "cross", false, 2, null) : (isCrossJoin = false);
            if (!isCrossJoin && head.getChildren().size() != 3) {
                v2 = head.errMalformedParseTree("Incorrect number of clauses provided to JOIN");
                throw null;
            }
            v3 = head.getToken();
            v4 = joinTokenType = v3 != null ? v3.getKeywordText() : null;
            if (v4 == null) ** GOTO lbl-1000
            var6_5 = v4;
            tmp = -1;
            switch (var6_5.hashCode()) {
                case 379135433: {
                    if (!var6_5.equals("cross_join")) break;
                    tmp = 1;
                    break;
                }
                case 823315827: {
                    if (!var6_5.equals("inner_join")) break;
                    tmp = 1;
                    break;
                }
                case 374001677: {
                    if (!var6_5.equals("outer_cross_join")) break;
                    tmp = 2;
                    break;
                }
                case -1387936403: {
                    if (!var6_5.equals("right_join")) break;
                    tmp = 3;
                    break;
                }
                case 3267882: {
                    if (!var6_5.equals("join")) break;
                    tmp = 1;
                    break;
                }
                case -413102132: {
                    if (!var6_5.equals("right_cross_join")) break;
                    tmp = 3;
                    break;
                }
                case 1741677154: {
                    if (!var6_5.equals("left_join")) break;
                    tmp = 4;
                    break;
                }
                case -516136703: {
                    if (!var6_5.equals("left_cross_join")) break;
                    tmp = 4;
                    break;
                }
                case 1743882862: {
                    if (!var6_5.equals("outer_join")) break;
                    tmp = 2;
                    break;
                }
            }
            switch (tmp) {
                case 1: {
                    v5 = JoinOp.INNER;
                    break;
                }
                case 4: {
                    v5 = JoinOp.LEFT;
                    break;
                }
                case 3: {
                    v5 = JoinOp.RIGHT;
                    break;
                }
                case 2: {
                    v5 = JoinOp.OUTER;
                    break;
                }
                default: lbl-1000:
                // 2 sources

                {
                    v6 = head.errMalformedParseTree("Unsupported syntax for " + (Object)head.getType());
                    throw null;
                }
            }
            joinOp = v5;
            condition = isCrossJoin != false ? (ExprNode)new Literal(this.trueValue, MetaKt.metaContainerOf(new Meta[0])) : this.toExprNode(head.getChildren().get(2));
            var7_7 = this.toSourceLocationMetaContainer($this$toFromSource.getToken());
            var17_8 = condition;
            var16_9 = this.unwrapAliasesAndUnpivot(head.getChildren().get(1));
            var15_10 = this.toFromSource(head.getChildren().get(0));
            var14_11 = joinOp;
            var8_12 = false;
            var9_13 = false;
            it = var7_7;
            $i$a$-let-SqlParser$toFromSource$1 = false;
            var19_17 = var18_16 = isCrossJoin ? it.add(IsImplictJoinMeta.Companion.getInstance()) : it;
            var20_18 = var17_8;
            var21_19 = var16_9;
            var22_20 = var15_10;
            var23_21 = var14_11;
            return new FromSourceJoin(var23_21, var22_20, var21_19, var20_18, var19_17);
        }
        return this.unwrapAliasesAndUnpivot(head);
    }

    /*
     * WARNING - void declaration
     */
    private final LetSource toLetSource(@NotNull ParseNode $this$toLetSource) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = $this$toLetSource.getChildren();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ParseNode parseNode = (ParseNode)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            LetBinding letBinding = this.toLetBinding((ParseNode)it);
            collection.add(letBinding);
        }
        List letBindings = (List)destination$iv$iv;
        return new LetSource(letBindings);
    }

    /*
     * WARNING - void declaration
     */
    private final LetBinding toLetBinding(@NotNull ParseNode $this$toLetBinding) {
        void asAliasSymbol;
        AsAlias asAlias = this.unwrapAsAlias($this$toLetBinding);
        SymbolicName symbolicName = asAlias.component1();
        ParseNode parseNode = asAlias.component2();
        if (asAliasSymbol == null) {
            Void void_ = $this$toLetBinding.errMalformedParseTree("Unsupported syntax for " + (Object)((Object)$this$toLetBinding.getType()));
            throw null;
        }
        return new LetBinding(this.toExprNode(parseNode), (SymbolicName)asAliasSymbol);
    }

    /*
     * WARNING - void declaration
     */
    private final FromSource unwrapAliasesAndUnpivot(@NotNull ParseNode $this$unwrapAliasesAndUnpivot) {
        FromSource fromSource;
        Pair pair = SqlParser.unwrapAliases$default(this, $this$unwrapAliasesAndUnpivot, null, 1, null);
        LetVariables letVariables = (LetVariables)pair.component1();
        ParseNode unwrappedParseNode = (ParseNode)pair.component2();
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$14[unwrappedParseNode.getType().ordinal()]) {
            case 1: {
                void aliases;
                ExprNode expr = this.toExprNode(unwrappedParseNode.getChildren().get(0));
                fromSource = new FromSourceUnpivot(expr, (LetVariables)aliases, this.toSourceLocationMetaContainer(unwrappedParseNode.getToken()));
                break;
            }
            default: {
                void aliases;
                fromSource = new FromSourceExpr(this.toExprNode(unwrappedParseNode), (LetVariables)aliases);
            }
        }
        return fromSource;
    }

    /*
     * WARNING - void declaration
     */
    private final DataType toDataType(@NotNull ParseNode $this$toDataType) {
        void $this$mapNotNullTo$iv$iv;
        void $this$mapNotNull$iv;
        SqlDataType sqlDataType;
        if ($this$toDataType.getType() != ParseType.TYPE) {
            Void void_ = $this$toDataType.errMalformedParseTree("Expected ParseType.TYPE instead of " + (Object)((Object)$this$toDataType.getType()));
            throw null;
        }
        Token token = $this$toDataType.getToken();
        if (token == null) {
            Intrinsics.throwNpe();
        }
        String string = token.getKeywordText();
        if (string == null) {
            Intrinsics.throwNpe();
        }
        if ((sqlDataType = SqlDataType.Companion.forTypeName(string)) == null) {
            StringBuilder stringBuilder = new StringBuilder().append("Invalid DataType: ");
            String string2 = $this$toDataType.getToken().getKeywordText();
            if (string2 == null) {
                Intrinsics.throwNpe();
            }
            Void void_ = $this$toDataType.errMalformedParseTree(stringBuilder.append(string2).toString());
            throw null;
        }
        Iterable iterable = $this$toDataType.getChildren();
        SqlDataType sqlDataType2 = sqlDataType;
        boolean $i$f$mapNotNull = false;
        void var5_6 = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (iterator2.hasNext()) {
            Long l;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl = false;
            ParseNode it = (ParseNode)element$iv$iv;
            boolean bl2 = false;
            Object object = it.getToken();
            if ((object != null && (object = ((Token)object).getValue()) != null ? Long.valueOf(IonValueExtensionsKt.longValue((IonValue)object)) : null) == null) continue;
            l = l;
            boolean bl3 = false;
            boolean bl4 = false;
            Long it$iv$iv = l;
            boolean bl5 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        List list = (List)destination$iv$iv;
        MetaContainer metaContainer = this.toSourceLocationMetaContainer($this$toDataType.getToken());
        List list2 = list;
        SqlDataType sqlDataType3 = sqlDataType2;
        return new DataType(sqlDataType3, list2, metaContainer);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final OrderingSpec toOrderingSpec(@NotNull ParseNode $this$toOrderingSpec) {
        if ($this$toOrderingSpec.getType() != ParseType.ORDERING_SPEC) {
            Void void_ = $this$toOrderingSpec.errMalformedParseTree("Expected ParseType.ORDERING_SPEC instead of " + (Object)((Object)$this$toOrderingSpec.getType()));
            throw null;
        }
        Token token = $this$toOrderingSpec.getToken();
        TokenType tokenType = token != null ? token.getType() : null;
        if (tokenType != null) {
            switch (SqlParser$WhenMappings.$EnumSwitchMapping$15[tokenType.ordinal()]) {
                case 1: {
                    OrderingSpec orderingSpec = OrderingSpec.ASC;
                    return orderingSpec;
                }
                case 2: {
                    OrderingSpec orderingSpec = OrderingSpec.DESC;
                    return orderingSpec;
                }
            }
        }
        Void void_ = $this$toOrderingSpec.errMalformedParseTree("Invalid ordering spec parsing");
        throw null;
    }

    /*
     * WARNING - void declaration
     */
    private final ReturningExpr toReturningExpr(@NotNull ParseNode $this$toReturningExpr) {
        Collection<ReturningElem> collection;
        void $this$mapTo$iv$iv;
        MetaContainer metas = this.toSourceLocationMetaContainer($this$toReturningExpr.getToken());
        Iterable $this$map$iv = $this$toReturningExpr.getChildren().get(0).getChildren();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void re;
            ParseNode parseNode = (ParseNode)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            ReturningElem returningElem = new ReturningElem(this.toReturningMapping(re.getChildren().get(0)), this.toColumnComponent(re.getChildren().get(1), metas));
            collection.add(returningElem);
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        return new ReturningExpr(list);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final ReturningMapping toReturningMapping(@NotNull ParseNode $this$toReturningMapping) {
        if ($this$toReturningMapping.getType() != ParseType.RETURNING_MAPPING) {
            Void void_ = $this$toReturningMapping.errMalformedParseTree("Expected ParseType.RETURNING_MAPPING instead of " + (Object)((Object)$this$toReturningMapping.getType()));
            throw null;
        }
        Token token = $this$toReturningMapping.getToken();
        String string = token != null ? token.getKeywordText() : null;
        if (string != null) {
            switch (string) {
                case "modified_old": {
                    ReturningMapping returningMapping = ReturningMapping.MODIFIED_OLD;
                    return returningMapping;
                }
                case "modified_new": {
                    ReturningMapping returningMapping = ReturningMapping.MODIFIED_NEW;
                    return returningMapping;
                }
                case "all_old": {
                    ReturningMapping returningMapping = ReturningMapping.ALL_OLD;
                    return returningMapping;
                }
                case "all_new": {
                    ReturningMapping returningMapping = ReturningMapping.ALL_NEW;
                    return returningMapping;
                }
            }
        }
        Void void_ = $this$toReturningMapping.errMalformedParseTree("Invalid ReturningMapping parsing");
        throw null;
    }

    private final ColumnComponent toColumnComponent(@NotNull ParseNode $this$toColumnComponent, MetaContainer metas) {
        ColumnComponent columnComponent;
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$16[$this$toColumnComponent.getType().ordinal()]) {
            case 1: {
                columnComponent = new ReturningWildcard(metas);
                break;
            }
            default: {
                columnComponent = new ReturningColumn(this.toExprNode($this$toColumnComponent));
            }
        }
        return columnComponent;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @NotNull
    public final ParseNode parseExpression$lang(@NotNull List<Token> $this$parseExpression, int precedence) {
        Intrinsics.checkParameterIsNotNull($this$parseExpression, "$this$parseExpression");
        ThreadInterruptUtilsKt.checkThreadInterrupted();
        expr = this.parseUnaryTerm($this$parseExpression);
        var4_4 = new Ref.ObjectRef<T>();
        var4_4.element = expr.getRemaining();
        $fun$headPrecedence$1 = new Function0<Integer>((Ref.ObjectRef)rem){
            final /* synthetic */ Ref.ObjectRef $rem;

            public final int invoke() {
                List $this$head$iv = (List)this.$rem.element;
                boolean $i$f$getHead = false;
                Token token = (Token)CollectionsKt.firstOrNull($this$head$iv);
                return token != null ? token.getInfixPrecedence() : 0;
            }
            {
                this.$rem = objectRef;
                super(0);
            }
        };
        while (true) {
            block41: {
                block42: {
                    block40: {
                        block39: {
                            block38: {
                                var6_6 = (List)rem.element;
                                var7_8 = false;
                                if (!(var6_6.isEmpty() == false) || precedence >= $fun$headPrecedence$1.invoke()) break;
                                $this$head$iv = (List)rem.element;
                                $i$f$getHead = false;
                                v0 = CollectionsKt.firstOrNull($this$head$iv);
                                if (v0 == null) {
                                    Intrinsics.throwNpe();
                                }
                                if (!(op = (Token)v0).isBinaryOperator() && !CollectionsKt.contains((Iterable)LexerConstantsKt.SPECIAL_INFIX_OPERATORS, op.getKeywordText())) break;
                                $fun$parseRightExpr$2 = new Function0<ParseNode>(this, (Ref.ObjectRef)rem, op){
                                    final /* synthetic */ SqlParser this$0;
                                    final /* synthetic */ Ref.ObjectRef $rem;
                                    final /* synthetic */ Token $op;

                                    @NotNull
                                    public final ParseNode invoke() {
                                        List<Token> list;
                                        if (((List)this.$rem.element).size() < 3) {
                                            Void void_ = TokenListExtensionsKt.err$default((List)this.$rem.element, "Missing right-hand side expression of infix operator", ErrorCode.PARSE_EXPECTED_EXPRESSION, null, 4, null);
                                            throw null;
                                        }
                                        List $this$tail$iv = (List)this.$rem.element;
                                        SqlParser sqlParser = this.this$0;
                                        boolean $i$f$getTail = false;
                                        switch ($this$tail$iv.size()) {
                                            case 0: 
                                            case 1: {
                                                List<T> list2 = Collections.emptyList();
                                                list = list2;
                                                Intrinsics.checkExpressionValueIsNotNull(list2, "emptyList()");
                                                break;
                                            }
                                            default: {
                                                list = $this$tail$iv.subList(1, $this$tail$iv.size());
                                            }
                                        }
                                        List<Token> list3 = list;
                                        return sqlParser.parseExpression$lang(list3, this.$op.getInfixPrecedence());
                                    }
                                    {
                                        this.this$0 = sqlParser;
                                        this.$rem = objectRef;
                                        this.$op = token;
                                        super(0);
                                    }
                                };
                                if (op.getKeywordText() == null) break block38;
                                switch (var9_11.hashCode()) {
                                    case -1179762114: {
                                        if (!var9_11.equals("is_not")) ** break;
                                        ** GOTO lbl31
                                    }
                                    case 3365: {
                                        if (!var9_11.equals("in")) ** break;
                                        break;
                                    }
                                    case -1039699439: {
                                        if (!var9_11.equals("not_in")) ** break;
                                        break;
                                    }
                                    case 3370: {
                                        if (!var9_11.equals("is")) ** break;
lbl31:
                                        // 2 sources

                                        var10_12 = (List)rem.element;
                                        var13_19 = this;
                                        $i$f$getTail = false;
                                        switch ($this$tail$iv.size()) {
                                            case 0: 
                                            case 1: {
                                                v1 = Collections.emptyList();
                                                v2 = v1;
                                                Intrinsics.checkExpressionValueIsNotNull(v1, "emptyList()");
                                                break;
                                            }
                                            default: {
                                                v2 = $this$tail$iv.subList(1, $this$tail$iv.size());
                                            }
                                        }
                                        var14_20 /* !! */  = v2;
                                        v3 = var13_19.parseType(var14_20 /* !! */ );
                                        break block39;
                                    }
                                }
                                $this$tail$iv = (List<Object>)rem.element;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v4 = Collections.emptyList();
                                        v5 /* !! */  = v4;
                                        Intrinsics.checkExpressionValueIsNotNull(v4, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v5 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                $this$head$iv = v5 /* !! */ ;
                                $i$f$getHead = false;
                                v6 = (Token)CollectionsKt.firstOrNull($this$head$iv);
                                if ((v6 != null ? v6.getType() : null) != TokenType.LEFT_PAREN) ** GOTO lbl-1000
                                $this$head$iv = (List)rem.element;
                                var13_19 = this.IN_OP_NORMAL_EVAL_KEYWORDS;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v7 = Collections.emptyList();
                                        v8 /* !! */  = v7;
                                        Intrinsics.checkExpressionValueIsNotNull(v7, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v8 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                var14_20 /* !! */  = v8 /* !! */ ;
                                $this$tail$iv = var14_20 /* !! */ ;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v9 = Collections.emptyList();
                                        v10 /* !! */  = v9;
                                        Intrinsics.checkExpressionValueIsNotNull(v9, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v10 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                var14_20 /* !! */  = v10 /* !! */ ;
                                $this$tail$iv = var14_20 /* !! */ ;
                                $i$f$getHead = false;
                                var14_20 /* !! */  = CollectionsKt.firstOrNull($this$head$iv);
                                v11 = (Token)var14_20 /* !! */ ;
                                if (!CollectionsKt.contains(var13_19, v11 != null ? v11.getKeywordText() : null)) {
                                    $this$head$iv = (List)rem.element;
                                    var13_19 = this;
                                    $i$f$getTail = false;
                                    switch ($this$tail$iv.size()) {
                                        case 0: 
                                        case 1: {
                                            v12 = Collections.emptyList();
                                            v13 /* !! */  = v12;
                                            Intrinsics.checkExpressionValueIsNotNull(v12, "emptyList()");
                                            break;
                                        }
                                        default: {
                                            v13 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                        }
                                    }
                                    var14_20 /* !! */  = v13 /* !! */ ;
                                    $this$tail$iv = var14_20 /* !! */ ;
                                    $i$f$getTail = false;
                                    switch ($this$tail$iv.size()) {
                                        case 0: 
                                        case 1: {
                                            v14 = Collections.emptyList();
                                            v15 /* !! */  = v14;
                                            Intrinsics.checkExpressionValueIsNotNull(v14, "emptyList()");
                                            break;
                                        }
                                        default: {
                                            v15 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                        }
                                    }
                                    var14_20 /* !! */  = v15 /* !! */ ;
                                    v3 = ParseNode.copy$default(SqlParser.parseArgList$default((SqlParser)var13_19, var14_20 /* !! */ , AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null).deriveExpected(TokenType.RIGHT_PAREN), ParseType.LIST, null, null, null, 14, null);
                                } else lbl-1000:
                                // 2 sources

                                {
                                    v3 = $fun$parseRightExpr$2.invoke();
                                }
                                break block39;
                            }
                            v3 = $fun$parseRightExpr$2.invoke();
                        }
                        right = v3;
                        rem.element = right.getRemaining();
                        if (!op.isBinaryOperator()) break block40;
                        v16 = new ParseNode(ParseType.BINARY, op, CollectionsKt.listOf(new ParseNode[]{expr, right}), (List)rem.element);
                        break block41;
                    }
                    if (op.getKeywordText() == null) break block42;
                    switch (var9_11.hashCode()) {
                        case 3321751: {
                            if (!var9_11.equals("like")) ** break;
                            break;
                        }
                        case 1576307075: {
                            if (!var9_11.equals("not_like")) ** break;
                            break;
                        }
                        case -1799013732: {
                            if (!var9_11.equals("not_between")) ** break;
                            ** GOTO lbl137
                        }
                        case -216634360: {
                            if (!var9_11.equals("between")) ** break;
lbl137:
                            // 2 sources

                            rest = TokenListExtensionsKt.tailExpectedKeyword((List)rem.element, "and");
                            if (TokenListExtensionsKt.onlyEndOfStatement(rest)) {
                                $this$head$iv = (List)rem.element;
                                $i$f$getHead = false;
                                v17 = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull($this$head$iv), "Expected expression after AND", ErrorCode.PARSE_EXPECTED_EXPRESSION, null, 4, null);
                                throw null;
                            }
                            rem.element = rest;
                            third = this.parseExpression$lang((List)rem.element, op.getInfixPrecedence());
                            rem.element = third.getRemaining();
                            v16 = new ParseNode(ParseType.TERNARY, op, CollectionsKt.listOf(new ParseNode[]{expr, right, third}), (List)rem.element);
                            break block41;
                        }
                    }
                    $this$head$iv = (List)rem.element;
                    $i$f$getHead = false;
                    v18 = (Token)CollectionsKt.firstOrNull($this$head$iv);
                    if (Intrinsics.areEqual(v18 != null ? v18.getKeywordText() : null, "escape")) {
                        rest = TokenListExtensionsKt.tailExpectedKeyword((List)rem.element, "escape");
                        if (TokenListExtensionsKt.onlyEndOfStatement(rest)) {
                            $this$head$iv = (List)rem.element;
                            $i$f$getHead = false;
                            v19 = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull($this$head$iv), "Expected expression after ESCAPE", ErrorCode.PARSE_EXPECTED_EXPRESSION, null, 4, null);
                            throw null;
                        }
                        rem.element = rest;
                        third = this.parseExpression$lang((List)rem.element, op.getInfixPrecedence());
                        rem.element = third.getRemaining();
                        v16 = new ParseNode(ParseType.TERNARY, op, CollectionsKt.listOf(new ParseNode[]{expr, right, third}), (List)rem.element);
                    } else {
                        v16 = new ParseNode(ParseType.BINARY, op, CollectionsKt.listOf(new ParseNode[]{expr, right}), (List)rem.element);
                    }
                    break block41;
                }
                v20 = TokenListExtensionsKt.err$default((List)rem.element, "Unknown infix operator", ErrorCode.PARSE_UNKNOWN_OPERATOR, null, 4, null);
                throw null;
            }
            expr = v16;
        }
        return expr;
    }

    public static /* synthetic */ ParseNode parseExpression$lang$default(SqlParser sqlParser, List list, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = -1;
        }
        return sqlParser.parseExpression$lang(list, n);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private final ParseNode parseUnaryTerm(@NotNull List<Token> $this$parseUnaryTerm) {
        List<Token> list;
        void $this$tail$iv;
        ParseNode parseNode;
        Boolean bl;
        List<Token> $this$head$iv = $this$parseUnaryTerm;
        boolean $i$f$getHead = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        Boolean bl2 = bl = token != null ? Boolean.valueOf(token.isUnaryOperator()) : null;
        if (!Intrinsics.areEqual(bl, true)) {
            parseNode = SqlParser.parsePathTerm$default(this, $this$parseUnaryTerm, null, 1, null);
            return parseNode;
        }
        List<Token> $this$head$iv2 = $this$parseUnaryTerm;
        boolean $i$f$getHead2 = false;
        Token token2 = CollectionsKt.firstOrNull($this$head$iv2);
        if (token2 == null) {
            Intrinsics.throwNpe();
        }
        Token op = token2;
        Function1<ParseNode, ParseNode> $fun$makeUnaryParseNode$1 = new Function1<ParseNode, ParseNode>(op){
            final /* synthetic */ Token $op;

            @NotNull
            public final ParseNode invoke(@NotNull ParseNode term) {
                Intrinsics.checkParameterIsNotNull(term, "term");
                return new ParseNode(ParseType.UNARY, this.$op, CollectionsKt.listOf(term), term.getRemaining());
            }
            {
                this.$op = token;
                super(1);
            }
        };
        String string = op.getKeywordText();
        if (string != null) {
            switch (string) {
                case "+": {
                    List<Token> list2;
                    void $this$tail$iv2;
                    List<Token> list3 = $this$parseUnaryTerm;
                    SqlParser sqlParser = this;
                    boolean $i$f$getTail = false;
                    switch ($this$tail$iv2.size()) {
                        case 0: 
                        case 1: {
                            List list4 = Collections.emptyList();
                            list2 = list4;
                            Intrinsics.checkExpressionValueIsNotNull(list4, "emptyList()");
                            break;
                        }
                        default: {
                            list2 = $this$tail$iv2.subList(1, $this$tail$iv2.size());
                        }
                    }
                    List<Token> list5 = list2;
                    ParseNode term = sqlParser.parseUnaryTerm(list5);
                    if (term.isNumericLiteral()) {
                        parseNode = term;
                        return parseNode;
                    }
                    parseNode = $fun$makeUnaryParseNode$1.invoke(term);
                    return parseNode;
                }
                case "-": {
                    List<Token> list6;
                    List<Token> $this$tail$iv2 = $this$parseUnaryTerm;
                    SqlParser sqlParser = this;
                    boolean $i$f$getTail = false;
                    switch ($this$tail$iv2.size()) {
                        case 0: 
                        case 1: {
                            List list7 = Collections.emptyList();
                            list6 = list7;
                            Intrinsics.checkExpressionValueIsNotNull(list7, "emptyList()");
                            break;
                        }
                        default: {
                            list6 = $this$tail$iv2.subList(1, $this$tail$iv2.size());
                        }
                    }
                    List<Token> list8 = list6;
                    ParseNode term = sqlParser.parseUnaryTerm(list8);
                    if (!term.isNumericLiteral()) {
                        parseNode = $fun$makeUnaryParseNode$1.invoke(term);
                        return parseNode;
                    }
                    Token token3 = term.getToken();
                    if (token3 == null) {
                        Intrinsics.throwNpe();
                    }
                    parseNode = ParseNode.copy$default(term, null, Token.copy$default(token3, null, NumberExtensionsKt.ionValue(NumberExtensionsKt.unaryMinus(term.numberValue()), this.ion), null, 5, null), null, null, 13, null);
                    return parseNode;
                }
            }
        }
        List<Token> term = $this$parseUnaryTerm;
        SqlParser sqlParser = this;
        Function1<ParseNode, ParseNode> function1 = $fun$makeUnaryParseNode$1;
        boolean $i$f$getTail = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                List list9 = Collections.emptyList();
                list = list9;
                Intrinsics.checkExpressionValueIsNotNull(list9, "emptyList()");
                break;
            }
            default: {
                list = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        List<Token> list10 = list;
        parseNode = function1.invoke(sqlParser.parseExpression$lang(list10, op.getPrefixPrecedence()));
        return parseNode;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parsePathTerm(@NotNull List<Token> $this$parsePathTerm, PathMode pathMode) {
        block0 : switch (SqlParser$WhenMappings.$EnumSwitchMapping$18[pathMode.ordinal()]) {
            case 1: {
                v0 = this.parseTerm($this$parsePathTerm);
                break;
            }
            case 2: {
                $this$head$iv = $this$parsePathTerm;
                $i$f$getHead = false;
                v1 = CollectionsKt.firstOrNull($this$head$iv);
                v2 = v1 != null ? v1.getType() : null;
                if (v2 != null) {
                    switch (SqlParser$WhenMappings.$EnumSwitchMapping$17[v2.ordinal()]) {
                        case 1: 
                        case 2: {
                            v0 = TokenListExtensionsKt.atomFromHead$default($this$parsePathTerm, null, 1, null);
                            break block0;
                        }
                    }
                }
                v3 = TokenListExtensionsKt.err$default($this$parsePathTerm, "Expected identifier for simple path", ErrorCode.PARSE_INVALID_PATH_COMPONENT, null, 4, null);
                throw null;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        term = v0;
        path = new ArrayList<ParseNode>((Collection)CollectionsKt.listOf(term));
        rem /* !! */  = term.getRemaining();
        hasPath = true;
        block46: while (hasPath) {
            block57: {
                $this$head$iv = rem /* !! */ ;
                $i$f$getHead = false;
                v4 = CollectionsKt.firstOrNull($this$head$iv);
                v5 = v4 != null ? v4.getType() : null;
                if (v5 == null) break block57;
                switch (SqlParser$WhenMappings.$EnumSwitchMapping$21[v5.ordinal()]) {
                    case 1: {
                        $this$head$iv = rem /* !! */ ;
                        $i$f$getHead = false;
                        if (CollectionsKt.firstOrNull($this$head$iv) == null) {
                            Intrinsics.throwNpe();
                        }
                        $this$tail$iv = rem /* !! */ ;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v6 = Collections.emptyList();
                                v7 /* !! */  = v6;
                                Intrinsics.checkExpressionValueIsNotNull(v6, "emptyList()");
                                break;
                            }
                            default: {
                                v7 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        rem /* !! */  = v7 /* !! */ ;
                        $this$head$iv = rem /* !! */ ;
                        $i$f$getHead = false;
                        v8 = CollectionsKt.firstOrNull($this$head$iv);
                        v9 = v8 != null ? v8.getType() : null;
                        if (v9 == null) ** GOTO lbl-1000
                        switch (SqlParser$WhenMappings.$EnumSwitchMapping$19[v9.ordinal()]) {
                            case 1: {
                                $i$f$getHead = rem /* !! */ ;
                                var15_22 = this.ion;
                                var14_21 = TokenType.LITERAL;
                                $i$f$getHead = false;
                                var16_23 /* !! */  = CollectionsKt.firstOrNull($this$head$iv);
                                v10 = (Token)var16_23 /* !! */ ;
                                v11 = v10 != null ? v10.getText() : null;
                                if (v11 == null) {
                                    Intrinsics.throwNpe();
                                }
                                $this$head$iv = rem /* !! */ ;
                                var15_22 = var15_22.newString(v11);
                                $i$f$getHead = false;
                                v12 /* !! */  = var16_23 /* !! */  = CollectionsKt.firstOrNull($this$head$iv);
                                if (v12 /* !! */  == null) {
                                    Intrinsics.throwNpe();
                                }
                                var19_26 = ((Token)v12 /* !! */ ).getSpan();
                                var20_27 = var15_22;
                                var21_28 = var14_21;
                                litToken = new Token((TokenType)var21_28, (IonValue)var20_27, var19_26);
                                $this$head$iv = rem /* !! */ ;
                                var16_23 /* !! */  = CollectionsKt.emptyList();
                                var15_22 = litToken;
                                var14_21 = ParseType.CASE_INSENSITIVE_ATOM;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v13 = Collections.emptyList();
                                        v14 /* !! */  = v13;
                                        Intrinsics.checkExpressionValueIsNotNull(v13, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v14 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                var22_29 /* !! */  = var17_24 /* !! */  = v14 /* !! */ ;
                                var23_30 /* !! */  = var16_23 /* !! */ ;
                                var24_31 = var15_22;
                                var25_32 = var14_21;
                                v15 = new ParseNode((ParseType)var25_32, (Token)var24_31, (List<ParseNode>)var23_30 /* !! */ , (List<Token>)var22_29 /* !! */ );
                                break;
                            }
                            case 2: {
                                $this$tail$iv = rem /* !! */ ;
                                var15_22 = this.ion;
                                var14_21 = TokenType.LITERAL;
                                $i$f$getHead = false;
                                var16_23 /* !! */  = CollectionsKt.firstOrNull($this$head$iv);
                                v16 = (Token)var16_23 /* !! */ ;
                                v17 = v16 != null ? v16.getText() : null;
                                if (v17 == null) {
                                    Intrinsics.throwNpe();
                                }
                                $this$head$iv = rem /* !! */ ;
                                var15_22 = var15_22.newString(v17);
                                $i$f$getHead = false;
                                v18 /* !! */  = var16_23 /* !! */  = CollectionsKt.firstOrNull($this$head$iv);
                                if (v18 /* !! */  == null) {
                                    Intrinsics.throwNpe();
                                }
                                var26_33 = ((Token)v18 /* !! */ ).getSpan();
                                var27_34 = var15_22;
                                var28_35 = var14_21;
                                litToken = new Token((TokenType)var28_35, (IonValue)var27_34, var26_33);
                                $this$head$iv = rem /* !! */ ;
                                var16_23 /* !! */  = CollectionsKt.emptyList();
                                var15_22 = litToken;
                                var14_21 = ParseType.CASE_SENSITIVE_ATOM;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v19 = Collections.emptyList();
                                        v20 /* !! */  = v19;
                                        Intrinsics.checkExpressionValueIsNotNull(v19, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v20 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                var29_36 /* !! */  = var17_24 /* !! */  = v20 /* !! */ ;
                                var30_37 /* !! */  = var16_23 /* !! */ ;
                                var31_38 = var15_22;
                                var32_39 = var14_21;
                                v15 = new ParseNode((ParseType)var32_39, (Token)var31_38, (List<ParseNode>)var30_37 /* !! */ , (List<Token>)var29_36 /* !! */ );
                                break;
                            }
                            case 3: {
                                if (pathMode != PathMode.FULL_PATH) {
                                    v21 = TokenListExtensionsKt.err$default(rem /* !! */ , "Invalid path dot component for simple path", ErrorCode.PARSE_INVALID_PATH_COMPONENT, null, 4, null);
                                    throw null;
                                }
                                litToken = rem /* !! */ ;
                                var14_21 = ParseType.PATH_UNPIVOT;
                                $i$f$getHead = false;
                                var15_22 = CollectionsKt.firstOrNull($this$head$iv);
                                $this$head$iv = rem /* !! */ ;
                                var16_23 /* !! */  = CollectionsKt.emptyList();
                                var15_22 = (Token)var15_22;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v22 = Collections.emptyList();
                                        v23 /* !! */  = v22;
                                        Intrinsics.checkExpressionValueIsNotNull(v22, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v23 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                var33_40 /* !! */  = var17_24 /* !! */  = v23 /* !! */ ;
                                var34_41 /* !! */  = var16_23 /* !! */ ;
                                var35_42 = var15_22;
                                var36_43 = var14_21;
                                v15 = new ParseNode((ParseType)var36_43, (Token)var35_42, (List<ParseNode>)var34_41 /* !! */ , (List<Token>)var33_40 /* !! */ );
                                break;
                            }
                            default: lbl-1000:
                            // 2 sources

                            {
                                v24 = TokenListExtensionsKt.err$default(rem /* !! */ , "Invalid path dot component", ErrorCode.PARSE_INVALID_PATH_COMPONENT, null, 4, null);
                                throw null;
                            }
                        }
                        pathPart = v15;
                        path.add(new ParseNode(ParseType.PATH_DOT, dotToken, CollectionsKt.listOf(pathPart), rem /* !! */ ));
                        $this$tail$iv = rem /* !! */ ;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v25 = Collections.emptyList();
                                v26 /* !! */  = v25;
                                Intrinsics.checkExpressionValueIsNotNull(v25, "emptyList()");
                                break;
                            }
                            default: {
                                v26 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        rem /* !! */  = v26 /* !! */ ;
                        continue block46;
                    }
                    case 2: {
                        $this$head$iv = rem /* !! */ ;
                        $i$f$getHead = false;
                        if (CollectionsKt.firstOrNull($this$head$iv) == null) {
                            Intrinsics.throwNpe();
                        }
                        $this$tail$iv = rem /* !! */ ;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v27 = Collections.emptyList();
                                v28 /* !! */  = v27;
                                Intrinsics.checkExpressionValueIsNotNull(v27, "emptyList()");
                                break;
                            }
                            default: {
                                v28 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        rem /* !! */  = v28 /* !! */ ;
                        $this$head$iv = rem /* !! */ ;
                        $i$f$getHead = false;
                        v29 = CollectionsKt.firstOrNull($this$head$iv);
                        v30 = v29 != null ? v29.getType() : null;
                        if (v30 == null) ** GOTO lbl-1000
                        switch (SqlParser$WhenMappings.$EnumSwitchMapping$20[v30.ordinal()]) {
                            case 1: {
                                $this$head$iv = rem /* !! */ ;
                                var14_21 = ParseType.PATH_WILDCARD;
                                $i$f$getHead = false;
                                var15_22 = CollectionsKt.firstOrNull($this$head$iv);
                                $this$head$iv = rem /* !! */ ;
                                var16_23 /* !! */  = CollectionsKt.emptyList();
                                var15_22 = (Token)var15_22;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v31 = Collections.emptyList();
                                        v32 /* !! */  = v31;
                                        Intrinsics.checkExpressionValueIsNotNull(v31, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v32 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                var37_44 /* !! */  = var17_24 /* !! */  = v32 /* !! */ ;
                                var38_45 /* !! */  = var16_23 /* !! */ ;
                                var39_46 = var15_22;
                                var40_47 = var14_21;
                                v33 = new ParseNode((ParseType)var40_47, (Token)var39_46, (List<ParseNode>)var38_45 /* !! */ , (List<Token>)var37_44 /* !! */ );
                                break;
                            }
                            default: lbl-1000:
                            // 2 sources

                            {
                                v33 = SqlParser.parseExpression$lang$default(this, rem /* !! */ , 0, 1, null);
                            }
                        }
                        expr = v33.deriveExpected(TokenType.RIGHT_BRACKET);
                        if (pathMode == PathMode.SIMPLE_PATH && expr.getType() != ParseType.ATOM) {
                            v34 = expr.getToken();
                            if ((v34 != null ? v34.getType() : null) != TokenType.LITERAL) {
                                v35 = TokenListExtensionsKt.err$default(rem /* !! */ , "Invalid path component for simple path", ErrorCode.PARSE_INVALID_PATH_COMPONENT, null, 4, null);
                                throw null;
                            }
                        }
                        $this$tail$iv = rem /* !! */ ;
                        var17_24 /* !! */  = CollectionsKt.listOf(expr);
                        var16_23 /* !! */  = leftBracketToken;
                        var15_22 = ParseType.PATH_SQB;
                        var12_20 = path;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v36 = Collections.emptyList();
                                v37 = v36;
                                Intrinsics.checkExpressionValueIsNotNull(v36, "emptyList()");
                                break;
                            }
                            default: {
                                v37 = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        var41_48 = var18_25 = v37;
                        var42_49 /* !! */  = var17_24 /* !! */ ;
                        var43_50 /* !! */  = var16_23 /* !! */ ;
                        var44_51 = var15_22;
                        var12_20.add(new ParseNode((ParseType)var44_51, (Token)var43_50 /* !! */ , var42_49 /* !! */ , var41_48));
                        rem /* !! */  = expr.getRemaining();
                        continue block46;
                    }
                }
            }
            hasPath = false;
        }
        switch (path.size()) {
            case 1: {
                v38 = term;
                break;
            }
            default: {
                v38 = new ParseNode(ParseType.PATH, null, (List<ParseNode>)path, rem /* !! */ );
            }
        }
        return v38;
    }

    static /* synthetic */ ParseNode parsePathTerm$default(SqlParser sqlParser, List list, PathMode pathMode, int n, Object object) {
        if ((n & 1) != 0) {
            pathMode = PathMode.FULL_PATH;
        }
        return sqlParser.parsePathTerm(list, pathMode);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseTerm(@NotNull List<Token> $this$parseTerm) {
        $this$head$iv = $this$parseTerm;
        $i$f$getHead = false;
        v0 = CollectionsKt.firstOrNull($this$head$iv);
        v1 = v0 != null ? v0.getType() : null;
        if (v1 == null) ** GOTO lbl-1000
        block0 : switch (SqlParser$WhenMappings.$EnumSwitchMapping$28[v1.ordinal()]) {
            case 1: {
                $this$head$iv = $this$parseTerm;
                $i$f$getHead = false;
                v2 = CollectionsKt.firstOrNull($this$head$iv);
                v3 = v2 != null ? v2.getKeywordText() : null;
                if (v3 != null) {
                    $this$head$iv = v3;
                    switch ($this$head$iv.hashCode()) {
                        case 64: {
                            if (!$this$head$iv.equals("@")) break;
                            $this$tail$iv = $this$parseTerm;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v4 = Collections.emptyList();
                                    v5 /* !! */  = v4;
                                    Intrinsics.checkExpressionValueIsNotNull(v4, "emptyList()");
                                    break;
                                }
                                default: {
                                    v5 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            $this$head$iv /* !! */  = v5 /* !! */ ;
                            $i$f$getHead = false;
                            v6 = (Token)CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                            v7 = v6 != null ? v6.getType() : null;
                            if (v7 != null) {
                                switch (SqlParser$WhenMappings.$EnumSwitchMapping$22[v7.ordinal()]) {
                                    case 1: 
                                    case 2: {
                                        $this$head$iv /* !! */  = $this$parseTerm;
                                        var14_15 /* !! */  = ParseType.UNARY;
                                        $i$f$getHead = false;
                                        var15_16 /* !! */  = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                                        $this$head$iv /* !! */  = $this$parseTerm;
                                        $i$f$getTail = false;
                                        switch ($this$tail$iv.size()) {
                                            case 0: 
                                            case 1: {
                                                v8 = Collections.emptyList();
                                                v9 /* !! */  = v8;
                                                Intrinsics.checkExpressionValueIsNotNull(v8, "emptyList()");
                                                break;
                                            }
                                            default: {
                                                v9 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                            }
                                        }
                                        var16_17 /* !! */  = v9 /* !! */ ;
                                        $this$tail$iv = $this$parseTerm;
                                        var16_17 /* !! */  = CollectionsKt.listOf(TokenListExtensionsKt.atomFromHead$default(var16_17 /* !! */ , null, 1, null));
                                        $i$f$getTail = false;
                                        switch ($this$tail$iv.size()) {
                                            case 0: 
                                            case 1: {
                                                v10 = Collections.emptyList();
                                                v11 /* !! */  = v10;
                                                Intrinsics.checkExpressionValueIsNotNull(v10, "emptyList()");
                                                break;
                                            }
                                            default: {
                                                v11 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                            }
                                        }
                                        var17_18 = v11 /* !! */ ;
                                        $this$tail$iv = var17_18;
                                        $i$f$getTail = false;
                                        switch ($this$tail$iv.size()) {
                                            case 0: 
                                            case 1: {
                                                v12 = Collections.emptyList();
                                                v13 /* !! */  = v12;
                                                Intrinsics.checkExpressionValueIsNotNull(v12, "emptyList()");
                                                break;
                                            }
                                            default: {
                                                v13 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                            }
                                        }
                                        var18_19 = var17_18 = v13 /* !! */ ;
                                        var19_20 /* !! */  = var16_17 /* !! */ ;
                                        var20_21 /* !! */  = var15_16 /* !! */ ;
                                        var21_22 /* !! */  = var14_15 /* !! */ ;
                                        v14 = new ParseNode(var21_22 /* !! */ , var20_21 /* !! */ , var19_20 /* !! */ , var18_19);
                                        break block0;
                                    }
                                }
                            }
                            v15 = TokenListExtensionsKt.err$default($this$parseTerm, "Identifier must follow @-operator", ErrorCode.PARSE_MISSING_IDENT_AFTER_AT, null, 4, null);
                            throw null;
                        }
                    }
                }
                v16 = TokenListExtensionsKt.err$default($this$parseTerm, "Unexpected operator", ErrorCode.PARSE_UNEXPECTED_OPERATOR, null, 4, null);
                throw null;
            }
            case 2: {
                $this$head$iv /* !! */  = $this$parseTerm;
                $i$f$getHead = false;
                v17 = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                v18 = $this$head$iv = v17 != null ? v17.getKeywordText() : null;
                if (CollectionsKt.contains((Iterable)LexerConstantsKt.BASE_DML_KEYWORDS, $this$head$iv)) {
                    v14 = this.parseBaseDml($this$parseTerm);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "update")) {
                    $this$head$iv /* !! */  = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v19 = Collections.emptyList();
                            v20 /* !! */  = v19;
                            Intrinsics.checkExpressionValueIsNotNull(v19, "emptyList()");
                            break;
                        }
                        default: {
                            v20 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v20 /* !! */ ;
                    v14 = var12_23.parseUpdate(var13_24);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "delete")) {
                    $this$tail$iv = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v21 = Collections.emptyList();
                            v22 /* !! */  = v21;
                            Intrinsics.checkExpressionValueIsNotNull(v21, "emptyList()");
                            break;
                        }
                        default: {
                            v22 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v22 /* !! */ ;
                    $this$tail$iv = $this$parseTerm;
                    $i$f$getHead = false;
                    v23 /* !! */  = var14_15 /* !! */  = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                    if (v23 /* !! */  == null) {
                        Intrinsics.throwNpe();
                    }
                    v14 = var12_23.parseDelete(var13_24, (Token)v23 /* !! */ );
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "case")) {
                    $this$tail$iv = $this$parseTerm;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v24 = Collections.emptyList();
                            v25 /* !! */  = v24;
                            Intrinsics.checkExpressionValueIsNotNull(v24, "emptyList()");
                            break;
                        }
                        default: {
                            v25 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    $this$head$iv /* !! */  = v25 /* !! */ ;
                    $i$f$getHead = false;
                    v26 = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                    v27 = v26 != null ? v26.getKeywordText() : null;
                    if (v27 != null) {
                        $this$head$iv /* !! */  = v27;
                        switch ($this$head$iv /* !! */ .hashCode()) {
                            case 3648314: {
                                if (!$this$head$iv /* !! */ .equals("when")) break;
                                $i$f$getHead = $this$parseTerm;
                                var12_23 = this;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v28 = Collections.emptyList();
                                        v29 /* !! */  = v28;
                                        Intrinsics.checkExpressionValueIsNotNull(v28, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v29 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                var13_24 = v29 /* !! */ ;
                                v14 = var12_23.parseCase(var13_24, false);
                                break block0;
                            }
                        }
                    }
                    $this$tail$iv = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v30 = Collections.emptyList();
                            v31 /* !! */  = v30;
                            Intrinsics.checkExpressionValueIsNotNull(v30, "emptyList()");
                            break;
                        }
                        default: {
                            v31 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v31 /* !! */ ;
                    v14 = var12_23.parseCase(var13_24, true);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "cast")) {
                    $this$head$iv /* !! */  = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v32 = Collections.emptyList();
                            v33 /* !! */  = v32;
                            Intrinsics.checkExpressionValueIsNotNull(v32, "emptyList()");
                            break;
                        }
                        default: {
                            v33 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v33 /* !! */ ;
                    v14 = var12_23.parseCast(var13_24);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "select")) {
                    $this$tail$iv = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v34 = Collections.emptyList();
                            v35 /* !! */  = v34;
                            Intrinsics.checkExpressionValueIsNotNull(v34, "emptyList()");
                            break;
                        }
                        default: {
                            v35 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v35 /* !! */ ;
                    v14 = var12_23.parseSelect(var13_24);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "create")) {
                    $this$tail$iv = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v36 = Collections.emptyList();
                            v37 /* !! */  = v36;
                            Intrinsics.checkExpressionValueIsNotNull(v36, "emptyList()");
                            break;
                        }
                        default: {
                            v37 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v37 /* !! */ ;
                    v14 = var12_23.parseCreate(var13_24);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "drop")) {
                    $this$tail$iv = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v38 = Collections.emptyList();
                            v39 /* !! */  = v38;
                            Intrinsics.checkExpressionValueIsNotNull(v38, "emptyList()");
                            break;
                        }
                        default: {
                            v39 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v39 /* !! */ ;
                    v14 = var12_23.parseDrop(var13_24);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "pivot")) {
                    $this$tail$iv = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v40 = Collections.emptyList();
                            v41 /* !! */  = v40;
                            Intrinsics.checkExpressionValueIsNotNull(v40, "emptyList()");
                            break;
                        }
                        default: {
                            v41 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v41 /* !! */ ;
                    v14 = var12_23.parsePivot(var13_24);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "from")) {
                    $this$tail$iv = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v42 = Collections.emptyList();
                            v43 /* !! */  = v42;
                            Intrinsics.checkExpressionValueIsNotNull(v42, "emptyList()");
                            break;
                        }
                        default: {
                            v43 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v43 /* !! */ ;
                    v14 = var12_23.parseFrom(var13_24);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "values")) {
                    $this$tail$iv = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v44 = Collections.emptyList();
                            v45 /* !! */  = v44;
                            Intrinsics.checkExpressionValueIsNotNull(v44, "emptyList()");
                            break;
                        }
                        default: {
                            v45 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v45 /* !! */ ;
                    v14 = ParseNode.copy$default(var12_23.parseTableValues(var13_24), ParseType.BAG, null, null, null, 14, null);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "substring")) {
                    $this$tail$iv = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v46 = Collections.emptyList();
                            v47 /* !! */  = v46;
                            Intrinsics.checkExpressionValueIsNotNull(v46, "emptyList()");
                            break;
                        }
                        default: {
                            v47 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v47 /* !! */ ;
                    $this$tail$iv = $this$parseTerm;
                    $i$f$getHead = false;
                    v48 /* !! */  = var14_15 /* !! */  = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                    if (v48 /* !! */  == null) {
                        Intrinsics.throwNpe();
                    }
                    v14 = var12_23.parseSubstring(var13_24, (Token)v48 /* !! */ );
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "trim")) {
                    $this$head$iv /* !! */  = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v49 = Collections.emptyList();
                            v50 /* !! */  = v49;
                            Intrinsics.checkExpressionValueIsNotNull(v49, "emptyList()");
                            break;
                        }
                        default: {
                            v50 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v50 /* !! */ ;
                    $this$tail$iv = $this$parseTerm;
                    $i$f$getHead = false;
                    v51 /* !! */  = var14_15 /* !! */  = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                    if (v51 /* !! */  == null) {
                        Intrinsics.throwNpe();
                    }
                    v14 = var12_23.parseTrim(var13_24, (Token)v51 /* !! */ );
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "extract")) {
                    $this$head$iv /* !! */  = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v52 = Collections.emptyList();
                            v53 /* !! */  = v52;
                            Intrinsics.checkExpressionValueIsNotNull(v52, "emptyList()");
                            break;
                        }
                        default: {
                            v53 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v53 /* !! */ ;
                    $this$tail$iv = $this$parseTerm;
                    $i$f$getHead = false;
                    v54 /* !! */  = var14_15 /* !! */  = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                    if (v54 /* !! */  == null) {
                        Intrinsics.throwNpe();
                    }
                    v14 = var12_23.parseExtract(var13_24, (Token)v54 /* !! */ );
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "date_add") || Intrinsics.areEqual($this$head$iv, "date_diff")) {
                    $this$head$iv /* !! */  = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v55 = Collections.emptyList();
                            v56 /* !! */  = v55;
                            Intrinsics.checkExpressionValueIsNotNull(v55, "emptyList()");
                            break;
                        }
                        default: {
                            v56 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v56 /* !! */ ;
                    $this$tail$iv = $this$parseTerm;
                    $i$f$getHead = false;
                    v57 /* !! */  = var14_15 /* !! */  = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                    if (v57 /* !! */  == null) {
                        Intrinsics.throwNpe();
                    }
                    v14 = var12_23.parseDateAddOrDateDiff(var13_24, (Token)v57 /* !! */ );
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "date")) {
                    $this$head$iv /* !! */  = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v58 = Collections.emptyList();
                            v59 /* !! */  = v58;
                            Intrinsics.checkExpressionValueIsNotNull(v58, "emptyList()");
                            break;
                        }
                        default: {
                            v59 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v59 /* !! */ ;
                    v14 = var12_23.parseDate(var13_24);
                    break;
                }
                if (Intrinsics.areEqual($this$head$iv, "time")) {
                    $this$tail$iv = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v60 = Collections.emptyList();
                            v61 /* !! */  = v60;
                            Intrinsics.checkExpressionValueIsNotNull(v60, "emptyList()");
                            break;
                        }
                        default: {
                            v61 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v61 /* !! */ ;
                    v14 = var12_23.parseTime(var13_24);
                    break;
                }
                if (CollectionsKt.contains((Iterable)LexerConstantsKt.FUNCTION_NAME_KEYWORDS, $this$head$iv)) {
                    $this$tail$iv = $this$parseTerm;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v62 = Collections.emptyList();
                            v63 /* !! */  = v62;
                            Intrinsics.checkExpressionValueIsNotNull(v62, "emptyList()");
                            break;
                        }
                        default: {
                            v63 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    $this$head$iv /* !! */  = v63 /* !! */ ;
                    $i$f$getHead = false;
                    v64 = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                    v65 = v64 != null ? v64.getType() : null;
                    if (v65 != null) {
                        switch (SqlParser$WhenMappings.$EnumSwitchMapping$23[v65.ordinal()]) {
                            case 1: {
                                $this$head$iv /* !! */  = $this$parseTerm;
                                var12_23 = this;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v66 = Collections.emptyList();
                                        v67 /* !! */  = v66;
                                        Intrinsics.checkExpressionValueIsNotNull(v66, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v67 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                var13_24 = v67 /* !! */ ;
                                $this$tail$iv = var13_24;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v68 = Collections.emptyList();
                                        v69 /* !! */  = v68;
                                        Intrinsics.checkExpressionValueIsNotNull(v68, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v69 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                var13_24 = v69 /* !! */ ;
                                $this$tail$iv = $this$parseTerm;
                                $i$f$getHead = false;
                                v70 /* !! */  = var14_15 /* !! */  = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                                if (v70 /* !! */  == null) {
                                    Intrinsics.throwNpe();
                                }
                                v14 = var12_23.parseFunctionCall(var13_24, (Token)v70 /* !! */ );
                                break block0;
                            }
                        }
                    }
                    v71 = TokenListExtensionsKt.err$default($this$parseTerm, "Unexpected keyword", ErrorCode.PARSE_UNEXPECTED_KEYWORD, null, 4, null);
                    throw null;
                }
                if (Intrinsics.areEqual($this$head$iv, "exec")) {
                    $this$head$iv /* !! */  = $this$parseTerm;
                    var12_23 = this;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v72 = Collections.emptyList();
                            v73 /* !! */  = v72;
                            Intrinsics.checkExpressionValueIsNotNull(v72, "emptyList()");
                            break;
                        }
                        default: {
                            v73 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var13_24 = v73 /* !! */ ;
                    v14 = var12_23.parseExec(var13_24);
                    break;
                }
                v74 = TokenListExtensionsKt.err$default($this$parseTerm, "Unexpected keyword", ErrorCode.PARSE_UNEXPECTED_KEYWORD, null, 4, null);
                throw null;
            }
            case 3: {
                $this$tail$iv = $this$parseTerm;
                var12_23 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v75 = Collections.emptyList();
                        v76 = v75;
                        Intrinsics.checkExpressionValueIsNotNull(v75, "emptyList()");
                        break;
                    }
                    default: {
                        v76 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var13_24 = v76;
                group = SqlParser.parseArgList$default(var12_23, var13_24, AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null).deriveExpected(TokenType.RIGHT_PAREN);
                switch (group.getChildren().size()) {
                    case 0: {
                        $this$tail$iv = $this$parseTerm;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v77 = Collections.emptyList();
                                v78 /* !! */  = v77;
                                Intrinsics.checkExpressionValueIsNotNull(v77, "emptyList()");
                                break;
                            }
                            default: {
                                v78 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        v79 = TokenListExtensionsKt.err$default(v78 /* !! */ , "Expression group cannot be empty", ErrorCode.PARSE_EXPECTED_EXPRESSION, null, 4, null);
                        throw null;
                    }
                    case 1: {
                        v14 = ParseNode.copy$default(group.getChildren().get(0), null, null, null, group.getRemaining(), 7, null);
                        break block0;
                    }
                }
                v14 = ParseNode.copy$default(group, ParseType.LIST, null, null, null, 14, null);
                break;
            }
            case 4: {
                $this$tail$iv = $this$parseTerm;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v80 = Collections.emptyList();
                        v81 /* !! */  = v80;
                        Intrinsics.checkExpressionValueIsNotNull(v80, "emptyList()");
                        break;
                    }
                    default: {
                        v81 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                $this$head$iv = v81 /* !! */ ;
                $i$f$getHead = false;
                v82 = (Token)CollectionsKt.firstOrNull($this$head$iv);
                v83 = v82 != null ? v82.getType() : null;
                if (v83 != null) {
                    switch (SqlParser$WhenMappings.$EnumSwitchMapping$24[v83.ordinal()]) {
                        case 1: {
                            $this$head$iv = $this$parseTerm;
                            var16_17 /* !! */  = CollectionsKt.emptyList();
                            var15_16 /* !! */  = null;
                            var14_15 /* !! */  = ParseType.LIST;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v84 = Collections.emptyList();
                                    v85 /* !! */  = v84;
                                    Intrinsics.checkExpressionValueIsNotNull(v84, "emptyList()");
                                    break;
                                }
                                default: {
                                    v85 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var17_18 = v85 /* !! */ ;
                            $this$tail$iv = var17_18;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v86 = Collections.emptyList();
                                    v87 /* !! */  = v86;
                                    Intrinsics.checkExpressionValueIsNotNull(v86, "emptyList()");
                                    break;
                                }
                                default: {
                                    v87 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var17_18 = v87 /* !! */ ;
                            var22_27 = var17_18;
                            var23_28 = var16_17 /* !! */ ;
                            var24_29 /* !! */  = var15_16 /* !! */ ;
                            var25_30 /* !! */  = var14_15 /* !! */ ;
                            v14 = new ParseNode(var25_30 /* !! */ , var24_29 /* !! */ , var23_28, var22_27);
                            break block0;
                        }
                    }
                }
                $this$tail$iv = $this$parseTerm;
                var12_23 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v88 = Collections.emptyList();
                        v89 /* !! */  = v88;
                        Intrinsics.checkExpressionValueIsNotNull(v88, "emptyList()");
                        break;
                    }
                    default: {
                        v89 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var13_24 = v89 /* !! */ ;
                v14 = var12_23.parseListLiteral(var13_24);
                break;
            }
            case 5: {
                $this$tail$iv = $this$parseTerm;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v90 = Collections.emptyList();
                        v91 /* !! */  = v90;
                        Intrinsics.checkExpressionValueIsNotNull(v90, "emptyList()");
                        break;
                    }
                    default: {
                        v91 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                $this$head$iv = v91 /* !! */ ;
                $i$f$getHead = false;
                v92 = (Token)CollectionsKt.firstOrNull($this$head$iv);
                v93 = v92 != null ? v92.getType() : null;
                if (v93 != null) {
                    switch (SqlParser$WhenMappings.$EnumSwitchMapping$25[v93.ordinal()]) {
                        case 1: {
                            $this$head$iv = $this$parseTerm;
                            var16_17 /* !! */  = CollectionsKt.emptyList();
                            var15_16 /* !! */  = null;
                            var14_15 /* !! */  = ParseType.BAG;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v94 = Collections.emptyList();
                                    v95 /* !! */  = v94;
                                    Intrinsics.checkExpressionValueIsNotNull(v94, "emptyList()");
                                    break;
                                }
                                default: {
                                    v95 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var17_18 = v95 /* !! */ ;
                            $this$tail$iv = var17_18;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v96 = Collections.emptyList();
                                    v97 /* !! */  = v96;
                                    Intrinsics.checkExpressionValueIsNotNull(v96, "emptyList()");
                                    break;
                                }
                                default: {
                                    v97 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var17_18 = v97 /* !! */ ;
                            var26_31 = var17_18;
                            var27_32 = var16_17 /* !! */ ;
                            var28_33 /* !! */  = var15_16 /* !! */ ;
                            var29_34 /* !! */  = var14_15 /* !! */ ;
                            v14 = new ParseNode(var29_34 /* !! */ , var28_33 /* !! */ , var27_32, var26_31);
                            break block0;
                        }
                    }
                }
                $this$tail$iv = $this$parseTerm;
                var12_23 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v98 = Collections.emptyList();
                        v99 /* !! */  = v98;
                        Intrinsics.checkExpressionValueIsNotNull(v98, "emptyList()");
                        break;
                    }
                    default: {
                        v99 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var13_24 = v99 /* !! */ ;
                v14 = var12_23.parseBagLiteral(var13_24);
                break;
            }
            case 6: {
                $this$tail$iv = $this$parseTerm;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v100 = Collections.emptyList();
                        v101 /* !! */  = v100;
                        Intrinsics.checkExpressionValueIsNotNull(v100, "emptyList()");
                        break;
                    }
                    default: {
                        v101 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                $this$head$iv = v101 /* !! */ ;
                $i$f$getHead = false;
                v102 = (Token)CollectionsKt.firstOrNull($this$head$iv);
                v103 = v102 != null ? v102.getType() : null;
                if (v103 != null) {
                    switch (SqlParser$WhenMappings.$EnumSwitchMapping$26[v103.ordinal()]) {
                        case 1: {
                            $this$head$iv = $this$parseTerm;
                            var16_17 /* !! */  = CollectionsKt.emptyList();
                            var15_16 /* !! */  = null;
                            var14_15 /* !! */  = ParseType.STRUCT;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v104 = Collections.emptyList();
                                    v105 /* !! */  = v104;
                                    Intrinsics.checkExpressionValueIsNotNull(v104, "emptyList()");
                                    break;
                                }
                                default: {
                                    v105 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var17_18 = v105 /* !! */ ;
                            $this$tail$iv = var17_18;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v106 = Collections.emptyList();
                                    v107 /* !! */  = v106;
                                    Intrinsics.checkExpressionValueIsNotNull(v106, "emptyList()");
                                    break;
                                }
                                default: {
                                    v107 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var17_18 = v107 /* !! */ ;
                            var30_35 = var17_18;
                            var31_36 = var16_17 /* !! */ ;
                            var32_37 /* !! */  = var15_16 /* !! */ ;
                            var33_38 /* !! */  = var14_15 /* !! */ ;
                            v14 = new ParseNode(var33_38 /* !! */ , var32_37 /* !! */ , var31_36, var30_35);
                            break block0;
                        }
                    }
                }
                $this$tail$iv = $this$parseTerm;
                var12_23 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v108 = Collections.emptyList();
                        v109 /* !! */  = v108;
                        Intrinsics.checkExpressionValueIsNotNull(v108, "emptyList()");
                        break;
                    }
                    default: {
                        v109 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var13_24 = v109 /* !! */ ;
                v14 = var12_23.parseStructLiteral(var13_24);
                break;
            }
            case 7: 
            case 8: {
                $this$tail$iv = $this$parseTerm;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v110 = Collections.emptyList();
                        v111 /* !! */  = v110;
                        Intrinsics.checkExpressionValueIsNotNull(v110, "emptyList()");
                        break;
                    }
                    default: {
                        v111 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                $this$head$iv = v111 /* !! */ ;
                $i$f$getHead = false;
                v112 = (Token)CollectionsKt.firstOrNull($this$head$iv);
                v113 = v112 != null ? v112.getType() : null;
                if (v113 != null) {
                    switch (SqlParser$WhenMappings.$EnumSwitchMapping$27[v113.ordinal()]) {
                        case 1: {
                            $this$head$iv = $this$parseTerm;
                            var12_23 = this;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v114 = Collections.emptyList();
                                    v115 /* !! */  = v114;
                                    Intrinsics.checkExpressionValueIsNotNull(v114, "emptyList()");
                                    break;
                                }
                                default: {
                                    v115 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var13_24 = v115 /* !! */ ;
                            $this$tail$iv = var13_24;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v116 = Collections.emptyList();
                                    v117 /* !! */  = v116;
                                    Intrinsics.checkExpressionValueIsNotNull(v116, "emptyList()");
                                    break;
                                }
                                default: {
                                    v117 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var13_24 = v117 /* !! */ ;
                            $this$tail$iv = $this$parseTerm;
                            $i$f$getHead = false;
                            v118 /* !! */  = var14_15 /* !! */  = CollectionsKt.firstOrNull($this$head$iv);
                            if (v118 /* !! */  == null) {
                                Intrinsics.throwNpe();
                            }
                            v14 = var12_23.parseFunctionCall(var13_24, (Token)v118 /* !! */ );
                            break block0;
                        }
                    }
                }
                v14 = TokenListExtensionsKt.atomFromHead$default($this$parseTerm, null, 1, null);
                break;
            }
            case 9: {
                $this$head$iv = $this$parseTerm;
                var14_15 /* !! */  = ParseType.PARAMETER;
                $i$f$getHead = false;
                v119 = var15_16 /* !! */  = CollectionsKt.firstOrNull($this$head$iv);
                if (v119 == null) {
                    Intrinsics.throwNpe();
                }
                var15_16 /* !! */  = v119;
                $this$head$iv = false;
                var16_17 /* !! */  = CollectionsKt.emptyList();
                $this$head$iv = $this$parseTerm;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v120 = Collections.emptyList();
                        v121 = v120;
                        Intrinsics.checkExpressionValueIsNotNull(v120, "emptyList()");
                        break;
                    }
                    default: {
                        v121 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var17_18 = v121;
                var34_39 = var17_18;
                var35_40 = var16_17 /* !! */ ;
                var36_41 = var15_16 /* !! */ ;
                var37_42 = var14_15 /* !! */ ;
                v14 = new ParseNode(var37_42, var36_41, var35_40, var34_39);
                break;
            }
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: {
                v14 = TokenListExtensionsKt.atomFromHead$default($this$parseTerm, null, 1, null);
                break;
            }
            default: lbl-1000:
            // 2 sources

            {
                v122 = TokenListExtensionsKt.err$default($this$parseTerm, "Unexpected term", ErrorCode.PARSE_UNEXPECTED_TERM, null, 4, null);
                throw null;
            }
        }
        var2_2 = v14;
        var3_8 = false;
        var4_14 = false;
        parseNode = var2_2;
        $i$a$-let-SqlParser$parseTerm$1 = false;
        if (parseNode.getToken() == null) {
            var7_44 = $this$parseTerm;
            var8_45 = null;
            var9_46 = parseNode;
            $i$f$getHead = false;
            var11_48 = CollectionsKt.firstOrNull($this$head$iv);
            v123 = ParseNode.copy$default(var9_46, var8_45, (Token)var11_48, null, null, 13, null);
        } else {
            v123 = parseNode;
        }
        return v123;
    }

    private final ParseNode parseCase(@NotNull List<Token> $this$parseCase, boolean isSimple) {
        List<Token> rem = $this$parseCase;
        ArrayList<ParseNode> children = new ArrayList<ParseNode>();
        if (isSimple) {
            ParseNode valueExpr = SqlParser.parseExpression$lang$default(this, $this$parseCase, 0, 1, null);
            children.add(valueExpr);
            rem = valueExpr.getRemaining();
        }
        ParseNode caseBody = this.parseCaseBody(rem);
        children.add(caseBody);
        rem = caseBody.getRemaining();
        return new ParseNode(ParseType.CASE, null, (List<ParseNode>)children, rem);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseCaseBody(@NotNull List<Token> $this$parseCaseBody) {
        List<Object> list;
        boolean $i$f$getTail;
        SqlParser sqlParser;
        boolean $i$f$getHead22;
        List<Token> $this$head$iv;
        ArrayList<ParseNode> children = new ArrayList<ParseNode>();
        List<Token> rem = $this$parseCaseBody;
        while (true) {
            List<Object> list2;
            void $this$tail$iv;
            $this$head$iv = rem;
            $i$f$getHead22 = false;
            Token token = CollectionsKt.firstOrNull($this$head$iv);
            if (!Intrinsics.areEqual(token != null ? token.getKeywordText() : null, "when")) break;
            List<Token> $i$f$getHead22 = rem;
            sqlParser = this;
            $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    List list3 = Collections.emptyList();
                    list2 = list3;
                    Intrinsics.checkExpressionValueIsNotNull(list3, "emptyList()");
                    break;
                }
                default: {
                    list2 = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            list = list2;
            ParseNode conditionExpr = SqlParser.parseExpression$lang$default(sqlParser, list, 0, 1, null).deriveExpectedKeyword("then");
            rem = conditionExpr.getRemaining();
            ParseNode result = SqlParser.parseExpression$lang$default(this, rem, 0, 1, null);
            rem = result.getRemaining();
            children.add(new ParseNode(ParseType.WHEN, null, CollectionsKt.listOf(conditionExpr, result), rem));
        }
        if (children.isEmpty()) {
            Void void_ = TokenListExtensionsKt.err$default($this$parseCaseBody, "Expected a WHEN clause in CASE", ErrorCode.PARSE_EXPECTED_WHEN_CLAUSE, null, 4, null);
            throw null;
        }
        $this$head$iv = rem;
        $i$f$getHead22 = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        if (Intrinsics.areEqual(token != null ? token.getKeywordText() : null, "else")) {
            List<Object> list4;
            void $this$tail$iv;
            List<Token> $i$f$getHead = rem;
            sqlParser = this;
            $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    List list5 = Collections.emptyList();
                    list4 = list5;
                    Intrinsics.checkExpressionValueIsNotNull(list5, "emptyList()");
                    break;
                }
                default: {
                    list4 = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            list = list4;
            ParseNode elseExpr = SqlParser.parseExpression$lang$default(sqlParser, list, 0, 1, null);
            rem = elseExpr.getRemaining();
            children.add(new ParseNode(ParseType.ELSE, null, CollectionsKt.listOf(elseExpr), rem));
        }
        return new ParseNode(ParseType.ARG_LIST, null, (List<ParseNode>)children, rem).deriveExpectedKeyword("end");
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseCast(@NotNull List<Token> $this$parseCast) {
        void $this$head$iv;
        List<Object> list;
        void $this$tail$iv;
        List<Token> $this$head$iv2 = $this$parseCast;
        boolean $i$f$getHead22 = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv2);
        if ((token != null ? token.getType() : null) != TokenType.LEFT_PAREN) {
            Void void_ = TokenListExtensionsKt.err$default($this$parseCast, "Missing left parenthesis after CAST", ErrorCode.PARSE_EXPECTED_LEFT_PAREN_AFTER_CAST, null, 4, null);
            throw null;
        }
        List<Token> $i$f$getHead22 = $this$parseCast;
        SqlParser sqlParser = this;
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
        List<Object> list3 = list;
        ParseNode valueExpr = SqlParser.parseExpression$lang$default(sqlParser, list3, 0, 1, null).deriveExpected(TokenType.AS);
        List<Token> rem = valueExpr.getRemaining();
        ParseNode typeNode = this.parseType(rem).deriveExpected(TokenType.RIGHT_PAREN);
        rem = typeNode.getRemaining();
        List<Token> list4 = $this$parseCast;
        ParseType parseType = ParseType.CAST;
        boolean $i$f$getHead = false;
        Object t = CollectionsKt.firstOrNull($this$head$iv);
        List<Token> list5 = rem;
        List<ParseNode> list6 = CollectionsKt.listOf(valueExpr, typeNode);
        Token token2 = (Token)t;
        ParseType parseType2 = parseType;
        return new ParseNode(parseType2, token2, list6, list5);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseType(@NotNull List<Token> $this$parseType) {
        $this$head$iv = $this$parseType;
        $i$f$getHead = false;
        v0 = CollectionsKt.firstOrNull($this$head$iv);
        typeName = v0 != null ? v0.getKeywordText() : null;
        $i$f$getHead = LexerConstantsKt.TYPE_NAME_ARITY_MAP;
        var5_6 = false;
        v1 = $i$f$getHead.get(typeName);
        if (v1 == null) {
            v2 = TokenListExtensionsKt.err$default($this$parseType, "Expected type name", ErrorCode.PARSE_EXPECTED_TYPE_NAME, null, 4, null);
            throw null;
        }
        typeArity = v1;
        $this$tail$iv = $this$parseType;
        $i$f$getTail = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                v3 = Collections.emptyList();
                v4 /* !! */  = v3;
                Intrinsics.checkExpressionValueIsNotNull(v3, "emptyList()");
                break;
            }
            default: {
                v4 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        $this$head$iv /* !! */  = v4 /* !! */ ;
        $i$f$getHead = false;
        v5 = (Token)CollectionsKt.firstOrNull($this$head$iv /* !! */ );
        v6 = v5 != null ? v5.getType() : null;
        if (v6 == null) ** GOTO lbl-1000
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$29[v6.ordinal()]) {
            case 1: {
                $this$head$iv /* !! */  = $this$parseType;
                var15_11 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v7 = Collections.emptyList();
                        v8 /* !! */  = v7;
                        Intrinsics.checkExpressionValueIsNotNull(v7, "emptyList()");
                        break;
                    }
                    default: {
                        v8 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var16_12 /* !! */  = v8 /* !! */ ;
                $this$tail$iv = var16_12 /* !! */ ;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v9 = Collections.emptyList();
                        v10 /* !! */  = v9;
                        Intrinsics.checkExpressionValueIsNotNull(v9, "emptyList()");
                        break;
                    }
                    default: {
                        v10 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var16_12 /* !! */  = v10 /* !! */ ;
                v11 = SqlParser.parseArgList$default((SqlParser)var15_11, var16_12 /* !! */ , AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null);
                $this$tail$iv = $this$parseType;
                var16_12 /* !! */  = ParseType.TYPE;
                var15_11 = v11;
                $i$f$getHead = false;
                var17_13 /* !! */  = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                v12 = ParseNode.copy$default((ParseNode)var15_11, var16_12 /* !! */ , (Token)var17_13 /* !! */ , null, null, 12, null).deriveExpected(TokenType.RIGHT_PAREN);
                break;
            }
            default: lbl-1000:
            // 2 sources

            {
                $this$head$iv /* !! */  = $this$parseType;
                var17_13 /* !! */  = ParseType.TYPE;
                $i$f$getHead = false;
                var18_14 /* !! */  = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                $this$head$iv /* !! */  = $this$parseType;
                var19_15 = CollectionsKt.emptyList();
                var18_14 /* !! */  = (Token)var18_14 /* !! */ ;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v13 = Collections.emptyList();
                        v14 = v13;
                        Intrinsics.checkExpressionValueIsNotNull(v13, "emptyList()");
                        break;
                    }
                    default: {
                        v14 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var21_17 = var20_16 = v14;
                var22_18 = var19_15;
                var23_19 /* !! */  = var18_14 /* !! */ ;
                var24_20 = var17_13 /* !! */ ;
                v12 = new ParseNode(var24_20, (Token)var23_19 /* !! */ , var22_18, var21_17);
            }
        }
        $this$tail$iv = v12;
        $i$f$getTail = false;
        var7_21 = false;
        it = $this$tail$iv;
        $i$a$-let-SqlParser$parseType$typeNode$1 = false;
        if (Intrinsics.areEqual(typeName, "time")) {
            v15 = CollectionsKt.firstOrNull(it.getChildren());
            if (v15 != null) {
                var10_24 = v15;
                var11_25 = false;
                var12_26 = false;
                precision = var10_24;
                $i$a$-also-SqlParser$parseType$typeNode$1$1 = false;
                v16 = precision.getToken();
                if ((v16 != null ? v16.getValue() : null) == null || !IonValueExtensionsKt.isUnsignedInteger(precision.getToken().getValue()) || IonValueExtensionsKt.longValue(precision.getToken().getValue()) < 0L || IonValueExtensionsKt.longValue(precision.getToken().getValue()) > (long)9) {
                    v17 = TokenListExtensionsKt.err$default(precision.getToken(), "Expected integer value between 0 and 9 for precision", ErrorCode.PARSE_INVALID_PRECISION_FOR_TIME, null, 4, null);
                    throw null;
                }
            }
            var12_27 = this.checkForOptionalTimeZone(it.getRemaining());
            var10_24 = var12_27.component1();
            isTimeZoneSpecified = var12_27.component2();
            if (isTimeZoneSpecified) {
                v18 = it.getToken();
                if (v18 == null) {
                    Intrinsics.throwNpe();
                }
                v19 = Token.copy$default(v18, null, this.ion.singleValue(SqlDataType.TIME_WITH_TIME_ZONE.getTypeName()), null, 5, null);
            } else {
                v19 = it.getToken();
            }
            newToken = v19;
            v20 = ParseNode.copy$default((ParseNode)it, null, newToken, null, (List)remainingAfterOptionalTimeZone, 5, null);
        } else {
            v20 = it;
        }
        if (!typeArity.contains((typeNode = v20).getChildren().size())) {
            pvmap = new PropertyValueMap(null, 1, null);
            v21 = typeName;
            if (v21 == null) {
                v21 = "";
            }
            pvmap.set(Property.CAST_TO, v21);
            pvmap.set(Property.EXPECTED_ARITY_MIN, typeArity.getFirst());
            pvmap.set(Property.EXPECTED_ARITY_MAX, typeArity.getLast());
            $this$tail$iv = $this$parseType;
            $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    v22 = Collections.emptyList();
                    v23 = v22;
                    Intrinsics.checkExpressionValueIsNotNull(v22, "emptyList()");
                    break;
                }
                default: {
                    v23 = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            v24 = TokenListExtensionsKt.err(v23, "CAST for " + typeName + " must have arity of " + typeArity, ErrorCode.PARSE_CAST_ARITY, pvmap);
            throw null;
        }
        for (ParseNode child : typeNode.getChildren()) {
            if (child.getType() == ParseType.ATOM) {
                v25 = child.getToken();
                if ((v25 != null ? v25.getType() : null) == TokenType.LITERAL) {
                    v26 = child.getToken().getValue();
                    if (v26 != null && IonValueExtensionsKt.isUnsignedInteger(v26)) continue;
                }
            }
            v27 = TokenListExtensionsKt.err$default($this$parseType, "Type parameter must be an unsigned integer literal", ErrorCode.PARSE_INVALID_TYPE_PARAM, null, 4, null);
            throw null;
        }
        return typeNode;
    }

    private final ParseNode parseFrom(@NotNull List<Token> $this$parseFrom) {
        ParseNode operation;
        ParseNode fromList;
        ArrayList<ParseNode> children;
        List<Token> rem;
        block1: {
            boolean bl;
            rem = $this$parseFrom;
            children = new ArrayList<ParseNode>();
            fromList = SqlParser.parseFromSourceList$default(this, rem, 0, 1, null);
            rem = fromList.getRemaining();
            ParseNode parseNode = this.parseOptionalWhere(rem);
            if (parseNode != null) {
                ParseNode parseNode2 = parseNode;
                boolean bl2 = false;
                bl = false;
                ParseNode it = parseNode2;
                boolean bl3 = false;
                children.add(it);
                rem = it.getRemaining();
            }
            operation = this.parseBaseDmls(rem);
            rem = operation.getRemaining();
            ParseNode parseNode3 = this.parseOptionalReturning(rem);
            if (parseNode3 == null) break block1;
            ParseNode parseNode4 = parseNode3;
            bl = false;
            boolean bl4 = false;
            ParseNode it = parseNode4;
            boolean bl5 = false;
            children.add(it);
            rem = it.getRemaining();
        }
        return new ParseNode(ParseType.FROM, null, CollectionsKt.plus((Collection)CollectionsKt.listOf(operation, fromList), (Iterable)children), rem);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseBaseDmls(@NotNull List<Token> $this$parseBaseDmls) {
        List<Token> rem = $this$parseBaseDmls;
        ArrayList<ParseNode> nodes = new ArrayList<ParseNode>();
        while (true) {
            void $this$head$iv;
            List<Token> list = rem;
            Iterable iterable = LexerConstantsKt.BASE_DML_KEYWORDS;
            boolean $i$f$getHead = false;
            Object t = CollectionsKt.firstOrNull($this$head$iv);
            Token token = (Token)t;
            if (!CollectionsKt.contains(iterable, token != null ? token.getKeywordText() : null)) break;
            ParseNode node = this.parseBaseDml(rem);
            nodes.add(node);
            rem = node.getRemaining();
        }
        if (nodes.size() == 0) {
            Void void_ = TokenListExtensionsKt.err$default($this$parseBaseDmls, "Expected data manipulation", ErrorCode.PARSE_MISSING_OPERATION, null, 4, null);
            throw null;
        }
        if (nodes.size() == 1) {
            Object e = nodes.get(0);
            Intrinsics.checkExpressionValueIsNotNull(e, "nodes[0]");
            return (ParseNode)e;
        }
        return new ParseNode(ParseType.DML_LIST, null, (List<ParseNode>)nodes, rem);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseBaseDml(@NotNull List<Token> $this$parseBaseDml) {
        $this$head$iv = rem = $this$parseBaseDml;
        $i$f$getHead = false;
        v0 = CollectionsKt.firstOrNull($this$head$iv);
        v1 = v0 != null ? v0.getKeywordText() : null;
        if (v1 == null) ** GOTO lbl-1000
        var3_3 = v1;
        tmp = -1;
        switch (var3_3.hashCode()) {
            case -103471066: {
                if (!var3_3.equals("insert_into")) break;
                tmp = 1;
                break;
            }
            case 113762: {
                if (!var3_3.equals("set")) break;
                tmp = 2;
                break;
            }
            case -934610812: {
                if (!var3_3.equals("remove")) break;
                tmp = 3;
                break;
            }
        }
        switch (tmp) {
            case 1: {
                var5_8 = rem;
                var14_11 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v2 = Collections.emptyList();
                        v3 = v2;
                        Intrinsics.checkExpressionValueIsNotNull(v2, "emptyList()");
                        break;
                    }
                    default: {
                        v3 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var15_17 /* !! */  = v3;
                lvalue = var14_11.parsePathTerm(var15_17 /* !! */ , PathMode.SIMPLE_PATH);
                $this$tail$iv = rem = lvalue.getRemaining();
                var14_11 = "value";
                $i$f$getHead = false;
                var15_17 /* !! */  = CollectionsKt.firstOrNull($this$head$iv);
                v4 = (Token)var15_17 /* !! */ ;
                if (!Intrinsics.areEqual(var14_11, v4 != null ? v4.getKeywordText() : null)) ** GOTO lbl115
                $i$f$getHead = rem;
                var14_11 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v5 = Collections.emptyList();
                        v6 /* !! */  = v5;
                        Intrinsics.checkExpressionValueIsNotNull(v5, "emptyList()");
                        break;
                    }
                    default: {
                        v6 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var15_17 /* !! */  = v6 /* !! */ ;
                value = SqlParser.parseExpression$lang$default((SqlParser)var14_11, var15_17 /* !! */ , 0, 1, null);
                rem = value.getRemaining();
                $this$head$iv = rem;
                $i$f$getHead = false;
                v7 = CollectionsKt.firstOrNull($this$head$iv);
                v8 = v7 != null ? v7.getKeywordText() : null;
                if (v8 == null) ** GOTO lbl-1000
                $this$head$iv = v8;
                switch ($this$head$iv.hashCode()) {
                    case 3123: {
                        if ($this$head$iv.equals("at")) {
                            $i$f$getHead = rem;
                            var14_11 = this;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v9 = Collections.emptyList();
                                    v10 /* !! */  = v9;
                                    Intrinsics.checkExpressionValueIsNotNull(v9, "emptyList()");
                                    break;
                                }
                                default: {
                                    v10 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var15_17 /* !! */  = v10 /* !! */ ;
                            $this$tail$iv = SqlParser.parseExpression$lang$default((SqlParser)var14_11, var15_17 /* !! */ , 0, 1, null);
                            var9_26 = false;
                            var10_28 = false;
                            it = $this$tail$iv;
                            $i$a$-also-SqlParser$parseBaseDml$position$1 = false;
                            rem = it.getRemaining();
                            v11 = $this$tail$iv;
                            break;
                        }
                    }
                    default: lbl-1000:
                    // 2 sources

                    {
                        v11 = null;
                    }
                }
                position = v11;
                v12 = this.parseOptionalOnConflict(rem);
                if (v12 != null) {
                    $this$tail$iv = v12;
                    var9_26 = false;
                    var10_28 = false;
                    it = $this$tail$iv;
                    $i$a$-also-SqlParser$parseBaseDml$onConflict$1 = false;
                    rem = it.getRemaining();
                    v13 = $this$tail$iv;
                } else {
                    v13 = null;
                }
                onConflict = v13;
                v14 = this.parseOptionalReturning(rem);
                if (v14 != null) {
                    var9_27 = v14;
                    var10_28 = false;
                    var11_30 = false;
                    it = var9_27;
                    $i$a$-also-SqlParser$parseBaseDml$returning$1 = false;
                    rem = it.getRemaining();
                    v15 = var9_27;
                } else {
                    v15 = null;
                }
                returning = v15;
                v16 = new ParseNode(ParseType.INSERT_VALUE, null, CollectionsKt.listOfNotNull(new ParseNode[]{lvalue, value, position, onConflict, returning}), rem);
                break;
lbl115:
                // 1 sources

                values = SqlParser.parseExpression$lang$default(this, rem, 0, 1, null);
                v16 = new ParseNode(ParseType.INSERT, null, CollectionsKt.listOf(new ParseNode[]{lvalue, values}), values.getRemaining());
                break;
            }
            case 2: {
                lvalue = rem;
                var14_12 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v17 = Collections.emptyList();
                        v18 = v17;
                        Intrinsics.checkExpressionValueIsNotNull(v17, "emptyList()");
                        break;
                    }
                    default: {
                        v18 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var15_18 = v18;
                v16 = var14_12.parseSetAssignments(var15_18, ParseType.UPDATE);
                break;
            }
            case 3: {
                $i$f$getTail = rem;
                var14_13 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v19 = Collections.emptyList();
                        v20 = v19;
                        Intrinsics.checkExpressionValueIsNotNull(v19, "emptyList()");
                        break;
                    }
                    default: {
                        v20 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var15_19 = v20;
                lvalue = var14_13.parsePathTerm(var15_19, PathMode.SIMPLE_PATH);
                rem = lvalue.getRemaining();
                v16 = new ParseNode(ParseType.REMOVE, null, CollectionsKt.listOf(lvalue), rem);
                break;
            }
            default: lbl-1000:
            // 2 sources

            {
                v21 = TokenListExtensionsKt.err$default($this$parseBaseDml, "Expected data manipulation", ErrorCode.PARSE_MISSING_OPERATION, null, 4, null);
                throw null;
            }
        }
        return v16;
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseConflictAction(@NotNull List<Token> $this$parseConflictAction, Token token) {
        List<Token> list;
        List<Token> list2;
        void $this$tail$iv;
        List<Token> rem;
        List<Token> list3 = rem = $this$parseConflictAction;
        List<ParseNode> list4 = CollectionsKt.emptyList();
        Token token2 = token;
        ParseType parseType = ParseType.CONFLICT_ACTION;
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
        List<ParseNode> list7 = list4;
        Token token3 = token2;
        ParseType parseType2 = parseType;
        return new ParseNode(parseType2, token3, list7, list6);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private final ParseNode parseOptionalOnConflict(@NotNull List<Token> $this$parseOptionalOnConflict) {
        List<Object> rem;
        List<Object> list;
        void $this$head$iv;
        List<Token> remaining;
        List<Token> list2 = remaining = $this$parseOptionalOnConflict;
        Object object = "on_conflict";
        boolean $i$f$getHead = false;
        Object t = CollectionsKt.firstOrNull($this$head$iv);
        Token token = (Token)t;
        if (!Intrinsics.areEqual(object, token != null ? token.getKeywordText() : null)) {
            return null;
        }
        List<Token> $this$tail$iv = remaining;
        boolean bl = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                List list3 = Collections.emptyList();
                list = list3;
                Intrinsics.checkExpressionValueIsNotNull(list3, "emptyList()");
                break;
            }
            default: {
                list = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        List<Object> $this$head$iv2 = rem = list;
        boolean bl2 = false;
        Token token2 = (Token)CollectionsKt.firstOrNull($this$head$iv2);
        String string = token2 != null ? token2.getKeywordText() : null;
        if (string != null) {
            String string2 = string;
            switch (string2.hashCode()) {
                case 113097959: {
                    List<Token> onConflictRem;
                    List<Object> list4;
                    if (!string2.equals("where")) break;
                    List<Object> $this$tail$iv2 = rem;
                    boolean $i$f$getTail = false;
                    switch ($this$tail$iv2.size()) {
                        case 0: 
                        case 1: {
                            List list5 = Collections.emptyList();
                            list4 = list5;
                            Intrinsics.checkExpressionValueIsNotNull(list5, "emptyList()");
                            break;
                        }
                        default: {
                            list4 = $this$tail$iv2.subList(1, $this$tail$iv2.size());
                        }
                    }
                    List<Object> list6 = list4;
                    ParseNode onConflictExpression = SqlParser.parseExpression$lang$default(this, list6, 0, 1, null);
                    List<Token> $this$head$iv3 = onConflictRem = onConflictExpression.getRemaining();
                    boolean $i$f$getHead2 = false;
                    Token token3 = CollectionsKt.firstOrNull($this$head$iv3);
                    String string3 = token3 != null ? token3.getKeywordText() : null;
                    if (string3 != null) {
                        String string4 = string3;
                        switch (string4.hashCode()) {
                            case -1042882535: {
                                void $this$head$iv4;
                                Object t2;
                                if (!string4.equals("do_nothing")) break;
                                List<Token> list7 = onConflictRem;
                                List<Token> list8 = onConflictRem;
                                object = this;
                                boolean $i$f$getHead3 = false;
                                Object t3 = t2 = CollectionsKt.firstOrNull($this$head$iv4);
                                if (t3 == null) {
                                    Intrinsics.throwNpe();
                                }
                                ParseNode conflictAction = super.parseConflictAction(list8, (Token)t3);
                                List<ParseNode> nodes = CollectionsKt.listOfNotNull(onConflictExpression, conflictAction);
                                ParseNode parseNode = new ParseNode(ParseType.ON_CONFLICT, null, nodes, conflictAction.getRemaining());
                                return parseNode;
                            }
                        }
                    }
                    List<Object> $this$head$iv5 = rem;
                    boolean $i$f$getHead4 = false;
                    Void void_ = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull($this$head$iv5), "invalid ON CONFLICT syntax", ErrorCode.PARSE_EXPECTED_CONFLICT_ACTION, null, 4, null);
                    throw null;
                }
            }
        }
        List<Object> list9 = rem;
        boolean $i$f$getHead5 = false;
        Void void_ = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull(list9), "invalid ON CONFLICT syntax", ErrorCode.PARSE_EXPECTED_WHERE_CLAUSE, null, 4, null);
        throw null;
    }

    private final ParseNode parseSetAssignments(@NotNull List<Token> $this$parseSetAssignments, ParseType type) {
        ParseNode parseNode = SqlParser.parseArgList$default(this, $this$parseSetAssignments, AliasSupportType.NONE, ArgListMode.SET_CLAUSE_ARG_LIST, 0, 4, null);
        boolean bl = false;
        boolean bl2 = false;
        ParseNode $this$run = parseNode;
        boolean bl3 = false;
        if ($this$run.getChildren().isEmpty()) {
            Void void_ = TokenListExtensionsKt.err$default($this$run.getRemaining(), "Expected assignment for SET", ErrorCode.PARSE_MISSING_SET_ASSIGNMENT, null, 4, null);
            throw null;
        }
        return ParseNode.copy$default($this$run, type, null, null, null, 14, null);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseDelete(@NotNull List<Token> $this$parseDelete, Token name) {
        ParseNode operation$iv;
        ArrayList<ParseNode> children$iv;
        List<Token> rem$iv;
        block5: {
            void $this$parseLegacyDml$iv;
            List<Object> list;
            List<Object> list2;
            void $this$tail$iv;
            List<Token> $this$head$iv = $this$parseDelete;
            boolean $i$f$getHead = false;
            Token token = CollectionsKt.firstOrNull($this$head$iv);
            if (Intrinsics.areEqual(token != null ? token.getKeywordText() : null, "from") ^ true) {
                Void void_ = TokenListExtensionsKt.err$default($this$parseDelete, "Expected FROM after DELETE", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
                throw null;
            }
            $this$head$iv = $this$parseDelete;
            SqlParser sqlParser = this;
            boolean $i$f$getTail22 = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    List list3 = Collections.emptyList();
                    list2 = list3;
                    Intrinsics.checkExpressionValueIsNotNull(list3, "emptyList()");
                    break;
                }
                default: {
                    list2 = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            List<Object> $i$f$getTail22 = list = list2;
            SqlParser this_$iv = sqlParser;
            boolean $i$f$parseLegacyDml = false;
            rem$iv = $this$parseLegacyDml$iv;
            children$iv = new ArrayList<ParseNode>();
            ParseNode parseNode = this_$iv.parsePathTerm(rem$iv, PathMode.SIMPLE_PATH);
            boolean bl = false;
            boolean bl2 = false;
            ParseNode it$iv = parseNode;
            boolean bl3 = false;
            ParseNode parseNode2 = this_$iv.parseOptionalAsAlias(it$iv.getRemaining(), it$iv);
            boolean bl4 = false;
            boolean bl5 = false;
            ParseNode asNode$iv = parseNode2;
            boolean bl6 = false;
            rem$iv = asNode$iv.getRemaining();
            parseNode = parseNode2;
            bl = false;
            bl2 = false;
            it$iv = parseNode;
            boolean bl7 = false;
            parseNode2 = this_$iv.parseOptionalAtAlias(it$iv.getRemaining(), it$iv);
            bl4 = false;
            bl5 = false;
            ParseNode atNode$iv = parseNode2;
            boolean bl8 = false;
            rem$iv = atNode$iv.getRemaining();
            parseNode = parseNode2;
            bl = false;
            bl2 = false;
            it$iv = parseNode;
            boolean bl9 = false;
            parseNode2 = this_$iv.parseOptionalByAlias(it$iv.getRemaining(), it$iv);
            bl4 = false;
            bl5 = false;
            ParseNode byNode$iv = parseNode2;
            boolean bl10 = false;
            rem$iv = byNode$iv.getRemaining();
            ParseNode source$iv = parseNode2;
            children$iv.add(new ParseNode(ParseType.FROM_CLAUSE, null, CollectionsKt.listOf(source$iv), rem$iv));
            List<Token> $this$parseLegacyDml = rem$iv;
            boolean bl11 = false;
            ParseNode parseNode3 = new ParseNode(ParseType.DELETE, name, CollectionsKt.emptyList(), $this$parseLegacyDml);
            bl2 = false;
            boolean bl12 = false;
            ParseNode it$iv2 = parseNode3;
            boolean bl13 = false;
            rem$iv = it$iv2.getRemaining();
            operation$iv = parseNode3;
            ParseNode parseNode4 = this_$iv.parseOptionalWhere(rem$iv);
            if (parseNode4 != null) {
                parseNode3 = parseNode4;
                bl2 = false;
                bl12 = false;
                it$iv2 = parseNode3;
                boolean bl14 = false;
                children$iv.add(it$iv2);
                rem$iv = it$iv2.getRemaining();
            }
            ParseNode parseNode5 = this_$iv.parseOptionalReturning(rem$iv);
            if (parseNode5 == null) break block5;
            parseNode3 = parseNode5;
            bl2 = false;
            bl12 = false;
            it$iv2 = parseNode3;
            boolean bl15 = false;
            children$iv.add(it$iv2);
            rem$iv = it$iv2.getRemaining();
        }
        return new ParseNode(ParseType.FROM, null, CollectionsKt.plus((Collection)CollectionsKt.listOf(operation$iv), (Iterable)children$iv), rem$iv);
    }

    private final ParseNode parseUpdate(@NotNull List<Token> $this$parseUpdate) {
        ParseNode operation$iv;
        ArrayList<ParseNode> children$iv;
        List<Token> rem$iv;
        block1: {
            List<Token> $this$parseLegacyDml$iv = $this$parseUpdate;
            SqlParser this_$iv = this;
            boolean $i$f$parseLegacyDml = false;
            rem$iv = $this$parseLegacyDml$iv;
            children$iv = new ArrayList<ParseNode>();
            ParseNode parseNode = this_$iv.parsePathTerm(rem$iv, PathMode.SIMPLE_PATH);
            boolean bl = false;
            boolean bl2 = false;
            ParseNode it$iv = parseNode;
            boolean bl3 = false;
            ParseNode parseNode2 = this_$iv.parseOptionalAsAlias(it$iv.getRemaining(), it$iv);
            boolean bl4 = false;
            boolean bl5 = false;
            ParseNode asNode$iv = parseNode2;
            boolean bl6 = false;
            rem$iv = asNode$iv.getRemaining();
            parseNode = parseNode2;
            bl = false;
            bl2 = false;
            it$iv = parseNode;
            boolean bl7 = false;
            parseNode2 = this_$iv.parseOptionalAtAlias(it$iv.getRemaining(), it$iv);
            bl4 = false;
            bl5 = false;
            ParseNode atNode$iv = parseNode2;
            boolean bl8 = false;
            rem$iv = atNode$iv.getRemaining();
            parseNode = parseNode2;
            bl = false;
            bl2 = false;
            it$iv = parseNode;
            boolean bl9 = false;
            parseNode2 = this_$iv.parseOptionalByAlias(it$iv.getRemaining(), it$iv);
            bl4 = false;
            bl5 = false;
            ParseNode byNode$iv = parseNode2;
            boolean bl10 = false;
            rem$iv = byNode$iv.getRemaining();
            ParseNode source$iv = parseNode2;
            children$iv.add(new ParseNode(ParseType.FROM_CLAUSE, null, CollectionsKt.listOf(source$iv), rem$iv));
            List<Token> $this$parseLegacyDml = rem$iv;
            boolean bl11 = false;
            ParseNode parseNode3 = this.parseBaseDmls($this$parseLegacyDml);
            bl2 = false;
            boolean bl12 = false;
            ParseNode it$iv2 = parseNode3;
            boolean bl13 = false;
            rem$iv = it$iv2.getRemaining();
            operation$iv = parseNode3;
            ParseNode parseNode4 = this_$iv.parseOptionalWhere(rem$iv);
            if (parseNode4 != null) {
                parseNode3 = parseNode4;
                bl2 = false;
                bl12 = false;
                it$iv2 = parseNode3;
                boolean bl14 = false;
                children$iv.add(it$iv2);
                rem$iv = it$iv2.getRemaining();
            }
            ParseNode parseNode5 = this_$iv.parseOptionalReturning(rem$iv);
            if (parseNode5 == null) break block1;
            parseNode3 = parseNode5;
            bl2 = false;
            bl12 = false;
            it$iv2 = parseNode3;
            boolean bl15 = false;
            children$iv.add(it$iv2);
            rem$iv = it$iv2.getRemaining();
        }
        return new ParseNode(ParseType.FROM, null, CollectionsKt.plus((Collection)CollectionsKt.listOf(operation$iv), (Iterable)children$iv), rem$iv);
    }

    private final ParseNode parseReturning(@NotNull List<Token> $this$parseReturning) {
        List<Token> rem = $this$parseReturning;
        List<ParseNode> returningElems = CollectionsKt.listOf(this.parseReturningElems(rem));
        rem = CollectionsKt.first(returningElems).getRemaining();
        return new ParseNode(ParseType.RETURNING, null, returningElems, rem);
    }

    private final ParseNode parseLegacyDml(@NotNull List<Token> $this$parseLegacyDml, Function1<? super List<Token>, ParseNode> parseDmlOp) {
        ParseNode operation;
        ArrayList<ParseNode> children;
        List<Token> rem;
        block1: {
            int $i$f$parseLegacyDml = 0;
            rem = $this$parseLegacyDml;
            children = new ArrayList<ParseNode>();
            ParseNode parseNode = this.parsePathTerm(rem, PathMode.SIMPLE_PATH);
            boolean bl = false;
            boolean bl2 = false;
            ParseNode it = parseNode;
            boolean bl3 = false;
            ParseNode parseNode2 = this.parseOptionalAsAlias(it.getRemaining(), it);
            boolean bl4 = false;
            boolean bl5 = false;
            ParseNode asNode = parseNode2;
            boolean bl6 = false;
            rem = asNode.getRemaining();
            parseNode = parseNode2;
            bl = false;
            bl2 = false;
            it = parseNode;
            boolean bl7 = false;
            parseNode2 = this.parseOptionalAtAlias(it.getRemaining(), it);
            bl4 = false;
            bl5 = false;
            ParseNode atNode = parseNode2;
            boolean bl8 = false;
            rem = atNode.getRemaining();
            parseNode = parseNode2;
            bl = false;
            bl2 = false;
            it = parseNode;
            boolean bl9 = false;
            parseNode2 = this.parseOptionalByAlias(it.getRemaining(), it);
            bl4 = false;
            bl5 = false;
            ParseNode byNode = parseNode2;
            boolean bl10 = false;
            rem = byNode.getRemaining();
            ParseNode source = parseNode2;
            children.add(new ParseNode(ParseType.FROM_CLAUSE, null, CollectionsKt.listOf(source), rem));
            ParseNode parseNode3 = parseDmlOp.invoke(rem);
            bl2 = false;
            boolean bl11 = false;
            ParseNode it2 = parseNode3;
            boolean bl12 = false;
            rem = it2.getRemaining();
            operation = parseNode3;
            ParseNode parseNode4 = this.parseOptionalWhere(rem);
            if (parseNode4 != null) {
                parseNode3 = parseNode4;
                bl2 = false;
                bl11 = false;
                it2 = parseNode3;
                boolean bl13 = false;
                children.add(it2);
                rem = it2.getRemaining();
            }
            ParseNode parseNode5 = this.parseOptionalReturning(rem);
            if (parseNode5 == null) break block1;
            parseNode3 = parseNode5;
            bl2 = false;
            bl11 = false;
            it2 = parseNode3;
            boolean bl14 = false;
            children.add(it2);
            rem = it2.getRemaining();
        }
        return new ParseNode(ParseType.FROM, null, CollectionsKt.plus((Collection)CollectionsKt.listOf(operation), (Iterable)children), rem);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseOptionalWhere(@NotNull List<Token> $this$parseOptionalWhere) {
        List<Token> rem;
        List<Token> $this$head$iv = rem = $this$parseOptionalWhere;
        boolean $i$f$getHead2 = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        if (Intrinsics.areEqual(token != null ? token.getKeywordText() : null, "where")) {
            List<Object> list;
            void $this$tail$iv;
            List<Token> $i$f$getHead2 = rem;
            SqlParser sqlParser = this;
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
            List<Object> list3 = list;
            ParseNode expr = SqlParser.parseExpression$lang$default(sqlParser, list3, 0, 1, null);
            rem = expr.getRemaining();
            return new ParseNode(ParseType.WHERE, null, CollectionsKt.listOf(expr), rem);
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseOptionalReturning(@NotNull List<Token> $this$parseOptionalReturning) {
        List<Token> rem;
        List<Token> $this$head$iv = rem = $this$parseOptionalReturning;
        boolean $i$f$getHead = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        if (Intrinsics.areEqual(token != null ? token.getKeywordText() : null, "returning")) {
            List<Token> list;
            void $this$tail$iv;
            $this$head$iv = rem;
            SqlParser sqlParser = this;
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
            List<Token> list3 = list;
            return sqlParser.parseReturning(list3);
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseReturningElems(@NotNull List<Token> $this$parseReturningElems) {
        void $this$parseDelimitedList$iv$iv;
        List<Token> $this$parseCommaList$iv = $this$parseReturningElems;
        SqlParser this_$iv = this;
        boolean $i$f$parseCommaList = false;
        List<Token> list = $this$parseCommaList$iv;
        SqlParser sqlParser = this_$iv;
        Function1 parseDelim$iv$iv = this_$iv.parseCommaDelim;
        boolean $i$f$parseDelimitedList = false;
        ArrayList<ParseNode> items$iv$iv = new ArrayList<ParseNode>();
        ParseNode delim$iv$iv = null;
        List<Token> rem$iv$iv = $this$parseDelimitedList$iv$iv;
        while (true) {
            Collection collection = rem$iv$iv;
            boolean bl = false;
            if (!(!collection.isEmpty())) break;
            ParseNode parseNode = delim$iv$iv;
            void $this$parseDelimitedList$iv = rem$iv$iv;
            boolean bl2 = false;
            void $this$parseCommaList = $this$parseDelimitedList$iv;
            boolean bl3 = false;
            List<Token> rem = $this$parseCommaList;
            ParseNode parseNode2 = this.parseReturningMapping(rem);
            boolean bl4 = false;
            boolean bl5 = false;
            ParseNode it = parseNode2;
            boolean bl6 = false;
            rem = it.getRemaining();
            ParseNode returningMapping = parseNode2;
            ParseNode parseNode3 = this.parseColumn(rem);
            bl5 = false;
            boolean bl7 = false;
            ParseNode it2 = parseNode3;
            boolean bl8 = false;
            rem = it2.getRemaining();
            ParseNode column = parseNode3;
            ParseNode child$iv$iv = new ParseNode(ParseType.RETURNING_ELEM, null, CollectionsKt.listOf(returningMapping, column), rem);
            items$iv$iv.add(child$iv$iv);
            rem$iv$iv = child$iv$iv.getRemaining();
            delim$iv$iv = (ParseNode)parseDelim$iv$iv.invoke(rem$iv$iv);
            if (delim$iv$iv == null) break;
            rem$iv$iv = delim$iv$iv.getRemaining();
        }
        return new ParseNode(ParseType.ARG_LIST, null, (List<ParseNode>)items$iv$iv, rem$iv$iv);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseReturningMapping(@NotNull List<Token> $this$parseReturningMapping) {
        block9: {
            $this$head$iv = rem = $this$parseReturningMapping;
            $i$f$getHead = false;
            v0 = CollectionsKt.firstOrNull($this$head$iv);
            v1 = v0 != null ? v0.getKeywordText() : null;
            if (v1 == null) break block9;
            var3_3 = v1;
            switch (var3_3.hashCode()) {
                case -911828478: {
                    if (!var3_3.equals("all_new")) break;
                    ** GOTO lbl19
                }
                case -625193174: {
                    if (!var3_3.equals("modified_new")) break;
                    ** GOTO lbl19
                }
                case -625192015: {
                    if (!var3_3.equals("modified_old")) break;
                    ** GOTO lbl19
                }
                case -911827319: {
                    if (!var3_3.equals("all_old")) break;
lbl19:
                    // 4 sources

                    $i$f$getHead = rem;
                    var8_8 = ParseType.RETURNING_MAPPING;
                    $i$f$getHead = false;
                    var9_10 /* !! */  = CollectionsKt.firstOrNull($this$head$iv);
                    var9_10 /* !! */  = (Token)var9_10 /* !! */ ;
                    $this$head$iv = false;
                    var10_11 = CollectionsKt.emptyList();
                    $this$head$iv = rem;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v2 = Collections.emptyList();
                            v3 = v2;
                            Intrinsics.checkExpressionValueIsNotNull(v2, "emptyList()");
                            break;
                        }
                        default: {
                            v3 = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var12_13 = var11_12 = v3;
                    var13_14 = var10_11;
                    var14_15 /* !! */  = var9_10 /* !! */ ;
                    var15_16 = var8_8;
                    return new ParseNode(var15_16, (Token)var14_15 /* !! */ , var13_14, var12_13);
                }
            }
        }
        v4 = TokenListExtensionsKt.err$default(rem, "Expected ( MODIFIED | ALL ) ( NEW | OLD ) in each returning element.", ErrorCode.PARSE_EXPECTED_RETURNING_CLAUSE, null, 4, null);
        throw null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private final ParseNode parseColumn(@NotNull List<Token> $this$parseColumn) {
        void var2_2;
        ParseNode parseNode;
        ParseNode parseNode2;
        List<Token> $this$head$iv = $this$parseColumn;
        boolean $i$f$getHead = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        TokenType tokenType = token != null ? token.getType() : null;
        if (tokenType != null) {
            switch (SqlParser$WhenMappings.$EnumSwitchMapping$31[tokenType.ordinal()]) {
                case 1: {
                    List<Token> list;
                    List<Token> list2;
                    void $this$tail$iv;
                    $this$head$iv = $this$parseColumn;
                    ParseType parseType = ParseType.RETURNING_WILDCARD;
                    $i$f$getHead = false;
                    Token token2 = CollectionsKt.firstOrNull($this$head$iv);
                    boolean $this$head$iv22 = false;
                    List<ParseNode> list3 = CollectionsKt.emptyList();
                    List<Token> $this$head$iv22 = $this$parseColumn;
                    boolean $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            List list4 = Collections.emptyList();
                            list2 = list4;
                            Intrinsics.checkExpressionValueIsNotNull(list4, "emptyList()");
                            break;
                        }
                        default: {
                            list2 = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    List<Token> list5 = list = list2;
                    List<ParseNode> list6 = list3;
                    Token token3 = token2;
                    ParseType parseType2 = parseType;
                    parseNode2 = new ParseNode(parseType2, token3, list6, list5);
                    return parseNode2;
                }
            }
        }
        ParseNode parseNode3 = SqlParser.parseExpression$lang$default(this, $this$parseColumn, 0, 1, null);
        boolean bl = false;
        boolean bl2 = false;
        ParseNode it = parseNode3;
        boolean bl3 = false;
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$30[it.getType().ordinal()]) {
            case 1: {
                parseNode = this.inspectColumnPathExpression(it);
                break;
            }
            case 2: {
                parseNode = it;
                break;
            }
            default: {
                Void void_ = TokenListExtensionsKt.err$default($this$parseColumn, "Unsupported syntax in RETURNING columns.", ErrorCode.PARSE_UNSUPPORTED_RETURNING_CLAUSE_SYNTAX, null, 4, null);
                throw null;
            }
        }
        ParseNode expr = parseNode;
        parseNode2 = var2_2;
        return parseNode2;
    }

    private final ParseNode inspectColumnPathExpression(ParseNode pathNode) {
        if (pathNode.getChildren().size() > 2) {
            Token token = pathNode.getChildren().get(2).getToken();
            if (token != null) {
                Void void_ = TokenListExtensionsKt.err$default(token, "More than two paths in RETURNING columns.", ErrorCode.PARSE_UNSUPPORTED_RETURNING_CLAUSE_SYNTAX, null, 4, null);
                throw null;
            }
        }
        return pathNode;
    }

    private final ParseNode parsePivot(@NotNull List<Token> $this$parsePivot) {
        List<Token> rem = $this$parsePivot;
        ParseNode value = SqlParser.parseExpression$lang$default(this, rem, 0, 1, null).deriveExpectedKeyword("at");
        rem = value.getRemaining();
        ParseNode name = SqlParser.parseExpression$lang$default(this, rem, 0, 1, null);
        rem = name.getRemaining();
        ParseNode selectAfterProjection = this.parseSelectAfterProjection(ParseType.PIVOT, new ParseNode(ParseType.MEMBER, null, CollectionsKt.listOf(name, value), rem));
        return selectAfterProjection;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseSelect(@NotNull List<Token> $this$parseSelect) {
        block21: {
            block20: {
                rem /* !! */  = $this$parseSelect;
                $this$head$iv = $this$parseSelect;
                $i$f$getHead = false;
                v0 = CollectionsKt.firstOrNull($this$head$iv);
                v1 = v0 != null ? v0.getKeywordText() : null;
                if (v1 == null) break block20;
                $this$head$iv = v1;
                switch ($this$head$iv.hashCode()) {
                    case 96673: {
                        if (!$this$head$iv.equals("all")) ** break;
                        break;
                    }
                    case 288698108: {
                        if (!$this$head$iv.equals("distinct")) ** break;
                        $this$tail$iv = $this$parseSelect;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v2 = Collections.emptyList();
                                v3 /* !! */  = v2;
                                Intrinsics.checkExpressionValueIsNotNull(v2, "emptyList()");
                                break;
                            }
                            default: {
                                v3 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        rem /* !! */  = v3 /* !! */ ;
                        v4 = true;
                        break block21;
                    }
                }
                $this$tail$iv = $this$parseSelect;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v5 = Collections.emptyList();
                        v6 /* !! */  = v5;
                        Intrinsics.checkExpressionValueIsNotNull(v5, "emptyList()");
                        break;
                    }
                    default: {
                        v6 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                rem /* !! */  = v6 /* !! */ ;
                v4 = false;
                break block21;
            }
            v4 = false;
        }
        distinct = v4;
        type = ParseType.SELECT_LIST;
        $this$head$iv = rem /* !! */ ;
        $i$f$getHead = false;
        v7 = CollectionsKt.firstOrNull($this$head$iv);
        if (Intrinsics.areEqual(v7 != null ? v7.getKeywordText() : null, "value")) {
            type = ParseType.SELECT_VALUE;
            $this$head$iv = rem /* !! */ ;
            var14_13 = this;
            $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    v8 = Collections.emptyList();
                    v9 /* !! */  = v8;
                    Intrinsics.checkExpressionValueIsNotNull(v8, "emptyList()");
                    break;
                }
                default: {
                    v9 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            var15_14 /* !! */  = v9 /* !! */ ;
            v10 = SqlParser.parseExpression$lang$default(var14_13, var15_14 /* !! */ , 0, 1, null);
        } else {
            block19: {
                list = this.parseSelectList(rem /* !! */ );
                if (list.getChildren().isEmpty()) {
                    v11 = TokenListExtensionsKt.err$default(rem /* !! */ , "Cannot have empty SELECT list", ErrorCode.PARSE_EMPTY_SELECT, null, 4, null);
                    throw null;
                }
                $this$firstOrNull$iv = list.getChildren();
                $i$f$firstOrNull = false;
                for (T element$iv : $this$firstOrNull$iv) {
                    it = (ParseNode)element$iv;
                    $i$a$-firstOrNull-SqlParser$parseSelect$projection$asterisk$1 = false;
                    if (!(it.getType() == ParseType.PROJECT_ALL && it.getChildren().isEmpty() != false)) continue;
                    v12 = element$iv;
                    break block19;
                }
                v12 = null;
            }
            asterisk = v12;
            if (asterisk != null && list.getChildren().size() > 1) {
                v13 = TokenListExtensionsKt.err$default(asterisk.getToken(), "Other expressions may not be present in the select list when '*' is used without dot notation.", ErrorCode.PARSE_ASTERISK_IS_NOT_ALONE_IN_SELECT_LIST, null, 4, null);
                throw null;
            }
            v10 = projection = list;
        }
        if (distinct) {
            projection = new ParseNode(ParseType.DISTINCT, null, CollectionsKt.listOf(projection), projection.getRemaining());
        }
        parseSelectAfterProjection = this.parseSelectAfterProjection(type, projection);
        return parseSelectAfterProjection;
    }

    private final void expectEof(@NotNull ParseNode $this$expectEof, String statementType) {
        if (!TokenListExtensionsKt.onlyEndOfStatement($this$expectEof.getRemaining())) {
            Void void_ = TokenListExtensionsKt.err$default($this$expectEof.getRemaining(), "Unexpected tokens after " + statementType + " statement!", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
            throw null;
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseCreate(@NotNull List<Token> $this$parseCreate) {
        block11: {
            block10: {
                $this$head$iv = $this$parseCreate;
                $i$f$getHead = false;
                v0 = CollectionsKt.firstOrNull($this$head$iv);
                v1 = v0 != null ? v0.getKeywordText() : null;
                if (v1 == null) break block10;
                var2_2 = v1;
                switch (var2_2.hashCode()) {
                    case 100346066: {
                        if (!var2_2.equals("index")) ** break;
                        break;
                    }
                    case 110115790: {
                        if (!var2_2.equals("table")) ** break;
                        $i$f$getHead = $this$parseCreate;
                        var7_7 = this;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v2 = Collections.emptyList();
                                v3 = v2;
                                Intrinsics.checkExpressionValueIsNotNull(v2, "emptyList()");
                                break;
                            }
                            default: {
                                v3 = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        var8_10 = v3;
                        v4 = var7_7.parseCreateTable(var8_10);
                        break block11;
                    }
                }
                $this$tail$iv = $this$parseCreate;
                var7_7 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v5 = Collections.emptyList();
                        v6 /* !! */  = v5;
                        Intrinsics.checkExpressionValueIsNotNull(v5, "emptyList()");
                        break;
                    }
                    default: {
                        v6 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var8_10 = v6 /* !! */ ;
                v4 = var7_7.parseCreateIndex(var8_10);
                break block11;
            }
            $this$head$iv = $this$parseCreate;
            $i$f$getHead = false;
            v7 = TokenListExtensionsKt.err$default(CollectionsKt.firstOrNull($this$head$iv), "Unexpected token following CREATE", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
            throw null;
        }
        var2_2 = v4;
        var3_6 = false;
        var4_8 = false;
        $this$apply = var2_2;
        $i$a$-apply-SqlParser$parseCreate$1 = false;
        this.expectEof((ParseNode)$this$apply, "CREATE");
        return var2_2;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseDrop(@NotNull List<Token> $this$parseDrop) {
        block11: {
            block10: {
                $this$head$iv = $this$parseDrop;
                $i$f$getHead = false;
                v0 = CollectionsKt.firstOrNull($this$head$iv);
                v1 = v0 != null ? v0.getKeywordText() : null;
                if (v1 == null) break block10;
                var2_2 = v1;
                switch (var2_2.hashCode()) {
                    case 100346066: {
                        if (!var2_2.equals("index")) ** break;
                        break;
                    }
                    case 110115790: {
                        if (!var2_2.equals("table")) ** break;
                        $i$f$getHead = $this$parseDrop;
                        var7_7 = this;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v2 = Collections.emptyList();
                                v3 = v2;
                                Intrinsics.checkExpressionValueIsNotNull(v2, "emptyList()");
                                break;
                            }
                            default: {
                                v3 = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        var8_10 = v3;
                        v4 = var7_7.parseDropTable(var8_10);
                        break block11;
                    }
                }
                $this$tail$iv = $this$parseDrop;
                var7_7 = this;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v5 = Collections.emptyList();
                        v6 /* !! */  = v5;
                        Intrinsics.checkExpressionValueIsNotNull(v5, "emptyList()");
                        break;
                    }
                    default: {
                        v6 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var8_10 = v6 /* !! */ ;
                v4 = var7_7.parseDropIndex(var8_10);
                break block11;
            }
            $this$head$iv = $this$parseDrop;
            $i$f$getHead = false;
            v7 = TokenListExtensionsKt.err$default(CollectionsKt.firstOrNull($this$head$iv), "Unexpected token following DROP", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
            throw null;
        }
        var2_2 = v4;
        var3_6 = false;
        var4_8 = false;
        $this$apply = var2_2;
        $i$a$-apply-SqlParser$parseDrop$1 = false;
        this.expectEof((ParseNode)$this$apply, "DROP");
        return var2_2;
    }

    /*
     * Unable to fully structure code
     */
    private final ParseNode parseCreateTable(@NotNull List<Token> $this$parseCreateTable) {
        $this$head$iv = $this$parseCreateTable;
        $i$f$getHead = false;
        v0 = CollectionsKt.firstOrNull($this$head$iv);
        v1 = v0 != null ? v0.getType() : null;
        if (v1 == null) ** GOTO lbl-1000
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$32[v1.ordinal()]) {
            case 1: 
            case 2: {
                break;
            }
            default: lbl-1000:
            // 2 sources

            {
                v2 = TokenListExtensionsKt.err$default($this$parseCreateTable, "Expected identifier!", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
                throw null;
            }
        }
        identifier = TokenListExtensionsKt.atomFromHead$default($this$parseCreateTable, null, 1, null);
        return new ParseNode(ParseType.CREATE_TABLE, null, CollectionsKt.listOf(identifier), identifier.getRemaining());
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseDropIndex(@NotNull List<Token> $this$parseDropIndex) {
        $this$head$iv = rem /* !! */  = $this$parseDropIndex;
        $i$f$getHead = false;
        v0 = CollectionsKt.firstOrNull($this$head$iv);
        v1 = v0 != null ? v0.getType() : null;
        if (v1 == null) ** GOTO lbl-1000
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$33[v1.ordinal()]) {
            case 1: 
            case 2: {
                break;
            }
            default: lbl-1000:
            // 2 sources

            {
                v2 = TokenListExtensionsKt.err$default(rem /* !! */ , "Expected identifier!", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
                throw null;
            }
        }
        identifier = TokenListExtensionsKt.atomFromHead$default($this$parseDropIndex, null, 1, null);
        $this$tail$iv = rem /* !! */ ;
        $i$f$getTail = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                v3 = Collections.emptyList();
                v4 /* !! */  = v3;
                Intrinsics.checkExpressionValueIsNotNull(v3, "emptyList()");
                break;
            }
            default: {
                v4 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        $this$head$iv = rem /* !! */  = v4 /* !! */ ;
        $i$f$getHead = false;
        v5 = CollectionsKt.firstOrNull($this$head$iv);
        if (Intrinsics.areEqual(v5 != null ? v5.getKeywordText() : null, "on") ^ true) {
            v6 = TokenListExtensionsKt.err$default(rem /* !! */ , "Expected ON", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
            throw null;
        }
        $this$tail$iv = rem /* !! */ ;
        $i$f$getTail = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                v7 = Collections.emptyList();
                v8 /* !! */  = v7;
                Intrinsics.checkExpressionValueIsNotNull(v7, "emptyList()");
                break;
            }
            default: {
                v8 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        rem /* !! */  = v8 /* !! */ ;
        $this$head$iv = rem /* !! */ ;
        $i$f$getHead = false;
        v9 = CollectionsKt.firstOrNull($this$head$iv);
        v10 = v9 != null ? v9.getType() : null;
        if (v10 == null) ** GOTO lbl-1000
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$34[v10.ordinal()]) {
            case 1: 
            case 2: {
                break;
            }
            default: lbl-1000:
            // 2 sources

            {
                v11 = TokenListExtensionsKt.err$default(rem /* !! */ , "Table target must be an identifier", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
                throw null;
            }
        }
        target = TokenListExtensionsKt.atomFromHead$default(rem /* !! */ , null, 1, null);
        $this$tail$iv = rem /* !! */ ;
        $i$f$getTail = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                v12 = Collections.emptyList();
                v13 /* !! */  = v12;
                Intrinsics.checkExpressionValueIsNotNull(v12, "emptyList()");
                break;
            }
            default: {
                v13 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        rem /* !! */  = v13 /* !! */ ;
        return new ParseNode(ParseType.DROP_INDEX, null, CollectionsKt.listOf(new ParseNode[]{identifier, target}), rem /* !! */ );
    }

    /*
     * Unable to fully structure code
     */
    private final ParseNode parseDropTable(@NotNull List<Token> $this$parseDropTable) {
        $this$head$iv = $this$parseDropTable;
        $i$f$getHead = false;
        v0 = CollectionsKt.firstOrNull($this$head$iv);
        v1 = v0 != null ? v0.getType() : null;
        if (v1 == null) ** GOTO lbl-1000
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$35[v1.ordinal()]) {
            case 1: 
            case 2: {
                break;
            }
            default: lbl-1000:
            // 2 sources

            {
                v2 = TokenListExtensionsKt.err$default($this$parseDropTable, "Expected identifier!", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
                throw null;
            }
        }
        identifier = TokenListExtensionsKt.atomFromHead$default($this$parseDropTable, null, 1, null);
        return new ParseNode(ParseType.DROP_TABLE, null, CollectionsKt.listOf(identifier), identifier.getRemaining());
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseCreateIndex(@NotNull List<Token> $this$parseCreateIndex) {
        $this$head$iv = rem /* !! */  = $this$parseCreateIndex;
        $i$f$getHead = false;
        v0 = CollectionsKt.firstOrNull($this$head$iv);
        if (Intrinsics.areEqual(v0 != null ? v0.getKeywordText() : null, "on") ^ true) {
            v1 = TokenListExtensionsKt.err$default($this$parseCreateIndex, "Expected ON", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
            throw null;
        }
        $this$tail$iv = rem /* !! */ ;
        $i$f$getTail = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                v2 = Collections.emptyList();
                v3 /* !! */  = v2;
                Intrinsics.checkExpressionValueIsNotNull(v2, "emptyList()");
                break;
            }
            default: {
                v3 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        $this$head$iv /* !! */  = rem /* !! */  = v3 /* !! */ ;
        $i$f$getHead = false;
        v4 = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
        v5 = v4 != null ? v4.getType() : null;
        if (v5 == null) ** GOTO lbl-1000
        switch (SqlParser$WhenMappings.$EnumSwitchMapping$36[v5.ordinal()]) {
            case 1: 
            case 2: {
                break;
            }
            default: lbl-1000:
            // 2 sources

            {
                v6 = TokenListExtensionsKt.err$default(rem /* !! */ , "Index target must be an identifier", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
                throw null;
            }
        }
        target = TokenListExtensionsKt.atomFromHead$default(rem /* !! */ , null, 1, null);
        $this$head$iv /* !! */  = rem /* !! */  = target.getRemaining();
        $i$f$getHead = false;
        v7 = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
        if ((v7 != null ? v7.getType() : null) != TokenType.LEFT_PAREN) {
            v8 = TokenListExtensionsKt.err$default(rem /* !! */ , "Expected parenthesis for keys", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
            throw null;
        }
        $i$f$getHead /* !! */  = rem /* !! */ ;
        var7_8 = this;
        $i$f$getTail = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                v9 = Collections.emptyList();
                v10 /* !! */  = v9;
                Intrinsics.checkExpressionValueIsNotNull(v9, "emptyList()");
                break;
            }
            default: {
                v10 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        var8_10 /* !! */  = v10 /* !! */ ;
        keys = SqlParser.parseArgList$default(var7_8, var8_10 /* !! */ , AliasSupportType.NONE, ArgListMode.SIMPLE_PATH_ARG_LIST, 0, 4, null).deriveExpected(TokenType.RIGHT_PAREN);
        rem /* !! */  = keys.getRemaining();
        return new ParseNode(ParseType.CREATE_INDEX, null, CollectionsKt.listOf(new ParseNode[]{target, keys}), rem /* !! */ );
    }

    private final ParseNode inspectPathExpression(ParseNode pathNode) {
        Object object;
        Object v3;
        ParseNode it;
        boolean $i$f$firstOrNull;
        Iterable $this$firstOrNull$iv;
        List flattened;
        block8: {
            Object object2;
            Object v0;
            block7: {
                inspectPathExpression.1 $fun$flattenParseNode$1 = inspectPathExpression.1.INSTANCE;
                flattened = CollectionsKt.drop((Iterable)$fun$flattenParseNode$1.invoke(pathNode), 2);
                $this$firstOrNull$iv = flattened;
                $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    it = (ParseNode)element$iv;
                    boolean bl = false;
                    if (!(it.getType() == ParseType.PATH_WILDCARD)) continue;
                    v0 = element$iv;
                    break block7;
                }
                v0 = null;
            }
            if ((object2 = (ParseNode)v0) != null && (object2 = ((ParseNode)object2).getToken()) != null) {
                Void void_ = TokenListExtensionsKt.err$default((Token)object2, "Invalid use of * in select list", ErrorCode.PARSE_INVALID_CONTEXT_FOR_WILDCARD_IN_SELECT_LIST, null, 4, null);
                throw null;
            }
            $this$firstOrNull$iv = CollectionsKt.dropLast(flattened, 1);
            $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                it = (ParseNode)element$iv;
                boolean bl = false;
                if (!(it.getType() == ParseType.PATH_UNPIVOT)) continue;
                v3 = element$iv;
                break block8;
            }
            v3 = null;
        }
        if ((object = (ParseNode)v3) != null && (object = ((ParseNode)object).getToken()) != null) {
            Void void_ = TokenListExtensionsKt.err$default((Token)object, "Invalid use of * in select list", ErrorCode.PARSE_INVALID_CONTEXT_FOR_WILDCARD_IN_SELECT_LIST, null, 4, null);
            throw null;
        }
        if (((ParseNode)CollectionsKt.last(flattened)).getType() == ParseType.PATH_UNPIVOT) {
            Object object3;
            Object v6;
            block9: {
                $this$firstOrNull$iv = flattened;
                $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    it = (ParseNode)element$iv;
                    boolean bl = false;
                    if (!(it.getType() == ParseType.PATH_SQB)) continue;
                    v6 = element$iv;
                    break block9;
                }
                v6 = null;
            }
            if ((object3 = (ParseNode)v6) != null && (object3 = ((ParseNode)object3).getToken()) != null) {
                Void void_ = TokenListExtensionsKt.err$default((Token)object3, "Cannot use [] and * together in SELECT list expression", ErrorCode.PARSE_CANNOT_MIX_SQB_AND_WILDCARD_IN_SELECT_LIST, null, 4, null);
                throw null;
            }
            ParseNode pathPart = ParseNode.copy$default(pathNode, null, null, CollectionsKt.dropLast(pathNode.getChildren(), 1), null, 11, null);
            return new ParseNode(ParseType.PROJECT_ALL, null, CollectionsKt.listOf(pathPart.getChildren().size() == 1 ? pathPart.getChildren().get(0) : pathPart), pathNode.getRemaining());
        }
        return pathNode;
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseSelectList(@NotNull List<Token> $this$parseSelectList) {
        void $this$parseDelimitedList$iv$iv;
        List<Token> $this$parseCommaList$iv = $this$parseSelectList;
        SqlParser this_$iv = this;
        boolean $i$f$parseCommaList = false;
        List<Token> list = $this$parseCommaList$iv;
        SqlParser sqlParser = this_$iv;
        Function1 parseDelim$iv$iv = this_$iv.parseCommaDelim;
        boolean $i$f$parseDelimitedList = false;
        ArrayList<ParseNode> items$iv$iv = new ArrayList<ParseNode>();
        ParseNode delim$iv$iv = null;
        List<Token> rem$iv$iv = $this$parseDelimitedList$iv$iv;
        while (true) {
            ParseNode parseNode;
            Collection collection = rem$iv$iv;
            boolean bl = false;
            if (!(!collection.isEmpty())) break;
            ParseNode parseNode2 = delim$iv$iv;
            void $this$parseDelimitedList$iv = rem$iv$iv;
            boolean bl2 = false;
            void $this$parseCommaList = $this$parseDelimitedList$iv;
            boolean bl3 = false;
            void $this$head$iv2 = $this$parseCommaList;
            boolean $i$f$getHead = false;
            Token token = (Token)CollectionsKt.firstOrNull($this$head$iv2);
            if ((token != null ? token.getType() : null) == TokenType.STAR) {
                List<Token> list2;
                List<Token> list3;
                void $this$tail$iv;
                $this$head$iv2 = $this$parseCommaList;
                ParseType parseType = ParseType.PROJECT_ALL;
                $i$f$getHead = false;
                Object object = CollectionsKt.firstOrNull($this$head$iv2);
                object = (Token)object;
                boolean $this$head$iv = false;
                List<ParseNode> list4 = CollectionsKt.emptyList();
                $this$head$iv2 = $this$parseCommaList;
                boolean $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        List list5 = Collections.emptyList();
                        list3 = list5;
                        Intrinsics.checkExpressionValueIsNotNull(list5, "emptyList()");
                        break;
                    }
                    default: {
                        list3 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                List<Token> list6 = list2 = list3;
                List<ParseNode> list7 = list4;
                Object object2 = object;
                ParseType parseType2 = parseType;
                parseNode = new ParseNode(parseType2, (Token)object2, list7, list6);
            } else {
                ParseNode parseNode3;
                ParseNode $i$f$getTail = SqlParser.parseExpression$lang$default(this, (List)$this$parseCommaList, 0, 1, null);
                boolean bl4 = false;
                boolean bl5 = false;
                ParseNode it = $i$f$getTail;
                boolean bl6 = false;
                switch (SqlParser$WhenMappings.$EnumSwitchMapping$37[it.getType().ordinal()]) {
                    case 1: {
                        parseNode3 = this.inspectPathExpression(it);
                        break;
                    }
                    default: {
                        parseNode3 = it;
                    }
                }
                ParseNode expr = parseNode3;
                List<Token> rem = expr.getRemaining();
                parseNode = this.parseOptionalAsAlias(rem, expr);
            }
            ParseNode child$iv$iv = parseNode;
            items$iv$iv.add(child$iv$iv);
            rem$iv$iv = child$iv$iv.getRemaining();
            delim$iv$iv = (ParseNode)parseDelim$iv$iv.invoke(rem$iv$iv);
            if (delim$iv$iv == null) break;
            rem$iv$iv = delim$iv$iv.getRemaining();
        }
        return new ParseNode(ParseType.ARG_LIST, null, (List<ParseNode>)items$iv$iv, rem$iv$iv);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseSelectAfterProjection(ParseType selectType, ParseNode projection) {
        block28: {
            children = new ArrayList<ParseNode>();
            var4_4 = new Ref.ObjectRef<T>();
            var4_4.element = projection.getRemaining();
            children.add(projection);
            $this$head$iv = (List)rem.element;
            $i$f$getHead = false;
            v0 = (Token)CollectionsKt.firstOrNull($this$head$iv);
            if (Intrinsics.areEqual(v0 != null ? v0.getKeywordText() : null, "from") ^ true) {
                v1 = TokenListExtensionsKt.err$default((List)rem.element, "Expected FROM after SELECT list", ErrorCode.PARSE_SELECT_MISSING_FROM, null, 4, null);
                throw null;
            }
            $i$f$getHead = (List)rem.element;
            var16_8 = this;
            $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    v2 = Collections.emptyList();
                    v3 = v2;
                    Intrinsics.checkExpressionValueIsNotNull(v2, "emptyList()");
                    break;
                }
                default: {
                    v3 = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            var17_11 /* !! */  = v3;
            fromList = var16_8.parseFromSourceList(var17_11 /* !! */ , OperatorPrecedenceGroups.SELECT.getPrecedence());
            rem.element = fromList.getRemaining();
            children.add(fromList);
            $fun$parseOptionalSingleExpressionClause$1 = new Function1<ParseType, Unit>(this, (Ref.ObjectRef)rem, children){
                final /* synthetic */ SqlParser this$0;
                final /* synthetic */ Ref.ObjectRef $rem;
                final /* synthetic */ ArrayList $children;

                /*
                 * WARNING - void declaration
                 */
                public final void invoke(@NotNull ParseType type) {
                    Intrinsics.checkParameterIsNotNull((Object)((Object)type), "type");
                    List $this$head$iv = (List)this.$rem.element;
                    boolean $i$f$getHead2 = false;
                    Token token = (Token)CollectionsKt.firstOrNull($this$head$iv);
                    if (Intrinsics.areEqual(token != null ? token.getKeywordText() : null, type.getIdentifier())) {
                        List<Token> list;
                        void $this$tail$iv;
                        List $i$f$getHead2 = (List)this.$rem.element;
                        SqlParser sqlParser = this.this$0;
                        boolean $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                List<T> list2 = Collections.emptyList();
                                list = list2;
                                Intrinsics.checkExpressionValueIsNotNull(list2, "emptyList()");
                                break;
                            }
                            default: {
                                list = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        List<Token> list3 = list;
                        ParseNode expr = sqlParser.parseExpression$lang(list3, OperatorPrecedenceGroups.SELECT.getPrecedence());
                        this.$rem.element = expr.getRemaining();
                        this.$children.add(new ParseNode(type, null, CollectionsKt.listOf(expr), (List)this.$rem.element));
                    }
                }
                {
                    this.this$0 = sqlParser;
                    this.$rem = objectRef;
                    this.$children = arrayList;
                    super(1);
                }
            };
            $this$head$iv = (List)rem.element;
            $i$f$getHead = false;
            v4 = (Token)CollectionsKt.firstOrNull($this$head$iv);
            if (Intrinsics.areEqual(v4 != null ? v4.getKeywordText() : null, "let")) {
                letParseNode = this.parseLet((List)rem.element);
                rem.element = letParseNode.getRemaining();
                children.add(letParseNode);
            }
            $fun$parseOptionalSingleExpressionClause$1.invoke(ParseType.WHERE);
            $this$head$iv = (List)rem.element;
            $i$f$getHead = false;
            v5 = (Token)CollectionsKt.firstOrNull($this$head$iv);
            if (Intrinsics.areEqual(v5 != null ? v5.getKeywordText() : null, "order")) {
                $this$head$iv = (List)rem.element;
                var16_8 = rem;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v6 = Collections.emptyList();
                        v7 /* !! */  = v6;
                        Intrinsics.checkExpressionValueIsNotNull(v6, "emptyList()");
                        break;
                    }
                    default: {
                        v7 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var17_11 /* !! */  = v7 /* !! */ ;
                var16_8.element = TokenListExtensionsKt.tailExpectedToken(var17_11 /* !! */ , TokenType.BY);
                orderByChildren = CollectionsKt.listOf(this.parseOrderByArgList((List)rem.element));
                rem.element = CollectionsKt.first(orderByChildren).getRemaining();
                children.add(new ParseNode(ParseType.ORDER_BY, null, orderByChildren, (List)rem.element));
            }
            $this$head$iv = (List)rem.element;
            $i$f$getHead = false;
            v8 = (Token)CollectionsKt.firstOrNull($this$head$iv);
            if (!Intrinsics.areEqual(v8 != null ? v8.getKeywordText() : null, "group")) break block28;
            $this$head$iv = (List)rem.element;
            var16_8 = rem;
            $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    v9 = Collections.emptyList();
                    v10 /* !! */  = v9;
                    Intrinsics.checkExpressionValueIsNotNull(v9, "emptyList()");
                    break;
                }
                default: {
                    v10 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            var17_11 /* !! */  = v10 /* !! */ ;
            var16_8.element = var17_11 /* !! */ ;
            $this$head$iv = (List)rem.element;
            $i$f$getHead = false;
            v11 = (Token)CollectionsKt.firstOrNull($this$head$iv);
            v12 = v11 != null ? v11.getKeywordText() : null;
            if (v12 == null) ** GOTO lbl-1000
            $this$head$iv = v12;
            switch ($this$head$iv.hashCode()) {
                case -792934015: {
                    if ($this$head$iv.equals("partial")) {
                        $i$f$getHead = (List)rem.element;
                        var16_8 = rem;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v13 = Collections.emptyList();
                                v14 /* !! */  = v13;
                                Intrinsics.checkExpressionValueIsNotNull(v13, "emptyList()");
                                break;
                            }
                            default: {
                                v14 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        var17_11 /* !! */  = v14 /* !! */ ;
                        var16_8.element = var17_11 /* !! */ ;
                        v15 = ParseType.GROUP_PARTIAL;
                        break;
                    }
                }
                default: lbl-1000:
                // 2 sources

                {
                    v15 = ParseType.GROUP;
                }
            }
            type = v15;
            groupChildren = new ArrayList<ParseNode>();
            rem.element = TokenListExtensionsKt.tailExpectedToken((List)rem.element, TokenType.BY);
            groupKey = this.parseArgList((List)rem.element, AliasSupportType.AS_ONLY, ArgListMode.NORMAL_ARG_LIST, OperatorPrecedenceGroups.SELECT.getPrecedence());
            $this$forEach$iv = groupKey.getChildren();
            $i$f$forEach = false;
            for (T element$iv : $this$forEach$iv) {
                it = (ParseNode)element$iv;
                $i$a$-forEach-SqlParser$parseSelectAfterProjection$2 = false;
                v16 = it.getToken();
                if ((v16 != null ? v16.getType() : null) != TokenType.LITERAL) continue;
                v17 = TokenListExtensionsKt.err$default(it.getToken(), "Literals (including ordinals) not supported in GROUP BY", ErrorCode.PARSE_UNSUPPORTED_LITERALS_GROUPBY, null, 4, null);
                throw null;
            }
            groupChildren.add(groupKey);
            rem.element = groupKey.getRemaining();
            $this$head$iv = (List)rem.element;
            $i$f$getHead = false;
            v18 = (Token)CollectionsKt.firstOrNull($this$head$iv);
            if (Intrinsics.areEqual(v18 != null ? v18.getKeywordText() : null, "group")) {
                $this$head$iv = (List)rem.element;
                var16_8 = rem;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v19 = Collections.emptyList();
                        v20 /* !! */  = v19;
                        Intrinsics.checkExpressionValueIsNotNull(v19, "emptyList()");
                        break;
                    }
                    default: {
                        v20 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var17_11 /* !! */  = v20 /* !! */ ;
                var16_8.element = TokenListExtensionsKt.tailExpectedKeyword(var17_11 /* !! */ , "as");
                $this$head$iv = (List)rem.element;
                $i$f$getHead = false;
                v21 = (Token)CollectionsKt.firstOrNull($this$head$iv);
                if (v21 == null || (v21 = v21.getType()) == null || !v21.isIdentifier()) {
                    v22 = TokenListExtensionsKt.err$default((List)rem.element, "Expected identifier for GROUP name", ErrorCode.PARSE_EXPECTED_IDENT_FOR_GROUP_NAME, null, 4, null);
                    throw null;
                }
                groupChildren.add(TokenListExtensionsKt.atomFromHead$default((List)rem.element, null, 1, null));
                $this$head$iv = (List)rem.element;
                var16_8 = rem;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v23 = Collections.emptyList();
                        v24 /* !! */  = v23;
                        Intrinsics.checkExpressionValueIsNotNull(v23, "emptyList()");
                        break;
                    }
                    default: {
                        v24 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var17_11 /* !! */  = v24 /* !! */ ;
                var16_8.element = var17_11 /* !! */ ;
            }
            children.add(new ParseNode(type, null, (List<ParseNode>)groupChildren, (List)rem.element));
        }
        $fun$parseOptionalSingleExpressionClause$1.invoke(ParseType.HAVING);
        $fun$parseOptionalSingleExpressionClause$1.invoke(ParseType.LIMIT);
        return new ParseNode(selectType, null, (List<ParseNode>)children, (List)rem.element);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseFunctionCall(@NotNull List<Token> $this$parseFunctionCall, Token name) {
        block29: {
            block30: {
                block32: {
                    block31: {
                        block28: {
                            $fun$parseCallArguments$1 = new Function3<String, List<? extends Token>, ParseType, ParseNode>(this, $this$parseFunctionCall, name){
                                final /* synthetic */ SqlParser this$0;
                                final /* synthetic */ List $this_parseFunctionCall;
                                final /* synthetic */ Token $name;

                                /*
                                 * WARNING - void declaration
                                 * Enabled aggressive block sorting
                                 */
                                @NotNull
                                public final ParseNode invoke(@NotNull String callName, @NotNull List<Token> args2, @NotNull ParseType callType) {
                                    ParseNode parseNode;
                                    Intrinsics.checkParameterIsNotNull(callName, "callName");
                                    Intrinsics.checkParameterIsNotNull(args2, "args");
                                    Intrinsics.checkParameterIsNotNull((Object)((Object)callType), "callType");
                                    List $this$head$iv = args2;
                                    boolean $i$f$getHead = false;
                                    Token token = (Token)CollectionsKt.firstOrNull($this$head$iv);
                                    TokenType tokenType = token != null ? token.getType() : null;
                                    if (tokenType != null) {
                                        switch (SqlParser$WhenMappings.$EnumSwitchMapping$38[tokenType.ordinal()]) {
                                            case 1: {
                                                Void void_ = TokenListExtensionsKt.err$default(this.$this_parseFunctionCall, callName + "(*) is not allowed", ErrorCode.PARSE_UNSUPPORTED_CALL_WITH_STAR, null, 4, null);
                                                throw null;
                                            }
                                            case 2: {
                                                List<Token> list;
                                                List<Token> list2;
                                                void $this$tail$iv;
                                                $this$head$iv = this.$this_parseFunctionCall;
                                                List<ParseNode> list3 = CollectionsKt.emptyList();
                                                Token token2 = this.$name;
                                                ParseType parseType = callType;
                                                boolean $i$f$getTail = false;
                                                switch ($this$tail$iv.size()) {
                                                    case 0: 
                                                    case 1: {
                                                        List<T> list4 = Collections.emptyList();
                                                        list2 = list4;
                                                        Intrinsics.checkExpressionValueIsNotNull(list4, "emptyList()");
                                                        break;
                                                    }
                                                    default: {
                                                        list2 = $this$tail$iv.subList(1, $this$tail$iv.size());
                                                    }
                                                }
                                                List<Token> list5 = list = list2;
                                                List<ParseNode> list6 = list3;
                                                Token token3 = token2;
                                                ParseType parseType2 = parseType;
                                                parseNode = new ParseNode(parseType2, token3, list6, list5);
                                                return parseNode;
                                            }
                                        }
                                    }
                                    parseNode = ParseNode.copy$default(SqlParser.parseArgList$default(this.this$0, args2, AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null), callType, this.$name, null, null, 12, null).deriveExpected(TokenType.RIGHT_PAREN);
                                    return parseNode;
                                }
                                {
                                    this.this$0 = sqlParser;
                                    this.$this_parseFunctionCall = list;
                                    this.$name = token;
                                    super(3);
                                }
                            };
                            v0 = name.getText();
                            if (v0 == null) {
                                Intrinsics.throwNpe();
                            }
                            callName = v0;
                            var6_5 = SqlParser.$$delegatedProperties[0];
                            memoizedTail = LazyKt.lazy((Function0)new Function0<List<? extends Token>>($this$parseFunctionCall){
                                final /* synthetic */ List $this_parseFunctionCall;

                                @NotNull
                                public final List<Token> invoke() {
                                    List<Token> list;
                                    List $this$tail$iv = this.$this_parseFunctionCall;
                                    boolean $i$f$getTail = false;
                                    switch ($this$tail$iv.size()) {
                                        case 0: 
                                        case 1: {
                                            List<T> list2 = Collections.emptyList();
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
                                {
                                    this.$this_parseFunctionCall = list;
                                    super(0);
                                }
                            });
                            $this$head$iv = $this$parseFunctionCall;
                            $i$f$getHead = false;
                            v1 = CollectionsKt.firstOrNull($this$head$iv);
                            keywordText = v1 != null ? v1.getKeywordText() : null;
                            var8_7 = callName;
                            if (!Intrinsics.areEqual(var8_7, "count")) break block28;
                            $this$head$iv = $this$parseFunctionCall;
                            $i$f$getHead = false;
                            v2 = CollectionsKt.firstOrNull($this$head$iv);
                            if ((v2 != null ? v2.getType() : null) == TokenType.RIGHT_PAREN) {
                                v3 = TokenListExtensionsKt.err$default($this$parseFunctionCall, "Aggregate functions are always unary", ErrorCode.PARSE_NON_UNARY_AGREGATE_FUNCTION_CALL, null, 4, null);
                                throw null;
                            }
                            $this$head$iv = $this$parseFunctionCall;
                            $i$f$getHead = false;
                            v4 = CollectionsKt.firstOrNull($this$head$iv);
                            if ((v4 != null ? v4.getType() : null) == TokenType.STAR) {
                                $this$head$iv = $this$parseFunctionCall;
                                var16_20 = CollectionsKt.emptyList();
                                var15_21 = name;
                                var14_22 = ParseType.CALL_AGG_WILDCARD;
                                $i$f$getTail = false;
                                switch ($this$tail$iv.size()) {
                                    case 0: 
                                    case 1: {
                                        v5 = Collections.emptyList();
                                        v6 = v5;
                                        Intrinsics.checkExpressionValueIsNotNull(v5, "emptyList()");
                                        break;
                                    }
                                    default: {
                                        v6 = $this$tail$iv.subList(1, $this$tail$iv.size());
                                    }
                                }
                                var18_25 = var17_24 = v6;
                                var19_26 = var16_20;
                                var20_27 = var15_21;
                                var21_28 = var14_22;
                                v7 = new ParseNode(var21_28, var20_27, var19_26, var18_25).deriveExpected(TokenType.RIGHT_PAREN);
                            } else {
                                $this$head$iv = $this$parseFunctionCall;
                                $i$f$getHead = false;
                                v8 = CollectionsKt.firstOrNull($this$head$iv);
                                if ((v8 != null ? v8.getType() : null) == TokenType.KEYWORD && Intrinsics.areEqual(keywordText, "distinct")) {
                                    $this$head$iv = memoizedTail;
                                    $i$f$getHead = null;
                                    var11_29 = false;
                                    $this$head$iv = (List)$this$head$iv.getValue();
                                    $i$f$getHead = false;
                                    v9 = (Token)CollectionsKt.firstOrNull($this$head$iv);
                                    v10 = v9 != null ? v9.getType() : null;
                                    if (v10 != null) {
                                        switch (SqlParser$WhenMappings.$EnumSwitchMapping$39[v10.ordinal()]) {
                                            case 1: {
                                                v11 = TokenListExtensionsKt.err$default($this$parseFunctionCall, "COUNT(DISTINCT *) is not supported", ErrorCode.PARSE_UNSUPPORTED_CALL_WITH_STAR, null, 4, null);
                                                throw null;
                                            }
                                        }
                                    }
                                    $this$head$iv = memoizedTail;
                                    $i$f$getHead = null;
                                    var12_32 = this;
                                    var11_29 = false;
                                    var13_35 = $this$head$iv.getValue();
                                    v7 = ParseNode.copy$default(SqlParser.parseArgList$default(var12_32, (List)var13_35, AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null), ParseType.CALL_DISTINCT_AGG, name, null, null, 12, null).deriveExpected(TokenType.RIGHT_PAREN);
                                } else {
                                    $this$head$iv = $this$parseFunctionCall;
                                    $i$f$getHead = false;
                                    v12 = CollectionsKt.firstOrNull($this$head$iv);
                                    if ((v12 != null ? v12.getType() : null) == TokenType.KEYWORD && Intrinsics.areEqual(keywordText, "all")) {
                                        $this$head$iv = memoizedTail;
                                        $i$f$getHead = null;
                                        var11_30 = false;
                                        $this$head$iv = (List)$this$head$iv.getValue();
                                        $i$f$getHead = false;
                                        v13 = (Token)CollectionsKt.firstOrNull($this$head$iv);
                                        v14 = v13 != null ? v13.getType() : null;
                                        if (v14 != null) {
                                            switch (SqlParser$WhenMappings.$EnumSwitchMapping$40[v14.ordinal()]) {
                                                case 1: {
                                                    v15 = TokenListExtensionsKt.err$default($this$parseFunctionCall, "COUNT(ALL *) is not supported", ErrorCode.PARSE_UNSUPPORTED_CALL_WITH_STAR, null, 4, null);
                                                    throw null;
                                                }
                                            }
                                        }
                                        $this$head$iv = memoizedTail;
                                        $i$f$getHead = null;
                                        var12_33 = this;
                                        var11_30 = false;
                                        var13_36 = $this$head$iv.getValue();
                                        v7 = ParseNode.copy$default(SqlParser.parseArgList$default(var12_33, (List)var13_36, AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null), ParseType.CALL_AGG, name, null, null, 12, null).deriveExpected(TokenType.RIGHT_PAREN);
                                    } else {
                                        v7 = ParseNode.copy$default(SqlParser.parseArgList$default(this, $this$parseFunctionCall, AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null), ParseType.CALL_AGG, name, null, null, 12, null).deriveExpected(TokenType.RIGHT_PAREN);
                                    }
                                }
                            }
                            break block29;
                        }
                        if (!LexerConstantsKt.STANDARD_AGGREGATE_FUNCTIONS.contains(var8_7)) break block30;
                        $this$head$iv = $this$parseFunctionCall;
                        $i$f$getHead = false;
                        v16 = CollectionsKt.firstOrNull($this$head$iv);
                        if ((v16 != null ? v16.getType() : null) != TokenType.KEYWORD) break block31;
                        $this$head$iv = $this$parseFunctionCall;
                        $i$f$getHead = false;
                        v17 = CollectionsKt.firstOrNull($this$head$iv);
                        if (!Intrinsics.areEqual(v17 != null ? v17.getKeywordText() : null, "distinct")) break block31;
                        $this$head$iv = $this$parseFunctionCall;
                        var13_37 = callName;
                        var12_34 = $fun$parseCallArguments$1;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v18 = Collections.emptyList();
                                v19 = v18;
                                Intrinsics.checkExpressionValueIsNotNull(v18, "emptyList()");
                                break;
                            }
                            default: {
                                v19 = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        var14_23 = v19;
                        v20 = var12_34.invoke(var13_37, var14_23, ParseType.CALL_DISTINCT_AGG);
                        break block32;
                    }
                    $this$head$iv = $this$parseFunctionCall;
                    $i$f$getHead = false;
                    v21 = CollectionsKt.firstOrNull($this$head$iv);
                    if ((v21 != null ? v21.getType() : null) != TokenType.KEYWORD) ** GOTO lbl-1000
                    $this$head$iv = $this$parseFunctionCall;
                    $i$f$getHead = false;
                    v22 = CollectionsKt.firstOrNull($this$head$iv);
                    if (Intrinsics.areEqual(v22 != null ? v22.getKeywordText() : null, "all")) {
                        $this$head$iv = $this$parseFunctionCall;
                        var13_37 = callName;
                        var12_34 = $fun$parseCallArguments$1;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v23 = Collections.emptyList();
                                v24 /* !! */  = v23;
                                Intrinsics.checkExpressionValueIsNotNull(v23, "emptyList()");
                                break;
                            }
                            default: {
                                v24 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        var14_23 = v24 /* !! */ ;
                        v20 = var12_34.invoke(var13_37, var14_23, ParseType.CALL_AGG);
                    } else lbl-1000:
                    // 2 sources

                    {
                        v20 = call = $fun$parseCallArguments$1.invoke(callName, $this$parseFunctionCall, ParseType.CALL_AGG);
                    }
                }
                if (call.getChildren().size() != 1) {
                    v25 = TokenListExtensionsKt.err$default($this$parseFunctionCall, "Aggregate functions are always unary", ErrorCode.PARSE_NON_UNARY_AGREGATE_FUNCTION_CALL, null, 4, null);
                    throw null;
                }
                v7 = call;
                break block29;
            }
            v7 = $fun$parseCallArguments$1.invoke(callName, $this$parseFunctionCall, ParseType.CALL);
        }
        return v7;
    }

    private final ParseNode parseExec(@NotNull List<Token> $this$parseExec) {
        List<Object> list;
        List<Token> rem;
        List<Token> $this$head$iv = rem = $this$parseExec;
        boolean $i$f$getHead = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        if ((token != null ? token.getType() : null) == TokenType.EOF) {
            Void void_ = TokenListExtensionsKt.err$default(rem, "No stored procedure provided", ErrorCode.PARSE_NO_STORED_PROCEDURE_PROVIDED, null, 4, null);
            throw null;
        }
        Iterable $this$forEach$iv = rem;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String string;
            Token it = (Token)element$iv;
            boolean bl = false;
            if (it.getKeywordText() != null) {
                String string2;
                boolean bl2 = false;
                String string3 = string2;
                if (string3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string4 = string3.toLowerCase();
                string = string4;
                Intrinsics.checkExpressionValueIsNotNull(string4, "(this as java.lang.String).toLowerCase()");
            } else {
                string = null;
            }
            if (!Intrinsics.areEqual(string, "exec")) continue;
            Void void_ = TokenListExtensionsKt.err$default(it, "EXEC call found at unexpected location", ErrorCode.PARSE_UNEXPECTED_TERM, null, 4, null);
            throw null;
        }
        List<Token> $this$head$iv2 = rem;
        boolean $i$f$getHead2 = false;
        Token procedureName = CollectionsKt.firstOrNull($this$head$iv2);
        List<Token> $this$tail$iv = rem;
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
        $this$head$iv2 = rem = list;
        $i$f$getHead2 = false;
        Token token2 = CollectionsKt.firstOrNull($this$head$iv2);
        if ((token2 != null ? token2.getType() : null) == TokenType.EOF) {
            return new ParseNode(ParseType.EXEC, procedureName, CollectionsKt.emptyList(), rem);
        }
        $this$head$iv2 = rem;
        $i$f$getHead2 = false;
        Token token3 = CollectionsKt.firstOrNull($this$head$iv2);
        if ((token3 != null ? token3.getType() : null) == TokenType.LEFT_PAREN) {
            Void void_ = TokenListExtensionsKt.err$default(rem, "Unexpected " + (Object)((Object)TokenType.LEFT_PAREN) + " found following stored procedure call", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
            throw null;
        }
        return ParseNode.copy$default(SqlParser.parseArgList$default(this, rem, AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null), ParseType.EXEC, procedureName, null, null, 12, null);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseSubstring(@NotNull List<Token> $this$parseSubstring, Token name) {
        void positionExpr;
        ParseNode parseNode;
        List<Object> list;
        void $this$tail$iv;
        List<Token> rem;
        List<Token> $this$head$iv = rem = $this$parseSubstring;
        boolean $i$f$getHead22 = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        if ((token != null ? token.getType() : null) != TokenType.LEFT_PAREN) {
            PropertyValueMap pvmap = new PropertyValueMap(null, 1, null);
            pvmap.set(Property.EXPECTED_TOKEN_TYPE, TokenType.LEFT_PAREN);
            Void void_ = TokenListExtensionsKt.err(rem, "Expected " + (Object)((Object)TokenType.LEFT_PAREN), ErrorCode.PARSE_EXPECTED_LEFT_PAREN_BUILTIN_FUNCTION_CALL, pvmap);
            throw null;
        }
        List<Token> $i$f$getHead22 = $this$parseSubstring;
        SqlParser sqlParser = this;
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
        List<Object> list3 = list;
        ParseNode stringExpr = SqlParser.parseExpression$lang$default(sqlParser, list3, 0, 1, null);
        rem = stringExpr.getRemaining();
        boolean parseSql92Syntax = false;
        Object $this$head$iv2 = rem;
        boolean $i$f$getHead = false;
        Token token2 = CollectionsKt.firstOrNull($this$head$iv2);
        if (token2 == null) {
            Intrinsics.throwNpe();
        }
        if (Intrinsics.areEqual(token2.getKeywordText(), "from")) {
            parseSql92Syntax = true;
            parseNode = stringExpr.deriveExpectedKeyword("from");
        } else {
            $this$head$iv2 = rem;
            $i$f$getHead = false;
            Token token3 = CollectionsKt.firstOrNull($this$head$iv2);
            if (token3 == null) {
                Intrinsics.throwNpe();
            }
            if (token3.getType() == TokenType.COMMA) {
                parseNode = stringExpr.deriveExpected(TokenType.COMMA);
            } else {
                Void void_ = TokenListExtensionsKt.err$default(rem, "Expected " + (Object)((Object)TokenType.KEYWORD) + " 'from' OR " + (Object)((Object)TokenType.COMMA), ErrorCode.PARSE_EXPECTED_ARGUMENT_DELIMITER, null, 4, null);
                throw null;
            }
        }
        stringExpr = parseNode;
        Pair<ParseNode, Token> pair = SqlParser.parseExpression$lang$default(this, stringExpr.getRemaining(), 0, 1, null).deriveExpected(parseSql92Syntax ? TokenType.FOR : TokenType.COMMA, TokenType.RIGHT_PAREN);
        $this$head$iv2 = pair.component1();
        Token expectedToken = pair.component2();
        if (expectedToken.getType() == TokenType.RIGHT_PAREN) {
            return new ParseNode(ParseType.CALL, name, CollectionsKt.listOf(stringExpr, positionExpr), positionExpr.getRemaining());
        }
        rem = positionExpr.getRemaining();
        ParseNode lengthExpr = SqlParser.parseExpression$lang$default(this, rem, 0, 1, null).deriveExpected(TokenType.RIGHT_PAREN);
        return new ParseNode(ParseType.CALL, name, CollectionsKt.listOf(stringExpr, positionExpr, lengthExpr), lengthExpr.getRemaining());
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseTrim(@NotNull List<Token> $this$parseTrim, Token name) {
        block36: {
            block35: {
                $this$head$iv = $this$parseTrim;
                $i$f$getHead = false;
                v0 = CollectionsKt.firstOrNull($this$head$iv);
                if ((v0 != null ? v0.getType() : null) != TokenType.LEFT_PAREN) {
                    v1 = TokenListExtensionsKt.err$default($this$parseTrim, "Expected " + (Object)TokenType.LEFT_PAREN, ErrorCode.PARSE_EXPECTED_LEFT_PAREN_BUILTIN_FUNCTION_CALL, null, 4, null);
                    throw null;
                }
                $this$head$iv = new Ref.ObjectRef<T>();
                $i$f$getHead = $this$parseTrim;
                var11_6 = $this$head$iv;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v2 = Collections.emptyList();
                        v3 /* !! */  = v2;
                        Intrinsics.checkExpressionValueIsNotNull(v2, "emptyList()");
                        break;
                    }
                    default: {
                        v3 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var12_9 /* !! */  = v3 /* !! */ ;
                var11_6.element = var12_9 /* !! */ ;
                $i$f$getTail = false;
                arguments = new ArrayList<E>();
                $fun$parseArgument$1 = new Function1<Function1<? super ParseNode, ? extends ParseNode>, List<? extends Token>>(this, (Ref.ObjectRef)rem, arguments){
                    final /* synthetic */ SqlParser this$0;
                    final /* synthetic */ Ref.ObjectRef $rem;
                    final /* synthetic */ List $arguments;

                    @NotNull
                    public final List<Token> invoke(@NotNull Function1<? super ParseNode, ParseNode> block) {
                        Intrinsics.checkParameterIsNotNull(block, "block");
                        ParseNode node = block.invoke(SqlParser.parseExpression$lang$default(this.this$0, (List)this.$rem.element, 0, 1, null));
                        this.$arguments.add(node);
                        return node.getRemaining();
                    }

                    public static /* synthetic */ List invoke$default(parseTrim.1 var0, Function1 function1, int n, Object object) {
                        if ((n & 1) != 0) {
                            function1 = parseTrim.1.INSTANCE;
                        }
                        return var0.invoke(function1);
                    }
                    {
                        this.this$0 = sqlParser;
                        this.$rem = objectRef;
                        this.$arguments = list;
                        super(1);
                    }
                };
                $this$head$iv = (List)rem.element;
                $i$f$getHead = false;
                maybeTrimSpec = (Token)CollectionsKt.firstOrNull($this$head$iv);
                v4 = maybeTrimSpec;
                if ((v4 != null ? v4.getType() : null) != TokenType.IDENTIFIER) ** GOTO lbl-1000
                v5 = LexerConstantsKt.TRIM_SPECIFICATION_KEYWORDS;
                v6 = maybeTrimSpec.getText();
                if (v6 != null) {
                    $i$f$getHead = v6;
                    var11_6 = v5;
                    var9_18 = false;
                    v7 = $i$f$getHead;
                    if (v7 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    v8 = v7.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(v8, "(this as java.lang.String).toLowerCase()");
                    var12_9 /* !! */  = v8;
                    v5 = var11_6;
                    v9 = var12_9 /* !! */ ;
                } else {
                    v9 = null;
                }
                if (CollectionsKt.contains(v5, v9)) {
                    var15_21 = Token.copy$default(maybeTrimSpec, TokenType.TRIM_SPECIFICATION, null, null, 6, null);
                    var14_22 /* !! */  = ParseType.ATOM;
                    var11_6 = arguments;
                    $i$f$getHead = false;
                    var16_23 /* !! */  = CollectionsKt.emptyList();
                    $i$f$getHead = (List)rem.element;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v10 = Collections.emptyList();
                            v11 = v10;
                            Intrinsics.checkExpressionValueIsNotNull(v10, "emptyList()");
                            break;
                        }
                        default: {
                            v11 = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var18_25 = var17_24 = v11;
                    var19_26 = var16_23 /* !! */ ;
                    var20_27 = var15_21;
                    var21_28 = var14_22 /* !! */ ;
                    var11_6.add(new ParseNode(var21_28, var20_27, var19_26, var18_25));
                    $this$tail$iv = (List)rem.element;
                    var11_6 = rem;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v12 = Collections.emptyList();
                            v13 /* !! */  = v12;
                            Intrinsics.checkExpressionValueIsNotNull(v12, "emptyList()");
                            break;
                        }
                        default: {
                            v13 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var12_9 /* !! */  = v13 /* !! */ ;
                    var11_6.element = var12_9 /* !! */ ;
                    v14 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v14 = hasSpecification = false;
                }
                if (!hasSpecification) break block35;
                $this$tail$iv = (List)rem.element;
                var11_6 = rem;
                $i$f$getHead = false;
                var12_9 /* !! */  = CollectionsKt.firstOrNull($this$head$iv);
                v15 = var11_6;
                v16 = (Token)var12_9 /* !! */ ;
                v17 = v16 != null ? v16.getKeywordText() : null;
                if (v17 == null) ** GOTO lbl-1000
                $this$head$iv = v17;
                switch ($this$head$iv.hashCode()) {
                    case 3151786: {
                        if ($this$head$iv.equals("from")) {
                            $i$f$getHead = (List)rem.element;
                            var11_6 = v15;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v18 = Collections.emptyList();
                                    v19 /* !! */  = v18;
                                    Intrinsics.checkExpressionValueIsNotNull(v18, "emptyList()");
                                    break;
                                }
                                default: {
                                    v19 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var12_9 /* !! */  = v19 /* !! */ ;
                            v15 = var11_6;
                            v20 /* !! */  = var12_9 /* !! */ ;
                            break;
                        }
                    }
                    default: lbl-1000:
                    // 2 sources

                    {
                        v20 /* !! */  = $fun$parseArgument$1.invoke((Function1<ParseNode, ParseNode>)((Function1<? super ParseNode, ParseNode>)parseTrim.2.INSTANCE));
                    }
                }
                v15.element = v20 /* !! */ ;
                rem.element = parseTrim.1.invoke$default($fun$parseArgument$1, null, 1, null);
                break block36;
            }
            $this$head$iv = (List)rem.element;
            $i$f$getHead = false;
            v21 = (Token)CollectionsKt.firstOrNull($this$head$iv);
            if (Intrinsics.areEqual(v21 != null ? v21.getKeywordText() : null, "from")) {
                $this$head$iv = (List)rem.element;
                var11_6 = rem;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        v22 = Collections.emptyList();
                        v23 /* !! */  = v22;
                        Intrinsics.checkExpressionValueIsNotNull(v22, "emptyList()");
                        break;
                    }
                    default: {
                        v23 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                var12_9 /* !! */  = v23 /* !! */ ;
                var11_6.element = var12_9 /* !! */ ;
                rem.element = parseTrim.1.invoke$default($fun$parseArgument$1, null, 1, null);
            } else {
                rem.element = parseTrim.1.invoke$default($fun$parseArgument$1, null, 1, null);
                $this$head$iv = (List)rem.element;
                $i$f$getHead = false;
                v24 = (Token)CollectionsKt.firstOrNull($this$head$iv);
                if (Intrinsics.areEqual(v24 != null ? v24.getKeywordText() : null, "from")) {
                    $this$head$iv = (List)rem.element;
                    var11_6 = rem;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v25 = Collections.emptyList();
                            v26 /* !! */  = v25;
                            Intrinsics.checkExpressionValueIsNotNull(v25, "emptyList()");
                            break;
                        }
                        default: {
                            v26 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    var12_9 /* !! */  = v26 /* !! */ ;
                    var11_6.element = var12_9 /* !! */ ;
                    rem.element = parseTrim.1.invoke$default($fun$parseArgument$1, null, 1, null);
                }
            }
        }
        $this$head$iv = (List)rem.element;
        $i$f$getHead = false;
        v27 = (Token)CollectionsKt.firstOrNull($this$head$iv);
        if ((v27 != null ? v27.getType() : null) != TokenType.RIGHT_PAREN) {
            v28 = TokenListExtensionsKt.err$default((List)rem.element, "Expected " + (Object)TokenType.RIGHT_PAREN, ErrorCode.PARSE_EXPECTED_RIGHT_PAREN_BUILTIN_FUNCTION_CALL, null, 4, null);
            throw null;
        }
        $this$head$iv = (List)rem.element;
        var15_21 = arguments;
        var14_22 /* !! */  = name;
        var13_30 = ParseType.CALL;
        $i$f$getTail = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                v29 = Collections.emptyList();
                v30 /* !! */  = v29;
                Intrinsics.checkExpressionValueIsNotNull(v29, "emptyList()");
                break;
            }
            default: {
                v30 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        var22_31 /* !! */  = var16_23 /* !! */  = v30 /* !! */ ;
        var23_32 = var15_21;
        var24_33 /* !! */  = var14_22 /* !! */ ;
        var25_34 = var13_30;
        return new ParseNode(var25_34, (Token)var24_33 /* !! */ , (List<ParseNode>)var23_32, var22_31 /* !! */ );
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private final ParseNode parseDatePart(@NotNull List<Token> $this$parseDatePart) {
        Object $this$head$iv222 = $this$parseDatePart;
        boolean $i$f$getHead = false;
        Token maybeDatePart = CollectionsKt.firstOrNull($this$head$iv222);
        Token token = maybeDatePart;
        if ((token != null ? token.getType() : null) == TokenType.IDENTIFIER) {
            String string;
            Iterable iterable = LexerConstantsKt.getDATE_PART_KEYWORDS();
            String string2 = maybeDatePart.getText();
            if (string2 != null) {
                $this$head$iv222 = string2;
                Iterable iterable2 = iterable;
                $i$f$getHead = false;
                Object object = $this$head$iv222;
                if (object == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string3 = ((String)object).toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
                String string4 = string3;
                iterable = iterable2;
                string = string4;
            } else {
                string = null;
            }
            if (CollectionsKt.contains(iterable, string)) {
                List<Token> list;
                List<Token> list2;
                void $this$tail$iv;
                Token token2 = Token.copy$default(maybeDatePart, TokenType.DATE_PART, null, null, 6, null);
                ParseType parseType = ParseType.ATOM;
                boolean $this$head$iv222 = false;
                List<ParseNode> list3 = CollectionsKt.emptyList();
                List<Token> $this$head$iv222 = $this$parseDatePart;
                boolean $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        List list4 = Collections.emptyList();
                        list2 = list4;
                        Intrinsics.checkExpressionValueIsNotNull(list4, "emptyList()");
                        break;
                    }
                    default: {
                        list2 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                List<Token> list5 = list = list2;
                List<ParseNode> list6 = list3;
                Token token3 = token2;
                ParseType parseType2 = parseType;
                return new ParseNode(parseType2, token3, list6, list5);
            }
        }
        Void void_ = TokenListExtensionsKt.err$default(maybeDatePart, "Expected one of: " + LexerConstantsKt.getDATE_PART_KEYWORDS(), ErrorCode.PARSE_EXPECTED_DATE_PART, null, 4, null);
        throw null;
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseExtract(@NotNull List<Token> $this$parseExtract, Token name) {
        List<Token> list;
        void $this$tail$iv;
        List<Token> $this$head$iv = $this$parseExtract;
        boolean $i$f$getHead22 = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        if ((token != null ? token.getType() : null) != TokenType.LEFT_PAREN) {
            Void void_ = TokenListExtensionsKt.err$default($this$parseExtract, "Expected " + (Object)((Object)TokenType.LEFT_PAREN), ErrorCode.PARSE_EXPECTED_LEFT_PAREN_BUILTIN_FUNCTION_CALL, null, 4, null);
            throw null;
        }
        List<Token> $i$f$getHead22 = $this$parseExtract;
        SqlParser sqlParser = this;
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
        List<Token> list3 = list;
        ParseNode datePart = sqlParser.parseDatePart(list3).deriveExpectedKeyword("from");
        List<Token> rem = datePart.getRemaining();
        ParseNode dateTimeType = SqlParser.parseExpression$lang$default(this, rem, 0, 1, null).deriveExpected(TokenType.RIGHT_PAREN);
        return new ParseNode(ParseType.CALL, name, CollectionsKt.listOf(datePart, dateTimeType), dateTimeType.getRemaining());
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseDate(@NotNull List<Token> $this$parseDate) {
        List<Token> list;
        List<Token> list2;
        void $this$tail$iv;
        void $this$head$iv322;
        Token dateStringToken;
        List<Token> $this$head$iv = $this$parseDate;
        boolean $i$f$getHead = false;
        Token token = dateStringToken = CollectionsKt.firstOrNull($this$head$iv);
        if ((token != null ? token.getValue() : null) == null || dateStringToken.getType() != TokenType.LITERAL || !IonValueExtensionsKt.isText(dateStringToken.getValue())) {
            $this$head$iv = $this$parseDate;
            StringBuilder stringBuilder = new StringBuilder().append("Expected date string followed by the keyword DATE, found ");
            List<Token> list3 = $this$parseDate;
            $i$f$getHead = false;
            Token token2 = CollectionsKt.firstOrNull($this$head$iv);
            Object object = token2;
            Void void_ = TokenListExtensionsKt.err$default(list3, stringBuilder.append((Object)(object != null && (object = ((Token)object).getValue()) != null ? object.getType() : null)).toString(), ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
            throw null;
        }
        String dateString = IonValueExtensionsKt.stringValue(dateStringToken.getValue());
        Regex regex = TimeExtensionsKt.getDATE_PATTERN_REGEX();
        String string = dateString;
        if (string == null) {
            Intrinsics.throwNpe();
        }
        if (!regex.matches(string)) {
            Void void_ = TokenListExtensionsKt.err$default($this$parseDate, "Expected DATE string to be of the format yyyy-MM-dd", ErrorCode.PARSE_INVALID_DATE_STRING, null, 4, null);
            throw null;
        }
        try {
            LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            String string2 = e.getLocalizedMessage();
            Intrinsics.checkExpressionValueIsNotNull(string2, "e.localizedMessage");
            Void void_ = TokenListExtensionsKt.err$default($this$parseDate, string2, ErrorCode.PARSE_INVALID_DATE_STRING, null, 4, null);
            throw null;
        }
        List<Token> e = $this$parseDate;
        ParseType parseType = ParseType.DATE;
        boolean $i$f$getHead2 = false;
        Object object = CollectionsKt.firstOrNull($this$head$iv322);
        object = (Token)object;
        boolean $this$head$iv322 = false;
        List<ParseNode> list4 = CollectionsKt.emptyList();
        List<Token> $this$head$iv322 = $this$parseDate;
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
        List<ParseNode> list7 = list4;
        Object object2 = object;
        ParseType parseType2 = parseType;
        return new ParseNode(parseType2, (Token)object2, list7, list6);
    }

    private final ParseNode parseOptionalPrecision(@NotNull List<Token> $this$parseOptionalPrecision) {
        ParseNode parseNode;
        block23: {
            block20: {
                List<Token> list;
                List<Token> list2;
                List<Object> list3;
                List<Object> rem;
                block22: {
                    boolean $i$f$getHead;
                    List<Object> $this$head$iv;
                    block21: {
                        List<Object> list4;
                        List<Token> $this$head$iv2 = $this$parseOptionalPrecision;
                        boolean $i$f$getHead2 = false;
                        Token token = CollectionsKt.firstOrNull($this$head$iv2);
                        if ((token != null ? token.getType() : null) != TokenType.LEFT_PAREN) break block20;
                        List<Token> $this$tail$iv = $this$parseOptionalPrecision;
                        boolean $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                List list5 = Collections.emptyList();
                                list4 = list5;
                                Intrinsics.checkExpressionValueIsNotNull(list5, "emptyList()");
                                break;
                            }
                            default: {
                                list4 = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        rem = list4;
                        $this$head$iv = rem;
                        $i$f$getHead = false;
                        if (CollectionsKt.firstOrNull($this$head$iv) == null) break block21;
                        $this$head$iv = rem;
                        $i$f$getHead = false;
                        Object t = CollectionsKt.firstOrNull($this$head$iv);
                        if (t == null) {
                            Intrinsics.throwNpe();
                        }
                        if (((Token)t).getType() != TokenType.LITERAL) break block21;
                        $this$head$iv = rem;
                        $i$f$getHead = false;
                        Object t2 = CollectionsKt.firstOrNull($this$head$iv);
                        if (t2 == null) {
                            Intrinsics.throwNpe();
                        }
                        IonValue ionValue2 = ((Token)t2).getValue();
                        if (ionValue2 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (!IonValueExtensionsKt.isUnsignedInteger(ionValue2)) break block21;
                        $this$head$iv = rem;
                        $i$f$getHead = false;
                        Object t3 = CollectionsKt.firstOrNull($this$head$iv);
                        if (t3 == null) {
                            Intrinsics.throwNpe();
                        }
                        IonValue ionValue3 = ((Token)t3).getValue();
                        if (ionValue3 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (IonValueExtensionsKt.longValue(ionValue3) < 0L) break block21;
                        $this$head$iv = rem;
                        $i$f$getHead = false;
                        Object t4 = CollectionsKt.firstOrNull($this$head$iv);
                        if (t4 == null) {
                            Intrinsics.throwNpe();
                        }
                        IonValue ionValue4 = ((Token)t4).getValue();
                        if (ionValue4 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (IonValueExtensionsKt.longValue(ionValue4) <= (long)9) break block22;
                    }
                    $this$head$iv = rem;
                    $i$f$getHead = false;
                    Void void_ = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull($this$head$iv), "Expected integer value between 0 and 9 for precision", ErrorCode.PARSE_INVALID_PRECISION_FOR_TIME, null, 4, null);
                    throw null;
                }
                List<Object> $this$head$iv = rem;
                boolean $i$f$getHead = false;
                Token precision = (Token)CollectionsKt.firstOrNull($this$head$iv);
                List<Object> $this$tail$iv = rem;
                boolean $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        List list6 = Collections.emptyList();
                        list3 = list6;
                        Intrinsics.checkExpressionValueIsNotNull(list6, "emptyList()");
                        break;
                    }
                    default: {
                        list3 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                $this$head$iv = rem = list3;
                $i$f$getHead = false;
                Token token = (Token)CollectionsKt.firstOrNull($this$head$iv);
                if ((token != null ? token.getType() : null) != TokenType.RIGHT_PAREN) {
                    $this$head$iv = rem;
                    $i$f$getHead = false;
                    Void void_ = TokenListExtensionsKt.errExpectedTokenType((Token)CollectionsKt.firstOrNull($this$head$iv), TokenType.RIGHT_PAREN);
                    throw null;
                }
                Token token2 = precision;
                ParseType parseType = ParseType.PRECISION;
                boolean $this$head$iv32 = false;
                List<ParseNode> list7 = CollectionsKt.emptyList();
                List<Object> $this$head$iv32 = rem;
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        List list8 = Collections.emptyList();
                        list2 = list8;
                        Intrinsics.checkExpressionValueIsNotNull(list8, "emptyList()");
                        break;
                    }
                    default: {
                        list2 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                List<Token> list9 = list = list2;
                List<ParseNode> list10 = list7;
                Token token3 = token2;
                ParseType parseType2 = parseType;
                parseNode = new ParseNode(parseType2, token3, list10, list9);
                break block23;
            }
            Token token = null;
            ParseType parseType = ParseType.PRECISION;
            boolean bl = false;
            List<ParseNode> list = CollectionsKt.emptyList();
            List<Token> list11 = $this$parseOptionalPrecision;
            List<ParseNode> list12 = list;
            Token token4 = token;
            ParseType parseType3 = parseType;
            parseNode = new ParseNode(parseType3, token4, list12, list11);
        }
        return parseNode;
    }

    private final Pair<List<Token>, Boolean> checkForOptionalTimeZone(@NotNull List<Token> $this$checkForOptionalTimeZone) {
        List<Token> $this$head$iv = $this$checkForOptionalTimeZone;
        boolean $i$f$getHead = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        if ((token != null ? token.getType() : null) == TokenType.KEYWORD) {
            List<Token> rem = TokenListExtensionsKt.tailExpectedKeyword(TokenListExtensionsKt.tailExpectedKeyword(TokenListExtensionsKt.tailExpectedKeyword($this$checkForOptionalTimeZone, "with"), "time"), "zone");
            return new Pair<List<Token>, Boolean>(rem, true);
        }
        return new Pair<List<Token>, Boolean>($this$checkForOptionalTimeZone, false);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseTime(@NotNull List<Token> $this$parseTime) {
        List<Token> list;
        List<Token> list2;
        void $this$tail$iv;
        List $this$head$iv;
        Object object;
        Token token;
        Token timeStringToken;
        void remainingAfterOptionalTimeZone;
        void rem;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = $this$parseTime;
        Function1<String, Unit> $fun$tryLocalTimeParsing$1 = new Function1<String, Unit>((Ref.ObjectRef)rem){
            final /* synthetic */ Ref.ObjectRef $rem;

            public final void invoke(@Nullable String time) {
                try {
                    LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
                } catch (DateTimeParseException e) {
                    List $this$head$iv = (List)this.$rem.element;
                    boolean $i$f$getHead = false;
                    Token token = (Token)CollectionsKt.firstOrNull($this$head$iv);
                    String string = e.getLocalizedMessage();
                    Intrinsics.checkExpressionValueIsNotNull(string, "e.localizedMessage");
                    Void void_ = TokenListExtensionsKt.err$default(token, string, ErrorCode.PARSE_INVALID_TIME_STRING, null, 4, null);
                    throw null;
                }
            }
            {
                this.$rem = objectRef;
                super(1);
            }
        };
        ParseNode precision = this.parseOptionalPrecision((List)rem.element);
        rem.element = precision.getRemaining();
        Pair<List<Token>, Boolean> pair = this.checkForOptionalTimeZone((List)rem.element);
        List<Token> list3 = pair.component1();
        boolean isTimeZoneSpecified = pair.component2();
        rem.element = remainingAfterOptionalTimeZone;
        List $this$head$iv2 = (List)rem.element;
        boolean $i$f$getHead = false;
        Token token2 = timeStringToken = (Token)CollectionsKt.firstOrNull($this$head$iv2);
        if ((token2 != null ? token2.getValue() : null) == null || timeStringToken.getType() != TokenType.LITERAL || !IonValueExtensionsKt.isText(timeStringToken.getValue())) {
            $this$head$iv2 = (List)rem.element;
            $i$f$getHead = false;
            Token token3 = (Token)CollectionsKt.firstOrNull($this$head$iv2);
            $this$head$iv2 = (List)rem.element;
            StringBuilder stringBuilder = new StringBuilder().append("Expected time string followed by the keyword TIME OR TIME WITH TIME ZONE, found ");
            Token token4 = token3;
            $i$f$getHead = false;
            Object t = CollectionsKt.firstOrNull($this$head$iv2);
            Object object2 = (Token)t;
            Void void_ = TokenListExtensionsKt.err$default(token4, stringBuilder.append((Object)(object2 != null && (object2 = ((Token)object2).getValue()) != null ? object2.getType() : null)).toString(), ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
            throw null;
        }
        String string = IonValueExtensionsKt.stringValue(timeStringToken.getValue());
        String timeString = string != null ? StringsKt.replace$default(string, " ", "", false, 4, null) : null;
        Regex regex = TimeExtensionsKt.getGenericTimeRegex();
        String string2 = timeString;
        if (string2 == null) {
            Intrinsics.throwNpe();
        }
        if (!regex.matches(string2)) {
            List $this$head$iv3 = (List)rem.element;
            boolean $i$f$getHead2 = false;
            Void void_ = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull($this$head$iv3), "Invalid format for time string. Expected format is \"TIME [(p)] [WITH TIME ZONE] HH:MM:SS[.ddddd...][+|-HH:MM]\"", ErrorCode.PARSE_INVALID_TIME_STRING, null, 4, null);
            throw null;
        }
        String newTimeString = timeString;
        boolean $i$f$getHead2 = isTimeZoneSpecified;
        if (!$i$f$getHead2) {
            $fun$tryLocalTimeParsing$1.invoke(timeString);
        } else if ($i$f$getHead2) {
            try {
                OffsetTime.parse(timeString, DateTimeFormatter.ISO_TIME);
            } catch (DateTimeParseException e) {
                if (TimeExtensionsKt.getTimeWithoutTimeZoneRegex().matches(timeString)) {
                    $fun$tryLocalTimeParsing$1.invoke(timeString);
                    StringBuilder stringBuilder = new StringBuilder().append(timeString);
                    ZoneOffset zoneOffset = TimeExtensionsKt.getDEFAULT_TIMEZONE_OFFSET();
                    Intrinsics.checkExpressionValueIsNotNull(zoneOffset, "DEFAULT_TIMEZONE_OFFSET");
                    newTimeString = stringBuilder.append(TimeExtensionsKt.getOffsetHHmm(zoneOffset)).toString();
                }
                List $this$head$iv4 = (List)rem.element;
                boolean $i$f$getHead3 = false;
                Token token5 = (Token)CollectionsKt.firstOrNull($this$head$iv4);
                String string3 = e.getLocalizedMessage();
                Intrinsics.checkExpressionValueIsNotNull(string3, "e.localizedMessage");
                Void void_ = TokenListExtensionsKt.err$default(token5, string3, ErrorCode.PARSE_INVALID_TIME_STRING, null, 4, null);
                throw null;
            }
        }
        if ((token = precision.getToken()) == null) {
            token = new Token(TokenType.LITERAL, this.ion.newInt(TimeExtensionsKt.getPrecisionFromTimeString(newTimeString)), timeStringToken.getSpan());
        }
        Token precisionOfValue = token;
        List e = (List)rem.element;
        ParseType parseType = isTimeZoneSpecified ? ParseType.TIME_WITH_TIME_ZONE : ParseType.TIME;
        boolean $i$f$getHead4 = false;
        Object t = object = CollectionsKt.firstOrNull($this$head$iv);
        if (t == null) {
            Intrinsics.throwNpe();
        }
        $this$head$iv = (List)rem.element;
        List<ParseNode> list4 = CollectionsKt.listOf(ParseNode.copy$default(precision, null, precisionOfValue, null, null, 13, null));
        object = Token.copy$default((Token)t, null, this.ion.newString(newTimeString), null, 5, null);
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
        List<ParseNode> list7 = list4;
        Object object3 = object;
        ParseType parseType2 = parseType;
        return new ParseNode(parseType2, (Token)object3, list7, list6);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseDateAddOrDateDiff(@NotNull List<Token> $this$parseDateAddOrDateDiff, Token name) {
        List<Token> list;
        void $this$tail$iv;
        List<Token> $this$head$iv = $this$parseDateAddOrDateDiff;
        boolean $i$f$getHead22 = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        if ((token != null ? token.getType() : null) != TokenType.LEFT_PAREN) {
            Void void_ = TokenListExtensionsKt.err$default($this$parseDateAddOrDateDiff, "Expected " + (Object)((Object)TokenType.LEFT_PAREN), ErrorCode.PARSE_EXPECTED_LEFT_PAREN_BUILTIN_FUNCTION_CALL, null, 4, null);
            throw null;
        }
        List<Token> $i$f$getHead22 = $this$parseDateAddOrDateDiff;
        SqlParser sqlParser = this;
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
        List<Token> list3 = list;
        ParseNode datePart = sqlParser.parseDatePart(list3).deriveExpected(TokenType.COMMA);
        ParseNode timestamp1 = SqlParser.parseExpression$lang$default(this, datePart.getRemaining(), 0, 1, null).deriveExpected(TokenType.COMMA);
        ParseNode timestamp2 = SqlParser.parseExpression$lang$default(this, timestamp1.getRemaining(), 0, 1, null).deriveExpected(TokenType.RIGHT_PAREN);
        return new ParseNode(ParseType.CALL, name, CollectionsKt.listOf(datePart, timestamp1, timestamp2), timestamp2.getRemaining());
    }

    private final ParseNode parseLet(@NotNull List<Token> $this$parseLet) {
        List<Object> list;
        List<Object> list2;
        List<Object> list3;
        ArrayList<ParseNode> letClauses = new ArrayList<ParseNode>();
        List<Token> $this$tail$iv = $this$parseLet;
        boolean $i$f$getTail = false;
        switch ($this$tail$iv.size()) {
            case 0: 
            case 1: {
                List list4 = Collections.emptyList();
                list3 = list4;
                Intrinsics.checkExpressionValueIsNotNull(list4, "emptyList()");
                break;
            }
            default: {
                list3 = $this$tail$iv.subList(1, $this$tail$iv.size());
            }
        }
        List<Token> rem = list3;
        ParseNode child2 = SqlParser.parseExpression$lang$default(this, rem, 0, 1, null);
        rem = child2.getRemaining();
        List<Object> $this$head$iv = rem;
        boolean $i$f$getHead = false;
        Token token = (Token)CollectionsKt.firstOrNull($this$head$iv);
        if ((token != null ? token.getType() : null) != TokenType.AS) {
            $this$head$iv = rem;
            $i$f$getHead = false;
            Void void_ = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull($this$head$iv), "Expected " + (Object)((Object)TokenType.AS) + " following " + (Object)((Object)ParseType.LET) + " expr", ErrorCode.PARSE_EXPECTED_AS_FOR_LET, null, 4, null);
            throw null;
        }
        List<Object> $this$tail$iv2 = rem;
        boolean $i$f$getTail2 = false;
        switch ($this$tail$iv2.size()) {
            case 0: 
            case 1: {
                List list5 = Collections.emptyList();
                list2 = list5;
                Intrinsics.checkExpressionValueIsNotNull(list5, "emptyList()");
                break;
            }
            default: {
                list2 = $this$tail$iv2.subList(1, $this$tail$iv2.size());
            }
        }
        rem = list2;
        $this$head$iv = rem;
        $i$f$getHead = false;
        Object object = (Token)CollectionsKt.firstOrNull($this$head$iv);
        if (object == null || (object = ((Token)object).getType()) == null || !((TokenType)((Object)object)).isIdentifier()) {
            $this$head$iv = rem;
            $i$f$getHead = false;
            Void void_ = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull($this$head$iv), "Expected identifier for " + (Object)((Object)TokenType.AS) + "-alias", ErrorCode.PARSE_EXPECTED_IDENT_FOR_ALIAS, null, 4, null);
            throw null;
        }
        List<Object> $this$head$iv2 = rem;
        boolean $i$f$getHead2 = false;
        Token name = (Token)CollectionsKt.firstOrNull($this$head$iv2);
        List<Object> $this$tail$iv3 = rem;
        boolean $i$f$getTail3 = false;
        switch ($this$tail$iv3.size()) {
            case 0: 
            case 1: {
                List list6 = Collections.emptyList();
                list = list6;
                Intrinsics.checkExpressionValueIsNotNull(list6, "emptyList()");
                break;
            }
            default: {
                list = $this$tail$iv3.subList(1, $this$tail$iv3.size());
            }
        }
        rem = list;
        letClauses.add(new ParseNode(ParseType.AS_ALIAS, name, CollectionsKt.listOf(child2), rem));
        while (true) {
            List<Object> list7;
            List<Object> list8;
            List<Object> list9;
            $this$head$iv2 = rem;
            $i$f$getHead2 = false;
            Token token2 = (Token)CollectionsKt.firstOrNull($this$head$iv2);
            if ((token2 != null ? token2.getType() : null) != TokenType.COMMA) break;
            $this$tail$iv3 = rem;
            $i$f$getTail3 = false;
            switch ($this$tail$iv3.size()) {
                case 0: 
                case 1: {
                    List list10 = Collections.emptyList();
                    list9 = list10;
                    Intrinsics.checkExpressionValueIsNotNull(list10, "emptyList()");
                    break;
                }
                default: {
                    list9 = $this$tail$iv3.subList(1, $this$tail$iv3.size());
                }
            }
            rem = list9;
            child2 = SqlParser.parseExpression$lang$default(this, rem, 0, 1, null);
            rem = child2.getRemaining();
            $this$head$iv2 = rem;
            $i$f$getHead2 = false;
            Token token3 = (Token)CollectionsKt.firstOrNull($this$head$iv2);
            if ((token3 != null ? token3.getType() : null) != TokenType.AS) {
                $this$head$iv2 = rem;
                $i$f$getHead2 = false;
                Void void_ = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull($this$head$iv2), "Expected " + (Object)((Object)TokenType.AS) + " following " + (Object)((Object)ParseType.LET) + " expr", ErrorCode.PARSE_EXPECTED_AS_FOR_LET, null, 4, null);
                throw null;
            }
            $this$tail$iv3 = rem;
            $i$f$getTail3 = false;
            switch ($this$tail$iv3.size()) {
                case 0: 
                case 1: {
                    List list11 = Collections.emptyList();
                    list8 = list11;
                    Intrinsics.checkExpressionValueIsNotNull(list11, "emptyList()");
                    break;
                }
                default: {
                    list8 = $this$tail$iv3.subList(1, $this$tail$iv3.size());
                }
            }
            rem = list8;
            $this$head$iv2 = rem;
            $i$f$getHead2 = false;
            Object object2 = (Token)CollectionsKt.firstOrNull($this$head$iv2);
            if (object2 == null || (object2 = ((Token)object2).getType()) == null || !((TokenType)((Object)object2)).isIdentifier()) {
                $this$head$iv2 = rem;
                $i$f$getHead2 = false;
                Void void_ = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull($this$head$iv2), "Expected identifier for " + (Object)((Object)TokenType.AS) + "-alias", ErrorCode.PARSE_EXPECTED_IDENT_FOR_ALIAS, null, 4, null);
                throw null;
            }
            $this$head$iv2 = rem;
            $i$f$getHead2 = false;
            name = (Token)CollectionsKt.firstOrNull($this$head$iv2);
            $this$tail$iv3 = rem;
            $i$f$getTail3 = false;
            switch ($this$tail$iv3.size()) {
                case 0: 
                case 1: {
                    List list12 = Collections.emptyList();
                    list7 = list12;
                    Intrinsics.checkExpressionValueIsNotNull(list12, "emptyList()");
                    break;
                }
                default: {
                    list7 = $this$tail$iv3.subList(1, $this$tail$iv3.size());
                }
            }
            rem = list7;
            letClauses.add(new ParseNode(ParseType.AS_ALIAS, name, CollectionsKt.listOf(child2), rem));
        }
        return new ParseNode(ParseType.LET, null, (List<ParseNode>)letClauses, rem);
    }

    private final ParseNode parseListLiteral(@NotNull List<Token> $this$parseListLiteral) {
        return ParseNode.copy$default(SqlParser.parseArgList$default(this, $this$parseListLiteral, AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null), ParseType.LIST, null, null, null, 14, null).deriveExpected(TokenType.RIGHT_BRACKET);
    }

    private final ParseNode parseBagLiteral(@NotNull List<Token> $this$parseBagLiteral) {
        return ParseNode.copy$default(SqlParser.parseArgList$default(this, $this$parseBagLiteral, AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null), ParseType.BAG, null, null, null, 14, null).deriveExpected(TokenType.RIGHT_DOUBLE_ANGLE_BRACKET);
    }

    private final ParseNode parseStructLiteral(@NotNull List<Token> $this$parseStructLiteral) {
        return ParseNode.copy$default(SqlParser.parseArgList$default(this, $this$parseStructLiteral, AliasSupportType.NONE, ArgListMode.STRUCT_LITERAL_ARG_LIST, 0, 4, null), ParseType.STRUCT, null, null, null, 14, null).deriveExpected(TokenType.RIGHT_CURLY);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseTableValues(@NotNull List<Token> $this$parseTableValues) {
        void $this$parseDelimitedList$iv$iv;
        List<Token> $this$parseCommaList$iv = $this$parseTableValues;
        SqlParser this_$iv = this;
        boolean $i$f$parseCommaList = false;
        List<Token> list = $this$parseCommaList$iv;
        SqlParser sqlParser = this_$iv;
        Function1 parseDelim$iv$iv = this_$iv.parseCommaDelim;
        boolean $i$f$parseDelimitedList = false;
        ArrayList<ParseNode> items$iv$iv = new ArrayList<ParseNode>();
        ParseNode delim$iv$iv = null;
        List<Token> rem$iv$iv = $this$parseDelimitedList$iv$iv;
        while (true) {
            List<Object> list2;
            Collection collection = rem$iv$iv;
            boolean bl = false;
            if (!(!collection.isEmpty())) break;
            ParseNode parseNode = delim$iv$iv;
            void $this$parseDelimitedList$iv = rem$iv$iv;
            boolean bl2 = false;
            void $this$parseCommaList = $this$parseDelimitedList$iv;
            boolean bl3 = false;
            List<Object> rem = $this$parseCommaList;
            void $this$head$iv = rem;
            boolean $i$f$getHead = false;
            Token token = (Token)CollectionsKt.firstOrNull($this$head$iv);
            if ((token != null ? token.getType() : null) != TokenType.LEFT_PAREN) {
                Void void_ = TokenListExtensionsKt.err$default((List)$this$parseCommaList, "Expected " + (Object)((Object)TokenType.LEFT_PAREN) + " for row value constructor", ErrorCode.PARSE_EXPECTED_LEFT_PAREN_VALUE_CONSTRUCTOR, null, 4, null);
                throw null;
            }
            void $this$tail$iv = rem;
            boolean $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    List list3 = Collections.emptyList();
                    list2 = list3;
                    Intrinsics.checkExpressionValueIsNotNull(list3, "emptyList()");
                    break;
                }
                default: {
                    list2 = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            rem = list2;
            ParseNode child$iv$iv = ParseNode.copy$default(SqlParser.parseArgList$default(this, rem, AliasSupportType.NONE, ArgListMode.NORMAL_ARG_LIST, 0, 4, null), ParseType.LIST, null, null, null, 14, null).deriveExpected(TokenType.RIGHT_PAREN);
            items$iv$iv.add(child$iv$iv);
            rem$iv$iv = child$iv$iv.getRemaining();
            delim$iv$iv = (ParseNode)parseDelim$iv$iv.invoke(rem$iv$iv);
            if (delim$iv$iv == null) break;
            rem$iv$iv = delim$iv$iv.getRemaining();
        }
        return new ParseNode(ParseType.ARG_LIST, null, (List<ParseNode>)items$iv$iv, rem$iv$iv);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseOrderByArgList(@NotNull List<Token> $this$parseOrderByArgList) {
        void $this$parseDelimitedList$iv;
        List<Token> list = $this$parseOrderByArgList;
        SqlParser sqlParser = this;
        Function1<List<Token>, ParseNode> parseDelim$iv = this.parseCommaDelim;
        boolean $i$f$parseDelimitedList = false;
        ArrayList<ParseNode> items$iv = new ArrayList<ParseNode>();
        ParseNode delim$iv = null;
        List<Token> rem$iv = $this$parseDelimitedList$iv;
        while (true) {
            Collection collection = rem$iv;
            boolean bl = false;
            if (!(!collection.isEmpty())) break;
            ParseNode parseNode = delim$iv;
            void $this$parseDelimitedList = rem$iv;
            boolean bl2 = false;
            List<Token> rem = $this$parseDelimitedList;
            ParseNode child2 = SqlParser.parseExpression$lang$default(this, rem, 0, 1, null);
            List<ParseNode> sortSpecKey = CollectionsKt.listOf(child2);
            List<Token> $this$head$iv2 = rem = child2.getRemaining();
            boolean $i$f$getHead = false;
            Token token = CollectionsKt.firstOrNull($this$head$iv2);
            TokenType tokenType = token != null ? token.getType() : null;
            if (tokenType != null) {
                switch (SqlParser$WhenMappings.$EnumSwitchMapping$43[tokenType.ordinal()]) {
                    case 1: 
                    case 2: {
                        List<Object> list2;
                        List<Token> list3;
                        List<Token> list4;
                        List<Token> $this$tail$iv;
                        ParseNode[] parseNodeArray = new ParseNode[2];
                        parseNodeArray[0] = child2;
                        $this$head$iv2 = rem;
                        ParseType parseType = ParseType.ORDERING_SPEC;
                        int n = 1;
                        ParseNode[] parseNodeArray2 = parseNodeArray;
                        ParseNode[] parseNodeArray3 = parseNodeArray;
                        $i$f$getHead = false;
                        Token token2 = CollectionsKt.firstOrNull($this$head$iv2);
                        boolean $this$head$iv = false;
                        List<ParseNode> list5 = CollectionsKt.emptyList();
                        $this$head$iv2 = rem;
                        boolean $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                List list6 = Collections.emptyList();
                                list4 = list6;
                                Intrinsics.checkExpressionValueIsNotNull(list6, "emptyList()");
                                break;
                            }
                            default: {
                                list4 = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        List<Token> list7 = list3 = list4;
                        List<ParseNode> list8 = list5;
                        Token token3 = token2;
                        ParseType parseType2 = parseType;
                        parseNodeArray2[n] = new ParseNode(parseType2, token3, list8, list7);
                        sortSpecKey = CollectionsKt.listOf(parseNodeArray3);
                        $this$tail$iv = rem;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                List list9 = Collections.emptyList();
                                list2 = list9;
                                Intrinsics.checkExpressionValueIsNotNull(list9, "emptyList()");
                                break;
                            }
                            default: {
                                list2 = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        rem = list2;
                    }
                }
            }
            ParseNode child$iv = new ParseNode(ParseType.SORT_SPEC, null, sortSpecKey, rem);
            items$iv.add(child$iv);
            rem$iv = child$iv.getRemaining();
            delim$iv = parseDelim$iv.invoke(rem$iv);
            if (delim$iv == null) break;
            rem$iv = delim$iv.getRemaining();
        }
        return new ParseNode(ParseType.ARG_LIST, null, (List<ParseNode>)items$iv, rem$iv);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final ParseNode parseFromSource(@NotNull List<Token> $this$parseFromSource, int precedence, boolean parseRemaining) {
        block40: {
            rem = $this$parseFromSource;
            $this$head$iv = rem;
            $i$f$getHead = false;
            v0 = CollectionsKt.firstOrNull($this$head$iv);
            v1 = v0 != null ? v0.getKeywordText() : null;
            if (v1 == null) ** GOTO lbl-1000
            $this$head$iv = v1;
            switch ($this$head$iv.hashCode()) {
                case -280365751: {
                    if ($this$head$iv.equals("unpivot")) {
                        var8_10 = rem;
                        var17_12 = this;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v2 = Collections.emptyList();
                                v3 = v2;
                                Intrinsics.checkExpressionValueIsNotNull(v2, "emptyList()");
                                break;
                            }
                            default: {
                                v3 = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        var18_16 = v3;
                        actualChild = var17_12.parseExpression$lang(var18_16, precedence);
                        $this$tail$iv = rem;
                        var19_17 = ParseType.UNPIVOT;
                        $i$f$getHead = false;
                        var20_18 = CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                        var21_19 = actualChild.getRemaining();
                        var22_20 = CollectionsKt.listOf(actualChild);
                        var23_21 = (Token)var20_18;
                        var24_22 = var19_17;
                        v4 = new ParseNode(var24_22, var23_21, var22_20, var21_19);
                        break;
                    }
                }
                default: lbl-1000:
                // 2 sources

                {
                    $this$tail$iv = rem;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v5 = Collections.emptyList();
                            v6 /* !! */  = v5;
                            Intrinsics.checkExpressionValueIsNotNull(v5, "emptyList()");
                            break;
                        }
                        default: {
                            v6 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    $this$head$iv /* !! */  = v6 /* !! */ ;
                    $i$f$getHead = false;
                    v7 = (Token)CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                    if ((v7 != null ? v7.getType() : null) == TokenType.LITERAL) ** GOTO lbl-1000
                    $this$tail$iv = rem;
                    $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            v8 = Collections.emptyList();
                            v9 /* !! */  = v8;
                            Intrinsics.checkExpressionValueIsNotNull(v8, "emptyList()");
                            break;
                        }
                        default: {
                            v9 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    $this$head$iv /* !! */  = v9 /* !! */ ;
                    $i$f$getHead = false;
                    v10 = (Token)CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                    if (Intrinsics.areEqual(v10 != null ? v10.getKeywordText() : null, "select")) lbl-1000:
                    // 2 sources

                    {
                        v11 = true;
                    } else {
                        v11 = false;
                    }
                    isSubqueryOrLiteral = v11;
                    $this$head$iv /* !! */  = rem;
                    $i$f$getHead = false;
                    v12 = (Token)CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                    if ((v12 != null ? v12.getType() : null) == TokenType.LEFT_PAREN && !isSubqueryOrLiteral) {
                        $this$tail$iv = rem;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v13 = Collections.emptyList();
                                v14 /* !! */  = v13;
                                Intrinsics.checkExpressionValueIsNotNull(v13, "emptyList()");
                                break;
                            }
                            default: {
                                v14 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        var18_16 = v14 /* !! */ ;
                        rem = var18_16;
                        v4 = SqlParser.parseFromSource$default(this, rem, precedence, false, 2, null).deriveExpected(TokenType.RIGHT_PAREN);
                        break;
                    }
                    v4 = this.parseExpression$lang(rem, precedence);
                }
            }
            child = v4;
            rem = child.getRemaining();
            $this$head$iv = this.parseOptionalAsAlias(rem, (ParseNode)child);
            isSubqueryOrLiteral = false;
            $this$tail$iv = false;
            it = $this$head$iv;
            $i$a$-also-SqlParser$parseFromSource$1 = false;
            rem = it.getRemaining();
            child = $this$head$iv;
            $this$head$iv = this.parseOptionalAtAlias(rem, (ParseNode)child);
            isSubqueryOrLiteral = false;
            $this$tail$iv = false;
            it = $this$head$iv;
            $i$a$-also-SqlParser$parseFromSource$2 = false;
            rem = it.getRemaining();
            child = $this$head$iv;
            $this$head$iv = this.parseOptionalByAlias(rem, (ParseNode)child);
            isSubqueryOrLiteral = false;
            $this$tail$iv = false;
            it = $this$head$iv;
            $i$a$-also-SqlParser$parseFromSource$3 = false;
            rem = it.getRemaining();
            left = child = $this$head$iv;
            delim = this.parseJoinDelim.invoke(rem);
            if (!parseRemaining) break block40;
            while (true) {
                block43: {
                    block41: {
                        block42: {
                            if ((v15 = delim) == null || (v15 = v15.getType()) == null) break;
                            if (!v15.isJoin()) break;
                            v16 = delim.getToken();
                            isCrossJoin = v16 != null && (v16 = v16.getKeywordText()) != null ? StringsKt.contains$default((CharSequence)v16, "cross", false, 2, null) : false;
                            v17 = delim.getToken();
                            hasOnClause = (v17 != null ? v17.getType() : null) == TokenType.KEYWORD && isCrossJoin == false;
                            children = null;
                            joinToken = delim.getToken();
                            $this$tail$iv = rem;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v18 = Collections.emptyList();
                                    v19 /* !! */  = v18;
                                    Intrinsics.checkExpressionValueIsNotNull(v18, "emptyList()");
                                    break;
                                }
                                default: {
                                    v19 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var18_16 = v19 /* !! */ ;
                            rem = var18_16;
                            if (!hasOnClause) break block41;
                            $this$head$iv = rem;
                            $i$f$getHead = false;
                            v20 = CollectionsKt.firstOrNull($this$head$iv);
                            if ((v20 != null ? v20.getType() : null) != TokenType.LEFT_PAREN) break block42;
                            $this$tail$iv = rem;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v21 = Collections.emptyList();
                                    v22 /* !! */  = v21;
                                    Intrinsics.checkExpressionValueIsNotNull(v21, "emptyList()");
                                    break;
                                }
                                default: {
                                    v22 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            $this$head$iv /* !! */  = v22 /* !! */ ;
                            $i$f$getHead = false;
                            v23 = (Token)CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                            if ((v23 != null ? v23.getType() : null) == TokenType.LITERAL) ** GOTO lbl-1000
                            $this$tail$iv = rem;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v24 = Collections.emptyList();
                                    v25 /* !! */  = v24;
                                    Intrinsics.checkExpressionValueIsNotNull(v24, "emptyList()");
                                    break;
                                }
                                default: {
                                    v25 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            $this$head$iv /* !! */  = v25 /* !! */ ;
                            $i$f$getHead = false;
                            v26 = (Token)CollectionsKt.firstOrNull($this$head$iv /* !! */ );
                            if (Intrinsics.areEqual(v26 != null ? v26.getKeywordText() : null, "select")) lbl-1000:
                            // 2 sources

                            {
                                v27 = true;
                            } else {
                                v27 = false;
                            }
                            isSubqueryOrLiteral = v27;
                            parenClause = this.parseFromSource(rem, precedence, true);
                            $this$head$iv = rem = parenClause.getRemaining();
                            $i$f$getHead = false;
                            v28 = CollectionsKt.firstOrNull($this$head$iv);
                            if (Intrinsics.areEqual(v28 != null ? v28.getKeywordText() : null, "on") ^ true) {
                                v29 = TokenListExtensionsKt.err$default(rem, "Expected 'ON'", ErrorCode.PARSE_MALFORMED_JOIN, null, 4, null);
                                throw null;
                            }
                            $i$f$getHead = rem;
                            var17_12 = this;
                            $i$f$getTail = false;
                            switch ($this$tail$iv.size()) {
                                case 0: 
                                case 1: {
                                    v30 = Collections.emptyList();
                                    v31 /* !! */  = v30;
                                    Intrinsics.checkExpressionValueIsNotNull(v30, "emptyList()");
                                    break;
                                }
                                default: {
                                    v31 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                                }
                            }
                            var18_16 = v31 /* !! */ ;
                            onClause = var17_12.parseExpression$lang(var18_16, precedence);
                            rem = onClause.getRemaining();
                            children = !isSubqueryOrLiteral ? CollectionsKt.listOf(new ParseNode[]{parenClause, left, onClause}) : CollectionsKt.listOf(new ParseNode[]{left, parenClause, onClause});
                            break block43;
                        }
                        rightRef = this.parseFromSource(rem, precedence, false);
                        $this$head$iv = rem = rightRef.getRemaining();
                        $i$f$getHead = false;
                        v32 = CollectionsKt.firstOrNull($this$head$iv);
                        if (Intrinsics.areEqual(v32 != null ? v32.getKeywordText() : null, "on") ^ true) {
                            v33 = TokenListExtensionsKt.err$default(rem, "Expected 'ON'", ErrorCode.PARSE_MALFORMED_JOIN, null, 4, null);
                            throw null;
                        }
                        $i$f$getHead = rem;
                        var17_12 = this;
                        $i$f$getTail = false;
                        switch ($this$tail$iv.size()) {
                            case 0: 
                            case 1: {
                                v34 = Collections.emptyList();
                                v35 /* !! */  = v34;
                                Intrinsics.checkExpressionValueIsNotNull(v34, "emptyList()");
                                break;
                            }
                            default: {
                                v35 /* !! */  = $this$tail$iv.subList(1, $this$tail$iv.size());
                            }
                        }
                        var18_16 = v35 /* !! */ ;
                        onClause = var17_12.parseExpression$lang(var18_16, precedence);
                        rem = onClause.getRemaining();
                        children = CollectionsKt.listOf(new ParseNode[]{left, rightRef, onClause});
                        break block43;
                    }
                    rightRef = this.parseFromSource(rem, precedence, false);
                    rem = rightRef.getRemaining();
                    children = CollectionsKt.listOf(new ParseNode[]{left, rightRef});
                    v36 = delim.getToken();
                    if ((v36 != null ? v36.getType() : null) == TokenType.COMMA) {
                        v37 = delim.getToken();
                        joinToken = v37 != null ? Token.copy$default(v37, TokenType.KEYWORD, this.ion.newSymbol("cross_join"), null, 4, null) : null;
                    }
                }
                left = new ParseNode(ParseType.FROM_SOURCE_JOIN, joinToken, children, rem);
                delim = this.parseJoinDelim.invoke(rem);
            }
            return left;
        }
        return child;
    }

    static /* synthetic */ ParseNode parseFromSource$default(SqlParser sqlParser, List list, int n, boolean bl, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = -1;
        }
        if ((n2 & 2) != 0) {
            bl = true;
        }
        return sqlParser.parseFromSource(list, n, bl);
    }

    private final ParseNode parseFromSourceList(@NotNull List<Token> $this$parseFromSourceList, int precedence) {
        ParseNode child2 = SqlParser.parseFromSource$default(this, $this$parseFromSourceList, precedence, false, 2, null);
        return new ParseNode(ParseType.FROM_CLAUSE, null, CollectionsKt.listOf(child2), child2.getRemaining());
    }

    static /* synthetic */ ParseNode parseFromSourceList$default(SqlParser sqlParser, List list, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = -1;
        }
        return sqlParser.parseFromSourceList(list, n);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseArgList(@NotNull List<Token> $this$parseArgList, AliasSupportType aliasSupportType, ArgListMode mode, int precedence) {
        void $this$parseDelimitedList$iv;
        Function1<List<Token>, ParseNode> parseDelim = this.parseCommaDelim;
        List<Token> list = $this$parseArgList;
        SqlParser this_$iv = this;
        boolean $i$f$parseDelimitedList = false;
        ArrayList<ParseNode> items$iv = new ArrayList<ParseNode>();
        ParseNode delim$iv = null;
        List<Token> rem$iv = $this$parseDelimitedList$iv;
        while (true) {
            ParseNode it;
            boolean bl;
            ParseNode parseNode;
            ParseNode parseNode2;
            Collection collection = rem$iv;
            boolean bl2 = false;
            if (!(!collection.isEmpty())) break;
            ParseNode parseNode3 = delim$iv;
            void $this$parseDelimitedList = rem$iv;
            boolean bl3 = false;
            List<Token> rem = $this$parseDelimitedList;
            switch (SqlParser$WhenMappings.$EnumSwitchMapping$44[mode.ordinal()]) {
                case 1: {
                    ParseNode field = this.parseExpression$lang(rem, precedence).deriveExpected(TokenType.COLON);
                    rem = field.getRemaining();
                    ParseNode value = this.parseExpression$lang(rem, precedence);
                    parseNode2 = new ParseNode(ParseType.MEMBER, null, CollectionsKt.listOf(field, value), value.getRemaining());
                    break;
                }
                case 2: {
                    parseNode2 = this.parsePathTerm(rem, PathMode.SIMPLE_PATH);
                    break;
                }
                case 3: {
                    List<Object> list2;
                    ParseNode lvalue = this.parsePathTerm(rem, PathMode.SIMPLE_PATH);
                    List<Token> $this$head$iv = rem = lvalue.getRemaining();
                    boolean $i$f$getHead = false;
                    Token token = CollectionsKt.firstOrNull($this$head$iv);
                    if (Intrinsics.areEqual(token != null ? token.getKeywordText() : null, "=") ^ true) {
                        Void void_ = TokenListExtensionsKt.err$default(rem, "Expected '='", ErrorCode.PARSE_MISSING_SET_ASSIGNMENT, null, 4, null);
                        throw null;
                    }
                    List<Token> $this$tail$iv = rem;
                    boolean $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            List list3 = Collections.emptyList();
                            list2 = list3;
                            Intrinsics.checkExpressionValueIsNotNull(list3, "emptyList()");
                            break;
                        }
                        default: {
                            list2 = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    List<Object> list4 = list2;
                    rem = list4;
                    ParseNode rvalue = this.parseExpression$lang(rem, precedence);
                    parseNode2 = new ParseNode(ParseType.ASSIGNMENT, null, CollectionsKt.listOf(lvalue, rvalue), rvalue.getRemaining());
                    break;
                }
                case 4: {
                    parseNode2 = this.parseExpression$lang(rem, precedence);
                    break;
                }
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            ParseNode child2 = parseNode2;
            rem = child2.getRemaining();
            if (aliasSupportType.getSupportsAs()) {
                parseNode = this.parseOptionalAsAlias(rem, child2);
                boolean bl4 = false;
                bl = false;
                it = parseNode;
                boolean bl5 = false;
                rem = it.getRemaining();
                child2 = parseNode;
            }
            if (aliasSupportType.getSupportsAt()) {
                parseNode = this.parseOptionalAtAlias(rem, child2);
                boolean bl6 = false;
                bl = false;
                it = parseNode;
                boolean bl7 = false;
                rem = it.getRemaining();
                child2 = parseNode;
            }
            if (aliasSupportType.getSupportsBy()) {
                parseNode = this.parseOptionalByAlias(rem, child2);
                boolean bl8 = false;
                bl = false;
                it = parseNode;
                boolean bl9 = false;
                rem = it.getRemaining();
                child2 = parseNode;
            }
            ParseNode child$iv = child2;
            items$iv.add(child$iv);
            rem$iv = child$iv.getRemaining();
            delim$iv = parseDelim.invoke(rem$iv);
            if (delim$iv == null) break;
            rem$iv = delim$iv.getRemaining();
        }
        return new ParseNode(ParseType.ARG_LIST, null, (List<ParseNode>)items$iv, rem$iv);
    }

    static /* synthetic */ ParseNode parseArgList$default(SqlParser sqlParser, List list, AliasSupportType aliasSupportType, ArgListMode argListMode, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = -1;
        }
        return sqlParser.parseArgList(list, aliasSupportType, argListMode, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final ParseNode parseOptionalAlias(@NotNull List<Token> $this$parseOptionalAlias, ParseNode child2, TokenType keywordTokenType, boolean keywordIsOptional, ParseType parseNodeType) {
        ParseNode parseNode;
        boolean $i$f$getTail;
        List<Token> $this$tail$iv;
        List<Token> list = $this$parseOptionalAlias;
        List<Token> $this$head$iv = list;
        boolean $i$f$getHead = false;
        Token token = CollectionsKt.firstOrNull($this$head$iv);
        if ((token != null ? token.getType() : null) == keywordTokenType) {
            List<Token> list2;
            List<Object> list3;
            List<Object> list4;
            $this$tail$iv = list;
            $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    List list5 = Collections.emptyList();
                    list4 = list5;
                    Intrinsics.checkExpressionValueIsNotNull(list5, "emptyList()");
                    break;
                }
                default: {
                    list4 = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            List<Object> $this$head$iv2 = list3 = list4;
            boolean $i$f$getHead2 = false;
            Token name = (Token)CollectionsKt.firstOrNull($this$head$iv2);
            $this$head$iv2 = list3;
            $i$f$getHead2 = false;
            Object object = (Token)CollectionsKt.firstOrNull($this$head$iv2);
            if (object == null || (object = ((Token)object).getType()) == null || !((TokenType)((Object)object)).isIdentifier()) {
                $this$head$iv2 = list3;
                $i$f$getHead2 = false;
                Void void_ = TokenListExtensionsKt.err$default((Token)CollectionsKt.firstOrNull($this$head$iv2), "Expected identifier for " + (Object)((Object)keywordTokenType) + "-alias", ErrorCode.PARSE_EXPECTED_IDENT_FOR_ALIAS, null, 4, null);
                throw null;
            }
            List<Object> $this$tail$iv2 = list3;
            boolean $i$f$getTail2 = false;
            switch ($this$tail$iv2.size()) {
                case 0: 
                case 1: {
                    List list6 = Collections.emptyList();
                    list2 = list6;
                    Intrinsics.checkExpressionValueIsNotNull(list6, "emptyList()");
                    break;
                }
                default: {
                    list2 = $this$tail$iv2.subList(1, $this$tail$iv2.size());
                }
            }
            List<Token> list7 = list2;
            parseNode = new ParseNode(parseNodeType, name, CollectionsKt.listOf(child2), list7);
            return parseNode;
        }
        if (keywordIsOptional) {
            $this$head$iv = list;
            $i$f$getHead = false;
            Object object = CollectionsKt.firstOrNull($this$head$iv);
            if (object != null && (object = ((Token)object).getType()) != null ? ((TokenType)((Object)object)).isIdentifier() : false) {
                List<Token> list8;
                List<Token> list9;
                $this$head$iv = list;
                ParseType parseType = parseNodeType;
                $i$f$getHead = false;
                Token token2 = CollectionsKt.firstOrNull($this$head$iv);
                $this$head$iv = list;
                List<ParseNode> list10 = CollectionsKt.listOf(child2);
                $i$f$getTail = false;
                switch ($this$tail$iv.size()) {
                    case 0: 
                    case 1: {
                        List list11 = Collections.emptyList();
                        list9 = list11;
                        Intrinsics.checkExpressionValueIsNotNull(list11, "emptyList()");
                        break;
                    }
                    default: {
                        list9 = $this$tail$iv.subList(1, $this$tail$iv.size());
                    }
                }
                List<Token> list12 = list8 = list9;
                List<ParseNode> list13 = list10;
                Token token3 = token2;
                ParseType parseType2 = parseType;
                parseNode = new ParseNode(parseType2, token3, list13, list12);
                return parseNode;
            }
        }
        parseNode = child2;
        return parseNode;
    }

    private final ParseNode parseOptionalAsAlias(@NotNull List<Token> $this$parseOptionalAsAlias, ParseNode child2) {
        return this.parseOptionalAlias($this$parseOptionalAsAlias, child2, TokenType.AS, true, ParseType.AS_ALIAS);
    }

    private final ParseNode parseOptionalAtAlias(@NotNull List<Token> $this$parseOptionalAtAlias, ParseNode child2) {
        return this.parseOptionalAlias($this$parseOptionalAtAlias, child2, TokenType.AT, false, ParseType.AT_ALIAS);
    }

    private final ParseNode parseOptionalByAlias(@NotNull List<Token> $this$parseOptionalByAlias, ParseNode child2) {
        return this.parseOptionalAlias($this$parseOptionalByAlias, child2, TokenType.BY, false, ParseType.BY_ALIAS);
    }

    /*
     * WARNING - void declaration
     */
    private final ParseNode parseCommaList(@NotNull List<Token> $this$parseCommaList, Function1<? super List<Token>, ParseNode> parseItem) {
        void $this$parseDelimitedList$iv;
        int $i$f$parseCommaList = 0;
        List<Token> list = $this$parseCommaList;
        SqlParser sqlParser = this;
        Function1 parseDelim$iv = this.parseCommaDelim;
        boolean $i$f$parseDelimitedList = false;
        ArrayList<ParseNode> items$iv = new ArrayList<ParseNode>();
        ParseNode delim$iv = null;
        List<Token> rem$iv = $this$parseDelimitedList$iv;
        while (true) {
            Collection collection = rem$iv;
            boolean bl = false;
            if (!(!collection.isEmpty())) break;
            ParseNode parseNode = delim$iv;
            void $this$parseDelimitedList = rem$iv;
            boolean bl2 = false;
            ParseNode child$iv = parseItem.invoke((List<Token>)$this$parseDelimitedList);
            items$iv.add(child$iv);
            rem$iv = child$iv.getRemaining();
            delim$iv = (ParseNode)parseDelim$iv.invoke(rem$iv);
            if (delim$iv == null) break;
            rem$iv = delim$iv.getRemaining();
        }
        return new ParseNode(ParseType.ARG_LIST, null, (List<ParseNode>)items$iv, rem$iv);
    }

    private final ParseNode parseDelimitedList(@NotNull List<Token> $this$parseDelimitedList, Function1<? super List<Token>, ParseNode> parseDelim, Function2<? super List<Token>, ? super ParseNode, ParseNode> parseItem) {
        int $i$f$parseDelimitedList = 0;
        ArrayList<ParseNode> items = new ArrayList<ParseNode>();
        ParseNode delim = null;
        List<Token> rem = $this$parseDelimitedList;
        while (true) {
            Collection collection = rem;
            boolean bl = false;
            if (!(!collection.isEmpty())) break;
            ParseNode child2 = parseItem.invoke(rem, delim);
            items.add(child2);
            rem = child2.getRemaining();
            delim = parseDelim.invoke(rem);
            if (delim == null) break;
            rem = delim.getRemaining();
        }
        return new ParseNode(ParseType.ARG_LIST, null, (List<ParseNode>)items, rem);
    }

    private final Void throwTopLevelParserError(@NotNull ParseNode $this$throwTopLevelParserError) {
        Token token = $this$throwTopLevelParserError.getToken();
        if (token != null) {
            Void void_ = TokenListExtensionsKt.err$default(token, "Keyword " + $this$throwTopLevelParserError.getToken().getText() + " only expected at the top level in the query", ErrorCode.PARSE_UNEXPECTED_TERM, null, 4, null);
            throw null;
        }
        Token token2 = $this$throwTopLevelParserError.getToken();
        throw (Throwable)new ParserException("Keyword " + (token2 != null ? token2.getText() : null) + " only expected at the top level in the query", ErrorCode.PARSE_UNEXPECTED_TERM, new PropertyValueMap(null, 1, null), null, 8, null);
    }

    /*
     * WARNING - void declaration
     */
    private final void validateTopLevelNodes(ParseNode node, int level, boolean topLevelTokenSeen, boolean dmlListTokenSeen) {
        void $this$mapTo$iv$iv;
        boolean isTopLevelType;
        ThreadInterruptUtilsKt.checkThreadInterrupted();
        boolean bl = node.getType().isDml();
        boolean bl2 = bl ? !dmlListTokenSeen && node.getType().isTopLevelType() : (isTopLevelType = node.getType().isTopLevelType());
        if (topLevelTokenSeen && isTopLevelType) {
            Void void_ = this.throwTopLevelParserError(node);
            throw null;
        }
        if (isTopLevelType && level > 0) {
            if (node.getType().isDml()) {
                if (level > 1) {
                    Void void_ = this.throwTopLevelParserError(node);
                    throw null;
                }
            } else {
                Void void_ = this.throwTopLevelParserError(node);
                throw null;
            }
        }
        Iterable $this$map$iv = node.getChildren();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ParseNode parseNode = (ParseNode)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl3 = false;
            this.validateTopLevelNodes((ParseNode)it, level + 1, topLevelTokenSeen || isTopLevelType, dmlListTokenSeen || node.getType() == ParseType.DML_LIST);
            Unit unit = Unit.INSTANCE;
            collection.add(unit);
        }
        List cfr_ignored_0 = (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public ExprNode parseExprNode(@NotNull String source) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        List<Token> tokens = new SqlLexer(this.ion).tokenize(source);
        ParseNode node = SqlParser.parseExpression$lang$default(this, tokens, 0, 1, null);
        List<Token> rem = node.getRemaining();
        if (!TokenListExtensionsKt.onlyEndOfStatement(rem)) {
            List<Token> $this$head$iv = rem;
            boolean $i$f$getHead = false;
            Token token = CollectionsKt.firstOrNull($this$head$iv);
            TokenType tokenType = token != null ? token.getType() : null;
            if (tokenType != null) {
                switch (SqlParser$WhenMappings.$EnumSwitchMapping$45[tokenType.ordinal()]) {
                    case 1: {
                        List<Object> list;
                        List<Token> $this$tail$iv = rem;
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
                        Void void_ = TokenListExtensionsKt.err$default(list, "Unexpected token after semicolon. (Only one query is allowed.)", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
                        throw null;
                    }
                }
            }
            Void void_ = TokenListExtensionsKt.err$default(rem, "Unexpected token after expression", ErrorCode.PARSE_UNEXPECTED_TOKEN, null, 4, null);
            throw null;
        }
        this.validateTopLevelNodes(node, 0, false, false);
        return this.toExprNode(node);
    }

    @Override
    @NotNull
    public PartiqlAst.Statement parseAstStatement(@NotNull String source) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        return ExprNodeToStatementKt.toAstStatement(this.parseExprNode(source));
    }

    @Override
    @NotNull
    public IonSexp parse(@NotNull String source) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        return AstSerializer.Companion.serialize(this.parseExprNode(source), AstVersion.V0, this.ion);
    }

    public SqlParser(@NotNull IonSystem ion) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        this.ion = ion;
        IonBool ionBool = this.ion.newBool(true);
        Intrinsics.checkExpressionValueIsNotNull(ionBool, "ion.newBool(true)");
        this.trueValue = ionBool;
        this.IN_OP_NORMAL_EVAL_KEYWORDS = SetsKt.setOf("select", "values");
        this.parseCommaDelim = parseCommaDelim.1.INSTANCE;
        this.parseJoinDelim = parseJoinDelim.1.INSTANCE;
    }

    public static final /* synthetic */ ExprNode access$toExprNode(SqlParser $this, ParseNode $this$access_u24toExprNode) {
        return $this.toExprNode($this$access_u24toExprNode);
    }

    public static final /* synthetic */ ParseNode access$parseDelimitedList(SqlParser $this, List $this$access_u24parseDelimitedList, Function1 parseDelim, Function2 parseItem) {
        return $this.parseDelimitedList($this$access_u24parseDelimitedList, parseDelim, parseItem);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bj\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000e"}, d2={"Lorg/partiql/lang/syntax/SqlParser$AliasSupportType;", "", "supportsAs", "", "supportsAt", "supportsBy", "(Ljava/lang/String;IZZZ)V", "getSupportsAs", "()Z", "getSupportsAt", "getSupportsBy", "NONE", "AS_ONLY", "AS_AT_BY", "lang"})
    public static final class AliasSupportType
    extends Enum<AliasSupportType> {
        public static final /* enum */ AliasSupportType NONE;
        public static final /* enum */ AliasSupportType AS_ONLY;
        public static final /* enum */ AliasSupportType AS_AT_BY;
        private static final /* synthetic */ AliasSupportType[] $VALUES;
        private final boolean supportsAs;
        private final boolean supportsAt;
        private final boolean supportsBy;

        static {
            AliasSupportType[] aliasSupportTypeArray = new AliasSupportType[3];
            AliasSupportType[] aliasSupportTypeArray2 = aliasSupportTypeArray;
            aliasSupportTypeArray[0] = NONE = new AliasSupportType(false, false, false);
            aliasSupportTypeArray[1] = AS_ONLY = new AliasSupportType(true, false, false);
            aliasSupportTypeArray[2] = AS_AT_BY = new AliasSupportType(true, true, true);
            $VALUES = aliasSupportTypeArray;
        }

        public final boolean getSupportsAs() {
            return this.supportsAs;
        }

        public final boolean getSupportsAt() {
            return this.supportsAt;
        }

        public final boolean getSupportsBy() {
            return this.supportsBy;
        }

        private AliasSupportType(boolean supportsAs, boolean supportsAt, boolean supportsBy) {
            this.supportsAs = supportsAs;
            this.supportsAt = supportsAt;
            this.supportsBy = supportsBy;
        }

        public static AliasSupportType[] values() {
            return (AliasSupportType[])$VALUES.clone();
        }

        public static AliasSupportType valueOf(String string) {
            return Enum.valueOf(AliasSupportType.class, string);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/syntax/SqlParser$ArgListMode;", "", "(Ljava/lang/String;I)V", "NORMAL_ARG_LIST", "STRUCT_LITERAL_ARG_LIST", "SIMPLE_PATH_ARG_LIST", "SET_CLAUSE_ARG_LIST", "lang"})
    public static final class ArgListMode
    extends Enum<ArgListMode> {
        public static final /* enum */ ArgListMode NORMAL_ARG_LIST;
        public static final /* enum */ ArgListMode STRUCT_LITERAL_ARG_LIST;
        public static final /* enum */ ArgListMode SIMPLE_PATH_ARG_LIST;
        public static final /* enum */ ArgListMode SET_CLAUSE_ARG_LIST;
        private static final /* synthetic */ ArgListMode[] $VALUES;

        static {
            ArgListMode[] argListModeArray = new ArgListMode[4];
            ArgListMode[] argListModeArray2 = argListModeArray;
            argListModeArray[0] = NORMAL_ARG_LIST = new ArgListMode();
            argListModeArray[1] = STRUCT_LITERAL_ARG_LIST = new ArgListMode();
            argListModeArray[2] = SIMPLE_PATH_ARG_LIST = new ArgListMode();
            argListModeArray[3] = SET_CLAUSE_ARG_LIST = new ArgListMode();
            $VALUES = argListModeArray;
        }

        public static ArgListMode[] values() {
            return (ArgListMode[])$VALUES.clone();
        }

        public static ArgListMode valueOf(String string) {
            return Enum.valueOf(ArgListMode.class, string);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/syntax/SqlParser$PathMode;", "", "(Ljava/lang/String;I)V", "FULL_PATH", "SIMPLE_PATH", "lang"})
    private static final class PathMode
    extends Enum<PathMode> {
        public static final /* enum */ PathMode FULL_PATH;
        public static final /* enum */ PathMode SIMPLE_PATH;
        private static final /* synthetic */ PathMode[] $VALUES;

        static {
            PathMode[] pathModeArray = new PathMode[2];
            PathMode[] pathModeArray2 = pathModeArray;
            pathModeArray[0] = FULL_PATH = new PathMode();
            pathModeArray[1] = SIMPLE_PATH = new PathMode();
            $VALUES = pathModeArray;
        }

        public static PathMode[] values() {
            return (PathMode[])$VALUES.clone();
        }

        public static PathMode valueOf(String string) {
            return Enum.valueOf(PathMode.class, string);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\bO\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B%\b\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=j\u0002\b>j\u0002\b?j\u0002\b@j\u0002\bAj\u0002\bBj\u0002\bCj\u0002\bDj\u0002\bEj\u0002\bFj\u0002\bGj\u0002\bHj\u0002\bIj\u0002\bJj\u0002\bKj\u0002\bLj\u0002\bMj\u0002\bNj\u0002\bOj\u0002\bPj\u0002\bQj\u0002\bRj\u0002\bSj\u0002\bTj\u0002\bUj\u0002\bV\u00a8\u0006W"}, d2={"Lorg/partiql/lang/syntax/SqlParser$ParseType;", "", "isJoin", "", "isTopLevelType", "isDml", "(Ljava/lang/String;IZZZ)V", "identifier", "", "getIdentifier", "()Ljava/lang/String;", "()Z", "ATOM", "CASE_SENSITIVE_ATOM", "CASE_INSENSITIVE_ATOM", "PROJECT_ALL", "PATH_WILDCARD", "PATH_UNPIVOT", "LET", "SELECT_LIST", "SELECT_VALUE", "DISTINCT", "INNER_JOIN", "LEFT_JOIN", "RIGHT_JOIN", "OUTER_JOIN", "WHERE", "ORDER_BY", "SORT_SPEC", "ORDERING_SPEC", "GROUP", "GROUP_PARTIAL", "HAVING", "LIMIT", "PIVOT", "UNPIVOT", "CALL", "DATE", "TIME", "TIME_WITH_TIME_ZONE", "CALL_AGG", "CALL_DISTINCT_AGG", "CALL_AGG_WILDCARD", "ARG_LIST", "AS_ALIAS", "AT_ALIAS", "BY_ALIAS", "PATH", "PATH_DOT", "PATH_SQB", "UNARY", "BINARY", "TERNARY", "LIST", "STRUCT", "MEMBER", "CAST", "TYPE", "CASE", "WHEN", "ELSE", "BAG", "INSERT", "INSERT_VALUE", "REMOVE", "SET", "UPDATE", "DELETE", "ASSIGNMENT", "FROM", "FROM_CLAUSE", "FROM_SOURCE_JOIN", "CHECK", "ON_CONFLICT", "CONFLICT_ACTION", "DML_LIST", "RETURNING", "RETURNING_ELEM", "RETURNING_MAPPING", "RETURNING_WILDCARD", "CREATE_TABLE", "DROP_TABLE", "DROP_INDEX", "CREATE_INDEX", "PARAMETER", "EXEC", "PRECISION", "lang"})
    public static final class ParseType
    extends Enum<ParseType> {
        public static final /* enum */ ParseType ATOM;
        public static final /* enum */ ParseType CASE_SENSITIVE_ATOM;
        public static final /* enum */ ParseType CASE_INSENSITIVE_ATOM;
        public static final /* enum */ ParseType PROJECT_ALL;
        public static final /* enum */ ParseType PATH_WILDCARD;
        public static final /* enum */ ParseType PATH_UNPIVOT;
        public static final /* enum */ ParseType LET;
        public static final /* enum */ ParseType SELECT_LIST;
        public static final /* enum */ ParseType SELECT_VALUE;
        public static final /* enum */ ParseType DISTINCT;
        public static final /* enum */ ParseType INNER_JOIN;
        public static final /* enum */ ParseType LEFT_JOIN;
        public static final /* enum */ ParseType RIGHT_JOIN;
        public static final /* enum */ ParseType OUTER_JOIN;
        public static final /* enum */ ParseType WHERE;
        public static final /* enum */ ParseType ORDER_BY;
        public static final /* enum */ ParseType SORT_SPEC;
        public static final /* enum */ ParseType ORDERING_SPEC;
        public static final /* enum */ ParseType GROUP;
        public static final /* enum */ ParseType GROUP_PARTIAL;
        public static final /* enum */ ParseType HAVING;
        public static final /* enum */ ParseType LIMIT;
        public static final /* enum */ ParseType PIVOT;
        public static final /* enum */ ParseType UNPIVOT;
        public static final /* enum */ ParseType CALL;
        public static final /* enum */ ParseType DATE;
        public static final /* enum */ ParseType TIME;
        public static final /* enum */ ParseType TIME_WITH_TIME_ZONE;
        public static final /* enum */ ParseType CALL_AGG;
        public static final /* enum */ ParseType CALL_DISTINCT_AGG;
        public static final /* enum */ ParseType CALL_AGG_WILDCARD;
        public static final /* enum */ ParseType ARG_LIST;
        public static final /* enum */ ParseType AS_ALIAS;
        public static final /* enum */ ParseType AT_ALIAS;
        public static final /* enum */ ParseType BY_ALIAS;
        public static final /* enum */ ParseType PATH;
        public static final /* enum */ ParseType PATH_DOT;
        public static final /* enum */ ParseType PATH_SQB;
        public static final /* enum */ ParseType UNARY;
        public static final /* enum */ ParseType BINARY;
        public static final /* enum */ ParseType TERNARY;
        public static final /* enum */ ParseType LIST;
        public static final /* enum */ ParseType STRUCT;
        public static final /* enum */ ParseType MEMBER;
        public static final /* enum */ ParseType CAST;
        public static final /* enum */ ParseType TYPE;
        public static final /* enum */ ParseType CASE;
        public static final /* enum */ ParseType WHEN;
        public static final /* enum */ ParseType ELSE;
        public static final /* enum */ ParseType BAG;
        public static final /* enum */ ParseType INSERT;
        public static final /* enum */ ParseType INSERT_VALUE;
        public static final /* enum */ ParseType REMOVE;
        public static final /* enum */ ParseType SET;
        public static final /* enum */ ParseType UPDATE;
        public static final /* enum */ ParseType DELETE;
        public static final /* enum */ ParseType ASSIGNMENT;
        public static final /* enum */ ParseType FROM;
        public static final /* enum */ ParseType FROM_CLAUSE;
        public static final /* enum */ ParseType FROM_SOURCE_JOIN;
        public static final /* enum */ ParseType CHECK;
        public static final /* enum */ ParseType ON_CONFLICT;
        public static final /* enum */ ParseType CONFLICT_ACTION;
        public static final /* enum */ ParseType DML_LIST;
        public static final /* enum */ ParseType RETURNING;
        public static final /* enum */ ParseType RETURNING_ELEM;
        public static final /* enum */ ParseType RETURNING_MAPPING;
        public static final /* enum */ ParseType RETURNING_WILDCARD;
        public static final /* enum */ ParseType CREATE_TABLE;
        public static final /* enum */ ParseType DROP_TABLE;
        public static final /* enum */ ParseType DROP_INDEX;
        public static final /* enum */ ParseType CREATE_INDEX;
        public static final /* enum */ ParseType PARAMETER;
        public static final /* enum */ ParseType EXEC;
        public static final /* enum */ ParseType PRECISION;
        private static final /* synthetic */ ParseType[] $VALUES;
        @NotNull
        private final String identifier;
        private final boolean isJoin;
        private final boolean isTopLevelType;
        private final boolean isDml;

        static {
            ParseType[] parseTypeArray = new ParseType[75];
            ParseType[] parseTypeArray2 = parseTypeArray;
            parseTypeArray[0] = ATOM = new ParseType("ATOM", 0, false, false, false, 7, null);
            parseTypeArray[1] = CASE_SENSITIVE_ATOM = new ParseType("CASE_SENSITIVE_ATOM", 1, false, false, false, 7, null);
            parseTypeArray[2] = CASE_INSENSITIVE_ATOM = new ParseType("CASE_INSENSITIVE_ATOM", 2, false, false, false, 7, null);
            parseTypeArray[3] = PROJECT_ALL = new ParseType("PROJECT_ALL", 3, false, false, false, 7, null);
            parseTypeArray[4] = PATH_WILDCARD = new ParseType("PATH_WILDCARD", 4, false, false, false, 7, null);
            parseTypeArray[5] = PATH_UNPIVOT = new ParseType("PATH_UNPIVOT", 5, false, false, false, 7, null);
            parseTypeArray[6] = LET = new ParseType("LET", 6, false, false, false, 7, null);
            parseTypeArray[7] = SELECT_LIST = new ParseType("SELECT_LIST", 7, false, false, false, 7, null);
            parseTypeArray[8] = SELECT_VALUE = new ParseType("SELECT_VALUE", 8, false, false, false, 7, null);
            parseTypeArray[9] = DISTINCT = new ParseType("DISTINCT", 9, false, false, false, 7, null);
            parseTypeArray[10] = INNER_JOIN = new ParseType("INNER_JOIN", 10, true, false, false, 6, null);
            parseTypeArray[11] = LEFT_JOIN = new ParseType("LEFT_JOIN", 11, true, false, false, 6, null);
            parseTypeArray[12] = RIGHT_JOIN = new ParseType("RIGHT_JOIN", 12, true, false, false, 6, null);
            parseTypeArray[13] = OUTER_JOIN = new ParseType("OUTER_JOIN", 13, true, false, false, 6, null);
            parseTypeArray[14] = WHERE = new ParseType("WHERE", 14, false, false, false, 7, null);
            parseTypeArray[15] = ORDER_BY = new ParseType("ORDER_BY", 15, false, false, false, 7, null);
            parseTypeArray[16] = SORT_SPEC = new ParseType("SORT_SPEC", 16, false, false, false, 7, null);
            parseTypeArray[17] = ORDERING_SPEC = new ParseType("ORDERING_SPEC", 17, false, false, false, 7, null);
            parseTypeArray[18] = GROUP = new ParseType("GROUP", 18, false, false, false, 7, null);
            parseTypeArray[19] = GROUP_PARTIAL = new ParseType("GROUP_PARTIAL", 19, false, false, false, 7, null);
            parseTypeArray[20] = HAVING = new ParseType("HAVING", 20, false, false, false, 7, null);
            parseTypeArray[21] = LIMIT = new ParseType("LIMIT", 21, false, false, false, 7, null);
            parseTypeArray[22] = PIVOT = new ParseType("PIVOT", 22, false, false, false, 7, null);
            parseTypeArray[23] = UNPIVOT = new ParseType("UNPIVOT", 23, false, false, false, 7, null);
            parseTypeArray[24] = CALL = new ParseType("CALL", 24, false, false, false, 7, null);
            parseTypeArray[25] = DATE = new ParseType("DATE", 25, false, false, false, 7, null);
            parseTypeArray[26] = TIME = new ParseType("TIME", 26, false, false, false, 7, null);
            parseTypeArray[27] = TIME_WITH_TIME_ZONE = new ParseType("TIME_WITH_TIME_ZONE", 27, false, false, false, 7, null);
            parseTypeArray[28] = CALL_AGG = new ParseType("CALL_AGG", 28, false, false, false, 7, null);
            parseTypeArray[29] = CALL_DISTINCT_AGG = new ParseType("CALL_DISTINCT_AGG", 29, false, false, false, 7, null);
            parseTypeArray[30] = CALL_AGG_WILDCARD = new ParseType("CALL_AGG_WILDCARD", 30, false, false, false, 7, null);
            parseTypeArray[31] = ARG_LIST = new ParseType("ARG_LIST", 31, false, false, false, 7, null);
            parseTypeArray[32] = AS_ALIAS = new ParseType("AS_ALIAS", 32, false, false, false, 7, null);
            parseTypeArray[33] = AT_ALIAS = new ParseType("AT_ALIAS", 33, false, false, false, 7, null);
            parseTypeArray[34] = BY_ALIAS = new ParseType("BY_ALIAS", 34, false, false, false, 7, null);
            parseTypeArray[35] = PATH = new ParseType("PATH", 35, false, false, false, 7, null);
            parseTypeArray[36] = PATH_DOT = new ParseType("PATH_DOT", 36, false, false, false, 7, null);
            parseTypeArray[37] = PATH_SQB = new ParseType("PATH_SQB", 37, false, false, false, 7, null);
            parseTypeArray[38] = UNARY = new ParseType("UNARY", 38, false, false, false, 7, null);
            parseTypeArray[39] = BINARY = new ParseType("BINARY", 39, false, false, false, 7, null);
            parseTypeArray[40] = TERNARY = new ParseType("TERNARY", 40, false, false, false, 7, null);
            parseTypeArray[41] = LIST = new ParseType("LIST", 41, false, false, false, 7, null);
            parseTypeArray[42] = STRUCT = new ParseType("STRUCT", 42, false, false, false, 7, null);
            parseTypeArray[43] = MEMBER = new ParseType("MEMBER", 43, false, false, false, 7, null);
            parseTypeArray[44] = CAST = new ParseType("CAST", 44, false, false, false, 7, null);
            parseTypeArray[45] = TYPE = new ParseType("TYPE", 45, false, false, false, 7, null);
            parseTypeArray[46] = CASE = new ParseType("CASE", 46, false, false, false, 7, null);
            parseTypeArray[47] = WHEN = new ParseType("WHEN", 47, false, false, false, 7, null);
            parseTypeArray[48] = ELSE = new ParseType("ELSE", 48, false, false, false, 7, null);
            parseTypeArray[49] = BAG = new ParseType("BAG", 49, false, false, false, 7, null);
            parseTypeArray[50] = INSERT = new ParseType("INSERT", 50, false, true, true, 1, null);
            parseTypeArray[51] = INSERT_VALUE = new ParseType("INSERT_VALUE", 51, false, true, true, 1, null);
            parseTypeArray[52] = REMOVE = new ParseType("REMOVE", 52, false, true, true, 1, null);
            parseTypeArray[53] = SET = new ParseType("SET", 53, false, true, true, 1, null);
            parseTypeArray[54] = UPDATE = new ParseType("UPDATE", 54, false, true, true, 1, null);
            parseTypeArray[55] = DELETE = new ParseType("DELETE", 55, false, true, true, 1, null);
            parseTypeArray[56] = ASSIGNMENT = new ParseType("ASSIGNMENT", 56, false, false, false, 7, null);
            parseTypeArray[57] = FROM = new ParseType("FROM", 57, false, false, false, 7, null);
            parseTypeArray[58] = FROM_CLAUSE = new ParseType("FROM_CLAUSE", 58, false, false, false, 7, null);
            parseTypeArray[59] = FROM_SOURCE_JOIN = new ParseType("FROM_SOURCE_JOIN", 59, false, false, false, 7, null);
            parseTypeArray[60] = CHECK = new ParseType("CHECK", 60, false, false, false, 7, null);
            parseTypeArray[61] = ON_CONFLICT = new ParseType("ON_CONFLICT", 61, false, false, false, 7, null);
            parseTypeArray[62] = CONFLICT_ACTION = new ParseType("CONFLICT_ACTION", 62, false, false, false, 7, null);
            parseTypeArray[63] = DML_LIST = new ParseType("DML_LIST", 63, false, true, true, 1, null);
            parseTypeArray[64] = RETURNING = new ParseType("RETURNING", 64, false, false, false, 7, null);
            parseTypeArray[65] = RETURNING_ELEM = new ParseType("RETURNING_ELEM", 65, false, false, false, 7, null);
            parseTypeArray[66] = RETURNING_MAPPING = new ParseType("RETURNING_MAPPING", 66, false, false, false, 7, null);
            parseTypeArray[67] = RETURNING_WILDCARD = new ParseType("RETURNING_WILDCARD", 67, false, false, false, 7, null);
            parseTypeArray[68] = CREATE_TABLE = new ParseType("CREATE_TABLE", 68, false, true, false, 5, null);
            parseTypeArray[69] = DROP_TABLE = new ParseType("DROP_TABLE", 69, false, true, false, 5, null);
            parseTypeArray[70] = DROP_INDEX = new ParseType("DROP_INDEX", 70, false, true, false, 5, null);
            parseTypeArray[71] = CREATE_INDEX = new ParseType("CREATE_INDEX", 71, false, true, false, 5, null);
            parseTypeArray[72] = PARAMETER = new ParseType("PARAMETER", 72, false, false, false, 7, null);
            parseTypeArray[73] = EXEC = new ParseType("EXEC", 73, false, true, false, 5, null);
            parseTypeArray[74] = PRECISION = new ParseType("PRECISION", 74, false, false, false, 7, null);
            $VALUES = parseTypeArray;
        }

        @NotNull
        public final String getIdentifier() {
            return this.identifier;
        }

        public final boolean isJoin() {
            return this.isJoin;
        }

        public final boolean isTopLevelType() {
            return this.isTopLevelType;
        }

        public final boolean isDml() {
            return this.isDml;
        }

        private ParseType(boolean isJoin, boolean isTopLevelType, boolean isDml) {
            String string;
            this.isJoin = isJoin;
            this.isTopLevelType = isTopLevelType;
            this.isDml = isDml;
            String string2 = this.name();
            ParseType parseType = this;
            boolean bl = false;
            String string3 = string2;
            if (string3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String string4 = string3.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(string4, "(this as java.lang.String).toLowerCase()");
            parseType.identifier = string = string4;
        }

        /* synthetic */ ParseType(String string, int n, boolean bl, boolean bl2, boolean bl3, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 1) != 0) {
                bl = false;
            }
            if ((n2 & 2) != 0) {
                bl2 = false;
            }
            if ((n2 & 4) != 0) {
                bl3 = false;
            }
            this(bl, bl2, bl3);
        }

        public static ParseType[] values() {
            return (ParseType[])$VALUES.clone();
        }

        public static ParseType valueOf(String string) {
            return Enum.valueOf(ParseType.class, string);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007H\u00c6\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H\u00c6\u0003J?\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H\u00c6\u0001J-\u0010\u0019\u001a\u00020\u00002#\u0010\u001a\u001a\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00070\u001b\u00a2\u0006\u0002\b\u001cH\u0002J\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001fJ\"\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00050 2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u001fJ\u000e\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020%J\u0013\u0010&\u001a\u00020\r2\b\u0010'\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u000e\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020%J\t\u0010+\u001a\u00020,H\u00d6\u0001J\u0006\u0010-\u001a\u00020.J\t\u0010/\u001a\u00020%H\u00d6\u0001J \u00100\u001a\u00020)2\u0006\u0010*\u001a\u00020%2\u0006\u00101\u001a\u0002022\b\b\u0002\u00103\u001a\u000204R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000eR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u00065"}, d2={"Lorg/partiql/lang/syntax/SqlParser$ParseNode;", "", "type", "Lorg/partiql/lang/syntax/SqlParser$ParseType;", "token", "Lorg/partiql/lang/syntax/Token;", "children", "", "remaining", "(Lorg/partiql/lang/syntax/SqlParser$ParseType;Lorg/partiql/lang/syntax/Token;Ljava/util/List;Ljava/util/List;)V", "getChildren", "()Ljava/util/List;", "isNumericLiteral", "", "()Z", "getRemaining", "getToken", "()Lorg/partiql/lang/syntax/Token;", "getType", "()Lorg/partiql/lang/syntax/SqlParser$ParseType;", "component1", "component2", "component3", "component4", "copy", "derive", "tokensHandler", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "deriveExpected", "expectedType", "Lorg/partiql/lang/syntax/TokenType;", "Lkotlin/Pair;", "expectedType1", "expectedType2", "deriveExpectedKeyword", "keyword", "", "equals", "other", "errMalformedParseTree", "", "message", "hashCode", "", "numberValue", "", "toString", "unsupported", "errorCode", "Lorg/partiql/lang/errors/ErrorCode;", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    public static final class ParseNode {
        private final boolean isNumericLiteral;
        @NotNull
        private final ParseType type;
        @Nullable
        private final Token token;
        @NotNull
        private final List<ParseNode> children;
        @NotNull
        private final List<Token> remaining;

        private final ParseNode derive(Function1<? super List<Token>, ? extends List<Token>> tokensHandler) {
            return ParseNode.copy$default(this, null, null, null, tokensHandler.invoke(this.remaining), 7, null);
        }

        @NotNull
        public final ParseNode deriveExpected(@NotNull TokenType expectedType) {
            Intrinsics.checkParameterIsNotNull((Object)expectedType, "expectedType");
            return this.derive((Function1<? super List<Token>, ? extends List<Token>>)new Function1<List<? extends Token>, List<? extends Token>>(expectedType){
                final /* synthetic */ TokenType $expectedType;

                @NotNull
                public final List<Token> invoke(@NotNull List<Token> $this$derive) {
                    List<Token> list;
                    List<Token> $this$head$iv;
                    Intrinsics.checkParameterIsNotNull($this$derive, "$receiver");
                    List<Token> list2 = $this$derive;
                    TokenType tokenType = this.$expectedType;
                    boolean $i$f$getHead = false;
                    T t = CollectionsKt.firstOrNull($this$head$iv);
                    Token token = (Token)t;
                    if (tokenType != (token != null ? token.getType() : null)) {
                        $this$head$iv = $this$derive;
                        $i$f$getHead = false;
                        Void void_ = TokenListExtensionsKt.errExpectedTokenType(CollectionsKt.firstOrNull($this$head$iv), this.$expectedType);
                        throw null;
                    }
                    List<Token> $this$tail$iv = $this$derive;
                    boolean $i$f$getTail = false;
                    switch ($this$tail$iv.size()) {
                        case 0: 
                        case 1: {
                            List<T> list3 = Collections.emptyList();
                            list = list3;
                            Intrinsics.checkExpressionValueIsNotNull(list3, "emptyList()");
                            break;
                        }
                        default: {
                            list = $this$tail$iv.subList(1, $this$tail$iv.size());
                        }
                    }
                    return list;
                }
                {
                    this.$expectedType = tokenType;
                    super(1);
                }
            });
        }

        @NotNull
        public final Pair<ParseNode, Token> deriveExpected(@NotNull TokenType expectedType1, @NotNull TokenType expectedType2) {
            List<Object> list;
            List<Token> $this$tail$iv;
            Object pvmap;
            List<Token> $this$head$iv;
            Intrinsics.checkParameterIsNotNull((Object)expectedType1, "expectedType1");
            Intrinsics.checkParameterIsNotNull((Object)expectedType2, "expectedType2");
            List<Token> list2 = this.remaining;
            TokenType tokenType = expectedType1;
            boolean $i$f$getHead = false;
            Object object = CollectionsKt.firstOrNull($this$head$iv);
            Token token = (Token)object;
            if (tokenType != (token != null ? token.getType() : null)) {
                $this$head$iv = this.remaining;
                tokenType = expectedType2;
                $i$f$getHead = false;
                object = CollectionsKt.firstOrNull($this$head$iv);
                Token token2 = (Token)object;
                if (tokenType != (token2 != null ? token2.getType() : null)) {
                    pvmap = new PropertyValueMap(null, 1, null);
                    ((PropertyValueMap)pvmap).set(Property.EXPECTED_TOKEN_TYPE_1_OF_2, expectedType1);
                    ((PropertyValueMap)pvmap).set(Property.EXPECTED_TOKEN_TYPE_2_OF_2, expectedType2);
                    Void void_ = TokenListExtensionsKt.err(this.remaining, "Expected " + (Object)((Object)this.type), ErrorCode.PARSE_EXPECTED_2_TOKEN_TYPES, (PropertyValueMap)pvmap);
                    throw null;
                }
            }
            pvmap = this.remaining;
            List list3 = null;
            Token token3 = null;
            Token token4 = null;
            ParseNode parseNode = this;
            boolean $i$f$getTail = false;
            switch ($this$tail$iv.size()) {
                case 0: 
                case 1: {
                    List list4 = Collections.emptyList();
                    list = list4;
                    Intrinsics.checkExpressionValueIsNotNull(list4, "emptyList()");
                    break;
                }
                default: {
                    list = $this$tail$iv.subList(1, $this$tail$iv.size());
                }
            }
            List<Object> list5 = list;
            $this$tail$iv = this.remaining;
            parseNode = ParseNode.copy$default(parseNode, token4, token3, list3, list5, 7, null);
            $i$f$getHead = false;
            Token token5 = token4 = CollectionsKt.firstOrNull($this$head$iv);
            if (token5 == null) {
                Intrinsics.throwNpe();
            }
            Token token6 = token5;
            ParseNode parseNode2 = parseNode;
            return new Pair<ParseNode, Token>(parseNode2, token6);
        }

        @NotNull
        public final ParseNode deriveExpectedKeyword(@NotNull String keyword) {
            Intrinsics.checkParameterIsNotNull(keyword, "keyword");
            return this.derive((Function1<? super List<Token>, ? extends List<Token>>)new Function1<List<? extends Token>, List<? extends Token>>(keyword){
                final /* synthetic */ String $keyword;

                @NotNull
                public final List<Token> invoke(@NotNull List<Token> $this$derive) {
                    Intrinsics.checkParameterIsNotNull($this$derive, "$receiver");
                    return TokenListExtensionsKt.tailExpectedKeyword($this$derive, this.$keyword);
                }
                {
                    this.$keyword = string;
                    super(1);
                }
            });
        }

        public final boolean isNumericLiteral() {
            return this.isNumericLiteral;
        }

        @NotNull
        public final Number numberValue() {
            Object object = this.token;
            if (object == null || (object = ((Token)object).getValue()) == null || (object = IonValueExtensionsKt.numberValue((IonValue)object)) == null) {
                Void void_ = ParseNode.unsupported$default(this, "Could not interpret token as number", ErrorCode.PARSE_EXPECTED_NUMBER, null, 4, null);
                throw null;
            }
            return object;
        }

        @NotNull
        public final Void unsupported(@NotNull String message, @NotNull ErrorCode errorCode, @NotNull PropertyValueMap errorContext) {
            Intrinsics.checkParameterIsNotNull(message, "message");
            Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
            Intrinsics.checkParameterIsNotNull(errorContext, "errorContext");
            Void void_ = TokenListExtensionsKt.err(this.remaining, message, errorCode, errorContext);
            throw null;
        }

        public static /* synthetic */ Void unsupported$default(ParseNode parseNode, String string, ErrorCode errorCode, PropertyValueMap propertyValueMap, int n, Object object) {
            if ((n & 4) != 0) {
                propertyValueMap = new PropertyValueMap(null, 1, null);
            }
            return parseNode.unsupported(string, errorCode, propertyValueMap);
        }

        @NotNull
        public final Void errMalformedParseTree(@NotNull String message) {
            PropertyValueMap context;
            block0: {
                Intrinsics.checkParameterIsNotNull(message, "message");
                context = new PropertyValueMap(null, 1, null);
                Object object = this.token;
                if (object == null || (object = ((Token)object).getSpan()) == null) break block0;
                Object object2 = object;
                boolean bl = false;
                boolean bl2 = false;
                Object it = object2;
                boolean bl3 = false;
                context.set(Property.LINE_NUMBER, ((SourceSpan)it).getLine());
                context.set(Property.COLUMN_NUMBER, ((SourceSpan)it).getColumn());
            }
            throw (Throwable)new ParserException(message, ErrorCode.PARSE_MALFORMED_PARSE_TREE, context, null, 8, null);
        }

        @NotNull
        public final ParseType getType() {
            return this.type;
        }

        @Nullable
        public final Token getToken() {
            return this.token;
        }

        @NotNull
        public final List<ParseNode> getChildren() {
            return this.children;
        }

        @NotNull
        public final List<Token> getRemaining() {
            return this.remaining;
        }

        /*
         * Unable to fully structure code
         */
        public ParseNode(@NotNull ParseType type, @Nullable Token token, @NotNull List<ParseNode> children, @NotNull List<Token> remaining) {
            Intrinsics.checkParameterIsNotNull((Object)type, "type");
            Intrinsics.checkParameterIsNotNull(children, "children");
            Intrinsics.checkParameterIsNotNull(remaining, "remaining");
            super();
            this.type = type;
            this.token = token;
            this.children = children;
            this.remaining = remaining;
            if (this.type != ParseType.ATOM) ** GOTO lbl-1000
            v0 = this.token;
            v1 = v0 != null ? v0.getType() : null;
            if (v1 == null) ** GOTO lbl-1000
            switch (SqlParser$ParseNode$WhenMappings.$EnumSwitchMapping$0[v1.ordinal()]) {
                case 1: 
                case 2: {
                    v2 = this.token.getValue();
                    if (v2 != null) {
                        v3 = IonValueExtensionsKt.isNumeric(v2);
                        break;
                    }
                    v3 = false;
                    break;
                }
                default: lbl-1000:
                // 2 sources

                {
                    v3 = false;
                }
            }
            if (v3) {
                v4 = true;
            } else lbl-1000:
            // 2 sources

            {
                v4 = false;
            }
            this.isNumericLiteral = v4;
        }

        @NotNull
        public final ParseType component1() {
            return this.type;
        }

        @Nullable
        public final Token component2() {
            return this.token;
        }

        @NotNull
        public final List<ParseNode> component3() {
            return this.children;
        }

        @NotNull
        public final List<Token> component4() {
            return this.remaining;
        }

        @NotNull
        public final ParseNode copy(@NotNull ParseType type, @Nullable Token token, @NotNull List<ParseNode> children, @NotNull List<Token> remaining) {
            Intrinsics.checkParameterIsNotNull((Object)type, "type");
            Intrinsics.checkParameterIsNotNull(children, "children");
            Intrinsics.checkParameterIsNotNull(remaining, "remaining");
            return new ParseNode(type, token, children, remaining);
        }

        public static /* synthetic */ ParseNode copy$default(ParseNode parseNode, ParseType parseType, Token token, List list, List list2, int n, Object object) {
            if ((n & 1) != 0) {
                parseType = parseNode.type;
            }
            if ((n & 2) != 0) {
                token = parseNode.token;
            }
            if ((n & 4) != 0) {
                list = parseNode.children;
            }
            if ((n & 8) != 0) {
                list2 = parseNode.remaining;
            }
            return parseNode.copy(parseType, token, list, list2);
        }

        @NotNull
        public String toString() {
            return "ParseNode(type=" + (Object)((Object)this.type) + ", token=" + this.token + ", children=" + this.children + ", remaining=" + this.remaining + ")";
        }

        public int hashCode() {
            ParseType parseType = this.type;
            Token token = this.token;
            List<ParseNode> list = this.children;
            List<Token> list2 = this.remaining;
            return (((parseType != null ? ((Object)((Object)parseType)).hashCode() : 0) * 31 + (token != null ? ((Object)token).hashCode() : 0)) * 31 + (list != null ? ((Object)list).hashCode() : 0)) * 31 + (list2 != null ? ((Object)list2).hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof ParseNode)) break block3;
                    ParseNode parseNode = (ParseNode)object;
                    if (!Intrinsics.areEqual((Object)this.type, (Object)parseNode.type) || !Intrinsics.areEqual(this.token, parseNode.token) || !Intrinsics.areEqual(this.children, parseNode.children) || !Intrinsics.areEqual(this.remaining, parseNode.remaining)) break block3;
                }
                return true;
            }
            return false;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001f\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2={"Lorg/partiql/lang/syntax/SqlParser$AsAlias;", "", "name", "Lorg/partiql/lang/ast/SymbolicName;", "node", "Lorg/partiql/lang/syntax/SqlParser$ParseNode;", "(Lorg/partiql/lang/ast/SymbolicName;Lorg/partiql/lang/syntax/SqlParser$ParseNode;)V", "getName", "()Lorg/partiql/lang/ast/SymbolicName;", "getNode", "()Lorg/partiql/lang/syntax/SqlParser$ParseNode;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "lang"})
    private static final class AsAlias {
        @Nullable
        private final SymbolicName name;
        @NotNull
        private final ParseNode node;

        @Nullable
        public final SymbolicName getName() {
            return this.name;
        }

        @NotNull
        public final ParseNode getNode() {
            return this.node;
        }

        public AsAlias(@Nullable SymbolicName name, @NotNull ParseNode node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            this.name = name;
            this.node = node;
        }

        @Nullable
        public final SymbolicName component1() {
            return this.name;
        }

        @NotNull
        public final ParseNode component2() {
            return this.node;
        }

        @NotNull
        public final AsAlias copy(@Nullable SymbolicName name, @NotNull ParseNode node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            return new AsAlias(name, node);
        }

        public static /* synthetic */ AsAlias copy$default(AsAlias asAlias, SymbolicName symbolicName, ParseNode parseNode, int n, Object object) {
            if ((n & 1) != 0) {
                symbolicName = asAlias.name;
            }
            if ((n & 2) != 0) {
                parseNode = asAlias.node;
            }
            return asAlias.copy(symbolicName, parseNode);
        }

        @NotNull
        public String toString() {
            return "AsAlias(name=" + this.name + ", node=" + this.node + ")";
        }

        public int hashCode() {
            SymbolicName symbolicName = this.name;
            ParseNode parseNode = this.node;
            return (symbolicName != null ? ((Object)symbolicName).hashCode() : 0) * 31 + (parseNode != null ? ((Object)parseNode).hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof AsAlias)) break block3;
                    AsAlias asAlias = (AsAlias)object;
                    if (!Intrinsics.areEqual(this.name, asAlias.name) || !Intrinsics.areEqual(this.node, asAlias.node)) break block3;
                }
                return true;
            }
            return false;
        }
    }
}

