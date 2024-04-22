/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

public class IntList {
    public static final int DEFAULT_INITIAL_CAPACITY = 8;
    private static final int GROWTH_MULTIPLIER = 2;
    private int[] data;
    private int numberOfValues;

    public IntList() {
        this(8);
    }

    public IntList(int initialCapacity) {
        this.data = new int[initialCapacity];
        this.numberOfValues = 0;
    }

    public IntList(IntList other) {
        this.numberOfValues = other.numberOfValues;
        this.data = new int[other.data.length];
        System.arraycopy(other.data, 0, this.data, 0, this.numberOfValues);
    }

    public int size() {
        return this.numberOfValues;
    }

    public boolean isEmpty() {
        return this.numberOfValues == 0;
    }

    public void clear() {
        this.numberOfValues = 0;
    }

    public int get(int index) {
        if (index < 0 || index >= this.numberOfValues) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " requested from IntList with " + this.numberOfValues + " values.");
        }
        return this.data[index];
    }

    public void add(int value) {
        if (this.numberOfValues == this.data.length) {
            this.grow();
        }
        this.data[this.numberOfValues] = value;
        ++this.numberOfValues;
    }

    private void grow() {
        int[] newData = new int[this.data.length * 2];
        System.arraycopy(this.data, 0, newData, 0, this.data.length);
        this.data = newData;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IntList{data=[");
        if (this.numberOfValues > 0) {
            for (int m = 0; m < this.numberOfValues; ++m) {
                builder.append(this.data[m]).append(",");
            }
        }
        builder.append("]}");
        return builder.toString();
    }
}

