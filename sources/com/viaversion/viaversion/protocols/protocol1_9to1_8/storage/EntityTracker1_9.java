package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.google.common.cache.CacheBuilder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.GameMode;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/storage/EntityTracker1_9.class */
public class EntityTracker1_9 extends EntityTrackerBase {
    public static final String WITHER_TRANSLATABLE = "{\"translate\":\"entity.WitherBoss.name\"}";
    public static final String DRAGON_TRANSLATABLE = "{\"translate\":\"entity.EnderDragon.name\"}";
    private GameMode gameMode;
    private String currentTeam;
    private int heldItemSlot;
    private final Int2ObjectMap<UUID> uuidMap = Int2ObjectSyncMap.hashmap();
    private final Int2ObjectMap<List<Metadata>> metadataBuffer = Int2ObjectSyncMap.hashmap();
    private final Int2ObjectMap<Integer> vehicleMap = Int2ObjectSyncMap.hashmap();
    private final Int2ObjectMap<BossBar> bossBarMap = Int2ObjectSyncMap.hashmap();
    private final IntSet validBlocking = Int2ObjectSyncMap.hashset();
    private final Set<Integer> knownHolograms = Int2ObjectSyncMap.hashset();
    private final Set<Position> blockInteractions = Collections.newSetFromMap(CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(250, TimeUnit.MILLISECONDS).build().asMap());
    private boolean blocking = false;
    private boolean autoTeam = false;
    private Position currentlyDigging = null;
    private boolean teamExists = false;
    private Item itemInSecondHand = null;

    public EntityTracker1_9(UserConnection user) {
        super(user, Entity1_10Types.EntityType.PLAYER);
    }

    public UUID getEntityUUID(int id) {
        UUID uuid = this.uuidMap.get(id);
        if (uuid == null) {
            uuid = UUID.randomUUID();
            this.uuidMap.put(id, (int) uuid);
        }
        return uuid;
    }

    public void setSecondHand(Item item) {
        setSecondHand(clientEntityId(), item);
    }

