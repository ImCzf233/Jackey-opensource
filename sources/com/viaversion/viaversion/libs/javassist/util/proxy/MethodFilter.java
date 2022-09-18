package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/MethodFilter.class */
public interface MethodFilter {
    boolean isHandled(Method method);
}
