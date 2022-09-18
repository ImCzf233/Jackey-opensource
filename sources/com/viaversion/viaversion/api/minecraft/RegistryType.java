package com.viaversion.viaversion.api.minecraft;

import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/RegistryType.class */
public enum RegistryType {
    BLOCK("block"),
    ITEM("item"),
    FLUID("fluid"),
    ENTITY("entity_type"),
    GAME_EVENT("game_event");
    
    private static final Map<String, RegistryType> MAP = new HashMap();
    private static final RegistryType[] VALUES = values();
    private final String resourceLocation;

    static {
        RegistryType[] values;
        for (RegistryType type : getValues()) {
            MAP.put(type.resourceLocation, type);
        }
    }

    public static RegistryType[] getValues() {
        return VALUES;
    }

    public static RegistryType getByKey(String resourceKey) {
        return MAP.get(resourceKey);
    }

    RegistryType(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Deprecated
    public String getResourceLocation() {
        return this.resourceLocation;
    }

    public String resourceLocation() {
        return this.resourceLocation;
    }
}
