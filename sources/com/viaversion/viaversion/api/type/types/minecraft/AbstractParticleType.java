package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import io.netty.buffer.ByteBuf;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/AbstractParticleType.class */
public abstract class AbstractParticleType extends Type<Particle> {
    protected final Int2ObjectMap<ParticleReader> readers = new Int2ObjectOpenHashMap();

    @FunctionalInterface
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/AbstractParticleType$ParticleReader.class */
    public interface ParticleReader {
        void read(ByteBuf byteBuf, Particle particle) throws Exception;
    }

    public AbstractParticleType() {
        super("Particle", Particle.class);
    }

    public void write(ByteBuf buffer, Particle object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.getId());
        for (Particle.ParticleData data : object.getArguments()) {
            data.getType().write(buffer, data.getValue());
        }
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Particle read(ByteBuf buffer) throws Exception {
        int type = Type.VAR_INT.readPrimitive(buffer);
        Particle particle = new Particle(type);
        ParticleReader reader = this.readers.get(type);
        if (reader != null) {
            reader.read(buffer, particle);
        }
        return particle;
    }

    public ParticleReader blockHandler() {
        return buf, particle -> {
            particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf))));
        };
    }

    public ParticleReader itemHandler(Type<Item> itemType) {
        return buf, particle -> {
            particle.getArguments().add(new Particle.ParticleData(itemType, itemType.read(buf)));
        };
    }

    public ParticleReader dustHandler() {
        return buf, particle -> {
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
        };
    }

    public ParticleReader dustTransitionHandler() {
        return buf, particle -> {
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
            particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
        };
    }

    public ParticleReader vibrationHandler(Type<Position> positionType) {
        return buf, particle -> {
            particle.getArguments().add(new Particle.ParticleData(positionType, positionType.read(buf)));
            String resourceLocation = Type.STRING.read(buf);
            if (resourceLocation.equals("block")) {
                particle.getArguments().add(new Particle.ParticleData(positionType, positionType.read(buf)));
            } else if (resourceLocation.equals("entity")) {
                particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf))));
            } else {
                Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
            }
            particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf))));
        };
    }
}
