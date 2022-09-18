package de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/entityreplacement/ShulkerBulletReplacement.class */
public class ShulkerBulletReplacement implements EntityReplacement {
    private int entityId;
    private List<Metadata> datawatcher = new ArrayList();
    private double locX;
    private double locY;
    private double locZ;
    private float yaw;
    private float pitch;
    private float headYaw;
    private UserConnection user;

    public ShulkerBulletReplacement(int entityId, UserConnection user) {
        this.entityId = entityId;
        this.user = user;
        spawn();
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void setLocation(double x, double y, double z) {
        if (x != this.locX || y != this.locY || z != this.locZ) {
            this.locX = x;
            this.locY = y;
            this.locZ = z;
            updateLocation();
        }
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void relMove(double x, double y, double z) {
        if (x == 0.0d && y == 0.0d && z == 0.0d) {
            return;
        }
        this.locX += x;
        this.locY += y;
        this.locZ += z;
        updateLocation();
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void setYawPitch(float yaw, float pitch) {
        if (this.yaw != yaw && this.pitch != pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
            updateLocation();
        }
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void setHeadYaw(float yaw) {
        this.headYaw = yaw;
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void updateMetadata(List<Metadata> metadataList) {
    }

    public void updateLocation() {
        PacketWrapper teleport = PacketWrapper.create(24, (ByteBuf) null, this.user);
        teleport.write(Type.VAR_INT, Integer.valueOf(this.entityId));
        teleport.write(Type.INT, Integer.valueOf((int) (this.locX * 32.0d)));
        teleport.write(Type.INT, Integer.valueOf((int) (this.locY * 32.0d)));
        teleport.write(Type.INT, Integer.valueOf((int) (this.locZ * 32.0d)));
        teleport.write(Type.BYTE, Byte.valueOf((byte) ((this.yaw / 360.0f) * 256.0f)));
        teleport.write(Type.BYTE, Byte.valueOf((byte) ((this.pitch / 360.0f) * 256.0f)));
        teleport.write(Type.BOOLEAN, true);
        PacketWrapper head = PacketWrapper.create(25, (ByteBuf) null, this.user);
        head.write(Type.VAR_INT, Integer.valueOf(this.entityId));
        head.write(Type.BYTE, Byte.valueOf((byte) ((this.headYaw / 360.0f) * 256.0f)));
        PacketUtil.sendPacket(teleport, Protocol1_8TO1_9.class, true, true);
        PacketUtil.sendPacket(head, Protocol1_8TO1_9.class, true, true);
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void spawn() {
        PacketWrapper spawn = PacketWrapper.create(14, (ByteBuf) null, this.user);
        spawn.write(Type.VAR_INT, Integer.valueOf(this.entityId));
        spawn.write(Type.BYTE, (byte) 66);
        spawn.write(Type.INT, 0);
        spawn.write(Type.INT, 0);
        spawn.write(Type.INT, 0);
        spawn.write(Type.BYTE, (byte) 0);
        spawn.write(Type.BYTE, (byte) 0);
        spawn.write(Type.INT, 0);
        PacketUtil.sendPacket(spawn, Protocol1_8TO1_9.class, true, true);
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void despawn() {
        PacketWrapper despawn = PacketWrapper.create(19, (ByteBuf) null, this.user);
        despawn.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{this.entityId});
        PacketUtil.sendPacket(despawn, Protocol1_8TO1_9.class, true, true);
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public int getEntityId() {
        return this.entityId;
    }
}
