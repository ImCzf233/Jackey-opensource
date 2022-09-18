package net.ccbluex.liquidbounce.injection.forge.mixins.resources;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({SkinManager.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/resources/MixinSkinManager.class */
public class MixinSkinManager {
    @Inject(method = {"loadSkinFromCache"}, cancellable = true, m23at = {@AbstractC1790At("HEAD")})
    private void injectSkinProtect(GameProfile gameProfile, CallbackInfoReturnable<Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>> cir) {
        if (gameProfile == null) {
            return;
        }
        NameProtect nameProtect = (NameProtect) LiquidBounce.moduleManager.getModule(NameProtect.class);
        if (nameProtect.getState() && nameProtect.skinProtectValue.get().booleanValue()) {
            if (nameProtect.allPlayersValue.get().booleanValue() || Objects.equals(gameProfile.getId(), Minecraft.func_71410_x().func_110432_I().func_148256_e().getId())) {
                cir.setReturnValue(new HashMap());
                cir.cancel();
            }
        }
    }
}
