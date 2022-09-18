package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiIngameForge.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiIngameForge.class */
public abstract class MixinGuiIngameForge extends MixinGuiInGame {
    public float xScale = 0.0f;

    @Shadow(remap = false)
    abstract boolean pre(RenderGameOverlayEvent.ElementType elementType);

    @Shadow(remap = false)
    abstract void post(RenderGameOverlayEvent.ElementType elementType);

    @Inject(method = {"renderChat"}, slice = {@Slice(from = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/eventhandler/EventBus;post(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", ordinal = 0, remap = false))}, m23at = {@AbstractC1790At(value = "RETURN", ordinal = 0)}, remap = false)
    private void fixProfilerSectionNotEnding(int width, int height, CallbackInfo ci) {
        Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71424_I.func_76322_c().endsWith("chat")) {
            mc.field_71424_I.func_76319_b();
        }
    }

    @Inject(method = {"renderExperience"}, m23at = {@AbstractC1790At("HEAD")}, remap = false)
    private void enableExperienceAlpha(int filled, int top, CallbackInfo ci) {
        GlStateManager.func_179141_d();
    }

    @Inject(method = {"renderExperience"}, m23at = {@AbstractC1790At("RETURN")}, remap = false)
    private void disableExperienceAlpha(int filled, int top, CallbackInfo ci) {
        GlStateManager.func_179118_c();
    }

    @Overwrite(remap = false)
    protected void renderPlayerList(int width, int height) {
        Minecraft mc = Minecraft.func_71410_x();
        ScoreObjective scoreobjective = mc.field_71441_e.func_96441_U().func_96539_a(0);
        NetHandlerPlayClient handler = mc.field_71439_g.field_71174_a;
        if (!mc.func_71387_A() || handler.func_175106_d().size() > 1 || scoreobjective != null) {
            this.xScale = AnimationUtils.animate(mc.field_71474_y.field_74321_H.func_151470_d() ? 100.0f : 0.0f, this.xScale, Animations.tabAnimations.get().equalsIgnoreCase("none") ? 1.0f : 0.0125f * RenderUtils.deltaTime);
            float rescaled = this.xScale / 100.0f;
            boolean displayable = rescaled > 0.0f;
            this.field_175196_v.func_175246_a(displayable);
            if (!displayable || pre(RenderGameOverlayEvent.ElementType.PLAYER_LIST)) {
                return;
            }
            GlStateManager.func_179094_E();
            String lowerCase = Animations.tabAnimations.get().toLowerCase();
            boolean z = true;
            switch (lowerCase.hashCode()) {
                case 3387192:
                    if (lowerCase.equals("none")) {
                        z = true;
                        break;
                    }
                    break;
                case 3744723:
                    if (lowerCase.equals("zoom")) {
                        z = false;
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
                    GlStateManager.func_179109_b((width / 2.0f) * (1.0f - rescaled), 0.0f, 0.0f);
                    GlStateManager.func_179152_a(rescaled, rescaled, rescaled);
                    break;
                case true:
                    GlStateManager.func_179152_a(1.0f, rescaled, 1.0f);
                    break;
            }
            this.field_175196_v.func_175249_a(width, mc.field_71441_e.func_96441_U(), scoreobjective);
            GlStateManager.func_179121_F();
            post(RenderGameOverlayEvent.ElementType.PLAYER_LIST);
            return;
        }
        this.field_175196_v.func_175246_a(false);
    }
}
