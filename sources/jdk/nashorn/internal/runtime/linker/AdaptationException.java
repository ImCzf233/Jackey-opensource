package jdk.nashorn.internal.runtime.linker;

import jdk.nashorn.internal.runtime.linker.AdaptationResult;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/AdaptationException.class */
final class AdaptationException extends Exception {
    private final AdaptationResult adaptationResult;

    public AdaptationException(AdaptationResult.Outcome outcome, String classList) {
        super(null, null, false, false);
        this.adaptationResult = new AdaptationResult(outcome, classList);
    }

    public AdaptationResult getAdaptationResult() {
        return this.adaptationResult;
    }
}
