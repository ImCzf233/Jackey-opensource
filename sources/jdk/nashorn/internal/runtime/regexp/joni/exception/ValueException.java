package jdk.nashorn.internal.runtime.regexp.joni.exception;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/exception/ValueException.class */
public class ValueException extends SyntaxException {
    private static final long serialVersionUID = -196013852479929134L;

    public ValueException(String message) {
        super(message);
    }

    public ValueException(String message, String str) {
        super(message.replaceAll("%n", str));
    }
}
