package org.spongepowered.asm.util.asm;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/asm/MixinVerifier.class */
public class MixinVerifier extends SimpleVerifier {
    private Type currentClass;
    private Type currentSuperClass;
    private List<Type> currentClassInterfaces;
    private boolean isInterface;

    public MixinVerifier(Type currentClass, Type currentSuperClass, List<Type> currentClassInterfaces, boolean isInterface) {
        super(currentClass, currentSuperClass, currentClassInterfaces, isInterface);
        this.currentClass = currentClass;
        this.currentSuperClass = currentSuperClass;
        this.currentClassInterfaces = currentClassInterfaces;
        this.isInterface = isInterface;
    }

    @Override // org.spongepowered.asm.lib.tree.analysis.SimpleVerifier
    protected boolean isInterface(Type type) {
        if (this.currentClass != null && type.equals(this.currentClass)) {
            return this.isInterface;
        }
        return ClassInfo.forType(type).isInterface();
    }

    @Override // org.spongepowered.asm.lib.tree.analysis.SimpleVerifier
    protected Type getSuperClass(Type type) {
        if (this.currentClass != null && type.equals(this.currentClass)) {
            return this.currentSuperClass;
        }
        ClassInfo c = ClassInfo.forType(type).getSuperClass();
        if (c != null) {
            return Type.getType("L" + c.getName() + ";");
        }
        return null;
    }

    @Override // org.spongepowered.asm.lib.tree.analysis.SimpleVerifier
    protected boolean isAssignableFrom(Type type, Type other) {
        if (type.equals(other)) {
            return true;
        }
        if (this.currentClass != null && type.equals(this.currentClass)) {
            if (getSuperClass(other) == null) {
                return false;
            }
            if (!this.isInterface) {
                return isAssignableFrom(type, getSuperClass(other));
            }
            return other.getSort() == 10 || other.getSort() == 9;
        } else if (this.currentClass != null && other.equals(this.currentClass)) {
            if (isAssignableFrom(type, this.currentSuperClass)) {
                return true;
            }
            if (this.currentClassInterfaces != null) {
                for (int i = 0; i < this.currentClassInterfaces.size(); i++) {
                    Type v = this.currentClassInterfaces.get(i);
                    if (isAssignableFrom(type, v)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        } else {
            ClassInfo typeInfo = ClassInfo.forType(type);
            if (typeInfo == null) {
                return false;
            }
            if (typeInfo.isInterface()) {
                typeInfo = ClassInfo.forName("java/lang/Object");
            }
            return ClassInfo.forType(other).hasSuperClass(typeInfo);
        }
    }
}
