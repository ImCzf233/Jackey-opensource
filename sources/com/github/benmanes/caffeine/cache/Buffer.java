package com.github.benmanes.caffeine.cache;

import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Buffer.class */
interface Buffer<E> {
    public static final int FULL = 1;
    public static final int SUCCESS = 0;
    public static final int FAILED = -1;

    int offer(E e);

    void drainTo(Consumer<E> consumer);

    int reads();

    int writes();

    static <E> Buffer<E> disabled() {
        return DisabledBuffer.INSTANCE;
    }

    default int size() {
        return writes() - reads();
    }
}
