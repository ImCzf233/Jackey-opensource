package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/version/VersionProvider.class */
public interface VersionProvider extends Provider {
    int getClosestServerProtocol(UserConnection userConnection) throws Exception;
}
