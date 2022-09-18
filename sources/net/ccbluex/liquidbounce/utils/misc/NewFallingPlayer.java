package net.ccbluex.liquidbounce.utils.misc;

import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NewFallingPlayer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018��2\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004BW\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\b\b\u0002\u0010\u0010\u001a\u00020\r¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u001c"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/misc/NewFallingPlayer;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "(Lnet/minecraft/entity/player/EntityPlayer;)V", "x", "", "y", "z", "motionX", "motionY", "motionZ", "yaw", "", "strafe", "forward", "jumpMovementFactor", "(DDDDDDFFFF)V", "calculateForTick", "", "findCollision", "Lnet/minecraft/util/BlockPos;", "ticks", "", "rayTrace", "start", "Lnet/minecraft/util/Vec3;", AsmConstants.END, "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/misc/NewFallingPlayer.class */
public final class NewFallingPlayer extends MinecraftInstance {

    /* renamed from: x */
    private double f372x;

    /* renamed from: y */
    private double f373y;

    /* renamed from: z */
    private double f374z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private final float yaw;
    private float strafe;
    private float forward;
    private final float jumpMovementFactor;

    public /* synthetic */ NewFallingPlayer(double d, double d2, double d3, double d4, double d5, double d6, float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(d, d2, d3, d4, d5, d6, f, f2, f3, (i & 512) != 0 ? 0.02f : f4);
    }

    public NewFallingPlayer(double x, double y, double z, double motionX, double motionY, double motionZ, float yaw, float strafe, float forward, float jumpMovementFactor) {
        this.f372x = x;
        this.f373y = y;
        this.f374z = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.yaw = yaw;
        this.strafe = strafe;
        this.forward = forward;
        this.jumpMovementFactor = jumpMovementFactor;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NewFallingPlayer(@NotNull EntityPlayer player) {
        this(player.field_70165_t, player.field_70163_u, player.field_70161_v, player.field_70159_w, player.field_70181_x, player.field_70179_y, player.field_70177_z, player.field_70702_br, player.field_70701_bs, player.field_70747_aH);
        Intrinsics.checkNotNullParameter(player, "player");
    }

    private final void calculateForTick() {
        this.strafe *= 0.98f;
        this.forward *= 0.98f;
        float v = (this.strafe * this.strafe) + (this.forward * this.forward);
        if (v >= 1.0E-4f) {
            float v2 = MathHelper.func_76129_c(v);
            if (v2 < 1.0f) {
                v2 = 1.0f;
            }
            float fixedJumpFactor = this.jumpMovementFactor;
            if (MinecraftInstance.f362mc.field_71439_g.func_70051_ag()) {
                fixedJumpFactor = (float) (fixedJumpFactor * 1.3d);
            }
            float v3 = fixedJumpFactor / v2;
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
        this.motionZ *= 0.91d;
        this.f372x += this.motionX;
        this.f373y += this.motionY;
        this.f374z += this.motionZ;
    }

    @Nullable
    public final BlockPos findCollision(int ticks) {
        int i = 0;
        while (i < ticks) {
            i++;
            Vec3 start = new Vec3(this.f372x, this.f373y, this.f374z);
            calculateForTick();
            Vec3 end = new Vec3(this.f372x, this.f373y, this.f374z);
            float w = MinecraftInstance.f362mc.field_71439_g.field_70130_N / 2.0f;
            BlockPos it = rayTrace(start, end);
            if (it != null) {
                return it;
            }
            Vec3 func_72441_c = start.func_72441_c(w, 0.0d, w);
            Intrinsics.checkNotNullExpressionValue(func_72441_c, "start.addVector(w.toDouble(), 0.0, w.toDouble())");
            BlockPos it2 = rayTrace(func_72441_c, end);
            if (it2 != null) {
                return it2;
            }
            Vec3 func_72441_c2 = start.func_72441_c(-w, 0.0d, w);
            Intrinsics.checkNotNullExpressionValue(func_72441_c2, "start.addVector(-w.toDouble(), 0.0, w.toDouble())");
            BlockPos it3 = rayTrace(func_72441_c2, end);
            if (it3 != null) {
                return it3;
            }
            Vec3 func_72441_c3 = start.func_72441_c(w, 0.0d, -w);
            Intrinsics.checkNotNullExpressionValue(func_72441_c3, "start.addVector(w.toDouble(), 0.0, -w.toDouble())");
            BlockPos it4 = rayTrace(func_72441_c3, end);
            if (it4 != null) {
                return it4;
            }
            Vec3 func_72441_c4 = start.func_72441_c(-w, 0.0d, -w);
            Intrinsics.checkNotNullExpressionValue(func_72441_c4, "start.addVector(-w.toDouble(), 0.0, -w.toDouble())");
            BlockPos it5 = rayTrace(func_72441_c4, end);
            if (it5 != null) {
                return it5;
            }
            Vec3 func_72441_c5 = start.func_72441_c(w, 0.0d, w / 2.0f);
            Intrinsics.checkNotNullExpressionValue(func_72441_c5, "start.addVector(w.toDoub…0.0, (w / 2f).toDouble())");
            BlockPos it6 = rayTrace(func_72441_c5, end);
            if (it6 != null) {
                return it6;
            }
            Vec3 func_72441_c6 = start.func_72441_c(-w, 0.0d, w / 2.0f);
            Intrinsics.checkNotNullExpressionValue(func_72441_c6, "start.addVector(-w.toDou…0.0, (w / 2f).toDouble())");
            BlockPos it7 = rayTrace(func_72441_c6, end);
            if (it7 != null) {
                return it7;
            }
            Vec3 func_72441_c7 = start.func_72441_c(w / 2.0f, 0.0d, w);
            Intrinsics.checkNotNullExpressionValue(func_72441_c7, "start.addVector((w / 2f)…ble(), 0.0, w.toDouble())");
            BlockPos it8 = rayTrace(func_72441_c7, end);
            if (it8 != null) {
                return it8;
            }
            Vec3 func_72441_c8 = start.func_72441_c(w / 2.0f, 0.0d, -w);
            Intrinsics.checkNotNullExpressionValue(func_72441_c8, "start.addVector((w / 2f)…le(), 0.0, -w.toDouble())");
            BlockPos it9 = rayTrace(func_72441_c8, end);
            if (it9 != null) {
                return it9;
            }
        }
        return null;
    }

    private final BlockPos rayTrace(Vec3 start, Vec3 end) {
        MovingObjectPosition result = MinecraftInstance.f362mc.field_71441_e.func_72901_a(start, end, true);
        if (result != null && result.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && result.field_178784_b == EnumFacing.UP) {
            return result.func_178782_a();
        }
        return null;
    }
}
