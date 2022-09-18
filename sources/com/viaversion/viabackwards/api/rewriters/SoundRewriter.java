package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/SoundRewriter.class */
public class SoundRewriter extends com.viaversion.viaversion.rewriter.SoundRewriter {
    private final BackwardsProtocol protocol;

    public SoundRewriter(BackwardsProtocol protocol) {
        super(protocol);
        this.protocol = protocol;
    }

    public void registerNamedSound(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((BackwardsProtocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.SoundRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(SoundRewriter.this.getNamedSoundHandler());
            }
        });
    }

    public void registerStopSound(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((BackwardsProtocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.SoundRewriter.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(SoundRewriter.this.getStopSoundHandler());
            }
        });
    }

    public PacketHandler getNamedSoundHandler() {
        return wrapper -> {
            String soundId = (String) wrapper.get(Type.STRING, 0);
            String mappedId = this.protocol.getMappingData().getMappedNamedSound(soundId);
            if (mappedId == null) {
                return;
            }
            if (!mappedId.isEmpty()) {
                wrapper.set(Type.STRING, 0, mappedId);
            } else {
                wrapper.cancel();
            }
        };
    }

    public PacketHandler getStopSoundHandler() {
        return wrapper -> {
            byte flags = ((Byte) wrapper.passthrough(Type.BYTE)).byteValue();
            if ((flags & 2) == 0) {
                return;
            }
            if ((flags & 1) != 0) {
                wrapper.passthrough(Type.VAR_INT);
            }
            String soundId = (String) wrapper.read(Type.STRING);
            String mappedId = this.protocol.getMappingData().getMappedNamedSound(soundId);
            if (mappedId == null) {
                wrapper.write(Type.STRING, soundId);
            } else if (!mappedId.isEmpty()) {
                wrapper.write(Type.STRING, mappedId);
            } else {
                wrapper.cancel();
            }
        };
    }
}
