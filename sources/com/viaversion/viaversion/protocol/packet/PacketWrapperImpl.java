package com.viaversion.viaversion.protocol.packet;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocol/packet/PacketWrapperImpl.class */
public class PacketWrapperImpl implements PacketWrapper {
    private static final Protocol[] PROTOCOL_ARRAY = new Protocol[0];
    private final ByteBuf inputBuffer;
    private final UserConnection userConnection;
    private PacketType packetType;

    /* renamed from: id */
    private int f202id;
    private boolean send = true;
    private final Deque<Pair<Type, Object>> readableObjects = new ArrayDeque();
    private final List<Pair<Type, Object>> packetValues = new ArrayList();

    public PacketWrapperImpl(int packetId, ByteBuf inputBuffer, UserConnection userConnection) {
        this.f202id = packetId;
        this.inputBuffer = inputBuffer;
        this.userConnection = userConnection;
    }

    public PacketWrapperImpl(PacketType packetType, ByteBuf inputBuffer, UserConnection userConnection) {
        this.packetType = packetType;
        this.f202id = packetType != null ? packetType.getId() : -1;
        this.inputBuffer = inputBuffer;
        this.userConnection = userConnection;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public <T> T get(Type<T> type, int index) throws Exception {
        int currentIndex = 0;
        for (Pair<Type, Object> packetValue : this.packetValues) {
            if (packetValue.key() == type) {
                if (currentIndex == index) {
                    return (T) packetValue.value();
                }
                currentIndex++;
            }
        }
        Exception e = new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index);
        throw new InformativeException(e).set("Type", type.getTypeName()).set("Index", Integer.valueOf(index)).set("Packet ID", Integer.valueOf(getId())).set("Packet Type", this.packetType).set("Data", this.packetValues);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    /* renamed from: is */
    public boolean mo79is(Type type, int index) {
        int currentIndex = 0;
        for (Pair<Type, Object> packetValue : this.packetValues) {
            if (packetValue.key() == type) {
                if (currentIndex == index) {
                    return true;
                }
                currentIndex++;
            }
        }
        return false;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public boolean isReadable(Type type, int index) {
        int currentIndex = 0;
        for (Pair<Type, Object> packetValue : this.readableObjects) {
            if (packetValue.key().getBaseClass() == type.getBaseClass()) {
                if (currentIndex == index) {
                    return true;
                }
                currentIndex++;
            }
        }
        return false;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public <T> void set(Type<T> type, int index, T value) throws Exception {
        int currentIndex = 0;
        for (Pair<Type, Object> packetValue : this.packetValues) {
            if (packetValue.key() == type) {
                if (currentIndex == index) {
                    packetValue.setValue(attemptTransform(type, value));
                    return;
                }
                currentIndex++;
            }
        }
        Exception e = new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index);
        throw new InformativeException(e).set("Type", type.getTypeName()).set("Index", Integer.valueOf(index)).set("Packet ID", Integer.valueOf(getId())).set("Packet Type", this.packetType);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public <T> T read(Type<T> type) throws Exception {
        if (type == Type.NOTHING) {
            return null;
        }
        if (this.readableObjects.isEmpty()) {
            Preconditions.checkNotNull(this.inputBuffer, "This packet does not have an input buffer.");
            try {
                return type.read(this.inputBuffer);
            } catch (Exception e) {
                throw new InformativeException(e).set("Type", type.getTypeName()).set("Packet ID", Integer.valueOf(getId())).set("Packet Type", this.packetType).set("Data", this.packetValues);
            }
        }
        Pair<Type, Object> read = this.readableObjects.poll();
        Type rtype = read.key();
        if (rtype == type || (type.getBaseClass() == rtype.getBaseClass() && type.getOutputClass() == rtype.getOutputClass())) {
            return (T) read.value();
        }
        if (rtype == Type.NOTHING) {
            return (T) read(type);
        }
        Exception e2 = new IOException("Unable to read type " + type.getTypeName() + ", found " + read.key().getTypeName());
        throw new InformativeException(e2).set("Type", type.getTypeName()).set("Packet ID", Integer.valueOf(getId())).set("Packet Type", this.packetType).set("Data", this.packetValues);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public <T> void write(Type<T> type, T value) {
        this.packetValues.add(new Pair<>(type, attemptTransform(type, value)));
    }

    private Object attemptTransform(Type<?> expectedType, Object value) {
        if (value != null && !expectedType.getOutputClass().isAssignableFrom(value.getClass())) {
            if (expectedType instanceof TypeConverter) {
                return ((TypeConverter) expectedType).from(value);
            }
            Via.getPlatform().getLogger().warning("Possible type mismatch: " + value.getClass().getName() + " -> " + expectedType.getOutputClass());
        }
        return value;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public <T> T passthrough(Type<T> type) throws Exception {
        T value = (T) read(type);
        write(type, value);
        return value;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void passthroughAll() throws Exception {
        this.packetValues.addAll(this.readableObjects);
        this.readableObjects.clear();
        if (this.inputBuffer.isReadable()) {
            passthrough(Type.REMAINING_BYTES);
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void writeToBuffer(ByteBuf buffer) throws Exception {
        if (this.f202id != -1) {
            Type.VAR_INT.writePrimitive(buffer, this.f202id);
        }
        if (!this.readableObjects.isEmpty()) {
            this.packetValues.addAll(this.readableObjects);
            this.readableObjects.clear();
        }
        int index = 0;
        for (Pair<Type, Object> packetValue : this.packetValues) {
            try {
                packetValue.key().write(buffer, packetValue.value());
                index++;
            } catch (Exception e) {
                throw new InformativeException(e).set("Index", Integer.valueOf(index)).set("Type", packetValue.key().getTypeName()).set("Packet ID", Integer.valueOf(getId())).set("Packet Type", this.packetType).set("Data", this.packetValues);
            }
        }
        writeRemaining(buffer);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void clearInputBuffer() {
        if (this.inputBuffer != null) {
            this.inputBuffer.clear();
        }
        this.readableObjects.clear();
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void clearPacket() {
        clearInputBuffer();
        this.packetValues.clear();
    }

    private void writeRemaining(ByteBuf output) {
        if (this.inputBuffer != null) {
            output.writeBytes(this.inputBuffer);
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void send(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
        send0(protocol, skipCurrentPipeline, true);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void scheduleSend(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
        send0(protocol, skipCurrentPipeline, false);
    }

    private void send0(Class<? extends Protocol> protocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
        if (isCancelled()) {
            return;
        }
        try {
            ByteBuf output = constructPacket(protocol, skipCurrentPipeline, Direction.CLIENTBOUND);
            if (currentThread) {
                user().sendRawPacket(output);
            } else {
                user().scheduleSendRawPacket(output);
            }
        } catch (Exception e) {
            if (!PipelineUtil.containsCause(e, CancelException.class)) {
                throw e;
            }
        }
    }

    private ByteBuf constructPacket(Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, Direction direction) throws Exception {
        Protocol[] protocols = (Protocol[]) user().getProtocolInfo().getPipeline().pipes().toArray(PROTOCOL_ARRAY);
        boolean reverse = direction == Direction.CLIENTBOUND;
        int index = -1;
        int i = 0;
        while (true) {
            if (i >= protocols.length) {
                break;
            } else if (protocols[i].getClass() != packetProtocol) {
                i++;
            } else {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new NoSuchElementException(packetProtocol.getCanonicalName());
        }
        if (skipCurrentPipeline) {
            index = reverse ? index - 1 : index + 1;
        }
        resetReader();
        apply(direction, user().getProtocolInfo().getState(), index, protocols, reverse);
        ByteBuf output = this.inputBuffer == null ? user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            writeToBuffer(output);
            ByteBuf retain = output.retain();
            output.release();
            return retain;
        } catch (Throwable th) {
            output.release();
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public ChannelFuture sendFuture(Class<? extends Protocol> packetProtocol) throws Exception {
        if (!isCancelled()) {
            ByteBuf output = constructPacket(packetProtocol, true, Direction.CLIENTBOUND);
            return user().sendRawPacketFuture(output);
        }
        return user().getChannel().newFailedFuture(new Exception("Cancelled packet"));
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void sendRaw() throws Exception {
        sendRaw(true);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void scheduleSendRaw() throws Exception {
        sendRaw(false);
    }

    private void sendRaw(boolean currentThread) throws Exception {
        if (isCancelled()) {
            return;
        }
        ByteBuf output = this.inputBuffer == null ? user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            writeToBuffer(output);
            if (currentThread) {
                user().sendRawPacket(output.retain());
            } else {
                user().scheduleSendRawPacket(output.retain());
            }
        } finally {
            output.release();
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public PacketWrapperImpl create(int packetId) {
        return new PacketWrapperImpl(packetId, (ByteBuf) null, user());
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public PacketWrapperImpl create(int packetId, PacketHandler handler) throws Exception {
        PacketWrapperImpl wrapper = create(packetId);
        handler.handle(wrapper);
        return wrapper;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public PacketWrapperImpl apply(Direction direction, State state, int index, List<Protocol> pipeline, boolean reverse) throws Exception {
        Protocol[] array = (Protocol[]) pipeline.toArray(PROTOCOL_ARRAY);
        return apply(direction, state, reverse ? array.length - 1 : index, array, reverse);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public PacketWrapperImpl apply(Direction direction, State state, int index, List<Protocol> pipeline) throws Exception {
        return apply(direction, state, index, (Protocol[]) pipeline.toArray(PROTOCOL_ARRAY), false);
    }

    private PacketWrapperImpl apply(Direction direction, State state, int index, Protocol[] pipeline, boolean reverse) throws Exception {
        if (reverse) {
            for (int i = index; i >= 0; i--) {
                pipeline[i].transform(direction, state, this);
                resetReader();
            }
        } else {
            for (int i2 = index; i2 < pipeline.length; i2++) {
                pipeline[i2].transform(direction, state, this);
                resetReader();
            }
        }
        return this;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void cancel() {
        this.send = false;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public boolean isCancelled() {
        return !this.send;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public UserConnection user() {
        return this.userConnection;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void resetReader() {
        for (int i = this.packetValues.size() - 1; i >= 0; i--) {
            this.readableObjects.addFirst(this.packetValues.get(i));
        }
        this.packetValues.clear();
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void sendToServerRaw() throws Exception {
        sendToServerRaw(true);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void scheduleSendToServerRaw() throws Exception {
        sendToServerRaw(false);
    }

    private void sendToServerRaw(boolean currentThread) throws Exception {
        if (isCancelled()) {
            return;
        }
        ByteBuf output = this.inputBuffer == null ? user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            writeToBuffer(output);
            if (currentThread) {
                user().sendRawPacketToServer(output.retain());
            } else {
                user().scheduleSendRawPacketToServer(output.retain());
            }
        } finally {
            output.release();
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void sendToServer(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
        sendToServer0(protocol, skipCurrentPipeline, true);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void scheduleSendToServer(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
        sendToServer0(protocol, skipCurrentPipeline, false);
    }

    private void sendToServer0(Class<? extends Protocol> protocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
        if (isCancelled()) {
            return;
        }
        try {
            ByteBuf output = constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
            if (currentThread) {
                user().sendRawPacketToServer(output);
            } else {
                user().scheduleSendRawPacketToServer(output);
            }
        } catch (Exception e) {
            if (!PipelineUtil.containsCause(e, CancelException.class)) {
                throw e;
            }
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public PacketType getPacketType() {
        return this.packetType;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
        this.f202id = packetType != null ? packetType.getId() : -1;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    public int getId() {
        return this.f202id;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketWrapper
    @Deprecated
    public void setId(int id) {
        this.packetType = null;
        this.f202id = id;
    }

    public ByteBuf getInputBuffer() {
        return this.inputBuffer;
    }

    public String toString() {
        return "PacketWrapper{packetValues=" + this.packetValues + ", readableObjects=" + this.readableObjects + ", id=" + this.f202id + ", packetType=" + this.packetType + '}';
    }
}
