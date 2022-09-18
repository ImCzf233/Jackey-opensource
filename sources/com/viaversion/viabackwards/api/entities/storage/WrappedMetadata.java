package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/entities/storage/WrappedMetadata.class */
public final class WrappedMetadata {
    private final List<Metadata> metadataList;

    public WrappedMetadata(List<Metadata> metadataList) {
        this.metadataList = metadataList;
    }

    public boolean has(Metadata data) {
        return this.metadataList.contains(data);
    }

    public void remove(Metadata data) {
        this.metadataList.remove(data);
    }

    public void remove(int index) {
        this.metadataList.removeIf(meta -> {
            return meta.m222id() == index;
        });
    }

    public void add(Metadata data) {
        this.metadataList.add(data);
    }

    public Metadata get(int index) {
        for (Metadata meta : this.metadataList) {
            if (index == meta.m222id()) {
                return meta;
            }
        }
        return null;
    }

    public List<Metadata> metadataList() {
        return this.metadataList;
    }

    public String toString() {
        return "MetaStorage{metaDataList=" + this.metadataList + '}';
    }
}
