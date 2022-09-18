package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/BaseItemType.class */
public abstract class BaseItemType extends Type<Item> {
    protected BaseItemType() {
        super(Item.class);
    }

    public BaseItemType(String typeName) {
        super(typeName, Item.class);
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseItemType.class;
    }
}
