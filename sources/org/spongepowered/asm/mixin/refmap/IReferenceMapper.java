package org.spongepowered.asm.mixin.refmap;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/refmap/IReferenceMapper.class */
public interface IReferenceMapper {
    boolean isDefault();

    String getResourceName();

    String getStatus();

    String getContext();

    void setContext(String str);

    String remap(String str, String str2);

    String remapWithContext(String str, String str2, String str3);
}
