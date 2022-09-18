package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.player.InvManager;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiContainer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiContainer.class */
public abstract class MixinGuiContainer extends MixinGuiScreen {
    @Shadow
    protected int field_146999_f;
    @Shadow
    protected int field_147000_g;
    @Shadow
    protected int field_147003_i;
    @Shadow
    protected int field_147009_r;
    @Shadow
    private int field_146988_G;
    @Shadow
    private int field_146996_I;
    private GuiButton stealButton;
    private GuiButton chestStealerButton;
    private GuiButton invManagerButton;
    private GuiButton killAuraButton;
    private float progress = 0.0f;
    private long lastMS = 0;

    @Shadow
    protected abstract boolean func_146983_a(int i);

    @Inject(method = {"initGui"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    public void injectInitGui(CallbackInfo callbackInfo) {
        GuiScreen guiScreen = Minecraft.func_71410_x().field_71462_r;
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);
        int firstY = 0;
        if (guiScreen instanceof GuiChest) {
            String str = hud.getContainerButton().get();
            boolean z = true;
            switch (str.hashCode()) {
                case -913702425:
                    if (str.equals("TopRight")) {
                        z = true;
                        break;
                    }
                    break;
                case 524532444:
                    if (str.equals("TopLeft")) {
                        z = false;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    if (LiquidBounce.moduleManager.getModule(KillAura.class).getState()) {
                        List<GuiButton> list = this.field_146292_n;
                        GuiButton guiButton = new GuiButton(1024576, 5, 5, 140, 20, "Disable KillAura");
                        this.killAuraButton = guiButton;
                        list.add(guiButton);
                        firstY = 0 + 20;
                    }
                    if (LiquidBounce.moduleManager.getModule(InvManager.class).getState()) {
                        List<GuiButton> list2 = this.field_146292_n;
                        GuiButton guiButton2 = new GuiButton(321123, 5, 5 + firstY, 140, 20, "Disable InvManager");
                        this.invManagerButton = guiButton2;
                        list2.add(guiButton2);
                        firstY += 20;
                    }
                    if (LiquidBounce.moduleManager.getModule(ChestStealer.class).getState()) {
                        List<GuiButton> list3 = this.field_146292_n;
                        GuiButton guiButton3 = new GuiButton(727, 5, 5 + firstY, 140, 20, "Disable Stealer");
                        this.chestStealerButton = guiButton3;
                        list3.add(guiButton3);
                        firstY += 20;
                    }
                    List<GuiButton> list4 = this.field_146292_n;
                    GuiButton guiButton4 = new GuiButton(1234123, 5, 5 + firstY, 140, 20, "Steal this chest");
                    this.stealButton = guiButton4;
                    list4.add(guiButton4);
                    break;
                case true:
                    if (LiquidBounce.moduleManager.getModule(KillAura.class).getState()) {
                        List<GuiButton> list5 = this.field_146292_n;
                        GuiButton guiButton5 = new GuiButton(1024576, this.field_146294_l - 145, 5, 140, 20, "Disable KillAura");
                        this.killAuraButton = guiButton5;
                        list5.add(guiButton5);
                        firstY = 0 + 20;
                    }
                    if (LiquidBounce.moduleManager.getModule(InvManager.class).getState()) {
                        List<GuiButton> list6 = this.field_146292_n;
                        GuiButton guiButton6 = new GuiButton(321123, this.field_146294_l - 145, 5 + firstY, 140, 20, "Disable InvManager");
                        this.invManagerButton = guiButton6;
                        list6.add(guiButton6);
                        firstY += 20;
                    }
                    if (LiquidBounce.moduleManager.getModule(ChestStealer.class).getState()) {
                        List<GuiButton> list7 = this.field_146292_n;
                        GuiButton guiButton7 = new GuiButton(727, this.field_146294_l - 145, 5 + firstY, 140, 20, "Disable Stealer");
                        this.chestStealerButton = guiButton7;
                        list7.add(guiButton7);
                        firstY += 20;
                    }
                    List<GuiButton> list8 = this.field_146292_n;
                    GuiButton guiButton8 = new GuiButton(1234123, this.field_146294_l - 145, 5 + firstY, 140, 20, "Steal this chest");
                    this.stealButton = guiButton8;
                    list8.add(guiButton8);
                    break;
            }
        }
        this.lastMS = System.currentTimeMillis();
        this.progress = 0.0f;
    }

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiScreen
    protected void injectedActionPerformed(GuiButton button) {
        ChestStealer chestStealer = (ChestStealer) LiquidBounce.moduleManager.getModule(ChestStealer.class);
        if (button.field_146127_k == 1024576) {
            LiquidBounce.moduleManager.getModule(KillAura.class).setState(false);
        }
        if (button.field_146127_k == 321123) {
            LiquidBounce.moduleManager.getModule(InvManager.class).setState(false);
        }
        if (button.field_146127_k == 727) {
            chestStealer.setState(false);
        }
        if (button.field_146127_k == 1234123 && !chestStealer.getState()) {
            chestStealer.setContentReceived(this.field_146297_k.field_71439_g.field_71070_bA.field_75152_c);
            chestStealer.setOnce(true);
            chestStealer.setState(true);
        }
    }

    @Inject(method = {"drawScreen"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void drawScreenHead(CallbackInfo callbackInfo) {
        Animations animMod = (Animations) LiquidBounce.moduleManager.getModule(Animations.class);
        ChestStealer chestStealer = (ChestStealer) LiquidBounce.moduleManager.getModule(ChestStealer.class);
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);
        Minecraft mc = Minecraft.func_71410_x();
        if (this.progress >= 1.0f) {
            this.progress = 1.0f;
        } else {
            this.progress = ((float) (System.currentTimeMillis() - this.lastMS)) / 750.0f;
        }
        double trueAnim = EaseUtils.easeOutQuart(this.progress);
        if (hud.getContainerBackground().get().booleanValue() && (!(mc.field_71462_r instanceof GuiChest) || !chestStealer.getState() || !chestStealer.getSilenceValue().get().booleanValue() || !chestStealer.getStillDisplayValue().get().booleanValue())) {
            RenderUtils.drawGradientRect(0, 0, this.field_146294_l, this.field_146295_m, -1072689136, -804253680);
        }
        boolean checkFullSilence = chestStealer.getState() && chestStealer.getSilenceValue().get().booleanValue() && !chestStealer.getStillDisplayValue().get().booleanValue();
        if (animMod != null && animMod.getState() && (!(mc.field_71462_r instanceof GuiChest) || !checkFullSilence)) {
            GL11.glPushMatrix();
            String str = Animations.guiAnimations.get();
            boolean z = true;
            switch (str.hashCode()) {
                case -1814666802:
                    if (str.equals("Smooth")) {
                        z = true;
                        break;
                    }
                    break;
                case -1752886533:
                    if (str.equals("VSlide")) {
                        z = true;
                        break;
                    }
                    break;
                case 2791411:
                    if (str.equals("Zoom")) {
                        z = false;
                        break;
                    }
                    break;
                case 2017836355:
                    if (str.equals("HVSlide")) {
                        z = true;
                        break;
                    }
                    break;
                case 2141272649:
                    if (str.equals("HSlide")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    GL11.glTranslated((1.0d - trueAnim) * (this.field_146294_l / 2.0d), (1.0d - trueAnim) * (this.field_146295_m / 2.0d), 0.0d);
                    GL11.glScaled(trueAnim, trueAnim, trueAnim);
                    break;
                case true:
                    GL11.glTranslated((1.0d - trueAnim) * (-this.field_146294_l), 0.0d, 0.0d);
                    break;
                case true:
                    GL11.glTranslated(0.0d, (1.0d - trueAnim) * (-this.field_146295_m), 0.0d);
                    break;
                case true:
                    GL11.glTranslated((1.0d - trueAnim) * (-this.field_146294_l), (1.0d - trueAnim) * (-this.field_146295_m), 0.0d);
                    break;
                case true:
                    GL11.glTranslated((1.0d - trueAnim) * (-this.field_146294_l), ((1.0d - trueAnim) * (-this.field_146295_m)) / 4.0d, 0.0d);
                    break;
            }
        }
        try {
            GuiScreen guiScreen = mc.field_71462_r;
            if (this.stealButton != null) {
                this.stealButton.field_146124_l = !chestStealer.getState();
            }
            if (this.killAuraButton != null) {
                this.killAuraButton.field_146124_l = LiquidBounce.moduleManager.getModule(KillAura.class).getState();
            }
            if (this.chestStealerButton != null) {
                this.chestStealerButton.field_146124_l = chestStealer.getState();
            }
            if (this.invManagerButton != null) {
                this.invManagerButton.field_146124_l = LiquidBounce.moduleManager.getModule(InvManager.class).getState();
            }
            if (chestStealer.getState() && chestStealer.getSilenceValue().get().booleanValue() && (guiScreen instanceof GuiChest)) {
                mc.func_71381_h();
                mc.field_71462_r = guiScreen;
                if (chestStealer.getShowStringValue().get().booleanValue() && !chestStealer.getStillDisplayValue().get().booleanValue()) {
                    mc.field_71466_p.func_175065_a("Stealing... Press Esc to stop.", ((this.field_146294_l / 2) - (mc.field_71466_p.func_78256_a("Stealing... Press Esc to stop.") / 2)) - 1, (this.field_146295_m / 2) + 30, 0, false);
                    mc.field_71466_p.func_175065_a("Stealing... Press Esc to stop.", ((this.field_146294_l / 2) - (mc.field_71466_p.func_78256_a("Stealing... Press Esc to stop.") / 2)) + 1, (this.field_146295_m / 2) + 30, 0, false);
                    mc.field_71466_p.func_175065_a("Stealing... Press Esc to stop.", (this.field_146294_l / 2) - (mc.field_71466_p.func_78256_a("Stealing... Press Esc to stop.") / 2), ((this.field_146295_m / 2) + 30) - 1, 0, false);
                    mc.field_71466_p.func_175065_a("Stealing... Press Esc to stop.", (this.field_146294_l / 2) - (mc.field_71466_p.func_78256_a("Stealing... Press Esc to stop.") / 2), (this.field_146295_m / 2) + 30 + 1, 0, false);
                    mc.field_71466_p.func_175065_a("Stealing... Press Esc to stop.", (this.field_146294_l / 2) - (mc.field_71466_p.func_78256_a("Stealing... Press Esc to stop.") / 2), (this.field_146295_m / 2) + 30, -1, false);
                }
                if (!chestStealer.getOnce() && !chestStealer.getStillDisplayValue().get().booleanValue()) {
                    callbackInfo.cancel();
                }
            }
        } catch (Exception e) {
        }
    }

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiScreen
    protected boolean shouldRenderBackground() {
        return false;
    }

    @Inject(method = {"drawScreen"}, m23at = {@AbstractC1790At("RETURN")})
    public void drawScreenReturn(CallbackInfo callbackInfo) {
        Animations animMod = (Animations) LiquidBounce.moduleManager.getModule(Animations.class);
        ChestStealer chestStealer = (ChestStealer) LiquidBounce.moduleManager.getModule(ChestStealer.class);
        Minecraft mc = Minecraft.func_71410_x();
        boolean checkFullSilence = chestStealer.getState() && chestStealer.getSilenceValue().get().booleanValue() && !chestStealer.getStillDisplayValue().get().booleanValue();
        if (animMod == null || !animMod.getState()) {
            return;
        }
        if (!(mc.field_71462_r instanceof GuiChest) || !checkFullSilence) {
            GL11.glPopMatrix();
        }
    }

    @Inject(method = {"mouseClicked"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void checkCloseClick(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        if (mouseButton - 100 == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
            this.field_146297_k.field_71439_g.func_71053_j();
            ci.cancel();
        }
    }

    @Inject(method = {"mouseClicked"}, m23at = {@AbstractC1790At("TAIL")})
    private void checkHotbarClicks(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        func_146983_a(mouseButton - 100);
    }

    @Inject(method = {"updateDragSplitting"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;")}, cancellable = true)
    private void fixRemnants(CallbackInfo ci) {
        if (this.field_146988_G == 2) {
            this.field_146996_I = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_77976_d();
            ci.cancel();
        }
    }
}
