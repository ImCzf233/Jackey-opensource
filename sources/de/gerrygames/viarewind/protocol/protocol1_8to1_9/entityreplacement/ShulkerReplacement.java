package de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/entityreplacement/ShulkerReplacement.class */
public class ShulkerReplacement implements EntityReplacement {
    private int entityId;
    private List<Metadata> datawatcher = new ArrayList();
    private double locX;
    private double locY;
    private double locZ;
    private UserConnection user;

    public ShulkerReplacement(int entityId, UserConnection user) {
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
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void setHeadYaw(float yaw) {
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
        teleport.write(Type.VAR_INT, Integer.valueOf(this.entityId));
        teleport.write(Type.INT, Integer.valueOf((int) (this.locX * 32.0d)));
        teleport.write(Type.INT, Integer.valueOf((int) (this.locY * 32.0d)));
        teleport.write(Type.INT, Integer.valueOf((int) (this.locZ * 32.0d)));
        teleport.write(Type.BYTE, (byte) 0);
        teleport.write(Type.BYTE, (byte) 0);
        teleport.write(Type.BOOLEAN, true);
        PacketUtil.sendPacket(teleport, Protocol1_8TO1_9.class, true, true);
    }

    public void updateMetadata() {
        PacketWrapper metadataPacket = PacketWrapper.create(28, (ByteBuf) null, this.user);
        metadataPacket.write(Type.VAR_INT, Integer.valueOf(this.entityId));
        ArrayList arrayList = new ArrayList();
        for (Metadata metadata : this.datawatcher) {
            if (metadata.m222id() != 11 && metadata.m222id() != 12 && metadata.m222id() != 13) {
                arrayList.add(new Metadata(metadata.m222id(), metadata.metaType(), metadata.getValue()));
            }
        }
        arrayList.add(new Metadata(11, MetaType1_9.VarInt, 2));
        MetadataRewriter.transform(Entity1_10Types.EntityType.MAGMA_CUBE, arrayList);
        metadataPacket.write(Types1_8.METADATA_LIST, arrayList);
        PacketUtil.sendPacket(metadataPacket, Protocol1_8TO1_9.class);
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void spawn() {
        PacketWrapper spawn = PacketWrapper.create(15, (ByteBuf) null, this.user);
        spawn.write(Type.VAR_INT, Integer.valueOf(this.entityId));
        spawn.write(Type.UNSIGNED_BYTE, (short) 62);
        spawn.write(Type.INT, 0);
        spawn.write(Type.INT, 0);
        spawn.write(Type.INT, 0);
        spawn.write(Type.BYTE, (byte) 0);
        spawn.write(Type.BYTE, (byte) 0);
        spawn.write(Type.BYTE, (byte) 0);
        spawn.write(Type.SHORT, (short) 0);
        spawn.write(Type.SHORT, (short) 0);
        spawn.write(Type.SHORT, (short) 0);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Metadata(0, MetaType1_9.Byte, (byte) 0));
        spawn.write(Types1_8.METADATA_LIST, arrayList);
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
