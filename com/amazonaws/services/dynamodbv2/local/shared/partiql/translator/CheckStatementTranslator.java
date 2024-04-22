/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.translator;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.PartiQLToAttributeValueConverter;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.StatementTranslator;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.model.ExtractedKeyAndConditionExprTree;
import ddb.partiql.shared.util.KeyAndConditionExpressionExtractorBase;
import ddb.partiql.shared.util.OperationName;
import java.util.HashMap;
import java.util.List;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.Select;

public class CheckStatementTranslator
extends StatementTranslator<NAry> {
    private static final OperationName OPERATION_NAME = OperationName.CHECK_ITEM;

    public CheckStatementTranslator(LocalDBAccess dbAccess, PartiQLToAttributeValueConverter converter, LocalPartiQLDbEnv localPartiQLDbEnv, ExpressionValidator validator, DocumentFactory documentFactory) {
        super(dbAccess, converter, localPartiQLDbEnv, validator, documentFactory);
    }

    @Override
    public TranslatedPartiQLOperation translate(ParsedPartiQLRequest<NAry> request) {
        Select select = (Select)request.getExprNode().getArgs().get(1);
        this.validateSupportedConditionCheckSyntax(select);
        List<String> fromSourceComponents = this.tableNameExtractor.getFromSourceComponents(select.getFrom(), this.opContext);
        if (fromSourceComponents.size() != 1) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Operations on indices are not supported in transactions").build(new Object[0]));
        }
        ExpressionWrapper filterExpressionWrapper = this.filterParser.getFilterExpression(select.getWhere(), request.getParameters(), 409600, request.getAreIonNumericTypesAllowed(), null, this.opContext);
        if (filterExpressionWrapper == null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("EXISTS() must contain a single item read with additional condition").build(new Object[0]));
        }
        String tableName = fromSourceComponents.get(0);
        ExtractedKeyAndConditionExprTree extractedKeyAndConditionExprTree = this.keyAndConditionExpressionExtractor.extractKeyFromExprTreeNode(filterExpressionWrapper.getExpression().getExprTree(), this.getTableInfo(tableName), KeyAndConditionExpressionExtractorBase.ExpressionType.CONDITION_CHECK);
        HashMap<String, AttributeValue> itemKey = new HashMap<String, AttributeValue>();
        extractedKeyAndConditionExprTree.getExtractedKeyConditions().forEach((k, v) -> itemKey.put((String)k, v.getAttributeValueList().get(0)));
        if (extractedKeyAndConditionExprTree.getConditionExpressionTreeNode() == null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("EXISTS() must contain a single item read with additional condition").build(new Object[0]));
        }
        this.validateKeyForSchemaMismatch(itemKey, this.getTableInfo(tableName));
        ExpressionWrapper conditionExpressionWrapper = new ExpressionWrapper(extractedKeyAndConditionExprTree.getConditionExpressionTreeNode(), this.validator);
        return TranslatedPartiQLOperation.builder().operationName(OPERATION_NAME).tableName(tableName).item(itemKey).conditionExpressionWrapper(conditionExpressionWrapper).build();
    }

    @Override
    public OperationName getOperationName() {
        return OPERATION_NAME;
    }

    private void validateSupportedConditionCheckSyntax(Select select) {
        if (select.getGroupBy() != null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported clause: %s").build("GROUP BY"));
        }
        if (select.getHaving() != null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported clause: %s", select.getHaving()).build("HAVING"));
        }
        if (select.getLimit() != null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported clause: %s", select.getLimit()).build("LIMIT"));
        }
    }
}

