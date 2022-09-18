package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.EntityPackets;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_15to1_14_4/metadata/MetadataRewriter1_15To1_14_4.class */
public class MetadataRewriter1_15To1_14_4 extends EntityRewriter<Protocol1_15To1_14_4> {
    public MetadataRewriter1_15To1_14_4(Protocol1_15To1_14_4 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
        if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
            ((Protocol1_15To1_14_4) this.protocol).getItemRewriter().handleItemToClient((Item) metadata.getValue());
        } else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
            int data = ((Integer) metadata.getValue()).intValue();
            metadata.setValue(Integer.valueOf(((Protocol1_15To1_14_4) this.protocol).getMappingData().getNewBlockStateId(data)));
        } else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
            rewriteParticle((Particle) metadata.getValue());
        }
        if (type == null) {
            return;
        }
        if (type.isOrHasParent(Entity1_15Types.MINECART_ABSTRACT) && metadata.m222id() == 10) {
            int data2 = ((Integer) metadata.getValue()).intValue();
            metadata.setValue(Integer.valueOf(((Protocol1_15To1_14_4) this.protocol).getMappingData().getNewBlockStateId(data2)));
        }
        if (metadata.m222id() > 11 && type.isOrHasParent(Entity1_15Types.LIVINGENTITY)) {
            metadata.setId(metadata.m222id() + 1);
        }
        if (type.isOrHasParent(Entity1_15Types.WOLF)) {
            if (metadata.m222id() == 18) {
                metadatas.remove(metadata);
            } else if (metadata.m222id() > 18) {
                metadata.setId(metadata.m222id() - 1);
            }
        }
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter, com.viaversion.viaversion.api.rewriter.EntityRewriter
    public int newEntityId(int id) {
        return EntityPackets.getNewEntityId(id);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int type) {
        return Entity1_15Types.getTypeFromId(type);
    }
}
