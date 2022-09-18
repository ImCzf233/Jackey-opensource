package com.viaversion.viaversion.api.protocol;

import java.util.Collection;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/ProtocolPipeline.class */
public interface ProtocolPipeline extends SimpleProtocol {
    void add(Protocol protocol);

    void add(Collection<Protocol> collection);

    boolean contains(Class<? extends Protocol> cls);

    <P extends Protocol> P getProtocol(Class<P> cls);

    List<Protocol> pipes();

    boolean hasNonBaseProtocols();

    void cleanPipes();
}
