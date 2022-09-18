package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/Frame.class */
public class Frame extends SpeedMode {
    private int motionTicks;
    private boolean move;
    private final TickTimer tickTimer = new TickTimer();

    public Frame() {
        super("Frame");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (f362mc.field_71439_g.field_71158_b.field_78900_b > 0.0f || f362mc.field_71439_g.field_71158_b.field_78902_a > 0.0f) {
            if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.func_70664_aZ();
                if (this.motionTicks == 1) {
                    this.tickTimer.reset();
                    if (this.move) {
                        f362mc.field_71439_g.field_70159_w = 0.0d;
                        f362mc.field_71439_g.field_70179_y = 0.0d;
                        this.move = false;
                    }
                    this.motionTicks = 0;
                } else {
                    this.motionTicks = 1;
                }
            } else if (!this.move && this.motionTicks == 1 && this.tickTimer.hasTimePassed(5)) {
                f362mc.field_71439_g.field_70159_w *= 4.25d;
                f362mc.field_71439_g.field_70179_y *= 4.25d;
                this.move = true;
            }
            if (!f362mc.field_71439_g.field_70122_E) {
                MovementUtils.strafe();
            }
            this.tickTimer.update();
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
