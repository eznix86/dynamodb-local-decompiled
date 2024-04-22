/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.partiql.pig.runtime.SymbolPrimitive
 */
package org.partiql.lang.eval;

import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonString;
import com.amazon.ion.IonValue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AggregateCallSiteListMeta;
import org.partiql.lang.ast.AggregateRegisterIdMeta;
import org.partiql.lang.ast.AstDeserializerBuilder;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.CallAgg;
import org.partiql.lang.ast.CaseSensitivity;
import org.partiql.lang.ast.CreateIndex;
import org.partiql.lang.ast.CreateTable;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DataType;
import org.partiql.lang.ast.DateTimeType;
import org.partiql.lang.ast.DropIndex;
import org.partiql.lang.ast.DropTable;
import org.partiql.lang.ast.Exec;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.ExprNodeToStatementKt;
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.FromSourceExpr;
import org.partiql.lang.ast.FromSourceJoin;
import org.partiql.lang.ast.FromSourceLet;
import org.partiql.lang.ast.FromSourceUnpivot;
import org.partiql.lang.ast.GroupBy;
import org.partiql.lang.ast.GroupByItem;
import org.partiql.lang.ast.GroupingStrategy;
import org.partiql.lang.ast.JoinOp;
import org.partiql.lang.ast.LetBinding;
import org.partiql.lang.ast.LetSource;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.Parameter;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.PathComponent;
import org.partiql.lang.ast.PathComponentExpr;
import org.partiql.lang.ast.PathComponentUnpivot;
import org.partiql.lang.ast.PathComponentWildcard;
import org.partiql.lang.ast.ScopeQualifier;
import org.partiql.lang.ast.SearchedCase;
import org.partiql.lang.ast.SearchedCaseWhen;
import org.partiql.lang.ast.Select;
import org.partiql.lang.ast.SelectListItem;
import org.partiql.lang.ast.SelectListItemExpr;
import org.partiql.lang.ast.SelectListItemProjectAll;
import org.partiql.lang.ast.SelectListItemStar;
import org.partiql.lang.ast.SelectProjection;
import org.partiql.lang.ast.SelectProjectionList;
import org.partiql.lang.ast.SelectProjectionPivot;
import org.partiql.lang.ast.SelectProjectionValue;
import org.partiql.lang.ast.Seq;
import org.partiql.lang.ast.SeqType;
import org.partiql.lang.ast.SetQuantifier;
import org.partiql.lang.ast.SimpleCase;
import org.partiql.lang.ast.SimpleCaseWhen;
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.ast.SqlDataType;
import org.partiql.lang.ast.StatementToExprNodeKt;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.StructField;
import org.partiql.lang.ast.SymbolicName;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.TypedOp;
import org.partiql.lang.ast.UniqueNameMeta;
import org.partiql.lang.ast.VariableReference;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.BaseExprValue;
import org.partiql.lang.eval.BindingCase;
import org.partiql.lang.eval.BindingName;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.BindingsKt;
import org.partiql.lang.eval.CompilationContext;
import org.partiql.lang.eval.CompileOptions;
import org.partiql.lang.eval.CompiledFromSource;
import org.partiql.lang.eval.CompiledLetSource;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.EvaluatingCompiler;
import org.partiql.lang.eval.EvaluatingCompiler$WhenMappings;
import org.partiql.lang.eval.EvaluatingCompiler$compileQueryWithoutProjection$1$$special$;
import org.partiql.lang.eval.EvaluatingCompiler$compileSelect$2$1$getQueryThunk$;
import org.partiql.lang.eval.EvaluatingCompiler$compileSelect$2$3$$special$;
import org.partiql.lang.eval.EvaluatingCompilerKt;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.EvaluationSession;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprAggregator;
import org.partiql.lang.eval.ExprAggregatorFactory;
import org.partiql.lang.eval.ExprFunction;
import org.partiql.lang.eval.ExprNodeExtensionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.Expression;
import org.partiql.lang.eval.ExpressionContext;
import org.partiql.lang.eval.FromProduction;
import org.partiql.lang.eval.Group;
import org.partiql.lang.eval.GroupKeyExprValue;
import org.partiql.lang.eval.JoinExpansion;
import org.partiql.lang.eval.MultipleProjectionElement;
import org.partiql.lang.eval.Named;
import org.partiql.lang.eval.ProjectionElement;
import org.partiql.lang.eval.RegisterBank;
import org.partiql.lang.eval.SequenceExprValue;
import org.partiql.lang.eval.SingleProjectionElement;
import org.partiql.lang.eval.StandardNamesKt;
import org.partiql.lang.eval.StructOrdering;
import org.partiql.lang.eval.ThunkFactory;
import org.partiql.lang.eval.binding.Alias;
import org.partiql.lang.eval.binding.LocalsBinder;
import org.partiql.lang.eval.binding.LocalsBinderKt;
import org.partiql.lang.eval.builtins.storedprocedure.StoredProcedure;
import org.partiql.lang.eval.like.PatternPart;
import org.partiql.lang.eval.like.PatternPartKt;
import org.partiql.lang.eval.time.Time;
import org.partiql.lang.eval.visitors.PartiqlAstSanityValidator;
import org.partiql.lang.syntax.SqlParser;
import org.partiql.lang.util.CollectionExtensionsKt;
import org.partiql.lang.util.IonValueExtensionsKt;
import org.partiql.lang.util.NumberExtensionsKt;
import org.partiql.lang.util.StringExtensionsKt;
import org.partiql.lang.util.ThreadInterruptUtilsKt;
import org.partiql.lang.util.WhenAsExpressionHelper;
import org.partiql.pig.runtime.SymbolPrimitive;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u00a2\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0000\u0018\u00002\u00020\u0001:\b\u00b8\u0001\u00b9\u0001\u00ba\u0001\u00bb\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u001a\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0002J<\u0010\u001e\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u001f0\u000e2\u0006\u0010 \u001a\u00020\u001b2\b\u0010!\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\"\u001a\u0004\u0018\u00010\u001dH\u0002J6\u0010#\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010%\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020%0$2\u0018\u0010'\u001a\u0014\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020(0$H\u0002J\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0006H\u0007J\u000e\u0010)\u001a\u00020*2\u0006\u0010,\u001a\u00020-J \u0010.\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0006\u00102\u001a\u000203H\u0002J \u00104\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0006\u00105\u001a\u000206H\u0002J \u00107\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0006\u00105\u001a\u00020-H\u0002J \u00108\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0006\u00105\u001a\u000209H\u0002J \u0010:\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0006\u00102\u001a\u00020-H\u0002JN\u0010;\u001a\b\u0012\u0004\u0012\u00020=0<2\u0006\u0010>\u001a\u00020?2\u000e\b\u0002\u0010@\u001a\b\u0012\u0004\u0012\u00020=0A2\b\b\u0002\u0010B\u001a\u00020C2\u001c\b\u0002\u0010D\u001a\u0016\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&\u0018\u00010/j\u0004\u0018\u0001`1H\u0002J\u001c\u0010E\u001a\b\u0012\u0004\u0012\u00020F0<2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020H0<H\u0002J.\u0010I\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\f\u0010J\u001a\b\u0012\u0004\u0012\u00020F0<2\u0006\u0010K\u001a\u00020LH\u0002J\u0016\u0010M\u001a\b\u0012\u0004\u0012\u00020N0<2\u0006\u0010O\u001a\u00020PH\u0002J \u0010Q\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0006\u00102\u001a\u00020RH\u0002J \u0010S\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0006\u00102\u001a\u00020TH\u0002J \u0010U\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0006\u00102\u001a\u00020VH\u0002J>\u0010W\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010Z\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010[\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002JL\u0010\\\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\f\u0010]\u001a\b\u0012\u0004\u0012\u00020-0<2\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010^\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010_\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010`\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010a\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J.\u0010b\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\f\u0010]\u001a\b\u0012\u0004\u0012\u00020-0<2\u0006\u0010Y\u001a\u00020LH\u0002JL\u0010c\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\f\u0010d\u001a\b\u0012\u0004\u0012\u00020-0<2\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010e\u001a\u00020LH\u0002J>\u0010f\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010g\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010h\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010i\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010j\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010k\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010l\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010m\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J>\u0010n\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u001c\u0010X\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`10<2\u0006\u0010Y\u001a\u00020LH\u0002J$\u0010o\u001a\u0016\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&\u0018\u00010/j\u0004\u0018\u0001`12\u0006\u00102\u001a\u00020VH\u0002J \u0010p\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0006\u00102\u001a\u00020qH\u0002J \u0010r\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0006\u00102\u001a\u00020sH\u0002J:\u0010t\u001a\u001e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020&0$j\b\u0012\u0004\u0012\u00020&`u2\u0006\u0010v\u001a\u00020L2\f\u0010w\u001a\b\u0012\u0004\u0012\u00020y0xH\u0002JA\u0010z\u001a\u0014\u0012\u0004\u0012\u000200\u0012\n\u0012\b\u0012\u0004\u0012\u00020|0{0/2\u0006\u0010}\u001a\u00020~2\f\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020=0<2\u000f\u0010\u0080\u0001\u001a\n\u0012\u0004\u0012\u00020N\u0018\u00010<H\u0002J\"\u0010\u0081\u0001\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0007\u00102\u001a\u00030\u0082\u0001H\u0002J\"\u0010\u0083\u0001\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0007\u0010\u0084\u0001\u001a\u00020~H\u0002J\u001a\u0010\u0085\u0001\u001a\t\u0012\u0005\u0012\u00030\u0086\u00010<2\b\u0010\u0087\u0001\u001a\u00030\u0088\u0001H\u0002J\"\u0010\u0089\u0001\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0007\u00102\u001a\u00030\u008a\u0001H\u0002J\"\u0010\u008b\u0001\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0007\u00102\u001a\u00030\u008c\u0001H\u0002J\"\u0010\u008d\u0001\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0007\u00102\u001a\u00030\u008e\u0001H\u0002J\"\u0010\u008f\u0001\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0007\u00105\u001a\u00030\u0090\u0001H\u0002J\"\u0010\u0091\u0001\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0007\u00102\u001a\u00030\u0092\u0001H\u0002J\"\u0010\u0093\u0001\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0007\u00102\u001a\u00030\u0094\u0001H\u0002Jl\u0010\u0095\u0001\u001a\u0017\u0012\u0004\u0012\u000200\u0012\u0005\u0012\u00030\u0096\u0001\u0012\u0006\u0012\u0004\u0018\u00010&0$2\u001b\u0010\u0097\u0001\u001a\u0016\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&\u0018\u00010/j\u0004\u0018\u0001`12/\u0010\u0098\u0001\u001a*\u0012\u0004\u0012\u000200\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0<\u0012\u0004\u0012\u00020&0$j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0<`uH\u0002J(\u0010\u0099\u0001\u001a\u0015\u0012\u0004\u0012\u000200\u0012\u0005\u0012\u00030\u0096\u0001\u0012\u0004\u0012\u0002000$2\n\u0010\u009a\u0001\u001a\u0005\u0018\u00010\u009b\u0001H\u0002J\"\u0010\u009c\u0001\u001a\u00020&2\r\u0010\u009d\u0001\u001a\b\u0012\u0004\u0012\u00020&0{2\b\u0010\u009e\u0001\u001a\u00030\u009f\u0001H\u0002J\u001c\u0010\u00a0\u0001\u001a\u00020&2\u0007\u0010}\u001a\u00030\u00a1\u00012\b\u0010\u00a2\u0001\u001a\u00030\u00a3\u0001H\u0007J\u0019\u0010\u00a0\u0001\u001a\u00020&2\u0006\u0010}\u001a\u00020-2\b\u0010\u00a2\u0001\u001a\u00030\u00a3\u0001J7\u0010\u00a4\u0001\u001a\u00030\u00a5\u00012\u0017\u0010\u00a6\u0001\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020&0/j\u0002`12\u0007\u0010\u00a7\u0001\u001a\u0002002\t\u0010\u00a8\u0001\u001a\u0004\u0018\u00010\u001dH\u0002J!\u0010\u00a9\u0001\u001a\u00020\u00102\u0007\u0010\u00aa\u0001\u001a\u00020\u00062\u0007\u0010\u00ab\u0001\u001a\u00020\u000f2\u0006\u0010Y\u001a\u00020LJB\u0010\u00ac\u0001\u001a\u0003H\u00ad\u0001\"\u0005\b\u0000\u0010\u00ad\u00012\b\u0010\u00ae\u0001\u001a\u00030\u00af\u00012\u000e\u0010\u00b0\u0001\u001a\t\u0012\u0004\u0012\u00020\u00060\u00b1\u00012\u000f\u0010\u00b2\u0001\u001a\n\u0012\u0005\u0012\u0003H\u00ad\u00010\u00b3\u0001H\u0002\u00a2\u0006\u0003\u0010\u00b4\u0001J\r\u0010\u00b5\u0001\u001a\u00020&*\u00020(H\u0002J\r\u0010\u00b5\u0001\u001a\u00020&*\u00020%H\u0002J\r\u0010\u00b5\u0001\u001a\u00020&*\u00020\u0006H\u0002J\u0013\u0010\u00b6\u0001\u001a\u00020&*\u00020&H\u0000\u00a2\u0006\u0003\b\u00b7\u0001R&\u0010\r\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0004\u0012\u00020\u00100\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00138BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u00bc\u0001"}, d2={"Lorg/partiql/lang/eval/EvaluatingCompiler;", "", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "functions", "", "", "Lorg/partiql/lang/eval/ExprFunction;", "procedures", "Lorg/partiql/lang/eval/builtins/storedprocedure/StoredProcedure;", "compileOptions", "Lorg/partiql/lang/eval/CompileOptions;", "(Lorg/partiql/lang/eval/ExprValueFactory;Ljava/util/Map;Ljava/util/Map;Lorg/partiql/lang/eval/CompileOptions;)V", "builtinAggregates", "Lkotlin/Pair;", "Lorg/partiql/lang/ast/SetQuantifier;", "Lorg/partiql/lang/eval/ExprAggregatorFactory;", "compilationContextStack", "Ljava/util/Stack;", "Lorg/partiql/lang/eval/CompilationContext;", "currentCompilationContext", "getCurrentCompilationContext", "()Lorg/partiql/lang/eval/CompilationContext;", "thunkFactory", "Lorg/partiql/lang/eval/ThunkFactory;", "checkEscapeChar", "escape", "Lcom/amazon/ion/IonValue;", "locationMeta", "Lorg/partiql/lang/ast/SourceLocationMeta;", "checkPattern", "", "pattern", "patternLocationMeta", "escapeLocationMeta", "comparisonAccumulator", "Lkotlin/Function2;", "", "Lorg/partiql/lang/eval/ExprValue;", "cmpFunc", "", "compile", "Lorg/partiql/lang/eval/Expression;", "source", "originalAst", "Lorg/partiql/lang/ast/ExprNode;", "compileCallAgg", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Environment;", "Lorg/partiql/lang/eval/ThunkEnv;", "expr", "Lorg/partiql/lang/ast/CallAgg;", "compileDate", "node", "Lorg/partiql/lang/ast/DateTimeType$Date;", "compileDdl", "compileExec", "Lorg/partiql/lang/ast/Exec;", "compileExprNode", "compileFromSources", "", "Lorg/partiql/lang/eval/CompiledFromSource;", "fromSource", "Lorg/partiql/lang/ast/FromSource;", "sources", "", "joinExpansion", "Lorg/partiql/lang/eval/JoinExpansion;", "conditionThunk", "compileGroupByExpressions", "Lorg/partiql/lang/eval/EvaluatingCompiler$CompiledGroupByItem;", "groupByItems", "Lorg/partiql/lang/ast/GroupByItem;", "compileGroupKeyThunk", "compiledGroupByItems", "selectMetas", "Lorg/partiql/lang/ast/MetaContainer;", "compileLetSources", "Lorg/partiql/lang/eval/CompiledLetSource;", "letSource", "Lorg/partiql/lang/ast/LetSource;", "compileLiteral", "Lorg/partiql/lang/ast/Literal;", "compileLiteralMissing", "Lorg/partiql/lang/ast/LiteralMissing;", "compileNAry", "Lorg/partiql/lang/ast/NAry;", "compileNAryAdd", "argThunks", "metas", "compileNAryAnd", "compileNAryBetween", "compileNAryCall", "args", "compileNAryDiv", "compileNAryEq", "compileNAryGt", "compileNAryGte", "compileNAryIn", "compileNAryLike", "argExprs", "operatorMetas", "compileNAryLte", "compileNAryMod", "compileNAryNe", "compileNAryNot", "compileNAryOr", "compileNAryStringConcat", "compileNArySub", "compileNaryLt", "compileNaryMul", "compileOptimizedNAry", "compileParameter", "Lorg/partiql/lang/ast/Parameter;", "compilePath", "Lorg/partiql/lang/ast/Path;", "compilePathComponents", "Lorg/partiql/lang/eval/ThunkEnvValue;", "pathMetas", "remainingComponents", "Ljava/util/LinkedList;", "Lorg/partiql/lang/ast/PathComponent;", "compileQueryWithoutProjection", "Lkotlin/sequences/Sequence;", "Lorg/partiql/lang/eval/FromProduction;", "ast", "Lorg/partiql/lang/ast/Select;", "compiledSources", "compiledLetSources", "compileSearchedCase", "Lorg/partiql/lang/ast/SearchedCase;", "compileSelect", "selectExpr", "compileSelectListToProjectionElements", "Lorg/partiql/lang/eval/ProjectionElement;", "selectList", "Lorg/partiql/lang/ast/SelectProjectionList;", "compileSeq", "Lorg/partiql/lang/ast/Seq;", "compileSimpleCase", "Lorg/partiql/lang/ast/SimpleCase;", "compileStruct", "Lorg/partiql/lang/ast/Struct;", "compileTime", "Lorg/partiql/lang/ast/DateTimeType$Time;", "compileTyped", "Lorg/partiql/lang/ast/Typed;", "compileVariableReference", "Lorg/partiql/lang/ast/VariableReference;", "createFilterHavingAndProjectClosure", "Lorg/partiql/lang/eval/Group;", "havingThunk", "selectProjectionThunk", "createGetGroupEnvClosure", "groupAsName", "Lorg/partiql/lang/ast/SymbolicName;", "createStructExprValue", "seq", "ordering", "Lorg/partiql/lang/eval/StructOrdering;", "eval", "Lcom/amazon/ion/IonSexp;", "session", "Lorg/partiql/lang/eval/EvaluationSession;", "evalLimit", "", "limitThunk", "env", "limitLocationMeta", "getAggregatorFactory", "funcName", "setQuantifier", "nestCompilationContext", "R", "expressionContext", "Lorg/partiql/lang/eval/ExpressionContext;", "fromSourceNames", "", "block", "Lkotlin/Function0;", "(Lorg/partiql/lang/eval/ExpressionContext;Ljava/util/Set;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "exprValue", "unpivot", "unpivot$lang", "Accumulator", "CompiledGroupByItem", "FromSourceBindingNamePair", "UnpivotedExprValue", "lang"})
public final class EvaluatingCompiler {
    private final ThunkFactory thunkFactory;
    private final Stack<CompilationContext> compilationContextStack;
    private final Map<Pair<String, SetQuantifier>, ExprAggregatorFactory> builtinAggregates;
    private final ExprValueFactory valueFactory;
    private final Map<String, ExprFunction> functions;
    private final Map<String, StoredProcedure> procedures;
    private final CompileOptions compileOptions;

    private final CompilationContext getCurrentCompilationContext() {
        CompilationContext compilationContext = this.compilationContextStack.peek();
        if (compilationContext == null) {
            throw (Throwable)new EvaluationException("compilationContextStack was empty.", null, null, null, true, 14, null);
        }
        return compilationContext;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final <R> R nestCompilationContext(ExpressionContext expressionContext, Set<String> fromSourceNames, Function0<? extends R> block) {
        this.compilationContextStack.push(this.compilationContextStack.empty() ? new CompilationContext(expressionContext, fromSourceNames) : this.compilationContextStack.peek().createNested(expressionContext, fromSourceNames));
        try {
            R r = block.invoke();
            return r;
        } finally {
            this.compilationContextStack.pop();
        }
    }

    private final ExprValue exprValue(@NotNull Number $this$exprValue) {
        ExprValue exprValue2;
        Number number = $this$exprValue;
        if (number instanceof Integer) {
            exprValue2 = this.valueFactory.newInt($this$exprValue.intValue());
        } else if (number instanceof Long) {
            exprValue2 = this.valueFactory.newInt($this$exprValue.longValue());
        } else if (number instanceof Double) {
            exprValue2 = this.valueFactory.newFloat($this$exprValue.doubleValue());
        } else if (number instanceof BigDecimal) {
            exprValue2 = this.valueFactory.newDecimal((BigDecimal)$this$exprValue);
        } else {
            Void void_ = ExceptionsKt.errNoContext("Cannot convert number to expression value: " + $this$exprValue, true);
            throw null;
        }
        return exprValue2;
    }

    private final ExprValue exprValue(boolean $this$exprValue) {
        return this.valueFactory.newBoolean($this$exprValue);
    }

    private final ExprValue exprValue(@NotNull String $this$exprValue) {
        return this.valueFactory.newString($this$exprValue);
    }

    private final Function2<Number, ExprValue, Number> comparisonAccumulator(Function2<? super Number, ? super Number, Boolean> cmpFunc) {
        return new Function2<Number, ExprValue, Number>(cmpFunc){
            final /* synthetic */ Function2 $cmpFunc;

            @NotNull
            public final Number invoke(@Nullable Number curr, @NotNull ExprValue next) {
                Intrinsics.checkParameterIsNotNull(next, "next");
                Number nextNum = ExprValueExtensionsKt.numberValue(next);
                Number number = curr;
                return number == null ? (Number)nextNum : (Number)((Boolean)this.$cmpFunc.invoke(nextNum, curr) != false ? (Number)nextNum : (Number)curr);
            }
            {
                this.$cmpFunc = function2;
                super(2);
            }
        };
    }

    @NotNull
    public final Expression compile(@NotNull ExprNode originalAst) {
        Intrinsics.checkParameterIsNotNull(originalAst, "originalAst");
        PartiqlAst.VisitorTransform visitorTransformer = this.compileOptions.getVisitorTransformMode().createVisitorTransform$lang();
        ExprNode transformedAst = StatementToExprNodeKt.toExprNode(visitorTransformer.transformStatement(ExprNodeToStatementKt.toAstStatement(originalAst)), this.valueFactory.getIon());
        PartiqlAstSanityValidator.INSTANCE.validate(ExprNodeToStatementKt.toAstStatement(transformedAst));
        Function1 thunk2 = (Function1)this.nestCompilationContext(ExpressionContext.NORMAL, SetsKt.emptySet(), (Function0)new Function0<Function1<? super Environment, ? extends ExprValue>>(this, transformedAst){
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ ExprNode $transformedAst;

            @NotNull
            public final Function1<Environment, ExprValue> invoke() {
                return EvaluatingCompiler.access$compileExprNode(this.this$0, this.$transformedAst);
            }
            {
                this.this$0 = evaluatingCompiler;
                this.$transformedAst = exprNode;
                super(0);
            }
        });
        return new Expression(thunk2){
            final /* synthetic */ Function1 $thunk;

            @NotNull
            public ExprValue eval(@NotNull EvaluationSession session) {
                Intrinsics.checkParameterIsNotNull(session, "session");
                Group group2 = null;
                Map map2 = null;
                Bindings<ExprValue> bindings2 = session.getGlobals();
                Bindings<ExprValue> bindings3 = session.getGlobals();
                EvaluationSession evaluationSession = session;
                Environment env = new Environment(bindings3, bindings2, evaluationSession, map2, group2, 24, null);
                return (ExprValue)this.$thunk.invoke(env);
            }
            {
                this.$thunk = $captured_local_variable$0;
            }
        };
    }

    @Deprecated(message="Please use CompilerPipeline instead")
    @NotNull
    public final Expression compile(@NotNull String source) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        SqlParser parser = new SqlParser(this.valueFactory.getIon());
        ExprNode ast = parser.parseExprNode(source);
        return this.compile(ast);
    }

    @Deprecated(message="Please use CompilerPipeline.compile(ExprNode).eval(EvaluationSession) instead.")
    @NotNull
    public final ExprValue eval(@NotNull IonSexp ast, @NotNull EvaluationSession session) {
        Intrinsics.checkParameterIsNotNull(ast, "ast");
        Intrinsics.checkParameterIsNotNull(session, "session");
        ExprNode exprNode = new AstDeserializerBuilder(this.valueFactory.getIon()).build().deserialize(ast, AstVersion.V0);
        return this.compile(exprNode).eval(session);
    }

    @NotNull
    public final ExprValue eval(@NotNull ExprNode ast, @NotNull EvaluationSession session) {
        Intrinsics.checkParameterIsNotNull(ast, "ast");
        Intrinsics.checkParameterIsNotNull(session, "session");
        return this.compile(ast).eval(session);
    }

