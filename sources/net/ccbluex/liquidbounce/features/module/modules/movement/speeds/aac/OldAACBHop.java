package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/OldAACBHop.class */
public class OldAACBHop extends SpeedMode {
    public OldAACBHop() {
        super("OldAACBHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (MovementUtils.isMoving()) {
            if (f362mc.field_71439_g.field_70122_E) {
                MovementUtils.strafe(0.56f);
                f362mc.field_71439_g.field_70181_x = 0.41999998688697815d;
                return;
            }
            MovementUtils.strafe(MovementUtils.getSpeed() * (f362mc.field_71439_g.field_70143_R > 0.4f ? 1.0f : 1.01f));
            return;
        }
        f362mc.field_71439_g.field_70159_w = 0.0d;
        f362mc.field_71439_g.field_70179_y = 0.0d;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
