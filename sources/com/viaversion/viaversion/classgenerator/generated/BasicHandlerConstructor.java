package com.viaversion.viaversion.classgenerator.generated;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.handlers.BukkitDecodeHandler;
import com.viaversion.viaversion.bukkit.handlers.BukkitEncodeHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/classgenerator/generated/BasicHandlerConstructor.class */
public class BasicHandlerConstructor implements HandlerConstructor {
    @Override // com.viaversion.viaversion.classgenerator.generated.HandlerConstructor
    public BukkitEncodeHandler newEncodeHandler(UserConnection info, MessageToByteEncoder minecraftEncoder) {
        return new BukkitEncodeHandler(info, minecraftEncoder);
    }

    @Override // com.viaversion.viaversion.classgenerator.generated.HandlerConstructor
    public BukkitDecodeHandler newDecodeHandler(UserConnection info, ByteToMessageDecoder minecraftDecoder) {
        return new BukkitDecodeHandler(info, minecraftDecoder);
    }
}
