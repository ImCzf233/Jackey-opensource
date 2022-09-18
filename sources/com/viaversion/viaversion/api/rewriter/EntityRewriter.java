package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/rewriter/EntityRewriter.class */
public interface EntityRewriter<T extends Protocol> extends Rewriter<T> {
    EntityType typeFromId(int i);

    int newEntityId(int i);

    void handleMetadata(int i, List<Metadata> list, UserConnection userConnection);

    default EntityType objectTypeFromId(int type) {
        return typeFromId(type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    default <E extends EntityTracker> E tracker(UserConnection connection) {
        return (E) connection.getEntityTracker(protocol().getClass());
    }
}
