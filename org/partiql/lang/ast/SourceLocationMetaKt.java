/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.MetaKt;
import org.partiql.lang.ast.SourceLocationMeta;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"'\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\"\u001b\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00060\bj\u0002`\t8F\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\n\"\u001d\u0010\u000b\u001a\u00060\bj\u0002`\t*\u00060\bj\u0002`\t8F\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2={"sourceLocation", "Lorg/partiql/lang/ast/SourceLocationMeta;", "", "", "", "Lorg/partiql/lang/ast/IonElementMetaContainer;", "getSourceLocation", "(Ljava/util/Map;)Lorg/partiql/lang/ast/SourceLocationMeta;", "Lorg/partiql/lang/ast/MetaContainer;", "Lorg/partiql/lang/ast/PartiQlMetaContainer;", "(Lorg/partiql/lang/ast/MetaContainer;)Lorg/partiql/lang/ast/SourceLocationMeta;", "sourceLocationContainer", "getSourceLocationContainer", "(Lorg/partiql/lang/ast/MetaContainer;)Lorg/partiql/lang/ast/MetaContainer;", "lang"})
public final class SourceLocationMetaKt {
    @Nullable
    public static final SourceLocationMeta getSourceLocation(@NotNull MetaContainer $this$sourceLocation) {
        Intrinsics.checkParameterIsNotNull($this$sourceLocation, "$this$sourceLocation");
        return (SourceLocationMeta)$this$sourceLocation.find("$source_location");
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final SourceLocationMeta getSourceLocation(@NotNull Map<String, ? extends Object> $this$sourceLocation) {
        void $this$metaOrNull$iv;
        Intrinsics.checkParameterIsNotNull($this$sourceLocation, "$this$sourceLocation");
        Map<String, ? extends Object> map2 = $this$sourceLocation;
        String key$iv = "$source_location";
        boolean $i$f$metaOrNull = false;
        return (SourceLocationMeta)$this$metaOrNull$iv.get(key$iv);
    }

    @NotNull
    public static final MetaContainer getSourceLocationContainer(@NotNull MetaContainer $this$sourceLocationContainer) {
        Object object;
        block3: {
            block2: {
                Intrinsics.checkParameterIsNotNull($this$sourceLocationContainer, "$this$sourceLocationContainer");
                object = SourceLocationMetaKt.getSourceLocation($this$sourceLocationContainer);
                if (object == null) break block2;
                SourceLocationMeta sourceLocationMeta = object;
                boolean bl = false;
                boolean bl2 = false;
                SourceLocationMeta it = sourceLocationMeta;
                boolean bl3 = false;
                object = MetaKt.metaContainerOf(it);
                if (object != null) break block3;
            }
            object = MetaKt.getEmptyMetaContainer();
        }
        return object;
    }
}

