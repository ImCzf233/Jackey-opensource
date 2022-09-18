package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/CustomSpeed.class */
public class CustomSpeed extends SpeedMode {
    private int groundTick = 0;

    public CustomSpeed() {
        super("Custom");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion(MotionEvent eventMotion) {
        Speed speed = (Speed) LiquidBounce.moduleManager.getModule(Speed.class);
        if (speed == null || eventMotion.getEventState() != EventState.PRE) {
            return;
        }
        if (MovementUtils.isMoving()) {
            f362mc.field_71428_T.field_74278_d = f362mc.field_71439_g.field_70181_x > 0.0d ? speed.upTimerValue.get().floatValue() : speed.downTimerValue.get().floatValue();
            if (f362mc.field_71439_g.field_70122_E) {
                if (this.groundTick >= speed.groundStay.get().intValue()) {
                    if (speed.doLaunchSpeedValue.get().booleanValue()) {
                        MovementUtils.strafe(speed.launchSpeedValue.get().floatValue());
                    }
                    if (speed.yValue.get().floatValue() != 0.0f) {
                        f362mc.field_71439_g.field_70181_x = speed.yValue.get().floatValue();
                    }
                } else if (speed.groundResetXZValue.get().booleanValue()) {
                    f362mc.field_71439_g.field_70159_w = 0.0d;
                    f362mc.field_71439_g.field_70179_y = 0.0d;
                }
                this.groundTick++;
                return;
            }
            this.groundTick = 0;
            String lowerCase = speed.strafeValue.get().toLowerCase();
            boolean z = true;
            switch (lowerCase.hashCode()) {
                case -891993841:
                    if (lowerCase.equals("strafe")) {
                        z = false;
                        break;
                    }
                    break;
                case 3444122:
                    if (lowerCase.equals("plus")) {
                        z = true;
                        break;
                    }
                    break;
                case 93922211:
                    if (lowerCase.equals("boost")) {
                        z = true;
                        break;
                    }
                    break;
                case 1845585249:
                    if (lowerCase.equals("plusonlyup")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    MovementUtils.strafe(speed.speedValue.get().floatValue());
                    break;
                case true:
                    MovementUtils.strafe();
                    break;
                case true:
                    MovementUtils.accelerate(speed.speedValue.get().floatValue() * 0.1f);
                    break;
                case true:
                    if (f362mc.field_71439_g.field_70181_x > 0.0d) {
                        MovementUtils.accelerate(speed.speedValue.get().floatValue() * 0.1f);
                        break;
                    } else {
                        MovementUtils.strafe();
                        break;
                    }
            }
            f362mc.field_71439_g.field_70181_x += speed.addYMotionValue.get().floatValue() * 0.03d;
        } else if (speed.resetXZValue.get().booleanValue()) {
            f362mc.field_71439_g.field_70159_w = 0.0d;
            f362mc.field_71439_g.field_70179_y = 0.0d;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onEnable() {
        Speed speed = (Speed) LiquidBounce.moduleManager.getModule(Speed.class);
        if (speed == null) {
            return;
        }
        if (speed.resetXZValue.get().booleanValue()) {
            EntityPlayerSP entityPlayerSP = f362mc.field_71439_g;
            f362mc.field_71439_g.field_70179_y = 0.0d;
            entityPlayerSP.field_70159_w = 0.0d;
        }
        if (speed.resetYValue.get().booleanValue()) {
            f362mc.field_71439_g.field_70181_x = 0.0d;
        }
        super.onEnable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
        super.onDisable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
