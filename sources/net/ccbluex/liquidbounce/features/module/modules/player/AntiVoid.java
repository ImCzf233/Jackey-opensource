package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Iterator;
import java.util.LinkedList;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.NewFallingPlayer;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "AntiVoid", spacedName = "Anti Void", description = "Prevents you from falling into the void.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/AntiVoid.class */
public class AntiVoid extends Module {
    private boolean shouldRender;
    private boolean shouldStopMotion;
    public final ListValue voidDetectionAlgorithm = new ListValue("Detect-Method", new String[]{"Collision", "Predict"}, "Collision");
    public final ListValue setBackModeValue = new ListValue("SetBack-Mode", new String[]{"Teleport", "FlyFlag", "IllegalPacket", "IllegalTeleport", "StopMotion", "Position", "Edit", "SpoofBack"}, "Teleport");
    public final IntegerValue maxFallDistSimulateValue = new IntegerValue("Predict-CheckFallDistance", 255, 0, 255, "m", () -> {
        return Boolean.valueOf(this.voidDetectionAlgorithm.get().equalsIgnoreCase("predict"));
    });
    public final IntegerValue maxFindRangeValue = new IntegerValue("Predict-MaxFindRange", 60, 0, 255, "m", () -> {
        return Boolean.valueOf(this.voidDetectionAlgorithm.get().equalsIgnoreCase("predict"));
    });
    public final IntegerValue illegalDupeValue = new IntegerValue("Illegal-Dupe", 1, 1, 5, "x", () -> {
        return Boolean.valueOf(this.setBackModeValue.get().toLowerCase().contains("illegal"));
    });
    public final FloatValue setBackFallDistValue = new FloatValue("Max-FallDistance", 5.0f, 0.0f, 255.0f, "m");
    public final BoolValue resetFallDistanceValue = new BoolValue("Reset-FallDistance", true);
    public final BoolValue renderTraceValue = new BoolValue("Render-Trace", true);
    public final BoolValue scaffoldValue = new BoolValue("AutoScaffold", true);
    public final BoolValue noFlyValue = new BoolValue("NoFly", true);
    private BlockPos detectedLocation = BlockPos.field_177992_a;
    private double lastX = 0.0d;
    private double lastY = 0.0d;
    private double lastZ = 0.0d;
    private double lastFound = 0.0d;
    private boolean shouldEdit = false;
    private final LinkedList<double[]> positions = new LinkedList<>();

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (this.noFlyValue.get().booleanValue() && LiquidBounce.moduleManager.getModule(Fly.class).getState()) {
            return;
        }
        this.detectedLocation = null;
        if (this.voidDetectionAlgorithm.get().equalsIgnoreCase("collision")) {
            if (f362mc.field_71439_g.field_70122_E && !(BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u - 1.0d, f362mc.field_71439_g.field_70161_v)) instanceof BlockAir)) {
                this.lastX = f362mc.field_71439_g.field_70169_q;
                this.lastY = f362mc.field_71439_g.field_70167_r;
                this.lastZ = f362mc.field_71439_g.field_70166_s;
            }
            this.shouldRender = this.renderTraceValue.get().booleanValue() && !MovementUtils.isBlockUnder();
            this.shouldStopMotion = false;
            this.shouldEdit = false;
            if (!MovementUtils.isBlockUnder() && f362mc.field_71439_g.field_70143_R >= this.setBackFallDistValue.get().floatValue()) {
                this.shouldStopMotion = true;
                String str = this.setBackModeValue.get();
                boolean z = true;
                switch (str.hashCode()) {
                    case -1295557813:
                        if (str.equals("Teleport")) {
                            z = true;
                            break;
                        }
                        break;
                    case 2155050:
                        if (str.equals("Edit")) {
                            z = true;
                            break;
                        }
                        break;
                    case 468059768:
                        if (str.equals("StopMotion")) {
                            z = true;
                            break;
                        }
                        break;
                    case 812449097:
                        if (str.equals("Position")) {
                            z = true;
                            break;
                        }
                        break;
                    case 906634847:
                        if (str.equals("FlyFlag")) {
                            z = true;
                            break;
                        }
                        break;
                    case 934155120:
                        if (str.equals("SpoofBack")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1224560801:
                        if (str.equals("IllegalTeleport")) {
                            z = false;
                            break;
                        }
                        break;
                    case 1455983486:
                        if (str.equals("IllegalPacket")) {
                            z = true;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case false:
                        f362mc.field_71439_g.func_70634_a(this.lastX, this.lastY, this.lastZ);
                    case true:
                        for (int i = 0; i < this.illegalDupeValue.get().intValue(); i++) {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u - 1.0E159d, f362mc.field_71439_g.field_70161_v, false));
                        }
                        break;
                    case true:
                        f362mc.field_71439_g.func_70634_a(this.lastX, this.lastY, this.lastZ);
                        break;
                    case true:
                        f362mc.field_71439_g.field_70181_x = 0.0d;
                        break;
                    case true:
                        float oldFallDist = f362mc.field_71439_g.field_70143_R;
                        f362mc.field_71439_g.field_70181_x = 0.0d;
                        f362mc.field_71439_g.field_70143_R = oldFallDist;
                        break;
                    case true:
                        PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + RandomUtils.nextDouble(6.0d, 10.0d), f362mc.field_71439_g.field_70161_v, f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70125_A, false));
                        break;
                    case true:
                    case true:
                        this.shouldEdit = true;
                        break;
                }
                if (this.resetFallDistanceValue.get().booleanValue() && !this.setBackModeValue.get().equalsIgnoreCase("StopMotion")) {
                    f362mc.field_71439_g.field_70143_R = 0.0f;
                }
                if (this.scaffoldValue.get().booleanValue() && !LiquidBounce.moduleManager.getModule(Scaffold.class).getState()) {
                    LiquidBounce.moduleManager.getModule(Scaffold.class).setState(true);
                }
            }
        } else {
            if (f362mc.field_71439_g.field_70122_E && !(BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u - 1.0d, f362mc.field_71439_g.field_70161_v)) instanceof BlockAir)) {
                this.lastX = f362mc.field_71439_g.field_70169_q;
                this.lastY = f362mc.field_71439_g.field_70167_r;
                this.lastZ = f362mc.field_71439_g.field_70166_s;
            }
            this.shouldStopMotion = false;
            this.shouldEdit = false;
            this.shouldRender = false;
            if (!f362mc.field_71439_g.field_70122_E && !f362mc.field_71439_g.func_70617_f_() && !f362mc.field_71439_g.func_70090_H()) {
                NewFallingPlayer NewFallingPlayer = new NewFallingPlayer(f362mc.field_71439_g);
                try {
                    this.detectedLocation = NewFallingPlayer.findCollision(this.maxFindRangeValue.get().intValue());
                } catch (Exception e) {
                }
                if (this.detectedLocation != null && Math.abs(f362mc.field_71439_g.field_70163_u - this.detectedLocation.func_177956_o()) + f362mc.field_71439_g.field_70143_R <= this.maxFallDistSimulateValue.get().intValue()) {
                    this.lastFound = f362mc.field_71439_g.field_70143_R;
                }
                this.shouldRender = this.renderTraceValue.get().booleanValue() && this.detectedLocation == null;
                if (f362mc.field_71439_g.field_70143_R - this.lastFound > this.setBackFallDistValue.get().floatValue()) {
                    this.shouldStopMotion = true;
                    String str2 = this.setBackModeValue.get();
                    boolean z2 = true;
                    switch (str2.hashCode()) {
                        case -1295557813:
                            if (str2.equals("Teleport")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case 2155050:
                            if (str2.equals("Edit")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case 468059768:
                            if (str2.equals("StopMotion")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case 812449097:
                            if (str2.equals("Position")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case 906634847:
                            if (str2.equals("FlyFlag")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case 934155120:
                            if (str2.equals("SpoofBack")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case 1224560801:
                            if (str2.equals("IllegalTeleport")) {
                                z2 = false;
                                break;
                            }
                            break;
                        case 1455983486:
                            if (str2.equals("IllegalPacket")) {
                                z2 = true;
                                break;
                            }
                            break;
                    }
                    switch (z2) {
                        case false:
                            f362mc.field_71439_g.func_70634_a(this.lastX, this.lastY, this.lastZ);
                        case true:
                            for (int i2 = 0; i2 < this.illegalDupeValue.get().intValue(); i2++) {
                                PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u - 1.0E159d, f362mc.field_71439_g.field_70161_v, false));
                            }
                            break;
                        case true:
                            f362mc.field_71439_g.func_70634_a(this.lastX, this.lastY, this.lastZ);
                            break;
                        case true:
                            f362mc.field_71439_g.field_70181_x = 0.0d;
                            break;
                        case true:
                            float oldFallDist2 = f362mc.field_71439_g.field_70143_R;
                            f362mc.field_71439_g.field_70181_x = 0.0d;
                            f362mc.field_71439_g.field_70143_R = oldFallDist2;
                            break;
                        case true:
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + RandomUtils.nextDouble(6.0d, 10.0d), f362mc.field_71439_g.field_70161_v, f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70125_A, false));
                            break;
                        case true:
                        case true:
                            this.shouldEdit = true;
                            break;
                    }
                    if (this.resetFallDistanceValue.get().booleanValue() && !this.setBackModeValue.get().equalsIgnoreCase("StopMotion")) {
                        f362mc.field_71439_g.field_70143_R = 0.0f;
                    }
                    if (this.scaffoldValue.get().booleanValue() && !LiquidBounce.moduleManager.getModule(Scaffold.class).getState()) {
                        LiquidBounce.moduleManager.getModule(Scaffold.class).setState(true);
                    }
                }
            }
        }
        if (this.shouldRender) {
            synchronized (this.positions) {
                this.positions.add(new double[]{f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b, f362mc.field_71439_g.field_70161_v});
            }
            return;
        }
        synchronized (this.positions) {
            this.positions.clear();
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (this.noFlyValue.get().booleanValue() && LiquidBounce.moduleManager.getModule(Fly.class).getState()) {
            return;
        }
        if (this.setBackModeValue.get().equalsIgnoreCase("StopMotion") && (event.getPacket() instanceof S08PacketPlayerPosLook)) {
            f362mc.field_71439_g.field_70143_R = 0.0f;
        }
        if (this.setBackModeValue.get().equalsIgnoreCase("Edit") && this.shouldEdit && (event.getPacket() instanceof C03PacketPlayer)) {
            event.getPacket().field_149477_b += 100.0d;
            this.shouldEdit = false;
        }
        if (this.setBackModeValue.get().equalsIgnoreCase("SpoofBack") && this.shouldEdit && (event.getPacket() instanceof C03PacketPlayer)) {
            C03PacketPlayer packetPlayer = event.getPacket();
            packetPlayer.field_149479_a = this.lastX;
            packetPlayer.field_149477_b = this.lastY;
            packetPlayer.field_149478_c = this.lastZ;
            packetPlayer.func_149469_a(false);
            this.shouldEdit = false;
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if ((!this.noFlyValue.get().booleanValue() || !LiquidBounce.moduleManager.getModule(Fly.class).getState()) && this.setBackModeValue.get().equalsIgnoreCase("StopMotion") && this.shouldStopMotion) {
            event.zero();
        }
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        if ((!this.noFlyValue.get().booleanValue() || !LiquidBounce.moduleManager.getModule(Fly.class).getState()) && this.shouldRender) {
            synchronized (this.positions) {
                GL11.glPushMatrix();
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glDisable(2929);
                f362mc.field_71460_t.func_175072_h();
                GL11.glLineWidth(1.0f);
                GL11.glBegin(3);
                GL11.glColor4f(1.0f, 1.0f, 0.1f, 1.0f);
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
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        reset();
        super.onDisable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        reset();
        super.onEnable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        return this.setBackModeValue.get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [net.ccbluex.liquidbounce.features.module.modules.player.AntiVoid] */
    private void reset() {
        this.detectedLocation = null;
        ?? r4 = 0;
        this.lastFound = 0.0d;
        this.lastZ = 0.0d;
        r4.lastY = this;
        this.lastX = this;
        this.shouldRender = false;
        this.shouldStopMotion = false;
        synchronized (this.positions) {
            this.positions.clear();
        }
    }
}
