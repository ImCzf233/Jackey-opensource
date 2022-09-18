package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.UpdateModelEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ModelPlayer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinModelPlayer.class */
public class MixinModelPlayer extends ModelBiped {
    @Shadow
    private boolean field_178735_y;

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(floatValue = 2.5f)})
    private float fixAlexArmHeight(float original) {
        return 2.0f;
    }

    @Overwrite
    public void func_178718_a(float scale) {
        if (this.field_178735_y) {
            this.field_178723_h.field_78800_c += 0.5f;
            this.field_178723_h.func_78794_c(scale);
            this.field_178723_h.field_78798_e -= 0.5f;
            return;
        }
        this.field_178723_h.func_78794_c(scale);
    }

    @Inject(method = {"setRotationAngles"}, m23at = {@AbstractC1790At("RETURN")})
    private void revertSwordAnimation(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new UpdateModelEvent((EntityPlayer) p_setRotationAngles_7_, (ModelPlayer) this));
    }
}
