package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACv4BHop.class */
public class AACv4BHop extends SpeedMode {
    public AACv4BHop() {
        super("AACv4BHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (!f362mc.field_71439_g.func_70090_H() && f362mc.field_71439_g.field_70701_bs > 0.0f) {
            if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.func_70664_aZ();
                f362mc.field_71428_T.field_74278_d = 1.6105f;
                f362mc.field_71439_g.field_70159_w *= 1.0708d;
                f362mc.field_71439_g.field_70179_y *= 1.0708d;
            } else if (f362mc.field_71439_g.field_70143_R > 0.0f) {
                f362mc.field_71428_T.field_74278_d = 0.6f;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
