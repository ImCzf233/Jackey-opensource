package com.github.benmanes.caffeine.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import sun.misc.Unsafe;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/base/UnsafeAccess.class */
public final class UnsafeAccess {
    static final String ANDROID = "THE_ONE";
    static final String OPEN_JDK = "theUnsafe";
    public static final Unsafe UNSAFE;

    static {
        try {
            UNSAFE = load(OPEN_JDK, ANDROID);
        } catch (Exception e) {
            throw new Error("Failed to load sun.misc.Unsafe", e);
        }
    }

    public static long objectFieldOffset(Class<?> clazz, String fieldName) {
        try {
            return UNSAFE.objectFieldOffset(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException | SecurityException e) {
            throw new Error(e);
        }
    }

    static Unsafe load(String openJdk, String android) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Field field;
        try {
            field = Unsafe.class.getDeclaredField(openJdk);
        } catch (NoSuchFieldException e) {
            try {
                field = Unsafe.class.getDeclaredField(android);
            } catch (NoSuchFieldException e2) {
                Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor(new Class[0]);
                unsafeConstructor.setAccessible(true);
                return unsafeConstructor.newInstance(new Object[0]);
            }
        }
        field.setAccessible(true);
        return (Unsafe) field.get(null);
    }

    private UnsafeAccess() {
    }
}
