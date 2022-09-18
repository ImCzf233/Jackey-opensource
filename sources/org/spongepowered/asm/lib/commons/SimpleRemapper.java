package org.spongepowered.asm.lib.commons;

import java.util.Collections;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/commons/SimpleRemapper.class */
public class SimpleRemapper extends Remapper {
    private final Map<String, String> mapping;

    public SimpleRemapper(Map<String, String> mapping) {
        this.mapping = mapping;
    }

    public SimpleRemapper(String oldName, String newName) {
        this.mapping = Collections.singletonMap(oldName, newName);
    }

    @Override // org.spongepowered.asm.lib.commons.Remapper
    public String mapMethodName(String owner, String name, String desc) {
        String s = map(owner + '.' + name + desc);
        return s == null ? name : s;
    }

    @Override // org.spongepowered.asm.lib.commons.Remapper
    public String mapInvokeDynamicMethodName(String name, String desc) {
        String s = map('.' + name + desc);
        return s == null ? name : s;
    }

    @Override // org.spongepowered.asm.lib.commons.Remapper
    public String mapFieldName(String owner, String name, String desc) {
        String s = map(owner + '.' + name);
        return s == null ? name : s;
    }

    @Override // org.spongepowered.asm.lib.commons.Remapper
    public String map(String key) {
        return this.mapping.get(key);
    }
}
