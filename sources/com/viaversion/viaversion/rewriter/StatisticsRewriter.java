package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/StatisticsRewriter.class */
public class StatisticsRewriter {
    private final Protocol protocol;
    private final int customStatsCategory = 8;

    public StatisticsRewriter(Protocol protocol) {
        this.protocol = protocol;
    }

    public void register(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.StatisticsRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    IdRewriteFunction statisticsRewriter;
                    int size = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    int newSize = size;
                    for (int i = 0; i < size; i++) {
                        int categoryId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int statisticId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int value = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        if (categoryId == 8 && StatisticsRewriter.this.protocol.getMappingData().getStatisticsMappings() != null) {
                            statisticId = StatisticsRewriter.this.protocol.getMappingData().getStatisticsMappings().getNewId(statisticId);
                            if (statisticId == -1) {
                                newSize--;
                            }
                        } else {
                            RegistryType type = StatisticsRewriter.this.getRegistryTypeForStatistic(categoryId);
                            if (type != null && (statisticsRewriter = StatisticsRewriter.this.getRewriter(type)) != null) {
                                statisticId = statisticsRewriter.rewrite(statisticId);
                            }
                        }
                        wrapper.write(Type.VAR_INT, Integer.valueOf(categoryId));
                        wrapper.write(Type.VAR_INT, Integer.valueOf(statisticId));
                        wrapper.write(Type.VAR_INT, Integer.valueOf(value));
                    }
                    if (newSize != size) {
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(newSize));
                    }
                });
            }
        });
    }

    protected IdRewriteFunction getRewriter(RegistryType type) {
        switch (type) {
            case BLOCK:
                if (this.protocol.getMappingData().getBlockMappings() == null) {
                    return null;
                }
                return id -> {
                    return this.protocol.getMappingData().getNewBlockId(id);
                };
            case ITEM:
                if (this.protocol.getMappingData().getItemMappings() == null) {
                    return null;
                }
                return id2 -> {
                    return this.protocol.getMappingData().getNewItemId(id2);
                };
            case ENTITY:
                if (this.protocol.getEntityRewriter() == null) {
                    return null;
                }
                return id3 -> {
                    return this.protocol.getEntityRewriter().newEntityId(id3);
                };
            default:
                throw new IllegalArgumentException("Unknown registry type in statistics packet: " + type);
        }
    }

    public RegistryType getRegistryTypeForStatistic(int statisticsId) {
        switch (statisticsId) {
            case 0:
                return RegistryType.BLOCK;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return RegistryType.ITEM;
            case 6:
            case 7:
                return RegistryType.ENTITY;
            default:
                return null;
        }
    }
}
