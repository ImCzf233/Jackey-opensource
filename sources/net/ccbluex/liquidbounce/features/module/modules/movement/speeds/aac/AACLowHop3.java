package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACLowHop3.class */
public class AACLowHop3 extends SpeedMode {
    private boolean firstJump;
    private boolean waitForGround;

    public AACLowHop3() {
        super("AACLowHop3");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onEnable() {
        this.firstJump = true;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (MovementUtils.isMoving()) {
            if (f362mc.field_71439_g.field_70737_aN <= 0) {
                if (f362mc.field_71439_g.field_70122_E) {
                    this.waitForGround = false;
                    if (!this.firstJump) {
                        this.firstJump = true;
                    }
                    f362mc.field_71439_g.func_70664_aZ();
                    f362mc.field_71439_g.field_70181_x = 0.41d;
                } else if (this.waitForGround || f362mc.field_71439_g.field_70123_F) {
                    return;
                } else {
                    this.firstJump = false;
                    f362mc.field_71439_g.field_70181_x -= 0.0149d;
                }
                if (!f362mc.field_71439_g.field_70123_F) {
                    MovementUtils.forward(this.firstJump ? 0.0016d : 0.001799d);
                }
            } else {
                this.firstJump = true;
                this.waitForGround = true;
            }
        } else {
            f362mc.field_71439_g.field_70179_y = 0.0d;
            f362mc.field_71439_g.field_70159_w = 0.0d;
        }
        double speed = MovementUtils.getSpeed();
        f362mc.field_71439_g.field_70159_w = -(Math.sin(MovementUtils.getDirection()) * speed);
        f362mc.field_71439_g.field_70179_y = Math.cos(MovementUtils.getDirection()) * speed;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
