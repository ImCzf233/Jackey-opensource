package net.ccbluex.liquidbounce.p004ui.client.clickgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.style.Style;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.style.styles.SlowlyStyle;
import net.ccbluex.liquidbounce.p004ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.p004ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

/* renamed from: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/clickgui/ClickGui.class */
public class ClickGui extends GuiScreen {
    private Panel clickedPanel;
    private int mouseX;
    private int mouseY;
    public double slide;
    public final List<Panel> panels = new ArrayList();
    private final ResourceLocation hudIcon = new ResourceLocation(LiquidBounce.CLIENT_NAME.toLowerCase() + "/custom_hud_icon.png");
    public Style style = new SlowlyStyle();
    public double progress = 0.0d;
    public long lastMS = System.currentTimeMillis();

    public ClickGui() {
        ModuleCategory[] values;
        int yPos = 5;
        for (final ModuleCategory category : ModuleCategory.values()) {
            this.panels.add(new Panel(category.getDisplayName(), 100, yPos, 100, 18, false) { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.1
                @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.Panel
                public void setupItems() {
                    Iterator<Module> it = LiquidBounce.moduleManager.getModules().iterator();
                    while (it.hasNext()) {
                        Module module = it.next();
                        if (module.getCategory() == category) {
                            getElements().add(new ModuleElement(module));
                        }
                    }
                }
            });
            yPos += 20;
        }
        this.panels.add(new Panel("Targets", 100, yPos + 20, 100, 18, false) { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2
            @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.Panel
            public void setupItems() {
                getElements().add(new ButtonElement("Players") { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2.1
                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement
                    public void createButton(String displayName) {
                        this.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(displayName);
                    }

                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement
                    public String getDisplayName() {
                        this.displayName = "Players";
                        this.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.Element
                    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
                        if (mouseButton == 0 && isHovering(mouseX, mouseY) && isVisible()) {
                            EntityUtils.targetPlayer = !EntityUtils.targetPlayer;
                            this.displayName = "Players";
                            this.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            return true;
                        }
                        return false;
                    }
                });
                getElements().add(new ButtonElement("Mobs") { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2.2
                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement
                    public void createButton(String displayName) {
                        this.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(displayName);
                    }

                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement
                    public String getDisplayName() {
                        this.displayName = "Mobs";
                        this.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.Element
                    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
                        if (mouseButton == 0 && isHovering(mouseX, mouseY) && isVisible()) {
                            EntityUtils.targetMobs = !EntityUtils.targetMobs;
                            this.displayName = "Mobs";
                            this.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            return true;
                        }
                        return false;
                    }
                });
                getElements().add(new ButtonElement("Animals") { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2.3
                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement
                    public void createButton(String displayName) {
                        this.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(displayName);
                    }

                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement
                    public String getDisplayName() {
                        this.displayName = "Animals";
                        this.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.Element
                    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
                        if (mouseButton == 0 && isHovering(mouseX, mouseY) && isVisible()) {
                            EntityUtils.targetAnimals = !EntityUtils.targetAnimals;
                            this.displayName = "Animals";
                            this.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            return true;
                        }
                        return false;
                    }
                });
                getElements().add(new ButtonElement("Invisible") { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2.4
                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement
                    public void createButton(String displayName) {
                        this.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(displayName);
                    }

                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement
                    public String getDisplayName() {
                        this.displayName = "Invisible";
                        this.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.Element
                    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
                        if (mouseButton == 0 && isHovering(mouseX, mouseY) && isVisible()) {
                            EntityUtils.targetInvisible = !EntityUtils.targetInvisible;
                            this.displayName = "Invisible";
                            this.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            return true;
                        }
                        return false;
                    }
                });
                getElements().add(new ButtonElement("Dead") { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2.5
                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement
                    public void createButton(String displayName) {
                        this.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(displayName);
                    }

                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement
                    public String getDisplayName() {
                        this.displayName = "Dead";
                        this.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.Element
                    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
                        if (mouseButton == 0 && isHovering(mouseX, mouseY) && isVisible()) {
                            EntityUtils.targetDead = !EntityUtils.targetDead;
                            this.displayName = "Dead";
                            this.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

    public void func_73866_w_() {
        this.progress = 0.0d;
        this.slide = 0.0d;
        this.lastMS = System.currentTimeMillis();
        super.func_73866_w_();
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        if (this.progress < 1.0d) {
            this.progress = ((float) (System.currentTimeMillis() - this.lastMS)) / (500.0f / ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).animSpeedValue.get().floatValue());
        } else {
            this.progress = 1.0d;
        }
        String lowerCase = ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).animationValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case -501060743:
                if (lowerCase.equals("slidebounce")) {
                    z = false;
                    break;
                }
                break;
            case -219959269:
                if (lowerCase.equals("zoombounce")) {
                    z = true;
                    break;
                }
                break;
            case 3387192:
                if (lowerCase.equals("none")) {
                    z = true;
                    break;
                }
                break;
            case 3744723:
                if (lowerCase.equals("zoom")) {
                    z = true;
                    break;
                }
                break;
            case 93332107:
                if (lowerCase.equals("azura")) {
                    z = true;
                    break;
                }
                break;
            case 109526449:
                if (lowerCase.equals("slide")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
            case true:
                this.slide = EaseUtils.easeOutBack(this.progress);
                break;
            case true:
            case true:
            case true:
                this.slide = EaseUtils.easeOutQuart(this.progress);
                break;
            case true:
                this.slide = 1.0d;
                break;
        }
        if (Mouse.isButtonDown(0) && mouseX >= 5 && mouseX <= 50 && mouseY <= this.field_146295_m - 5 && mouseY >= this.field_146295_m - 50) {
            this.field_146297_k.func_147108_a(new GuiHudDesigner());
        }
        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
        double scale = ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get().floatValue();
        int mouseX2 = (int) (mouseX / scale);
        int mouseY2 = (int) (mouseY / scale);
        this.mouseX = mouseX2;
        this.mouseY = mouseY2;
        String str = ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).backgroundValue.get();
        boolean z2 = true;
        switch (str.hashCode()) {
            case -1085510111:
                if (str.equals("Default")) {
                    z2 = false;
                    break;
                }
                break;
            case 154295120:
                if (str.equals("Gradient")) {
                    z2 = true;
                    break;
                }
                break;
        }
        switch (z2) {
            case false:
                func_146276_q_();
                break;
            case true:
                func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, ColorUtils.reAlpha(ClickGUI.generateColor(), ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).gradEndValue.get().intValue()).getRGB(), ColorUtils.reAlpha(ClickGUI.generateColor(), ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).gradStartValue.get().intValue()).getRGB());
                break;
        }
        GlStateManager.func_179118_c();
        RenderUtils.drawImage(this.hudIcon, 9, this.field_146295_m - 41, 32, 32);
        GlStateManager.func_179141_d();
        String lowerCase2 = ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).animationValue.get().toLowerCase();
        boolean z3 = true;
        switch (lowerCase2.hashCode()) {
            case -501060743:
                if (lowerCase2.equals("slidebounce")) {
                    z3 = true;
                    break;
                }
                break;
            case -219959269:
                if (lowerCase2.equals("zoombounce")) {
                    z3 = true;
                    break;
                }
                break;
            case 3387192:
                if (lowerCase2.equals("none")) {
                    z3 = true;
                    break;
                }
                break;
            case 3744723:
                if (lowerCase2.equals("zoom")) {
                    z3 = true;
                    break;
                }
                break;
            case 93332107:
                if (lowerCase2.equals("azura")) {
                    z3 = false;
                    break;
                }
                break;
            case 109526449:
                if (lowerCase2.equals("slide")) {
                    z3 = true;
                    break;
                }
                break;
        }
        switch (z3) {
            case false:
                GlStateManager.func_179137_b(0.0d, (1.0d - this.slide) * this.field_146295_m * 2.0d, 0.0d);
                GlStateManager.func_179139_a(scale, scale + ((1.0d - this.slide) * 2.0d), scale);
                break;
            case true:
            case true:
                GlStateManager.func_179137_b(0.0d, (1.0d - this.slide) * this.field_146295_m * 2.0d, 0.0d);
                GlStateManager.func_179139_a(scale, scale, scale);
                break;
            case true:
                GlStateManager.func_179137_b((1.0d - this.slide) * (this.field_146294_l / 2.0d), (1.0d - this.slide) * (this.field_146295_m / 2.0d), (1.0d - this.slide) * (this.field_146294_l / 2.0d));
                GlStateManager.func_179139_a(scale * this.slide, scale * this.slide, scale * this.slide);
                break;
            case true:
                GlStateManager.func_179137_b((1.0d - this.slide) * (this.field_146294_l / 2.0d), (1.0d - this.slide) * (this.field_146295_m / 2.0d), 0.0d);
                GlStateManager.func_179139_a(scale * this.slide, scale * this.slide, scale * this.slide);
                break;
            case true:
                GlStateManager.func_179139_a(scale, scale, scale);
                break;
        }
        for (Panel panel : this.panels) {
            panel.updateFade(RenderUtils.deltaTime);
            panel.drawScreen(mouseX2, mouseY2, partialTicks);
        }
        for (Panel panel2 : this.panels) {
            for (Element element : panel2.getElements()) {
                if (element instanceof ModuleElement) {
                    ModuleElement moduleElement = (ModuleElement) element;
                    if (mouseX2 != 0 && mouseY2 != 0 && moduleElement.isHovering(mouseX2, mouseY2) && moduleElement.isVisible() && element.getY() <= panel2.getY() + panel2.getFade()) {
                        this.style.drawDescription(mouseX2, mouseY2, moduleElement.getModule().getDescription());
                    }
                }
            }
        }
        GlStateManager.func_179140_f();
        RenderHelper.func_74518_a();
        String lowerCase3 = ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).animationValue.get().toLowerCase();
        boolean z4 = true;
        switch (lowerCase3.hashCode()) {
            case -501060743:
                if (lowerCase3.equals("slidebounce")) {
                    z4 = true;
                    break;
                }
                break;
            case -219959269:
                if (lowerCase3.equals("zoombounce")) {
                    z4 = true;
                    break;
                }
                break;
            case 3744723:
                if (lowerCase3.equals("zoom")) {
                    z4 = true;
                    break;
                }
                break;
            case 93332107:
                if (lowerCase3.equals("azura")) {
                    z4 = false;
                    break;
                }
                break;
            case 109526449:
                if (lowerCase3.equals("slide")) {
                    z4 = true;
                    break;
                }
                break;
        }
        switch (z4) {
            case false:
                GlStateManager.func_179137_b(0.0d, (1.0d - this.slide) * this.field_146295_m * (-2.0d), 0.0d);
                break;
            case true:
            case true:
                GlStateManager.func_179137_b(0.0d, (1.0d - this.slide) * this.field_146295_m * (-2.0d), 0.0d);
                break;
            case true:
                GlStateManager.func_179137_b((-1.0d) * (1.0d - this.slide) * (this.field_146294_l / 2.0d), (-1.0d) * (1.0d - this.slide) * (this.field_146295_m / 2.0d), (-1.0d) * (1.0d - this.slide) * (this.field_146294_l / 2.0d));
                break;
            case true:
                GlStateManager.func_179137_b((-1.0d) * (1.0d - this.slide) * (this.field_146294_l / 2.0d), (-1.0d) * (1.0d - this.slide) * (this.field_146295_m / 2.0d), 0.0d);
                break;
        }
        GlStateManager.func_179152_a(1.0f, 1.0f, 1.0f);
        AWTFontRenderer.Companion.setAssumeNonVolatile(false);
        super.func_73863_a(mouseX2, mouseY2, partialTicks);
    }

