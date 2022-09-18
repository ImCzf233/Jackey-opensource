package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.utils.PacketUtil;
import de.gerrygames.viarewind.utils.Tickable;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/storage/Levitation.class */
public class Levitation extends StoredObject implements Tickable {
    private int amplifier;
    private volatile boolean active = false;

    public Levitation(UserConnection user) {
        super(user);
    }

    @Override // de.gerrygames.viarewind.utils.Tickable
    public void tick() {
        if (!this.active) {
            return;
        }
        int vY = (this.amplifier + 1) * TokenId.EXOR_E;
        PacketWrapper packet = PacketWrapper.create(18, (ByteBuf) null, getUser());
        packet.write(Type.VAR_INT, Integer.valueOf(((EntityTracker) getUser().get(EntityTracker.class)).getPlayerId()));
        packet.write(Type.SHORT, (short) 0);
        packet.write(Type.SHORT, Short.valueOf((short) vY));
        packet.write(Type.SHORT, (short) 0);
        PacketUtil.sendPacket(packet, Protocol1_8TO1_9.class);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }
}
