package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityTypeRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/metadata/MetadataRewriter1_13To1_12_2.class */
public class MetadataRewriter1_13To1_12_2 extends EntityRewriter<Protocol1_13To1_12_2> {
    public MetadataRewriter1_13To1_12_2(Protocol1_13To1_12_2 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
        if (metadata.metaType().typeId() > 4) {
            metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId() + 1));
        } else {
            metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
        }
        if (metadata.m222id() == 2) {
            if (metadata.getValue() != null && !((String) metadata.getValue()).isEmpty()) {
                metadata.setTypeAndValue(Types1_13.META_TYPES.optionalComponentType, ChatRewriter.legacyTextToJson((String) metadata.getValue()));
            } else {
                metadata.setTypeAndValue(Types1_13.META_TYPES.optionalComponentType, null);
            }
        }
        if (type == Entity1_13Types.EntityType.ENDERMAN && metadata.m222id() == 12) {
            int stateId = ((Integer) metadata.getValue()).intValue();
            int id = stateId & 4095;
            int data = (stateId >> 12) & 15;
            metadata.setValue(Integer.valueOf((id << 4) | (data & 15)));
        }
        if (metadata.metaType() == Types1_13.META_TYPES.itemType) {
            metadata.setMetaType(Types1_13.META_TYPES.itemType);
            ((Protocol1_13To1_12_2) this.protocol).getItemRewriter().handleItemToClient((Item) metadata.getValue());
        } else if (metadata.metaType() == Types1_13.META_TYPES.blockStateType) {
            metadata.setValue(Integer.valueOf(WorldPackets.toNewId(((Integer) metadata.getValue()).intValue())));
        }
        if (type == null) {
            return;
        }
        if (type == Entity1_13Types.EntityType.WOLF && metadata.m222id() == 17) {
            metadata.setValue(Integer.valueOf(15 - ((Integer) metadata.getValue()).intValue()));
        }
        if (type.isOrHasParent(Entity1_13Types.EntityType.ZOMBIE) && metadata.m222id() > 14) {
            metadata.setId(metadata.m222id() + 1);
        }
        if (type.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT) && metadata.m222id() == 9) {
            int oldId = ((Integer) metadata.getValue()).intValue();
            int combined = ((oldId & 4095) << 4) | ((oldId >> 12) & 15);
            int newId = WorldPackets.toNewId(combined);
            metadata.setValue(Integer.valueOf(newId));
        }
        if (type == Entity1_13Types.EntityType.AREA_EFFECT_CLOUD) {
            if (metadata.m222id() == 9) {
                int particleId = ((Integer) metadata.getValue()).intValue();
                Metadata parameter1Meta = metaByIndex(10, metadatas);
                Metadata parameter2Meta = metaByIndex(11, metadatas);
                int parameter1 = parameter1Meta != null ? ((Integer) parameter1Meta.getValue()).intValue() : 0;
                int parameter2 = parameter2Meta != null ? ((Integer) parameter2Meta.getValue()).intValue() : 0;
                Particle particle = ParticleRewriter.rewriteParticle(particleId, new Integer[]{Integer.valueOf(parameter1), Integer.valueOf(parameter2)});
                if (particle != null && particle.getId() != -1) {
                    metadatas.add(new Metadata(9, Types1_13.META_TYPES.particleType, particle));
                }
            }
            if (metadata.m222id() >= 9) {
                metadatas.remove(metadata);
            }
        }
        if (metadata.m222id() == 0) {
            metadata.setValue(Byte.valueOf((byte) (((Byte) metadata.getValue()).byteValue() & (-17))));
        }
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter, com.viaversion.viaversion.api.rewriter.EntityRewriter
    public int newEntityId(int id) {
        return EntityTypeRewriter.getNewId(id);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int type) {
        return Entity1_13Types.getTypeFromId(type, false);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType objectTypeFromId(int type) {
        return Entity1_13Types.getTypeFromId(type, true);
    }
}