    public void func_146274_d() throws IOException {
        super.func_146274_d();
        int wheel = Mouse.getEventDWheel();
        for (int i = this.panels.size() - 1; i >= 0 && !this.panels.get(i).handleScroll(this.mouseX, this.mouseY, wheel); i--) {
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        double scale = ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get().floatValue();
        int mouseX2 = (int) (mouseX / scale);
        int mouseY2 = (int) (mouseY / scale);
        for (int i = this.panels.size() - 1; i >= 0 && !this.panels.get(i).mouseClicked(mouseX2, mouseY2, mouseButton); i--) {
        }
        Iterator<Panel> it = this.panels.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Panel panel = it.next();
            panel.drag = false;
            if (mouseButton == 0 && panel.isHovering(mouseX2, mouseY2)) {
                this.clickedPanel = panel;
                break;
            }
        }
        if (this.clickedPanel != null) {
            this.clickedPanel.f342x2 = this.clickedPanel.f340x - mouseX2;
            this.clickedPanel.f343y2 = this.clickedPanel.f341y - mouseY2;
            this.clickedPanel.drag = true;
            this.panels.remove(this.clickedPanel);
            this.panels.add(this.clickedPanel);
            this.clickedPanel = null;
        }
        super.func_73864_a(mouseX2, mouseY2, mouseButton);
    }

