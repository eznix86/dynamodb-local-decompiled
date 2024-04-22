/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.eval.visitors.PipelinedVisitorTransform;
import org.partiql.lang.eval.visitors.VisitorTransformsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\r\u0010\u0003\u001a\u00020\u0004H \u00a2\u0006\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/eval/VisitorTransformMode;", "", "(Ljava/lang/String;I)V", "createVisitorTransform", "Lorg/partiql/lang/domains/PartiqlAst$VisitorTransform;", "createVisitorTransform$lang", "DEFAULT", "NONE", "lang"})
public abstract class VisitorTransformMode
extends Enum<VisitorTransformMode> {
    public static final /* enum */ VisitorTransformMode DEFAULT;
    public static final /* enum */ VisitorTransformMode NONE;
    private static final /* synthetic */ VisitorTransformMode[] $VALUES;

    static {
        VisitorTransformMode[] visitorTransformModeArray = new VisitorTransformMode[2];
        VisitorTransformMode[] visitorTransformModeArray2 = visitorTransformModeArray;
        visitorTransformModeArray[0] = DEFAULT = new DEFAULT("DEFAULT", 0);
        visitorTransformModeArray[1] = NONE = new NONE("NONE", 1);
        $VALUES = visitorTransformModeArray;
    }

    @NotNull
    public abstract PartiqlAst.VisitorTransform createVisitorTransform$lang();

    private VisitorTransformMode() {
    }

    public /* synthetic */ VisitorTransformMode(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1, DefaultConstructorMarker $constructor_marker) {
        this();
    }

    public static VisitorTransformMode[] values() {
        return (VisitorTransformMode[])$VALUES.clone();
    }

    public static VisitorTransformMode valueOf(String string) {
        return Enum.valueOf(VisitorTransformMode.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0001\u0018\u00002\u00020\u0001J\r\u0010\u0002\u001a\u00020\u0003H\u0010\u00a2\u0006\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/VisitorTransformMode$DEFAULT;", "Lorg/partiql/lang/eval/VisitorTransformMode;", "createVisitorTransform", "Lorg/partiql/lang/eval/visitors/PipelinedVisitorTransform;", "createVisitorTransform$lang", "lang"})
    static final class DEFAULT
    extends VisitorTransformMode {
        @Override
        @NotNull
        public PipelinedVisitorTransform createVisitorTransform$lang() {
            return VisitorTransformsKt.basicVisitorTransforms();
        }

        /*
         * WARNING - void declaration
         */
        DEFAULT() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0001\u0018\u00002\u00020\u0001J\r\u0010\u0002\u001a\u00020\u0003H\u0010\u00a2\u0006\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/VisitorTransformMode$NONE;", "Lorg/partiql/lang/eval/VisitorTransformMode;", "createVisitorTransform", "Lorg/partiql/lang/domains/PartiqlAst$VisitorTransform;", "createVisitorTransform$lang", "lang"})
    static final class NONE
    extends VisitorTransformMode {
        @Override
        @NotNull
        public PartiqlAst.VisitorTransform createVisitorTransform$lang() {
            return VisitorTransformsKt.IDENTITY_VISITOR_TRANSFORM;
        }

        /*
         * WARNING - void declaration
         */
        NONE() {
            void var1_1;
        }
    }
}

