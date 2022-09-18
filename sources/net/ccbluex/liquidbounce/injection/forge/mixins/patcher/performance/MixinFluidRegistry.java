package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.performance;

import java.util.Collections;
import java.util.Set;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({FluidRegistry.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/performance/MixinFluidRegistry.class */
public class MixinFluidRegistry {
    @Shadow(remap = false)
    static Set<Fluid> bucketFluids;

    @Overwrite(remap = false)
    public static Set<Fluid> getBucketFluids() {
        return Collections.unmodifiableSet(bucketFluids);
    }
}
