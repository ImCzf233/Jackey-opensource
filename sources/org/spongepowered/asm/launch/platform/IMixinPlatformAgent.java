package org.spongepowered.asm.launch.platform;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/launch/platform/IMixinPlatformAgent.class */
public interface IMixinPlatformAgent {
    String getPhaseProvider();

    void prepare();

    void initPrimaryContainer();

    void inject();

    String getLaunchTarget();
}
