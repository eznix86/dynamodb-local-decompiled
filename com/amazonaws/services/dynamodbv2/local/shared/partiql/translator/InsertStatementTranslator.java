/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.translator;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.PartiQLToAttributeValueConverter;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.StatementTranslator;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.model.PartiQLToAttributeValueConverterBase;
import ddb.partiql.shared.util.ExprNodeTranslators;
import ddb.partiql.shared.util.OperationName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.InsertValueOp;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.Parameter;
import org.partiql.lang.ast.Seq;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.StructField;

public class InsertStatementTranslator
extends StatementTranslator<DataManipulation> {
    private static final OperationName OPERATION_NAME = OperationName.INSERT;

    public InsertStatementTranslator(LocalDBAccess dbAccess, PartiQLToAttributeValueConverter converter, LocalPartiQLDbEnv localPartiQLDbEnv, ExpressionValidator validator, DocumentFactory documentFactory) {
        super(dbAccess, converter, localPartiQLDbEnv, validator, documentFactory);
    }

    @Override
    public TranslatedPartiQLOperation translate(ParsedPartiQLRequest<DataManipulation> request) {
        DataManipulation exprNode = request.getExprNode();
        List<DataManipulationOperation> dmlOps = exprNode.getDmlOperations().getOps();
        this.localPartiQLDbEnv.dbPqlAssert(dmlOps.size() == 1, "translate", "There should be exactly one operation");
        DataManipulationOperation dmlOp = dmlOps.get(0);
        this.localPartiQLDbEnv.dbPqlAssert(dmlOp instanceof InsertValueOp, "translate", "only insert should be used", "name", dmlOp.getName());
        InsertValueOp insertValueOp = (InsertValueOp)dmlOp;
        if (exprNode.getFrom() != null || exprNode.getWhere() != null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported FROM ... WHERE ... clause in INSERT statement.", exprNode).build(new Object[0]));
        }
        if (insertValueOp.getPosition() != null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("AT is not supported in insert statements.", exprNode).build(new Object[0]));
        }
        if (insertValueOp.getOnConflict() != null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported clause: %s").build("ON CONFLICT"));
        }
        Map<String, AttributeValue> item = this.getItem(insertValueOp.getValue(), request.getParameters());
        String tableName = this.tableNameExtractor.getTableName(insertValueOp.getLvalue());
        this.validateKeyAttributesInItem(item, this.getTableInfo(tableName));
        ExprTreeNode exprTreeNode = this.addUnaryHashKeyCondition(this.getTableInfo(tableName), null, Operator.attribute_not_exists);
        ExpressionWrapper conditionExpressionWrapper = new ExpressionWrapper(exprTreeNode, this.validator);
        ReturnValue returnValue = this.getReturnValue(exprNode.getReturning());
        this.validateReturnValueForDMLOperation(returnValue, OPERATION_NAME);
        return TranslatedPartiQLOperation.builder().operationName(OPERATION_NAME).tableName(tableName).item(item).conditionExpressionWrapper(conditionExpressionWrapper).returnValue(returnValue).returnValuesOnConditionCheckFailure(this.getReturnValuesOnConditionCheckFailure(request.getReturnValuesOnConditionCheckFailure())).build();
    }

    @Override
    public OperationName getOperationName() {
        return OPERATION_NAME;
    }

    private Map<String, AttributeValue> getItem(ExprNode itemAsExprNode, List<AttributeValue> parameters) {
        if (!(itemAsExprNode instanceof Struct)) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported format in item expression. Please use struct format {...} to express item values for insert statement.", itemAsExprNode).build(new Object[0]));
        }
        Struct itemAsStruct = (Struct)itemAsExprNode;
        HashMap<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        for (StructField structField : itemAsStruct.getFields()) {
            String attributeName = this.getAttributeName(structField.getName());
            AttributeValue attributeValue = this.getAttributeValue(structField.getExpr(), parameters);
            if (item.containsKey(attributeName)) {
                throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Duplicate keys in tuple. Key: %s under %s", structField.getName()).build(attributeName, PartiQLToAttributeValueConverterBase.initDescriptivePath()));
            }
            item.put(attributeName, attributeValue);
        }
        return item;
    }

    private AttributeValue getAttributeValue(ExprNode attributeValueAsExprNode, List<AttributeValue> parameters) {
        if (attributeValueAsExprNode instanceof Literal || attributeValueAsExprNode instanceof Seq || attributeValueAsExprNode instanceof Struct) {
            return (AttributeValue)this.converter.exprNodeToInternalAttributes(attributeValueAsExprNode, 409600, null);
        }
        if (attributeValueAsExprNode instanceof Parameter) {
            int position = ((Parameter)attributeValueAsExprNode).getPosition();
            if (parameters == null || position > parameters.size()) {
                throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Number of parameters in request and statement don't match.").build(new Object[0]));
            }
            return parameters.get(position - 1);
        }
        throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported data type: %s under key %s", attributeValueAsExprNode).build(ExprNodeTranslators.extractExprIdentifierAsString(attributeValueAsExprNode), PartiQLToAttributeValueConverterBase.initDescriptivePath()));
    }

    private String getAttributeName(ExprNode attributeNameAsExprNode) {
        return (String)this.converter.exprNodeToInternalAttributeNames(attributeNameAsExprNode, PartiQLToAttributeValueConverterBase.initDescriptivePath());
    }

    private void validateKeyAttributesInItem(Map<String, AttributeValue> item, TableInfo tableInfo) {
        ArrayList<AttributeDefinition> keyAttributes = new ArrayList<AttributeDefinition>();
        keyAttributes.add(tableInfo.getHashKey());
        if (tableInfo.hasRangeKey()) {
            keyAttributes.add(tableInfo.getRangeKey());
        }
        for (AttributeDefinition keyAttribute : keyAttributes) {
            String keyAttributeName = keyAttribute.getAttributeName();
            if (!item.containsKey(keyAttributeName)) {
                throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Key attribute should be present in the item: Key %s").build(keyAttributeName));
            }
            if (item.get(keyAttributeName).getType().toString().equals(keyAttribute.getAttributeType())) continue;
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Key attribute's data type should match its data type in table's schema: Key %s").build(keyAttributeName));
        }
    }
}

