package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/Jump.class */
public class Jump extends SpeedMode {
    public Jump() {
        super("Jump");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        Speed speed = (Speed) LiquidBounce.moduleManager.getModule(Speed.class);
        if (speed == null) {
            return;
        }
        if (MovementUtils.isMoving() && f362mc.field_71439_g.field_70122_E && !f362mc.field_71474_y.field_74314_A.func_151470_d() && !f362mc.field_71439_g.func_70090_H() && !f362mc.field_71439_g.func_180799_ab() && f362mc.field_71439_g.field_70773_bE == 0) {
            f362mc.field_71439_g.func_70664_aZ();
            f362mc.field_71439_g.field_70773_bE = 10;
        }
        if (speed.jumpStrafe.get().booleanValue() && MovementUtils.isMoving() && !f362mc.field_71439_g.field_70122_E && !f362mc.field_71439_g.func_70090_H() && !f362mc.field_71439_g.func_180799_ab()) {
            MovementUtils.strafe();
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
