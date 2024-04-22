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
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.SelectListItem;
import org.partiql.lang.ast.SelectProjection;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\f\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0015"}, d2={"Lorg/partiql/lang/ast/SelectProjectionList;", "Lorg/partiql/lang/ast/SelectProjection;", "items", "", "Lorg/partiql/lang/ast/SelectListItem;", "(Ljava/util/List;)V", "children", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getItems", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class SelectProjectionList
extends SelectProjection {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final List<SelectListItem> items;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final List<SelectListItem> getItems() {
        return this.items;
    }

    public SelectProjectionList(@NotNull List<? extends SelectListItem> items) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        super(null);
        this.items = items;
        this.children = this.items;
    }

    @NotNull
    public final List<SelectListItem> component1() {
        return this.items;
    }

    @NotNull
    public final SelectProjectionList copy(@NotNull List<? extends SelectListItem> items) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        return new SelectProjectionList(items);
    }

    public static /* synthetic */ SelectProjectionList copy$default(SelectProjectionList selectProjectionList, List list, int n, Object object) {
        if ((n & 1) != 0) {
            list = selectProjectionList.items;
        }
        return selectProjectionList.copy(list);
    }

    @NotNull
    public String toString() {
        return "SelectProjectionList(items=" + this.items + ")";
    }

    public int hashCode() {
        List<SelectListItem> list = this.items;
        return list != null ? ((Object)list).hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SelectProjectionList)) break block3;
                SelectProjectionList selectProjectionList = (SelectProjectionList)object;
                if (!Intrinsics.areEqual(this.items, selectProjectionList.items)) break block3;
            }
            return true;
        }
        return false;
    }
}

