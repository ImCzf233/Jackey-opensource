package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/EntityTracker.class */
public class EntityTracker extends StoredObject implements ClientEntityIdChangeListener {
    private final Map<Integer, Entity1_10Types.EntityType> clientEntityTypes = new ConcurrentHashMap();
    private final Map<Integer, List<Metadata>> metadataBuffer = new ConcurrentHashMap();
    private final Map<Integer, Integer> vehicles = new ConcurrentHashMap();
    private final Map<Integer, EntityReplacement> entityReplacements = new ConcurrentHashMap();
    private final Map<Integer, UUID> playersByEntityId = new HashMap();
    private final Map<UUID, Integer> playersByUniqueId = new HashMap();
    private final Map<UUID, Item[]> playerEquipment = new HashMap();
    private int gamemode = 0;
    private int playerId = -1;
    private int spectating = -1;
    private int dimension = 0;

    public EntityTracker(UserConnection user) {
        super(user);
    }

    public void removeEntity(int entityId) {
        this.clientEntityTypes.remove(Integer.valueOf(entityId));
        if (this.entityReplacements.containsKey(Integer.valueOf(entityId))) {
            this.entityReplacements.remove(Integer.valueOf(entityId)).despawn();
        }
        if (this.playersByEntityId.containsKey(Integer.valueOf(entityId))) {
            this.playersByUniqueId.remove(this.playersByEntityId.remove(Integer.valueOf(entityId)));
        }
    }

    public void addPlayer(Integer entityId, UUID uuid) {
        this.playersByUniqueId.put(uuid, entityId);
        this.playersByEntityId.put(entityId, uuid);
    }

    public UUID getPlayerUUID(int entityId) {
        return this.playersByEntityId.get(Integer.valueOf(entityId));
    }

    public int getPlayerEntityId(UUID uuid) {
        return this.playersByUniqueId.getOrDefault(uuid, -1).intValue();
    }

    public Item[] getPlayerEquipment(UUID uuid) {
        return this.playerEquipment.get(uuid);
    }

    public void setPlayerEquipment(UUID uuid, Item[] equipment) {
        this.playerEquipment.put(uuid, equipment);
    }

    public Map<Integer, Entity1_10Types.EntityType> getClientEntityTypes() {
        return this.clientEntityTypes;
    }

    public void addMetadataToBuffer(int entityID, List<Metadata> metadataList) {
        if (this.metadataBuffer.containsKey(Integer.valueOf(entityID))) {
            this.metadataBuffer.get(Integer.valueOf(entityID)).addAll(metadataList);
        } else if (!metadataList.isEmpty()) {
            this.metadataBuffer.put(Integer.valueOf(entityID), metadataList);
        }
    }

    public void addEntityReplacement(EntityReplacement entityReplacement) {
        this.entityReplacements.put(Integer.valueOf(entityReplacement.getEntityId()), entityReplacement);
    }

    public EntityReplacement getEntityReplacement(int entityId) {
        return this.entityReplacements.get(Integer.valueOf(entityId));
    }

    public List<Metadata> getBufferedMetadata(int entityId) {
        return this.metadataBuffer.get(Integer.valueOf(entityId));
    }

    public void sendMetadataBuffer(int entityId) {
        if (!this.metadataBuffer.containsKey(Integer.valueOf(entityId))) {
            return;
        }
        if (this.entityReplacements.containsKey(Integer.valueOf(entityId))) {
            this.entityReplacements.get(Integer.valueOf(entityId)).updateMetadata(this.metadataBuffer.remove(Integer.valueOf(entityId)));
            return;
        }
        Entity1_10Types.EntityType type = getClientEntityTypes().get(Integer.valueOf(entityId));
        PacketWrapper wrapper = PacketWrapper.create(28, (ByteBuf) null, getUser());
        wrapper.write(Type.VAR_INT, Integer.valueOf(entityId));
        wrapper.write(Types1_8.METADATA_LIST, this.metadataBuffer.get(Integer.valueOf(entityId)));
        MetadataRewriter.transform(type, this.metadataBuffer.get(Integer.valueOf(entityId)));
        if (!this.metadataBuffer.get(Integer.valueOf(entityId)).isEmpty()) {
            PacketUtil.sendPacket(wrapper, Protocol1_7_6_10TO1_8.class);
        }
        this.metadataBuffer.remove(Integer.valueOf(entityId));
    }

