package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/SNCPBHop.class */
public class SNCPBHop extends SpeedMode {
    private int level = 1;
    private double moveSpeed = 0.2873d;
    private double lastDist;
    private int timerDelay;

    public SNCPBHop() {
        super("SNCPBHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onEnable() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
        this.lastDist = 0.0d;
        this.moveSpeed = 0.0d;
        this.level = 4;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
        this.moveSpeed = getBaseMoveSpeed();
        this.level = 0;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        double xDist = f362mc.field_71439_g.field_70165_t - f362mc.field_71439_g.field_70169_q;
        double zDist = f362mc.field_71439_g.field_70161_v - f362mc.field_71439_g.field_70166_s;
        this.lastDist = Math.sqrt((xDist * xDist) + (zDist * zDist));
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
            if (MovementUtils.isMoving()) {
                f362mc.field_71428_T.field_74278_d = 32767.0f;
            }
            if (MovementUtils.isMoving()) {
                f362mc.field_71428_T.field_74278_d = 1.3f;
                f362mc.field_71439_g.field_70159_w *= 1.0199999809265137d;
                f362mc.field_71439_g.field_70179_y *= 1.0199999809265137d;
            }
        }
        if (f362mc.field_71439_g.field_70122_E && MovementUtils.isMoving()) {
            this.level = 2;
        }
        if (round(f362mc.field_71439_g.field_70163_u - ((int) f362mc.field_71439_g.field_70163_u)) == round(0.138d)) {
            f362mc.field_71439_g.field_70181_x -= 0.08d;
            event.setY(event.getY() - 0.09316090325960147d);
            f362mc.field_71439_g.field_70163_u -= 0.09316090325960147d;
        }
        if (this.level == 1 && (f362mc.field_71439_g.field_70701_bs != 0.0f || f362mc.field_71439_g.field_70702_br != 0.0f)) {
            this.level = 2;
            this.moveSpeed = (1.35d * getBaseMoveSpeed()) - 0.01d;
        } else if (this.level == 2) {
            this.level = 3;
            f362mc.field_71439_g.field_70181_x = 0.399399995803833d;
            event.setY(0.399399995803833d);
            this.moveSpeed *= 2.149d;
        } else if (this.level == 3) {
            this.level = 4;
            double difference = 0.66d * (this.lastDist - getBaseMoveSpeed());
            this.moveSpeed = this.lastDist - difference;
        } else if (this.level == 88) {
            this.moveSpeed = getBaseMoveSpeed();
            this.lastDist = 0.0d;
            this.level = 89;
        } else if (this.level == 89) {
            if (f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, f362mc.field_71439_g.field_70181_x, 0.0d)).size() > 0 || f362mc.field_71439_g.field_70124_G) {
                this.level = 1;
            }
            this.lastDist = 0.0d;
            this.moveSpeed = getBaseMoveSpeed();
            return;
        } else if (f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, f362mc.field_71439_g.field_70181_x, 0.0d)).size() > 0 || f362mc.field_71439_g.field_70124_G) {
            this.moveSpeed = getBaseMoveSpeed();
            this.lastDist = 0.0d;
            this.level = 88;
            return;
        } else {
            this.moveSpeed = this.lastDist - (this.lastDist / 159.0d);
        }
        this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
        MovementInput movementInput = f362mc.field_71439_g.field_71158_b;
        float forward = movementInput.field_78900_b;
        float strafe = movementInput.field_78902_a;
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
        double mx2 = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz2 = Math.sin(Math.toRadians(yaw + 90.0f));
        event.setX((forward * this.moveSpeed * mx2) + (strafe * this.moveSpeed * mz2));
        event.setZ(((forward * this.moveSpeed) * mz2) - ((strafe * this.moveSpeed) * mx2));
        f362mc.field_71439_g.field_70138_W = 0.6f;
        if (forward == 0.0f && strafe == 0.0f) {
            event.setX(0.0d);
            event.setZ(0.0d);
        }
    }

    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873d;
        if (f362mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
            baseSpeed = 0.2873d * (1.0d + (0.2d * (f362mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() + 1)));
        }
        return baseSpeed;
    }

    private double round(double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(3, RoundingMode.HALF_UP).doubleValue();
    }
}
