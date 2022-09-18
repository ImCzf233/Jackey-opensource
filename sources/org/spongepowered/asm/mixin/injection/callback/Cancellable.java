package org.spongepowered.asm.mixin.injection.callback;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/callback/Cancellable.class */
public interface Cancellable {
    boolean isCancellable();

    boolean isCancelled();

    void cancel() throws CancellationException;
}
