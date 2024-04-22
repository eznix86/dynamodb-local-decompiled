/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazon.ionelement.api.AnyElement
 *  com.amazon.ionelement.api.IntElement
 *  com.amazon.ionelement.api.IntElementSize
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.visitors;

import com.amazon.ionelement.api.AnyElement;
import com.amazon.ionelement.api.IntElement;
import com.amazon.ionelement.api.IntElementSize;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.passes.SemanticException;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.domains.UtilKt;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.ExceptionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0014J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u000bH\u0014J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\rH\u0014\u00a8\u0006\u000e"}, d2={"Lorg/partiql/lang/eval/visitors/PartiqlAstSanityValidator;", "Lorg/partiql/lang/domains/PartiqlAst$Visitor;", "()V", "validate", "", "statement", "Lorg/partiql/lang/domains/PartiqlAst$Statement;", "visitExprCallAgg", "node", "Lorg/partiql/lang/domains/PartiqlAst$Expr$CallAgg;", "visitExprLit", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Lit;", "visitExprSelect", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Select;", "lang"})
public final class PartiqlAstSanityValidator
extends PartiqlAst.Visitor {
    public static final PartiqlAstSanityValidator INSTANCE;

    public final void validate(@NotNull PartiqlAst.Statement statement) {
        Intrinsics.checkParameterIsNotNull(statement, "statement");
        this.walkStatement(statement);
    }

    @Override
    protected void visitExprLit(@NotNull PartiqlAst.Expr.Lit node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        AnyElement ionValue2 = node.getValue();
        Map<String, Object> metas = node.getMetas();
        if (node.getValue() instanceof IntElement && ionValue2.getIntegerSize() == IntElementSize.BIG_INTEGER) {
            Void void_ = ExceptionsKt.errIntOverflow(UtilKt.errorContextFrom(metas));
            throw null;
        }
    }

    @Override
    protected void visitExprCallAgg(@NotNull PartiqlAst.Expr.CallAgg node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        PartiqlAst.SetQuantifier setQuantifier = node.getSetq();
        Map<String, Object> metas = node.getMetas();
        if (setQuantifier instanceof PartiqlAst.SetQuantifier.Distinct && metas.containsKey("$is_count_star")) {
            Void void_ = ExceptionsKt.err("COUNT(DISTINCT *) is not supported", ErrorCode.EVALUATOR_COUNT_DISTINCT_STAR, UtilKt.errorContextFrom(metas), false);
            throw null;
        }
    }

    @Override
    protected void visitExprSelect(@NotNull PartiqlAst.Expr.Select node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        PartiqlAst.Projection projection = node.getProject();
        PartiqlAst.GroupBy groupBy2 = node.getGroup();
        PartiqlAst.Expr having2 = node.getHaving();
        Map<String, Object> metas = node.getMetas();
        if (groupBy2 != null) {
            if (groupBy2.getStrategy() instanceof PartiqlAst.GroupingStrategy.GroupPartial) {
                PropertyValueMap propertyValueMap = UtilKt.errorContextFrom(metas);
                ErrorCode errorCode = ErrorCode.EVALUATOR_FEATURE_NOT_SUPPORTED_YET;
                String string = "GROUP PARTIAL not supported yet";
                boolean bl = false;
                boolean bl2 = false;
                PropertyValueMap it = propertyValueMap;
                boolean bl3 = false;
                it.set(Property.FEATURE_NAME, "GROUP PARTIAL");
                PropertyValueMap propertyValueMap2 = propertyValueMap;
                Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap2, false);
                throw null;
            }
            PartiqlAst.Projection projection2 = projection;
            if (projection2 instanceof PartiqlAst.Projection.ProjectPivot) {
                PropertyValueMap propertyValueMap = UtilKt.errorContextFrom(metas);
                ErrorCode errorCode = ErrorCode.EVALUATOR_FEATURE_NOT_SUPPORTED_YET;
                String string = "PIVOT with GROUP BY not supported yet";
                boolean bl = false;
                boolean bl4 = false;
                PropertyValueMap it = propertyValueMap;
                boolean bl5 = false;
                it.set(Property.FEATURE_NAME, "PIVOT with GROUP BY");
                PropertyValueMap propertyValueMap3 = propertyValueMap;
                Void void_ = ExceptionsKt.err(string, errorCode, propertyValueMap3, false);
                throw null;
            }
            if (projection2 instanceof PartiqlAst.Projection.ProjectValue || projection2 instanceof PartiqlAst.Projection.ProjectList) {
                // empty if block
            }
        }
        if ((groupBy2 == null || groupBy2.getKeyList().getKeys().isEmpty()) && having2 != null) {
            throw (Throwable)new SemanticException("HAVING used without GROUP BY (or grouping expressions)", ErrorCode.SEMANTIC_HAVING_USED_WITHOUT_GROUP_BY, UtilKt.addSourceLocation(new PropertyValueMap(null, 1, null), metas), null, 8, null);
        }
    }

    private PartiqlAstSanityValidator() {
    }

    static {
        PartiqlAstSanityValidator partiqlAstSanityValidator;
        INSTANCE = partiqlAstSanityValidator = new PartiqlAstSanityValidator();
    }
}

