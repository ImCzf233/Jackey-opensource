package de.gerrygames.viarewind.protocol.protocol1_8to1_9;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.InventoryPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.ScoreboardPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.SpawnPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import de.gerrygames.viarewind.utils.Ticker;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/Protocol1_8TO1_9.class */
public class Protocol1_8TO1_9 extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> {
    public static final Timer TIMER = new Timer("ViaRewind-1_8TO1_9", true);
    public static final Set<String> VALID_ATTRIBUTES = new HashSet();
    public static final ValueTransformer<Double, Integer> TO_OLD_INT = new ValueTransformer<Double, Integer>(Type.INT) { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9.1
        public Integer transform(PacketWrapper wrapper, Double inputValue) {
            return Integer.valueOf((int) (inputValue.doubleValue() * 32.0d));
        }
    };
    public static final ValueTransformer<Float, Byte> DEGREES_TO_ANGLE = new ValueTransformer<Float, Byte>(Type.BYTE) { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9.2
        public Byte transform(PacketWrapper packetWrapper, Float degrees) throws Exception {
            return Byte.valueOf((byte) ((degrees.floatValue() / 360.0f) * 256.0f));
        }
    };

    static {
        VALID_ATTRIBUTES.add("generic.maxHealth");
        VALID_ATTRIBUTES.add("generic.followRange");
        VALID_ATTRIBUTES.add("generic.knockbackResistance");
        VALID_ATTRIBUTES.add("generic.movementSpeed");
        VALID_ATTRIBUTES.add("generic.attackDamage");
        VALID_ATTRIBUTES.add("horse.jumpStrength");
        VALID_ATTRIBUTES.add("zombie.spawnReinforcements");
    }

    public Protocol1_8TO1_9() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_8.class, ServerboundPackets1_9.class, ServerboundPackets1_8.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        EntityPackets.register(this);
        InventoryPackets.register(this);
        PlayerPackets.register(this);
        ScoreboardPackets.register(this);
        SpawnPackets.register(this);
        WorldPackets.register(this);
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        Ticker.init();
        userConnection.put(new Windows(userConnection));
        userConnection.put(new EntityTracker(userConnection));
        userConnection.put(new Levitation(userConnection));
        userConnection.put(new PlayerPosition(userConnection));
        userConnection.put(new Cooldown(userConnection));
        userConnection.put(new BlockPlaceDestroyTracker(userConnection));
        userConnection.put(new BossBarStorage(userConnection));
        userConnection.put(new ClientWorld(userConnection));
    }
}
