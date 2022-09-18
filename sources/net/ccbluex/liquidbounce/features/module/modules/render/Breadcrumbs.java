package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "Breadcrumbs", description = "Leaves a trail behind you.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Breadcrumbs.class */
public class Breadcrumbs extends Module {
    private double lastX;
    private double lastY;
    public final BoolValue unlimitedValue = new BoolValue("Unlimited", false);
    public final FloatValue lineWidth = new FloatValue("LineWidth", 0.0f, 1.0f, 10.0f);
    public final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
    public final IntegerValue colorGreenValue = new IntegerValue("G", 179, 0, 255);
    public final IntegerValue colorBlueValue = new IntegerValue("B", 72, 0, 255);
    public final IntegerValue fadeSpeedValue = new IntegerValue("Fade-Speed", 25, 0, 255);
    public final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    private final LinkedList<Dot> positions = new LinkedList<>();
    private double lastZ = 0.0d;

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        Color color = this.colorRainbow.get().booleanValue() ? ColorUtils.rainbow() : new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue());
        synchronized (this.positions) {
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            f362mc.field_71460_t.func_175072_h();
            GL11.glLineWidth(this.lineWidth.get().floatValue());
            GL11.glBegin(3);
            double renderPosX = f362mc.func_175598_ae().field_78730_l;
            double renderPosY = f362mc.func_175598_ae().field_78731_m;
            double renderPosZ = f362mc.func_175598_ae().field_78728_n;
            List<Dot> removeQueue = new ArrayList<>();
            Iterator<Dot> it = this.positions.iterator();
            while (it.hasNext()) {
                Dot dot = it.next();
                if (dot.alpha > 0) {
                    dot.render(color, renderPosX, renderPosY, renderPosZ, this.unlimitedValue.get().booleanValue() ? 0 : this.fadeSpeedValue.get().intValue());
                } else {
                    removeQueue.add(dot);
                }
            }
            for (Dot removeDot : removeQueue) {
                this.positions.remove(removeDot);
            }
            GL11.glColor4d(1.0d, 1.0d, 1.0d, 1.0d);
            GL11.glEnd();
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        synchronized (this.positions) {
            if (f362mc.field_71439_g.field_70165_t != this.lastX || f362mc.field_71439_g.func_174813_aQ().field_72338_b != this.lastY || f362mc.field_71439_g.field_70161_v != this.lastZ) {
                this.positions.add(new Dot(new double[]{f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b, f362mc.field_71439_g.field_70161_v}));
                this.lastX = f362mc.field_71439_g.field_70165_t;
                this.lastY = f362mc.field_71439_g.func_174813_aQ().field_72338_b;
                this.lastZ = f362mc.field_71439_g.field_70161_v;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        synchronized (this.positions) {
            this.positions.add(new Dot(new double[]{f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b + (f362mc.field_71439_g.func_70047_e() * 0.5f), f362mc.field_71439_g.field_70161_v}));
            this.positions.add(new Dot(new double[]{f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b, f362mc.field_71439_g.field_70161_v}));
        }
        super.onEnable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        synchronized (this.positions) {
            this.positions.clear();
        }
        super.onDisable();
    }

    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Breadcrumbs$Dot.class */
    class Dot {
        public int alpha = 255;
        private final double[] pos;

        public Dot(double[] position) {
            Breadcrumbs.this = this$0;
            this.pos = position;
        }

        public void render(Color color, double renderPosX, double renderPosY, double renderPosZ, int decreaseBy) {
            Color reColor = ColorUtils.reAlpha(color, this.alpha);
            RenderUtils.glColor(reColor);
            GL11.glVertex3d(this.pos[0] - renderPosX, this.pos[1] - renderPosY, this.pos[2] - renderPosZ);
            this.alpha -= decreaseBy;
            if (this.alpha < 0) {
                this.alpha = 0;
            }
        }
    }
}
