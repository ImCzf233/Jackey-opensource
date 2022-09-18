package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/BaseItemArrayType.class */
public abstract class BaseItemArrayType extends Type<Item[]> {
    protected BaseItemArrayType() {
        super(Item[].class);
    }

    public BaseItemArrayType(String typeName) {
        super(typeName, Item[].class);
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseItemArrayType.class;
    }
}
