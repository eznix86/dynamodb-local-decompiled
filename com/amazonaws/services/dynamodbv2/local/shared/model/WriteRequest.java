/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.model;

import com.amazonaws.services.dynamodbv2.local.shared.model.DeleteRequest;
import com.amazonaws.services.dynamodbv2.local.shared.model.PutRequest;
import java.io.Serializable;

public class WriteRequest
implements Serializable {
    private PutRequest putRequest;
    private DeleteRequest deleteRequest;

    public WriteRequest() {
    }

    public WriteRequest(PutRequest putRequest) {
        this.setPutRequest(putRequest);
    }

    public WriteRequest(DeleteRequest deleteRequest) {
        this.setDeleteRequest(deleteRequest);
    }

    public PutRequest getPutRequest() {
        return this.putRequest;
    }

    public void setPutRequest(PutRequest putRequest) {
        this.putRequest = putRequest;
    }

    public WriteRequest withPutRequest(PutRequest putRequest) {
        this.putRequest = putRequest;
        return this;
    }

    public DeleteRequest getDeleteRequest() {
        return this.deleteRequest;
    }

    public void setDeleteRequest(DeleteRequest deleteRequest) {
        this.deleteRequest = deleteRequest;
    }

    public WriteRequest withDeleteRequest(DeleteRequest deleteRequest) {
        this.deleteRequest = deleteRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (this.getPutRequest() != null) {
            sb.append("PutRequest: ").append(this.getPutRequest()).append(",");
        }
        if (this.getDeleteRequest() != null) {
            sb.append("DeleteRequest: ").append(this.getDeleteRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int prime = 31;
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getPutRequest() == null ? 0 : this.getPutRequest().hashCode());
        hashCode = 31 * hashCode + (this.getDeleteRequest() == null ? 0 : this.getDeleteRequest().hashCode());
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WriteRequest)) {
            return false;
        }
        WriteRequest other = (WriteRequest)obj;
        if (other.getPutRequest() == null ^ this.getPutRequest() == null) {
            return false;
        }
        if (other.getPutRequest() != null && !other.getPutRequest().equals(this.getPutRequest())) {
            return false;
        }
        if (other.getDeleteRequest() == null ^ this.getDeleteRequest() == null) {
            return false;
        }
        return other.getDeleteRequest() == null || other.getDeleteRequest().equals(this.getDeleteRequest());
    }
}

