package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC4BHop.class */
public class AAC4BHop extends SpeedMode {
    private boolean legitHop;

    public AAC4BHop() {
        super("AAC4BHop");
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
    public void onEnable() {
        this.legitHop = true;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71439_g.field_71102_ce = 0.02f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onTick() {
        if (MovementUtils.isMoving()) {
            if (this.legitHop) {
                if (f362mc.field_71439_g.field_70122_E) {
                    f362mc.field_71439_g.func_70664_aZ();
                    f362mc.field_71439_g.field_70122_E = false;
                    this.legitHop = false;
                    return;
                }
                return;
            } else if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.field_70122_E = false;
                MovementUtils.strafe(0.375f);
                f362mc.field_71439_g.func_70664_aZ();
                f362mc.field_71439_g.field_70181_x = 0.41d;
                return;
            } else {
                f362mc.field_71439_g.field_71102_ce = 0.0211f;
                return;
            }
        }
        f362mc.field_71439_g.field_70159_w = 0.0d;
        f362mc.field_71439_g.field_70179_y = 0.0d;
        this.legitHop = true;
    }
}
