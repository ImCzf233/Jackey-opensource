package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityTypeRewriter;
import java.lang.reflect.Field;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/EntityTypeMapping.class */
public class EntityTypeMapping {
    private static final Int2IntMap TYPES = new Int2IntOpenHashMap();

    static {
        TYPES.defaultReturnValue(-1);
        try {
            Field field = EntityTypeRewriter.class.getDeclaredField("ENTITY_TYPES");
            field.setAccessible(true);
            Int2IntMap entityTypes = (Int2IntMap) field.get(null);
            ObjectIterator<Int2IntMap.Entry> it = entityTypes.int2IntEntrySet().iterator();
            while (it.hasNext()) {
                Int2IntMap.Entry entry = it.next();
                TYPES.put(entry.getIntValue(), entry.getIntKey());
            }
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }

    public static int getOldId(int type1_13) {
        return TYPES.get(type1_13);
    }
}
