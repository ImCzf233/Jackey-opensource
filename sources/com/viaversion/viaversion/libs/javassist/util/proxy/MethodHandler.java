package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/MethodHandler.class */
public interface MethodHandler {
    Object invoke(Object obj, Method method, Method method2, Object[] objArr) throws Throwable;
}
