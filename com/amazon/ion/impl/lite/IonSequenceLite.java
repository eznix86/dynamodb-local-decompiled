/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonValue;
import com.amazon.ion.ValueFactory;
import com.amazon.ion.impl._Private_CurriedValueFactory;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContainerLite;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonValueLite;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

abstract class IonSequenceLite
extends IonContainerLite
implements IonSequence {
    protected static final IonValueLite[] EMPTY_VALUE_ARRAY = new IonValueLite[0];

    IonSequenceLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonSequenceLite(IonSequenceLite existing, IonContext context) {
        super(existing, context);
    }

    IonSequenceLite(ContainerlessContext context, Collection<? extends IonValue> elements) throws ContainedValueException, NullPointerException, IllegalArgumentException {
        this(context, elements == null);
        assert (this._children == null);
        if (elements != null) {
            this._children = new IonValueLite[elements.size()];
            for (IonValueLite ionValueLite : elements) {
                super.add(ionValueLite);
            }
        }
    }

    @Override
    public IonSequence clone() {
        return (IonSequence)((Object)this.deepClone(false));
    }

    @Override
    public boolean add(IonValue element) throws ContainedValueException, NullPointerException {
        return super.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends IonValue> c) {
        this.checkForLock();
        if (c == null) {
            throw new NullPointerException();
        }
        boolean changed = false;
        for (IonValue ionValue2 : c) {
            changed = this.add(ionValue2) || changed;
        }
        return changed;
    }

    @Override
    public boolean addAll(int index, Collection<? extends IonValue> c) {
        this.checkForLock();
        if (c == null) {
            throw new NullPointerException();
        }
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        }
        boolean changed = false;
        for (IonValue ionValue2 : c) {
            this.add(index++, ionValue2);
            changed = true;
        }
        if (changed) {
            this.patch_elements_helper(index);
        }
        return changed;
    }

    @Override
    public ValueFactory add() {
        return new _Private_CurriedValueFactory(this.getSystem()){

            @Override
            protected void handle(IonValue newValue) {
                IonSequenceLite.this.add(newValue);
            }
        };
    }

    @Override
    public void add(int index, IonValue element) throws ContainedValueException, NullPointerException {
        this.add(index, (IonValueLite)element);
    }

    @Override
    public ValueFactory add(final int index) {
        return new _Private_CurriedValueFactory(this.getSystem()){

            @Override
            protected void handle(IonValue newValue) {
                IonSequenceLite.this.add(index, newValue);
                IonSequenceLite.this.patch_elements_helper(index + 1);
            }
        };
    }

    @Override
    public IonValue set(int index, IonValue element) {
        this.checkForLock();
        IonValueLite concrete = (IonValueLite)element;
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException("" + index);
        }
        this.validateNewChild(element);
        assert (this._children != null);
        concrete._context = this.getContextForIndex(element, index);
        IonValueLite removed = this.set_child(index, concrete);
        concrete._elementid(index);
        removed.detachFromContainer();
        return removed;
    }

    @Override
    public IonValue remove(int index) {
        this.checkForLock();
        if (index < 0 || index >= this.get_child_count()) {
            throw new IndexOutOfBoundsException("" + index);
        }
        IonValueLite v = this.get_child(index);
        assert (v._elementid() == index);
        this.remove_child(index);
        this.patch_elements_helper(index);
        return v;
    }

    @Override
    public boolean remove(Object o2) {
        this.checkForLock();
        int idx = this.lastIndexOf(o2);
        if (idx < 0) {
            return false;
        }
        assert (o2 instanceof IonValueLite);
        assert (((IonValueLite)o2)._elementid() == idx);
        this.remove_child(idx);
        this.patch_elements_helper(idx);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        this.checkForLock();
        for (Object o2 : c) {
            int idx = this.lastIndexOf(o2);
            if (idx < 0) continue;
            assert (o2 == this.get_child(idx));
            this.remove_child(idx);
            this.patch_elements_helper(idx);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        IonValue v;
        this.checkForLock();
        if (this.get_child_count() < 1) {
            return false;
        }
        IdentityHashMap<IonValue, IonValue> keepers = new IdentityHashMap<IonValue, IonValue>();
        for (Object o2 : c) {
            v = (IonValue)o2;
            if (this != v.getContainer()) continue;
            keepers.put(v, v);
        }
        boolean changed = false;
        int ii = this.get_child_count();
        while (ii > 0) {
            if (keepers.containsKey(v = this.get_child(--ii))) continue;
            this.remove(v);
            this.patch_elements_helper(ii);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean contains(Object o2) {
        if (o2 == null) {
            throw new NullPointerException();
        }
        if (!(o2 instanceof IonValue)) {
            throw new ClassCastException();
        }
        return ((IonValue)o2).getContainer() == this;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o2 : c) {
            if (this.contains(o2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int indexOf(Object o2) {
        if (o2 == null) {
            throw new NullPointerException();
        }
        _Private_IonValue v = (_Private_IonValue)o2;
        if (this != v.getContainer()) {
            return -1;
        }
        return v.getElementId();
    }

    @Override
    public int lastIndexOf(Object o2) {
        return this.indexOf(o2);
    }

    private static void checkSublistParameters(int size, int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex is less than zero");
        }
        if (toIndex < fromIndex) {
            throw new IllegalArgumentException("toIndex may not be less than fromIndex");
        }
        if (toIndex > size) {
            throw new IndexOutOfBoundsException("toIndex exceeds size");
        }
    }

    @Override
    public List<IonValue> subList(int fromIndex, int toIndex) {
        IonSequenceLite.checkSublistParameters(this.size(), fromIndex, toIndex);
        return new SubListView(fromIndex, toIndex);
    }

    @Override
    public IonValue[] toArray() {
        if (this.get_child_count() < 1) {
            return EMPTY_VALUE_ARRAY;
        }
        IonValue[] array = new IonValue[this.get_child_count()];
        System.arraycopy(this._children, 0, array, 0, this.get_child_count());
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int size = this.get_child_count();
        if (a.length < size) {
            Class<?> type = a.getClass().getComponentType();
            a = (Object[])Array.newInstance(type, size);
        }
        if (size > 0) {
            System.arraycopy(this._children, 0, a, 0, size);
        }
        if (size < a.length) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public <T extends IonValue> T[] extract(Class<T> type) {
        this.checkForLock();
        if (this.isNullValue()) {
            return null;
        }
        IonValue[] array = (IonValue[])Array.newInstance(type, this.size());
        this.toArray(array);
        this.clear();
        return array;
    }

    private class SubListView
    extends AbstractList<IonValue> {
        private final int fromIndex;
        private int size;

        private SubListView(int fromIndex, int toIndex) {
            this.fromIndex = fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = IonSequenceLite.this.structuralModificationCount;
        }

        @Override
        public int size() {
            this.checkForParentModification();
            return this.size;
        }

        @Override
        public boolean isEmpty() {
            this.checkForParentModification();
            return this.size == 0;
        }

        @Override
        public IonValue get(int index) {
            this.checkForParentModification();
            this.rangeCheck(index);
            return IonSequenceLite.this.get(this.toParentIndex(index));
        }

        @Override
        public IonValue set(int index, IonValue element) {
            this.checkForParentModification();
            this.rangeCheck(index);
            return IonSequenceLite.this.set(this.toParentIndex(index), element);
        }

        @Override
        public boolean contains(Object o2) {
            this.checkForParentModification();
            return super.contains(o2);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            this.checkForParentModification();
            return super.containsAll(collection);
        }

        @Override
        public Object[] toArray() {
            this.checkForParentModification();
            return super.toArray();
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            this.checkForParentModification();
            return super.toArray(ts);
        }

        @Override
        public boolean add(IonValue ionValue2) {
            this.checkForParentModification();
            int parentIndex = this.toParentIndex(this.size);
            if (parentIndex == IonSequenceLite.this.size()) {
                IonSequenceLite.this.add(ionValue2);
            } else {
                IonSequenceLite.this.add(parentIndex, ionValue2);
            }
            this.modCount = IonSequenceLite.this.structuralModificationCount;
            ++this.size;
            return true;
        }

        @Override
        public void add(int index, IonValue ionValue2) {
            this.checkForParentModification();
            this.rangeCheck(index);
            IonSequenceLite.this.add(this.toParentIndex(index), ionValue2);
            this.modCount = IonSequenceLite.this.structuralModificationCount;
            ++this.size;
        }

        @Override
        public boolean addAll(int i, Collection<? extends IonValue> collection) {
            this.checkForParentModification();
            return super.addAll(i, collection);
        }

        @Override
        public boolean addAll(Collection<? extends IonValue> collection) {
            this.checkForParentModification();
            return super.addAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            this.checkForParentModification();
            if (this.size < 1) {
                return false;
            }
            IdentityHashMap toRetain = new IdentityHashMap();
            for (Object o2 : c) {
                toRetain.put(o2, null);
            }
            ArrayList<IonValue> toRemove = new ArrayList<IonValue>(this.size - c.size());
            for (int i = 0; i < this.size; ++i) {
                IonValue ionValue2 = this.get(i);
                if (toRetain.containsKey(ionValue2)) continue;
                toRemove.add(ionValue2);
            }
            return this.removeAll(toRemove);
        }

        @Override
        public void clear() {
            this.checkForParentModification();
            int parentIndex = this.toParentIndex(0);
            for (int i = 0; i < this.size; ++i) {
                IonSequenceLite.this.remove(parentIndex);
            }
            this.size = 0;
            this.modCount = IonSequenceLite.this.structuralModificationCount;
        }

        @Override
        public IonValue remove(int index) {
            this.checkForParentModification();
            this.rangeCheck(index);
            IonValue removed = IonSequenceLite.this.remove(this.toParentIndex(index));
            this.modCount = IonSequenceLite.this.structuralModificationCount;
            --this.size;
            return removed;
        }

        @Override
        public boolean remove(Object o2) {
            this.checkForParentModification();
            int index = this.indexOf(o2);
            if (index < 0) {
                return false;
            }
            this.remove(index);
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            this.checkForParentModification();
            boolean changed = false;
            for (Object o2 : c) {
                if (!this.remove(o2)) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public int indexOf(Object o2) {
            this.checkForParentModification();
            return super.indexOf(o2);
        }

        @Override
        public int lastIndexOf(Object o2) {
            this.checkForParentModification();
            return super.lastIndexOf(o2);
        }

        @Override
        protected void removeRange(int from2, int to) {
            this.checkForParentModification();
            super.removeRange(from2, to);
        }

        @Override
        public Iterator<IonValue> iterator() {
            return this.listIterator(0);
        }

        @Override
        public ListIterator<IonValue> listIterator() {
            return this.listIterator(0);
        }

        @Override
        public ListIterator<IonValue> listIterator(final int index) {
            this.checkForParentModification();
            return new ListIterator<IonValue>(){
                private int lastReturnedIndex;
                private int nextIndex;
                {
                    this.lastReturnedIndex = index;
                    this.nextIndex = index;
                }

                @Override
                public boolean hasNext() {
                    return this.nextIndex < SubListView.this.size();
                }

                @Override
                public IonValue next() {
                    this.lastReturnedIndex = this.nextIndex++;
                    return SubListView.this.get(this.lastReturnedIndex);
                }

                @Override
                public boolean hasPrevious() {
                    return this.nextIndex > 0;
                }

                @Override
                public IonValue previous() {
                    this.lastReturnedIndex = --this.nextIndex;
                    return SubListView.this.get(this.lastReturnedIndex);
                }

                @Override
                public int nextIndex() {
                    return this.nextIndex;
                }

                @Override
                public int previousIndex() {
                    return this.nextIndex - 1;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void set(IonValue ionValue2) {
                    SubListView.this.set(this.lastReturnedIndex, ionValue2);
                }

                @Override
                public void add(IonValue ionValue2) {
                    SubListView.this.add(this.lastReturnedIndex, ionValue2);
                }
            };
        }

        @Override
        public List<IonValue> subList(int fromIndex, int toIndex) {
            IonSequenceLite.checkSublistParameters(this.size(), fromIndex, toIndex);
            this.checkForParentModification();
            return new SubListView(this.toParentIndex(fromIndex), this.toParentIndex(toIndex));
        }

        @Override
        public boolean equals(Object o2) {
            this.checkForParentModification();
            return super.equals(o2);
        }

        @Override
        public int hashCode() {
            this.checkForParentModification();
            return super.hashCode();
        }

        @Override
        public String toString() {
            this.checkForParentModification();
            return super.toString();
        }

        private void rangeCheck(int index) {
            if (index < 0 || index >= this.size) {
                throw new IndexOutOfBoundsException(String.valueOf(index));
            }
        }

        private int toParentIndex(int index) {
            return index + this.fromIndex;
        }

        private void checkForParentModification() {
            if (this.modCount != IonSequenceLite.this.structuralModificationCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}

