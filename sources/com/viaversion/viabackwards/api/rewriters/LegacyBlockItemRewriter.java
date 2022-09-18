package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.MappedLegacyBlockItem;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.BlockColors;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter.class */
public abstract class LegacyBlockItemRewriter<T extends BackwardsProtocol> extends ItemRewriterBase<T> {
    private static final Map<String, Int2ObjectMap<MappedLegacyBlockItem>> LEGACY_MAPPINGS = new HashMap();
    protected final Int2ObjectMap<MappedLegacyBlockItem> replacementData;

    static {
        JsonObject jsonObject = VBMappingDataLoader.loadFromDataDir("legacy-mappings.json");
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            Int2ObjectMap<MappedLegacyBlockItem> mappings = new Int2ObjectOpenHashMap<>(8);
            LEGACY_MAPPINGS.put(entry.getKey(), mappings);
            for (Map.Entry<String, JsonElement> dataEntry : entry.getValue().getAsJsonObject().entrySet()) {
                JsonObject object = dataEntry.getValue().getAsJsonObject();
                int id = object.getAsJsonPrimitive("id").getAsInt();
                JsonPrimitive jsonData = object.getAsJsonPrimitive("data");
                short data = jsonData != null ? jsonData.getAsShort() : (short) 0;
                String name = object.getAsJsonPrimitive("name").getAsString();
                JsonPrimitive blockField = object.getAsJsonPrimitive("block");
                boolean block = blockField != null && blockField.getAsBoolean();
                if (dataEntry.getKey().indexOf(45) != -1) {
                    String[] split = dataEntry.getKey().split("-", 2);
                    int from = Integer.parseInt(split[0]);
                    int to = Integer.parseInt(split[1]);
                    if (name.contains("%color%")) {
                        for (int i = from; i <= to; i++) {
                            mappings.put(i, (int) new MappedLegacyBlockItem(id, data, name.replace("%color%", BlockColors.get(i - from)), block));
                        }
                    } else {
                        MappedLegacyBlockItem mappedBlockItem = new MappedLegacyBlockItem(id, data, name, block);
                        for (int i2 = from; i2 <= to; i2++) {
                            mappings.put(i2, (int) mappedBlockItem);
                        }
                    }
                } else {
                    mappings.put(Integer.parseInt(dataEntry.getKey()), (int) new MappedLegacyBlockItem(id, data, name, block));
                }
            }
        }
    }

    public LegacyBlockItemRewriter(T protocol) {
        super(protocol, false);
        this.replacementData = LEGACY_MAPPINGS.get(protocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        MappedLegacyBlockItem data = this.replacementData.get(item.identifier());
        if (data == null) {
            return super.handleItemToClient(item);
        }
        short originalData = item.data();
        item.setIdentifier(data.getId());
        if (data.getData() != -1) {
            item.setData(data.getData());
        }
        if (data.getName() != null) {
            if (item.tag() == null) {
                item.setTag(new CompoundTag());
            }
            CompoundTag display = (CompoundTag) item.tag().get("display");
            if (display == null) {
                CompoundTag tag = item.tag();
                CompoundTag compoundTag = new CompoundTag();
                display = compoundTag;
                tag.put("display", compoundTag);
            }
            StringTag nameTag = (StringTag) display.get("Name");
            if (nameTag == null) {
                StringTag stringTag = new StringTag(data.getName());
                nameTag = stringTag;
                display.put("Name", stringTag);
                display.put(this.nbtTagName + "|customName", new ByteTag());
            }
            String value = nameTag.getValue();
            if (value.contains("%vb_color%")) {
                display.put("Name", new StringTag(value.replace("%vb_color%", BlockColors.get(originalData))));
            }
        }
        return item;
    }

    public int handleBlockID(int idx) {
        int type = idx >> 4;
        int meta = idx & 15;
        Block b = handleBlock(type, meta);
        return b == null ? idx : (b.getId() << 4) | (b.getData() & 15);
    }

    public Block handleBlock(int blockId, int data) {
        MappedLegacyBlockItem settings = this.replacementData.get(blockId);
        if (settings == null || !settings.isBlock()) {
            return null;
        }
        Block block = settings.getBlock();
        if (block.getData() == -1) {
            return block.withData(data);
        }
        return block;
    }

    public void handleChunk(Chunk chunk) {
        MappedLegacyBlockItem settings;
        Tag yTag;
        Tag zTag;
        ChunkSection section;
        Map<Pos, CompoundTag> tags = new HashMap<>();
        for (CompoundTag tag : chunk.getBlockEntities()) {
            Tag xTag = tag.get("x");
            if (xTag != null && (yTag = tag.get("y")) != null && (zTag = tag.get("z")) != null) {
                Pos pos = new Pos(((NumberTag) xTag).asInt() & 15, ((NumberTag) yTag).asInt(), ((NumberTag) zTag).asInt() & 15);
                tags.put(pos, tag);
                if (pos.getY() >= 0 && pos.getY() <= 255 && (section = chunk.getSections()[pos.getY() >> 4]) != null) {
                    int block = section.getFlatBlock(pos.getX(), pos.getY() & 15, pos.getZ());
                    int btype = block >> 4;
                    MappedLegacyBlockItem settings2 = this.replacementData.get(btype);
                    if (settings2 != null && settings2.hasBlockEntityHandler()) {
                        settings2.getBlockEntityHandler().handleOrNewCompoundTag(block, tag);
                    }
                }
            }
        }
        for (int i = 0; i < chunk.getSections().length; i++) {
            ChunkSection section2 = chunk.getSections()[i];
            if (section2 != null) {
                boolean hasBlockEntityHandler = false;
                for (int j = 0; j < section2.getPaletteSize(); j++) {
                    int block2 = section2.getPaletteEntry(j);
                    int btype2 = block2 >> 4;
                    int meta = block2 & 15;
                    Block b = handleBlock(btype2, meta);
                    if (b != null) {
                        section2.setPaletteEntry(j, (b.getId() << 4) | (b.getData() & 15));
                    }
                    if (!hasBlockEntityHandler && (settings = this.replacementData.get(btype2)) != null && settings.hasBlockEntityHandler()) {
                        hasBlockEntityHandler = true;
                    }
                }
                if (hasBlockEntityHandler) {
                    for (int x = 0; x < 16; x++) {
                        for (int y = 0; y < 16; y++) {
                            for (int z = 0; z < 16; z++) {
                                int block3 = section2.getFlatBlock(x, y, z);
                                int btype3 = block3 >> 4;
                                int i2 = block3 & 15;
                                MappedLegacyBlockItem settings3 = this.replacementData.get(btype3);
                                if (settings3 != null && settings3.hasBlockEntityHandler() && !tags.containsKey(new Pos(x, y + (i << 4), z))) {
                                    CompoundTag tag2 = new CompoundTag();
                                    tag2.put("x", new IntTag(x + (chunk.getX() << 4)));
                                    tag2.put("y", new IntTag(y + (i << 4)));
                                    tag2.put("z", new IntTag(z + (chunk.getZ() << 4)));
                                    settings3.getBlockEntityHandler().handleOrNewCompoundTag(block3, tag2);
                                    chunk.getBlockEntities().add(tag2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public CompoundTag getNamedTag(String text) {
        CompoundTag tag = new CompoundTag();
        tag.put("display", new CompoundTag());
        String text2 = "§r" + text;
        ((CompoundTag) tag.get("display")).put("Name", new StringTag(this.jsonNameFormat ? ChatRewriter.legacyTextToJsonString(text2) : text2));
        return tag;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos.class */
    public static final class Pos {

        /* renamed from: x */
        private final int f17x;

        /* renamed from: y */
        private final short f18y;

        /* renamed from: z */
        private final int f19z;

        private Pos(int x, int y, int z) {
            this.f17x = x;
            this.f18y = (short) y;
            this.f19z = z;
        }

        public int getX() {
            return this.f17x;
        }

        public int getY() {
            return this.f18y;
        }

        public int getZ() {
            return this.f19z;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pos pos = (Pos) o;
            return this.f17x == pos.f17x && this.f18y == pos.f18y && this.f19z == pos.f19z;
        }

        public int hashCode() {
            int result = this.f17x;
            return (31 * ((31 * result) + this.f18y)) + this.f19z;
        }

        public String toString() {
            return "Pos{x=" + this.f17x + ", y=" + ((int) this.f18y) + ", z=" + this.f19z + '}';
        }
    }
}
