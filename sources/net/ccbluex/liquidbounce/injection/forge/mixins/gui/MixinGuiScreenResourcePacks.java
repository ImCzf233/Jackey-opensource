package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiScreenResourcePacks.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiScreenResourcePacks.class */
public class MixinGuiScreenResourcePacks {
    @Inject(method = {"actionPerformed"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Ljava/util/Collections;reverse(Ljava/util/List;)V", remap = false)})
    private void clearHandles(CallbackInfo ci) {
        ResourcePackRepository repository = Minecraft.func_71410_x().func_110438_M();
        for (ResourcePackRepository.Entry entry : repository.func_110613_c()) {
            IResourcePack current = repository.func_148530_e();
            if (current == null || !entry.func_110515_d().equals(current.func_130077_b())) {
                entry.func_110517_b();
            }
        }
    }
}
