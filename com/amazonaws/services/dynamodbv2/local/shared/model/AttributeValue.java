/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.internal.ListWithAutoConstructFlag
 */
package com.amazonaws.services.dynamodbv2.local.shared.model;

import com.amazonaws.internal.ListWithAutoConstructFlag;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathListElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.local.shared.access.DDBType;
import com.amazonaws.services.dynamodbv2.local.shared.access.EncoderDecoderUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.UnsignedByteArrayComparator;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeValue
implements DocumentNode {
    public static final Map<DDBType, DocumentNodeType> DynamoDBLocalTypeConverter;
    public static final Map<DocumentNodeType, DDBType> DynamoDBLibTypeConverterFromDocumentNodeType;
    private String s;
    private String n;
    private ByteBuffer b;
    private int level;
    private ListWithAutoConstructFlag<String> sS;
    private ListWithAutoConstructFlag<String> nS;
    private ListWithAutoConstructFlag<ByteBuffer> bS;
    private Map<String, AttributeValue> m;
    private ListWithAutoConstructFlag<AttributeValue> l;
    private Boolean nULL;
    private Boolean bOOL;
    private byte[] rawScalarValue = null;
    private List<byte[]> rawSetValue = null;
    private DDBType type;

    public AttributeValue() {
    }

    public AttributeValue(String s) {
        this.type = DDBType.S;
        this.rawScalarValue = EncoderDecoderUtils.encodeString(s);
        this.setS(s);
    }

    public AttributeValue(List<String> sS) {
        this.type = DDBType.SS;
        this.rawSetValue = EncoderDecoderUtils.getRawBytesForStringSet(this.getSS());
        this.setSS(sS);
    }

    public String getS() {
        return this.s;
    }

    public void setS(String s) {
        this.s = s;
        this.type = DDBType.S;
        this.rawScalarValue = EncoderDecoderUtils.encodeString(s);
    }

    public AttributeValue withS(String s) {
        this.s = s;
        this.type = DDBType.S;
        this.rawScalarValue = EncoderDecoderUtils.encodeString(s);
        return this;
    }

    public String getN() {
        return this.n;
    }

    public void setN(String n) {
        LocalDBUtils.validateNumericValue(n);
        this.type = DDBType.N;
        this.rawScalarValue = LocalDBUtils.encodeBigDecimal(new BigDecimal(n));
        this.n = n;
    }

    public AttributeValue withN(String n) {
        LocalDBUtils.validateNumericValue(n);
        this.type = DDBType.N;
        this.rawScalarValue = LocalDBUtils.encodeBigDecimal(new BigDecimal(n));
        this.n = n;
        return this;
    }

    public ByteBuffer getB() {
        return this.b;
    }

    public void setB(ByteBuffer b) {
        this.type = DDBType.B;
        int length = b.remaining();
        this.rawScalarValue = new byte[length];
        b.duplicate().get(this.rawScalarValue, 0, length);
        this.b = ByteBuffer.wrap(this.rawScalarValue);
    }

    public AttributeValue withB(ByteBuffer b) {
        this.type = DDBType.B;
        this.rawScalarValue = new byte[b.capacity()];
        b.duplicate().get(this.rawScalarValue, 0, b.limit());
        this.b = b;
        return this;
    }

    public List<String> getSS() {
        return this.sS;
    }

    public void setSS(Collection<String> sS) {
        if (sS == null) {
            this.sS = null;
            return;
        }
        ListWithAutoConstructFlag sSCopy = new ListWithAutoConstructFlag(sS.size());
        sSCopy.addAll(sS);
        this.type = DDBType.SS;
        this.rawSetValue = EncoderDecoderUtils.getRawBytesForStringSet((List<String>)sSCopy);
        this.sS = sSCopy;
    }

    public AttributeValue withSS(String ... sS) {
        if (this.getSS() == null) {
            this.setSS(new ArrayList<String>(sS.length));
        }
        for (String value : sS) {
            this.getSS().add(value);
        }
        this.type = DDBType.SS;
        this.rawSetValue = EncoderDecoderUtils.getRawBytesForStringSet(this.getSS());
        return this;
    }

    public AttributeValue withSS(Collection<String> sS) {
        if (sS == null) {
            this.sS = null;
        } else {
            ListWithAutoConstructFlag sSCopy = new ListWithAutoConstructFlag(sS.size());
            sSCopy.addAll(sS);
            this.sS = sSCopy;
        }
        this.type = DDBType.SS;
        this.rawSetValue = EncoderDecoderUtils.getRawBytesForStringSet(this.getSS());
        return this;
    }

    public List<String> getNS() {
        return this.nS;
    }

    public void setNS(Collection<String> nS) {
        if (nS == null) {
            this.nS = null;
            return;
        }
        ListWithAutoConstructFlag nSCopy = new ListWithAutoConstructFlag(nS.size());
        nSCopy.addAll(nS);
        this.type = DDBType.NS;
        this.nS = nSCopy;
        this.rawSetValue = EncoderDecoderUtils.getRawBytesForNumberSet(this.nS);
    }

    public AttributeValue withNS(String ... nS) {
        if (this.getNS() == null) {
            this.setNS(new ArrayList<String>(nS.length));
        }
        for (String value : nS) {
            this.getNS().add(value);
        }
        this.type = DDBType.NS;
        this.rawSetValue = EncoderDecoderUtils.getRawBytesForNumberSet(this.nS);
        return this;
    }

    public AttributeValue withNS(Collection<String> nS) {
        this.type = DDBType.NS;
        if (nS == null) {
            this.nS = null;
        } else {
            ListWithAutoConstructFlag nSCopy = new ListWithAutoConstructFlag(nS.size());
            nSCopy.addAll(nS);
            this.nS = nSCopy;
        }
        this.rawSetValue = EncoderDecoderUtils.getRawBytesForNumberSet(this.nS);
        return this;
    }

    public List<ByteBuffer> getBS() {
        return this.bS;
    }

    public void setBS(Collection<ByteBuffer> bS) {
        if (bS == null) {
            this.bS = null;
            return;
        }
        ListWithAutoConstructFlag bSCopy = new ListWithAutoConstructFlag(bS.size());
        bSCopy.addAll(bS);
        this.type = DDBType.BS;
        this.bS = bSCopy;
        this.rawSetValue = EncoderDecoderUtils.getRawBytesForBinarySet(this.bS);
    }

    public AttributeValue withBS(ByteBuffer ... bS) {
        if (this.getBS() == null) {
            this.setBS(new ArrayList<ByteBuffer>(bS.length));
        }
        for (ByteBuffer value : bS) {
            this.getBS().add(value);
        }
        this.type = DDBType.BS;
        this.rawSetValue = EncoderDecoderUtils.getRawBytesForBinarySet(this.bS);
        return this;
    }

    public AttributeValue withBS(Collection<ByteBuffer> bS) {
        if (bS == null) {
            this.bS = null;
        } else {
            ListWithAutoConstructFlag bSCopy = new ListWithAutoConstructFlag(bS.size());
            bSCopy.addAll(bS);
            this.bS = bSCopy;
        }
        this.type = DDBType.BS;
        this.rawSetValue = EncoderDecoderUtils.getRawBytesForBinarySet(this.bS);
        return this;
    }

    public Map<String, AttributeValue> getM() {
        return this.m;
    }

    public void setM(Map<String, AttributeValue> m) {
        this.type = DDBType.M;
        this.m = m;
    }

    public AttributeValue withM(Map<String, AttributeValue> m) {
        this.setM(m);
        return this;
    }

    public AttributeValue addMEntry(String key, AttributeValue value) {
        if (null == this.m) {
            this.m = new HashMap<String, AttributeValue>();
            this.type = DDBType.M;
        }
        if (this.m.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key + ") are provided.");
        }
        this.m.put(key, value);
        return this;
    }

    public AttributeValue clearMEntries() {
        this.m = null;
        return this;
    }

    public List<AttributeValue> getL() {
        return this.l;
    }

    public void setL(Collection<AttributeValue> l) {
        if (l == null) {
            this.l = null;
            return;
        }
        ListWithAutoConstructFlag lCopy = new ListWithAutoConstructFlag(l.size());
        lCopy.addAll(l);
        this.type = DDBType.L;
        this.l = lCopy;
    }

    public AttributeValue withL(AttributeValue ... l) {
        if (this.getL() == null) {
            this.setL(new ArrayList<AttributeValue>(l.length));
        }
        for (AttributeValue value : l) {
            this.getL().add(value);
        }
        this.type = DDBType.L;
        return this;
    }

    public AttributeValue withL(Collection<AttributeValue> l) {
        if (l == null) {
            this.l = null;
        } else {
            ListWithAutoConstructFlag lCopy = new ListWithAutoConstructFlag(l.size());
            lCopy.addAll(l);
            this.l = lCopy;
        }
        this.type = DDBType.L;
        return this;
    }

    public Boolean isNULL() {
        return this.nULL;
    }

    public AttributeValue withNULL(Boolean nULL) {
        this.nULL = nULL;
        this.type = DDBType.NULL;
        return this;
    }

    public Boolean getNULL() {
        return this.nULL;
    }

    public void setNULL(Boolean nULL) {
        this.nULL = nULL;
        this.type = DDBType.NULL;
    }

    public Boolean isBOOL() {
        return this.bOOL;
    }

    public AttributeValue withBOOL(Boolean bOOL) {
        this.bOOL = bOOL;
        this.type = DDBType.BOOL;
        return this;
    }

    public Boolean getBOOL() {
        return this.bOOL;
    }

    public void setBOOL(Boolean bOOL) {
        this.bOOL = bOOL;
        this.type = DDBType.BOOL;
    }

    public DDBType getType() {
        return this.type;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AttributeValue: {");
        if (this.getS() != null) {
            sb.append("S:").append(this.getS());
        } else if (this.getN() != null) {
            sb.append("N:").append(this.getN());
        } else if (this.getB() != null) {
            sb.append("B:").append(this.getB());
        } else if (this.getSS() != null) {
            sb.append("SS:").append(this.getSS());
        } else if (this.getNS() != null) {
            sb.append("NS:").append(this.getNS());
        } else if (this.getBS() != null) {
            sb.append("BS:").append(this.getBS());
        } else if (this.getM() != null) {
            sb.append("M:").append(this.getM());
        } else if (this.getL() != null) {
            sb.append("L:").append(this.getL());
        } else if (this.isNULL() != null) {
            sb.append("NULL:").append(this.isNULL());
        } else if (this.isBOOL() != null) {
            sb.append("BOOL:").append(this.isBOOL());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int prime = 31;
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getS() == null ? 0 : this.getS().hashCode());
        hashCode = 31 * hashCode + (this.getN() == null ? 0 : this.getN().hashCode());
        hashCode = 31 * hashCode + (this.getB() == null ? 0 : this.getB().hashCode());
        hashCode = 31 * hashCode + (this.getSS() == null ? 0 : this.getSS().hashCode());
        hashCode = 31 * hashCode + (this.getNS() == null ? 0 : this.getNS().hashCode());
        hashCode = 31 * hashCode + (this.getBS() == null ? 0 : this.getBS().hashCode());
        hashCode = 31 * hashCode + (this.getM() == null ? 0 : this.getM().hashCode());
        hashCode = 31 * hashCode + (this.getL() == null ? 0 : this.getL().hashCode());
        hashCode = 31 * hashCode + (this.isNULL() == null ? 0 : this.isNULL().hashCode());
        hashCode = 31 * hashCode + (this.isBOOL() == null ? 0 : this.isBOOL().hashCode());
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AttributeValue)) {
            return false;
        }
        AttributeValue other = (AttributeValue)obj;
        if (other.getType() != this.getType()) {
            return false;
        }
        if (other.getS() == null ^ this.getS() == null) {
            return false;
        }
        if (other.getN() == null ^ this.getN() == null) {
            return false;
        }
        if (other.getB() == null ^ this.getB() == null) {
            return false;
        }
        if (!(other.getS() == null && other.getN() == null && other.getB() == null || Arrays.equals(other.getRawScalarValue(), this.getRawScalarValue()))) {
            return false;
        }
        if (other.getSS() == null ^ this.getSS() == null) {
            return false;
        }
        if (other.getNS() == null ^ this.getNS() == null) {
            return false;
        }
        if (other.getBS() == null ^ this.getBS() == null) {
            return false;
        }
        if (other.getSS() != null || other.getNS() != null || other.getBS() != null) {
            if (other.getRawSetValue().size() != this.getRawSetValue().size()) {
                return false;
            }
            for (int i = 0; i < other.getRawSetValue().size(); ++i) {
                if (Arrays.equals(other.getRawSetValue().get(i), this.getRawSetValue().get(i))) continue;
                return false;
            }
        }
        if (other.getM() == null ^ this.getM() == null) {
            return false;
        }
        if (other.getM() != null && !other.getM().equals(this.getM())) {
            return false;
        }
        if (other.getL() == null ^ this.getL() == null) {
            return false;
        }
        if (other.getL() != null && !other.getL().equals(this.getL())) {
            return false;
        }
        if (other.isNULL() == null ^ this.isNULL() == null) {
            return false;
        }
        if (other.isNULL() != null && !other.isNULL().equals(this.isNULL())) {
            return false;
        }
        if (other.isBOOL() == null ^ this.isBOOL() == null) {
            return false;
        }
        return other.isBOOL() == null || other.isBOOL().equals(this.isBOOL());
    }

    @Override
    @JsonIgnore
    public DocumentNode getChild(DocPathElement key) {
        if (DDBType.M == this.type && key instanceof DocPathMapElement) {
            return this.getM().get(key.getFieldName());
        }
        if (DDBType.L == this.type && key instanceof DocPathListElement) {
            if (key.getListIndex() >= this.getL().size()) {
                return null;
            }
            return this.getL().get(key.getListIndex());
        }
        return null;
    }

    @Override
    @JsonIgnore
    public List<DocPathElement> getChildren() {
        ArrayList<DocPathElement> returnList = new ArrayList<DocPathElement>();
        if (this.type == DDBType.L) {
            for (int i = 0; i < this.getL().size(); ++i) {
                returnList.add(new DocPathListElement(i));
            }
        } else if (this.type == DDBType.M) {
            for (String name : this.getM().keySet()) {
                returnList.add(new DocPathMapElement(name));
            }
        } else {
            return null;
        }
        return returnList;
    }

    @Override
    @JsonIgnore
    public boolean eq(DocumentNode other) {
        return this.equals(other);
    }

    @Override
    @JsonIgnore
    public boolean numericEq(DocumentNode other) {
        return this.eq(other);
    }

    @Override
    @JsonIgnore
    public boolean lessThan(DocumentNode other) {
        return UnsignedByteArrayComparator.compareUnsignedByteArrays(this.getRawScalarValue(), other.getRawScalarValue()) < 0;
    }

    @Override
    @JsonIgnore
    public boolean greaterThan(DocumentNode other) {
        return UnsignedByteArrayComparator.compareUnsignedByteArrays(this.getRawScalarValue(), other.getRawScalarValue()) > 0;
    }

    @Override
    @JsonIgnore
    public int compare(DocumentNode other) {
        if (!DDBType.SortableScalarTypeSet.contains((Object)this.type)) {
            throw AWSExceptionFactory.buildInternalServerException(LocalDBClientExceptionMessage.NON_SCALAR_TYPE_COMPARISON.getMessage() + ". Type: " + this.type.name());
        }
        if (other.getNodeType() != this.getNodeType()) {
            throw AWSExceptionFactory.buildInternalServerException(LocalDBClientExceptionMessage.INVALID_TYPE_COMPARISON.getMessage() + ". Types: " + other.getNodeType().getAbbrName() + " " + this.getNodeType().getAbbrName());
        }
        return UnsignedByteArrayComparator.compareUnsignedByteArrays(this.getRawScalarValue(), other.getRawScalarValue());
    }

    @Override
    @JsonIgnore
    public int compareTo(DocumentNode o2) {
        return this.compare(o2);
    }

    @Override
    @JsonIgnore
    public boolean getBooleanValue() {
        return this.getBOOL();
    }

    @Override
    @JsonIgnore
    public BigDecimal getNValue() {
        return new BigDecimal(this.getN());
    }

    @Override
    @JsonIgnore
    public String getSValue() {
        return this.getS();
    }

    @Override
    @JsonIgnore
    public boolean isMap() {
        return this.getM() != null;
    }

    @Override
    @JsonIgnore
    public int getLevel() {
        return this.level;
    }

    @Override
    @JsonIgnore
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    @JsonIgnore
    public byte[] getRawScalarValue() {
        return this.rawScalarValue;
    }

    @Override
    @JsonIgnore
    public List<byte[]> getRawSetValue() {
        return this.rawSetValue;
    }

    @Override
    @JsonIgnore
    public DocumentNode mergeCollection(DocumentNode documentNode) {
        throw new UnsupportedOperationException();
    }

    @Override
    @JsonIgnore
    public DocumentNode removeElementsFromCollection(DocumentNode documentNode) {
        throw new UnsupportedOperationException();
    }

    @Override
    @JsonIgnore
    public DocumentNodeType getNodeType() {
        return DynamoDBLocalTypeConverter.get((Object)this.type);
    }

    static {
        HashMap<DDBType, DocumentNodeType> TypeToDocType = new HashMap<DDBType, DocumentNodeType>();
        TypeToDocType.put(DDBType.N, DocumentNodeType.NUMBER);
        TypeToDocType.put(DDBType.NS, DocumentNodeType.NUMBER_SET);
        TypeToDocType.put(DDBType.S, DocumentNodeType.STRING);
        TypeToDocType.put(DDBType.SS, DocumentNodeType.STRING_SET);
        TypeToDocType.put(DDBType.B, DocumentNodeType.BINARY);
        TypeToDocType.put(DDBType.BS, DocumentNodeType.BINARY_SET);
        TypeToDocType.put(DDBType.M, DocumentNodeType.MAP);
        TypeToDocType.put(DDBType.L, DocumentNodeType.LIST);
        TypeToDocType.put(DDBType.NULL, DocumentNodeType.NULL);
        TypeToDocType.put(DDBType.BOOL, DocumentNodeType.BOOLEAN);
        DynamoDBLocalTypeConverter = Collections.unmodifiableMap(TypeToDocType);
        HashMap<DocumentNodeType, DDBType> DocTypeToType = new HashMap<DocumentNodeType, DDBType>();
        DocTypeToType.put(DocumentNodeType.NUMBER, DDBType.N);
        DocTypeToType.put(DocumentNodeType.NUMBER_SET, DDBType.NS);
        DocTypeToType.put(DocumentNodeType.STRING, DDBType.S);
        DocTypeToType.put(DocumentNodeType.STRING_SET, DDBType.SS);
        DocTypeToType.put(DocumentNodeType.BINARY, DDBType.B);
        DocTypeToType.put(DocumentNodeType.BINARY_SET, DDBType.BS);
        DocTypeToType.put(DocumentNodeType.MAP, DDBType.M);
        DocTypeToType.put(DocumentNodeType.LIST, DDBType.L);
        DocTypeToType.put(DocumentNodeType.NULL, DDBType.NULL);
        DocTypeToType.put(DocumentNodeType.BOOLEAN, DDBType.BOOL);
        DynamoDBLibTypeConverterFromDocumentNodeType = Collections.unmodifiableMap(DocTypeToType);
    }
}

