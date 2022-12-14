package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/TagRewriter.class */
public class TagRewriter {
    private static final int[] EMPTY_ARRAY = new int[0];
    private final Protocol protocol;
    private final Map<RegistryType, List<TagData>> newTags = new EnumMap(RegistryType.class);

    public TagRewriter(Protocol protocol) {
        this.protocol = protocol;
    }

    public void loadFromMappingData() {
        RegistryType[] values;
        for (RegistryType type : RegistryType.getValues()) {
            List<TagData> tags = this.protocol.getMappingData().getTags(type);
            if (tags != null) {
                getOrComputeNewTags(type).addAll(tags);
            }
        }
    }

    public void addEmptyTag(RegistryType tagType, String tagId) {
        getOrComputeNewTags(tagType).add(new TagData(tagId, EMPTY_ARRAY));
    }

    public void addEmptyTags(RegistryType tagType, String... tagIds) {
        List<TagData> tagList = getOrComputeNewTags(tagType);
        for (String id : tagIds) {
            tagList.add(new TagData(id, EMPTY_ARRAY));
        }
    }

    public void addEntityTag(String tagId, EntityType... entities) {
        int[] ids = new int[entities.length];
        for (int i = 0; i < entities.length; i++) {
            ids[i] = entities[i].getId();
        }
        addTagRaw(RegistryType.ENTITY, tagId, ids);
    }

    public void addTag(RegistryType tagType, String tagId, int... unmappedIds) {
        List<TagData> newTags = getOrComputeNewTags(tagType);
        IdRewriteFunction rewriteFunction = getRewriter(tagType);
        for (int i = 0; i < unmappedIds.length; i++) {
            int oldId = unmappedIds[i];
            unmappedIds[i] = rewriteFunction.rewrite(oldId);
        }
        newTags.add(new TagData(tagId, unmappedIds));
    }

    public void addTagRaw(RegistryType tagType, String tagId, int... ids) {
        getOrComputeNewTags(tagType).add(new TagData(tagId, ids));
    }

    public void register(ClientboundPacketType packetType, final RegistryType readUntilType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.TagRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(TagRewriter.this.getHandler(readUntilType));
            }
        });
    }

    public void registerGeneric(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.TagRewriter.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(TagRewriter.this.getGenericHandler());
            }
        });
    }

    public PacketHandler getHandler(RegistryType readUntilType) {
        return wrapper -> {
            RegistryType[] values;
            for (RegistryType type : RegistryType.getValues()) {
                handle(readUntilType, getRewriter(type), getNewTags(type));
                if (type == readUntilType) {
                    return;
                }
            }
        };
    }

    public PacketHandler getGenericHandler() {
        return wrapper -> {
            int length = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
            for (int i = 0; i < length; i++) {
                String registryKey = (String) wrapper.passthrough(Type.STRING);
                if (registryKey.startsWith("minecraft:")) {
                    registryKey = registryKey.substring(10);
                }
                RegistryType type = RegistryType.getByKey(registryKey);
                if (type != null) {
                    handle(wrapper, getRewriter(type), getNewTags(type));
                } else {
                    handle(wrapper, null, null);
                }
            }
        };
    }

    public void handle(PacketWrapper wrapper, IdRewriteFunction rewriteFunction, List<TagData> newTags) throws Exception {
        int tagsSize = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
        wrapper.write(Type.VAR_INT, Integer.valueOf(newTags != null ? tagsSize + newTags.size() : tagsSize));
        for (int i = 0; i < tagsSize; i++) {
            wrapper.passthrough(Type.STRING);
            int[] ids = (int[]) wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
            if (rewriteFunction != null) {
                IntList idList = new IntArrayList(ids.length);
                for (int id : ids) {
                    int mappedId = rewriteFunction.rewrite(id);
                    if (mappedId != -1) {
                        idList.add(mappedId);
                    }
                }
                wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, idList.toArray(EMPTY_ARRAY));
            } else {
                wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, ids);
            }
        }
        if (newTags != null) {
            for (TagData tag : newTags) {
                wrapper.write(Type.STRING, tag.identifier());
                wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, tag.entries());
            }
        }
    }

    public List<TagData> getNewTags(RegistryType tagType) {
        return this.newTags.get(tagType);
    }

    public List<TagData> getOrComputeNewTags(RegistryType tagType) {
        return this.newTags.computeIfAbsent(tagType, type -> {
            return new ArrayList();
        });
    }

    public IdRewriteFunction getRewriter(RegistryType tagType) {
        MappingData mappingData = this.protocol.getMappingData();
        switch (tagType) {
            case BLOCK:
                if (mappingData == null || mappingData.getBlockMappings() == null) {
                    return null;
                }
                mappingData.getClass();
                return this::getNewBlockId;
            case ITEM:
                if (mappingData == null || mappingData.getItemMappings() == null) {
                    return null;
                }
                mappingData.getClass();
                return this::getNewItemId;
            case ENTITY:
                if (this.protocol.getEntityRewriter() == null) {
                    return null;
                }
                return id -> {
                    return this.protocol.getEntityRewriter().newEntityId(id);
                };
            case FLUID:
            case GAME_EVENT:
            default:
                return null;
        }
    }
}
