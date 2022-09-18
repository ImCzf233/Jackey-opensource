package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/PlayerAbilities.class */
public class PlayerAbilities extends StoredObject {
    private boolean sprinting;
    private boolean allowFly;
    private boolean flying;
    private boolean invincible;
    private boolean creative;
    private float flySpeed;
    private float walkSpeed;

    public PlayerAbilities(UserConnection user) {
        super(user);
    }

    public byte getFlags() {
        byte flags = 0;
        if (this.invincible) {
            flags = (byte) (0 | 8);
        }
        if (this.allowFly) {
            flags = (byte) (flags | 4);
        }
        if (this.flying) {
            flags = (byte) (flags | 2);
        }
        if (this.creative) {
            flags = (byte) (flags | 1);
        }
        return flags;
    }

    public boolean isSprinting() {
        return this.sprinting;
    }

    public boolean isAllowFly() {
        return this.allowFly;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public boolean isInvincible() {
        return this.invincible;
    }

    public boolean isCreative() {
        return this.creative;
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }

    public void setAllowFly(boolean allowFly) {
        this.allowFly = allowFly;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public void setCreative(boolean creative) {
        this.creative = creative;
    }

    public void setFlySpeed(float flySpeed) {
        this.flySpeed = flySpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PlayerAbilities)) {
            return false;
        }
        PlayerAbilities other = (PlayerAbilities) o;
        return other.canEqual(this) && isSprinting() == other.isSprinting() && isAllowFly() == other.isAllowFly() && isFlying() == other.isFlying() && isInvincible() == other.isInvincible() && isCreative() == other.isCreative() && Float.compare(getFlySpeed(), other.getFlySpeed()) == 0 && Float.compare(getWalkSpeed(), other.getWalkSpeed()) == 0;
    }

    protected boolean canEqual(Object other) {
        return other instanceof PlayerAbilities;
    }

    public int hashCode() {
        int result = (1 * 59) + (isSprinting() ? 79 : 97);
        return (((((((((((result * 59) + (isAllowFly() ? 79 : 97)) * 59) + (isFlying() ? 79 : 97)) * 59) + (isInvincible() ? 79 : 97)) * 59) + (isCreative() ? 79 : 97)) * 59) + Float.floatToIntBits(getFlySpeed())) * 59) + Float.floatToIntBits(getWalkSpeed());
    }

    public String toString() {
        return "PlayerAbilities(sprinting=" + isSprinting() + ", allowFly=" + isAllowFly() + ", flying=" + isFlying() + ", invincible=" + isInvincible() + ", creative=" + isCreative() + ", flySpeed=" + getFlySpeed() + ", walkSpeed=" + getWalkSpeed() + ")";
    }
}
