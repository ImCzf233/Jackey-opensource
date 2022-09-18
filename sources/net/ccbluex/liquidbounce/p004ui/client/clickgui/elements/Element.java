package net.ccbluex.liquidbounce.p004ui.client.clickgui.elements;

import net.ccbluex.liquidbounce.utils.MinecraftInstance;

/* renamed from: net.ccbluex.liquidbounce.ui.client.clickgui.elements.Element */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/clickgui/elements/Element.class */
public class Element extends MinecraftInstance {

    /* renamed from: x */
    private int f344x;

    /* renamed from: y */
    private int f345y;
    private int width;
    private int height;
    private boolean visible;

    public void setLocation(int x, int y) {
        this.f344x = x;
        this.f345y = y;
    }

    public void drawScreen(int mouseX, int mouseY, float button) {
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    public boolean mouseReleased(int mouseX, int mouseY, int state) {
        return false;
    }

    public int getX() {
        return this.f344x;
    }

    public void setX(int x) {
        this.f344x = x;
    }

    public int getY() {
        return this.f345y;
    }

    public void setY(int y) {
        this.f345y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
