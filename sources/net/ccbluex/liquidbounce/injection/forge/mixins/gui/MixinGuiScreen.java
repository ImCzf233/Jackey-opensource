package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.Collections;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.HoverDetails;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.p004ui.client.GuiBackground;
import net.ccbluex.liquidbounce.utils.render.ParticleUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.BackgroundShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiScreen.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiScreen.class */
public abstract class MixinGuiScreen {
    @Shadow
    public Minecraft field_146297_k;
    @Shadow
    protected List<GuiButton> field_146292_n;
    @Shadow
    public int field_146294_l;
    @Shadow
    public int field_146295_m;
    @Shadow
    protected FontRenderer field_146289_q;

    @Shadow
    public abstract void func_175272_a(IChatComponent iChatComponent, int i, int i2);

    @Shadow
    protected abstract void func_146283_a(List<String> list, int i, int i2);

    @Shadow
    public void func_73876_c() {
    }

    @Redirect(method = {"handleKeyboardInput"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z", remap = false))
    private boolean checkCharacter() {
        return (Keyboard.getEventKey() == 0 && Keyboard.getEventCharacter() >= ' ') || Keyboard.getEventKeyState();
    }

    @Inject(method = {"drawWorldBackground"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void drawWorldBackground(CallbackInfo callbackInfo) {
        if (!shouldRenderBackground()) {
            callbackInfo.cancel();
            return;
        }
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);
        if (hud.getInventoryParticle().get().booleanValue() && this.field_146297_k.field_71439_g != null) {
            ScaledResolution scaledResolution = new ScaledResolution(this.field_146297_k);
            int width = scaledResolution.func_78326_a();
            int height = scaledResolution.func_78328_b();
            ParticleUtils.drawParticles((Mouse.getX() * width) / this.field_146297_k.field_71443_c, (height - ((Mouse.getY() * height) / this.field_146297_k.field_71440_d)) - 1);
        }
    }

    @Inject(method = {"drawBackground"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void drawClientBackground(CallbackInfo callbackInfo) {
        GlStateManager.func_179140_f();
        GlStateManager.func_179106_n();
        if (GuiBackground.Companion.getEnabled()) {
            if (LiquidBounce.INSTANCE.getBackground() == null) {
                BackgroundShader.BACKGROUND_SHADER.startShader();
                Tessellator instance = Tessellator.func_178181_a();
                WorldRenderer worldRenderer = instance.func_178180_c();
                worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
                worldRenderer.func_181662_b(0.0d, this.field_146295_m, 0.0d).func_181675_d();
                worldRenderer.func_181662_b(this.field_146294_l, this.field_146295_m, 0.0d).func_181675_d();
                worldRenderer.func_181662_b(this.field_146294_l, 0.0d, 0.0d).func_181675_d();
                worldRenderer.func_181662_b(0.0d, 0.0d, 0.0d).func_181675_d();
                instance.func_78381_a();
                BackgroundShader.BACKGROUND_SHADER.stopShader();
            } else {
                ScaledResolution scaledResolution = new ScaledResolution(this.field_146297_k);
                int width = scaledResolution.func_78326_a();
                int height = scaledResolution.func_78328_b();
                this.field_146297_k.func_110434_K().func_110577_a(LiquidBounce.INSTANCE.getBackground());
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                Gui.func_152125_a(0, 0, 0.0f, 0.0f, width, height, width, height, width, height);
            }
            if (GuiBackground.Companion.getParticles()) {
                ParticleUtils.drawParticles((Mouse.getX() * this.field_146294_l) / this.field_146297_k.field_71443_c, (this.field_146295_m - ((Mouse.getY() * this.field_146295_m) / this.field_146297_k.field_71440_d)) - 1);
            }
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"drawBackground"}, m23at = {@AbstractC1790At("RETURN")})
    private void drawParticles(CallbackInfo callbackInfo) {
        if (GuiBackground.Companion.getParticles()) {
            ParticleUtils.drawParticles((Mouse.getX() * this.field_146294_l) / this.field_146297_k.field_71443_c, (this.field_146295_m - ((Mouse.getY() * this.field_146295_m) / this.field_146297_k.field_71440_d)) - 1);
        }
    }

    @Inject(method = {"sendChatMessage(Ljava/lang/String;Z)V"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void messageSend(String msg, boolean addToChat, CallbackInfo callbackInfo) {
        if (msg.startsWith(String.valueOf(LiquidBounce.commandManager.getPrefix())) && addToChat) {
            this.field_146297_k.field_71456_v.func_146158_b().func_146239_a(msg);
            LiquidBounce.commandManager.executeCommands(msg);
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"handleComponentHover"}, m23at = {@AbstractC1790At("HEAD")})
    private void handleHoverOverComponent(IChatComponent component, int x, int y, CallbackInfo callbackInfo) {
        if (component == null || component.func_150256_b().func_150235_h() == null || !LiquidBounce.moduleManager.getModule(HoverDetails.class).getState()) {
            return;
        }
        ChatStyle chatStyle = component.func_150256_b();
        ClickEvent clickEvent = chatStyle.func_150235_h();
        HoverEvent hoverEvent = chatStyle.func_150210_i();
        func_146283_a(Collections.singletonList("§c§l" + clickEvent.func_150669_a().func_150673_b().toUpperCase() + ": §a" + clickEvent.func_150668_b()), x, y - (hoverEvent != null ? 17 : 0));
    }

    @Inject(method = {"actionPerformed"}, m23at = {@AbstractC1790At("RETURN")})
    protected void injectActionPerformed(GuiButton button, CallbackInfo callbackInfo) {
        injectedActionPerformed(button);
    }

    protected boolean shouldRenderBackground() {
        return true;
    }

    protected void injectedActionPerformed(GuiButton button) {
    }
}
