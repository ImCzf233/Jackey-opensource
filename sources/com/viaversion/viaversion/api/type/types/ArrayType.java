package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/ArrayType.class */
public class ArrayType<T> extends Type<T[]> {
    private final Type<T> elementType;

    public ArrayType(Type<T> type) {
        super(type.getTypeName() + " Array", getArrayClass(type.getOutputClass()));
        this.elementType = type;
    }

    public static Class<?> getArrayClass(Class<?> componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public T[] read(ByteBuf buffer) throws Exception {
        int amount = Type.VAR_INT.readPrimitive(buffer);
        T[] array = (T[]) ((Object[]) Array.newInstance(this.elementType.getOutputClass(), amount));
        for (int i = 0; i < amount; i++) {
            array[i] = this.elementType.read(buffer);
        }
        return array;
    }

    public void write(ByteBuf buffer, T[] object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.length);
        for (T o : object) {
            this.elementType.write(buffer, o);
        }
    }
}
