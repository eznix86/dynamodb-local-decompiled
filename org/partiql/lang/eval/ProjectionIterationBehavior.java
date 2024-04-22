/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/ProjectionIterationBehavior;", "", "(Ljava/lang/String;I)V", "FILTER_MISSING", "UNFILTERED", "lang"})
public final class ProjectionIterationBehavior
extends Enum<ProjectionIterationBehavior> {
    public static final /* enum */ ProjectionIterationBehavior FILTER_MISSING;
    public static final /* enum */ ProjectionIterationBehavior UNFILTERED;
    private static final /* synthetic */ ProjectionIterationBehavior[] $VALUES;

    static {
        ProjectionIterationBehavior[] projectionIterationBehaviorArray = new ProjectionIterationBehavior[2];
        ProjectionIterationBehavior[] projectionIterationBehaviorArray2 = projectionIterationBehaviorArray;
        projectionIterationBehaviorArray[0] = FILTER_MISSING = new ProjectionIterationBehavior();
        projectionIterationBehaviorArray[1] = UNFILTERED = new ProjectionIterationBehavior();
        $VALUES = projectionIterationBehaviorArray;
    }

    public static ProjectionIterationBehavior[] values() {
        return (ProjectionIterationBehavior[])$VALUES.clone();
    }

    public static ProjectionIterationBehavior valueOf(String string) {
        return Enum.valueOf(ProjectionIterationBehavior.class, string);
    }
}

