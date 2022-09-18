package com.github.benmanes.caffeine.cache;

import java.lang.reflect.Constructor;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalCacheFactory.class */
public final class LocalCacheFactory {
    public static final String MAXIMUM = "maximum";
    public static final String WINDOW_MAXIMUM = "windowMaximum";
    public static final String MAIN_PROTECTED_MAXIMUM = "mainProtectedMaximum";
    public static final String WEIGHTED_SIZE = "weightedSize";
    public static final String WINDOW_WEIGHTED_SIZE = "windowWeightedSize";
    public static final String MAIN_PROTECTED_WEIGHTED_SIZE = "mainProtectedWeightedSize";
    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String ACCESS_TIME = "accessTime";
    public static final String WRITE_TIME = "writeTime";

    private LocalCacheFactory() {
    }

    public static <K, V> BoundedLocalCache<K, V> newBoundedLocalCache(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        StringBuilder sb = new StringBuilder("com.github.benmanes.caffeine.cache.");
        if (builder.isStrongKeys()) {
            sb.append('S');
        } else {
            sb.append('W');
        }
        if (builder.isStrongValues()) {
            sb.append('S');
        } else {
            sb.append('I');
        }
        if (builder.removalListener != null) {
            sb.append('L');
        }
        if (builder.isRecordingStats()) {
            sb.append('S');
        }
        if (builder.evicts()) {
            sb.append('M');
            if (builder.isWeighted()) {
                sb.append('W');
            } else {
                sb.append('S');
            }
        }
        if (builder.expiresAfterAccess() || builder.expiresVariable()) {
            sb.append('A');
        }
        if (builder.expiresAfterWrite()) {
            sb.append('W');
        }
        if (builder.refreshAfterWrite()) {
            sb.append('R');
        }
        try {
            Class<?> clazz = Class.forName(sb.toString());
            Constructor<?> ctor = clazz.getDeclaredConstructor(Caffeine.class, CacheLoader.class, Boolean.TYPE);
            BoundedLocalCache<K, V> factory = (BoundedLocalCache) ctor.newInstance(builder, cacheLoader, Boolean.valueOf(async));
            return factory;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(sb.toString(), e);
        }
    }
}
