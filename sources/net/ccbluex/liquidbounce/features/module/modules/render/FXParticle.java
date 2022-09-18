package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: SuperheroFX.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n��\u0018��2\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\u0006\u0010\u001e\u001a\u00020\u001fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u000b\u0010\nR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u001a\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u001b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u001c\u0010\nR\u000e\u0010\u001d\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��¨\u0006 "}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/FXParticle;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "posX", "", "posY", "posZ", "animHDir", "animVDir", "(DDDDD)V", "getAnimHDir", "()D", "getAnimVDir", "canRemove", "", "getCanRemove", "()Z", "setCanRemove", "(Z)V", "color", "Ljava/awt/Color;", "fadeTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "firstDraw", "fontHeight", "messageString", "", "getPosX", "getPosY", "getPosZ", "stringLength", "draw", "", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/FXParticle.class */
public final class FXParticle extends MinecraftInstance {
    private final double posX;
    private final double posY;
    private final double posZ;
    private final double animHDir;
    private final double animVDir;
    @NotNull
    private final Color color;
    private boolean canRemove;
    @NotNull
    private final String messageString = (String) CollectionsKt.random(CollectionsKt.listOf((Object[]) new String[]{"kaboom", "bam", "zap", "smash", "fatality", "kapow", "wham"}), Random.Default);
    @NotNull
    private final MSTimer fadeTimer = new MSTimer();
    private final double stringLength = Fonts.fontBangers.func_78256_a(this.messageString);
    private final double fontHeight = Fonts.fontBangers.field_78288_b;
    private boolean firstDraw = true;

    public FXParticle(double posX, double posY, double posZ, double animHDir, double animVDir) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.animHDir = animHDir;
        this.animVDir = animVDir;
        Object random = CollectionsKt.random(CollectionsKt.listOf((Object[]) new Color[]{Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW}), Random.Default);
        Intrinsics.checkNotNullExpressionValue(random, "listOf(Color.BLUE, Color…D, Color.YELLOW).random()");
        this.color = (Color) random;
    }

    public final double getPosX() {
        return this.posX;
    }

    public final double getPosY() {
        return this.posY;
    }

    public final double getPosZ() {
        return this.posZ;
    }

    public final double getAnimHDir() {
        return this.animHDir;
    }

    public final double getAnimVDir() {
        return this.animVDir;
    }

    public final boolean getCanRemove() {
        return this.canRemove;
    }

    public final void setCanRemove(boolean z) {
        this.canRemove = z;
    }

    public final void draw() {
        RenderManager renderManager = MinecraftInstance.f362mc.func_175598_ae();
        if (renderManager == null) {
            return;
        }
        if (this.firstDraw) {
            this.fadeTimer.reset();
            this.firstDraw = false;
        }
        float alpha = RangesKt.coerceIn((float) (this.fadeTimer.hasTimePassed(250L) ? this.fadeTimer.hasTimeLeft(500L) : 250 - this.fadeTimer.hasTimeLeft(250L)), 0.0f, 250.0f) / 250.0f;
        float progress = RangesKt.coerceIn((float) (this.fadeTimer.hasTimePassed(250L) ? Math.abs(this.fadeTimer.hasTimeLeft(250L) - 250) : 250 - this.fadeTimer.hasTimeLeft(250L)), 0.0f, 500.0f) / 250.0f;
        float textY = MinecraftInstance.f362mc.field_71474_y.field_74320_O != 2 ? -1.0f : 1.0f;
        double offsetX = (this.stringLength / 2.0d) * 0.02d * progress;
        double offsetY = (this.fontHeight / 2.0d) * 0.02d * progress;
        if (progress >= 2.0f) {
            this.canRemove = true;
            return;
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179088_q();
        GlStateManager.func_179136_a(1.0f, -1500000.0f);
        GL11.glTranslated(((this.posX + (this.animHDir * progress)) - offsetX) - renderManager.field_78725_b, ((this.posY + (this.animVDir * progress)) - offsetY) - renderManager.field_78726_c, this.posZ - renderManager.field_78723_d);
        GlStateManager.func_179114_b(-renderManager.field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(progress * (-0.02f), progress * (-0.02f), progress * 0.02f);
        GlStateManager.func_179114_b(textY * renderManager.field_78732_j, 1.0f, 0.0f, 0.0f);
        GL11.glDepthMask(false);
        Fonts.fontBangers.drawString(this.messageString, 0.25f, 0.25f, new Color(0.0f, 0.0f, 0.0f, alpha * 0.75f).getRGB());
        Fonts.fontBangers.drawString(this.messageString, 0.0f, 0.0f, ColorUtils.reAlpha(this.color, alpha).getRGB());
        GL11.glColor4f(187.0f, 255.0f, 255.0f, 1.0f);
        GL11.glDepthMask(true);
        GlStateManager.func_179136_a(1.0f, 1500000.0f);
        GlStateManager.func_179113_r();
        GlStateManager.func_179121_F();
    }
}
