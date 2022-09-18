package net.ccbluex.liquidbounce.features.module.modules.player;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.Breadcrumbs;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import org.apache.log4j.Level;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "Blink", description = "Suspends all movement packets.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/Blink.class */
public class Blink extends Module {
    private boolean disableLogger;
    private final LinkedBlockingQueue<Packet> packets = new LinkedBlockingQueue<>();
    private EntityOtherPlayerMP fakePlayer = null;
    private final LinkedList<double[]> positions = new LinkedList<>();
    public final BoolValue pulseValue = new BoolValue("Pulse", false);
    private final BoolValue c0FValue = new BoolValue("C0FCancel", false);
    private final IntegerValue pulseDelayValue = new IntegerValue("PulseDelay", 1000, 500, (int) Level.TRACE_INT, "ms");
    private final MSTimer pulseTimer = new MSTimer();

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        if (!this.pulseValue.get().booleanValue()) {
            this.fakePlayer = new EntityOtherPlayerMP(f362mc.field_71441_e, f362mc.field_71439_g.func_146103_bH());
            this.fakePlayer.func_71049_a(f362mc.field_71439_g, true);
            this.fakePlayer.func_82149_j(f362mc.field_71439_g);
            this.fakePlayer.field_70759_as = f362mc.field_71439_g.field_70759_as;
            f362mc.field_71441_e.func_73027_a(-1337, this.fakePlayer);
        }
        synchronized (this.positions) {
            this.positions.add(new double[]{f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b + (f362mc.field_71439_g.func_70047_e() / 2.0f), f362mc.field_71439_g.field_70161_v});
            this.positions.add(new double[]{f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b, f362mc.field_71439_g.field_70161_v});
        }
        this.pulseTimer.reset();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        blink();
        if (this.fakePlayer != null) {
            f362mc.field_71441_e.func_73028_b(this.fakePlayer.func_145782_y());
            this.fakePlayer = null;
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        Packet<?> packet = event.getPacket();
        if (f362mc.field_71439_g == null || this.disableLogger) {
            return;
        }
        if (packet instanceof C03PacketPlayer) {
            event.cancelEvent();
        }
        if ((packet instanceof C03PacketPlayer.C04PacketPlayerPosition) || (packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) || (packet instanceof C08PacketPlayerBlockPlacement) || (packet instanceof C0APacketAnimation) || (packet instanceof C0BPacketEntityAction) || (packet instanceof C02PacketUseEntity) || (this.c0FValue.get().booleanValue() && (packet instanceof C0FPacketConfirmTransaction))) {
            event.cancelEvent();
            this.packets.add(packet);
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        synchronized (this.positions) {
            this.positions.add(new double[]{f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b, f362mc.field_71439_g.field_70161_v});
        }
        if (this.pulseValue.get().booleanValue() && this.pulseTimer.hasTimePassed(this.pulseDelayValue.get().intValue())) {
            blink();
            this.pulseTimer.reset();
        }
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        Breadcrumbs breadcrumbs = (Breadcrumbs) LiquidBounce.moduleManager.getModule(Breadcrumbs.class);
        Color color = breadcrumbs.colorRainbow.get().booleanValue() ? ColorUtils.rainbow() : new Color(breadcrumbs.colorRedValue.get().intValue(), breadcrumbs.colorGreenValue.get().intValue(), breadcrumbs.colorBlueValue.get().intValue());
        synchronized (this.positions) {
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            f362mc.field_71460_t.func_175072_h();
            GL11.glBegin(3);
            RenderUtils.glColor(color);
            double renderPosX = f362mc.func_175598_ae().field_78730_l;
            double renderPosY = f362mc.func_175598_ae().field_78731_m;
            double renderPosZ = f362mc.func_175598_ae().field_78728_n;
            Iterator<double[]> it = this.positions.iterator();
            while (it.hasNext()) {
                double[] pos = it.next();
                GL11.glVertex3d(pos[0] - renderPosX, pos[1] - renderPosY, pos[2] - renderPosZ);
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

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        return String.valueOf(this.packets.size());
    }

    private void blink() {
        try {
            this.disableLogger = true;
            while (!this.packets.isEmpty()) {
                f362mc.func_147114_u().func_147298_b().func_179290_a(this.packets.take());
            }
            this.disableLogger = false;
        } catch (Exception e) {
            e.printStackTrace();
            this.disableLogger = false;
        }
        synchronized (this.positions) {
            this.positions.clear();
        }
    }
}
