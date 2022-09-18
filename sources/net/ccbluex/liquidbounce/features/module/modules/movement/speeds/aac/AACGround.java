package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACGround.class */
public class AACGround extends SpeedMode {
    public AACGround() {
        super("AACGround");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        if (!MovementUtils.isMoving()) {
            return;
        }
        f362mc.field_71428_T.field_74278_d = ((Speed) LiquidBounce.moduleManager.getModule(Speed.class)).aacGroundTimerValue.get().floatValue();
        f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v, true));
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
    }
}
