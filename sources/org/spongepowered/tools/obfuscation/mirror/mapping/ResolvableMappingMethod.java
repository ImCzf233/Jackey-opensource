package org.spongepowered.tools.obfuscation.mirror.mapping;

import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/mirror/mapping/ResolvableMappingMethod.class */
public final class ResolvableMappingMethod extends MappingMethod {
    private final TypeHandle ownerHandle;

    public ResolvableMappingMethod(TypeHandle owner, String name, String desc) {
        super(owner.getName(), name, desc);
        this.ownerHandle = owner;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod getSuper() {
        if (this.ownerHandle == null) {
            return super.getSuper();
        }
        String name = getSimpleName();
        String desc = getDesc();
        String signature = TypeUtils.getJavaSignature(desc);
        TypeHandle superClass = this.ownerHandle.getSuperclass();
        if (superClass != null && superClass.findMethod(name, signature) != null) {
            return superClass.getMappingMethod(name, desc);
        }
        for (TypeHandle iface : this.ownerHandle.getInterfaces()) {
            if (iface.findMethod(name, signature) != null) {
                return iface.getMappingMethod(name, desc);
            }
        }
        if (superClass != null) {
            return superClass.getMappingMethod(name, desc).getSuper();
        }
        return super.getSuper();
    }

    public MappingMethod move(TypeHandle newOwner) {
        return new ResolvableMappingMethod(newOwner, getSimpleName(), getDesc());
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod remap(String newName) {
        return new ResolvableMappingMethod(this.ownerHandle, newName, getDesc());
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod transform(String newDesc) {
        return new ResolvableMappingMethod(this.ownerHandle, getSimpleName(), newDesc);
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod copy() {
        return new ResolvableMappingMethod(this.ownerHandle, getSimpleName(), getDesc());
    }
}
