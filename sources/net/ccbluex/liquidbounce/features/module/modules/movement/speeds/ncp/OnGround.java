package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/OnGround.class */
public class OnGround extends SpeedMode {
    public OnGround() {
        super("OnGround");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (!MovementUtils.isMoving() || f362mc.field_71439_g.field_70143_R > 3.994d || f362mc.field_71439_g.func_70090_H() || f362mc.field_71439_g.func_70617_f_() || f362mc.field_71439_g.field_70123_F) {
            return;
        }
        f362mc.field_71439_g.field_70163_u -= 0.3993000090122223d;
        f362mc.field_71439_g.field_70181_x = -1000.0d;
        f362mc.field_71439_g.field_70726_aT = 0.3f;
        f362mc.field_71439_g.field_70140_Q = 44.0f;
        f362mc.field_71428_T.field_74278_d = 1.0f;
        if (f362mc.field_71439_g.field_70122_E) {
            f362mc.field_71439_g.field_70163_u += 0.3993000090122223d;
            f362mc.field_71439_g.field_70181_x = 0.3993000090122223d;
            f362mc.field_71439_g.field_82151_R = 44.0f;
            f362mc.field_71439_g.field_70159_w *= 1.590000033378601d;
            f362mc.field_71439_g.field_70179_y *= 1.590000033378601d;
            f362mc.field_71439_g.field_70726_aT = 0.0f;
            f362mc.field_71428_T.field_74278_d = 1.199f;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
