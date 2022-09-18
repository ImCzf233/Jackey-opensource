package net.ccbluex.liquidbounce.utils;

import java.util.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.FastBow;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/RotationUtils.class */
public final class RotationUtils extends MinecraftInstance implements Listenable {
    private static int keepLength;
    public static Rotation targetRotation;
    private static Random random = new Random();
    public static Rotation serverRotation = new Rotation(0.0f, 0.0f);
    public static boolean keepCurrentRotation = false;

    /* renamed from: x */
    private static double f366x = random.nextDouble();

    /* renamed from: y */
    private static double f367y = random.nextDouble();

    /* renamed from: z */
    private static double f368z = random.nextDouble();

    public static Rotation OtherRotation(AxisAlignedBB bb, Vec3 vec, boolean predict, boolean throughWalls, float distance) {
        Vec3 eyesPos = new Vec3(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b + f362mc.field_71439_g.func_70047_e(), f362mc.field_71439_g.field_70161_v);
        Vec3 eyes = f362mc.field_71439_g.func_174824_e(1.0f);
        VecRotation vecRotation = null;
        double d = 0.15d;
        while (true) {
            double xSearch = d;
            if (xSearch >= 0.85d) {
                break;
            }
            double d2 = 0.15d;
            while (true) {
                double ySearch = d2;
                if (ySearch < 1.0d) {
                    double d3 = 0.15d;
                    while (true) {
                        double zSearch = d3;
                        if (zSearch < 0.85d) {
                            Vec3 vec3 = new Vec3(bb.field_72340_a + ((bb.field_72336_d - bb.field_72340_a) * xSearch), bb.field_72338_b + ((bb.field_72337_e - bb.field_72338_b) * ySearch), bb.field_72339_c + ((bb.field_72334_f - bb.field_72339_c) * zSearch));
                            Rotation rotation = toRotation(vec3, predict);
                            double vecDist = eyes.func_72438_d(vec3);
                            if (vecDist <= distance && (throughWalls || isVisible(vec3))) {
                                VecRotation currentVec = new VecRotation(vec3, rotation);
                                if (vecRotation == null) {
                                    vecRotation = currentVec;
                                }
                            }
                            d3 = zSearch + 0.1d;
                        }
                    }
                    d2 = ySearch + 0.1d;
                }
            }
            d = xSearch + 0.1d;
        }
        if (predict) {
            eyesPos.func_72441_c(f362mc.field_71439_g.field_70159_w, f362mc.field_71439_g.field_70181_x, f362mc.field_71439_g.field_70179_y);
        }
        double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        return new Rotation(MathHelper.func_76142_g(((float) Math.toDegrees(Math.atan2(diffZ, diffX))) - 90.0f), MathHelper.func_76142_g((float) (-Math.toDegrees(Math.atan2(diffY, Math.sqrt((diffX * diffX) + (diffZ * diffZ)))))));
    }

