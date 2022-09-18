package org.spongepowered.asm.mixin.extensibility;

import java.util.List;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/extensibility/IMixinInfo.class */
public interface IMixinInfo {
    IMixinConfig getConfig();

    String getName();

    String getClassName();

    String getClassRef();

    byte[] getClassBytes();

    boolean isDetachedSuper();

    ClassNode getClassNode(int i);

    List<String> getTargetClasses();

    int getPriority();

    MixinEnvironment.Phase getPhase();
}
