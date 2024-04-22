/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import java.util.ArrayList;
import java.util.List;

public final class _Private_RecyclingStack<T> {
    private final List<T> elements;
    private final ElementFactory<T> elementFactory;
    private int currentIndex;
    private T top;

    public _Private_RecyclingStack(int initialCapacity, ElementFactory<T> elementFactory) {
        this.elements = new ArrayList<T>(initialCapacity);
        this.elementFactory = elementFactory;
        this.currentIndex = -1;
        this.top = null;
    }

    public T push() {
        ++this.currentIndex;
        if (this.currentIndex >= this.elements.size()) {
            this.top = this.elementFactory.newElement();
            this.elements.add(this.top);
        } else {
            this.top = this.elements.get(this.currentIndex);
        }
        return this.top;
    }

    public T peek() {
        return this.top;
    }

    public T pop() {
        T popped = this.top;
        --this.currentIndex;
        if (this.currentIndex >= 0) {
            this.top = this.elements.get(this.currentIndex);
        } else {
            this.top = null;
            this.currentIndex = -1;
        }
        return popped;
    }

    public boolean isEmpty() {
        return this.top == null;
    }

    public int size() {
        return this.currentIndex + 1;
    }

    public static interface ElementFactory<T> {
        public T newElement();
    }
}

