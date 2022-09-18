package net.ccbluex.liquidbounce.p004ui.client.clickgui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

/* renamed from: net.ccbluex.liquidbounce.ui.client.clickgui.Panel */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/clickgui/Panel.class */
public abstract class Panel extends MinecraftInstance {
    private final String name;

    /* renamed from: x */
    public int f340x;

    /* renamed from: y */
    public int f341y;

    /* renamed from: x2 */
    public int f342x2;

    /* renamed from: y2 */
    public int f343y2;
    private final int width;
    private final int height;
    private int scroll;
    private int dragged;
    private boolean open;
    public boolean drag;
    private float elementsHeight;
    private float fade;
    private final List<Element> elements = new ArrayList();
    private boolean scrollbar = false;
    private boolean visible = true;

    public abstract void setupItems();

    public Panel(String name, int x, int y, int width, int height, boolean open) {
        this.name = name;
        this.f340x = x;
        this.f341y = y;
        this.width = width;
        this.height = height;
        this.open = open;
        setupItems();
    }

    public void drawScreen(int mouseX, int mouseY, float button) {
        if (!this.visible) {
            return;
        }
        int maxElements = ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get().intValue();
        if (this.drag) {
            int nx = this.f342x2 + mouseX;
            int ny = this.f343y2 + mouseY;
            if (nx > -1) {
                this.f340x = nx;
            }
            if (ny > -1) {
                this.f341y = ny;
            }
        }
        this.elementsHeight = getElementsHeight() - 1;
        boolean scrollbar = this.elements.size() >= maxElements;
        if (this.scrollbar != scrollbar) {
            this.scrollbar = scrollbar;
        }
        LiquidBounce.clickGui.style.drawPanel(mouseX, mouseY, this);
        int y = (this.f341y + this.height) - 2;
        int count = 0;
        for (Element element : this.elements) {
            count++;
            if (count > this.scroll && count < this.scroll + maxElements + 1 && this.scroll < this.elements.size()) {
                element.setLocation(this.f340x, y);
                element.setWidth(getWidth());
                if (y <= getY() + this.fade) {
                    element.drawScreen(mouseX, mouseY, button);
                }
                y += element.getHeight() + 1;
                element.setVisible(true);
            } else {
                element.setVisible(false);
            }
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible) {
            return false;
        }
        if (mouseButton == 1 && isHovering(mouseX, mouseY)) {
            this.open = !this.open;
            f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("random.bow"), 1.0f));
            return true;
        }
        for (Element element : this.elements) {
            if (element.getY() <= getY() + this.fade && element.mouseClicked(mouseX, mouseY, mouseButton)) {
                return true;
            }
        }
        return false;
    }

    public boolean mouseReleased(int mouseX, int mouseY, int state) {
        if (!this.visible) {
            return false;
        }
        this.drag = false;
        if (!this.open) {
            return false;
        }
        for (Element element : this.elements) {
            if (element.getY() <= getY() + this.fade && element.mouseReleased(mouseX, mouseY, state)) {
                return true;
            }
        }
        return false;
    }

    public boolean handleScroll(int mouseX, int mouseY, int wheel) {
        int maxElements = ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get().intValue();
        if (mouseX >= getX() && mouseX <= getX() + 100 && mouseY >= getY() && mouseY <= getY() + 19 + this.elementsHeight) {
            if (wheel < 0 && this.scroll < this.elements.size() - maxElements) {
                this.scroll++;
                if (this.scroll < 0) {
                    this.scroll = 0;
                }
            } else if (wheel > 0) {
                this.scroll--;
                if (this.scroll < 0) {
                    this.scroll = 0;
                }
            }
            if (wheel < 0) {
                if (this.dragged < this.elements.size() - maxElements) {
                    this.dragged++;
                    return true;
                }
                return true;
            } else if (wheel > 0 && this.dragged >= 1) {
                this.dragged--;
                return true;
            } else {
                return true;
            }
        }
        return false;
    }

    public void updateFade(int delta) {
        if (this.open) {
            if (this.fade < this.elementsHeight) {
                this.fade += 0.4f * delta;
            }
            if (this.fade > this.elementsHeight) {
                this.fade = (int) this.elementsHeight;
                return;
            }
            return;
        }
        if (this.fade > 0.0f) {
            this.fade -= 0.4f * delta;
        }
        if (this.fade < 0.0f) {
            this.fade = 0.0f;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getX() {
        return this.f340x;
    }

    public int getY() {
        return this.f341y;
    }

    public void setX(int dragX) {
        this.f340x = dragX;
    }

    public void setY(int dragY) {
        this.f341y = dragY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean getScrollbar() {
        return this.scrollbar;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean getOpen() {
        return this.open;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public List<Element> getElements() {
        return this.elements;
    }

    public int getFade() {
        return (int) this.fade;
    }

    public int getDragged() {
        return this.dragged;
    }

    private int getElementsHeight() {
        int height = 0;
        int count = 0;
        for (Element element : this.elements) {
            if (count < ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get().intValue()) {
                height += element.getHeight() + 1;
                count++;
            }
        }
        return height;
    }

    public boolean isHovering(int mouseX, int mouseY) {
        float textWidth = f362mc.field_71466_p.func_78256_a(StringUtils.func_76338_a(this.name)) - 100.0f;
        if (mouseX >= (this.f340x - (textWidth / 2.0f)) - 19.0f && mouseX <= (this.f340x - (textWidth / 2.0f)) + f362mc.field_71466_p.func_78256_a(StringUtils.func_76338_a(this.name)) + 19.0f && mouseY >= this.f341y) {
            if (mouseY <= (this.f341y + this.height) - (this.open ? 2 : 0)) {
                return true;
            }
        }
        return false;
    }
}
