/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.util;

import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import org.partiql.lang.ast.NAryOp;

public abstract class OperatorMappingsBase<R> {
    public static Operator getLogicalOperator(NAryOp nAryOp) {
        if (nAryOp == NAryOp.AND) {
            return Operator.AND;
        }
        if (nAryOp == NAryOp.OR) {
            return Operator.OR;
        }
        if (nAryOp == NAryOp.NOT) {
            return Operator.NOT;
        }
        return null;
    }

    public static Operator getComparator(NAryOp nAryOp) {
        Operator comparator = Operator.getSymbolComparator(nAryOp.getSymbol());
        if (comparator != null) {
            return comparator;
        }
        if (nAryOp == NAryOp.BETWEEN) {
            return Operator.BETWEEN;
        }
        if (nAryOp == NAryOp.IN) {
            return Operator.IN;
        }
        return null;
    }

    public abstract R getComparisonOperator(Operator var1);
}

