package com.viaversion.viaversion.libs.javassist.util.proxy;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/ProxyObject.class */
public interface ProxyObject extends Proxy {
    @Override // com.viaversion.viaversion.libs.javassist.util.proxy.Proxy
    void setHandler(MethodHandler methodHandler);

    MethodHandler getHandler();
}
