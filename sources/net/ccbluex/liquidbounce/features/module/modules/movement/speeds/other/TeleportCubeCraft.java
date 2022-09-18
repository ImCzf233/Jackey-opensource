package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/TeleportCubeCraft.class */
public class TeleportCubeCraft extends SpeedMode {
    private final MSTimer timer = new MSTimer();

    public TeleportCubeCraft() {
        super("TeleportCubeCraft");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
        if (MovementUtils.isMoving() && f362mc.field_71439_g.field_70122_E && this.timer.hasTimePassed(300L)) {
            double yaw = MovementUtils.getDirection();
            float length = ((Speed) LiquidBounce.moduleManager.getModule(Speed.class)).cubecraftPortLengthValue.get().floatValue();
            event.setX((-Math.sin(yaw)) * length);
            event.setZ(Math.cos(yaw) * length);
            this.timer.reset();
        }
    }
}
