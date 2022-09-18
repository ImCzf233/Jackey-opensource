package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.BlockCarpet;
import net.minecraft.util.MathHelper;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACHop3313.class */
public class AACHop3313 extends SpeedMode {
    public AACHop3313() {
        super("AACHop3.3.13");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        if (!MovementUtils.isMoving() || f362mc.field_71439_g.func_70090_H() || f362mc.field_71439_g.func_180799_ab() || f362mc.field_71439_g.func_70617_f_() || f362mc.field_71439_g.func_70115_ae() || f362mc.field_71439_g.field_70737_aN > 0) {
            return;
        }
        if (f362mc.field_71439_g.field_70122_E && f362mc.field_71439_g.field_70124_G) {
            float yawRad = f362mc.field_71439_g.field_70177_z * 0.017453292f;
            f362mc.field_71439_g.field_70159_w -= MathHelper.func_76126_a(yawRad) * 0.202f;
            f362mc.field_71439_g.field_70179_y += MathHelper.func_76134_b(yawRad) * 0.202f;
            f362mc.field_71439_g.field_70181_x = 0.4050000011920929d;
            LiquidBounce.eventManager.callEvent(new JumpEvent(0.405f));
            MovementUtils.strafe();
        } else if (f362mc.field_71439_g.field_70143_R < 0.31f) {
            if (BlockUtils.getBlock(f362mc.field_71439_g.func_180425_c()) instanceof BlockCarpet) {
                return;
            }
            f362mc.field_71439_g.field_70747_aH = f362mc.field_71439_g.field_70702_br == 0.0f ? 0.027f : 0.021f;
            f362mc.field_71439_g.field_70159_w *= 1.001d;
            f362mc.field_71439_g.field_70179_y *= 1.001d;
            if (!f362mc.field_71439_g.field_70123_F) {
                f362mc.field_71439_g.field_70181_x -= 0.01499999314546585d;
            }
        } else {
            f362mc.field_71439_g.field_70747_aH = 0.02f;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71439_g.field_70747_aH = 0.02f;
    }
}
