package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.MetaType1_7_6_10;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import de.gerrygames.viarewind.utils.math.AABB;
import de.gerrygames.viarewind.utils.math.Vector3d;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/entityreplacements/ArmorStandReplacement.class */
public class ArmorStandReplacement implements EntityReplacement {
    private int entityId;
    private double locX;
    private double locY;
    private double locZ;
    private UserConnection user;
    private float yaw;
    private float pitch;
    private float headYaw;
    private static int ENTITY_ID = 2147467647;
    private List<Metadata> datawatcher = new ArrayList();
    private int[] entityIds = null;
    private State currentState = null;
    private boolean invisible = false;
    private boolean nameTagVisible = false;
    private String name = null;
    private boolean small = false;
    private boolean marker = false;

    /* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/entityreplacements/ArmorStandReplacement$State.class */
    public enum State {
        HOLOGRAM,
        ZOMBIE
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public int getEntityId() {
        return this.entityId;
    }

    public ArmorStandReplacement(int entityId, UserConnection user) {
        this.entityId = entityId;
        this.user = user;
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
        if ((this.yaw != yaw && this.pitch != pitch) || this.headYaw != yaw) {
            this.yaw = yaw;
            this.headYaw = yaw;
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
        updateState();
    }

    public void updateState() {
        byte flags = 0;
        byte armorStandFlags = 0;
        for (Metadata metadata : this.datawatcher) {
            if (metadata.m222id() == 0 && metadata.metaType() == MetaType1_8.Byte) {
                flags = ((Byte) metadata.getValue()).byteValue();
            } else if (metadata.m222id() == 2 && metadata.metaType() == MetaType1_8.String) {
                this.name = (String) metadata.getValue();
                if (this.name != null && this.name.equals("")) {
                    this.name = null;
                }
            } else if (metadata.m222id() == 10 && metadata.metaType() == MetaType1_8.Byte) {
                armorStandFlags = ((Byte) metadata.getValue()).byteValue();
            } else if (metadata.m222id() == 3 && metadata.metaType() == MetaType1_8.Byte) {
                this.nameTagVisible = ((byte) metadata.m222id()) != 0;
            }
        }
        this.invisible = (flags & 32) != 0;
        this.small = (armorStandFlags & 1) != 0;
        this.marker = (armorStandFlags & 16) != 0;
        State prevState = this.currentState;
        if (this.invisible && this.name != null) {
            this.currentState = State.HOLOGRAM;
        } else {
            this.currentState = State.ZOMBIE;
        }
        if (this.currentState != prevState) {
            despawn();
            spawn();
            return;
        }
        updateMetadata();
        updateLocation();
    }

    public void updateLocation() {
        if (this.entityIds == null) {
            return;
        }
        if (this.currentState == State.ZOMBIE) {
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
        } else if (this.currentState == State.HOLOGRAM) {
            PacketWrapper detach = PacketWrapper.create(27, (ByteBuf) null, this.user);
            detach.write(Type.INT, Integer.valueOf(this.entityIds[1]));
            detach.write(Type.INT, -1);
            detach.write(Type.BOOLEAN, false);
            PacketWrapper teleportSkull = PacketWrapper.create(24, (ByteBuf) null, this.user);
            teleportSkull.write(Type.INT, Integer.valueOf(this.entityIds[0]));
            teleportSkull.write(Type.INT, Integer.valueOf((int) (this.locX * 32.0d)));
            teleportSkull.write(Type.INT, Integer.valueOf((int) ((this.locY + (this.marker ? 54.85d : this.small ? 56.0d : 57.0d)) * 32.0d)));
            teleportSkull.write(Type.INT, Integer.valueOf((int) (this.locZ * 32.0d)));
            teleportSkull.write(Type.BYTE, (byte) 0);
            teleportSkull.write(Type.BYTE, (byte) 0);
            PacketWrapper teleportHorse = PacketWrapper.create(24, (ByteBuf) null, this.user);
            teleportHorse.write(Type.INT, Integer.valueOf(this.entityIds[1]));
            teleportHorse.write(Type.INT, Integer.valueOf((int) (this.locX * 32.0d)));
            teleportHorse.write(Type.INT, Integer.valueOf((int) ((this.locY + 56.75d) * 32.0d)));
            teleportHorse.write(Type.INT, Integer.valueOf((int) (this.locZ * 32.0d)));
            teleportHorse.write(Type.BYTE, (byte) 0);
            teleportHorse.write(Type.BYTE, (byte) 0);
            PacketWrapper attach = PacketWrapper.create(27, (ByteBuf) null, this.user);
            attach.write(Type.INT, Integer.valueOf(this.entityIds[1]));
            attach.write(Type.INT, Integer.valueOf(this.entityIds[0]));
            attach.write(Type.BOOLEAN, false);
            PacketUtil.sendPacket(detach, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(teleportSkull, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(teleportHorse, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(attach, Protocol1_7_6_10TO1_8.class, true, true);
        }
    }

    public void updateMetadata() {
        if (this.entityIds == null) {
            return;
        }
        PacketWrapper metadataPacket = PacketWrapper.create(28, (ByteBuf) null, this.user);
        if (this.currentState == State.ZOMBIE) {
            metadataPacket.write(Type.INT, Integer.valueOf(this.entityIds[0]));
            ArrayList arrayList = new ArrayList();
            for (Metadata metadata : this.datawatcher) {
                if (metadata.m222id() >= 0 && metadata.m222id() <= 9) {
                    arrayList.add(new Metadata(metadata.m222id(), metadata.metaType(), metadata.getValue()));
                }
            }
            if (this.small) {
                arrayList.add(new Metadata(12, MetaType1_8.Byte, (byte) 1));
            }
            MetadataRewriter.transform(Entity1_10Types.EntityType.ZOMBIE, arrayList);
            metadataPacket.write(Types1_7_6_10.METADATA_LIST, arrayList);
        } else if (this.currentState == State.HOLOGRAM) {
            metadataPacket.write(Type.INT, Integer.valueOf(this.entityIds[1]));
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new Metadata(12, MetaType1_7_6_10.Int, -1700000));
            arrayList2.add(new Metadata(10, MetaType1_7_6_10.String, this.name));
            arrayList2.add(new Metadata(11, MetaType1_7_6_10.Byte, (byte) 1));
            metadataPacket.write(Types1_7_6_10.METADATA_LIST, arrayList2);
        } else {
            return;
        }
        PacketUtil.sendPacket(metadataPacket, Protocol1_7_6_10TO1_8.class);
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void spawn() {
        if (this.entityIds != null) {
            despawn();
        }
        if (this.currentState == State.ZOMBIE) {
            PacketWrapper spawn = PacketWrapper.create(15, (ByteBuf) null, this.user);
            spawn.write(Type.VAR_INT, Integer.valueOf(this.entityId));
            spawn.write(Type.UNSIGNED_BYTE, (short) 54);
            spawn.write(Type.INT, Integer.valueOf((int) (this.locX * 32.0d)));
            spawn.write(Type.INT, Integer.valueOf((int) (this.locY * 32.0d)));
            spawn.write(Type.INT, Integer.valueOf((int) (this.locZ * 32.0d)));
            spawn.write(Type.BYTE, (byte) 0);
            spawn.write(Type.BYTE, (byte) 0);
            spawn.write(Type.BYTE, (byte) 0);
            spawn.write(Type.SHORT, (short) 0);
            spawn.write(Type.SHORT, (short) 0);
            spawn.write(Type.SHORT, (short) 0);
            spawn.write(Types1_7_6_10.METADATA_LIST, new ArrayList());
            PacketUtil.sendPacket(spawn, Protocol1_7_6_10TO1_8.class, true, true);
            this.entityIds = new int[]{this.entityId};
        } else if (this.currentState == State.HOLOGRAM) {
            int i = ENTITY_ID;
            ENTITY_ID = i - 1;
            int[] entityIds = {this.entityId, i};
            PacketWrapper spawnSkull = PacketWrapper.create(14, (ByteBuf) null, this.user);
            spawnSkull.write(Type.VAR_INT, Integer.valueOf(entityIds[0]));
            spawnSkull.write(Type.BYTE, (byte) 66);
            spawnSkull.write(Type.INT, Integer.valueOf((int) (this.locX * 32.0d)));
            spawnSkull.write(Type.INT, Integer.valueOf((int) (this.locY * 32.0d)));
            spawnSkull.write(Type.INT, Integer.valueOf((int) (this.locZ * 32.0d)));
            spawnSkull.write(Type.BYTE, (byte) 0);
            spawnSkull.write(Type.BYTE, (byte) 0);
            spawnSkull.write(Type.INT, 0);
            PacketWrapper spawnHorse = PacketWrapper.create(15, (ByteBuf) null, this.user);
            spawnHorse.write(Type.VAR_INT, Integer.valueOf(entityIds[1]));
            spawnHorse.write(Type.UNSIGNED_BYTE, (short) 100);
            spawnHorse.write(Type.INT, Integer.valueOf((int) (this.locX * 32.0d)));
            spawnHorse.write(Type.INT, Integer.valueOf((int) (this.locY * 32.0d)));
            spawnHorse.write(Type.INT, Integer.valueOf((int) (this.locZ * 32.0d)));
            spawnHorse.write(Type.BYTE, (byte) 0);
            spawnHorse.write(Type.BYTE, (byte) 0);
            spawnHorse.write(Type.BYTE, (byte) 0);
            spawnHorse.write(Type.SHORT, (short) 0);
            spawnHorse.write(Type.SHORT, (short) 0);
            spawnHorse.write(Type.SHORT, (short) 0);
            spawnHorse.write(Types1_7_6_10.METADATA_LIST, new ArrayList());
            PacketUtil.sendPacket(spawnSkull, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(spawnHorse, Protocol1_7_6_10TO1_8.class, true, true);
            this.entityIds = entityIds;
        }
        updateMetadata();
        updateLocation();
    }

    public AABB getBoundingBox() {
        double w = this.small ? 0.25d : 0.5d;
        double h = this.small ? 0.9875d : 1.975d;
        Vector3d min = new Vector3d(this.locX - (w / 2.0d), this.locY, this.locZ - (w / 2.0d));
        Vector3d max = new Vector3d(this.locX + (w / 2.0d), this.locY + h, this.locZ + (w / 2.0d));
        return new AABB(min, max);
    }

    @Override // de.gerrygames.viarewind.replacement.EntityReplacement
    public void despawn() {
        int[] iArr;
        if (this.entityIds == null) {
            return;
        }
        PacketWrapper despawn = PacketWrapper.create(19, (ByteBuf) null, this.user);
        despawn.write(Type.BYTE, Byte.valueOf((byte) this.entityIds.length));
        for (int id : this.entityIds) {
            despawn.write(Type.INT, Integer.valueOf(id));
        }
        this.entityIds = null;
        PacketUtil.sendPacket(despawn, Protocol1_7_6_10TO1_8.class, true, true);
    }
}
