/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.SourceLocationMeta;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0005"}, d2={"sourceLocationMeta", "Lorg/partiql/lang/ast/SourceLocationMeta;", "Lorg/partiql/lang/ast/MetaContainer;", "getSourceLocationMeta", "(Lorg/partiql/lang/ast/MetaContainer;)Lorg/partiql/lang/ast/SourceLocationMeta;", "lang"})
public final class EvaluatingCompilerKt {
    private static final SourceLocationMeta getSourceLocationMeta(@NotNull MetaContainer $this$sourceLocationMeta) {
        Meta meta = $this$sourceLocationMeta.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        return (SourceLocationMeta)meta;
    }

    public static final /* synthetic */ SourceLocationMeta access$getSourceLocationMeta$p(MetaContainer $this$access_u24sourceLocationMeta_u24p) {
        return EvaluatingCompilerKt.getSourceLocationMeta($this$access_u24sourceLocationMeta_u24p);
    }
}

