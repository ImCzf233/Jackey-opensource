package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/throwables/InjectionValidationException.class */
public class InjectionValidationException extends Exception {
    private static final long serialVersionUID = 1;
    private final InjectorGroupInfo group;

    public InjectionValidationException(InjectorGroupInfo group, String message) {
        super(message);
        this.group = group;
    }

    public InjectorGroupInfo getGroup() {
        return this.group;
    }
}
