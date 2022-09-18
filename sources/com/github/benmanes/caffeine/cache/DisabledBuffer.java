package com.github.benmanes.caffeine.cache;

import java.util.function.Consumer;

/* compiled from: Buffer.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/DisabledBuffer.class */
public enum DisabledBuffer implements Buffer<Object> {
    INSTANCE;

    @Override // com.github.benmanes.caffeine.cache.Buffer
    public int offer(Object e) {
        return 0;
    }

    @Override // com.github.benmanes.caffeine.cache.Buffer
    public void drainTo(Consumer<Object> consumer) {
    }

    @Override // com.github.benmanes.caffeine.cache.Buffer
    public int size() {
        return 0;
    }

    @Override // com.github.benmanes.caffeine.cache.Buffer
    public int reads() {
        return 0;
    }

    @Override // com.github.benmanes.caffeine.cache.Buffer
    public int writes() {
        return 0;
    }
}
