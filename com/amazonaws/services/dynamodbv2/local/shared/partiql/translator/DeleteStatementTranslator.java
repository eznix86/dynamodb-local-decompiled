/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.translator;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.DynamoDBLocalSharedOpContext;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.PartiQLToAttributeValueConverter;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.StatementTranslator;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import ddb.partiql.shared.model.ExtractedKeyAndConditionExprTree;
import ddb.partiql.shared.util.OperationName;
import java.util.List;
import java.util.Map;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.DeleteOp;

public class DeleteStatementTranslator
extends StatementTranslator<DataManipulation> {
    private static final OperationName OPERATION_NAME = OperationName.DELETE;

    public DeleteStatementTranslator(LocalDBAccess dbAccess, PartiQLToAttributeValueConverter converter, LocalPartiQLDbEnv localPartiQLDbEnv, ExpressionValidator validator, DocumentFactory documentFactory) {
        super(dbAccess, converter, localPartiQLDbEnv, validator, documentFactory);
    }

    @Override
    public TranslatedPartiQLOperation translate(ParsedPartiQLRequest<DataManipulation> request) {
        DataManipulation dataManipulation = request.getExprNode();
        DataManipulation exprNode = request.getExprNode();
        List<DataManipulationOperation> dmlOps = exprNode.getDmlOperations().getOps();
        this.localPartiQLDbEnv.dbPqlAssert(dmlOps.size() == 1, "translate", "There should be exactly one operation");
        DataManipulationOperation dmlOp = dmlOps.get(0);
        this.localPartiQLDbEnv.dbPqlAssert(dmlOp instanceof DeleteOp, "translate", "Operation should be of type delete", "name", dmlOp.getName());
        ExpressionWrapper filterExpression = this.filterParser.getFilterExpression(dataManipulation.getWhere(), request.getParameters(), 0x100000, request.getAreIonNumericTypesAllowed(), null, null);
        if (filterExpression == null) {
            throw this.localPartiQLDbEnv.createValidationError("Where clause does not contain a mandatory equality on all key attributes");
        }
        String tableName = this.tableNameExtractor.getTableName(dataManipulation.getFrom(), new DynamoDBLocalSharedOpContext());
        Map.Entry<TableInfo, ExtractedKeyAndConditionExprTree<String, Condition>> tableAndExtractedKeyAndConditionExprTree = this.getTableAndExtractKey(tableName, filterExpression);
        ExtractedKeyAndConditionExprTree<String, Condition> keyAndConditionExprTree = tableAndExtractedKeyAndConditionExprTree.getValue();
        ExpressionWrapper conditionExpressionWrapper = keyAndConditionExprTree.getConditionExpressionTreeNode() == null ? null : new ExpressionWrapper(keyAndConditionExprTree.getConditionExpressionTreeNode(), this.validator);
        Map<String, AttributeValue> key = this.getKeyForDMLStatement(keyAndConditionExprTree.getExtractedKeyConditions());
        this.validateKeyForSchemaMismatch(key, tableAndExtractedKeyAndConditionExprTree.getKey());
        ReturnValue returnValue = this.getReturnValue(dataManipulation.getReturning());
        this.validateReturnValueForDMLOperation(returnValue, OPERATION_NAME);
        return TranslatedPartiQLOperation.builder().operationName(OPERATION_NAME).tableName(tableName).item(key).conditionExpressionWrapper(conditionExpressionWrapper).returnValue(returnValue).returnValuesOnConditionCheckFailure(this.getReturnValuesOnConditionCheckFailure(request.getReturnValuesOnConditionCheckFailure())).build();
    }

    @Override
    public OperationName getOperationName() {
        return OPERATION_NAME;
    }
}

