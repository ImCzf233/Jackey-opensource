package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/Particle1_13Type.class */
public class Particle1_13Type extends AbstractParticleType {
    public Particle1_13Type() {
        this.readers.put(3, (int) blockHandler());
        this.readers.put(20, (int) blockHandler());
        this.readers.put(11, (int) dustHandler());
        this.readers.put(27, (int) itemHandler(Type.FLAT_ITEM));
    }
}
