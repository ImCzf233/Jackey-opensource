package net.ccbluex.liquidbounce.features.module.modules.movement;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.awt.Color;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.internal.progressionUtil;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: TargetStrafe.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u00108\u001a\u00020\u000bH\u0002J\u0011\u00109\u001a\b\u0012\u0004\u0012\u00020;0:¢\u0006\u0002\u0010<J\u0006\u0010=\u001a\u00020>J\u0006\u0010?\u001a\u00020;J\u0018\u0010@\u001a\u00020\u000b2\u0006\u0010A\u001a\u00020\u00112\u0006\u0010B\u001a\u00020\u0011H\u0002J \u0010C\u001a\u00020>2\u0006\u0010D\u001a\u00020E2\u0006\u00102\u001a\u00020>2\u0006\u0010F\u001a\u00020;H\u0002J\b\u0010G\u001a\u00020HH\u0016J\b\u0010I\u001a\u00020HH\u0016J\u0010\u0010J\u001a\u00020H2\u0006\u0010K\u001a\u00020LH\u0007J\u0010\u0010M\u001a\u00020H2\u0006\u0010K\u001a\u00020NH\u0007J\u0010\u0010O\u001a\u00020H2\u0006\u0010K\u001a\u00020PH\u0007J\u0016\u0010Q\u001a\u00020H2\u0006\u0010K\u001a\u00020N2\u0006\u0010R\u001a\u00020>R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\u001a\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001b\u0010\r\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001f\u0010\r\"\u0004\b \u0010\u001dR\u0011\u0010!\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\"\u0010\rR\u000e\u0010#\u001a\u00020$X\u0082.¢\u0006\u0002\n��R\u001a\u0010%\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b&\u0010\u0013\"\u0004\b'\u0010\u0015R\u000e\u0010(\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010)\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010*\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010+\u001a\u00020\t¢\u0006\b\n��\u001a\u0004\b,\u0010-R\u000e\u0010.\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010/\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00100\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00101\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00102\u001a\u000203X\u0082.¢\u0006\u0002\n��R\u000e\u00104\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u0011\u00105\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b6\u00107¨\u0006S"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/TargetStrafe;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "accuracyValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "alwaysRender", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "blueValue", "brightnessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "canStrafe", "", "getCanStrafe", "()Z", "colorType", "Lnet/ccbluex/liquidbounce/value/ListValue;", "direction", "", "getDirection", "()I", "setDirection", "(I)V", "expMode", "fly", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Fly;", "greenValue", "hasChangedThirdPerson", "getHasChangedThirdPerson", "setHasChangedThirdPerson", "(Z)V", "hasModifiedMovement", "getHasModifiedMovement", "setHasModifiedMovement", "keyMode", "getKeyMode", "killAura", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "lastView", "getLastView", "setLastView", "mixerSecondsValue", "modeValue", "outLine", "radius", "getRadius", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "redValue", "render", "safewalk", "saturationValue", "speed", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Speed;", "thicknessValue", "thirdPerson", "getThirdPerson", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "checkVoid", "getData", "", "", "()[Ljava/lang/Float;", "getMovingDir", "", "getMovingYaw", "isVoid", "X", "Z", "maximizeSpeed", "ent", "Lnet/minecraft/entity/EntityLivingBase;", AsmConstants.CODERANGE, "onEnable", "", "onInitialize", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "strafe", "moveSpeed", "LiquidBounce"})
@ModuleInfo(name = "TargetStrafe", spacedName = "Target Strafe", description = "Strafe around your target. (Require Fly or Speed to be enabled)", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/TargetStrafe.class */
public final class TargetStrafe extends Module {
    private KillAura killAura;
    private Speed speed;
    private Fly fly;
    private int lastView;
    private boolean hasModifiedMovement;
    @NotNull
    private final FloatValue radius = new FloatValue("Radius", 2.0f, 0.1f, 4.0f, "m");
    @NotNull
    private final BoolValue render = new BoolValue("Render", true);
    @NotNull
    private final BoolValue alwaysRender = new BoolValue("Always-Render", true, new TargetStrafe$alwaysRender$1(this));
    @NotNull
    private final ListValue modeValue = new ListValue("KeyMode", new String[]{"Jump", "None"}, "None");
    @NotNull
    private final BoolValue safewalk = new BoolValue("SafeWalk", true);
    @NotNull
    private final BoolValue thirdPerson = new BoolValue("ThirdPerson", true);
    @NotNull
    private final ListValue colorType = new ListValue("Color", new String[]{"Custom", "Dynamic", "Rainbow", "Rainbow2", "Sky", "Fade", "Mixer"}, "Custom");
    @NotNull
    private final IntegerValue redValue = new IntegerValue("Red", 255, 0, 255);
    @NotNull
    private final IntegerValue greenValue = new IntegerValue("Green", 255, 0, 255);
    @NotNull
    private final IntegerValue blueValue = new IntegerValue("Blue", 255, 0, 255);
    @NotNull
    private final FloatValue saturationValue = new FloatValue("Saturation", 0.7f, 0.0f, 1.0f);
    @NotNull
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
    @NotNull
    private final IntegerValue mixerSecondsValue = new IntegerValue("Mixer-Seconds", 2, 1, 10);
    @NotNull
    private final IntegerValue accuracyValue = new IntegerValue("Accuracy", 0, 0, 59);
    @NotNull
    private final FloatValue thicknessValue = new FloatValue("Thickness", 1.0f, 0.1f, 5.0f);
    @NotNull
    private final BoolValue outLine = new BoolValue("Outline", true);
    @NotNull
    private final BoolValue expMode = new BoolValue("ExperimentalSpeed", false);
    private int direction = 1;
    private boolean hasChangedThirdPerson = true;

    @NotNull
    public final FloatValue getRadius() {
        return this.radius;
    }

    @NotNull
    public final BoolValue getThirdPerson() {
        return this.thirdPerson;
    }

    public final int getDirection() {
        return this.direction;
    }

    public final void setDirection(int i) {
        this.direction = i;
    }

    public final int getLastView() {
        return this.lastView;
    }

    public final void setLastView(int i) {
        this.lastView = i;
    }

    public final boolean getHasChangedThirdPerson() {
        return this.hasChangedThirdPerson;
    }

    public final void setHasChangedThirdPerson(boolean z) {
        this.hasChangedThirdPerson = z;
    }

    public final boolean getHasModifiedMovement() {
        return this.hasModifiedMovement;
    }

    public final void setHasModifiedMovement(boolean z) {
        this.hasModifiedMovement = z;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onInitialize() {
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
        if (module == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        }
        this.killAura = (KillAura) module;
        Module module2 = LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
        if (module2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.Speed");
        }
        this.speed = (Speed) module2;
        Module module3 = LiquidBounce.INSTANCE.getModuleManager().getModule(Fly.class);
        if (module3 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.Fly");
        }
        this.fly = (Fly) module3;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.hasChangedThirdPerson = true;
        this.lastView = MinecraftInstance.f362mc.field_71474_y.field_74320_O;
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.thirdPerson.get().booleanValue()) {
            if (getCanStrafe()) {
                if (this.hasChangedThirdPerson) {
                    this.lastView = MinecraftInstance.f362mc.field_71474_y.field_74320_O;
                }
                MinecraftInstance.f362mc.field_71474_y.field_74320_O = 1;
                this.hasChangedThirdPerson = false;
            } else if (!this.hasChangedThirdPerson) {
                MinecraftInstance.f362mc.field_71474_y.field_74320_O = this.lastView;
                this.hasChangedThirdPerson = true;
            }
        }
        if (event.getEventState() == EventState.PRE) {
            if (MinecraftInstance.f362mc.field_71439_g.field_70123_F) {
                this.direction = -this.direction;
            }
            if (MinecraftInstance.f362mc.field_71474_y.field_74370_x.field_74513_e) {
                this.direction = 1;
            }
            if (MinecraftInstance.f362mc.field_71474_y.field_74366_z.field_74513_e) {
                this.direction = -1;
            }
        }
    }

    @EventTarget(priority = 2)
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (getCanStrafe()) {
            if (!this.hasModifiedMovement) {
                strafe(event, MovementUtils.getSpeed(event.getX(), event.getZ()));
            }
            if (this.safewalk.get().booleanValue() && checkVoid()) {
                event.setSafeWalk(true);
            }
        }
        this.hasModifiedMovement = false;
    }

    public final void strafe(@NotNull MoveEvent event, double moveSpeed) {
        double d;
        Intrinsics.checkNotNullParameter(event, "event");
        KillAura killAura = this.killAura;
        if (killAura == null) {
            Intrinsics.throwUninitializedPropertyAccessException("killAura");
            killAura = null;
        }
        if (killAura.getTarget() == null) {
            return;
        }
        KillAura killAura2 = this.killAura;
        if (killAura2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("killAura");
            killAura2 = null;
        }
        Entity target = killAura2.getTarget();
        Intrinsics.checkNotNull(target);
        float rotYaw = RotationUtils.getRotationsEntity(target).getYaw();
        double forward = MinecraftInstance.f362mc.field_71439_g.func_70032_d(target) <= this.radius.get().floatValue() ? 0.0d : 1.0d;
        double strafe = this.direction;
        if (this.expMode.get().booleanValue()) {
            KillAura killAura3 = this.killAura;
            if (killAura3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("killAura");
                killAura3 = null;
            }
            d = maximizeSpeed(target, moveSpeed, killAura3.getRangeValue().get().floatValue());
        } else {
            d = moveSpeed;
        }
        double modifySpeed = d;
        MovementUtils.setSpeed(event, modifySpeed, rotYaw, strafe, forward);
        this.hasModifiedMovement = true;
    }

    @NotNull
    public final Float[] getData() {
        KillAura killAura = this.killAura;
        if (killAura == null) {
            Intrinsics.throwUninitializedPropertyAccessException("killAura");
            killAura = null;
        }
        if (killAura.getTarget() == null) {
            return new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)};
        }
        KillAura killAura2 = this.killAura;
        if (killAura2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("killAura");
            killAura2 = null;
        }
        Entity target = killAura2.getTarget();
        Intrinsics.checkNotNull(target);
        float rotYaw = RotationUtils.getRotationsEntity(target).getYaw();
        float forward = MinecraftInstance.f362mc.field_71439_g.func_70032_d(target) <= this.radius.get().floatValue() ? 0.0f : 1.0f;
        float strafe = this.direction;
        return new Float[]{Float.valueOf(rotYaw), Float.valueOf(strafe), Float.valueOf(forward)};
    }

    public final float getMovingYaw() {
        Float[] dt = getData();
        return MovementUtils.getRawDirectionRotation(dt[0].floatValue(), dt[1].floatValue(), dt[2].floatValue());
    }

    public final double getMovingDir() {
        Float[] dt = getData();
        return MovementUtils.getDirectionRotation(dt[0].floatValue(), dt[1].floatValue(), dt[2].floatValue());
    }

    private final double maximizeSpeed(EntityLivingBase ent, double speed, float range) {
        if (MinecraftInstance.f362mc.field_71439_g == null) {
            return 0.0d;
        }
        return MinecraftInstance.f362mc.field_71439_g.func_70032_d((Entity) ent) <= this.radius.get().floatValue() ? RangesKt.coerceIn(speed, 0.0d, range / 20.0d) : speed;
    }

    public final boolean getKeyMode() {
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        if (Intrinsics.areEqual(lowerCase, "jump")) {
            return MinecraftInstance.f362mc.field_71474_y.field_74314_A.func_151470_d();
        }
        if (!Intrinsics.areEqual(lowerCase, "none")) {
            return false;
        }
        if (MinecraftInstance.f362mc.field_71439_g.field_71158_b.field_78902_a == 0.0f) {
            if (MinecraftInstance.f362mc.field_71439_g.field_71158_b.field_78900_b == 0.0f) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0030, code lost:
        if (r0.getState() != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean getCanStrafe() {
        /*
            r3 = this;
            r0 = r3
            boolean r0 = r0.getState()
            if (r0 == 0) goto L76
            r0 = r3
            net.ccbluex.liquidbounce.features.module.modules.movement.Speed r0 = r0.speed
            r1 = r0
            if (r1 != 0) goto L17
        L10:
            java.lang.String r0 = "speed"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r0 = 0
        L17:
            boolean r0 = r0.getState()
            if (r0 != 0) goto L33
            r0 = r3
            net.ccbluex.liquidbounce.features.module.modules.movement.Fly r0 = r0.fly
            r1 = r0
            if (r1 != 0) goto L2d
        L26:
            java.lang.String r0 = "fly"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r0 = 0
        L2d:
            boolean r0 = r0.getState()
            if (r0 == 0) goto L76
        L33:
            r0 = r3
            net.ccbluex.liquidbounce.features.module.modules.combat.KillAura r0 = r0.killAura
            r1 = r0
            if (r1 != 0) goto L43
        L3c:
            java.lang.String r0 = "killAura"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r0 = 0
        L43:
            boolean r0 = r0.getState()
            if (r0 == 0) goto L76
            r0 = r3
            net.ccbluex.liquidbounce.features.module.modules.combat.KillAura r0 = r0.killAura
            r1 = r0
            if (r1 != 0) goto L59
        L52:
            java.lang.String r0 = "killAura"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r0 = 0
        L59:
            net.minecraft.entity.EntityLivingBase r0 = r0.getTarget()
            if (r0 == 0) goto L76
            net.minecraft.client.Minecraft r0 = net.ccbluex.liquidbounce.utils.MinecraftInstance.f362mc
            net.minecraft.client.entity.EntityPlayerSP r0 = r0.field_71439_g
            boolean r0 = r0.func_70093_af()
            if (r0 != 0) goto L76
            r0 = r3
            boolean r0 = r0.getKeyMode()
            if (r0 == 0) goto L76
            r0 = 1
            goto L77
        L76:
            r0 = 0
        L77:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe.getCanStrafe():boolean");
    }

    private final boolean checkVoid() {
        int i = -1;
        while (i < 1) {
            int x = i;
            i++;
            int i2 = -1;
            while (i2 < 1) {
                int z = i2;
                i2++;
                if (isVoid(x, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isVoid(int X, int Z) {
        if (MinecraftInstance.f362mc.field_71439_g.field_70163_u < 0.0d) {
            return true;
        }
        for (int off = 0; off < ((int) MinecraftInstance.f362mc.field_71439_g.field_70163_u) + 2; off += 2) {
            AxisAlignedBB bb = MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().func_72317_d(X, -off, Z);
            Intrinsics.checkNotNullExpressionValue(bb, "mc.thePlayer.entityBound…toDouble(), Z.toDouble())");
            WorldClient worldClient = MinecraftInstance.f362mc.field_71441_e;
            Intrinsics.checkNotNull(worldClient);
            Entity entity = MinecraftInstance.f362mc.field_71439_g;
            if (entity == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.Entity");
            }
            if (!worldClient.func_72945_a(entity, bb).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(event, "event");
        KillAura killAura = this.killAura;
        if (killAura == null) {
            Intrinsics.throwUninitializedPropertyAccessException("killAura");
            killAura = null;
        }
        EntityLivingBase target = killAura.getTarget();
        if ((getCanStrafe() || this.alwaysRender.get().booleanValue()) && this.render.get().booleanValue() && target != null) {
            GL11.glPushMatrix();
            GL11.glTranslated((target.field_70142_S + ((target.field_70165_t - target.field_70142_S) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - MinecraftInstance.f362mc.func_175598_ae().field_78725_b, (target.field_70137_T + ((target.field_70163_u - target.field_70137_T) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - MinecraftInstance.f362mc.func_175598_ae().field_78726_c, (target.field_70136_U + ((target.field_70161_v - target.field_70136_U) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - MinecraftInstance.f362mc.func_175598_ae().field_78723_d);
            GL11.glEnable(3042);
            GL11.glEnable(2848);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glBlendFunc(770, 771);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            if (this.outLine.get().booleanValue()) {
                GL11.glLineWidth(this.thicknessValue.get().floatValue() + 1.25f);
                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glBegin(2);
                int intValue = 60 - this.accuracyValue.get().intValue();
                if (intValue <= 0) {
                    throw new IllegalArgumentException("Step must be positive, was: " + intValue + '.');
                }
                int i3 = 0;
                int progressionLastElement = progressionUtil.getProgressionLastElement(0, (int) TokenId.EXOR_E, intValue);
                if (0 <= progressionLastElement) {
                    do {
                        i2 = i3;
                        i3 += intValue;
                        GL11.glVertex2f(((float) Math.cos((i2 * 3.141592653589793d) / 180.0d)) * this.radius.get().floatValue(), ((float) Math.sin((i2 * 3.141592653589793d) / 180.0d)) * this.radius.get().floatValue());
                    } while (i2 != progressionLastElement);
                    GL11.glEnd();
                } else {
                    GL11.glEnd();
                }
            }
            Color rainbow2 = ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
            Color sky = RenderUtils.skyRainbow(0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
            Color mixer = ColorMixer.getMixedColor(0, this.mixerSecondsValue.get().intValue());
            Color fade = ColorUtils.fade(new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue()), 0, 100);
            GL11.glLineWidth(this.thicknessValue.get().floatValue());
            GL11.glBegin(2);
            int intValue2 = 60 - this.accuracyValue.get().intValue();
            if (intValue2 <= 0) {
                throw new IllegalArgumentException("Step must be positive, was: " + intValue2 + '.');
            }
            int i4 = 0;
            int progressionLastElement2 = progressionUtil.getProgressionLastElement(0, (int) TokenId.EXOR_E, intValue2);
            if (0 <= progressionLastElement2) {
                do {
                    i = i4;
                    i4 += intValue2;
                    String str = this.colorType.get();
                    switch (str.hashCode()) {
                        case -1656737386:
                            if (str.equals("Rainbow")) {
                                Color rainbow = new Color(RenderUtils.getNormalRainbow(i, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue()));
                                GL11.glColor3f(rainbow.getRed() / 255.0f, rainbow.getGreen() / 255.0f, rainbow.getBlue() / 255.0f);
                                break;
                            }
                            GL11.glColor3f(fade.getRed() / 255.0f, fade.getGreen() / 255.0f, fade.getBlue() / 255.0f);
                            break;
                        case -505546721:
                            if (str.equals("Dynamic")) {
                                if (!getCanStrafe()) {
                                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                    break;
                                } else {
                                    GL11.glColor4f(0.25f, 1.0f, 0.25f, 1.0f);
                                    break;
                                }
                            }
                            GL11.glColor3f(fade.getRed() / 255.0f, fade.getGreen() / 255.0f, fade.getBlue() / 255.0f);
                            break;
                        case 83201:
                            if (str.equals("Sky")) {
                                GL11.glColor3f(sky.getRed() / 255.0f, sky.getGreen() / 255.0f, sky.getBlue() / 255.0f);
                                break;
                            }
                            GL11.glColor3f(fade.getRed() / 255.0f, fade.getGreen() / 255.0f, fade.getBlue() / 255.0f);
                            break;
                        case 74357737:
                            if (str.equals("Mixer")) {
                                GL11.glColor3f(mixer.getRed() / 255.0f, mixer.getGreen() / 255.0f, mixer.getBlue() / 255.0f);
                                break;
                            }
                            GL11.glColor3f(fade.getRed() / 255.0f, fade.getGreen() / 255.0f, fade.getBlue() / 255.0f);
                            break;
                        case 180748636:
                            if (str.equals("Rainbow2")) {
                                Intrinsics.checkNotNull(rainbow2);
                                GL11.glColor3f(rainbow2.getRed() / 255.0f, rainbow2.getGreen() / 255.0f, rainbow2.getBlue() / 255.0f);
                                break;
                            }
                            GL11.glColor3f(fade.getRed() / 255.0f, fade.getGreen() / 255.0f, fade.getBlue() / 255.0f);
                            break;
                        case 2029746065:
                            if (str.equals("Custom")) {
                                GL11.glColor3f(this.redValue.get().floatValue() / 255.0f, this.greenValue.get().floatValue() / 255.0f, this.blueValue.get().floatValue() / 255.0f);
                                break;
                            }
                            GL11.glColor3f(fade.getRed() / 255.0f, fade.getGreen() / 255.0f, fade.getBlue() / 255.0f);
                            break;
                        default:
                            GL11.glColor3f(fade.getRed() / 255.0f, fade.getGreen() / 255.0f, fade.getBlue() / 255.0f);
                            break;
                    }
                    GL11.glVertex2f(((float) Math.cos((i * 3.141592653589793d) / 180.0d)) * this.radius.get().floatValue(), ((float) Math.sin((i * 3.141592653589793d) / 180.0d)) * this.radius.get().floatValue());
                } while (i != progressionLastElement2);
                GL11.glEnd();
                GL11.glDisable(3042);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDisable(2848);
                GL11.glPopMatrix();
                GlStateManager.func_179117_G();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
            GL11.glEnd();
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
            GlStateManager.func_179117_G();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
