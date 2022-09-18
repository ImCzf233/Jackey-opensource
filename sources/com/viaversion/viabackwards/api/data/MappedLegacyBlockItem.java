package com.viaversion.viabackwards.api.data;

import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/data/MappedLegacyBlockItem.class */
public class MappedLegacyBlockItem {

    /* renamed from: id */
    private final int f8id;
    private final short data;
    private final String name;
    private final Block block;
    private BlockEntityHandler blockEntityHandler;

    @FunctionalInterface
    /* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/data/MappedLegacyBlockItem$BlockEntityHandler.class */
    public interface BlockEntityHandler {
        CompoundTag handleOrNewCompoundTag(int i, CompoundTag compoundTag);
    }

    public MappedLegacyBlockItem(int id, short data, String name, boolean block) {
        this.f8id = id;
        this.data = data;
        this.name = name != null ? "§f" + name : null;
        this.block = block ? new Block(id, data) : null;
    }

    public int getId() {
        return this.f8id;
    }

    public short getData() {
        return this.data;
    }

    public String getName() {
        return this.name;
    }

    public boolean isBlock() {
        return this.block != null;
    }

    public Block getBlock() {
        return this.block;
    }

    public boolean hasBlockEntityHandler() {
        return this.blockEntityHandler != null;
    }

    public BlockEntityHandler getBlockEntityHandler() {
        return this.blockEntityHandler;
    }

    public void setBlockEntityHandler(BlockEntityHandler blockEntityHandler) {
        this.blockEntityHandler = blockEntityHandler;
    }
}
