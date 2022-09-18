package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import net.ccbluex.liquidbounce.injection.implementations.IItemStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ItemStack.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/item/MixinItemStack.class */
public class MixinItemStack implements IItemStack {
    private long itemDelay;
    private String cachedDisplayName;

    @Inject(method = {"<init>(Lnet/minecraft/item/Item;IILnet/minecraft/nbt/NBTTagCompound;)V"}, m23at = {@AbstractC1790At("RETURN")})
    private void init(CallbackInfo callbackInfo) {
        this.itemDelay = System.currentTimeMillis();
    }

    @Override // net.ccbluex.liquidbounce.injection.implementations.IItemStack
    public long getItemDelay() {
        return this.itemDelay;
    }

    @Redirect(method = {"getTooltip"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Ljava/lang/Integer;toHexString(I)Ljava/lang/String;"))
    private String fixHexColorString(int i) {
        return String.format("%06X", Integer.valueOf(i));
    }

    @Inject(method = {"getDisplayName"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void returnCachedDisplayName(CallbackInfoReturnable<String> cir) {
        if (this.cachedDisplayName != null) {
            cir.setReturnValue(this.cachedDisplayName);
        }
    }

    @Inject(method = {"getDisplayName"}, m23at = {@AbstractC1790At("RETURN")})
    private void cacheDisplayName(CallbackInfoReturnable<String> cir) {
        this.cachedDisplayName = cir.getReturnValue();
    }

    @Inject(method = {"setStackDisplayName"}, m23at = {@AbstractC1790At("HEAD")})
    private void resetCachedDisplayName(String displayName, CallbackInfoReturnable<ItemStack> cir) {
        this.cachedDisplayName = null;
    }
}
