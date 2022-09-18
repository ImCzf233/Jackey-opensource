package org.spongepowered.asm.util.throwables;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/throwables/InvalidConstraintException.class */
public class InvalidConstraintException extends IllegalArgumentException {
    private static final long serialVersionUID = 1;

    public InvalidConstraintException() {
    }

    public InvalidConstraintException(String s) {
        super(s);
    }

    public InvalidConstraintException(Throwable cause) {
        super(cause);
    }

    public InvalidConstraintException(String message, Throwable cause) {
        super(message, cause);
    }
}
