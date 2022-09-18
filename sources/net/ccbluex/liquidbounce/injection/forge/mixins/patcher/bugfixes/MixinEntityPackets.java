package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.bugfixes;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({S14PacketEntity.class, S19PacketEntityHeadLook.class, S19PacketEntityStatus.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/bugfixes/MixinEntityPackets.class */
public class MixinEntityPackets {
    @Inject(method = {"getEntity", "func_149065_a", "func_149381_a", "func_149161_a"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true, remap = false)
    private void addNullCheck(World worldIn, CallbackInfoReturnable<Entity> cir) {
        if (worldIn == null) {
            cir.setReturnValue(null);
        }
    }
}
