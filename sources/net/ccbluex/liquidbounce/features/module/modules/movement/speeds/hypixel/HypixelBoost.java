package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.hypixel;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/hypixel/HypixelBoost.class */
public class HypixelBoost extends SpeedMode {
    public HypixelBoost() {
        super("HypixelBoost");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
        TargetStrafe targetStrafe;
        Speed speed = (Speed) LiquidBounce.moduleManager.getModule(Speed.class);
        if (speed == null || (targetStrafe = (TargetStrafe) LiquidBounce.moduleManager.getModule(TargetStrafe.class)) == null) {
            return;
        }
        f362mc.field_71428_T.field_74278_d = 1.0f;
        if (MovementUtils.isMoving() && !f362mc.field_71439_g.func_70090_H() && !f362mc.field_71439_g.func_180799_ab() && !f362mc.field_71474_y.field_74314_A.func_151470_d()) {
            double moveSpeed = Math.max(MovementUtils.getBaseMoveSpeed() * speed.baseStrengthValue.get().floatValue(), MovementUtils.getSpeed());
            if (f362mc.field_71439_g.field_70122_E) {
                if (speed.sendJumpValue.get().booleanValue()) {
                    f362mc.field_71439_g.func_70664_aZ();
                }
                if (speed.recalcValue.get().booleanValue()) {
                    moveSpeed = Math.max(MovementUtils.getBaseMoveSpeed() * speed.baseStrengthValue.get().floatValue(), MovementUtils.getSpeed());
                }
                EntityPlayerSP entityPlayerSP = f362mc.field_71439_g;
                double jumpBoostModifier = MovementUtils.getJumpBoostModifier(f362mc.field_71439_g.field_70123_F ? 0.42d : speed.jumpYValue.get().floatValue());
                entityPlayerSP.field_70181_x = jumpBoostModifier;
                event.setY(jumpBoostModifier);
                moveSpeed *= speed.moveSpeedValue.get().floatValue();
            } else if (speed.glideStrengthValue.get().floatValue() > 0.0f && event.getY() < 0.0d) {
                EntityPlayerSP entityPlayerSP2 = f362mc.field_71439_g;
                double floatValue = entityPlayerSP2.field_70181_x + speed.glideStrengthValue.get().floatValue();
                entityPlayerSP2.field_70181_x = floatValue;
                event.setY(floatValue);
            }
            f362mc.field_71428_T.field_74278_d = Math.max(speed.baseTimerValue.get().floatValue() + (Math.abs((float) f362mc.field_71439_g.field_70181_x) * speed.baseMTimerValue.get().floatValue()), 1.0f);
            if (targetStrafe.getCanStrafe()) {
                targetStrafe.strafe(event, moveSpeed);
            } else {
                MovementUtils.setSpeed(event, moveSpeed);
            }
        }
    }
}
