package com.viaversion.viaversion.unsupported;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/unsupported/UnsupportedMethods.class */
public final class UnsupportedMethods {
    private final String className;
    private final Set<String> methodNames;

    public UnsupportedMethods(String className, Set<String> methodNames) {
        this.className = className;
        this.methodNames = Collections.unmodifiableSet(methodNames);
    }

    public String getClassName() {
        return this.className;
    }

    public final boolean findMatch() {
        Method[] declaredMethods;
        try {
            for (Method method : Class.forName(this.className).getDeclaredMethods()) {
                if (this.methodNames.contains(method.getName())) {
                    return true;
                }
            }
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
