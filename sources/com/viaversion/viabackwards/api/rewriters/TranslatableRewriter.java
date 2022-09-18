package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/TranslatableRewriter.class */
public class TranslatableRewriter extends ComponentRewriter {
    private static final Map<String, Map<String, String>> TRANSLATABLES = new HashMap();
    protected final Map<String, String> newTranslatables;

    public static void loadTranslatables() {
        JsonObject jsonObject = VBMappingDataLoader.loadData("translation-mappings.json");
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            Map<String, String> versionMappings = new HashMap<>();
            TRANSLATABLES.put(entry.getKey(), versionMappings);
            for (Map.Entry<String, JsonElement> translationEntry : entry.getValue().getAsJsonObject().entrySet()) {
                versionMappings.put(translationEntry.getKey(), translationEntry.getValue().getAsString());
            }
        }
    }

    public TranslatableRewriter(BackwardsProtocol protocol) {
        this(protocol, protocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
    }

    public TranslatableRewriter(BackwardsProtocol protocol, String sectionIdentifier) {
        super(protocol);
        Map<String, String> newTranslatables = TRANSLATABLES.get(sectionIdentifier);
        if (newTranslatables == null) {
            ViaBackwards.getPlatform().getLogger().warning("Error loading " + sectionIdentifier + " translatables!");
            this.newTranslatables = new HashMap();
            return;
        }
        this.newTranslatables = newTranslatables;
    }

    public void registerPing() {
        this.protocol.registerClientbound(State.LOGIN, 0, 0, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.TranslatableRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    TranslatableRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    public void registerDisconnect(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.TranslatableRewriter.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    TranslatableRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.rewriter.ComponentRewriter
    public void registerChatMessage(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.TranslatableRewriter.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    TranslatableRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    public void registerLegacyOpenWindow(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.TranslatableRewriter.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                handler(wrapper -> {
                    TranslatableRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    public void registerOpenWindow(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.TranslatableRewriter.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    TranslatableRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    public void registerTabList(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.TranslatableRewriter.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    TranslatableRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                    TranslatableRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    public void registerCombatKill(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.TranslatableRewriter.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.INT);
                handler(wrapper -> {
                    TranslatableRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.rewriter.ComponentRewriter
    public void handleTranslate(JsonObject root, String translate) {
        String newTranslate = this.newTranslatables.get(translate);
        if (newTranslate != null) {
            root.addProperty("translate", newTranslate);
        }
    }
}
