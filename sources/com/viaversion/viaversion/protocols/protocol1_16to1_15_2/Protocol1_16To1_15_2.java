package com.viaversion.viaversion.protocols.protocol1_16to1_15_2;

import com.google.common.base.Joiner;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.TranslationMappings;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.GsonUtil;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import kotlin.text.Typography;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16to1_15_2/Protocol1_16To1_15_2.class */
public class Protocol1_16To1_15_2 extends AbstractProtocol<ClientboundPackets1_15, ClientboundPackets1_16, ServerboundPackets1_14, ServerboundPackets1_16> {
    private static final UUID ZERO_UUID = new UUID(0, 0);
    public static final MappingData MAPPINGS = new MappingData();
    private final EntityRewriter metadataRewriter = new MetadataRewriter1_16To1_15_2(this);
    private final ItemRewriter itemRewriter = new InventoryPackets(this);
    private TagRewriter tagRewriter;

    public Protocol1_16To1_15_2() {
        super(ClientboundPackets1_15.class, ClientboundPackets1_16.class, ServerboundPackets1_14.class, ServerboundPackets1_16.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        this.tagRewriter = new TagRewriter(this);
        this.tagRewriter.register(ClientboundPackets1_15.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter(this).register(ClientboundPackets1_15.STATISTICS);
        registerClientbound(State.LOGIN, 2, 2, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    UUID uuid = UUID.fromString((String) wrapper.read(Type.STRING));
                    wrapper.write(Type.UUID_INT_ARRAY, uuid);
                });
            }
        });
        registerClientbound(State.STATUS, 0, 0, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    JsonArray sample;
                    String[] split;
                    String original = (String) wrapper.passthrough(Type.STRING);
                    JsonObject object = (JsonObject) GsonUtil.getGson().fromJson(original, (Class<Object>) JsonObject.class);
                    JsonObject players = object.getAsJsonObject("players");
                    if (players == null || (sample = players.getAsJsonArray("sample")) == null) {
                        return;
                    }
                    JsonArray splitSamples = new JsonArray();
                    Iterator<JsonElement> it = sample.iterator();
                    while (it.hasNext()) {
                        JsonElement element = it.next();
                        JsonObject playerInfo = element.getAsJsonObject();
                        String name = playerInfo.getAsJsonPrimitive("name").getAsString();
                        if (name.indexOf(10) == -1) {
                            splitSamples.add(playerInfo);
                        } else {
                            String id = playerInfo.getAsJsonPrimitive("id").getAsString();
                            for (String s : name.split("\n")) {
                                JsonObject newSample = new JsonObject();
                                newSample.addProperty("name", s);
                                newSample.addProperty("id", id);
                                splitSamples.add(newSample);
                            }
                        }
                    }
                    if (splitSamples.size() != sample.size()) {
                        players.add("sample", splitSamples);
                        wrapper.set(Type.STRING, 0, object.toString());
                    }
                });
            }
        });
        final ComponentRewriter componentRewriter = new TranslationMappings(this);
        registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.CHAT_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.COMPONENT);
                map(Type.BYTE);
                ComponentRewriter componentRewriter2 = componentRewriter;
                handler(wrapper -> {
                    componentRewriter2.processText((JsonElement) wrapper.get(Type.COMPONENT, 0));
                    wrapper.write(Type.UUID, Protocol1_16To1_15_2.ZERO_UUID);
                });
            }
        });
        componentRewriter.registerBossBar(ClientboundPackets1_15.BOSSBAR);
        componentRewriter.registerTitle(ClientboundPackets1_15.TITLE);
        componentRewriter.registerCombatEvent(ClientboundPackets1_15.COMBAT_EVENT);
        SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_15.ENTITY_SOUND);
        registerServerbound((Protocol1_16To1_15_2) ServerboundPackets1_16.INTERACT_ENTITY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    int action = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    if (action == 0 || action == 2) {
                        if (action == 2) {
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                        }
                        wrapper.passthrough(Type.VAR_INT);
                    }
                    wrapper.read(Type.BOOLEAN);
                });
            }
        });
        if (Via.getConfig().isIgnoreLong1_16ChannelNames()) {
            registerServerbound((Protocol1_16To1_15_2) ServerboundPackets1_16.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2.5
                @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
                public void registerMap() {
                    handler(wrapper -> {
                        String channel = (String) wrapper.passthrough(Type.STRING);
                        if (channel.length() > 32) {
                            if (!Via.getConfig().isSuppressConversionWarnings()) {
                                Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel, as it is longer than 32 characters: " + channel);
                            }
                            wrapper.cancel();
                        } else if (channel.equals("minecraft:register") || channel.equals("minecraft:unregister")) {
                            String[] channels = new String((byte[]) wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("��");
                            List<String> checkedChannels = new ArrayList<>(channels.length);
                            for (String registeredChannel : channels) {
                                if (registeredChannel.length() > 32) {
                                    if (!Via.getConfig().isSuppressConversionWarnings()) {
                                        Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel register of '" + registeredChannel + "', as it is longer than 32 characters");
                                    }
                                } else {
                                    checkedChannels.add(registeredChannel);
                                }
                            }
                            if (checkedChannels.isEmpty()) {
                                wrapper.cancel();
                            } else {
                                wrapper.write(Type.REMAINING_BYTES, Joiner.on((char) 0).join(checkedChannels).getBytes(StandardCharsets.UTF_8));
                            }
                        }
                    });
                }
            });
        }
        registerServerbound((Protocol1_16To1_15_2) ServerboundPackets1_16.PLAYER_ABILITIES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.passthrough(Type.BYTE);
                    wrapper.write(Type.FLOAT, Float.valueOf(0.05f));
                    wrapper.write(Type.FLOAT, Float.valueOf(0.1f));
                });
            }
        });
        cancelServerbound(ServerboundPackets1_16.GENERATE_JIGSAW);
        cancelServerbound(ServerboundPackets1_16.UPDATE_JIGSAW_BLOCK);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void onMappingDataLoaded() {
        int[] wallPostOverrideTag = new int[47];
        int arrayIndex = 0 + 1;
        wallPostOverrideTag[0] = 140;
        int arrayIndex2 = arrayIndex + 1;
        wallPostOverrideTag[arrayIndex] = 179;
        int arrayIndex3 = arrayIndex2 + 1;
        wallPostOverrideTag[arrayIndex2] = 264;
        for (int i = 153; i <= 158; i++) {
            int i2 = arrayIndex3;
            arrayIndex3++;
            wallPostOverrideTag[i2] = i;
        }
        for (int i3 = 163; i3 <= 168; i3++) {
            int i4 = arrayIndex3;
            arrayIndex3++;
            wallPostOverrideTag[i4] = i3;
        }
        for (int i5 = 408; i5 <= 439; i5++) {
            int i6 = arrayIndex3;
            arrayIndex3++;
            wallPostOverrideTag[i6] = i5;
        }
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wall_post_override", wallPostOverrideTag);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:beacon_base_blocks", 133, 134, 148, CharacterType.f308S);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:climbable", 160, 241, 658);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fire", 142);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:campfires", 679);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fence_gates", 242, 467, 468, 469, 470, 471);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:unstable_bottom_center", 242, 467, 468, 469, 470, 471);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wooden_trapdoors", 193, 194, 195, Opcode.WIDE, 197, 198);
        this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:wooden_trapdoors", Typography.times, 216, 217, 218, 219, 220);
        this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:beacon_payment_items", 529, 530, 531, 760);
        this.tagRewriter.addTag(RegistryType.ENTITY, "minecraft:impact_projectiles", 2, 72, 71, 37, 69, 79, 83, 15, 93);
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:guarded_by_piglins");
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_speed_blocks");
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_fire_base_blocks");
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
        this.tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:non_flammable_wood");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:bamboo_plantable_on", "minecraft:beds", "minecraft:bee_growables", "minecraft:beehives", "minecraft:coral_plants", "minecraft:crops", "minecraft:dragon_immune", "minecraft:flowers", "minecraft:portals", "minecraft:shulker_boxes", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:underwater_bonemeals", "minecraft:wither_immune", "minecraft:wooden_fences", "minecraft:wooden_trapdoors");
        this.tagRewriter.addEmptyTags(RegistryType.ENTITY, "minecraft:arrows", "minecraft:beehive_inhabitors", "minecraft:raiders", "minecraft:skeletons");
        this.tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:beds", "minecraft:coals", "minecraft:fences", "minecraft:flowers", "minecraft:lectern_books", "minecraft:music_discs", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:walls", "minecraft:wooden_fences");
        Types1_16.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.VAR_INT_ITEM);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(getClass(), new EntityTrackerBase(userConnection, Entity1_16Types.PLAYER));
        userConnection.put(new InventoryTracker1_16());
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityRewriter getEntityRewriter() {
        return this.metadataRewriter;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}
