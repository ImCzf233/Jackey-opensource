package com.github.benmanes.caffeine.cache;

import java.util.AbstractMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/WriteThroughEntry.class */
final class WriteThroughEntry<K, V> extends AbstractMap.SimpleEntry<K, V> {
    static final long serialVersionUID = 1;
    private final ConcurrentMap<K, V> map;

    public WriteThroughEntry(ConcurrentMap<K, V> map, K key, V value) {
        super(key, value);
        this.map = (ConcurrentMap) Objects.requireNonNull(map);
    }

    @Override // java.util.AbstractMap.SimpleEntry, java.util.Map.Entry
    public V setValue(V value) {
        this.map.put(getKey(), value);
        return (V) super.setValue(value);
    }

    Object writeReplace() {
        return new AbstractMap.SimpleEntry(this);
    }
}
