package jdk.nashorn.internal.runtime;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/PropertyHashMap.class */
public final class PropertyHashMap implements Map<String, Property> {
    private static final int INITIAL_BINS = 32;
    private static final int LIST_THRESHOLD = 8;
    public static final PropertyHashMap EMPTY_HASHMAP;
    private final int size;
    private final int threshold;
    private final Element list;
    private final Element[] bins;
    private Property[] properties;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !PropertyHashMap.class.desiredAssertionStatus();
        EMPTY_HASHMAP = new PropertyHashMap();
    }

    private PropertyHashMap() {
        this.size = 0;
        this.threshold = 0;
        this.bins = null;
        this.list = null;
    }

    private PropertyHashMap(PropertyHashMap map) {
        this.size = map.size;
        this.threshold = map.threshold;
        this.bins = map.bins;
        this.list = map.list;
    }

    private PropertyHashMap(int size, Element[] bins, Element list) {
        this.size = size;
        this.threshold = bins != null ? threeQuarters(bins.length) : 0;
        this.bins = bins;
        this.list = list;
    }

    public PropertyHashMap immutableReplace(Property property, Property newProperty) {
        if ($assertionsDisabled || property.getKey().equals(newProperty.getKey())) {
            if (!$assertionsDisabled && findElement(property.getKey()) == null) {
                throw new AssertionError("replacing property that doesn't exist in map: '" + property.getKey() + "'");
            }
            return cloneMap().replaceNoClone(property.getKey(), newProperty);
        }
        throw new AssertionError("replacing properties with different keys: '" + property.getKey() + "' != '" + newProperty.getKey() + "'");
    }

    public PropertyHashMap immutableAdd(Property property) {
        int newSize = this.size + 1;
        PropertyHashMap newMap = cloneMap(newSize);
        return newMap.addNoClone(property);
    }

    public PropertyHashMap immutableAdd(Property... newProperties) {
        int newSize = this.size + newProperties.length;
        PropertyHashMap newMap = cloneMap(newSize);
        for (Property property : newProperties) {
            newMap = newMap.addNoClone(property);
        }
        return newMap;
    }

    public PropertyHashMap immutableAdd(Collection<Property> newProperties) {
        if (newProperties != null) {
            int newSize = this.size + newProperties.size();
            PropertyHashMap newMap = cloneMap(newSize);
            for (Property property : newProperties) {
                newMap = newMap.addNoClone(property);
            }
            return newMap;
        }
        return this;
    }

    public PropertyHashMap immutableRemove(Property property) {
        return immutableRemove(property.getKey());
    }

    public PropertyHashMap immutableRemove(String key) {
        if (this.bins != null) {
            int binIndex = binIndex(this.bins, key);
            Element bin = this.bins[binIndex];
            if (findElement(bin, key) != null) {
                int newSize = this.size - 1;
                Element[] newBins = null;
                if (newSize >= 8) {
                    newBins = (Element[]) this.bins.clone();
                    newBins[binIndex] = removeFromList(bin, key);
                }
                Element newList = removeFromList(this.list, key);
                return new PropertyHashMap(newSize, newBins, newList);
            }
        } else if (findElement(this.list, key) != null) {
            int newSize2 = this.size - 1;
            return newSize2 != 0 ? new PropertyHashMap(newSize2, null, removeFromList(this.list, key)) : EMPTY_HASHMAP;
        }
        return this;
    }

    public Property find(String key) {
        Element element = findElement(key);
        if (element != null) {
            return element.getProperty();
        }
        return null;
    }

    public Property[] getProperties() {
        if (this.properties == null) {
            Property[] array = new Property[this.size];
            int i = this.size;
            Element element = this.list;
            while (true) {
                Element element2 = element;
                if (element2 == null) {
                    break;
                }
                i--;
                array[i] = element2.getProperty();
                element = element2.getLink();
            }
            this.properties = array;
        }
        return this.properties;
    }

    private static int binIndex(Element[] bins, String key) {
        return key.hashCode() & (bins.length - 1);
    }

    private static int binsNeeded(int n) {
        return 1 << (32 - Integer.numberOfLeadingZeros((n + (n >>> 1)) | 31));
    }

    private static int threeQuarters(int n) {
        return (n >>> 1) + (n >>> 2);
    }

    private static Element[] rehash(Element list, int binSize) {
        Element[] newBins = new Element[binSize];
        Element element = list;
        while (true) {
            Element element2 = element;
            if (element2 != null) {
                Property property = element2.getProperty();
                String key = property.getKey();
                int binIndex = binIndex(newBins, key);
                newBins[binIndex] = new Element(newBins[binIndex], property);
                element = element2.getLink();
            } else {
                return newBins;
            }
        }
    }

    private Element findElement(String key) {
        if (this.bins != null) {
            int binIndex = binIndex(this.bins, key);
            return findElement(this.bins[binIndex], key);
        }
        return findElement(this.list, key);
    }

    private static Element findElement(Element elementList, String key) {
        int hashCode = key.hashCode();
        Element element = elementList;
        while (true) {
            Element element2 = element;
            if (element2 != null) {
                if (!element2.match(key, hashCode)) {
                    element = element2.getLink();
                } else {
                    return element2;
                }
            } else {
                return null;
            }
        }
    }

    private PropertyHashMap cloneMap() {
        return new PropertyHashMap(this.size, this.bins == null ? null : (Element[]) this.bins.clone(), this.list);
    }

    private PropertyHashMap cloneMap(int newSize) {
        Element[] newBins;
        if (this.bins == null && newSize <= 8) {
            newBins = null;
        } else if (newSize > this.threshold) {
            newBins = rehash(this.list, binsNeeded(newSize));
        } else {
            newBins = (Element[]) this.bins.clone();
        }
        return new PropertyHashMap(newSize, newBins, this.list);
    }

    private PropertyHashMap addNoClone(Property property) {
        int newSize = this.size;
        String key = property.getKey();
        Element newList = this.list;
        if (this.bins != null) {
            int binIndex = binIndex(this.bins, key);
            Element bin = this.bins[binIndex];
            if (findElement(bin, key) != null) {
                newSize--;
                bin = removeFromList(bin, key);
                newList = removeFromList(this.list, key);
            }
            this.bins[binIndex] = new Element(bin, property);
        } else if (findElement(this.list, key) != null) {
            newSize--;
            newList = removeFromList(this.list, key);
        }
        return new PropertyHashMap(newSize, this.bins, new Element(newList, property));
    }

    private PropertyHashMap replaceNoClone(String key, Property property) {
        if (this.bins != null) {
            int binIndex = binIndex(this.bins, key);
            Element bin = this.bins[binIndex];
            this.bins[binIndex] = replaceInList(bin, key, property);
        }
        Element newList = this.list;
        return new PropertyHashMap(this.size, this.bins, replaceInList(newList, key, property));
    }

    private static Element removeFromList(Element list, String key) {
        if (list == null) {
            return null;
        }
        int hashCode = key.hashCode();
        if (list.match(key, hashCode)) {
            return list.getLink();
        }
        Element head = new Element(null, list.getProperty());
        Element previous = head;
        Element link = list.getLink();
        while (true) {
            Element element = link;
            if (element != null) {
                if (element.match(key, hashCode)) {
                    previous.setLink(element.getLink());
                    return head;
                }
                Element next = new Element(null, element.getProperty());
                previous.setLink(next);
                previous = next;
                link = element.getLink();
            } else {
                return list;
            }
        }
    }

    private static Element replaceInList(Element list, String key, Property property) {
        if ($assertionsDisabled || list != null) {
            int hashCode = key.hashCode();
            if (list.match(key, hashCode)) {
                return new Element(list.getLink(), property);
            }
            Element head = new Element(null, list.getProperty());
            Element previous = head;
            Element link = list.getLink();
            while (true) {
                Element element = link;
                if (element != null) {
                    if (element.match(key, hashCode)) {
                        previous.setLink(new Element(element.getLink(), property));
                        return head;
                    }
                    Element next = new Element(null, element.getProperty());
                    previous.setLink(next);
                    previous = next;
                    link = element.getLink();
                } else {
                    return list;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    @Override // java.util.Map
    public int size() {
        return this.size;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // java.util.Map
    public boolean containsKey(Object key) {
        if (key instanceof String) {
            return findElement((String) key) != null;
        } else if (!$assertionsDisabled && !(key instanceof String)) {
            throw new AssertionError();
        } else {
            return false;
        }
    }

    public boolean containsKey(String key) {
        return findElement(key) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(Object value) {
        if (value instanceof Property) {
            Property property = (Property) value;
            Element element = findElement(property.getKey());
            return element != null && element.getProperty().equals(value);
        }
        return false;
    }

    @Override // java.util.Map
    public Property get(Object key) {
        if (key instanceof String) {
            Element element = findElement((String) key);
            if (element == null) {
                return null;
            }
            return element.getProperty();
        } else if (!$assertionsDisabled && !(key instanceof String)) {
            throw new AssertionError();
        } else {
            return null;
        }
    }

    public Property get(String key) {
        Element element = findElement(key);
        if (element != null) {
            return element.getProperty();
        }
        return null;
    }

    public Property put(String key, Property value) {
        throw new UnsupportedOperationException("Immutable map.");
    }

    @Override // java.util.Map
    public Property remove(Object key) {
        throw new UnsupportedOperationException("Immutable map.");
    }

    @Override // java.util.Map
    public void putAll(Map<? extends String, ? extends Property> m) {
        throw new UnsupportedOperationException("Immutable map.");
    }

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException("Immutable map.");
    }

    @Override // java.util.Map
    public Set<String> keySet() {
        HashSet<String> set = new HashSet<>();
        Element element = this.list;
        while (true) {
            Element element2 = element;
            if (element2 != null) {
                set.add(element2.getKey());
                element = element2.getLink();
            } else {
                return Collections.unmodifiableSet(set);
            }
        }
    }

    @Override // java.util.Map
    public Collection<Property> values() {
        return Collections.unmodifiableList(Arrays.asList(getProperties()));
    }

    @Override // java.util.Map
    public Set<Map.Entry<String, Property>> entrySet() {
        HashSet<Map.Entry<String, Property>> set = new HashSet<>();
        Element element = this.list;
        while (true) {
            Element element2 = element;
            if (element2 != null) {
                set.add(element2);
                element = element2.getLink();
            } else {
                return Collections.unmodifiableSet(set);
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/PropertyHashMap$Element.class */
    public static final class Element implements Map.Entry<String, Property> {
        private Element link;
        private final Property property;
        private final String key;
        private final int hashCode;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !PropertyHashMap.class.desiredAssertionStatus();
        }

        Element(Element link, Property property) {
            this.link = link;
            this.property = property;
            this.key = property.getKey();
            this.hashCode = this.key.hashCode();
        }

        boolean match(String otherKey, int otherHashCode) {
            return this.hashCode == otherHashCode && this.key.equals(otherKey);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object other) {
            if ($assertionsDisabled || !(this.property == null || other == null)) {
                return (other instanceof Element) && this.property.equals(((Element) other).property);
            }
            throw new AssertionError();
        }

        @Override // java.util.Map.Entry
        public String getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public Property getValue() {
            return this.property;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.hashCode;
        }

        public Property setValue(Property value) {
            throw new UnsupportedOperationException("Immutable map.");
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append('[');
            Element elem = this;
            do {
                sb.append(elem.getValue());
                elem = elem.link;
                if (elem != null) {
                    sb.append(" -> ");
                }
            } while (elem != null);
            sb.append(']');
            return sb.toString();
        }

        Element getLink() {
            return this.link;
        }

        void setLink(Element link) {
            this.link = link;
        }

        Property getProperty() {
            return this.property;
        }
    }
}
