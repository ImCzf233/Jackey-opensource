package com.viaversion.viaversion.libs.opennbt.tag;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/TagRegistry.class */
public class TagRegistry {
    private static final Int2ObjectMap<Class<? extends Tag>> idToTag = new Int2ObjectOpenHashMap();
    private static final Object2IntMap<Class<? extends Tag>> tagToId = new Object2IntOpenHashMap();
    private static final Int2ObjectMap<Supplier<? extends Tag>> instanceSuppliers = new Int2ObjectOpenHashMap();

    static {
        tagToId.defaultReturnValue(-1);
        register(1, ByteTag.class, ByteTag::new);
        register(2, ShortTag.class, ShortTag::new);
        register(3, IntTag.class, IntTag::new);
        register(4, LongTag.class, LongTag::new);
        register(5, FloatTag.class, FloatTag::new);
        register(6, DoubleTag.class, DoubleTag::new);
        register(7, ByteArrayTag.class, ByteArrayTag::new);
        register(8, StringTag.class, StringTag::new);
        register(9, ListTag.class, ListTag::new);
        register(10, CompoundTag.class, CompoundTag::new);
        register(11, IntArrayTag.class, IntArrayTag::new);
        register(12, LongArrayTag.class, LongArrayTag::new);
    }

    public static void register(int id, Class<? extends Tag> tag, Supplier<? extends Tag> supplier) throws TagRegisterException {
        if (idToTag.containsKey(id)) {
            throw new TagRegisterException("Tag ID \"" + id + "\" is already in use.");
        }
        if (tagToId.containsKey(tag)) {
            throw new TagRegisterException("Tag \"" + tag.getSimpleName() + "\" is already registered.");
        }
        instanceSuppliers.put(id, (int) supplier);
        idToTag.put(id, (int) tag);
        tagToId.put((Object2IntMap<Class<? extends Tag>>) tag, id);
    }

    public static void unregister(int id) {
        tagToId.removeInt(getClassFor(id));
        idToTag.remove(id);
    }

    @Nullable
    public static Class<? extends Tag> getClassFor(int id) {
        return idToTag.get(id);
    }

    public static int getIdFor(Class<? extends Tag> clazz) {
        return tagToId.getInt(clazz);
    }

    public static Tag createInstance(int id) throws TagCreateException {
        Supplier<? extends Tag> supplier = instanceSuppliers.get(id);
        if (supplier == null) {
            throw new TagCreateException("Could not find tag with ID \"" + id + "\".");
        }
        return supplier.get();
    }
}
