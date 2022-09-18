package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/types/IntArrayType.class */
public class IntArrayType extends Type<int[]> {
    public IntArrayType() {
        super(int[].class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public int[] read(ByteBuf byteBuf) throws Exception {
        int readByte = byteBuf.readByte();
        int[] array = new int[readByte];
        byte b = 0;
        while (true) {
            byte i = b;
            if (i < readByte) {
                array[i] = byteBuf.readInt();
                b = (byte) (i + 1);
            } else {
                return array;
            }
        }
    }

    public void write(ByteBuf byteBuf, int[] array) throws Exception {
        byteBuf.writeByte(array.length);
        for (int i : array) {
            byteBuf.writeInt(i);
        }
    }
}
