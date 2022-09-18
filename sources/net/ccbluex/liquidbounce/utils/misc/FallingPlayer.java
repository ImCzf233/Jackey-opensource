package net.ccbluex.liquidbounce.utils.misc;

import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/misc/FallingPlayer.class */
public class FallingPlayer extends MinecraftInstance {

    /* renamed from: x */
    private double f369x;

    /* renamed from: y */
    private double f370y;

    /* renamed from: z */
    private double f371z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private final float yaw;
    private float strafe;
    private float forward;

    public FallingPlayer(double x, double y, double z, double motionX, double motionY, double motionZ, float yaw, float strafe, float forward) {
        this.f369x = x;
        this.f370y = y;
        this.f371z = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.yaw = yaw;
        this.strafe = strafe;
        this.forward = forward;
    }

    private void calculateForTick() {
        this.strafe *= 0.98f;
        this.forward *= 0.98f;
        float v = (this.strafe * this.strafe) + (this.forward * this.forward);
        if (v >= 1.0E-4f) {
            float v2 = MathHelper.func_76129_c(v);
            if (v2 < 1.0f) {
                v2 = 1.0f;
            }
            float v3 = f362mc.field_71439_g.field_70747_aH / v2;
            this.strafe *= v3;
            this.forward *= v3;
            float f1 = MathHelper.func_76126_a((this.yaw * 3.1415927f) / 180.0f);
            float f2 = MathHelper.func_76134_b((this.yaw * 3.1415927f) / 180.0f);
            this.motionX += (this.strafe * f2) - (this.forward * f1);
            this.motionZ += (this.forward * f2) + (this.strafe * f1);
        }
        this.motionY -= 0.08d;
        this.motionX *= 0.91d;
        this.motionY *= 0.9800000190734863d;
        this.motionY *= 0.91d;
        this.motionZ *= 0.91d;
        this.f369x += this.motionX;
        this.f370y += this.motionY;
        this.f371z += this.motionZ;
    }

    public CollisionResult findCollision(int ticks) {
        for (int i = 0; i < ticks; i++) {
            Vec3 start = new Vec3(this.f369x, this.f370y, this.f371z);
            calculateForTick();
            Vec3 end = new Vec3(this.f369x, this.f370y, this.f371z);
            float w = f362mc.field_71439_g.field_70130_N / 2.0f;
            BlockPos raytracedBlock = rayTrace(start, end);
            if (raytracedBlock != null) {
                return new CollisionResult(raytracedBlock, i);
            }
            BlockPos raytracedBlock2 = rayTrace(start.func_72441_c(w, 0.0d, w), end);
            if (raytracedBlock2 != null) {
                return new CollisionResult(raytracedBlock2, i);
            }
            BlockPos raytracedBlock3 = rayTrace(start.func_72441_c(-w, 0.0d, w), end);
            if (raytracedBlock3 != null) {
                return new CollisionResult(raytracedBlock3, i);
            }
            BlockPos raytracedBlock4 = rayTrace(start.func_72441_c(w, 0.0d, -w), end);
            if (raytracedBlock4 != null) {
                return new CollisionResult(raytracedBlock4, i);
            }
            BlockPos raytracedBlock5 = rayTrace(start.func_72441_c(-w, 0.0d, -w), end);
            if (raytracedBlock5 != null) {
                return new CollisionResult(raytracedBlock5, i);
            }
            BlockPos raytracedBlock6 = rayTrace(start.func_72441_c(w, 0.0d, w / 2.0f), end);
            if (raytracedBlock6 != null) {
                return new CollisionResult(raytracedBlock6, i);
            }
            BlockPos raytracedBlock7 = rayTrace(start.func_72441_c(-w, 0.0d, w / 2.0f), end);
            if (raytracedBlock7 != null) {
                return new CollisionResult(raytracedBlock7, i);
            }
            BlockPos raytracedBlock8 = rayTrace(start.func_72441_c(w / 2.0f, 0.0d, w), end);
            if (raytracedBlock8 != null) {
                return new CollisionResult(raytracedBlock8, i);
            }
            BlockPos raytracedBlock9 = rayTrace(start.func_72441_c(w / 2.0f, 0.0d, -w), end);
            if (raytracedBlock9 != null) {
                return new CollisionResult(raytracedBlock9, i);
            }
        }
        return null;
    }

    @Nullable
    private BlockPos rayTrace(Vec3 start, Vec3 end) {
        MovingObjectPosition result = f362mc.field_71441_e.func_72901_a(start, end, true);
        if (result != null && result.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && result.field_178784_b == EnumFacing.UP) {
            return result.func_178782_a();
        }
        return null;
    }

    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/misc/FallingPlayer$CollisionResult.class */
    public static class CollisionResult {
        private final BlockPos pos;
        private final int tick;

        public CollisionResult(BlockPos pos, int tick) {
            this.pos = pos;
            this.tick = tick;
        }

        public BlockPos getPos() {
            return this.pos;
        }

        public int getTick() {
            return this.tick;
        }
    }
}
