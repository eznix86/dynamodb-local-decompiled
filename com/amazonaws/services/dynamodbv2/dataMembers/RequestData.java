/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.server.Request
 */
package com.amazonaws.services.dynamodbv2.dataMembers;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import java.util.Enumeration;
import org.eclipse.jetty.server.Request;

public class RequestData {
    private final Request baseRequest;
    private byte[] requestBody = null;

    public RequestData(Request baseRequest) throws IllegalArgumentException {
        if (baseRequest == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, "Received null request object");
        }
        this.baseRequest = baseRequest;
    }

    public byte[] getRequestBody() {
        return this.requestBody;
    }

    public void setRequestBody(byte[] requestBody) throws DynamoDBLocalServiceException {
        if (this.requestBody != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, "Request body has already been set");
        }
        this.requestBody = requestBody;
    }

    public Request getBaseRequest() {
        return this.baseRequest;
    }

    public String toString() {
        StringBuilder output = new StringBuilder("[Request] ");
        Enumeration headerSet = this.baseRequest.getHeaders().getFieldNames();
        while (headerSet.hasMoreElements()) {
            String head = (String)headerSet.nextElement();
            output.append("header name: ").append(head).append(" : value: ").append(this.baseRequest.getHeaders().get(head)).append("\n");
        }
        if (this.requestBody == null) {
            output.append("<< request body is null >>");
        } else if (this.requestBody.length > 4096) {
            output.append("<< request body not shown (length > 4KB) >>");
        } else {
            output.append("request body: ").append(new String(this.requestBody, LocalDBUtils.UTF8)).append("\n");
        }
        return output.toString();
    }
}

