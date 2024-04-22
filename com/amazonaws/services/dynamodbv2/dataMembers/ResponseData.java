/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.server.Response
 */
package com.amazonaws.services.dynamodbv2.dataMembers;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import java.util.Set;
import java.util.zip.CRC32;
import org.eclipse.jetty.server.Response;

public class ResponseData {
    private final Response response;
    private byte[] responseBody = null;

    public ResponseData(Response response) throws DynamoDBLocalServiceException {
        if (response == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, "Received null response object");
        }
        this.response = response;
    }

    public byte[] getResponseBody() {
        return this.responseBody;
    }

    public void setResponseBody(byte[] responseBody) throws DynamoDBLocalServiceException {
        if (this.responseBody != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, "Response body has already been set");
        }
        this.responseBody = responseBody;
        CRC32 checksum = new CRC32();
        checksum.update(responseBody, 0, responseBody.length);
    }

    public Response getResponse() {
        return this.response;
    }

    public String toString() {
        StringBuilder output = new StringBuilder("[Response] ");
        Set collection = this.response.getHeaders().getFieldNamesCollection();
        for (String headName : collection) {
            output.append(String.format("header name: %s : value: %s\n", headName, this.response.getHeaders().get(headName)));
        }
        output.append("status: ").append(this.response.getStatus()).append("\n");
        if (this.responseBody.length > 4096) {
            output.append("<< response body not shown (length > 4KB) >>");
        } else {
            output.append("response body: ").append(new String(this.responseBody, LocalDBUtils.UTF8)).append("\n");
        }
        return output.toString();
    }
}

