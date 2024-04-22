/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.token;

import java.util.Date;
import java.util.Objects;

public abstract class ContinuationToken {
    private final String subscriberId;
    private final String requestHash;
    private final Date creationTime;
    private final TokenVersion version;

    protected ContinuationToken(String subscriberId, String requestHash, Date creationTime, TokenVersion version) {
        this.subscriberId = subscriberId;
        this.requestHash = requestHash;
        this.creationTime = creationTime;
        this.version = version;
    }

    public String getSubscriberId() {
        return this.subscriberId;
    }

    public String getRequestHash() {
        return this.requestHash;
    }

    public Date getCreationTime() {
        return new Date(this.creationTime.getTime());
    }

    public TokenVersion getVersion() {
        return this.version;
    }

    public boolean equals(Object o2) {
        if (this == o2) {
            return true;
        }
        if (o2 == null || this.getClass() != o2.getClass()) {
            return false;
        }
        ContinuationToken that = (ContinuationToken)o2;
        return Objects.equals(this.subscriberId, that.subscriberId) && Objects.equals(this.requestHash, that.requestHash) && Objects.equals(this.creationTime, that.creationTime) && this.version == that.version;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.subscriberId, this.requestHash, this.creationTime, this.version});
    }

    public static enum TokenVersion {
        V1;

    }
}

