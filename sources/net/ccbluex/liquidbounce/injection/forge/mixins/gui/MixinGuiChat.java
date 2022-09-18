package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiChat.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiChat.class */
public abstract class MixinGuiChat extends MixinGuiScreen {
    @Shadow
    protected GuiTextField field_146415_a;
    @Shadow
    private List<String> field_146412_t;
    @Shadow
    private boolean field_146414_r;
    private float yPosOfInputField;
    private float fade = 0.0f;

    @Shadow
    public abstract void func_146406_a(String[] strArr);

    @Inject(method = {"initGui"}, m23at = {@AbstractC1790At("RETURN")})
    private void init(CallbackInfo callbackInfo) {
        this.field_146415_a.field_146210_g = this.field_146295_m + 1;
        this.yPosOfInputField = this.field_146415_a.field_146210_g;
    }

    @Inject(method = {"keyTyped"}, m23at = {@AbstractC1790At("RETURN")})
    private void updateLength(CallbackInfo callbackInfo) {
        if (!this.field_146415_a.func_146179_b().startsWith(String.valueOf(LiquidBounce.commandManager.getPrefix()))) {
            return;
        }
        LiquidBounce.commandManager.autoComplete(this.field_146415_a.func_146179_b());
        if (!this.field_146415_a.func_146179_b().startsWith(LiquidBounce.commandManager.getPrefix() + "lc")) {
            this.field_146415_a.func_146203_f(10000);
        } else {
            this.field_146415_a.func_146203_f(100);
        }
    }

    @Inject(method = {"updateScreen"}, m23at = {@AbstractC1790At("HEAD")})
    private void updateScreen(CallbackInfo callbackInfo) {
        int delta = RenderUtils.deltaTime;
        if (this.fade < 14.0f) {
            this.fade = AnimationUtils.animate(14.0f, this.fade, 0.025f * delta);
        }
        if (this.fade > 14.0f) {
            this.fade = 14.0f;
        }
        if (this.yPosOfInputField > this.field_146295_m - 12) {
            this.yPosOfInputField = AnimationUtils.animate(this.field_146295_m - 12, this.yPosOfInputField, 0.021428572f * delta);
        }
        if (this.yPosOfInputField < this.field_146295_m - 12) {
            this.yPosOfInputField = this.field_146295_m - 12;
        }
        this.field_146415_a.field_146210_g = (int) this.yPosOfInputField;
    }

    @Inject(method = {"autocompletePlayerNames"}, m23at = {@AbstractC1790At("HEAD")})
    private void prioritizeClientFriends(CallbackInfo callbackInfo) {
        this.field_146412_t.sort(Comparator.comparing(s -> {
            return Boolean.valueOf(!LiquidBounce.fileManager.friendsConfig.isFriend(s));
        }));
    }

    @Inject(method = {"sendAutocompleteRequest"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void handleClientCommandCompletion(String full, String ignored, CallbackInfo callbackInfo) {
        if (LiquidBounce.commandManager.autoComplete(full)) {
            this.field_146414_r = true;
            String[] latestAutoComplete = LiquidBounce.commandManager.getLatestAutoComplete();
            if (full.toLowerCase().endsWith(latestAutoComplete[latestAutoComplete.length - 1].toLowerCase())) {
                return;
            }
            func_146406_a(latestAutoComplete);
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"onAutocompleteResponse"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiChat;autocompletePlayerNames(F)V", shift = AbstractC1790At.Shift.BEFORE)}, cancellable = true)
    private void onAutocompleteResponse(String[] autoCompleteResponse, CallbackInfo callbackInfo) {
        if (LiquidBounce.commandManager.getLatestAutoComplete().length != 0) {
            callbackInfo.cancel();
        }
    }

    @Overwrite
    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);
        if (hud.getCmdBorderValue().get().booleanValue() && !this.field_146415_a.func_146179_b().isEmpty() && this.field_146415_a.func_146179_b().startsWith(String.valueOf(LiquidBounce.commandManager.getPrefix()))) {
            Stencil.write(true);
            RenderUtils.drawRect(2.0f, this.field_146295_m - this.fade, this.field_146294_l - 2, (this.field_146295_m - this.fade) + 12.0f, Integer.MIN_VALUE);
            Stencil.erase(false);
            RenderUtils.drawRect(1.0f, (this.field_146295_m - this.fade) - 1.0f, this.field_146294_l - 1, (this.field_146295_m - this.fade) + 13.0f, new Color(20, 110, 255).getRGB());
            Stencil.dispose();
        } else {
            RenderUtils.drawRect(2.0f, this.field_146295_m - this.fade, this.field_146294_l - 2, (this.field_146295_m - this.fade) + 12.0f, Integer.MIN_VALUE);
        }
        this.field_146415_a.func_146194_f();
        if (LiquidBounce.commandManager.getLatestAutoComplete().length > 0 && !this.field_146415_a.func_146179_b().isEmpty() && this.field_146415_a.func_146179_b().startsWith(String.valueOf(LiquidBounce.commandManager.getPrefix()))) {
            String[] latestAutoComplete = LiquidBounce.commandManager.getLatestAutoComplete();
            String[] textArray = this.field_146415_a.func_146179_b().split(" ");
            String trimmedString = latestAutoComplete[0].replaceFirst("(?i)" + textArray[textArray.length - 1], "");
            this.field_146297_k.field_71466_p.func_175063_a(trimmedString, this.field_146415_a.field_146209_f + this.field_146297_k.field_71466_p.func_78256_a(this.field_146415_a.func_146179_b()), this.field_146415_a.field_146210_g, new Color(165, 165, 165).getRGB());
        }
        IChatComponent ichatcomponent = this.field_146297_k.field_71456_v.func_146158_b().func_146236_a(Mouse.getX(), Mouse.getY());
        if (ichatcomponent != null) {
            func_175272_a(ichatcomponent, mouseX, mouseY);
        }
    }
}
