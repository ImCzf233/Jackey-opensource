package org.spongepowered.tools.obfuscation.interfaces;

import java.util.Collection;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/interfaces/IObfuscationEnvironment.class */
public interface IObfuscationEnvironment {
    MappingMethod getObfMethod(MemberInfo memberInfo);

    MappingMethod getObfMethod(MappingMethod mappingMethod);

    MappingMethod getObfMethod(MappingMethod mappingMethod, boolean z);

    MappingField getObfField(MemberInfo memberInfo);

    MappingField getObfField(MappingField mappingField);

    MappingField getObfField(MappingField mappingField, boolean z);

    String getObfClass(String str);

    MemberInfo remapDescriptor(MemberInfo memberInfo);

    String remapDescriptor(String str);

    void writeMappings(Collection<IMappingConsumer> collection);
}
