package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.rewriter.meta.MetaFilter;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEventImpl;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/EntityRewriter.class */
public abstract class EntityRewriter<T extends Protocol> extends RewriterBase<T> implements com.viaversion.viaversion.api.rewriter.EntityRewriter<T> {
    private static final Metadata[] EMPTY_ARRAY = new Metadata[0];
    protected final List<MetaFilter> metadataFilters;
    protected final boolean trackMappedType;
    protected Int2IntMap typeMappings;

    public EntityRewriter(T protocol) {
        this(protocol, true);
    }

    public EntityRewriter(T protocol, boolean trackMappedType) {
        super(protocol);
        this.metadataFilters = new ArrayList();
        this.trackMappedType = trackMappedType;
        protocol.put(this);
    }

    public MetaFilter.Builder filter() {
        return new MetaFilter.Builder(this);
    }

    public void registerFilter(MetaFilter filter) {
        Preconditions.checkArgument(!this.metadataFilters.contains(filter));
        this.metadataFilters.add(filter);
    }

    public void handleMetadata(int entityId, List<Metadata> metadataList, UserConnection connection) {
        Metadata[] metadataArr;
        EntityType type = tracker(connection).entityType(entityId);
        int i = 0;
        for (Metadata metadata : (Metadata[]) metadataList.toArray(EMPTY_ARRAY)) {
            if (!callOldMetaHandler(entityId, type, metadata, metadataList, connection)) {
                int i2 = i;
                i--;
                metadataList.remove(i2);
            } else {
                MetaHandlerEvent event = null;
                Iterator<MetaFilter> it = this.metadataFilters.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    MetaFilter filter = it.next();
                    if (filter.isFiltered(type, metadata)) {
                        if (event == null) {
                            event = new MetaHandlerEventImpl(connection, type, entityId, metadata, metadataList);
                        }
                        try {
                            filter.handler().handle(event, metadata);
                            if (event.cancelled()) {
                                int i3 = i;
                                i--;
                                metadataList.remove(i3);
                                break;
                            }
                        } catch (Exception e) {
                            logException(e, type, metadataList, metadata);
                            int i4 = i;
                            i--;
                            metadataList.remove(i4);
                        }
                    }
                }
                if (event != null && event.extraMeta() != null) {
                    metadataList.addAll(event.extraMeta());
                }
                i++;
            }
        }
    }

    @Deprecated
    private boolean callOldMetaHandler(int entityId, EntityType type, Metadata metadata, List<Metadata> metadataList, UserConnection connection) {
        try {
            handleMetadata(entityId, type, metadata, metadataList, connection);
            return true;
        } catch (Exception e) {
            logException(e, type, metadataList, metadata);
            return false;
        }
    }

    @Deprecated
    protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
    }

    public int newEntityId(int id) {
        return this.typeMappings != null ? this.typeMappings.getOrDefault(id, id) : id;
    }

    public void mapEntityType(EntityType type, EntityType mappedType) {
        Preconditions.checkArgument(type.getClass() != mappedType.getClass(), "EntityTypes should not be of the same class/enum");
        mapEntityType(type.getId(), mappedType.getId());
    }

    public void mapEntityType(int id, int mappedId) {
        if (this.typeMappings == null) {
            this.typeMappings = new Int2IntOpenHashMap();
            this.typeMappings.defaultReturnValue(-1);
        }
        this.typeMappings.put(id, mappedId);
    }

    public <E extends Enum<E> & EntityType> void mapTypes(EntityType[] oldTypes, Class<E> newTypeClass) {
        if (this.typeMappings == null) {
            this.typeMappings = new Int2IntOpenHashMap(oldTypes.length, 0.99f);
            this.typeMappings.defaultReturnValue(-1);
        }
        for (EntityType oldType : oldTypes) {
            try {
                this.typeMappings.put(oldType.getId(), ((EntityType) Enum.valueOf(newTypeClass, oldType.name())).getId());
            } catch (IllegalArgumentException e) {
                if (!this.typeMappings.containsKey(oldType.getId())) {
                    Via.getPlatform().getLogger().warning("Could not find new entity type for " + oldType + "! Old type: " + oldType.getClass().getEnclosingClass().getSimpleName() + ", new type: " + newTypeClass.getEnclosingClass().getSimpleName());
                }
            }
        }
    }

    public void registerMetaTypeHandler(MetaType itemType, MetaType blockType, MetaType particleType) {
        filter().handler(event, meta -> {
            if (itemType != null && meta.metaType() == itemType) {
                this.protocol.getItemRewriter().handleItemToClient((Item) meta.value());
            } else if (itemType != null && meta.metaType() == itemType) {
                int data = ((Integer) meta.value()).intValue();
                meta.setValue(Integer.valueOf(this.protocol.getMappingData().getNewBlockStateId(data)));
            } else if (blockType != null && meta.metaType() == blockType) {
                rewriteParticle((Particle) meta.value());
            }
        });
    }

    public void registerTracker(ClientboundPacketType packetType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.EntityRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT);
                handler(EntityRewriter.this.trackerHandler());
            }
        });
    }

    public void registerTrackerWithData(ClientboundPacketType packetType, final EntityType fallingBlockType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.EntityRewriter.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.INT);
                handler(EntityRewriter.this.trackerHandler());
                EntityType entityType = fallingBlockType;
                handler(wrapper -> {
                    int entityId = ((Integer) entityType.get(Type.VAR_INT, 0)).intValue();
                    EntityType entityType2 = EntityRewriter.this.tracker(entityType.user()).entityType(entityId);
                    if (entityType2 == fallingBlockType) {
                        entityType.set(Type.INT, 0, Integer.valueOf(EntityRewriter.this.protocol.getMappingData().getNewBlockStateId(((Integer) entityType.get(Type.INT, 0)).intValue())));
                    }
                });
            }
        });
    }

    public void registerTracker(ClientboundPacketType packetType, final EntityType entityType, final Type<Integer> intType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.EntityRewriter.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                Type type = intType;
                EntityType entityType2 = entityType;
                handler(wrapper -> {
                    int entityId = ((Integer) entityType2.passthrough(intType)).intValue();
                    EntityRewriter.this.tracker(entityType2.user()).addEntity(entityId, type);
                });
            }
        });
    }

    public void registerTracker(ClientboundPacketType packetType, EntityType entityType) {
        registerTracker(packetType, entityType, Type.VAR_INT);
    }

    public void registerRemoveEntities(ClientboundPacketType packetType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.EntityRewriter.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int[] entityIds = (int[]) wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                    EntityTracker entityTracker = EntityRewriter.this.tracker(wrapper.user());
                    for (int entity : entityIds) {
                        entityTracker.removeEntity(entity);
                    }
                });
            }
        });
    }

    public void registerRemoveEntity(ClientboundPacketType packetType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.EntityRewriter.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int entityId = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    EntityRewriter.this.tracker(wrapper.user()).removeEntity(entityId);
                });
            }
        });
    }

    public void registerMetadataRewriter(ClientboundPacketType packetType, final Type<List<Metadata>> oldMetaType, final Type<List<Metadata>> newMetaType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.EntityRewriter.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                if (oldMetaType != null) {
                    map(oldMetaType, newMetaType);
                } else {
                    map(newMetaType);
                }
                Type type = newMetaType;
                handler(wrapper -> {
                    int entityId = ((Integer) type.get(Type.VAR_INT, 0)).intValue();
                    List<Metadata> metadata = (List) type.get(newMetaType, 0);
                    EntityRewriter.this.handleMetadata(entityId, metadata, type.user());
                });
            }
        });
    }

    public void registerMetadataRewriter(ClientboundPacketType packetType, Type<List<Metadata>> metaType) {
        registerMetadataRewriter(packetType, null, metaType);
    }

    public PacketHandler trackerHandler() {
        return trackerAndRewriterHandler(null);
    }

    public PacketHandler worldDataTrackerHandler(int nbtIndex) {
        return wrapper -> {
            EntityTracker tracker = tracker(nbtIndex.user());
            CompoundTag registryData = (CompoundTag) nbtIndex.get(Type.NBT, nbtIndex);
            Tag height = registryData.get("height");
            if (height instanceof IntTag) {
                int blockHeight = ((IntTag) height).asInt();
                tracker.setCurrentWorldSectionHeight(blockHeight >> 4);
            } else {
                Via.getPlatform().getLogger().warning("Height missing in dimension data: " + registryData);
            }
            Tag minY = registryData.get("min_y");
            if (minY instanceof IntTag) {
                tracker.setCurrentMinY(((IntTag) minY).asInt());
            } else {
                Via.getPlatform().getLogger().warning("Min Y missing in dimension data: " + registryData);
            }
            String world = (String) nbtIndex.get(Type.STRING, 0);
            if (tracker.currentWorld() != null && !tracker.currentWorld().equals(world)) {
                tracker.clearEntities();
            }
            tracker.setCurrentWorld(world);
        };
    }

    public PacketHandler biomeSizeTracker() {
        return wrapper -> {
            CompoundTag registry = (CompoundTag) wrapper.get(Type.NBT, 0);
            CompoundTag biomeRegistry = (CompoundTag) registry.get("minecraft:worldgen/biome");
            ListTag biomes = (ListTag) biomeRegistry.get("value");
            tracker(wrapper.user()).setBiomesSent(biomes.size());
        };
    }

    public PacketHandler trackerAndRewriterHandler(Type<List<Metadata>> metaType) {
        return wrapper -> {
            int entityId = ((Integer) metaType.get(Type.VAR_INT, 0)).intValue();
            int type = ((Integer) metaType.get(Type.VAR_INT, 1)).intValue();
            int newType = newEntityId(type);
            if (newType != type) {
                metaType.set(Type.VAR_INT, 1, Integer.valueOf(newType));
            }
            EntityType entType = typeFromId(this.trackMappedType ? newType : type);
            tracker(metaType.user()).addEntity(entityId, entType);
            if (metaType != null) {
                handleMetadata(entityId, (List) metaType.get(metaType, 0), metaType.user());
            }
        };
    }

    public PacketHandler trackerAndRewriterHandler(Type<List<Metadata>> metaType, EntityType entityType) {
        return wrapper -> {
            int entityId = ((Integer) metaType.get(Type.VAR_INT, 0)).intValue();
            tracker(metaType.user()).addEntity(entityId, entityType);
            if (entityType != null) {
                handleMetadata(entityId, (List) metaType.get(entityType, 0), metaType.user());
            }
        };
    }

    public PacketHandler objectTrackerHandler() {
        return wrapper -> {
            int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
            byte type = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
            EntityType entType = objectTypeFromId(type);
            tracker(wrapper.user()).addEntity(entityId, entType);
        };
    }

    @Deprecated
    public Metadata metaByIndex(int index, List<Metadata> metadataList) {
        for (Metadata metadata : metadataList) {
            if (metadata.m222id() == index) {
                return metadata;
            }
        }
        return null;
    }

    public void rewriteParticle(Particle particle) {
        ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
        int id = particle.getId();
        if (mappings.isBlockParticle(id)) {
            Particle.ParticleData data = particle.getArguments().get(0);
            data.setValue(Integer.valueOf(this.protocol.getMappingData().getNewBlockStateId(((Integer) data.get()).intValue())));
        } else if (mappings.isItemParticle(id) && this.protocol.getItemRewriter() != null) {
            Item item = (Item) particle.getArguments().get(0).get();
            this.protocol.getItemRewriter().handleItemToClient(item);
        }
        particle.setId(this.protocol.getMappingData().getNewParticleId(id));
    }

    private void logException(Exception e, EntityType type, List<Metadata> metadataList, Metadata metadata) {
        if (!Via.getConfig().isSuppressMetadataErrors() || Via.getManager().isDebug()) {
            Logger logger = Via.getPlatform().getLogger();
            logger.severe("An error occurred in metadata handler " + getClass().getSimpleName() + " for " + (type != null ? type.name() : "untracked") + " entity type: " + metadata);
            logger.severe((String) metadataList.stream().sorted(Comparator.comparingInt((v0) -> {
                return v0.m222id();
            })).map((v0) -> {
                return v0.toString();
            }).collect(Collectors.joining("\n", "Full metadata: ", "")));
            e.printStackTrace();
        }
    }
}
