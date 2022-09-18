package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/Particle.class */
public class Particle {
    private List<ParticleData> arguments = new ArrayList(4);

    /* renamed from: id */
    private int f58id;

    public Particle(int id) {
        this.f58id = id;
    }

    public int getId() {
        return this.f58id;
    }

    public void setId(int id) {
        this.f58id = id;
    }

    public List<ParticleData> getArguments() {
        return this.arguments;
    }

    @Deprecated
    public void setArguments(List<ParticleData> arguments) {
        this.arguments = arguments;
    }

    public <T> void add(Type<T> type, T value) {
        this.arguments.add(new ParticleData(type, value));
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/Particle$ParticleData.class */
    public static class ParticleData {
        private Type type;
        private Object value;

        public ParticleData(Type type, Object value) {
            this.type = type;
            this.value = value;
        }

        public Type getType() {
            return this.type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Object getValue() {
            return this.value;
        }

        public <T> T get() {
            return (T) this.value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
