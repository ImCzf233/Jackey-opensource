package com.viaversion.viaversion.libs.gson.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/LinkedTreeMap.class */
public final class LinkedTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
    private static final Comparator<Comparable> NATURAL_ORDER;
    Comparator<? super K> comparator;
    Node<K, V> root;
    int size;
    int modCount;
    final Node<K, V> header;
    private LinkedTreeMap<K, V>.EntrySet entrySet;
    private LinkedTreeMap<K, V>.KeySet keySet;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !LinkedTreeMap.class.desiredAssertionStatus();
        NATURAL_ORDER = new Comparator<Comparable>() { // from class: com.viaversion.viaversion.libs.gson.internal.LinkedTreeMap.1
            public int compare(Comparable a, Comparable b) {
                return a.compareTo(b);
            }
        };
    }

    public LinkedTreeMap() {
        this(NATURAL_ORDER);
    }

    public LinkedTreeMap(Comparator<? super K> comparator) {
        Comparator<? super K> comparator2;
        this.size = 0;
        this.modCount = 0;
        this.header = new Node<>();
        if (comparator != null) {
            comparator2 = comparator;
        } else {
            comparator2 = NATURAL_ORDER;
        }
        this.comparator = comparator2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object key) {
        Node<K, V> node = findByObject(key);
        if (node != null) {
            return node.value;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object key) {
        return findByObject(key) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        Node<K, V> created = find(key, true);
        V result = created.value;
        created.value = value;
        return result;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.root = null;
        this.size = 0;
        this.modCount++;
        Node<K, V> header = this.header;
        header.prev = header;
        header.next = header;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object key) {
        Node<K, V> node = removeInternalByKey(key);
        if (node != null) {
            return node.value;
        }
        return null;
    }

    Node<K, V> find(K key, boolean create) {
        Node<K, V> created;
        Comparable<Object> comparable;
        int i;
        Comparator<? super K> comparator = this.comparator;
        Node<K, V> nearest = this.root;
        int comparison = 0;
        if (nearest != null) {
            if (comparator == NATURAL_ORDER) {
                comparable = (Comparable) key;
            } else {
                comparable = null;
            }
            Comparable<Object> comparableKey = comparable;
            while (true) {
                if (comparableKey != null) {
                    i = comparableKey.compareTo(nearest.key);
                } else {
                    i = comparator.compare(key, (K) nearest.key);
                }
                comparison = i;
                if (comparison == 0) {
                    return nearest;
                }
                Node<K, V> child = comparison < 0 ? nearest.left : nearest.right;
                if (child == null) {
                    break;
                }
                nearest = child;
            }
        }
        if (!create) {
            return null;
        }
        Node<K, V> header = this.header;
        if (nearest == null) {
            if (comparator == NATURAL_ORDER && !(key instanceof Comparable)) {
                throw new ClassCastException(key.getClass().getName() + " is not Comparable");
            }
            created = new Node<>(nearest, key, header, header.prev);
            this.root = created;
        } else {
            created = new Node<>(nearest, key, header, header.prev);
            if (comparison < 0) {
                nearest.left = created;
            } else {
                nearest.right = created;
            }
            rebalance(nearest, true);
        }
        this.size++;
        this.modCount++;
        return created;
    }

    /* JADX WARN: Multi-variable type inference failed */
    Node<K, V> findByObject(Object key) {
        if (key != 0) {
            try {
                return find(key, false);
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

    Node<K, V> findByEntry(Map.Entry<?, ?> entry) {
        Node<K, V> mine = findByObject(entry.getKey());
        boolean valuesEqual = mine != null && equal(mine.value, entry.getValue());
        if (valuesEqual) {
            return mine;
        }
        return null;
    }

    private boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    void removeInternal(Node<K, V> node, boolean unlink) {
        if (unlink) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        Node<K, V> left = node.left;
        Node<K, V> right = node.right;
        Node<K, V> originalParent = node.parent;
        if (left != null && right != null) {
            Node<K, V> adjacent = left.height > right.height ? left.last() : right.first();
            removeInternal(adjacent, false);
            int leftHeight = 0;
            Node<K, V> left2 = node.left;
            if (left2 != null) {
                leftHeight = left2.height;
                adjacent.left = left2;
                left2.parent = adjacent;
                node.left = null;
            }
            int rightHeight = 0;
            Node<K, V> right2 = node.right;
            if (right2 != null) {
                rightHeight = right2.height;
                adjacent.right = right2;
                right2.parent = adjacent;
                node.right = null;
            }
            adjacent.height = Math.max(leftHeight, rightHeight) + 1;
            replaceInParent(node, adjacent);
            return;
        }
        if (left != null) {
            replaceInParent(node, left);
            node.left = null;
        } else if (right != null) {
            replaceInParent(node, right);
            node.right = null;
        } else {
            replaceInParent(node, null);
        }
        rebalance(originalParent, false);
        this.size--;
        this.modCount++;
    }

    Node<K, V> removeInternalByKey(Object key) {
        Node<K, V> node = findByObject(key);
        if (node != null) {
            removeInternal(node, true);
        }
        return node;
    }

    private void replaceInParent(Node<K, V> node, Node<K, V> replacement) {
        Node<K, V> parent = node.parent;
        node.parent = null;
        if (replacement != null) {
            replacement.parent = parent;
        }
        if (parent != null) {
            if (parent.left == node) {
                parent.left = replacement;
                return;
            } else if (!$assertionsDisabled && parent.right != node) {
                throw new AssertionError();
            } else {
                parent.right = replacement;
                return;
            }
        }
        this.root = replacement;
    }

    private void rebalance(Node<K, V> unbalanced, boolean insert) {
        Node<K, V> node = unbalanced;
        while (true) {
            Node<K, V> node2 = node;
            if (node2 != null) {
                Node<K, V> left = node2.left;
                Node<K, V> right = node2.right;
                int leftHeight = left != null ? left.height : 0;
                int rightHeight = right != null ? right.height : 0;
                int delta = leftHeight - rightHeight;
                if (delta == -2) {
                    Node<K, V> rightLeft = right.left;
                    Node<K, V> rightRight = right.right;
                    int rightRightHeight = rightRight != null ? rightRight.height : 0;
                    int rightLeftHeight = rightLeft != null ? rightLeft.height : 0;
                    int rightDelta = rightLeftHeight - rightRightHeight;
                    if (rightDelta == -1 || (rightDelta == 0 && !insert)) {
                        rotateLeft(node2);
                    } else if (!$assertionsDisabled && rightDelta != 1) {
                        throw new AssertionError();
                    } else {
                        rotateRight(right);
                        rotateLeft(node2);
                    }
                    if (insert) {
                        return;
                    }
                } else if (delta == 2) {
                    Node<K, V> leftLeft = left.left;
                    Node<K, V> leftRight = left.right;
                    int leftRightHeight = leftRight != null ? leftRight.height : 0;
                    int leftLeftHeight = leftLeft != null ? leftLeft.height : 0;
                    int leftDelta = leftLeftHeight - leftRightHeight;
                    if (leftDelta == 1 || (leftDelta == 0 && !insert)) {
                        rotateRight(node2);
                    } else if (!$assertionsDisabled && leftDelta != -1) {
                        throw new AssertionError();
                    } else {
                        rotateLeft(left);
                        rotateRight(node2);
                    }
                    if (insert) {
                        return;
                    }
                } else if (delta == 0) {
                    node2.height = leftHeight + 1;
                    if (insert) {
                        return;
                    }
                } else if (!$assertionsDisabled && delta != -1 && delta != 1) {
                    throw new AssertionError();
                } else {
                    node2.height = Math.max(leftHeight, rightHeight) + 1;
                    if (!insert) {
                        return;
                    }
                }
                node = node2.parent;
            } else {
                return;
            }
        }
    }

    private void rotateLeft(Node<K, V> root) {
        Node<K, V> left = root.left;
        Node<K, V> pivot = root.right;
        Node<K, V> pivotLeft = pivot.left;
        Node<K, V> pivotRight = pivot.right;
        root.right = pivotLeft;
        if (pivotLeft != null) {
            pivotLeft.parent = root;
        }
        replaceInParent(root, pivot);
        pivot.left = root;
        root.parent = pivot;
        root.height = Math.max(left != null ? left.height : 0, pivotLeft != null ? pivotLeft.height : 0) + 1;
        pivot.height = Math.max(root.height, pivotRight != null ? pivotRight.height : 0) + 1;
    }

    private void rotateRight(Node<K, V> root) {
        Node<K, V> pivot = root.left;
        Node<K, V> right = root.right;
        Node<K, V> pivotLeft = pivot.left;
        Node<K, V> pivotRight = pivot.right;
        root.left = pivotRight;
        if (pivotRight != null) {
            pivotRight.parent = root;
        }
        replaceInParent(root, pivot);
        pivot.right = root;
        root.parent = pivot;
        root.height = Math.max(right != null ? right.height : 0, pivotRight != null ? pivotRight.height : 0) + 1;
        pivot.height = Math.max(root.height, pivotLeft != null ? pivotLeft.height : 0) + 1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        LinkedTreeMap<K, V>.EntrySet result = this.entrySet;
        if (result != null) {
            return result;
        }
        LinkedTreeMap<K, V>.EntrySet entrySet = new EntrySet();
        this.entrySet = entrySet;
        return entrySet;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        LinkedTreeMap<K, V>.KeySet result = this.keySet;
        if (result != null) {
            return result;
        }
        LinkedTreeMap<K, V>.KeySet keySet = new KeySet();
        this.keySet = keySet;
        return keySet;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/LinkedTreeMap$Node.class */
    public static final class Node<K, V> implements Map.Entry<K, V> {
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> next;
        Node<K, V> prev;
        final K key;
        V value;
        int height;

        Node() {
            this.key = null;
            this.prev = this;
            this.next = this;
        }

        Node(Node<K, V> parent, K key, Node<K, V> next, Node<K, V> prev) {
            this.parent = parent;
            this.key = key;
            this.height = 1;
            this.next = next;
            this.prev = prev;
            prev.next = this;
            next.prev = this;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry other = (Map.Entry) o;
                if (this.key != null ? this.key.equals(other.getKey()) : other.getKey() == null) {
                    if (this.value != null ? this.value.equals(other.getValue()) : other.getValue() == null) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
        }

        public String toString() {
            return this.key + "=" + this.value;
        }

        public Node<K, V> first() {
            Node<K, V> node = this;
            Node<K, V> node2 = node.left;
            while (true) {
                Node<K, V> child = node2;
                if (child != null) {
                    node = child;
                    node2 = node.left;
                } else {
                    return node;
                }
            }
        }

        public Node<K, V> last() {
            Node<K, V> node = this;
            Node<K, V> node2 = node.right;
            while (true) {
                Node<K, V> child = node2;
                if (child != null) {
                    node = child;
                    node2 = node.right;
                } else {
                    return node;
                }
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/LinkedTreeMap$LinkedTreeMapIterator.class */
    public abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
        Node<K, V> next;
        Node<K, V> lastReturned = null;
        int expectedModCount;

        LinkedTreeMapIterator() {
            LinkedTreeMap.this = r4;
            this.next = LinkedTreeMap.this.header.next;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.next != LinkedTreeMap.this.header;
        }

        final Node<K, V> nextNode() {
            Node<K, V> e = this.next;
            if (e == LinkedTreeMap.this.header) {
                throw new NoSuchElementException();
            }
            if (LinkedTreeMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.next = e.next;
            this.lastReturned = e;
            return e;
        }

        @Override // java.util.Iterator
        public final void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            LinkedTreeMap.this.removeInternal(this.lastReturned, true);
            this.lastReturned = null;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/LinkedTreeMap$EntrySet.class */
    public class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
            LinkedTreeMap.this = this$0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return LinkedTreeMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<Map.Entry<K, V>>() { // from class: com.viaversion.viaversion.libs.gson.internal.LinkedTreeMap.EntrySet.1
                {
                    EntrySet.this = this;
                    LinkedTreeMap linkedTreeMap = LinkedTreeMap.this;
                }

                @Override // java.util.Iterator
                public Map.Entry<K, V> next() {
                    return nextNode();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            return (o instanceof Map.Entry) && LinkedTreeMap.this.findByEntry((Map.Entry) o) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            Node<K, V> node;
            if (!(o instanceof Map.Entry) || (node = LinkedTreeMap.this.findByEntry((Map.Entry) o)) == null) {
                return false;
            }
            LinkedTreeMap.this.removeInternal(node, true);
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            LinkedTreeMap.this.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/LinkedTreeMap$KeySet.class */
    public final class KeySet extends AbstractSet<K> {
        KeySet() {
            LinkedTreeMap.this = this$0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return LinkedTreeMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<K>() { // from class: com.viaversion.viaversion.libs.gson.internal.LinkedTreeMap.KeySet.1
                {
                    KeySet.this = this;
                    LinkedTreeMap linkedTreeMap = LinkedTreeMap.this;
                }

                @Override // java.util.Iterator
                public K next() {
                    return nextNode().key;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            return LinkedTreeMap.this.containsKey(o);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object key) {
            return LinkedTreeMap.this.removeInternalByKey(key) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            LinkedTreeMap.this.clear();
        }
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }
}
