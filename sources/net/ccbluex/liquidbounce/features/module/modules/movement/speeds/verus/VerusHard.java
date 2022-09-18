package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.verus;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/verus/VerusHard.class */
public class VerusHard extends SpeedMode {
    public VerusHard() {
        super("VerusHard");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
        super.onDisable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        Speed speed = (Speed) LiquidBounce.moduleManager.getModule(Speed.class);
        if (speed == null) {
            return;
        }
        if (!f362mc.field_71474_y.field_74351_w.func_151470_d() && !f362mc.field_71474_y.field_74370_x.func_151470_d() && !f362mc.field_71474_y.field_74366_z.func_151470_d() && !f362mc.field_71474_y.field_74368_y.func_151470_d()) {
            return;
        }
        f362mc.field_71428_T.field_74278_d = speed.verusTimer.get().floatValue();
        if (f362mc.field_71439_g.field_70122_E) {
            f362mc.field_71439_g.func_70664_aZ();
            if (f362mc.field_71439_g.func_70051_ag()) {
                MovementUtils.strafe(MovementUtils.getSpeed() + 0.2f);
            }
        }
        MovementUtils.strafe(Math.max((float) MovementUtils.getBaseMoveSpeed(), MovementUtils.getSpeed()));
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