    private final Function1<Environment, ExprValue> compileExprNode(ExprNode expr) {
        Function1<Environment, ExprValue> function1;
        ThreadInterruptUtilsKt.checkThreadInterrupted();
        ExprNode exprNode = expr;
        if (exprNode instanceof Literal) {
            function1 = this.compileLiteral((Literal)expr);
        } else if (exprNode instanceof LiteralMissing) {
            function1 = this.compileLiteralMissing((LiteralMissing)expr);
        } else if (exprNode instanceof VariableReference) {
            function1 = this.compileVariableReference((VariableReference)expr);
        } else if (exprNode instanceof NAry) {
            function1 = this.compileNAry((NAry)expr);
        } else if (exprNode instanceof Typed) {
            function1 = this.compileTyped((Typed)expr);
        } else if (exprNode instanceof SimpleCase) {
            function1 = this.compileSimpleCase((SimpleCase)expr);
        } else if (exprNode instanceof SearchedCase) {
            function1 = this.compileSearchedCase((SearchedCase)expr);
        } else if (exprNode instanceof Path) {
            function1 = this.compilePath((Path)expr);
        } else if (exprNode instanceof Struct) {
            function1 = this.compileStruct((Struct)expr);
        } else if (exprNode instanceof Seq) {
            function1 = this.compileSeq((Seq)expr);
        } else if (exprNode instanceof Select) {
            function1 = this.compileSelect((Select)expr);
        } else if (exprNode instanceof CallAgg) {
            function1 = this.compileCallAgg((CallAgg)expr);
        } else if (exprNode instanceof Parameter) {
            function1 = this.compileParameter((Parameter)expr);
        } else {
            if (exprNode instanceof DataManipulation) {
                PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(expr.getMetas());
                ErrorCode errorCode = ErrorCode.EVALUATOR_FEATURE_NOT_SUPPORTED_YET;
                String string = "DML operations are not supported yet";
                boolean bl = false;
                boolean bl2 = false;
                PropertyValueMap it = propertyValueMap;
                boolean bl3 = false;
                it.set(Property.FEATURE_NAME, "DataManipulation." + CollectionsKt.first(((DataManipulation)expr).getDmlOperations().getOps()).getName());
                PropertyValueMap propertyValueMap2 = propertyValueMap;
                Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap2, false);
                throw null;
            }
            if (exprNode instanceof CreateTable || exprNode instanceof CreateIndex || exprNode instanceof DropIndex || exprNode instanceof DropTable) {
                function1 = this.compileDdl(expr);
            } else if (exprNode instanceof Exec) {
                function1 = this.compileExec((Exec)expr);
            } else if (exprNode instanceof DateTimeType.Date) {
                function1 = this.compileDate((DateTimeType.Date)expr);
            } else if (exprNode instanceof DateTimeType.Time) {
                function1 = this.compileTime((DateTimeType.Time)expr);
            } else {
                throw new NoWhenBranchMatchedException();
            }
        }
        return function1;
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNAry(NAry expr) {
        Function1<Environment, ExprValue> function1;
        void op;
        void args2;
        NAry nAry = expr;
        NAryOp nAryOp = nAry.component1();
        List<ExprNode> list = nAry.component2();
        MetaContainer metas = nAry.component3();
        Function1<Environment, ExprValue> optimizedThunk = this.compileOptimizedNAry(expr);
        if (optimizedThunk != null) {
            return optimizedThunk;
        }
        Function0<List<? extends Function1<? super Environment, ? extends ExprValue>>> $fun$argThunks$1 = new Function0<List<? extends Function1<? super Environment, ? extends ExprValue>>>(this, (List)args2){
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ List $args;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final List<Function1<Environment, ExprValue>> invoke() {
                void $this$mapTo$iv$iv;
                Iterable $this$map$iv = this.$args;
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    ExprNode exprNode = (ExprNode)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    Function1 function1 = EvaluatingCompiler.access$compileExprNode(this.this$0, (ExprNode)it);
                    collection.add(function1);
                }
                return (List)destination$iv$iv;
            }
            {
                this.this$0 = evaluatingCompiler;
                this.$args = list;
                super(0);
            }
        };
        switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[op.ordinal()]) {
            case 1: {
                function1 = this.compileNAryAdd((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 2: {
                function1 = this.compileNArySub((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 3: {
                function1 = this.compileNaryMul((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 4: {
                function1 = this.compileNAryDiv((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 5: {
                function1 = this.compileNAryMod((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 6: {
                function1 = this.compileNAryEq((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 7: {
                function1 = this.compileNAryNe((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 8: {
                function1 = this.compileNaryLt((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 9: {
                function1 = this.compileNAryLte((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 10: {
                function1 = this.compileNAryGt((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 11: {
                function1 = this.compileNAryGte((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 12: {
                function1 = this.compileNAryBetween((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 13: {
                function1 = this.compileNAryLike((List<? extends ExprNode>)args2, (List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 14: {
                function1 = this.compileNAryIn((List<? extends ExprNode>)args2, metas);
                break;
            }
            case 15: {
                function1 = this.compileNAryNot((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 16: {
                function1 = this.compileNAryAnd((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 17: {
                function1 = this.compileNAryOr((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 18: {
                function1 = this.compileNAryStringConcat((List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 19: {
                function1 = this.compileNAryCall((List<? extends ExprNode>)args2, (List<? extends Function1<? super Environment, ? extends ExprValue>>)$fun$argThunks$1.invoke(), metas);
                break;
            }
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: {
                PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(metas);
                ErrorCode errorCode = ErrorCode.EVALUATOR_FEATURE_NOT_SUPPORTED_YET;
                String string = "NAryOp." + op + " is not yet supported";
                boolean bl = false;
                boolean bl2 = false;
                PropertyValueMap it = propertyValueMap;
                boolean bl3 = false;
                it.set(Property.FEATURE_NAME, "NAryOp." + op);
                PropertyValueMap propertyValueMap2 = propertyValueMap;
                Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap2, false);
                throw null;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return function1;
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileOptimizedNAry(NAry expr) {
        void args2;
        void op;
        NAry nAry = expr;
        NAryOp nAryOp = nAry.component1();
        List<ExprNode> list = nAry.component2();
        MetaContainer metas = nAry.component3();
        if (op == NAryOp.IN && args2.size() == 2) {
            ExprNode targetExpr = (ExprNode)args2.get(0);
            ExprNode collectionExpr = (ExprNode)args2.get(1);
            if (collectionExpr instanceof Seq) {
                boolean bl;
                Function1<Environment, ExprValue> targetThunk;
                block10: {
                    targetThunk = this.compileExprNode(targetExpr);
                    Iterable $this$all$iv = ((Seq)collectionExpr).getValues();
                    boolean $i$f$all = false;
                    if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                        bl = true;
                    } else {
                        for (Object element$iv : $this$all$iv) {
                            ExprNode it = (ExprNode)element$iv;
                            boolean bl2 = false;
                            if (it instanceof Literal) continue;
                            bl = false;
                            break block10;
                        }
                        bl = true;
                    }
                }
                if (bl) {
                    KMappedMarker kMappedMarker;
                    Literal it;
                    Collection<Literal> collection;
                    Iterable $this$mapTo$iv$iv;
                    Iterable $this$map$iv;
                    Object element$iv;
                    TreeSet<Literal> values2 = new TreeSet<Literal>(ExprValueExtensionsKt.getDEFAULT_COMPARATOR());
                    Iterable $i$f$all = ((Seq)collectionExpr).getValues();
                    TreeSet<Literal> treeSet = values2;
                    boolean $i$f$map = false;
                    element$iv = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        ExprNode exprNode = (ExprNode)item$iv$iv;
                        collection = destination$iv$iv;
                        boolean bl3 = false;
                        void v1 = it;
                        if (v1 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.ast.Literal");
                        }
                        kMappedMarker = (Literal)v1;
                        collection.add((Literal)kMappedMarker);
                    }
                    collection = (List)destination$iv$iv;
                    $this$map$iv = collection;
                    $i$f$map = false;
                    $this$mapTo$iv$iv = $this$map$iv;
                    destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    $i$f$mapTo = false;
                    for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        it = (Literal)item$iv$iv;
                        collection = destination$iv$iv;
                        boolean bl4 = false;
                        kMappedMarker = this.valueFactory.newFromIonValue(it.getIonValue());
                        collection.add((Literal)kMappedMarker);
                    }
                    collection = (List)destination$iv$iv;
                    treeSet.addAll(collection);
                    ThunkFactory this_$iv = this.thunkFactory;
                    boolean $i$f$thunkEnv$lang = false;
                    Meta meta = metas.find("$source_location");
                    if (!(meta instanceof SourceLocationMeta)) {
                        meta = null;
                    }
                    SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                    return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, targetThunk, values2){
                        final /* synthetic */ ThunkFactory this$0$inline_fun;
                        final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                        final /* synthetic */ EvaluatingCompiler this$0;
                        final /* synthetic */ Function1 $targetThunk$inlined;
                        final /* synthetic */ TreeSet $values$inlined;
                        {
                            this.this$0$inline_fun = thunkFactory;
                            this.$sourceLocationMeta = sourceLocationMeta;
                            this.this$0 = evaluatingCompiler;
                            this.$targetThunk$inlined = function1;
                            this.$values$inlined = treeSet;
                            super(1);
                        }

                        /*
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final ExprValue invoke(@NotNull Environment env) {
                            ExprValue exprValue2;
                            Intrinsics.checkParameterIsNotNull(env, "env");
                            ThunkFactory thunkFactory = this.this$0$inline_fun;
                            SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                            boolean $i$f$handleException$lang = false;
                            try {
                                boolean bl = false;
                                Environment env2 = env;
                                boolean bl2 = false;
                                ExprValue targetValue = (ExprValue)this.$targetThunk$inlined.invoke(env2);
                                exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBoolean(this.$values$inlined.contains(targetValue));
                            } catch (EvaluationException e$iv) {
                                if (e$iv.getErrorContext() == null) {
                                    throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                }
                                if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                    SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                    if (sourceLocationMeta != null) {
                                        SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                        boolean bl = false;
                                        boolean bl3 = false;
                                        SourceLocationMeta it$iv = sourceLocationMeta2;
                                        boolean bl4 = false;
                                        ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                    }
                                }
                                throw (Throwable)e$iv;
                            } catch (Exception e$iv) {
                                void this_$iv;
                                R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                throw null;
                            }
                            return exprValue2;
                        }
                    };
                }
            }
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNAryAdd(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        Function1 function1;
        switch (argThunks.size()) {
            case 1: {
                Function1<? super Environment, ? extends ExprValue> firstThunk = argThunks.get(0);
                ThunkFactory this_$iv = this.thunkFactory;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = metas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv22 = (SourceLocationMeta)meta;
                function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv22, this, firstThunk){
                    final /* synthetic */ ThunkFactory this$0$inline_fun;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ EvaluatingCompiler this$0;
                    final /* synthetic */ Function1 $firstThunk$inlined;
                    {
                        this.this$0$inline_fun = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.this$0 = evaluatingCompiler;
                        this.$firstThunk$inlined = function1;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0$inline_fun;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            ExprValue exprValue3;
                            boolean bl = false;
                            Environment env2 = env;
                            boolean bl2 = false;
                            ExprValue value = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                            if (value.getType().isUnknown()) {
                                exprValue3 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).getNullValue();
                            } else {
                                ExprValueExtensionsKt.numberValue(value);
                                exprValue3 = value;
                            }
                            exprValue2 = exprValue3;
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl3 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl4 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
                break;
            }
            default: {
                void this_$iv;
                ThunkFactory firstThunk = this.thunkFactory;
                ExprValue nullValue$iv = this.valueFactory.getNullValue();
                boolean $i$f$thunkFold$lang = false;
                Collection sourceLocationMeta$iv22 = argThunks;
                boolean bl = false;
                boolean sourceLocationMeta$iv22 = !sourceLocationMeta$iv22.isEmpty();
                bl = false;
                boolean bl2 = false;
                if (!sourceLocationMeta$iv22) {
                    boolean bl3 = false;
                    String string = "argThunks must not be empty";
                    throw (Throwable)new IllegalArgumentException(string.toString());
                }
                Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
                List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
                void this_$iv$iv = this_$iv;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = metas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
                function1 = new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, nullValue$iv, otherThunks$iv, this){
                    final /* synthetic */ ThunkFactory this$0$inline_fun;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ Function1 $firstThunk$inlined;
                    final /* synthetic */ ExprValue $nullValue$inlined;
                    final /* synthetic */ List $otherThunks$inlined;
                    final /* synthetic */ EvaluatingCompiler this$0;
                    {
                        this.this$0$inline_fun = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.$firstThunk$inlined = function1;
                        this.$nullValue$inlined = exprValue2;
                        this.$otherThunks$inlined = list;
                        this.this$0 = evaluatingCompiler;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0$inline_fun;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            ExprValue exprValue3;
                            block10: {
                                boolean bl = false;
                                Environment env2 = env;
                                boolean bl2 = false;
                                ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                                if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                                    exprValue3 = this.$nullValue$inlined;
                                } else {
                                    Iterable $this$fold$iv = this.$otherThunks$inlined;
                                    boolean $i$f$fold = false;
                                    ExprValue accumulator$iv = firstValue;
                                    for (T element$iv : $this$fold$iv) {
                                        void rValue;
                                        void curr;
                                        Function1 function1 = (Function1)element$iv;
                                        ExprValue acc = accumulator$iv;
                                        boolean bl3 = false;
                                        ExprValue currValue = (ExprValue)curr.invoke(env2);
                                        if (currValue.getType().isUnknown()) {
                                            exprValue3 = this.$nullValue$inlined;
                                            break block10;
                                        }
                                        ExprValue exprValue4 = currValue;
                                        ExprValue lValue = acc;
                                        boolean bl4 = false;
                                        accumulator$iv = EvaluatingCompiler.access$exprValue(this.this$0, NumberExtensionsKt.plus(ExprValueExtensionsKt.numberValue(lValue), ExprValueExtensionsKt.numberValue((ExprValue)rValue)));
                                    }
                                    exprValue3 = accumulator$iv;
                                }
                            }
                            exprValue2 = exprValue3;
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl5 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl6 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
            }
        }
        return function1;
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNArySub(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        Function1 function1;
        switch (argThunks.size()) {
            case 1: {
                Function1<? super Environment, ? extends ExprValue> firstThunk = argThunks.get(0);
                ThunkFactory this_$iv = this.thunkFactory;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = metas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv22 = (SourceLocationMeta)meta;
                function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv22, this, firstThunk){
                    final /* synthetic */ ThunkFactory this$0$inline_fun;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ EvaluatingCompiler this$0;
                    final /* synthetic */ Function1 $firstThunk$inlined;
                    {
                        this.this$0$inline_fun = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.this$0 = evaluatingCompiler;
                        this.$firstThunk$inlined = function1;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0$inline_fun;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            boolean bl = false;
                            Environment env2 = env;
                            boolean bl2 = false;
                            ExprValue value = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                            exprValue2 = value.getType().isUnknown() ? EvaluatingCompiler.access$getValueFactory$p(this.this$0).getNullValue() : EvaluatingCompiler.access$exprValue(this.this$0, NumberExtensionsKt.unaryMinus(ExprValueExtensionsKt.numberValue(value)));
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl3 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl4 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
                break;
            }
            default: {
                void this_$iv;
                ThunkFactory firstThunk = this.thunkFactory;
                ExprValue nullValue$iv = this.valueFactory.getNullValue();
                boolean $i$f$thunkFold$lang = false;
                Collection sourceLocationMeta$iv22 = argThunks;
                boolean bl = false;
                boolean sourceLocationMeta$iv22 = !sourceLocationMeta$iv22.isEmpty();
                bl = false;
                boolean bl2 = false;
                if (!sourceLocationMeta$iv22) {
                    boolean bl3 = false;
                    String string = "argThunks must not be empty";
                    throw (Throwable)new IllegalArgumentException(string.toString());
                }
                Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
                List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
                void this_$iv$iv = this_$iv;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = metas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
                function1 = new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, nullValue$iv, otherThunks$iv, this){
                    final /* synthetic */ ThunkFactory this$0$inline_fun;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ Function1 $firstThunk$inlined;
                    final /* synthetic */ ExprValue $nullValue$inlined;
                    final /* synthetic */ List $otherThunks$inlined;
                    final /* synthetic */ EvaluatingCompiler this$0;
                    {
                        this.this$0$inline_fun = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.$firstThunk$inlined = function1;
                        this.$nullValue$inlined = exprValue2;
                        this.$otherThunks$inlined = list;
                        this.this$0 = evaluatingCompiler;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0$inline_fun;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            ExprValue exprValue3;
                            block10: {
                                boolean bl = false;
                                Environment env2 = env;
                                boolean bl2 = false;
                                ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                                if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                                    exprValue3 = this.$nullValue$inlined;
                                } else {
                                    Iterable $this$fold$iv = this.$otherThunks$inlined;
                                    boolean $i$f$fold = false;
                                    ExprValue accumulator$iv = firstValue;
                                    for (T element$iv : $this$fold$iv) {
                                        void rValue;
                                        void curr;
                                        Function1 function1 = (Function1)element$iv;
                                        ExprValue acc = accumulator$iv;
                                        boolean bl3 = false;
                                        ExprValue currValue = (ExprValue)curr.invoke(env2);
                                        if (currValue.getType().isUnknown()) {
                                            exprValue3 = this.$nullValue$inlined;
                                            break block10;
                                        }
                                        ExprValue exprValue4 = currValue;
                                        ExprValue lValue = acc;
                                        boolean bl4 = false;
                                        accumulator$iv = EvaluatingCompiler.access$exprValue(this.this$0, NumberExtensionsKt.minus(ExprValueExtensionsKt.numberValue(lValue), ExprValueExtensionsKt.numberValue((ExprValue)rValue)));
                                    }
                                    exprValue3 = accumulator$iv;
                                }
                            }
                            exprValue2 = exprValue3;
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl5 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl6 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
            }
        }
        return function1;
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNaryMul(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        void this_$iv;
        ThunkFactory thunkFactory = this.thunkFactory;
        ExprValue nullValue$iv = this.valueFactory.getNullValue();
        boolean $i$f$thunkFold$lang = false;
        Collection collection = argThunks;
        boolean bl = false;
        boolean bl2 = !collection.isEmpty();
        bl = false;
        boolean bl3 = false;
        if (!bl2) {
            boolean bl4 = false;
            String string = "argThunks must not be empty";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
        List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
        void this_$iv$iv = this_$iv;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, nullValue$iv, otherThunks$iv, this){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValue $nullValue$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            final /* synthetic */ EvaluatingCompiler this$0;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$nullValue$inlined = exprValue2;
                this.$otherThunks$inlined = list;
                this.this$0 = evaluatingCompiler;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block10: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$nullValue$inlined;
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void rValue;
                                void curr;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue acc = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currValue = (ExprValue)curr.invoke(env2);
                                if (currValue.getType().isUnknown()) {
                                    exprValue3 = this.$nullValue$inlined;
                                    break block10;
                                }
                                ExprValue exprValue4 = currValue;
                                ExprValue lValue = acc;
                                boolean bl4 = false;
                                accumulator$iv = EvaluatingCompiler.access$exprValue(this.this$0, NumberExtensionsKt.times(ExprValueExtensionsKt.numberValue(lValue), ExprValueExtensionsKt.numberValue((ExprValue)rValue)));
                            }
                            exprValue3 = accumulator$iv;
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl5 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl6 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNAryDiv(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        void this_$iv;
        ThunkFactory thunkFactory = this.thunkFactory;
        ExprValue nullValue$iv = this.valueFactory.getNullValue();
        boolean $i$f$thunkFold$lang = false;
        Collection collection = argThunks;
        boolean bl = false;
        boolean bl2 = !collection.isEmpty();
        bl = false;
        boolean bl3 = false;
        if (!bl2) {
            boolean bl4 = false;
            String string = "argThunks must not be empty";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
        List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
        void this_$iv$iv = this_$iv;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, nullValue$iv, otherThunks$iv, this){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValue $nullValue$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            final /* synthetic */ EvaluatingCompiler this$0;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$nullValue$inlined = exprValue2;
                this.$otherThunks$inlined = list;
                this.this$0 = evaluatingCompiler;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block13: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$nullValue$inlined;
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                ExprValue exprValue4;
                                void rValue;
                                void curr;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue acc = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currValue = (ExprValue)curr.invoke(env2);
                                if (currValue.getType().isUnknown()) {
                                    exprValue3 = this.$nullValue$inlined;
                                    break block13;
                                }
                                ExprValue exprValue5 = currValue;
                                ExprValue lValue = acc;
                                boolean bl4 = false;
                                Number denominator = ExprValueExtensionsKt.numberValue((ExprValue)rValue);
                                if (NumberExtensionsKt.isZero(denominator)) {
                                    Void void_ = ExceptionsKt.err("/ by zero", ErrorCode.EVALUATOR_DIVIDE_BY_ZERO, null, false);
                                    throw null;
                                }
                                try {
                                    exprValue4 = EvaluatingCompiler.access$exprValue(this.this$0, NumberExtensionsKt.div(ExprValueExtensionsKt.numberValue(lValue), denominator));
                                } catch (ArithmeticException e) {
                                    throw (Throwable)new EvaluationException(e, null, null, true, 6, null);
                                }
                                accumulator$iv = exprValue4;
                            }
                            exprValue3 = accumulator$iv;
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl5 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl6 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNAryMod(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        void this_$iv;
        ThunkFactory thunkFactory = this.thunkFactory;
        ExprValue nullValue$iv = this.valueFactory.getNullValue();
        boolean $i$f$thunkFold$lang = false;
        Collection collection = argThunks;
        boolean bl = false;
        boolean bl2 = !collection.isEmpty();
        bl = false;
        boolean bl3 = false;
        if (!bl2) {
            boolean bl4 = false;
            String string = "argThunks must not be empty";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
        List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
        void this_$iv$iv = this_$iv;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, nullValue$iv, otherThunks$iv, this){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValue $nullValue$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            final /* synthetic */ EvaluatingCompiler this$0;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$nullValue$inlined = exprValue2;
                this.$otherThunks$inlined = list;
                this.this$0 = evaluatingCompiler;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block11: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$nullValue$inlined;
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void rValue;
                                void curr;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue acc = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currValue = (ExprValue)curr.invoke(env2);
                                if (currValue.getType().isUnknown()) {
                                    exprValue3 = this.$nullValue$inlined;
                                    break block11;
                                }
                                ExprValue exprValue4 = currValue;
                                ExprValue lValue = acc;
                                boolean bl4 = false;
                                Number denominator = ExprValueExtensionsKt.numberValue((ExprValue)rValue);
                                if (NumberExtensionsKt.isZero(denominator)) {
                                    Void void_ = ExceptionsKt.err("% by zero", ErrorCode.EVALUATOR_MODULO_BY_ZERO, null, false);
                                    throw null;
                                }
                                accumulator$iv = EvaluatingCompiler.access$exprValue(this.this$0, NumberExtensionsKt.rem(ExprValueExtensionsKt.numberValue(lValue), denominator));
                            }
                            exprValue3 = accumulator$iv;
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl5 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl6 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNAryEq(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        void this_$iv;
        ThunkFactory thunkFactory = this.thunkFactory;
        ExprValueFactory valueFactory$iv = this.valueFactory;
        boolean $i$f$thunkAndMap$lang = false;
        boolean bl = argThunks.size() >= 2;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            boolean bl4 = false;
            String string = "argThunks must have at least two elements";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
        List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
        void this_$iv$iv = this_$iv;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, valueFactory$iv, otherThunks$iv){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValueFactory $valueFactory$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$valueFactory$inlined = exprValueFactory;
                this.$otherThunks$inlined = list;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block11: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$valueFactory$inlined.getNullValue();
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void rValue;
                                void currentThunk;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue lastValue = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currentValue = (ExprValue)currentThunk.invoke(env2);
                                if (ExprValueExtensionsKt.isUnknown(currentValue)) {
                                    exprValue3 = this.$valueFactory$inlined.getNullValue();
                                    break block11;
                                }
                                ExprValue exprValue4 = currentValue;
                                ExprValue lValue = lastValue;
                                boolean bl4 = false;
                                boolean result = ExprValueExtensionsKt.exprEquals(lValue, (ExprValue)rValue);
                                if (!result) {
                                    exprValue3 = this.$valueFactory$inlined.newBoolean(false);
                                    break block11;
                                }
                                accumulator$iv = currentValue;
                            }
                            exprValue3 = this.$valueFactory$inlined.newBoolean(true);
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl5 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl6 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNAryNe(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        void this_$iv;
        ThunkFactory thunkFactory = this.thunkFactory;
        ExprValue nullValue$iv = this.valueFactory.getNullValue();
        boolean $i$f$thunkFold$lang = false;
        Collection collection = argThunks;
        boolean bl = false;
        boolean bl2 = !collection.isEmpty();
        bl = false;
        boolean bl3 = false;
        if (!bl2) {
            boolean bl4 = false;
            String string = "argThunks must not be empty";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
        List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
        void this_$iv$iv = this_$iv;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, nullValue$iv, otherThunks$iv, this){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValue $nullValue$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            final /* synthetic */ EvaluatingCompiler this$0;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$nullValue$inlined = exprValue2;
                this.$otherThunks$inlined = list;
                this.this$0 = evaluatingCompiler;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block10: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$nullValue$inlined;
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void rValue;
                                void curr;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue acc = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currValue = (ExprValue)curr.invoke(env2);
                                if (currValue.getType().isUnknown()) {
                                    exprValue3 = this.$nullValue$inlined;
                                    break block10;
                                }
                                ExprValue exprValue4 = currValue;
                                ExprValue lValue = acc;
                                boolean bl4 = false;
                                accumulator$iv = EvaluatingCompiler.access$exprValue(this.this$0, !ExprValueExtensionsKt.exprEquals(lValue, (ExprValue)rValue));
                            }
                            exprValue3 = accumulator$iv;
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl5 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl6 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNaryLt(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        void this_$iv;
        ThunkFactory thunkFactory = this.thunkFactory;
        ExprValueFactory valueFactory$iv = this.valueFactory;
        boolean $i$f$thunkAndMap$lang = false;
        boolean bl = argThunks.size() >= 2;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            boolean bl4 = false;
            String string = "argThunks must have at least two elements";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
        List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
        void this_$iv$iv = this_$iv;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, valueFactory$iv, otherThunks$iv){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValueFactory $valueFactory$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$valueFactory$inlined = exprValueFactory;
                this.$otherThunks$inlined = list;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block11: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$valueFactory$inlined.getNullValue();
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void rValue;
                                boolean result;
                                void currentThunk;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue lastValue = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currentValue = (ExprValue)currentThunk.invoke(env2);
                                if (ExprValueExtensionsKt.isUnknown(currentValue)) {
                                    exprValue3 = this.$valueFactory$inlined.getNullValue();
                                    break block11;
                                }
                                ExprValue exprValue4 = currentValue;
                                ExprValue lValue = lastValue;
                                boolean bl4 = false;
                                boolean bl5 = result = ExprValueExtensionsKt.compareTo(lValue, (ExprValue)rValue) < 0;
                                if (!result) {
                                    exprValue3 = this.$valueFactory$inlined.newBoolean(false);
                                    break block11;
                                }
                                accumulator$iv = currentValue;
                            }
                            exprValue3 = this.$valueFactory$inlined.newBoolean(true);
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl6 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl7 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNAryLte(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        void this_$iv;
        ThunkFactory thunkFactory = this.thunkFactory;
        ExprValueFactory valueFactory$iv = this.valueFactory;
        boolean $i$f$thunkAndMap$lang = false;
        boolean bl = argThunks.size() >= 2;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            boolean bl4 = false;
            String string = "argThunks must have at least two elements";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
        List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
        void this_$iv$iv = this_$iv;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, valueFactory$iv, otherThunks$iv){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValueFactory $valueFactory$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$valueFactory$inlined = exprValueFactory;
                this.$otherThunks$inlined = list;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block11: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$valueFactory$inlined.getNullValue();
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void rValue;
                                boolean result;
                                void currentThunk;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue lastValue = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currentValue = (ExprValue)currentThunk.invoke(env2);
                                if (ExprValueExtensionsKt.isUnknown(currentValue)) {
                                    exprValue3 = this.$valueFactory$inlined.getNullValue();
                                    break block11;
                                }
                                ExprValue exprValue4 = currentValue;
                                ExprValue lValue = lastValue;
                                boolean bl4 = false;
                                boolean bl5 = result = ExprValueExtensionsKt.compareTo(lValue, (ExprValue)rValue) <= 0;
                                if (!result) {
                                    exprValue3 = this.$valueFactory$inlined.newBoolean(false);
                                    break block11;
                                }
                                accumulator$iv = currentValue;
                            }
                            exprValue3 = this.$valueFactory$inlined.newBoolean(true);
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl6 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl7 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNAryGt(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        void this_$iv;
        ThunkFactory thunkFactory = this.thunkFactory;
        ExprValueFactory valueFactory$iv = this.valueFactory;
        boolean $i$f$thunkAndMap$lang = false;
        boolean bl = argThunks.size() >= 2;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            boolean bl4 = false;
            String string = "argThunks must have at least two elements";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
        List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
        void this_$iv$iv = this_$iv;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, valueFactory$iv, otherThunks$iv){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValueFactory $valueFactory$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$valueFactory$inlined = exprValueFactory;
                this.$otherThunks$inlined = list;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block11: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$valueFactory$inlined.getNullValue();
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void rValue;
                                boolean result;
                                void currentThunk;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue lastValue = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currentValue = (ExprValue)currentThunk.invoke(env2);
                                if (ExprValueExtensionsKt.isUnknown(currentValue)) {
                                    exprValue3 = this.$valueFactory$inlined.getNullValue();
                                    break block11;
                                }
                                ExprValue exprValue4 = currentValue;
                                ExprValue lValue = lastValue;
                                boolean bl4 = false;
                                boolean bl5 = result = ExprValueExtensionsKt.compareTo(lValue, (ExprValue)rValue) > 0;
                                if (!result) {
                                    exprValue3 = this.$valueFactory$inlined.newBoolean(false);
                                    break block11;
                                }
                                accumulator$iv = currentValue;
                            }
                            exprValue3 = this.$valueFactory$inlined.newBoolean(true);
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl6 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl7 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNAryGte(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        void this_$iv;
        ThunkFactory thunkFactory = this.thunkFactory;
        ExprValueFactory valueFactory$iv = this.valueFactory;
        boolean $i$f$thunkAndMap$lang = false;
        boolean bl = argThunks.size() >= 2;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            boolean bl4 = false;
            String string = "argThunks must have at least two elements";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
        List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
        void this_$iv$iv = this_$iv;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, valueFactory$iv, otherThunks$iv){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValueFactory $valueFactory$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$valueFactory$inlined = exprValueFactory;
                this.$otherThunks$inlined = list;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block11: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$valueFactory$inlined.getNullValue();
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void rValue;
                                boolean result;
                                void currentThunk;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue lastValue = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currentValue = (ExprValue)currentThunk.invoke(env2);
                                if (ExprValueExtensionsKt.isUnknown(currentValue)) {
                                    exprValue3 = this.$valueFactory$inlined.getNullValue();
                                    break block11;
                                }
                                ExprValue exprValue4 = currentValue;
                                ExprValue lValue = lastValue;
                                boolean bl4 = false;
                                boolean bl5 = result = ExprValueExtensionsKt.compareTo(lValue, (ExprValue)rValue) >= 0;
                                if (!result) {
                                    exprValue3 = this.$valueFactory$inlined.newBoolean(false);
                                    break block11;
                                }
                                accumulator$iv = currentValue;
                            }
                            exprValue3 = this.$valueFactory$inlined.newBoolean(true);
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl6 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl7 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    private final Function1<Environment, ExprValue> compileNAryBetween(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        Function1<? super Environment, ? extends ExprValue> valueThunk = argThunks.get(0);
        Function1<? super Environment, ? extends ExprValue> fromThunk = argThunks.get(1);
        Function1<? super Environment, ? extends ExprValue> toThunk = argThunks.get(2);
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, valueThunk, fromThunk, toThunk){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ Function1 $valueThunk$inlined;
            final /* synthetic */ Function1 $fromThunk$inlined;
            final /* synthetic */ Function1 $toThunk$inlined;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.this$0 = evaluatingCompiler;
                this.$valueThunk$inlined = function1;
                this.$fromThunk$inlined = function12;
                this.$toThunk$inlined = function13;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    boolean bl = false;
                    Environment env2 = env;
                    boolean bl2 = false;
                    ExprValue value = (ExprValue)this.$valueThunk$inlined.invoke(env2);
                    exprValue2 = EvaluatingCompiler.access$exprValue(this.this$0, ExprValueExtensionsKt.compareTo(value, (ExprValue)this.$fromThunk$inlined.invoke(env2)) >= 0 && ExprValueExtensionsKt.compareTo(value, (ExprValue)this.$toThunk$inlined.invoke(env2)) <= 0);
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl3 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl4 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final Function1<Environment, ExprValue> compileNAryIn(List<? extends ExprNode> args, MetaContainer metas) {
        block10: {
            leftArg = this.compileExprNode(args.get(0));
            rightArg = args.get(1);
            if (!(rightArg instanceof Seq) || ((Seq)rightArg).getType() != SeqType.LIST) ** GOTO lbl-1000
            $this$all$iv = ((Seq)rightArg).getValues();
            $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                v0 = true;
            } else {
                for (T element$iv : $this$all$iv) {
                    it = (ExprNode)element$iv /* !! */ ;
                    $i$a$-all-EvaluatingCompiler$compileNAryIn$1 = false;
                    if (it instanceof Literal) continue;
                    v0 = false;
                    break block10;
                }
                v0 = true;
            }
        }
        if (v0) {
            $this$map$iv = ((Seq)rightArg).getValues();
            $i$f$map = false;
            element$iv /* !! */  = $this$map$iv;
            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            $i$f$mapTo = false;
            for (T item$iv$iv : $this$mapTo$iv$iv) {
                var13_23 = (ExprNode)item$iv$iv;
                var15_25 = destination$iv$iv;
                $i$a$-map-EvaluatingCompiler$compileNAryIn$inSet$1 = false;
                v1 = it;
                if (v1 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.ast.Literal");
                }
                var16_26 /* !! */  = (Literal)v1;
                var15_25.add(var16_26 /* !! */ );
            }
            $this$map$iv = (List)destination$iv$iv;
            destination$iv = new TreeSet<E>(ExprValueExtensionsKt.getDEFAULT_COMPARATOR());
            $i$f$mapTo = false;
            for (T item$iv : $this$mapTo$iv) {
                var11_20 = (Literal)item$iv;
                var15_25 = destination$iv;
                $i$a$-mapTo-EvaluatingCompiler$compileNAryIn$inSet$2 = false;
                var16_26 /* !! */  = this.valueFactory.newFromIonValue(it.getIonValue());
                var15_25.add(var16_26 /* !! */ );
            }
            inSet = (TreeSet)destination$iv;
            this_$iv = this.thunkFactory;
            $i$f$thunkEnv$lang = false;
            v2 = metas.find("$source_location");
            if (!(v2 instanceof SourceLocationMeta)) {
                v2 = null;
            }
            sourceLocationMeta$iv = (SourceLocationMeta)v2;
            v3 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, leftArg, inSet){
                final /* synthetic */ ThunkFactory this$0$inline_fun;
                final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                final /* synthetic */ EvaluatingCompiler this$0;
                final /* synthetic */ Function1 $leftArg$inlined;
                final /* synthetic */ TreeSet $inSet$inlined;
                {
                    this.this$0$inline_fun = thunkFactory;
                    this.$sourceLocationMeta = sourceLocationMeta;
                    this.this$0 = evaluatingCompiler;
                    this.$leftArg$inlined = function1;
                    this.$inSet$inlined = treeSet;
                    super(1);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final ExprValue invoke(@NotNull Environment env) {
                    ExprValue exprValue2;
                    Intrinsics.checkParameterIsNotNull(env, "env");
                    ThunkFactory thunkFactory = this.this$0$inline_fun;
                    SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                    boolean $i$f$handleException$lang = false;
                    try {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue value = (ExprValue)this.$leftArg$inlined.invoke(env2);
                        exprValue2 = EvaluatingCompiler.access$exprValue(this.this$0, this.$inSet$inlined.contains(value));
                    } catch (EvaluationException e$iv) {
                        if (e$iv.getErrorContext() == null) {
                            throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                        }
                        if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                            SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                            if (sourceLocationMeta != null) {
                                SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                boolean bl = false;
                                boolean bl3 = false;
                                SourceLocationMeta it$iv = sourceLocationMeta2;
                                boolean bl4 = false;
                                ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                            }
                        }
                        throw (Throwable)e$iv;
                    } catch (Exception e$iv) {
                        void this_$iv;
                        R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                        throw null;
                    }
                    return exprValue2;
                }
            };
        } else lbl-1000:
        // 2 sources

        {
            rightArgThunk = this.compileExprNode(rightArg);
            this_$iv = this.thunkFactory;
            $i$f$thunkEnv$lang = false;
            v4 = metas.find("$source_location");
            if (!(v4 instanceof SourceLocationMeta)) {
                v4 = null;
            }
            sourceLocationMeta$iv = (SourceLocationMeta)v4;
            v3 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, leftArg, rightArgThunk){
                final /* synthetic */ ThunkFactory this$0$inline_fun;
                final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                final /* synthetic */ EvaluatingCompiler this$0;
                final /* synthetic */ Function1 $leftArg$inlined;
                final /* synthetic */ Function1 $rightArgThunk$inlined;
                {
                    this.this$0$inline_fun = thunkFactory;
                    this.$sourceLocationMeta = sourceLocationMeta;
                    this.this$0 = evaluatingCompiler;
                    this.$leftArg$inlined = function1;
                    this.$rightArgThunk$inlined = function12;
                    super(1);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final ExprValue invoke(@NotNull Environment env) {
                    ExprValue exprValue2;
                    Intrinsics.checkParameterIsNotNull(env, "env");
                    ThunkFactory thunkFactory = this.this$0$inline_fun;
                    SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                    boolean $i$f$handleException$lang = false;
                    try {
                        boolean bl;
                        EvaluatingCompiler evaluatingCompiler;
                        block9: {
                            void $this$any$iv;
                            boolean bl2 = false;
                            Environment env2 = env;
                            boolean bl3 = false;
                            ExprValue value = (ExprValue)this.$leftArg$inlined.invoke(env2);
                            ExprValue rigthArgExprValue = (ExprValue)this.$rightArgThunk$inlined.invoke(env2);
                            Iterable iterable = rigthArgExprValue;
                            evaluatingCompiler = this.this$0;
                            boolean $i$f$any = false;
                            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                bl = false;
                            } else {
                                for (T element$iv : $this$any$iv) {
                                    ExprValue it = (ExprValue)element$iv;
                                    boolean bl4 = false;
                                    if (!ExprValueExtensionsKt.exprEquals(it, value)) continue;
                                    bl = true;
                                    break block9;
                                }
                                bl = false;
                            }
                        }
                        boolean bl5 = bl;
                        exprValue2 = EvaluatingCompiler.access$exprValue(evaluatingCompiler, bl5);
                    } catch (EvaluationException e$iv) {
                        if (e$iv.getErrorContext() == null) {
                            throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                        }
                        if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                            SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                            if (sourceLocationMeta != null) {
                                SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                boolean bl = false;
                                boolean bl6 = false;
                                SourceLocationMeta it$iv = sourceLocationMeta2;
                                boolean bl7 = false;
                                ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                            }
                        }
                        throw (Throwable)e$iv;
                    } catch (Exception e$iv) {
                        void this_$iv;
                        R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                        throw null;
                    }
                    return exprValue2;
                }
            };
        }
        return v3;
    }

    private final Function1<Environment, ExprValue> compileNAryNot(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        Function1<? super Environment, ? extends ExprValue> arg = argThunks.get(0);
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, arg){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ Function1 $arg$inlined;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.this$0 = evaluatingCompiler;
                this.$arg$inlined = function1;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    boolean bl = false;
                    Environment env2 = env;
                    boolean bl2 = false;
                    ExprValue value = (ExprValue)this.$arg$inlined.invoke(env2);
                    exprValue2 = value.getType().isUnknown() ? EvaluatingCompiler.access$getValueFactory$p(this.this$0).getNullValue() : EvaluatingCompiler.access$exprValue(this.this$0, !ExprValueExtensionsKt.booleanValue(value));
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl3 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl4 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    private final Function1<Environment, ExprValue> compileNAryAnd(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, argThunks){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ List $argThunks$inlined;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.this$0 = evaluatingCompiler;
                this.$argThunks$inlined = list;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block12: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        boolean hasUnknowns = false;
                        Iterable $this$forEach$iv = this.$argThunks$inlined;
                        boolean $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            Function1 currThunk = (Function1)element$iv;
                            boolean bl3 = false;
                            ExprValue currValue = (ExprValue)currThunk.invoke(env2);
                            if (ExprValueExtensionsKt.isUnknown(currValue)) {
                                hasUnknowns = true;
                                continue;
                            }
                            if (ExprValueExtensionsKt.booleanValue(currValue)) continue;
                            exprValue3 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBoolean(false);
                            break block12;
                        }
                        boolean bl4 = hasUnknowns;
                        if (bl4) {
                            exprValue3 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).getNullValue();
                        } else if (!bl4) {
                            exprValue3 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBoolean(true);
                        } else {
                            throw new NoWhenBranchMatchedException();
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl5 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl6 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    private final Function1<Environment, ExprValue> compileNAryOr(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, argThunks){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ List $argThunks$inlined;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.this$0 = evaluatingCompiler;
                this.$argThunks$inlined = list;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block12: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        boolean hasUnknowns = false;
                        Iterable $this$forEach$iv = this.$argThunks$inlined;
                        boolean $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            Function1 currThunk = (Function1)element$iv;
                            boolean bl3 = false;
                            ExprValue currValue = (ExprValue)currThunk.invoke(env2);
                            if (ExprValueExtensionsKt.isUnknown(currValue)) {
                                hasUnknowns = true;
                                continue;
                            }
                            if (!ExprValueExtensionsKt.booleanValue(currValue)) continue;
                            exprValue3 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBoolean(true);
                            break block12;
                        }
                        boolean bl4 = hasUnknowns;
                        if (bl4) {
                            exprValue3 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).getNullValue();
                        } else if (!bl4) {
                            exprValue3 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBoolean(false);
                        } else {
                            throw new NoWhenBranchMatchedException();
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl5 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl6 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileNAryStringConcat(List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        void this_$iv;
        ThunkFactory thunkFactory = this.thunkFactory;
        ExprValue nullValue$iv = this.valueFactory.getNullValue();
        boolean $i$f$thunkFold$lang = false;
        Collection collection = argThunks;
        boolean bl = false;
        boolean bl2 = !collection.isEmpty();
        bl = false;
        boolean bl3 = false;
        if (!bl2) {
            boolean bl4 = false;
            String string = "argThunks must not be empty";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk$iv = CollectionsKt.first(argThunks);
        List otherThunks$iv = CollectionsKt.drop((Iterable)argThunks, 1);
        void this_$iv$iv = this_$iv;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv$iv, sourceLocationMeta$iv$iv, firstThunk$iv, nullValue$iv, otherThunks$iv, this, metas){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValue $nullValue$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ MetaContainer $metas$inlined;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$nullValue$inlined = exprValue2;
                this.$otherThunks$inlined = list;
                this.this$0 = evaluatingCompiler;
                this.$metas$inlined = metaContainer;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block11: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$nullValue$inlined;
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void rValue;
                                void curr;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue acc = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currValue = (ExprValue)curr.invoke(env2);
                                if (currValue.getType().isUnknown()) {
                                    exprValue3 = this.$nullValue$inlined;
                                    break block11;
                                }
                                ExprValue exprValue4 = currValue;
                                ExprValue lValue = acc;
                                boolean bl4 = false;
                                ExprValueType lType = lValue.getType();
                                ExprValueType rType = rValue.getType();
                                if (!lType.isText() || !rType.isText()) {
                                    PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(this.$metas$inlined);
                                    ErrorCode errorCode = ErrorCode.EVALUATOR_CONCAT_FAILED_DUE_TO_INCOMPATIBLE_TYPE;
                                    String string = "Wrong argument type for ||";
                                    boolean bl5 = false;
                                    boolean bl6 = false;
                                    PropertyValueMap it = propertyValueMap;
                                    boolean bl7 = false;
                                    it.set(Property.ACTUAL_ARGUMENT_TYPES, CollectionsKt.listOf(lType, rType).toString());
                                    PropertyValueMap propertyValueMap2 = propertyValueMap;
                                    Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap2, false);
                                    throw null;
                                }
                                accumulator$iv = EvaluatingCompiler.access$exprValue(this.this$0, ExprValueExtensionsKt.stringValue(lValue) + ExprValueExtensionsKt.stringValue((ExprValue)rValue));
                            }
                            exprValue3 = accumulator$iv;
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl8 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl9 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    private final Function1<Environment, ExprValue> compileNAryCall(List<? extends ExprNode> args2, List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer metas) {
        ExprNode exprNode = CollectionsKt.first(args2);
        if (!(exprNode instanceof VariableReference)) {
            exprNode = null;
        }
        VariableReference variableReference = (VariableReference)exprNode;
        if (variableReference == null) {
            Void void_ = ExceptionsKt.err("First argument of call must be a VariableReference", ExceptionsKt.errorContextFrom(metas), true);
            throw null;
        }
        VariableReference funcExpr = variableReference;
        List funcArgThunks = CollectionsKt.drop((Iterable)argThunks, 1);
        ExprFunction exprFunction = this.functions.get(funcExpr.getId());
        if (exprFunction == null) {
            PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(metas);
            ErrorCode errorCode = ErrorCode.EVALUATOR_NO_SUCH_FUNCTION;
            String string = "No such function: " + funcExpr.getId();
            boolean bl = false;
            boolean bl2 = false;
            PropertyValueMap it = propertyValueMap;
            boolean bl3 = false;
            it.set(Property.FUNCTION_NAME, funcExpr.getId());
            PropertyValueMap propertyValueMap2 = propertyValueMap;
            Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap2, false);
            throw null;
        }
        ExprFunction func = exprFunction;
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, funcArgThunks, func){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ List $funcArgThunks$inlined;
            final /* synthetic */ ExprFunction $func$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$funcArgThunks$inlined = list;
                this.$func$inlined = exprFunction;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    void $this$mapTo$iv$iv;
                    boolean bl = false;
                    Environment env2 = env;
                    boolean bl2 = false;
                    Iterable $this$map$iv = this.$funcArgThunks$inlined;
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it;
                        Function1 function1 = (Function1)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl3 = false;
                        ExprValue exprValue3 = (ExprValue)it.invoke(env2);
                        collection.add(exprValue3);
                    }
                    List funcArgValues = (List)destination$iv$iv;
                    exprValue2 = this.$func$inlined.call(env2, funcArgValues);
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl4 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl5 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileLiteral(Literal expr) {
        void ionValue2;
        Literal literal = expr;
        IonValue ionValue3 = literal.component1();
        MetaContainer metas = literal.component2();
        ExprValue value = this.valueFactory.newFromIonValue((IonValue)ionValue2);
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, value){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ ExprValue $value$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$value$inlined = exprValue2;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    boolean bl = false;
                    Environment it = env;
                    boolean bl2 = false;
                    exprValue2 = this.$value$inlined;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl3 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl4 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    private final Function1<Environment, ExprValue> compileLiteralMissing(LiteralMissing expr) {
        LiteralMissing literalMissing = expr;
        MetaContainer metas = literalMissing.component1();
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ EvaluatingCompiler this$0;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.this$0 = evaluatingCompiler;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    boolean bl = false;
                    Environment $noName_0 = env;
                    boolean bl2 = false;
                    exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).getMissingValue();
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl3 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl4 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileVariableReference(VariableReference expr) {
        Function1 function1;
        block14: {
            UniqueNameMeta uniqueNameMeta;
            MetaContainer metas;
            block13: {
                void lookupStrategy;
                Function1 function12;
                SourceLocationMeta sourceLocationMeta$iv;
                boolean $i$f$thunkEnv$lang;
                ThunkFactory this_$iv;
                void case_;
                void id;
                VariableReference variableReference = expr;
                String string = variableReference.component1();
                CaseSensitivity caseSensitivity = variableReference.component2();
                ScopeQualifier scopeQualifier = variableReference.component3();
                metas = variableReference.component4();
                Meta meta = expr.getMetas().find("$unique_name");
                if (!(meta instanceof UniqueNameMeta)) {
                    meta = null;
                }
                uniqueNameMeta = (UniqueNameMeta)meta;
                Set<String> fromSourceNames = this.getCurrentCompilationContext().getFromSourceNames();
                UniqueNameMeta uniqueNameMeta2 = uniqueNameMeta;
                if (uniqueNameMeta2 != null) break block13;
                BindingName bindingName = new BindingName((String)id, BindingsKt.toBindingCase((CaseSensitivity)case_));
                switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$1[this.compileOptions.getUndefinedVariable().ordinal()]) {
                    case 1: {
                        this_$iv = this.thunkFactory;
                        $i$f$thunkEnv$lang = false;
                        Meta meta2 = metas.find("$source_location");
                        if (!(meta2 instanceof SourceLocationMeta)) {
                            meta2 = null;
                        }
                        sourceLocationMeta$iv = (SourceLocationMeta)meta2;
                        function12 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, bindingName, fromSourceNames, metas){
                            final /* synthetic */ ThunkFactory this$0;
                            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                            final /* synthetic */ BindingName $bindingName$inlined;
                            final /* synthetic */ Set $fromSourceNames$inlined;
                            final /* synthetic */ MetaContainer $metas$inlined;
                            {
                                this.this$0 = thunkFactory;
                                this.$sourceLocationMeta = sourceLocationMeta;
                                this.$bindingName$inlined = bindingName;
                                this.$fromSourceNames$inlined = set2;
                                this.$metas$inlined = metaContainer;
                                super(1);
                            }

                            /*
                             * WARNING - void declaration
                             */
                            @NotNull
                            public final ExprValue invoke(@NotNull Environment env) {
                                ExprValue exprValue2;
                                Intrinsics.checkParameterIsNotNull(env, "env");
                                ThunkFactory thunkFactory = this.this$0;
                                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                boolean $i$f$handleException$lang = false;
                                try {
                                    boolean bl = false;
                                    Environment env2 = env;
                                    boolean bl2 = false;
                                    ExprValue value = env2.getCurrent().get(this.$bindingName$inlined);
                                    if (value == null) {
                                        PropertyValueMap it;
                                        boolean bl3;
                                        PropertyValueMap propertyValueMap;
                                        boolean bl4;
                                        block11: {
                                            Iterable $this$any$iv = this.$fromSourceNames$inlined;
                                            boolean $i$f$any = false;
                                            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                                bl4 = false;
                                            } else {
                                                for (T element$iv : $this$any$iv) {
                                                    String it2 = (String)element$iv;
                                                    boolean bl5 = false;
                                                    if (!this.$bindingName$inlined.isEquivalentTo(it2)) continue;
                                                    bl4 = true;
                                                    break block11;
                                                }
                                                bl4 = false;
                                            }
                                        }
                                        if (bl4) {
                                            propertyValueMap = ExceptionsKt.errorContextFrom(this.$metas$inlined);
                                            ErrorCode errorCode = ErrorCode.EVALUATOR_VARIABLE_NOT_INCLUDED_IN_GROUP_BY;
                                            String string = "Variable not in GROUP BY or aggregation function: " + this.$bindingName$inlined.getName();
                                            bl3 = false;
                                            boolean bl6 = false;
                                            it = propertyValueMap;
                                            boolean bl7 = false;
                                            it.set(Property.BINDING_NAME, this.$bindingName$inlined.getName());
                                            PropertyValueMap propertyValueMap2 = propertyValueMap;
                                            DefaultConstructorMarker defaultConstructorMarker = null;
                                            int n = 8;
                                            boolean bl8 = false;
                                            Throwable throwable = null;
                                            PropertyValueMap propertyValueMap3 = propertyValueMap2;
                                            ErrorCode errorCode2 = errorCode;
                                            String string2 = string;
                                            throw (Throwable)new EvaluationException(string2, errorCode2, propertyValueMap3, throwable, bl8, n, defaultConstructorMarker);
                                        }
                                        propertyValueMap = ExceptionsKt.errorContextFrom(this.$metas$inlined);
                                        ErrorCode errorCode = ErrorCode.EVALUATOR_BINDING_DOES_NOT_EXIST;
                                        String string = "No such binding: " + this.$bindingName$inlined.getName();
                                        bl3 = false;
                                        boolean bl9 = false;
                                        it = propertyValueMap;
                                        boolean bl10 = false;
                                        it.set(Property.BINDING_NAME, this.$bindingName$inlined.getName());
                                        PropertyValueMap propertyValueMap4 = propertyValueMap;
                                        DefaultConstructorMarker defaultConstructorMarker = null;
                                        int n = 8;
                                        boolean bl11 = false;
                                        Throwable throwable = null;
                                        PropertyValueMap propertyValueMap5 = propertyValueMap4;
                                        ErrorCode errorCode3 = errorCode;
                                        String string3 = string;
                                        throw (Throwable)new EvaluationException(string3, errorCode3, propertyValueMap5, throwable, bl11, n, defaultConstructorMarker);
                                    }
                                    exprValue2 = value;
                                } catch (EvaluationException e$iv) {
                                    if (e$iv.getErrorContext() == null) {
                                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                    }
                                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                        if (sourceLocationMeta != null) {
                                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                            boolean bl = false;
                                            boolean bl12 = false;
                                            SourceLocationMeta it$iv = sourceLocationMeta2;
                                            boolean bl13 = false;
                                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                        }
                                    }
                                    throw (Throwable)e$iv;
                                } catch (Exception e$iv) {
                                    void this_$iv;
                                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                    throw null;
                                }
                                return exprValue2;
                            }
                        };
                        break;
                    }
                    case 2: {
                        this_$iv = this.thunkFactory;
                        $i$f$thunkEnv$lang = false;
                        Meta meta3 = metas.find("$source_location");
                        if (!(meta3 instanceof SourceLocationMeta)) {
                            meta3 = null;
                        }
                        sourceLocationMeta$iv = (SourceLocationMeta)meta3;
                        function12 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, bindingName){
                            final /* synthetic */ ThunkFactory this$0$inline_fun;
                            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                            final /* synthetic */ EvaluatingCompiler this$0;
                            final /* synthetic */ BindingName $bindingName$inlined;
                            {
                                this.this$0$inline_fun = thunkFactory;
                                this.$sourceLocationMeta = sourceLocationMeta;
                                this.this$0 = evaluatingCompiler;
                                this.$bindingName$inlined = bindingName;
                                super(1);
                            }

                            /*
                             * WARNING - void declaration
                             */
                            @NotNull
                            public final ExprValue invoke(@NotNull Environment env) {
                                ExprValue exprValue2;
                                Intrinsics.checkParameterIsNotNull(env, "env");
                                ThunkFactory thunkFactory = this.this$0$inline_fun;
                                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                boolean $i$f$handleException$lang = false;
                                try {
                                    boolean bl = false;
                                    Environment env2 = env;
                                    boolean bl2 = false;
                                    ExprValue exprValue3 = env2.getCurrent().get(this.$bindingName$inlined);
                                    if (exprValue3 == null) {
                                        exprValue3 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).getMissingValue();
                                    }
                                    exprValue2 = exprValue3;
                                } catch (EvaluationException e$iv) {
                                    if (e$iv.getErrorContext() == null) {
                                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                    }
                                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                        if (sourceLocationMeta != null) {
                                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                            boolean bl = false;
                                            boolean bl3 = false;
                                            SourceLocationMeta it$iv = sourceLocationMeta2;
                                            boolean bl4 = false;
                                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                        }
                                    }
                                    throw (Throwable)e$iv;
                                } catch (Exception e$iv) {
                                    void this_$iv;
                                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                    throw null;
                                }
                                return exprValue2;
                            }
                        };
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                Function1 evalVariableReference = function12;
                switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$2[lookupStrategy.ordinal()]) {
                    case 1: {
                        function1 = evalVariableReference;
                        break block14;
                    }
                    case 2: {
                        this_$iv = this.thunkFactory;
                        $i$f$thunkEnv$lang = false;
                        Meta meta4 = metas.find("$source_location");
                        if (!(meta4 instanceof SourceLocationMeta)) {
                            meta4 = null;
                        }
                        sourceLocationMeta$iv = (SourceLocationMeta)meta4;
                        function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, evalVariableReference){
                            final /* synthetic */ ThunkFactory this$0;
                            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                            final /* synthetic */ Function1 $evalVariableReference$inlined;
                            {
                                this.this$0 = thunkFactory;
                                this.$sourceLocationMeta = sourceLocationMeta;
                                this.$evalVariableReference$inlined = function1;
                                super(1);
                            }

                            /*
                             * WARNING - void declaration
                             */
                            @NotNull
                            public final ExprValue invoke(@NotNull Environment env) {
                                ExprValue exprValue2;
                                Intrinsics.checkParameterIsNotNull(env, "env");
                                ThunkFactory thunkFactory = this.this$0;
                                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                boolean $i$f$handleException$lang = false;
                                try {
                                    boolean bl = false;
                                    Environment env2 = env;
                                    boolean bl2 = false;
                                    exprValue2 = (ExprValue)this.$evalVariableReference$inlined.invoke(env2.flipToLocals$lang());
                                } catch (EvaluationException e$iv) {
                                    if (e$iv.getErrorContext() == null) {
                                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                    }
                                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                        if (sourceLocationMeta != null) {
                                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                            boolean bl = false;
                                            boolean bl3 = false;
                                            SourceLocationMeta it$iv = sourceLocationMeta2;
                                            boolean bl4 = false;
                                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                        }
                                    }
                                    throw (Throwable)e$iv;
                                } catch (Exception e$iv) {
                                    void this_$iv;
                                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                    throw null;
                                }
                                return exprValue2;
                            }
                        };
                        break block14;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
            }
            BindingName bindingName = new BindingName(uniqueNameMeta.getUniqueName(), BindingCase.SENSITIVE);
            ThunkFactory this_$iv = this.thunkFactory;
            boolean $i$f$thunkEnv$lang = false;
            Meta meta = metas.find("$source_location");
            if (!(meta instanceof SourceLocationMeta)) {
                meta = null;
            }
            SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
            function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, bindingName, metas){
                final /* synthetic */ ThunkFactory this$0;
                final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                final /* synthetic */ BindingName $bindingName$inlined;
                final /* synthetic */ MetaContainer $metas$inlined;
                {
                    this.this$0 = thunkFactory;
                    this.$sourceLocationMeta = sourceLocationMeta;
                    this.$bindingName$inlined = bindingName;
                    this.$metas$inlined = metaContainer;
                    super(1);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final ExprValue invoke(@NotNull Environment env) {
                    ExprValue exprValue2;
                    Intrinsics.checkParameterIsNotNull(env, "env");
                    ThunkFactory thunkFactory = this.this$0;
                    SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                    boolean $i$f$handleException$lang = false;
                    try {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue exprValue3 = env2.getCurrent().get(this.$bindingName$inlined);
                        if (exprValue3 == null) {
                            Void void_ = ExceptionsKt.err("Uniquely named binding \"" + this.$bindingName$inlined.getName() + "\" does not exist for some reason", ExceptionsKt.errorContextFrom(this.$metas$inlined), true);
                            throw null;
                        }
                        exprValue2 = exprValue3;
                    } catch (EvaluationException e$iv) {
                        if (e$iv.getErrorContext() == null) {
                            throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                        }
                        if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                            SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                            if (sourceLocationMeta != null) {
                                SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                boolean bl = false;
                                boolean bl3 = false;
                                SourceLocationMeta it$iv = sourceLocationMeta2;
                                boolean bl4 = false;
                                ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                            }
                        }
                        throw (Throwable)e$iv;
                    } catch (Exception e$iv) {
                        void this_$iv;
                        R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                        throw null;
                    }
                    return exprValue2;
                }
            };
        }
        return function1;
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileParameter(Parameter expr) {
        void ordinal;
        Parameter parameter = expr;
        int n = parameter.component1();
        MetaContainer metas = parameter.component2();
        void index = ordinal - true;
        return new Function1<Environment, ExprValue>((int)index, (int)ordinal, metas){
            final /* synthetic */ int $index;
            final /* synthetic */ int $ordinal;
            final /* synthetic */ MetaContainer $metas;

            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                Intrinsics.checkParameterIsNotNull(env, "env");
                List<ExprValue> params = env.getSession().getParameters();
                if (params.size() <= this.$index) {
                    PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(this.$metas);
                    ErrorCode errorCode = ErrorCode.EVALUATOR_UNBOUND_PARAMETER;
                    String string = "Unbound parameter for ordinal: " + this.$ordinal;
                    boolean bl = false;
                    boolean bl2 = false;
                    PropertyValueMap it = propertyValueMap;
                    boolean bl3 = false;
                    it.set(Property.EXPECTED_PARAMETER_ORDINAL, this.$ordinal);
                    it.set(Property.BOUND_PARAMETER_COUNT, params.size());
                    PropertyValueMap propertyValueMap2 = propertyValueMap;
                    DefaultConstructorMarker defaultConstructorMarker = null;
                    int n = 8;
                    boolean bl4 = false;
                    Throwable throwable = null;
                    PropertyValueMap propertyValueMap3 = propertyValueMap2;
                    ErrorCode errorCode2 = errorCode;
                    String string2 = string;
                    throw (Throwable)new EvaluationException(string2, errorCode2, propertyValueMap3, throwable, bl4, n, defaultConstructorMarker);
                }
                return params.get(this.$index);
            }
            {
                this.$index = n;
                this.$ordinal = n2;
                this.$metas = metaContainer;
                super(1);
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileTyped(Typed expr) {
        Function1 function1;
        void op;
        void dataType;
        void exp;
        Typed typed = expr;
        TypedOp typedOp = typed.component1();
        ExprNode exprNode = typed.component2();
        DataType dataType2 = typed.component3();
        MetaContainer metas = typed.component4();
        Function1<Environment, ExprValue> expThunk = this.compileExprNode((ExprNode)exp);
        ExprValueType exprValueType = ExprValueType.Companion.fromSqlDataType(dataType.getSqlDataType());
        block0 : switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$4[op.ordinal()]) {
            case 1: {
                ThunkFactory this_$iv;
                void var9_8 = dataType;
                SqlDataType sqlDataType = var9_8.component1();
                switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$3[sqlDataType.ordinal()]) {
                    case 1: {
                        this_$iv = this.thunkFactory;
                        boolean $i$f$thunkEnv$lang = false;
                        Meta meta = metas.find("$source_location");
                        if (!(meta instanceof SourceLocationMeta)) {
                            meta = null;
                        }
                        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                        function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, expThunk){
                            final /* synthetic */ ThunkFactory this$0$inline_fun;
                            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                            final /* synthetic */ EvaluatingCompiler this$0;
                            final /* synthetic */ Function1 $expThunk$inlined;
                            {
                                this.this$0$inline_fun = thunkFactory;
                                this.$sourceLocationMeta = sourceLocationMeta;
                                this.this$0 = evaluatingCompiler;
                                this.$expThunk$inlined = function1;
                                super(1);
                            }

                            /*
                             * WARNING - void declaration
                             */
                            @NotNull
                            public final ExprValue invoke(@NotNull Environment env) {
                                ExprValue exprValue2;
                                Intrinsics.checkParameterIsNotNull(env, "env");
                                ThunkFactory thunkFactory = this.this$0$inline_fun;
                                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                boolean $i$f$handleException$lang = false;
                                try {
                                    boolean bl = false;
                                    Environment env2 = env;
                                    boolean bl2 = false;
                                    ExprValue expValue = (ExprValue)this.$expThunk$inlined.invoke(env2);
                                    exprValue2 = EvaluatingCompiler.access$exprValue(this.this$0, expValue.getType() == ExprValueType.MISSING || expValue.getType() == ExprValueType.NULL);
                                } catch (EvaluationException e$iv) {
                                    if (e$iv.getErrorContext() == null) {
                                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                    }
                                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                        if (sourceLocationMeta != null) {
                                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                            boolean bl = false;
                                            boolean bl3 = false;
                                            SourceLocationMeta it$iv = sourceLocationMeta2;
                                            boolean bl4 = false;
                                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                        }
                                    }
                                    throw (Throwable)e$iv;
                                } catch (Exception e$iv) {
                                    void this_$iv;
                                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                    throw null;
                                }
                                return exprValue2;
                            }
                        };
                        break block0;
                    }
                }
                this_$iv = this.thunkFactory;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = metas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, expThunk, exprValueType){
                    final /* synthetic */ ThunkFactory this$0$inline_fun;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ EvaluatingCompiler this$0;
                    final /* synthetic */ Function1 $expThunk$inlined;
                    final /* synthetic */ ExprValueType $exprValueType$inlined;
                    {
                        this.this$0$inline_fun = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.this$0 = evaluatingCompiler;
                        this.$expThunk$inlined = function1;
                        this.$exprValueType$inlined = exprValueType;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0$inline_fun;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            boolean bl = false;
                            Environment env2 = env;
                            boolean bl2 = false;
                            ExprValue expValue = (ExprValue)this.$expThunk$inlined.invoke(env2);
                            exprValue2 = EvaluatingCompiler.access$exprValue(this.this$0, expValue.getType() == this.$exprValueType$inlined);
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl3 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl4 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
                break;
            }
            case 2: {
                SourceLocationMeta locationMeta = EvaluatingCompilerKt.access$getSourceLocationMeta$p(metas);
                ThunkFactory this_$iv = this.thunkFactory;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = metas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, expThunk, (DataType)dataType, locationMeta){
                    final /* synthetic */ ThunkFactory this$0$inline_fun;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ EvaluatingCompiler this$0;
                    final /* synthetic */ Function1 $expThunk$inlined;
                    final /* synthetic */ DataType $dataType$inlined;
                    final /* synthetic */ SourceLocationMeta $locationMeta$inlined;
                    {
                        this.this$0$inline_fun = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.this$0 = evaluatingCompiler;
                        this.$expThunk$inlined = function1;
                        this.$dataType$inlined = dataType;
                        this.$locationMeta$inlined = sourceLocationMeta2;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0$inline_fun;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            boolean bl = false;
                            Environment env2 = env;
                            boolean bl2 = false;
                            ExprValue valueToCast = (ExprValue)this.$expThunk$inlined.invoke(env2);
                            exprValue2 = ExprValueExtensionsKt.cast(valueToCast, this.$dataType$inlined, EvaluatingCompiler.access$getValueFactory$p(this.this$0), this.$locationMeta$inlined);
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl3 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl4 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return function1;
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileSimpleCase(SimpleCase expr) {
        void $this$mapTo$iv$iv;
        void branches;
        Function1 function1;
        void elseExpr;
        void valueExpr;
        SimpleCase simpleCase = expr;
        ExprNode exprNode = simpleCase.component1();
        List<SimpleCaseWhen> list = simpleCase.component2();
        ExprNode exprNode2 = simpleCase.component3();
        MetaContainer metas = simpleCase.component4();
        Function1<Environment, ExprValue> valueThunk = this.compileExprNode((ExprNode)valueExpr);
        if (elseExpr != null) {
            function1 = this.compileExprNode((ExprNode)elseExpr);
        } else {
            ThunkFactory this_$iv = this.thunkFactory;
            boolean $i$f$thunkEnv$lang = false;
            Meta meta = metas.find("$source_location");
            if (!(meta instanceof SourceLocationMeta)) {
                meta = null;
            }
            SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
            function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this){
                final /* synthetic */ ThunkFactory this$0$inline_fun;
                final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                final /* synthetic */ EvaluatingCompiler this$0;
                {
                    this.this$0$inline_fun = thunkFactory;
                    this.$sourceLocationMeta = sourceLocationMeta;
                    this.this$0 = evaluatingCompiler;
                    super(1);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final ExprValue invoke(@NotNull Environment env) {
                    ExprValue exprValue2;
                    Intrinsics.checkParameterIsNotNull(env, "env");
                    ThunkFactory thunkFactory = this.this$0$inline_fun;
                    SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                    boolean $i$f$handleException$lang = false;
                    try {
                        boolean bl = false;
                        Environment $noName_0 = env;
                        boolean bl2 = false;
                        exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).getNullValue();
                    } catch (EvaluationException e$iv) {
                        if (e$iv.getErrorContext() == null) {
                            throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                        }
                        if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                            SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                            if (sourceLocationMeta != null) {
                                SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                boolean bl = false;
                                boolean bl3 = false;
                                SourceLocationMeta it$iv = sourceLocationMeta2;
                                boolean bl4 = false;
                                ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                            }
                        }
                        throw (Throwable)e$iv;
                    } catch (Exception e$iv) {
                        void this_$iv;
                        R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                        throw null;
                    }
                    return exprValue2;
                }
            };
        }
        Function1 elseThunk = function1;
        Iterable $this$map$iv = (Iterable)branches;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            SimpleCaseWhen simpleCaseWhen = (SimpleCaseWhen)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Pair<Function1<Environment, ExprValue>, Function1<Environment, ExprValue>> pair = new Pair<Function1<Environment, ExprValue>, Function1<Environment, ExprValue>>(this.compileExprNode(it.getValueExpr()), this.compileExprNode(it.getThenExpr()));
            collection.add(pair);
        }
        List branchThunks = (List)destination$iv$iv;
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, valueThunk, branchThunks, elseThunk){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $valueThunk$inlined;
            final /* synthetic */ List $branchThunks$inlined;
            final /* synthetic */ Function1 $elseThunk$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$valueThunk$inlined = function1;
                this.$branchThunks$inlined = list;
                this.$elseThunk$inlined = function12;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block7: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue caseValue = (ExprValue)this.$valueThunk$inlined.invoke(env2);
                        Iterable $this$forEach$iv = this.$branchThunks$inlined;
                        boolean $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            ExprValue thenValue;
                            Pair bt = (Pair)element$iv;
                            boolean bl3 = false;
                            ExprValue branchValue = (ExprValue)((Function1)bt.getFirst()).invoke(env2);
                            if (!ExprValueExtensionsKt.exprEquals(caseValue, branchValue)) continue;
                            exprValue3 = thenValue = (ExprValue)((Function1)bt.getSecond()).invoke(env2);
                            break block7;
                        }
                        exprValue3 = (ExprValue)this.$elseThunk$inlined.invoke(env2);
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl4 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl5 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileSearchedCase(SearchedCase expr) {
        void $this$mapTo$iv$iv;
        void whenClauses;
        Function1 function1;
        void elseExpr;
        SearchedCase searchedCase = expr;
        List<SearchedCaseWhen> list = searchedCase.component1();
        ExprNode exprNode = searchedCase.component2();
        MetaContainer metas = searchedCase.component3();
        if (elseExpr != null) {
            function1 = this.compileExprNode((ExprNode)elseExpr);
        } else {
            ThunkFactory this_$iv = this.thunkFactory;
            boolean $i$f$thunkEnv$lang = false;
            Meta meta = metas.find("$source_location");
            if (!(meta instanceof SourceLocationMeta)) {
                meta = null;
            }
            SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
            function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this){
                final /* synthetic */ ThunkFactory this$0$inline_fun;
                final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                final /* synthetic */ EvaluatingCompiler this$0;
                {
                    this.this$0$inline_fun = thunkFactory;
                    this.$sourceLocationMeta = sourceLocationMeta;
                    this.this$0 = evaluatingCompiler;
                    super(1);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final ExprValue invoke(@NotNull Environment env) {
                    ExprValue exprValue2;
                    Intrinsics.checkParameterIsNotNull(env, "env");
                    ThunkFactory thunkFactory = this.this$0$inline_fun;
                    SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                    boolean $i$f$handleException$lang = false;
                    try {
                        boolean bl = false;
                        Environment $noName_0 = env;
                        boolean bl2 = false;
                        exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).getNullValue();
                    } catch (EvaluationException e$iv) {
                        if (e$iv.getErrorContext() == null) {
                            throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                        }
                        if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                            SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                            if (sourceLocationMeta != null) {
                                SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                boolean bl = false;
                                boolean bl3 = false;
                                SourceLocationMeta it$iv = sourceLocationMeta2;
                                boolean bl4 = false;
                                ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                            }
                        }
                        throw (Throwable)e$iv;
                    } catch (Exception e$iv) {
                        void this_$iv;
                        R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                        throw null;
                    }
                    return exprValue2;
                }
            };
        }
        Function1 elseThunk = function1;
        Iterable $this$map$iv = (Iterable)whenClauses;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            SearchedCaseWhen searchedCaseWhen = (SearchedCaseWhen)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Pair<Function1<Environment, ExprValue>, Function1<Environment, ExprValue>> pair = new Pair<Function1<Environment, ExprValue>, Function1<Environment, ExprValue>>(this.compileExprNode(it.getCondition()), this.compileExprNode(it.getThenExpr()));
            collection.add(pair);
        }
        List branchThunks = (List)destination$iv$iv;
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, branchThunks, elseThunk){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ List $branchThunks$inlined;
            final /* synthetic */ Function1 $elseThunk$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$branchThunks$inlined = list;
                this.$elseThunk$inlined = function1;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block7: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        Iterable $this$forEach$iv = this.$branchThunks$inlined;
                        boolean $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            Pair bt = (Pair)element$iv;
                            boolean bl3 = false;
                            ExprValue conditionValue = (ExprValue)((Function1)bt.getFirst()).invoke(env2);
                            if (!ExprValueExtensionsKt.booleanValue(conditionValue)) continue;
                            exprValue3 = (ExprValue)((Function1)bt.getSecond()).invoke(env2);
                            break block7;
                        }
                        exprValue3 = (ExprValue)this.$elseThunk$inlined.invoke(env2);
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl4 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl5 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileStruct(Struct expr) {
        void $this$mapTo$iv$iv;
        void fields;
        Struct struct = expr;
        List<StructField> list = struct.component1();
        MetaContainer metas = struct.component2();
        Iterable $this$map$iv = (Iterable)fields;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void nameExpr;
            void it;
            StructField structField = (StructField)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            void var14_14 = it;
            ExprNode exprNode = var14_14.component1();
            ExprNode valueExpr = var14_14.component2();
            @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001f\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B5\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006\u0012\u0016\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006\u00a2\u0006\u0002\u0010\bR!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR!\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n\u00a8\u0006\f"}, d2={"org/partiql/lang/eval/EvaluatingCompiler$compileStruct$StructFieldThunks", "", "nameThunk", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Environment;", "Lorg/partiql/lang/eval/ExprValue;", "Lorg/partiql/lang/eval/ThunkEnv;", "valueThunk", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getNameThunk", "()Lkotlin/jvm/functions/Function1;", "getValueThunk", "lang"})
            public final class StructFieldThunks {
                @NotNull
                private final Function1<Environment, ExprValue> nameThunk;
                @NotNull
                private final Function1<Environment, ExprValue> valueThunk;

                @NotNull
                public final Function1<Environment, ExprValue> getNameThunk() {
                    return this.nameThunk;
                }

                @NotNull
                public final Function1<Environment, ExprValue> getValueThunk() {
                    return this.valueThunk;
                }

                public StructFieldThunks(@NotNull Function1<? super Environment, ? extends ExprValue> nameThunk, @NotNull Function1<? super Environment, ? extends ExprValue> valueThunk) {
                    Intrinsics.checkParameterIsNotNull(nameThunk, "nameThunk");
                    Intrinsics.checkParameterIsNotNull(valueThunk, "valueThunk");
                    this.nameThunk = nameThunk;
                    this.valueThunk = valueThunk;
                }
            }
            StructFieldThunks structFieldThunks = new StructFieldThunks(this.compileExprNode((ExprNode)nameExpr), this.compileExprNode(valueExpr));
            collection.add(structFieldThunks);
        }
        List fieldThunks = (List)destination$iv$iv;
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, fieldThunks){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ List $fieldThunks$inlined;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.this$0 = evaluatingCompiler;
                this.$fieldThunks$inlined = list;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    void $this$mapTo$iv$iv;
                    boolean bl = false;
                    Environment env2 = env;
                    boolean bl2 = false;
                    Iterable $this$map$iv = this.$fieldThunks$inlined;
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it;
                        StructFieldThunks structFieldThunks = (StructFieldThunks)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl3 = false;
                        ExprValue exprValue3 = ExprValueExtensionsKt.namedValue(it.getValueThunk().invoke(env2), it.getNameThunk().invoke(env2));
                        collection.add(exprValue3);
                    }
                    Sequence<T> seq2 = CollectionsKt.asSequence((List)destination$iv$iv);
                    exprValue2 = EvaluatingCompiler.access$createStructExprValue(this.this$0, seq2, StructOrdering.ORDERED);
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl4 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl5 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileSeq(Seq expr) {
        Function1 function1;
        ExprValueType exprValueType;
        void seqType;
        void $this$mapTo$iv$iv;
        void items;
        Seq seq2 = expr;
        SeqType seqType2 = seq2.component1();
        List<ExprNode> list = seq2.component2();
        MetaContainer metas = seq2.component3();
        Iterable $this$map$iv = (Iterable)items;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ExprNode exprNode = (ExprNode)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Function1<Environment, ExprValue> function12 = this.compileExprNode((ExprNode)it);
            collection.add(function12);
        }
        Sequence itemThunks = CollectionsKt.asSequence((List)destination$iv$iv);
        switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$5[seqType.ordinal()]) {
            case 1: {
                exprValueType = ExprValueType.SEXP;
                break;
            }
            case 2: {
                exprValueType = ExprValueType.LIST;
                break;
            }
            case 3: {
                exprValueType = ExprValueType.BAG;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        ExprValueType type = exprValueType;
        switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$6[type.ordinal()]) {
            case 1: {
                function1 = new Function1<Environment, Sequence<? extends ExprValue>>(itemThunks){
                    final /* synthetic */ Sequence $itemThunks;

                    @NotNull
                    public final Sequence<ExprValue> invoke(@NotNull Environment env) {
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        return SequencesKt.map(this.$itemThunks, (Function1)new Function1<Function1<? super Environment, ? extends ExprValue>, ExprValue>(env){
                            final /* synthetic */ Environment $env;

                            @NotNull
                            public final ExprValue invoke(@NotNull Function1<? super Environment, ? extends ExprValue> itemThunk) {
                                Intrinsics.checkParameterIsNotNull(itemThunk, "itemThunk");
                                return ExprValueExtensionsKt.unnamedValue(itemThunk.invoke(this.$env));
                            }
                            {
                                this.$env = environment;
                                super(1);
                            }
                        });
                    }
                    {
                        this.$itemThunks = sequence;
                        super(1);
                    }
                };
                break;
            }
            default: {
                function1 = new Function1<Environment, Sequence<? extends ExprValue>>(this, itemThunks){
                    final /* synthetic */ EvaluatingCompiler this$0;
                    final /* synthetic */ Sequence $itemThunks;

                    @NotNull
                    public final Sequence<ExprValue> invoke(@NotNull Environment env) {
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        return SequencesKt.mapIndexed(this.$itemThunks, (Function2)new Function2<Integer, Function1<? super Environment, ? extends ExprValue>, ExprValue>(this, env){
                            final /* synthetic */ compileSeq.makeItemThunkSequence.2 this$0;
                            final /* synthetic */ Environment $env;

                            @NotNull
                            public final ExprValue invoke(int i, @NotNull Function1<? super Environment, ? extends ExprValue> itemThunk) {
                                Intrinsics.checkParameterIsNotNull(itemThunk, "itemThunk");
                                return ExprValueExtensionsKt.namedValue(itemThunk.invoke(this.$env), EvaluatingCompiler.access$exprValue(this.this$0.this$0, i));
                            }
                            {
                                this.this$0 = var1_1;
                                this.$env = environment;
                                super(2);
                            }
                        });
                    }
                    {
                        this.this$0 = evaluatingCompiler;
                        this.$itemThunks = sequence;
                        super(1);
                    }
                };
            }
        }
        Function1 makeItemThunkSequence2 = function1;
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, type, makeItemThunkSequence2){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ ExprValueType $type$inlined;
            final /* synthetic */ Function1 $makeItemThunkSequence$inlined;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.this$0 = evaluatingCompiler;
                this.$type$inlined = exprValueType;
                this.$makeItemThunkSequence$inlined = function1;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    boolean bl = false;
                    Environment env2 = env;
                    boolean bl2 = false;
                    exprValue2 = new SequenceExprValue(EvaluatingCompiler.access$getValueFactory$p(this.this$0).getIon(), this.$type$inlined, (Sequence)this.$makeItemThunkSequence$inlined.invoke(env2));
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl3 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl4 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    private final long evalLimit(Function1<? super Environment, ? extends ExprValue> limitThunk, Environment env, SourceLocationMeta limitLocationMeta) {
        ExprValue limitExprValue = limitThunk.invoke(env);
        if (limitExprValue.getType() != ExprValueType.INT) {
            PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(limitLocationMeta);
            ErrorCode errorCode = ErrorCode.EVALUATOR_NON_INT_LIMIT_VALUE;
            String string = "LIMIT value was not an integer";
            boolean bl = false;
            boolean bl2 = false;
            PropertyValueMap it = propertyValueMap;
            boolean bl3 = false;
            it.set(Property.ACTUAL_TYPE, limitExprValue.getType().toString());
            PropertyValueMap propertyValueMap2 = propertyValueMap;
            Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap2, false);
            throw null;
        }
        IonValue ionValue2 = limitExprValue.getIonValue();
        if (ionValue2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.amazon.ion.IonInt");
        }
        IonInt limitIonValue = (IonInt)ionValue2;
        if (limitIonValue.getIntegerSize() == IntegerSize.BIG_INTEGER) {
            Void void_ = ExceptionsKt.err("IntegerSize.BIG_INTEGER not supported for LIMIT values", ExceptionsKt.errorContextFrom(limitLocationMeta), true);
            throw null;
        }
        long limitValue = ExprValueExtensionsKt.numberValue(limitExprValue).longValue();
        if (limitValue < 0L) {
            Void void_ = ExceptionsKt.err("negative LIMIT", ErrorCode.EVALUATOR_NEGATIVE_LIMIT, ExceptionsKt.errorContextFrom(limitLocationMeta), false);
            throw null;
        }
        return limitValue;
    }

    /*
     * Unable to fully structure code
     */
    private final Function1<Environment, ExprValue> compileSelect(Select selectExpr) {
        v0 = selectExpr.getOrderBy();
        if (v0 != null) {
            var2_2 = v0;
            var3_4 = false;
            var4_6 = false;
            it = var2_2;
            $i$a$-let-EvaluatingCompiler$compileSelect$1 = false;
            var7_12 = ExceptionsKt.errorContextFrom(selectExpr.getMetas());
            var8_14 = ErrorCode.EVALUATOR_FEATURE_NOT_SUPPORTED_YET;
            var9_16 = "ORDER BY is not supported in evaluator yet";
            var10_18 = false;
            var11_19 = false;
            it = var7_12;
            $i$a$-also-EvaluatingCompiler$compileSelect$1$1 = false;
            it.set(Property.FEATURE_NAME, "ORDER BY");
            var14_22 = var7_12;
            v1 = ExceptionsKt.err(var9_16, var8_14, var14_22, false);
            throw null;
        }
        fold = new PartiqlAst.VisitorFold<Set<? extends String>>(){

            @NotNull
            protected Set<String> visitFromSourceScan(@NotNull PartiqlAst.FromSource.Scan node, @NotNull Set<String> accumulator) {
                Intrinsics.checkParameterIsNotNull(node, "node");
                Intrinsics.checkParameterIsNotNull(accumulator, "accumulator");
                String[] stringArray = new String[3];
                SymbolPrimitive symbolPrimitive = node.getAsAlias();
                stringArray[0] = symbolPrimitive != null ? symbolPrimitive.getText() : null;
                SymbolPrimitive symbolPrimitive2 = node.getAtAlias();
                stringArray[1] = symbolPrimitive2 != null ? symbolPrimitive2.getText() : null;
                SymbolPrimitive symbolPrimitive3 = node.getByAlias();
                stringArray[2] = symbolPrimitive3 != null ? symbolPrimitive3.getText() : null;
                List<String> aliases = CollectionsKt.listOfNotNull(stringArray);
                return SetsKt.plus(accumulator, (Iterable)CollectionsKt.toSet((Iterable)aliases));
            }

            @NotNull
            protected Set<String> visitLetBinding(@NotNull PartiqlAst.LetBinding node, @NotNull Set<String> accumulator) {
                Intrinsics.checkParameterIsNotNull(node, "node");
                Intrinsics.checkParameterIsNotNull(accumulator, "accumulator");
                List<String> aliases = CollectionsKt.listOfNotNull(node.getName().getText());
                return SetsKt.plus(accumulator, (Iterable)aliases);
            }

            @NotNull
            public Set<String> walkExprSelect(@NotNull PartiqlAst.Expr.Select node, @NotNull Set<String> accumulator) {
                Intrinsics.checkParameterIsNotNull(node, "node");
                Intrinsics.checkParameterIsNotNull(accumulator, "accumulator");
                return accumulator;
            }
        };
        v2 = ExprNodeToStatementKt.toAstExpr(selectExpr);
        if (v2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.domains.PartiqlAst.Expr.Select");
        }
        pigGeneratedAst = (PartiqlAst.Expr.Select)v2;
        v3 = fold.walkFromSource(pigGeneratedAst.getFrom(), SetsKt.emptySet());
        v4 = pigGeneratedAst.getFromLet();
        if (v4 == null) ** GOTO lbl-1000
        var5_9 = v4;
        var15_23 = v3;
        var6_11 = false;
        var7_13 = false;
        it = var5_9;
        $i$a$-let-EvaluatingCompiler$compileSelect$allFromSourceAliases$1 = false;
        var16_24 = fold.walkLet(pigGeneratedAst.getFromLet(), SetsKt.emptySet());
        v3 = var15_23;
        v4 = var16_24;
        if (v4 != null) {
            v5 = v4;
        } else lbl-1000:
        // 2 sources

        {
            v5 = SetsKt.emptySet();
        }
        allFromSourceAliases = CollectionsKt.union(v3, v5);
        return (Function1)this.nestCompilationContext(ExpressionContext.NORMAL, SetsKt.emptySet(), (Function0)new Function0<Function1<? super Environment, ? extends ExprValue>>(this, selectExpr, allFromSourceAliases){
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ Select $selectExpr;
            final /* synthetic */ Set $allFromSourceAliases;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Function1<Environment, ExprValue> invoke() {
                Function1 function1;
                ExprNode valueExpr;
                void projection;
                void having2;
                void setQuantifier;
                void groupBy2;
                Function1 function12;
                void limit2;
                List list;
                void fromLet2;
                void from2;
                Select select = this.$selectExpr;
                SetQuantifier setQuantifier2 = select.component1();
                SelectProjection selectProjection = select.component2();
                FromSource fromSource = select.component3();
                LetSource letSource = select.component4();
                GroupBy groupBy3 = select.component6();
                ExprNode exprNode = select.component7();
                ExprNode exprNode2 = select.component9();
                MetaContainer metas = select.component10();
                List fromSourceThunks = EvaluatingCompiler.compileFromSources$default(this.this$0, (FromSource)from2, null, null, null, 14, null);
                void v0 = fromLet2;
                if (v0 != null) {
                    void var11_10 = v0;
                    boolean bl = false;
                    boolean bl2 = false;
                    void it = var11_10;
                    boolean bl3 = false;
                    list = EvaluatingCompiler.access$compileLetSources(this.this$0, (LetSource)it);
                } else {
                    list = null;
                }
                List letSourceThunks = list;
                Function1 sourceThunks = EvaluatingCompiler.access$compileQueryWithoutProjection(this.this$0, this.$selectExpr, fromSourceThunks, letSourceThunks);
                void v2 = limit2;
                if (v2 != null) {
                    void var13_14 = v2;
                    boolean it = false;
                    boolean bl = false;
                    void it2 = var13_14;
                    boolean bl4 = false;
                    function12 = EvaluatingCompiler.access$compileExprNode(this.this$0, (ExprNode)limit2);
                } else {
                    function12 = null;
                }
                Function1 limitThunk = function12;
                MetaContainer metaContainer = limit2;
                SourceLocationMeta limitLocationMeta = metaContainer != null && (metaContainer = metaContainer.getMetas()) != null ? EvaluatingCompilerKt.access$getSourceLocationMeta$p(metaContainer) : null;
                Function1<Function2<? super Environment, ? super List<? extends ExprValue>, ? extends ExprValue>, Function1<? super Environment, ? extends ExprValue>> $fun$getQueryThunk$1 = new Function1<Function2<? super Environment, ? super List<? extends ExprValue>, ? extends ExprValue>, Function1<? super Environment, ? extends ExprValue>>(this, (GroupBy)groupBy2, metas, sourceThunks, (SetQuantifier)setQuantifier, limitThunk, limitLocationMeta, fromSourceThunks, (ExprNode)having2){
                    final /* synthetic */ compileSelect.2 this$0;
                    final /* synthetic */ GroupBy $groupBy;
                    final /* synthetic */ MetaContainer $metas;
                    final /* synthetic */ Function1 $sourceThunks;
                    final /* synthetic */ SetQuantifier $setQuantifier;
                    final /* synthetic */ Function1 $limitThunk;
                    final /* synthetic */ SourceLocationMeta $limitLocationMeta;
                    final /* synthetic */ List $fromSourceThunks;
                    final /* synthetic */ ExprNode $having;

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final Function1<Environment, ExprValue> invoke(@NotNull Function2<? super Environment, ? super List<? extends ExprValue>, ? extends ExprValue> selectProjectionThunk) {
                        Object compiledAggregates;
                        Function1 function1;
                        void this_$iv;
                        Object sourceLocationMeta$iv;
                        boolean $i$f$thunkEnv$lang;
                        MetaContainer metas$iv;
                        void groupByItems;
                        Intrinsics.checkParameterIsNotNull(selectProjectionThunk, "selectProjectionThunk");
                        GroupBy groupBy2 = this.$groupBy;
                        if (groupBy2 == null) {
                            GroupingStrategy groupingStrategy = GroupingStrategy.FULL;
                            boolean bl = false;
                            List<T> list = CollectionsKt.emptyList();
                            DefaultConstructorMarker defaultConstructorMarker = null;
                            int n = 4;
                            SymbolicName symbolicName = null;
                            List<T> list2 = list;
                            GroupingStrategy groupingStrategy2 = groupingStrategy;
                            groupBy2 = new GroupBy(groupingStrategy2, list2, symbolicName, n, defaultConstructorMarker);
                        }
                        GroupBy groupBy3 = groupBy2;
                        List<GroupByItem> list = groupBy3.component2();
                        SymbolicName groupAsName = groupBy3.component3();
                        AggregateCallSiteListMeta aggregateListMeta = (AggregateCallSiteListMeta)this.this$0.$selectExpr.getMetas().find("$aggregate_call_sites");
                        Object object = aggregateListMeta;
                        boolean hasAggregateCallSites = object != null && (object = ((AggregateCallSiteListMeta)object).getAggregateCallSites()) != null ? CollectionsKt.any((Iterable)object) : false;
                        if (groupByItems.isEmpty() && !hasAggregateCallSites) {
                            ThunkFactory thunkFactory = EvaluatingCompiler.access$getThunkFactory$p(this.this$0.this$0);
                            metas$iv = this.$metas;
                            $i$f$thunkEnv$lang = false;
                            Meta meta = metas$iv.find("$source_location");
                            if (!(meta instanceof SourceLocationMeta)) {
                                meta = null;
                            }
                            sourceLocationMeta$iv = (SourceLocationMeta)meta;
                            function1 = new Function1<Environment, ExprValue>((ThunkFactory)this_$iv, (SourceLocationMeta)sourceLocationMeta$iv, this, selectProjectionThunk){
                                final /* synthetic */ ThunkFactory this$0$inline_fun;
                                final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                                final /* synthetic */ compileSelect.1 this$0;
                                final /* synthetic */ Function2 $selectProjectionThunk$inlined;
                                {
                                    this.this$0$inline_fun = thunkFactory;
                                    this.$sourceLocationMeta = sourceLocationMeta;
                                    this.this$0 = var3_3;
                                    this.$selectProjectionThunk$inlined = function2;
                                    super(1);
                                }

                                /*
                                 * WARNING - void declaration
                                 */
                                @NotNull
                                public final ExprValue invoke(@NotNull Environment env) {
                                    ExprValue exprValue2;
                                    Intrinsics.checkParameterIsNotNull(env, "env");
                                    ThunkFactory thunkFactory = this.this$0$inline_fun;
                                    SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                    boolean $i$f$handleException$lang = false;
                                    try {
                                        Sequence<Object> sequence;
                                        boolean bl = false;
                                        Environment env2 = env;
                                        boolean bl2 = false;
                                        Sequence<R> projectedRows = SequencesKt.map((Sequence)this.this$0.$sourceThunks.invoke(env2), (Function1)new Function1<FromProduction, ExprValue>(this){
                                            final /* synthetic */ compileSelect$2$1$getQueryThunk$$inlined$thunkEnv$lang$1 this$0;
                                            {
                                                this.this$0 = var1_1;
                                                super(1);
                                            }

                                            @NotNull
                                            public final ExprValue invoke(@NotNull FromProduction $dstr$joinedValues$projectEnv) {
                                                Intrinsics.checkParameterIsNotNull($dstr$joinedValues$projectEnv, "<name for destructuring parameter 0>");
                                                List<ExprValue> joinedValues = $dstr$joinedValues$projectEnv.component1();
                                                Environment projectEnv = $dstr$joinedValues$projectEnv.component2();
                                                return (ExprValue)this.this$0.$selectProjectionThunk$inlined.invoke(projectEnv, joinedValues);
                                            }
                                        });
                                        switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$7[this.this$0.$setQuantifier.ordinal()]) {
                                            case 1: {
                                                sequence = SequencesKt.filter(projectedRows, ExprValueExtensionsKt.createUniqueExprValueFilter());
                                                break;
                                            }
                                            case 2: {
                                                sequence = projectedRows;
                                                break;
                                            }
                                            default: {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                        }
                                        Sequence<Object> sequence2 = sequence;
                                        boolean bl3 = false;
                                        boolean bl4 = false;
                                        Sequence<Object> rows = sequence2;
                                        boolean bl5 = false;
                                        Function1 function1 = this.this$0.$limitThunk;
                                        Sequence<Object> quantifiedRows = function1 == null ? rows : CollectionExtensionsKt.take(rows, EvaluatingCompiler.access$evalLimit(this.this$0.this$0.this$0, this.this$0.$limitThunk, env2, this.this$0.$limitLocationMeta));
                                        exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0).newBag(SequencesKt.map(quantifiedRows, compileSelect.queryThunk.1.1.INSTANCE));
                                    } catch (EvaluationException e$iv) {
                                        if (e$iv.getErrorContext() == null) {
                                            throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                        }
                                        if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                            SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                            if (sourceLocationMeta != null) {
                                                SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                                boolean bl = false;
                                                boolean bl6 = false;
                                                SourceLocationMeta it$iv = sourceLocationMeta2;
                                                boolean bl7 = false;
                                                ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                            }
                                        }
                                        throw (Throwable)e$iv;
                                    } catch (Exception e$iv) {
                                        void this_$iv;
                                        R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                        throw null;
                                    }
                                    return exprValue2;
                                }
                            };
                        } else {
                            List list3;
                            Object object2;
                            Collection collection;
                            Object object3 = aggregateListMeta;
                            if (object3 != null && (object3 = ((AggregateCallSiteListMeta)object3).getAggregateCallSites()) != null) {
                                void $this$mapTo$iv$iv;
                                Iterable $this$map$iv = (Iterable)object3;
                                boolean $i$f$map = false;
                                sourceLocationMeta$iv = $this$map$iv;
                                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                                boolean $i$f$mapTo = false;
                                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                                    void it;
                                    PartiqlAst.Expr.CallAgg callAgg = (PartiqlAst.Expr.CallAgg)item$iv$iv;
                                    collection = destination$iv$iv;
                                    boolean bl = false;
                                    String funcName = it.getFuncName().getText();
                                    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000%\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b\u00a2\u0006\u0002\u0010\tR!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2={"org/partiql/lang/eval/EvaluatingCompiler$compileSelect$2$1$queryThunk$CompiledAggregate", "", "factory", "Lorg/partiql/lang/eval/ExprAggregatorFactory;", "argThunk", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Environment;", "Lorg/partiql/lang/eval/ExprValue;", "Lorg/partiql/lang/eval/ThunkEnv;", "(Lorg/partiql/lang/eval/ExprAggregatorFactory;Lkotlin/jvm/functions/Function1;)V", "getArgThunk", "()Lkotlin/jvm/functions/Function1;", "getFactory", "()Lorg/partiql/lang/eval/ExprAggregatorFactory;", "lang"})
                                    public final class CompiledAggregate {
                                        @NotNull
                                        private final ExprAggregatorFactory factory;
                                        @NotNull
                                        private final Function1<Environment, ExprValue> argThunk;

                                        @NotNull
                                        public final ExprAggregatorFactory getFactory() {
                                            return this.factory;
                                        }

                                        @NotNull
                                        public final Function1<Environment, ExprValue> getArgThunk() {
                                            return this.argThunk;
                                        }

                                        public CompiledAggregate(@NotNull ExprAggregatorFactory factory, @NotNull Function1<? super Environment, ? extends ExprValue> argThunk2) {
                                            Intrinsics.checkParameterIsNotNull(factory, "factory");
                                            Intrinsics.checkParameterIsNotNull(argThunk2, "argThunk");
                                            this.factory = factory;
                                            this.argThunk = argThunk2;
                                        }
                                    }
                                    object2 = new CompiledAggregate(this.this$0.this$0.getAggregatorFactory(funcName, StatementToExprNodeKt.toExprNodeSetQuantifier(it.getSetq()), StatementToExprNodeKt.toPartiQlMetaContainer(it.getMetas())), EvaluatingCompiler.access$compileExprNode(this.this$0.this$0, StatementToExprNodeKt.toExprNode(it.getArg(), EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0).getIon())));
                                    collection.add(object2);
                                }
                                list3 = (List)destination$iv$iv;
                            } else {
                                list3 = null;
                            }
                            compiledAggregates = list3;
                            Object $i$f$map = aggregateListMeta;
                            Function0 createRegisterBank2 = $i$f$map == null ? (Function0)compileSelect.queryThunk.createRegisterBank.1.INSTANCE : (Function0)new Function0<RegisterBank>(aggregateListMeta, compiledAggregates){
                                final /* synthetic */ AggregateCallSiteListMeta $aggregateListMeta;
                                final /* synthetic */ List $compiledAggregates;

                                /*
                                 * WARNING - void declaration
                                 */
                                @NotNull
                                public final RegisterBank invoke() {
                                    RegisterBank registerBank;
                                    block2: {
                                        registerBank = new RegisterBank(this.$aggregateListMeta.getAggregateCallSites().size());
                                        boolean bl = false;
                                        boolean bl2 = false;
                                        RegisterBank $this$apply = registerBank;
                                        boolean bl3 = false;
                                        List list = this.$compiledAggregates;
                                        if (list == null) break block2;
                                        Iterable $this$forEachIndexed$iv = list;
                                        boolean $i$f$forEachIndexed = false;
                                        int index$iv = 0;
                                        for (T item$iv : $this$forEachIndexed$iv) {
                                            void ca;
                                            int n = index$iv++;
                                            boolean bl4 = false;
                                            if (n < 0) {
                                                CollectionsKt.throwIndexOverflow();
                                            }
                                            int n2 = n;
                                            CompiledAggregate compiledAggregate = (CompiledAggregate)item$iv;
                                            int index = n2;
                                            boolean bl5 = false;
                                            $this$apply.set(index, ca.getFactory().create());
                                        }
                                    }
                                    return registerBank;
                                }
                                {
                                    this.$aggregateListMeta = aggregateCallSiteListMeta;
                                    this.$compiledAggregates = list;
                                    super(0);
                                }
                            };
                            if (groupByItems.isEmpty()) {
                                void this_$iv2;
                                $i$f$map = EvaluatingCompiler.access$getThunkFactory$p(this.this$0.this$0);
                                MetaContainer metas$iv2 = this.$metas;
                                boolean $i$f$thunkEnv$lang2 = false;
                                Meta meta = metas$iv2.find("$source_location");
                                if (!(meta instanceof SourceLocationMeta)) {
                                    meta = null;
                                }
                                SourceLocationMeta sourceLocationMeta$iv2 = (SourceLocationMeta)meta;
                                function1 = new Function1<Environment, ExprValue>((ThunkFactory)this_$iv2, sourceLocationMeta$iv2, this, createRegisterBank2, (List)compiledAggregates, selectProjectionThunk){
                                    final /* synthetic */ ThunkFactory this$0$inline_fun;
                                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                                    final /* synthetic */ compileSelect.1 this$0;
                                    final /* synthetic */ Function0 $createRegisterBank$inlined;
                                    final /* synthetic */ List $compiledAggregates$inlined;
                                    final /* synthetic */ Function2 $selectProjectionThunk$inlined;
                                    {
                                        this.this$0$inline_fun = thunkFactory;
                                        this.$sourceLocationMeta = sourceLocationMeta;
                                        this.this$0 = var3_3;
                                        this.$createRegisterBank$inlined = function0;
                                        this.$compiledAggregates$inlined = list;
                                        this.$selectProjectionThunk$inlined = function2;
                                        super(1);
                                    }

                                    /*
                                     * WARNING - void declaration
                                     */
                                    @NotNull
                                    public final ExprValue invoke(@NotNull Environment env) {
                                        ExprValue exprValue2;
                                        Intrinsics.checkParameterIsNotNull(env, "env");
                                        ThunkFactory thunkFactory = this.this$0$inline_fun;
                                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                        boolean $i$f$handleException$lang = false;
                                        try {
                                            boolean bl = false;
                                            Environment env2 = env;
                                            boolean bl2 = false;
                                            R r = this.this$0.$sourceThunks.invoke(env2);
                                            boolean bl3 = false;
                                            boolean bl4 = false;
                                            Sequence<T> rows = (Sequence<T>)r;
                                            boolean bl5 = false;
                                            Function1 function1 = this.this$0.$limitThunk;
                                            Sequence<T> fromProductions = function1 == null ? rows : CollectionExtensionsKt.take(rows, EvaluatingCompiler.access$evalLimit(this.this$0.this$0.this$0, this.this$0.$limitThunk, env2, this.this$0.$limitLocationMeta));
                                            RegisterBank registers = (RegisterBank)this.$createRegisterBank$inlined.invoke();
                                            Group syntheticGroup = new Group(EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0).getNullValue(), registers);
                                            Sequence<T> $this$forEach$iv = fromProductions;
                                            boolean $i$f$forEach = false;
                                            Iterator<T> iterator2 = $this$forEach$iv.iterator();
                                            while (iterator2.hasNext()) {
                                                T element$iv = iterator2.next();
                                                FromProduction fromProduction = (FromProduction)element$iv;
                                                boolean bl6 = false;
                                                List list = this.$compiledAggregates$inlined;
                                                if (list == null) continue;
                                                Iterable $this$forEachIndexed$iv = list;
                                                boolean $i$f$forEachIndexed = false;
                                                int index$iv = 0;
                                                for (T item$iv : $this$forEachIndexed$iv) {
                                                    void ca;
                                                    int n = index$iv++;
                                                    boolean bl7 = false;
                                                    if (n < 0) {
                                                        CollectionsKt.throwIndexOverflow();
                                                    }
                                                    int n2 = n;
                                                    CompiledAggregate compiledAggregate = (CompiledAggregate)item$iv;
                                                    int index = n2;
                                                    boolean bl8 = false;
                                                    registers.get(index).getAggregator().next(ca.getArgThunk().invoke(fromProduction.getEnv()));
                                                }
                                            }
                                            ExprValue groupResult = (ExprValue)this.$selectProjectionThunk$inlined.invoke(Environment.copy$default(env2, null, null, null, null, syntheticGroup, 15, null), CollectionsKt.listOf(syntheticGroup.getKey()));
                                            exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0).newBag(CollectionsKt.asSequence((Iterable)CollectionsKt.listOf(groupResult)));
                                        } catch (EvaluationException e$iv) {
                                            if (e$iv.getErrorContext() == null) {
                                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                            }
                                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                                if (sourceLocationMeta != null) {
                                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                                    boolean bl = false;
                                                    boolean bl9 = false;
                                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                                    boolean bl10 = false;
                                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                                }
                                            }
                                            throw (Throwable)e$iv;
                                        } catch (Exception e$iv) {
                                            void this_$iv;
                                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                            throw null;
                                        }
                                        return exprValue2;
                                    }
                                };
                            } else {
                                void this_$iv3;
                                Function1 function12;
                                void $this$mapTo$iv$iv2;
                                Object item$iv$iv;
                                List compiledGroupByItems = EvaluatingCompiler.access$compileGroupByExpressions(this.this$0.this$0, (List)groupByItems);
                                Function1 groupKeyThunk = EvaluatingCompiler.access$compileGroupKeyThunk(this.this$0.this$0, compiledGroupByItems, this.$metas);
                                Iterable $this$map$iv = this.$fromSourceThunks;
                                boolean $i$f$map22 = false;
                                item$iv$iv = $this$map$iv;
                                Object destination$iv$iv2 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                                boolean $i$f$mapTo = false;
                                for (E item$iv$iv2 : $this$mapTo$iv$iv2) {
                                    void it;
                                    CompiledFromSource compiledFromSource = (CompiledFromSource)item$iv$iv2;
                                    collection = destination$iv$iv2;
                                    boolean bl = false;
                                    object2 = new FromSourceBindingNamePair(new BindingName(it.getAlias().getAsName(), BindingCase.SENSITIVE), EvaluatingCompiler.access$exprValue(this.this$0.this$0, it.getAlias().getAsName()));
                                    collection.add(object2);
                                }
                                List fromSourceBindingNames = (List)destination$iv$iv2;
                                ExprNode exprNode = this.$having;
                                if (exprNode != null) {
                                    ExprNode $i$f$map22 = exprNode;
                                    boolean $this$mapTo$iv$iv2 = false;
                                    boolean destination$iv$iv2 = false;
                                    ExprNode it = $i$f$map22;
                                    boolean bl = false;
                                    function12 = EvaluatingCompiler.access$compileExprNode(this.this$0.this$0, it);
                                } else {
                                    function12 = null;
                                }
                                Function1 havingThunk = function12;
                                Function2 filterHavingAndProject = EvaluatingCompiler.access$createFilterHavingAndProjectClosure(this.this$0.this$0, havingThunk, selectProjectionThunk);
                                Function2 getGroupEnv = EvaluatingCompiler.access$createGetGroupEnvClosure(this.this$0.this$0, groupAsName);
                                destination$iv$iv2 = EvaluatingCompiler.access$getThunkFactory$p(this.this$0.this$0);
                                MetaContainer metas$iv3 = this.$metas;
                                boolean $i$f$thunkEnv$lang3 = false;
                                Meta meta = metas$iv3.find("$source_location");
                                if (!(meta instanceof SourceLocationMeta)) {
                                    meta = null;
                                }
                                SourceLocationMeta sourceLocationMeta$iv3 = (SourceLocationMeta)meta;
                                function1 = new Function1<Environment, ExprValue>((ThunkFactory)this_$iv3, sourceLocationMeta$iv3, this, groupKeyThunk, createRegisterBank2, (List)compiledAggregates, groupAsName, fromSourceBindingNames, getGroupEnv, filterHavingAndProject){
                                    final /* synthetic */ ThunkFactory this$0$inline_fun;
                                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                                    final /* synthetic */ compileSelect.1 this$0;
                                    final /* synthetic */ Function1 $groupKeyThunk$inlined;
                                    final /* synthetic */ Function0 $createRegisterBank$inlined;
                                    final /* synthetic */ List $compiledAggregates$inlined;
                                    final /* synthetic */ SymbolicName $groupAsName$inlined;
                                    final /* synthetic */ List $fromSourceBindingNames$inlined;
                                    final /* synthetic */ Function2 $getGroupEnv$inlined;
                                    final /* synthetic */ Function2 $filterHavingAndProject$inlined;
                                    {
                                        this.this$0$inline_fun = thunkFactory;
                                        this.$sourceLocationMeta = sourceLocationMeta;
                                        this.this$0 = var3_3;
                                        this.$groupKeyThunk$inlined = function1;
                                        this.$createRegisterBank$inlined = function0;
                                        this.$compiledAggregates$inlined = list;
                                        this.$groupAsName$inlined = symbolicName;
                                        this.$fromSourceBindingNames$inlined = list2;
                                        this.$getGroupEnv$inlined = function2;
                                        this.$filterHavingAndProject$inlined = function22;
                                        super(1);
                                    }

                                    /*
                                     * WARNING - void declaration
                                     */
                                    @NotNull
                                    public final ExprValue invoke(@NotNull Environment env) {
                                        ExprValue exprValue2;
                                        Intrinsics.checkParameterIsNotNull(env, "env");
                                        ThunkFactory thunkFactory = this.this$0$inline_fun;
                                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                        boolean $i$f$handleException$lang = false;
                                        try {
                                            void $this$mapNotNullTo$iv$iv;
                                            boolean bl;
                                            Object element$iv;
                                            Sequence fromProductions;
                                            boolean bl2 = false;
                                            Environment env2 = env;
                                            boolean bl3 = false;
                                            Sequence $this$forEach$iv = fromProductions = (Sequence)this.this$0.$sourceThunks.invoke(env2);
                                            boolean $i$f$forEach = false;
                                            Iterator<T> iterator2 = $this$forEach$iv.iterator();
                                            while (iterator2.hasNext()) {
                                                boolean bl4;
                                                Group group2;
                                                element$iv = iterator2.next();
                                                FromProduction fromProduction = (FromProduction)element$iv;
                                                boolean bl5 = false;
                                                ExprValue groupKey = (ExprValue)this.$groupKeyThunk$inlined.invoke(fromProduction.getEnv());
                                                Map<ExprValue, Group> $this$getOrPut$iv = env2.getGroups();
                                                boolean $i$f$getOrPut = false;
                                                Group value$iv = $this$getOrPut$iv.get(groupKey);
                                                if (value$iv == null) {
                                                    boolean bl6 = false;
                                                    Group answer$iv = new Group(groupKey, (RegisterBank)this.$createRegisterBank$inlined.invoke());
                                                    $this$getOrPut$iv.put(groupKey, answer$iv);
                                                    group2 = answer$iv;
                                                } else {
                                                    group2 = value$iv;
                                                }
                                                Group group3 = group2;
                                                List list = this.$compiledAggregates$inlined;
                                                if (list == null) {
                                                    Intrinsics.throwNpe();
                                                }
                                                Iterable $this$forEachIndexed$iv = list;
                                                boolean $i$f$forEachIndexed = false;
                                                int index$iv = 0;
                                                for (T item$iv : $this$forEachIndexed$iv) {
                                                    void ca;
                                                    int n = index$iv++;
                                                    bl4 = false;
                                                    if (n < 0) {
                                                        CollectionsKt.throwIndexOverflow();
                                                    }
                                                    int n2 = n;
                                                    CompiledAggregate compiledAggregate = (CompiledAggregate)item$iv;
                                                    int index = n2;
                                                    boolean bl7 = false;
                                                    group3.getRegisters().get(index).getAggregator().next(ca.getArgThunk().invoke(fromProduction.getEnv()));
                                                }
                                                $this$forEachIndexed$iv = this.$groupAsName$inlined;
                                                boolean bl8 = false;
                                                bl = false;
                                                Iterable $this$run = $this$forEachIndexed$iv;
                                                boolean bl9 = false;
                                                Sequence<R> sequence = SequencesKt.map(CollectionsKt.asSequence(this.$fromSourceBindingNames$inlined), (Function1)new Function1<FromSourceBindingNamePair, ExprValue>(fromProduction, group3, env2, this){
                                                    final /* synthetic */ FromProduction $fromProduction$inlined;
                                                    final /* synthetic */ Group $group$inlined;
                                                    final /* synthetic */ Environment $env$inlined;
                                                    final /* synthetic */ compileSelect$2$1$getQueryThunk$$inlined$thunkEnv$lang$3 this$0;
                                                    {
                                                        this.$fromProduction$inlined = fromProduction;
                                                        this.$group$inlined = group2;
                                                        this.$env$inlined = environment;
                                                        this.this$0 = var4_4;
                                                        super(1);
                                                    }

                                                    @NotNull
                                                    public final ExprValue invoke(@NotNull FromSourceBindingNamePair pair) {
                                                        Intrinsics.checkParameterIsNotNull(pair, "pair");
                                                        ExprValue exprValue2 = this.$fromProduction$inlined.getEnv().getCurrent().get(pair.getBindingName());
                                                        if (exprValue2 == null) {
                                                            Void void_ = ExceptionsKt.errNoContext("Could not resolve from source binding name during group as variable mapping", true);
                                                            throw null;
                                                        }
                                                        return ExprValueExtensionsKt.namedValue(exprValue2, pair.getNameExprValue());
                                                    }
                                                });
                                                bl4 = false;
                                                Sequence<R> seq2 = sequence;
                                                group3.getGroupValues().add(EvaluatingCompiler.access$createStructExprValue(this.this$0.this$0.this$0, seq2, StructOrdering.UNORDERED));
                                            }
                                            Map<ExprValue, Group> $this$mapNotNull$iv = env2.getGroups();
                                            boolean $i$f$mapNotNull = false;
                                            element$iv = $this$mapNotNull$iv;
                                            Collection destination$iv$iv = new ArrayList<E>();
                                            boolean $i$f$mapNotNullTo = false;
                                            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                                            boolean $i$f$forEach2 = false;
                                            void var17_22 = $this$forEach$iv$iv$iv;
                                            bl = false;
                                            Iterator<Map.Entry<K, V>> iterator3 = var17_22.entrySet().iterator();
                                            while (iterator3.hasNext()) {
                                                ExprValue exprValue3;
                                                Map.Entry<K, V> element$iv$iv$iv;
                                                Map.Entry<K, V> element$iv$iv = element$iv$iv$iv = iterator3.next();
                                                boolean bl10 = false;
                                                Map.Entry<K, V> g = element$iv$iv;
                                                boolean bl11 = false;
                                                Environment groupByEnv = (Environment)this.$getGroupEnv$inlined.invoke(env2, g.getValue());
                                                if ((ExprValue)this.$filterHavingAndProject$inlined.invoke(groupByEnv, g.getValue()) == null) continue;
                                                boolean bl12 = false;
                                                boolean bl13 = false;
                                                ExprValue it$iv$iv = exprValue3;
                                                boolean bl14 = false;
                                                destination$iv$iv.add(it$iv$iv);
                                            }
                                            Sequence<T> sequence = CollectionsKt.asSequence((List)destination$iv$iv);
                                            boolean bl15 = false;
                                            boolean bl16 = false;
                                            Sequence<T> rows = sequence;
                                            boolean bl17 = false;
                                            Function1 function1 = this.this$0.$limitThunk;
                                            Sequence<T> projectedRows = function1 == null ? rows : CollectionExtensionsKt.take(rows, EvaluatingCompiler.access$evalLimit(this.this$0.this$0.this$0, this.this$0.$limitThunk, env2, this.this$0.$limitLocationMeta));
                                            exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0).newBag(projectedRows);
                                        } catch (EvaluationException e$iv) {
                                            if (e$iv.getErrorContext() == null) {
                                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                            }
                                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                                if (sourceLocationMeta != null) {
                                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                                    boolean bl = false;
                                                    boolean bl18 = false;
                                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                                    boolean bl19 = false;
                                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                                }
                                            }
                                            throw (Throwable)e$iv;
                                        } catch (Exception e$iv) {
                                            void this_$iv;
                                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                            throw null;
                                        }
                                        return exprValue2;
                                    }
                                };
                            }
                        }
                        Function1 queryThunk2 = function1;
                        compiledAggregates = EvaluatingCompiler.access$getThunkFactory$p(this.this$0.this$0);
                        metas$iv = this.$metas;
                        $i$f$thunkEnv$lang = false;
                        Meta meta = metas$iv.find("$source_location");
                        if (!(meta instanceof SourceLocationMeta)) {
                            meta = null;
                        }
                        sourceLocationMeta$iv = (SourceLocationMeta)meta;
                        return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv, (SourceLocationMeta)sourceLocationMeta$iv, queryThunk2){
                            final /* synthetic */ ThunkFactory this$0;
                            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                            final /* synthetic */ Function1 $queryThunk$inlined;
                            {
                                this.this$0 = thunkFactory;
                                this.$sourceLocationMeta = sourceLocationMeta;
                                this.$queryThunk$inlined = function1;
                                super(1);
                            }

                            /*
                             * WARNING - void declaration
                             */
                            @NotNull
                            public final ExprValue invoke(@NotNull Environment env) {
                                ExprValue exprValue2;
                                Intrinsics.checkParameterIsNotNull(env, "env");
                                ThunkFactory thunkFactory = this.this$0;
                                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                boolean $i$f$handleException$lang = false;
                                try {
                                    boolean bl = false;
                                    Environment env2 = env;
                                    boolean bl2 = false;
                                    exprValue2 = (ExprValue)this.$queryThunk$inlined.invoke(env2.nestQuery$lang());
                                } catch (EvaluationException e$iv) {
                                    if (e$iv.getErrorContext() == null) {
                                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                    }
                                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                        if (sourceLocationMeta != null) {
                                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                            boolean bl = false;
                                            boolean bl3 = false;
                                            SourceLocationMeta it$iv = sourceLocationMeta2;
                                            boolean bl4 = false;
                                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                        }
                                    }
                                    throw (Throwable)e$iv;
                                } catch (Exception e$iv) {
                                    void this_$iv;
                                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                    throw null;
                                }
                                return exprValue2;
                            }
                        };
                    }
                    {
                        this.this$0 = var1_1;
                        this.$groupBy = groupBy2;
                        this.$metas = metaContainer;
                        this.$sourceThunks = function1;
                        this.$setQuantifier = setQuantifier;
                        this.$limitThunk = function12;
                        this.$limitLocationMeta = sourceLocationMeta;
                        this.$fromSourceThunks = list;
                        this.$having = exprNode;
                        super(1);
                    }
                };
                void var15_20 = projection;
                if (var15_20 instanceof SelectProjectionValue) {
                    SelectProjectionValue bl4 = (SelectProjectionValue)projection;
                    valueExpr = bl4.component1();
                    function1 = (Function1)EvaluatingCompiler.access$nestCompilationContext(this.this$0, ExpressionContext.NORMAL, this.$allFromSourceAliases, new Function0<Function1<? super Environment, ? extends ExprValue>>(this, valueExpr, $fun$getQueryThunk$1, metas){
                        final /* synthetic */ compileSelect.2 this$0;
                        final /* synthetic */ ExprNode $valueExpr;
                        final /* synthetic */ compileSelect.1 $getQueryThunk$1;
                        final /* synthetic */ MetaContainer $metas;

                        /*
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final Function1<Environment, ExprValue> invoke() {
                            void this_$iv;
                            void metas$iv;
                            Function1 valueThunk = EvaluatingCompiler.access$compileExprNode(this.this$0.this$0, this.$valueExpr);
                            ThunkFactory thunkFactory = EvaluatingCompiler.access$getThunkFactory$p(this.this$0.this$0);
                            MetaContainer metaContainer = this.$metas;
                            compileSelect.1 var6_4 = this.$getQueryThunk$1;
                            boolean $i$f$thunkEnvValue$lang = false;
                            Meta meta = metas$iv.find("$source_location");
                            if (!(meta instanceof SourceLocationMeta)) {
                                meta = null;
                            }
                            SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                            Function2 function2 = new Function2<Environment, List<? extends ExprValue>, ExprValue>((ThunkFactory)this_$iv, sourceLocationMeta$iv, valueThunk){
                                final /* synthetic */ ThunkFactory this$0;
                                final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                                final /* synthetic */ Function1 $valueThunk$inlined;
                                {
                                    this.this$0 = thunkFactory;
                                    this.$sourceLocationMeta = sourceLocationMeta;
                                    this.$valueThunk$inlined = function1;
                                    super(2);
                                }

                                /*
                                 * Ignored method signature, as it can't be verified against descriptor
                                 * WARNING - void declaration
                                 */
                                @NotNull
                                public final ExprValue invoke(@NotNull Environment env, Object arg1) {
                                    ExprValue exprValue2;
                                    Intrinsics.checkParameterIsNotNull(env, "env");
                                    ThunkFactory thunkFactory = this.this$0;
                                    SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                    boolean $i$f$handleException$lang = false;
                                    try {
                                        boolean bl = false;
                                        List list = (List)arg1;
                                        Environment env2 = env;
                                        boolean bl2 = false;
                                        exprValue2 = (ExprValue)this.$valueThunk$inlined.invoke(env2);
                                    } catch (EvaluationException e$iv) {
                                        if (e$iv.getErrorContext() == null) {
                                            throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                        }
                                        if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                            SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                            if (sourceLocationMeta != null) {
                                                SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                                boolean bl = false;
                                                boolean bl3 = false;
                                                SourceLocationMeta it$iv = sourceLocationMeta2;
                                                boolean bl4 = false;
                                                ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                            }
                                        }
                                        throw (Throwable)e$iv;
                                    } catch (Exception e$iv) {
                                        void this_$iv;
                                        R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                        throw null;
                                    }
                                    return exprValue2;
                                }
                            };
                            return var6_4.invoke(function2);
                        }
                        {
                            this.this$0 = var1_1;
                            this.$valueExpr = exprNode;
                            this.$getQueryThunk$1 = var3_3;
                            this.$metas = metaContainer;
                            super(0);
                        }
                    });
                } else if (var15_20 instanceof SelectProjectionPivot) {
                    void asExpr;
                    SelectProjectionPivot selectProjectionPivot = (SelectProjectionPivot)projection;
                    valueExpr = selectProjectionPivot.component1();
                    ExprNode atExpr = selectProjectionPivot.component2();
                    function1 = (Function1)EvaluatingCompiler.access$nestCompilationContext(this.this$0, ExpressionContext.NORMAL, this.$allFromSourceAliases, new Function0<Function1<? super Environment, ? extends ExprValue>>(this, (ExprNode)asExpr, atExpr, metas, sourceThunks, limitThunk, limitLocationMeta){
                        final /* synthetic */ compileSelect.2 this$0;
                        final /* synthetic */ ExprNode $asExpr;
                        final /* synthetic */ ExprNode $atExpr;
                        final /* synthetic */ MetaContainer $metas;
                        final /* synthetic */ Function1 $sourceThunks;
                        final /* synthetic */ Function1 $limitThunk;
                        final /* synthetic */ SourceLocationMeta $limitLocationMeta;

                        /*
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final Function1<Environment, ExprValue> invoke() {
                            void this_$iv;
                            Function1 asThunk = EvaluatingCompiler.access$compileExprNode(this.this$0.this$0, this.$asExpr);
                            Function1 atThunk = EvaluatingCompiler.access$compileExprNode(this.this$0.this$0, this.$atExpr);
                            ThunkFactory thunkFactory = EvaluatingCompiler.access$getThunkFactory$p(this.this$0.this$0);
                            MetaContainer metas$iv = this.$metas;
                            boolean $i$f$thunkEnv$lang = false;
                            Meta meta = metas$iv.find("$source_location");
                            if (!(meta instanceof SourceLocationMeta)) {
                                meta = null;
                            }
                            SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                            return new Function1<Environment, ExprValue>((ThunkFactory)this_$iv, sourceLocationMeta$iv, this, asThunk, atThunk){
                                final /* synthetic */ ThunkFactory this$0$inline_fun;
                                final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                                final /* synthetic */ compileSelect.3 this$0;
                                final /* synthetic */ Function1 $asThunk$inlined;
                                final /* synthetic */ Function1 $atThunk$inlined;
                                {
                                    this.this$0$inline_fun = thunkFactory;
                                    this.$sourceLocationMeta = sourceLocationMeta;
                                    this.this$0 = var3_3;
                                    this.$asThunk$inlined = function1;
                                    this.$atThunk$inlined = function12;
                                    super(1);
                                }

                                /*
                                 * WARNING - void declaration
                                 */
                                @NotNull
                                public final ExprValue invoke(@NotNull Environment env) {
                                    ExprValue exprValue2;
                                    Intrinsics.checkParameterIsNotNull(env, "env");
                                    ThunkFactory thunkFactory = this.this$0$inline_fun;
                                    SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                    boolean $i$f$handleException$lang = false;
                                    try {
                                        boolean bl = false;
                                        Environment env2 = env;
                                        boolean bl2 = false;
                                        Sequence<T> sequence = (Sequence<T>)this.this$0.$sourceThunks.invoke(env2);
                                        boolean bl3 = false;
                                        bl3 = false;
                                        boolean bl4 = false;
                                        Sequence<T> rows = sequence;
                                        boolean bl5 = false;
                                        Function1 function1 = this.this$0.$limitThunk;
                                        Sequence<T> sourceValue = function1 == null ? rows : CollectionExtensionsKt.take(rows, EvaluatingCompiler.access$evalLimit(this.this$0.this$0.this$0, this.this$0.$limitThunk, env2, this.this$0.$limitLocationMeta));
                                        Sequence<R> seq2 = SequencesKt.map(SequencesKt.filter(SequencesKt.map(sourceValue, (Function1)new Function1<FromProduction, Pair<? extends ExprValue, ? extends ExprValue>>(this){
                                            final /* synthetic */ compileSelect$2$3$$special$$inlined$thunkEnv$lang$1 this$0;
                                            {
                                                this.this$0 = var1_1;
                                                super(1);
                                            }

                                            @NotNull
                                            public final Pair<ExprValue, ExprValue> invoke(@NotNull FromProduction $dstr$_u24__u24$env) {
                                                Intrinsics.checkParameterIsNotNull($dstr$_u24__u24$env, "<name for destructuring parameter 0>");
                                                Environment env = $dstr$_u24__u24$env.component2();
                                                return new Pair<ExprValue, ExprValue>((ExprValue)this.this$0.$asThunk$inlined.invoke(env), (ExprValue)this.this$0.$atThunk$inlined.invoke(env));
                                            }
                                        }), compileSelect.1.seq.2.INSTANCE), compileSelect.1.seq.3.INSTANCE);
                                        exprValue2 = EvaluatingCompiler.access$createStructExprValue(this.this$0.this$0.this$0, seq2, StructOrdering.UNORDERED);
                                    } catch (EvaluationException e$iv) {
                                        if (e$iv.getErrorContext() == null) {
                                            throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                        }
                                        if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                            SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                            if (sourceLocationMeta != null) {
                                                SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                                boolean bl = false;
                                                boolean bl6 = false;
                                                SourceLocationMeta it$iv = sourceLocationMeta2;
                                                boolean bl7 = false;
                                                ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                            }
                                        }
                                        throw (Throwable)e$iv;
                                    } catch (Exception e$iv) {
                                        void this_$iv;
                                        R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                        throw null;
                                    }
                                    return exprValue2;
                                }
                            };
                        }
                        {
                            this.this$0 = var1_1;
                            this.$asExpr = exprNode;
                            this.$atExpr = exprNode2;
                            this.$metas = metaContainer;
                            this.$sourceThunks = function1;
                            this.$limitThunk = function12;
                            this.$limitLocationMeta = sourceLocationMeta;
                            super(0);
                        }
                    });
                } else if (var15_20 instanceof SelectProjectionList) {
                    SelectProjectionList selectProjectionList = (SelectProjectionList)projection;
                    List<SelectListItem> items = selectProjectionList.component1();
                    function1 = (Function1)EvaluatingCompiler.access$nestCompilationContext(this.this$0, ExpressionContext.SELECT_LIST, this.$allFromSourceAliases, new Function0<Function1<? super Environment, ? extends ExprValue>>(this, items, (SelectProjection)projection, metas, $fun$getQueryThunk$1){
                        final /* synthetic */ compileSelect.2 this$0;
                        final /* synthetic */ List $items;
                        final /* synthetic */ SelectProjection $projection;
                        final /* synthetic */ MetaContainer $metas;
                        final /* synthetic */ compileSelect.1 $getQueryThunk$1;

                        /*
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final Function1<Environment, ExprValue> invoke() {
                            void this_$iv;
                            boolean bl;
                            Object $this$none$iv;
                            List projectionElements;
                            block6: {
                                void $this$filterIsInstanceTo$iv$iv;
                                Iterable $this$filterIsInstance$iv = this.$items;
                                boolean $i$f$filterIsInstance = false;
                                Iterable iterable = $this$filterIsInstance$iv;
                                Collection destination$iv$iv = new ArrayList<E>();
                                boolean $i$f$filterIsInstanceTo2 = false;
                                for (T element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                                    if (!(element$iv$iv instanceof SelectListItemStar)) continue;
                                    destination$iv$iv.add(element$iv$iv);
                                }
                                if (CollectionsKt.any((List)destination$iv$iv)) {
                                    Void void_ = ExceptionsKt.errNoContext("Encountered a SelectListItemStar--did SelectStarVisitorTransform execute?", true);
                                    throw null;
                                }
                                projectionElements = EvaluatingCompiler.access$compileSelectListToProjectionElements(this.this$0.this$0, (SelectProjectionList)this.$projection);
                                $this$none$iv = ((SelectProjectionList)this.$projection).getItems();
                                boolean $i$f$none = false;
                                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                                    bl = true;
                                } else {
                                    Iterator<T> $i$f$filterIsInstanceTo2 = $this$none$iv.iterator();
                                    while ($i$f$filterIsInstanceTo2.hasNext()) {
                                        T element$iv = $i$f$filterIsInstanceTo2.next();
                                        SelectListItem it = (SelectListItem)element$iv;
                                        boolean bl2 = false;
                                        if (!(it instanceof SelectListItemProjectAll)) continue;
                                        bl = false;
                                        break block6;
                                    }
                                    bl = true;
                                }
                            }
                            StructOrdering ordering = bl ? StructOrdering.ORDERED : StructOrdering.UNORDERED;
                            $this$none$iv = EvaluatingCompiler.access$getThunkFactory$p(this.this$0.this$0);
                            MetaContainer metas$iv = this.$metas;
                            boolean $i$f$thunkEnvValue$lang = false;
                            Meta meta = metas$iv.find("$source_location");
                            if (!(meta instanceof SourceLocationMeta)) {
                                meta = null;
                            }
                            SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                            Function2 projectionThunk = new Function2<Environment, List<? extends ExprValue>, ExprValue>((ThunkFactory)this_$iv, sourceLocationMeta$iv, this, projectionElements, ordering){
                                final /* synthetic */ ThunkFactory this$0$inline_fun;
                                final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                                final /* synthetic */ compileSelect.4 this$0;
                                final /* synthetic */ List $projectionElements$inlined;
                                final /* synthetic */ StructOrdering $ordering$inlined;
                                {
                                    this.this$0$inline_fun = thunkFactory;
                                    this.$sourceLocationMeta = sourceLocationMeta;
                                    this.this$0 = var3_3;
                                    this.$projectionElements$inlined = list;
                                    this.$ordering$inlined = structOrdering;
                                    super(2);
                                }

                                /*
                                 * Ignored method signature, as it can't be verified against descriptor
                                 * WARNING - void declaration
                                 */
                                @NotNull
                                public final ExprValue invoke(@NotNull Environment env, Object arg1) {
                                    ExprValue exprValue2;
                                    Intrinsics.checkParameterIsNotNull(env, "env");
                                    ThunkFactory thunkFactory = this.this$0$inline_fun;
                                    SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                    boolean $i$f$handleException$lang = false;
                                    try {
                                        boolean bl = false;
                                        List list = (List)arg1;
                                        Environment env2 = env;
                                        boolean bl2 = false;
                                        boolean bl3 = false;
                                        List columns = new ArrayList<E>();
                                        for (ProjectionElement element : this.$projectionElements$inlined) {
                                            ProjectionElement projectionElement = element;
                                            if (projectionElement instanceof SingleProjectionElement) {
                                                ExprValue eval = ((SingleProjectionElement)element).getThunk().invoke(env2);
                                                columns.add(ExprValueExtensionsKt.namedValue(eval, ((SingleProjectionElement)element).getName()));
                                                continue;
                                            }
                                            if (!(projectionElement instanceof MultipleProjectionElement)) continue;
                                            for (Function1<Environment, ExprValue> projThunk : ((MultipleProjectionElement)element).getThunks()) {
                                                Iterable iterable;
                                                ExprValue value = projThunk.invoke(env2);
                                                if (value.getType() == ExprValueType.MISSING) continue;
                                                Sequence<T> children = CollectionsKt.asSequence(value);
                                                if (!SequencesKt.any(children) || value.getType().isSequence()) {
                                                    ExprValue name = EvaluatingCompiler.access$exprValue(this.this$0.this$0.this$0, StandardNamesKt.syntheticColumnName(columns.size()));
                                                    columns.add(ExprValueExtensionsKt.namedValue(value, name));
                                                    continue;
                                                }
                                                switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$8[EvaluatingCompiler.access$getCompileOptions$p(this.this$0.this$0.this$0).getProjectionIteration().ordinal()]) {
                                                    case 1: {
                                                        void $this$filterTo$iv$iv;
                                                        Iterable $this$filter$iv = value;
                                                        boolean $i$f$filter = false;
                                                        Iterable iterable2 = $this$filter$iv;
                                                        Collection destination$iv$iv = new ArrayList<E>();
                                                        boolean $i$f$filterTo = false;
                                                        for (T element$iv$iv : $this$filterTo$iv$iv) {
                                                            ExprValue it = (ExprValue)element$iv$iv;
                                                            boolean bl4 = false;
                                                            if (!(it.getType() != ExprValueType.MISSING)) continue;
                                                            destination$iv$iv.add(element$iv$iv);
                                                        }
                                                        iterable = (List)destination$iv$iv;
                                                        break;
                                                    }
                                                    case 2: {
                                                        iterable = value;
                                                        break;
                                                    }
                                                    default: {
                                                        throw new NoWhenBranchMatchedException();
                                                    }
                                                }
                                                Iterable valuesToProject = iterable;
                                                for (ExprValue childValue : valuesToProject) {
                                                    Named namedFacet = childValue.asFacet(Named.class);
                                                    Object object = namedFacet;
                                                    if (object == null || (object = object.getName()) == null) {
                                                        object = EvaluatingCompiler.access$exprValue(this.this$0.this$0.this$0, StandardNamesKt.syntheticColumnName(columns.size()));
                                                    }
                                                    Object name = object;
                                                    columns.add(ExprValueExtensionsKt.namedValue(childValue, (ExprValue)name));
                                                }
                                            }
                                        }
                                        exprValue2 = EvaluatingCompiler.access$createStructExprValue(this.this$0.this$0.this$0, CollectionsKt.asSequence(columns), this.$ordering$inlined);
                                    } catch (EvaluationException e$iv) {
                                        if (e$iv.getErrorContext() == null) {
                                            throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                        }
                                        if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                            SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                            if (sourceLocationMeta != null) {
                                                SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                                boolean bl = false;
                                                boolean bl5 = false;
                                                SourceLocationMeta it$iv = sourceLocationMeta2;
                                                boolean bl6 = false;
                                                ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                            }
                                        }
                                        throw (Throwable)e$iv;
                                    } catch (Exception e$iv) {
                                        void this_$iv;
                                        R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                        throw null;
                                    }
                                    return exprValue2;
                                }
                            };
                            return this.$getQueryThunk$1.invoke(projectionThunk);
                        }
                        {
                            this.this$0 = var1_1;
                            this.$items = list;
                            this.$projection = selectProjection;
                            this.$metas = metaContainer;
                            this.$getQueryThunk$1 = var5_5;
                            super(0);
                        }
                    });
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                return function1;
            }
            {
                this.this$0 = evaluatingCompiler;
                this.$selectExpr = select;
                this.$allFromSourceAliases = set2;
                super(0);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    private final List<CompiledGroupByItem> compileGroupByExpressions(List<GroupByItem> groupByItems) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = groupByItems;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            SymbolicName alias;
            void it;
            GroupByItem groupByItem = (GroupByItem)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            if (it.getAsName() == null) {
                Void void_ = ExceptionsKt.errNoContext("GroupByItem.asName was not specified", true);
                throw null;
            }
            UniqueNameMeta uniqueNameMeta = (UniqueNameMeta)alias.getMetas().find("$unique_name");
            String uniqueName = uniqueNameMeta != null ? uniqueNameMeta.getUniqueName() : null;
            CompiledGroupByItem compiledGroupByItem = new CompiledGroupByItem(this.exprValue(alias.getName()), uniqueName, this.compileExprNode(it.getExpr()));
            collection.add(compiledGroupByItem);
        }
        return (List)destination$iv$iv;
    }

    private final Function1<Environment, ExprValue> compileGroupKeyThunk(List<CompiledGroupByItem> compiledGroupByItems, MetaContainer selectMetas) {
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = selectMetas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, compiledGroupByItems){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ List $compiledGroupByItems$inlined;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.this$0 = evaluatingCompiler;
                this.$compiledGroupByItems$inlined = list;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    void $this$mapTo$iv$iv;
                    boolean bl = false;
                    Environment env2 = env;
                    boolean bl2 = false;
                    HashMap<K, V> uniqueNames = new HashMap<K, V>(this.$compiledGroupByItems$inlined.size(), 1.0f);
                    Iterable $this$map$iv = this.$compiledGroupByItems$inlined;
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void cgbi;
                        CompiledGroupByItem compiledGroupByItem = (CompiledGroupByItem)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl3 = false;
                        ExprValue value = ExprValueExtensionsKt.namedValue(cgbi.getThunk().invoke(env2), cgbi.getAlias());
                        if (cgbi.getUniqueId() != null) {
                            ((Map)uniqueNames).put(cgbi.getUniqueId(), value);
                        }
                        ExprValue exprValue3 = value;
                        collection.add(exprValue3);
                    }
                    List keyValues = (List)destination$iv$iv;
                    exprValue2 = new GroupKeyExprValue(EvaluatingCompiler.access$getValueFactory$p(this.this$0).getIon(), CollectionsKt.asSequence(keyValues), (Map<String, ? extends ExprValue>)uniqueNames);
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl4 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl5 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    private final Function2<Environment, Group, Environment> createGetGroupEnvClosure(SymbolicName groupAsName) {
        return groupAsName != null ? (Function2)new Function2<Environment, Group, Environment>(this, groupAsName){
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ SymbolicName $groupAsName;

            @NotNull
            public final Environment invoke(@NotNull Environment groupByEnv, @NotNull Group currentGroup) {
                Intrinsics.checkParameterIsNotNull(groupByEnv, "groupByEnv");
                Intrinsics.checkParameterIsNotNull(currentGroup, "currentGroup");
                Bindings<T> groupAsBindings2 = Bindings.Companion.buildLazyBindings((Function1)new Function1<Bindings.LazyBindingBuilder<ExprValue>, Unit>(this, currentGroup){
                    final /* synthetic */ createGetGroupEnvClosure.1 this$0;
                    final /* synthetic */ Group $currentGroup;

                    public final void invoke(@NotNull Bindings.LazyBindingBuilder<ExprValue> $this$buildLazyBindings) {
                        Intrinsics.checkParameterIsNotNull($this$buildLazyBindings, "$receiver");
                        $this$buildLazyBindings.addBinding(this.this$0.$groupAsName.getName(), new Function0<ExprValue>(this){
                            final /* synthetic */ createGetGroupEnvClosure.groupAsBindings.1 this$0;

                            @NotNull
                            public final ExprValue invoke() {
                                return EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0).newBag(CollectionsKt.asSequence((Iterable)this.this$0.$currentGroup.getGroupValues()));
                            }
                            {
                                this.this$0 = var1_1;
                                super(0);
                            }
                        });
                    }
                    {
                        this.this$0 = var1_1;
                        this.$currentGroup = group2;
                        super(1);
                    }
                });
                return Environment.nest$lang$default(Environment.nest$lang$default(groupByEnv, currentGroup.getKey().getBindings(), null, currentGroup, 2, null), groupAsBindings2, null, null, 6, null);
            }
            {
                this.this$0 = evaluatingCompiler;
                this.$groupAsName = symbolicName;
                super(2);
            }
        } : (Function2)createGetGroupEnvClosure.2.INSTANCE;
    }

    private final Function2<Environment, Group, ExprValue> createFilterHavingAndProjectClosure(Function1<? super Environment, ? extends ExprValue> havingThunk, Function2<? super Environment, ? super List<? extends ExprValue>, ? extends ExprValue> selectProjectionThunk) {
        return havingThunk != null ? (Function2)new Function2<Environment, Group, ExprValue>(havingThunk, selectProjectionThunk){
            final /* synthetic */ Function1 $havingThunk;
            final /* synthetic */ Function2 $selectProjectionThunk;

            @Nullable
            public final ExprValue invoke(@NotNull Environment groupByEnv, @NotNull Group currentGroup) {
                Intrinsics.checkParameterIsNotNull(groupByEnv, "groupByEnv");
                Intrinsics.checkParameterIsNotNull(currentGroup, "currentGroup");
                ExprValue havingClauseResult = (ExprValue)this.$havingThunk.invoke(groupByEnv);
                return ExprValueExtensionsKt.isNotUnknown(havingClauseResult) && ExprValueExtensionsKt.booleanValue(havingClauseResult) ? (ExprValue)this.$selectProjectionThunk.invoke(groupByEnv, CollectionsKt.listOf(currentGroup.getKey())) : null;
            }
            {
                this.$havingThunk = function1;
                this.$selectProjectionThunk = function2;
                super(2);
            }
        } : (Function2)new Function2<Environment, Group, ExprValue>(selectProjectionThunk){
            final /* synthetic */ Function2 $selectProjectionThunk;

            @NotNull
            public final ExprValue invoke(@NotNull Environment groupByEnv, @NotNull Group currentGroup) {
                Intrinsics.checkParameterIsNotNull(groupByEnv, "groupByEnv");
                Intrinsics.checkParameterIsNotNull(currentGroup, "currentGroup");
                return (ExprValue)this.$selectProjectionThunk.invoke(groupByEnv, CollectionsKt.listOf(currentGroup.getKey()));
            }
            {
                this.$selectProjectionThunk = function2;
                super(2);
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileCallAgg(CallAgg expr) {
        Function1 function1;
        void argExpr;
        void setQuantifier;
        void funcExpr;
        CallAgg callAgg = expr;
        ExprNode exprNode = callAgg.component1();
        SetQuantifier setQuantifier2 = callAgg.component2();
        ExprNode exprNode2 = callAgg.component3();
        MetaContainer metas = callAgg.component4();
        if (metas.hasMeta("$is_count_star") && this.getCurrentCompilationContext().getExpressionContext() != ExpressionContext.SELECT_LIST) {
            Void void_ = ExceptionsKt.err("COUNT(*) is not allowed in this context", ExceptionsKt.errorContextFrom(metas), false);
            throw null;
        }
        void v1 = funcExpr;
        if (v1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.ast.VariableReference");
        }
        VariableReference funcVarRef = (VariableReference)v1;
        String string = funcVarRef.getId();
        EvaluatingCompiler evaluatingCompiler = this;
        boolean bl = false;
        String string2 = string;
        if (string2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String string3 = string2.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
        String string4 = string3;
        ExprAggregatorFactory aggFactory = evaluatingCompiler.getAggregatorFactory(string4, (SetQuantifier)setQuantifier, metas);
        Function1 argThunk2 = (Function1)this.nestCompilationContext(ExpressionContext.AGG_ARG, SetsKt.emptySet(), (Function0)new Function0<Function1<? super Environment, ? extends ExprValue>>(this, (ExprNode)argExpr){
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ ExprNode $argExpr;

            @NotNull
            public final Function1<Environment, ExprValue> invoke() {
                return EvaluatingCompiler.access$compileExprNode(this.this$0, this.$argExpr);
            }
            {
                this.this$0 = evaluatingCompiler;
                this.$argExpr = exprNode;
                super(0);
            }
        });
        switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$9[this.getCurrentCompilationContext().getExpressionContext().ordinal()]) {
            case 1: {
                Void void_ = ExceptionsKt.err("The arguments of an aggregate function cannot contain aggregate functions", ExceptionsKt.errorContextFrom(metas), false);
                throw null;
            }
            case 2: {
                ThunkFactory this_$iv = this.thunkFactory;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = metas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, aggFactory, argThunk2){
                    final /* synthetic */ ThunkFactory this$0;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ ExprAggregatorFactory $aggFactory$inlined;
                    final /* synthetic */ Function1 $argThunk$inlined;
                    {
                        this.this$0 = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.$aggFactory$inlined = exprAggregatorFactory;
                        this.$argThunk$inlined = function1;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            boolean bl = false;
                            Environment env2 = env;
                            boolean bl2 = false;
                            ExprAggregator aggregator = this.$aggFactory$inlined.create();
                            ExprValue argValue = (ExprValue)this.$argThunk$inlined.invoke(env2);
                            Iterable $this$forEach$iv = argValue;
                            boolean $i$f$forEach = false;
                            for (T element$iv : $this$forEach$iv) {
                                ExprValue it = (ExprValue)element$iv;
                                boolean bl3 = false;
                                aggregator.next(it);
                            }
                            exprValue2 = aggregator.compute();
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl4 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl5 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
                break;
            }
            case 3: {
                Meta meta = metas.find("$aggregate_register_id");
                if (meta == null) {
                    throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.ast.AggregateRegisterIdMeta");
                }
                AggregateRegisterIdMeta registerIdMeta = (AggregateRegisterIdMeta)meta;
                int registerId = registerIdMeta.getRegisterId();
                ThunkFactory this_$iv = this.thunkFactory;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta2 = metas.find("$source_location");
                if (!(meta2 instanceof SourceLocationMeta)) {
                    meta2 = null;
                }
                SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta2;
                function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, metas, registerId){
                    final /* synthetic */ ThunkFactory this$0;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ MetaContainer $metas$inlined;
                    final /* synthetic */ int $registerId$inlined;
                    {
                        this.this$0 = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.$metas$inlined = metaContainer;
                        this.$registerId$inlined = n;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            boolean bl = false;
                            Environment env2 = env;
                            boolean bl2 = false;
                            Object object = env2.getCurrentGroup();
                            if (object == null || (object = ((Group)object).getRegisters()) == null) {
                                Void void_ = ExceptionsKt.err("No current group or current group has no registers", ExceptionsKt.errorContextFrom(this.$metas$inlined), true);
                                throw null;
                            }
                            Object registers = object;
                            exprValue2 = ((RegisterBank)registers).get(this.$registerId$inlined).getAggregator().compute();
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl3 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl4 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return function1;
    }

    @NotNull
    public final ExprAggregatorFactory getAggregatorFactory(@NotNull String funcName, @NotNull SetQuantifier setQuantifier, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(funcName, "funcName");
        Intrinsics.checkParameterIsNotNull((Object)setQuantifier, "setQuantifier");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        Object object = funcName;
        boolean bl = false;
        String string = ((String)object).toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(string, "(this as java.lang.String).toLowerCase()");
        Pair<String, SetQuantifier> key = TuplesKt.to(string, setQuantifier);
        ExprAggregatorFactory exprAggregatorFactory = this.builtinAggregates.get(key);
        if (exprAggregatorFactory == null) {
            object = ExceptionsKt.errorContextFrom(metas);
            ErrorCode errorCode = ErrorCode.EVALUATOR_NO_SUCH_FUNCTION;
            String string2 = "No such function: " + funcName;
            bl = false;
            boolean bl2 = false;
            Object it = object;
            boolean bl3 = false;
            ((PropertyValueMap)it).set(Property.FUNCTION_NAME, funcName);
            Object object2 = object;
            Void void_ = ExceptionsKt.err(string2, errorCode, (PropertyValueMap)object2, false);
            throw null;
        }
        return exprAggregatorFactory;
    }

    /*
     * WARNING - void declaration
     */
    private final List<CompiledFromSource> compileFromSources(FromSource fromSource, List<CompiledFromSource> sources, JoinExpansion joinExpansion, Function1<? super Environment, ? extends ExprValue> conditionThunk) {
        WhenAsExpressionHelper whenAsExpressionHelper;
        MetaContainer metas = fromSource.metas();
        FromSource fromSource2 = fromSource;
        if (fromSource2 instanceof FromSourceLet) {
            Function1 function1;
            boolean $i$f$case = false;
            boolean bl = false;
            FromSource fromSource3 = fromSource;
            if (fromSource3 instanceof FromSourceExpr) {
                function1 = this.compileExprNode(((FromSourceLet)fromSource).getExpr());
            } else if (fromSource3 instanceof FromSourceUnpivot) {
                void this_$iv;
                Function1<Environment, ExprValue> valueThunk = this.compileExprNode(((FromSourceLet)fromSource).getExpr());
                ThunkFactory thunkFactory = this.thunkFactory;
                MetaContainer metas$iv = metas;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = metas$iv.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                function1 = new Function1<Environment, ExprValue>((ThunkFactory)this_$iv, sourceLocationMeta$iv, valueThunk, this, fromSource, metas, sources, joinExpansion, conditionThunk){
                    final /* synthetic */ ThunkFactory this$0$inline_fun;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ Function1 $valueThunk$inlined;
                    final /* synthetic */ EvaluatingCompiler this$0;
                    final /* synthetic */ FromSource $fromSource$inlined;
                    final /* synthetic */ MetaContainer $metas$inlined;
                    final /* synthetic */ List $sources$inlined;
                    final /* synthetic */ JoinExpansion $joinExpansion$inlined;
                    final /* synthetic */ Function1 $conditionThunk$inlined;
                    {
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.$valueThunk$inlined = function1;
                        this.this$0 = evaluatingCompiler;
                        this.$fromSource$inlined = fromSource;
                        this.$metas$inlined = metaContainer;
                        this.$sources$inlined = list;
                        this.$joinExpansion$inlined = joinExpansion;
                        this.$conditionThunk$inlined = function12;
                        this.this$0$inline_fun = thunkFactory;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0$inline_fun;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            boolean bl = false;
                            Environment env2 = env;
                            boolean bl2 = false;
                            exprValue2 = this.this$0.unpivot$lang((ExprValue)this.$valueThunk$inlined.invoke(env2));
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl3 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl4 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
            } else {
                throw new NoWhenBranchMatchedException();
            }
            Function1 thunk2 = function1;
            Object object = ((FromSourceLet)fromSource).getVariables().getAsName();
            if (object == null || (object = ((SymbolicName)object).getName()) == null) {
                Void void_ = ExceptionsKt.err("FromSourceExpr.variables.asName was null", ExceptionsKt.errorContextFrom(((FromSourceLet)fromSource).getExpr().getMetas()), true);
                throw null;
            }
            SymbolicName symbolicName = ((FromSourceLet)fromSource).getVariables().getAtName();
            SymbolicName symbolicName2 = ((FromSourceLet)fromSource).getVariables().getByName();
            sources.add(new CompiledFromSource(new Alias((String)object, symbolicName != null ? symbolicName.getName() : null, symbolicName2 != null ? symbolicName2.getName() : null), thunk2, joinExpansion, conditionThunk));
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (fromSource2 instanceof FromSourceJoin) {
            void right;
            JoinExpansion joinExpansion2;
            void joinOp;
            void left;
            boolean $i$f$case = false;
            boolean bl = false;
            FromSourceJoin fromSourceJoin = (FromSourceJoin)fromSource;
            JoinOp valueThunk = fromSourceJoin.component1();
            FromSource this_$iv = fromSourceJoin.component2();
            FromSource metas$iv = fromSourceJoin.component3();
            ExprNode condition = fromSourceJoin.component4();
            List leftSources = EvaluatingCompiler.compileFromSources$default(this, (FromSource)left, null, null, null, 14, null);
            sources.addAll(leftSources);
            switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$10[joinOp.ordinal()]) {
                case 1: {
                    joinExpansion2 = JoinExpansion.INNER;
                    break;
                }
                case 2: {
                    joinExpansion2 = JoinExpansion.OUTER;
                    break;
                }
                case 3: 
                case 4: {
                    PropertyValueMap sourceLocationMeta$iv = ExceptionsKt.errorContextFrom(metas);
                    ErrorCode errorCode = ErrorCode.EVALUATOR_FEATURE_NOT_SUPPORTED_YET;
                    String string = "RIGHT and FULL JOIN not supported";
                    boolean bl2 = false;
                    boolean bl3 = false;
                    PropertyValueMap it = sourceLocationMeta$iv;
                    boolean bl4 = false;
                    it.set(Property.FEATURE_NAME, "RIGHT and FULL JOIN");
                    PropertyValueMap propertyValueMap = sourceLocationMeta$iv;
                    Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap, false);
                    throw null;
                }
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            JoinExpansion joinExpansionInner = joinExpansion2;
            Function1<Environment, ExprValue> conditionThunkInner = this.compileExprNode(condition);
            this.compileFromSources((FromSource)right, sources, joinExpansionInner, conditionThunkInner);
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        whenAsExpressionHelper.toUnit();
        return sources;
    }

    static /* synthetic */ List compileFromSources$default(EvaluatingCompiler evaluatingCompiler, FromSource fromSource, List list, JoinExpansion joinExpansion, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            list = new ArrayList();
        }
        if ((n & 4) != 0) {
            joinExpansion = JoinExpansion.INNER;
        }
        if ((n & 8) != 0) {
            function1 = null;
        }
        return evaluatingCompiler.compileFromSources(fromSource, list, joinExpansion, function1);
    }

    /*
     * WARNING - void declaration
     */
    private final List<CompiledLetSource> compileLetSources(LetSource letSource) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = letSource.getBindings();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            LetBinding letBinding = (LetBinding)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            CompiledLetSource compiledLetSource = new CompiledLetSource(it.getName().getName(), this.compileExprNode(it.getExpr()));
            collection.add(compiledLetSource);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, Sequence<FromProduction>> compileQueryWithoutProjection(Select ast, List<CompiledFromSource> compiledSources, List<CompiledLetSource> compiledLetSources) {
        Function1<Environment, ExprValue> function1;
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = compiledSources;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            CompiledFromSource compiledFromSource = (CompiledFromSource)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Alias alias = it.getAlias();
            collection.add(alias);
        }
        LocalsBinder localsBinder2 = LocalsBinderKt.localsBinder((List)destination$iv$iv, this.valueFactory.getMissingValue());
        ExprNode exprNode = ast.getWhere();
        if (exprNode != null) {
            ExprNode exprNode2 = exprNode;
            boolean bl = false;
            boolean bl2 = false;
            ExprNode it = exprNode2;
            boolean bl3 = false;
            function1 = this.compileExprNode(it);
        } else {
            function1 = null;
        }
        Function1<Environment, ExprValue> whereThunk = function1;
        return new Function1<Environment, Sequence<? extends FromProduction>>(this, compiledSources, localsBinder2, compiledLetSources, whereThunk){
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ List $compiledSources;
            final /* synthetic */ LocalsBinder $localsBinder;
            final /* synthetic */ List $compiledLetSources;
            final /* synthetic */ Function1 $whereThunk;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Sequence<FromProduction> invoke(@NotNull Environment rootEnv) {
                void var3_6;
                void $this$foldLeftProduct$iv;
                Intrinsics.checkParameterIsNotNull(rootEnv, "rootEnv");
                Environment fromEnv = rootEnv.flipToGlobalsFirst$lang();
                List list = this.$compiledSources;
                compileQueryWithoutProjection.seq.1 initialContext$iv = compileQueryWithoutProjection.seq.1.INSTANCE;
                boolean $i$f$foldLeftProduct = false;
                Sequence<R> seq2 = SequencesKt.map(CollectionsKt.asSequence((Iterable)new Iterable<List<? extends ExprValue>>((List)$this$foldLeftProduct$iv, (Object)initialContext$iv, this, fromEnv, rootEnv){
                    final /* synthetic */ List $this_foldLeftProduct;
                    final /* synthetic */ Object $initialContext;
                    final /* synthetic */ compileQueryWithoutProjection.1 this$0;
                    final /* synthetic */ Environment $fromEnv$inlined;
                    final /* synthetic */ Environment $rootEnv$inlined;
                    {
                        this.$this_foldLeftProduct = $receiver;
                        this.$initialContext = $captured_local_variable$2;
                        this.this$0 = var3_3;
                        this.$fromEnv$inlined = environment;
                        this.$rootEnv$inlined = environment2;
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public Iterator<List<ExprValue>> iterator() {
                        void bindEnv;
                        void source;
                        void var3_3;
                        void $this$mapTo$iv;
                        List sources = this.$this_foldLeftProduct;
                        Iterable iterable = sources;
                        Collection destination$iv = new ArrayList<E>();
                        boolean $i$f$mapTo = false;
                        Iterator<T> iterator2 = $this$mapTo$iv.iterator();
                        while (iterator2.hasNext()) {
                            T item$iv;
                            T t = item$iv = iterator2.next();
                            Collection collection = destination$iv;
                            boolean bl = false;
                            Iterator<T> iterator3 = Collections.emptyList().iterator();
                            collection.add(iterator3);
                        }
                        List iterators = (List)var3_3;
                        CompiledFromSource compiledFromSource = (CompiledFromSource)sources.get(0);
                        Function1 function1 = (Function1)this.$initialContext;
                        int n = 0;
                        List list = iterators;
                        boolean bl = false;
                        Function1<ExprValue, Pair<? extends Function1<? super Environment, ? extends Environment>, ? extends ExprValue>> $fun$correlatedBind$1 = new Function1<ExprValue, Pair<? extends Function1<? super Environment, ? extends Environment>, ? extends ExprValue>>((CompiledFromSource)source, (Function1)bindEnv, this){
                            final /* synthetic */ CompiledFromSource $source;
                            final /* synthetic */ Function1 $bindEnv;
                            final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1 this$0;
                            {
                                this.$source = compiledFromSource;
                                this.$bindEnv = function1;
                                this.this$0 = var3_3;
                                super(1);
                            }

                            @NotNull
                            public final Pair<Function1<Environment, Environment>, ExprValue> invoke(@NotNull ExprValue value) {
                                Intrinsics.checkParameterIsNotNull(value, "value");
                                Alias alias = this.$source.getAlias();
                                Function1 nextBindEnv = new Function1<Environment, Environment>(this, alias, value){
                                    final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$lambda$1 this$0;
                                    final /* synthetic */ Alias $alias;
                                    final /* synthetic */ ExprValue $value;
                                    {
                                        this.this$0 = var1_1;
                                        this.$alias = alias;
                                        this.$value = exprValue2;
                                        super(1);
                                    }

                                    @NotNull
                                    public final Environment invoke(@NotNull Environment env) {
                                        Intrinsics.checkParameterIsNotNull(env, "env");
                                        Environment childEnv = (Environment)this.this$0.$bindEnv.invoke(env);
                                        return Environment.nest$lang$default(childEnv, Bindings.Companion.buildLazyBindings((Function1)new Function1<Bindings.LazyBindingBuilder<ExprValue>, Unit>(this){
                                            final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$lambda$1$1 this$0;
                                            {
                                                this.this$0 = var1_1;
                                                super(1);
                                            }

                                            public final void invoke(@NotNull Bindings.LazyBindingBuilder<ExprValue> $this$buildLazyBindings) {
                                                Intrinsics.checkParameterIsNotNull($this$buildLazyBindings, "$receiver");
                                                $this$buildLazyBindings.addBinding(this.this$0.$alias.getAsName(), new Function0<ExprValue>(this){
                                                    final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$lambda$1$1$1 this$0;
                                                    {
                                                        this.this$0 = var1_1;
                                                        super(0);
                                                    }

                                                    @NotNull
                                                    public final ExprValue invoke() {
                                                        return this.this$0.this$0.$value;
                                                    }
                                                });
                                                if (this.this$0.$alias.getAtName() != null) {
                                                    $this$buildLazyBindings.addBinding(this.this$0.$alias.getAtName(), new Function0<ExprValue>(this){
                                                        final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$lambda$1$1$1 this$0;
                                                        {
                                                            this.this$0 = var1_1;
                                                            super(0);
                                                        }

                                                        @NotNull
                                                        public final ExprValue invoke() {
                                                            ExprValue exprValue2 = ExprValueExtensionsKt.getName(this.this$0.this$0.$value);
                                                            if (exprValue2 == null) {
                                                                exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0.this$0.this$0.this$0).getMissingValue();
                                                            }
                                                            return exprValue2;
                                                        }
                                                    });
                                                }
                                                if (this.this$0.$alias.getByName() != null) {
                                                    $this$buildLazyBindings.addBinding(this.this$0.$alias.getByName(), new Function0<ExprValue>(this){
                                                        final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$lambda$1$1$1 this$0;
                                                        {
                                                            this.this$0 = var1_1;
                                                            super(0);
                                                        }

                                                        @NotNull
                                                        public final ExprValue invoke() {
                                                            ExprValue exprValue2 = ExprValueExtensionsKt.getAddress(this.this$0.this$0.$value);
                                                            if (exprValue2 == null) {
                                                                exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0.this$0.this$0.this$0).getMissingValue();
                                                            }
                                                            return exprValue2;
                                                        }
                                                    });
                                                }
                                            }
                                        }), Environment.CurrentMode.GLOBALS_THEN_LOCALS, null, 4, null);
                                    }
                                };
                                return new Pair<Function1<Environment, Environment>, ExprValue>(nextBindEnv, value);
                            }
                        };
                        Iterator<R> iter = SequencesKt.map(CollectionsKt.asSequence(ExprValueExtensionsKt.rangeOver(source.getThunk().invoke((Environment)bindEnv.invoke(this.$fromEnv$inlined)))), (Function1)new Function1<ExprValue, Pair<? extends Function1<? super Environment, ? extends Environment>, ? extends ExprValue>>($fun$correlatedBind$1){
                            final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$lambda$1 $correlatedBind$1;
                            {
                                this.$correlatedBind$1 = var1_1;
                                super(1);
                            }

                            @NotNull
                            public final Pair<Function1<Environment, Environment>, ExprValue> invoke(@NotNull ExprValue it) {
                                Intrinsics.checkParameterIsNotNull(it, "it");
                                return this.$correlatedBind$1.invoke(it);
                            }
                        }).iterator();
                        Function1<Environment, ExprValue> filter = source.getFilter();
                        if (filter != null) {
                            iter = SequencesKt.filter(SequencesKt.asSequence(iter), (Function1)new Function1<Pair<? extends Function1<? super Environment, ? extends Environment>, ? extends ExprValue>, Boolean>(filter, this){
                                final /* synthetic */ Function1 $filter;
                                final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1 this$0;
                                {
                                    this.$filter = function1;
                                    this.this$0 = var2_2;
                                    super(1);
                                }

                                public final boolean invoke(@NotNull Pair<? extends Function1<? super Environment, Environment>, ? extends ExprValue> $dstr$bindEnv$_u24__u24) {
                                    Intrinsics.checkParameterIsNotNull($dstr$bindEnv$_u24__u24, "<name for destructuring parameter 0>");
                                    Function1<? super Environment, Environment> bindEnv = $dstr$bindEnv$_u24__u24.component1();
                                    Environment filterEnv = bindEnv.invoke(this.this$0.$rootEnv$inlined).flipToLocals$lang();
                                    ExprValue filterResult = (ExprValue)this.$filter.invoke(filterEnv);
                                    return ExprValueExtensionsKt.isUnknown(filterResult) ? false : ExprValueExtensionsKt.booleanValue(filterResult);
                                }
                            }).iterator();
                        }
                        if (!iter.hasNext()) {
                            Iterator<Object> iterator4;
                            switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$11[source.getJoinExpansion().ordinal()]) {
                                case 1: {
                                    iterator4 = CollectionsKt.listOf($fun$correlatedBind$1.invoke(EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0).getNullValue())).iterator();
                                    break;
                                }
                                case 2: {
                                    iterator4 = iter;
                                    break;
                                }
                                default: {
                                    throw new NoWhenBranchMatchedException();
                                }
                            }
                            iter = iterator4;
                        }
                        Iterator<R> iterator5 = iter;
                        list.set(n, iterator5);
                        return new Iterator<List<? extends ExprValue>>(this, iterators, sources){
                            private boolean fetched;
                            private final ArrayList<Pair<Function1<? super Environment, ? extends Environment>, ExprValue>> curr;
                            final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1 this$0;
                            final /* synthetic */ List $iterators;
                            final /* synthetic */ List $sources;
                            {
                                void var11_11;
                                void destination$iv;
                                void $this$mapTo$iv;
                                this.this$0 = $outer;
                                this.$iterators = $captured_local_variable$1;
                                this.$sources = $captured_local_variable$2;
                                Iterable iterable = $captured_local_variable$2;
                                Collection collection = new ArrayList<E>();
                                compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$1 var6_6 = this;
                                boolean $i$f$mapTo = false;
                                Iterator<T> iterator2 = $this$mapTo$iv.iterator();
                                while (iterator2.hasNext()) {
                                    T item$iv;
                                    T t = item$iv = iterator2.next();
                                    var11_11 = destination$iv;
                                    boolean bl = false;
                                    E e = null;
                                    var11_11.add(e);
                                }
                                var11_11 = destination$iv;
                                var6_6.curr = (ArrayList)var11_11;
                            }

                            /*
                             * WARNING - void declaration
                             */
                            public final boolean fetchIfNeeded() {
                                block4: while (!this.fetched) {
                                    Iterator iter;
                                    int idx;
                                    boolean bl;
                                    block13: {
                                        Iterable $this$any$iv = this.$iterators;
                                        boolean $i$f$any = false;
                                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                            bl = false;
                                        } else {
                                            for (T element$iv : $this$any$iv) {
                                                Iterator it = (Iterator)element$iv;
                                                boolean bl2 = false;
                                                if (!it.hasNext()) continue;
                                                bl = true;
                                                break block13;
                                            }
                                            bl = false;
                                        }
                                    }
                                    if (!bl) break;
                                    for (idx = this.$iterators.size() - 1; idx >= 0 && !(iter = (Iterator)this.$iterators.get(idx)).hasNext(); --idx) {
                                    }
                                    this.curr.set(idx, (Pair<Function1<? super Environment, ? extends Environment>, ExprValue>)((Iterator)this.$iterators.get(idx)).next());
                                    ++idx;
                                    while (idx < this.$iterators.size()) {
                                        void source;
                                        Pair<Function1<? super Environment, ? extends Environment>, ExprValue> pair = this.curr.get(idx - 1);
                                        if (pair == null) {
                                            Intrinsics.throwNpe();
                                        }
                                        Function1<? super Environment, ? extends Environment> ctx = pair.getFirst();
                                        CompiledFromSource compiledFromSource = (CompiledFromSource)this.$sources.get(idx);
                                        Function1<? super Environment, ? extends Environment> bindEnv = ctx;
                                        boolean bl3 = false;
                                        Function1<ExprValue, Pair<? extends Function1<? super Environment, ? extends Environment>, ? extends ExprValue>> $fun$correlatedBind$1 = new Function1<ExprValue, Pair<? extends Function1<? super Environment, ? extends Environment>, ? extends ExprValue>>((CompiledFromSource)source, bindEnv, this){
                                            final /* synthetic */ CompiledFromSource $source;
                                            final /* synthetic */ Function1 $bindEnv;
                                            final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$1 this$0;
                                            {
                                                this.$source = compiledFromSource;
                                                this.$bindEnv = function1;
                                                this.this$0 = var3_3;
                                                super(1);
                                            }

                                            @NotNull
                                            public final Pair<Function1<Environment, Environment>, ExprValue> invoke(@NotNull ExprValue value) {
                                                Intrinsics.checkParameterIsNotNull(value, "value");
                                                Alias alias = this.$source.getAlias();
                                                Function1 nextBindEnv = new Function1<Environment, Environment>(this, alias, value){
                                                    final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$1$lambda$1 this$0;
                                                    final /* synthetic */ Alias $alias;
                                                    final /* synthetic */ ExprValue $value;
                                                    {
                                                        this.this$0 = var1_1;
                                                        this.$alias = alias;
                                                        this.$value = exprValue2;
                                                        super(1);
                                                    }

                                                    @NotNull
                                                    public final Environment invoke(@NotNull Environment env) {
                                                        Intrinsics.checkParameterIsNotNull(env, "env");
                                                        Environment childEnv = (Environment)this.this$0.$bindEnv.invoke(env);
                                                        return Environment.nest$lang$default(childEnv, Bindings.Companion.buildLazyBindings((Function1)new Function1<Bindings.LazyBindingBuilder<ExprValue>, Unit>(this){
                                                            final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$1$lambda$1$1 this$0;
                                                            {
                                                                this.this$0 = var1_1;
                                                                super(1);
                                                            }

                                                            public final void invoke(@NotNull Bindings.LazyBindingBuilder<ExprValue> $this$buildLazyBindings) {
                                                                Intrinsics.checkParameterIsNotNull($this$buildLazyBindings, "$receiver");
                                                                $this$buildLazyBindings.addBinding(this.this$0.$alias.getAsName(), new Function0<ExprValue>(this){
                                                                    final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$1$lambda$1$1$1 this$0;
                                                                    {
                                                                        this.this$0 = var1_1;
                                                                        super(0);
                                                                    }

                                                                    @NotNull
                                                                    public final ExprValue invoke() {
                                                                        return this.this$0.this$0.$value;
                                                                    }
                                                                });
                                                                if (this.this$0.$alias.getAtName() != null) {
                                                                    $this$buildLazyBindings.addBinding(this.this$0.$alias.getAtName(), new Function0<ExprValue>(this){
                                                                        final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$1$lambda$1$1$1 this$0;
                                                                        {
                                                                            this.this$0 = var1_1;
                                                                            super(0);
                                                                        }

                                                                        @NotNull
                                                                        public final ExprValue invoke() {
                                                                            ExprValue exprValue2 = ExprValueExtensionsKt.getName(this.this$0.this$0.$value);
                                                                            if (exprValue2 == null) {
                                                                                exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0.this$0.this$0.this$0.this$0).getMissingValue();
                                                                            }
                                                                            return exprValue2;
                                                                        }
                                                                    });
                                                                }
                                                                if (this.this$0.$alias.getByName() != null) {
                                                                    $this$buildLazyBindings.addBinding(this.this$0.$alias.getByName(), new Function0<ExprValue>(this){
                                                                        final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$1$lambda$1$1$1 this$0;
                                                                        {
                                                                            this.this$0 = var1_1;
                                                                            super(0);
                                                                        }

                                                                        @NotNull
                                                                        public final ExprValue invoke() {
                                                                            ExprValue exprValue2 = ExprValueExtensionsKt.getAddress(this.this$0.this$0.$value);
                                                                            if (exprValue2 == null) {
                                                                                exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0.this$0.this$0.this$0.this$0).getMissingValue();
                                                                            }
                                                                            return exprValue2;
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        }), Environment.CurrentMode.GLOBALS_THEN_LOCALS, null, 4, null);
                                                    }
                                                };
                                                return new Pair<Function1<Environment, Environment>, ExprValue>(nextBindEnv, value);
                                            }
                                        };
                                        Iterator<R> iter2 = SequencesKt.map(CollectionsKt.asSequence(ExprValueExtensionsKt.rangeOver(source.getThunk().invoke(bindEnv.invoke(this.this$0.$fromEnv$inlined)))), (Function1)new Function1<ExprValue, Pair<? extends Function1<? super Environment, ? extends Environment>, ? extends ExprValue>>($fun$correlatedBind$1){
                                            final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$1$lambda$1 $correlatedBind$1;
                                            {
                                                this.$correlatedBind$1 = var1_1;
                                                super(1);
                                            }

                                            @NotNull
                                            public final Pair<Function1<Environment, Environment>, ExprValue> invoke(@NotNull ExprValue it) {
                                                Intrinsics.checkParameterIsNotNull(it, "it");
                                                return this.$correlatedBind$1.invoke(it);
                                            }
                                        }).iterator();
                                        Function1<Environment, ExprValue> filter = source.getFilter();
                                        if (filter != null) {
                                            iter2 = SequencesKt.filter(SequencesKt.asSequence(iter2), (Function1)new Function1<Pair<? extends Function1<? super Environment, ? extends Environment>, ? extends ExprValue>, Boolean>(filter, this){
                                                final /* synthetic */ Function1 $filter;
                                                final /* synthetic */ compileQueryWithoutProjection$1$$special$$inlined$foldLeftProduct$1$1 this$0;
                                                {
                                                    this.$filter = function1;
                                                    this.this$0 = var2_2;
                                                    super(1);
                                                }

                                                public final boolean invoke(@NotNull Pair<? extends Function1<? super Environment, Environment>, ? extends ExprValue> $dstr$bindEnv$_u24__u24) {
                                                    Intrinsics.checkParameterIsNotNull($dstr$bindEnv$_u24__u24, "<name for destructuring parameter 0>");
                                                    Function1<? super Environment, Environment> bindEnv = $dstr$bindEnv$_u24__u24.component1();
                                                    Environment filterEnv = bindEnv.invoke(this.this$0.this$0.$rootEnv$inlined).flipToLocals$lang();
                                                    ExprValue filterResult = (ExprValue)this.$filter.invoke(filterEnv);
                                                    return ExprValueExtensionsKt.isUnknown(filterResult) ? false : ExprValueExtensionsKt.booleanValue(filterResult);
                                                }
                                            }).iterator();
                                        }
                                        if (!iter2.hasNext()) {
                                            Iterator<Object> iterator2;
                                            switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$11[source.getJoinExpansion().ordinal()]) {
                                                case 1: {
                                                    iterator2 = CollectionsKt.listOf($fun$correlatedBind$1.invoke(EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0).getNullValue())).iterator();
                                                    break;
                                                }
                                                case 2: {
                                                    iterator2 = iter2;
                                                    break;
                                                }
                                                default: {
                                                    throw new NoWhenBranchMatchedException();
                                                }
                                            }
                                            iter2 = iterator2;
                                        }
                                        Iterator<R> iter3 = iter2;
                                        this.$iterators.set(idx, iter3);
                                        if (!iter3.hasNext()) continue block4;
                                        this.curr.set(idx, (Pair<Function1<? super Environment, ? extends Environment>, ExprValue>)iter3.next());
                                        ++idx;
                                    }
                                    this.fetched = true;
                                }
                                return this.fetched;
                            }

                            public boolean hasNext() {
                                return this.fetchIfNeeded();
                            }

                            /*
                             * WARNING - void declaration
                             */
                            @NotNull
                            public List<ExprValue> next() {
                                void $this$mapTo$iv$iv;
                                if (!this.fetchIfNeeded()) {
                                    throw (Throwable)new NoSuchElementException("Exhausted iterator");
                                }
                                this.fetched = false;
                                Iterable $this$map$iv = this.curr;
                                boolean $i$f$map = false;
                                Iterable iterable = $this$map$iv;
                                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                                boolean $i$f$mapTo = false;
                                for (T item$iv$iv : $this$mapTo$iv$iv) {
                                    void it;
                                    Pair pair = (Pair)item$iv$iv;
                                    Collection collection = destination$iv$iv;
                                    boolean bl = false;
                                    void v0 = it;
                                    if (v0 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    B b = v0.getSecond();
                                    collection.add(b);
                                }
                                return (List)destination$iv$iv;
                            }

                            public void remove() {
                                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                            }
                        };
                    }
                }), (Function1)new Function1<List<? extends ExprValue>, FromProduction>(this, fromEnv){
                    final /* synthetic */ compileQueryWithoutProjection.1 this$0;
                    final /* synthetic */ Environment $fromEnv;

                    @NotNull
                    public final FromProduction invoke(@NotNull List<? extends ExprValue> joinedValues) {
                        Intrinsics.checkParameterIsNotNull(joinedValues, "joinedValues");
                        return new FromProduction(joinedValues, Environment.nest$lang$default(this.$fromEnv, this.this$0.$localsBinder.bindLocals(joinedValues), null, null, 6, null));
                    }
                    {
                        this.this$0 = var1_1;
                        this.$fromEnv = environment;
                        super(1);
                    }
                });
                if (this.$compiledLetSources != null) {
                    seq2 = SequencesKt.map(seq2, (Function1)new Function1<FromProduction, FromProduction>(this){
                        final /* synthetic */ compileQueryWithoutProjection.1 this$0;

                        /*
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final FromProduction invoke(@NotNull FromProduction fromProduction) {
                            Intrinsics.checkParameterIsNotNull(fromProduction, "fromProduction");
                            Environment parentEnv = fromProduction.getEnv();
                            Iterable $this$fold$iv = this.this$0.$compiledLetSources;
                            boolean $i$f$fold = false;
                            Environment accumulator$iv = parentEnv;
                            for (T element$iv : $this$fold$iv) {
                                void curLetSource;
                                CompiledLetSource compiledLetSource = (CompiledLetSource)element$iv;
                                Environment accEnvironment = accumulator$iv;
                                boolean bl = false;
                                ExprValue letValue = curLetSource.getThunk().invoke(accEnvironment);
                                Bindings<T> binding2 = Bindings.Companion.over((Function1)new Function1<BindingName, ExprValue>((CompiledLetSource)curLetSource, letValue){
                                    final /* synthetic */ CompiledLetSource $curLetSource;
                                    final /* synthetic */ ExprValue $letValue;

                                    @Nullable
                                    public final ExprValue invoke(@NotNull BindingName bindingName) {
                                        Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
                                        return bindingName.isEquivalentTo(this.$curLetSource.getName()) ? this.$letValue : null;
                                    }
                                    {
                                        this.$curLetSource = compiledLetSource;
                                        this.$letValue = exprValue2;
                                        super(1);
                                    }
                                });
                                accumulator$iv = Environment.nest$lang$default(accEnvironment, binding2, null, null, 6, null);
                            }
                            Environment letEnv2 = accumulator$iv;
                            return FromProduction.copy$default(fromProduction, null, letEnv2, 1, null);
                        }
                        {
                            this.this$0 = var1_1;
                            super(1);
                        }
                    });
                }
                if (this.$whereThunk != null) {
                    seq2 = SequencesKt.filter(seq2, (Function1)new Function1<FromProduction, Boolean>(this){
                        final /* synthetic */ compileQueryWithoutProjection.1 this$0;

                        public final boolean invoke(@NotNull FromProduction $dstr$_u24__u24$env) {
                            boolean bl;
                            Intrinsics.checkParameterIsNotNull($dstr$_u24__u24$env, "<name for destructuring parameter 0>");
                            Environment env = $dstr$_u24__u24$env.component2();
                            ExprValue whereClauseResult = (ExprValue)this.this$0.$whereThunk.invoke(env);
                            boolean bl2 = ExprValueExtensionsKt.isUnknown(whereClauseResult);
                            if (bl2) {
                                bl = false;
                            } else if (!bl2) {
                                bl = ExprValueExtensionsKt.booleanValue(whereClauseResult);
                            } else {
                                throw new NoWhenBranchMatchedException();
                            }
                            return bl;
                        }
                        {
                            this.this$0 = var1_1;
                            super(1);
                        }
                    });
                }
                return var3_6;
            }
            {
                this.this$0 = evaluatingCompiler;
                this.$compiledSources = list;
                this.$localsBinder = localsBinder2;
                this.$compiledLetSources = list2;
                this.$whereThunk = function1;
                super(1);
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final List<ProjectionElement> compileSelectListToProjectionElements(SelectProjectionList selectList) {
        void $this$mapIndexedTo$iv$iv;
        Iterable $this$mapIndexed$iv = selectList.getItems();
        boolean $i$f$mapIndexed = false;
        Iterable iterable = $this$mapIndexed$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
            ProjectionElement projectionElement;
            void it;
            int n = index$iv$iv++;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            int n2 = n;
            SelectListItem selectListItem = (SelectListItem)item$iv$iv;
            int n3 = n2;
            Collection collection2 = collection;
            boolean bl2 = false;
            void var17_17 = it;
            if (var17_17 instanceof SelectListItemStar) {
                Void void_ = ExceptionsKt.errNoContext("Encountered a SelectListItemStar--did SelectStarVisitorTransform execute?", true);
                throw null;
            }
            if (var17_17 instanceof SelectListItemExpr) {
                void itemExpr;
                SelectListItemExpr selectListItemExpr = (SelectListItemExpr)it;
                ExprNode exprNode = selectListItemExpr.component1();
                SymbolicName asName = selectListItemExpr.component2();
                Object object = asName;
                if (object == null || (object = ((SymbolicName)object).getName()) == null) {
                    void idx;
                    object = ExprNodeExtensionsKt.extractColumnAlias((ExprNode)itemExpr, (int)idx);
                }
                Object alias = object;
                Function1<Environment, ExprValue> thunk2 = this.compileExprNode((ExprNode)itemExpr);
                projectionElement = new SingleProjectionElement(this.valueFactory.newString((String)alias), thunk2);
            } else if (var17_17 instanceof SelectListItemProjectAll) {
                projectionElement = new MultipleProjectionElement(CollectionsKt.listOf(this.compileExprNode(((SelectListItemProjectAll)it).getExpr())));
            } else {
                throw new NoWhenBranchMatchedException();
            }
            ProjectionElement projectionElement2 = projectionElement;
            collection2.add(projectionElement2);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compilePath(Path expr) {
        void components;
        void root;
        Path path = expr;
        ExprNode exprNode = path.component1();
        List<PathComponent> list = path.component2();
        MetaContainer metas = path.component3();
        Function1<Environment, ExprValue> rootThunk = this.compileExprNode((ExprNode)root);
        LinkedList<PathComponent> remainingComponents = new LinkedList<PathComponent>();
        Iterable $this$forEach$iv = (Iterable)components;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PathComponent it = (PathComponent)element$iv;
            boolean bl = false;
            remainingComponents.addLast(it);
        }
        Function2<Environment, ExprValue, ExprValue> componentThunk = this.compilePathComponents(metas, remainingComponents);
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, rootThunk, componentThunk){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $rootThunk$inlined;
            final /* synthetic */ Function2 $componentThunk$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$rootThunk$inlined = function1;
                this.$componentThunk$inlined = function2;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    boolean bl = false;
                    Environment env2 = env;
                    boolean bl2 = false;
                    ExprValue rootValue = (ExprValue)this.$rootThunk$inlined.invoke(env2);
                    exprValue2 = (ExprValue)this.$componentThunk$inlined.invoke(env2, rootValue);
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl3 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl4 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function2<Environment, ExprValue, ExprValue> compilePathComponents(MetaContainer pathMetas, LinkedList<PathComponent> remainingComponents) {
        Function2 function2;
        ArrayList<Object> componentThunks = new ArrayList<Object>();
        while (!remainingComponents.isEmpty()) {
            MetaContainer pathComponentMetas;
            Object object;
            ArrayList<Object> arrayList;
            Object object2;
            ArrayList<Object> arrayList2;
            PathComponent c = remainingComponents.removeFirst();
            PathComponent pathComponent = c;
            if (pathComponent instanceof PathComponentExpr) {
                SourceLocationMeta sourceLocationMeta$iv;
                MetaContainer metas$iv;
                boolean $i$f$thunkEnvValue$lang;
                void indexExpr;
                PathComponentExpr pathComponentExpr = (PathComponentExpr)c;
                ExprNode exprNode = pathComponentExpr.component1();
                CaseSensitivity caseSensitivity = pathComponentExpr.component2();
                SourceLocationMeta locationMeta = EvaluatingCompilerKt.access$getSourceLocationMeta$p(indexExpr.getMetas());
                if (indexExpr instanceof Literal && ((Literal)indexExpr).getIonValue() instanceof IonString) {
                    void this_$iv;
                    String string = ((IonString)((Literal)indexExpr).getIonValue()).stringValue();
                    Intrinsics.checkExpressionValueIsNotNull(string, "indexExpr.ionValue.stringValue()");
                    BindingName lookupName = new BindingName(string, BindingsKt.toBindingCase(caseSensitivity));
                    ThunkFactory thunkFactory = this.thunkFactory;
                    MetaContainer metaContainer = indexExpr.getMetas();
                    arrayList2 = componentThunks;
                    $i$f$thunkEnvValue$lang = false;
                    Meta meta = metas$iv.find("$source_location");
                    if (!(meta instanceof SourceLocationMeta)) {
                        meta = null;
                    }
                    sourceLocationMeta$iv = (SourceLocationMeta)meta;
                    object2 = new Function2<Environment, ExprValue, ExprValue>((ThunkFactory)this_$iv, sourceLocationMeta$iv, this, lookupName){
                        final /* synthetic */ ThunkFactory this$0$inline_fun;
                        final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                        final /* synthetic */ EvaluatingCompiler this$0;
                        final /* synthetic */ BindingName $lookupName$inlined;
                        {
                            this.this$0$inline_fun = thunkFactory;
                            this.$sourceLocationMeta = sourceLocationMeta;
                            this.this$0 = evaluatingCompiler;
                            this.$lookupName$inlined = bindingName;
                            super(2);
                        }

                        /*
                         * Ignored method signature, as it can't be verified against descriptor
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final ExprValue invoke(@NotNull Environment env, Object arg1) {
                            ExprValue exprValue2;
                            Intrinsics.checkParameterIsNotNull(env, "env");
                            ThunkFactory thunkFactory = this.this$0$inline_fun;
                            SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                            boolean $i$f$handleException$lang = false;
                            try {
                                void componentValue;
                                boolean bl = false;
                                ExprValue exprValue3 = (ExprValue)arg1;
                                Environment $noName_0 = env;
                                boolean bl2 = false;
                                ExprValue exprValue4 = componentValue.getBindings().get(this.$lookupName$inlined);
                                if (exprValue4 == null) {
                                    exprValue4 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).getMissingValue();
                                }
                                exprValue2 = exprValue4;
                            } catch (EvaluationException e$iv) {
                                if (e$iv.getErrorContext() == null) {
                                    throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                }
                                if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                    SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                    if (sourceLocationMeta != null) {
                                        SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                        boolean bl = false;
                                        boolean bl3 = false;
                                        SourceLocationMeta it$iv = sourceLocationMeta2;
                                        boolean bl4 = false;
                                        ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                    }
                                }
                                throw (Throwable)e$iv;
                            } catch (Exception e$iv) {
                                void this_$iv;
                                R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                throw null;
                            }
                            return exprValue2;
                        }
                    };
                    arrayList = arrayList2;
                    object = object2;
                } else {
                    Function1<Environment, ExprValue> indexThunk = this.compileExprNode((ExprNode)indexExpr);
                    ThunkFactory this_$iv = this.thunkFactory;
                    metas$iv = indexExpr.getMetas();
                    $i$f$thunkEnvValue$lang = false;
                    Meta meta = metas$iv.find("$source_location");
                    if (!(meta instanceof SourceLocationMeta)) {
                        meta = null;
                    }
                    sourceLocationMeta$iv = (SourceLocationMeta)meta;
                    object2 = new Function2<Environment, ExprValue, ExprValue>(this_$iv, sourceLocationMeta$iv, this, indexThunk, caseSensitivity, locationMeta){
                        final /* synthetic */ ThunkFactory this$0$inline_fun;
                        final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                        final /* synthetic */ EvaluatingCompiler this$0;
                        final /* synthetic */ Function1 $indexThunk$inlined;
                        final /* synthetic */ CaseSensitivity $caseSensitivity$inlined;
                        final /* synthetic */ SourceLocationMeta $locationMeta$inlined;
                        {
                            this.this$0$inline_fun = thunkFactory;
                            this.$sourceLocationMeta = sourceLocationMeta;
                            this.this$0 = evaluatingCompiler;
                            this.$indexThunk$inlined = function1;
                            this.$caseSensitivity$inlined = caseSensitivity;
                            this.$locationMeta$inlined = sourceLocationMeta2;
                            super(2);
                        }

                        /*
                         * Ignored method signature, as it can't be verified against descriptor
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final ExprValue invoke(@NotNull Environment env, Object arg1) {
                            ExprValue exprValue2;
                            Intrinsics.checkParameterIsNotNull(env, "env");
                            ThunkFactory thunkFactory = this.this$0$inline_fun;
                            SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                            boolean $i$f$handleException$lang = false;
                            try {
                                ExprValue exprValue3;
                                void componentValue;
                                boolean bl = false;
                                ExprValue exprValue4 = (ExprValue)arg1;
                                Environment env2 = env;
                                boolean bl2 = false;
                                ExprValue indexValue = (ExprValue)this.$indexThunk$inlined.invoke(env2);
                                if (indexValue.getType() == ExprValueType.INT) {
                                    exprValue3 = componentValue.getOrdinalBindings().get(ExprValueExtensionsKt.numberValue(indexValue).intValue());
                                } else if (indexValue.getType().isText()) {
                                    BindingName lookupName = new BindingName(ExprValueExtensionsKt.stringValue(indexValue), BindingsKt.toBindingCase(this.$caseSensitivity$inlined));
                                    exprValue3 = componentValue.getBindings().get(lookupName);
                                } else {
                                    Void void_ = ExceptionsKt.err("Cannot convert index to int/string: " + indexValue, ExceptionsKt.errorContextFrom(this.$locationMeta$inlined), false);
                                    throw null;
                                }
                                ExprValue exprValue5 = exprValue3;
                                if (exprValue3 == null) {
                                    exprValue5 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).getMissingValue();
                                }
                                exprValue2 = exprValue5;
                            } catch (EvaluationException e$iv) {
                                if (e$iv.getErrorContext() == null) {
                                    throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                }
                                if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                    SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                    if (sourceLocationMeta != null) {
                                        SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                        boolean bl = false;
                                        boolean bl3 = false;
                                        SourceLocationMeta it$iv = sourceLocationMeta2;
                                        boolean bl4 = false;
                                        ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                    }
                                }
                                throw (Throwable)e$iv;
                            } catch (Exception e$iv) {
                                void this_$iv;
                                R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                throw null;
                            }
                            return exprValue2;
                        }
                    };
                    arrayList = arrayList2;
                    object = object2;
                }
            } else if (pathComponent instanceof PathComponentUnpivot) {
                Object tempThunk;
                PathComponentUnpivot caseSensitivity = (PathComponentUnpivot)c;
                pathComponentMetas = caseSensitivity.component1();
                if (!remainingComponents.isEmpty()) {
                    void this_$iv;
                    tempThunk = this.compilePathComponents(pathMetas, remainingComponents);
                    ThunkFactory locationMeta = this.thunkFactory;
                    boolean $i$f$thunkEnvValue$lang = false;
                    Meta meta = pathComponentMetas.find("$source_location");
                    if (!(meta instanceof SourceLocationMeta)) {
                        meta = null;
                    }
                    SourceLocationMeta sourceLocationMeta$iv2 = (SourceLocationMeta)meta;
                    object2 = new Function2<Environment, ExprValue, ExprValue>((ThunkFactory)this_$iv, sourceLocationMeta$iv2, this, (Function2)tempThunk){
                        final /* synthetic */ ThunkFactory this$0$inline_fun;
                        final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                        final /* synthetic */ EvaluatingCompiler this$0;
                        final /* synthetic */ Function2 $tempThunk$inlined;
                        {
                            this.this$0$inline_fun = thunkFactory;
                            this.$sourceLocationMeta = sourceLocationMeta;
                            this.this$0 = evaluatingCompiler;
                            this.$tempThunk$inlined = function2;
                            super(2);
                        }

                        /*
                         * Ignored method signature, as it can't be verified against descriptor
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final ExprValue invoke(@NotNull Environment env, Object arg1) {
                            ExprValue exprValue2;
                            Intrinsics.checkParameterIsNotNull(env, "env");
                            ThunkFactory thunkFactory = this.this$0$inline_fun;
                            SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                            boolean $i$f$handleException$lang = false;
                            try {
                                void $this$flatMapTo$iv$iv;
                                void componentValue;
                                boolean bl = false;
                                ExprValue exprValue3 = (ExprValue)arg1;
                                Environment env2 = env;
                                boolean bl2 = false;
                                Iterable $this$flatMap$iv = this.this$0.unpivot$lang((ExprValue)componentValue);
                                boolean $i$f$flatMap = false;
                                Iterable iterable = $this$flatMap$iv;
                                Collection destination$iv$iv = new ArrayList<E>();
                                boolean $i$f$flatMapTo = false;
                                for (T element$iv$iv : $this$flatMapTo$iv$iv) {
                                    ExprValue it = (ExprValue)element$iv$iv;
                                    boolean bl3 = false;
                                    Iterable<ExprValue> list$iv$iv = ExprValueExtensionsKt.rangeOver((ExprValue)this.$tempThunk$inlined.invoke(env2, it));
                                    CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
                                }
                                Sequence<T> mapped = CollectionsKt.asSequence((List)destination$iv$iv);
                                exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBag(mapped);
                            } catch (EvaluationException e$iv) {
                                if (e$iv.getErrorContext() == null) {
                                    throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                }
                                if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                    SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                    if (sourceLocationMeta != null) {
                                        SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                        boolean bl = false;
                                        boolean bl4 = false;
                                        SourceLocationMeta it$iv = sourceLocationMeta2;
                                        boolean bl5 = false;
                                        ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                    }
                                }
                                throw (Throwable)e$iv;
                            } catch (Exception e$iv) {
                                void this_$iv;
                                R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                throw null;
                            }
                            return exprValue2;
                        }
                    };
                    arrayList = arrayList2;
                    object = object2;
                } else {
                    void this_$iv;
                    tempThunk = this.thunkFactory;
                    boolean $i$f$thunkEnvValue$lang = false;
                    Meta meta = pathComponentMetas.find("$source_location");
                    if (!(meta instanceof SourceLocationMeta)) {
                        meta = null;
                    }
                    SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                    object2 = new Function2<Environment, ExprValue, ExprValue>((ThunkFactory)this_$iv, sourceLocationMeta$iv, this){
                        final /* synthetic */ ThunkFactory this$0$inline_fun;
                        final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                        final /* synthetic */ EvaluatingCompiler this$0;
                        {
                            this.this$0$inline_fun = thunkFactory;
                            this.$sourceLocationMeta = sourceLocationMeta;
                            this.this$0 = evaluatingCompiler;
                            super(2);
                        }

                        /*
                         * Ignored method signature, as it can't be verified against descriptor
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final ExprValue invoke(@NotNull Environment env, Object arg1) {
                            ExprValue exprValue2;
                            Intrinsics.checkParameterIsNotNull(env, "env");
                            ThunkFactory thunkFactory = this.this$0$inline_fun;
                            SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                            boolean $i$f$handleException$lang = false;
                            try {
                                void componentValue;
                                boolean bl = false;
                                ExprValue exprValue3 = (ExprValue)arg1;
                                Environment $noName_0 = env;
                                boolean bl2 = false;
                                exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBag(CollectionsKt.asSequence(this.this$0.unpivot$lang((ExprValue)componentValue)));
                            } catch (EvaluationException e$iv) {
                                if (e$iv.getErrorContext() == null) {
                                    throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                }
                                if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                    SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                    if (sourceLocationMeta != null) {
                                        SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                        boolean bl = false;
                                        boolean bl3 = false;
                                        SourceLocationMeta it$iv = sourceLocationMeta2;
                                        boolean bl4 = false;
                                        ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                    }
                                }
                                throw (Throwable)e$iv;
                            } catch (Exception e$iv) {
                                void this_$iv;
                                R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                throw null;
                            }
                            return exprValue2;
                        }
                    };
                    arrayList = arrayList2;
                    object = object2;
                }
            } else if (pathComponent instanceof PathComponentWildcard) {
                PathComponentWildcard this_$iv = (PathComponentWildcard)c;
                pathComponentMetas = this_$iv.component1();
                if (!remainingComponents.isEmpty()) {
                    SourceLocationMeta sourceLocationMeta$iv;
                    void $this$filterIsInstanceTo$iv$iv;
                    void $this$filterIsInstance$iv;
                    Iterable $i$f$thunkEnvValue$lang = remainingComponents;
                    boolean $i$f$filterIsInstance2 = false;
                    void sourceLocationMeta$iv2 = $this$filterIsInstance$iv;
                    Collection destination$iv$iv = new ArrayList();
                    boolean $i$f$filterIsInstanceTo = false;
                    for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                        if (!(element$iv$iv instanceof PathComponentWildcard)) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    object2 = (List)destination$iv$iv;
                    ArrayList<Object> arrayList3 = arrayList2;
                    boolean hasMoreWildCards = CollectionsKt.any((Iterable)object2);
                    Function2<Environment, ExprValue, ExprValue> tempThunk = this.compilePathComponents(pathMetas, remainingComponents);
                    if (!hasMoreWildCards) {
                        void this_$iv2;
                        ThunkFactory $i$f$filterIsInstance2 = this.thunkFactory;
                        arrayList2 = arrayList3;
                        boolean $i$f$thunkEnvValue$lang2 = false;
                        Meta meta = pathComponentMetas.find("$source_location");
                        if (!(meta instanceof SourceLocationMeta)) {
                            meta = null;
                        }
                        sourceLocationMeta$iv = (SourceLocationMeta)meta;
                        object2 = new Function2<Environment, ExprValue, ExprValue>((ThunkFactory)this_$iv2, sourceLocationMeta$iv, this, tempThunk){
                            final /* synthetic */ ThunkFactory this$0$inline_fun;
                            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                            final /* synthetic */ EvaluatingCompiler this$0;
                            final /* synthetic */ Function2 $tempThunk$inlined;
                            {
                                this.this$0$inline_fun = thunkFactory;
                                this.$sourceLocationMeta = sourceLocationMeta;
                                this.this$0 = evaluatingCompiler;
                                this.$tempThunk$inlined = function2;
                                super(2);
                            }

                            /*
                             * Ignored method signature, as it can't be verified against descriptor
                             * WARNING - void declaration
                             */
                            @NotNull
                            public final ExprValue invoke(@NotNull Environment env, Object arg1) {
                                ExprValue exprValue2;
                                Intrinsics.checkParameterIsNotNull(env, "env");
                                ThunkFactory thunkFactory = this.this$0$inline_fun;
                                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                boolean $i$f$handleException$lang = false;
                                try {
                                    void $this$mapTo$iv$iv;
                                    void componentValue;
                                    boolean bl = false;
                                    ExprValue exprValue3 = (ExprValue)arg1;
                                    Environment env2 = env;
                                    boolean bl2 = false;
                                    Iterable<ExprValue> $this$map$iv = ExprValueExtensionsKt.rangeOver((ExprValue)componentValue);
                                    boolean $i$f$map = false;
                                    Iterable<ExprValue> iterable = $this$map$iv;
                                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                                    boolean $i$f$mapTo = false;
                                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                                        void it;
                                        ExprValue exprValue4 = (ExprValue)item$iv$iv;
                                        Collection collection = destination$iv$iv;
                                        boolean bl3 = false;
                                        ExprValue exprValue5 = (ExprValue)this.$tempThunk$inlined.invoke(env2, it);
                                        collection.add(exprValue5);
                                    }
                                    Sequence<T> mapped = CollectionsKt.asSequence((List)destination$iv$iv);
                                    exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBag(mapped);
                                } catch (EvaluationException e$iv) {
                                    if (e$iv.getErrorContext() == null) {
                                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                    }
                                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                        if (sourceLocationMeta != null) {
                                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                            boolean bl = false;
                                            boolean bl4 = false;
                                            SourceLocationMeta it$iv = sourceLocationMeta2;
                                            boolean bl5 = false;
                                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                        }
                                    }
                                    throw (Throwable)e$iv;
                                } catch (Exception e$iv) {
                                    void this_$iv;
                                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                    throw null;
                                }
                                return exprValue2;
                            }
                        };
                        arrayList = arrayList2;
                        object = object2;
                    } else {
                        ThunkFactory this_$iv2 = this.thunkFactory;
                        arrayList2 = arrayList3;
                        boolean $i$f$thunkEnvValue$lang3 = false;
                        Meta meta = pathComponentMetas.find("$source_location");
                        if (!(meta instanceof SourceLocationMeta)) {
                            meta = null;
                        }
                        sourceLocationMeta$iv = (SourceLocationMeta)meta;
                        object2 = new Function2<Environment, ExprValue, ExprValue>(this_$iv2, sourceLocationMeta$iv, this, tempThunk){
                            final /* synthetic */ ThunkFactory this$0$inline_fun;
                            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                            final /* synthetic */ EvaluatingCompiler this$0;
                            final /* synthetic */ Function2 $tempThunk$inlined;
                            {
                                this.this$0$inline_fun = thunkFactory;
                                this.$sourceLocationMeta = sourceLocationMeta;
                                this.this$0 = evaluatingCompiler;
                                this.$tempThunk$inlined = function2;
                                super(2);
                            }

                            /*
                             * Ignored method signature, as it can't be verified against descriptor
                             * WARNING - void declaration
                             */
                            @NotNull
                            public final ExprValue invoke(@NotNull Environment env, Object arg1) {
                                ExprValue exprValue2;
                                Intrinsics.checkParameterIsNotNull(env, "env");
                                ThunkFactory thunkFactory = this.this$0$inline_fun;
                                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                                boolean $i$f$handleException$lang = false;
                                try {
                                    void $this$flatMapTo$iv$iv;
                                    void componentValue;
                                    boolean bl = false;
                                    ExprValue exprValue3 = (ExprValue)arg1;
                                    Environment env2 = env;
                                    boolean bl2 = false;
                                    Iterable<ExprValue> $this$flatMap$iv = ExprValueExtensionsKt.rangeOver((ExprValue)componentValue);
                                    boolean $i$f$flatMap = false;
                                    Iterable<ExprValue> iterable = $this$flatMap$iv;
                                    Collection destination$iv$iv = new ArrayList<E>();
                                    boolean $i$f$flatMapTo = false;
                                    for (T element$iv$iv : $this$flatMapTo$iv$iv) {
                                        ExprValue it = (ExprValue)element$iv$iv;
                                        boolean bl3 = false;
                                        ExprValue tempValue = (ExprValue)this.$tempThunk$inlined.invoke(env2, it);
                                        Iterable list$iv$iv = tempValue;
                                        CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
                                    }
                                    Sequence<T> mapped = CollectionsKt.asSequence((List)destination$iv$iv);
                                    exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBag(mapped);
                                } catch (EvaluationException e$iv) {
                                    if (e$iv.getErrorContext() == null) {
                                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                    }
                                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                        if (sourceLocationMeta != null) {
                                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                            boolean bl = false;
                                            boolean bl4 = false;
                                            SourceLocationMeta it$iv = sourceLocationMeta2;
                                            boolean bl5 = false;
                                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                        }
                                    }
                                    throw (Throwable)e$iv;
                                } catch (Exception e$iv) {
                                    void this_$iv;
                                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                    throw null;
                                }
                                return exprValue2;
                            }
                        };
                        arrayList = arrayList2;
                        object = object2;
                    }
                } else {
                    ThunkFactory hasMoreWildCards = this.thunkFactory;
                    boolean $i$f$thunkEnvValue$lang = false;
                    Meta meta = pathComponentMetas.find("$source_location");
                    if (!(meta instanceof SourceLocationMeta)) {
                        meta = null;
                    }
                    SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                    object2 = new Function2<Environment, ExprValue, ExprValue>((ThunkFactory)((Object)this_$iv), sourceLocationMeta$iv, this){
                        final /* synthetic */ ThunkFactory this$0$inline_fun;
                        final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                        final /* synthetic */ EvaluatingCompiler this$0;
                        {
                            this.this$0$inline_fun = thunkFactory;
                            this.$sourceLocationMeta = sourceLocationMeta;
                            this.this$0 = evaluatingCompiler;
                            super(2);
                        }

                        /*
                         * Ignored method signature, as it can't be verified against descriptor
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final ExprValue invoke(@NotNull Environment env, Object arg1) {
                            ExprValue exprValue2;
                            Intrinsics.checkParameterIsNotNull(env, "env");
                            ThunkFactory thunkFactory = this.this$0$inline_fun;
                            SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                            boolean $i$f$handleException$lang = false;
                            try {
                                void componentValue;
                                boolean bl = false;
                                ExprValue exprValue3 = (ExprValue)arg1;
                                Environment $noName_0 = env;
                                boolean bl2 = false;
                                Sequence<ExprValue> mapped = CollectionsKt.asSequence(ExprValueExtensionsKt.rangeOver((ExprValue)componentValue));
                                exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBag(mapped);
                            } catch (EvaluationException e$iv) {
                                if (e$iv.getErrorContext() == null) {
                                    throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                                }
                                if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                    SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                    if (sourceLocationMeta != null) {
                                        SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                        boolean bl = false;
                                        boolean bl3 = false;
                                        SourceLocationMeta it$iv = sourceLocationMeta2;
                                        boolean bl4 = false;
                                        ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                    }
                                }
                                throw (Throwable)e$iv;
                            } catch (Exception e$iv) {
                                void this_$iv;
                                R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                                throw null;
                            }
                            return exprValue2;
                        }
                    };
                    arrayList = arrayList2;
                    object = object2;
                }
            } else {
                throw new NoWhenBranchMatchedException();
            }
            arrayList.add(object);
        }
        switch (componentThunks.size()) {
            case 1: {
                function2 = (Function2)CollectionsKt.first((List)componentThunks);
                break;
            }
            default: {
                ThunkFactory this_$iv = this.thunkFactory;
                boolean $i$f$thunkEnvValue$lang = false;
                Meta meta = pathMetas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                function2 = new Function2<Environment, ExprValue, ExprValue>(this_$iv, sourceLocationMeta$iv, componentThunks){
                    final /* synthetic */ ThunkFactory this$0;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ ArrayList $componentThunks$inlined;
                    {
                        this.this$0 = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.$componentThunks$inlined = arrayList;
                        super(2);
                    }

                    /*
                     * Ignored method signature, as it can't be verified against descriptor
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env, Object arg1) {
                        void var18_18;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            void rootValue;
                            boolean bl = false;
                            ExprValue exprValue2 = (ExprValue)arg1;
                            Environment env2 = env;
                            boolean bl2 = false;
                            Iterable $this$fold$iv = this.$componentThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = rootValue;
                            for (T element$iv : $this$fold$iv) {
                                void componentThunk;
                                Function2 function2 = (Function2)element$iv;
                                void componentValue = accumulator$iv;
                                boolean bl3 = false;
                                accumulator$iv = (ExprValue)componentThunk.invoke(env2, componentValue);
                            }
                            var18_18 = accumulator$iv;
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl4 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl5 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return var18_18;
                    }
                };
            }
        }
        return function2;
    }

    private final Function1<Environment, ExprValue> compileNAryLike(List<? extends ExprNode> argExprs, List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, MetaContainer operatorMetas) {
        Function1 function1;
        ExprNode valueExpr = argExprs.get(0);
        ExprNode patternExpr = argExprs.get(1);
        ExprNode escapeExpr = argExprs.size() > 2 ? argExprs.get(2) : null;
        SourceLocationMeta patternLocationMeta = EvaluatingCompilerKt.access$getSourceLocationMeta$p(patternExpr.getMetas());
        KMappedMarker kMappedMarker = escapeExpr;
        SourceLocationMeta escapeLocationMeta = kMappedMarker != null && (kMappedMarker = kMappedMarker.getMetas()) != null ? EvaluatingCompilerKt.access$getSourceLocationMeta$p((MetaContainer)kMappedMarker) : null;
        Function2<ExprValue, ExprValue, Function0<? extends List<? extends PatternPart>>> $fun$getPatternParts$1 = new Function2<ExprValue, ExprValue, Function0<? extends List<? extends PatternPart>>>(this, operatorMetas, patternLocationMeta, escapeLocationMeta){
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ MetaContainer $operatorMetas;
            final /* synthetic */ SourceLocationMeta $patternLocationMeta;
            final /* synthetic */ SourceLocationMeta $escapeLocationMeta;

            /*
             * WARNING - void declaration
             */
            @Nullable
            public final Function0<List<PatternPart>> invoke(@NotNull ExprValue pattern, @Nullable ExprValue escape) {
                void patternString;
                boolean bl;
                Object object;
                Object $this$any$iv;
                block9: {
                    ExprValue it;
                    T element$iv;
                    boolean bl2;
                    boolean $i$f$any;
                    List<ExprValue> patternArgs;
                    block8: {
                        Intrinsics.checkParameterIsNotNull(pattern, "pattern");
                        patternArgs = CollectionsKt.listOfNotNull(pattern, escape);
                        $this$any$iv = patternArgs;
                        $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            bl2 = false;
                        } else {
                            object = $this$any$iv.iterator();
                            while (object.hasNext()) {
                                element$iv = object.next();
                                it = (ExprValue)element$iv;
                                boolean bl3 = false;
                                if (!it.getType().isUnknown()) continue;
                                bl2 = true;
                                break block8;
                            }
                            bl2 = false;
                        }
                    }
                    if (bl2) {
                        return null;
                    }
                    $this$any$iv = patternArgs;
                    $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        object = $this$any$iv.iterator();
                        while (object.hasNext()) {
                            element$iv = object.next();
                            it = (ExprValue)element$iv;
                            boolean bl4 = false;
                            if (!(!it.getType().isText())) continue;
                            bl = true;
                            break block9;
                        }
                        bl = false;
                    }
                }
                if (bl) {
                    return new Function0(this, pattern, escape){
                        final /* synthetic */ compileNAryLike.1 this$0;
                        final /* synthetic */ ExprValue $pattern;
                        final /* synthetic */ ExprValue $escape;

                        @NotNull
                        public final Void invoke() {
                            PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(this.this$0.$operatorMetas);
                            ErrorCode errorCode = ErrorCode.EVALUATOR_LIKE_INVALID_INPUTS;
                            String string = "LIKE expression must be given non-null strings as input";
                            boolean bl = false;
                            boolean bl2 = false;
                            PropertyValueMap it = propertyValueMap;
                            boolean bl3 = false;
                            it.set(Property.LIKE_PATTERN, ((Object)this.$pattern.getIonValue()).toString());
                            if (this.$escape != null) {
                                it.set(Property.LIKE_ESCAPE, ((Object)this.$escape.getIonValue()).toString());
                            }
                            PropertyValueMap propertyValueMap2 = propertyValueMap;
                            Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap2, false);
                            throw null;
                        }
                        {
                            this.this$0 = var1_1;
                            this.$pattern = exprValue2;
                            this.$escape = exprValue3;
                            super(0);
                        }
                    };
                }
                ExprValue exprValue2 = escape;
                object = EvaluatingCompiler.access$checkPattern(this.this$0, pattern.getIonValue(), this.$patternLocationMeta, exprValue2 != null ? exprValue2.getIonValue() : null, this.$escapeLocationMeta);
                $this$any$iv = (String)((Pair)object).component1();
                Integer escapeChar = (Integer)((Pair)object).component2();
                CharSequence charSequence = (CharSequence)patternString;
                boolean bl5 = false;
                List<T> patternParts = charSequence.length() == 0 ? CollectionsKt.emptyList() : PatternPartKt.parsePattern((String)patternString, escapeChar);
                return new Function0<List<? extends PatternPart>>(patternParts){
                    final /* synthetic */ List $patternParts;

                    @NotNull
                    public final List<PatternPart> invoke() {
                        return this.$patternParts;
                    }
                    {
                        this.$patternParts = list;
                        super(0);
                    }
                };
            }
            {
                this.this$0 = evaluatingCompiler;
                this.$operatorMetas = metaContainer;
                this.$patternLocationMeta = sourceLocationMeta;
                this.$escapeLocationMeta = sourceLocationMeta2;
                super(2);
            }
        };
        Function2<ExprValue, Function0<? extends List<? extends PatternPart>>, ExprValue> $fun$runPatternParts$2 = new Function2<ExprValue, Function0<? extends List<? extends PatternPart>>, ExprValue>(this, operatorMetas){
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ MetaContainer $operatorMetas;

            @NotNull
            public final ExprValue invoke(@NotNull ExprValue value, @Nullable Function0<? extends List<? extends PatternPart>> patternParts) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(value, "value");
                if (patternParts == null || value.getType().isUnknown()) {
                    exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).getNullValue();
                } else {
                    if (!value.getType().isText()) {
                        PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(this.$operatorMetas);
                        ErrorCode errorCode = ErrorCode.EVALUATOR_LIKE_INVALID_INPUTS;
                        String string = "LIKE expression must be given non-null strings as input";
                        boolean bl = false;
                        boolean bl2 = false;
                        PropertyValueMap it = propertyValueMap;
                        boolean bl3 = false;
                        it.set(Property.LIKE_VALUE, ((Object)value.getIonValue()).toString());
                        PropertyValueMap propertyValueMap2 = propertyValueMap;
                        Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap2, false);
                        throw null;
                    }
                    exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newBoolean(PatternPartKt.executePattern(patternParts.invoke(), ExprValueExtensionsKt.stringValue(value)));
                }
                return exprValue2;
            }
            {
                this.this$0 = evaluatingCompiler;
                this.$operatorMetas = metaContainer;
                super(2);
            }
        };
        Function1<? super Environment, ? extends ExprValue> valueThunk = argThunks.get(0);
        if (patternExpr instanceof Literal && (escapeExpr == null || escapeExpr instanceof Literal)) {
            ExprValue exprValue2;
            Object object;
            Function2<ExprValue, ExprValue, Function0<? extends List<? extends PatternPart>>> function2 = $fun$getPatternParts$1;
            ExprValue exprValue3 = this.valueFactory.newFromIonValue(((Literal)patternExpr).getIonValue());
            ExprNode exprNode = escapeExpr;
            if (!(exprNode instanceof Literal)) {
                exprNode = null;
            }
            if ((object = (Literal)exprNode) != null && (object = ((Literal)object).getIonValue()) != null) {
                Object object2 = object;
                ExprValue exprValue4 = exprValue3;
                Function2<ExprValue, ExprValue, Function0<? extends List<? extends PatternPart>>> function22 = function2;
                boolean bl = false;
                boolean bl2 = false;
                Object it = object2;
                boolean bl3 = false;
                ExprValue exprValue5 = this.valueFactory.newFromIonValue((IonValue)it);
                function2 = function22;
                exprValue3 = exprValue4;
                exprValue2 = exprValue5;
            } else {
                exprValue2 = null;
            }
            Function0<List<PatternPart>> patternParts = function2.invoke(exprValue3, exprValue2);
            if (valueExpr instanceof Literal) {
                ExprValue resultValue = $fun$runPatternParts$2.invoke(this.valueFactory.newFromIonValue(((Literal)valueExpr).getIonValue()), patternParts);
                ThunkFactory this_$iv = this.thunkFactory;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = operatorMetas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, resultValue){
                    final /* synthetic */ ThunkFactory this$0;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ ExprValue $resultValue$inlined;
                    {
                        this.this$0 = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.$resultValue$inlined = exprValue2;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            boolean bl = false;
                            Environment it = env;
                            boolean bl2 = false;
                            exprValue2 = this.$resultValue$inlined;
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl3 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl4 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
            }
            ThunkFactory this_$iv = this.thunkFactory;
            boolean $i$f$thunkEnv$lang = false;
            Meta meta = operatorMetas.find("$source_location");
            if (!(meta instanceof SourceLocationMeta)) {
                meta = null;
            }
            SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
            function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, valueThunk, $fun$runPatternParts$2, patternParts){
                final /* synthetic */ ThunkFactory this$0;
                final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                final /* synthetic */ Function1 $valueThunk$inlined;
                final /* synthetic */ compileNAryLike.2 $runPatternParts$2$inlined;
                final /* synthetic */ Function0 $patternParts$inlined;
                {
                    this.this$0 = thunkFactory;
                    this.$sourceLocationMeta = sourceLocationMeta;
                    this.$valueThunk$inlined = function1;
                    this.$runPatternParts$2$inlined = var4_4;
                    this.$patternParts$inlined = function0;
                    super(1);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final ExprValue invoke(@NotNull Environment env) {
                    ExprValue exprValue2;
                    Intrinsics.checkParameterIsNotNull(env, "env");
                    ThunkFactory thunkFactory = this.this$0;
                    SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                    boolean $i$f$handleException$lang = false;
                    try {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue value = (ExprValue)this.$valueThunk$inlined.invoke(env2);
                        exprValue2 = this.$runPatternParts$2$inlined.invoke(value, this.$patternParts$inlined);
                    } catch (EvaluationException e$iv) {
                        if (e$iv.getErrorContext() == null) {
                            throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                        }
                        if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                            SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                            if (sourceLocationMeta != null) {
                                SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                boolean bl = false;
                                boolean bl3 = false;
                                SourceLocationMeta it$iv = sourceLocationMeta2;
                                boolean bl4 = false;
                                ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                            }
                        }
                        throw (Throwable)e$iv;
                    } catch (Exception e$iv) {
                        void this_$iv;
                        R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                        throw null;
                    }
                    return exprValue2;
                }
            };
        } else {
            Function1<? super Environment, ? extends ExprValue> patternThunk = argThunks.get(1);
            if (argThunks.size() == 2) {
                ThunkFactory this_$iv = this.thunkFactory;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = operatorMetas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, valueThunk, patternThunk, $fun$getPatternParts$1, $fun$runPatternParts$2){
                    final /* synthetic */ ThunkFactory this$0;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ Function1 $valueThunk$inlined;
                    final /* synthetic */ Function1 $patternThunk$inlined;
                    final /* synthetic */ compileNAryLike.1 $getPatternParts$1$inlined;
                    final /* synthetic */ compileNAryLike.2 $runPatternParts$2$inlined;
                    {
                        this.this$0 = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.$valueThunk$inlined = function1;
                        this.$patternThunk$inlined = function12;
                        this.$getPatternParts$1$inlined = var5_5;
                        this.$runPatternParts$2$inlined = var6_6;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            boolean bl = false;
                            Environment env2 = env;
                            boolean bl2 = false;
                            ExprValue value = (ExprValue)this.$valueThunk$inlined.invoke(env2);
                            ExprValue pattern = (ExprValue)this.$patternThunk$inlined.invoke(env2);
                            Function0<List<PatternPart>> pps = this.$getPatternParts$1$inlined.invoke(pattern, null);
                            exprValue2 = this.$runPatternParts$2$inlined.invoke(value, pps);
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl3 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl4 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
            } else {
                Function1<? super Environment, ? extends ExprValue> escapeThunk = argThunks.get(2);
                ThunkFactory this_$iv = this.thunkFactory;
                boolean $i$f$thunkEnv$lang = false;
                Meta meta = operatorMetas.find("$source_location");
                if (!(meta instanceof SourceLocationMeta)) {
                    meta = null;
                }
                SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
                function1 = new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, valueThunk, patternThunk, escapeThunk, $fun$getPatternParts$1, $fun$runPatternParts$2){
                    final /* synthetic */ ThunkFactory this$0;
                    final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
                    final /* synthetic */ Function1 $valueThunk$inlined;
                    final /* synthetic */ Function1 $patternThunk$inlined;
                    final /* synthetic */ Function1 $escapeThunk$inlined;
                    final /* synthetic */ compileNAryLike.1 $getPatternParts$1$inlined;
                    final /* synthetic */ compileNAryLike.2 $runPatternParts$2$inlined;
                    {
                        this.this$0 = thunkFactory;
                        this.$sourceLocationMeta = sourceLocationMeta;
                        this.$valueThunk$inlined = function1;
                        this.$patternThunk$inlined = function12;
                        this.$escapeThunk$inlined = function13;
                        this.$getPatternParts$1$inlined = var6_6;
                        this.$runPatternParts$2$inlined = var7_7;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final ExprValue invoke(@NotNull Environment env) {
                        ExprValue exprValue2;
                        Intrinsics.checkParameterIsNotNull(env, "env");
                        ThunkFactory thunkFactory = this.this$0;
                        SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                        boolean $i$f$handleException$lang = false;
                        try {
                            boolean bl = false;
                            Environment env2 = env;
                            boolean bl2 = false;
                            ExprValue value = (ExprValue)this.$valueThunk$inlined.invoke(env2);
                            ExprValue pattern = (ExprValue)this.$patternThunk$inlined.invoke(env2);
                            ExprValue escape = (ExprValue)this.$escapeThunk$inlined.invoke(env2);
                            Function0<List<PatternPart>> pps = this.$getPatternParts$1$inlined.invoke(pattern, escape);
                            exprValue2 = this.$runPatternParts$2$inlined.invoke(value, pps);
                        } catch (EvaluationException e$iv) {
                            if (e$iv.getErrorContext() == null) {
                                throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                            }
                            if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                                SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                                if (sourceLocationMeta != null) {
                                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                                    boolean bl = false;
                                    boolean bl3 = false;
                                    SourceLocationMeta it$iv = sourceLocationMeta2;
                                    boolean bl4 = false;
                                    ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                                }
                            }
                            throw (Throwable)e$iv;
                        } catch (Exception e$iv) {
                            void this_$iv;
                            R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                            throw null;
                        }
                        return exprValue2;
                    }
                };
            }
        }
        return function1;
    }

    private final Pair<String, Integer> checkPattern(IonValue pattern, SourceLocationMeta patternLocationMeta, IonValue escape, SourceLocationMeta escapeLocationMeta) {
        String string = IonValueExtensionsKt.stringValue(pattern);
        if (string == null) {
            Void void_ = ExceptionsKt.err("Must provide a non-null value for PATTERN in a LIKE predicate: " + pattern, ExceptionsKt.errorContextFrom(patternLocationMeta), false);
            throw null;
        }
        String patternString = string;
        IonValue ionValue2 = escape;
        if (ionValue2 != null) {
            String escapeCharString;
            IonValue ionValue3 = ionValue2;
            boolean bl = false;
            boolean bl2 = false;
            IonValue it = ionValue3;
            boolean bl3 = false;
            String string2 = escapeCharString = this.checkEscapeChar(escape, escapeLocationMeta);
            int n = 0;
            boolean bl4 = false;
            String string3 = string2;
            if (string3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            int escapeCharCodePoint = string3.codePointAt(n);
            Set<Integer> validEscapedChars = SetsKt.setOf(95, 37, escapeCharCodePoint);
            Iterator<Integer> iter = StringExtensionsKt.codePointSequence(patternString).iterator();
            while (iter.hasNext()) {
                int current = ((Number)iter.next()).intValue();
                if (current != escapeCharCodePoint || iter.hasNext() && validEscapedChars.contains(iter.next())) continue;
                PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(patternLocationMeta);
                ErrorCode errorCode = ErrorCode.EVALUATOR_LIKE_PATTERN_INVALID_ESCAPE_SEQUENCE;
                String string4 = "Invalid escape sequence : " + patternString;
                boolean bl5 = false;
                boolean bl6 = false;
                PropertyValueMap $this$apply = propertyValueMap;
                boolean bl7 = false;
                $this$apply.set(Property.LIKE_PATTERN, patternString);
                $this$apply.set(Property.LIKE_ESCAPE, escapeCharString);
                PropertyValueMap propertyValueMap2 = propertyValueMap;
                Void void_ = ExceptionsKt.err(string4, errorCode, propertyValueMap2, false);
                throw null;
            }
            return new Pair<String, Integer>(patternString, escapeCharCodePoint);
        }
        return new Pair<String, Object>(patternString, null);
    }

    private final String checkEscapeChar(IonValue escape, SourceLocationMeta locationMeta) {
        String escapeChar;
        boolean bl;
        String string;
        String string2;
        block9: {
            block8: {
                string2 = IonValueExtensionsKt.stringValue(escape);
                if (string2 == null) break block8;
                string = string2;
                boolean bl2 = false;
                bl = false;
                String it = string;
                boolean bl3 = false;
                string2 = it;
                if (string2 != null) break block9;
            }
            Void void_ = ExceptionsKt.err("Must provide a value when using ESCAPE in a LIKE predicate: " + escape, ExceptionsKt.errorContextFrom(locationMeta), false);
            throw null;
        }
        string = escapeChar = string2;
        switch (string.hashCode()) {
            case 0: {
                if (!string.equals("")) break;
                Void void_ = ExceptionsKt.err("Cannot use empty character as ESCAPE character in a LIKE predicate: " + escape, ExceptionsKt.errorContextFrom(locationMeta), false);
                throw null;
            }
        }
        String string3 = escapeChar;
        bl = false;
        String string4 = string3;
        if (string4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        if (((Object)StringsKt.trim((CharSequence)string4)).toString().length() != 1) {
            Void void_ = ExceptionsKt.err("Escape character must have size 1 : " + escapeChar, ExceptionsKt.errorContextFrom(locationMeta), false);
            throw null;
        }
        return escapeChar;
    }

    private final Function1<Environment, ExprValue> compileDdl(ExprNode node) {
        return new Function1(node){
            final /* synthetic */ ExprNode $node;

            @NotNull
            public final Void invoke(@NotNull Environment $noName_0) {
                Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
                PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(this.$node.getMetas());
                ErrorCode errorCode = ErrorCode.EVALUATOR_FEATURE_NOT_SUPPORTED_YET;
                String string = "DDL operations are not supported yet";
                boolean bl = false;
                boolean bl2 = false;
                PropertyValueMap it = propertyValueMap;
                boolean bl3 = false;
                it.set(Property.FEATURE_NAME, "DDL Operations");
                PropertyValueMap propertyValueMap2 = propertyValueMap;
                Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap2, false);
                throw null;
            }
            {
                this.$node = exprNode;
                super(1);
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileExec(Exec node) {
        void $this$mapTo$iv$iv;
        void args2;
        void procedureName;
        Exec exec = node;
        SymbolicName symbolicName = exec.component1();
        List<ExprNode> list = exec.component2();
        MetaContainer metas = exec.component3();
        StoredProcedure storedProcedure = this.procedures.get(procedureName.getName());
        if (storedProcedure == null) {
            PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(metas);
            ErrorCode errorCode = ErrorCode.EVALUATOR_NO_SUCH_PROCEDURE;
            String string = "No such stored procedure: " + procedureName.getName();
            boolean bl = false;
            boolean bl2 = false;
            PropertyValueMap it = propertyValueMap;
            boolean bl3 = false;
            it.set(Property.PROCEDURE_NAME, procedureName.getName());
            PropertyValueMap propertyValueMap2 = propertyValueMap;
            Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap2, false);
            throw null;
        }
        StoredProcedure procedure = storedProcedure;
        if (!procedure.getSignature().getArity().contains(args2.size())) {
            PropertyValueMap propertyValueMap = ExceptionsKt.errorContextFrom(metas);
            boolean bl = false;
            boolean it = false;
            PropertyValueMap it2 = propertyValueMap;
            boolean bl4 = false;
            it2.set(Property.EXPECTED_ARITY_MIN, procedure.getSignature().getArity().getFirst());
            it2.set(Property.EXPECTED_ARITY_MAX, procedure.getSignature().getArity().getLast());
            PropertyValueMap errorContext = propertyValueMap;
            String message = procedure.getSignature().getArity().getFirst() == 1 && procedure.getSignature().getArity().getLast() == 1 ? procedure.getSignature().getName() + " takes a single argument, received: " + args2.size() : (procedure.getSignature().getArity().getFirst() == procedure.getSignature().getArity().getLast() ? procedure.getSignature().getName() + " takes exactly " + procedure.getSignature().getArity().getFirst() + " arguments, received: " + args2.size() : procedure.getSignature().getName() + " takes between " + procedure.getSignature().getArity().getFirst() + " and " + procedure.getSignature().getArity().getLast() + " arguments, received: " + args2.size());
            throw (Throwable)new EvaluationException(message, ErrorCode.EVALUATOR_INCORRECT_NUMBER_OF_ARGUMENTS_TO_PROCEDURE_CALL, errorContext, null, false, 8, null);
        }
        Iterable $this$map$iv = (Iterable)args2;
        boolean $i$f$map = false;
        Iterable it = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it3;
            ExprNode exprNode = (ExprNode)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Function1<Environment, ExprValue> function1 = this.compileExprNode((ExprNode)it3);
            collection.add(function1);
        }
        List argThunks = (List)destination$iv$iv;
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, argThunks, procedure){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ List $argThunks$inlined;
            final /* synthetic */ StoredProcedure $procedure$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$argThunks$inlined = list;
                this.$procedure$inlined = storedProcedure;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    void $this$mapTo$iv$iv;
                    boolean bl = false;
                    Environment env2 = env;
                    boolean bl2 = false;
                    Iterable $this$map$iv = this.$argThunks$inlined;
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it;
                        Function1 function1 = (Function1)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl3 = false;
                        ExprValue exprValue3 = (ExprValue)it.invoke(env2);
                        collection.add(exprValue3);
                    }
                    List procedureArgValues = (List)destination$iv$iv;
                    exprValue2 = this.$procedure$inlined.call(env2.getSession(), procedureArgValues);
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl4 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl5 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileDate(DateTimeType.Date node) {
        void day;
        void month;
        void year2;
        DateTimeType.Date date = node;
        int n = date.component1();
        int n2 = date.component2();
        int n3 = date.component3();
        MetaContainer metas = date.component4();
        ExprValue value = this.valueFactory.newDate((int)year2, (int)month, (int)day);
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, value){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ ExprValue $value$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$value$inlined = exprValue2;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    boolean bl = false;
                    Environment it = env;
                    boolean bl2 = false;
                    exprValue2 = this.$value$inlined;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl3 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl4 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private final Function1<Environment, ExprValue> compileTime(DateTimeType.Time node) {
        void tz_minutes;
        void precision;
        void nano;
        void second;
        void minute;
        void hour;
        DateTimeType.Time time = node;
        int n = time.component1();
        int n2 = time.component2();
        int n3 = time.component3();
        int n4 = time.component4();
        int n5 = time.component5();
        Integer n6 = time.component6();
        MetaContainer metas = time.component7();
        ThunkFactory this_$iv = this.thunkFactory;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, this, (int)hour, (int)minute, (int)second, (int)nano, (int)precision, (Integer)tz_minutes){
            final /* synthetic */ ThunkFactory this$0$inline_fun;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ EvaluatingCompiler this$0;
            final /* synthetic */ int $hour$inlined;
            final /* synthetic */ int $minute$inlined;
            final /* synthetic */ int $second$inlined;
            final /* synthetic */ int $nano$inlined;
            final /* synthetic */ int $precision$inlined;
            final /* synthetic */ Integer $tz_minutes$inlined;
            {
                this.this$0$inline_fun = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.this$0 = evaluatingCompiler;
                this.$hour$inlined = n;
                this.$minute$inlined = n2;
                this.$second$inlined = n3;
                this.$nano$inlined = n4;
                this.$precision$inlined = n5;
                this.$tz_minutes$inlined = n6;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0$inline_fun;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    boolean bl = false;
                    Environment it = env;
                    boolean bl2 = false;
                    exprValue2 = EvaluatingCompiler.access$getValueFactory$p(this.this$0).newTime(Time.Companion.of(this.$hour$inlined, this.$minute$inlined, this.$second$inlined, this.$nano$inlined, this.$precision$inlined, this.$tz_minutes$inlined));
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl3 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl4 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    @NotNull
    public final ExprValue unpivot$lang(@NotNull ExprValue $this$unpivot) {
        Intrinsics.checkParameterIsNotNull($this$unpivot, "$this$unpivot");
        return $this$unpivot instanceof UnpivotedExprValue ? $this$unpivot : ($this$unpivot.getType() == ExprValueType.STRUCT ? (ExprValue)new UnpivotedExprValue($this$unpivot) : (ExprValue)new UnpivotedExprValue((Iterable<? extends ExprValue>)CollectionsKt.listOf(ExprValueExtensionsKt.namedValue($this$unpivot, this.valueFactory.newString(StandardNamesKt.syntheticColumnName(0))))));
    }

    private final ExprValue createStructExprValue(Sequence<? extends ExprValue> seq2, StructOrdering ordering) {
        Sequence<? extends ExprValue> sequence;
        switch (EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$12[this.compileOptions.getProjectionIteration().ordinal()]) {
            case 1: {
                sequence = SequencesKt.filter(seq2, createStructExprValue.1.INSTANCE);
                break;
            }
            case 2: {
                sequence = seq2;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return this.valueFactory.newStruct(sequence, ordering);
    }

    public EvaluatingCompiler(@NotNull ExprValueFactory valueFactory, @NotNull Map<String, ? extends ExprFunction> functions, @NotNull Map<String, ? extends StoredProcedure> procedures, @NotNull CompileOptions compileOptions) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        Intrinsics.checkParameterIsNotNull(functions, "functions");
        Intrinsics.checkParameterIsNotNull(procedures, "procedures");
        Intrinsics.checkParameterIsNotNull(compileOptions, "compileOptions");
        this.valueFactory = valueFactory;
        this.functions = functions;
        this.procedures = procedures;
        this.compileOptions = compileOptions;
        this.thunkFactory = new ThunkFactory(this.compileOptions.getThunkOptions());
        this.compilationContextStack = new Stack();
        this.builtinAggregates = (Map)((Function0)new Function0<Map<Pair<? extends String, ? extends SetQuantifier>, ? extends ExprAggregatorFactory>>(this){
            final /* synthetic */ EvaluatingCompiler this$0;

            @NotNull
            public final Map<Pair<String, SetQuantifier>, ExprAggregatorFactory> invoke() {
                Function2 countAccFunc2 = builtinAggregates.countAccFunc.1.INSTANCE;
                Function2 sumAccFunc2 = builtinAggregates.sumAccFunc.1.INSTANCE;
                Function2 minAccFunc2 = EvaluatingCompiler.access$comparisonAccumulator(this.this$0, builtinAggregates.minAccFunc.1.INSTANCE);
                Function2 maxAccFunc2 = EvaluatingCompiler.access$comparisonAccumulator(this.this$0, builtinAggregates.maxAccFunc.1.INSTANCE);
                Function1 avgAggregateGenerator2 = new Function1<Function1<? super ExprValue, ? extends Boolean>, builtinAggregates.avgAggregateGenerator.1>(this){
                    final /* synthetic */ builtinAggregates.1 this$0;

                    @NotNull
                    public final builtinAggregates.avgAggregateGenerator.1 invoke(@NotNull Function1<? super ExprValue, Boolean> filter) {
                        Intrinsics.checkParameterIsNotNull(filter, "filter");
                        return new ExprAggregator(this, filter){
                            @Nullable
                            private Number sum;
                            private long count;
                            final /* synthetic */ builtinAggregates.avgAggregateGenerator.1 this$0;
                            final /* synthetic */ Function1 $filter;

                            @Nullable
                            public final Number getSum() {
                                return this.sum;
                            }

                            public final void setSum(@Nullable Number number) {
                                this.sum = number;
                            }

                            public final long getCount() {
                                return this.count;
                            }

                            public final void setCount(long l) {
                                this.count = l;
                            }

                            public void next(@NotNull ExprValue value) {
                                block2: {
                                    Number number;
                                    block4: {
                                        block3: {
                                            Intrinsics.checkParameterIsNotNull(value, "value");
                                            if (!ExprValueExtensionsKt.isNotUnknown(value) || !((Boolean)this.$filter.invoke(value)).booleanValue()) break block2;
                                            builtinAggregates.avgAggregateGenerator.1 v0 = this;
                                            number = this.sum;
                                            if (number == null) break block3;
                                            Number number2 = number;
                                            builtinAggregates.avgAggregateGenerator.1 var7_4 = v0;
                                            boolean bl = false;
                                            boolean bl2 = false;
                                            Number it = number2;
                                            boolean bl3 = false;
                                            Number number3 = NumberExtensionsKt.plus(it, ExprValueExtensionsKt.numberValue(value));
                                            v0 = var7_4;
                                            number = number3;
                                            if (number != null) break block4;
                                        }
                                        number = ExprValueExtensionsKt.numberValue(value);
                                    }
                                    v0.sum = number;
                                    long l = this.count;
                                    this.count = l + 1L;
                                }
                            }

                            @NotNull
                            public ExprValue compute() {
                                Object object;
                                block3: {
                                    block2: {
                                        object = this.sum;
                                        if (object == null) break block2;
                                        Number number = object;
                                        boolean bl = false;
                                        boolean bl2 = false;
                                        Number it = number;
                                        boolean bl3 = false;
                                        object = EvaluatingCompiler.access$exprValue(this.this$0.this$0.this$0, NumberExtensionsKt.div(it, NumberExtensionsKt.bigDecimalOf$default(this.count, null, 2, null)));
                                        if (object != null) break block3;
                                    }
                                    object = EvaluatingCompiler.access$getValueFactory$p(this.this$0.this$0.this$0).getNullValue();
                                }
                                return object;
                            }
                            {
                                this.this$0 = $outer;
                                this.$filter = $captured_local_variable$1;
                            }
                        };
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                };
                Function1 allFilter2 = builtinAggregates.allFilter.1.INSTANCE;
                return MapsKt.mapOf(TuplesKt.to(new Pair<String, SetQuantifier>("count", SetQuantifier.ALL), ExprAggregatorFactory.Companion.over((Function0<? extends ExprAggregator>)new Function0<Accumulator>(this, countAccFunc2, allFilter2){
                    final /* synthetic */ builtinAggregates.1 this$0;
                    final /* synthetic */ Function2 $countAccFunc;
                    final /* synthetic */ Function1 $allFilter;

                    @NotNull
                    public final Accumulator invoke() {
                        return new Accumulator(this.this$0.this$0, 0L, this.$countAccFunc, this.$allFilter);
                    }
                    {
                        this.this$0 = var1_1;
                        this.$countAccFunc = function2;
                        this.$allFilter = function1;
                        super(0);
                    }
                })), TuplesKt.to(new Pair<String, SetQuantifier>("count", SetQuantifier.DISTINCT), ExprAggregatorFactory.Companion.over((Function0<? extends ExprAggregator>)new Function0<Accumulator>(this, countAccFunc2){
                    final /* synthetic */ builtinAggregates.1 this$0;
                    final /* synthetic */ Function2 $countAccFunc;

                    @NotNull
                    public final Accumulator invoke() {
                        return new Accumulator(this.this$0.this$0, 0L, this.$countAccFunc, ExprValueExtensionsKt.createUniqueExprValueFilter());
                    }
                    {
                        this.this$0 = var1_1;
                        this.$countAccFunc = function2;
                        super(0);
                    }
                })), TuplesKt.to(new Pair<String, SetQuantifier>("sum", SetQuantifier.ALL), ExprAggregatorFactory.Companion.over((Function0<? extends ExprAggregator>)new Function0<Accumulator>(this, sumAccFunc2, allFilter2){
                    final /* synthetic */ builtinAggregates.1 this$0;
                    final /* synthetic */ Function2 $sumAccFunc;
                    final /* synthetic */ Function1 $allFilter;

                    @NotNull
                    public final Accumulator invoke() {
                        return new Accumulator(this.this$0.this$0, null, this.$sumAccFunc, this.$allFilter);
                    }
                    {
                        this.this$0 = var1_1;
                        this.$sumAccFunc = function2;
                        this.$allFilter = function1;
                        super(0);
                    }
                })), TuplesKt.to(new Pair<String, SetQuantifier>("sum", SetQuantifier.DISTINCT), ExprAggregatorFactory.Companion.over((Function0<? extends ExprAggregator>)new Function0<Accumulator>(this, sumAccFunc2){
                    final /* synthetic */ builtinAggregates.1 this$0;
                    final /* synthetic */ Function2 $sumAccFunc;

                    @NotNull
                    public final Accumulator invoke() {
                        return new Accumulator(this.this$0.this$0, null, this.$sumAccFunc, ExprValueExtensionsKt.createUniqueExprValueFilter());
                    }
                    {
                        this.this$0 = var1_1;
                        this.$sumAccFunc = function2;
                        super(0);
                    }
                })), TuplesKt.to(new Pair<String, SetQuantifier>("avg", SetQuantifier.ALL), ExprAggregatorFactory.Companion.over((Function0<? extends ExprAggregator>)new Function0<builtinAggregates.avgAggregateGenerator.1>(avgAggregateGenerator2, allFilter2){
                    final /* synthetic */ Function1 $avgAggregateGenerator;
                    final /* synthetic */ Function1 $allFilter;

                    @NotNull
                    public final builtinAggregates.avgAggregateGenerator.1 invoke() {
                        return this.$avgAggregateGenerator.invoke(this.$allFilter);
                    }
                    {
                        this.$avgAggregateGenerator = function1;
                        this.$allFilter = function12;
                        super(0);
                    }
                })), TuplesKt.to(new Pair<String, SetQuantifier>("avg", SetQuantifier.DISTINCT), ExprAggregatorFactory.Companion.over((Function0<? extends ExprAggregator>)new Function0<builtinAggregates.avgAggregateGenerator.1>(avgAggregateGenerator2){
                    final /* synthetic */ Function1 $avgAggregateGenerator;

                    @NotNull
                    public final builtinAggregates.avgAggregateGenerator.1 invoke() {
                        return this.$avgAggregateGenerator.invoke(ExprValueExtensionsKt.createUniqueExprValueFilter());
                    }
                    {
                        this.$avgAggregateGenerator = function1;
                        super(0);
                    }
                })), TuplesKt.to(new Pair<String, SetQuantifier>("max", SetQuantifier.ALL), ExprAggregatorFactory.Companion.over((Function0<? extends ExprAggregator>)new Function0<Accumulator>(this, maxAccFunc2, allFilter2){
                    final /* synthetic */ builtinAggregates.1 this$0;
                    final /* synthetic */ Function2 $maxAccFunc;
                    final /* synthetic */ Function1 $allFilter;

                    @NotNull
                    public final Accumulator invoke() {
                        return new Accumulator(this.this$0.this$0, null, this.$maxAccFunc, this.$allFilter);
                    }
                    {
                        this.this$0 = var1_1;
                        this.$maxAccFunc = function2;
                        this.$allFilter = function1;
                        super(0);
                    }
                })), TuplesKt.to(new Pair<String, SetQuantifier>("max", SetQuantifier.DISTINCT), ExprAggregatorFactory.Companion.over((Function0<? extends ExprAggregator>)new Function0<Accumulator>(this, maxAccFunc2){
                    final /* synthetic */ builtinAggregates.1 this$0;
                    final /* synthetic */ Function2 $maxAccFunc;

                    @NotNull
                    public final Accumulator invoke() {
                        return new Accumulator(this.this$0.this$0, null, this.$maxAccFunc, ExprValueExtensionsKt.createUniqueExprValueFilter());
                    }
                    {
                        this.this$0 = var1_1;
                        this.$maxAccFunc = function2;
                        super(0);
                    }
                })), TuplesKt.to(new Pair<String, SetQuantifier>("min", SetQuantifier.ALL), ExprAggregatorFactory.Companion.over((Function0<? extends ExprAggregator>)new Function0<Accumulator>(this, minAccFunc2, allFilter2){
                    final /* synthetic */ builtinAggregates.1 this$0;
                    final /* synthetic */ Function2 $minAccFunc;
                    final /* synthetic */ Function1 $allFilter;

                    @NotNull
                    public final Accumulator invoke() {
                        return new Accumulator(this.this$0.this$0, null, this.$minAccFunc, this.$allFilter);
                    }
                    {
                        this.this$0 = var1_1;
                        this.$minAccFunc = function2;
                        this.$allFilter = function1;
                        super(0);
                    }
                })), TuplesKt.to(new Pair<String, SetQuantifier>("min", SetQuantifier.DISTINCT), ExprAggregatorFactory.Companion.over((Function0<? extends ExprAggregator>)new Function0<Accumulator>(this, minAccFunc2){
                    final /* synthetic */ builtinAggregates.1 this$0;
                    final /* synthetic */ Function2 $minAccFunc;

                    @NotNull
                    public final Accumulator invoke() {
                        return new Accumulator(this.this$0.this$0, null, this.$minAccFunc, ExprValueExtensionsKt.createUniqueExprValueFilter());
                    }
                    {
                        this.this$0 = var1_1;
                        this.$minAccFunc = function2;
                        super(0);
                    }
                })));
            }
            {
                this.this$0 = evaluatingCompiler;
                super(0);
            }
        }).invoke();
    }

    public /* synthetic */ EvaluatingCompiler(ExprValueFactory exprValueFactory, Map map2, Map map3, CompileOptions compileOptions, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            compileOptions = CompileOptions.Companion.standard();
        }
        this(exprValueFactory, map2, map3, compileOptions);
    }

    public static final /* synthetic */ Function1 access$compileExprNode(EvaluatingCompiler $this, ExprNode expr) {
        return $this.compileExprNode(expr);
    }

    public static final /* synthetic */ ExprValue access$exprValue(EvaluatingCompiler $this, boolean $this$access_u24exprValue) {
        return $this.exprValue($this$access_u24exprValue);
    }

    public static final /* synthetic */ ExprValue access$exprValue(EvaluatingCompiler $this, String $this$access_u24exprValue) {
        return $this.exprValue($this$access_u24exprValue);
    }

    public static final /* synthetic */ ExprValue access$createStructExprValue(EvaluatingCompiler $this, Sequence seq2, StructOrdering ordering) {
        return $this.createStructExprValue(seq2, ordering);
    }

    public static final /* synthetic */ List access$compileLetSources(EvaluatingCompiler $this, LetSource letSource) {
        return $this.compileLetSources(letSource);
    }

    public static final /* synthetic */ Function1 access$compileQueryWithoutProjection(EvaluatingCompiler $this, Select ast, List compiledSources, List compiledLetSources) {
        return $this.compileQueryWithoutProjection(ast, compiledSources, compiledLetSources);
    }

    public static final /* synthetic */ ThunkFactory access$getThunkFactory$p(EvaluatingCompiler $this) {
        return $this.thunkFactory;
    }

    public static final /* synthetic */ long access$evalLimit(EvaluatingCompiler $this, Function1 limitThunk, Environment env, SourceLocationMeta limitLocationMeta) {
        return $this.evalLimit(limitThunk, env, limitLocationMeta);
    }

    public static final /* synthetic */ List access$compileGroupByExpressions(EvaluatingCompiler $this, List groupByItems) {
        return $this.compileGroupByExpressions(groupByItems);
    }

    public static final /* synthetic */ Function1 access$compileGroupKeyThunk(EvaluatingCompiler $this, List compiledGroupByItems, MetaContainer selectMetas) {
        return $this.compileGroupKeyThunk(compiledGroupByItems, selectMetas);
    }

    public static final /* synthetic */ Function2 access$createFilterHavingAndProjectClosure(EvaluatingCompiler $this, Function1 havingThunk, Function2 selectProjectionThunk) {
        return $this.createFilterHavingAndProjectClosure(havingThunk, selectProjectionThunk);
    }

    public static final /* synthetic */ Function2 access$createGetGroupEnvClosure(EvaluatingCompiler $this, SymbolicName groupAsName) {
        return $this.createGetGroupEnvClosure(groupAsName);
    }

    public static final /* synthetic */ Object access$nestCompilationContext(EvaluatingCompiler $this, ExpressionContext expressionContext, Set fromSourceNames, Function0 block) {
        return $this.nestCompilationContext(expressionContext, fromSourceNames, block);
    }

    public static final /* synthetic */ List access$compileSelectListToProjectionElements(EvaluatingCompiler $this, SelectProjectionList selectList) {
        return $this.compileSelectListToProjectionElements(selectList);
    }

    public static final /* synthetic */ CompileOptions access$getCompileOptions$p(EvaluatingCompiler $this) {
        return $this.compileOptions;
    }

    public static final /* synthetic */ Pair access$checkPattern(EvaluatingCompiler $this, IonValue pattern, SourceLocationMeta patternLocationMeta, IonValue escape, SourceLocationMeta escapeLocationMeta) {
        return $this.checkPattern(pattern, patternLocationMeta, escape, escapeLocationMeta);
    }

    public static final /* synthetic */ Function2 access$comparisonAccumulator(EvaluatingCompiler $this, Function2 cmpFunc) {
        return $this.comparisonAccumulator(cmpFunc);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0016\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00030\u0007j\u0002`\t\u00a2\u0006\u0002\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR!\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00030\u0007j\u0002`\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0011"}, d2={"Lorg/partiql/lang/eval/EvaluatingCompiler$CompiledGroupByItem;", "", "alias", "Lorg/partiql/lang/eval/ExprValue;", "uniqueId", "", "thunk", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Environment;", "Lorg/partiql/lang/eval/ThunkEnv;", "(Lorg/partiql/lang/eval/ExprValue;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "getAlias", "()Lorg/partiql/lang/eval/ExprValue;", "getThunk", "()Lkotlin/jvm/functions/Function1;", "getUniqueId", "()Ljava/lang/String;", "lang"})
    private static final class CompiledGroupByItem {
        @NotNull
        private final ExprValue alias;
        @Nullable
        private final String uniqueId;
        @NotNull
        private final Function1<Environment, ExprValue> thunk;

        @NotNull
        public final ExprValue getAlias() {
            return this.alias;
        }

        @Nullable
        public final String getUniqueId() {
            return this.uniqueId;
        }

        @NotNull
        public final Function1<Environment, ExprValue> getThunk() {
            return this.thunk;
        }

        public CompiledGroupByItem(@NotNull ExprValue alias, @Nullable String uniqueId, @NotNull Function1<? super Environment, ? extends ExprValue> thunk2) {
            Intrinsics.checkParameterIsNotNull(alias, "alias");
            Intrinsics.checkParameterIsNotNull(thunk2, "thunk");
            this.alias = alias;
            this.uniqueId = uniqueId;
            this.thunk = thunk2;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2={"Lorg/partiql/lang/eval/EvaluatingCompiler$FromSourceBindingNamePair;", "", "bindingName", "Lorg/partiql/lang/eval/BindingName;", "nameExprValue", "Lorg/partiql/lang/eval/ExprValue;", "(Lorg/partiql/lang/eval/BindingName;Lorg/partiql/lang/eval/ExprValue;)V", "getBindingName", "()Lorg/partiql/lang/eval/BindingName;", "getNameExprValue", "()Lorg/partiql/lang/eval/ExprValue;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "lang"})
    private static final class FromSourceBindingNamePair {
        @NotNull
        private final BindingName bindingName;
        @NotNull
        private final ExprValue nameExprValue;

        @NotNull
        public final BindingName getBindingName() {
            return this.bindingName;
        }

        @NotNull
        public final ExprValue getNameExprValue() {
            return this.nameExprValue;
        }

        public FromSourceBindingNamePair(@NotNull BindingName bindingName, @NotNull ExprValue nameExprValue) {
            Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
            Intrinsics.checkParameterIsNotNull(nameExprValue, "nameExprValue");
            this.bindingName = bindingName;
            this.nameExprValue = nameExprValue;
        }

        @NotNull
        public final BindingName component1() {
            return this.bindingName;
        }

        @NotNull
        public final ExprValue component2() {
            return this.nameExprValue;
        }

        @NotNull
        public final FromSourceBindingNamePair copy(@NotNull BindingName bindingName, @NotNull ExprValue nameExprValue) {
            Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
            Intrinsics.checkParameterIsNotNull(nameExprValue, "nameExprValue");
            return new FromSourceBindingNamePair(bindingName, nameExprValue);
        }

        public static /* synthetic */ FromSourceBindingNamePair copy$default(FromSourceBindingNamePair fromSourceBindingNamePair, BindingName bindingName, ExprValue exprValue2, int n, Object object) {
            if ((n & 1) != 0) {
                bindingName = fromSourceBindingNamePair.bindingName;
            }
            if ((n & 2) != 0) {
                exprValue2 = fromSourceBindingNamePair.nameExprValue;
            }
            return fromSourceBindingNamePair.copy(bindingName, exprValue2);
        }

        @NotNull
        public String toString() {
            return "FromSourceBindingNamePair(bindingName=" + this.bindingName + ", nameExprValue=" + this.nameExprValue + ")";
        }

        public int hashCode() {
            BindingName bindingName = this.bindingName;
            ExprValue exprValue2 = this.nameExprValue;
            return (bindingName != null ? ((Object)bindingName).hashCode() : 0) * 31 + (exprValue2 != null ? exprValue2.hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof FromSourceBindingNamePair)) break block3;
                    FromSourceBindingNamePair fromSourceBindingNamePair = (FromSourceBindingNamePair)object;
                    if (!Intrinsics.areEqual(this.bindingName, fromSourceBindingNamePair.bindingName) || !Intrinsics.areEqual(this.nameExprValue, fromSourceBindingNamePair.nameExprValue)) break block3;
                }
                return true;
            }
            return false;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001BC\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u001a\u0010\u0004\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0002\u0010\nJ\b\u0010\u0013\u001a\u00020\u0006H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0006H\u0016R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR%\u0010\u0004\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0017"}, d2={"Lorg/partiql/lang/eval/EvaluatingCompiler$Accumulator;", "Lorg/partiql/lang/eval/ExprAggregator;", "current", "", "nextFunc", "Lkotlin/Function2;", "Lorg/partiql/lang/eval/ExprValue;", "valueFilter", "Lkotlin/Function1;", "", "(Lorg/partiql/lang/eval/EvaluatingCompiler;Ljava/lang/Number;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)V", "getCurrent", "()Ljava/lang/Number;", "setCurrent", "(Ljava/lang/Number;)V", "getNextFunc", "()Lkotlin/jvm/functions/Function2;", "getValueFilter", "()Lkotlin/jvm/functions/Function1;", "compute", "next", "", "value", "lang"})
    private final class Accumulator
    implements ExprAggregator {
        @Nullable
        private Number current;
        @NotNull
        private final Function2<Number, ExprValue, Number> nextFunc;
        @NotNull
        private final Function1<ExprValue, Boolean> valueFilter;
        final /* synthetic */ EvaluatingCompiler this$0;

        @Override
        public void next(@NotNull ExprValue value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            if (ExprValueExtensionsKt.isNotUnknown(value) && this.valueFilter.invoke(value).booleanValue()) {
                this.current = this.nextFunc.invoke(this.current, value);
            }
        }

        @Override
        @NotNull
        public ExprValue compute() {
            Object object = this.current;
            if (object == null || (object = this.this$0.exprValue((Number)object)) == null) {
                object = this.this$0.valueFactory.getNullValue();
            }
            return object;
        }

        @Nullable
        public final Number getCurrent() {
            return this.current;
        }

        public final void setCurrent(@Nullable Number number) {
            this.current = number;
        }

        @NotNull
        public final Function2<Number, ExprValue, Number> getNextFunc() {
            return this.nextFunc;
        }

        @NotNull
        public final Function1<ExprValue, Boolean> getValueFilter() {
            return this.valueFilter;
        }

        public Accumulator(@Nullable EvaluatingCompiler $outer, @NotNull Number current, @NotNull Function2<? super Number, ? super ExprValue, ? extends Number> nextFunc, Function1<? super ExprValue, Boolean> valueFilter) {
            Intrinsics.checkParameterIsNotNull(nextFunc, "nextFunc");
            Intrinsics.checkParameterIsNotNull(valueFilter, "valueFilter");
            this.this$0 = $outer;
            this.current = current;
            this.nextFunc = nextFunc;
            this.valueFilter = valueFilter;
        }

        public /* synthetic */ Accumulator(EvaluatingCompiler evaluatingCompiler, Number number, Function2 function2, Function1 function1, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 1) != 0) {
                number = 0L;
            }
            if ((n & 4) != 0) {
                function1 = 1.INSTANCE;
            }
            this(evaluatingCompiler, number, function2, function1);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u000fH\u0096\u0002R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lorg/partiql/lang/eval/EvaluatingCompiler$UnpivotedExprValue;", "Lorg/partiql/lang/eval/BaseExprValue;", "values", "", "Lorg/partiql/lang/eval/ExprValue;", "(Ljava/lang/Iterable;)V", "ionValue", "", "getIonValue", "()Ljava/lang/Void;", "type", "Lorg/partiql/lang/eval/ExprValueType;", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "iterator", "", "lang"})
    private static final class UnpivotedExprValue
    extends BaseExprValue {
        @NotNull
        private final ExprValueType type;
        private final Iterable<ExprValue> values;

        @Override
        @NotNull
        public ExprValueType getType() {
            return this.type;
        }

        @Override
        @NotNull
        public Iterator<ExprValue> iterator() {
            return this.values.iterator();
        }

        @NotNull
        public Void getIonValue() {
            throw (Throwable)new UnsupportedOperationException("Synthetic value cannot provide ion value");
        }

        public UnpivotedExprValue(@NotNull Iterable<? extends ExprValue> values2) {
            Intrinsics.checkParameterIsNotNull(values2, "values");
            this.values = values2;
            this.type = ExprValueType.BAG;
        }
    }
}

