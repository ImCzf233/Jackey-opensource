package de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetaIndex;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.Optional;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/metadata/MetaIndex1_8to1_9.class */
public class MetaIndex1_8to1_9 {
    private static final HashMap<Pair<Entity1_10Types.EntityType, Integer>, MetaIndex> metadataRewrites = new HashMap<>();

    static {
        MetaIndex[] values;
        for (MetaIndex index : MetaIndex.values()) {
            metadataRewrites.put(new Pair<>(index.getClazz(), Integer.valueOf(index.getNewIndex())), index);
        }
    }

    private static Optional<MetaIndex> getIndex(Entity1_10Types.EntityType type, int index) {
        Pair pair = new Pair(type, Integer.valueOf(index));
        if (metadataRewrites.containsKey(pair)) {
            return Optional.of(metadataRewrites.get(pair));
        }
        return Optional.empty();
    }

    public static MetaIndex searchIndex(Entity1_10Types.EntityType type, int index) {
        Entity1_10Types.EntityType currentType = type;
        do {
            Optional<MetaIndex> optMeta = getIndex(currentType, index);
            if (optMeta.isPresent()) {
                return optMeta.get();
            }
            currentType = currentType.getParent();
        } while (currentType != null);
        return null;
    }
}
