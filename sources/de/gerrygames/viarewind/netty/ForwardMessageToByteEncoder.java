package de.gerrygames.viarewind.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/netty/ForwardMessageToByteEncoder.class */
public class ForwardMessageToByteEncoder extends MessageToByteEncoder<ByteBuf> {
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        out.writeBytes(msg);
    }
}
