/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.DataManipulationOperation;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/ast/DeleteOp;", "Lorg/partiql/lang/ast/DataManipulationOperation;", "()V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "lang"})
public final class DeleteOp
extends DataManipulationOperation {
    public static final DeleteOp INSTANCE;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return CollectionsKt.emptyList();
    }

    private DeleteOp() {
        super("delete", null);
    }

    static {
        DeleteOp deleteOp;
        INSTANCE = deleteOp = new DeleteOp();
    }
}

