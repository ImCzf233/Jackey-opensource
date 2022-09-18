package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/HiveHop.class */
public class HiveHop extends SpeedMode {
    public HiveHop() {
        super("HiveHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onEnable() {
        f362mc.field_71439_g.field_71102_ce = 0.0425f;
        f362mc.field_71428_T.field_74278_d = 1.04f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71439_g.field_71102_ce = 0.02f;
        f362mc.field_71428_T.field_74278_d = 1.0f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        if (MovementUtils.isMoving()) {
            if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.field_70181_x = 0.3d;
            }
            f362mc.field_71439_g.field_71102_ce = 0.0425f;
            f362mc.field_71428_T.field_74278_d = 1.04f;
            MovementUtils.strafe();
            return;
        }
        EntityPlayerSP entityPlayerSP = f362mc.field_71439_g;
        f362mc.field_71439_g.field_70179_y = 0.0d;
        entityPlayerSP.field_70159_w = 0.0d;
        f362mc.field_71439_g.field_71102_ce = 0.02f;
        f362mc.field_71428_T.field_74278_d = 1.0f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
