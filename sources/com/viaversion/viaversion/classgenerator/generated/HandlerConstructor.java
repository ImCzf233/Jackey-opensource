package com.viaversion.viaversion.classgenerator.generated;

import com.viaversion.viaversion.api.connection.UserConnection;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/classgenerator/generated/HandlerConstructor.class */
public interface HandlerConstructor {
    MessageToByteEncoder newEncodeHandler(UserConnection userConnection, MessageToByteEncoder messageToByteEncoder);

    ByteToMessageDecoder newDecodeHandler(UserConnection userConnection, ByteToMessageDecoder byteToMessageDecoder);
}
