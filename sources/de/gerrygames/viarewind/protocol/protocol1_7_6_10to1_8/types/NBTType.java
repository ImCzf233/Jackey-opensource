package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.NBTIO;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/types/NBTType.class */
public class NBTType extends Type<CompoundTag> {
    public NBTType() {
        super(CompoundTag.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public CompoundTag read(ByteBuf buffer) {
        short length = buffer.readShort();
        if (length < 0) {
            return null;
        }
        ByteBufInputStream byteBufInputStream = new ByteBufInputStream(buffer);
        DataInputStream dataInputStream = new DataInputStream(byteBufInputStream);
        try {
            return NBTIO.readTag((DataInput) dataInputStream);
        } catch (Throwable throwable) {
            try {
                throwable.printStackTrace();
                try {
                    dataInputStream.close();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } finally {
                try {
                    dataInputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void write(ByteBuf buffer, CompoundTag nbt) throws Exception {
        if (nbt == null) {
            buffer.writeShort(-1);
            return;
        }
        ByteBuf buf = buffer.alloc().buffer();
        ByteBufOutputStream bytebufStream = new ByteBufOutputStream(buf);
        DataOutputStream dataOutputStream = new DataOutputStream(bytebufStream);
        NBTIO.writeTag((DataOutput) dataOutputStream, nbt);
        dataOutputStream.close();
        buffer.writeShort(buf.readableBytes());
        buffer.writeBytes(buf);
        buf.release();
    }
}
