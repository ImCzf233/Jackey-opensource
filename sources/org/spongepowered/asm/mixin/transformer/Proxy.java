package org.spongepowered.asm.mixin.transformer;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.service.ILegacyClassTransformer;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/Proxy.class */
public final class Proxy implements IClassTransformer, ILegacyClassTransformer {
    private static List<Proxy> proxies = new ArrayList();
    private static MixinTransformer transformer = new MixinTransformer();
    private boolean isActive;

    public Proxy() {
        this.isActive = true;
        for (Proxy hook : proxies) {
            hook.isActive = false;
        }
        proxies.add(this);
        LogManager.getLogger("mixin").debug("Adding new mixin transformer proxy #{}", new Object[]{Integer.valueOf(proxies.size())});
    }

    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (this.isActive) {
            return transformer.transformClassBytes(name, transformedName, basicClass);
        }
        return basicClass;
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public String getName() {
        return getClass().getName();
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public boolean isDelegationExcluded() {
        return true;
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
        if (this.isActive) {
            return transformer.transformClassBytes(name, transformedName, basicClass);
        }
        return basicClass;
    }
}
