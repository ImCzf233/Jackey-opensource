package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.Arrays;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/data/Mappings.class */
public interface Mappings {

    @FunctionalInterface
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/data/Mappings$MappingsSupplier.class */
    public interface MappingsSupplier<T extends Mappings> {
        T supply(int[] iArr, int i);
    }

    int getNewId(int i);

    void setNewId(int i, int i2);

    int size();

    int mappedSize();

    static <T extends Mappings> Builder<T> builder(MappingsSupplier<T> supplier) {
        return new Builder<>(supplier);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/data/Mappings$Builder.class */
    public static class Builder<T extends Mappings> {
        protected final MappingsSupplier<T> supplier;
        protected JsonElement unmapped;
        protected JsonElement mapped;
        protected JsonObject diffMappings;
        protected int mappedSize = -1;
        protected int size = -1;
        protected boolean warnOnMissing = true;

        public Builder(MappingsSupplier<T> supplier) {
            this.supplier = supplier;
        }

        public Builder<T> customEntrySize(int size) {
            this.size = size;
            return this;
        }

        public Builder<T> customMappedSize(int size) {
            this.mappedSize = size;
            return this;
        }

        public Builder<T> warnOnMissing(boolean warnOnMissing) {
            this.warnOnMissing = warnOnMissing;
            return this;
        }

        public Builder<T> unmapped(JsonArray unmappedArray) {
            this.unmapped = unmappedArray;
            return this;
        }

        public Builder<T> unmapped(JsonObject unmappedObject) {
            this.unmapped = unmappedObject;
            return this;
        }

        public Builder<T> mapped(JsonArray mappedArray) {
            this.mapped = mappedArray;
            return this;
        }

        public Builder<T> mapped(JsonObject mappedObject) {
            this.mapped = mappedObject;
            return this;
        }

        public Builder<T> diffMappings(JsonObject diffMappings) {
            this.diffMappings = diffMappings;
            return this;
        }

        public T build() {
            int size = this.size != -1 ? this.size : size(this.unmapped);
            int mappedSize = this.mappedSize != -1 ? this.mappedSize : size(this.mapped);
            int[] mappings = new int[size];
            Arrays.fill(mappings, -1);
            if (this.unmapped.isJsonArray()) {
                if (this.mapped.isJsonObject()) {
                    MappingDataLoader.mapIdentifiers(mappings, toJsonObject(this.unmapped.getAsJsonArray()), this.mapped.getAsJsonObject(), this.diffMappings, this.warnOnMissing);
                } else {
                    MappingDataLoader.mapIdentifiers(mappings, this.unmapped.getAsJsonArray(), this.mapped.getAsJsonArray(), this.diffMappings, this.warnOnMissing);
                }
            } else if (this.mapped.isJsonArray()) {
                MappingDataLoader.mapIdentifiers(mappings, this.unmapped.getAsJsonObject(), toJsonObject(this.mapped.getAsJsonArray()), this.diffMappings, this.warnOnMissing);
            } else {
                MappingDataLoader.mapIdentifiers(mappings, this.unmapped.getAsJsonObject(), this.mapped.getAsJsonObject(), this.diffMappings, this.warnOnMissing);
            }
            return this.supplier.supply(mappings, mappedSize);
        }

        public int size(JsonElement element) {
            return element.isJsonObject() ? element.getAsJsonObject().size() : element.getAsJsonArray().size();
        }

        public JsonObject toJsonObject(JsonArray array) {
            JsonObject object = new JsonObject();
            for (int i = 0; i < array.size(); i++) {
                JsonElement element = array.get(i);
                object.add(Integer.toString(i), element);
            }
            return object;
        }
    }
}
