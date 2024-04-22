/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.ValueFactory;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl._Private_CurriedValueFactory;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContainerLite;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonValueLite;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

final class IonStructLite
extends IonContainerLite
implements IonStruct {
    private static final int HASH_SIGNATURE = IonType.STRUCT.toString().hashCode();
    private Map<String, Integer> _field_map;
    private boolean hasNullFieldName = false;
    public int _field_map_duplicate_count;

    IonStructLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    private IonStructLite(IonStructLite existing, IonContext context) {
        super(existing, context);
        this._field_map = null;
        this._field_map_duplicate_count = existing._field_map_duplicate_count;
        this.hasNullFieldName = existing.hasNullFieldName;
    }

    @Override
    public IonStructLite clone() {
        return (IonStructLite)this.deepClone(false);
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonStructLite(this, context);
    }

    @Override
    protected void transitionToLargeSize(int size) {
        if (this._field_map != null) {
            return;
        }
        this.build_field_map();
    }

    private void build_field_map() {
        int size = this._children == null ? 0 : this._children.length;
        this._field_map = new HashMap<String, Integer>((int)Math.ceil((float)size / 0.75f), 0.75f);
        this._field_map_duplicate_count = 0;
        int count = this.get_child_count();
        for (int ii = 0; ii < count; ++ii) {
            IonValueLite v = this.get_child(ii);
            if (this._field_map.get(v._fieldName) != null) {
                ++this._field_map_duplicate_count;
            }
            this._field_map.put(v._fieldName, ii);
        }
    }

    @Override
    void forceMaterializationOfLazyState() {
        this.fieldMapIsActive(this._child_count);
    }

    private void add_field(String fieldName, int newFieldIdx) {
        Integer idx = this._field_map.get(fieldName);
        if (idx != null) {
            ++this._field_map_duplicate_count;
            if (idx > newFieldIdx) {
                newFieldIdx = idx;
            }
        }
        this._field_map.put(fieldName, newFieldIdx);
    }

    private void remove_field(String fieldName, int lowest_idx, int copies) {
        if (this._field_map == null) {
            return;
        }
        this._field_map.remove(fieldName);
        this._field_map_duplicate_count -= copies - 1;
    }

    private void remove_field_from_field_map(String fieldName, int idx) {
        Integer field_idx = this._field_map.get(fieldName);
        assert (field_idx != null);
        if (field_idx != idx) {
            assert (this._field_map_duplicate_count > 0);
            --this._field_map_duplicate_count;
        } else if (this._field_map_duplicate_count > 0) {
            int ii = this.find_last_duplicate(fieldName, idx);
            if (ii == -1) {
                this._field_map.remove(fieldName);
            } else {
                this._field_map.put(fieldName, ii);
                --this._field_map_duplicate_count;
            }
        } else {
            this._field_map.remove(fieldName);
        }
    }

    private void patch_map_elements_helper(int removed_idx) {
        if (this._field_map == null) {
            return;
        }
        if (removed_idx >= this.get_child_count()) {
            return;
        }
        for (int ii = removed_idx; ii < this.get_child_count(); ++ii) {
            IonValueLite value = this.get_child(ii);
            String field_name = value.getFieldName();
            Integer map_idx = this._field_map.get(field_name);
            if (map_idx == ii) continue;
            this._field_map.put(field_name, ii);
        }
    }

    @Override
    public void dump(PrintWriter out) {
        super.dump(out);
        if (this._field_map == null) {
            return;
        }
        out.println("   dups: " + this._field_map_duplicate_count);
        Iterator<Map.Entry<String, Integer>> it = this._field_map.entrySet().iterator();
        out.print("   map: [");
        boolean first = true;
        while (it.hasNext()) {
            Map.Entry<String, Integer> e = it.next();
            if (!first) {
                out.print(",");
            }
            out.print(e.getKey() + ":" + e.getValue());
            first = false;
        }
        out.println("]");
    }

    @Override
    public String validate() {
        if (this._field_map == null) {
            return null;
        }
        String error = "";
        for (Map.Entry<String, Integer> e : this._field_map.entrySet()) {
            IonValueLite v;
            int idx = e.getValue();
            IonValueLite ionValueLite = v = idx >= 0 && idx < this.get_child_count() ? this.get_child(idx) : null;
            if (v != null && idx == v._elementid() && e.getKey().equals(v.getFieldName())) continue;
            error = error + "map entry [" + e + "] doesn't match list value [" + v + "]\n";
        }
        return error == "" ? null : error;
    }

    private int find_last_duplicate(String fieldName, int existing_idx) {
        int ii = existing_idx;
        while (ii > 0) {
            IonValueLite field;
            if (!fieldName.equals((field = this.get_child(--ii)).getFieldName())) continue;
            return ii;
        }
        assert (this.there_is_only_one(fieldName, existing_idx));
        return -1;
    }

    private boolean there_is_only_one(String fieldName, int existing_idx) {
        int count = 0;
        for (int ii = 0; ii < this.get_child_count(); ++ii) {
            IonValueLite v = this.get_child(ii);
            if (!v.getFieldName().equals(fieldName)) continue;
            ++count;
        }
        return count == 1 || count == 0;
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    public IonStruct cloneAndRemove(String ... fieldNames) {
        return this.doClone(false, fieldNames);
    }

    @Override
    public IonStruct cloneAndRetain(String ... fieldNames) {
        return this.doClone(true, fieldNames);
    }

    private IonStruct doClone(boolean keep, String ... fieldNames) {
        IonStructLite clone;
        if (this.isNullValue()) {
            clone = this.getSystem().newNullStruct();
        } else if (this._children == null) {
            clone = this.getSystem().newEmptyStruct();
        } else {
            HashSet<String> fields = new HashSet<String>(Arrays.asList(fieldNames));
            if (keep && fields.contains(null)) {
                throw new NullPointerException("Can't retain an unknown field name");
            }
            clone = this.getSystem().newEmptyStruct();
            clone._children = new IonValueLite[this._children.length];
            clone._child_count = 0;
            for (int i = 0; i < this._child_count; ++i) {
                IonValueLite value = this._children[i];
                if (fields.contains(value._fieldName) != keep) continue;
                IonValueLite clonedChild = (IonValueLite)value.clone();
                clonedChild._fieldName = value.getFieldName();
                clonedChild._elementid(clone._child_count);
                clonedChild._context = clone;
                if (!clone._isSymbolIdPresent() && clonedChild._isSymbolIdPresent()) {
                    clone.cascadeSIDPresentToContextRoot();
                }
                clone._children[clone._child_count++] = clonedChild;
            }
        }
        if (this._annotations != null && this._annotations.length > 0) {
            clone._annotations = (SymbolToken[])this._annotations.clone();
            clone.checkAnnotationsForSids();
        }
        return clone;
    }

    @Override
    public IonType getType() {
        return IonType.STRUCT;
    }

    @Override
    public boolean containsKey(Object fieldName) {
        String name = (String)fieldName;
        return null != this.get(name);
    }

    @Override
    public boolean containsValue(Object value) {
        IonValue v = (IonValue)value;
        return v.getContainer() == this;
    }

    @Override
    public IonValue get(String fieldName) {
        IonValueLite field;
        int field_idx = this.find_field_helper(fieldName);
        if (field_idx < 0) {
            if (this.hasNullFieldName) {
                throw new UnknownSymbolException("Unable to determine whether the field exists because the struct contains field names with unknown text.");
            }
            field = null;
        } else {
            field = this.get_child(field_idx);
        }
        return field;
    }

    private boolean fieldMapIsActive(int proposedSize) {
        if (this._field_map != null) {
            return true;
        }
        if (proposedSize <= 5) {
            return false;
        }
        if (this._isLocked()) {
            return false;
        }
        this.build_field_map();
        return true;
    }

    private int find_field_helper(String fieldName) {
        IonStructLite.validateFieldName(fieldName);
        if (!this.isNullValue()) {
            if (this.fieldMapIsActive(this._child_count)) {
                Integer idx = this._field_map.get(fieldName);
                if (idx != null) {
                    return idx;
                }
            } else {
                int size = this.get_child_count();
                for (int ii = 0; ii < size; ++ii) {
                    IonValueLite field = this.get_child(ii);
                    if (!fieldName.equals(field.getFieldName())) continue;
                    return ii;
                }
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        super.clear();
        this._field_map = null;
        this._field_map_duplicate_count = 0;
    }

    @Override
    public boolean add(IonValue child2) throws NullPointerException, IllegalArgumentException, ContainedValueException {
        IonValueLite concrete = (IonValueLite)child2;
        this._add(concrete._fieldName, concrete);
        return true;
    }

    @Override
    public ValueFactory add(final String fieldName) {
        return new _Private_CurriedValueFactory(this.getSystem()){

            @Override
            protected void handle(IonValue newValue) {
                IonStructLite.this.add(fieldName, newValue);
            }
        };
    }

    private void _add(String fieldName, IonValueLite child2) {
        this.hasNullFieldName |= fieldName == null;
        this.add(this._child_count, child2);
        if (this.fieldMapIsActive(this._child_count)) {
            this.add_field(fieldName, child2._elementid());
        }
    }

    @Override
    public void add(String fieldName, IonValue value) {
        this.checkForLock();
        this.validateNewChild(value);
        IonStructLite.validateFieldName(fieldName);
        IonValueLite concrete = (IonValueLite)value;
        concrete._fieldName = fieldName;
        this._add(fieldName, concrete);
    }

    @Override
    public void add(SymbolToken fieldName, IonValue child2) {
        String text = fieldName.getText();
        if (text != null) {
            this.add(text, child2);
            return;
        }
        if (fieldName.getSid() < 0) {
            throw new IllegalArgumentException("fieldName has no text or ID");
        }
        this.checkForLock();
        this.validateNewChild(child2);
        IonValueLite concrete = (IonValueLite)child2;
        concrete.setFieldNameSymbol(fieldName);
        this._add(text, concrete);
    }

    @Override
    public ValueFactory put(final String fieldName) {
        return new _Private_CurriedValueFactory(this.getSystem()){

            @Override
            protected void handle(IonValue newValue) {
                IonStructLite.this.put(fieldName, newValue);
            }
        };
    }

    @Override
    public void putAll(Map<? extends String, ? extends IonValue> m) {
        for (Map.Entry<? extends String, ? extends IonValue> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void put(String fieldName, IonValue value) {
        this.checkForLock();
        IonStructLite.validateFieldName(fieldName);
        if (value != null) {
            this.validateNewChild(value);
        }
        int lowestRemovedIndex = this._child_count;
        boolean any_removed = false;
        if (this._field_map_duplicate_count == 0 && this.fieldMapIsActive(this._child_count)) {
            Integer idx = this._field_map.get(fieldName);
            if (idx != null) {
                lowestRemovedIndex = idx;
                this.remove_field_from_field_map(fieldName, lowestRemovedIndex);
                this.remove_child(lowestRemovedIndex);
                any_removed = true;
            }
        } else {
            int copies_removed = 0;
            int ii = this.get_child_count();
            while (ii > 0) {
                IonValueLite child2 = this.get_child(--ii);
                if (!fieldName.equals(child2._fieldName)) continue;
                this.remove_child(ii);
                lowestRemovedIndex = ii;
                ++copies_removed;
                any_removed = true;
            }
            if (any_removed) {
                this.remove_field(fieldName, lowestRemovedIndex, copies_removed);
            }
        }
        if (any_removed) {
            this.patch_map_elements_helper(lowestRemovedIndex);
            this.patch_elements_helper(lowestRemovedIndex);
        }
        if (value != null) {
            this.add(fieldName, value);
        }
    }

    @Override
    void beforeIteratorRemove(IonValueLite value, int idx) {
        if (this._field_map != null) {
            this.remove_field_from_field_map(value.getFieldName(), idx);
        }
    }

    @Override
    void afterIteratorRemove(IonValueLite value, int idx) {
        if (this._field_map != null) {
            this.patch_map_elements_helper(idx);
        }
    }

    @Override
    public IonValue remove(String fieldName) {
        this.checkForLock();
        IonValue field = this.get(fieldName);
        if (field == null) {
            return null;
        }
        int idx = ((IonValueLite)field)._elementid();
        if (this._field_map != null) {
            this.remove_field_from_field_map(fieldName, idx);
        }
        super.remove(field);
        if (this._field_map != null) {
            this.patch_map_elements_helper(idx);
        }
        return field;
    }

    @Override
    public boolean remove(IonValue element) {
        if (element == null) {
            throw new NullPointerException();
        }
        this.checkForLock();
        if (element.getContainer() != this) {
            return false;
        }
        IonValueLite concrete = (IonValueLite)element;
        int idx = concrete._elementid();
        if (this._field_map != null) {
            this.remove_field_from_field_map(concrete.getFieldName(), idx);
        }
        super.remove(concrete);
        if (this._field_map != null) {
            this.patch_map_elements_helper(idx);
        }
        return true;
    }

    @Override
    public boolean removeAll(String ... fieldNames) {
        int size;
        boolean removedAny = false;
        this.checkForLock();
        int ii = size = this.get_child_count();
        while (ii > 0) {
            IonValueLite field;
            if (!IonStructLite.isListedField(field = this.get_child(--ii), fieldNames)) continue;
            field.removeFromContainer();
            removedAny = true;
        }
        return removedAny;
    }

    @Override
    public boolean retainAll(String ... fieldNames) {
        int size;
        this.checkForLock();
        boolean removedAny = false;
        int ii = size = this.get_child_count();
        while (ii > 0) {
            IonValueLite field;
            if (IonStructLite.isListedField(field = this.get_child(--ii), fieldNames)) continue;
            field.removeFromContainer();
            removedAny = true;
        }
        return removedAny;
    }

    private static boolean isListedField(IonValue field, String[] fields) {
        String fieldName = field.getFieldName();
        for (String key : fields) {
            if (!key.equals(fieldName)) continue;
            return true;
        }
        return false;
    }

    private static void validateFieldName(String fieldName) {
        if (fieldName == null) {
            throw new NullPointerException("fieldName is null");
        }
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

