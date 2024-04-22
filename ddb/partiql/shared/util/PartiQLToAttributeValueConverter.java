/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.util;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import org.partiql.lang.ast.ExprNode;

public interface PartiQLToAttributeValueConverter<E, R, V extends DocumentNode> {
    public R exprNodeToInternalAttributeNames(ExprNode var1, String var2);

    public V exprNodeToInternalAttributes(ExprNode var1, int var2, E var3);
}

