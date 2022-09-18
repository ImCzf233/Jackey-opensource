package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.SpinBot;
import net.ccbluex.liquidbounce.features.module.modules.render.Rotations;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ModelBiped.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinModelBiped.class */
public class MixinModelBiped {
    @Shadow
    public ModelRenderer field_178723_h;
    @Shadow
    public int field_78120_m;
    @Shadow
    public ModelRenderer field_78116_c;

    @Inject(method = {"setRotationAngles"}, m23at = {@AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F")})
    private void revertSwordAnimation(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_, CallbackInfo callbackInfo) {
        if (this.field_78120_m == 3) {
            this.field_178723_h.field_78796_g = 0.0f;
        }
        Rotations rotationModule = (Rotations) LiquidBounce.moduleManager.getModule(Rotations.class);
        if ((p_setRotationAngles_7_ instanceof EntityPlayer) && p_setRotationAngles_7_.equals(Minecraft.func_71410_x().field_71439_g)) {
            SpinBot spinBot = (SpinBot) LiquidBounce.moduleManager.getModule(SpinBot.class);
            if (spinBot.getState() && !spinBot.getPitchMode().get().equalsIgnoreCase("none")) {
                this.field_78116_c.field_78795_f = spinBot.getPitch() / 57.295776f;
            } else if (rotationModule.getState() && RotationUtils.serverRotation != null) {
                this.field_78116_c.field_78795_f = RotationUtils.serverRotation.getPitch() / 57.295776f;
            }
        }
    }
}
