package com.github.benmanes.caffeine.cache;

/* compiled from: MpscGrowableArrayQueue.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnsafeRefArrayAccess.class */
final class UnsafeRefArrayAccess {
    public static final long REF_ARRAY_BASE;
    public static final int REF_ELEMENT_SHIFT;

    static {
        int scale = UnsafeAccess.UNSAFE.arrayIndexScale(Object[].class);
        if (4 == scale) {
            REF_ELEMENT_SHIFT = 2;
        } else if (8 == scale) {
            REF_ELEMENT_SHIFT = 3;
        } else {
            throw new IllegalStateException("Unknown pointer size");
        }
        REF_ARRAY_BASE = UnsafeAccess.UNSAFE.arrayBaseOffset(Object[].class);
    }

    private UnsafeRefArrayAccess() {
    }

    public static <E> void spElement(E[] buffer, long offset, E e) {
        UnsafeAccess.UNSAFE.putObject(buffer, offset, e);
    }

    public static <E> void soElement(E[] buffer, long offset, E e) {
        UnsafeAccess.UNSAFE.putOrderedObject(buffer, offset, e);
    }

    public static <E> E lpElement(E[] buffer, long offset) {
        return (E) UnsafeAccess.UNSAFE.getObject(buffer, offset);
    }

    public static <E> E lvElement(E[] buffer, long offset) {
        return (E) UnsafeAccess.UNSAFE.getObjectVolatile(buffer, offset);
    }

    public static long calcElementOffset(long index) {
        return REF_ARRAY_BASE + (index << REF_ELEMENT_SHIFT);
    }
}
