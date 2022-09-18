package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/References.class */
final class References {
    private References() {
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/References$InternalReference.class */
    interface InternalReference<E> {
        E get();

        Object getKeyReference();

        default boolean referenceEquals(Object object) {
            if (object == this) {
                return true;
            }
            if (object instanceof InternalReference) {
                InternalReference<?> referent = (InternalReference) object;
                return get() == referent.get();
            }
            return false;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/References$LookupKeyReference.class */
    static final class LookupKeyReference<E> implements InternalReference<E> {
        private final int hashCode;

        /* renamed from: e */
        private final E f4e;

        public LookupKeyReference(E e) {
            this.hashCode = System.identityHashCode(e);
            this.f4e = (E) Objects.requireNonNull(e);
        }

        @Override // com.github.benmanes.caffeine.cache.References.InternalReference
        public E get() {
            return this.f4e;
        }

        @Override // com.github.benmanes.caffeine.cache.References.InternalReference
        public Object getKeyReference() {
            return this;
        }

        public boolean equals(Object object) {
            return referenceEquals(object);
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/References$WeakKeyReference.class */
    static class WeakKeyReference<K> extends WeakReference<K> implements InternalReference<K> {
        private final int hashCode;

        public WeakKeyReference(K key, ReferenceQueue<K> queue) {
            super(key, queue);
            this.hashCode = System.identityHashCode(key);
        }

        @Override // com.github.benmanes.caffeine.cache.References.InternalReference
        public Object getKeyReference() {
            return this;
        }

        public boolean equals(Object object) {
            return referenceEquals(object);
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/References$WeakValueReference.class */
    static final class WeakValueReference<V> extends WeakReference<V> implements InternalReference<V> {
        private final Object keyReference;

        public WeakValueReference(Object keyReference, V value, ReferenceQueue<V> queue) {
            super(value, queue);
            this.keyReference = keyReference;
        }

        @Override // com.github.benmanes.caffeine.cache.References.InternalReference
        public Object getKeyReference() {
            return this.keyReference;
        }

        public boolean equals(Object object) {
            return referenceEquals(object);
        }

        public int hashCode() {
            return super.hashCode();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/References$SoftValueReference.class */
    static final class SoftValueReference<V> extends SoftReference<V> implements InternalReference<V> {
        private final Object keyReference;

        public SoftValueReference(Object keyReference, V value, ReferenceQueue<V> queue) {
            super(value, queue);
            this.keyReference = keyReference;
        }

        @Override // com.github.benmanes.caffeine.cache.References.InternalReference
        public Object getKeyReference() {
            return this.keyReference;
        }

        public boolean equals(Object object) {
            return referenceEquals(object);
        }

        public int hashCode() {
            return super.hashCode();
        }
    }
}
