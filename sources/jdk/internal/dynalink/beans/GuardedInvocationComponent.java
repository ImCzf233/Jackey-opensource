package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.linker.GuardedInvocation;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/GuardedInvocationComponent.class */
class GuardedInvocationComponent {
    private final GuardedInvocation guardedInvocation;
    private final Validator validator;

    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/GuardedInvocationComponent$ValidationType.class */
    public enum ValidationType {
        NONE,
        INSTANCE_OF,
        EXACT_CLASS,
        IS_ARRAY
    }

    public GuardedInvocationComponent(MethodHandle invocation) {
        this(invocation, (MethodHandle) null, ValidationType.NONE);
    }

    public GuardedInvocationComponent(MethodHandle invocation, MethodHandle guard, ValidationType validationType) {
        this(invocation, guard, null, validationType);
    }

    public GuardedInvocationComponent(MethodHandle invocation, MethodHandle guard, Class<?> validatorClass, ValidationType validationType) {
        this(invocation, guard, new Validator(validatorClass, validationType));
    }

    public GuardedInvocationComponent(GuardedInvocation guardedInvocation, Class<?> validatorClass, ValidationType validationType) {
        this(guardedInvocation, new Validator(validatorClass, validationType));
    }

    public GuardedInvocationComponent replaceInvocation(MethodHandle newInvocation) {
        return replaceInvocation(newInvocation, this.guardedInvocation.getGuard());
    }

    GuardedInvocationComponent replaceInvocation(MethodHandle newInvocation, MethodHandle newGuard) {
        return new GuardedInvocationComponent(this.guardedInvocation.replaceMethods(newInvocation, newGuard), this.validator);
    }

    private GuardedInvocationComponent(MethodHandle invocation, MethodHandle guard, Validator validator) {
        this(new GuardedInvocation(invocation, guard), validator);
    }

    private GuardedInvocationComponent(GuardedInvocation guardedInvocation, Validator validator) {
        this.guardedInvocation = guardedInvocation;
        this.validator = validator;
    }

    public GuardedInvocation getGuardedInvocation() {
        return this.guardedInvocation;
    }

    public Class<?> getValidatorClass() {
        return this.validator.validatorClass;
    }

    public ValidationType getValidationType() {
        return this.validator.validationType;
    }

    public GuardedInvocationComponent compose(MethodHandle compositeInvocation, MethodHandle otherGuard, Class<?> otherValidatorClass, ValidationType otherValidationType) {
        Validator compositeValidator = this.validator.compose(new Validator(otherValidatorClass, otherValidationType));
        MethodHandle compositeGuard = compositeValidator == this.validator ? this.guardedInvocation.getGuard() : otherGuard;
        return new GuardedInvocationComponent(compositeInvocation, compositeGuard, compositeValidator);
    }

    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/GuardedInvocationComponent$Validator.class */
    public static class Validator {
        final Class<?> validatorClass;
        final ValidationType validationType;

        Validator(Class<?> validatorClass, ValidationType validationType) {
            this.validatorClass = validatorClass;
            this.validationType = validationType;
        }

        Validator compose(Validator other) {
            if (other.validationType == ValidationType.NONE) {
                return this;
            }
            switch (this.validationType) {
                case INSTANCE_OF:
                    switch (other.validationType) {
                        case INSTANCE_OF:
                            if (isAssignableFrom(other)) {
                                return other;
                            }
                            if (other.isAssignableFrom(this)) {
                                return this;
                            }
                            break;
                        case EXACT_CLASS:
                            if (isAssignableFrom(other)) {
                                return other;
                            }
                            break;
                        case IS_ARRAY:
                            if (this.validatorClass.isArray()) {
                                return this;
                            }
                            break;
                        default:
                            throw new AssertionError();
                    }
                case EXACT_CLASS:
                    switch (other.validationType) {
                        case INSTANCE_OF:
                            if (other.isAssignableFrom(this)) {
                                return this;
                            }
                            break;
                        case EXACT_CLASS:
                            if (this.validatorClass == other.validatorClass) {
                                return this;
                            }
                            break;
                        case IS_ARRAY:
                            if (this.validatorClass.isArray()) {
                                return this;
                            }
                            break;
                        default:
                            throw new AssertionError();
                    }
                case IS_ARRAY:
                    switch (other.validationType) {
                        case INSTANCE_OF:
                        case EXACT_CLASS:
                            if (other.validatorClass.isArray()) {
                                return other;
                            }
                            break;
                        case IS_ARRAY:
                            return this;
                        default:
                            throw new AssertionError();
                    }
                case NONE:
                    return other;
                default:
                    throw new AssertionError();
            }
            throw new AssertionError("Incompatible composition " + this + " vs " + other);
        }

        private boolean isAssignableFrom(Validator other) {
            return this.validatorClass.isAssignableFrom(other.validatorClass);
        }

        public String toString() {
            return "Validator[" + this.validationType + (this.validatorClass == null ? "" : " " + this.validatorClass.getName()) + "]";
        }
    }
}
