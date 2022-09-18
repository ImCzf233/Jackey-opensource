package de.enzaxd.viaforge.platform;

import com.viaversion.viaversion.ViaAPIBase;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/platform/ViaAPI.class */
public class ViaAPI extends ViaAPIBase<UUID> {
    @Override // com.viaversion.viaversion.api.ViaAPI
    public /* bridge */ /* synthetic */ void sendRawPacket(Object x0, ByteBuf x1) {
        super.sendRawPacket((UUID) x0, x1);
    }

    @Override // com.viaversion.viaversion.api.ViaAPI
    public /* bridge */ /* synthetic */ int getPlayerVersion(Object x0) {
        return super.getPlayerVersion((UUID) x0);
    }
}
