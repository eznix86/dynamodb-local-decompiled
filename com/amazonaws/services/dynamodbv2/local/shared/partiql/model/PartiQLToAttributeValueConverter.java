/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.model;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.DynamoDBLocalSharedOpContext;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.exceptions.PartiQLBigBirdDataTypeException;
import ddb.partiql.shared.model.PartiQLToAttributeValueConverterBase;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.Seq;
import org.partiql.lang.ast.SeqType;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.StructField;

public class PartiQLToAttributeValueConverter
extends PartiQLToAttributeValueConverterBase<DynamoDBLocalSharedOpContext, String, AttributeValue> {
    public PartiQLToAttributeValueConverter(PartiQLDbEnv dbEnv, DocumentFactory documentFactory) {
        super(dbEnv, documentFactory);
    }

    @Override
    protected boolean isBigBirdDataTypeException(RuntimeException rte) {
        return rte instanceof PartiQLBigBirdDataTypeException;
    }

    @Override
    protected String buildBigBirdDataTypeErrorMessage(RuntimeException rte) {
        PartiQLBigBirdDataTypeException partiQLBigBirdDataTypeException = (PartiQLBigBirdDataTypeException)rte;
        return partiQLBigBirdDataTypeException.getErrorCode().toString() + ":" + partiQLBigBirdDataTypeException.getMessage();
    }

    @Override
    protected void validateNestedLevel(int depth) {
        LocalDBValidatorUtils.validateNestedLevel(depth);
    }

    @Override
    protected int validateAndReturnItemSize(AttributeValue attributeValue, int runningItemSizeCount, int maxItemSize, DynamoDBLocalSharedOpContext opContext) {
        int attributeValueSize = (int)LocalDBUtils.getAttributeValueSizeBytes(attributeValue);
        if (attributeValueSize + runningItemSizeCount > maxItemSize) {
            new AWSExceptionFactory().ITEM_TOO_LARGE.throwAsException();
        }
        return attributeValueSize;
    }

    @Override
    protected String makeAttributeName(String name) {
        return name;
    }

    @Override
    protected DocumentNode structToInternalAttributes(Struct struct, int maxItemSize, int depth, String descriptivePathPrefix, DynamoDBLocalSharedOpContext opContext) {
        HashMap<String, AttributeValue> map2 = new HashMap<String, AttributeValue>();
        int runningItemSizeCount = 0;
        for (StructField structField : struct.getFields()) {
            AttributeValue attributeValue;
            ExprNode structNameNode = structField.component1();
            String attributeName = (String)this.exprNodeToInternalAttributeNames(structNameNode, descriptivePathPrefix);
            AttributeValue prevAttributeValue = map2.put(attributeName, attributeValue = (AttributeValue)this.safeExprNodeToInternalAttributes(structField.component2(), maxItemSize, depth + 1, PartiQLToAttributeValueConverter.addToDescriptivePath(descriptivePathPrefix, attributeName), opContext));
            if (prevAttributeValue != null) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, new ExceptionMessageBuilder("Duplicate keys in tuple. Key: %s under %s", struct).build(attributeName, descriptivePathPrefix));
            }
            runningItemSizeCount += attributeName.length();
            runningItemSizeCount += this.validateAndReturnItemSize(attributeValue, runningItemSizeCount, maxItemSize, opContext);
        }
        return new AttributeValue().withM(map2);
    }

    @Override
    protected DocumentNode listToInternalAttributes(Seq seq2, int maxItemSize, int depth, String descriptivePathPrefix, DynamoDBLocalSharedOpContext opContext) {
        this.dbEnv.dbPqlAssert(seq2.getType().equals((Object)SeqType.LIST), "listToInternalAttributes", "only LIST seq type can be converted to a DynamoDB list", "seqType", (Object)seq2.getType());
        ArrayList<AttributeValue> list = new ArrayList<AttributeValue>();
        int runningItemSizeCount = 0;
        int listInd = 0;
        for (ExprNode node : seq2.getValues()) {
            AttributeValue attributeValue = (AttributeValue)this.safeExprNodeToInternalAttributes(node, maxItemSize, depth + 1, PartiQLToAttributeValueConverter.addToDescriptivePath(descriptivePathPrefix, Integer.toString(listInd)), opContext);
            list.add(attributeValue);
            runningItemSizeCount += this.validateAndReturnItemSize(attributeValue, runningItemSizeCount, maxItemSize, opContext);
            ++listInd;
        }
        return new AttributeValue().withL(list);
    }

    @Override
    protected DocumentNode stringListToInternalAttributes(List<String> stringList) {
        return this.documentFactory.makeStringSet(stringList);
    }

    @Override
    protected DocumentNode numberListToInternalAttributes(List<BigDecimal> numberList) {
        return this.documentFactory.makeNumberSet(numberList);
    }
}

