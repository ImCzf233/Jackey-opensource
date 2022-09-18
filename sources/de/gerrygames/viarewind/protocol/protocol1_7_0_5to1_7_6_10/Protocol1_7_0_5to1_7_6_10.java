package de.gerrygames.viarewind.protocol.protocol1_7_0_5to1_7_6_10;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_0_5to1_7_6_10/Protocol1_7_0_5to1_7_6_10.class */
public class Protocol1_7_0_5to1_7_6_10 extends AbstractProtocol<ClientboundPackets1_7, ClientboundPackets1_7, ServerboundPackets1_7, ServerboundPackets1_7> {
    public static final ValueTransformer<String, String> REMOVE_DASHES = new ValueTransformer<String, String>(Type.STRING) { // from class: de.gerrygames.viarewind.protocol.protocol1_7_0_5to1_7_6_10.Protocol1_7_0_5to1_7_6_10.1
        public String transform(PacketWrapper packetWrapper, String s) {
            return s.replace("-", "");
        }
    };

    public Protocol1_7_0_5to1_7_6_10() {
        super(ClientboundPackets1_7.class, ClientboundPackets1_7.class, ServerboundPackets1_7.class, ServerboundPackets1_7.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerClientbound(State.LOGIN, 2, 2, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_0_5to1_7_6_10.Protocol1_7_0_5to1_7_6_10.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING, Protocol1_7_0_5to1_7_6_10.REMOVE_DASHES);
                map(Type.STRING);
            }
        });
        registerClientbound((Protocol1_7_0_5to1_7_6_10) ClientboundPackets1_7.SPAWN_PLAYER, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_0_5to1_7_6_10.Protocol1_7_0_5to1_7_6_10.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.STRING, Protocol1_7_0_5to1_7_6_10.REMOVE_DASHES);
                map(Type.STRING);
                handler(packetWrapper -> {
                    int size = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    for (int i = 0; i < size * 3; i++) {
                        packetWrapper.read(Type.STRING);
                    }
                });
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Types1_7_6_10.METADATA_LIST);
            }
        });
        registerClientbound((Protocol1_7_0_5to1_7_6_10) ClientboundPackets1_7.TEAMS, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_0_5to1_7_6_10.Protocol1_7_0_5to1_7_6_10.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                handler(packetWrapper -> {
                    byte mode = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    if (mode == 0 || mode == 2) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.BYTE);
                    }
                    if (mode == 0 || mode == 3 || mode == 4) {
                        List<String> entryList = new ArrayList<>();
                        int size = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                        for (int i = 0; i < size; i++) {
                            entryList.add((String) packetWrapper.read(Type.STRING));
                        }
                        List<String> entryList2 = (List) entryList.stream().map(it -> {
                            return it.length() > 16 ? it.substring(0, 16) : it;
                        }).distinct().collect(Collectors.toList());
                        packetWrapper.write(Type.SHORT, Short.valueOf((short) entryList2.size()));
                        for (String entry : entryList2) {
                            packetWrapper.write(Type.STRING, entry);
                        }
                    }
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
    }
}
