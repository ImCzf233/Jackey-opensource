package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/UUIDIntArrayType.class */
public class UUIDIntArrayType extends Type<UUID> {
    public UUIDIntArrayType() {
        super(UUID.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public UUID read(ByteBuf buffer) {
        int[] ints = {buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()};
        return uuidFromIntArray(ints);
    }

    public void write(ByteBuf buffer, UUID object) {
        int[] ints = uuidToIntArray(object);
        buffer.writeInt(ints[0]);
        buffer.writeInt(ints[1]);
        buffer.writeInt(ints[2]);
        buffer.writeInt(ints[3]);
    }

    public static UUID uuidFromIntArray(int[] ints) {
        return new UUID((ints[0] << 32) | (ints[1] & JSType.MAX_UINT), (ints[2] << 32) | (ints[3] & JSType.MAX_UINT));
    }

    public static int[] uuidToIntArray(UUID uuid) {
        return bitsToIntArray(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }

    public static int[] bitsToIntArray(long long1, long long2) {
        return new int[]{(int) (long1 >> 32), (int) long1, (int) (long2 >> 32), (int) long2};
    }
}
