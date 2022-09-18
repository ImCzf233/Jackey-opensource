package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.util.MathHelper;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/spectre/SpectreOnGround.class */
public class SpectreOnGround extends SpeedMode {
    private int speedUp;

    public SpectreOnGround() {
        super("SpectreOnGround");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
        if (!MovementUtils.isMoving() || f362mc.field_71439_g.field_71158_b.field_78901_c) {
            return;
        }
        if (this.speedUp >= 10) {
            if (f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.field_70159_w = 0.0d;
                f362mc.field_71439_g.field_70179_y = 0.0d;
                this.speedUp = 0;
            }
        } else if (f362mc.field_71439_g.field_70122_E && f362mc.field_71474_y.field_74351_w.func_151470_d()) {
            float f = f362mc.field_71439_g.field_70177_z * 0.017453292f;
            f362mc.field_71439_g.field_70159_w -= MathHelper.func_76126_a(f) * 0.145f;
            f362mc.field_71439_g.field_70179_y += MathHelper.func_76134_b(f) * 0.145f;
            event.setX(f362mc.field_71439_g.field_70159_w);
            event.setY(0.005d);
            event.setZ(f362mc.field_71439_g.field_70179_y);
            this.speedUp++;
        }
    }
}