    public int getVehicle(int passengerId) {
        for (Map.Entry<Integer, Integer> vehicle : this.vehicles.entrySet()) {
            if (vehicle.getValue().intValue() == passengerId) {
                return vehicle.getValue().intValue();
            }
        }
        return -1;
    }

    public int getPassenger(int vehicleId) {
        return this.vehicles.getOrDefault(Integer.valueOf(vehicleId), -1).intValue();
    }

    public void setPassenger(int vehicleId, int passengerId) {
        if (vehicleId == this.spectating && this.spectating != this.playerId) {
            try {
                PacketWrapper sneakPacket = PacketWrapper.create(11, (ByteBuf) null, getUser());
                sneakPacket.write(Type.VAR_INT, Integer.valueOf(this.playerId));
                sneakPacket.write(Type.VAR_INT, 0);
                sneakPacket.write(Type.VAR_INT, 0);
                PacketWrapper unsneakPacket = PacketWrapper.create(11, (ByteBuf) null, getUser());
                unsneakPacket.write(Type.VAR_INT, Integer.valueOf(this.playerId));
                unsneakPacket.write(Type.VAR_INT, 1);
                unsneakPacket.write(Type.VAR_INT, 0);
                PacketUtil.sendToServer(sneakPacket, Protocol1_7_6_10TO1_8.class, true, true);
                setSpectating(this.playerId);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (vehicleId == -1) {
            int oldVehicleId = getVehicle(passengerId);
            this.vehicles.remove(Integer.valueOf(oldVehicleId));
        } else if (passengerId == -1) {
            this.vehicles.remove(Integer.valueOf(vehicleId));
        } else {
            this.vehicles.put(Integer.valueOf(vehicleId), Integer.valueOf(passengerId));
        }
    }

    public int getSpectating() {
        return this.spectating;
    }

    public boolean setSpectating(int spectating) {
        if (spectating != this.playerId && getPassenger(spectating) != -1) {
            PacketWrapper sneakPacket = PacketWrapper.create(11, (ByteBuf) null, getUser());
            sneakPacket.write(Type.VAR_INT, Integer.valueOf(this.playerId));
            sneakPacket.write(Type.VAR_INT, 0);
            sneakPacket.write(Type.VAR_INT, 0);
            PacketWrapper unsneakPacket = PacketWrapper.create(11, (ByteBuf) null, getUser());
            unsneakPacket.write(Type.VAR_INT, Integer.valueOf(this.playerId));
            unsneakPacket.write(Type.VAR_INT, 1);
            unsneakPacket.write(Type.VAR_INT, 0);
            PacketUtil.sendToServer(sneakPacket, Protocol1_7_6_10TO1_8.class, true, true);
            setSpectating(this.playerId);
            return false;
        }
        if (this.spectating != spectating && this.spectating != this.playerId) {
            PacketWrapper unmount = PacketWrapper.create(27, (ByteBuf) null, getUser());
            unmount.write(Type.INT, Integer.valueOf(this.playerId));
            unmount.write(Type.INT, -1);
            unmount.write(Type.BOOLEAN, false);
            PacketUtil.sendPacket(unmount, Protocol1_7_6_10TO1_8.class);
        }
        this.spectating = spectating;
        if (spectating != this.playerId) {
            PacketWrapper mount = PacketWrapper.create(27, (ByteBuf) null, getUser());
            mount.write(Type.INT, Integer.valueOf(this.playerId));
            mount.write(Type.INT, Integer.valueOf(this.spectating));
            mount.write(Type.BOOLEAN, false);
            PacketUtil.sendPacket(mount, Protocol1_7_6_10TO1_8.class);
            return true;
        }
        return true;
    }

    public int getGamemode() {
        return this.gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(int playerId) {
        this.spectating = playerId;
        this.playerId = playerId;
    }

    public void clearEntities() {
        this.clientEntityTypes.clear();
        this.entityReplacements.clear();
        this.vehicles.clear();
        this.metadataBuffer.clear();
    }

    public int getDimension() {
        return this.dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    @Override // com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener
    public void setClientEntityId(int playerEntityId) {
        if (this.spectating == this.playerId) {
            this.spectating = playerEntityId;
        }
        this.clientEntityTypes.remove(Integer.valueOf(this.playerId));
        this.playerId = playerEntityId;
        this.clientEntityTypes.put(Integer.valueOf(this.playerId), Entity1_10Types.EntityType.ENTITY_HUMAN);
    }
}
