package com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.EulerAngle;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/metadata/MetadataRewriter1_9To1_8.class */
public class MetadataRewriter1_9To1_8 extends EntityRewriter<Protocol1_9To1_8> {
    public MetadataRewriter1_9To1_8(Protocol1_9To1_8 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
        MetaIndex metaIndex = MetaIndex.searchIndex(type, metadata.m222id());
        if (metaIndex == null) {
            throw new Exception("Could not find valid metadata");
        }
        if (metaIndex.getNewType() == null) {
            metadatas.remove(metadata);
            return;
        }
        metadata.setId(metaIndex.getNewIndex());
        metadata.setMetaTypeUnsafe(metaIndex.getNewType());
        Object value = metadata.getValue();
        switch (metaIndex.getNewType()) {
            case Byte:
                if (metaIndex.getOldType() == MetaType1_8.Byte) {
                    metadata.setValue(value);
                }
                if (metaIndex.getOldType() == MetaType1_8.Int) {
                    metadata.setValue(Byte.valueOf(((Integer) value).byteValue()));
                }
                if (metaIndex == MetaIndex.ENTITY_STATUS && type == Entity1_10Types.EntityType.PLAYER) {
                    Byte val = (byte) 0;
                    if ((((Byte) value).byteValue() & 16) == 16) {
                        val = (byte) 1;
                    }
                    int newIndex = MetaIndex.PLAYER_HAND.getNewIndex();
                    MetaType metaType = MetaIndex.PLAYER_HAND.getNewType();
                    metadatas.add(new Metadata(newIndex, metaType, val));
                    return;
                }
                return;
            case OptUUID:
                String owner = (String) value;
                UUID toWrite = null;
                if (!owner.isEmpty()) {
                    try {
                        toWrite = UUID.fromString(owner);
                    } catch (Exception e) {
                    }
                }
                metadata.setValue(toWrite);
                return;
            case VarInt:
                if (metaIndex.getOldType() == MetaType1_8.Byte) {
                    metadata.setValue(Integer.valueOf(((Byte) value).intValue()));
                }
                if (metaIndex.getOldType() == MetaType1_8.Short) {
                    metadata.setValue(Integer.valueOf(((Short) value).intValue()));
                }
                if (metaIndex.getOldType() == MetaType1_8.Int) {
                    metadata.setValue(value);
                    return;
                }
                return;
            case Float:
                metadata.setValue(value);
                return;
            case String:
                metadata.setValue(value);
                return;
            case Boolean:
                if (metaIndex == MetaIndex.AGEABLE_AGE) {
                    metadata.setValue(Boolean.valueOf(((Byte) value).byteValue() < 0));
                    return;
                } else {
                    metadata.setValue(Boolean.valueOf(((Byte) value).byteValue() != 0));
                    return;
                }
            case Slot:
                metadata.setValue(value);
                ItemRewriter.toClient((Item) metadata.getValue());
                return;
            case Position:
                Vector vector = (Vector) value;
                metadata.setValue(vector);
                return;
            case Vector3F:
                EulerAngle angle = (EulerAngle) value;
                metadata.setValue(angle);
                return;
            case Chat:
                metadata.setValue(Protocol1_9To1_8.fixJson(value.toString()));
                return;
            case BlockID:
                metadata.setValue(Integer.valueOf(((Number) value).intValue()));
                return;
            default:
                metadatas.remove(metadata);
                throw new Exception("Unhandled MetaDataType: " + metaIndex.getNewType());
        }
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int type) {
        return Entity1_10Types.getTypeFromId(type, false);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType objectTypeFromId(int type) {
        return Entity1_10Types.getTypeFromId(type, true);
    }
}
