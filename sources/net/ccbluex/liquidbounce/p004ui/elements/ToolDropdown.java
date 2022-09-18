package net.ccbluex.liquidbounce.p004ui.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: ToolDropdown.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0007\n��\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004J.\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004J \u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J8\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0002J\b\u0010\u0019\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��¨\u0006\u001a"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/elements/ToolDropdown;", "", "()V", "dropState", "", "expandIcon", "Lnet/minecraft/util/ResourceLocation;", "fullHeight", "", "drawCheckbox", "", "x", "y", "width", "state", "drawToggleSwitch", "height", "handleClick", "mouseX", "", "mouseY", "button", "Lnet/minecraft/client/gui/GuiButton;", "handleDraw", "isMouseOver", "toggleState", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.elements.ToolDropdown */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/elements/ToolDropdown.class */
public final class ToolDropdown {
    private static float fullHeight;
    private static boolean dropState;
    @NotNull
    public static final ToolDropdown INSTANCE = new ToolDropdown();
    @NotNull
    private static final ResourceLocation expandIcon = new ResourceLocation("liquidbounce+/expand.png");

    private ToolDropdown() {
    }

    @JvmStatic
    public static final void handleDraw(@NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        int gray = new Color(100, 100, 100).getRGB();
        float bWidth = button.func_146117_b();
        GL11.glPushMatrix();
        GL11.glTranslatef((button.field_146128_h + button.func_146117_b()) - 10.0f, button.field_146129_i + 10.0f, 0.0f);
        if (button.func_146115_a()) {
            GL11.glTranslatef(0.0f, dropState ? -1.0f : 1.0f, 0.0f);
        }
        GL11.glPushMatrix();
        GL11.glRotatef(180.0f * (fullHeight / 100.0f), 0.0f, 0.0f, 1.0f);
        RenderUtils.drawImage(expandIcon, -4, -4, 8, 8);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GlStateManager.func_179117_G();
        if (!dropState) {
            if (fullHeight == 0.0f) {
                return;
            }
        }
        ToolDropdown toolDropdown = INSTANCE;
        fullHeight = AnimationUtils.animate(dropState ? 100.0f : 0.0f, fullHeight, 0.01f * RenderUtils.deltaTime);
        GL11.glPushMatrix();
        RenderUtils.makeScissorBox(button.field_146128_h, button.field_146129_i + 20.0f, button.field_146128_h + bWidth, button.field_146129_i + 20.0f + fullHeight);
        GL11.glEnable(3089);
        GL11.glPushMatrix();
        GL11.glTranslatef(button.field_146128_h, (button.field_146129_i + 20.0f) - (100.0f - fullHeight), 0.0f);
        RenderUtils.newDrawRect(0.0f, 0.0f, bWidth, 100.0f, new Color(24, 24, 24).getRGB());
        Fonts.font35.drawString("AntiForge", 4.0f, 7.0f, -1);
        Fonts.font35.drawString("Block FML", 4.0f, 27.0f, AntiForge.enabled ? -1 : gray);
        Fonts.font35.drawString("Block FML Proxy Packets", 4.0f, 47.0f, AntiForge.enabled ? -1 : gray);
        Fonts.font35.drawString("Block Payload Packets", 4.0f, 67.0f, AntiForge.enabled ? -1 : gray);
        Fonts.font35.drawString("BungeeCord Spoof", 4.0f, 87.0f, -1);
        INSTANCE.drawToggleSwitch(bWidth - 24.0f, 5.0f, 20.0f, 10.0f, AntiForge.enabled);
        INSTANCE.drawCheckbox(bWidth - 14.0f, 25.0f, 10.0f, AntiForge.blockFML);
        INSTANCE.drawCheckbox(bWidth - 14.0f, 45.0f, 10.0f, AntiForge.blockProxyPacket);
        INSTANCE.drawCheckbox(bWidth - 14.0f, 65.0f, 10.0f, AntiForge.blockPayloadPackets);
        INSTANCE.drawToggleSwitch(bWidth - 24.0f, 85.0f, 20.0f, 10.0f, BungeeCordSpoof.enabled);
        GL11.glPopMatrix();
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    @JvmStatic
    public static final boolean handleClick(int mouseX, int mouseY, @NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        float bX = button.field_146128_h;
        float bY = button.field_146129_i;
        float bWidth = button.func_146117_b();
        if (dropState && INSTANCE.isMouseOver(mouseX, mouseY, bX, bY + 20.0f, bWidth, fullHeight)) {
            if (INSTANCE.isMouseOver(mouseX, mouseY, bX, bY + 20.0f, bWidth, 20.0f)) {
                AntiForge.enabled = !AntiForge.enabled;
            } else if (INSTANCE.isMouseOver(mouseX, mouseY, bX, bY + 40.0f, bWidth, 20.0f)) {
                AntiForge.blockFML = !AntiForge.blockFML;
            } else if (INSTANCE.isMouseOver(mouseX, mouseY, bX, bY + 60.0f, bWidth, 20.0f)) {
                AntiForge.blockProxyPacket = !AntiForge.blockProxyPacket;
            } else if (INSTANCE.isMouseOver(mouseX, mouseY, bX, bY + 80.0f, bWidth, 20.0f)) {
                AntiForge.blockPayloadPackets = !AntiForge.blockPayloadPackets;
            } else if (INSTANCE.isMouseOver(mouseX, mouseY, bX, bY + 100.0f, bWidth, 20.0f)) {
                BungeeCordSpoof.Companion companion = BungeeCordSpoof.Companion;
                BungeeCordSpoof.enabled = !BungeeCordSpoof.enabled;
            }
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
            return true;
        }
        return false;
    }

    private final boolean isMouseOver(int mouseX, int mouseY, float x, float y, float width, float height) {
        return ((float) mouseX) >= x && ((float) mouseX) < x + width && ((float) mouseY) >= y && ((float) mouseY) < y + height;
    }

    @JvmStatic
    public static final void toggleState() {
        ToolDropdown toolDropdown = INSTANCE;
        dropState = !dropState;
    }

    public final void drawToggleSwitch(float x, float y, float width, float height, boolean state) {
        int borderColor = state ? new Color(0, 140, 255).getRGB() : new Color(160, 160, 160).getRGB();
        int mainColor = state ? borderColor : new Color(24, 24, 24).getRGB();
        RenderUtils.originalRoundedRect(x - 0.5f, y - 0.5f, x + width + 0.5f, y + height + 0.5f, (height + 1.0f) / 2.0f, borderColor);
        RenderUtils.originalRoundedRect(x, y, x + width, y + height, height / 2.0f, mainColor);
        if (state) {
            RenderUtils.drawFilledCircle(((x + width) - 2.0f) - ((height - 4.0f) / 2.0f), y + 2.0f + ((height - 4.0f) / 2.0f), (height - 4.0f) / 2.0f, new Color(24, 24, 24));
        } else {
            RenderUtils.drawFilledCircle(x + 2.0f + ((height - 4.0f) / 2.0f), y + 2.0f + ((height - 4.0f) / 2.0f), (height - 4.0f) / 2.0f, new Color(160, 160, 160));
        }
    }

    public final void drawCheckbox(float x, float y, float width, boolean state) {
        int borderColor = state ? new Color(0, 140, 255).getRGB() : new Color(160, 160, 160).getRGB();
        int mainColor = state ? borderColor : new Color(24, 24, 24).getRGB();
        RenderUtils.originalRoundedRect(x - 0.5f, y - 0.5f, x + width + 0.5f, y + width + 0.5f, 3.0f, borderColor);
        RenderUtils.originalRoundedRect(x, y, x + width, y + width, 3.0f, mainColor);
        if (state) {
            GL11.glColor4f(0.094f, 0.094f, 0.094f, 1.0f);
            RenderUtils.drawLine(x + (width / 4.0f), y + (width / 2.0f), x + (width / 2.15f), y + ((width / 4.0f) * 3.0f), 2.0f);
            RenderUtils.drawLine(x + (width / 2.15f), y + ((width / 4.0f) * 3.0f), x + ((width / 3.95f) * 3.0f), y + (width / 3.0f), 2.0f);
            GlStateManager.func_179117_G();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
