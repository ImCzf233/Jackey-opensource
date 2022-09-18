package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/base/BaseProtocol1_16.class */
public class BaseProtocol1_16 extends BaseProtocol1_7 {
    @Override // com.viaversion.viaversion.protocols.base.BaseProtocol1_7
    protected UUID passthroughLoginUUID(PacketWrapper wrapper) throws Exception {
        return (UUID) wrapper.passthrough(Type.UUID_INT_ARRAY);
    }
}
