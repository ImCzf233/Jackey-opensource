package de.enzaxd.viaforge.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.bungee.providers.BungeeMovementTransmitter;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import de.enzaxd.viaforge.ViaForge;

/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/platform/ProviderLoader.class */
public class ProviderLoader implements ViaPlatformLoader {
    @Override // com.viaversion.viaversion.api.platform.ViaPlatformLoader
    public void load() {
        Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
        Via.getManager().getProviders().use(VersionProvider.class, new BaseVersionProvider() { // from class: de.enzaxd.viaforge.platform.ProviderLoader.1
            @Override // com.viaversion.viaversion.protocols.base.BaseVersionProvider, com.viaversion.viaversion.api.protocol.version.VersionProvider
            public int getClosestServerProtocol(UserConnection connection) throws Exception {
                if (connection.isClientSide()) {
                    return ViaForge.getInstance().getVersion();
                }
                return super.getClosestServerProtocol(connection);
            }
        });
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatformLoader
    public void unload() {
    }
}
