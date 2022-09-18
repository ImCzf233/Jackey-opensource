package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13_1to1_13/metadata/MetadataRewriter1_13_1To1_13.class */
public class MetadataRewriter1_13_1To1_13 extends EntityRewriter<Protocol1_13_1To1_13> {
    public MetadataRewriter1_13_1To1_13(Protocol1_13_1To1_13 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) {
        if (metadata.metaType() == Types1_13.META_TYPES.itemType) {
            ((Protocol1_13_1To1_13) this.protocol).getItemRewriter().handleItemToClient((Item) metadata.getValue());
        } else if (metadata.metaType() == Types1_13.META_TYPES.blockStateType) {
            int data = ((Integer) metadata.getValue()).intValue();
            metadata.setValue(Integer.valueOf(((Protocol1_13_1To1_13) this.protocol).getMappingData().getNewBlockStateId(data)));
        } else if (metadata.metaType() == Types1_13.META_TYPES.particleType) {
            rewriteParticle((Particle) metadata.getValue());
        }
        if (type == null) {
            return;
        }
        if (type.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT) && metadata.m222id() == 9) {
            int data2 = ((Integer) metadata.getValue()).intValue();
            metadata.setValue(Integer.valueOf(((Protocol1_13_1To1_13) this.protocol).getMappingData().getNewBlockStateId(data2)));
        } else if (type.isOrHasParent(Entity1_13Types.EntityType.ABSTRACT_ARROW) && metadata.m222id() >= 7) {
            metadata.setId(metadata.m222id() + 1);
        }
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
