package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.verus;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/verus/VulcanLowHop.class */
public class VulcanLowHop extends SpeedMode {
    public VulcanLowHop() {
        super("VulcanLowHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
        if (!f362mc.field_71439_g.field_70134_J && !f362mc.field_71439_g.func_180799_ab() && !f362mc.field_71439_g.func_70090_H() && !f362mc.field_71439_g.func_70617_f_() && f362mc.field_71439_g.field_70154_o == null && MovementUtils.isMoving()) {
            f362mc.field_71474_y.field_74314_A.field_74513_e = false;
            if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.func_70664_aZ();
                f362mc.field_71439_g.field_70181_x = 0.0d;
                MovementUtils.strafe(0.61f);
                event.setY(0.41999998688698d);
            }
            MovementUtils.strafe();
        }
    }
}
