package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/base/BaseVersionProvider.class */
public class BaseVersionProvider implements VersionProvider {
    @Override // com.viaversion.viaversion.api.protocol.version.VersionProvider
    public int getClosestServerProtocol(UserConnection connection) throws Exception {
        return Via.getAPI().getServerVersion().lowestSupportedVersion();
    }
}
