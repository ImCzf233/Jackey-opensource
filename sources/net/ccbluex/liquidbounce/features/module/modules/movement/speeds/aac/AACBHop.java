package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.util.MathHelper;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACBHop.class */
public class AACBHop extends SpeedMode {
    public AACBHop() {
        super("AACBHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (f362mc.field_71439_g.func_70090_H()) {
            return;
        }
        if (MovementUtils.isMoving()) {
            f362mc.field_71428_T.field_74278_d = 1.08f;
            if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.field_70181_x = 0.399d;
                float f = f362mc.field_71439_g.field_70177_z * 0.017453292f;
                f362mc.field_71439_g.field_70159_w -= MathHelper.func_76126_a(f) * 0.2f;
                f362mc.field_71439_g.field_70179_y += MathHelper.func_76134_b(f) * 0.2f;
                f362mc.field_71428_T.field_74278_d = 2.0f;
                return;
            }
            f362mc.field_71439_g.field_70181_x *= 0.97d;
            f362mc.field_71439_g.field_70159_w *= 1.008d;
            f362mc.field_71439_g.field_70179_y *= 1.008d;
            return;
        }
        f362mc.field_71439_g.field_70159_w = 0.0d;
        f362mc.field_71439_g.field_70179_y = 0.0d;
        f362mc.field_71428_T.field_74278_d = 1.0f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
    }
}
