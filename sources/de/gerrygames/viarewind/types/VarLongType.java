package de.gerrygames.viarewind.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/types/VarLongType.class */
public class VarLongType extends Type<Long> {
    public static final VarLongType VAR_LONG = new VarLongType();

    public VarLongType() {
        super("VarLong", Long.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Long read(ByteBuf byteBuf) throws Exception {
        byte b0;
        long i = 0;
        int j = 0;
        do {
            b0 = byteBuf.readByte();
            int i2 = j;
            j++;
            i |= (b0 & Byte.MAX_VALUE) << (i2 * 7);
            if (j > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while ((b0 & 128) == 128);
        return Long.valueOf(i);
    }

    public void write(ByteBuf byteBuf, Long i) throws Exception {
        while ((i.longValue() & (-128)) != 0) {
            byteBuf.writeByte(((int) (i.longValue() & 127)) | 128);
            i = Long.valueOf(i.longValue() >>> 7);
        }
        byteBuf.writeByte(i.intValue());
    }
}
