package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/interfaces/IObfuscationDataProvider.class */
public interface IObfuscationDataProvider {
    <T> ObfuscationData<T> getObfEntryRecursive(MemberInfo memberInfo);

    <T> ObfuscationData<T> getObfEntry(MemberInfo memberInfo);

    <T> ObfuscationData<T> getObfEntry(IMapping<T> iMapping);

    ObfuscationData<MappingMethod> getObfMethodRecursive(MemberInfo memberInfo);

    ObfuscationData<MappingMethod> getObfMethod(MemberInfo memberInfo);

    ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo memberInfo);

    ObfuscationData<MappingMethod> getObfMethod(MappingMethod mappingMethod);

    ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod mappingMethod);

    ObfuscationData<MappingField> getObfFieldRecursive(MemberInfo memberInfo);

    ObfuscationData<MappingField> getObfField(MemberInfo memberInfo);

    ObfuscationData<MappingField> getObfField(MappingField mappingField);

    ObfuscationData<String> getObfClass(TypeHandle typeHandle);

    ObfuscationData<String> getObfClass(String str);
}
