package com.github.benmanes.caffeine.cache;

import java.io.Serializable;
import java.util.Objects;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Weigher.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedWeigher.class */
public final class BoundedWeigher<K, V> implements Weigher<K, V>, Serializable {
    static final long serialVersionUID = 1;
    final Weigher<? super K, ? super V> delegate;

    public BoundedWeigher(Weigher<? super K, ? super V> delegate) {
        this.delegate = (Weigher) Objects.requireNonNull(delegate);
    }

    @Override // com.github.benmanes.caffeine.cache.Weigher
    public int weigh(K key, V value) {
        int weight = this.delegate.weigh(key, value);
        Caffeine.requireArgument(weight >= 0);
        return weight;
    }

    Object writeReplace() {
        return this.delegate;
    }
}
