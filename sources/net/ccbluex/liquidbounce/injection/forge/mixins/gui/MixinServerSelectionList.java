package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ServerListEntryLanDetected;
import net.minecraft.client.gui.ServerSelectionList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ServerSelectionList.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinServerSelectionList.class */
public abstract class MixinServerSelectionList extends GuiSlot {
    @Shadow
    @Final
    private List<ServerListEntryLanDetected> field_148199_m;
    @Shadow
    @Final
    private GuiListExtended.IGuiListEntry field_148196_n;

    public MixinServerSelectionList(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, width, height, topIn, bottomIn, slotHeightIn);
    }

    @Overwrite
    protected int func_148137_d() {
        return this.field_148155_a - 5;
    }

    @Inject(method = {"getListEntry"}, m23at = {@AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/client/gui/ServerSelectionList;serverListLan:Ljava/util/List;")}, cancellable = true)
    private void resolveIndexError(int index, CallbackInfoReturnable<GuiListExtended.IGuiListEntry> cir) {
        if (index >= this.field_148199_m.size()) {
            cir.setReturnValue(this.field_148196_n);
        }
    }
}
