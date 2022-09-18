package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/YPort.class */
public class YPort extends SpeedMode {
    private double moveSpeed = 0.2873d;
    private int level = 1;
    private double lastDist;
    private int timerDelay;
    private boolean safeJump;

    public YPort() {
        super("YPort");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (!this.safeJump && !f362mc.field_71474_y.field_74314_A.func_151470_d() && !f362mc.field_71439_g.func_70617_f_() && !f362mc.field_71439_g.func_70055_a(Material.field_151586_h) && !f362mc.field_71439_g.func_70055_a(Material.field_151587_i) && !f362mc.field_71439_g.func_70090_H() && (((!(getBlock(-1.1d) instanceof BlockAir) && !(getBlock(-1.1d) instanceof BlockAir)) || (!(getBlock(-0.1d) instanceof BlockAir) && f362mc.field_71439_g.field_70159_w != 0.0d && f362mc.field_71439_g.field_70179_y != 0.0d && !f362mc.field_71439_g.field_70122_E && f362mc.field_71439_g.field_70143_R < 3.0f && f362mc.field_71439_g.field_70143_R > 0.05d)) && this.level == 3)) {
            f362mc.field_71439_g.field_70181_x = -0.3994d;
        }
        double xDist = f362mc.field_71439_g.field_70165_t - f362mc.field_71439_g.field_70169_q;
        double zDist = f362mc.field_71439_g.field_70161_v - f362mc.field_71439_g.field_70166_s;
        this.lastDist = Math.sqrt((xDist * xDist) + (zDist * zDist));
        if (!MovementUtils.isMoving()) {
            this.safeJump = true;
        } else if (f362mc.field_71439_g.field_70122_E) {
            this.safeJump = false;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
        this.timerDelay++;
        this.timerDelay %= 5;
        if (this.timerDelay != 0) {
            f362mc.field_71428_T.field_74278_d = 1.0f;
        } else {
            if (MovementUtils.hasMotion()) {
                f362mc.field_71428_T.field_74278_d = 32767.0f;
            }
            if (MovementUtils.hasMotion()) {
                f362mc.field_71428_T.field_74278_d = 1.3f;
                f362mc.field_71439_g.field_70159_w *= 1.0199999809265137d;
                f362mc.field_71439_g.field_70179_y *= 1.0199999809265137d;
            }
        }
        if (f362mc.field_71439_g.field_70122_E && MovementUtils.hasMotion()) {
            this.level = 2;
        }
        if (round(f362mc.field_71439_g.field_70163_u - ((int) f362mc.field_71439_g.field_70163_u)) == round(0.138d)) {
            f362mc.field_71439_g.field_70181_x -= 0.08d;
            event.setY(event.getY() - 0.09316090325960147d);
            f362mc.field_71439_g.field_70163_u -= 0.09316090325960147d;
        }
        if (this.level == 1 && (f362mc.field_71439_g.field_70701_bs != 0.0f || f362mc.field_71439_g.field_70702_br != 0.0f)) {
            this.level = 2;
            this.moveSpeed = (1.38d * getBaseMoveSpeed()) - 0.01d;
        } else if (this.level == 2) {
            this.level = 3;
            f362mc.field_71439_g.field_70181_x = 0.399399995803833d;
            event.setY(0.399399995803833d);
            this.moveSpeed *= 2.149d;
        } else if (this.level == 3) {
            this.level = 4;
            double difference = 0.66d * (this.lastDist - getBaseMoveSpeed());
            this.moveSpeed = this.lastDist - difference;
        } else {
            if (f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, f362mc.field_71439_g.field_70181_x, 0.0d)).size() > 0 || f362mc.field_71439_g.field_70124_G) {
                this.level = 1;
            }
            this.moveSpeed = this.lastDist - (this.lastDist / 159.0d);
        }
        this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
        float forward = f362mc.field_71439_g.field_71158_b.field_78900_b;
        float strafe = f362mc.field_71439_g.field_71158_b.field_78902_a;
        float yaw = f362mc.field_71439_g.field_70177_z;
        if (forward == 0.0f && strafe == 0.0f) {
            event.setX(0.0d);
            event.setZ(0.0d);
        } else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += forward > 0.0f ? -45 : 45;
                strafe = 0.0f;
            } else if (strafe <= -1.0f) {
                yaw += forward > 0.0f ? 45 : -45;
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        event.setX((forward * this.moveSpeed * mx) + (strafe * this.moveSpeed * mz));
        event.setZ(((forward * this.moveSpeed) * mz) - ((strafe * this.moveSpeed) * mx));
        f362mc.field_71439_g.field_70138_W = 0.6f;
        if (forward == 0.0f && strafe == 0.0f) {
            event.setX(0.0d);
            event.setZ(0.0d);
        }
    }

    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873d;
        if (f362mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
            int amplifier = f362mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c();
            baseSpeed = 0.2873d * (1.0d + (0.2d * (amplifier + 1)));
        }
        return baseSpeed;
    }

    private Block getBlock(AxisAlignedBB axisAlignedBB) {
        for (int x = MathHelper.func_76128_c(axisAlignedBB.field_72340_a); x < MathHelper.func_76128_c(axisAlignedBB.field_72336_d) + 1; x++) {
            for (int z = MathHelper.func_76128_c(axisAlignedBB.field_72339_c); z < MathHelper.func_76128_c(axisAlignedBB.field_72334_f) + 1; z++) {
                Block block = f362mc.field_71441_e.func_180495_p(new BlockPos(x, (int) axisAlignedBB.field_72338_b, z)).func_177230_c();
                if (block != null) {
                    return block;
                }
            }
        }
        return null;
    }

    private Block getBlock(double offset) {
        return getBlock(f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, offset, 0.0d));
    }

    private double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        return bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
    }
}
