/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.KeyType
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.DataTypes;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessExceptionType;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SQLiteIndexElement {
    private AttributeDefinition dynamoDBAttribute = null;
    private KeyType keyType = null;
    private String sqliteColumnName = null;
    private DataTypes sqliteDataType = null;

    public SQLiteIndexElement() {
    }

    public SQLiteIndexElement(KeySchemaElement keySchemaElement, List<AttributeDefinition> attributes, String sqliteColumnName) {
        this(keySchemaElement.getAttributeName(), keySchemaElement.getKeyType(), attributes, sqliteColumnName);
    }

    private SQLiteIndexElement(String keyAttributeName, String keyType, List<AttributeDefinition> attributes, String sqliteColumnName) {
        AttributeDefinition retrievedAttributeDefinition = null;
        for (AttributeDefinition attributeDefinition : attributes) {
            if (!attributeDefinition.getAttributeName().equals(keyAttributeName)) continue;
            retrievedAttributeDefinition = attributeDefinition;
            break;
        }
        LocalDBUtils.ldAccessAssertTrue(retrievedAttributeDefinition == null, LocalDBAccessExceptionType.VALIDATION_EXCEPTION, "Given attribute name (%s) not found in list of attribute definitions.", keyAttributeName);
        this.initialize(KeyType.fromValue((String)keyType), retrievedAttributeDefinition, sqliteColumnName);
    }

    public SQLiteIndexElement(KeyType keyAttributeType, AttributeDefinition attributeDefinition, String sqliteColumnName) {
        this.initialize(keyAttributeType, attributeDefinition, sqliteColumnName);
    }

    public static DataTypes getSQLiteDataType(AttributeDefinition attributeDefinition) {
        if (attributeDefinition.getAttributeType().equals("S")) {
            return DataTypes.TEXT;
        }
        if (attributeDefinition.getAttributeType().equals("B")) {
            return DataTypes.BLOB;
        }
        if (attributeDefinition.getAttributeType().equals("N")) {
            return DataTypes.BLOB;
        }
        throw new IllegalArgumentException("Unknown Attribute Type: " + attributeDefinition.getAttributeType());
    }

    @JsonProperty(value="DynamoDBAttribute")
    public AttributeDefinition getDynamoDBAttribute() {
        return this.dynamoDBAttribute;
    }

    @JsonProperty(value="DynamoDBAttribute")
    public void setDynamoDBAttribute(AttributeDefinition dynamoDBAttribute) {
        this.dynamoDBAttribute = dynamoDBAttribute;
    }

    @JsonProperty(value="KeyType")
    public KeyType getKeyType() {
        return this.keyType;
    }

    @JsonProperty(value="KeyType")
    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
    }

    @JsonProperty(value="SQLiteColumnName")
    public String getSqliteColumnName() {
        return this.sqliteColumnName;
    }

    @JsonProperty(value="SQLiteColumnName")
    public void setSqliteColumnName(String sqliteColumnName) {
        this.sqliteColumnName = sqliteColumnName;
    }

    @JsonProperty(value="SQLiteDataType")
    public DataTypes getSqliteDataType() {
        return this.sqliteDataType;
    }

    @JsonProperty(value="SQLiteDataType")
    public void setSqliteDataType(DataTypes sqliteDataType) {
        this.sqliteDataType = sqliteDataType;
    }

    private void initialize(KeyType keyAttributeType, AttributeDefinition attributeDefinition, String sqliteColumnName) {
        this.dynamoDBAttribute = attributeDefinition;
        this.keyType = keyAttributeType;
        this.sqliteColumnName = sqliteColumnName;
        this.sqliteDataType = SQLiteIndexElement.getSQLiteDataType(this.dynamoDBAttribute);
    }

    public String toString() {
        return "{" + this.dynamoDBAttribute.getAttributeName() + ":" + this.dynamoDBAttribute.getAttributeType() + "}\t" + this.keyType.toString() + "\t" + this.sqliteColumnName + "\t" + this.sqliteDataType.toString();
    }
}

