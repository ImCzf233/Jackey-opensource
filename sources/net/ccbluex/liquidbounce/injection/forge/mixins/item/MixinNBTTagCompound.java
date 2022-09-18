package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NBTTagCompound.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/item/MixinNBTTagCompound.class */
public class MixinNBTTagCompound {
    @Inject(method = {"setTag"}, m23at = {@AbstractC1790At("HEAD")})
    private void failFast(String key, NBTBase value, CallbackInfo ci) {
        if (value == null) {
            throw new IllegalArgumentException("Invalid null NBT value with key " + key);
        }
    }
}
