package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.ChatPackets1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/Protocol1_11_1To1_12.class */
public class Protocol1_11_1To1_12 extends BackwardsProtocol<ClientboundPackets1_12, ClientboundPackets1_9_3, ServerboundPackets1_12, ServerboundPackets1_9_3> {
    private static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.12", "1.11", null, true) { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12.1
        @Override // com.viaversion.viabackwards.api.data.BackwardsMappings
        public boolean shouldWarnOnMissing(String key) {
            return super.shouldWarnOnMissing(key) && !key.equals("sounds");
        }
    };
    private final EntityPackets1_12 entityPackets = new EntityPackets1_12(this);
    private final BlockItemPackets1_12 blockItemPackets = new BlockItemPackets1_12(this);

    public Protocol1_11_1To1_12() {
        super(ClientboundPackets1_12.class, ClientboundPackets1_9_3.class, ServerboundPackets1_12.class, ServerboundPackets1_9_3.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.blockItemPackets.register();
        this.entityPackets.register();
        new SoundPackets1_12(this).register();
        new ChatPackets1_12(this).register();
        registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.TITLE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int action = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    if (action >= 0 && action <= 2) {
                        JsonElement component = (JsonElement) wrapper.read(Type.COMPONENT);
                        wrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(component.toString()));
                    }
                });
            }
        });
        cancelClientbound(ClientboundPackets1_12.ADVANCEMENTS);
        cancelClientbound(ClientboundPackets1_12.UNLOCK_RECIPES);
        cancelClientbound(ClientboundPackets1_12.SELECT_ADVANCEMENTS_TAB);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        user.addEntityTracker(getClass(), new EntityTrackerBase(user, Entity1_12Types.EntityType.PLAYER, true));
        user.put(new ShoulderTracker(user));
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol, com.viaversion.viaversion.api.protocol.Protocol
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityPackets1_12 getEntityRewriter() {
        return this.entityPackets;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public BlockItemPackets1_12 getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol, com.viaversion.viaversion.api.protocol.AbstractProtocol, com.viaversion.viaversion.api.protocol.Protocol
    public boolean hasMappingDataToLoad() {
        return true;
    }
}
