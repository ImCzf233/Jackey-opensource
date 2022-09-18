package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/rewriter/RewriterBase.class */
public abstract class RewriterBase<T extends Protocol> implements Rewriter<T> {
    protected final T protocol;

    public RewriterBase(T protocol) {
        this.protocol = protocol;
    }

    @Override // com.viaversion.viaversion.api.rewriter.Rewriter
    public final void register() {
        registerPackets();
        registerRewrites();
    }

    protected void registerPackets() {
    }

    protected void registerRewrites() {
    }

    @Override // com.viaversion.viaversion.api.rewriter.Rewriter
    public T protocol() {
        return this.protocol;
    }
}
