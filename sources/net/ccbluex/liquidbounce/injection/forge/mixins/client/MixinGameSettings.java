package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({GameSettings.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/client/MixinGameSettings.class */
public class MixinGameSettings {
    @Overwrite
    public static boolean func_100015_a(KeyBinding key) {
        int keyCode = key.func_151463_i();
        if (keyCode == 0 || keyCode >= 256) {
            return false;
        }
        return keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode);
    }
}
