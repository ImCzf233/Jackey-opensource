package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.util.MathHelper;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/NCPYPort.class */
public class NCPYPort extends SpeedMode {
    private int jumps;

    public NCPYPort() {
        super("NCPYPort");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (f362mc.field_71439_g.func_70617_f_() || f362mc.field_71439_g.func_70090_H() || f362mc.field_71439_g.func_180799_ab() || f362mc.field_71439_g.field_70134_J || !MovementUtils.isMoving() || f362mc.field_71439_g.func_70090_H()) {
            return;
        }
        if (this.jumps >= 4 && f362mc.field_71439_g.field_70122_E) {
            this.jumps = 0;
        }
        if (f362mc.field_71439_g.field_70122_E) {
            f362mc.field_71439_g.field_70181_x = this.jumps <= 1 ? 0.41999998688697815d : 0.4000000059604645d;
            float f = f362mc.field_71439_g.field_70177_z * 0.017453292f;
            f362mc.field_71439_g.field_70159_w -= MathHelper.func_76126_a(f) * 0.2f;
            f362mc.field_71439_g.field_70179_y += MathHelper.func_76134_b(f) * 0.2f;
            this.jumps++;
        } else if (this.jumps <= 1) {
            f362mc.field_71439_g.field_70181_x = -5.0d;
        }
        MovementUtils.strafe();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
