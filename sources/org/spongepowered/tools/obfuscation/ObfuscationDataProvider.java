package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/ObfuscationDataProvider.class */
public class ObfuscationDataProvider implements IObfuscationDataProvider {

    /* renamed from: ap */
    private final IMixinAnnotationProcessor f450ap;
    private final List<ObfuscationEnvironment> environments;

    public ObfuscationDataProvider(IMixinAnnotationProcessor ap, List<ObfuscationEnvironment> environments) {
        this.f450ap = ap;
        this.environments = environments;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public <T> ObfuscationData<T> getObfEntryRecursive(MemberInfo targetMember) {
        MemberInfo currentTarget = targetMember;
        ObfuscationData<String> obfTargetNames = getObfClass(currentTarget.owner);
        ObfuscationData<T> obfData = getObfEntry(currentTarget);
        while (obfData.isEmpty()) {
            try {
                TypeHandle targetType = this.f450ap.getTypeProvider().getTypeHandle(currentTarget.owner);
                if (targetType == null) {
                    return obfData;
                }
                TypeHandle superClass = targetType.getSuperclass();
                obfData = getObfEntryUsing(currentTarget, superClass);
                if (!obfData.isEmpty()) {
                    return applyParents(obfTargetNames, obfData);
                }
                for (TypeHandle iface : targetType.getInterfaces()) {
                    obfData = getObfEntryUsing(currentTarget, iface);
                    if (!obfData.isEmpty()) {
                        return applyParents(obfTargetNames, obfData);
                    }
                }
                if (superClass == null) {
                    break;
                }
                currentTarget = currentTarget.move(superClass.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
                return getObfEntry(targetMember);
            }
        }
        return obfData;
    }

    private <T> ObfuscationData<T> getObfEntryUsing(MemberInfo targetMember, TypeHandle targetClass) {
        return targetClass == null ? new ObfuscationData<>() : getObfEntry(targetMember.move(targetClass.getName()));
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public <T> ObfuscationData<T> getObfEntry(MemberInfo targetMember) {
        if (targetMember.isField()) {
            return (ObfuscationData<T>) getObfField(targetMember);
        }
        return (ObfuscationData<T>) getObfMethod(targetMember.asMethodMapping());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public <T> ObfuscationData<T> getObfEntry(IMapping<T> mapping) {
        if (mapping != null) {
            if (mapping.getType() == IMapping.Type.FIELD) {
                return (ObfuscationData<T>) getObfField((MappingField) mapping);
            }
            if (mapping.getType() == IMapping.Type.METHOD) {
                return (ObfuscationData<T>) getObfMethod((MappingMethod) mapping);
            }
        }
        return new ObfuscationData<>();
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData<MappingMethod> getObfMethodRecursive(MemberInfo targetMember) {
        return getObfEntryRecursive(targetMember);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData<MappingMethod> getObfMethod(MemberInfo method) {
        return getRemappedMethod(method, method.isConstructor());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo method) {
        return getRemappedMethod(method, true);
    }

    private ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo method, boolean remapDescriptor) {
        ObfuscationData<MappingMethod> data = new ObfuscationData<>();
        for (ObfuscationEnvironment env : this.environments) {
            MappingMethod obfMethod = env.getObfMethod(method);
            if (obfMethod != null) {
                data.put(env.getType(), obfMethod);
            }
        }
        if (!data.isEmpty() || !remapDescriptor) {
            return data;
        }
        return remapDescriptor(data, method);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData<MappingMethod> getObfMethod(MappingMethod method) {
        return getRemappedMethod(method, method.isConstructor());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod method) {
        return getRemappedMethod(method, true);
    }

    private ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod method, boolean remapDescriptor) {
        ObfuscationData<MappingMethod> data = new ObfuscationData<>();
        for (ObfuscationEnvironment env : this.environments) {
            MappingMethod obfMethod = env.getObfMethod(method);
            if (obfMethod != null) {
                data.put(env.getType(), obfMethod);
            }
        }
        if (!data.isEmpty() || !remapDescriptor) {
            return data;
        }
        return remapDescriptor(data, new MemberInfo(method));
    }

    public ObfuscationData<MappingMethod> remapDescriptor(ObfuscationData<MappingMethod> data, MemberInfo method) {
        for (ObfuscationEnvironment env : this.environments) {
            MemberInfo obfMethod = env.remapDescriptor(method);
            if (obfMethod != null) {
                data.put(env.getType(), obfMethod.asMethodMapping());
            }
        }
        return data;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData<MappingField> getObfFieldRecursive(MemberInfo targetMember) {
        return getObfEntryRecursive(targetMember);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData<MappingField> getObfField(MemberInfo field) {
        return getObfField(field.asFieldMapping());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData<MappingField> getObfField(MappingField field) {
        ObfuscationData<MappingField> data = new ObfuscationData<>();
        for (ObfuscationEnvironment env : this.environments) {
            MappingField obfField = env.getObfField(field);
            if (obfField != null) {
                if (obfField.getDesc() == null && field.getDesc() != null) {
                    obfField = obfField.transform(env.remapDescriptor(field.getDesc()));
                }
                data.put(env.getType(), obfField);
            }
        }
        return data;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData<String> getObfClass(TypeHandle type) {
        return getObfClass(type.getName());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData<String> getObfClass(String className) {
        ObfuscationData<String> data = new ObfuscationData<>(className);
        for (ObfuscationEnvironment env : this.environments) {
            String obfClass = env.getObfClass(className);
            if (obfClass != null) {
                data.put(env.getType(), obfClass);
            }
        }
        return data;
    }

    private static <T> ObfuscationData<T> applyParents(ObfuscationData<String> parents, ObfuscationData<T> members) {
        Iterator<ObfuscationType> it = members.iterator();
        while (it.hasNext()) {
            ObfuscationType type = it.next();
            String obfClass = parents.get(type);
            T obfMember = members.get(type);
            members.put(type, MemberInfo.fromMapping((IMapping) obfMember).move(obfClass).asMapping());
        }
        return members;
    }
}
