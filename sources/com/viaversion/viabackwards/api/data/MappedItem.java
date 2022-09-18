package com.viaversion.viabackwards.api.data;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/data/MappedItem.class */
public class MappedItem {

    /* renamed from: id */
    private final int f7id;
    private final String jsonName;

    public MappedItem(int id, String name) {
        this.f7id = id;
        this.jsonName = ChatRewriter.legacyTextToJsonString("§f" + name);
    }

    public int getId() {
        return this.f7id;
    }

    public String getJsonName() {
        return this.jsonName;
    }
}
