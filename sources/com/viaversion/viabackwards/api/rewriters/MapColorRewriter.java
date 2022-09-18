package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/MapColorRewriter.class */
public final class MapColorRewriter {
    public static PacketHandler getRewriteHandler(IdRewriteFunction rewriter) {
        return wrapper -> {
            int iconCount = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
            for (int i = 0; i < iconCount; i++) {
                wrapper.passthrough(Type.VAR_INT);
                wrapper.passthrough(Type.BYTE);
                wrapper.passthrough(Type.BYTE);
                wrapper.passthrough(Type.BYTE);
                if (((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                    wrapper.passthrough(Type.COMPONENT);
                }
            }
            short columns = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
            if (columns < 1) {
                return;
            }
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            byte[] data = (byte[]) wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
            for (int i2 = 0; i2 < data.length; i2++) {
                int color = data[i2] & 255;
                int mappedColor = rewriter.rewrite(color);
                if (mappedColor != -1) {
                    data[i2] = (byte) mappedColor;
                }
            }
        };
    }
}
