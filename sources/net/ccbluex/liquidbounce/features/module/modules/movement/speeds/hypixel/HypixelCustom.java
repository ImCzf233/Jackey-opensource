package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.hypixel;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.world.Timer;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

/* compiled from: HypixelCustom.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0004H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/hypixel/HypixelCustom;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onJump", "", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/hypixel/HypixelCustom.class */
public final class HypixelCustom extends SpeedMode {
    public HypixelCustom() {
        super("HypixelCustom");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (SpeedMode.f362mc.field_71439_g != null && MovementUtils.isMoving()) {
            event.cancelEvent();
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        EntityPlayerSP thePlayer = SpeedMode.f362mc.field_71439_g;
        if (thePlayer == null) {
            return;
        }
        Speed speedModule = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
        Intrinsics.checkNotNull(speedModule);
        Module scaffoldModule = LiquidBounce.INSTANCE.getModuleManager().getModule(Scaffold.class);
        Module timer = LiquidBounce.INSTANCE.getModuleManager().getModule(Timer.class);
        if (MovementUtils.isMoving()) {
            if (thePlayer.field_70122_E && thePlayer.field_70124_G) {
                Intrinsics.checkNotNull(scaffoldModule);
                thePlayer.field_70181_x = MovementUtils.getJumpBoostModifier(scaffoldModule.getState() ? 0.41999d : speedModule.motionYValue.get().floatValue(), true);
                if (scaffoldModule.getState()) {
                    MovementUtils.strafe(0.37f);
                    return;
                } else {
                    MovementUtils.strafe((float) Math.max(speedModule.customSpeedValue.get().doubleValue() + (MovementUtils.getSpeedEffect() * 0.1d), MovementUtils.getBaseMoveSpeed(0.2873d)));
                    return;
                }
            }
            Intrinsics.checkNotNull(timer);
            if (!timer.getState() && speedModule.timerValue.get().booleanValue()) {
                SpeedMode.f362mc.field_71428_T.field_74278_d = 1.07f;
            }
            MovementUtils.setMotion(MovementUtils.getSpeed(), speedModule.smoothStrafe.get().booleanValue());
            return;
        }
        thePlayer.field_70159_w *= 0.0d;
        thePlayer.field_70179_y *= 0.0d;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        EntityPlayerSP thePlayer = SpeedMode.f362mc.field_71439_g;
        if (thePlayer == null) {
            return;
        }
        Speed speedModule = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
        Intrinsics.checkNotNull(speedModule);
        if (MovementUtils.isMoving() && thePlayer.field_70123_F) {
            MovementUtils.setMotion(event, MovementUtils.getBaseMoveSpeed(0.258d), 1.0d, speedModule.smoothStrafe.get().booleanValue());
        }
    }
}
