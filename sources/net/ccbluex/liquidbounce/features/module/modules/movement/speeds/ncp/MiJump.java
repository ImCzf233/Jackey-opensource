package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/MiJump.class */
public class MiJump extends SpeedMode {
    public MiJump() {
        super("MiJump");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (!MovementUtils.isMoving()) {
            return;
        }
        if (f362mc.field_71439_g.field_70122_E && !f362mc.field_71439_g.field_71158_b.field_78901_c) {
            f362mc.field_71439_g.field_70181_x += 0.1d;
            f362mc.field_71439_g.field_70159_w *= 1.8d;
            f362mc.field_71439_g.field_70179_y *= 1.8d;
            double currentSpeed = Math.sqrt(Math.pow(f362mc.field_71439_g.field_70159_w, 2.0d) + Math.pow(f362mc.field_71439_g.field_70179_y, 2.0d));
            if (currentSpeed > 0.66d) {
                f362mc.field_71439_g.field_70159_w = (f362mc.field_71439_g.field_70159_w / currentSpeed) * 0.66d;
                f362mc.field_71439_g.field_70179_y = (f362mc.field_71439_g.field_70179_y / currentSpeed) * 0.66d;
            }
        }
        MovementUtils.strafe();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