    public void setSecondHand(int entityID, Item item) {
        PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_EQUIPMENT, (ByteBuf) null, user());
        wrapper.write(Type.VAR_INT, Integer.valueOf(entityID));
        wrapper.write(Type.VAR_INT, 1);
        Type<Item> type = Type.ITEM;
        this.itemInSecondHand = item;
        wrapper.write(type, item);
        try {
            wrapper.scheduleSend(Protocol1_9To1_8.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Item getItemInSecondHand() {
        return this.itemInSecondHand;
    }

    public void syncShieldWithSword() {
        boolean swordInHand = hasSwordInHand();
        if (!swordInHand || this.itemInSecondHand == null) {
            setSecondHand(swordInHand ? new DataItem(442, (byte) 1, (short) 0, null) : null);
        }
    }

    public boolean hasSwordInHand() {
        InventoryTracker inventoryTracker = (InventoryTracker) user().get(InventoryTracker.class);
        int inventorySlot = this.heldItemSlot + 36;
        int itemIdentifier = inventoryTracker.getItemId((short) 0, (short) inventorySlot);
        return Protocol1_9To1_8.isSword(itemIdentifier);
    }

    @Override // com.viaversion.viaversion.data.entity.EntityTrackerBase, com.viaversion.viaversion.api.data.entity.EntityTracker
    public void removeEntity(int entityId) {
        super.removeEntity(entityId);
        this.vehicleMap.remove(entityId);
        this.uuidMap.remove(entityId);
        this.validBlocking.remove(entityId);
        this.knownHolograms.remove(Integer.valueOf(entityId));
        this.metadataBuffer.remove(entityId);
        BossBar bar = this.bossBarMap.remove(entityId);
        if (bar != null) {
            bar.hide();
            ((BossBarProvider) Via.getManager().getProviders().get(BossBarProvider.class)).handleRemove(user(), bar.getId());
        }
    }

    public boolean interactedBlockRecently(int x, int y, int z) {
        return this.blockInteractions.contains(new Position(x, (short) y, z));
    }

    public void addBlockInteraction(Position p) {
        this.blockInteractions.add(p);
    }

    public void handleMetadata(int entityId, List<Metadata> metadataList) {
        Metadata displayName;
        Metadata displayNameVisible;
        EntityType type = entityType(entityId);
        if (type == null) {
            return;
        }
        Iterator it = new ArrayList(metadataList).iterator();
        while (it.hasNext()) {
            Metadata metadata = (Metadata) it.next();
            if (type == Entity1_10Types.EntityType.WITHER && metadata.m222id() == 10) {
                metadataList.remove(metadata);
            }
            if (type == Entity1_10Types.EntityType.ENDER_DRAGON && metadata.m222id() == 11) {
                metadataList.remove(metadata);
            }
            if (type == Entity1_10Types.EntityType.SKELETON && getMetaByIndex(metadataList, 12) == null) {
                metadataList.add(new Metadata(12, MetaType1_9.Boolean, true));
            }
            if (type == Entity1_10Types.EntityType.HORSE && metadata.m222id() == 16 && ((Integer) metadata.getValue()).intValue() == Integer.MIN_VALUE) {
                metadata.setValue(0);
            }
            if (type == Entity1_10Types.EntityType.PLAYER) {
                if (metadata.m222id() == 0) {
                    byte data = ((Byte) metadata.getValue()).byteValue();
                    if (entityId != getProvidedEntityId() && Via.getConfig().isShieldBlocking()) {
                        if ((data & 16) == 16) {
                            if (this.validBlocking.contains(entityId)) {
                                Item shield = new DataItem(442, (byte) 1, (short) 0, null);
                                setSecondHand(entityId, shield);
                            } else {
                                setSecondHand(entityId, null);
                            }
                        } else {
                            setSecondHand(entityId, null);
                        }
                    }
                }
                if (metadata.m222id() == 12 && Via.getConfig().isLeftHandedHandling()) {
                    metadataList.add(new Metadata(13, MetaType1_9.Byte, Byte.valueOf((byte) ((((Byte) metadata.getValue()).byteValue() & 128) != 0 ? 0 : 1))));
                }
            }
            if (type == Entity1_10Types.EntityType.ARMOR_STAND && Via.getConfig().isHologramPatch() && metadata.m222id() == 0 && getMetaByIndex(metadataList, 10) != null) {
                Metadata meta = getMetaByIndex(metadataList, 10);
                byte data2 = ((Byte) metadata.getValue()).byteValue();
                if ((data2 & 32) == 32 && (((Byte) meta.getValue()).byteValue() & 1) == 1 && (displayName = getMetaByIndex(metadataList, 2)) != null && !((String) displayName.getValue()).isEmpty() && (displayNameVisible = getMetaByIndex(metadataList, 3)) != null && ((Boolean) displayNameVisible.getValue()).booleanValue() && !this.knownHolograms.contains(Integer.valueOf(entityId))) {
                    this.knownHolograms.add(Integer.valueOf(entityId));
                    try {
                        PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_POSITION, (ByteBuf) null, user());
                        wrapper.write(Type.VAR_INT, Integer.valueOf(entityId));
                        wrapper.write(Type.SHORT, (short) 0);
                        wrapper.write(Type.SHORT, Short.valueOf((short) (128.0d * Via.getConfig().getHologramYOffset() * 32.0d)));
                        wrapper.write(Type.SHORT, (short) 0);
                        wrapper.write(Type.BOOLEAN, true);
                        wrapper.scheduleSend(Protocol1_9To1_8.class);
                    } catch (Exception e) {
                    }
                }
            }
            if (Via.getConfig().isBossbarPatch() && (type == Entity1_10Types.EntityType.ENDER_DRAGON || type == Entity1_10Types.EntityType.WITHER)) {
                if (metadata.m222id() == 2) {
                    BossBar bar = this.bossBarMap.get(entityId);
                    String title = (String) metadata.getValue();
                    String title2 = title.isEmpty() ? type == Entity1_10Types.EntityType.ENDER_DRAGON ? DRAGON_TRANSLATABLE : WITHER_TRANSLATABLE : title;
                    if (bar == null) {
                        BossBar bar2 = Via.getAPI().legacyAPI().createLegacyBossBar(title2, BossColor.PINK, BossStyle.SOLID);
                        this.bossBarMap.put(entityId, (int) bar2);
                        bar2.addConnection(user());
                        bar2.show();
                        ((BossBarProvider) Via.getManager().getProviders().get(BossBarProvider.class)).handleAdd(user(), bar2.getId());
                    } else {
                        bar.setTitle(title2);
                    }
                } else if (metadata.m222id() == 6 && !Via.getConfig().isBossbarAntiflicker()) {
                    BossBar bar3 = this.bossBarMap.get(entityId);
                    float maxHealth = type == Entity1_10Types.EntityType.ENDER_DRAGON ? 200.0f : 300.0f;
                    float health = Math.max(0.0f, Math.min(((Float) metadata.getValue()).floatValue() / maxHealth, 1.0f));
                    if (bar3 == null) {
                        BossBar bar4 = Via.getAPI().legacyAPI().createLegacyBossBar(type == Entity1_10Types.EntityType.ENDER_DRAGON ? DRAGON_TRANSLATABLE : WITHER_TRANSLATABLE, health, BossColor.PINK, BossStyle.SOLID);
                        this.bossBarMap.put(entityId, (int) bar4);
                        bar4.addConnection(user());
                        bar4.show();
                        ((BossBarProvider) Via.getManager().getProviders().get(BossBarProvider.class)).handleAdd(user(), bar4.getId());
                    } else {
                        bar3.setHealth(health);
                    }
                }
            }
        }
    }

    public Metadata getMetaByIndex(List<Metadata> list, int index) {
        for (Metadata meta : list) {
            if (index == meta.m222id()) {
                return meta;
            }
        }
        return null;
    }

    public void sendTeamPacket(boolean add, boolean now) {
        PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.TEAMS, (ByteBuf) null, user());
        wrapper.write(Type.STRING, "viaversion");
        if (add) {
            if (!this.teamExists) {
                wrapper.write(Type.BYTE, (byte) 0);
                wrapper.write(Type.STRING, "viaversion");
                wrapper.write(Type.STRING, "§f");
                wrapper.write(Type.STRING, "");
                wrapper.write(Type.BYTE, (byte) 0);
                wrapper.write(Type.STRING, "");
                wrapper.write(Type.STRING, "never");
                wrapper.write(Type.BYTE, (byte) 15);
            } else {
                wrapper.write(Type.BYTE, (byte) 3);
            }
            wrapper.write(Type.STRING_ARRAY, new String[]{user().getProtocolInfo().getUsername()});
        } else {
            wrapper.write(Type.BYTE, (byte) 1);
        }
        this.teamExists = add;
        try {
            if (now) {
                wrapper.send(Protocol1_9To1_8.class);
            } else {
                wrapper.scheduleSend(Protocol1_9To1_8.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMetadataToBuffer(int entityID, List<Metadata> metadataList) {
        List<Metadata> metadata = this.metadataBuffer.get(entityID);
        if (metadata != null) {
            metadata.addAll(metadataList);
        } else {
            this.metadataBuffer.put(entityID, (int) metadataList);
        }
    }

    public void sendMetadataBuffer(int entityId) {
        List<Metadata> metadataList = this.metadataBuffer.get(entityId);
        if (metadataList != null) {
            PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_METADATA, (ByteBuf) null, user());
            wrapper.write(Type.VAR_INT, Integer.valueOf(entityId));
            wrapper.write(Types1_9.METADATA_LIST, metadataList);
            ((MetadataRewriter1_9To1_8) ((Protocol1_9To1_8) Via.getManager().getProtocolManager().getProtocol(Protocol1_9To1_8.class)).get(MetadataRewriter1_9To1_8.class)).handleMetadata(entityId, metadataList, user());
            handleMetadata(entityId, metadataList);
            if (!metadataList.isEmpty()) {
                try {
                    wrapper.scheduleSend(Protocol1_9To1_8.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.metadataBuffer.remove(entityId);
        }
    }

    public int getProvidedEntityId() {
        try {
            return ((EntityIdProvider) Via.getManager().getProviders().get(EntityIdProvider.class)).getEntityId(user());
        } catch (Exception e) {
            return clientEntityId();
        }
    }

    public Map<Integer, UUID> getUuidMap() {
        return this.uuidMap;
    }

    public Map<Integer, List<Metadata>> getMetadataBuffer() {
        return this.metadataBuffer;
    }

    public Map<Integer, Integer> getVehicleMap() {
        return this.vehicleMap;
    }

    public Map<Integer, BossBar> getBossBarMap() {
        return this.bossBarMap;
    }

    public Set<Integer> getValidBlocking() {
        return this.validBlocking;
    }

    public Set<Integer> getKnownHolograms() {
        return this.knownHolograms;
    }

    public Set<Position> getBlockInteractions() {
        return this.blockInteractions;
    }

    public boolean isBlocking() {
        return this.blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public boolean isAutoTeam() {
        return this.autoTeam;
    }

    public void setAutoTeam(boolean autoTeam) {
        this.autoTeam = autoTeam;
    }

    public Position getCurrentlyDigging() {
        return this.currentlyDigging;
    }

    public void setCurrentlyDigging(Position currentlyDigging) {
        this.currentlyDigging = currentlyDigging;
    }

    public boolean isTeamExists() {
        return this.teamExists;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public String getCurrentTeam() {
        return this.currentTeam;
    }

    public void setCurrentTeam(String currentTeam) {
        this.currentTeam = currentTeam;
    }

    public void setHeldItemSlot(int heldItemSlot) {
        this.heldItemSlot = heldItemSlot;
    }
}
