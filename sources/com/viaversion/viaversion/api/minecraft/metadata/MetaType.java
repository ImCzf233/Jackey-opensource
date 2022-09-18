package com.viaversion.viaversion.api.minecraft.metadata;

import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/metadata/MetaType.class */
public interface MetaType {
    Type type();

    int typeId();

    static MetaType create(int typeId, Type<?> type) {
        return new MetaTypeImpl(typeId, type);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/metadata/MetaType$MetaTypeImpl.class */
    public static final class MetaTypeImpl implements MetaType {
        private final int typeId;
        private final Type<?> type;

        MetaTypeImpl(int typeId, Type<?> type) {
            this.typeId = typeId;
            this.type = type;
        }

        @Override // com.viaversion.viaversion.api.minecraft.metadata.MetaType
        public int typeId() {
            return this.typeId;
        }

        @Override // com.viaversion.viaversion.api.minecraft.metadata.MetaType
        public Type<?> type() {
            return this.type;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MetaTypeImpl metaType = (MetaTypeImpl) o;
            if (this.typeId == metaType.typeId) {
                return this.type.equals(metaType.type);
            }
            return false;
        }

        public int hashCode() {
            int result = this.typeId;
            return (31 * result) + this.type.hashCode();
        }
    }
}
