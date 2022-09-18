package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/ParticleMapping.class */
public class ParticleMapping {
    private static final ParticleData[] particles;

    static {
        ParticleHandler blockHandler = new ParticleHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.1
            @Override // com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.ParticleHandler
            public int[] rewrite(Protocol1_12_2To1_13 protocol, PacketWrapper wrapper) throws Exception {
                return rewrite(((Integer) wrapper.read(Type.VAR_INT)).intValue());
            }

            @Override // com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.ParticleHandler
            public int[] rewrite(Protocol1_12_2To1_13 protocol, List<Particle.ParticleData> data) {
                return rewrite(((Integer) data.get(0).getValue()).intValue());
            }

            private int[] rewrite(int newType) {
                int blockType = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(newType);
                int type = blockType >> 4;
                int meta = blockType & 15;
                return new int[]{type + (meta << 12)};
            }

            @Override // com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.ParticleHandler
            public boolean isBlockHandler() {
                return true;
            }
        };
        particles = new ParticleData[]{rewrite(16), rewrite(20), rewrite(35), rewrite(37, blockHandler), rewrite(4), rewrite(29), rewrite(9), rewrite(44), rewrite(42), rewrite(19), rewrite(18), rewrite(30, new ParticleHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.2
            @Override // com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.ParticleHandler
            public int[] rewrite(Protocol1_12_2To1_13 protocol, PacketWrapper wrapper) throws Exception {
                float r = ((Float) wrapper.read(Type.FLOAT)).floatValue();
                float g = ((Float) wrapper.read(Type.FLOAT)).floatValue();
                float b = ((Float) wrapper.read(Type.FLOAT)).floatValue();
                float scale = ((Float) wrapper.read(Type.FLOAT)).floatValue();
                wrapper.set(Type.FLOAT, 3, Float.valueOf(r));
                wrapper.set(Type.FLOAT, 4, Float.valueOf(g));
                wrapper.set(Type.FLOAT, 5, Float.valueOf(b));
                wrapper.set(Type.FLOAT, 6, Float.valueOf(scale));
                wrapper.set(Type.INT, 1, 0);
                return null;
            }

            @Override // com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.ParticleHandler
            public int[] rewrite(Protocol1_12_2To1_13 protocol, List<Particle.ParticleData> data) {
                return null;
            }
        }), rewrite(13), rewrite(41), rewrite(10), rewrite(25), rewrite(43), rewrite(15), rewrite(2), rewrite(1), rewrite(46, blockHandler), rewrite(3), rewrite(6), rewrite(26), rewrite(21), rewrite(34), rewrite(14), rewrite(36, new ParticleHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.3
            @Override // com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.ParticleHandler
            public int[] rewrite(Protocol1_12_2To1_13 protocol, PacketWrapper wrapper) throws Exception {
                return rewrite(protocol, (Item) wrapper.read(Type.FLAT_ITEM));
            }

            @Override // com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.ParticleHandler
            public int[] rewrite(Protocol1_12_2To1_13 protocol, List<Particle.ParticleData> data) {
                return rewrite(protocol, (Item) data.get(0).getValue());
            }

            private int[] rewrite(Protocol1_12_2To1_13 protocol, Item newItem) {
                Item item = protocol.getItemRewriter().handleItemToClient(newItem);
                return new int[]{item.identifier(), item.data()};
            }
        }), rewrite(33), rewrite(31), rewrite(12), rewrite(27), rewrite(22), rewrite(23), rewrite(0), rewrite(24), rewrite(39), rewrite(11), rewrite(48), rewrite(12), rewrite(45), rewrite(47), rewrite(7), rewrite(5), rewrite(17), rewrite(4), rewrite(4), rewrite(4), rewrite(18), rewrite(18)};
    }

    public static ParticleData getMapping(int id) {
        return particles[id];
    }

    private static ParticleData rewrite(int replacementId) {
        return new ParticleData(replacementId);
    }

    private static ParticleData rewrite(int replacementId, ParticleHandler handler) {
        return new ParticleData(replacementId, handler);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/ParticleMapping$ParticleHandler.class */
    public interface ParticleHandler {
        int[] rewrite(Protocol1_12_2To1_13 protocol1_12_2To1_13, PacketWrapper packetWrapper) throws Exception;

        int[] rewrite(Protocol1_12_2To1_13 protocol1_12_2To1_13, List<Particle.ParticleData> list);

        default boolean isBlockHandler() {
            return false;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/ParticleMapping$ParticleData.class */
    public static final class ParticleData {
        private final int historyId;
        private final ParticleHandler handler;

        private ParticleData(int historyId, ParticleHandler handler) {
            this.historyId = historyId;
            this.handler = handler;
        }

        private ParticleData(int historyId) {
            this(historyId, (ParticleHandler) null);
        }

        public int[] rewriteData(Protocol1_12_2To1_13 protocol, PacketWrapper wrapper) throws Exception {
            if (this.handler == null) {
                return null;
            }
            return this.handler.rewrite(protocol, wrapper);
        }

        public int[] rewriteMeta(Protocol1_12_2To1_13 protocol, List<Particle.ParticleData> data) {
            if (this.handler == null) {
                return null;
            }
            return this.handler.rewrite(protocol, data);
        }

        public int getHistoryId() {
            return this.historyId;
        }

        public ParticleHandler getHandler() {
            return this.handler;
        }
    }
}
