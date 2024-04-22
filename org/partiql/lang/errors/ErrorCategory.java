/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.errors;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\f"}, d2={"Lorg/partiql/lang/errors/ErrorCategory;", "", "message", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "toString", "LEXER", "PARSER", "SEMANTIC", "EVALUATOR", "lang"})
public final class ErrorCategory
extends Enum<ErrorCategory> {
    public static final /* enum */ ErrorCategory LEXER;
    public static final /* enum */ ErrorCategory PARSER;
    public static final /* enum */ ErrorCategory SEMANTIC;
    public static final /* enum */ ErrorCategory EVALUATOR;
    private static final /* synthetic */ ErrorCategory[] $VALUES;
    @NotNull
    private final String message;

    static {
        ErrorCategory[] errorCategoryArray = new ErrorCategory[4];
        ErrorCategory[] errorCategoryArray2 = errorCategoryArray;
        errorCategoryArray[0] = LEXER = new ErrorCategory("Lexer Error");
        errorCategoryArray[1] = PARSER = new ErrorCategory("Parser Error");
        errorCategoryArray[2] = SEMANTIC = new ErrorCategory("Semantic Error");
        errorCategoryArray[3] = EVALUATOR = new ErrorCategory("Evaluator Error");
        $VALUES = errorCategoryArray;
    }

    @NotNull
    public String toString() {
        return this.message;
    }

    @NotNull
    public final String getMessage() {
        return this.message;
    }

    private ErrorCategory(String message) {
        this.message = message;
    }

    public static ErrorCategory[] values() {
        return (ErrorCategory[])$VALUES.clone();
    }

    public static ErrorCategory valueOf(String string) {
        return Enum.valueOf(ErrorCategory.class, string);
    }
}

