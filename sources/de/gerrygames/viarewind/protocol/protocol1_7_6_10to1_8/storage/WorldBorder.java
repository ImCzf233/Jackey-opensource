package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.utils.PacketUtil;
import de.gerrygames.viarewind.utils.Tickable;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/WorldBorder.class */
public class WorldBorder extends StoredObject implements Tickable {

    /* renamed from: x */
    private double f211x;

    /* renamed from: z */
    private double f212z;
    private double oldDiameter;
    private double newDiameter;
    private long lerpTime;
    private long lerpStartTime;
    private int portalTeleportBoundary;
    private int warningTime;
    private int warningBlocks;
    private boolean init = false;
    private final int VIEW_DISTANCE = 16;

    public WorldBorder(UserConnection user) {
        super(user);
    }

    @Override // de.gerrygames.viarewind.utils.Tickable
    public void tick() {
        if (!isInit()) {
            return;
        }
        sendPackets();
    }

    /* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/WorldBorder$Side.class */
    public enum Side {
        NORTH(0, -1),
        EAST(1, 0),
        SOUTH(0, 1),
        WEST(-1, 0);
        
        private int modX;
        private int modZ;

        Side(int modX, int modZ) {
            this.modX = modX;
            this.modZ = modZ;
        }
    }

    private void sendPackets() {
        Side[] values;
        double center;
        double pos;
        double d;
        PlayerPosition position = (PlayerPosition) getUser().get(PlayerPosition.class);
        double radius = getSize() / 2.0d;
        for (Side side : Side.values()) {
            if (side.modX != 0) {
                pos = position.getPosZ();
                center = this.f212z;
                d = Math.abs((this.f211x + (radius * side.modX)) - position.getPosX());
            } else {
                center = this.f211x;
                pos = position.getPosX();
                d = Math.abs((this.f212z + (radius * side.modZ)) - position.getPosZ());
            }
            if (d < 16.0d) {
                double r = Math.sqrt(256.0d - (d * d));
                double minH = Math.ceil(pos - r);
                double maxH = Math.floor(pos + r);
                double minV = Math.ceil(position.getPosY() - r);
                double maxV = Math.floor(position.getPosY() + r);
                if (minH < center - radius) {
                    minH = Math.ceil(center - radius);
                }
                if (maxH > center + radius) {
                    maxH = Math.floor(center + radius);
                }
                if (minV < 0.0d) {
                    minV = 0.0d;
                }
                double centerH = (minH + maxH) / 2.0d;
                double centerV = (minV + maxV) / 2.0d;
                int a = (int) Math.floor((maxH - minH) * (maxV - minV) * 0.5d);
                PacketWrapper particles = PacketWrapper.create(42, (ByteBuf) null, getUser());
                particles.write(Type.STRING, "fireworksSpark");
                particles.write(Type.FLOAT, Float.valueOf((float) (side.modX != 0 ? this.f211x + (radius * side.modX) : centerH)));
                particles.write(Type.FLOAT, Float.valueOf((float) centerV));
                particles.write(Type.FLOAT, Float.valueOf((float) (side.modX == 0 ? this.f212z + (radius * side.modZ) : centerH)));
                particles.write(Type.FLOAT, Float.valueOf((float) (side.modX != 0 ? 0.0d : (maxH - minH) / 2.5d)));
                particles.write(Type.FLOAT, Float.valueOf((float) ((maxV - minV) / 2.5d)));
                particles.write(Type.FLOAT, Float.valueOf((float) (side.modX == 0 ? 0.0d : (maxH - minH) / 2.5d)));
                particles.write(Type.FLOAT, Float.valueOf(0.0f));
                particles.write(Type.INT, Integer.valueOf(a));
                PacketUtil.sendPacket(particles, Protocol1_7_6_10TO1_8.class, true, true);
            }
        }
    }

    private boolean isInit() {
        return this.init;
    }

    public void init(double x, double z, double oldDiameter, double newDiameter, long lerpTime, int portalTeleportBoundary, int warningTime, int warningBlocks) {
        this.f211x = x;
        this.f212z = z;
        this.oldDiameter = oldDiameter;
        this.newDiameter = newDiameter;
        this.lerpTime = lerpTime;
        this.portalTeleportBoundary = portalTeleportBoundary;
        this.warningTime = warningTime;
        this.warningBlocks = warningBlocks;
        this.init = true;
    }

    public double getX() {
        return this.f211x;
    }

    public double getZ() {
        return this.f212z;
    }

    public void setCenter(double x, double z) {
        this.f211x = x;
        this.f212z = z;
    }

    public double getOldDiameter() {
        return this.oldDiameter;
    }

    public double getNewDiameter() {
        return this.newDiameter;
    }

    public long getLerpTime() {
        return this.lerpTime;
    }

    public void lerpSize(double oldDiameter, double newDiameter, long lerpTime) {
        this.oldDiameter = oldDiameter;
        this.newDiameter = newDiameter;
        this.lerpTime = lerpTime;
        this.lerpStartTime = System.currentTimeMillis();
    }

    public void setSize(double size) {
        this.oldDiameter = size;
        this.newDiameter = size;
        this.lerpTime = 0L;
    }

    public double getSize() {
        if (this.lerpTime == 0) {
            return this.newDiameter;
        }
        long time = System.currentTimeMillis() - this.lerpStartTime;
        double percent = time / this.lerpTime;
        if (percent > 1.0d) {
            percent = 1.0d;
        } else if (percent < 0.0d) {
            percent = 0.0d;
        }
        return this.oldDiameter + ((this.newDiameter - this.oldDiameter) * percent);
    }

    public int getPortalTeleportBoundary() {
        return this.portalTeleportBoundary;
    }

    public void setPortalTeleportBoundary(int portalTeleportBoundary) {
        this.portalTeleportBoundary = portalTeleportBoundary;
    }

    public int getWarningTime() {
        return this.warningTime;
    }

    public void setWarningTime(int warningTime) {
        this.warningTime = warningTime;
    }

    public int getWarningBlocks() {
        return this.warningBlocks;
    }

    public void setWarningBlocks(int warningBlocks) {
        this.warningBlocks = warningBlocks;
    }
}
