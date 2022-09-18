package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Rotation.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��<\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\b\u0086\b\u0018��2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0012\u001a\u00020��2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u000e\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\u000e\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cJ\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\u001f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/Rotation;", "", "yaw", "", "pitch", "(FF)V", "getPitch", "()F", "setPitch", "(F)V", "getYaw", "setYaw", "applyStrafeToPlayer", "", "event", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "component1", "component2", "copy", "equals", "", "other", "fixedSensitivity", "sensitivity", "hashCode", "", "toPlayer", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "toString", "", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/Rotation.class */
public final class Rotation {
    private float yaw;
    private float pitch;

    public final float component1() {
        return this.yaw;
    }

    public final float component2() {
        return this.pitch;
    }

    @NotNull
    public final Rotation copy(float yaw, float pitch) {
        return new Rotation(yaw, pitch);
    }

    public static /* synthetic */ Rotation copy$default(Rotation rotation, float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = rotation.yaw;
        }
        if ((i & 2) != 0) {
            f2 = rotation.pitch;
        }
        return rotation.copy(f, f2);
    }

    @NotNull
    public String toString() {
        return "Rotation(yaw=" + this.yaw + ", pitch=" + this.pitch + ')';
    }

    public int hashCode() {
        int result = Float.hashCode(this.yaw);
        return (result * 31) + Float.hashCode(this.pitch);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Rotation)) {
            return false;
        }
        Rotation rotation = (Rotation) other;
        return Intrinsics.areEqual((Object) Float.valueOf(this.yaw), (Object) Float.valueOf(rotation.yaw)) && Intrinsics.areEqual((Object) Float.valueOf(this.pitch), (Object) Float.valueOf(rotation.pitch));
    }

    public Rotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setYaw(float f) {
        this.yaw = f;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final void setPitch(float f) {
        this.pitch = f;
    }

    public final void toPlayer(@NotNull EntityPlayer player) {
        Intrinsics.checkNotNullParameter(player, "player");
        if (Float.isNaN(this.yaw) || Float.isNaN(this.pitch)) {
            return;
        }
        fixedSensitivity(MinecraftInstance.f362mc.field_71474_y.field_74341_c);
        player.field_70177_z = this.yaw;
        player.field_70125_A = this.pitch;
    }

    public final void fixedSensitivity(float sensitivity) {
        float f = (sensitivity * 0.6f) + 0.2f;
        float gcd = f * f * f * 1.2f;
        Rotation rotation = RotationUtils.serverRotation;
        float deltaYaw = this.yaw - rotation.yaw;
        this.yaw = rotation.yaw + (deltaYaw - (deltaYaw % gcd));
        float deltaPitch = this.pitch - rotation.pitch;
        this.pitch = rotation.pitch + (deltaPitch - (deltaPitch % gcd));
    }

    public final void applyStrafeToPlayer(@NotNull StrafeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        EntityPlayerSP player = MinecraftInstance.f362mc.field_71439_g;
        int dif = (int) ((MathHelper.func_76142_g(((player.field_70177_z - this.yaw) - 23.5f) - 135) + 180) / 45);
        float yaw = this.yaw;
        float strafe = event.getStrafe();
        float forward = event.getForward();
        float friction = event.getFriction();
        float calcForward = 0.0f;
        float calcStrafe = 0.0f;
        switch (dif) {
            case 0:
                calcForward = forward;
                calcStrafe = strafe;
                break;
            case 1:
                float calcForward2 = 0.0f + forward;
                float calcStrafe2 = 0.0f - forward;
                calcForward = calcForward2 + strafe;
                calcStrafe = calcStrafe2 + strafe;
                break;
            case 2:
                calcForward = strafe;
                calcStrafe = -forward;
                break;
            case 3:
                float calcForward3 = 0.0f - forward;
                float calcStrafe3 = 0.0f - forward;
                calcForward = calcForward3 + strafe;
                calcStrafe = calcStrafe3 - strafe;
                break;
            case 4:
                calcForward = -forward;
                calcStrafe = -strafe;
                break;
            case 5:
                float calcForward4 = 0.0f - forward;
                float calcStrafe4 = 0.0f + forward;
                calcForward = calcForward4 - strafe;
                calcStrafe = calcStrafe4 - strafe;
                break;
            case 6:
                calcForward = -strafe;
                calcStrafe = forward;
                break;
            case 7:
                float calcForward5 = 0.0f + forward;
                float calcStrafe5 = 0.0f + forward;
                calcForward = calcForward5 - strafe;
                calcStrafe = calcStrafe5 + strafe;
                break;
        }
        if (calcForward > 1.0f || ((calcForward < 0.9f && calcForward > 0.3f) || calcForward < -1.0f || (calcForward > -0.9f && calcForward < -0.3f))) {
            calcForward *= 0.5f;
        }
        if (calcStrafe > 1.0f || ((calcStrafe < 0.9f && calcStrafe > 0.3f) || calcStrafe < -1.0f || (calcStrafe > -0.9f && calcStrafe < -0.3f))) {
            calcStrafe *= 0.5f;
        }
        float d = (calcStrafe * calcStrafe) + (calcForward * calcForward);
        if (d >= 1.0E-4f) {
            float d2 = MathHelper.func_76129_c(d);
            if (d2 < 1.0f) {
                d2 = 1.0f;
            }
            float d3 = friction / d2;
            float calcStrafe6 = calcStrafe * d3;
            float calcForward6 = calcForward * d3;
            float yawSin = MathHelper.func_76126_a((float) ((yaw * 3.141592653589793d) / 180.0f));
            float yawCos = MathHelper.func_76134_b((float) ((yaw * 3.141592653589793d) / 180.0f));
            player.field_70159_w += (calcStrafe6 * yawCos) - (calcForward6 * yawSin);
            player.field_70179_y += (calcForward6 * yawCos) + (calcStrafe6 * yawSin);
        }
    }
}
