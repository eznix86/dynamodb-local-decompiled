/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazon.ionelement.api.IonMeta
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.domains;

import com.amazon.ionelement.api.IonMeta;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.BindingCase;
import org.partiql.lang.eval.ExceptionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u001a\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0003j\u0004\u0018\u0001`\u0006\u001a\"\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006\u001a\u001a\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006*\u00020\n\u001a\u0012\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004\u001a\n\u0010\u000f\u001a\u00020\u0010*\u00020\u0011\u00a8\u0006\u0012"}, d2={"errorContextFrom", "Lorg/partiql/lang/errors/PropertyValueMap;", "metaContainer", "", "", "", "Lcom/amazon/ionelement/api/MetaContainer;", "addSourceLocation", "metas", "extractSourceLocation", "Lorg/partiql/lang/domains/PartiqlAst$PartiqlAstNode;", "id", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Id;", "Lorg/partiql/lang/domains/PartiqlAst$Builder;", "name", "toBindingCase", "Lorg/partiql/lang/eval/BindingCase;", "Lorg/partiql/lang/domains/PartiqlAst$CaseSensitivity;", "lang"})
public final class UtilKt {
    @NotNull
    public static final PartiqlAst.Expr.Id id(@NotNull PartiqlAst.Builder $this$id, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull($this$id, "$this$id");
        Intrinsics.checkParameterIsNotNull(name, "name");
        return PartiqlAst.Builder.DefaultImpls.id$default($this$id, name, PartiqlAst.Builder.DefaultImpls.caseInsensitive$default($this$id, null, 1, null), PartiqlAst.Builder.DefaultImpls.unqualified$default($this$id, null, 1, null), null, 8, null);
    }

    @NotNull
    public static final Map<String, Object> extractSourceLocation(@NotNull PartiqlAst.PartiqlAstNode $this$extractSourceLocation) {
        Map map2;
        Intrinsics.checkParameterIsNotNull($this$extractSourceLocation, "$this$extractSourceLocation");
        switch ($this$extractSourceLocation.getMetas().size()) {
            case 0: {
                map2 = IonMeta.emptyMetaContainer();
                break;
            }
            case 1: {
                if ($this$extractSourceLocation.getMetas().containsKey("$source_location")) {
                    map2 = $this$extractSourceLocation.getMetas();
                    break;
                }
                map2 = IonMeta.emptyMetaContainer();
                break;
            }
            default: {
                Object object = $this$extractSourceLocation.getMetas().get("$source_location");
                if (object != null) {
                    Object v = object;
                    boolean bl = false;
                    boolean bl2 = false;
                    Object it = v;
                    boolean bl3 = false;
                    object = IonMeta.metaContainerOf((Pair[])new Pair[]{TuplesKt.to("$source_location", it)});
                    if (object != null) {
                        map2 = (Map)object;
                        break;
                    }
                }
                map2 = IonMeta.emptyMetaContainer();
            }
        }
        return map2;
    }

    @NotNull
    public static final PropertyValueMap addSourceLocation(@NotNull PropertyValueMap $this$addSourceLocation, @NotNull Map<String, ? extends Object> metas) {
        block1: {
            Intrinsics.checkParameterIsNotNull($this$addSourceLocation, "$this$addSourceLocation");
            Intrinsics.checkParameterIsNotNull(metas, "metas");
            Object object = metas.get("$source_location");
            if (!(object instanceof SourceLocationMeta)) {
                object = null;
            }
            SourceLocationMeta sourceLocationMeta = (SourceLocationMeta)object;
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
    public static final BindingCase toBindingCase(@NotNull PartiqlAst.CaseSensitivity $this$toBindingCase) {
        BindingCase bindingCase;
        Intrinsics.checkParameterIsNotNull($this$toBindingCase, "$this$toBindingCase");
        PartiqlAst.CaseSensitivity caseSensitivity = $this$toBindingCase;
        if (caseSensitivity instanceof PartiqlAst.CaseSensitivity.CaseInsensitive) {
            bindingCase = BindingCase.INSENSITIVE;
        } else if (caseSensitivity instanceof PartiqlAst.CaseSensitivity.CaseSensitive) {
            bindingCase = BindingCase.SENSITIVE;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return bindingCase;
    }

    @NotNull
    public static final PropertyValueMap errorContextFrom(@Nullable Map<String, ? extends Object> metaContainer) {
        SourceLocationMeta location;
        if (metaContainer == null) {
            return new PropertyValueMap(null, 1, null);
        }
        Object object = metaContainer.get("$source_location");
        if (!(object instanceof SourceLocationMeta)) {
            object = null;
        }
        return (location = (SourceLocationMeta)object) != null ? ExceptionsKt.errorContextFrom(location) : new PropertyValueMap(null, 1, null);
    }
}

