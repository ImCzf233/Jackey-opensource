package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Phase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

/* compiled from: Step.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010!\u001a\u00020\bH\u0002J\b\u0010\"\u001a\u00020#H\u0002J\b\u0010$\u001a\u00020#H\u0016J\u0010\u0010%\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0007J\u0010\u0010(\u001a\u00020#2\u0006\u0010&\u001a\u00020)H\u0007J\u0010\u0010*\u001a\u00020#2\u0006\u0010&\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020#2\u0006\u0010&\u001a\u00020-H\u0007J\u0010\u0010.\u001a\u00020#2\u0006\u0010&\u001a\u00020/H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0010R\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0010R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n��R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001f\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010 \u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��¨\u00060"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Step;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "heightValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "isAACStep", "", "isStep", "jumpHeightValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "ncp1Values", "", "", "[Ljava/lang/Double;", "ncp2Values", "ncpNextStep", "", "spartanSwitch", "stepX", "stepY", "stepZ", "tag", "", "getTag", "()Ljava/lang/String;", "ticks", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "timerValue", "usedTimer", "couldStep", "fakeJump", "", "onDisable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onStepConfirm", "Lnet/ccbluex/liquidbounce/event/StepConfirmEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "Step", description = "Allows you to step up blocks.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Step.class */
public final class Step extends Module {
    private boolean isStep;
    private double stepX;
    private double stepY;
    private double stepZ;
    private int ncpNextStep;
    private boolean spartanSwitch;
    private boolean isAACStep;
    private int ticks;
    private boolean usedTimer;
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Jump", "NCPPacket", "NCP", "MotionNCP", "OldNCP", "AAC", "LAAC", "AAC3.3.4", "Spartan", "Rewinside", "1.5Twillight"}, "NCP");
    @NotNull
    private final FloatValue heightValue = new FloatValue("Height", 1.0f, 0.6f, 10.0f);
    @NotNull
    private final FloatValue timerValue = new FloatValue("Timer", 1.0f, 0.3f, 10.0f, "x");
    @NotNull
    private final FloatValue jumpHeightValue = new FloatValue("JumpHeight", 0.42f, 0.37f, 0.42f);
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, 500, "ms");
    @NotNull
    private final MSTimer timer = new MSTimer();
    @NotNull
    private final Double[] ncp1Values = {Double.valueOf(0.425d), Double.valueOf(0.821d), Double.valueOf(0.699d), Double.valueOf(0.599d), Double.valueOf(1.022d), Double.valueOf(1.372d), Double.valueOf(1.652d), Double.valueOf(1.869d), Double.valueOf(2.019d), Double.valueOf(1.919d)};
    @NotNull
    private final Double[] ncp2Values = {Double.valueOf(0.42d), Double.valueOf(0.7532d), Double.valueOf(1.01d), Double.valueOf(1.093d), Double.valueOf(1.015d)};

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (MinecraftInstance.f362mc.field_71439_g == null) {
            return;
        }
        MinecraftInstance.f362mc.field_71439_g.field_70138_W = 0.5f;
        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
        MinecraftInstance.f362mc.field_71439_g.field_71102_ce = 0.02f;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.usedTimer) {
            MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
            this.usedTimer = false;
        }
        String mode = this.modeValue.get();
        if (StringsKt.equals(mode, "jump", true) && MinecraftInstance.f362mc.field_71439_g.field_70123_F && MinecraftInstance.f362mc.field_71439_g.field_70122_E && !MinecraftInstance.f362mc.field_71474_y.field_74314_A.func_151470_d()) {
            fakeJump();
            MinecraftInstance.f362mc.field_71439_g.field_70181_x = this.jumpHeightValue.get().floatValue();
        } else if (StringsKt.equals(mode, "laac", true)) {
            if (MinecraftInstance.f362mc.field_71439_g.field_70123_F && !MinecraftInstance.f362mc.field_71439_g.func_70617_f_() && !MinecraftInstance.f362mc.field_71439_g.func_70090_H() && !MinecraftInstance.f362mc.field_71439_g.func_180799_ab() && !MinecraftInstance.f362mc.field_71439_g.field_70134_J) {
                if (MinecraftInstance.f362mc.field_71439_g.field_70122_E && this.timer.hasTimePassed(this.delayValue.get().intValue())) {
                    this.isStep = true;
                    fakeJump();
                    MinecraftInstance.f362mc.field_71439_g.field_70181_x += 0.620000001490116d;
                    float f = MinecraftInstance.f362mc.field_71439_g.field_70177_z * 0.017453292f;
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w -= MathHelper.func_76126_a(f) * 0.2d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y += MathHelper.func_76134_b(f) * 0.2d;
                    this.timer.reset();
                }
                MinecraftInstance.f362mc.field_71439_g.field_70122_E = true;
                return;
            }
            this.isStep = false;
        } else if (StringsKt.equals(mode, "aac3.3.4", true)) {
            if (MinecraftInstance.f362mc.field_71439_g.field_70123_F && MovementUtils.isMoving()) {
                if (MinecraftInstance.f362mc.field_71439_g.field_70122_E && couldStep()) {
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 1.26d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 1.26d;
                    MinecraftInstance.f362mc.field_71439_g.func_70664_aZ();
                    this.isAACStep = true;
                }
                if (this.isAACStep) {
                    MinecraftInstance.f362mc.field_71439_g.field_70181_x -= 0.015d;
                    if (MinecraftInstance.f362mc.field_71439_g.func_71039_bw()) {
                        return;
                    }
                    if (MinecraftInstance.f362mc.field_71439_g.field_71158_b.field_78902_a == 0.0f) {
                        MinecraftInstance.f362mc.field_71439_g.field_70747_aH = 0.3f;
                        return;
                    }
                    return;
                }
                return;
            }
            this.isAACStep = false;
        } else if (StringsKt.equals(mode, "1.5Twillight", true)) {
            if (MovementUtils.isMoving() && MinecraftInstance.f362mc.field_71439_g.field_70123_F) {
                this.ticks++;
                if (this.ticks == 1) {
                    MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.4399d;
                }
                if (this.ticks == 12) {
                    MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.4399d;
                }
                if (this.ticks >= 40) {
                    this.ticks = 0;
                }
            } else if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                this.ticks = 0;
            }
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        String mode = this.modeValue.get();
        if (StringsKt.equals(mode, "motionncp", true) && MinecraftInstance.f362mc.field_71439_g.field_70123_F && !MinecraftInstance.f362mc.field_71474_y.field_74314_A.func_151470_d()) {
            if (MinecraftInstance.f362mc.field_71439_g.field_70122_E && couldStep()) {
                fakeJump();
                MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
                event.setY(0.41999998688698d);
                this.ncpNextStep = 1;
            } else if (this.ncpNextStep == 1) {
                event.setY(0.33319999363422d);
                this.ncpNextStep = 2;
            } else if (this.ncpNextStep == 2) {
                double yaw = MovementUtils.getDirection();
                event.setY(0.24813599859094704d);
                event.setX((-Math.sin(yaw)) * 0.7d);
                event.setZ(Math.cos(yaw) * 0.7d);
                this.ncpNextStep = 0;
            }
        }
    }

    @EventTarget
    public final void onStep(@NotNull StepEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g == null) {
            return;
        }
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(Phase.class);
        Intrinsics.checkNotNull(module);
        Phase phaseMod = (Phase) module;
        if (phaseMod.getState() && !StringsKt.equals(phaseMod.modeValue.get(), "hypixel", true)) {
            event.setStepHeight(0.0f);
            return;
        }
        Module module2 = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);
        if (module2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.Fly");
        }
        Fly fly = (Fly) module2;
        if (fly.getState()) {
            String flyMode = fly.modeValue.get();
            if (StringsKt.equals(flyMode, "Rewinside", true)) {
                event.setStepHeight(0.0f);
                return;
            }
        }
        String mode = this.modeValue.get();
        if (!MinecraftInstance.f362mc.field_71439_g.field_70122_E || !this.timer.hasTimePassed(this.delayValue.get().intValue()) || StringsKt.equals(mode, "Jump", true) || StringsKt.equals(mode, "MotionNCP", true) || StringsKt.equals(mode, "LAAC", true) || StringsKt.equals(mode, "AAC3.3.4", true) || StringsKt.equals(mode, "AACv4", true) || StringsKt.equals(mode, "1.5Twillight", true)) {
            MinecraftInstance.f362mc.field_71439_g.field_70138_W = 0.5f;
            event.setStepHeight(0.5f);
            return;
        }
        float height = this.heightValue.get().floatValue();
        MinecraftInstance.f362mc.field_71439_g.field_70138_W = height;
        MinecraftInstance.f362mc.field_71428_T.field_74278_d = this.timerValue.get().floatValue();
        this.usedTimer = true;
        event.setStepHeight(height);
        if (event.getStepHeight() > 0.5f) {
            this.isStep = true;
            this.stepX = MinecraftInstance.f362mc.field_71439_g.field_70165_t;
            this.stepY = MinecraftInstance.f362mc.field_71439_g.field_70163_u;
            this.stepZ = MinecraftInstance.f362mc.field_71439_g.field_70161_v;
        }
    }

    @EventTarget(ignoreCondition = true)
    public final void onStepConfirm(@NotNull StepConfirmEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g == null || !this.isStep) {
            return;
        }
        if (MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY > 0.5d) {
            String mode = this.modeValue.get();
            if (StringsKt.equals(mode, "NCPPacket", true)) {
                double rHeight = MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                if (rHeight > 2.019d) {
                    Object[] $this$forEach$iv = this.ncp1Values;
                    int i = 0;
                    int length = $this$forEach$iv.length;
                    while (i < length) {
                        Object element$iv = $this$forEach$iv[i];
                        i++;
                        double it = ((Number) element$iv).doubleValue();
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                    }
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                } else if (rHeight > 1.869d) {
                    int i2 = 0;
                    while (i2 < 8) {
                        int i3 = i2;
                        i2++;
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + this.ncp1Values[i3].doubleValue(), this.stepZ, false));
                    }
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                } else if (rHeight > 1.5d) {
                    int i4 = 0;
                    while (i4 < 7) {
                        int i5 = i4;
                        i4++;
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + this.ncp1Values[i5].doubleValue(), this.stepZ, false));
                    }
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                } else if (rHeight > 1.015d) {
                    Object[] $this$forEach$iv2 = this.ncp2Values;
                    int i6 = 0;
                    int length2 = $this$forEach$iv2.length;
                    while (i6 < length2) {
                        Object element$iv2 = $this$forEach$iv2[i6];
                        i6++;
                        double it2 = ((Number) element$iv2).doubleValue();
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it2, this.stepZ, false));
                    }
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                } else if (rHeight > 0.875d) {
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698d, this.stepZ, false));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212d, this.stepZ, false));
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                } else if (rHeight > 0.6d) {
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.39d, this.stepZ, false));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.6938d, this.stepZ, false));
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                }
            } else if (StringsKt.equals(mode, "NCP", true) || StringsKt.equals(mode, "AAC", true)) {
                fakeJump();
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698d, this.stepZ, false));
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212d, this.stepZ, false));
                this.timer.reset();
            } else if (StringsKt.equals(mode, "Spartan", true)) {
                fakeJump();
                if (this.spartanSwitch) {
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698d, this.stepZ, false));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212d, this.stepZ, false));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.001335979112147d, this.stepZ, false));
                } else {
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.6d, this.stepZ, false));
                }
                this.spartanSwitch = !this.spartanSwitch;
                this.timer.reset();
            } else if (StringsKt.equals(mode, "Rewinside", true)) {
                fakeJump();
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698d, this.stepZ, false));
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212d, this.stepZ, false));
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.001335979112147d, this.stepZ, false));
                this.timer.reset();
            }
        }
        this.isStep = false;
        this.stepX = 0.0d;
        this.stepY = 0.0d;
        this.stepZ = 0.0d;
    }

    @EventTarget(ignoreCondition = true)
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        C03PacketPlayer packet = event.getPacket();
        if ((packet instanceof C03PacketPlayer) && this.isStep && StringsKt.equals(this.modeValue.get(), "OldNCP", true)) {
            packet.field_149477_b += 0.07d;
            this.isStep = false;
        }
    }

    private final void fakeJump() {
        MinecraftInstance.f362mc.field_71439_g.field_70160_al = true;
        MinecraftInstance.f362mc.field_71439_g.func_71029_a(StatList.field_75953_u);
    }

    private final boolean couldStep() {
        double yaw = MovementUtils.getDirection();
        double x = (-Math.sin(yaw)) * 0.4d;
        double z = Math.cos(yaw) * 0.4d;
        return MinecraftInstance.f362mc.field_71441_e.func_147461_a(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().func_72317_d(x, 1.001335979112147d, z)).isEmpty();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return this.modeValue.get();
    }
}
