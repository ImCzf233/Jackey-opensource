package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.BannerHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.BedHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.PistonHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.SkullHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.SpawnerHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/providers/BackwardsBlockEntityProvider.class */
public class BackwardsBlockEntityProvider implements Provider {
    private final Map<String, BackwardsBlockEntityHandler> handlers = new HashMap();

    /* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/providers/BackwardsBlockEntityProvider$BackwardsBlockEntityHandler.class */
    public interface BackwardsBlockEntityHandler {
        CompoundTag transform(UserConnection userConnection, int i, CompoundTag compoundTag);
    }

    public BackwardsBlockEntityProvider() {
        this.handlers.put("minecraft:flower_pot", new FlowerPotHandler());
        this.handlers.put("minecraft:bed", new BedHandler());
        this.handlers.put("minecraft:banner", new BannerHandler());
        this.handlers.put("minecraft:skull", new SkullHandler());
        this.handlers.put("minecraft:mob_spawner", new SpawnerHandler());
        this.handlers.put("minecraft:piston", new PistonHandler());
    }

    public boolean isHandled(String key) {
        return this.handlers.containsKey(key);
    }

    public CompoundTag transform(UserConnection user, Position position, CompoundTag tag) throws Exception {
        Tag idTag = tag.get("id");
        if (!(idTag instanceof StringTag)) {
            return tag;
        }
        String id = (String) idTag.getValue();
        BackwardsBlockEntityHandler handler = this.handlers.get(id);
        if (handler == null) {
            if (Via.getManager().isDebug()) {
                ViaBackwards.getPlatform().getLogger().warning("Unhandled BlockEntity " + id + " full tag: " + tag);
            }
            return tag;
        }
        BackwardsBlockStorage storage = (BackwardsBlockStorage) user.get(BackwardsBlockStorage.class);
        Integer blockId = storage.get(position);
        if (blockId == null) {
            if (Via.getManager().isDebug()) {
                ViaBackwards.getPlatform().getLogger().warning("Handled BlockEntity does not have a stored block :( " + id + " full tag: " + tag);
            }
            return tag;
        }
        return handler.transform(user, blockId.intValue(), tag);
    }

    public CompoundTag transform(UserConnection user, Position position, String id) throws Exception {
        CompoundTag tag = new CompoundTag();
        tag.put("id", new StringTag(id));
        tag.put("x", new IntTag(Math.toIntExact(position.getX())));
        tag.put("y", new IntTag(Math.toIntExact(position.getY())));
        tag.put("z", new IntTag(Math.toIntExact(position.getZ())));
        return transform(user, position, tag);
    }
}
