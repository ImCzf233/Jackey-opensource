package com.viaversion.viaversion.exception;

import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/exception/InformativeException.class */
public class InformativeException extends Exception {
    private final Map<String, Object> info = new HashMap();
    private int sources;

    public InformativeException(Throwable cause) {
        super(cause);
    }

    public InformativeException set(String key, Object value) {
        this.info.put(key, value);
        return this;
    }

    public InformativeException addSource(Class<?> sourceClazz) {
        StringBuilder append = new StringBuilder().append("Source ");
        int i = this.sources;
        this.sources = i + 1;
        return set(append.append(i).toString(), getSource(sourceClazz));
    }

    private String getSource(Class<?> sourceClazz) {
        return sourceClazz.isAnonymousClass() ? sourceClazz.getName() + " (Anonymous)" : sourceClazz.getName();
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        StringBuilder builder = new StringBuilder("Please post this error to https://github.com/ViaVersion/ViaVersion/issues and follow the issue template\n{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : this.info.entrySet()) {
            if (!first) {
                builder.append(", ");
            }
            builder.append(entry.getKey()).append(": ").append(entry.getValue());
            first = false;
        }
        return builder.append("}\nActual Error: ").toString();
    }

    @Override // java.lang.Throwable
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
