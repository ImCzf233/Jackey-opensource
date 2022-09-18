package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.util.Int2IntBiMap;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/data/MappingData.class */
public interface MappingData {
    void load();

    int getNewBlockStateId(int i);

    int getNewBlockId(int i);

    int getNewItemId(int i);

    int getOldItemId(int i);

    int getNewParticleId(int i);

    List<TagData> getTags(RegistryType registryType);

    Int2IntBiMap getItemMappings();

    ParticleMappings getParticleMappings();

    Mappings getBlockMappings();

    Mappings getBlockEntityMappings();

    Mappings getBlockStateMappings();

    Mappings getSoundMappings();

    Mappings getStatisticsMappings();
}
