package com.github.benmanes.caffeine.cache;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Weigher.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SingletonWeigher.class */
public enum SingletonWeigher implements Weigher<Object, Object> {
    INSTANCE;

    @Override // com.github.benmanes.caffeine.cache.Weigher
    public int weigh(Object key, Object value) {
        return 1;
    }
}
