package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CompressionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.HandItemProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.CommandBlockStorage;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.InventoryTracker;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
import com.viaversion.viaversion.util.GsonUtil;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/Protocol1_9To1_8.class */
public class Protocol1_9To1_8 extends AbstractProtocol<ClientboundPackets1_8, ClientboundPackets1_9, ServerboundPackets1_8, ServerboundPackets1_9> {
    public static final ValueTransformer<String, JsonElement> FIX_JSON = new ValueTransformer<String, JsonElement>(Type.COMPONENT) { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8.1
        public JsonElement transform(PacketWrapper wrapper, String line) {
            return Protocol1_9To1_8.fixJson(line);
        }
    };
    private final EntityRewriter metadataRewriter = new MetadataRewriter1_9To1_8(this);

    public Protocol1_9To1_8() {
        super(ClientboundPackets1_8.class, ClientboundPackets1_9.class, ServerboundPackets1_8.class, ServerboundPackets1_9.class);
    }

    public static JsonElement fixJson(String line) {
        if (line == null || line.equalsIgnoreCase(Configurator.NULL)) {
            line = "{\"text\":\"\"}";
        } else if ((!line.startsWith("\"") || !line.endsWith("\"")) && (!line.startsWith("{") || !line.endsWith("}"))) {
            return constructJson(line);
        } else {
            if (line.startsWith("\"") && line.endsWith("\"")) {
                line = "{\"text\":" + line + "}";
            }
        }
        try {
            return (JsonElement) GsonUtil.getGson().fromJson(line, (Class<Object>) JsonObject.class);
        } catch (Exception e) {
            if (Via.getConfig().isForceJsonTransform()) {
                return constructJson(line);
            }
            Via.getPlatform().getLogger().warning("Invalid JSON String: \"" + line + "\" Please report this issue to the ViaVersion Github: " + e.getMessage());
            return (JsonElement) GsonUtil.getGson().fromJson("{\"text\":\"\"}", (Class<Object>) JsonObject.class);
        }
    }

    private static JsonElement constructJson(String text) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", text);
        return jsonObject;
    }

    public static Item getHandItem(UserConnection info) {
        return ((HandItemProvider) Via.getManager().getProviders().get(HandItemProvider.class)).getHandItem(info);
    }

    public static boolean isSword(int id) {
        return id == 267 || id == 268 || id == 272 || id == 276 || id == 283;
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.metadataRewriter.register();
        registerClientbound(State.LOGIN, 0, 0, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    if (wrapper.isReadable(Type.COMPONENT, 0)) {
                        return;
                    }
                    wrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson((String) wrapper.read(Type.STRING)));
                });
            }
        });
        SpawnPackets.register(this);
        InventoryPackets.register(this);
        EntityPackets.register(this);
        PlayerPackets.register(this);
        WorldPackets.register(this);
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void register(ViaProviders providers) {
        providers.register(HandItemProvider.class, new HandItemProvider());
        providers.register(CommandBlockProvider.class, new CommandBlockProvider());
        providers.register(EntityIdProvider.class, new EntityIdProvider());
        providers.register(BossBarProvider.class, new BossBarProvider());
        providers.register(MainHandProvider.class, new MainHandProvider());
        providers.register(CompressionProvider.class, new CompressionProvider());
        providers.require(MovementTransmitterProvider.class);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(getClass(), new EntityTracker1_9(userConnection));
        userConnection.put(new ClientChunks(userConnection));
        userConnection.put(new MovementTracker());
        userConnection.put(new InventoryTracker());
        userConnection.put(new CommandBlockStorage());
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityRewriter getEntityRewriter() {
        return this.metadataRewriter;
    }
}
