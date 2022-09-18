package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spartan;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/spartan/SpartanYPort.class */
public class SpartanYPort extends SpeedMode {
    private int airMoves;

    public SpartanYPort() {
        super("SpartanYPort");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (f362mc.field_71474_y.field_74351_w.func_151470_d() && !f362mc.field_71474_y.field_74314_A.func_151470_d()) {
            if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.func_70664_aZ();
                this.airMoves = 0;
                return;
            }
            f362mc.field_71428_T.field_74278_d = 1.08f;
            if (this.airMoves >= 3) {
                f362mc.field_71439_g.field_70747_aH = 0.0275f;
            }
            if (this.airMoves >= 4 && this.airMoves % 2 == 0.0d) {
                f362mc.field_71439_g.field_70181_x = (-0.3199999928474426d) - (0.009d * Math.random());
                f362mc.field_71439_g.field_70747_aH = 0.0238f;
            }
            this.airMoves++;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
