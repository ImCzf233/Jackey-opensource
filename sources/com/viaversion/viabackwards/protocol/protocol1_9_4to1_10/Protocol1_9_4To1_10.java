package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.BlockItemPackets1_10;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.EntityPackets1_10;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_9_4to1_10/Protocol1_9_4To1_10.class */
public class Protocol1_9_4To1_10 extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.10", "1.9.4", null, true);
    private static final ValueTransformer<Float, Short> TO_OLD_PITCH = new ValueTransformer<Float, Short>(Type.UNSIGNED_BYTE) { // from class: com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10.1
        public Short transform(PacketWrapper packetWrapper, Float inputValue) throws Exception {
            return Short.valueOf((short) Math.round(inputValue.floatValue() * 63.5f));
        }
    };
    private final EntityPackets1_10 entityPackets = new EntityPackets1_10(this);
    private final BlockItemPackets1_10 blockItemPackets = new BlockItemPackets1_10(this);

    public Protocol1_9_4To1_10() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.entityPackets.register();
        this.blockItemPackets.register();
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        registerClientbound((Protocol1_9_4To1_10) ClientboundPackets1_9_3.NAMED_SOUND, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.VAR_INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.FLOAT);
                map(Type.FLOAT, Protocol1_9_4To1_10.TO_OLD_PITCH);
                handler(soundRewriter.getNamedSoundHandler());
            }
        });
        registerClientbound((Protocol1_9_4To1_10) ClientboundPackets1_9_3.SOUND, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.FLOAT);
                map(Type.FLOAT, Protocol1_9_4To1_10.TO_OLD_PITCH);
                handler(soundRewriter.getSoundHandler());
            }
        });
        registerServerbound((Protocol1_9_4To1_10) ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING, Type.NOTHING);
                map(Type.VAR_INT);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        user.addEntityTracker(getClass(), new EntityTrackerBase(user, Entity1_10Types.EntityType.PLAYER));
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol, com.viaversion.viaversion.api.protocol.Protocol
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityPackets1_10 getEntityRewriter() {
        return this.entityPackets;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public BlockItemPackets1_10 getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol, com.viaversion.viaversion.api.protocol.AbstractProtocol, com.viaversion.viaversion.api.protocol.Protocol
    public boolean hasMappingDataToLoad() {
        return true;
    }
}
