/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonBool;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.Assignment;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.AstDeserializationKt;
import org.partiql.lang.ast.AstDeserializerInternal;
import org.partiql.lang.ast.AstDeserializerInternal$WhenMappings;
import org.partiql.lang.ast.AstKt;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.CallAgg;
import org.partiql.lang.ast.CaseSensitivity;
import org.partiql.lang.ast.CreateIndex;
import org.partiql.lang.ast.CreateTable;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.DataType;
import org.partiql.lang.ast.DmlOpList;
import org.partiql.lang.ast.DropIndex;
import org.partiql.lang.ast.DropTable;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.FromSourceExpr;
import org.partiql.lang.ast.FromSourceJoin;
import org.partiql.lang.ast.FromSourceUnpivot;
import org.partiql.lang.ast.GroupBy;
import org.partiql.lang.ast.GroupByItem;
import org.partiql.lang.ast.GroupingStrategy;
import org.partiql.lang.ast.InsertOp;
import org.partiql.lang.ast.InsertValueOp;
import org.partiql.lang.ast.IsImplictJoinMeta;
import org.partiql.lang.ast.JoinOp;
import org.partiql.lang.ast.LegacyLogicalNotMeta;
import org.partiql.lang.ast.LetVariables;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.MetaDeserializer;
import org.partiql.lang.ast.MetaKt;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.NodeTag;
import org.partiql.lang.ast.OnConflict;
import org.partiql.lang.ast.Parameter;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.PathComponent;
import org.partiql.lang.ast.PathComponentExpr;
import org.partiql.lang.ast.PathComponentUnpivot;
import org.partiql.lang.ast.PathComponentWildcard;
import org.partiql.lang.ast.RemoveOp;
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
import org.partiql.lang.ast.SexpValidationRules;
import org.partiql.lang.ast.SimpleCase;
import org.partiql.lang.ast.SimpleCaseWhen;
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.ast.SqlDataType;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.StructField;
import org.partiql.lang.ast.SymbolicName;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.TypedOp;
import org.partiql.lang.ast.UtilKt;
import org.partiql.lang.ast.VariableReference;
import org.partiql.lang.util.IonValueExtensionsKt;
import org.partiql.lang.util.ThreadInterruptUtilsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u00a6\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0011J\u001e\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010\u001e\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010\u001f\u001a\u00020\u00152\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0018\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001a2\u0006\u0010!\u001a\u00020\u0011H\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001bH\u0002J\u0010\u0010'\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001bH\u0002J\u001e\u0010(\u001a\u00020)2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010*\u001a\u00020+2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0015\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020\u0011H\u0000\u00a2\u0006\u0002\b.J\u001c\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0015002\u0006\u00101\u001a\u00020\u0011H\u0002J\"\u00102\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0015000\u001a2\u0006\u00103\u001a\u00020\u0011H\u0002J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u0011H\u0002J \u00107\u001a\u0002082\u0006\u0010!\u001a\u00020\u00112\u0006\u00109\u001a\u00020:2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J&\u0010;\u001a\u00020<2\u0006\u0010!\u001a\u00020\u00112\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J&\u0010=\u001a\u00020>2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u00109\u001a\u00020:2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001a\u0010?\u001a\u0002052\u0006\u00106\u001a\u00020\u00112\b\b\u0002\u00109\u001a\u00020:H\u0002J\u0010\u0010@\u001a\u00020A2\u0006\u0010!\u001a\u00020\u0011H\u0002J\u001e\u0010B\u001a\u00020C2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\"\u0010D\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020E002\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0002J5\u0010F\u001a\u0002HG\"\u0004\b\u0000\u0010G2\u0006\u0010H\u001a\u00020\u001b2\u0018\u0010I\u001a\u0014\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u0002HG0JH\u0002\u00a2\u0006\u0002\u0010KJ\u001e\u0010L\u001a\u00020M2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010N\u001a\u00020O2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010P\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010R\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010S\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010T\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010U\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010V\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010W\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010X\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010Y\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010Z\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010[\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010\\\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010]\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010^\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010_\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010`\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010a\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010b\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010c\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010d\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010e\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010f\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010g\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010h\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010i\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010j\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010k\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010l\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020\u0011H\u0002J\u001c\u0010p\u001a\b\u0012\u0004\u0012\u00020q0\u001a2\f\u0010r\u001a\b\u0012\u0004\u0012\u00020\u00110\u001aH\u0002J\u0010\u0010s\u001a\u00020t2\u0006\u0010u\u001a\u00020\u0011H\u0002J\u001c\u0010v\u001a\u00020w2\u0012\u0010x\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u0007H\u0002J\u001e\u0010y\u001a\u00020C2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0018\u0010z\u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0018\u0010{\u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010|\u001a\u00020}2\u0006\u0010~\u001a\u00020\u0011H\u0002J\u0012\u0010\u007f\u001a\u00030\u0080\u00012\u0007\u0010\u0081\u0001\u001a\u00020\u0011H\u0002J(\u0010\u0082\u0001\u001a\u00030\u0083\u00012\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001f\u0010\u0084\u0001\u001a\t\u0012\u0005\u0012\u00030\u0085\u00010\u001a2\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u001aH\u0002J8\u0010\u0087\u0001\u001a\u0002HG\"\u0004\b\u0000\u0010G2\u0007\u0010\u0088\u0001\u001a\u00020\u00112\u0018\u0010I\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u0002HG0JH\u0002\u00a2\u0006\u0003\u0010\u0089\u0001J\u0019\u0010\u008a\u0001\u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J \u0010\u008b\u0001\u001a\u00030\u008c\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J \u0010\u008d\u0001\u001a\u00030\u008c\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J \u0010\u008e\u0001\u001a\u00030\u008f\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J \u0010\u0090\u0001\u001a\u00030\u008f\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001f\u0010\u0091\u0001\u001a\u00020Q2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0019\u0010\u0092\u0001\u001a\u00030\u0093\u00012\u0007\u0010\u0094\u0001\u001a\u00020\u0011H\u0000\u00a2\u0006\u0003\b\u0095\u0001J\u0019\u0010\u0096\u0001\u001a\b\u0012\u0004\u0012\u00020\u00150\u001a*\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0002J\u0018\u0010\u0097\u0001\u001a\u0004\u0018\u00010\u001b*\u00020\u00112\u0007\u0010\u0098\u0001\u001a\u00020\bH\u0002J\u0010\u0010\u0099\u0001\u001a\u0005\u0018\u00010\u009a\u0001*\u00020\u001bH\u0002J\u0018\u0010\u009b\u0001\u001a\u0005\u0018\u00010\u009a\u0001*\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u000f\u001a\u00020\u0010*\u00020\u00118BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u009c\u0001"}, d2={"Lorg/partiql/lang/ast/AstDeserializerInternal;", "", "astVersion", "Lorg/partiql/lang/ast/AstVersion;", "ion", "Lcom/amazon/ion/IonSystem;", "metaDeserializers", "", "", "Lorg/partiql/lang/ast/MetaDeserializer;", "(Lorg/partiql/lang/ast/AstVersion;Lcom/amazon/ion/IonSystem;Ljava/util/Map;)V", "getAstVersion", "()Lorg/partiql/lang/ast/AstVersion;", "getIon", "()Lcom/amazon/ion/IonSystem;", "nodeTag", "Lorg/partiql/lang/ast/NodeTag;", "Lcom/amazon/ion/IonSexp;", "getNodeTag", "(Lcom/amazon/ion/IonSexp;)Lorg/partiql/lang/ast/NodeTag;", "deserialize", "Lorg/partiql/lang/ast/ExprNode;", "sexp", "deserializeCallAgg", "Lorg/partiql/lang/ast/CallAgg;", "targetArgs", "", "Lcom/amazon/ion/IonValue;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "deserializeCallAggWildcard", "deserializeCreateV0", "deserializeDataManipulation", "target", "deserializeDataManipulationOperation", "Lorg/partiql/lang/ast/DataManipulationOperation;", "deserializeDataType", "Lorg/partiql/lang/ast/DataType;", "dataTypeSexp", "deserializeDataTypeV0", "deserializeDropIndexV0", "Lorg/partiql/lang/ast/DropIndex;", "deserializeDropTableV0", "Lorg/partiql/lang/ast/DropTable;", "deserializeExprNode", "metaOrTermOrExp", "deserializeExprNode$lang", "deserializeExprPair", "Lkotlin/Pair;", "expr_pair", "deserializeExprPairList", "exprPairList", "deserializeFromSource", "Lorg/partiql/lang/ast/FromSource;", "termOrFromSource", "deserializeFromSourceExprV0", "Lorg/partiql/lang/ast/FromSourceExpr;", "variables", "Lorg/partiql/lang/ast/LetVariables;", "deserializeFromSourceJoinV0", "Lorg/partiql/lang/ast/FromSourceJoin;", "deserializeFromSourceUnpivotV0", "Lorg/partiql/lang/ast/FromSourceUnpivot;", "deserializeFromSourceV0", "deserializeGroupByItem", "Lorg/partiql/lang/ast/GroupByItem;", "deserializeId", "Lorg/partiql/lang/ast/VariableReference;", "deserializeIdentifier", "Lorg/partiql/lang/ast/CaseSensitivity;", "deserializeIonValueMetaOrTerm", "T", "targetValue", "deserializeNode", "Lkotlin/Function2;", "(Lcom/amazon/ion/IonValue;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "deserializeLit", "Lorg/partiql/lang/ast/Literal;", "deserializeMissing", "Lorg/partiql/lang/ast/LiteralMissing;", "deserializeNAryAdd", "Lorg/partiql/lang/ast/NAry;", "deserializeNAryAnd", "deserializeNAryBetween", "deserializeNAryCall", "deserializeNAryDiv", "deserializeNAryEq", "deserializeNAryExcept", "deserializeNAryExceptAll", "deserializeNAryGt", "deserializeNAryGte", "deserializeNAryIn", "deserializeNAryIntersect", "deserializeNAryIntersectAll", "deserializeNAryLike", "deserializeNAryLt", "deserializeNAryLte", "deserializeNAryMod", "deserializeNAryMul", "deserializeNAryNe", "deserializeNAryNot", "deserializeNAryNotBetween", "deserializeNAryNotIn", "deserializeNAryNotLlike", "deserializeNAryOr", "deserializeNAryStringConcat", "deserializeNArySub", "deserializeNAryUnion", "deserializeNAryUnionAll", "deserializePath", "Lorg/partiql/lang/ast/Path;", "pathSexp", "deserializePathComponents", "Lorg/partiql/lang/ast/PathComponent;", "componentSexps", "deserializePathExpr", "Lorg/partiql/lang/ast/PathComponentExpr;", "pathExpr", "deserializeProjectionPivotV0", "Lorg/partiql/lang/ast/SelectProjectionPivot;", "children", "deserializeScopeQualifier", "deserializeSearchedCase", "deserializeSelect", "deserializeSelectListItems", "Lorg/partiql/lang/ast/SelectProjectionList;", "projectChild", "deserializeSelectValueOrListV0", "Lorg/partiql/lang/ast/SelectProjection;", "project", "deserializeSeq", "Lorg/partiql/lang/ast/Seq;", "deserializeSetAssignments", "Lorg/partiql/lang/ast/Assignment;", "targets", "deserializeSexpMetaOrTerm", "targetSexp", "(Lcom/amazon/ion/IonSexp;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "deserializeSimpleCase", "deserializeStruct", "Lorg/partiql/lang/ast/Struct;", "deserializeStructV0", "deserializeTypedCast", "Lorg/partiql/lang/ast/Typed;", "deserializeTypedIs", "deserializeTypedIsNot", "validate", "", "rootSexp", "validate$lang", "deserializeAllExprNodes", "singleWrappedChildWithTagOrNull", "tagName", "termToSymbolicName", "Lorg/partiql/lang/ast/SymbolicName;", "toSymbolicName", "lang"})
public final class AstDeserializerInternal {
    @NotNull
    private final AstVersion astVersion;
    @NotNull
    private final IonSystem ion;
    private final Map<String, MetaDeserializer> metaDeserializers;

