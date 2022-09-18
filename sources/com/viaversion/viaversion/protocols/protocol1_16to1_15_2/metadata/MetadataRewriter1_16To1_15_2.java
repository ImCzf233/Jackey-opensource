package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16to1_15_2/metadata/MetadataRewriter1_16To1_15_2.class */
public class MetadataRewriter1_16To1_15_2 extends EntityRewriter<Protocol1_16To1_15_2> {
    public MetadataRewriter1_16To1_15_2(Protocol1_16To1_15_2 protocol) {
        super(protocol);
        mapEntityType(Entity1_15Types.ZOMBIE_PIGMAN, Entity1_16Types.ZOMBIFIED_PIGLIN);
        mapTypes(Entity1_15Types.values(), Entity1_16Types.class);
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
        metadata.setMetaType(Types1_16.META_TYPES.byId(metadata.metaType().typeId()));
        if (metadata.metaType() == Types1_16.META_TYPES.itemType) {
            ((Protocol1_16To1_15_2) this.protocol).getItemRewriter().handleItemToClient((Item) metadata.getValue());
        } else if (metadata.metaType() == Types1_16.META_TYPES.blockStateType) {
            int data = ((Integer) metadata.getValue()).intValue();
            metadata.setValue(Integer.valueOf(((Protocol1_16To1_15_2) this.protocol).getMappingData().getNewBlockStateId(data)));
        } else if (metadata.metaType() == Types1_16.META_TYPES.particleType) {
            rewriteParticle((Particle) metadata.getValue());
        }
        if (type == null) {
            return;
        }
        if (type.isOrHasParent(Entity1_16Types.MINECART_ABSTRACT) && metadata.m222id() == 10) {
            int data2 = ((Integer) metadata.getValue()).intValue();
            metadata.setValue(Integer.valueOf(((Protocol1_16To1_15_2) this.protocol).getMappingData().getNewBlockStateId(data2)));
        }
        if (type.isOrHasParent(Entity1_16Types.ABSTRACT_ARROW)) {
            if (metadata.m222id() == 8) {
                metadatas.remove(metadata);
            } else if (metadata.m222id() > 8) {
                metadata.setId(metadata.m222id() - 1);
            }
        }
        if (type == Entity1_16Types.WOLF && metadata.m222id() == 16) {
            byte mask = ((Byte) metadata.value()).byteValue();
            int angerTime = (mask & 2) != 0 ? Integer.MAX_VALUE : 0;
            metadatas.add(new Metadata(20, Types1_16.META_TYPES.varIntType, Integer.valueOf(angerTime)));
        }
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int type) {
        return Entity1_16Types.getTypeFromId(type);
    }
}
