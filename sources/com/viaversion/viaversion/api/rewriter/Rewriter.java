package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/rewriter/Rewriter.class */
public interface Rewriter<T extends Protocol> {
    void register();

    T protocol();
}
