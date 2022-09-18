package net.ccbluex.liquidbounce.injection.forge.mixins.world;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({WorldClient.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/world/MixinWorldClient.class */
public class MixinWorldClient {
    @ModifyVariable(method = {"doVoidFogParticles"}, m18at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/block/Block;randomDisplayTick(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V", shift = AbstractC1790At.Shift.AFTER), ordinal = 0)
    private boolean handleBarriers(boolean flag) {
        TrueSight trueSight = (TrueSight) LiquidBounce.moduleManager.getModule(TrueSight.class);
        return flag || (trueSight.getState() && trueSight.getBarriersValue().get().booleanValue());
    }
}
