package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/meta/MetaHandlerEventImpl.class */
public class MetaHandlerEventImpl implements MetaHandlerEvent {
    private final UserConnection connection;
    private final EntityType entityType;
    private final int entityId;
    private final List<Metadata> metadataList;
    private final Metadata meta;
    private List<Metadata> extraData;
    private boolean cancel;

    public MetaHandlerEventImpl(UserConnection connection, EntityType entityType, int entityId, Metadata meta, List<Metadata> metadataList) {
        this.connection = connection;
        this.entityType = entityType;
        this.entityId = entityId;
        this.meta = meta;
        this.metadataList = metadataList;
    }

    @Override // com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent
    public UserConnection user() {
        return this.connection;
    }

    @Override // com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent
    public int entityId() {
        return this.entityId;
    }

    @Override // com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent
    public EntityType entityType() {
        return this.entityType;
    }

    @Override // com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent
    public Metadata meta() {
        return this.meta;
    }

    @Override // com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent
    public void cancel() {
        this.cancel = true;
    }

    @Override // com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent
    public boolean cancelled() {
        return this.cancel;
    }

    @Override // com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent
    public Metadata metaAtIndex(int index) {
        for (Metadata meta : this.metadataList) {
            if (index == meta.m222id()) {
                return meta;
            }
        }
        return null;
    }

    @Override // com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent
    public List<Metadata> metadataList() {
        return Collections.unmodifiableList(this.metadataList);
    }

    @Override // com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent
    public List<Metadata> extraMeta() {
        return this.extraData;
    }

    @Override // com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent
    public void createExtraMeta(Metadata metadata) {
        List<Metadata> list;
        if (this.extraData != null) {
            list = this.extraData;
        } else {
            ArrayList arrayList = new ArrayList();
            list = arrayList;
            this.extraData = arrayList;
        }
        list.add(metadata);
    }
}
