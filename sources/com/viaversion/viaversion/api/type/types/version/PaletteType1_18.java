package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.BiIntConsumer;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.MathUtil;
import io.netty.buffer.ByteBuf;
import java.util.function.IntToLongFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/version/PaletteType1_18.class */
public final class PaletteType1_18 extends Type<DataPalette> {
    private final int globalPaletteBits;
    private final PaletteType type;

    public PaletteType1_18(PaletteType type, int globalPaletteBits) {
        super(DataPalette.class);
        this.globalPaletteBits = globalPaletteBits;
        this.type = type;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public DataPalette read(ByteBuf buffer) throws Exception {
        DataPaletteImpl palette;
        BiIntConsumer biIntConsumer;
        int bitsPerValue = buffer.readByte();
        if (bitsPerValue > this.type.highestBitsPerValue()) {
            bitsPerValue = this.globalPaletteBits;
        }
        if (bitsPerValue == 0) {
            DataPaletteImpl palette2 = new DataPaletteImpl(this.type.size(), 1);
            palette2.addId(Type.VAR_INT.readPrimitive(buffer));
            Type.VAR_INT.readPrimitive(buffer);
            return palette2;
        }
        if (bitsPerValue != this.globalPaletteBits) {
            int paletteLength = Type.VAR_INT.readPrimitive(buffer);
            palette = new DataPaletteImpl(this.type.size(), paletteLength);
            for (int i = 0; i < paletteLength; i++) {
                palette.addId(Type.VAR_INT.readPrimitive(buffer));
            }
        } else {
            palette = new DataPaletteImpl(this.type.size());
        }
        long[] values = new long[Type.VAR_INT.readPrimitive(buffer)];
        if (values.length > 0) {
            char valuesPerLong = (char) (64 / bitsPerValue);
            int expectedLength = ((this.type.size() + valuesPerLong) - 1) / valuesPerLong;
            if (values.length != expectedLength) {
                throw new IllegalStateException("Palette data length (" + values.length + ") does not match expected length (" + expectedLength + ")! bitsPerValue=" + bitsPerValue + ", originalBitsPerValue=" + bitsPerValue);
            }
            for (int i2 = 0; i2 < values.length; i2++) {
                values[i2] = buffer.readLong();
            }
            int i3 = bitsPerValue;
            int size = this.type.size();
            if (bitsPerValue == this.globalPaletteBits) {
                DataPaletteImpl dataPaletteImpl = palette;
                dataPaletteImpl.getClass();
                biIntConsumer = this::setIdAt;
            } else {
                DataPaletteImpl dataPaletteImpl2 = palette;
                dataPaletteImpl2.getClass();
                biIntConsumer = this::setPaletteIndexAt;
            }
            CompactArrayUtil.iterateCompactArrayWithPadding(i3, size, values, biIntConsumer);
        }
        return palette;
    }

    public void write(ByteBuf buffer, DataPalette palette) throws Exception {
        IntToLongFunction intToLongFunction;
        if (palette.size() == 1) {
            buffer.writeByte(0);
            Type.VAR_INT.writePrimitive(buffer, palette.idByIndex(0));
            Type.VAR_INT.writePrimitive(buffer, 0);
            return;
        }
        int min = this.type == PaletteType.BLOCKS ? 4 : 1;
        int bitsPerValue = Math.max(min, MathUtil.ceilLog2(palette.size()));
        if (bitsPerValue > this.type.highestBitsPerValue()) {
            bitsPerValue = this.globalPaletteBits;
        }
        buffer.writeByte(bitsPerValue);
        if (bitsPerValue != this.globalPaletteBits) {
            Type.VAR_INT.writePrimitive(buffer, palette.size());
            for (int i = 0; i < palette.size(); i++) {
                Type.VAR_INT.writePrimitive(buffer, palette.idByIndex(i));
            }
        }
        int i2 = bitsPerValue;
        int size = this.type.size();
        if (bitsPerValue == this.globalPaletteBits) {
            palette.getClass();
            intToLongFunction = this::idAt;
        } else {
            palette.getClass();
            intToLongFunction = this::paletteIndexAt;
        }
        long[] data = CompactArrayUtil.createCompactArrayWithPadding(i2, size, intToLongFunction);
        Type.VAR_INT.writePrimitive(buffer, data.length);
        for (long l : data) {
            buffer.writeLong(l);
        }
    }
}
