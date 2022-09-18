package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({EntityXPOrb.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinEntityXPOrb.class */
public class MixinEntityXPOrb {
    @Redirect(method = {"onUpdate"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getEyeHeight()F"))
    private float lowerHeight(EntityPlayer entityPlayer) {
        return (float) (entityPlayer.func_70047_e() / 2.0d);
    }
}
