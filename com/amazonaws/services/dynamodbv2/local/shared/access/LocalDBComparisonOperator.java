/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonServiceException
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.DDBType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public enum LocalDBComparisonOperator {
    EQ{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            AttributeValue expectedVal = attributeValueList.get(0);
            DDBType type = LocalDBComparisonOperator.checkType(expectedVal, value);
            if (type == null) {
                return false;
            }
            return LocalDBComparisonOperator.compareFilterHelper(value, expectedVal, type) == 0;
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return type != null;
        }
    }
    ,
    NE{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            return !EQ.evaluate(attributeValueList, value);
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return EQ.isTypeSupported(type);
        }
    }
    ,
    IN{

        @Override
        public boolean isValidForQuery() {
            return false;
        }

        @Override
        public boolean isValidAttributeCount(int count) {
            return count >= 1;
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return LocalDBComparisonOperator.isSortableScalar(type);
        }

        @Override
        public void isValidAttributeList(List<AttributeValue> attributeValueList) {
            LocalDBComparisonOperator.checkForEmptyAttributesList(attributeValueList);
            DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(attributeValueList.get(0));
            if (type == DDBType.BS || type == DDBType.SS || type == DDBType.NS) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_COMPARISON_SETS.getMessage());
            }
            for (int i = 1; i < attributeValueList.size(); ++i) {
                DDBType curType = LocalDBUtils.getDataTypeOfAttributeValue(attributeValueList.get(i));
                if (curType == DDBType.BS || curType == DDBType.SS || curType == DDBType.NS) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_COMPARISON_SETS.getMessage());
                }
                if (curType == type) continue;
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_COMPARISON_IN_DATATYPES.getMessage());
            }
        }

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            if (value == null) {
                return false;
            }
            DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(value);
            try {
                LocalDBUtils.validateConsistentTypes(value, attributeValueList.get(0));
            } catch (AmazonServiceException ase) {
                if (ase.getMessage().equals(LocalDBClientExceptionMessage.INCONSISTENT_TYPES.getMessage())) {
                    return false;
                }
                throw ase;
            }
            for (AttributeValue attrVal : attributeValueList) {
                if (LocalDBComparisonOperator.compareFilterHelper(attrVal, value, type) != 0) continue;
                return true;
            }
            return false;
        }
    }
    ,
    LE{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            AttributeValue expectedVal = attributeValueList.get(0);
            DDBType type = LocalDBComparisonOperator.checkType(expectedVal, value);
            if (type == null) {
                return false;
            }
            int compareResult = LocalDBComparisonOperator.compareFilterHelper(value, expectedVal, type);
            return compareResult <= 0;
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return LocalDBComparisonOperator.isSortableScalar(type);
        }

        @Override
        public boolean evaluateExclusive(List<AttributeValue> attributeValueList, AttributeValue value, boolean asc) {
            if (asc) {
                DDBType type;
                AttributeValue expectedVal = attributeValueList.get(0);
                int compareResult = LocalDBComparisonOperator.compareFilterHelper(value, expectedVal, type = LocalDBUtils.validateConsistentTypes(value, expectedVal));
                return compareResult < 0;
            }
            return this.evaluate(attributeValueList, value);
        }
    }
    ,
    LT{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            AttributeValue expectedVal = attributeValueList.get(0);
            DDBType type = LocalDBComparisonOperator.checkType(expectedVal, value);
            if (type == null) {
                return false;
            }
            int compareResult = LocalDBComparisonOperator.compareFilterHelper(value, expectedVal, type);
            return compareResult < 0;
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return LocalDBComparisonOperator.isSortableScalar(type);
        }
    }
    ,
    GE{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            AttributeValue expectedVal = attributeValueList.get(0);
            DDBType type = LocalDBComparisonOperator.checkType(expectedVal, value);
            if (type == null) {
                return false;
            }
            int compareResult = LocalDBComparisonOperator.compareFilterHelper(value, expectedVal, type);
            return compareResult >= 0;
        }

        @Override
        public boolean evaluateExclusive(List<AttributeValue> attributeValueList, AttributeValue value, boolean asc) {
            DDBType type;
            if (asc) {
                return this.evaluate(attributeValueList, value);
            }
            AttributeValue expectedVal = attributeValueList.get(0);
            int compareResult = LocalDBComparisonOperator.compareFilterHelper(value, expectedVal, type = LocalDBUtils.validateConsistentTypes(value, expectedVal));
            return compareResult > 0;
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return LocalDBComparisonOperator.isSortableScalar(type);
        }
    }
    ,
    GT{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            AttributeValue expectedVal = attributeValueList.get(0);
            DDBType type = LocalDBComparisonOperator.checkType(expectedVal, value);
            if (type == null) {
                return false;
            }
            int compareResult = LocalDBComparisonOperator.compareFilterHelper(value, expectedVal, type);
            return compareResult > 0;
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return LocalDBComparisonOperator.isSortableScalar(type);
        }
    }
    ,
    BETWEEN{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            return GE.evaluate(attributeValueList, value) && LE.evaluate(attributeValueList.subList(1, 2), value);
        }

        @Override
        public boolean isValidAttributeCount(int count) {
            return count == 2;
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return LocalDBComparisonOperator.isSortableScalar(type);
        }

        @Override
        public boolean evaluateExclusive(List<AttributeValue> attributeValueList, AttributeValue value, boolean asc) {
            if (asc) {
                return GE.evaluate(attributeValueList, value) && LT.evaluate(attributeValueList.subList(1, 2), value);
            }
            return GT.evaluate(attributeValueList, value) && LE.evaluate(attributeValueList.subList(1, 2), value);
        }

        @Override
        public void isValidAttributeList(List<AttributeValue> attributeValueList) {
            if (attributeValueList == null || attributeValueList.size() == 0) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_COMPARISON_NO_DATA.getMessage());
            }
            if (attributeValueList.size() != 2) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_COMPARISON_BETWEEN.getMessage());
            }
            DDBType typeLowerPivot = LocalDBUtils.getDataTypeOfAttributeValue(attributeValueList.get(0));
            DDBType typeUpperPivot = LocalDBUtils.getDataTypeOfAttributeValue(attributeValueList.get(1));
            if (typeLowerPivot == DDBType.NS || typeLowerPivot == DDBType.SS || typeLowerPivot == DDBType.BS || typeUpperPivot == DDBType.NS || typeUpperPivot == DDBType.SS || typeUpperPivot == DDBType.BS) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_COMPARISON_SETS.getMessage());
            }
            if (typeLowerPivot != typeUpperPivot) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INCONSISTENT_TYPES.getMessage());
            }
            if (LocalDBComparisonOperator.compareFilterHelper(attributeValueList.get(0), attributeValueList.get(1), typeUpperPivot) > 0) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_BETWEEN_PIVOTS.getMessage());
            }
        }
    }
    ,
    NOT_NULL{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            return value != null;
        }

        @Override
        public boolean isValidAttributeCount(int count) {
            return count == 0;
        }

        @Override
        public boolean isValidForQuery() {
            return false;
        }

        @Override
        public void isValidAttributeList(List<AttributeValue> attributeValueList) {
            if (attributeValueList != null && !attributeValueList.isEmpty()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_NUMBER_OF_ARGUMENTS.getMessage(), new Object[]{this}));
            }
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return true;
        }
    }
    ,
    NULL{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            return value == null;
        }

        @Override
        public boolean isValidAttributeCount(int count) {
            return count == 0;
        }

        @Override
        public boolean isValidForQuery() {
            return false;
        }

        @Override
        public void isValidAttributeList(List<AttributeValue> attributeValueList) {
            if (attributeValueList != null && !attributeValueList.isEmpty()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_NUMBER_OF_ARGUMENTS.getMessage(), new Object[]{this}));
            }
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return true;
        }
    }
    ,
    CONTAINS{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            AttributeValue expectedVal = attributeValueList.get(0);
            DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(expectedVal);
            return LocalDBComparisonOperator.containsFilterHelper(value, expectedVal, type);
        }

        @Override
        public boolean isValidForQuery() {
            return false;
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return DDBType.AllScalarTypeSet.contains((Object)type);
        }
    }
    ,
    NOT_CONTAINS{

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            if (value == null) {
                return false;
            }
            return !CONTAINS.evaluate(attributeValueList, value);
        }

        @Override
        public boolean isValidForQuery() {
            return false;
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return CONTAINS.isTypeSupported(type);
        }
    }
    ,
    BEGINS_WITH{

        @Override
        public void isValidAttributeList(List<AttributeValue> attributeValueList) {
            LocalDBComparisonOperator.checkForEmptyAttributesList(attributeValueList);
            LocalDBComparisonOperator.checkForOversizedAttributeValuesList(attributeValueList, this.toString());
            AttributeValue value = attributeValueList.get(0);
            DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(value);
            if (type == DDBType.N || type.isSet()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_COMPARISON_DATATYPE_BEGINS.getMessage());
            }
        }

        @Override
        public boolean isTypeSupported(DDBType type) {
            return type == DDBType.S || type == DDBType.B;
        }

        @Override
        public boolean evaluate(List<AttributeValue> attributeValueList, AttributeValue value) {
            AttributeValue expectedVal = attributeValueList.get(0);
            DDBType type = LocalDBComparisonOperator.checkType(expectedVal, value);
            if (type == null) {
                return false;
            }
            switch (type) {
                case S: {
                    return value.getS().startsWith(expectedVal.getS());
                }
                case B: {
                    byte[] valueBuf = value.getB().array();
                    byte[] comparisonBuf = expectedVal.getB().array();
                    if (comparisonBuf.length > valueBuf.length) {
                        return false;
                    }
                    for (int i = 0; i < comparisonBuf.length; ++i) {
                        if (valueBuf[i] == comparisonBuf[i]) continue;
                        return false;
                    }
                    return true;
                }
            }
            LocalDBUtils.ldClientFail(LocalDBClientExceptionType.UNREACHABLE_CODE);
            return false;
        }
    };


    private static void checkForEmptyAttributesList(List<AttributeValue> attributeValueList) {
        if (attributeValueList == null || attributeValueList.isEmpty()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_COMPARISON_NO_DATA.getMessage());
        }
    }

    private static void checkForOversizedAttributeValuesList(List<AttributeValue> attributeValueList, String operator) {
        if (attributeValueList.size() > 1) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_NUMBER_OF_ARGUMENTS.getMessage(), operator));
        }
    }

    private static boolean isSortableScalar(DDBType type) {
        return DDBType.SortableScalarTypeSet.contains((Object)type);
    }

    public static LocalDBComparisonOperator fromValue(String value) {
        if (value == null || "".equals(value)) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (ComparisonOperator.EQ.toString().equals(value)) {
            return EQ;
        }
        if (ComparisonOperator.NE.toString().equals(value)) {
            return NE;
        }
        if (ComparisonOperator.IN.toString().equals(value)) {
            return IN;
        }
        if (ComparisonOperator.LE.toString().equals(value)) {
            return LE;
        }
        if (ComparisonOperator.LT.toString().equals(value)) {
            return LT;
        }
        if (ComparisonOperator.GE.toString().equals(value)) {
            return GE;
        }
        if (ComparisonOperator.GT.toString().equals(value)) {
            return GT;
        }
        if (ComparisonOperator.BETWEEN.toString().equals(value)) {
            return BETWEEN;
        }
        if (ComparisonOperator.NOT_NULL.toString().equals(value)) {
            return NOT_NULL;
        }
        if (ComparisonOperator.NULL.toString().equals(value)) {
            return NULL;
        }
        if (ComparisonOperator.CONTAINS.toString().equals(value)) {
            return CONTAINS;
        }
        if (ComparisonOperator.NOT_CONTAINS.toString().equals(value)) {
            return NOT_CONTAINS;
        }
        if (ComparisonOperator.BEGINS_WITH.toString().equals(value)) {
            return BEGINS_WITH;
        }
        throw new IllegalArgumentException();
    }

    public static LocalDBComparisonOperator fromValue(ComparisonOperator op) {
        return LocalDBComparisonOperator.fromValue(op.toString());
    }

    private static DDBType checkType(AttributeValue expectedValue, AttributeValue value) {
        DDBType type;
        block3: {
            type = null;
            if (value != null) {
                try {
                    type = LocalDBUtils.validateConsistentTypes(value, expectedValue);
                } catch (AmazonServiceException ase) {
                    if (ase.getMessage().equals(LocalDBClientExceptionMessage.INCONSISTENT_TYPES.getMessage())) break block3;
                    throw ase;
                }
            }
        }
        return type;
    }

    private static int compareFilterHelper(AttributeValue actualVal, AttributeValue expectedVal, DDBType type) {
        switch (type) {
            case S: 
            case B: 
            case N: {
                return actualVal.compare(expectedVal);
            }
            case NULL: {
                return actualVal.getNULL().compareTo(expectedVal.getNULL());
            }
            case BOOL: {
                return actualVal.getBOOL().compareTo(expectedVal.getBOOL());
            }
            case SS: 
            case NS: 
            case BS: {
                return LocalDBComparisonOperator.setEquals(actualVal, expectedVal, type) ? 0 : -1;
            }
            case L: {
                if (actualVal.getL().containsAll(expectedVal.getL()) && expectedVal.getL().containsAll(actualVal.getL())) {
                    return 0;
                }
                return -1;
            }
            case M: {
                if (actualVal.getM().equals(expectedVal.getM())) {
                    return 0;
                }
                return -1;
            }
        }
        LocalDBUtils.ldClientFail(LocalDBClientExceptionType.UNREACHABLE_CODE);
        return 0;
    }

    private static boolean setEquals(AttributeValue actual, AttributeValue expected, DDBType type) {
        if (type == DDBType.SS) {
            return actual.getSS().containsAll(expected.getSS()) && expected.getSS().containsAll(actual.getSS());
        }
        if (type == DDBType.BS) {
            return actual.getBS().containsAll(expected.getBS()) && expected.getBS().containsAll(actual.getBS());
        }
        if (type == DDBType.NS) {
            Set<BigDecimal> actualSetBigDec = LocalDBComparisonOperator.convertNumberSet(actual);
            Set<BigDecimal> expectedSetBigDec = LocalDBComparisonOperator.convertNumberSet(expected);
            for (BigDecimal actualBigDec : actualSetBigDec) {
                boolean isEquals = false;
                for (BigDecimal expectedBigDec : expectedSetBigDec) {
                    if (expectedBigDec.compareTo(actualBigDec) != 0) continue;
                    isEquals = true;
                    break;
                }
                if (isEquals) continue;
                return false;
            }
            return expectedSetBigDec.size() <= actualSetBigDec.size();
        }
        return true;
    }

    private static Set<BigDecimal> convertNumberSet(AttributeValue attrVal) {
        HashSet<BigDecimal> setBigDec = new HashSet<BigDecimal>();
        for (String numString : attrVal.getNS()) {
            setBigDec.add(new BigDecimal(numString));
        }
        return setBigDec;
    }

    private static boolean containsFilterHelper(AttributeValue actualVal, AttributeValue expectedVal, DDBType type) {
        if (actualVal == null) {
            return false;
        }
        boolean compares = false;
        switch (type) {
            case N: {
                if (actualVal.getNS() == null && actualVal.getL() == null) {
                    return false;
                }
                if (actualVal.getNS() != null) {
                    for (String numString : actualVal.getNS()) {
                        compares |= new BigDecimal(numString).compareTo(new BigDecimal(expectedVal.getN())) == 0;
                    }
                }
                if (actualVal.getL() != null && actualVal.getL().contains(expectedVal)) {
                    compares = true;
                }
                return compares;
            }
            case S: {
                if (actualVal.getS() != null) {
                    return actualVal.getS().contains(expectedVal.getS());
                }
                if (actualVal.getSS() == null && actualVal.getL() == null) {
                    return false;
                }
                if (actualVal.getSS() != null) {
                    for (String strString : actualVal.getSS()) {
                        compares |= strString.compareTo(expectedVal.getS()) == 0;
                    }
                }
                if (actualVal.getL() != null && actualVal.getL().contains(expectedVal)) {
                    compares = true;
                }
                return compares;
            }
            case B: {
                if (actualVal.getB() != null) {
                    return new String(actualVal.getB().array(), LocalDBUtils.UTF8).contains(new String(expectedVal.getB().array(), LocalDBUtils.UTF8));
                }
                if (actualVal.getBS() == null && actualVal.getL() == null) {
                    return false;
                }
                if (actualVal.getBS() != null) {
                    for (ByteBuffer byteBuf : actualVal.getBS()) {
                        compares |= byteBuf.compareTo(expectedVal.getB()) == 0;
                    }
                }
                if (actualVal.getL() != null && actualVal.getL().contains(expectedVal)) {
                    compares = true;
                }
                return compares;
            }
            case NULL: {
                return actualVal.getL() != null && actualVal.getL().contains(expectedVal);
            }
            case BOOL: {
                return actualVal.getL() != null && actualVal.getL().contains(expectedVal);
            }
        }
        LocalDBUtils.ldClientFail(LocalDBClientExceptionType.UNREACHABLE_CODE);
        return false;
    }

    public void isValidAttributeList(List<AttributeValue> attributeValueList) {
        LocalDBComparisonOperator.checkForEmptyAttributesList(attributeValueList);
        LocalDBComparisonOperator.checkForOversizedAttributeValuesList(attributeValueList, this.toString());
        for (AttributeValue value : attributeValueList) {
            DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(value);
            if (this != EQ && this != NE && type.isSet()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_COMPARISON_SETS.getMessage());
            }
            if (type != DDBType.N) continue;
            LocalDBUtils.validateNumericValue(value.getN());
        }
    }

    public boolean isValidForQuery() {
        return true;
    }

    public boolean isValidAttributeCount(int count) {
        return count == 1;
    }

    public abstract boolean evaluate(List<AttributeValue> var1, AttributeValue var2);

    public boolean evaluateExclusive(List<AttributeValue> attributeValueList, AttributeValue value, boolean asc) {
        return this.evaluate(attributeValueList, value);
    }

    public abstract boolean isTypeSupported(DDBType var1);
}

