/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.util.ThreadInterruptUtilsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00000\tH\u0097\u0002R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00000\u00048&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0018\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\u00a8\u0006\""}, d2={"Lorg/partiql/lang/ast/AstNode;", "", "()V", "children", "", "children$annotations", "getChildren", "()Ljava/util/List;", "iterator", "", "Lorg/partiql/lang/ast/ExprNode;", "Lorg/partiql/lang/ast/SimpleCaseWhen;", "Lorg/partiql/lang/ast/SearchedCaseWhen;", "Lorg/partiql/lang/ast/DataManipulationOperation;", "Lorg/partiql/lang/ast/DmlOpList;", "Lorg/partiql/lang/ast/OnConflict;", "Lorg/partiql/lang/ast/Assignment;", "Lorg/partiql/lang/ast/InsertReturning;", "Lorg/partiql/lang/ast/ReturningExpr;", "Lorg/partiql/lang/ast/ReturningElem;", "Lorg/partiql/lang/ast/ColumnComponent;", "Lorg/partiql/lang/ast/SymbolicName;", "Lorg/partiql/lang/ast/PathComponent;", "Lorg/partiql/lang/ast/SelectProjection;", "Lorg/partiql/lang/ast/SelectListItem;", "Lorg/partiql/lang/ast/FromSource;", "Lorg/partiql/lang/ast/LetSource;", "Lorg/partiql/lang/ast/LetBinding;", "Lorg/partiql/lang/ast/GroupBy;", "Lorg/partiql/lang/ast/GroupByItem;", "Lorg/partiql/lang/ast/OrderBy;", "Lorg/partiql/lang/ast/SortSpec;", "Lorg/partiql/lang/ast/StructField;", "Lorg/partiql/lang/ast/DataType;", "lang"})
public abstract class AstNode
implements Iterable<AstNode>,
KMappedMarker {
    @Deprecated(message="DO NOT USE - see kdoc, see https://github.com/partiql/partiql-lang-kotlin/issues/396")
    public static /* synthetic */ void children$annotations() {
    }

    @NotNull
    public abstract List<AstNode> getChildren();

    @Override
    @Deprecated(message="DO NOT USE - see kdoc for alternatives")
    @NotNull
    public Iterator<AstNode> iterator() {
        boolean bl = false;
        List allNodes = new ArrayList();
        Function1<AstNode, Unit> $fun$depthFirstSequence$1 = new Function1<AstNode, Unit>(allNodes){
            final /* synthetic */ List $allNodes;

            /*
             * WARNING - void declaration
             */
            public final void invoke(@NotNull AstNode node) {
                void $this$mapTo$iv$iv$iv;
                Intrinsics.checkParameterIsNotNull(node, "node");
                this.$allNodes.add(node);
                List<AstNode> $this$interruptibleMap$iv = node.getChildren();
                boolean $i$f$interruptibleMap = false;
                Iterable $this$map$iv$iv = $this$interruptibleMap$iv;
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv$iv;
                Collection destination$iv$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv$iv, 10));
                boolean $i$f$mapTo = false;
                Iterator<T> iterator2 = $this$mapTo$iv$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    void it$iv;
                    T item$iv$iv$iv;
                    T t = item$iv$iv$iv = iterator2.next();
                    Collection collection = destination$iv$iv$iv;
                    boolean bl = false;
                    ThreadInterruptUtilsKt.checkThreadInterrupted();
                    AstNode it = (AstNode)it$iv;
                    boolean bl2 = false;
                    this.invoke(it);
                    Unit unit = Unit.INSTANCE;
                    collection.add(unit);
                }
                List cfr_ignored_0 = (List)destination$iv$iv$iv;
            }
            {
                this.$allNodes = list;
                super(1);
            }
        };
        $fun$depthFirstSequence$1.invoke(this);
        return CollectionsKt.toList(allNodes).iterator();
    }

    private AstNode() {
    }

    public /* synthetic */ AstNode(DefaultConstructorMarker $constructor_marker) {
        this();
    }
}

