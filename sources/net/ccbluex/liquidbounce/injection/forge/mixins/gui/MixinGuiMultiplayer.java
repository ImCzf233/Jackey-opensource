package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import de.enzaxd.viaforge.ViaForge;
import de.enzaxd.viaforge.protocol.ProtocolCollection;
import java.util.List;
import net.ccbluex.liquidbounce.p004ui.elements.ToolDropdown;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiMultiplayer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiMultiplayer.class */
public abstract class MixinGuiMultiplayer extends MixinGuiScreen {
    private GuiButton toolButton;
    private GuiSlider viaSlider;

    @Inject(method = {"initGui"}, m23at = {@AbstractC1790At("RETURN")})
    private void initGui(CallbackInfo callbackInfo) {
        List<GuiButton> list = this.field_146292_n;
        GuiButton guiButton = new GuiButton(997, 5, 8, 138, 20, "Tools");
        this.toolButton = guiButton;
        list.add(guiButton);
        List<GuiButton> list2 = this.field_146292_n;
        GuiSlider guiSlider = new GuiSlider(1337, this.field_146294_l - 104, 8, 98, 20, "Version: ", "", 0.0d, ProtocolCollection.values().length - 1, (ProtocolCollection.values().length - 1) - getProtocolIndex(ViaForge.getInstance().getVersion()), false, true, guiSlider2 -> {
            ViaForge.getInstance().setVersion(ProtocolCollection.values()[(ProtocolCollection.values().length - 1) - guiSlider2.getValueInt()].getVersion().getVersion());
            updatePortalText();
        });
        this.viaSlider = guiSlider;
        list2.add(guiSlider);
        updatePortalText();
    }

    private void updatePortalText() {
        if (this.viaSlider == null) {
            return;
        }
        this.viaSlider.field_146126_j = "Version: " + ProtocolCollection.getProtocolById(ViaForge.getInstance().getVersion()).getName();
    }

    private int getProtocolIndex(int id) {
        for (int i = 0; i < ProtocolCollection.values().length; i++) {
            if (ProtocolCollection.values()[i].getVersion().getVersion() == id) {
                return i;
            }
        }
        return -1;
    }

    @Inject(method = {"drawScreen"}, m23at = {@AbstractC1790At("TAIL")})
    private void injectToolDraw(int mouseX, int mouseY, float partialTicks, CallbackInfo callbackInfo) {
        ToolDropdown.handleDraw(this.toolButton);
    }

    @Inject(method = {"mouseClicked"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void injectToolClick(int mouseX, int mouseY, int mouseButton, CallbackInfo callbackInfo) {
        if (mouseButton == 0 && ToolDropdown.handleClick(mouseX, mouseY, this.toolButton)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"actionPerformed"}, m23at = {@AbstractC1790At("HEAD")})
    private void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
        if (button.field_146127_k == 997) {
            ToolDropdown.toggleState();
        }
    }
}
