package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.VillagerData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import io.netty.buffer.ByteBuf;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_14to1_13_2/metadata/MetadataRewriter1_14To1_13_2.class */
public class MetadataRewriter1_14To1_13_2 extends EntityRewriter<Protocol1_14To1_13_2> {
    public MetadataRewriter1_14To1_13_2(Protocol1_14To1_13_2 protocol) {
        super(protocol);
        mapTypes(Entity1_13Types.EntityType.values(), Entity1_14Types.class);
        mapEntityType(Entity1_13Types.EntityType.OCELOT, Entity1_14Types.CAT);
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
        metadata.setMetaType(Types1_14.META_TYPES.byId(metadata.metaType().typeId()));
        EntityTracker1_14 tracker = (EntityTracker1_14) tracker(connection);
        if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
            ((Protocol1_14To1_13_2) this.protocol).getItemRewriter().handleItemToClient((Item) metadata.getValue());
        } else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
            int data = ((Integer) metadata.getValue()).intValue();
            metadata.setValue(Integer.valueOf(((Protocol1_14To1_13_2) this.protocol).getMappingData().getNewBlockStateId(data)));
        } else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
            rewriteParticle((Particle) metadata.getValue());
        }
        if (type == null) {
            return;
        }
        if (metadata.m222id() > 5) {
            metadata.setId(metadata.m222id() + 1);
        }
        if (metadata.m222id() == 8 && type.isOrHasParent(Entity1_14Types.LIVINGENTITY)) {
            float v = ((Number) metadata.getValue()).floatValue();
            if (Float.isNaN(v) && Via.getConfig().is1_14HealthNaNFix()) {
                metadata.setValue(Float.valueOf(1.0f));
            }
        }
        if (metadata.m222id() > 11 && type.isOrHasParent(Entity1_14Types.LIVINGENTITY)) {
            metadata.setId(metadata.m222id() + 1);
        }
        if (type.isOrHasParent(Entity1_14Types.ABSTRACT_INSENTIENT) && metadata.m222id() == 13) {
            tracker.setInsentientData(entityId, (byte) ((((Number) metadata.getValue()).byteValue() & (-5)) | (tracker.getInsentientData(entityId) & 4)));
            metadata.setValue(Byte.valueOf(tracker.getInsentientData(entityId)));
        }
        if (type.isOrHasParent(Entity1_14Types.PLAYER)) {
            if (entityId != tracker.clientEntityId()) {
                if (metadata.m222id() == 0) {
                    byte flags = ((Number) metadata.getValue()).byteValue();
                    tracker.setEntityFlags(entityId, flags);
                } else if (metadata.m222id() == 7) {
                    tracker.setRiptide(entityId, (((Number) metadata.getValue()).byteValue() & 4) != 0);
                }
                if (metadata.m222id() == 0 || metadata.m222id() == 7) {
                    metadatas.add(new Metadata(6, Types1_14.META_TYPES.poseType, Integer.valueOf(recalculatePlayerPose(entityId, tracker))));
                }
            }
        } else if (type.isOrHasParent(Entity1_14Types.ZOMBIE)) {
            if (metadata.m222id() == 16) {
                tracker.setInsentientData(entityId, (byte) ((tracker.getInsentientData(entityId) & (-5)) | (((Boolean) metadata.getValue()).booleanValue() ? 4 : 0)));
                metadatas.remove(metadata);
                metadatas.add(new Metadata(13, Types1_14.META_TYPES.byteType, Byte.valueOf(tracker.getInsentientData(entityId))));
            } else if (metadata.m222id() > 16) {
                metadata.setId(metadata.m222id() - 1);
            }
        }
        if (type.isOrHasParent(Entity1_14Types.MINECART_ABSTRACT)) {
            if (metadata.m222id() == 10) {
                int data2 = ((Integer) metadata.getValue()).intValue();
                metadata.setValue(Integer.valueOf(((Protocol1_14To1_13_2) this.protocol).getMappingData().getNewBlockStateId(data2)));
            }
        } else if (type.m224is(Entity1_14Types.HORSE)) {
            if (metadata.m222id() == 18) {
                metadatas.remove(metadata);
                int armorType = ((Integer) metadata.getValue()).intValue();
                DataItem dataItem = null;
                if (armorType == 1) {
                    dataItem = new DataItem(((Protocol1_14To1_13_2) this.protocol).getMappingData().getNewItemId(727), (byte) 1, (short) 0, null);
                } else if (armorType == 2) {
                    dataItem = new DataItem(((Protocol1_14To1_13_2) this.protocol).getMappingData().getNewItemId(728), (byte) 1, (short) 0, null);
                } else if (armorType == 3) {
                    dataItem = new DataItem(((Protocol1_14To1_13_2) this.protocol).getMappingData().getNewItemId(729), (byte) 1, (short) 0, null);
                }
                PacketWrapper equipmentPacket = PacketWrapper.create(ClientboundPackets1_14.ENTITY_EQUIPMENT, (ByteBuf) null, connection);
                equipmentPacket.write(Type.VAR_INT, Integer.valueOf(entityId));
                equipmentPacket.write(Type.VAR_INT, 4);
                equipmentPacket.write(Type.FLAT_VAR_INT_ITEM, dataItem);
                equipmentPacket.scheduleSend(Protocol1_14To1_13_2.class);
            }
        } else if (type.m224is(Entity1_14Types.VILLAGER)) {
            if (metadata.m222id() == 15) {
                metadata.setTypeAndValue(Types1_14.META_TYPES.villagerDatatType, new VillagerData(2, getNewProfessionId(((Integer) metadata.getValue()).intValue()), 0));
            }
        } else if (type.m224is(Entity1_14Types.ZOMBIE_VILLAGER)) {
            if (metadata.m222id() == 18) {
                metadata.setTypeAndValue(Types1_14.META_TYPES.villagerDatatType, new VillagerData(2, getNewProfessionId(((Integer) metadata.getValue()).intValue()), 0));
            }
        } else if (type.isOrHasParent(Entity1_14Types.ABSTRACT_ARROW)) {
            if (metadata.m222id() >= 9) {
                metadata.setId(metadata.m222id() + 1);
            }
        } else if (type.m224is(Entity1_14Types.FIREWORK_ROCKET)) {
            if (metadata.m222id() == 8) {
                metadata.setMetaType(Types1_14.META_TYPES.optionalVarIntType);
                if (metadata.getValue().equals(0)) {
                    metadata.setValue(null);
                }
            }
        } else if (type.isOrHasParent(Entity1_14Types.ABSTRACT_SKELETON) && metadata.m222id() == 14) {
            tracker.setInsentientData(entityId, (byte) ((tracker.getInsentientData(entityId) & (-5)) | (((Boolean) metadata.getValue()).booleanValue() ? 4 : 0)));
            metadatas.remove(metadata);
            metadatas.add(new Metadata(13, Types1_14.META_TYPES.byteType, Byte.valueOf(tracker.getInsentientData(entityId))));
        }
        if (type.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE) && metadata.m222id() == 14) {
            tracker.setInsentientData(entityId, (byte) ((tracker.getInsentientData(entityId) & (-5)) | (((Number) metadata.getValue()).byteValue() != 0 ? 4 : 0)));
            metadatas.remove(metadata);
            metadatas.add(new Metadata(13, Types1_14.META_TYPES.byteType, Byte.valueOf(tracker.getInsentientData(entityId))));
        }
        if ((type.m224is(Entity1_14Types.WITCH) || type.m224is(Entity1_14Types.RAVAGER) || type.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE)) && metadata.m222id() >= 14) {
            metadata.setId(metadata.m222id() + 1);
        }
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int type) {
        return Entity1_14Types.getTypeFromId(type);
    }

    private static boolean isSneaking(byte flags) {
        return (flags & 2) != 0;
    }

    private static boolean isSwimming(byte flags) {
        return (flags & 16) != 0;
    }

    private static int getNewProfessionId(int old) {
        switch (old) {
            case 0:
                return 5;
            case 1:
                return 9;
            case 2:
                return 4;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 11;
            default:
                return 0;
        }
    }

    private static boolean isFallFlying(int entityFlags) {
        return (entityFlags & 128) != 0;
    }

    public static int recalculatePlayerPose(int entityId, EntityTracker1_14 tracker) {
        byte flags = tracker.getEntityFlags(entityId);
        int pose = 0;
        if (isFallFlying(flags)) {
            pose = 1;
        } else if (tracker.isSleeping(entityId)) {
            pose = 2;
        } else if (isSwimming(flags)) {
            pose = 3;
        } else if (tracker.isRiptide(entityId)) {
            pose = 4;
        } else if (isSneaking(flags)) {
            pose = 5;
        }
        return pose;
    }
}
