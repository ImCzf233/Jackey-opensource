package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16to1_15_2/storage/InventoryTracker1_16.class */
public class InventoryTracker1_16 implements StorableObject {
    private short inventory = -1;

    public short getInventory() {
        return this.inventory;
    }

    public void setInventory(short inventory) {
        this.inventory = inventory;
    }
}
