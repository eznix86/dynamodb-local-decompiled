/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazon.ion.Decimal;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.google.common.collect.ImmutableTable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public final class TypeCastMatrix {
    private static final ImmutableTable<DocumentNodeType, DocumentNodeType, DocumentNodeType> IMPLICIT_CASTING_MATRIX = new ImmutableTable.Builder<DocumentNodeType, DocumentNodeType, DocumentNodeType>().put(DocumentNodeType.DECIMAL, DocumentNodeType.INT, DocumentNodeType.DECIMAL).put(DocumentNodeType.DECIMAL, DocumentNodeType.DOUBLE, DocumentNodeType.DECIMAL).put(DocumentNodeType.DECIMAL, DocumentNodeType.NUMBER, DocumentNodeType.DECIMAL).put(DocumentNodeType.INT, DocumentNodeType.DECIMAL, DocumentNodeType.DECIMAL).put(DocumentNodeType.INT, DocumentNodeType.NUMBER, DocumentNodeType.NUMBER).put(DocumentNodeType.INT, DocumentNodeType.DOUBLE, DocumentNodeType.DOUBLE).put(DocumentNodeType.NUMBER, DocumentNodeType.DECIMAL, DocumentNodeType.DECIMAL).put(DocumentNodeType.NUMBER, DocumentNodeType.INT, DocumentNodeType.NUMBER).put(DocumentNodeType.NUMBER, DocumentNodeType.DOUBLE, DocumentNodeType.NUMBER).put(DocumentNodeType.DOUBLE, DocumentNodeType.DECIMAL, DocumentNodeType.DECIMAL).put(DocumentNodeType.DOUBLE, DocumentNodeType.INT, DocumentNodeType.DOUBLE).put(DocumentNodeType.DOUBLE, DocumentNodeType.NUMBER, DocumentNodeType.NUMBER).build();

    private TypeCastMatrix() {
    }

    public static Optional<DocumentNodeType> getResultType(DocumentNodeType leftOpType, DocumentNodeType rightOpType) {
        if (leftOpType == rightOpType) {
            return Optional.of(leftOpType);
        }
        return Optional.ofNullable((DocumentNodeType)((Object)IMPLICIT_CASTING_MATRIX.get((Object)leftOpType, (Object)rightOpType)));
    }

    public static boolean isCastingAllowed(DocumentNodeType sourceType, DocumentNodeType targetType) {
        Optional<DocumentNodeType> resultType = TypeCastMatrix.getResultType(sourceType, targetType);
        return resultType.filter(castedType -> castedType == targetType).isPresent();
    }

    public static DocumentNode safeTypeCast(DocumentNode documentNode, DocumentNodeType targetType, DocumentFactory docFactory) {
        if (TypeCastMatrix.isCastingAllowed(documentNode.getNodeType(), targetType)) {
            return TypeCastMatrix.typeCast(documentNode, targetType, docFactory);
        }
        return null;
    }

    public static DocumentNode typeCast(DocumentNode documentNode, DocumentNodeType targetType, DocumentFactory docFactory) {
        DocumentNodeType sourceType = documentNode.getNodeType();
        if (sourceType == targetType) {
            return documentNode;
        }
        switch (sourceType) {
            case NUMBER: {
                BigDecimal num = documentNode.getNValue();
                switch (targetType) {
                    case INT: {
                        return docFactory.makeInt(num.toBigInteger());
                    }
                    case FLOAT: {
                        return docFactory.makeFloat(num.floatValue());
                    }
                    case DOUBLE: {
                        return docFactory.makeDouble(num.doubleValue());
                    }
                    case DECIMAL: {
                        return docFactory.makeDecimal(Decimal.valueOf(num));
                    }
                    case HELENUS_DECIMAL: {
                        return docFactory.makeHelenusDecimal(num);
                    }
                }
                break;
            }
            case HELENUS_DECIMAL: {
                BigDecimal num = documentNode.getHelenusDecimalValue();
                switch (targetType) {
                    case INT: {
                        return docFactory.makeInt(num.toBigInteger());
                    }
                    case FLOAT: {
                        return docFactory.makeFloat(num.floatValue());
                    }
                    case DOUBLE: {
                        return docFactory.makeDouble(num.doubleValue());
                    }
                    case DECIMAL: {
                        return docFactory.makeDecimal(Decimal.valueOf(num));
                    }
                    case NUMBER: {
                        return docFactory.makeNumber(num);
                    }
                }
                break;
            }
            case DECIMAL: {
                Decimal num = documentNode.getDecimalValue();
                switch (targetType) {
                    case INT: {
                        return docFactory.makeInt(num.toBigInteger());
                    }
                    case FLOAT: {
                        return docFactory.makeFloat(num.floatValue());
                    }
                    case DOUBLE: {
                        return docFactory.makeDouble(num.doubleValue());
                    }
                    case HELENUS_DECIMAL: {
                        return docFactory.makeHelenusDecimal(num);
                    }
                    case NUMBER: {
                        return docFactory.makeNumber(num);
                    }
                }
                break;
            }
            case FLOAT: {
                Float num = documentNode.getFloatValue();
                switch (targetType) {
                    case INT: {
                        return docFactory.makeInt(BigDecimal.valueOf(num.floatValue()).toBigInteger());
                    }
                    case DOUBLE: {
                        return docFactory.makeDouble(Double.parseDouble(num.toString()));
                    }
                    case DECIMAL: {
                        return docFactory.makeDecimal(Decimal.valueOf(num.toString()));
                    }
                    case HELENUS_DECIMAL: {
                        return docFactory.makeHelenusDecimal(new BigDecimal(num.toString()));
                    }
                    case NUMBER: {
                        return docFactory.makeNumber(new BigDecimal(num.toString()));
                    }
                }
                break;
            }
            case DOUBLE: {
                Double num = documentNode.getDoubleValue();
                switch (targetType) {
                    case INT: {
                        return docFactory.makeInt(BigDecimal.valueOf(num).toBigInteger());
                    }
                    case FLOAT: {
                        return docFactory.makeFloat(num.floatValue());
                    }
                    case DECIMAL: {
                        return docFactory.makeDecimal(Decimal.valueOf(num.toString()));
                    }
                    case HELENUS_DECIMAL: {
                        return docFactory.makeHelenusDecimal(new BigDecimal(num.toString()));
                    }
                    case NUMBER: {
                        return docFactory.makeNumber(new BigDecimal(num.toString()));
                    }
                }
                break;
            }
            case INT: {
                BigInteger num = documentNode.getIntValue();
                switch (targetType) {
                    case FLOAT: {
                        return docFactory.makeFloat(num.floatValue());
                    }
                    case DOUBLE: {
                        return docFactory.makeDouble(num.doubleValue());
                    }
                    case DECIMAL: {
                        return docFactory.makeDecimal(Decimal.valueOf(num));
                    }
                    case HELENUS_DECIMAL: {
                        return docFactory.makeHelenusDecimal(new BigDecimal(num));
                    }
                    case NUMBER: {
                        return docFactory.makeNumber(new BigDecimal(num));
                    }
                }
            }
        }
        return null;
    }
}

