package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/metadata/types/MetaType1_12.class */
public enum MetaType1_12 implements MetaType {
    Byte(0, Type.BYTE),
    VarInt(1, Type.VAR_INT),
    Float(2, Type.FLOAT),
    String(3, Type.STRING),
    Chat(4, Type.COMPONENT),
    Slot(5, Type.ITEM),
    Boolean(6, Type.BOOLEAN),
    Vector3F(7, Type.ROTATION),
    Position(8, Type.POSITION),
    OptPosition(9, Type.OPTIONAL_POSITION),
    Direction(10, Type.VAR_INT),
    OptUUID(11, Type.OPTIONAL_UUID),
    BlockID(12, Type.VAR_INT),
    NBTTag(13, Type.NBT);
    
    private final int typeID;
    private final Type type;

    MetaType1_12(int typeID, Type type) {
        this.typeID = typeID;
        this.type = type;
    }

    public static MetaType1_12 byId(int id) {
        return values()[id];
    }

    @Override // com.viaversion.viaversion.api.minecraft.metadata.MetaType
    public int typeId() {
        return this.typeID;
    }

    @Override // com.viaversion.viaversion.api.minecraft.metadata.MetaType
    public Type type() {
        return this.type;
    }
}
