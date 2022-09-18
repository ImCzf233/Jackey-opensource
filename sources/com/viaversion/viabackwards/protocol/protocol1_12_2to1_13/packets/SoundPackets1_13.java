package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.NamedSoundMapping;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/SoundPackets1_13.class */
public class SoundPackets1_13 extends RewriterBase<Protocol1_12_2To1_13> {
    private static final String[] SOUND_SOURCES = {"master", "music", "record", "weather", "block", "hostile", "neutral", "player", "ambient", "voice"};

    public SoundPackets1_13(Protocol1_12_2To1_13 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.NAMED_SOUND, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.SoundPackets1_13.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    String sound = (String) wrapper.read(Type.STRING);
                    String mappedSound = NamedSoundMapping.getOldId(sound);
                    if (mappedSound == null) {
                        String mappedNamedSound = ((Protocol1_12_2To1_13) SoundPackets1_13.this.protocol).getMappingData().getMappedNamedSound(sound);
                        mappedSound = mappedNamedSound;
                        if (mappedNamedSound == null) {
                            wrapper.write(Type.STRING, sound);
                            return;
                        }
                    }
                    wrapper.write(Type.STRING, mappedSound);
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.STOP_SOUND, (ClientboundPackets1_13) ClientboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.SoundPackets1_13.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    String source;
                    String sound;
                    wrapper.write(Type.STRING, "MC|StopSound");
                    byte flags = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                    if ((flags & 1) != 0) {
                        source = SoundPackets1_13.SOUND_SOURCES[((Integer) wrapper.read(Type.VAR_INT)).intValue()];
                    } else {
                        source = "";
                    }
                    if ((flags & 2) != 0) {
                        String newSound = (String) wrapper.read(Type.STRING);
                        sound = ((Protocol1_12_2To1_13) SoundPackets1_13.this.protocol).getMappingData().getMappedNamedSound(newSound);
                        if (sound == null) {
                            sound = "";
                        }
                    } else {
                        sound = "";
                    }
                    wrapper.write(Type.STRING, source);
                    wrapper.write(Type.STRING, sound);
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.SOUND, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.SoundPackets1_13.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int newSound = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    int oldSound = ((Protocol1_12_2To1_13) SoundPackets1_13.this.protocol).getMappingData().getSoundMappings().getNewId(newSound);
                    if (oldSound == -1) {
                        wrapper.cancel();
                    } else {
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(oldSound));
                    }
                });
            }
        });
    }
}
