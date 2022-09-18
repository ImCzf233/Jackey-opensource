package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/WarnCallback.class */
public interface WarnCallback {
    public static final WarnCallback DEFAULT = new WarnCallback() { // from class: jdk.nashorn.internal.runtime.regexp.joni.WarnCallback.1
        @Override // jdk.nashorn.internal.runtime.regexp.joni.WarnCallback
        public void warn(String message) {
            System.err.println(message);
        }
    };

    void warn(String str);
}
