package de.gerrygames.viarewind.utils.math;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/utils/math/RayTracing.class */
public class RayTracing {
    public static Vector3d trace(Ray3d ray, AABB aabb, double distance) {
        Vector3d invDir = new Vector3d(1.0d / ray.dir.f216x, 1.0d / ray.dir.f217y, 1.0d / ray.dir.f218z);
        boolean signDirX = invDir.f216x < 0.0d;
        boolean signDirY = invDir.f217y < 0.0d;
        boolean signDirZ = invDir.f218z < 0.0d;
        Vector3d bbox = signDirX ? aabb.max : aabb.min;
        double tmin = (bbox.f216x - ray.start.f216x) * invDir.f216x;
        Vector3d bbox2 = signDirX ? aabb.min : aabb.max;
        double tmax = (bbox2.f216x - ray.start.f216x) * invDir.f216x;
        Vector3d bbox3 = signDirY ? aabb.max : aabb.min;
        double tymin = (bbox3.f217y - ray.start.f217y) * invDir.f217y;
        Vector3d bbox4 = signDirY ? aabb.min : aabb.max;
        double tymax = (bbox4.f217y - ray.start.f217y) * invDir.f217y;
        if (tmin > tymax || tymin > tmax) {
            return null;
        }
        if (tymin > tmin) {
            tmin = tymin;
        }
        if (tymax < tmax) {
            tmax = tymax;
        }
        Vector3d bbox5 = signDirZ ? aabb.max : aabb.min;
        double tzmin = (bbox5.f218z - ray.start.f218z) * invDir.f218z;
        Vector3d bbox6 = signDirZ ? aabb.min : aabb.max;
        double tzmax = (bbox6.f218z - ray.start.f218z) * invDir.f218z;
        if (tmin > tzmax || tzmin > tmax) {
            return null;
        }
        if (tzmin > tmin) {
            tmin = tzmin;
        }
        if (tzmax < tmax) {
            tmax = tzmax;
        }
        if (tmin <= distance && tmax > 0.0d) {
            return ray.start.clone().add(ray.dir.clone().normalize().multiply(tmin));
        }
        return null;
    }
}
