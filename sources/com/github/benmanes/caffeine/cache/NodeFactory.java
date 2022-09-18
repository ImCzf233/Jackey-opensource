package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.References;
import java.lang.ref.ReferenceQueue;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/NodeFactory.class */
public interface NodeFactory<K, V> {
    public static final Object RETIRED_STRONG_KEY = new Object();
    public static final Object DEAD_STRONG_KEY = new Object();
    public static final References.WeakKeyReference<Object> RETIRED_WEAK_KEY = new References.WeakKeyReference<>(null, null);
    public static final References.WeakKeyReference<Object> DEAD_WEAK_KEY = new References.WeakKeyReference<>(null, null);

    Node<K, V> newNode(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int i, long j);

    Node<K, V> newNode(Object obj, V v, ReferenceQueue<V> referenceQueue, int i, long j);

    default Object newReferenceKey(K key, ReferenceQueue<K> referenceQueue) {
        return key;
    }

    default Object newLookupKey(Object key) {
        return key;
    }

    static <K, V> NodeFactory<K, V> newFactory(Caffeine<K, V> builder, boolean isAsync) {
        StringBuilder sb = new StringBuilder("com.github.benmanes.caffeine.cache.");
        if (builder.isStrongKeys()) {
            sb.append('P');
        } else {
            sb.append('F');
        }
        if (builder.isStrongValues()) {
            sb.append('S');
        } else if (builder.isWeakValues()) {
            sb.append('W');
        } else {
            sb.append('D');
        }
        if (builder.expiresVariable()) {
            if (builder.refreshAfterWrite()) {
                sb.append('A');
                if (builder.evicts()) {
                    sb.append('W');
                }
            } else {
                sb.append('W');
            }
        } else {
            if (builder.expiresAfterAccess()) {
                sb.append('A');
            }
            if (builder.expiresAfterWrite()) {
                sb.append('W');
            }
        }
        if (builder.refreshAfterWrite()) {
            sb.append('R');
        }
        if (builder.evicts()) {
            sb.append('M');
            if (isAsync || (builder.isWeighted() && builder.weigher != Weigher.singletonWeigher())) {
                sb.append('W');
            } else {
                sb.append('S');
            }
        }
        try {
            Class<?> clazz = Class.forName(sb.toString());
            NodeFactory<K, V> factory = (NodeFactory) clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            return factory;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(sb.toString(), e);
        }
    }

    default boolean weakValues() {
        return false;
    }

    default boolean softValues() {
        return false;
    }
}
