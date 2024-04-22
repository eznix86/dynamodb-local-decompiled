/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.util;

import com.amazon.ion.system.IonTextWriterBuilder;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.parser.ExpressionParser;
import org.partiql.lang.ast.CallAgg;
import org.partiql.lang.ast.CreateIndex;
import org.partiql.lang.ast.CreateTable;
import org.partiql.lang.ast.DropIndex;
import org.partiql.lang.ast.DropTable;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.Parameter;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.SearchedCase;
import org.partiql.lang.ast.Select;
import org.partiql.lang.ast.Seq;
import org.partiql.lang.ast.SimpleCase;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.VariableReference;

public final class ExprNodeTranslators {
    public static Operator translateCustomFunctionToDynamoOperator(VariableReference variableReference, ExpressionParser.ExpressionType type, PartiQLDbEnv dbEnv) {
        String traceHeader = "translateCustomFunctionToDynamoOperator";
        Operator operator = Operator.getFunctionOperator(variableReference.getId(), dbEnv);
        if (operator == null) {
            throw dbEnv.createValidationError(new ExceptionMessageBuilder("Unrecognized function: %s", variableReference).build(variableReference.getId()));
        }
        switch (type) {
            case CONDITION: {
                Operator.validateConditionFunction(operator, dbEnv);
                break;
            }
            case UPDATE: {
                Operator.validateValueFunction(operator, dbEnv);
                break;
            }
            default: {
                dbEnv.dbPqlAssert(false, "translateCustomFunctionToDynamoOperator", "Unexpected Expression Type", (Object)type);
            }
        }
        return operator;
    }

    public static String extractExprIdentifierAsString(ExprNode node) {
        String traceHeader = "extractStringFromExprNode";
        if (node instanceof Literal) {
            return ((Literal)node).getIonValue().toString(IonTextWriterBuilder.minimal());
        }
        if (node instanceof LiteralMissing) {
            return ExprNodeTokens.MISSING.name();
        }
        if (node instanceof VariableReference) {
            return ((VariableReference)node).getId();
        }
        if (node instanceof NAry) {
            NAryOp op = ((NAry)node).getOp();
            if (op.equals((Object)NAryOp.CALL)) {
                VariableReference functionName = (VariableReference)((NAry)node).getArgs().get(0);
                return functionName.getId();
            }
            return op.getSymbol();
        }
        if (node instanceof CallAgg) {
            return ((VariableReference)((CallAgg)node).getFuncExpr()).getId();
        }
        if (node instanceof Typed) {
            return ((Typed)node).getOp().getText();
        }
        if (node instanceof Path) {
            return ExprNodeTokens.PATH.name();
        }
        if (node instanceof SimpleCase) {
            return ExprNodeTokens.CASE + " " + ExprNodeTranslators.extractExprIdentifierAsString(((SimpleCase)node).getValueExpr());
        }
        if (node instanceof SearchedCase) {
            return ExprNodeTokens.CASE.name();
        }
        if (node instanceof Select) {
            return ExprNodeTokens.SELECT.name();
        }
        if (node instanceof Struct) {
            return ExprNodeTokens.STRUCT.name();
        }
        if (node instanceof CreateTable || node instanceof CreateIndex) {
            return ExprNodeTokens.CREATE.name();
        }
        if (node instanceof DropTable || node instanceof DropIndex) {
            return ExprNodeTokens.DROP.name();
        }
        if (node instanceof Seq) {
            return ((Seq)node).getType().getTypeName().toUpperCase();
        }
        if (node instanceof Parameter) {
            return ExprNodeTokens.PARAMETER.name();
        }
        return ExprNodeTokens.UNKNOWN.name();
    }

    private ExprNodeTranslators() {
    }

    public static enum ExprNodeTokens {
        MISSING,
        CASE,
        SELECT,
        CREATE,
        DROP,
        PATH,
        STRUCT,
        PARAMETER,
        UNKNOWN;

    }
}

