package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/ParticleType.class */
public class ParticleType extends Type<Particle> {
    private final Int2ObjectMap<ParticleReader> readers;

    @FunctionalInterface
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/ParticleType$ParticleReader.class */
    public interface ParticleReader {
        void read(ByteBuf byteBuf, Particle particle) throws Exception;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/ParticleType$Readers.class */
    public static final class Readers {
        public static final ParticleReader BLOCK = buf, particle -> {
            particle.add(Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf)));
        };
        public static final ParticleReader ITEM = ParticleType.itemHandler(Type.FLAT_ITEM);
        public static final ParticleReader VAR_INT_ITEM = ParticleType.itemHandler(Type.FLAT_VAR_INT_ITEM);
        public static final ParticleReader DUST = buf, particle -> {
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        };
        public static final ParticleReader DUST_TRANSITION = buf, particle -> {
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        };
        public static final ParticleReader VIBRATION = buf, particle -> {
            particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
            String resourceLocation = Type.STRING.read(buf);
            particle.add(Type.STRING, resourceLocation);
            if (resourceLocation.startsWith("minecraft:")) {
                resourceLocation = resourceLocation.substring(10);
            }
            if (resourceLocation.equals("block")) {
                particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
            } else if (resourceLocation.equals("entity")) {
                particle.add(Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf)));
            } else {
                Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
            }
            particle.add(Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf)));
        };
    }

    public ParticleType(Int2ObjectMap<ParticleReader> readers) {
        super("Particle", Particle.class);
        this.readers = readers;
    }

    public ParticleType() {
        this(new Int2ObjectArrayMap());
    }

    public ParticleTypeFiller filler(Protocol<?, ?, ?, ?> protocol) {
        return filler(protocol, true);
    }

    public ParticleTypeFiller filler(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
        return new ParticleTypeFiller(protocol, useMappedNames);
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

    public static ParticleReader itemHandler(Type<Item> itemType) {
        return buf, particle -> {
            particle.add(itemType, itemType.read(buf));
        };
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/ParticleType$ParticleTypeFiller.class */
    public final class ParticleTypeFiller {
        private final ParticleMappings mappings;
        private final boolean useMappedNames;

        private ParticleTypeFiller(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
            ParticleType.this = this$0;
            this.mappings = protocol.getMappingData().getParticleMappings();
            this.useMappedNames = useMappedNames;
        }

        public ParticleTypeFiller reader(String identifier, ParticleReader reader) {
            ParticleType.this.readers.put(this.useMappedNames ? this.mappings.mappedId(identifier) : this.mappings.m233id(identifier), (int) reader);
            return this;
        }

        public ParticleTypeFiller reader(int id, ParticleReader reader) {
            ParticleType.this.readers.put(id, (int) reader);
            return this;
        }
    }
}
