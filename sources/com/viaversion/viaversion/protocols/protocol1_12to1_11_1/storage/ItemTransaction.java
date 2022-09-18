package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_12to1_11_1/storage/ItemTransaction.class */
public class ItemTransaction {
    private final short windowId;
    private final short slotId;
    private final short actionId;

    public ItemTransaction(short windowId, short slotId, short actionId) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.actionId = actionId;
    }

    public short getWindowId() {
        return this.windowId;
    }

    public short getSlotId() {
        return this.slotId;
    }

    public short getActionId() {
        return this.actionId;
    }

    public String toString() {
        return "ItemTransaction{windowId=" + ((int) this.windowId) + ", slotId=" + ((int) this.slotId) + ", actionId=" + ((int) this.actionId) + '}';
    }
}
