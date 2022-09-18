package com.viaversion.viaversion.libs.gson;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/JsonIOException.class */
public final class JsonIOException extends JsonParseException {
    private static final long serialVersionUID = 1;

    public JsonIOException(String msg) {
        super(msg);
    }

    public JsonIOException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JsonIOException(Throwable cause) {
        super(cause);
    }
}
