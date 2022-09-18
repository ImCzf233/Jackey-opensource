package de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata;

import com.viaversion.viaversion.api.minecraft.EulerAngle;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetaIndex;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/metadata/MetadataRewriter.class */
public class MetadataRewriter {
    public static void transform(Entity1_10Types.EntityType type, List<Metadata> list) {
        Iterator it = new ArrayList(list).iterator();
        while (it.hasNext()) {
            Metadata entry = (Metadata) it.next();
            MetaIndex metaIndex = MetaIndex1_8to1_9.searchIndex(type, entry.m222id());
            if (metaIndex != null) {
                try {
                    if (metaIndex.getOldType() == MetaType1_8.NonExistent || metaIndex.getNewType() == null) {
                        list.remove(entry);
                    } else {
                        Object value = entry.getValue();
                        entry.setMetaTypeUnsafe(metaIndex.getOldType());
                        entry.setId(metaIndex.getIndex());
                        switch (metaIndex.getNewType()) {
                            case Byte:
                                if (metaIndex.getOldType() == MetaType1_8.Byte) {
                                    entry.setValue(value);
                                }
                                if (metaIndex.getOldType() == MetaType1_8.Int) {
                                    entry.setValue(Integer.valueOf(((Byte) value).intValue()));
                                    break;
                                }
                                break;
                            case OptUUID:
                                if (metaIndex.getOldType() != MetaType1_8.String) {
                                    list.remove(entry);
                                    break;
                                } else {
                                    UUID owner = (UUID) value;
                                    if (owner != null) {
                                        entry.setValue(owner.toString());
                                        break;
                                    } else {
                                        entry.setValue("");
                                        break;
                                    }
                                }
                            case BlockID:
                                list.remove(entry);
                                list.add(new Metadata(metaIndex.getIndex(), MetaType1_8.Short, Short.valueOf(((Integer) value).shortValue())));
                                break;
                            case VarInt:
                                if (metaIndex.getOldType() == MetaType1_8.Byte) {
                                    entry.setValue(Byte.valueOf(((Integer) value).byteValue()));
                                }
                                if (metaIndex.getOldType() == MetaType1_8.Short) {
                                    entry.setValue(Short.valueOf(((Integer) value).shortValue()));
                                }
                                if (metaIndex.getOldType() == MetaType1_8.Int) {
                                    entry.setValue(value);
                                    break;
                                }
                                break;
                            case Float:
                                entry.setValue(value);
                                break;
                            case String:
                                entry.setValue(value);
                                break;
                            case Boolean:
                                if (metaIndex != MetaIndex.AGEABLE_AGE) {
                                    entry.setValue(Byte.valueOf((byte) (((Boolean) value).booleanValue() ? 1 : 0)));
                                    break;
                                } else {
                                    entry.setValue(Byte.valueOf((byte) (((Boolean) value).booleanValue() ? -1 : 0)));
                                    break;
                                }
                            case Slot:
                                entry.setValue(ItemRewriter.toClient((Item) value));
                                break;
                            case Position:
                                Vector vector = (Vector) value;
                                entry.setValue(vector);
                                break;
                            case Vector3F:
                                EulerAngle angle = (EulerAngle) value;
                                entry.setValue(angle);
                                break;
                            case Chat:
                                entry.setValue(value);
                                break;
                            default:
                                ViaRewind.getPlatform().getLogger().warning("[Out] Unhandled MetaDataType: " + metaIndex.getNewType());
                                list.remove(entry);
                                break;
                        }
                        if (!metaIndex.getOldType().type().getOutputClass().isAssignableFrom(entry.getValue().getClass())) {
                            list.remove(entry);
                        }
                    }
                } catch (Exception e) {
                    list.remove(entry);
                }
            } else {
                throw new Exception("Could not find valid metadata");
            }
            list.remove(entry);
        }
    }
}
