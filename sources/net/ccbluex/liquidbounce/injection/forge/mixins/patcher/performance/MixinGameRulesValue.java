package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.performance;

import java.util.Objects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {"net.minecraft.world.GameRules$Value"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/performance/MixinGameRulesValue.class */
public class MixinGameRulesValue {
    @Shadow
    private String field_82762_a;

    @Inject(method = {"setValue(Ljava/lang/String;)V"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void cancelIfUnchanged(String value, CallbackInfo ci) {
        if (Objects.equals(this.field_82762_a, value)) {
            ci.cancel();
        }
    }
}
