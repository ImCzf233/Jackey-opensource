package org.spongepowered.asm.mixin.transformer.ext;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ext/ITargetClassContext.class */
public interface ITargetClassContext {
    ClassInfo getClassInfo();

    ClassNode getClassNode();
}
