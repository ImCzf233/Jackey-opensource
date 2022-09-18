package net.ccbluex.liquidbounce.p004ui.client.hud.designer;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p004ui.client.hud.HUD;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/* compiled from: GuiHudDesigner.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000eH\u0016J\u0018\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0014J \u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0014J \u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u0010H\u0014J\b\u0010\u001d\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u001e"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;", "Lnet/minecraft/client/gui/GuiScreen;", "()V", "buttonAction", "", "editorPanel", "Lnet/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel;", "selectedElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "getSelectedElement", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "setSelectedElement", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;)V", "drawScreen", "", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "mouseReleased", "state", "onGuiClosed", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.designer.GuiHudDesigner */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner.class */
public final class GuiHudDesigner extends GuiScreen {
    @NotNull
    private EditorPanel editorPanel = new EditorPanel(this, 2, 2);
    @Nullable
    private Element selectedElement;
    private boolean buttonAction;

    @Nullable
    public final Element getSelectedElement() {
        return this.selectedElement;
    }

    public final void setSelectedElement(@Nullable Element element) {
        this.selectedElement = element;
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.editorPanel = new EditorPanel(this, this.field_146294_l / 2, this.field_146295_m / 2);
        super.func_73866_w_();
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        LiquidBounce.INSTANCE.getHud().render(true);
        LiquidBounce.INSTANCE.getHud().handleMouseMove(mouseX, mouseY);
        if (!CollectionsKt.contains(LiquidBounce.INSTANCE.getHud().getElements(), this.selectedElement)) {
            this.selectedElement = null;
        }
        int wheel = Mouse.getDWheel();
        this.editorPanel.drawPanel(mouseX, mouseY, wheel);
        if (wheel != 0) {
            Iterator<Element> it = LiquidBounce.INSTANCE.getHud().getElements().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Element element = it.next();
                if (element.isInBorder((mouseX / element.getScale()) - element.getRenderX(), (mouseY / element.getScale()) - element.getRenderY())) {
                    element.setScale(element.getScale() + (wheel > 0 ? 0.05f : -0.05f));
                }
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (this.buttonAction) {
            this.buttonAction = false;
            return;
        }
        LiquidBounce.INSTANCE.getHud().handleMouseClick(mouseX, mouseY, mouseButton);
        if (mouseX < this.editorPanel.getX() || mouseX > this.editorPanel.getX() + this.editorPanel.getWidth() || mouseY < this.editorPanel.getY() || mouseY > this.editorPanel.getY() + Math.min(this.editorPanel.getRealHeight(), 200)) {
            this.selectedElement = null;
            this.editorPanel.setCreate(false);
        }
        if (mouseButton == 0) {
            for (Element element : LiquidBounce.INSTANCE.getHud().getElements()) {
                if (element.isInBorder((mouseX / element.getScale()) - element.getRenderX(), (mouseY / element.getScale()) - element.getRenderY())) {
                    this.selectedElement = element;
                    return;
                }
            }
        }
    }

    protected void func_146286_b(int mouseX, int mouseY, int state) {
        super.func_146286_b(mouseX, mouseY, state);
        LiquidBounce.INSTANCE.getHud().handleMouseReleased();
    }

    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
        super.func_146281_b();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        switch (keyCode) {
            case 1:
                this.selectedElement = null;
                this.editorPanel.setCreate(false);
                break;
            case 211:
                if (211 == keyCode && this.selectedElement != null) {
                    HUD hud = LiquidBounce.INSTANCE.getHud();
                    Element element = this.selectedElement;
                    Intrinsics.checkNotNull(element);
                    hud.removeElement(element);
                    break;
                }
                break;
            default:
                LiquidBounce.INSTANCE.getHud().handleKey(typedChar, keyCode);
                break;
        }
        super.func_73869_a(typedChar, keyCode);
    }
}
