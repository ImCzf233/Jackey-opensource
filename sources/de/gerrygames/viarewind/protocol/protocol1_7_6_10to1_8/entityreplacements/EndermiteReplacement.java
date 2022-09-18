package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/entityreplacements/EndermiteReplacement.class */
public class EndermiteReplacement implements EntityReplacement {
    private int entityId;
    private List<Metadata> datawatcher = new ArrayList();
    private double locX;
    private double locY;
    private double locZ;
    private float yaw;
    private float pitch;
    private float headYaw;
    private UserConnection user;

    public EndermiteReplacement(int entityId, UserConnection user) {
        this.entityId = entityId;
        this.user = user;
        spawn();
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void setLocation(double x, double y, double z) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        updateLocation();
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void relMove(double x, double y, double z) {
        this.locX += x;
        this.locY += y;
        this.locZ += z;
        updateLocation();
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void setYawPitch(float yaw, float pitch) {
        if (this.yaw != yaw || this.pitch != pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
            updateLocation();
        }
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void setHeadYaw(float yaw) {
        if (this.headYaw != yaw) {
            this.headYaw = yaw;
            updateLocation();
        }
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void updateMetadata(List<Metadata> metadataList) {
        for (Metadata metadata : metadataList) {
            this.datawatcher.removeIf(m -> {
                return m.m222id() == metadata.m222id();
            });
            this.datawatcher.add(metadata);
        }
        updateMetadata();
    }

    public void updateLocation() {
        PacketWrapper teleport = PacketWrapper.create(24, (ByteBuf) null, this.user);
        teleport.write(Type.INT, Integer.valueOf(this.entityId));
        teleport.write(Type.INT, Integer.valueOf((int) (this.locX * 32.0d)));
        teleport.write(Type.INT, Integer.valueOf((int) (this.locY * 32.0d)));
        teleport.write(Type.INT, Integer.valueOf((int) (this.locZ * 32.0d)));
        teleport.write(Type.BYTE, Byte.valueOf((byte) ((this.yaw / 360.0f) * 256.0f)));
        teleport.write(Type.BYTE, Byte.valueOf((byte) ((this.pitch / 360.0f) * 256.0f)));
        PacketWrapper head = PacketWrapper.create(25, (ByteBuf) null, this.user);
        head.write(Type.INT, Integer.valueOf(this.entityId));
        head.write(Type.BYTE, Byte.valueOf((byte) ((this.headYaw / 360.0f) * 256.0f)));
        PacketUtil.sendPacket(teleport, Protocol1_7_6_10TO1_8.class, true, true);
        PacketUtil.sendPacket(head, Protocol1_7_6_10TO1_8.class, true, true);
    }

    public void updateMetadata() {
        PacketWrapper metadataPacket = PacketWrapper.create(28, (ByteBuf) null, this.user);
        metadataPacket.write(Type.INT, Integer.valueOf(this.entityId));
        ArrayList arrayList = new ArrayList();
        for (Metadata metadata : this.datawatcher) {
            arrayList.add(new Metadata(metadata.m222id(), metadata.metaType(), metadata.getValue()));
        }
        MetadataRewriter.transform(Entity1_10Types.EntityType.SQUID, arrayList);
        metadataPacket.write(Types1_7_6_10.METADATA_LIST, arrayList);
        PacketUtil.sendPacket(metadataPacket, Protocol1_7_6_10TO1_8.class);
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void spawn() {
        PacketWrapper spawn = PacketWrapper.create(15, (ByteBuf) null, this.user);
        spawn.write(Type.VAR_INT, Integer.valueOf(this.entityId));
        spawn.write(Type.UNSIGNED_BYTE, (short) 60);
        spawn.write(Type.INT, 0);
        spawn.write(Type.INT, 0);
        spawn.write(Type.INT, 0);
        spawn.write(Type.BYTE, (byte) 0);
        spawn.write(Type.BYTE, (byte) 0);
        spawn.write(Type.BYTE, (byte) 0);
        spawn.write(Type.SHORT, (short) 0);
        spawn.write(Type.SHORT, (short) 0);
        spawn.write(Type.SHORT, (short) 0);
        spawn.write(Types1_7_6_10.METADATA_LIST, new ArrayList());
        PacketUtil.sendPacket(spawn, Protocol1_7_6_10TO1_8.class, true, true);
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void despawn() {
        PacketWrapper despawn = PacketWrapper.create(19, (ByteBuf) null, this.user);
        despawn.write(Types1_7_6_10.INT_ARRAY, new int[]{this.entityId});
        PacketUtil.sendPacket(despawn, Protocol1_7_6_10TO1_8.class, true, true);
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public int getEntityId() {
        return this.entityId;
    }
}
