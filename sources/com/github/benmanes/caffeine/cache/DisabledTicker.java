package com.github.benmanes.caffeine.cache;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Ticker.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/DisabledTicker.class */
public enum DisabledTicker implements Ticker {
    INSTANCE;

    @Override // com.github.benmanes.caffeine.cache.Ticker
    public long read() {
        return 0L;
    }
}
