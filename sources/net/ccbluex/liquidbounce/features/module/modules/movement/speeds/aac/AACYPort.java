package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACYPort.class */
public class AACYPort extends SpeedMode {
    public AACYPort() {
        super("AACYPort");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (MovementUtils.isMoving() && !f362mc.field_71439_g.func_70093_af()) {
            f362mc.field_71439_g.field_70726_aT = 0.0f;
            if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.field_70181_x = 0.3425000011920929d;
                f362mc.field_71439_g.field_70159_w *= 1.5893000364303589d;
                f362mc.field_71439_g.field_70179_y *= 1.5893000364303589d;
                return;
            }
            f362mc.field_71439_g.field_70181_x = -0.19d;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
