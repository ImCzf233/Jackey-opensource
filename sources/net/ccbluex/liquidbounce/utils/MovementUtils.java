package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/MovementUtils.class */
public final class MovementUtils extends MinecraftInstance {
    private static double lastX = -999999.0d;
    private static double lastZ = -999999.0d;

    public static float getSpeed() {
        return (float) getSpeed(f362mc.field_71439_g.field_70159_w, f362mc.field_71439_g.field_70179_y);
    }

    public static double getSpeed(double motionX, double motionZ) {
        return Math.sqrt((motionX * motionX) + (motionZ * motionZ));
    }

    public static boolean isOnIce() {
        EntityPlayerSP player = f362mc.field_71439_g;
        Block blockUnder = f362mc.field_71441_e.func_180495_p(new BlockPos(player.field_70165_t, player.field_70163_u - 1.0d, player.field_70161_v)).func_177230_c();
        return (blockUnder instanceof BlockIce) || (blockUnder instanceof BlockPackedIce);
    }

    public static boolean isBlockUnder() {
        if (f362mc.field_71439_g == null || f362mc.field_71439_g.field_70163_u < 0.0d) {
            return false;
        }
        for (int off = 0; off < ((int) f362mc.field_71439_g.field_70163_u) + 2; off += 2) {
            AxisAlignedBB bb = f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, -off, 0.0d);
            if (!f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static void accelerate() {
        accelerate(getSpeed());
    }

    public static void accelerate(float speed) {
        if (!isMoving()) {
            return;
        }
        double yaw = getDirection();
        f362mc.field_71439_g.field_70159_w += (-Math.sin(yaw)) * speed;
        f362mc.field_71439_g.field_70179_y += Math.cos(yaw) * speed;
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static boolean isMoving() {
        return (f362mc.field_71439_g == null || (f362mc.field_71439_g.field_71158_b.field_78900_b == 0.0f && f362mc.field_71439_g.field_71158_b.field_78902_a == 0.0f)) ? false : true;
    }

    public static boolean hasMotion() {
        return (f362mc.field_71439_g.field_70159_w == 0.0d || f362mc.field_71439_g.field_70179_y == 0.0d || f362mc.field_71439_g.field_70181_x == 0.0d) ? false : true;
    }

    public static void strafe(float speed) {
        if (!isMoving()) {
            return;
        }
        double yaw = getDirection();
        f362mc.field_71439_g.field_70159_w = (-Math.sin(yaw)) * speed;
        f362mc.field_71439_g.field_70179_y = Math.cos(yaw) * speed;
    }

    public static void strafeCustom(float speed, float cYaw, float strafe, float forward) {
        if (!isMoving()) {
            return;
        }
        double yaw = getDirectionRotation(cYaw, strafe, forward);
        f362mc.field_71439_g.field_70159_w = (-Math.sin(yaw)) * speed;
        f362mc.field_71439_g.field_70179_y = Math.cos(yaw) * speed;
    }

    public static void forward(double length) {
        double yaw = Math.toRadians(f362mc.field_71439_g.field_70177_z);
        f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t + ((-Math.sin(yaw)) * length), f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v + (Math.cos(yaw) * length));
    }

    public static double getDirection() {
        TargetStrafe ts = (TargetStrafe) LiquidBounce.moduleManager.getModule(TargetStrafe.class);
        return ts.getCanStrafe() ? ts.getMovingDir() : getDirectionRotation(f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70702_br, f362mc.field_71439_g.field_70701_bs);
    }

    public static float getRawDirection() {
        return getRawDirectionRotation(f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70702_br, f362mc.field_71439_g.field_70701_bs);
    }

    public static float getRawDirection(float yaw) {
        return getRawDirectionRotation(yaw, f362mc.field_71439_g.field_70702_br, f362mc.field_71439_g.field_70701_bs);
    }

    public static float getPredictionYaw(double x, double z) {
        if (f362mc.field_71439_g == null) {
            lastX = -999999.0d;
            lastZ = -999999.0d;
            return 0.0f;
        }
        if (lastX == -999999.0d) {
            lastX = f362mc.field_71439_g.field_70169_q;
        }
        if (lastZ == -999999.0d) {
            lastZ = f362mc.field_71439_g.field_70166_s;
        }
        float returnValue = (float) ((Math.atan2(z - lastZ, x - lastX) * 180.0d) / 3.141592653589793d);
        lastX = x;
        lastZ = z;
        return returnValue;
    }

    public static double getDirectionRotation(float yaw, float pStrafe, float pForward) {
        float rotationYaw = yaw;
        if (pForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (pForward < 0.0f) {
            forward = -0.5f;
        } else if (pForward > 0.0f) {
            forward = 0.5f;
        }
        if (pStrafe > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (pStrafe < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static float getRawDirectionRotation(float yaw, float pStrafe, float pForward) {
        float rotationYaw = yaw;
        if (pForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (pForward < 0.0f) {
            forward = -0.5f;
        } else if (pForward > 0.0f) {
            forward = 0.5f;
        }
        if (pStrafe > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (pStrafe < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return rotationYaw;
    }

    public static float getScaffoldRotation(float yaw, float strafe) {
        float rotationYaw = yaw + 180.0f;
        if (strafe < 0.0f) {
            rotationYaw -= 90.0f * (-0.5f);
        }
        if (strafe > 0.0f) {
            rotationYaw += 90.0f * (-0.5f);
        }
        return rotationYaw;
    }

    public static int getJumpEffect() {
        if (f362mc.field_71439_g.func_70644_a(Potion.field_76430_j)) {
            return f362mc.field_71439_g.func_70660_b(Potion.field_76430_j).func_76458_c() + 1;
        }
        return 0;
    }

    public static int getSpeedEffect() {
        if (f362mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
            return f362mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() + 1;
        }
        return 0;
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873d;
        if (f362mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
            baseSpeed = 0.2873d * (1.0d + (0.2d * (f362mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() + 1)));
        }
        return baseSpeed;
    }

    public static double getBaseMoveSpeed(double customSpeed) {
        double baseSpeed = isOnIce() ? 0.258977700006d : customSpeed;
        if (f362mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
            int amplifier = f362mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c();
            baseSpeed *= 1.0d + (0.2d * (amplifier + 1));
        }
        return baseSpeed;
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        return getJumpBoostModifier(baseJumpHeight, true);
    }

    public static double getJumpBoostModifier(double baseJumpHeight, boolean potionJump) {
        if (f362mc.field_71439_g.func_70644_a(Potion.field_76430_j) && potionJump) {
            int amplifier = f362mc.field_71439_g.func_70660_b(Potion.field_76430_j).func_76458_c();
            baseJumpHeight += (amplifier + 1) * 0.1f;
        }
        return baseJumpHeight;
    }

    public static void setMotion(MoveEvent event, double speed, double motion, boolean smoothStrafe) {
        double forward = f362mc.field_71439_g.field_71158_b.field_78900_b;
        double strafe = f362mc.field_71439_g.field_71158_b.field_78902_a;
        double yaw = f362mc.field_71439_g.field_70177_z;
        int direction = smoothStrafe ? 45 : 90;
        if (forward == 0.0d && strafe == 0.0d) {
            event.setX(0.0d);
            event.setZ(0.0d);
            return;
        }
        if (forward != 0.0d) {
            if (strafe > 0.0d) {
                yaw += forward > 0.0d ? -direction : direction;
            } else if (strafe < 0.0d) {
                yaw += forward > 0.0d ? direction : -direction;
            }
            strafe = 0.0d;
            if (forward > 0.0d) {
                forward = 1.0d;
            } else if (forward < 0.0d) {
                forward = -1.0d;
            }
        }
        double cos = Math.cos(Math.toRadians(yaw + 90.0d));
        double sin = Math.sin(Math.toRadians(yaw + 90.0d));
        event.setX(((forward * speed * cos) + (strafe * speed * sin)) * motion);
        event.setZ((((forward * speed) * sin) - ((strafe * speed) * cos)) * motion);
    }

    public static void setMotion(double speed, boolean smoothStrafe) {
        double forward = f362mc.field_71439_g.field_71158_b.field_78900_b;
        double strafe = f362mc.field_71439_g.field_71158_b.field_78902_a;
        float yaw = f362mc.field_71439_g.field_70177_z;
        int direction = smoothStrafe ? 45 : 90;
        if (forward == 0.0d && strafe == 0.0d) {
            f362mc.field_71439_g.field_70159_w = 0.0d;
            f362mc.field_71439_g.field_70179_y = 0.0d;
            return;
        }
        if (forward != 0.0d) {
            if (strafe > 0.0d) {
                yaw += forward > 0.0d ? -direction : direction;
            } else if (strafe < 0.0d) {
                yaw += forward > 0.0d ? direction : -direction;
            }
            strafe = 0.0d;
            if (forward > 0.0d) {
                forward = 1.0d;
            } else if (forward < 0.0d) {
                forward = -1.0d;
            }
        }
        f362mc.field_71439_g.field_70159_w = (forward * speed * (-Math.sin(Math.toRadians(yaw)))) + (strafe * speed * Math.cos(Math.toRadians(yaw)));
        f362mc.field_71439_g.field_70179_y = ((forward * speed) * Math.cos(Math.toRadians(yaw))) - ((strafe * speed) * (-Math.sin(Math.toRadians(yaw))));
    }

    public static void setSpeed(MoveEvent moveEvent, double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_71158_b.field_78902_a, f362mc.field_71439_g.field_71158_b.field_78900_b);
    }

    public static void setSpeed(MoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward == 0.0d && strafe == 0.0d) {
            moveEvent.setZ(0.0d);
            moveEvent.setX(0.0d);
            return;
        }
        if (forward != 0.0d) {
            if (strafe > 0.0d) {
                yaw += forward > 0.0d ? -45 : 45;
            } else if (strafe < 0.0d) {
                yaw += forward > 0.0d ? 45 : -45;
            }
            strafe = 0.0d;
            if (forward > 0.0d) {
                forward = 1.0d;
            } else if (forward < 0.0d) {
                forward = -1.0d;
            }
        }
        if (strafe > 0.0d) {
            strafe = 1.0d;
        } else if (strafe < 0.0d) {
            strafe = -1.0d;
        }
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        moveEvent.setX((forward * moveSpeed * cos) + (strafe * moveSpeed * sin));
        moveEvent.setZ(((forward * moveSpeed) * sin) - ((strafe * moveSpeed) * cos));
    }
}
