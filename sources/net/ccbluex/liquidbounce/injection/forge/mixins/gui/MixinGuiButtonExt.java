package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({GuiButtonExt.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiButtonExt.class */
public abstract class MixinGuiButtonExt extends GuiButton {
    private float bright;
    private float moveX = 0.0f;
    private float cut;
    private float alpha;

    public MixinGuiButtonExt(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, p_i1020_4_);
    }

    public MixinGuiButtonExt(int p_i46323_1_, int p_i46323_2_, int p_i46323_3_, int p_i46323_4_, int p_i46323_5_, String p_i46323_6_) {
        super(p_i46323_1_, p_i46323_2_, p_i46323_3_, p_i46323_4_, p_i46323_5_, p_i46323_6_);
    }

    @Overwrite
    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        int i;
        int i2;
        if (this.field_146125_m) {
            FontRenderer fontRenderer = mc.func_135016_M().func_135042_a() ? mc.field_71466_p : Fonts.font40;
            this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
            int delta = RenderUtils.deltaTime;
            float speedDelta = 0.01f * delta;
            HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);
            if (hud == null) {
                return;
            }
            if (this.field_146124_l && this.field_146123_n) {
                this.cut += 0.05f * delta;
                if (this.cut >= 4.0f) {
                    this.cut = 4.0f;
                }
                this.alpha += 0.3f * delta;
                if (this.alpha >= 210.0f) {
                    this.alpha = 210.0f;
                }
                this.moveX = AnimationUtils.animate(this.field_146120_f - 2.4f, this.moveX, speedDelta);
            } else {
                this.cut -= 0.05f * delta;
                if (this.cut <= 0.0f) {
                    this.cut = 0.0f;
                }
                this.alpha -= 0.3f * delta;
                if (this.alpha <= 120.0f) {
                    this.alpha = 120.0f;
                }
                this.moveX = AnimationUtils.animate(0.0f, this.moveX, speedDelta);
            }
            float roundCorner = Math.max(0.0f, (2.4f + this.moveX) - (this.field_146120_f - 2.4f));
            String lowerCase = hud.getGuiButtonStyle().get().toLowerCase();
            boolean z = true;
            switch (lowerCase.hashCode()) {
                case -1914759705:
                    if (lowerCase.equals("liquidbounce+")) {
                        z = true;
                        break;
                    }
                    break;
                case -615955772:
                    if (lowerCase.equals("liquidbounce")) {
                        z = true;
                        break;
                    }
                    break;
                case 695073197:
                    if (lowerCase.equals(Key.MINECRAFT_NAMESPACE)) {
                        z = false;
                        break;
                    }
                    break;
                case 1385468589:
                    if (lowerCase.equals("rounded")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    mc.func_110434_K().func_110577_a(field_146122_a);
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
                    int i3 = func_146114_a(this.field_146123_n);
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179120_a(770, 771, 1, 0);
                    GlStateManager.func_179112_b(770, 771);
                    func_73729_b(this.field_146128_h, this.field_146129_i, 0, 46 + (i3 * 20), this.field_146120_f / 2, this.field_146121_g);
                    func_73729_b(this.field_146128_h + (this.field_146120_f / 2), this.field_146129_i, 200 - (this.field_146120_f / 2), 46 + (i3 * 20), this.field_146120_f / 2, this.field_146121_g);
                    func_146119_b(mc, mouseX, mouseY);
                    int j = 14737632;
                    if (!this.field_146124_l) {
                        j = 10526880;
                    } else if (this.field_146123_n) {
                        j = 16777120;
                    }
                    func_73732_a(mc.field_71466_p, this.field_146126_j, this.field_146128_h + (this.field_146120_f / 2), this.field_146129_i + ((this.field_146121_g - 8) / 2), j);
                    break;
                case true:
                    int i4 = this.field_146128_h + ((int) this.cut);
                    int i5 = this.field_146129_i;
                    int i6 = (this.field_146128_h + this.field_146120_f) - ((int) this.cut);
                    int i7 = this.field_146129_i + this.field_146121_g;
                    if (this.field_146124_l) {
                        i2 = new Color(0.0f, 0.0f, 0.0f, this.alpha / 255.0f).getRGB();
                    } else {
                        i2 = new Color(0.5f, 0.5f, 0.5f, 0.5f).getRGB();
                    }
                    Gui.func_73734_a(i4, i5, i6, i7, i2);
                    break;
                case true:
                    float f = this.field_146128_h;
                    float f2 = this.field_146129_i;
                    float f3 = this.field_146128_h + this.field_146120_f;
                    float f4 = this.field_146129_i + this.field_146121_g;
                    if (this.field_146124_l) {
                        i = new Color(0.0f, 0.0f, 0.0f, this.alpha / 255.0f).getRGB();
                    } else {
                        i = new Color(0.5f, 0.5f, 0.5f, 0.5f).getRGB();
                    }
                    RenderUtils.originalRoundedRect(f, f2, f3, f4, 2.0f, i);
                    break;
                case true:
                    RenderUtils.drawRoundedRect(this.field_146128_h, this.field_146129_i, this.field_146128_h + this.field_146120_f, this.field_146129_i + this.field_146121_g, 2.4f, new Color(0, 0, 0, 150).getRGB());
                    RenderUtils.customRounded(this.field_146128_h, this.field_146129_i, this.field_146128_h + 2.4f + this.moveX, this.field_146129_i + this.field_146121_g, 2.4f, roundCorner, roundCorner, 2.4f, (this.field_146124_l ? new Color(0, 111, 255) : new Color(71, 71, 71)).getRGB());
                    break;
            }
            if (hud.getGuiButtonStyle().get().equalsIgnoreCase(Key.MINECRAFT_NAMESPACE)) {
                return;
            }
            mc.func_110434_K().func_110577_a(field_146122_a);
            func_146119_b(mc, mouseX, mouseY);
            fontRenderer.func_175063_a(this.field_146126_j, (this.field_146128_h + (this.field_146120_f / 2)) - (fontRenderer.func_78256_a(this.field_146126_j) / 2), (this.field_146129_i + ((this.field_146121_g - 5) / 2.0f)) - 2.0f, 14737632);
            GlStateManager.func_179117_G();
        }
    }
}
