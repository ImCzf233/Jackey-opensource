package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC5BHop.class */
public class AAC5BHop extends SpeedMode {
    private boolean legitJump;

    public AAC5BHop() {
        super("AAC5BHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onTick() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
        if (f362mc.field_71439_g.func_70090_H()) {
            return;
        }
        if (MovementUtils.isMoving()) {
            if (f362mc.field_71439_g.field_70122_E) {
                if (this.legitJump) {
                    f362mc.field_71439_g.func_70664_aZ();
                    this.legitJump = false;
                    return;
                }
                f362mc.field_71439_g.field_70181_x = 0.41d;
                f362mc.field_71439_g.field_70122_E = false;
                MovementUtils.strafe(0.374f);
                return;
            } else if (f362mc.field_71439_g.field_70181_x < 0.0d) {
                f362mc.field_71439_g.field_71102_ce = 0.0201f;
                f362mc.field_71428_T.field_74278_d = 1.02f;
                return;
            } else {
                f362mc.field_71428_T.field_74278_d = 1.01f;
                return;
            }
        }
        this.legitJump = true;
        f362mc.field_71439_g.field_70159_w = 0.0d;
        f362mc.field_71439_g.field_70179_y = 0.0d;
    }
}
