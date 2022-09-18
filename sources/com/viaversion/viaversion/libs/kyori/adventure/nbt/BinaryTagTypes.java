package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagTypes.class */
public final class BinaryTagTypes {
    public static final BinaryTagType<EndBinaryTag> END = BinaryTagType.register(EndBinaryTag.class, (byte) 0, input -> {
        return EndBinaryTag.get();
    }, null);
    public static final BinaryTagType<ByteBinaryTag> BYTE = BinaryTagType.registerNumeric(ByteBinaryTag.class, (byte) 1, input -> {
        return ByteBinaryTag.m136of(input.readByte());
    }, tag, output -> {
        output.writeByte(tag.value());
    });
    public static final BinaryTagType<ShortBinaryTag> SHORT = BinaryTagType.registerNumeric(ShortBinaryTag.class, (byte) 2, input -> {
        return ShortBinaryTag.m128of(input.readShort());
    }, tag, output -> {
        output.writeShort(tag.value());
    });
    public static final BinaryTagType<IntBinaryTag> INT = BinaryTagType.registerNumeric(IntBinaryTag.class, (byte) 3, input -> {
        return IntBinaryTag.m132of(input.readInt());
    }, tag, output -> {
        output.writeInt(tag.value());
    });
    public static final BinaryTagType<LongBinaryTag> LONG = BinaryTagType.registerNumeric(LongBinaryTag.class, (byte) 4, input -> {
        return LongBinaryTag.m129of(input.readLong());
    }, tag, output -> {
        output.writeLong(tag.value());
    });
    public static final BinaryTagType<FloatBinaryTag> FLOAT = BinaryTagType.registerNumeric(FloatBinaryTag.class, (byte) 5, input -> {
        return FloatBinaryTag.m134of(input.readFloat());
    }, tag, output -> {
        output.writeFloat(tag.value());
    });
    public static final BinaryTagType<DoubleBinaryTag> DOUBLE = BinaryTagType.registerNumeric(DoubleBinaryTag.class, (byte) 6, input -> {
        return DoubleBinaryTag.m135of(input.readDouble());
    }, tag, output -> {
        output.writeDouble(tag.value());
    });
    public static final BinaryTagType<ByteArrayBinaryTag> BYTE_ARRAY = BinaryTagType.register(ByteArrayBinaryTag.class, (byte) 7, input -> {
        int length = input.readInt();
        BinaryTagScope ignored = TrackingDataInput.enter(input, length);
        try {
            byte[] value = new byte[length];
            input.readFully(value);
            ByteArrayBinaryTag m137of = ByteArrayBinaryTag.m137of(value);
            if (ignored != null) {
                ignored.close();
            }
            return m137of;
        } catch (Throwable th) {
            if (ignored != null) {
                try {
                    ignored.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }, tag, output -> {
        byte[] value = ByteArrayBinaryTagImpl.value(tag);
        output.writeInt(value.length);
        output.write(value);
    });
    public static final BinaryTagType<StringBinaryTag> STRING = BinaryTagType.register(StringBinaryTag.class, (byte) 8, input -> {
        return StringBinaryTag.m127of(input.readUTF());
    }, tag, output -> {
        output.writeUTF(tag.value());
    });
    public static final BinaryTagType<ListBinaryTag> LIST = BinaryTagType.register(ListBinaryTag.class, (byte) 9, input -> {
        BinaryTagType<? extends BinaryTag> type = BinaryTagType.m139of(input.readByte());
        int length = input.readInt();
        BinaryTagScope ignored = TrackingDataInput.enter(input, length * 8);
        try {
            List<BinaryTag> tags = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                tags.add(type.read(input));
            }
            ListBinaryTag m131of = ListBinaryTag.m131of(type, tags);
            if (ignored != null) {
                ignored.close();
            }
            return m131of;
        } catch (Throwable th) {
            if (ignored != null) {
                try {
                    ignored.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }, tag, output -> {
        output.writeByte(tag.elementType().mo138id());
        int size = tag.size();
        output.writeInt(size);
        for (BinaryTag item : tag) {
            BinaryTagType.write(item.type(), item, output);
        }
    });
    public static final BinaryTagType<CompoundBinaryTag> COMPOUND = BinaryTagType.register(CompoundBinaryTag.class, (byte) 10, input -> {
        BinaryTagScope ignored = TrackingDataInput.enter(input);
        try {
            Map<String, BinaryTag> tags = new HashMap<>();
            while (true) {
                BinaryTagType<? extends BinaryTag> type = BinaryTagType.m139of(input.readByte());
                if (type == END) {
                    break;
                }
                String key = input.readUTF();
                BinaryTag tag = type.read(input);
                tags.put(key, tag);
            }
            CompoundBinaryTagImpl compoundBinaryTagImpl = new CompoundBinaryTagImpl(tags);
            if (ignored != null) {
                ignored.close();
            }
            return compoundBinaryTagImpl;
        } catch (Throwable th) {
            if (ignored != null) {
                try {
                    ignored.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }, tag, output -> {
        for (Map.Entry<String, ? extends BinaryTag> entry : tag) {
            BinaryTag value = entry.getValue();
            if (value != null) {
                BinaryTagType<? extends BinaryTag> type = value.type();
                output.writeByte(type.mo138id());
                if (type != END) {
                    output.writeUTF(entry.getKey());
                    BinaryTagType.write(type, value, output);
                }
            }
        }
        output.writeByte(END.mo138id());
    });
    public static final BinaryTagType<IntArrayBinaryTag> INT_ARRAY = BinaryTagType.register(IntArrayBinaryTag.class, (byte) 11, input -> {
        int length = input.readInt();
        BinaryTagScope ignored = TrackingDataInput.enter(input, length * 4);
        try {
            int[] value = new int[length];
            for (int i = 0; i < length; i++) {
                value[i] = input.readInt();
            }
            IntArrayBinaryTag m133of = IntArrayBinaryTag.m133of(value);
            if (ignored != null) {
                ignored.close();
            }
            return m133of;
        } catch (Throwable th) {
            if (ignored != null) {
                try {
                    ignored.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }, tag, output -> {
        int[] value = IntArrayBinaryTagImpl.value(tag);
        int length = value.length;
        output.writeInt(length);
        for (int i : value) {
            output.writeInt(i);
        }
    });
    public static final BinaryTagType<LongArrayBinaryTag> LONG_ARRAY = BinaryTagType.register(LongArrayBinaryTag.class, (byte) 12, input -> {
        int length = input.readInt();
        BinaryTagScope ignored = TrackingDataInput.enter(input, length * 8);
        try {
            long[] value = new long[length];
            for (int i = 0; i < length; i++) {
                value[i] = input.readLong();
            }
            LongArrayBinaryTag m130of = LongArrayBinaryTag.m130of(value);
            if (ignored != null) {
                ignored.close();
            }
            return m130of;
        } catch (Throwable th) {
            if (ignored != null) {
                try {
                    ignored.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }, tag, output -> {
        long[] value = LongArrayBinaryTagImpl.value(tag);
        int length = value.length;
        output.writeInt(length);
        for (long j : value) {
            output.writeLong(j);
        }
    });

    private BinaryTagTypes() {
    }
}
