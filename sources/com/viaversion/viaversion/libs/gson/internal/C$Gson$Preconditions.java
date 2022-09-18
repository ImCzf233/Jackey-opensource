package com.viaversion.viaversion.libs.gson.internal;

/* renamed from: com.viaversion.viaversion.libs.gson.internal.$Gson$Preconditions */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/$Gson$Preconditions.class */
public final class C$Gson$Preconditions {
    private C$Gson$Preconditions() {
        throw new UnsupportedOperationException();
    }

    public static <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    public static void checkArgument(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }
}
