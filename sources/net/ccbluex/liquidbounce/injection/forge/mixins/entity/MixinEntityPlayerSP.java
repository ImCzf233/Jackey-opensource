package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.Events;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PushOutEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AntiHunger;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PortalMenu;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiDesync;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.movement.InvMove;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sneak;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityPlayerSP.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinEntityPlayerSP.class */
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer {
    @Shadow
    public boolean field_175171_bO;
    @Shadow
    public int field_71157_e;
    @Shadow
    protected int field_71156_d;
    @Shadow
    public float field_71086_bY;
    @Shadow
    public float field_71080_cy;
    @Shadow
    protected Minecraft field_71159_c;
    @Shadow
    public MovementInput field_71158_b;
    @Shadow
    public float field_110321_bQ;
    @Shadow
    public int field_110320_a;
    @Shadow
    @Final
    public NetHandlerPlayClient field_71174_a;
    @Shadow
    private boolean field_175170_bN;
    @Shadow
    private double field_175172_bI;
    @Shadow
    private int field_175168_bP;
    @Shadow
    private double field_175166_bJ;
    @Shadow
    private double field_175167_bK;
    @Shadow
    private float field_175164_bL;
    @Shadow
    private float field_175165_bM;
    @Unique
    private boolean lastOnGround;

    @Shadow
    public abstract void func_85030_a(String str, float f, float f2);

    @Shadow
    public abstract void func_70031_b(boolean z);

    @Shadow
    protected abstract boolean func_145771_j(double d, double d2, double d3);

    @Shadow
    public abstract void func_71016_p();

    @Shadow
    protected abstract void func_110318_g();

    @Shadow
    public abstract boolean func_110317_t();

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntity
    @Shadow
    public abstract boolean func_70093_af();

    @Overwrite
    protected boolean func_175160_A() {
        return (this.field_71159_c.func_175606_aa() != null && this.field_71159_c.func_175606_aa().equals(this)) || (LiquidBounce.moduleManager != null && LiquidBounce.moduleManager.getModule(Fly.class).getState());
    }

    @Overwrite
    public void func_175161_p() {
        try {
            MotionEvent event = new MotionEvent(this.field_70165_t, func_174813_aQ().field_72338_b, this.field_70161_v, this.field_70177_z, this.field_70125_A, this.field_70122_E);
            LiquidBounce.eventManager.callEvent(event);
            InvMove inventoryMove = (InvMove) LiquidBounce.moduleManager.getModule(InvMove.class);
            Sneak sneak = (Sneak) LiquidBounce.moduleManager.getModule(Sneak.class);
            boolean fakeSprint = (inventoryMove.getState() && inventoryMove.isAACAP()) || LiquidBounce.moduleManager.getModule(AntiHunger.class).getState() || (sneak.getState() && ((!MovementUtils.isMoving() || !sneak.stopMoveValue.get().booleanValue()) && sneak.modeValue.get().equalsIgnoreCase("MineSecure")));
            Events actionEvent = new Events(func_70051_ag() && !fakeSprint, func_70093_af());
            boolean sprinting = actionEvent.getSprinting();
            boolean sneaking = actionEvent.getSneaking();
            if (sprinting != this.field_175171_bO) {
                if (sprinting) {
                    this.field_71174_a.func_147297_a(new C0BPacketEntityAction((EntityPlayerSP) this, C0BPacketEntityAction.Action.START_SPRINTING));
                } else {
                    this.field_71174_a.func_147297_a(new C0BPacketEntityAction((EntityPlayerSP) this, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
                this.field_175171_bO = sprinting;
            }
            if (sneaking != this.field_175170_bN && (!sneak.getState() || sneak.modeValue.get().equalsIgnoreCase("Legit"))) {
                if (sneaking) {
                    this.field_71174_a.func_147297_a(new C0BPacketEntityAction((EntityPlayerSP) this, C0BPacketEntityAction.Action.START_SNEAKING));
                } else {
                    this.field_71174_a.func_147297_a(new C0BPacketEntityAction((EntityPlayerSP) this, C0BPacketEntityAction.Action.STOP_SNEAKING));
                }
                this.field_175170_bN = sneaking;
            }
            if (func_175160_A()) {
                float yaw = event.getYaw();
                float pitch = event.getPitch();
                float lastReportedYaw = RotationUtils.serverRotation.getYaw();
                float lastReportedPitch = RotationUtils.serverRotation.getPitch();
                if (RotationUtils.targetRotation != null) {
                    yaw = RotationUtils.targetRotation.getYaw();
                    pitch = RotationUtils.targetRotation.getPitch();
                }
                double xDiff = event.getX() - this.field_175172_bI;
                double yDiff = event.getY() - this.field_175166_bJ;
                double zDiff = event.getZ() - this.field_175167_bK;
                double yawDiff = yaw - lastReportedYaw;
                double pitchDiff = pitch - lastReportedPitch;
                boolean moved = ((xDiff * xDiff) + (yDiff * yDiff)) + (zDiff * zDiff) > (LiquidBounce.moduleManager.getModule(AntiDesync.class).getState() ? 0.0d : 9.0E-4d) || this.field_175168_bP >= 20;
                boolean rotated = (yawDiff == 0.0d && pitchDiff == 0.0d) ? false : true;
                if (this.field_70154_o == null) {
                    if (moved && rotated) {
                        this.field_71174_a.func_147297_a(new C03PacketPlayer.C06PacketPlayerPosLook(event.getX(), event.getY(), event.getZ(), yaw, pitch, event.getOnGround()));
                    } else if (moved) {
                        this.field_71174_a.func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(event.getX(), event.getY(), event.getZ(), event.getOnGround()));
                    } else if (rotated) {
                        this.field_71174_a.func_147297_a(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, event.getOnGround()));
                    } else {
                        this.field_71174_a.func_147297_a(new C03PacketPlayer(event.getOnGround()));
                    }
                } else {
                    this.field_71174_a.func_147297_a(new C03PacketPlayer.C06PacketPlayerPosLook(this.field_70159_w, -999.0d, this.field_70179_y, yaw, pitch, event.getOnGround()));
                    moved = false;
                }
                this.field_175168_bP++;
                if (moved) {
                    this.field_175172_bI = event.getX();
                    this.field_175166_bJ = event.getY();
                    this.field_175167_bK = event.getZ();
                    this.field_175168_bP = 0;
                }
                if (rotated) {
                    this.field_175164_bL = yaw;
                    this.field_175165_bM = pitch;
                }
            }
            if (func_175160_A()) {
                this.lastOnGround = event.getOnGround();
            }
            event.setEventState(EventState.POST);
            LiquidBounce.eventManager.callEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = {"pushOutOfBlocks"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void onPushOutOfBlocks(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        PushOutEvent event = new PushOutEvent();
        if (this.field_70145_X) {
            event.cancelEvent();
        }
        LiquidBounce.eventManager.callEvent(event);
        if (event.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntityLivingBase
    @Overwrite
    public void func_70636_d() {
        LiquidBounce.eventManager.callEvent(new UpdateEvent());
        if (this.field_71157_e > 0) {
            this.field_71157_e--;
            if (this.field_71157_e == 0) {
                func_70031_b(false);
            }
        }
        if (this.field_71156_d > 0) {
            this.field_71156_d--;
        }
        this.field_71080_cy = this.field_71086_bY;
        if (this.field_71087_bX) {
            if (this.field_71159_c.field_71462_r != null && !this.field_71159_c.field_71462_r.func_73868_f() && !LiquidBounce.moduleManager.getModule(PortalMenu.class).getState()) {
                this.field_71159_c.func_147108_a((GuiScreen) null);
            }
            if (this.field_71086_bY == 0.0f) {
                this.field_71159_c.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), (this.field_70146_Z.nextFloat() * 0.4f) + 0.8f));
            }
            this.field_71086_bY += 0.0125f;
            if (this.field_71086_bY >= 1.0f) {
                this.field_71086_bY = 1.0f;
            }
            this.field_71087_bX = false;
        } else if (func_70644_a(Potion.field_76431_k) && func_70660_b(Potion.field_76431_k).func_76459_b() > 60) {
            this.field_71086_bY += 0.006666667f;
            if (this.field_71086_bY > 1.0f) {
                this.field_71086_bY = 1.0f;
            }
        } else {
            if (this.field_71086_bY > 0.0f) {
                this.field_71086_bY -= 0.05f;
            }
            if (this.field_71086_bY < 0.0f) {
                this.field_71086_bY = 0.0f;
            }
        }
        if (this.field_71088_bW > 0) {
            this.field_71088_bW--;
        }
        boolean flag = this.field_71158_b.field_78901_c;
        boolean flag1 = this.field_71158_b.field_78899_d;
        boolean flag2 = this.field_71158_b.field_78900_b >= 0.8f;
        this.field_71158_b.func_78898_a();
        NoSlow noSlow = (NoSlow) LiquidBounce.moduleManager.getModule(NoSlow.class);
        KillAura killAura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
        if (func_70694_bm() != null && ((func_71039_bw() || ((func_70694_bm().func_77973_b() instanceof ItemSword) && killAura.getBlockingStatus())) && !func_70115_ae())) {
            SlowDownEvent slowDownEvent = new SlowDownEvent(0.2f, 0.2f);
            LiquidBounce.eventManager.callEvent(slowDownEvent);
            this.field_71158_b.field_78902_a *= slowDownEvent.getStrafe();
            this.field_71158_b.field_78900_b *= slowDownEvent.getForward();
            this.field_71156_d = 0;
        }
        func_145771_j(this.field_70165_t - (this.field_70130_N * 0.35d), func_174813_aQ().field_72338_b + 0.5d, this.field_70161_v + (this.field_70130_N * 0.35d));
        func_145771_j(this.field_70165_t - (this.field_70130_N * 0.35d), func_174813_aQ().field_72338_b + 0.5d, this.field_70161_v - (this.field_70130_N * 0.35d));
        func_145771_j(this.field_70165_t + (this.field_70130_N * 0.35d), func_174813_aQ().field_72338_b + 0.5d, this.field_70161_v - (this.field_70130_N * 0.35d));
        func_145771_j(this.field_70165_t + (this.field_70130_N * 0.35d), func_174813_aQ().field_72338_b + 0.5d, this.field_70161_v + (this.field_70130_N * 0.35d));
        Sprint sprint = (Sprint) LiquidBounce.moduleManager.getModule(Sprint.class);
        boolean flag3 = !sprint.getFoodValue().get().booleanValue() || ((float) func_71024_bL().func_75116_a()) > 6.0f || this.field_71075_bZ.field_75101_c;
        if (this.field_70122_E && !flag1 && !flag2 && this.field_71158_b.field_78900_b >= 0.8f && !func_70051_ag() && flag3 && !func_71039_bw() && !func_70644_a(Potion.field_76440_q)) {
            if (this.field_71156_d <= 0 && !this.field_71159_c.field_71474_y.field_151444_V.func_151470_d()) {
                this.field_71156_d = 7;
            } else {
                func_70031_b(true);
            }
        }
        if (!func_70051_ag() && this.field_71158_b.field_78900_b >= 0.8f && flag3 && ((noSlow.getState() || !func_71039_bw()) && !func_70644_a(Potion.field_76440_q) && this.field_71159_c.field_71474_y.field_151444_V.func_151470_d())) {
            func_70031_b(true);
        }
        Scaffold scaffold = (Scaffold) LiquidBounce.moduleManager.getModule(Scaffold.class);
        NoSlow noSlow2 = (NoSlow) LiquidBounce.moduleManager.getModule(NoSlow.class);
        if ((scaffold.getState() && scaffold.towerActivation() && scaffold.sprintModeValue.get().equalsIgnoreCase("Off")) || ((scaffold.getState() && scaffold.sprintModeValue.get().equalsIgnoreCase("Off")) || (sprint.getState() && sprint.getCheckServerSide().get().booleanValue() && ((this.field_70122_E || !sprint.getCheckServerSideGround().get().booleanValue()) && !sprint.getAllDirectionsValue().get().booleanValue() && RotationUtils.targetRotation != null && RotationUtils.getRotationDifference(new Rotation(this.field_71159_c.field_71439_g.field_70177_z, this.field_71159_c.field_71439_g.field_70125_A)) > 30.0d)))) {
            func_70031_b(false);
        }
        if (func_70051_ag() && (((!sprint.getState() || !sprint.getAllDirectionsValue().get().booleanValue()) && this.field_71158_b.field_78900_b < 0.8f) || this.field_70123_F || !flag3)) {
            func_70031_b(false);
        }
        if (this.field_71075_bZ.field_75101_c) {
            if (this.field_71159_c.field_71442_b.func_178887_k()) {
                if (!this.field_71075_bZ.field_75100_b) {
                    this.field_71075_bZ.field_75100_b = true;
                    func_71016_p();
                }
            } else if (!flag && this.field_71158_b.field_78901_c) {
                if (this.field_71101_bC == 0) {
                    this.field_71101_bC = 7;
                } else {
                    this.field_71075_bZ.field_75100_b = !this.field_71075_bZ.field_75100_b;
                    func_71016_p();
                    this.field_71101_bC = 0;
                }
            }
        }
        if (this.field_71075_bZ.field_75100_b && func_175160_A()) {
            if (this.field_71158_b.field_78899_d) {
                this.field_70181_x -= this.field_71075_bZ.func_75093_a() * 3.0f;
            }
            if (this.field_71158_b.field_78901_c) {
                this.field_70181_x += this.field_71075_bZ.func_75093_a() * 3.0f;
            }
        }
        if (func_110317_t()) {
            if (this.field_110320_a < 0) {
                this.field_110320_a++;
                if (this.field_110320_a == 0) {
                    this.field_110321_bQ = 0.0f;
                }
            }
            if (flag && !this.field_71158_b.field_78901_c) {
                this.field_110320_a = -10;
                func_110318_g();
            } else if (!flag && this.field_71158_b.field_78901_c) {
                this.field_110320_a = 0;
                this.field_110321_bQ = 0.0f;
            } else if (flag) {
                this.field_110320_a++;
                if (this.field_110320_a < 10) {
                    this.field_110321_bQ = this.field_110320_a * 0.1f;
                } else {
                    this.field_110321_bQ = 0.8f + ((2.0f / (this.field_110320_a - 9)) * 0.1f);
                }
            }
        } else {
            this.field_110321_bQ = 0.0f;
        }
        super.func_70636_d();
        if (this.field_70122_E && this.field_71075_bZ.field_75100_b && !this.field_71159_c.field_71442_b.func_178887_k()) {
            this.field_71075_bZ.field_75100_b = false;
            func_71016_p();
        }
    }

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntity
    public void func_70091_d(double x, double y, double z) {
        double d;
        double d2;
        double d3;
        MoveEvent moveEvent = new MoveEvent(x, y, z);
        LiquidBounce.eventManager.callEvent(moveEvent);
        if (moveEvent.isCancelled()) {
            return;
        }
        double x2 = moveEvent.getX();
        double y2 = moveEvent.getY();
        double z2 = moveEvent.getZ();
        if (this.field_70145_X) {
            func_174826_a(func_174813_aQ().func_72317_d(x2, y2, z2));
            this.field_70165_t = (func_174813_aQ().field_72340_a + func_174813_aQ().field_72336_d) / 2.0d;
            this.field_70163_u = func_174813_aQ().field_72338_b;
            this.field_70161_v = (func_174813_aQ().field_72339_c + func_174813_aQ().field_72334_f) / 2.0d;
            return;
        }
        this.field_70170_p.field_72984_F.func_76320_a("move");
        double d0 = this.field_70165_t;
        double d1 = this.field_70163_u;
        double d22 = this.field_70161_v;
        if (this.field_70134_J) {
            this.field_70134_J = false;
            x2 *= 0.25d;
            y2 *= 0.05000000074505806d;
            z2 *= 0.25d;
            this.field_70159_w = 0.0d;
            this.field_70181_x = 0.0d;
            this.field_70179_y = 0.0d;
        }
        double d32 = x2;
        double d4 = y2;
        double d5 = z2;
        boolean flag = this.field_70122_E && func_70093_af();
        if (flag || moveEvent.isSafeWalk()) {
            while (x2 != 0.0d && this.field_70170_p.func_72945_a((Entity) this, func_174813_aQ().func_72317_d(x2, -1.0d, 0.0d)).isEmpty()) {
                if (x2 < 0.05d && x2 >= (-0.05d)) {
                    d3 = 0.0d;
                } else {
                    d3 = x2 > 0.0d ? x2 - 0.05d : x2 + 0.05d;
                }
                x2 = d3;
                d32 = x2;
            }
            while (z2 != 0.0d && this.field_70170_p.func_72945_a((Entity) this, func_174813_aQ().func_72317_d(0.0d, -1.0d, z2)).isEmpty()) {
                if (z2 < 0.05d && z2 >= (-0.05d)) {
                    d2 = 0.0d;
                } else {
                    d2 = z2 > 0.0d ? z2 - 0.05d : z2 + 0.05d;
                }
                z2 = d2;
                d5 = z2;
            }
            while (x2 != 0.0d && z2 != 0.0d && this.field_70170_p.func_72945_a((Entity) this, func_174813_aQ().func_72317_d(x2, -1.0d, z2)).isEmpty()) {
                if (x2 < 0.05d && x2 >= (-0.05d)) {
                    x2 = 0.0d;
                } else {
                    x2 = x2 > 0.0d ? x2 - 0.05d : x2 + 0.05d;
                }
                d32 = x2;
                if (z2 < 0.05d && z2 >= (-0.05d)) {
                    d = 0.0d;
                } else {
                    d = z2 > 0.0d ? z2 - 0.05d : z2 + 0.05d;
                }
                z2 = d;
                d5 = z2;
            }
        }
        List<AxisAlignedBB> list1 = this.field_70170_p.func_72945_a((Entity) this, func_174813_aQ().func_72321_a(x2, y2, z2));
        AxisAlignedBB axisalignedbb = func_174813_aQ();
        for (AxisAlignedBB axisalignedbb1 : list1) {
            y2 = axisalignedbb1.func_72323_b(func_174813_aQ(), y2);
        }
        func_174826_a(func_174813_aQ().func_72317_d(0.0d, y2, 0.0d));
        boolean flag1 = this.field_70122_E || (d4 != y2 && d4 < 0.0d);
        for (AxisAlignedBB axisalignedbb2 : list1) {
            x2 = axisalignedbb2.func_72316_a(func_174813_aQ(), x2);
        }
        func_174826_a(func_174813_aQ().func_72317_d(x2, 0.0d, 0.0d));
        for (AxisAlignedBB axisalignedbb13 : list1) {
            z2 = axisalignedbb13.func_72322_c(func_174813_aQ(), z2);
        }
        func_174826_a(func_174813_aQ().func_72317_d(0.0d, 0.0d, z2));
        if (this.field_70138_W > 0.0f && flag1 && (d32 != x2 || d5 != z2)) {
            StepEvent stepEvent = new StepEvent(this.field_70138_W);
            LiquidBounce.eventManager.callEvent(stepEvent);
            double d11 = x2;
            double d7 = y2;
            double d8 = z2;
            AxisAlignedBB axisalignedbb3 = func_174813_aQ();
            func_174826_a(axisalignedbb);
            double y3 = stepEvent.getStepHeight();
            List<AxisAlignedBB> list = this.field_70170_p.func_72945_a((Entity) this, func_174813_aQ().func_72321_a(d32, y3, d5));
            AxisAlignedBB axisalignedbb4 = func_174813_aQ();
            AxisAlignedBB axisalignedbb5 = axisalignedbb4.func_72321_a(d32, 0.0d, d5);
            double d9 = y3;
            for (AxisAlignedBB axisalignedbb6 : list) {
                d9 = axisalignedbb6.func_72323_b(axisalignedbb5, d9);
            }
            AxisAlignedBB axisalignedbb42 = axisalignedbb4.func_72317_d(0.0d, d9, 0.0d);
            double d15 = d32;
            for (AxisAlignedBB axisalignedbb7 : list) {
                d15 = axisalignedbb7.func_72316_a(axisalignedbb42, d15);
            }
            AxisAlignedBB axisalignedbb43 = axisalignedbb42.func_72317_d(d15, 0.0d, 0.0d);
            double d16 = d5;
            for (AxisAlignedBB axisalignedbb8 : list) {
                d16 = axisalignedbb8.func_72322_c(axisalignedbb43, d16);
            }
            AxisAlignedBB axisalignedbb44 = axisalignedbb43.func_72317_d(0.0d, 0.0d, d16);
            AxisAlignedBB axisalignedbb14 = func_174813_aQ();
            double d17 = y3;
            for (AxisAlignedBB axisalignedbb9 : list) {
                d17 = axisalignedbb9.func_72323_b(axisalignedbb14, d17);
            }
            AxisAlignedBB axisalignedbb142 = axisalignedbb14.func_72317_d(0.0d, d17, 0.0d);
            double d18 = d32;
            for (AxisAlignedBB axisalignedbb10 : list) {
                d18 = axisalignedbb10.func_72316_a(axisalignedbb142, d18);
            }
            AxisAlignedBB axisalignedbb143 = axisalignedbb142.func_72317_d(d18, 0.0d, 0.0d);
            double d19 = d5;
            for (AxisAlignedBB axisalignedbb11 : list) {
                d19 = axisalignedbb11.func_72322_c(axisalignedbb143, d19);
            }
            AxisAlignedBB axisalignedbb144 = axisalignedbb143.func_72317_d(0.0d, 0.0d, d19);
            double d20 = (d15 * d15) + (d16 * d16);
            double d10 = (d18 * d18) + (d19 * d19);
            if (d20 > d10) {
                x2 = d15;
                z2 = d16;
                y2 = -d9;
                func_174826_a(axisalignedbb44);
            } else {
                x2 = d18;
                z2 = d19;
                y2 = -d17;
                func_174826_a(axisalignedbb144);
            }
            for (AxisAlignedBB axisalignedbb12 : list) {
                y2 = axisalignedbb12.func_72323_b(func_174813_aQ(), y2);
            }
            func_174826_a(func_174813_aQ().func_72317_d(0.0d, y2, 0.0d));
            if ((d11 * d11) + (d8 * d8) >= (x2 * x2) + (z2 * z2)) {
                x2 = d11;
                y2 = d7;
                z2 = d8;
                func_174826_a(axisalignedbb3);
            } else {
                LiquidBounce.eventManager.callEvent(new StepConfirmEvent());
            }
        }
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("rest");
        this.field_70165_t = (func_174813_aQ().field_72340_a + func_174813_aQ().field_72336_d) / 2.0d;
        this.field_70163_u = func_174813_aQ().field_72338_b;
        this.field_70161_v = (func_174813_aQ().field_72339_c + func_174813_aQ().field_72334_f) / 2.0d;
        this.field_70123_F = (d32 == x2 && d5 == z2) ? false : true;
        this.field_70124_G = d4 != y2;
        this.field_70122_E = this.field_70124_G && d4 < 0.0d;
        this.field_70132_H = this.field_70123_F || this.field_70124_G;
        int i = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224d);
        int k = MathHelper.func_76128_c(this.field_70161_v);
        BlockPos blockpos = new BlockPos(i, j, k);
        Block block1 = this.field_70170_p.func_180495_p(blockpos).func_177230_c();
        if (block1.func_149688_o() == Material.field_151579_a) {
            Block block = this.field_70170_p.func_180495_p(blockpos.func_177977_b()).func_177230_c();
            if ((block instanceof BlockFence) || (block instanceof BlockWall) || (block instanceof BlockFenceGate)) {
                block1 = block;
                blockpos = blockpos.func_177977_b();
            }
        }
        func_180433_a(y2, this.field_70122_E, block1, blockpos);
        if (d32 != x2) {
            this.field_70159_w = 0.0d;
        }
        if (d5 != z2) {
            this.field_70179_y = 0.0d;
        }
        if (d4 != y2) {
            block1.func_176216_a(this.field_70170_p, (Entity) this);
        }
        if (func_70041_e_() && !flag && this.field_70154_o == null) {
            double d12 = this.field_70165_t - d0;
            double d13 = this.field_70163_u - d1;
            double d14 = this.field_70161_v - d22;
            if (block1 != Blocks.field_150468_ap) {
                d13 = 0.0d;
            }
            if (block1 != null && this.field_70122_E) {
                block1.func_176199_a(this.field_70170_p, blockpos, (Entity) this);
            }
            this.field_70140_Q = (float) (this.field_70140_Q + (MathHelper.func_76133_a((d12 * d12) + (d14 * d14)) * 0.6d));
            this.field_82151_R = (float) (this.field_82151_R + (MathHelper.func_76133_a((d12 * d12) + (d13 * d13) + (d14 * d14)) * 0.6d));
            if (this.field_82151_R > getNextStepDistance() && block1.func_149688_o() != Material.field_151579_a) {
                setNextStepDistance(((int) this.field_82151_R) + 1);
                if (func_70090_H()) {
                    float f = MathHelper.func_76133_a((this.field_70159_w * this.field_70159_w * 0.20000000298023224d) + (this.field_70181_x * this.field_70181_x) + (this.field_70179_y * this.field_70179_y * 0.20000000298023224d)) * 0.35f;
                    if (f > 1.0f) {
                        f = 1.0f;
                    }
                    func_85030_a(func_145776_H(), f, 1.0f + ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4f));
                }
                func_180429_a(blockpos, block1);
            }
        }
        try {
            func_145775_I();
            boolean flag2 = func_70026_G();
            if (this.field_70170_p.func_147470_e(func_174813_aQ().func_72331_e(0.001d, 0.001d, 0.001d))) {
                func_70081_e(1);
                if (!flag2) {
                    func_70015_d(getFire() + 1);
                    if (getFire() == 0) {
                        func_70015_d(8);
                    }
                }
            } else if (getFire() <= 0) {
                func_70015_d(-this.field_70174_ab);
            }
            if (flag2 && getFire() > 0) {
                func_85030_a("random.fizz", 0.7f, 1.6f + ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4f));
                func_70015_d(-this.field_70174_ab);
            }
            this.field_70170_p.field_72984_F.func_76319_b();
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
            CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
            func_85029_a(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }
}
