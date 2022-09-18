package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.render.Cape;
import net.ccbluex.liquidbounce.features.module.modules.render.NoFOV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AbstractClientPlayer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinAbstractClientPlayer.class */
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer {
    @Inject(method = {"getLocationCape"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void getCape(CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        Cape capeMod = (Cape) LiquidBounce.moduleManager.getModule(Cape.class);
        if (capeMod.getState() && Objects.equals(func_146103_bH().getName(), Minecraft.func_71410_x().field_71439_g.func_146103_bH().getName())) {
            callbackInfoReturnable.setReturnValue(capeMod.getCapeLocation(capeMod.getStyleValue().get()));
        }
    }

    @Inject(method = {"getFovModifier"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void getFovModifier(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        NoFOV fovModule = (NoFOV) LiquidBounce.moduleManager.getModule(NoFOV.class);
        if (fovModule.getState()) {
            float newFOV = fovModule.getFovValue().get().floatValue();
            if (!func_71039_bw()) {
                callbackInfoReturnable.setReturnValue(Float.valueOf(newFOV));
            } else if (func_71011_bu().func_77973_b() != Items.field_151031_f) {
                callbackInfoReturnable.setReturnValue(Float.valueOf(newFOV));
            } else {
                int i = func_71057_bx();
                float f1 = i / 20.0f;
                callbackInfoReturnable.setReturnValue(Float.valueOf(newFOV * (1.0f - ((f1 > 1.0f ? 1.0f : f1 * f1) * 0.15f))));
            }
        }
    }

    @Inject(method = {"getLocationSkin()Lnet/minecraft/util/ResourceLocation;"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void getSkin(CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        NameProtect nameProtect = (NameProtect) LiquidBounce.moduleManager.getModule(NameProtect.class);
        if (nameProtect.getState() && nameProtect.skinProtectValue.get().booleanValue()) {
            if (!nameProtect.allPlayersValue.get().booleanValue() && !Objects.equals(func_146103_bH().getName(), Minecraft.func_71410_x().field_71439_g.func_146103_bH().getName())) {
                return;
            }
            callbackInfoReturnable.setReturnValue((!nameProtect.customSkinValue.get().booleanValue() || nameProtect.skinImage == null) ? DefaultPlayerSkin.func_177334_a(func_110124_au()) : nameProtect.skinImage);
        }
    }
}
