package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/ComponentRewriter.class */
public class ComponentRewriter {
    protected final Protocol protocol;

    public ComponentRewriter(Protocol protocol) {
        this.protocol = protocol;
    }

    public ComponentRewriter() {
        this.protocol = null;
    }

    public void registerComponentPacket(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ComponentRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    ComponentRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    @Deprecated
    public void registerChatMessage(ClientboundPacketType packetType) {
        registerComponentPacket(packetType);
    }

    public void registerBossBar(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ComponentRewriter.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UUID);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int action = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    if (action == 0 || action == 3) {
                        ComponentRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                    }
                });
            }
        });
    }

    public void registerCombatEvent(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ComponentRewriter.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    if (((Integer) wrapper.passthrough(Type.VAR_INT)).intValue() == 2) {
                        wrapper.passthrough(Type.VAR_INT);
                        wrapper.passthrough(Type.INT);
                        ComponentRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                    }
                });
            }
        });
    }

    public void registerTitle(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ComponentRewriter.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int action = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    if (action >= 0 && action <= 2) {
                        ComponentRewriter.this.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                    }
                });
            }
        });
    }

    public JsonElement processText(String value) {
        try {
            JsonElement root = JsonParser.parseString(value);
            processText(root);
            return root;
        } catch (JsonSyntaxException e) {
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().severe("Error when trying to parse json: " + value);
                throw e;
            }
            return new JsonPrimitive(value);
        }
    }

    public void processText(JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return;
        }
        if (element.isJsonArray()) {
            processAsArray(element);
        } else if (element.isJsonPrimitive()) {
            handleText(element.getAsJsonPrimitive());
        } else {
            JsonObject object = element.getAsJsonObject();
            JsonPrimitive text = object.getAsJsonPrimitive("text");
            if (text != null) {
                handleText(text);
            }
            JsonElement translate = object.get("translate");
            if (translate != null) {
                handleTranslate(object, translate.getAsString());
                JsonElement with = object.get("with");
                if (with != null) {
                    processAsArray(with);
                }
            }
            JsonElement extra = object.get("extra");
            if (extra != null) {
                processAsArray(extra);
            }
            JsonObject hoverEvent = object.getAsJsonObject("hoverEvent");
            if (hoverEvent != null) {
                handleHoverEvent(hoverEvent);
            }
        }
    }

    protected void handleText(JsonPrimitive text) {
    }

    public void handleTranslate(JsonObject object, String translate) {
    }

    public void handleHoverEvent(JsonObject hoverEvent) {
        JsonObject contents;
        String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
        if (action.equals("show_text")) {
            JsonElement value = hoverEvent.get("value");
            processText(value != null ? value : hoverEvent.get("contents"));
        } else if (action.equals("show_entity") && (contents = hoverEvent.getAsJsonObject("contents")) != null) {
            processText(contents.get("name"));
        }
    }

    private void processAsArray(JsonElement element) {
        Iterator<JsonElement> it = element.getAsJsonArray().iterator();
        while (it.hasNext()) {
            JsonElement jsonElement = it.next();
            processText(jsonElement);
        }
    }

    public <T extends Protocol> T getProtocol() {
        return (T) this.protocol;
    }
}
