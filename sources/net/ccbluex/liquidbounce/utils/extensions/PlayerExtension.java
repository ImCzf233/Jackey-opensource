package net.ccbluex.liquidbounce.utils.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��$\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\u001a\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t\u001a\u0012\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\f\u001a\u00020\u0002\"\u0015\u0010��\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\r"}, m53d2 = {"rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "Lnet/minecraft/entity/Entity;", "getRotation", "(Lnet/minecraft/entity/Entity;)Lnet/ccbluex/liquidbounce/utils/Rotation;", "getNearestPointBB", "Lnet/minecraft/util/Vec3;", "eye", "box", "Lnet/minecraft/util/AxisAlignedBB;", "getDistanceToEntityBox", "", "entity", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/extensions/PlayerExtensionKt.class */
public final class PlayerExtension {
    public static final double getDistanceToEntityBox(@NotNull Entity $this$getDistanceToEntityBox, @NotNull Entity entity) {
        Intrinsics.checkNotNullParameter($this$getDistanceToEntityBox, "<this>");
        Intrinsics.checkNotNullParameter(entity, "entity");
        Vec3 eyes = $this$getDistanceToEntityBox.func_174824_e(1.0f);
        Intrinsics.checkNotNullExpressionValue(eyes, "eyes");
        AxisAlignedBB func_174813_aQ = entity.func_174813_aQ();
        Intrinsics.checkNotNullExpressionValue(func_174813_aQ, "entity.entityBoundingBox");
        Vec3 pos = getNearestPointBB(eyes, func_174813_aQ);
        double xDist = Math.abs(pos.field_72450_a - eyes.field_72450_a);
        double yDist = Math.abs(pos.field_72448_b - eyes.field_72448_b);
        double zDist = Math.abs(pos.field_72449_c - eyes.field_72449_c);
        return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2) + Math.pow(zDist, 2));
    }

    @NotNull
    public static final Vec3 getNearestPointBB(@NotNull Vec3 eye, @NotNull AxisAlignedBB box) {
        Intrinsics.checkNotNullParameter(eye, "eye");
        Intrinsics.checkNotNullParameter(box, "box");
        double[] origin = new double[3];
        origin[0] = eye.field_72450_a;
        origin[1] = eye.field_72448_b;
        origin[2] = eye.field_72449_c;
        double[] destMins = {box.field_72340_a, box.field_72338_b, box.field_72339_c};
        double[] destMaxs = {box.field_72336_d, box.field_72337_e, box.field_72334_f};
        int i = 0;
        while (i < 3) {
            int i2 = i;
            i++;
            if (origin[i2] > destMaxs[i2]) {
                origin[i2] = destMaxs[i2];
            } else if (origin[i2] < destMins[i2]) {
                origin[i2] = destMins[i2];
            }
        }
        return new Vec3(origin[0], origin[1], origin[2]);
    }

    @NotNull
    public static final Rotation getRotation(@NotNull Entity $this$rotation) {
        Intrinsics.checkNotNullParameter($this$rotation, "<this>");
        return new Rotation($this$rotation.field_70177_z, $this$rotation.field_70125_A);
    }
}