    protected void func_146286_b(int mouseX, int mouseY, int state) {
        double scale = ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get().floatValue();
        int mouseX2 = (int) (mouseX / scale);
        int mouseY2 = (int) (mouseY / scale);
        for (Panel panel : this.panels) {
            panel.mouseReleased(mouseX2, mouseY2, state);
        }
        super.func_146286_b(mouseX2, mouseY2, state);
    }

    public void func_73876_c() {
        for (Panel panel : this.panels) {
            for (Element element : panel.getElements()) {
                if (element instanceof ButtonElement) {
                    ButtonElement buttonElement = (ButtonElement) element;
                    if (buttonElement.isHovering(this.mouseX, this.mouseY)) {
                        if (buttonElement.hoverTime < 7) {
                            buttonElement.hoverTime++;
                        }
                    } else if (buttonElement.hoverTime > 0) {
                        buttonElement.hoverTime--;
                    }
                }
                if (element instanceof ModuleElement) {
                    if (((ModuleElement) element).getModule().getState()) {
                        if (((ModuleElement) element).slowlyFade < 255) {
                            ((ModuleElement) element).slowlyFade += 50;
                        }
                    } else if (((ModuleElement) element).slowlyFade > 0) {
                        ((ModuleElement) element).slowlyFade -= 50;
                    }
                    if (((ModuleElement) element).slowlyFade > 255) {
                        ((ModuleElement) element).slowlyFade = 255;
                    }
                    if (((ModuleElement) element).slowlyFade < 0) {
                        ((ModuleElement) element).slowlyFade = 0;
                    }
                }
            }
        }
        super.func_73876_c();
    }

    public void func_146281_b() {
        LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.clickGuiConfig);
    }

    public boolean func_73868_f() {
        return false;
    }
}
