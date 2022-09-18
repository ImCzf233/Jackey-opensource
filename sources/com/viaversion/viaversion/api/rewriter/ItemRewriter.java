package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/rewriter/ItemRewriter.class */
public interface ItemRewriter<T extends Protocol> extends Rewriter<T> {
    Item handleItemToClient(Item item);

    Item handleItemToServer(Item item);
}
