package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Velocity.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\"\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020#2\u0006\u0010%\u001a\u00020(H\u0007J\u0010\u0010)\u001a\u00020#2\u0006\u0010%\u001a\u00020*H\u0007J\u0010\u0010+\u001a\u00020#2\u0006\u0010%\u001a\u00020,H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n��R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010 \u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010!\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��¨\u0006-"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Velocity;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aac5KillAuraValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "aacPushXZReducerValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "aacPushYReducerValue", "aacStrafeValue", "horizontalExplosionValue", "horizontalValue", "jump", "", "legitFaceValue", "legitStrafeValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "phaseOffsetValue", "pos", "Lnet/minecraft/util/BlockPos;", "reduceChance", "reverse2StrengthValue", "reverseHurt", "reverseStrengthValue", "shouldAffect", "tag", "", "getTag", "()Ljava/lang/String;", "velocityInput", "velocityTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "verticalExplosionValue", "verticalValue", "onDisable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "Velocity", description = "Allows you to modify the amount of knockback you take.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/Velocity.class */
public final class Velocity extends Module {
    @NotNull
    private final FloatValue horizontalValue = new FloatValue("Horizontal", 0.0f, -1.0f, 1.0f, "x");
    @NotNull
    private final FloatValue verticalValue = new FloatValue("Vertical", 0.0f, -1.0f, 1.0f, "x");
    @NotNull
    private final FloatValue horizontalExplosionValue = new FloatValue("HorizontalExplosion", 0.0f, 0.0f, 1.0f, "x");
    @NotNull
    private final FloatValue verticalExplosionValue = new FloatValue("VerticalExplosion", 0.0f, 0.0f, 1.0f, "x");
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Cancel", "Simple", "AACv4", "AAC4Reduce", "AAC5Reduce", "AAC5.2.0", "AAC", "AACPush", "AACZero", "Reverse", "SmoothReverse", "Jump", "Glitch", "Phase", "Matrix", "Legit", "AEMine"}, "Cancel");
    @NotNull
    private final BoolValue aac5KillAuraValue = new BoolValue("AAC5.2.0-Attack-Only", true, new Velocity$aac5KillAuraValue$1(this));
    @NotNull
    private final FloatValue reduceChance = new FloatValue("Reduce-Chance", 100.0f, 0.0f, 100.0f, "%");
    private boolean shouldAffect = true;
    @NotNull
    private final FloatValue reverseStrengthValue = new FloatValue("ReverseStrength", 1.0f, 0.1f, 1.0f, "x", new Velocity$reverseStrengthValue$1(this));
    @NotNull
    private final FloatValue reverse2StrengthValue = new FloatValue("SmoothReverseStrength", 0.05f, 0.02f, 0.1f, "x", new Velocity$reverse2StrengthValue$1(this));
    @NotNull
    private final FloatValue aacPushXZReducerValue = new FloatValue("AACPushXZReducer", 2.0f, 1.0f, 3.0f, "x", new Velocity$aacPushXZReducerValue$1(this));
    @NotNull
    private final BoolValue aacPushYReducerValue = new BoolValue("AACPushYReducer", true, new Velocity$aacPushYReducerValue$1(this));
    @NotNull
    private final BoolValue legitStrafeValue = new BoolValue("LegitStrafe", false, new Velocity$legitStrafeValue$1(this));
    @NotNull
    private final BoolValue legitFaceValue = new BoolValue("LegitFace", true, new Velocity$legitFaceValue$1(this));
    @NotNull
    private final BoolValue aacStrafeValue = new BoolValue("AACStrafeValue", false, new Velocity$aacStrafeValue$1(this));
    @NotNull
    private final FloatValue phaseOffsetValue = new FloatValue("Phase-Offset", 0.05f, -10.0f, 10.0f, "m", new Velocity$phaseOffsetValue$1(this));
    @NotNull
    private MSTimer velocityTimer = new MSTimer();
    private boolean velocityInput;
    @Nullable
    private BlockPos pos;
    private boolean reverseHurt;
    private boolean jump;

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return this.modeValue.get();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
        if (entityPlayerSP == null) {
            return;
        }
        entityPlayerSP.field_71102_ce = 0.02f;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN <= 0) {
            this.shouldAffect = ((float) Math.random()) < this.reduceChance.get().floatValue() / 100.0f;
        }
        if (MinecraftInstance.f362mc.field_71439_g.func_70090_H() || MinecraftInstance.f362mc.field_71439_g.func_180799_ab() || MinecraftInstance.f362mc.field_71439_g.field_70134_J || !this.shouldAffect) {
            return;
        }
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1970553484:
                if (lowerCase.equals("smoothreverse")) {
                    if (!this.velocityInput) {
                        MinecraftInstance.f362mc.field_71439_g.field_71102_ce = 0.02f;
                        return;
                    }
                    if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN > 0) {
                        this.reverseHurt = true;
                    }
                    if (!MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                        if (this.reverseHurt) {
                            MinecraftInstance.f362mc.field_71439_g.field_71102_ce = this.reverse2StrengthValue.get().floatValue();
                            return;
                        }
                        return;
                    } else if (this.velocityTimer.hasTimePassed(80L)) {
                        this.velocityInput = false;
                        this.reverseHurt = false;
                        return;
                    } else {
                        return;
                    }
                }
                return;
            case -1513652168:
                if (lowerCase.equals("aac5reduce")) {
                    if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN > 1 && this.velocityInput) {
                        MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0.81d;
                        MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 0.81d;
                    }
                    if (!this.velocityInput) {
                        return;
                    }
                    if ((MinecraftInstance.f362mc.field_71439_g.field_70737_aN < 5 || MinecraftInstance.f362mc.field_71439_g.field_70122_E) && this.velocityTimer.hasTimePassed(120L)) {
                        this.velocityInput = false;
                        return;
                    }
                    return;
                }
                return;
            case -1421312393:
                if (!lowerCase.equals("aemine") || MinecraftInstance.f362mc.field_71439_g.field_70737_aN <= 0) {
                    return;
                }
                if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN >= 6) {
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0.605001d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 0.605001d;
                    MinecraftInstance.f362mc.field_71439_g.field_70181_x *= 0.727d;
                    return;
                } else if (!MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0.305001d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 0.305001d;
                    MinecraftInstance.f362mc.field_71439_g.field_70181_x -= 0.095d;
                    return;
                } else {
                    return;
                }
            case -1243181771:
                if (lowerCase.equals("glitch")) {
                    MinecraftInstance.f362mc.field_71439_g.field_70145_X = this.velocityInput;
                    if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN == 7) {
                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.4d;
                    }
                    this.velocityInput = false;
                    return;
                }
                return;
            case -1234547235:
                if (lowerCase.equals("aacpush")) {
                    if (this.jump) {
                        if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                            this.jump = false;
                        }
                    } else {
                        if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN > 0) {
                            if (!(MinecraftInstance.f362mc.field_71439_g.field_70159_w == 0.0d)) {
                                if (!(MinecraftInstance.f362mc.field_71439_g.field_70179_y == 0.0d)) {
                                    MinecraftInstance.f362mc.field_71439_g.field_70122_E = true;
                                }
                            }
                        }
                        if (MinecraftInstance.f362mc.field_71439_g.field_70172_ad > 0 && this.aacPushYReducerValue.get().booleanValue()) {
                            Module module = LiquidBounce.INSTANCE.getModuleManager().get(Speed.class);
                            Intrinsics.checkNotNull(module);
                            if (!module.getState()) {
                                MinecraftInstance.f362mc.field_71439_g.field_70181_x -= 0.014999993d;
                            }
                        }
                    }
                    if (MinecraftInstance.f362mc.field_71439_g.field_70172_ad >= 19) {
                        float reduce = this.aacPushXZReducerValue.get().floatValue();
                        MinecraftInstance.f362mc.field_71439_g.field_70159_w /= reduce;
                        MinecraftInstance.f362mc.field_71439_g.field_70179_y /= reduce;
                        return;
                    }
                    return;
                }
                return;
            case -1234264725:
                if (lowerCase.equals("aaczero")) {
                    if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN > 0) {
                        if (!this.velocityInput || MinecraftInstance.f362mc.field_71439_g.field_70122_E || MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.0f) {
                            return;
                        }
                        MinecraftInstance.f362mc.field_71439_g.func_70024_g(0.0d, -1.0d, 0.0d);
                        MinecraftInstance.f362mc.field_71439_g.field_70122_E = true;
                        return;
                    }
                    this.velocityInput = false;
                    return;
                }
                return;
            case -1081239615:
                if (!lowerCase.equals("matrix") || MinecraftInstance.f362mc.field_71439_g.field_70737_aN <= 0) {
                    return;
                }
                if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                    if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN <= 6) {
                        MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0.700054132d;
                        MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 0.700054132d;
                    }
                    if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN <= 5) {
                        MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0.803150645d;
                        MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 0.803150645d;
                        return;
                    }
                    return;
                } else if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN <= 10) {
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0.605001d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 0.605001d;
                    return;
                } else {
                    return;
                }
            case 96323:
                if (lowerCase.equals("aac") && this.velocityInput && this.velocityTimer.hasTimePassed(50L)) {
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w *= this.horizontalValue.get().doubleValue();
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y *= this.horizontalValue.get().doubleValue();
                    MinecraftInstance.f362mc.field_71439_g.field_70181_x *= this.verticalValue.get().doubleValue();
                    if (this.aacStrafeValue.get().booleanValue()) {
                        MovementUtils.strafe();
                    }
                    this.velocityInput = false;
                    return;
                }
                return;
            case 3273774:
                if (lowerCase.equals("jump") && MinecraftInstance.f362mc.field_71439_g.field_70737_aN > 0 && MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                    MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.42d;
                    float yaw = MinecraftInstance.f362mc.field_71439_g.field_70177_z * 0.017453292f;
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w -= MathHelper.func_76126_a(yaw) * 0.2d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y += MathHelper.func_76134_b(yaw) * 0.2d;
                    return;
                }
                return;
            case 92570113:
                if (lowerCase.equals("aacv4")) {
                    if (!MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                        if (this.velocityInput) {
                            MinecraftInstance.f362mc.field_71439_g.field_71102_ce = 0.02f;
                            MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0.6d;
                            MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 0.6d;
                            return;
                        }
                        return;
                    } else if (this.velocityTimer.hasTimePassed(80L)) {
                        this.velocityInput = false;
                        MinecraftInstance.f362mc.field_71439_g.field_71102_ce = 0.02f;
                        return;
                    } else {
                        return;
                    }
                }
                return;
            case 1099846370:
                if (!lowerCase.equals("reverse") || !this.velocityInput) {
                    return;
                }
                if (!MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                    MovementUtils.strafe(MovementUtils.getSpeed() * this.reverseStrengthValue.get().floatValue());
                    return;
                } else if (this.velocityTimer.hasTimePassed(80L)) {
                    this.velocityInput = false;
                    return;
                } else {
                    return;
                }
            case 1893811447:
                if (lowerCase.equals("aac4reduce")) {
                    if (MinecraftInstance.f362mc.field_71439_g.field_70737_aN > 0 && !MinecraftInstance.f362mc.field_71439_g.field_70122_E && this.velocityInput && this.velocityTimer.hasTimePassed(80L)) {
                        MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0.62d;
                        MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 0.62d;
                    }
                    if (!this.velocityInput) {
                        return;
                    }
                    if ((MinecraftInstance.f362mc.field_71439_g.field_70737_aN < 4 || MinecraftInstance.f362mc.field_71439_g.field_70122_E) && this.velocityTimer.hasTimePassed(120L)) {
                        this.velocityInput = false;
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x012c, code lost:
        if (r0.equals("aac") == false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x013a, code lost:
        if (r0.equals("aacv4") == false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0148, code lost:
        if (r0.equals("aaczero") == false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0172, code lost:
        if (r0.equals("reverse") == false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01aa, code lost:
        if (r0.equals("aac5reduce") == false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x01b8, code lost:
        if (r0.equals("smoothreverse") == false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x024f, code lost:
        r11.velocityInput = true;
     */
    @net.ccbluex.liquidbounce.event.EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPacket(@org.jetbrains.annotations.NotNull net.ccbluex.liquidbounce.event.PacketEvent r12) {
        /*
            Method dump skipped, instructions count: 931
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.combat.Velocity.onPacket(net.ccbluex.liquidbounce.event.PacketEvent):void");
    }

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        BlockPos blockPos;
        BlockPos blockPos2;
        BlockPos blockPos3;
        Intrinsics.checkNotNullParameter(event, "event");
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        if (!Intrinsics.areEqual(lowerCase, "legit") || this.pos == null || MinecraftInstance.f362mc.field_71439_g.field_70737_aN <= 0) {
            return;
        }
        Intrinsics.checkNotNull(this.pos);
        Intrinsics.checkNotNull(this.pos);
        Intrinsics.checkNotNull(this.pos);
        Rotation rot = RotationUtils.getRotations(blockPos.func_177958_n(), blockPos2.func_177956_o(), blockPos3.func_177952_p());
        if (this.legitFaceValue.get().booleanValue()) {
            RotationUtils.setTargetRotation(rot);
        }
        float yaw = rot.getYaw();
        if (this.legitStrafeValue.get().booleanValue()) {
            float speed = MovementUtils.getSpeed();
            double yaw1 = Math.toRadians(yaw);
            MinecraftInstance.f362mc.field_71439_g.field_70159_w = (-Math.sin(yaw1)) * speed;
            MinecraftInstance.f362mc.field_71439_g.field_70179_y = Math.cos(yaw1) * speed;
            return;
        }
        float strafe = event.getStrafe();
        float forward = event.getForward();
        float friction = event.getFriction();
        float f = (strafe * strafe) + (forward * forward);
        if (f >= 1.0E-4f) {
            float f2 = MathHelper.func_76129_c(f);
            if (f2 < 1.0f) {
                f2 = 1.0f;
            }
            float f3 = friction / f2;
            float strafe2 = strafe * f3;
            float forward2 = forward * f3;
            float yawSin = MathHelper.func_76126_a((float) ((yaw * 3.141592653589793d) / 180.0f));
            float yawCos = MathHelper.func_76134_b((float) ((yaw * 3.141592653589793d) / 180.0f));
            MinecraftInstance.f362mc.field_71439_g.field_70159_w += (strafe2 * yawCos) - (forward2 * yawSin);
            MinecraftInstance.f362mc.field_71439_g.field_70179_y += (forward2 * yawCos) + (strafe2 * yawSin);
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g == null || MinecraftInstance.f362mc.field_71439_g.func_70090_H() || MinecraftInstance.f362mc.field_71439_g.func_180799_ab() || MinecraftInstance.f362mc.field_71439_g.field_70134_J || !this.shouldAffect) {
            return;
        }
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1234547235:
                if (lowerCase.equals("aacpush")) {
                    this.jump = true;
                    if (!MinecraftInstance.f362mc.field_71439_g.field_70124_G) {
                        event.cancelEvent();
                        return;
                    }
                    return;
                }
                return;
            case -1234264725:
                if (lowerCase.equals("aaczero") && MinecraftInstance.f362mc.field_71439_g.field_70737_aN > 0) {
                    event.cancelEvent();
                    return;
                }
                return;
            case 92570113:
                if (lowerCase.equals("aacv4") && MinecraftInstance.f362mc.field_71439_g.field_70737_aN > 0) {
                    event.cancelEvent();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
