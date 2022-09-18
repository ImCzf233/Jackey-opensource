package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.bugfixes;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({ItemModelMesher.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/bugfixes/MixinItemModelMesher.class */
public class MixinItemModelMesher {
    @Shadow
    @Final
    private ModelManager field_178090_d;

    @Inject(method = {"getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/resources/model/IBakedModel;"}, m23at = {@AbstractC1790At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;")}, locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void returnMissingModel(ItemStack stack, CallbackInfoReturnable<IBakedModel> cir, Item item) {
        if (item == null) {
            cir.setReturnValue(this.field_178090_d.func_174951_a());
        }
    }
}
