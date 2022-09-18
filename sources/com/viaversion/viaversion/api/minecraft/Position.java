package com.viaversion.viaversion.api.minecraft;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/Position.class */
public class Position {

    /* renamed from: x */
    private final int f36x;

    /* renamed from: y */
    private final int f37y;

    /* renamed from: z */
    private final int f38z;

    public Position(int x, int y, int z) {
        this.f36x = x;
        this.f37y = y;
        this.f38z = z;
    }

    @Deprecated
    public Position(int x, short y, int z) {
        this(x, (int) y, z);
    }

    @Deprecated
    public Position(Position toCopy) {
        this(toCopy.m228x(), toCopy.m227y(), toCopy.m226z());
    }

    public Position getRelative(BlockFace face) {
        return new Position(this.f36x + face.modX(), (short) (this.f37y + face.modY()), this.f38z + face.modZ());
    }

    /* renamed from: x */
    public int m228x() {
        return this.f36x;
    }

    /* renamed from: y */
    public int m227y() {
        return this.f37y;
    }

    /* renamed from: z */
    public int m226z() {
        return this.f38z;
    }

    @Deprecated
    public int getX() {
        return this.f36x;
    }

    @Deprecated
    public int getY() {
        return this.f37y;
    }

    @Deprecated
    public int getZ() {
        return this.f38z;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return this.f36x == position.f36x && this.f37y == position.f37y && this.f38z == position.f38z;
    }

    public int hashCode() {
        int result = this.f36x;
        return (31 * ((31 * result) + this.f37y)) + this.f38z;
    }

    public String toString() {
        return "Position{x=" + this.f36x + ", y=" + this.f37y + ", z=" + this.f38z + '}';
    }
}
