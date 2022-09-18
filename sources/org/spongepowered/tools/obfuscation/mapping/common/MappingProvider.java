package org.spongepowered.tools.obfuscation.mapping.common;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/mapping/common/MappingProvider.class */
public abstract class MappingProvider implements IMappingProvider {
    protected final Messager messager;
    protected final Filer filer;
    protected final BiMap<String, String> packageMap = HashBiMap.create();
    protected final BiMap<String, String> classMap = HashBiMap.create();
    protected final BiMap<MappingField, MappingField> fieldMap = HashBiMap.create();
    protected final BiMap<MappingMethod, MappingMethod> methodMap = HashBiMap.create();

    public MappingProvider(Messager messager, Filer filer) {
        this.messager = messager;
        this.filer = filer;
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public void clear() {
        this.packageMap.clear();
        this.classMap.clear();
        this.fieldMap.clear();
        this.methodMap.clear();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public boolean isEmpty() {
        return this.packageMap.isEmpty() && this.classMap.isEmpty() && this.fieldMap.isEmpty() && this.methodMap.isEmpty();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public MappingMethod getMethodMapping(MappingMethod method) {
        return (MappingMethod) this.methodMap.get(method);
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public MappingField getFieldMapping(MappingField field) {
        return (MappingField) this.fieldMap.get(field);
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public String getClassMapping(String className) {
        return (String) this.classMap.get(className);
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public String getPackageMapping(String packageName) {
        return (String) this.packageMap.get(packageName);
    }
}
