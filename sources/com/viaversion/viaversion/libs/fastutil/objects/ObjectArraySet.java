package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectArraySet.class */
public class ObjectArraySet<K> extends AbstractObjectSet<K> implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;

    /* renamed from: a */
    private transient Object[] f145a;
    private int size;

    static /* synthetic */ int access$010(ObjectArraySet x0) {
        int i = x0.size;
        x0.size = i - 1;
        return i;
    }

    public ObjectArraySet(Object[] a) {
        this.f145a = a;
        this.size = a.length;
    }

    public ObjectArraySet() {
        this.f145a = ObjectArrays.EMPTY_ARRAY;
    }

    public ObjectArraySet(int capacity) {
        this.f145a = new Object[capacity];
    }

    public ObjectArraySet(ObjectCollection<K> c) {
        this(c.size());
        addAll(c);
    }

    public ObjectArraySet(Collection<? extends K> c) {
        this(c.size());
        addAll(c);
    }

    public ObjectArraySet(ObjectSet<K> c) {
        this(c.size());
        int i = 0;
        ObjectIterator<K> it = c.iterator();
        while (it.hasNext()) {
            Object x = it.next();
            this.f145a[i] = x;
            i++;
        }
        this.size = i;
    }

    public ObjectArraySet(Set<? extends K> c) {
        this(c.size());
        int i = 0;
        for (K x : c) {
            this.f145a[i] = x;
            i++;
        }
        this.size = i;
    }

    public ObjectArraySet(Object[] a, int size) {
        this.f145a = a;
        this.size = size;
        if (size > a.length) {
            throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")");
        }
    }

    /* renamed from: of */
    public static <K> ObjectArraySet<K> m179of() {
        return ofUnchecked();
    }

    /* renamed from: of */
    public static <K> ObjectArraySet<K> m178of(K e) {
        return ofUnchecked(e);
    }

    @SafeVarargs
    /* renamed from: of */
    public static <K> ObjectArraySet<K> m177of(K... a) {
        if (a.length == 2) {
            if (Objects.equals(a[0], a[1])) {
                throw new IllegalArgumentException("Duplicate element: " + a[1]);
            }
        } else if (a.length > 2) {
            ObjectOpenHashSet.m154of((Object[]) a);
        }
        return ofUnchecked(a);
    }

    public static <K> ObjectArraySet<K> ofUnchecked() {
        return new ObjectArraySet<>();
    }

    @SafeVarargs
    public static <K> ObjectArraySet<K> ofUnchecked(K... a) {
        return new ObjectArraySet<>(a);
    }

    private int findKey(Object o) {
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return -1;
            }
        } while (!Objects.equals(this.f145a[i], o));
        return i;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    public ObjectIterator<K> iterator() {
        return new ObjectIterator<K>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.ObjectArraySet.1
            int next = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.next < ObjectArraySet.this.size;
            }

            @Override // java.util.Iterator
            public K next() {
                if (hasNext()) {
                    Object[] objArr = ObjectArraySet.this.f145a;
                    int i = this.next;
                    this.next = i + 1;
                    return (K) objArr[i];
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                int access$010 = ObjectArraySet.access$010(ObjectArraySet.this);
                int i = this.next;
                this.next = i - 1;
                int tail = access$010 - i;
                System.arraycopy(ObjectArraySet.this.f145a, this.next + 1, ObjectArraySet.this.f145a, this.next, tail);
                ObjectArraySet.this.f145a[ObjectArraySet.this.size] = null;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
            public int skip(int n) {
                if (n >= 0) {
                    int remaining = ObjectArraySet.this.size - this.next;
                    if (n < remaining) {
                        this.next += n;
                        return n;
                    }
                    this.next = ObjectArraySet.this.size;
                    return remaining;
                }
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
        };
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectArraySet$Spliterator.class */
    public final class Spliterator implements ObjectSpliterator<K> {
        boolean hasSplit;
        int pos;
        int max;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !ObjectArraySet.class.desiredAssertionStatus();
        }

        public Spliterator(ObjectArraySet objectArraySet) {
            this(0, objectArraySet.size, false);
        }

        private Spliterator(int pos, int max, boolean hasSplit) {
            ObjectArraySet.this = r6;
            this.hasSplit = false;
            if ($assertionsDisabled || pos <= max) {
                this.pos = pos;
                this.max = max;
                this.hasSplit = hasSplit;
                return;
            }
            throw new AssertionError("pos " + pos + " must be <= max " + max);
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : ObjectArraySet.this.size;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16465;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> action) {
            if (this.pos < getWorkingMax()) {
                Object[] objArr = ObjectArraySet.this.f145a;
                int i = this.pos;
                this.pos = i + 1;
                action.accept(objArr[i]);
                return true;
            }
            return false;
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> action) {
            int max = getWorkingMax();
            while (this.pos < max) {
                action.accept(ObjectArraySet.this.f145a[this.pos]);
                this.pos++;
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator
        public long skip(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            int max = getWorkingMax();
            if (this.pos >= max) {
                return 0L;
            }
            int remaining = max - this.pos;
            if (n < remaining) {
                this.pos = SafeMath.safeLongToInt(this.pos + n);
                return n;
            }
            long n2 = remaining;
            this.pos = max;
            return n2;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
            int max = getWorkingMax();
            int retLen = (max - this.pos) >> 1;
            if (retLen <= 1) {
                return null;
            }
            this.max = max;
            int myNewPos = this.pos + retLen;
            int oldPos = this.pos;
            this.pos = myNewPos;
            this.hasSplit = true;
            return new Spliterator(oldPos, myNewPos, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
    public ObjectSpliterator<K> spliterator() {
        return new Spliterator(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object k) {
        return findKey(k) != -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object k) {
        int pos = findKey(k);
        if (pos == -1) {
            return false;
        }
        int tail = (this.size - pos) - 1;
        for (int i = 0; i < tail; i++) {
            this.f145a[pos + i] = this.f145a[pos + i + 1];
        }
        this.size--;
        this.f145a[this.size] = null;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(K k) {
        int pos = findKey(k);
        if (pos != -1) {
            return false;
        }
        if (this.size == this.f145a.length) {
            Object[] b = new Object[this.size == 0 ? 2 : this.size * 2];
            int i = this.size;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == 0) {
                    break;
                }
                b[i] = this.f145a[i];
            }
            this.f145a = b;
        }
        Object[] objArr = this.f145a;
        int i3 = this.size;
        this.size = i3 + 1;
        objArr[i3] = k;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        Arrays.fill(this.f145a, 0, this.size, (Object) null);
        this.size = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public Object[] toArray() {
        return Arrays.copyOf(this.f145a, this.size, Object[].class);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r0v16, types: [java.lang.Object[]] */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public <K> K[] toArray(K[] a) {
        if (a == null) {
            a = new Object[this.size];
        } else if (a.length < this.size) {
            a = (Object[]) Array.newInstance(a.getClass().getComponentType(), this.size);
        }
        System.arraycopy(this.f145a, 0, a, 0, this.size);
        if (a.length > this.size) {
            a[this.size] = null;
        }
        return a;
    }

    public ObjectArraySet<K> clone() {
        try {
            ObjectArraySet<K> c = (ObjectArraySet) super.clone();
            c.f145a = (Object[]) this.f145a.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            s.writeObject(this.f145a[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.f145a = new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            this.f145a[i] = s.readObject();
        }
    }
}
