/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.partiql.lang.ast.AstNode;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0003\u0004\u0005\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/ast/PathComponent;", "Lorg/partiql/lang/ast/AstNode;", "()V", "Lorg/partiql/lang/ast/PathComponentExpr;", "Lorg/partiql/lang/ast/PathComponentUnpivot;", "Lorg/partiql/lang/ast/PathComponentWildcard;", "lang"})
public abstract class PathComponent
extends AstNode {
    private PathComponent() {
        super(null);
    }

    public /* synthetic */ PathComponent(DefaultConstructorMarker $constructor_marker) {
        this();
    }
}

