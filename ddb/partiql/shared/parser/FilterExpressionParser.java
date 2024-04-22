/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.parser;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.parser.ExpressionParser;
import ddb.partiql.shared.util.EmptyAttributeValueBehavior;
import ddb.partiql.shared.util.EmptyAttributeValueValidator;
import ddb.partiql.shared.util.PartiQLToAttributeValueConverter;
import java.util.Collections;
import java.util.List;
import org.partiql.lang.ast.ExprNode;

public class FilterExpressionParser<E, R, V extends DocumentNode>
extends ExpressionParser<E, R, V> {
    public FilterExpressionParser(PartiQLToAttributeValueConverter<E, R, V> converter, PartiQLDbEnv dbEnv, ExpressionValidator validator, EmptyAttributeValueValidator<V> emptyAttributeValueValidator, DocumentFactory documentFactory) {
        super(converter, dbEnv, validator, emptyAttributeValueValidator, documentFactory);
    }

    public ExpressionWrapper getFilterExpression(ExprNode node, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        if (node == null) {
            return null;
        }
        List<ExprTreeNode> exprTreeNodes = this.convertExprNodeToExprTreeNode(Collections.singletonList(node), parameters, ExpressionParser.ExpressionType.CONDITION, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
        if (!(exprTreeNodes.get(0) instanceof ExprTreeOpNode) || ((ExprTreeOpNode)exprTreeNodes.get(0)).getOperator() == Operator.size) {
            throw this.dbEnv.createValidationError("WHERE clause must contain an expression that resolves to a boolean");
        }
        return new ExpressionWrapper(exprTreeNodes.get(0), this.validator);
    }

    public ExpressionWrapper getNegatedFilterExpression(ExprNode node, List<V> parameters, int maxItemSize, boolean areIonNumericTypesAllowed, EmptyAttributeValueBehavior emptyAttributeValueBehavior, E opContext) {
        if (node == null) {
            return null;
        }
        List<ExprTreeNode> exprTreeNodes = this.convertExprNodeToExprTreeNode(Collections.singletonList(node), parameters, ExpressionParser.ExpressionType.CONDITION, maxItemSize, areIonNumericTypesAllowed, emptyAttributeValueBehavior, opContext);
        return new ExpressionWrapper(new ExprTreeOpNode(exprTreeNodes, Operator.NOT), this.validator);
    }
}

