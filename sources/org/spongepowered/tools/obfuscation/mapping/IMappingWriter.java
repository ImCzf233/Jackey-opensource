package org.spongepowered.tools.obfuscation.mapping;

import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/mapping/IMappingWriter.class */
public interface IMappingWriter {
    void write(String str, ObfuscationType obfuscationType, IMappingConsumer.MappingSet<MappingField> mappingSet, IMappingConsumer.MappingSet<MappingMethod> mappingSet2);
}
