package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.NBTIO;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/types/CompressedNBTType.class */
public class CompressedNBTType extends Type<CompoundTag> {
    public CompressedNBTType() {
        super(CompoundTag.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public CompoundTag read(ByteBuf buffer) throws IOException {
        short length = buffer.readShort();
        if (length <= 0) {
            return null;
        }
        ByteBuf compressed = buffer.readSlice(length);
        GZIPInputStream gzipStream = new GZIPInputStream(new ByteBufInputStream(compressed));
        try {
            CompoundTag readTag = NBTIO.readTag(gzipStream);
            gzipStream.close();
            return readTag;
        } catch (Throwable th) {
            try {
                gzipStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void write(ByteBuf buffer, CompoundTag nbt) throws Exception {
        if (nbt == null) {
            buffer.writeShort(-1);
            return;
        }
        ByteBuf compressedBuf = buffer.alloc().buffer();
        try {
            GZIPOutputStream gzipStream = new GZIPOutputStream(new ByteBufOutputStream(compressedBuf));
            NBTIO.writeTag(gzipStream, nbt);
            gzipStream.close();
            buffer.writeShort(compressedBuf.readableBytes());
            buffer.writeBytes(compressedBuf);
            compressedBuf.release();
        } catch (Throwable th) {
            compressedBuf.release();
            throw th;
        }
    }
}
