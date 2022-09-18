package jdk.nashorn.internal.codegen;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationException.class */
public class CompilationException extends RuntimeException {
    public CompilationException(String description) {
        super(description);
    }

    CompilationException(Exception cause) {
        super(cause);
    }
}
