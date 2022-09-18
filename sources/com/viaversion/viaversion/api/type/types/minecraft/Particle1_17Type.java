package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/Particle1_17Type.class */
public class Particle1_17Type extends AbstractParticleType {
    public Particle1_17Type() {
        this.readers.put(4, (int) blockHandler());
        this.readers.put(25, (int) blockHandler());
        this.readers.put(15, (int) dustHandler());
        this.readers.put(16, (int) dustTransitionHandler());
        this.readers.put(36, (int) itemHandler(Type.FLAT_VAR_INT_ITEM));
        this.readers.put(37, (int) vibrationHandler(Type.POSITION1_14));
    }
}
