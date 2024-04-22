/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.util;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import ddb.partiql.shared.util.EmptyAttributeValueBehavior;

public interface EmptyAttributeValueValidator<V extends DocumentNode> {
    public void validateKeyAttributeValue(V var1);

    public void validateAttributeValue(V var1, EmptyAttributeValueBehavior var2);
}

