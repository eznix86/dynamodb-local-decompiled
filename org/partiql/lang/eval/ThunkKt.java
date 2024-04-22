/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ThunkKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\",\u0010\u0000\u001a\u001a\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00040\u0001j\u0002`\u0005X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007*\"\u0010\b\"\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t*4\u0010\f\u001a\u0004\b\u0000\u0010\r\"\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u000b0\u00012\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u000b0\u0001*2\u0010\u000e\"\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00040\u00012\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00040\u0001\u00a8\u0006\u000f"}, d2={"defaultExceptionHandler", "Lkotlin/Function2;", "", "Lorg/partiql/lang/ast/SourceLocationMeta;", "", "Lorg/partiql/lang/eval/ThunkExceptionHandler;", "getDefaultExceptionHandler", "()Lkotlin/jvm/functions/Function2;", "ThunkEnv", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Environment;", "Lorg/partiql/lang/eval/ExprValue;", "ThunkEnvValue", "T", "ThunkExceptionHandler", "lang"})
public final class ThunkKt {
    @NotNull
    private static final Function2 defaultExceptionHandler = defaultExceptionHandler.1.INSTANCE;

    @NotNull
    public static final Function2 getDefaultExceptionHandler() {
        return defaultExceptionHandler;
    }
}

