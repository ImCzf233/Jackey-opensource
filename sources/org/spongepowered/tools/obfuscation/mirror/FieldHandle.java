package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/mirror/FieldHandle.class */
public class FieldHandle extends MemberHandle<MappingField> {
    private final VariableElement element;
    private final boolean rawType;

    public FieldHandle(TypeElement owner, VariableElement element) {
        this(TypeUtils.getInternalName(owner), element);
    }

    public FieldHandle(String owner, VariableElement element) {
        this(owner, element, false);
    }

    public FieldHandle(TypeElement owner, VariableElement element, boolean rawType) {
        this(TypeUtils.getInternalName(owner), element, rawType);
    }

    public FieldHandle(String owner, VariableElement element, boolean rawType) {
        this(owner, element, rawType, TypeUtils.getName(element), TypeUtils.getInternalName(element));
    }

    public FieldHandle(String owner, String name, String desc) {
        this(owner, null, false, name, desc);
    }

    private FieldHandle(String owner, VariableElement element, boolean rawType, String name, String desc) {
        super(owner, name, desc);
        this.element = element;
        this.rawType = rawType;
    }

    public boolean isImaginary() {
        return this.element == null;
    }

    public VariableElement getElement() {
        return this.element;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.MemberHandle
    public Visibility getVisibility() {
        return TypeUtils.getVisibility(this.element);
    }

    public boolean isRawType() {
        return this.rawType;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.MemberHandle
    public MappingField asMapping(boolean includeOwner) {
        return new MappingField(includeOwner ? getOwner() : null, getName(), getDesc());
    }

    public String toString() {
        String owner = getOwner() != null ? "L" + getOwner() + ";" : "";
        String name = Strings.nullToEmpty(getName());
        String desc = Strings.nullToEmpty(getDesc());
        return String.format("%s%s:%s", owner, name, desc);
    }
}
