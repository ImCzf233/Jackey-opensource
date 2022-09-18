package com.viaversion.viaversion.exception;

import com.viaversion.viaversion.api.Via;
import io.netty.handler.codec.DecoderException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/exception/CancelDecoderException.class */
public class CancelDecoderException extends DecoderException implements CancelCodecException {
    public static final CancelDecoderException CACHED = new CancelDecoderException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these") { // from class: com.viaversion.viaversion.exception.CancelDecoderException.1
        /* JADX WARN: Multi-variable type inference failed */
        public Throwable fillInStackTrace() {
            return this;
        }
    };

    public CancelDecoderException() {
    }

    public CancelDecoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CancelDecoderException(String message) {
        super(message);
    }

    public CancelDecoderException(Throwable cause) {
        super(cause);
    }

    public static CancelDecoderException generate(Throwable cause) {
        return Via.getManager().isDebug() ? new CancelDecoderException(cause) : CACHED;
    }
}
