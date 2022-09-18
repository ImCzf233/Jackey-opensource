package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/Particle1_14Type.class */
public class Particle1_14Type extends AbstractParticleType {
    public Particle1_14Type() {
        this.readers.put(3, (int) blockHandler());
        this.readers.put(23, (int) blockHandler());
        this.readers.put(14, (int) dustHandler());
        this.readers.put(32, (int) itemHandler(Type.FLAT_VAR_INT_ITEM));
    }
}
