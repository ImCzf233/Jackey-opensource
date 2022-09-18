package org.spongepowered.asm.service.mojang;

import javax.annotation.Resource;
import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.service.ILegacyClassTransformer;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/service/mojang/LegacyTransformerHandle.class */
class LegacyTransformerHandle implements ILegacyClassTransformer {
    private final IClassTransformer transformer;

    public LegacyTransformerHandle(IClassTransformer transformer) {
        this.transformer = transformer;
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public String getName() {
        return this.transformer.getClass().getName();
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public boolean isDelegationExcluded() {
        return this.transformer.getClass().getAnnotation(Resource.class) != null;
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
        return this.transformer.transform(name, transformedName, basicClass);
    }
}
