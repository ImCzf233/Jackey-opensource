package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/storage/PlayerPosition.class */
public class PlayerPosition extends StoredObject {
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private int confirmId = -1;

    public PlayerPosition(UserConnection user) {
        super(user);
    }

    public void setPos(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw % 360.0f;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch % 360.0f;
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public int getConfirmId() {
        return this.confirmId;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setConfirmId(int confirmId) {
        this.confirmId = confirmId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PlayerPosition)) {
            return false;
        }
        PlayerPosition other = (PlayerPosition) o;
        return other.canEqual(this) && Double.compare(getPosX(), other.getPosX()) == 0 && Double.compare(getPosY(), other.getPosY()) == 0 && Double.compare(getPosZ(), other.getPosZ()) == 0 && Float.compare(getYaw(), other.getYaw()) == 0 && Float.compare(getPitch(), other.getPitch()) == 0 && isOnGround() == other.isOnGround() && getConfirmId() == other.getConfirmId();
    }

    protected boolean canEqual(Object other) {
        return other instanceof PlayerPosition;
    }

    public int hashCode() {
        long $posX = Double.doubleToLongBits(getPosX());
        int result = (1 * 59) + ((int) (($posX >>> 32) ^ $posX));
        long $posY = Double.doubleToLongBits(getPosY());
        int result2 = (result * 59) + ((int) (($posY >>> 32) ^ $posY));
        long $posZ = Double.doubleToLongBits(getPosZ());
        return (((((((((result2 * 59) + ((int) (($posZ >>> 32) ^ $posZ))) * 59) + Float.floatToIntBits(getYaw())) * 59) + Float.floatToIntBits(getPitch())) * 59) + (isOnGround() ? 79 : 97)) * 59) + getConfirmId();
    }

    public String toString() {
        return "PlayerPosition(posX=" + getPosX() + ", posY=" + getPosY() + ", posZ=" + getPosZ() + ", yaw=" + getYaw() + ", pitch=" + getPitch() + ", onGround=" + isOnGround() + ", confirmId=" + getConfirmId() + ")";
    }
}
