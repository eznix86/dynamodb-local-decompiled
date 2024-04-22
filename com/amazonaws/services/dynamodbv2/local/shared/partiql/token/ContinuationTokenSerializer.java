/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.token;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.mapper.DynamoDBObjectMapper;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.token.SerialContinuationToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.token.ContinuationToken;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.partiql.lang.ast.Select;

public class ContinuationTokenSerializer {
    private static final String VERSION_STRING = "version";
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final Long TOKEN_EXPIRATION_TIME = 3600000L;
    private static final int NEXT_TOKEN_SIZE_MAX = 32768;
    private static final String TOO_LARGE_NEXT_TOKEN_ERR_MSG = "Member must have length less than or equal to 32768";
    private final DynamoDBObjectMapper dynamoDBObjectMapper;
    private final LocalPartiQLDbEnv localPartiQLDbEnv;

    public ContinuationTokenSerializer(DynamoDBObjectMapper dynamoDBObjectMapper, LocalPartiQLDbEnv localPartiQLDbEnv) {
        this.dynamoDBObjectMapper = dynamoDBObjectMapper;
        this.localPartiQLDbEnv = localPartiQLDbEnv;
    }

    public Map<String, AttributeValue> deserializeAndConvertContinuationToken(ParsedPartiQLRequest<Select> request) {
        SerialContinuationToken continuationToken;
        String continuationTokenString = request.getContinuationToken();
        if (continuationTokenString == null) {
            return null;
        }
        if (continuationTokenString.length() > 32768) {
            throw this.localPartiQLDbEnv.createValidationError(TOO_LARGE_NEXT_TOKEN_ERR_MSG);
        }
        try {
            ObjectNode objectNode = this.dynamoDBObjectMapper.readValue(DECODER.decode(request.getContinuationToken()), ObjectNode.class);
            ContinuationToken.TokenVersion tokenVersion = ContinuationToken.TokenVersion.valueOf(objectNode.get(VERSION_STRING).textValue());
            this.localPartiQLDbEnv.dbPqlAssert(tokenVersion.equals((Object)ContinuationToken.TokenVersion.V1), "decryptAndDeserializeContinuationToken", "Unrecognized token version", (Object)tokenVersion);
            objectNode.remove(VERSION_STRING);
            continuationToken = this.dynamoDBObjectMapper.treeToValue(objectNode, SerialContinuationToken.class);
        } catch (IOException e) {
            throw this.localPartiQLDbEnv.createValidationError("Invalid NextToken");
        }
        this.validateContinuationToken(request, continuationToken);
        return continuationToken.getOpIndexToExclusiveNextKey().get(0);
    }

    public String createAndSerializeContinuationToken(ParsedPartiQLRequest<Select> request, Map<String, AttributeValue> exclusiveStartKey) {
        if (exclusiveStartKey == null) {
            return null;
        }
        HashMap<Integer, Map<String, AttributeValue>> indexToExclusiveStartKey = new HashMap<Integer, Map<String, AttributeValue>>();
        indexToExclusiveStartKey.put(0, exclusiveStartKey);
        SerialContinuationToken serialContinuationToken = new SerialContinuationToken(request.getRequestHash(), Date.from(Instant.now()), indexToExclusiveStartKey);
        try {
            return ENCODER.encodeToString(this.dynamoDBObjectMapper.writeValueAsString(serialContinuationToken).getBytes());
        } catch (IOException e) {
            throw AWSExceptionFactory.buildInternalServerException("failed to serialize continuation token");
        }
    }

    private void validateContinuationToken(ParsedPartiQLRequest<Select> request, ContinuationToken token) {
        if (token == null) {
            return;
        }
        if (request.getRequestHash() != null && !request.getRequestHash().equals(token.getRequestHash())) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("NextToken does not match request").build(new Object[0]));
        }
        if (Instant.now().toEpochMilli() - token.getCreationTime().getTime() >= TOKEN_EXPIRATION_TIME) {
            throw this.localPartiQLDbEnv.createValidationError(new ExceptionMessageBuilder("Given NextToken has already expired").build(new Object[0]));
        }
    }
}

