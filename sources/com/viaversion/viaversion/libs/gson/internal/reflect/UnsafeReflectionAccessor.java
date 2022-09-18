package com.viaversion.viaversion.libs.gson.internal.reflect;

import com.viaversion.viaversion.libs.gson.JsonIOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/reflect/UnsafeReflectionAccessor.class */
final class UnsafeReflectionAccessor extends ReflectionAccessor {
    private static Class unsafeClass;
    private final Object theUnsafe = getUnsafeInstance();
    private final Field overrideField = getOverrideField();

    @Override // com.viaversion.viaversion.libs.gson.internal.reflect.ReflectionAccessor
    public void makeAccessible(AccessibleObject ao) {
        boolean success = makeAccessibleWithUnsafe(ao);
        if (!success) {
            try {
                ao.setAccessible(true);
            } catch (SecurityException e) {
                throw new JsonIOException("Gson couldn't modify fields for " + ao + "\nand sun.misc.Unsafe not found.\nEither write a custom type adapter, or make fields accessible, or include sun.misc.Unsafe.", e);
            }
        }
    }

    boolean makeAccessibleWithUnsafe(AccessibleObject ao) {
        if (this.theUnsafe != null && this.overrideField != null) {
            try {
                Method method = unsafeClass.getMethod("objectFieldOffset", Field.class);
                long overrideOffset = ((Long) method.invoke(this.theUnsafe, this.overrideField)).longValue();
                Method putBooleanMethod = unsafeClass.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
                putBooleanMethod.invoke(this.theUnsafe, ao, Long.valueOf(overrideOffset), true);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private static Object getUnsafeInstance() {
        try {
            unsafeClass = Class.forName("sun.misc.Unsafe");
            Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            return unsafeField.get(null);
        } catch (Exception e) {
            return null;
        }
    }

    private static Field getOverrideField() {
        try {
            return AccessibleObject.class.getDeclaredField("override");
        } catch (Exception e) {
            return null;
        }
    }
}
