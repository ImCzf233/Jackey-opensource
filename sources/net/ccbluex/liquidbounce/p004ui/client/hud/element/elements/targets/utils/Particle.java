package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: Particle.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��8\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0006\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018��2\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ8\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u00052\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u00052\b\b\u0002\u0010/\u001a\u00020,R\u001a\u0010\u000b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u000fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0016\u0010\r\"\u0004\b\u0017\u0010\u000fR\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\"\u0010\r\"\u0004\b#\u0010\u000fR\u001a\u0010$\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b%\u0010\r\"\u0004\b&\u0010\u000f¨\u00060"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/Particle;", "", "color", "Ljava/awt/Color;", "distX", "", "distY", "radius", "drawType", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/ShapeType;", "(Ljava/awt/Color;FFFLnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/ShapeType;)V", "alpha", "getAlpha", "()F", "setAlpha", "(F)V", "getColor", "()Ljava/awt/Color;", "setColor", "(Ljava/awt/Color;)V", "getDistX", "setDistX", "getDistY", "setDistY", "getDrawType", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/ShapeType;", "setDrawType", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/ShapeType;)V", "progress", "", "getProgress", "()D", "setProgress", "(D)V", "getRadius", "setRadius", "rotate", "getRotate", "setRotate", "render", "", "x", "y", "fade", "", "speed", "fadeSpeed", "canRotate", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.utils.Particle */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/Particle.class */
public final class Particle {
    @NotNull
    private Color color;
    private float distX;
    private float distY;
    private float radius;
    @NotNull
    private ShapeType drawType;
    private float alpha;
    private double progress;
    private float rotate;

    public Particle(@NotNull Color color, float distX, float distY, float radius, @NotNull ShapeType drawType) {
        Intrinsics.checkNotNullParameter(color, "color");
        Intrinsics.checkNotNullParameter(drawType, "drawType");
        this.color = color;
        this.distX = distX;
        this.distY = distY;
        this.radius = radius;
        this.drawType = drawType;
        this.alpha = 1.0f;
    }

    public /* synthetic */ Particle(Color color, float f, float f2, float f3, ShapeType shapeType, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(color, f, f2, f3, (i & 16) != 0 ? ShapeType.SOLID_CIRCLE : shapeType);
    }

    @NotNull
    public final Color getColor() {
        return this.color;
    }

    public final void setColor(@NotNull Color color) {
        Intrinsics.checkNotNullParameter(color, "<set-?>");
        this.color = color;
    }

    public final float getDistX() {
        return this.distX;
    }

    public final void setDistX(float f) {
        this.distX = f;
    }

    public final float getDistY() {
        return this.distY;
    }

    public final void setDistY(float f) {
        this.distY = f;
    }

    public final float getRadius() {
        return this.radius;
    }

    public final void setRadius(float f) {
        this.radius = f;
    }

    @NotNull
    public final ShapeType getDrawType() {
        return this.drawType;
    }

    public final void setDrawType(@NotNull ShapeType shapeType) {
        Intrinsics.checkNotNullParameter(shapeType, "<set-?>");
        this.drawType = shapeType;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    public final double getProgress() {
        return this.progress;
    }

    public final void setProgress(double d) {
        this.progress = d;
    }

    public final float getRotate() {
        return this.rotate;
    }

    public final void setRotate(float f) {
        this.rotate = f;
    }

    public static /* synthetic */ void render$default(Particle particle, float f, float f2, boolean z, float f3, float f4, boolean z2, int i, Object obj) {
        if ((i & 32) != 0) {
            z2 = false;
        }
        particle.render(f, f2, z, f3, f4, z2);
    }

    public final void render(float x, float y, boolean fade, float speed, float fadeSpeed, boolean canRotate) {
        if (this.progress >= 1.0d) {
            this.progress = 1.0d;
            if (fade) {
                this.alpha -= (fadeSpeed * 0.02f) * RenderUtils.deltaTime;
            }
            if (this.alpha < 0.0f) {
                this.alpha = 0.0f;
            }
        } else {
            this.progress += speed * 0.025f * RenderUtils.deltaTime;
        }
        if (this.alpha <= 0.0f) {
            return;
        }
        Color reColored = new Color(this.color.getRed() / 255.0f, this.color.getGreen() / 255.0f, this.color.getBlue() / 255.0f, this.alpha);
        float easeOut = (float) EaseUtils.easeOutQuart(this.progress);
        if (canRotate && this.drawType != ShapeType.SOLID_CIRCLE && this.drawType != ShapeType.CIRCLE) {
            this.rotate += 10.0f * (1.0f - easeOut);
            GL11.glPushMatrix();
            GL11.glTranslatef(x + (this.distX * easeOut), y + (this.distY * easeOut), 0.0f);
            GL11.glPushMatrix();
            GL11.glRotatef(this.rotate, 0.0f, 0.0f, 1.0f);
            this.drawType.performRendering(0.0f, 0.0f, this.radius, reColored);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            return;
        }
        this.drawType.performRendering(x + (this.distX * easeOut), y + (this.distY * easeOut), this.radius, reColored);
    }
}
