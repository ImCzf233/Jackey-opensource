package com.viaversion.viaversion.bungee.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/util/BungeePipelineUtil.class */
public class BungeePipelineUtil {
    private static Method DECODE_METHOD;
    private static Method ENCODE_METHOD;

    static {
        try {
            DECODE_METHOD = MessageToMessageDecoder.class.getDeclaredMethod("decode", ChannelHandlerContext.class, Object.class, List.class);
            DECODE_METHOD.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            ENCODE_METHOD = MessageToByteEncoder.class.getDeclaredMethod("encode", ChannelHandlerContext.class, Object.class, ByteBuf.class);
            ENCODE_METHOD.setAccessible(true);
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        }
    }

    public static List<Object> callDecode(MessageToMessageDecoder decoder, ChannelHandlerContext ctx, ByteBuf input) throws InvocationTargetException {
        List<Object> output = new ArrayList<>();
        try {
            DECODE_METHOD.invoke(decoder, ctx, input, output);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static ByteBuf callEncode(MessageToByteEncoder encoder, ChannelHandlerContext ctx, ByteBuf input) throws InvocationTargetException {
        ByteBuf output = ctx.alloc().buffer();
        try {
            ENCODE_METHOD.invoke(encoder, ctx, input, output);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static ByteBuf decompress(ChannelHandlerContext ctx, ByteBuf bytebuf) {
        try {
            return (ByteBuf) callDecode(ctx.pipeline().get("decompress"), ctx.pipeline().context("decompress"), bytebuf).get(0);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return ctx.alloc().buffer();
        }
    }

    public static ByteBuf compress(ChannelHandlerContext ctx, ByteBuf bytebuf) {
        try {
            return callEncode(ctx.pipeline().get("compress"), ctx.pipeline().context("compress"), bytebuf);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return ctx.alloc().buffer();
        }
    }
}
