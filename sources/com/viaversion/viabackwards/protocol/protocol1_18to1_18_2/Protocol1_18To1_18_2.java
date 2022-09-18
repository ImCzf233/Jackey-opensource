package com.viaversion.viabackwards.protocol.protocol1_18to1_18_2;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.data.CommandRewriter1_18_2;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_18to1_18_2/Protocol1_18To1_18_2.class */
public final class Protocol1_18To1_18_2 extends BackwardsProtocol<ClientboundPackets1_18, ClientboundPackets1_18, ServerboundPackets1_17, ServerboundPackets1_17> {
    public Protocol1_18To1_18_2() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        new CommandRewriter1_18_2(this).registerDeclareCommands(ClientboundPackets1_18.DECLARE_COMMANDS);
        final PacketHandler entityEffectIdHandler = wrapper -> {
            int id = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
            if (((byte) id) != id) {
                if (!Via.getConfig().isSuppressConversionWarnings()) {
                    ViaBackwards.getPlatform().getLogger().warning("Cannot send entity effect id " + id + " to old client");
                }
                wrapper.cancel();
                return;
            }
            wrapper.write(Type.BYTE, Byte.valueOf((byte) id));
        };
        registerClientbound((Protocol1_18To1_18_2) ClientboundPackets1_18.ENTITY_EFFECT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.Protocol1_18To1_18_2.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(entityEffectIdHandler);
            }
        });
        registerClientbound((Protocol1_18To1_18_2) ClientboundPackets1_18.REMOVE_ENTITY_EFFECT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.Protocol1_18To1_18_2.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(entityEffectIdHandler);
            }
        });
        registerClientbound((Protocol1_18To1_18_2) ClientboundPackets1_18.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.Protocol1_18To1_18_2.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BOOLEAN);
                map(Type.UNSIGNED_BYTE);
                map(Type.BYTE);
                map(Type.STRING_ARRAY);
                map(Type.NBT);
                map(Type.NBT);
                handler(wrapper2 -> {
                    CompoundTag registry = (CompoundTag) wrapper2.get(Type.NBT, 0);
                    CompoundTag dimensionsHolder = (CompoundTag) registry.get("minecraft:dimension_type");
                    ListTag dimensions = (ListTag) dimensionsHolder.get("value");
                    Iterator<Tag> it = dimensions.iterator();
                    while (it.hasNext()) {
                        Tag dimension = it.next();
                        Protocol1_18To1_18_2.this.removeTagPrefix((CompoundTag) ((CompoundTag) dimension).get("element"));
                    }
                    Protocol1_18To1_18_2.this.removeTagPrefix((CompoundTag) wrapper2.get(Type.NBT, 1));
                });
            }
        });
        registerClientbound((Protocol1_18To1_18_2) ClientboundPackets1_18.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.Protocol1_18To1_18_2.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper2 -> {
                    Protocol1_18To1_18_2.this.removeTagPrefix((CompoundTag) wrapper2.passthrough(Type.NBT));
                });
            }
        });
    }

    public void removeTagPrefix(CompoundTag tag) {
        Tag infiniburnTag = tag.get("infiniburn");
        if (infiniburnTag instanceof StringTag) {
            StringTag infiniburn = (StringTag) infiniburnTag;
            infiniburn.setValue(infiniburn.getValue().substring(1));
        }
    }
}
