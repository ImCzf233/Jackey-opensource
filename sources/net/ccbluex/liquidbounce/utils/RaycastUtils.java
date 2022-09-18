package net.ccbluex.liquidbounce.utils;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/RaycastUtils.class */
public final class RaycastUtils extends MinecraftInstance {

    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/RaycastUtils$IEntityFilter.class */
    public interface IEntityFilter {
        boolean canRaycast(Entity entity);
    }

    public static Entity raycastEntity(double range, IEntityFilter entityFilter) {
        return raycastEntity(range, RotationUtils.serverRotation.getYaw(), RotationUtils.serverRotation.getPitch(), entityFilter);
    }

    private static Entity raycastEntity(double range, float yaw, float pitch, IEntityFilter entityFilter) {
        Entity renderViewEntity = f362mc.func_175606_aa();
        if (renderViewEntity != null && f362mc.field_71441_e != null) {
            double blockReachDistance = range;
            Vec3 eyePosition = renderViewEntity.func_174824_e(1.0f);
            float yawCos = MathHelper.func_76134_b(((-yaw) * 0.017453292f) - 3.1415927f);
            float yawSin = MathHelper.func_76126_a(((-yaw) * 0.017453292f) - 3.1415927f);
            float pitchCos = -MathHelper.func_76134_b((-pitch) * 0.017453292f);
            float pitchSin = MathHelper.func_76126_a((-pitch) * 0.017453292f);
            Vec3 entityLook = new Vec3(yawSin * pitchCos, pitchSin, yawCos * pitchCos);
            Vec3 vector = eyePosition.func_72441_c(entityLook.field_72450_a * blockReachDistance, entityLook.field_72448_b * blockReachDistance, entityLook.field_72449_c * blockReachDistance);
            List<Entity> entityList = f362mc.field_71441_e.func_175674_a(renderViewEntity, renderViewEntity.func_174813_aQ().func_72321_a(entityLook.field_72450_a * blockReachDistance, entityLook.field_72448_b * blockReachDistance, entityLook.field_72449_c * blockReachDistance).func_72314_b(1.0d, 1.0d, 1.0d), Predicates.and(EntitySelectors.field_180132_d, (v0) -> {
                return v0.func_70067_L();
            }));
            Entity pointedEntity = null;
            for (Entity entity : entityList) {
                if (entityFilter.canRaycast(entity)) {
                    float collisionBorderSize = entity.func_70111_Y();
                    AxisAlignedBB axisAlignedBB = entity.func_174813_aQ().func_72314_b(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                    MovingObjectPosition movingObjectPosition = axisAlignedBB.func_72327_a(eyePosition, vector);
                    if (axisAlignedBB.func_72318_a(eyePosition)) {
                        if (blockReachDistance >= 0.0d) {
                            pointedEntity = entity;
                            blockReachDistance = 0.0d;
                        }
                    } else if (movingObjectPosition != null) {
                        double eyeDistance = eyePosition.func_72438_d(movingObjectPosition.field_72307_f);
                        if (eyeDistance < blockReachDistance || blockReachDistance == 0.0d) {
                            if (entity == renderViewEntity.field_70154_o && !renderViewEntity.canRiderInteract()) {
                                if (blockReachDistance == 0.0d) {
                                    pointedEntity = entity;
                                }
                            } else {
                                pointedEntity = entity;
                                blockReachDistance = eyeDistance;
                            }
                        }
                    }
                }
            }
            return pointedEntity;
        }
        return null;
    }
}
