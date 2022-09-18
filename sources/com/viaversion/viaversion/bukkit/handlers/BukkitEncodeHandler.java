package com.viaversion.viaversion.bukkit.handlers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.handlers.ChannelHandlerContextWrapper;
import com.viaversion.viaversion.handlers.ViaCodecHandler;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/handlers/BukkitEncodeHandler.class */
public class BukkitEncodeHandler extends MessageToByteEncoder implements ViaCodecHandler {
    private static Field versionField;
    private final UserConnection info;
    private final MessageToByteEncoder minecraftEncoder;

    static {
        try {
            versionField = NMSUtil.nms("PacketEncoder", "net.minecraft.network.PacketEncoder").getDeclaredField("version");
            versionField.setAccessible(true);
        } catch (Exception e) {
        }
    }

    public BukkitEncodeHandler(UserConnection info, MessageToByteEncoder minecraftEncoder) {
        this.info = info;
        this.minecraftEncoder = minecraftEncoder;
    }

    protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf bytebuf) throws Exception {
        if (versionField != null) {
            versionField.set(this.minecraftEncoder, versionField.get(this));
        }
        if (!(o instanceof ByteBuf)) {
            try {
                PipelineUtil.callEncode(this.minecraftEncoder, new ChannelHandlerContextWrapper(ctx, this), o, bytebuf);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof Exception) {
                    throw ((Exception) e.getCause());
                }
                if (e.getCause() instanceof Error) {
                    throw ((Error) e.getCause());
                }
            }
        } else {
            bytebuf.writeBytes((ByteBuf) o);
        }
        transform(bytebuf);
    }

    @Override // com.viaversion.viaversion.handlers.ViaCodecHandler
    public void transform(ByteBuf bytebuf) throws Exception {
        if (!this.info.checkClientboundPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            return;
        }
        this.info.transformClientbound(bytebuf, CancelEncoderException::generate);
    }

    @Override // com.viaversion.viaversion.handlers.ViaCodecHandler
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (PipelineUtil.containsCause(cause, CancelCodecException.class)) {
            return;
        }
        super.exceptionCaught(ctx, cause);
        if (!NMSUtil.isDebugPropertySet() && PipelineUtil.containsCause(cause, InformativeException.class)) {
            if (this.info.getProtocolInfo().getState() != State.HANDSHAKE || Via.getManager().isDebug()) {
                cause.printStackTrace();
            }
        }
    }
}
