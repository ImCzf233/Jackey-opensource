package net.ccbluex.liquidbounce.p004ui.client.clickgui.style;

import net.ccbluex.liquidbounce.p004ui.client.clickgui.Panel;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

/* renamed from: net.ccbluex.liquidbounce.ui.client.clickgui.style.Style */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/clickgui/style/Style.class */
public abstract class Style extends MinecraftInstance {
    public abstract void drawPanel(int i, int i2, Panel panel);

    public abstract void drawDescription(int i, int i2, String str);

    public abstract void drawButtonElement(int i, int i2, ButtonElement buttonElement);

    public abstract void drawModuleElement(int i, int i2, ModuleElement moduleElement);
}
