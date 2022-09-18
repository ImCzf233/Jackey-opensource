package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/gen/InvokerInfo.class */
public class InvokerInfo extends AccessorInfo {
    public InvokerInfo(MixinTargetContext mixin, MethodNode method) {
        super(mixin, method, Invoker.class);
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorInfo
    protected AccessorInfo.AccessorType initType() {
        return AccessorInfo.AccessorType.METHOD_PROXY;
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorInfo
    protected Type initTargetFieldType() {
        return null;
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorInfo
    protected MemberInfo initTarget() {
        return new MemberInfo(getTargetName(), (String) null, this.method.desc);
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorInfo
    public void locate() {
        this.targetMethod = findTargetMethod();
    }

    private MethodNode findTargetMethod() {
        return (MethodNode) findTarget(this.classNode.methods);
    }
}
