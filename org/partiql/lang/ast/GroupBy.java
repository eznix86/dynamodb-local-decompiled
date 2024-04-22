/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.GroupByItem;
import org.partiql.lang.ast.GroupingStrategy;
import org.partiql.lang.ast.SymbolicName;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\bH\u00c6\u0003J/\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001e"}, d2={"Lorg/partiql/lang/ast/GroupBy;", "Lorg/partiql/lang/ast/AstNode;", "grouping", "Lorg/partiql/lang/ast/GroupingStrategy;", "groupByItems", "", "Lorg/partiql/lang/ast/GroupByItem;", "groupName", "Lorg/partiql/lang/ast/SymbolicName;", "(Lorg/partiql/lang/ast/GroupingStrategy;Ljava/util/List;Lorg/partiql/lang/ast/SymbolicName;)V", "children", "getChildren", "()Ljava/util/List;", "getGroupByItems", "getGroupName", "()Lorg/partiql/lang/ast/SymbolicName;", "getGrouping", "()Lorg/partiql/lang/ast/GroupingStrategy;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class GroupBy
extends AstNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final GroupingStrategy grouping;
    @NotNull
    private final List<GroupByItem> groupByItems;
    @Nullable
    private final SymbolicName groupName;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final GroupingStrategy getGrouping() {
        return this.grouping;
    }

    @NotNull
    public final List<GroupByItem> getGroupByItems() {
        return this.groupByItems;
    }

    @Nullable
    public final SymbolicName getGroupName() {
        return this.groupName;
    }

    public GroupBy(@NotNull GroupingStrategy grouping, @NotNull List<GroupByItem> groupByItems, @Nullable SymbolicName groupName) {
        Intrinsics.checkParameterIsNotNull((Object)grouping, "grouping");
        Intrinsics.checkParameterIsNotNull(groupByItems, "groupByItems");
        super(null);
        this.grouping = grouping;
        this.groupByItems = groupByItems;
        this.groupName = groupName;
        this.children = this.groupByItems;
    }

    public /* synthetic */ GroupBy(GroupingStrategy groupingStrategy, List list, SymbolicName symbolicName, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            symbolicName = null;
        }
        this(groupingStrategy, list, symbolicName);
    }

    @NotNull
    public final GroupingStrategy component1() {
        return this.grouping;
    }

    @NotNull
    public final List<GroupByItem> component2() {
        return this.groupByItems;
    }

    @Nullable
    public final SymbolicName component3() {
        return this.groupName;
    }

    @NotNull
    public final GroupBy copy(@NotNull GroupingStrategy grouping, @NotNull List<GroupByItem> groupByItems, @Nullable SymbolicName groupName) {
        Intrinsics.checkParameterIsNotNull((Object)grouping, "grouping");
        Intrinsics.checkParameterIsNotNull(groupByItems, "groupByItems");
        return new GroupBy(grouping, groupByItems, groupName);
    }

    public static /* synthetic */ GroupBy copy$default(GroupBy groupBy2, GroupingStrategy groupingStrategy, List list, SymbolicName symbolicName, int n, Object object) {
        if ((n & 1) != 0) {
            groupingStrategy = groupBy2.grouping;
        }
        if ((n & 2) != 0) {
            list = groupBy2.groupByItems;
        }
        if ((n & 4) != 0) {
            symbolicName = groupBy2.groupName;
        }
        return groupBy2.copy(groupingStrategy, list, symbolicName);
    }

    @NotNull
    public String toString() {
        return "GroupBy(grouping=" + (Object)((Object)this.grouping) + ", groupByItems=" + this.groupByItems + ", groupName=" + this.groupName + ")";
    }

    public int hashCode() {
        GroupingStrategy groupingStrategy = this.grouping;
        List<GroupByItem> list = this.groupByItems;
        SymbolicName symbolicName = this.groupName;
        return ((groupingStrategy != null ? ((Object)((Object)groupingStrategy)).hashCode() : 0) * 31 + (list != null ? ((Object)list).hashCode() : 0)) * 31 + (symbolicName != null ? ((Object)symbolicName).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof GroupBy)) break block3;
                GroupBy groupBy2 = (GroupBy)object;
                if (!Intrinsics.areEqual((Object)this.grouping, (Object)groupBy2.grouping) || !Intrinsics.areEqual(this.groupByItems, groupBy2.groupByItems) || !Intrinsics.areEqual(this.groupName, groupBy2.groupName)) break block3;
            }
            return true;
        }
        return false;
    }
}

