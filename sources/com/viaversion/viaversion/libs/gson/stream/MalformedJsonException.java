package com.viaversion.viaversion.libs.gson.stream;

import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/stream/MalformedJsonException.class */
public final class MalformedJsonException extends IOException {
    private static final long serialVersionUID = 1;

    public MalformedJsonException(String msg) {
        super(msg);
    }

    public MalformedJsonException(String msg, Throwable throwable) {
        super(msg);
        initCause(throwable);
    }

    public MalformedJsonException(Throwable throwable) {
        initCause(throwable);
    }
}
