/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import org.partiql.lang.ast.NodeTag;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u0016\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u00a8\u0006\u0007"}, d2={"err", "", "message", "", "errInvalidContext", "nodeTag", "Lorg/partiql/lang/ast/NodeTag;", "lang"})
public final class AstDeserializationKt {
    private static final Void err(String message) {
        throw (Throwable)new IllegalArgumentException(message);
    }

    private static final Void errInvalidContext(NodeTag nodeTag) {
        throw (Throwable)new IllegalArgumentException("Invalid context for " + nodeTag.getDefinition().getTagText() + " node");
    }

    public static final /* synthetic */ Void access$err(String message) {
        return AstDeserializationKt.err(message);
    }

    public static final /* synthetic */ Void access$errInvalidContext(NodeTag nodeTag) {
        return AstDeserializationKt.errInvalidContext(nodeTag);
    }
}

