/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.util;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import ddb.partiql.shared.dbenv.DataAccessModelFactory;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import java.util.ArrayList;
import java.util.List;

public final class ExprTreeNodeUtils {
    private final PartiQLDbEnv dbEnv;
    private final DataAccessModelFactory dataAccessModelFactory;

    public ExprTreeNodeUtils(PartiQLDbEnv dbEnv, DataAccessModelFactory dataAccessModelFactory) {
        this.dbEnv = dbEnv;
        this.dataAccessModelFactory = dataAccessModelFactory;
    }

    public static ExprTreePathNode getPathNodeChild(ExprTreeNode node) {
        if (node != null && node.getChildren() != null) {
            for (ExprTreeNode childNode : node.getChildren()) {
                if (!(childNode instanceof ExprTreePathNode)) continue;
                return (ExprTreePathNode)childNode;
            }
        }
        return null;
    }

    public static List<ExprTreeValueNode> getValueNodeChildren(ExprTreeNode node) {
        ArrayList<ExprTreeValueNode> valueNodes = new ArrayList<ExprTreeValueNode>();
        if (node != null && node.getChildren() != null) {
            for (ExprTreeNode childNode : node.getChildren()) {
                if (!(childNode instanceof ExprTreeValueNode)) continue;
                valueNodes.add((ExprTreeValueNode)childNode);
            }
        }
        return valueNodes;
    }

    public <N> N getKeyAttributeName(ExprTreePathNode pathNode, List<N> keyAttributeNames) {
        if (pathNode == null) {
            return null;
        }
        DocPath docPath = pathNode.getDocPath();
        DocPathElement docPathElement = docPath.getElements().get(0);
        Object attributeName = this.dataAccessModelFactory.makeAttributeName(docPathElement.getFieldName());
        if (keyAttributeNames.contains(attributeName)) {
            if (docPath.getElements().size() > 1) {
                throw this.dbEnv.createValidationError("Key attributes must be scalars; list random access '[]' and map lookup '.' are not allowed: Key %s", String::format, attributeName);
            }
            return attributeName;
        }
        return null;
    }
}

