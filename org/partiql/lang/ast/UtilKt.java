/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonInt;
import com.amazon.ion.IonSystem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.CallAgg;
import org.partiql.lang.ast.CaseSensitivity;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.IsCountStarMeta;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.MetaKt;
import org.partiql.lang.ast.ScopeQualifier;
import org.partiql.lang.ast.SetQuantifier;
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.ast.VariableReference;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0012\u0010\u0006\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005\u00a8\u0006\b"}, d2={"createCountStar", "Lorg/partiql/lang/ast/CallAgg;", "ion", "Lcom/amazon/ion/IonSystem;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "addSourceLocation", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
public final class UtilKt {
    @NotNull
    public static final PropertyValueMap addSourceLocation(@NotNull PropertyValueMap $this$addSourceLocation, @NotNull MetaContainer metas) {
        block1: {
            Intrinsics.checkParameterIsNotNull($this$addSourceLocation, "$this$addSourceLocation");
            Intrinsics.checkParameterIsNotNull(metas, "metas");
            Meta meta = metas.find("$source_location");
            if (!(meta instanceof SourceLocationMeta)) {
                meta = null;
            }
            SourceLocationMeta sourceLocationMeta = (SourceLocationMeta)meta;
            if (sourceLocationMeta == null) break block1;
            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
            boolean bl = false;
            boolean bl2 = false;
            SourceLocationMeta it = sourceLocationMeta2;
            boolean bl3 = false;
            $this$addSourceLocation.set(Property.LINE_NUMBER, it.getLineNum());
            $this$addSourceLocation.set(Property.COLUMN_NUMBER, it.getCharOffset());
        }
        return $this$addSourceLocation;
    }

    @NotNull
    public static final CallAgg createCountStar(@NotNull IonSystem ion, @NotNull MetaContainer metas) {
        Object object;
        block3: {
            block2: {
                Intrinsics.checkParameterIsNotNull(ion, "ion");
                Intrinsics.checkParameterIsNotNull(metas, "metas");
                object = metas.find("$source_location");
                if (object == null) break block2;
                Meta meta = object;
                boolean bl = false;
                boolean bl2 = false;
                Meta it = meta;
                boolean bl3 = false;
                object = MetaKt.metaContainerOf(it);
                if (object != null) break block3;
            }
            object = MetaKt.metaContainerOf(new Meta[0]);
        }
        Object srcLocationMetaOnly = object;
        ExprNode exprNode = new VariableReference("count", CaseSensitivity.INSENSITIVE, ScopeQualifier.UNQUALIFIED, (MetaContainer)srcLocationMetaOnly);
        IonInt ionInt = ion.newInt(1);
        Intrinsics.checkExpressionValueIsNotNull(ionInt, "ion.newInt(1)");
        return new CallAgg(exprNode, SetQuantifier.ALL, new Literal(ionInt, (MetaContainer)srcLocationMetaOnly), metas.add(IsCountStarMeta.Companion.getInstance()));
    }
}

