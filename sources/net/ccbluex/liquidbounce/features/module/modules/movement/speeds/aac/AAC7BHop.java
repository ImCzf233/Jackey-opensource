package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC7BHop.class */
public class AAC7BHop extends SpeedMode {
    public AAC7BHop() {
        super("AAC7BHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        if (!MovementUtils.isMoving() || f362mc.field_71439_g.field_70154_o != null || f362mc.field_71439_g.field_70737_aN > 0) {
            return;
        }
        if (f362mc.field_71439_g.field_70122_E) {
            f362mc.field_71439_g.func_70664_aZ();
            f362mc.field_71439_g.field_70181_x = 0.405d;
            f362mc.field_71439_g.field_70159_w *= 1.004d;
            f362mc.field_71439_g.field_70179_y *= 1.004d;
            return;
        }
        double speed = MovementUtils.getSpeed() * 1.0072d;
        double yaw = Math.toRadians(f362mc.field_71439_g.field_70177_z);
        f362mc.field_71439_g.field_70159_w = (-Math.sin(yaw)) * speed;
        f362mc.field_71439_g.field_70179_y = Math.cos(yaw) * speed;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
