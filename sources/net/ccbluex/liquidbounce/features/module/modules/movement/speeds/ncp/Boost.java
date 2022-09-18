package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import java.util.Iterator;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.util.AxisAlignedBB;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/Boost.class */
public class Boost extends SpeedMode {
    private int motionDelay;
    private float ground;

    public Boost() {
        super("Boost");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        double speed = 3.1981d;
        double offset = 4.69d;
        boolean shouldOffset = true;
        Iterator it = f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, f362mc.field_71439_g.func_174813_aQ().func_72317_d(f362mc.field_71439_g.field_70159_w / 4.69d, 0.0d, f362mc.field_71439_g.field_70179_y / 4.69d)).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object o = it.next();
            if (o instanceof AxisAlignedBB) {
                shouldOffset = false;
                break;
            }
        }
        if (f362mc.field_71439_g.field_70122_E && this.ground < 1.0f) {
            this.ground += 0.2f;
        }
        if (!f362mc.field_71439_g.field_70122_E) {
            this.ground = 0.0f;
        }
        if (this.ground == 1.0f && shouldSpeedUp()) {
            if (!f362mc.field_71439_g.func_70051_ag()) {
                offset = 4.69d + 0.8d;
            }
            if (f362mc.field_71439_g.field_70702_br != 0.0f) {
                speed = 3.1981d - 0.1d;
                offset += 0.5d;
            }
            if (f362mc.field_71439_g.func_70090_H()) {
                speed -= 0.1d;
            }
            this.motionDelay++;
            switch (this.motionDelay) {
                case 1:
                    f362mc.field_71439_g.field_70159_w *= speed;
                    f362mc.field_71439_g.field_70179_y *= speed;
                    return;
                case 2:
                    f362mc.field_71439_g.field_70159_w /= 1.458d;
                    f362mc.field_71439_g.field_70179_y /= 1.458d;
                    return;
                case 3:
                default:
                    return;
                case 4:
                    if (shouldOffset) {
                        f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t + (f362mc.field_71439_g.field_70159_w / offset), f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v + (f362mc.field_71439_g.field_70179_y / offset));
                    }
                    this.motionDelay = 0;
                    return;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }

    private boolean shouldSpeedUp() {
        return !f362mc.field_71439_g.func_70090_H() && !f362mc.field_71439_g.func_70617_f_() && !f362mc.field_71439_g.func_70093_af() && MovementUtils.isMoving();
    }
}
