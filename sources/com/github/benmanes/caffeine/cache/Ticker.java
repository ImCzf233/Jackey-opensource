package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Ticker.class */
public interface Ticker {
    long read();

    static Ticker systemTicker() {
        return SystemTicker.INSTANCE;
    }

    static Ticker disabledTicker() {
        return DisabledTicker.INSTANCE;
    }
}
