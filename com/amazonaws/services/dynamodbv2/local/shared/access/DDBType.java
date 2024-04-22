/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ScalarAttributeType
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum DDBType {
    S(false, ScalarAttributeType.S),
    N(false, ScalarAttributeType.N),
    B(false, ScalarAttributeType.B),
    SS(true, null),
    NS(true, null),
    BS(true, null),
    BOOL(false, null),
    NULL(false, null),
    L(false, null),
    M(false, null);

    public static final Set<DDBType> SortableScalarTypeSet;
    public static final Set<DDBType> AllScalarTypeSet;
    public static final Set<DDBType> SetTypes;
    public static final Set<DDBType> DocumentTypes;
    private final boolean isSet;
    private final ScalarAttributeType scalarAttributeType;

    private DDBType(boolean isSet, ScalarAttributeType scalarAttributeType) {
        this.isSet = isSet;
        this.scalarAttributeType = scalarAttributeType;
    }

    public ScalarAttributeType getScalarAttributeType() {
        return this.scalarAttributeType;
    }

    public boolean isSet() {
        return this.isSet;
    }

    static {
        HashSet<DDBType> mutableSet = new HashSet<DDBType>();
        mutableSet.add(S);
        mutableSet.add(N);
        mutableSet.add(B);
        SortableScalarTypeSet = Collections.unmodifiableSet(mutableSet);
        mutableSet = new HashSet();
        mutableSet.add(S);
        mutableSet.add(N);
        mutableSet.add(B);
        mutableSet.add(BOOL);
        mutableSet.add(NULL);
        AllScalarTypeSet = Collections.unmodifiableSet(mutableSet);
        mutableSet = new HashSet();
        mutableSet.add(SS);
        mutableSet.add(NS);
        mutableSet.add(BS);
        SetTypes = Collections.unmodifiableSet(mutableSet);
        HashSet<DDBType> nestedValidTypes = new HashSet<DDBType>();
        nestedValidTypes.add(M);
        nestedValidTypes.add(L);
        DocumentTypes = Collections.unmodifiableSet(nestedValidTypes);
    }
}