    public static VecRotation faceBlock(BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        VecRotation vecRotation = null;
        double d = 0.1d;
        while (true) {
            double xSearch = d;
            if (xSearch < 0.9d) {
                double d2 = 0.1d;
                while (true) {
                    double ySearch = d2;
                    if (ySearch < 0.9d) {
                        double d3 = 0.1d;
                        while (true) {
                            double zSearch = d3;
                            if (zSearch < 0.9d) {
                                Vec3 eyesPos = new Vec3(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b + f362mc.field_71439_g.func_70047_e(), f362mc.field_71439_g.field_70161_v);
                                Vec3 posVec = new Vec3(blockPos).func_72441_c(xSearch, ySearch, zSearch);
                                double dist = eyesPos.func_72438_d(posVec);
                                double diffX = posVec.field_72450_a - eyesPos.field_72450_a;
                                double diffY = posVec.field_72448_b - eyesPos.field_72448_b;
                                double diffZ = posVec.field_72449_c - eyesPos.field_72449_c;
                                double diffXZ = MathHelper.func_76133_a((diffX * diffX) + (diffZ * diffZ));
                                Rotation rotation = new Rotation(MathHelper.func_76142_g(((float) Math.toDegrees(Math.atan2(diffZ, diffX))) - 90.0f), MathHelper.func_76142_g((float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)))));
                                Vec3 rotationVector = getVectorForRotation(rotation);
                                Vec3 vector = eyesPos.func_72441_c(rotationVector.field_72450_a * dist, rotationVector.field_72448_b * dist, rotationVector.field_72449_c * dist);
                                MovingObjectPosition obj = f362mc.field_71441_e.func_147447_a(eyesPos, vector, false, false, true);
                                if (obj.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
                                    VecRotation currentVec = new VecRotation(posVec, rotation);
                                    if (vecRotation == null || getRotationDifference(currentVec.getRotation()) < getRotationDifference(vecRotation.getRotation())) {
                                        vecRotation = currentVec;
                                    }
                                }
                                d3 = zSearch + 0.1d;
                            }
                        }
                        d2 = ySearch + 0.1d;
                    }
                }
                d = xSearch + 0.1d;
            } else {
                return vecRotation;
            }
        }
    }

    public static void faceBow(Entity target, boolean silent, boolean predict, float predictSize) {
        EntityPlayerSP player = f362mc.field_71439_g;
        double posX = (target.field_70165_t + (predict ? (target.field_70165_t - target.field_70169_q) * predictSize : 0.0d)) - (player.field_70165_t + (predict ? player.field_70165_t - player.field_70169_q : 0.0d));
        double posY = ((((target.func_174813_aQ().field_72338_b + (predict ? (target.func_174813_aQ().field_72338_b - target.field_70167_r) * predictSize : 0.0d)) + target.func_70047_e()) - 0.15d) - (player.func_174813_aQ().field_72338_b + (predict ? player.field_70163_u - player.field_70167_r : 0.0d))) - player.func_70047_e();
        double posZ = (target.field_70161_v + (predict ? (target.field_70161_v - target.field_70166_s) * predictSize : 0.0d)) - (player.field_70161_v + (predict ? player.field_70161_v - player.field_70166_s : 0.0d));
        double posSqrt = Math.sqrt((posX * posX) + (posZ * posZ));
        float velocity = LiquidBounce.moduleManager.getModule(FastBow.class).getState() ? 1.0f : player.func_71057_bx() / 20.0f;
        float velocity2 = ((velocity * velocity) + (velocity * 2.0f)) / 3.0f;
        if (velocity2 > 1.0f) {
            velocity2 = 1.0f;
        }
        Rotation rotation = new Rotation(((float) ((Math.atan2(posZ, posX) * 180.0d) / 3.141592653589793d)) - 90.0f, (float) (-Math.toDegrees(Math.atan(((velocity2 * velocity2) - Math.sqrt((((velocity2 * velocity2) * velocity2) * velocity2) - (0.006000000052154064d * ((0.006000000052154064d * (posSqrt * posSqrt)) + ((2.0d * posY) * (velocity2 * velocity2)))))) / (0.006000000052154064d * posSqrt)))));
        if (silent) {
            setTargetRotation(rotation);
        } else {
            limitAngleChange(new Rotation(player.field_70177_z, player.field_70125_A), rotation, 10 + new Random().nextInt(6)).toPlayer(f362mc.field_71439_g);
        }
    }

    public static Rotation toRotation(Vec3 vec, boolean predict) {
        Vec3 eyesPos = new Vec3(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b + f362mc.field_71439_g.func_70047_e(), f362mc.field_71439_g.field_70161_v);
        if (predict) {
            eyesPos.func_72441_c(f362mc.field_71439_g.field_70159_w, f362mc.field_71439_g.field_70181_x, f362mc.field_71439_g.field_70179_y);
        }
        double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        return new Rotation(MathHelper.func_76142_g(((float) Math.toDegrees(Math.atan2(diffZ, diffX))) - 90.0f), MathHelper.func_76142_g((float) (-Math.toDegrees(Math.atan2(diffY, Math.sqrt((diffX * diffX) + (diffZ * diffZ)))))));
    }

    public static Vec3 getCenter(AxisAlignedBB bb) {
        return new Vec3(bb.field_72340_a + ((bb.field_72336_d - bb.field_72340_a) * 0.5d), bb.field_72338_b + ((bb.field_72337_e - bb.field_72338_b) * 0.5d), bb.field_72339_c + ((bb.field_72334_f - bb.field_72339_c) * 0.5d));
    }

    public static VecRotation searchCenter(AxisAlignedBB bb, boolean outborder, boolean random2, boolean predict, boolean throughWalls, float distance) {
        return searchCenter(bb, outborder, random2, predict, throughWalls, distance, 0.0f, false);
    }

    public static float roundRotation(float yaw, int strength) {
        return Math.round(yaw / strength) * strength;
    }

    public static VecRotation searchCenter(AxisAlignedBB bb, boolean outborder, boolean random2, boolean predict, boolean throughWalls, float distance, float randomMultiply, boolean newRandom) {
        if (outborder) {
            Vec3 vec3 = new Vec3(bb.field_72340_a + ((bb.field_72336_d - bb.field_72340_a) * ((f366x * 0.3d) + 1.0d)), bb.field_72338_b + ((bb.field_72337_e - bb.field_72338_b) * ((f367y * 0.3d) + 1.0d)), bb.field_72339_c + ((bb.field_72334_f - bb.field_72339_c) * ((f368z * 0.3d) + 1.0d)));
            return new VecRotation(vec3, toRotation(vec3, predict));
        }
        Vec3 randomVec = new Vec3(bb.field_72340_a + ((bb.field_72336_d - bb.field_72340_a) * f366x * randomMultiply * (newRandom ? Math.random() : 1.0d)), bb.field_72338_b + ((bb.field_72337_e - bb.field_72338_b) * f367y * randomMultiply * (newRandom ? Math.random() : 1.0d)), bb.field_72339_c + ((bb.field_72334_f - bb.field_72339_c) * f368z * randomMultiply * (newRandom ? Math.random() : 1.0d)));
        Rotation randomRotation = toRotation(randomVec, predict);
        Vec3 eyes = f362mc.field_71439_g.func_174824_e(1.0f);
        VecRotation vecRotation = null;
        double d = 0.15d;
        while (true) {
            double xSearch = d;
            if (xSearch < 0.85d) {
                double d2 = 0.15d;
                while (true) {
                    double ySearch = d2;
                    if (ySearch < 1.0d) {
                        double d3 = 0.15d;
                        while (true) {
                            double zSearch = d3;
                            if (zSearch < 0.85d) {
                                Vec3 vec32 = new Vec3(bb.field_72340_a + ((bb.field_72336_d - bb.field_72340_a) * xSearch), bb.field_72338_b + ((bb.field_72337_e - bb.field_72338_b) * ySearch), bb.field_72339_c + ((bb.field_72334_f - bb.field_72339_c) * zSearch));
                                Rotation rotation = toRotation(vec32, predict);
                                double vecDist = eyes.func_72438_d(vec32);
                                if (vecDist <= distance && (throughWalls || isVisible(vec32))) {
                                    VecRotation currentVec = new VecRotation(vec32, rotation);
                                    if (vecRotation != null) {
                                        if (random2) {
                                            if (getRotationDifference(currentVec.getRotation(), randomRotation) >= getRotationDifference(vecRotation.getRotation(), randomRotation)) {
                                            }
                                        } else if (getRotationDifference(currentVec.getRotation()) >= getRotationDifference(vecRotation.getRotation())) {
                                        }
                                    }
                                    vecRotation = currentVec;
                                }
                                d3 = zSearch + 0.1d;
                            }
                        }
                        d2 = ySearch + 0.1d;
                    }
                }
                d = xSearch + 0.1d;
            } else {
                return vecRotation;
            }
        }
    }

    public static double getRotationDifference(Entity entity) {
        Rotation rotation = toRotation(getCenter(entity.func_174813_aQ()), true);
        return getRotationDifference(rotation, new Rotation(f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70125_A));
    }

    public static double getRotationDifference(Rotation rotation) {
        if (serverRotation == null) {
            return 0.0d;
        }
        return getRotationDifference(rotation, serverRotation);
    }

    public static double getRotationDifference(Rotation a, Rotation b) {
        return Math.hypot(getAngleDifference(a.getYaw(), b.getYaw()), a.getPitch() - b.getPitch());
    }

    @NotNull
    public static Rotation limitAngleChange(Rotation currentRotation, Rotation targetRotation2, float turnSpeed) {
        float yawDifference = getAngleDifference(targetRotation2.getYaw(), currentRotation.getYaw());
        float pitchDifference = getAngleDifference(targetRotation2.getPitch(), currentRotation.getPitch());
        return new Rotation(currentRotation.getYaw() + (yawDifference > turnSpeed ? turnSpeed : Math.max(yawDifference, -turnSpeed)), currentRotation.getPitch() + (pitchDifference > turnSpeed ? turnSpeed : Math.max(pitchDifference, -turnSpeed)));
    }

    private static float getAngleDifference(float a, float b) {
        return ((((a - b) % 360.0f) + 540.0f) % 360.0f) - 180.0f;
    }

    public static Vec3 getVectorForRotation(Rotation rotation) {
        float yawCos = MathHelper.func_76134_b(((-rotation.getYaw()) * 0.017453292f) - 3.1415927f);
        float yawSin = MathHelper.func_76126_a(((-rotation.getYaw()) * 0.017453292f) - 3.1415927f);
        float pitchCos = -MathHelper.func_76134_b((-rotation.getPitch()) * 0.017453292f);
        float pitchSin = MathHelper.func_76126_a((-rotation.getPitch()) * 0.017453292f);
        return new Vec3(yawSin * pitchCos, pitchSin, yawCos * pitchCos);
    }

    public static boolean isFaced(Entity targetEntity, double blockReachDistance) {
        return RaycastUtils.raycastEntity(blockReachDistance, entity -> {
            return entity == targetEntity;
        }) != null;
    }

    public static boolean isVisible(Vec3 vec3) {
        Vec3 eyesPos = new Vec3(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b + f362mc.field_71439_g.func_70047_e(), f362mc.field_71439_g.field_70161_v);
        return f362mc.field_71441_e.func_72933_a(eyesPos, vec3) == null;
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (targetRotation != null) {
            keepLength--;
            if (keepLength <= 0) {
                reset();
            }
        }
        if (random.nextGaussian() > 0.8d) {
            f366x = Math.random();
        }
        if (random.nextGaussian() > 0.8d) {
            f367y = Math.random();
        }
        if (random.nextGaussian() > 0.8d) {
            f368z = Math.random();
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        C03PacketPlayer packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer packetPlayer = packet;
            if (targetRotation != null && !keepCurrentRotation && (targetRotation.getYaw() != serverRotation.getYaw() || targetRotation.getPitch() != serverRotation.getPitch())) {
                packetPlayer.field_149476_e = targetRotation.getYaw();
                packetPlayer.field_149473_f = targetRotation.getPitch();
                packetPlayer.field_149481_i = true;
            }
            if (packetPlayer.field_149481_i) {
                serverRotation = new Rotation(packetPlayer.field_149476_e, packetPlayer.field_149473_f);
            }
        }
    }

    public static void setTargetRotation(Rotation rotation) {
        setTargetRotation(rotation, 0);
    }

    public static void setTargetRotation(Rotation rotation, int keepLength2) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        rotation.fixedSensitivity(f362mc.field_71474_y.field_74341_c);
        targetRotation = rotation;
        keepLength = keepLength2;
    }

    public static void reset() {
        keepLength = 0;
        targetRotation = null;
    }

    public static Rotation getRotationsEntity(EntityLivingBase entity) {
        return getRotations(entity.field_70165_t, (entity.field_70163_u + entity.func_70047_e()) - 0.4d, entity.field_70161_v);
    }

    public static Rotation getRotations(Entity ent) {
        double x = ent.field_70165_t;
        double z = ent.field_70161_v;
        double y = ent.field_70163_u + (ent.func_70047_e() / 2.0f);
        return getRotationFromPosition(x, z, y);
    }

    public static Rotation getRotations(double posX, double posY, double posZ) {
        EntityPlayerSP player = f362mc.field_71439_g;
        double x = posX - player.field_70165_t;
        double y = posY - (player.field_70163_u + player.func_70047_e());
        double z = posZ - player.field_70161_v;
        double dist = MathHelper.func_76133_a((x * x) + (z * z));
        float yaw = ((float) ((Math.atan2(z, x) * 180.0d) / 3.141592653589793d)) - 90.0f;
        float pitch = (float) (-((Math.atan2(y, dist) * 180.0d) / 3.141592653589793d));
        return new Rotation(yaw, pitch);
    }

    public static Rotation getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - f362mc.field_71439_g.field_70165_t;
        double zDiff = z - f362mc.field_71439_g.field_70161_v;
        double yDiff = (y - f362mc.field_71439_g.field_70163_u) - 1.2d;
        double dist = MathHelper.func_76133_a((xDiff * xDiff) + (zDiff * zDiff));
        float yaw = ((float) ((Math.atan2(zDiff, xDiff) * 180.0d) / 3.141592653589793d)) - 90.0f;
        float pitch = (float) (((-Math.atan2(yDiff, dist)) * 180.0d) / 3.141592653589793d);
        return new Rotation(yaw, pitch);
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return true;
    }
}
