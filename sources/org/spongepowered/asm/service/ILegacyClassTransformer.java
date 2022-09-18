package org.spongepowered.asm.service;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/service/ILegacyClassTransformer.class */
public interface ILegacyClassTransformer extends ITransformer {
    String getName();

    boolean isDelegationExcluded();

    byte[] transformClassBytes(String str, String str2, byte[] bArr);
}
