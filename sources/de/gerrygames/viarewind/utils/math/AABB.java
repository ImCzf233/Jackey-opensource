package de.gerrygames.viarewind.utils.math;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/utils/math/AABB.class */
public class AABB {
    Vector3d min;
    Vector3d max;

    public AABB(Vector3d min, Vector3d max) {
        this.min = min;
        this.max = max;
    }

    public Vector3d getMin() {
        return this.min;
    }

    public Vector3d getMax() {
        return this.max;
    }
}
