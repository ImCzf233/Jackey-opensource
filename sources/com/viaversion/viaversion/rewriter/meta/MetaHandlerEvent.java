package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/meta/MetaHandlerEvent.class */
public interface MetaHandlerEvent {
    UserConnection user();

    int entityId();

    EntityType entityType();

    Metadata meta();

    void cancel();

    boolean cancelled();

    Metadata metaAtIndex(int i);

    List<Metadata> metadataList();

    List<Metadata> extraMeta();

    void createExtraMeta(Metadata metadata);

    default int index() {
        return meta().m222id();
    }

    default void setIndex(int index) {
        meta().setId(index);
    }
}
