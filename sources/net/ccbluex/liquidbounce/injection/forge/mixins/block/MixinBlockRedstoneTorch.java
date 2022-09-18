package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({BlockRedstoneTorch.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockRedstoneTorch.class */
public class MixinBlockRedstoneTorch {
    @Shadow
    private static Map<World, List<BlockRedstoneTorch.Toggle>> field_150112_b = new WeakHashMap();
}
