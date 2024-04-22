/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.Projection
 *  com.amazonaws.services.dynamodbv2.model.ProjectionType
 *  com.amazonaws.services.dynamodbv2.model.Select
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.google.Sets;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.ReadDataPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.Select;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

abstract class PaginatingFunction<I, O>
extends ReadDataPlaneFunction<I, O> {
    protected static final boolean SEQUENTIAL_READS = false;
    protected static final Set<Select> LSI_SELECTS_TO_READ_FROM_BASE_TABLE = Sets.newHashSet(Select.ALL_ATTRIBUTES, Select.SPECIFIC_ATTRIBUTES);
    private final DocumentFactory documentFactory;

    PaginatingFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory) {
        super(dbAccess, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, TransactionsEnabledMode.TRANSACTIONS_DISABLED);
        this.documentFactory = documentFactory;
    }

    protected List<String> determineAttributesToGetWhenSelectingAllProjectedAttributes(TableInfo tableInfo, String indexName, String projectionType, List<String> nonKeyAttributes) {
        switch (ProjectionType.fromValue((String)projectionType)) {
            case INCLUDE: {
                List attributesToGet = this.getAttributeNames(this.getKeyAttributes(tableInfo, indexName));
                attributesToGet.addAll(nonKeyAttributes);
                return attributesToGet;
            }
            case KEYS_ONLY: {
                return this.getAttributeNames(this.getKeyAttributes(tableInfo, indexName));
            }
            case ALL: {
                return null;
            }
        }
        LocalDBUtils.ldClientFail(LocalDBClientExceptionType.UNREACHABLE_CODE);
        return null;
    }

    Select validateSelect(String selectStr, List<String> attrsToGet, ProjectionExpression projectionExpression, String indexName, TableInfo tableInfo) {
        Select newSelect = null;
        if (StringUtils.isEmpty(selectStr)) {
            newSelect = attrsToGet != null || projectionExpression != null ? Select.SPECIFIC_ATTRIBUTES : (indexName != null ? Select.ALL_PROJECTED_ATTRIBUTES : Select.ALL_ATTRIBUTES);
        } else {
            try {
                newSelect = Select.fromValue((String)selectStr);
            } catch (IllegalArgumentException ie) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_SELECT.getMessage());
            }
            String addErrorInfo = null;
            if (attrsToGet != null || projectionExpression != null) {
                addErrorInfo = attrsToGet != null ? "AttributesToGet" : "ProjectionExpression";
            }
            switch (newSelect) {
                case ALL_ATTRIBUTES: {
                    Projection projection;
                    if (attrsToGet != null || projectionExpression != null) {
                        String errorMessage = "Cannot specify the " + addErrorInfo + " when choosing to get " + Select.ALL_ATTRIBUTES;
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, errorMessage);
                    }
                    if (indexName == null || !tableInfo.isGSIIndex(indexName) || (projection = tableInfo.getProjection(indexName)) != null && ProjectionType.ALL.name().equals(projection.getProjectionType())) break;
                    String errorMessage = "Select type " + Select.ALL_ATTRIBUTES + " is not supported for global secondary index " + indexName + " because its projection type is not " + ProjectionType.ALL;
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), errorMessage));
                }
                case ALL_PROJECTED_ATTRIBUTES: {
                    if (attrsToGet != null || projectionExpression != null) {
                        String errorMessage = "Cannot specify the " + addErrorInfo + " when choosing to get " + Select.ALL_PROJECTED_ATTRIBUTES;
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, errorMessage);
                    }
                    if (!StringUtils.isEmpty(indexName)) break;
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, Select.ALL_PROJECTED_ATTRIBUTES + " can be used only when Querying using an IndexName");
                }
                case COUNT: {
                    if (attrsToGet != null) {
                        this.awsExceptionFactory.OBTAINING_COUNT_AND_ATTRIBUTES.throwAsException();
                        break;
                    }
                    if (projectionExpression == null) break;
                    this.awsExceptionFactory.OBTAINING_COUNT_AND_PROJECTIONEXPRESSION.throwAsException();
                    break;
                }
                case SPECIFIC_ATTRIBUTES: {
                    if (attrsToGet == null && projectionExpression == null) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Must specify the AttributesToGet or ProjectionExpression when choosing to get " + Select.SPECIFIC_ATTRIBUTES);
                    }
                    this.validateAttributesToGetAndProjExpr((List)attrsToGet, projectionExpression, indexName, tableInfo);
                    break;
                }
                default: {
                    this.awsExceptionFactory.INTERNAL_FAILURE.throwAsException();
                }
            }
        }
        return newSelect;
    }
}

