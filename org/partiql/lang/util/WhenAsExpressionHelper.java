/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.util;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/util/WhenAsExpressionHelper;", "", "()V", "toUnit", "", "Companion", "lang"})
public final class WhenAsExpressionHelper {
    @NotNull
    private static final WhenAsExpressionHelper Instance;
    public static final Companion Companion;

    public final void toUnit() {
    }

    private WhenAsExpressionHelper() {
    }

    static {
        Companion = new Companion(null);
        Instance = new WhenAsExpressionHelper();
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/util/WhenAsExpressionHelper$Companion;", "", "()V", "Instance", "Lorg/partiql/lang/util/WhenAsExpressionHelper;", "getInstance", "()Lorg/partiql/lang/util/WhenAsExpressionHelper;", "lang"})
    public static final class Companion {
        @NotNull
        public final WhenAsExpressionHelper getInstance() {
            return Instance;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

