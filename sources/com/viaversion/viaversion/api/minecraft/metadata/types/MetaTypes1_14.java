package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/metadata/types/MetaTypes1_14.class */
public final class MetaTypes1_14 extends AbstractMetaTypes {
    public final MetaType particleType;
    public final MetaType byteType = add(0, Type.BYTE);
    public final MetaType varIntType = add(1, Type.VAR_INT);
    public final MetaType floatType = add(2, Type.FLOAT);
    public final MetaType stringType = add(3, Type.STRING);
    public final MetaType componentType = add(4, Type.COMPONENT);
    public final MetaType optionalComponentType = add(5, Type.OPTIONAL_COMPONENT);
    public final MetaType itemType = add(6, Type.FLAT_VAR_INT_ITEM);
    public final MetaType booleanType = add(7, Type.BOOLEAN);
    public final MetaType rotationType = add(8, Type.ROTATION);
    public final MetaType positionType = add(9, Type.POSITION1_14);
    public final MetaType optionalPositionType = add(10, Type.OPTIONAL_POSITION_1_14);
    public final MetaType directionType = add(11, Type.VAR_INT);
    public final MetaType optionalUUIDType = add(12, Type.OPTIONAL_UUID);
    public final MetaType blockStateType = add(13, Type.VAR_INT);
    public final MetaType nbtType = add(14, Type.NBT);
    public final MetaType villagerDatatType = add(16, Type.VILLAGER_DATA);
    public final MetaType optionalVarIntType = add(17, Type.OPTIONAL_VAR_INT);
    public final MetaType poseType = add(18, Type.VAR_INT);

    public MetaTypes1_14(ParticleType particleType) {
        super(19);
        this.particleType = add(15, particleType);
    }
}
