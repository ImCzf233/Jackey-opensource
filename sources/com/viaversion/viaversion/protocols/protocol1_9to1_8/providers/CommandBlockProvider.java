package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.CommandBlockStorage;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import io.netty.buffer.ByteBuf;
import java.util.Optional;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/providers/CommandBlockProvider.class */
public class CommandBlockProvider implements Provider {
    public void addOrUpdateBlock(UserConnection user, Position position, CompoundTag tag) throws Exception {
        checkPermission(user);
        if (isEnabled()) {
            getStorage(user).addOrUpdateBlock(position, tag);
        }
    }

    public Optional<CompoundTag> get(UserConnection user, Position position) throws Exception {
        checkPermission(user);
        if (isEnabled()) {
            return getStorage(user).getCommandBlock(position);
        }
        return Optional.empty();
    }

    public void unloadChunk(UserConnection user, int x, int z) throws Exception {
        checkPermission(user);
        if (isEnabled()) {
            getStorage(user).unloadChunk(x, z);
        }
    }

    private CommandBlockStorage getStorage(UserConnection connection) {
        return (CommandBlockStorage) connection.get(CommandBlockStorage.class);
    }

    public void sendPermission(UserConnection user) throws Exception {
        if (!isEnabled()) {
            return;
        }
        PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_STATUS, (ByteBuf) null, user);
        EntityTracker1_9 tracker = (EntityTracker1_9) user.getEntityTracker(Protocol1_9To1_8.class);
        wrapper.write(Type.INT, Integer.valueOf(tracker.getProvidedEntityId()));
        wrapper.write(Type.BYTE, (byte) 26);
        wrapper.scheduleSend(Protocol1_9To1_8.class);
        ((CommandBlockStorage) user.get(CommandBlockStorage.class)).setPermissions(true);
    }

    private void checkPermission(UserConnection user) throws Exception {
        if (!isEnabled()) {
            return;
        }
        CommandBlockStorage storage = getStorage(user);
        if (!storage.isPermissions()) {
            sendPermission(user);
        }
    }

    public boolean isEnabled() {
        return true;
    }

    public void unloadChunks(UserConnection userConnection) {
        if (isEnabled()) {
            getStorage(userConnection).unloadChunks();
        }
    }
}
