package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/YPort2.class */
public class YPort2 extends SpeedMode {
    public YPort2() {
        super("YPort2");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (f362mc.field_71439_g.func_70617_f_() || f362mc.field_71439_g.func_70090_H() || f362mc.field_71439_g.func_180799_ab() || f362mc.field_71439_g.field_70134_J || !MovementUtils.isMoving()) {
            return;
        }
        if (f362mc.field_71439_g.field_70122_E) {
            f362mc.field_71439_g.func_70664_aZ();
        } else {
            f362mc.field_71439_g.field_70181_x = -1.0d;
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
