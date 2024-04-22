/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.DeleteRequest
 *  com.amazonaws.services.dynamodbv2.model.ItemResponse
 *  com.amazonaws.services.dynamodbv2.model.KeysAndAttributes
 *  com.amazonaws.services.dynamodbv2.model.PutRequest
 *  com.amazonaws.services.dynamodbv2.model.WriteRequest
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.PutRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteRequest;
import com.amazonaws.services.dynamodbv2.model.ItemResponse;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalDBOutputConverter {
    public Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> internalToExternalAttributes(Map<String, AttributeValue> attributes) {
        if (attributes == null) {
            return null;
        }
        HashMap<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> out = new HashMap<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>(attributes.size());
        for (Map.Entry<String, AttributeValue> e : attributes.entrySet()) {
            out.put(e.getKey(), this.internalToExternalAttribute(e.getValue()));
        }
        return out;
    }

    private com.amazonaws.services.dynamodbv2.model.AttributeValue internalToExternalAttribute(AttributeValue value) {
        if (value == null) {
            return null;
        }
        com.amazonaws.services.dynamodbv2.model.AttributeValue out = new com.amazonaws.services.dynamodbv2.model.AttributeValue();
        if (value.getS() != null) {
            out.setS(value.getS());
        } else if (value.getN() != null) {
            out.setN(value.getN());
        } else if (value.getB() != null) {
            out.setB(value.getB());
        } else if (value.isNULL() != null) {
            out.setNULL(value.isNULL());
        } else if (value.getBOOL() != null) {
            out.setBOOL(value.getBOOL());
        } else if (value.getSS() != null) {
            out.setSS(value.getSS());
        } else if (value.getNS() != null) {
            out.setNS(value.getNS());
        } else if (value.getBS() != null) {
            out.setBS(value.getBS());
        } else if (value.getL() != null) {
            ArrayList<com.amazonaws.services.dynamodbv2.model.AttributeValue> lval = new ArrayList<com.amazonaws.services.dynamodbv2.model.AttributeValue>(value.getL().size());
            for (AttributeValue lv : value.getL()) {
                lval.add(this.internalToExternalAttribute(lv));
            }
            out.setL(lval);
        } else if (value.getM() != null) {
            out.setM(this.internalToExternalAttributes(value.getM()));
        }
        return out;
    }

    public Map<String, List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>>> internalToExternalBatchGetResponses(Map<String, List<Map<String, AttributeValue>>> internal) {
        if (internal == null) {
            return null;
        }
        HashMap<String, List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>>> external = new HashMap<String, List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>>>(internal.size());
        for (Map.Entry<String, List<Map<String, AttributeValue>>> e : internal.entrySet()) {
            external.put(e.getKey(), this.internalToExternalItemList(e.getValue()));
        }
        return external;
    }

    public List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> internalToExternalItemList(List<Map<String, AttributeValue>> internal) {
        if (internal == null) {
            return null;
        }
        ArrayList<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> external = new ArrayList<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>>(internal.size());
        for (Map<String, AttributeValue> internalItem : internal) {
            external.add(this.internalToExternalAttributes(internalItem));
        }
        return external;
    }

    public List<ItemResponse> internalToExternalTransactGetItemsResponses(List<Map<String, AttributeValue>> internal) {
        if (internal == null) {
            return null;
        }
        ArrayList<ItemResponse> result = new ArrayList<ItemResponse>(internal.size());
        for (Map<String, AttributeValue> item : internal) {
            result.add(new ItemResponse().withItem(this.internalToExternalAttributes(item)));
        }
        return result;
    }

    public Map<String, KeysAndAttributes> internalToExternalBatchGetRequests(Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.KeysAndAttributes> internal) {
        if (internal == null) {
            return null;
        }
        HashMap<String, KeysAndAttributes> external = new HashMap<String, KeysAndAttributes>(internal.size());
        for (Map.Entry<String, com.amazonaws.services.dynamodbv2.local.shared.model.KeysAndAttributes> e : internal.entrySet()) {
            KeysAndAttributes kas = new KeysAndAttributes();
            kas.setAttributesToGet(e.getValue().getAttributesToGet());
            kas.setConsistentRead(e.getValue().getConsistentRead());
            kas.setKeys(this.internalToExternalItemList(e.getValue().getKeys()));
            kas.setExpressionAttributeNames(e.getValue().getExpressionAttributeNames());
            kas.setProjectionExpression(e.getValue().getProjectionExpression());
            external.put(e.getKey(), kas);
        }
        return external;
    }

    public Map<String, List<WriteRequest>> internalToExternalBatchWriteRequests(Map<String, List<com.amazonaws.services.dynamodbv2.local.shared.model.WriteRequest>> internal) {
        if (internal == null) {
            return null;
        }
        HashMap<String, List<WriteRequest>> external = new HashMap<String, List<WriteRequest>>(internal.size());
        for (Map.Entry<String, List<com.amazonaws.services.dynamodbv2.local.shared.model.WriteRequest>> e : internal.entrySet()) {
            if (e.getValue() == null) {
                external.put(e.getKey(), null);
                continue;
            }
            ArrayList<WriteRequest> writes = new ArrayList<WriteRequest>(e.getValue().size());
            for (com.amazonaws.services.dynamodbv2.local.shared.model.WriteRequest writeInternal : e.getValue()) {
                WriteRequest write = new WriteRequest();
                PutRequest putInternal = writeInternal.getPutRequest();
                com.amazonaws.services.dynamodbv2.local.shared.model.DeleteRequest deleteInternal = writeInternal.getDeleteRequest();
                if (putInternal != null) {
                    com.amazonaws.services.dynamodbv2.model.PutRequest putExternal = new com.amazonaws.services.dynamodbv2.model.PutRequest();
                    putExternal.setItem(this.internalToExternalAttributes(putInternal.getItem()));
                    write.setPutRequest(putExternal);
                }
                if (deleteInternal != null) {
                    DeleteRequest deleteExternal = new DeleteRequest();
                    deleteExternal.setKey(this.internalToExternalAttributes(deleteInternal.getKey()));
                    write.setDeleteRequest(deleteExternal);
                }
                writes.add(write);
            }
            external.put(e.getKey(), writes);
        }
        return external;
    }
}

