package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.Patcher;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiNewChat.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiNewChat.class */
public abstract class MixinGuiNewChat {
    private float displayPercent;
    private int lineBeingDrawn;
    private int newLines;
    @Shadow
    @Final
    private Minecraft field_146247_f;
    @Shadow
    @Final
    private List<ChatLine> field_146253_i;
    @Shadow
    private int field_146250_j;
    @Shadow
    private boolean field_146251_k;
    @Shadow
    @Final
    private List<ChatLine> field_146252_h;
    private String lastMessage;
    private int sameMessageAmount;
    private int line;
    private HUD hud;
    private float animationPercent = 0.0f;
    private final HashMap<String, String> stringCache = new HashMap<>();

    @Shadow
    public abstract int func_146232_i();

    @Shadow
    public abstract boolean func_146241_e();

    @Shadow
    public abstract float func_146244_h();

    @Shadow
    public abstract int func_146228_f();

    @Shadow
    public abstract void func_146242_c(int i);

    @Shadow
    public abstract void func_146229_b(int i);

    @Shadow
    public abstract void func_146234_a(IChatComponent iChatComponent, int i);

    private void checkHud() {
        if (this.hud == null) {
            this.hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);
        }
    }

    @Redirect(method = {"deleteChatLine"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatLine;getChatLineID()I"))
    private int checkIfChatLineIsNull(ChatLine instance) {
        if (instance == null) {
            return -1;
        }
        return instance.func_74539_c();
    }

    @Overwrite
    public void func_146227_a(IChatComponent chatComponent) {
        checkHud();
        if (!this.hud.getChatCombineValue().get().booleanValue()) {
            func_146234_a(chatComponent, this.line);
            return;
        }
        String text = fixString(chatComponent.func_150254_d());
        if (text.equals(this.lastMessage)) {
            Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146242_c(this.line);
            this.sameMessageAmount++;
            this.lastMessage = text;
            chatComponent.func_150258_a(ChatFormatting.WHITE + " (x" + this.sameMessageAmount + ")");
        } else {
            this.sameMessageAmount = 1;
            this.lastMessage = text;
        }
        this.line++;
        if (this.line > 256) {
            this.line = 0;
        }
        func_146234_a(chatComponent, this.line);
    }

    @Inject(method = {"printChatMessageWithOptionalDeletion"}, m23at = {@AbstractC1790At("HEAD")})
    private void resetPercentage(CallbackInfo ci) {
        this.displayPercent = 0.0f;
    }

    @Overwrite
    public void func_146230_a(int updateCounter) {
        int j1;
        checkHud();
        boolean canFont = this.hud.getState() && this.hud.getFontChatValue().get().booleanValue();
        if (Patcher.chatPosition.get().booleanValue()) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(0.0f, -12.0f, 0.0f);
        }
        if (this.field_146247_f.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN) {
            int i = func_146232_i();
            boolean flag = false;
            int j = 0;
            int k = this.field_146253_i.size();
            float f = (this.field_146247_f.field_71474_y.field_74357_r * 0.9f) + 0.1f;
            if (k > 0) {
                if (func_146241_e()) {
                    flag = true;
                }
                if (this.field_146251_k || !this.hud.getState() || !this.hud.getChatAnimationValue().get().booleanValue()) {
                    this.displayPercent = 1.0f;
                } else if (this.displayPercent < 1.0f) {
                    this.displayPercent += (this.hud.getChatAnimationSpeedValue().get().floatValue() / 10.0f) * RenderUtils.deltaTime;
                    this.displayPercent = MathHelper.func_76131_a(this.displayPercent, 0.0f, 1.0f);
                }
                float t = this.displayPercent - 1.0f;
                this.animationPercent = MathHelper.func_76131_a(1.0f - (((t * t) * t) * t), 0.0f, 1.0f);
                float f1 = func_146244_h();
                int l = MathHelper.func_76123_f(func_146228_f() / f1);
                GlStateManager.func_179094_E();
                if (this.hud.getState() && this.hud.getChatAnimationValue().get().booleanValue()) {
                    GlStateManager.func_179109_b(0.0f, (1.0f - this.animationPercent) * 9.0f * func_146244_h(), 0.0f);
                }
                GlStateManager.func_179109_b(2.0f, 20.0f, 0.0f);
                GlStateManager.func_179152_a(f1, f1, 1.0f);
                for (int i1 = 0; i1 + this.field_146250_j < this.field_146253_i.size() && i1 < i; i1++) {
                    ChatLine chatline = this.field_146253_i.get(i1 + this.field_146250_j);
                    this.lineBeingDrawn = i1 + this.field_146250_j;
                    if (chatline != null && ((j1 = updateCounter - chatline.func_74540_b()) < 200 || flag)) {
                        double d0 = MathHelper.func_151237_a((1.0d - (j1 / 200.0d)) * 10.0d, 0.0d, 1.0d);
                        double d02 = d0 * d0;
                        int l1 = (int) (255.0d * d02);
                        if (flag) {
                            l1 = 255;
                        }
                        int l12 = (int) (l1 * f);
                        j++;
                        if (l12 > 3) {
                            int j2 = (-i1) * 9;
                            if (this.hud.getState() && this.hud.getChatRectValue().get().booleanValue()) {
                                if (!this.hud.getChatAnimationValue().get().booleanValue() || this.lineBeingDrawn > this.newLines || flag) {
                                    RenderUtils.drawRect(0, j2 - 9, 0 + l + 4, j2, (l12 / 2) << 24);
                                } else {
                                    RenderUtils.drawRect(0, j2 - 9, 0 + l + 4, j2, new Color(0.0f, 0.0f, 0.0f, this.animationPercent * (((float) d02) / 2.0f)).getRGB());
                                }
                            }
                            GlStateManager.func_179117_G();
                            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                            String s = fixString(chatline.func_151461_a().func_150254_d());
                            GlStateManager.func_179147_l();
                            if (!this.hud.getState() || !this.hud.getChatAnimationValue().get().booleanValue() || this.lineBeingDrawn > this.newLines) {
                                (canFont ? this.hud.getFontType().get() : this.field_146247_f.field_71466_p).func_175065_a(s, 0, j2 - 8, 16777215 + (l12 << 24), true);
                            } else {
                                (canFont ? this.hud.getFontType().get() : this.field_146247_f.field_71466_p).func_175065_a(s, 0, j2 - 8, new Color(1.0f, 1.0f, 1.0f, this.animationPercent * ((float) d02)).getRGB(), true);
                            }
                            GlStateManager.func_179118_c();
                            GlStateManager.func_179084_k();
                        }
                    }
                }
                if (flag) {
                    int i12 = this.field_146247_f.field_71466_p.field_78288_b;
                    GlStateManager.func_179109_b(-3.0f, 0.0f, 0.0f);
                    int l2 = (k * i12) + k;
                    int j12 = (j * i12) + j;
                    int j3 = (this.field_146250_j * j12) / k;
                    int k1 = (j12 * j12) / l2;
                    if (l2 != j12) {
                        int l13 = j3 > 0 ? 170 : 96;
                        int l3 = this.field_146251_k ? 13382451 : 3355562;
                        RenderUtils.drawRect(0.0f, -j3, 2.0f, (-j3) - k1, l3 + (l13 << 24));
                        RenderUtils.drawRect(2.0f, -j3, 1.0f, (-j3) - k1, 13421772 + (l13 << 24));
                    }
                }
                GlStateManager.func_179121_F();
            }
        }
        if (Patcher.chatPosition.get().booleanValue()) {
            GlStateManager.func_179121_F();
        }
    }

    private String fixString(String str) {
        char[] charArray;
        if (this.stringCache.containsKey(str)) {
            return this.stringCache.get(str);
        }
        String str2 = str.replaceAll("\uf8ff", "");
        StringBuilder sb = new StringBuilder();
        for (char c : str2.toCharArray()) {
            if (c > 65281 && c < 65376) {
                sb.append(Character.toChars(c - 65248));
            } else {
                sb.append(c);
            }
        }
        String result = sb.toString();
        this.stringCache.put(str2, result);
        return result;
    }

    @ModifyVariable(method = {"setChatLine"}, m18at = @AbstractC1790At("STORE"), ordinal = 0)
    private List<IChatComponent> setNewLines(List<IChatComponent> original) {
        this.newLines = original.size() - 1;
        return original;
    }

    @Overwrite
    public IChatComponent func_146236_a(int p_146236_1_, int p_146236_2_) {
        checkHud();
        boolean flagFont = this.hud.getState() && this.hud.getFontChatValue().get().booleanValue();
        if (!func_146241_e()) {
            return null;
        }
        ScaledResolution sc = new ScaledResolution(this.field_146247_f);
        int scaleFactor = sc.func_78325_e();
        float chatScale = func_146244_h();
        int mX = (p_146236_1_ / scaleFactor) - 3;
        int mY = ((p_146236_2_ / scaleFactor) - 27) - (Patcher.chatPosition.get().booleanValue() ? 12 : 0);
        int mX2 = MathHelper.func_76141_d(mX / chatScale);
        int mY2 = MathHelper.func_76141_d(mY / chatScale);
        if (mX2 >= 0 && mY2 >= 0) {
            int lineCount = Math.min(func_146232_i(), this.field_146253_i.size());
            if (mX2 > MathHelper.func_76141_d(func_146228_f() / func_146244_h())) {
                return null;
            }
            if (mY2 < ((flagFont ? this.hud.getFontType().get() : this.field_146247_f.field_71466_p).field_78288_b * lineCount) + lineCount) {
                int line = (mY2 / (flagFont ? this.hud.getFontType().get() : this.field_146247_f.field_71466_p).field_78288_b) + this.field_146250_j;
                if (line >= 0 && line < this.field_146253_i.size()) {
                    ChatLine chatLine = this.field_146253_i.get(line);
                    int maxWidth = 0;
                    for (ChatComponentText chatComponentText : chatLine.func_151461_a()) {
                        if (chatComponentText instanceof ChatComponentText) {
                            maxWidth += (flagFont ? this.hud.getFontType().get() : this.field_146247_f.field_71466_p).func_78256_a(GuiUtilRenderComponents.func_178909_a(chatComponentText.func_150265_g(), false));
                            if (maxWidth > mX2) {
                                return chatComponentText;
                            }
                        }
                    }
                    return null;
                }
                return null;
            }
            return null;
        }
        return null;
    }
}
