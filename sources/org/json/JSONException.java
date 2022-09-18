package org.json;

/* loaded from: Jackey Client b2.jar:org/json/JSONException.class */
public class JSONException extends RuntimeException {
    private static final long serialVersionUID = 0;

    public JSONException(String message) {
        super(message);
    }

    public JSONException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
