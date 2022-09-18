package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/NCPFHop.class */
public class NCPFHop extends SpeedMode {
    public NCPFHop() {
        super("NCPFHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onEnable() {
        f362mc.field_71428_T.field_74278_d = 1.0866f;
        super.onEnable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71439_g.field_71102_ce = 0.02f;
        f362mc.field_71428_T.field_74278_d = 1.0f;
        super.onDisable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        if (MovementUtils.isMoving()) {
            if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.func_70664_aZ();
                f362mc.field_71439_g.field_70159_w *= 1.01d;
                f362mc.field_71439_g.field_70179_y *= 1.01d;
                f362mc.field_71439_g.field_71102_ce = 0.0223f;
            }
            f362mc.field_71439_g.field_70181_x -= 9.9999E-4d;
            MovementUtils.strafe();
            return;
        }
        f362mc.field_71439_g.field_70159_w = 0.0d;
        f362mc.field_71439_g.field_70179_y = 0.0d;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
