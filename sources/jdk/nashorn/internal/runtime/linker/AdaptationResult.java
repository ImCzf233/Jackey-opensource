package jdk.nashorn.internal.runtime.linker;

import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ECMAException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/AdaptationResult.class */
final class AdaptationResult {
    static final AdaptationResult SUCCESSFUL_RESULT = new AdaptationResult(Outcome.SUCCESS, "");
    private final Outcome outcome;
    private final String[] messageArgs;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/AdaptationResult$Outcome.class */
    public enum Outcome {
        SUCCESS,
        ERROR_FINAL_CLASS,
        ERROR_NON_PUBLIC_CLASS,
        ERROR_NO_ACCESSIBLE_CONSTRUCTOR,
        ERROR_MULTIPLE_SUPERCLASSES,
        ERROR_NO_COMMON_LOADER,
        ERROR_FINAL_FINALIZER,
        ERROR_OTHER
    }

    public AdaptationResult(Outcome outcome, String... messageArgs) {
        this.outcome = outcome;
        this.messageArgs = messageArgs;
    }

    public Outcome getOutcome() {
        return this.outcome;
    }

    public ECMAException typeError() {
        return ECMAErrors.typeError("extend." + this.outcome, this.messageArgs);
    }
}
