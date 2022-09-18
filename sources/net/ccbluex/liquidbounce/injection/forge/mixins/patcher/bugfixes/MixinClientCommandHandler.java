package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.bugfixes;

import java.util.Locale;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.client.ClientCommandHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ClientCommandHandler.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/bugfixes/MixinClientCommandHandler.class */
public class MixinClientCommandHandler {
    @ModifyArg(method = {"executeCommand", "func_71556_a"}, m20at = @AbstractC1790At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", remap = false), remap = false)
    private Object makeLowerCaseForGet(Object s) {
        if (s instanceof String) {
            return ((String) s).toLowerCase(Locale.ENGLISH);
        }
        return s;
    }

    @Inject(method = {"executeCommand", "func_71556_a"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true, remap = false)
    private void checkForSlash(ICommandSender sender, String message, CallbackInfoReturnable<Integer> cir) {
        if (!message.trim().startsWith("/")) {
            cir.setReturnValue(0);
        }
    }
}
