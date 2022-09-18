package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/metadata/MetadataRewriter1_16_2To1_16_1.class */
public class MetadataRewriter1_16_2To1_16_1 extends EntityRewriter<Protocol1_16_2To1_16_1> {
    public MetadataRewriter1_16_2To1_16_1(Protocol1_16_2To1_16_1 protocol) {
        super(protocol);
        mapTypes(Entity1_16Types.values(), Entity1_16_2Types.class);
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
        if (metadata.metaType() == Types1_16.META_TYPES.itemType) {
            ((Protocol1_16_2To1_16_1) this.protocol).getItemRewriter().handleItemToClient((Item) metadata.getValue());
        } else if (metadata.metaType() == Types1_16.META_TYPES.blockStateType) {
            int data = ((Integer) metadata.getValue()).intValue();
            metadata.setValue(Integer.valueOf(((Protocol1_16_2To1_16_1) this.protocol).getMappingData().getNewBlockStateId(data)));
        } else if (metadata.metaType() == Types1_16.META_TYPES.particleType) {
            rewriteParticle((Particle) metadata.getValue());
        }
        if (type == null) {
            return;
        }
        if (type.isOrHasParent(Entity1_16_2Types.MINECART_ABSTRACT) && metadata.m222id() == 10) {
            int data2 = ((Integer) metadata.getValue()).intValue();
            metadata.setValue(Integer.valueOf(((Protocol1_16_2To1_16_1) this.protocol).getMappingData().getNewBlockStateId(data2)));
        }
        if (type.isOrHasParent(Entity1_16_2Types.ABSTRACT_PIGLIN)) {
            if (metadata.m222id() == 15) {
                metadata.setId(16);
            } else if (metadata.m222id() == 16) {
                metadata.setId(15);
            }
        }
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int type) {
        return Entity1_16_2Types.getTypeFromId(type);
    }
}
