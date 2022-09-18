package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC2BHop.class */
public class AAC2BHop extends SpeedMode {
    public AAC2BHop() {
        super("AAC2BHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (f362mc.field_71439_g.func_70090_H()) {
            return;
        }
        if (MovementUtils.isMoving()) {
            if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.func_70664_aZ();
                f362mc.field_71439_g.field_70159_w *= 1.02d;
                f362mc.field_71439_g.field_70179_y *= 1.02d;
                return;
            } else if (f362mc.field_71439_g.field_70181_x > -0.2d) {
                f362mc.field_71439_g.field_70747_aH = 0.08f;
                f362mc.field_71439_g.field_70181_x += 0.01431d;
                f362mc.field_71439_g.field_70747_aH = 0.07f;
                return;
            } else {
                return;
            }
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
