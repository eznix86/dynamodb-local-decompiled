/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.translator;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
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
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.model.ExtractedKeyAndConditionExprTree;
import ddb.partiql.shared.parser.ProjectionExpressionParser;
import ddb.partiql.shared.util.ExprTreeNodeUtils;
import ddb.partiql.shared.util.KeyAndConditionExpressionExtractorBase;
import ddb.partiql.shared.util.OperationName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.partiql.lang.ast.Select;
import org.partiql.lang.ast.SetQuantifier;

public class SelectStatementTranslator
extends StatementTranslator<Select> {
    private static final OperationName OPERATION_NAME = OperationName.SELECT;
    private final ProjectionExpressionParser<DynamoDBLocalSharedOpContext, String, AttributeValue> projectionExpressionParser;

    public SelectStatementTranslator(LocalDBAccess dbAccess, PartiQLToAttributeValueConverter converter, LocalPartiQLDbEnv localPartiQLDbEnv, ExpressionValidator validator, DocumentFactory documentFactory) {
        super(dbAccess, converter, localPartiQLDbEnv, validator, documentFactory);
        this.projectionExpressionParser = new ProjectionExpressionParser<DynamoDBLocalSharedOpContext, String, AttributeValue>(converter, localPartiQLDbEnv, validator, documentFactory);
    }

    @Override
    public OperationName getOperationName() {
        return OPERATION_NAME;
    }

    @Override
    public TranslatedPartiQLOperation translate(ParsedPartiQLRequest<Select> request) {
        return this.translate(request, SelectUseCase.SINGLETON_OPERATION);
    }

    public TranslatedPartiQLOperation translate(ParsedPartiQLRequest<Select> request, SelectUseCase selectUseCase) {
        Select select = request.getExprNode();
        this.validateSupportedSelectSyntax(select);
        List<String> fromSourceComponents = this.tableNameExtractor.getFromSourceComponents(select.getFrom(), this.opContext);
        ProjectionExpressionWrapper projectionExpressionWrapper = this.projectionExpressionParser.getProjectionExpression(select.getProjection(), this.opContext);
        String tableName = fromSourceComponents.get(0);
        ExpressionWrapper filterExpressionWrapper = this.filterParser.getFilterExpression(select.getWhere(), request.getParameters(), 409600, request.getAreIonNumericTypesAllowed(), null, this.opContext);
        String indexName = null;
        boolean isTransaction = selectUseCase.equals((Object)SelectUseCase.TRANSACTION);
        if (fromSourceComponents.size() == 2) {
            if (isTransaction) {
                throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Reads on indices are not supported within transactions.").build(new Object[0]));
            }
            indexName = fromSourceComponents.get(1);
        }
        TableInfo tableInfo = this.getTableInfo(tableName);
        if (select.getOrderBy() != null) {
            List<String> keyAttributeNames;
            if (filterExpressionWrapper == null) {
                throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Must have WHERE clause in the statement when using ORDER BY clause.").build(new Object[0]));
            }
            List<String> list = keyAttributeNames = indexName != null ? tableInfo.getIndexKeyNames(indexName) : tableInfo.getBaseTableKeyNames();
            if (!this.nonOptionalHashExists(filterExpressionWrapper.getExpression().getExprTree(), keyAttributeNames)) {
                throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Must have at least one non-optional hash key condition in WHERE clause when using ORDER BY clause.").build(new Object[0]));
            }
        }
        Map<String, AttributeValue> key = null;
        boolean isBatch = selectUseCase.equals((Object)SelectUseCase.BATCH);
        ExprTreeNode nonKeyConditions = null;
        Map<String, Condition> keyConditions = null;
        if (isTransaction || isBatch) {
            try {
                ExtractedKeyAndConditionExprTree<String, Condition> extractedKeyAndConditionExprTree = this.extractKey(tableName, filterExpressionWrapper);
                key = this.getKeyForDMLStatement(extractedKeyAndConditionExprTree.getExtractedKeyConditions());
                nonKeyConditions = extractedKeyAndConditionExprTree.getConditionExpressionTreeNode();
            } catch (Exception exception) {
                // empty catch block
            }
            if (key == null || nonKeyConditions != null) {
                if (isTransaction) {
                    throw this.localPartiQLDbEnv.createValidationError("Select statements within ExecuteTransaction must specify the primary key in the where clause.");
                }
                throw this.localPartiQLDbEnv.createValidationError("Only single item select is supported");
            }
            this.validateKeyForSchemaMismatch(key, tableInfo);
        } else if (filterExpressionWrapper != null) {
            try {
                ExtractedKeyAndConditionExprTree<String, Condition> extractedKeyAndConditionExprTree = this.attemptToExtractKeyConditions(filterExpressionWrapper.getExpression().getExprTree(), this.getTableInfo(tableName), indexName);
                if (extractedKeyAndConditionExprTree != null) {
                    keyConditions = extractedKeyAndConditionExprTree.getExtractedKeyConditions();
                    nonKeyConditions = extractedKeyAndConditionExprTree.getConditionExpressionTreeNode();
                    filterExpressionWrapper = nonKeyConditions == null ? null : new ExpressionWrapper(nonKeyConditions, this.validator);
                }
            } catch (Exception exception) {
                // empty catch block
            }
            if (keyConditions != null) {
                this.validateKeyConditionsForSchemaMismatch(keyConditions, tableInfo);
            }
        }
        return TranslatedPartiQLOperation.builder().operationName(this.getOperationName()).tableName(tableName).indexName(indexName).item(key).keyConditions(keyConditions).projectionExpressionWrapper(projectionExpressionWrapper).conditionExpressionWrapper(filterExpressionWrapper).orderBy(select.getOrderBy()).limit(request.getLimit()).isConsistentRead(request.getIsConsistentRead()).build();
    }

    private void validateSupportedSelectSyntax(Select select) {
        if (select.getGroupBy() != null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported clause: %s").build("GROUP BY"));
        }
        if (select.getHaving() != null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported clause: %s", select.getHaving()).build("HAVING"));
        }
        if (select.getLimit() != null) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported clause: %s", select.getLimit()).build("LIMIT"));
        }
        if (SetQuantifier.DISTINCT.equals((Object)select.getSetQuantifier())) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Unsupported token in expression: %s").build(new Object[]{SetQuantifier.DISTINCT}));
        }
    }

    public boolean nonOptionalHashExists(ExprTreeNode node, List<String> keyAttributeNames) {
        if (KeyAndConditionExpressionExtractorBase.isTerminalOpNode(node)) {
            return this.checkKeyCondition(node, keyAttributeNames);
        }
        Operator dynamoOperator = ((ExprTreeOpNode)node).getOperator();
        boolean leftChildExists = this.nonOptionalHashExists(node.getChildren().get(0), keyAttributeNames);
        boolean rightChildExists = this.nonOptionalHashExists(node.getChildren().get(1), keyAttributeNames);
        if (dynamoOperator == Operator.OR) {
            return leftChildExists && rightChildExists;
        }
        return leftChildExists || rightChildExists;
    }

    private boolean checkKeyCondition(ExprTreeNode node, List<String> keyAttributeNames) {
        if (!(node instanceof ExprTreeOpNode)) {
            return false;
        }
        ExprTreePathNode pathNode = ExprTreeNodeUtils.getPathNodeChild(node);
        List<ExprTreeValueNode> valueNodes = ExprTreeNodeUtils.getValueNodeChildren(node);
        if (pathNode == null) {
            return false;
        }
        String attributeName = pathNode.getDocPath().getElements().get(0).getFieldName();
        if (attributeName == null) {
            return false;
        }
        if (attributeName.equals(keyAttributeNames.get(0))) {
            return !valueNodes.isEmpty();
        }
        return false;
    }

    private ExtractedKeyAndConditionExprTree<String, Condition> attemptToExtractKeyConditions(ExprTreeNode node, TableInfo tableInfo, String indexName) {
        List<String> keyAttributeNames;
        HashMap extractedKeyAndConditions = new HashMap();
        String hashKeyName = tableInfo.getHashKey().getAttributeName();
        if (indexName != null) {
            keyAttributeNames = tableInfo.getIndexKeyNames(indexName);
            if (tableInfo.isGSIIndex(indexName)) {
                hashKeyName = tableInfo.getGSIHashKey(indexName).getAttributeName();
            }
        } else {
            keyAttributeNames = tableInfo.getBaseTableKeyNames();
        }
        ExprTreeNode conditionExpressionTree = this.keyAndConditionExpressionExtractor.extractKeyFromExprTreeNode(node, keyAttributeNames, extractedKeyAndConditions, KeyAndConditionExpressionExtractorBase.ExpressionType.SELECT);
        if (extractedKeyAndConditions.containsKey(hashKeyName)) {
            return new ExtractedKeyAndConditionExprTree<String, Condition>(extractedKeyAndConditions, conditionExpressionTree);
        }
        return null;
    }

    public static enum SelectUseCase {
        SINGLETON_OPERATION,
        BATCH,
        TRANSACTION;

    }
}

