/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.helpers;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionHelper;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateExpression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ExpressionUtils {
    public static Set<String> getConditionExpressionTopLevelAttributes(Expression expression, DbEnv dbEnv) {
        if (expression == null) {
            return new HashSet<String>(0);
        }
        return ExpressionUtils.docPathElementsToAttrNames(ExpressionHelper.getConditionExpressionTopLevelDocPathElements(expression, dbEnv));
    }

    public static Set<String> getProjectionExpressionTopLevelAttributes(ProjectionExpression projectionExpression, DbEnv dbEnv) {
        if (projectionExpression == null) {
            return new HashSet<String>(0);
        }
        return ExpressionUtils.docPathElementsToAttrNames(ExpressionHelper.getProjectionExpressionTopLevelDocPathElements(projectionExpression, dbEnv));
    }

    public static Set<String> getUpdateExpressionTopLevelAttributes(UpdateExpression updateExpression, DbEnv dbEnv) {
        if (updateExpression == null) {
            return new HashSet<String>(0);
        }
        return ExpressionUtils.docPathElementsToAttrNames(ExpressionHelper.getUpdateExpressionTopLevelDocPathElements(updateExpression, dbEnv));
    }

    public static Set<String> docPathElementsToAttrNames(Collection<DocPathElement> docPathElements) {
        HashSet<String> attrNames = new HashSet<String>();
        if (docPathElements != null) {
            for (DocPathElement docPathElement : docPathElements) {
                if (!(docPathElement instanceof DocPathMapElement)) continue;
                attrNames.add(docPathElement.getFieldName());
            }
        }
        return attrNames;
    }
}

