package org.spongepowered.asm.obfuscation.mapping;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/obfuscation/mapping/IMapping.class */
public interface IMapping<TMapping> {

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/obfuscation/mapping/IMapping$Type.class */
    public enum Type {
        FIELD,
        METHOD,
        CLASS,
        PACKAGE
    }

    Type getType();

    TMapping move(String str);

    TMapping remap(String str);

    TMapping transform(String str);

    TMapping copy();

    String getName();

    String getSimpleName();

    String getOwner();

    String getDesc();

    TMapping getSuper();

    String serialise();
}
