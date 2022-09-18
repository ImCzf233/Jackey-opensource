package org.spongepowered.tools.obfuscation.mirror;

import org.spongepowered.asm.obfuscation.mapping.IMapping;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/mirror/MemberHandle.class */
public abstract class MemberHandle<T extends IMapping<T>> {
    private final String owner;
    private final String name;
    private final String desc;

    public abstract Visibility getVisibility();

    public abstract T asMapping(boolean z);

    public MemberHandle(String owner, String name, String desc) {
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }

    public final String getOwner() {
        return this.owner;
    }

    public final String getName() {
        return this.name;
    }

    public final String getDesc() {
        return this.desc;
    }
}
