package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/OptionalComponentType.class */
public class OptionalComponentType extends Type<JsonElement> {
    public OptionalComponentType() {
        super(JsonElement.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public JsonElement read(ByteBuf buffer) throws Exception {
        boolean present = buffer.readBoolean();
        if (present) {
            return Type.COMPONENT.read(buffer);
        }
        return null;
    }

    public void write(ByteBuf buffer, JsonElement object) throws Exception {
        if (object == null) {
            buffer.writeBoolean(false);
            return;
        }
        buffer.writeBoolean(true);
        Type.COMPONENT.write(buffer, object);
    }
}
