package jdk.nashorn.internal.runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.SwitchPoint;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.LongAdder;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.runtime.options.Options;
import jdk.nashorn.internal.scripts.C1654JO;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/PropertyMap.class */
public class PropertyMap implements Iterable<Object>, Serializable {
    private static final int INITIAL_SOFT_REFERENCE_DERIVATION_LIMIT;
    private static final int NOT_EXTENSIBLE = 1;
    private static final int CONTAINS_ARRAY_KEYS = 2;
    private final int flags;
    private transient PropertyHashMap properties;
    private final int fieldCount;
    private final int fieldMaximum;
    private final int spillLength;
    private final String className;
    private final int softReferenceDerivationLimit;
    private transient SharedPropertyMap sharedProtoMap;
    private transient HashMap<String, SwitchPoint> protoSwitches;
    private transient WeakHashMap<Property, Reference<PropertyMap>> history;
    private transient WeakHashMap<ScriptObject, SoftReference<PropertyMap>> protoHistory;
    private transient PropertyListeners listeners;
    private transient BitSet freeSlots;
    private static final long serialVersionUID = -7041836752008732533L;
    private static LongAdder count;
    private static LongAdder clonedCount;
    private static LongAdder historyHit;
    private static LongAdder protoInvalidations;
    private static LongAdder protoHistoryHit;
    private static LongAdder setProtoNewMapCount;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !PropertyMap.class.desiredAssertionStatus();
        INITIAL_SOFT_REFERENCE_DERIVATION_LIMIT = Math.max(0, Options.getIntProperty("nashorn.propertyMap.softReferenceDerivationLimit", 32));
        if (Context.DEBUG) {
            count = new LongAdder();
            clonedCount = new LongAdder();
            historyHit = new LongAdder();
            protoInvalidations = new LongAdder();
            protoHistoryHit = new LongAdder();
            setProtoNewMapCount = new LongAdder();
        }
    }

    private PropertyMap(PropertyHashMap properties, int flags, String className, int fieldCount, int fieldMaximum, int spillLength) {
        this.properties = properties;
        this.className = className;
        this.fieldCount = fieldCount;
        this.fieldMaximum = fieldMaximum;
        this.spillLength = spillLength;
        this.flags = flags;
        this.softReferenceDerivationLimit = INITIAL_SOFT_REFERENCE_DERIVATION_LIMIT;
        if (Context.DEBUG) {
            count.increment();
        }
    }

    private PropertyMap(PropertyMap propertyMap, PropertyHashMap properties, int flags, int fieldCount, int spillLength, int softReferenceDerivationLimit) {
        this.properties = properties;
        this.flags = flags;
        this.spillLength = spillLength;
        this.fieldCount = fieldCount;
        this.fieldMaximum = propertyMap.fieldMaximum;
        this.className = propertyMap.className;
        this.listeners = propertyMap.listeners;
        this.freeSlots = propertyMap.freeSlots;
        this.sharedProtoMap = propertyMap.sharedProtoMap;
        this.softReferenceDerivationLimit = softReferenceDerivationLimit;
        if (Context.DEBUG) {
            count.increment();
            clonedCount.increment();
        }
    }

    public PropertyMap(PropertyMap propertyMap) {
        this(propertyMap, propertyMap.properties, propertyMap.flags, propertyMap.fieldCount, propertyMap.spillLength, propertyMap.softReferenceDerivationLimit);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(this.properties.getProperties());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        Property[] props = (Property[]) in.readObject();
        this.properties = PropertyHashMap.EMPTY_HASHMAP.immutableAdd(props);
        if ($assertionsDisabled || this.className != null) {
            Class<?> structure = Context.forStructureClass(this.className);
            for (Property prop : props) {
                prop.initMethodHandles(structure);
            }
            return;
        }
        throw new AssertionError();
    }

    public static PropertyMap newMap(Collection<Property> properties, String className, int fieldCount, int fieldMaximum, int spillLength) {
        PropertyHashMap newProperties = PropertyHashMap.EMPTY_HASHMAP.immutableAdd(properties);
        return new PropertyMap(newProperties, 0, className, fieldCount, fieldMaximum, spillLength);
    }

    public static PropertyMap newMap(Collection<Property> properties) {
        return (properties == null || properties.isEmpty()) ? newMap() : newMap(properties, C1654JO.class.getName(), 0, 0, 0);
    }

    public static PropertyMap newMap(Class<? extends ScriptObject> clazz) {
        return new PropertyMap(PropertyHashMap.EMPTY_HASHMAP, 0, clazz.getName(), 0, 0, 0);
    }

    public static PropertyMap newMap() {
        return newMap(C1654JO.class);
    }

    public int size() {
        return this.properties.size();
    }

    public int getListenerCount() {
        if (this.listeners == null) {
            return 0;
        }
        return this.listeners.getListenerCount();
    }

    public void addListener(String key, PropertyMap listenerMap) {
        if (listenerMap != this) {
            this.listeners = PropertyListeners.addListener(this.listeners, key, listenerMap);
        }
    }

    public void propertyAdded(Property property, boolean isSelf) {
        if (!isSelf) {
            invalidateProtoSwitchPoint(property.getKey());
        }
        if (this.listeners != null) {
            this.listeners.propertyAdded(property);
        }
    }

    public void propertyDeleted(Property property, boolean isSelf) {
        if (!isSelf) {
            invalidateProtoSwitchPoint(property.getKey());
        }
        if (this.listeners != null) {
            this.listeners.propertyDeleted(property);
        }
    }

    public void propertyModified(Property oldProperty, Property newProperty, boolean isSelf) {
        if (!isSelf) {
            invalidateProtoSwitchPoint(oldProperty.getKey());
        }
        if (this.listeners != null) {
            this.listeners.propertyModified(oldProperty, newProperty);
        }
    }

    public void protoChanged(boolean isSelf) {
        if (!isSelf) {
            invalidateAllProtoSwitchPoints();
        } else if (this.sharedProtoMap != null) {
            this.sharedProtoMap.invalidateSwitchPoint();
        }
        if (this.listeners != null) {
            this.listeners.protoChanged();
        }
    }

    public synchronized SwitchPoint getSwitchPoint(String key) {
        if (this.protoSwitches == null) {
            this.protoSwitches = new HashMap<>();
        }
        SwitchPoint switchPoint = this.protoSwitches.get(key);
        if (switchPoint == null) {
            switchPoint = new SwitchPoint();
            this.protoSwitches.put(key, switchPoint);
        }
        return switchPoint;
    }

    synchronized void invalidateProtoSwitchPoint(String key) {
        SwitchPoint sp;
        if (this.protoSwitches != null && (sp = this.protoSwitches.get(key)) != null) {
            this.protoSwitches.remove(key);
            if (Context.DEBUG) {
                protoInvalidations.increment();
            }
            SwitchPoint.invalidateAll(new SwitchPoint[]{sp});
        }
    }

    synchronized void invalidateAllProtoSwitchPoints() {
        int size;
        if (this.protoSwitches != null && (size = this.protoSwitches.size()) > 0) {
            if (Context.DEBUG) {
                protoInvalidations.add(size);
            }
            SwitchPoint.invalidateAll((SwitchPoint[]) this.protoSwitches.values().toArray(new SwitchPoint[size]));
            this.protoSwitches.clear();
        }
    }

    public PropertyMap addPropertyBind(AccessorProperty property, Object bindTo) {
        return addPropertyNoHistory(new AccessorProperty(property, bindTo));
    }

    private int logicalSlotIndex(Property property) {
        int slot = property.getSlot();
        if (slot < 0) {
            return -1;
        }
        return property.isSpill() ? slot + this.fieldMaximum : slot;
    }

    private int newSpillLength(Property newProperty) {
        return newProperty.isSpill() ? Math.max(this.spillLength, newProperty.getSlot() + 1) : this.spillLength;
    }

    private int newFieldCount(Property newProperty) {
        return !newProperty.isSpill() ? Math.max(this.fieldCount, newProperty.getSlot() + 1) : this.fieldCount;
    }

    private int newFlags(Property newProperty) {
        return ArrayIndex.isValidArrayIndex(ArrayIndex.getArrayIndex(newProperty.getKey())) ? this.flags | 2 : this.flags;
    }

    private void updateFreeSlots(Property oldProperty, Property newProperty) {
        int slotIndex;
        int slotIndex2;
        boolean freeSlotsCloned = false;
        if (oldProperty != null && (slotIndex2 = logicalSlotIndex(oldProperty)) >= 0) {
            BitSet newFreeSlots = this.freeSlots == null ? new BitSet() : (BitSet) this.freeSlots.clone();
            if (!$assertionsDisabled && newFreeSlots.get(slotIndex2)) {
                throw new AssertionError();
            }
            newFreeSlots.set(slotIndex2);
            this.freeSlots = newFreeSlots;
            freeSlotsCloned = true;
        }
        if (this.freeSlots != null && newProperty != null && (slotIndex = logicalSlotIndex(newProperty)) > -1 && this.freeSlots.get(slotIndex)) {
            BitSet newFreeSlots2 = freeSlotsCloned ? this.freeSlots : (BitSet) this.freeSlots.clone();
            newFreeSlots2.clear(slotIndex);
            this.freeSlots = newFreeSlots2.isEmpty() ? null : newFreeSlots2;
        }
    }

    public final PropertyMap addPropertyNoHistory(Property property) {
        propertyAdded(property, true);
        return addPropertyInternal(property);
    }

    public final synchronized PropertyMap addProperty(Property property) {
        propertyAdded(property, true);
        PropertyMap newMap = checkHistory(property);
        if (newMap == null) {
            newMap = addPropertyInternal(property);
            addToHistory(property, newMap);
        }
        return newMap;
    }

    private PropertyMap deriveMap(PropertyHashMap newProperties, int newFlags, int newFieldCount, int newSpillLength) {
        return new PropertyMap(this, newProperties, newFlags, newFieldCount, newSpillLength, this.softReferenceDerivationLimit == 0 ? 0 : this.softReferenceDerivationLimit - 1);
    }

    private PropertyMap addPropertyInternal(Property property) {
        PropertyHashMap newProperties = this.properties.immutableAdd(property);
        PropertyMap newMap = deriveMap(newProperties, newFlags(property), newFieldCount(property), newSpillLength(property));
        newMap.updateFreeSlots(null, property);
        return newMap;
    }

    public final synchronized PropertyMap deleteProperty(Property property) {
        propertyDeleted(property, true);
        PropertyMap newMap = checkHistory(property);
        String key = property.getKey();
        if (newMap == null && this.properties.containsKey(key)) {
            PropertyHashMap newProperties = this.properties.immutableRemove(key);
            boolean isSpill = property.isSpill();
            int slot = property.getSlot();
            if (isSpill && slot >= 0 && slot == this.spillLength - 1) {
                newMap = deriveMap(newProperties, this.flags, this.fieldCount, this.spillLength - 1);
                newMap.freeSlots = this.freeSlots;
            } else if (!isSpill && slot >= 0 && slot == this.fieldCount - 1) {
                newMap = deriveMap(newProperties, this.flags, this.fieldCount - 1, this.spillLength);
                newMap.freeSlots = this.freeSlots;
            } else {
                newMap = deriveMap(newProperties, this.flags, this.fieldCount, this.spillLength);
                newMap.updateFreeSlots(property, null);
            }
            addToHistory(property, newMap);
        }
        return newMap;
    }

    public final PropertyMap replaceProperty(Property oldProperty, Property newProperty) {
        propertyModified(oldProperty, newProperty, true);
        boolean sameType = oldProperty.getClass() == newProperty.getClass();
        if (!$assertionsDisabled && !sameType && (!(oldProperty instanceof AccessorProperty) || !(newProperty instanceof UserAccessorProperty))) {
            throw new AssertionError("arbitrary replaceProperty attempted " + sameType + " oldProperty=" + oldProperty.getClass() + " newProperty=" + newProperty.getClass() + " [" + oldProperty.getLocalType() + " => " + newProperty.getLocalType() + "]");
        }
        int newSpillLength = sameType ? this.spillLength : Math.max(this.spillLength, newProperty.getSlot() + 1);
        PropertyHashMap newProperties = this.properties.immutableReplace(oldProperty, newProperty);
        PropertyMap newMap = deriveMap(newProperties, this.flags, this.fieldCount, newSpillLength);
        if (!sameType) {
            newMap.updateFreeSlots(oldProperty, newProperty);
        }
        return newMap;
    }

    public final UserAccessorProperty newUserAccessors(String key, int propertyFlags) {
        return new UserAccessorProperty(key, propertyFlags, getFreeSpillSlot());
    }

    public final Property findProperty(String key) {
        return this.properties.find(key);
    }

    public final PropertyMap addAll(PropertyMap other) {
        if ($assertionsDisabled || this != other) {
            Property[] otherProperties = other.properties.getProperties();
            PropertyHashMap newProperties = this.properties.immutableAdd(otherProperties);
            PropertyMap newMap = deriveMap(newProperties, this.flags, this.fieldCount, this.spillLength);
            for (Property property : otherProperties) {
                if (!$assertionsDisabled && property.getSlot() != -1) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && ArrayIndex.isValidArrayIndex(ArrayIndex.getArrayIndex(property.getKey()))) {
                    throw new AssertionError();
                }
            }
            return newMap;
        }
        throw new AssertionError("adding property map to itself");
    }

    public final Property[] getProperties() {
        return this.properties.getProperties();
    }

    public final String getClassName() {
        return this.className;
    }

    public PropertyMap preventExtensions() {
        return deriveMap(this.properties, this.flags | 1, this.fieldCount, this.spillLength);
    }

    public PropertyMap seal() {
        Property[] properties;
        PropertyHashMap newProperties = PropertyHashMap.EMPTY_HASHMAP;
        for (Property oldProperty : this.properties.getProperties()) {
            newProperties = newProperties.immutableAdd(oldProperty.addFlags(4));
        }
        return deriveMap(newProperties, this.flags | 1, this.fieldCount, this.spillLength);
    }

    public PropertyMap freeze() {
        Property[] properties;
        PropertyHashMap newProperties = PropertyHashMap.EMPTY_HASHMAP;
        for (Property oldProperty : this.properties.getProperties()) {
            int propertyFlags = 4;
            if (!(oldProperty instanceof UserAccessorProperty)) {
                propertyFlags = 4 | 1;
            }
            newProperties = newProperties.immutableAdd(oldProperty.addFlags(propertyFlags));
        }
        return deriveMap(newProperties, this.flags | 1, this.fieldCount, this.spillLength);
    }

    private boolean anyConfigurable() {
        Property[] properties;
        for (Property property : this.properties.getProperties()) {
            if (property.isConfigurable()) {
                return true;
            }
        }
        return false;
    }

    private boolean allFrozen() {
        Property[] properties;
        for (Property property : this.properties.getProperties()) {
            if ((!(property instanceof UserAccessorProperty) && property.isWritable()) || property.isConfigurable()) {
                return false;
            }
        }
        return true;
    }

    private PropertyMap checkProtoHistory(ScriptObject proto) {
        PropertyMap cachedMap;
        if (this.protoHistory != null) {
            SoftReference<PropertyMap> weakMap = this.protoHistory.get(proto);
            cachedMap = weakMap != null ? weakMap.get() : null;
        } else {
            cachedMap = null;
        }
        if (Context.DEBUG && cachedMap != null) {
            protoHistoryHit.increment();
        }
        return cachedMap;
    }

    private void addToProtoHistory(ScriptObject newProto, PropertyMap newMap) {
        if (this.protoHistory == null) {
            this.protoHistory = new WeakHashMap<>();
        }
        this.protoHistory.put(newProto, new SoftReference<>(newMap));
    }

    private void addToHistory(Property property, PropertyMap newMap) {
        if (this.history == null) {
            this.history = new WeakHashMap<>();
        }
        this.history.put(property, this.softReferenceDerivationLimit == 0 ? new WeakReference<>(newMap) : new SoftReference<>(newMap));
    }

    private PropertyMap checkHistory(Property property) {
        if (this.history != null) {
            Reference<PropertyMap> ref = this.history.get(property);
            PropertyMap historicMap = ref == null ? null : ref.get();
            if (historicMap != null) {
                if (Context.DEBUG) {
                    historyHit.increment();
                }
                return historicMap;
            }
            return null;
        }
        return null;
    }

    public boolean equalsWithoutType(PropertyMap otherMap) {
        if (this.properties.size() != otherMap.properties.size()) {
            return false;
        }
        Iterator<Property> iter = this.properties.values().iterator();
        Iterator<Property> otherIter = otherMap.properties.values().iterator();
        while (iter.hasNext() && otherIter.hasNext()) {
            if (!iter.next().equalsWithoutType(otherIter.next())) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        Property[] properties;
        StringBuilder sb = new StringBuilder();
        sb.append(Debug.m67id(this));
        sb.append(" = {\n");
        for (Property property : getProperties()) {
            sb.append('\t');
            sb.append(property);
            sb.append('\n');
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.lang.Iterable
    public Iterator<Object> iterator() {
        return new PropertyMapIterator(this);
    }

    public final boolean containsArrayKeys() {
        return (this.flags & 2) != 0;
    }

    public boolean isExtensible() {
        return (this.flags & 1) == 0;
    }

    public boolean isSealed() {
        return !isExtensible() && !anyConfigurable();
    }

    public boolean isFrozen() {
        return !isExtensible() && allFrozen();
    }

    public int getFreeFieldSlot() {
        int freeSlot;
        if (this.freeSlots != null && (freeSlot = this.freeSlots.nextSetBit(0)) > -1 && freeSlot < this.fieldMaximum) {
            return freeSlot;
        }
        if (this.fieldCount < this.fieldMaximum) {
            return this.fieldCount;
        }
        return -1;
    }

    public int getFreeSpillSlot() {
        int freeSlot;
        if (this.freeSlots != null && (freeSlot = this.freeSlots.nextSetBit(this.fieldMaximum)) > -1) {
            return freeSlot - this.fieldMaximum;
        }
        return this.spillLength;
    }

    public synchronized PropertyMap changeProto(ScriptObject newProto) {
        PropertyMap nextMap = checkProtoHistory(newProto);
        if (nextMap != null) {
            return nextMap;
        }
        if (Context.DEBUG) {
            setProtoNewMapCount.increment();
        }
        PropertyMap newMap = makeUnsharedCopy();
        addToProtoHistory(newProto, newMap);
        return newMap;
    }

    public PropertyMap makeUnsharedCopy() {
        PropertyMap newMap = new PropertyMap(this);
        newMap.sharedProtoMap = null;
        return newMap;
    }

    public void setSharedProtoMap(SharedPropertyMap protoMap) {
        this.sharedProtoMap = protoMap;
    }

    public PropertyMap getSharedProtoMap() {
        return this.sharedProtoMap;
    }

    public boolean isValidSharedProtoMap() {
        return false;
    }

    public SwitchPoint getSharedProtoSwitchPoint() {
        return null;
    }

    public boolean isInvalidSharedMapFor(ScriptObject prototype) {
        return this.sharedProtoMap != null && (!this.sharedProtoMap.isValidSharedProtoMap() || prototype == null || this.sharedProtoMap != prototype.getMap());
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/PropertyMap$PropertyMapIterator.class */
    private static class PropertyMapIterator implements Iterator<Object> {
        final Iterator<Property> iter;
        Property property;

        PropertyMapIterator(PropertyMap propertyMap) {
            this.iter = Arrays.asList(propertyMap.properties.getProperties()).iterator();
            this.property = this.iter.hasNext() ? this.iter.next() : null;
            skipNotEnumerable();
        }

        private void skipNotEnumerable() {
            while (this.property != null && !this.property.isEnumerable()) {
                this.property = this.iter.hasNext() ? this.iter.next() : null;
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.property != null;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (this.property == null) {
                throw new NoSuchElementException();
            }
            Object key = this.property.getKey();
            this.property = this.iter.next();
            skipNotEnumerable();
            return key;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static String diff(PropertyMap map0, PropertyMap map1) {
        Property[] properties;
        Property[] properties2;
        StringBuilder sb = new StringBuilder();
        if (map0 != map1) {
            sb.append(">>> START: Map diff");
            boolean found = false;
            for (Property p : map0.getProperties()) {
                Property p2 = map1.findProperty(p.getKey());
                if (p2 == null) {
                    sb.append("FIRST ONLY : [").append(p).append("]");
                    found = true;
                } else if (p2 != p) {
                    sb.append("DIFFERENT  : [").append(p).append("] != [").append(p2).append("]");
                    found = true;
                }
            }
            for (Property p22 : map1.getProperties()) {
                Property p1 = map0.findProperty(p22.getKey());
                if (p1 == null) {
                    sb.append("SECOND ONLY: [").append(p22).append("]");
                    found = true;
                }
            }
            if (!found) {
                sb.append(map0).append("!=").append(map1);
            }
            sb.append("<<< END: Map diff\n");
        }
        return sb.toString();
    }

    public static long getCount() {
        return count.longValue();
    }

    public static long getClonedCount() {
        return clonedCount.longValue();
    }

    public static long getHistoryHit() {
        return historyHit.longValue();
    }

    public static long getProtoInvalidations() {
        return protoInvalidations.longValue();
    }

    public static long getProtoHistoryHit() {
        return protoHistoryHit.longValue();
    }

    public static long getSetProtoNewMapCount() {
        return setProtoNewMapCount.longValue();
    }
}
