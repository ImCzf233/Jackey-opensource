package de.gerrygames.viarewind.utils.math;

import java.util.Objects;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/utils/math/Vector3d.class */
public class Vector3d {

    /* renamed from: x */
    double f216x;

    /* renamed from: y */
    double f217y;

    /* renamed from: z */
    double f218z;

    public Vector3d(double x, double y, double z) {
        this.f216x = x;
        this.f217y = y;
        this.f218z = z;
    }

    public Vector3d() {
    }

    public void set(Vector3d vec) {
        this.f216x = vec.f216x;
        this.f217y = vec.f217y;
        this.f218z = vec.f218z;
    }

    public Vector3d set(double x, double y, double z) {
        this.f216x = x;
        this.f217y = y;
        this.f218z = z;
        return this;
    }

    public Vector3d multiply(double a) {
        this.f216x *= a;
        this.f217y *= a;
        this.f218z *= a;
        return this;
    }

    public Vector3d add(Vector3d vec) {
        this.f216x += vec.f216x;
        this.f217y += vec.f217y;
        this.f218z += vec.f218z;
        return this;
    }

    public Vector3d substract(Vector3d vec) {
        this.f216x -= vec.f216x;
        this.f217y -= vec.f217y;
        this.f218z -= vec.f218z;
        return this;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return (this.f216x * this.f216x) + (this.f217y * this.f217y) + (this.f218z * this.f218z);
    }

    public Vector3d normalize() {
        double length = length();
        multiply(1.0d / length);
        return this;
    }

    public Vector3d clone() {
        return new Vector3d(this.f216x, this.f217y, this.f218z);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vector3d vector3d = (Vector3d) o;
        return Double.compare(vector3d.f216x, this.f216x) == 0 && Double.compare(vector3d.f217y, this.f217y) == 0 && Double.compare(vector3d.f218z, this.f218z) == 0;
    }

    public int hashCode() {
        return Objects.hash(Double.valueOf(this.f216x), Double.valueOf(this.f217y), Double.valueOf(this.f218z));
    }

    public String toString() {
        return "Vector3d{x=" + this.f216x + ", y=" + this.f217y + ", z=" + this.f218z + '}';
    }

    public double getX() {
        return this.f216x;
    }

    public double getY() {
        return this.f217y;
    }

    public double getZ() {
        return this.f218z;
    }

    public void setX(double x) {
        this.f216x = x;
    }

    public void setY(double y) {
        this.f217y = y;
    }

    public void setZ(double z) {
        this.f218z = z;
    }
}
