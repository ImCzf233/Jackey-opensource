package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACPort.class */
public class AACPort extends SpeedMode {
    public AACPort() {
        super("AACPort");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        if (!MovementUtils.isMoving()) {
            return;
        }
        float f = f362mc.field_71439_g.field_70177_z * 0.017453292f;
        double d = 0.2d;
        while (true) {
            double d2 = d;
            if (d2 <= ((Speed) LiquidBounce.moduleManager.getModule(Speed.class)).portMax.get().floatValue()) {
                double x = f362mc.field_71439_g.field_70165_t - (MathHelper.func_76126_a(f) * d2);
                double z = f362mc.field_71439_g.field_70161_v + (MathHelper.func_76134_b(f) * d2);
                if (f362mc.field_71439_g.field_70163_u >= ((int) f362mc.field_71439_g.field_70163_u) + 0.5d || (BlockUtils.getBlock(new BlockPos(x, f362mc.field_71439_g.field_70163_u, z)) instanceof BlockAir)) {
                    f362mc.field_71439_g.field_71174_a.func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, f362mc.field_71439_g.field_70163_u, z, true));
                    d = d2 + 0.2d;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
