package com.viaversion.viaversion.protocols.protocol1_18_2to1_18;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_18_2to1_18/Protocol1_18_2To1_18.class */
public final class Protocol1_18_2To1_18 extends AbstractProtocol<ClientboundPackets1_18, ClientboundPackets1_18, ServerboundPackets1_17, ServerboundPackets1_17> {
    public Protocol1_18_2To1_18() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        TagRewriter tagRewriter = new TagRewriter(this);
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:fall_damage_resetting");
        tagRewriter.registerGeneric(ClientboundPackets1_18.TAGS);
        registerClientbound((Protocol1_18_2To1_18) ClientboundPackets1_18.ENTITY_EFFECT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18_2to1_18.Protocol1_18_2To1_18.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE, Type.VAR_INT);
            }
        });
        registerClientbound((Protocol1_18_2To1_18) ClientboundPackets1_18.REMOVE_ENTITY_EFFECT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18_2to1_18.Protocol1_18_2To1_18.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE, Type.VAR_INT);
            }
        });
        registerClientbound((Protocol1_18_2To1_18) ClientboundPackets1_18.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18_2to1_18.Protocol1_18_2To1_18.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BOOLEAN);
                map(Type.UNSIGNED_BYTE);
                map(Type.BYTE);
                map(Type.STRING_ARRAY);
                map(Type.NBT);
                map(Type.NBT);
                handler(wrapper -> {
                    CompoundTag registry = (CompoundTag) wrapper.get(Type.NBT, 0);
                    CompoundTag dimensionsHolder = (CompoundTag) registry.get("minecraft:dimension_type");
                    ListTag dimensions = (ListTag) dimensionsHolder.get("value");
                    Iterator<Tag> it = dimensions.iterator();
                    while (it.hasNext()) {
                        Tag dimension = it.next();
                        Protocol1_18_2To1_18.this.addTagPrefix((CompoundTag) ((CompoundTag) dimension).get("element"));
                    }
                    Protocol1_18_2To1_18.this.addTagPrefix((CompoundTag) wrapper.get(Type.NBT, 1));
                });
            }
        });
        registerClientbound((Protocol1_18_2To1_18) ClientboundPackets1_18.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18_2to1_18.Protocol1_18_2To1_18.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    Protocol1_18_2To1_18.this.addTagPrefix((CompoundTag) wrapper.passthrough(Type.NBT));
                });
            }
        });
    }

    public void addTagPrefix(CompoundTag tag) {
        Tag infiniburnTag = tag.get("infiniburn");
        if (infiniburnTag instanceof StringTag) {
            StringTag infiniburn = (StringTag) infiniburnTag;
            infiniburn.setValue("#" + infiniburn.getValue());
        }
    }
}
