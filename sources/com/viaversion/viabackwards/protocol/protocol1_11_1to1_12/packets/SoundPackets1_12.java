package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.rewriters.LegacySoundRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;
import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/packets/SoundPackets1_12.class */
public class SoundPackets1_12 extends LegacySoundRewriter<Protocol1_11_1To1_12> {
    public SoundPackets1_12(Protocol1_11_1To1_12 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.NAMED_SOUND, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.VAR_INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.FLOAT);
                map(Type.FLOAT);
            }
        });
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.SOUND, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int oldId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        int newId = SoundPackets1_12.this.handleSounds(oldId);
                        if (newId == -1) {
                            wrapper.cancel();
                            return;
                        }
                        if (SoundPackets1_12.this.hasPitch(oldId)) {
                            wrapper.set(Type.FLOAT, 1, Float.valueOf(SoundPackets1_12.this.handlePitch(oldId)));
                        }
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(newId));
                    }
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        added(26, 277, 1.4f);
        added(27, -1);
        added(72, 70);
        added(73, 70);
        added(74, 70);
        added(75, 70);
        added(80, 70);
        added(150, -1);
        added(151, -1);
        added(152, -1);
        added(195, -1);
        added(274, 198, 0.8f);
        added(275, 199, 0.8f);
        added(276, 200, 0.8f);
        added(277, Opcode.JSR_W, 0.8f);
        added(278, 191, 0.9f);
        added(279, 203, 1.5f);
        added(280, 202, 0.8f);
        added(TokenId.GOTO, 133, 0.6f);
        added(320, 134, 1.7f);
        added(321, 219, 1.5f);
        added(TokenId.IMPORT, 136, 0.7f);
        added(TokenId.INSTANCEOF, 135, 1.6f);
        added(TokenId.INT, 138, 1.5f);
        added(TokenId.INTERFACE, 163, 1.5f);
        added(TokenId.LONG, 170, 1.5f);
        added(TokenId.NATIVE, 178, 1.5f);
        added(TokenId.NEW, 186, 1.5f);
        added(TokenId.PACKAGE, 192, 1.5f);
        added(TokenId.PRIVATE, 198, 1.5f);
        added(TokenId.PROTECTED, 226, 1.5f);
        added(TokenId.PUBLIC, 259, 1.5f);
        added(TokenId.RETURN, 198, 1.3f);
        added(TokenId.SHORT, 291, 1.5f);
        added(TokenId.STATIC, 321, 1.5f);
        added(TokenId.SUPER, TokenId.SWITCH, 1.5f);
        added(TokenId.SWITCH, TokenId.STRICT, 1.5f);
        added(TokenId.SYNCHRONIZED, TokenId.MOD_E, 1.5f);
        added(TokenId.THIS, TokenId.MINUSMINUS, 1.5f);
        added(TokenId.THROW, 376, 1.5f);
        added(341, 385, 1.5f);
        added(TokenId.TRANSIENT, 390, 1.5f);
        added(TokenId.TRY, TokenId.Identifier, 1.5f);
        added(TokenId.VOID, TokenId.LongConstant, 1.5f);
        added(TokenId.VOLATILE, 408, 1.5f);
        added(TokenId.WHILE, 414, 1.5f);
        added(TokenId.STRICT, 418, 1.5f);
        added(348, 427, 1.5f);
        added(349, 438, 1.5f);
        added(TokenId.NEQ, 442, 1.5f);
        added(TokenId.MOD_E, 155);
        added(TokenId.OROR, TokenId.FINALLY);
        added(TokenId.ANDAND, TokenId.FINALLY);
        added(544, -1);
        added(545, -1);
        added(546, TokenId.FLOAT, 1.5f);
    }
}
