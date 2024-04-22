/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api;

import com.amazonaws.services.dynamodbv2.local.google.Preconditions;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import java.util.Collections;
import java.util.Map;

public class Mutation {
    private static final String AT_LEAST_ONE_OF_PRE_AND_POST_IMAGES_MUST_BE_SET = "at least one of pre and post images must be set";
    private final Map<String, AttributeValue> preImage;
    private final Map<String, AttributeValue> postImage;

    public Mutation(Map<String, AttributeValue> preImage, Map<String, AttributeValue> postImage) {
        Preconditions.checkArgument(preImage != null || postImage != null, AT_LEAST_ONE_OF_PRE_AND_POST_IMAGES_MUST_BE_SET);
        this.preImage = preImage == null ? null : Collections.unmodifiableMap(preImage);
        this.postImage = postImage == null ? null : Collections.unmodifiableMap(postImage);
    }

    public Map<String, AttributeValue> getPreImage() {
        return this.preImage;
    }

    public Map<String, AttributeValue> getPostImage() {
        return this.postImage;
    }

    public boolean isDeletion() {
        return this.postImage == null;
    }

    public boolean isCreation() {
        return this.preImage == null;
    }

    public boolean isUpdate() {
        return this.preImage != null && this.postImage != null;
    }
}

