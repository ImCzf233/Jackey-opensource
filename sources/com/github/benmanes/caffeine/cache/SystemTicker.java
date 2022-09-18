package com.github.benmanes.caffeine.cache;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Ticker.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SystemTicker.class */
public enum SystemTicker implements Ticker {
    INSTANCE;

    @Override // com.github.benmanes.caffeine.cache.Ticker
    public long read() {
        return System.nanoTime();
    }
}
