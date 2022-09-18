package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/Particle1_18Type.class */
public class Particle1_18Type extends AbstractParticleType {
    public Particle1_18Type() {
        this.readers.put(2, (int) blockHandler());
        this.readers.put(3, (int) blockHandler());
        this.readers.put(24, (int) blockHandler());
        this.readers.put(14, (int) dustHandler());
        this.readers.put(15, (int) dustTransitionHandler());
        this.readers.put(35, (int) itemHandler(Type.FLAT_VAR_INT_ITEM));
        this.readers.put(36, (int) vibrationHandler(Type.POSITION1_14));
    }
}
