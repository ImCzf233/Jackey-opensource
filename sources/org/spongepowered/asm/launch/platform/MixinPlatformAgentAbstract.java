package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/launch/platform/MixinPlatformAgentAbstract.class */
public abstract class MixinPlatformAgentAbstract implements IMixinPlatformAgent {
    protected static final Logger logger = LogManager.getLogger("mixin");
    protected final MixinPlatformManager manager;
    protected final URI uri;
    protected final File container;
    protected final MainAttributes attributes;

    public MixinPlatformAgentAbstract(MixinPlatformManager manager, URI uri) {
        this.manager = manager;
        this.uri = uri;
        this.container = this.uri != null ? new File(this.uri) : null;
        this.attributes = MainAttributes.m27of(uri);
    }

    public String toString() {
        return String.format("PlatformAgent[%s:%s]", getClass().getSimpleName(), this.uri);
    }

    @Override // org.spongepowered.asm.launch.platform.IMixinPlatformAgent
    public String getPhaseProvider() {
        return null;
    }
}
