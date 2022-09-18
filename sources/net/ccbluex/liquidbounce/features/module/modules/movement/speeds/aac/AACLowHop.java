package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACLowHop.class */
public class AACLowHop extends SpeedMode {
    private boolean legitJump;

    public AACLowHop() {
        super("AACLowHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onEnable() {
        this.legitJump = true;
        super.onEnable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (MovementUtils.isMoving()) {
            if (f362mc.field_71439_g.field_70122_E) {
                if (this.legitJump) {
                    f362mc.field_71439_g.func_70664_aZ();
                    this.legitJump = false;
                    return;
                }
                f362mc.field_71439_g.field_70181_x = 0.34299999475479126d;
                MovementUtils.strafe(0.534f);
                return;
            }
            return;
        }
        this.legitJump = true;
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
