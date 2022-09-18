package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.chunks;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.CustomByteType;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
import de.gerrygames.viarewind.replacement.Replacement;
import de.gerrygames.viarewind.storage.BlockState;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.zip.Deflater;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/chunks/ChunkPacketTransformer.class */
public class ChunkPacketTransformer {
    private static byte[] transformChunkData(byte[] data, int primaryBitMask, boolean skyLight, boolean groundUp) {
        int dataSize = 0;
        ByteBuf buf = Unpooled.buffer();
        ByteBuf blockDataBuf = Unpooled.buffer();
        for (int i = 0; i < 16; i++) {
            if ((primaryBitMask & (1 << i)) != 0) {
                byte tmp = 0;
                for (int j = 0; j < 4096; j++) {
                    short blockData = (short) (((data[dataSize + 1] & 255) << 8) | (data[dataSize] & 255));
                    dataSize += 2;
                    int id = BlockState.extractId(blockData);
                    int meta = BlockState.extractData(blockData);
                    Replacement replace = ReplacementRegistry1_7_6_10to1_8.getReplacement(id, meta);
                    if (replace != null) {
                        id = replace.getId();
                        meta = replace.replaceData(meta);
                    }
                    buf.writeByte(id);
                    if (j % 2 == 0) {
                        tmp = (byte) (meta & 15);
                    } else {
                        blockDataBuf.writeByte(tmp | ((meta & 15) << 4));
                    }
                }
            }
        }
        buf.writeBytes(blockDataBuf);
        blockDataBuf.release();
        int columnCount = Integer.bitCount(primaryBitMask);
        buf.writeBytes(data, dataSize, 2048 * columnCount);
        int dataSize2 = dataSize + (2048 * columnCount);
        if (skyLight) {
            buf.writeBytes(data, dataSize2, 2048 * columnCount);
            dataSize2 += 2048 * columnCount;
        }
        if (groundUp && dataSize2 + 256 <= data.length) {
            buf.writeBytes(data, dataSize2, 256);
            int i2 = dataSize2 + 256;
        }
        byte[] data2 = new byte[buf.readableBytes()];
        buf.readBytes(data2);
        buf.release();
        return data2;
    }

    private static int calcSize(int i, boolean flag, boolean flag1) {
        int j = i * 2 * 16 * 16 * 16;
        int k = (((i * 16) * 16) * 16) / 2;
        int l = flag ? (((i * 16) * 16) * 16) / 2 : 0;
        int i1 = flag1 ? 256 : 0;
        return j + k + l + i1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void transformChunkBulk(PacketWrapper packetWrapper) throws Exception {
        boolean skyLightSent = ((Boolean) packetWrapper.read(Type.BOOLEAN)).booleanValue();
        int columnCount = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
        int[] chunkX = new int[columnCount];
        int[] chunkZ = new int[columnCount];
        int[] primaryBitMask = new int[columnCount];
        byte[] bArr = new byte[columnCount];
        for (int i = 0; i < columnCount; i++) {
            chunkX[i] = ((Integer) packetWrapper.read(Type.INT)).intValue();
            chunkZ[i] = ((Integer) packetWrapper.read(Type.INT)).intValue();
            primaryBitMask[i] = ((Integer) packetWrapper.read(Type.UNSIGNED_SHORT)).intValue();
        }
        int totalSize = 0;
        for (int i2 = 0; i2 < columnCount; i2++) {
            int size = calcSize(Integer.bitCount(primaryBitMask[i2]), skyLightSent, true);
            CustomByteType customByteType = new CustomByteType(Integer.valueOf(size));
            bArr[i2] = transformChunkData((byte[]) packetWrapper.read(customByteType), primaryBitMask[i2], skyLightSent, true);
            totalSize += bArr[i2].length;
        }
        packetWrapper.write(Type.SHORT, Short.valueOf((short) columnCount));
        byte[] buildBuffer = new byte[totalSize];
        int bufferLocation = 0;
        for (int i3 = 0; i3 < columnCount; i3++) {
            System.arraycopy(bArr[i3], 0, buildBuffer, bufferLocation, bArr[i3].length);
            bufferLocation += bArr[i3].length;
        }
        Deflater deflater = new Deflater(4);
        deflater.reset();
        deflater.setInput(buildBuffer);
        deflater.finish();
        byte[] buffer = new byte[buildBuffer.length + 100];
        int compressedSize = deflater.deflate(buffer);
        byte[] finalBuffer = new byte[compressedSize];
        System.arraycopy(buffer, 0, finalBuffer, 0, compressedSize);
        packetWrapper.write(Type.INT, Integer.valueOf(compressedSize));
        packetWrapper.write(Type.BOOLEAN, Boolean.valueOf(skyLightSent));
        CustomByteType customByteType2 = new CustomByteType(Integer.valueOf(compressedSize));
        packetWrapper.write(customByteType2, finalBuffer);
        for (int i4 = 0; i4 < columnCount; i4++) {
            packetWrapper.write(Type.INT, Integer.valueOf(chunkX[i4]));
            packetWrapper.write(Type.INT, Integer.valueOf(chunkZ[i4]));
            packetWrapper.write(Type.SHORT, Short.valueOf((short) primaryBitMask[i4]));
            packetWrapper.write(Type.SHORT, (short) 0);
        }
    }
}