    private final NodeTag getNodeTag(@NotNull IonSexp $this$nodeTag) {
        NodeTag nodeTag = NodeTag.Companion.fromTagText(IonValueExtensionsKt.getTagText($this$nodeTag));
        if (nodeTag == null) {
            Void void_ = AstDeserializationKt.access$err("Unknown tag: '" + IonValueExtensionsKt.getTagText($this$nodeTag) + '\'');
            throw null;
        }
        return nodeTag;
    }

    @NotNull
    public final ExprNode deserialize(@NotNull IonSexp sexp) {
        Intrinsics.checkParameterIsNotNull(sexp, "sexp");
        this.validate$lang(sexp);
        return this.deserializeExprNode$lang(sexp);
    }

    /*
     * WARNING - void declaration
     */
    public final void validate$lang(@NotNull IonSexp rootSexp) {
        Intrinsics.checkParameterIsNotNull(rootSexp, "rootSexp");
        ThreadInterruptUtilsKt.checkThreadInterrupted();
        NodeTag nodeTag = this.getNodeTag(rootSexp);
        List<IonValue> nodeArgs = IonValueExtensionsKt.getArgs(rootSexp);
        SexpValidationRules sexpValidationRules = nodeTag.getDefinition().getValidationRules().get((Object)this.astVersion);
        if (sexpValidationRules == null) {
            Void void_ = AstDeserializationKt.access$err("Tag '" + nodeTag.getDefinition().getTagText() + "' is not valid for AST version " + this.astVersion.getNumber() + '.');
            throw null;
        }
        SexpValidationRules rules = sexpValidationRules;
        if (!rules.getArityRange().contains(nodeArgs.size())) {
            Void void_ = AstDeserializationKt.access$err("Incorrect arity of " + nodeArgs.size() + " for node '" + nodeTag.getDefinition().getTagText() + "'.  Expected " + rules.getArityRange());
            throw null;
        }
        if (nodeTag != NodeTag.LIT) {
            void $this$filterIsInstanceTo$iv$iv;
            Iterable $this$filterIsInstance$iv = nodeArgs;
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof IonSexp)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$forEach$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                IonSexp it = (IonSexp)element$iv;
                boolean bl = false;
                this.validate$lang(it);
            }
        }
    }

    private final <T> T deserializeSexpMetaOrTerm(IonSexp targetSexp, Function2<? super IonSexp, ? super MetaContainer, ? extends T> deserializeNode) {
        return this.deserializeIonValueMetaOrTerm(targetSexp, new Function2<IonValue, MetaContainer, T>(deserializeNode){
            final /* synthetic */ Function2 $deserializeNode;

            public final T invoke(@NotNull IonValue target, @NotNull MetaContainer metas) {
                Intrinsics.checkParameterIsNotNull(target, "target");
                Intrinsics.checkParameterIsNotNull(metas, "metas");
                return (T)this.$deserializeNode.invoke(IonValueExtensionsKt.asIonSexp(target), metas);
            }
            {
                this.$deserializeNode = function2;
                super(2);
            }
        });
    }

    private final <T> T deserializeIonValueMetaOrTerm(IonValue targetValue, Function2<? super IonValue, ? super MetaContainer, ? extends T> deserializeNode) {
        T t;
        IonValue ionValue2 = targetValue;
        if (!(ionValue2 instanceof IonSexp)) {
            t = deserializeNode.invoke(targetValue, MetaKt.getEmptyMetaContainer());
        } else {
            String string = IonValueExtensionsKt.getTagText((IonSexp)targetValue);
            switch (string.hashCode()) {
                case 3347973: {
                    if (string.equals("meta")) {
                        IonStruct struct = IonValueExtensionsKt.asIonStruct(IonValueExtensionsKt.getArgs((IonSexp)targetValue).get(1));
                        long lineNum = IonValueExtensionsKt.longValue(IonValueExtensionsKt.field(struct, "line"));
                        long charOffset = IonValueExtensionsKt.longValue(IonValueExtensionsKt.field(struct, "column"));
                        SourceLocationMeta locationMeta = new SourceLocationMeta(lineNum, charOffset, 0L, 4, null);
                        IonValue expSexp = IonValueExtensionsKt.getArgs((IonSexp)targetValue).get(0);
                        t = deserializeNode.invoke(expSexp, MetaKt.metaContainerOf(locationMeta));
                        break;
                    }
                }
                default: {
                    t = deserializeNode.invoke(targetValue, MetaKt.getEmptyMetaContainer());
                }
            }
        }
        return t;
    }

    /*
     * WARNING - void declaration
     */
    private final List<ExprNode> deserializeAllExprNodes(@NotNull List<? extends IonValue> $this$deserializeAllExprNodes) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = $this$deserializeAllExprNodes;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            IonValue ionValue2 = (IonValue)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            ExprNode exprNode = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp((IonValue)it));
            collection.add(exprNode);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public final ExprNode deserializeExprNode$lang(@NotNull IonSexp metaOrTermOrExp) {
        Intrinsics.checkParameterIsNotNull(metaOrTermOrExp, "metaOrTermOrExp");
        ThreadInterruptUtilsKt.checkThreadInterrupted();
        return (ExprNode)this.deserializeSexpMetaOrTerm(metaOrTermOrExp, (Function2)new Function2<IonSexp, MetaContainer, ExprNode>(this){
            final /* synthetic */ AstDeserializerInternal this$0;

            @NotNull
            public final ExprNode invoke(@NotNull IonSexp target, @NotNull MetaContainer metas) {
                ExprNode exprNode;
                Intrinsics.checkParameterIsNotNull(target, "target");
                Intrinsics.checkParameterIsNotNull(metas, "metas");
                NodeTag nodeTag = AstDeserializerInternal.access$getNodeTag$p(this.this$0, target);
                List<IonValue> targetArgs = IonValueExtensionsKt.getArgs(target);
                switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[nodeTag.ordinal()]) {
                    case 1: {
                        exprNode = AstDeserializerInternal.access$deserializeLit(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 2: {
                        exprNode = AstDeserializerInternal.access$deserializeMissing(this.this$0, metas);
                        break;
                    }
                    case 3: {
                        exprNode = AstDeserializerInternal.access$deserializeId(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 4: {
                        exprNode = AstDeserializerInternal.access$deserializeScopeQualifier(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 5: {
                        exprNode = AstDeserializerInternal.access$deserializeSelect(this.this$0, target, metas);
                        break;
                    }
                    case 6: {
                        exprNode = AstDeserializerInternal.access$deserializeSelect(this.this$0, target, metas);
                        break;
                    }
                    case 7: {
                        exprNode = AstDeserializerInternal.access$deserializeDataManipulation(this.this$0, target, metas);
                        break;
                    }
                    case 8: {
                        exprNode = AstDeserializerInternal.access$deserializePath(this.this$0, target);
                        break;
                    }
                    case 9: {
                        exprNode = AstDeserializerInternal.access$deserializeCallAgg(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 10: {
                        exprNode = AstDeserializerInternal.access$deserializeCallAggWildcard(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 11: {
                        exprNode = AstDeserializerInternal.access$deserializeStruct(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 12: {
                        IonValue ionValue2 = target.get(1);
                        Intrinsics.checkExpressionValueIsNotNull(ionValue2, "target[1]");
                        exprNode = new Parameter(IonValueExtensionsKt.asIonInt(ionValue2).intValue(), metas);
                        break;
                    }
                    case 13: 
                    case 14: 
                    case 15: {
                        exprNode = AstDeserializerInternal.access$deserializeSeq(this.this$0, nodeTag, targetArgs, metas);
                        break;
                    }
                    case 16: {
                        exprNode = AstDeserializerInternal.access$deserializeSimpleCase(this.this$0, target, metas);
                        break;
                    }
                    case 17: {
                        exprNode = AstDeserializerInternal.access$deserializeSearchedCase(this.this$0, target, metas);
                        break;
                    }
                    case 18: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryNot(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 19: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryAdd(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 20: {
                        exprNode = AstDeserializerInternal.access$deserializeNArySub(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 21: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryMul(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 22: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryDiv(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 23: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryMod(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 24: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryGt(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 25: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryGte(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 26: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryLt(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 27: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryLte(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 28: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryEq(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 29: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryNe(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 30: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryIn(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 31: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryNotIn(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 32: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryAnd(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 33: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryOr(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 34: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryLike(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 35: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryNotLlike(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 36: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryBetween(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 37: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryNotBetween(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 38: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryStringConcat(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 39: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryCall(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 40: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryUnion(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 41: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryUnionAll(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 42: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryExcept(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 43: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryExceptAll(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 44: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryIntersect(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 45: {
                        exprNode = AstDeserializerInternal.access$deserializeNAryIntersectAll(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 46: {
                        exprNode = AstDeserializerInternal.access$deserializeTypedIs(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 47: {
                        exprNode = AstDeserializerInternal.access$deserializeTypedIsNot(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 48: {
                        exprNode = AstDeserializerInternal.access$deserializeTypedCast(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 49: {
                        exprNode = AstDeserializerInternal.access$deserializeCreateV0(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 50: {
                        exprNode = AstDeserializerInternal.access$deserializeDropIndexV0(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 51: {
                        exprNode = AstDeserializerInternal.access$deserializeDropTableV0(this.this$0, targetArgs, metas);
                        break;
                    }
                    case 52: 
                    case 53: 
                    case 54: 
                    case 55: 
                    case 56: 
                    case 57: 
                    case 58: 
                    case 59: 
                    case 60: 
                    case 61: 
                    case 62: 
                    case 63: 
                    case 64: 
                    case 65: 
                    case 66: 
                    case 67: 
                    case 68: 
                    case 69: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 73: 
                    case 74: 
                    case 75: 
                    case 76: 
                    case 77: 
                    case 78: 
                    case 79: 
                    case 80: 
                    case 81: 
                    case 82: 
                    case 83: 
                    case 84: 
                    case 85: 
                    case 86: {
                        Void void_ = AstDeserializationKt.access$errInvalidContext(nodeTag);
                        throw null;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                return exprNode;
            }
            {
                this.this$0 = astDeserializerInternal;
                super(2);
            }
        });
    }

    private final Literal deserializeLit(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new Literal(CollectionsKt.first(targetArgs), metas);
    }

    private final LiteralMissing deserializeMissing(MetaContainer metas) {
        return new LiteralMissing(metas);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final VariableReference deserializeId(List<? extends IonValue> targetArgs, MetaContainer metas) {
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$1[this.astVersion.ordinal()]) {
            case 1: {
                String string = IonValueExtensionsKt.asIonSymbol(targetArgs.get(0)).stringValue();
                Intrinsics.checkExpressionValueIsNotNull(string, "targetArgs[0].asIonSymbol().stringValue()");
                String string2 = IonValueExtensionsKt.asIonSymbol(targetArgs.get(1)).stringValue();
                Intrinsics.checkExpressionValueIsNotNull(string2, "targetArgs[1].asIonSymbol().stringValue()");
                return new VariableReference(string, CaseSensitivity.Companion.fromSymbol(string2), ScopeQualifier.UNQUALIFIED, metas);
            }
        }
        throw new NoWhenBranchMatchedException();
    }

    private final VariableReference deserializeScopeQualifier(List<? extends IonValue> targetArgs, MetaContainer metas) {
        IonSexp qualifiedSexp = IonValueExtensionsKt.asIonSexp(targetArgs.get(0));
        String string = IonValueExtensionsKt.asIonSymbol(IonValueExtensionsKt.getArgs(qualifiedSexp).get(0)).stringValue();
        if (string == null) {
            Intrinsics.throwNpe();
        }
        String string2 = IonValueExtensionsKt.asIonSymbol(IonValueExtensionsKt.getArgs(qualifiedSexp).get(1)).stringValue();
        Intrinsics.checkExpressionValueIsNotNull(string2, "qualifiedSexp.args[1].asIonSymbol().stringValue()");
        return new VariableReference(string, CaseSensitivity.Companion.fromSymbol(string2), ScopeQualifier.LEXICAL, metas);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final CallAgg deserializeCallAgg(List<? extends IonValue> targetArgs, MetaContainer metas) {
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$2[this.astVersion.ordinal()]) {
            case 1: {
                String string = IonValueExtensionsKt.asIonSymbol(targetArgs.get(0)).stringValue();
                Intrinsics.checkExpressionValueIsNotNull(string, "targetArgs[0].asIonSymbol().stringValue()");
                String string2 = IonValueExtensionsKt.asIonSymbol(targetArgs.get(1)).toString();
                ExprNode exprNode = new VariableReference(string, CaseSensitivity.INSENSITIVE, ScopeQualifier.UNQUALIFIED, MetaKt.getEmptyMetaContainer());
                boolean bl = false;
                String string3 = string2;
                if (string3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string4 = string3.toUpperCase();
                Intrinsics.checkExpressionValueIsNotNull(string4, "(this as java.lang.String).toUpperCase()");
                String string5 = string4;
                MetaContainer metaContainer = metas;
                ExprNode exprNode2 = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(targetArgs.get(2)));
                SetQuantifier setQuantifier = SetQuantifier.valueOf(string5);
                ExprNode exprNode3 = exprNode;
                return new CallAgg(exprNode3, setQuantifier, exprNode2, metaContainer);
            }
        }
        throw new NoWhenBranchMatchedException();
    }

    private final CallAgg deserializeCallAggWildcard(List<? extends IonValue> targetArgs, MetaContainer metas) {
        if (Intrinsics.areEqual(IonValueExtensionsKt.asIonSymbol(targetArgs.get(0)).stringValue(), "count") ^ true) {
            Void void_ = AstDeserializationKt.access$err("Only the 'count' function may be invoked with 'call_agg_wildcard'");
            throw null;
        }
        return UtilKt.createCountStar(this.ion, metas);
    }

    private final Struct deserializeStruct(List<? extends IonValue> targetArgs, MetaContainer metas) {
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$3[this.astVersion.ordinal()]) {
            case 1: {
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return this.deserializeStructV0(targetArgs, metas);
    }

    /*
     * WARNING - void declaration
     */
    private final Struct deserializeStructV0(List<? extends IonValue> targetArgs, MetaContainer metas) {
        if (targetArgs.size() % 2 != 0) {
            Void void_ = AstDeserializationKt.access$err("Arity of 'struct' node must be divisible by 2.");
            throw null;
        }
        int pairCount = targetArgs.size() / 2;
        ArrayList<StructField> pairs = new ArrayList<StructField>();
        int n = 0;
        int n2 = pairCount - 1;
        if (n <= n2) {
            while (true) {
                void i;
                void keyIndex = i * 2;
                pairs.add(new StructField(this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(targetArgs.get((int)keyIndex))), this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(targetArgs.get((int)(keyIndex + true))))));
                if (i == n2) break;
                ++i;
            }
        }
        return new Struct(CollectionsKt.toList((Iterable)pairs), metas);
    }

    private final Seq deserializeSeq(NodeTag nodeTag, List<? extends IonValue> targetArgs, MetaContainer metas) {
        SeqType seqType;
        String tagText;
        block2: {
            tagText = nodeTag.getDefinition().getTagText();
            SeqType[] $this$firstOrNull$iv = SeqType.values();
            boolean $i$f$firstOrNull = false;
            SeqType[] seqTypeArray = $this$firstOrNull$iv;
            int n = seqTypeArray.length;
            for (int i = 0; i < n; ++i) {
                SeqType element$iv;
                SeqType it = element$iv = seqTypeArray[i];
                boolean bl = false;
                if (!Intrinsics.areEqual(it.getTypeName(), tagText)) continue;
                seqType = element$iv;
                break block2;
            }
            seqType = null;
        }
        if (seqType == null) {
            Void void_ = AstDeserializationKt.access$err("Invalid node for deserialzing sequence: " + tagText);
            throw null;
        }
        SeqType type = seqType;
        return new Seq(type, this.deserializeAllExprNodes(targetArgs), metas);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final NAry deserializeNAryNot(List<? extends IonValue> targetArgs, MetaContainer metas) {
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$4[this.astVersion.ordinal()]) {
            case 1: {
                return new NAry(NAryOp.NOT, this.deserializeAllExprNodes(targetArgs), metas);
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    private final NAry deserializeNAryAdd(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.ADD, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNArySub(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.SUB, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryMul(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.MUL, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryDiv(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.DIV, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryMod(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.MOD, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryGt(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.GT, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryGte(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.GTE, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryLt(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.LT, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryLte(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.LTE, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryEq(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.EQ, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryNe(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.NE, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryAnd(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.AND, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryOr(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.OR, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryNotIn(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.NOT, CollectionsKt.listOf(new NAry(NAryOp.IN, this.deserializeAllExprNodes(targetArgs), metas)), MetaKt.plus(metas, MetaKt.metaContainerOf(LegacyLogicalNotMeta.Companion.getInstance())));
    }

    private final NAry deserializeNAryIn(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.IN, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryNotLlike(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.NOT, CollectionsKt.listOf(new NAry(NAryOp.LIKE, this.deserializeAllExprNodes(targetArgs), metas)), MetaKt.plus(metas, MetaKt.metaContainerOf(LegacyLogicalNotMeta.Companion.getInstance())));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final NAry deserializeNAryLike(List<? extends IonValue> targetArgs, MetaContainer metas) {
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$5[this.astVersion.ordinal()]) {
            case 1: {
                return new NAry(NAryOp.LIKE, this.deserializeAllExprNodes(targetArgs), metas);
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    private final NAry deserializeNAryNotBetween(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.NOT, CollectionsKt.listOf(new NAry(NAryOp.BETWEEN, this.deserializeAllExprNodes(targetArgs), metas)), MetaKt.plus(metas, MetaKt.metaContainerOf(LegacyLogicalNotMeta.Companion.getInstance())));
    }

    private final NAry deserializeNAryBetween(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.BETWEEN, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryStringConcat(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.STRING_CONCAT, this.deserializeAllExprNodes(targetArgs), metas);
    }

    private final NAry deserializeNAryCall(List<? extends IonValue> targetArgs, MetaContainer metas) {
        String string = IonValueExtensionsKt.asIonSymbol(targetArgs.get(0)).stringValue();
        Intrinsics.checkExpressionValueIsNotNull(string, "targetArgs[0].asIonSymbol().stringValue()");
        VariableReference functionReference = new VariableReference(string, CaseSensitivity.INSENSITIVE, ScopeQualifier.UNQUALIFIED, MetaKt.getEmptyMetaContainer());
        List<ExprNode> argExprNodes = this.deserializeAllExprNodes(CollectionsKt.drop((Iterable)targetArgs, 1));
        return new NAry(NAryOp.CALL, CollectionsKt.plus((Collection)CollectionsKt.listOf(functionReference), (Iterable)argExprNodes), metas);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final NAry deserializeNAryUnion(List<? extends IonValue> targetArgs, MetaContainer metas) {
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$6[this.astVersion.ordinal()]) {
            case 1: {
                return new NAry(NAryOp.UNION, this.deserializeAllExprNodes(targetArgs), metas);
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    private final NAry deserializeNAryUnionAll(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.UNION_ALL, this.deserializeAllExprNodes(targetArgs), metas);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final NAry deserializeNAryExcept(List<? extends IonValue> targetArgs, MetaContainer metas) {
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$7[this.astVersion.ordinal()]) {
            case 1: {
                return new NAry(NAryOp.EXCEPT, this.deserializeAllExprNodes(targetArgs), metas);
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    private final NAry deserializeNAryExceptAll(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.EXCEPT_ALL, this.deserializeAllExprNodes(targetArgs), metas);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final NAry deserializeNAryIntersect(List<? extends IonValue> targetArgs, MetaContainer metas) {
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$8[this.astVersion.ordinal()]) {
            case 1: {
                return new NAry(NAryOp.INTERSECT, this.deserializeAllExprNodes(targetArgs), metas);
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    private final NAry deserializeNAryIntersectAll(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.INTERSECT_ALL, this.deserializeAllExprNodes(targetArgs), metas);
    }

    /*
     * WARNING - void declaration
     */
    private final ExprNode deserializeCreateV0(List<? extends IonValue> targetArgs, MetaContainer metas) {
        ExprNode exprNode;
        String id = IonValueExtensionsKt.stringValue(targetArgs.get(0));
        IonSexp target = IonValueExtensionsKt.asIonSexp(targetArgs.get(1));
        List<IonValue> args2 = IonValueExtensionsKt.getArgs(target);
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$9[this.getNodeTag(target).ordinal()]) {
            case 1: {
                String string = id;
                if (string == null) {
                    Void void_ = AstDeserializationKt.access$err("Table name must be specified");
                    throw null;
                }
                String tableName = string;
                exprNode = new CreateTable(tableName, metas);
                break;
            }
            case 2: {
                void $this$mapTo$iv$iv;
                String string = IonValueExtensionsKt.stringValue(args2.get(0));
                if (string == null) {
                    Void void_ = AstDeserializationKt.access$err("Table name must be specified");
                    throw null;
                }
                String tableName = string;
                Iterable $this$map$iv = IonValueExtensionsKt.toListOfIonSexp(CollectionsKt.drop((Iterable)args2, 1));
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    IonSexp ionSexp = (IonSexp)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    Pair<NodeTag, void> pair = new Pair<NodeTag, void>(this.getNodeTag((IonSexp)it), it);
                    collection.add(pair);
                }
                Map children = MapsKt.toMap((List)destination$iv$iv);
                List<IonValue> list = (IonSexp)children.get((Object)NodeTag.KEYS);
                if (list == null || (list = IonValueExtensionsKt.getArgs(list)) == null || (list = this.deserializeAllExprNodes(list)) == null) {
                    Void void_ = AstDeserializationKt.access$err("Index definition expects keys");
                    throw null;
                }
                List<IonValue> keys2 = list;
                exprNode = new CreateIndex(tableName, keys2, metas);
                break;
            }
            default: {
                Void void_ = AstDeserializationKt.access$errInvalidContext(this.getNodeTag(target));
                throw null;
            }
        }
        return exprNode;
    }

    private final DropTable deserializeDropTableV0(List<? extends IonValue> targetArgs, MetaContainer metas) {
        String string = IonValueExtensionsKt.stringValue(targetArgs.get(0));
        if (string == null) {
            Void void_ = AstDeserializationKt.access$err("Table name must be specified");
            throw null;
        }
        String tableName = string;
        return new DropTable(tableName, metas);
    }

    private final DropIndex deserializeDropIndexV0(List<? extends IonValue> targetArgs, MetaContainer metas) {
        String string = IonValueExtensionsKt.stringValue(targetArgs.get(0));
        if (string == null) {
            Void void_ = AstDeserializationKt.access$err("Table name must be specified");
            throw null;
        }
        String tableName = string;
        ExprNode exprNode = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(targetArgs.get(1)));
        if (exprNode == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.ast.VariableReference");
        }
        VariableReference id = (VariableReference)exprNode;
        return new DropIndex(tableName, id, metas);
    }

    private final Pair<String, CaseSensitivity> deserializeIdentifier(List<? extends IonValue> targetArgs) {
        String string = IonValueExtensionsKt.stringValue(targetArgs.get(0));
        if (string == null) {
            Void void_ = AstDeserializationKt.access$err("Identifier deserialization: expecting string value, got " + targetArgs.get(0));
            throw null;
        }
        return TuplesKt.to(string, CaseSensitivity.Companion.fromSymbol(IonValueExtensionsKt.getTagText(IonValueExtensionsKt.asIonSexp(targetArgs.get(1)))));
    }

    private final Typed deserializeTypedIs(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new Typed(TypedOp.IS, this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(targetArgs.get(0))), this.deserializeDataType(targetArgs.get(1)), metas);
    }

    private final NAry deserializeTypedIsNot(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new NAry(NAryOp.NOT, CollectionsKt.listOf(new Typed(TypedOp.IS, this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(targetArgs.get(0))), this.deserializeDataType(targetArgs.get(1)), metas)), MetaKt.plus(metas, MetaKt.metaContainerOf(LegacyLogicalNotMeta.Companion.getInstance())));
    }

    private final Typed deserializeTypedCast(List<? extends IonValue> targetArgs, MetaContainer metas) {
        return new Typed(TypedOp.CAST, this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(targetArgs.get(0))), this.deserializeDataType(targetArgs.get(1)), metas);
    }

    /*
     * WARNING - void declaration
     */
    private final ExprNode deserializeDataManipulation(IonSexp target, MetaContainer metas) {
        ExprNode where2;
        ExprNode exprNode;
        FromSource fromSource;
        void $this$mapTo$iv$iv;
        List<IonSexp> args2 = IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)IonValueExtensionsKt.getArgs(target));
        NodeTag nodeTag = this.getNodeTag(args2.get(0));
        List<DataManipulationOperation> dmlOp = this.deserializeDataManipulationOperation(args2.get(0));
        Iterable $this$map$iv = IonValueExtensionsKt.toListOfIonSexp(CollectionsKt.drop((Iterable)args2, 1));
        boolean $i$f$map2 = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            IonSexp ionSexp = (IonSexp)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Pair<NodeTag, void> pair = new Pair<NodeTag, void>(this.getNodeTag((IonSexp)it), it);
            collection.add(pair);
        }
        Map children = MapsKt.toMap((List)destination$iv$iv);
        IonSexp ionSexp = (IonSexp)children.get((Object)NodeTag.FROM);
        if (ionSexp != null) {
            IonSexp $i$f$map2 = ionSexp;
            boolean bl = false;
            boolean bl2 = false;
            IonSexp it = $i$f$map2;
            boolean bl3 = false;
            fromSource = this.deserializeFromSource(IonValueExtensionsKt.asIonSexp(CollectionsKt.first(IonValueExtensionsKt.getArgs(it))));
        } else {
            fromSource = null;
        }
        FromSource from2 = fromSource;
        IonSexp ionSexp2 = (IonSexp)children.get((Object)NodeTag.WHERE);
        if (ionSexp2 != null) {
            IonSexp ionSexp3 = ionSexp2;
            boolean bl = false;
            boolean bl4 = false;
            IonSexp it = ionSexp3;
            boolean bl5 = false;
            exprNode = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(CollectionsKt.first(IonValueExtensionsKt.getArgs(it))));
        } else {
            exprNode = where2 = null;
        }
        if (from2 == null && where2 != null) {
            Void void_ = AstDeserializationKt.access$err("WHERE cannot be specified without FROM in DML node");
            throw null;
        }
        return new DataManipulation(new DmlOpList(dmlOp), from2, where2, null, metas);
    }

    /*
     * WARNING - void declaration
     */
    private final List<DataManipulationOperation> deserializeDataManipulationOperation(IonSexp target) {
        List<InsertValueOp> list;
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$12[this.getNodeTag(target).ordinal()]) {
            case 1: {
                List<IonSexp> sexpArgs = IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)IonValueExtensionsKt.getArgs(target));
                list = CollectionsKt.listOf(new InsertOp(this.deserializeExprNode$lang(sexpArgs.get(0)), this.deserializeExprNode$lang(sexpArgs.get(1))));
                break;
            }
            case 2: {
                ExprNode exprNode;
                List<IonValue> args2 = IonValueExtensionsKt.getArgs(target);
                ExprNode exprNode2 = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(args2.get(0)));
                ExprNode exprNode3 = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(args2.get(1)));
                switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$11[this.astVersion.ordinal()]) {
                    case 1: {
                        IonValue ionValue2 = CollectionsKt.getOrNull(args2, 2);
                        if (ionValue2 != null) {
                            IonValue ionValue3 = ionValue2;
                            ExprNode exprNode4 = exprNode3;
                            ExprNode exprNode5 = exprNode2;
                            boolean bl = false;
                            boolean bl2 = false;
                            IonValue it = ionValue3;
                            boolean bl3 = false;
                            ExprNode exprNode6 = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(it));
                            exprNode2 = exprNode5;
                            exprNode3 = exprNode4;
                            exprNode = exprNode6;
                            break;
                        }
                        exprNode = null;
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                OnConflict onConflict = null;
                ExprNode exprNode7 = exprNode;
                ExprNode exprNode8 = exprNode3;
                ExprNode exprNode9 = exprNode2;
                list = CollectionsKt.listOf(new InsertValueOp(exprNode9, exprNode8, exprNode7, onConflict));
                break;
            }
            case 3: {
                void $this$mapTo$iv$iv;
                Iterable $this$map$iv = this.deserializeSetAssignments(IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)IonValueExtensionsKt.getArgs(target)));
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    Assignment assignment = (Assignment)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    AssignmentOp assignmentOp = new AssignmentOp((Assignment)it);
                    collection.add(assignmentOp);
                }
                list = (List<InsertValueOp>)destination$iv$iv;
                break;
            }
            case 4: {
                list = CollectionsKt.listOf(new RemoveOp(this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(target).get(0)))));
                break;
            }
            case 5: {
                list = CollectionsKt.listOf(AstKt.DeleteOp());
                break;
            }
            default: {
                Void void_ = AstDeserializationKt.access$errInvalidContext(this.getNodeTag(target));
                throw null;
            }
        }
        return list;
    }

    /*
     * WARNING - void declaration
     */
    private final List<Assignment> deserializeSetAssignments(List<? extends IonSexp> targets) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)targets);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            IonSexp ionSexp = (IonSexp)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            if (this.getNodeTag((IonSexp)it) != NodeTag.ASSIGNMENT) {
                Void void_ = AstDeserializationKt.access$errInvalidContext(this.getNodeTag((IonSexp)it));
                throw null;
            }
            List<IonSexp> assignArgs = IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)IonValueExtensionsKt.getArgs((IonSexp)it));
            Assignment assignment = new Assignment(this.deserializeExprNode$lang(assignArgs.get(0)), this.deserializeExprNode$lang(assignArgs.get(1)));
            collection.add(assignment);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final ExprNode deserializeSelect(IonSexp target, MetaContainer metas) {
        ExprNode exprNode;
        ExprNode exprNode2;
        GroupBy groupBy2;
        IonSexp ionSexp;
        ExprNode whereExprNode;
        ExprNode exprNode3;
        SelectProjection selectProjection;
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)IonValueExtensionsKt.getArgs(target));
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            IonSexp ionSexp2 = (IonSexp)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Pair<NodeTag, void> pair = new Pair<NodeTag, void>(this.getNodeTag((IonSexp)it), it);
            collection.add(pair);
        }
        Map children = MapsKt.toMap((List)destination$iv$iv);
        SetQuantifier setQuantifier = SetQuantifier.ALL;
        block0 : switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$14[this.astVersion.ordinal()]) {
            case 1: {
                switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$13[this.getNodeTag(target).ordinal()]) {
                    case 1: {
                        selectProjection = this.deserializeProjectionPivotV0(children);
                        break block0;
                    }
                    case 2: {
                        IonSexp ionSexp3 = (IonSexp)children.get((Object)NodeTag.PROJECT);
                        if (ionSexp3 == null) {
                            ionSexp3 = (IonSexp)children.get((Object)NodeTag.PROJECT_DISTINCT);
                        }
                        if (ionSexp3 == null) {
                            Void void_ = AstDeserializationKt.access$err("select node missing project or project_distinct");
                            throw null;
                        }
                        IonSexp project2 = ionSexp3;
                        if (this.getNodeTag(project2) == NodeTag.PROJECT_DISTINCT) {
                            setQuantifier = SetQuantifier.DISTINCT;
                        }
                        selectProjection = this.deserializeSelectValueOrListV0(project2);
                        break block0;
                    }
                }
                Void void_ = AstDeserializationKt.access$errInvalidContext(this.getNodeTag(target));
                throw null;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        SelectProjection projection = selectProjection;
        IonSexp ionSexp4 = (IonSexp)children.get((Object)NodeTag.FROM);
        if (ionSexp4 == null) {
            Void void_ = AstDeserializationKt.access$err("select node missing from");
            throw null;
        }
        IonSexp from2 = ionSexp4;
        FromSource fromExprNode = this.deserializeFromSource(IonValueExtensionsKt.asIonSexp(CollectionsKt.first(IonValueExtensionsKt.getArgs(from2))));
        IonSexp ionSexp5 = (IonSexp)children.get((Object)NodeTag.WHERE);
        if (ionSexp5 != null) {
            IonSexp ionSexp6 = ionSexp5;
            boolean item$iv$iv = false;
            boolean it = false;
            Object it2 = ionSexp6;
            boolean bl = false;
            exprNode3 = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(CollectionsKt.first(IonValueExtensionsKt.getArgs((IonSexp)it2))));
        } else {
            exprNode3 = whereExprNode = null;
        }
        if ((ionSexp = (IonSexp)children.get((Object)NodeTag.GROUP)) == null) {
            ionSexp = (IonSexp)children.get((Object)NodeTag.GROUP_PARTIAL);
        }
        if (ionSexp != null) {
            GroupingStrategy groupingStrategy;
            SymbolicName symbolicName;
            void $this$mapTo$iv$iv2;
            IonSexp item$iv$iv = ionSexp;
            boolean it = false;
            boolean bl = false;
            IonSexp it3 = item$iv$iv;
            boolean bl2 = false;
            IonSexp bySexp = IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.singleArgWithTag(it3, "by"));
            Iterable $this$map$iv2 = IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)IonValueExtensionsKt.getArgs(bySexp));
            boolean $i$f$map22 = false;
            Iterable iterable2 = $this$map$iv2;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            boolean $i$f$mapTo2 = false;
            for (Object item$iv$iv2 : $this$mapTo$iv$iv2) {
                void gbiSexp;
                IonSexp ionSexp7 = (IonSexp)item$iv$iv2;
                Collection collection = destination$iv$iv2;
                boolean bl3 = false;
                GroupByItem groupByItem = this.deserializeGroupByItem(IonValueExtensionsKt.asIonSexp((IonValue)gbiSexp));
                collection.add(groupByItem);
            }
            List items = (List)destination$iv$iv2;
            IonValue ionValue2 = this.singleWrappedChildWithTagOrNull(it3, "name");
            if (ionValue2 != null) {
                IonValue $i$f$map22 = ionValue2;
                boolean bl4 = false;
                boolean bl5 = false;
                IonValue nameArg = $i$f$map22;
                boolean bl6 = false;
                symbolicName = (SymbolicName)this.deserializeSexpMetaOrTerm(IonValueExtensionsKt.asIonSexp(nameArg), deserializeSelect.groupBy.1.nameSymbol.1.1.INSTANCE);
            } else {
                symbolicName = null;
            }
            SymbolicName nameSymbol2 = symbolicName;
            switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$15[this.getNodeTag(it3).ordinal()]) {
                case 1: {
                    groupingStrategy = GroupingStrategy.FULL;
                    break;
                }
                default: {
                    groupingStrategy = GroupingStrategy.PARTIAL;
                }
            }
            GroupingStrategy groupingSrategy = groupingStrategy;
            groupBy2 = new GroupBy(groupingSrategy, items, nameSymbol2);
        } else {
            groupBy2 = null;
        }
        GroupBy groupBy3 = groupBy2;
        IonSexp ionSexp8 = (IonSexp)children.get((Object)NodeTag.HAVING);
        if (ionSexp8 != null) {
            IonSexp it = ionSexp8;
            boolean bl = false;
            boolean bl7 = false;
            IonSexp it4 = it;
            boolean bl8 = false;
            exprNode2 = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(CollectionsKt.first(IonValueExtensionsKt.getArgs(it4))));
        } else {
            exprNode2 = null;
        }
        ExprNode havingExprNode = exprNode2;
        IonSexp ionSexp9 = (IonSexp)children.get((Object)NodeTag.LIMIT);
        if (ionSexp9 != null) {
            IonSexp ionSexp10 = ionSexp9;
            boolean bl = false;
            boolean bl9 = false;
            IonSexp it = ionSexp10;
            boolean bl10 = false;
            exprNode = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(CollectionsKt.first(IonValueExtensionsKt.getArgs(it))));
        } else {
            exprNode = null;
        }
        ExprNode limitExprNode = exprNode;
        return new Select(setQuantifier, projection, fromExprNode, null, whereExprNode, groupBy3, havingExprNode, null, limitExprNode, metas, 136, null);
    }

    private final SelectProjectionPivot deserializeProjectionPivotV0(Map<NodeTag, ? extends IonSexp> children) {
        IonSexp ionSexp = children.get((Object)NodeTag.MEMBER);
        if (ionSexp == null) {
            Void void_ = AstDeserializationKt.access$err("(pivot ...) missing member node");
            throw null;
        }
        IonSexp member = ionSexp;
        List<IonValue> memberArgs = IonValueExtensionsKt.getArgs(member);
        ExprNode nameExpr = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(memberArgs.get(0)));
        ExprNode valueExpr = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(memberArgs.get(1)));
        return new SelectProjectionPivot(nameExpr, valueExpr);
    }

    private final SelectProjection deserializeSelectValueOrListV0(IonSexp project2) {
        SelectProjection selectProjection;
        IonValue ionValue2 = project2.get(1);
        Intrinsics.checkExpressionValueIsNotNull(ionValue2, "project[1]");
        IonSexp projectChild = IonValueExtensionsKt.asIonSexp(ionValue2);
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$16[this.getNodeTag(projectChild).ordinal()]) {
            case 1: {
                selectProjection = new SelectProjectionValue(this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(CollectionsKt.first(IonValueExtensionsKt.getArgs(projectChild)))));
                break;
            }
            case 2: {
                selectProjection = this.deserializeSelectListItems(projectChild);
                break;
            }
            default: {
                Void void_ = AstDeserializationKt.access$errInvalidContext(this.getNodeTag(projectChild));
                throw null;
            }
        }
        return selectProjection;
    }

    /*
     * WARNING - void declaration
     */
    private final SelectProjectionList deserializeSelectListItems(IonSexp projectChild) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = IonValueExtensionsKt.getArgs(projectChild);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void selectListItemSexp;
            IonValue ionValue2 = (IonValue)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            SelectListItem selectListItem = (SelectListItem)this.deserializeSexpMetaOrTerm(IonValueExtensionsKt.asIonSexp((IonValue)selectListItemSexp), (Function2)new Function2<IonSexp, MetaContainer, SelectListItem>((IonValue)selectListItemSexp, this){
                final /* synthetic */ IonValue $selectListItemSexp;
                final /* synthetic */ AstDeserializerInternal this$0;
                {
                    this.$selectListItemSexp = ionValue2;
                    this.this$0 = astDeserializerInternal;
                    super(2);
                }

                @NotNull
                public final SelectListItem invoke(@NotNull IonSexp itemTarget, @NotNull MetaContainer metas) {
                    SelectListItem selectListItem;
                    Intrinsics.checkParameterIsNotNull(itemTarget, "itemTarget");
                    Intrinsics.checkParameterIsNotNull(metas, "metas");
                    switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$17[AstDeserializerInternal.access$getNodeTag$p(this.this$0, itemTarget).ordinal()]) {
                        case 1: {
                            String string = IonValueExtensionsKt.asIonSymbol(IonValueExtensionsKt.getArgs(itemTarget).get(0)).stringValue();
                            Intrinsics.checkExpressionValueIsNotNull(string, "itemTarget.args[0].asIonSymbol().stringValue()");
                            SymbolicName asName = new SymbolicName(string, metas);
                            selectListItem = new SelectListItemExpr(this.this$0.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(itemTarget).get(1))), asName);
                            break;
                        }
                        case 2: {
                            if (IonValueExtensionsKt.getArity(itemTarget) == 0) {
                                selectListItem = new SelectListItemStar(metas);
                                break;
                            }
                            selectListItem = new SelectListItemProjectAll(this.this$0.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(itemTarget).get(0))));
                            break;
                        }
                        default: {
                            selectListItem = new SelectListItemExpr(this.this$0.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(this.$selectListItemSexp)), null, 2, null);
                        }
                    }
                    return selectListItem;
                }
            });
            collection.add(selectListItem);
        }
        List selectListItems = (List)destination$iv$iv;
        return new SelectProjectionList(selectListItems);
    }

    private final FromSource deserializeFromSource(IonSexp termOrFromSource) {
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$18[this.astVersion.ordinal()]) {
            case 1: {
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return AstDeserializerInternal.deserializeFromSourceV0$default(this, termOrFromSource, null, 2, null);
    }

    private final FromSource deserializeFromSourceV0(IonSexp termOrFromSource, LetVariables variables) {
        return (FromSource)this.deserializeSexpMetaOrTerm(termOrFromSource, (Function2)new Function2<IonSexp, MetaContainer, FromSource>(this, variables){
            final /* synthetic */ AstDeserializerInternal this$0;
            final /* synthetic */ LetVariables $variables;

            @NotNull
            public final FromSource invoke(@NotNull IonSexp target, @NotNull MetaContainer metas) {
                FromSource fromSource;
                Intrinsics.checkParameterIsNotNull(target, "target");
                Intrinsics.checkParameterIsNotNull(metas, "metas");
                List<IonValue> targetArgs = IonValueExtensionsKt.getArgs(target);
                switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$19[AstDeserializerInternal.access$getNodeTag$p(this.this$0, target).ordinal()]) {
                    case 1: {
                        if (this.$variables.getAtName() != null) {
                            String string = "'at' previously encountered in this from source";
                            boolean bl = false;
                            throw (Throwable)new IllegalStateException(string.toString());
                        }
                        IonValue ionValue2 = target.get(2);
                        Intrinsics.checkExpressionValueIsNotNull(ionValue2, "target[2]");
                        IonSexp ionSexp = IonValueExtensionsKt.asIonSexp(ionValue2);
                        String string = IonValueExtensionsKt.asIonSymbol(IonValueExtensionsKt.getArgs(target).get(0)).stringValue();
                        Intrinsics.checkExpressionValueIsNotNull(string, "target.args[0].asIonSymbol().stringValue()");
                        fromSource = AstDeserializerInternal.access$deserializeFromSourceV0(this.this$0, ionSexp, LetVariables.copy$default(this.$variables, null, new SymbolicName(string, metas), null, 5, null));
                        break;
                    }
                    case 2: {
                        if (this.$variables.getAsName() != null) {
                            String string = "'as' previously encountered in this from source";
                            boolean bl = false;
                            throw (Throwable)new IllegalStateException(string.toString());
                        }
                        IonValue ionValue3 = target.get(2);
                        Intrinsics.checkExpressionValueIsNotNull(ionValue3, "target[2]");
                        IonSexp ionSexp = IonValueExtensionsKt.asIonSexp(ionValue3);
                        String string = IonValueExtensionsKt.asIonSymbol(IonValueExtensionsKt.getArgs(target).get(0)).stringValue();
                        Intrinsics.checkExpressionValueIsNotNull(string, "target.args[0].asIonSymbol().stringValue()");
                        fromSource = AstDeserializerInternal.access$deserializeFromSourceV0(this.this$0, ionSexp, LetVariables.copy$default(this.$variables, new SymbolicName(string, metas), null, null, 6, null));
                        break;
                    }
                    case 3: {
                        if (this.$variables.getByName() != null) {
                            String string = "'by' previously encountered in this from source";
                            boolean bl = false;
                            throw (Throwable)new IllegalStateException(string.toString());
                        }
                        IonValue ionValue4 = target.get(2);
                        Intrinsics.checkExpressionValueIsNotNull(ionValue4, "target[2]");
                        IonSexp ionSexp = IonValueExtensionsKt.asIonSexp(ionValue4);
                        String string = IonValueExtensionsKt.asIonSymbol(IonValueExtensionsKt.getArgs(target).get(0)).stringValue();
                        Intrinsics.checkExpressionValueIsNotNull(string, "target.args[0].asIonSymbol().stringValue()");
                        fromSource = AstDeserializerInternal.access$deserializeFromSourceV0(this.this$0, ionSexp, LetVariables.copy$default(this.$variables, null, null, new SymbolicName(string, metas), 3, null));
                        break;
                    }
                    case 4: {
                        fromSource = AstDeserializerInternal.access$deserializeFromSourceUnpivotV0(this.this$0, targetArgs, this.$variables, metas);
                        break;
                    }
                    case 5: 
                    case 6: 
                    case 7: 
                    case 8: {
                        if (this.$variables.isAnySpecified()) {
                            String string = "join from sources cannot have 'at', 'as' or 'by' names.";
                            boolean bl = false;
                            throw (Throwable)new IllegalStateException(string.toString());
                        }
                        fromSource = AstDeserializerInternal.access$deserializeFromSourceJoinV0(this.this$0, target, targetArgs, metas);
                        break;
                    }
                    default: {
                        fromSource = AstDeserializerInternal.access$deserializeFromSourceExprV0(this.this$0, target, this.$variables, metas);
                    }
                }
                return fromSource;
            }
            {
                this.this$0 = astDeserializerInternal;
                this.$variables = letVariables;
                super(2);
            }
        });
    }

    static /* synthetic */ FromSource deserializeFromSourceV0$default(AstDeserializerInternal astDeserializerInternal, IonSexp ionSexp, LetVariables letVariables, int n, Object object) {
        if ((n & 2) != 0) {
            letVariables = new LetVariables(null, null, null);
        }
        return astDeserializerInternal.deserializeFromSourceV0(ionSexp, letVariables);
    }

    private final FromSourceUnpivot deserializeFromSourceUnpivotV0(List<? extends IonValue> targetArgs, LetVariables variables, MetaContainer metas) {
        ExprNode expr = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(targetArgs.get(0)));
        return new FromSourceUnpivot(expr, variables, metas);
    }

    /*
     * WARNING - void declaration
     */
    private final FromSourceJoin deserializeFromSourceJoinV0(IonSexp target, List<? extends IonValue> targetArgs, MetaContainer metas) {
        void condition;
        Pair<ExprNode, MetaContainer> pair;
        JoinOp joinOp;
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$20[this.getNodeTag(target).ordinal()]) {
            case 1: {
                joinOp = JoinOp.INNER;
                break;
            }
            case 2: {
                joinOp = JoinOp.LEFT;
                break;
            }
            case 3: {
                joinOp = JoinOp.RIGHT;
                break;
            }
            case 4: {
                joinOp = JoinOp.OUTER;
                break;
            }
            default: {
                throw (Throwable)new IllegalStateException("Illegal join operator: " + this.getNodeTag(target).getDefinition().getTagText());
            }
        }
        JoinOp joinOp2 = joinOp;
        FromSource leftFromSource = AstDeserializerInternal.deserializeFromSourceV0$default(this, IonValueExtensionsKt.asIonSexp(targetArgs.get(0)), null, 2, null);
        FromSource rightFromSource = AstDeserializerInternal.deserializeFromSourceV0$default(this, IonValueExtensionsKt.asIonSexp(targetArgs.get(1)), null, 2, null);
        if (IonValueExtensionsKt.getArity(target) > 2) {
            pair = new Pair<ExprNode, MetaContainer>(this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(targetArgs.get(2))), metas);
        } else {
            IonBool ionBool = this.ion.newBool(true);
            Intrinsics.checkExpressionValueIsNotNull(ionBool, "ion.newBool(true)");
            pair = new Pair<Literal, MetaContainer>(new Literal(ionBool, MetaKt.getEmptyMetaContainer()), MetaKt.plus(metas, MetaKt.metaContainerOf(IsImplictJoinMeta.Companion.getInstance())));
        }
        Pair<ExprNode, MetaContainer> pair2 = pair;
        ExprNode exprNode = pair2.component1();
        MetaContainer metasMaybeWithImplicitJoin = pair2.component2();
        return new FromSourceJoin(joinOp2, leftFromSource, rightFromSource, (ExprNode)condition, metasMaybeWithImplicitJoin);
    }

    private final FromSourceExpr deserializeFromSourceExprV0(IonSexp target, LetVariables variables, MetaContainer metas) {
        return new FromSourceExpr(this.deserializeExprNode$lang(target).copy(metas), variables);
    }

    private final GroupByItem deserializeGroupByItem(IonSexp target) {
        return (GroupByItem)this.deserializeSexpMetaOrTerm(target, (Function2)new Function2<IonSexp, MetaContainer, GroupByItem>(this){
            final /* synthetic */ AstDeserializerInternal this$0;

            @NotNull
            public final GroupByItem invoke(@NotNull IonSexp innerTarget, @NotNull MetaContainer metas) {
                GroupByItem groupByItem;
                Intrinsics.checkParameterIsNotNull(innerTarget, "innerTarget");
                Intrinsics.checkParameterIsNotNull(metas, "metas");
                List<IonValue> innerTargetArgs = IonValueExtensionsKt.getArgs(innerTarget);
                switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$21[AstDeserializerInternal.access$getNodeTag$p(this.this$0, innerTarget).ordinal()]) {
                    case 1: {
                        String string = IonValueExtensionsKt.asIonSymbol(innerTargetArgs.get(0)).stringValue();
                        Intrinsics.checkExpressionValueIsNotNull(string, "innerTargetArgs[0].asIonSymbol().stringValue()");
                        SymbolicName symbolicName = new SymbolicName(string, metas);
                        ExprNode expr = this.this$0.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(innerTargetArgs.get(1)));
                        groupByItem = new GroupByItem(expr, symbolicName);
                        break;
                    }
                    default: {
                        ExprNode expr = this.this$0.deserializeExprNode$lang(innerTarget).copy(metas);
                        groupByItem = new GroupByItem(expr, null);
                    }
                }
                return groupByItem;
            }
            {
                this.this$0 = astDeserializerInternal;
                super(2);
            }
        });
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     */
    private final ExprNode deserializeSimpleCase(IonSexp target, MetaContainer metas) {
        targetArgs = IonValueExtensionsKt.getArgs(target);
        valueExpr = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(CollectionsKt.first(targetArgs)));
        clauses = CollectionsKt.drop((Iterable)targetArgs, 1);
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$22[this.astVersion.ordinal()]) {
            case 1: {
                clausesIonSexp = IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)clauses);
                $this$filter$iv = clausesIonSexp;
                $i$f$filter = false;
                var10_10 = $this$filter$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    it = (IonSexp)element$iv$iv;
                    $i$a$-filter-AstDeserializerInternal$deserializeSimpleCase$whenClauses$1 = false;
                    if (!(this.getNodeTag(it) == NodeTag.WHEN)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$map$iv = (List)destination$iv$iv;
                $i$f$map = false;
                $this$filterTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (IonSexp)item$iv$iv;
                    var19_23 = destination$iv$iv;
                    $i$a$-map-AstDeserializerInternal$deserializeSimpleCase$whenClauses$2 = false;
                    whenValueExpr = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(it).get(0)));
                    thenExpr = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(it).get(1)));
                    var20_24 = new SimpleCaseWhen(whenValueExpr, thenExpr);
                    var19_23.add(var20_24);
                }
                whenClauses = (List)destination$iv$iv;
                $this$singleOrNull$iv = clausesIonSexp;
                $i$f$singleOrNull = false;
                single$iv = null;
                found$iv = false;
                for (T element$iv : $this$singleOrNull$iv) {
                    it = (IonSexp)element$iv;
                    $i$a$-singleOrNull-AstDeserializerInternal$deserializeSimpleCase$elseClause$1 = false;
                    if (!(this.getNodeTag(it) == NodeTag.ELSE)) continue;
                    if (!found$iv) ** GOTO lbl47
                    v0 = null;
                    ** GOTO lbl51
lbl47:
                    // 1 sources

                    single$iv = element$iv;
                    found$iv = true;
                }
                v0 = found$iv == false ? null : single$iv;
lbl51:
                // 2 sources

                v1 = v0;
                if (v1 != null) {
                    var9_9 = v1;
                    var10_11 = false;
                    var11_13 = false;
                    it = var9_9;
                    $i$a$-let-AstDeserializerInternal$deserializeSimpleCase$elseClause$2 = false;
                    v2 = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(CollectionsKt.first(IonValueExtensionsKt.getArgs(it))));
                } else {
                    v2 = null;
                }
                elseClause = v2;
                return new SimpleCase(valueExpr, whenClauses, elseClause, metas);
            }
        }
        throw new NoWhenBranchMatchedException();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     */
    private final ExprNode deserializeSearchedCase(IonSexp target, MetaContainer metas) {
        targetArgs = IonValueExtensionsKt.getArgs(target);
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$23[this.astVersion.ordinal()]) {
            case 1: {
                clauses = IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)targetArgs);
                $this$filter$iv = clauses;
                $i$f$filter = false;
                var8_8 = $this$filter$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    it = (IonSexp)element$iv$iv;
                    $i$a$-filter-AstDeserializerInternal$deserializeSearchedCase$whenClauses$1 = false;
                    if (!(this.getNodeTag(it) == NodeTag.WHEN)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$map$iv = (List)destination$iv$iv;
                $i$f$map = false;
                $this$filterTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (IonSexp)item$iv$iv;
                    var17_21 = destination$iv$iv;
                    $i$a$-map-AstDeserializerInternal$deserializeSearchedCase$whenClauses$2 = false;
                    whenConditionExpr = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(it).get(0)));
                    thenExpr = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(it).get(1)));
                    var18_22 = new SearchedCaseWhen(whenConditionExpr, thenExpr);
                    var17_21.add(var18_22);
                }
                whenClauses = (List)destination$iv$iv;
                $this$singleOrNull$iv = clauses;
                $i$f$singleOrNull = false;
                single$iv = null;
                found$iv = false;
                for (T element$iv : $this$singleOrNull$iv) {
                    it = (IonSexp)element$iv;
                    $i$a$-singleOrNull-AstDeserializerInternal$deserializeSearchedCase$elseClause$1 = false;
                    if (!(this.getNodeTag(it) == NodeTag.ELSE)) continue;
                    if (!found$iv) ** GOTO lbl45
                    v0 = null;
                    ** GOTO lbl49
lbl45:
                    // 1 sources

                    single$iv = element$iv;
                    found$iv = true;
                }
                v0 = found$iv == false ? null : single$iv;
lbl49:
                // 2 sources

                v1 = v0;
                if (v1 != null) {
                    var7_7 = v1;
                    var8_9 = false;
                    var9_11 = false;
                    it = var7_7;
                    $i$a$-let-AstDeserializerInternal$deserializeSearchedCase$elseClause$2 = false;
                    v2 = this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(CollectionsKt.first(IonValueExtensionsKt.getArgs(it))));
                } else {
                    v2 = null;
                }
                elseClause = v2;
                return new SearchedCase(whenClauses, elseClause, metas);
            }
        }
        throw new NoWhenBranchMatchedException();
    }

    private final Pair<ExprNode, ExprNode> deserializeExprPair(IonSexp expr_pair) {
        return TuplesKt.to(this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(expr_pair).get(0))), this.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(expr_pair).get(1))));
    }

    /*
     * WARNING - void declaration
     */
    private final List<Pair<ExprNode, ExprNode>> deserializeExprPairList(IonSexp exprPairList) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)IonValueExtensionsKt.getArgs(exprPairList));
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            IonSexp ionSexp = (IonSexp)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Pair<ExprNode, ExprNode> pair = this.deserializeExprPair((IonSexp)it);
            collection.add(pair);
        }
        return (List)destination$iv$iv;
    }

    private final Path deserializePath(IonSexp pathSexp) {
        return (Path)this.deserializeSexpMetaOrTerm(pathSexp, (Function2)new Function2<IonSexp, MetaContainer, Path>(this, pathSexp){
            final /* synthetic */ AstDeserializerInternal this$0;
            final /* synthetic */ IonSexp $pathSexp;

            @NotNull
            public final Path invoke(@NotNull IonSexp target, @NotNull MetaContainer metas) {
                Intrinsics.checkParameterIsNotNull(target, "target");
                Intrinsics.checkParameterIsNotNull(metas, "metas");
                ExprNode root = this.this$0.deserializeExprNode$lang(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(target).get(0)));
                List<IonSexp> componentSexps = IonValueExtensionsKt.toListOfIonSexp((Iterable<? extends IonValue>)CollectionsKt.drop((Iterable)IonValueExtensionsKt.getArgs(this.$pathSexp), 1));
                List pathComponents = AstDeserializerInternal.access$deserializePathComponents(this.this$0, componentSexps);
                return new Path(root, pathComponents, metas);
            }
            {
                this.this$0 = astDeserializerInternal;
                this.$pathSexp = ionSexp;
                super(2);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    private final List<PathComponent> deserializePathComponents(List<? extends IonSexp> componentSexps) {
        Collection destination$iv$iv;
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$26[this.astVersion.ordinal()]) {
            case 1: {
                void $this$mapTo$iv$iv;
                Iterable $this$map$iv = componentSexps;
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void targetComponent;
                    Pair<IonSexp, CaseSensitivity> pair;
                    void componentSexp;
                    IonSexp ionSexp = (IonSexp)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$24[this.getNodeTag((IonSexp)componentSexp).ordinal()]) {
                        case 1: {
                            pair = new Pair<IonSexp, CaseSensitivity>(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs((IonSexp)componentSexp).get(0)), CaseSensitivity.INSENSITIVE);
                            break;
                        }
                        case 2: {
                            pair = new Pair<IonSexp, CaseSensitivity>(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs((IonSexp)componentSexp).get(0)), CaseSensitivity.SENSITIVE);
                            break;
                        }
                        default: {
                            pair = new Pair<void, CaseSensitivity>(componentSexp, CaseSensitivity.SENSITIVE);
                        }
                    }
                    Pair<IonSexp, CaseSensitivity> pair2 = pair;
                    IonSexp ionSexp2 = (IonSexp)pair2.component1();
                    CaseSensitivity caseSensitivity = pair2.component2();
                    PathComponent pathComponent = (PathComponent)this.deserializeSexpMetaOrTerm((IonSexp)targetComponent, (Function2)new Function2<IonSexp, MetaContainer, PathComponent>(caseSensitivity, this){
                        final /* synthetic */ CaseSensitivity $caseSensitivity;
                        final /* synthetic */ AstDeserializerInternal this$0;
                        {
                            this.$caseSensitivity = caseSensitivity;
                            this.this$0 = astDeserializerInternal;
                            super(2);
                        }

                        @NotNull
                        public final PathComponent invoke(@NotNull IonSexp target, @NotNull MetaContainer metas) {
                            PathComponent pathComponent;
                            Intrinsics.checkParameterIsNotNull(target, "target");
                            Intrinsics.checkParameterIsNotNull(metas, "metas");
                            block0 : switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$25[AstDeserializerInternal.access$getNodeTag$p(this.this$0, target).ordinal()]) {
                                case 1: {
                                    switch (IonValueExtensionsKt.getArity(target)) {
                                        case 0: {
                                            pathComponent = new PathComponentWildcard(metas);
                                            break block0;
                                        }
                                        case 1: {
                                            if (Intrinsics.areEqual(IonValueExtensionsKt.asIonSymbol(IonValueExtensionsKt.getArgs(target).get(0)).stringValue(), "unpivot") ^ true) {
                                                Void void_ = AstDeserializationKt.access$err("Invalid argument to '(*)' in path component. Expected no argument or 'unpivot'");
                                                throw null;
                                            }
                                            pathComponent = new PathComponentUnpivot(metas);
                                            break block0;
                                        }
                                    }
                                    throw (Throwable)new IllegalStateException("invalid arity for (star) or (*) (this should have been caught earlier)");
                                }
                                default: {
                                    ExprNode exprNode = this.this$0.deserializeExprNode$lang(target).copy(metas);
                                    pathComponent = new PathComponentExpr(exprNode, this.$caseSensitivity);
                                }
                            }
                            PathComponent pc = pathComponent;
                            return pc;
                        }
                    });
                    collection.add(pathComponent);
                }
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return (List)destination$iv$iv;
    }

    private final PathComponentExpr deserializePathExpr(IonSexp pathExpr) {
        IonSexp rootWithMeta = IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(pathExpr).get(0));
        CaseSensitivity caseSensitivity = CaseSensitivity.Companion.fromSymbol(IonValueExtensionsKt.getTagText(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(pathExpr).get(1))));
        return (PathComponentExpr)this.deserializeSexpMetaOrTerm(rootWithMeta, (Function2)new Function2<IonSexp, MetaContainer, PathComponentExpr>(this, caseSensitivity){
            final /* synthetic */ AstDeserializerInternal this$0;
            final /* synthetic */ CaseSensitivity $caseSensitivity;

            @NotNull
            public final PathComponentExpr invoke(@NotNull IonSexp root, @NotNull MetaContainer metas) {
                Intrinsics.checkParameterIsNotNull(root, "root");
                Intrinsics.checkParameterIsNotNull(metas, "metas");
                ExprNode rootExprNode = this.this$0.deserializeExprNode$lang(root).copy(metas);
                return new PathComponentExpr(rootExprNode, this.$caseSensitivity);
            }
            {
                this.this$0 = astDeserializerInternal;
                this.$caseSensitivity = caseSensitivity;
                super(2);
            }
        });
    }

    private final DataType deserializeDataType(IonValue dataTypeSexp) {
        switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$27[this.astVersion.ordinal()]) {
            case 1: {
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return this.deserializeDataTypeV0(dataTypeSexp);
    }

    private final DataType deserializeDataTypeV0(IonValue dataTypeSexp) {
        return (DataType)this.deserializeSexpMetaOrTerm(IonValueExtensionsKt.asIonSexp(dataTypeSexp), (Function2)new Function2<IonSexp, MetaContainer, DataType>(this){
            final /* synthetic */ AstDeserializerInternal this$0;

            /*
             * WARNING - void declaration
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @NotNull
            public final DataType invoke(@NotNull IonSexp target, @NotNull MetaContainer metas) {
                Intrinsics.checkParameterIsNotNull(target, "target");
                Intrinsics.checkParameterIsNotNull(metas, "metas");
                NodeTag nodeTag = AstDeserializerInternal.access$getNodeTag$p(this.this$0, target);
                switch (AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$28[nodeTag.ordinal()]) {
                    case 1: {
                        void $this$mapTo$iv$iv;
                        String typeName;
                        String string = typeName = IonValueExtensionsKt.asIonSymbol(IonValueExtensionsKt.getArgs(target).get(0)).stringValue();
                        Intrinsics.checkExpressionValueIsNotNull(string, "typeName");
                        SqlDataType sqlDataType = SqlDataType.Companion.forTypeName(string);
                        if (sqlDataType == null) {
                            Void void_ = AstDeserializationKt.access$err('\'' + typeName + "' is not a valid data type");
                            throw null;
                        }
                        SqlDataType sqlDataType2 = sqlDataType;
                        Iterable $this$map$iv = CollectionsKt.drop((Iterable)IonValueExtensionsKt.getArgs(target), 1);
                        boolean $i$f$map = false;
                        Iterable iterable = $this$map$iv;
                        Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        boolean $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            void it;
                            IonValue ionValue2 = (IonValue)item$iv$iv;
                            Collection collection = destination$iv$iv;
                            boolean bl = false;
                            Long l = IonValueExtensionsKt.asIonInt((IonValue)it).longValue();
                            collection.add(l);
                        }
                        List args2 = (List)destination$iv$iv;
                        if (sqlDataType2.getArityRange().contains(args2.size())) return new DataType(sqlDataType2, args2, metas);
                        Void void_ = AstDeserializationKt.access$err("Type " + typeName + " arity range " + sqlDataType2.getArityRange() + " was but " + args2.size() + " were specified");
                        throw null;
                    }
                    default: {
                        Void void_ = AstDeserializationKt.access$err("Expected `" + NodeTag.TYPE.getDefinition().getTagText() + "` tag instead found " + nodeTag.getDefinition().getTagText());
                        throw null;
                    }
                }
            }
            {
                this.this$0 = astDeserializerInternal;
                super(2);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    private final IonValue singleWrappedChildWithTagOrNull(@NotNull IonSexp $this$singleWrappedChildWithTagOrNull, String tagName) {
        Object v1;
        block7: {
            void $this$mapTo$iv$iv;
            Iterable $this$map$iv = IonValueExtensionsKt.getArgs($this$singleWrappedChildWithTagOrNull);
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                IonValue ionValue2 = (IonValue)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                IonSexp ionSexp = IonValueExtensionsKt.asIonSexp((IonValue)it);
                collection.add(ionSexp);
            }
            Iterable $this$singleOrNull$iv = (List)destination$iv$iv;
            boolean $i$f$singleOrNull = false;
            Object single$iv = null;
            boolean found$iv = false;
            for (Object element$iv : $this$singleOrNull$iv) {
                String string;
                IonSexp it = (IonSexp)element$iv;
                boolean bl = false;
                String string2 = IonValueExtensionsKt.getTagText(it);
                switch (string2.hashCode()) {
                    case 3347973: {
                        if (string2.equals("meta")) {
                            string = IonValueExtensionsKt.getTagText(IonValueExtensionsKt.asIonSexp(IonValueExtensionsKt.getArgs(it).get(0)));
                            break;
                        }
                    }
                    default: {
                        string = IonValueExtensionsKt.getTagText(it);
                    }
                }
                String tagText = string;
                if (!Intrinsics.areEqual(tagText, tagName)) continue;
                if (found$iv) {
                    v1 = null;
                    break block7;
                }
                single$iv = element$iv;
                found$iv = true;
            }
            v1 = !found$iv ? null : single$iv;
        }
        return v1;
    }

    private final SymbolicName termToSymbolicName(@NotNull IonValue $this$termToSymbolicName) {
        SymbolicName symbolicName;
        if ($this$termToSymbolicName.isNullValue()) {
            symbolicName = null;
        } else if ($this$termToSymbolicName instanceof IonSymbol) {
            String string = ((IonSymbol)$this$termToSymbolicName).stringValue();
            Intrinsics.checkExpressionValueIsNotNull(string, "this.stringValue()");
            symbolicName = new SymbolicName(string, MetaKt.getEmptyMetaContainer());
        } else if ($this$termToSymbolicName instanceof IonSexp) {
            symbolicName = (SymbolicName)this.deserializeIonValueMetaOrTerm(IonValueExtensionsKt.asIonSexp($this$termToSymbolicName), termToSymbolicName.1.INSTANCE);
        } else {
            String string = "Can't convert " + (Object)((Object)$this$termToSymbolicName.getType()) + " to a symbolic name";
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        return symbolicName;
    }

    private final SymbolicName toSymbolicName(@NotNull IonValue $this$toSymbolicName, MetaContainer metas) {
        SymbolicName symbolicName;
        if ($this$toSymbolicName.isNullValue()) {
            symbolicName = null;
        } else {
            String string = IonValueExtensionsKt.asIonSymbol($this$toSymbolicName).stringValue();
            Intrinsics.checkExpressionValueIsNotNull(string, "asIonSymbol().stringValue()");
            symbolicName = new SymbolicName(string, metas);
        }
        return symbolicName;
    }

    @NotNull
    public final AstVersion getAstVersion() {
        return this.astVersion;
    }

    @NotNull
    public final IonSystem getIon() {
        return this.ion;
    }

    public AstDeserializerInternal(@NotNull AstVersion astVersion, @NotNull IonSystem ion, @NotNull Map<String, ? extends MetaDeserializer> metaDeserializers) {
        Intrinsics.checkParameterIsNotNull((Object)astVersion, "astVersion");
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        Intrinsics.checkParameterIsNotNull(metaDeserializers, "metaDeserializers");
        this.astVersion = astVersion;
        this.ion = ion;
        this.metaDeserializers = metaDeserializers;
    }

    public static final /* synthetic */ NodeTag access$getNodeTag$p(AstDeserializerInternal $this, IonSexp $this$access_u24nodeTag_u24p) {
        return $this.getNodeTag($this$access_u24nodeTag_u24p);
    }

    public static final /* synthetic */ Literal access$deserializeLit(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeLit(targetArgs, metas);
    }

    public static final /* synthetic */ LiteralMissing access$deserializeMissing(AstDeserializerInternal $this, MetaContainer metas) {
        return $this.deserializeMissing(metas);
    }

    public static final /* synthetic */ VariableReference access$deserializeId(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeId(targetArgs, metas);
    }

    public static final /* synthetic */ VariableReference access$deserializeScopeQualifier(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeScopeQualifier(targetArgs, metas);
    }

    public static final /* synthetic */ ExprNode access$deserializeSelect(AstDeserializerInternal $this, IonSexp target, MetaContainer metas) {
        return $this.deserializeSelect(target, metas);
    }

    public static final /* synthetic */ ExprNode access$deserializeDataManipulation(AstDeserializerInternal $this, IonSexp target, MetaContainer metas) {
        return $this.deserializeDataManipulation(target, metas);
    }

    public static final /* synthetic */ Path access$deserializePath(AstDeserializerInternal $this, IonSexp pathSexp) {
        return $this.deserializePath(pathSexp);
    }

    public static final /* synthetic */ CallAgg access$deserializeCallAgg(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeCallAgg(targetArgs, metas);
    }

    public static final /* synthetic */ CallAgg access$deserializeCallAggWildcard(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeCallAggWildcard(targetArgs, metas);
    }

    public static final /* synthetic */ Struct access$deserializeStruct(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeStruct(targetArgs, metas);
    }

    public static final /* synthetic */ Seq access$deserializeSeq(AstDeserializerInternal $this, NodeTag nodeTag, List targetArgs, MetaContainer metas) {
        return $this.deserializeSeq(nodeTag, targetArgs, metas);
    }

    public static final /* synthetic */ ExprNode access$deserializeSimpleCase(AstDeserializerInternal $this, IonSexp target, MetaContainer metas) {
        return $this.deserializeSimpleCase(target, metas);
    }

    public static final /* synthetic */ ExprNode access$deserializeSearchedCase(AstDeserializerInternal $this, IonSexp target, MetaContainer metas) {
        return $this.deserializeSearchedCase(target, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryNot(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryNot(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryAdd(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryAdd(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNArySub(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNArySub(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryMul(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryMul(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryDiv(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryDiv(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryMod(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryMod(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryGt(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryGt(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryGte(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryGte(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryLt(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryLt(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryLte(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryLte(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryEq(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryEq(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryNe(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryNe(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryIn(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryIn(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryNotIn(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryNotIn(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryAnd(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryAnd(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryOr(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryOr(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryLike(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryLike(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryNotLlike(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryNotLlike(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryBetween(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryBetween(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryNotBetween(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryNotBetween(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryStringConcat(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryStringConcat(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryCall(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryCall(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryUnion(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryUnion(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryUnionAll(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryUnionAll(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryExcept(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryExcept(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryExceptAll(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryExceptAll(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryIntersect(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryIntersect(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeNAryIntersectAll(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeNAryIntersectAll(targetArgs, metas);
    }

    public static final /* synthetic */ Typed access$deserializeTypedIs(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeTypedIs(targetArgs, metas);
    }

    public static final /* synthetic */ NAry access$deserializeTypedIsNot(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeTypedIsNot(targetArgs, metas);
    }

    public static final /* synthetic */ Typed access$deserializeTypedCast(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeTypedCast(targetArgs, metas);
    }

    public static final /* synthetic */ ExprNode access$deserializeCreateV0(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeCreateV0(targetArgs, metas);
    }

    public static final /* synthetic */ DropIndex access$deserializeDropIndexV0(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeDropIndexV0(targetArgs, metas);
    }

    public static final /* synthetic */ DropTable access$deserializeDropTableV0(AstDeserializerInternal $this, List targetArgs, MetaContainer metas) {
        return $this.deserializeDropTableV0(targetArgs, metas);
    }

    public static final /* synthetic */ FromSource access$deserializeFromSourceV0(AstDeserializerInternal $this, IonSexp termOrFromSource, LetVariables variables) {
        return $this.deserializeFromSourceV0(termOrFromSource, variables);
    }

    public static final /* synthetic */ FromSourceUnpivot access$deserializeFromSourceUnpivotV0(AstDeserializerInternal $this, List targetArgs, LetVariables variables, MetaContainer metas) {
        return $this.deserializeFromSourceUnpivotV0(targetArgs, variables, metas);
    }

    public static final /* synthetic */ FromSourceJoin access$deserializeFromSourceJoinV0(AstDeserializerInternal $this, IonSexp target, List targetArgs, MetaContainer metas) {
        return $this.deserializeFromSourceJoinV0(target, targetArgs, metas);
    }

    public static final /* synthetic */ FromSourceExpr access$deserializeFromSourceExprV0(AstDeserializerInternal $this, IonSexp target, LetVariables variables, MetaContainer metas) {
        return $this.deserializeFromSourceExprV0(target, variables, metas);
    }

    public static final /* synthetic */ List access$deserializePathComponents(AstDeserializerInternal $this, List componentSexps) {
        return $this.deserializePathComponents(componentSexps);
    }
}

