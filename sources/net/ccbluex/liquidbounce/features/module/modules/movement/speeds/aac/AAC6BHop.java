package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC6BHop.class */
public class AAC6BHop extends SpeedMode {
    private boolean legitJump;

    public AAC6BHop() {
        super("AAC6BHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
        if (f362mc.field_71439_g.func_70090_H()) {
            return;
        }
        if (MovementUtils.isMoving()) {
            if (f362mc.field_71439_g.field_70122_E) {
                if (this.legitJump) {
                    f362mc.field_71439_g.field_70181_x = 0.4d;
                    MovementUtils.strafe(0.15f);
                    f362mc.field_71439_g.field_70122_E = false;
                    this.legitJump = false;
                    return;
                }
                f362mc.field_71439_g.field_70181_x = 0.41d;
                MovementUtils.strafe(0.47458485f);
            }
            if (f362mc.field_71439_g.field_70181_x < 0.0d && f362mc.field_71439_g.field_70181_x > -0.2d) {
                f362mc.field_71428_T.field_74278_d = (float) (1.2d + f362mc.field_71439_g.field_70181_x);
            }
            f362mc.field_71439_g.field_71102_ce = 0.022151f;
            return;
        }
        this.legitJump = true;
        f362mc.field_71439_g.field_70159_w = 0.0d;
        f362mc.field_71439_g.field_70179_y = 0.0d;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onEnable() {
        this.legitJump = true;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
        f362mc.field_71439_g.field_71102_ce = 0.02f;
    }
}
