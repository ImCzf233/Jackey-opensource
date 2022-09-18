package com.viaversion.viaversion.exception;

import com.viaversion.viaversion.api.Via;
import io.netty.handler.codec.EncoderException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/exception/CancelEncoderException.class */
public class CancelEncoderException extends EncoderException implements CancelCodecException {
    public static final CancelEncoderException CACHED = new CancelEncoderException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these") { // from class: com.viaversion.viaversion.exception.CancelEncoderException.1
        /* JADX WARN: Multi-variable type inference failed */
        public Throwable fillInStackTrace() {
            return this;
        }
    };

    public CancelEncoderException() {
    }

    public CancelEncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CancelEncoderException(String message) {
        super(message);
    }

    public CancelEncoderException(Throwable cause) {
        super(cause);
    }

    public static CancelEncoderException generate(Throwable cause) {
        return Via.getManager().isDebug() ? new CancelEncoderException(cause) : CACHED;
    }
}
