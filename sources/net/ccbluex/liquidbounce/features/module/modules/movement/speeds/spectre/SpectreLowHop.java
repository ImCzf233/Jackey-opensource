package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/spectre/SpectreLowHop.class */
public class SpectreLowHop extends SpeedMode {
    public SpectreLowHop() {
        super("SpectreLowHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (!MovementUtils.isMoving() || f362mc.field_71439_g.field_71158_b.field_78901_c) {
            return;
        }
        if (f362mc.field_71439_g.field_70122_E) {
            MovementUtils.strafe(1.1f);
            f362mc.field_71439_g.field_70181_x = 0.15d;
            return;
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
