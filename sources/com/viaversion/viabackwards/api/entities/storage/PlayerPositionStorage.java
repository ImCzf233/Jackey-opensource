package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/entities/storage/PlayerPositionStorage.class */
public abstract class PlayerPositionStorage implements StorableObject {

    /* renamed from: x */
    private double f13x;

    /* renamed from: y */
    private double f14y;

    /* renamed from: z */
    private double f15z;

    public double getX() {
        return this.f13x;
    }

    public double getY() {
        return this.f14y;
    }

    public double getZ() {
        return this.f15z;
    }

    public void setX(double x) {
        this.f13x = x;
    }

    public void setY(double y) {
        this.f14y = y;
    }

    public void setZ(double z) {
        this.f15z = z;
    }

    public void setCoordinates(PacketWrapper wrapper, boolean relative) throws Exception {
        setCoordinates(((Double) wrapper.get(Type.DOUBLE, 0)).doubleValue(), ((Double) wrapper.get(Type.DOUBLE, 1)).doubleValue(), ((Double) wrapper.get(Type.DOUBLE, 2)).doubleValue(), relative);
    }

    public void setCoordinates(double x, double y, double z, boolean relative) {
        if (relative) {
            this.f13x += x;
            this.f14y += y;
            this.f15z += z;
            return;
        }
        this.f13x = x;
        this.f14y = y;
        this.f15z = z;
    }
}
