package com.viaversion.viabackwards.protocol.protocol1_14to1_14_1;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets.EntityPackets1_14_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_14to1_14_1/Protocol1_14To1_14_1.class */
public class Protocol1_14To1_14_1 extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
    private final EntityRewriter entityRewriter = new EntityPackets1_14_1(this);

    public Protocol1_14To1_14_1() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.entityRewriter.register();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection user) {
        user.addEntityTracker(getClass(), new EntityTrackerBase(user, Entity1_15Types.PLAYER));
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
}
