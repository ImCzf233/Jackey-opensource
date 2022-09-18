package net.ccbluex.liquidbounce.p004ui.client.clickgui.elements;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

/* renamed from: net.ccbluex.liquidbounce.ui.client.clickgui.elements.ModuleElement */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/clickgui/elements/ModuleElement.class */
public class ModuleElement extends ButtonElement {
    private final Module module;
    private boolean showSettings;
    private float settingsWidth = 0.0f;
    private boolean wasPressed;
    public int slowlySettingsYPos;
    public int slowlyFade;

    public ModuleElement(Module module) {
        super(null);
        this.displayName = module.getName();
        this.module = module;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement, net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.Element
    public void drawScreen(int mouseX, int mouseY, float button) {
        LiquidBounce.clickGui.style.drawModuleElement(mouseX, mouseY, this);
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.Element
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && isHovering(mouseX, mouseY) && isVisible()) {
            this.module.toggle();
            return true;
        } else if (mouseButton == 1 && isHovering(mouseX, mouseY) && isVisible()) {
            this.showSettings = !this.showSettings;
            f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            return true;
        } else {
            return false;
        }
    }

    public Module getModule() {
        return this.module;
    }

    public boolean isShowSettings() {
        return this.showSettings;
    }

    public void setShowSettings(boolean showSettings) {
        this.showSettings = showSettings;
    }

    public boolean isntPressed() {
        return !this.wasPressed;
    }

    public void updatePressed() {
        this.wasPressed = Mouse.isButtonDown(0);
    }

    public float getSettingsWidth() {
        return this.settingsWidth;
    }

    public void setSettingsWidth(float settingsWidth) {
        this.settingsWidth = settingsWidth;
    }
}
