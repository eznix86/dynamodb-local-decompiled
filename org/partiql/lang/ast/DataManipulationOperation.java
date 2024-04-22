/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.AstNode;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0005\u0007\b\t\n\u000b\u00a8\u0006\f"}, d2={"Lorg/partiql/lang/ast/DataManipulationOperation;", "Lorg/partiql/lang/ast/AstNode;", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "Lorg/partiql/lang/ast/InsertOp;", "Lorg/partiql/lang/ast/InsertValueOp;", "Lorg/partiql/lang/ast/AssignmentOp;", "Lorg/partiql/lang/ast/RemoveOp;", "Lorg/partiql/lang/ast/DeleteOp;", "lang"})
public abstract class DataManipulationOperation
extends AstNode {
    @NotNull
    private final String name;

    @NotNull
    public final String getName() {
        return this.name;
    }

    private DataManipulationOperation(String name) {
        super(null);
        this.name = name;
    }

    public /* synthetic */ DataManipulationOperation(String name, DefaultConstructorMarker $constructor_marker) {
        this(name);
    }
}

