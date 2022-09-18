package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.bugfixes;

import java.util.Locale;
import net.minecraft.command.CommandHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({CommandHandler.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/bugfixes/MixinCommandHandler.class */
public class MixinCommandHandler {
    @ModifyArg(method = {"executeCommand"}, m20at = @AbstractC1790At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", remap = false))
    private Object makeLowerCaseForGet(Object s) {
        if (s instanceof String) {
            return ((String) s).toLowerCase(Locale.ENGLISH);
        }
        return s;
    }

    @ModifyArg(method = {"registerCommand"}, m20at = @AbstractC1790At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", remap = false), index = 0)
    private Object makeLowerCaseForPut(Object s) {
        if (s instanceof String) {
            return ((String) s).toLowerCase(Locale.ENGLISH);
        }
        return s;
    }
}
