package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/meta/MetaHandler.class */
public interface MetaHandler {
    void handle(MetaHandlerEvent metaHandlerEvent, Metadata metadata);
}
