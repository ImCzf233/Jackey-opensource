package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/BlockRewriter.class */
public class BlockRewriter {
    private final Protocol protocol;
    private final Type<Position> positionType;

    public BlockRewriter(Protocol protocol, Type<Position> positionType) {
        this.protocol = protocol;
        this.positionType = positionType;
    }

    public void registerBlockAction(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.BlockRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(BlockRewriter.this.positionType);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int id = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    int mappedId = BlockRewriter.this.protocol.getMappingData().getNewBlockId(id);
                    if (mappedId == -1) {
                        wrapper.cancel();
                    } else {
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(mappedId));
                    }
                });
            }
        });
    }

    public void registerBlockChange(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.BlockRewriter.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(BlockRewriter.this.positionType);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    wrapper.set(Type.VAR_INT, 0, Integer.valueOf(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(((Integer) wrapper.get(Type.VAR_INT, 0)).intValue())));
                });
            }
        });
    }

    public void registerMultiBlockChange(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.BlockRewriter.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.INT);
                handler(wrapper -> {
                    BlockChangeRecord[] blockChangeRecordArr;
                    for (BlockChangeRecord record : (BlockChangeRecord[]) wrapper.passthrough(Type.BLOCK_CHANGE_RECORD_ARRAY)) {
                        record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
    }

    public void registerVarLongMultiBlockChange(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.BlockRewriter.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.LONG);
                map(Type.BOOLEAN);
                handler(wrapper -> {
                    BlockChangeRecord[] blockChangeRecordArr;
                    for (BlockChangeRecord record : (BlockChangeRecord[]) wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                        record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
    }

    public void registerAcknowledgePlayerDigging(ClientboundPacketType packetType) {
        registerBlockChange(packetType);
    }

    public void registerEffect(ClientboundPacketType packetType, final int playRecordId, final int blockBreakId) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.BlockRewriter.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(BlockRewriter.this.positionType);
                map(Type.INT);
                int i = playRecordId;
                int i2 = blockBreakId;
                handler(wrapper -> {
                    int id = ((Integer) i2.get(Type.INT, 0)).intValue();
                    int data = ((Integer) i2.get(Type.INT, 1)).intValue();
                    if (id == playRecordId) {
                        i2.set(Type.INT, 1, Integer.valueOf(BlockRewriter.this.protocol.getMappingData().getNewItemId(data)));
                    } else if (id == i) {
                        i2.set(Type.INT, 1, Integer.valueOf(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(data)));
                    }
                });
            }
        });
    }
}
