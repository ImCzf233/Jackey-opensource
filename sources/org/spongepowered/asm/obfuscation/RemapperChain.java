package org.spongepowered.asm.obfuscation;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/obfuscation/RemapperChain.class */
public class RemapperChain implements IRemapper {
    private final List<IRemapper> remappers = new ArrayList();

    public String toString() {
        return String.format("RemapperChain[%d]", Integer.valueOf(this.remappers.size()));
    }

    public RemapperChain add(IRemapper remapper) {
        this.remappers.add(remapper);
        return this;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper
    public String mapMethodName(String owner, String name, String desc) {
        for (IRemapper remapper : this.remappers) {
            String newName = remapper.mapMethodName(owner, name, desc);
            if (newName != null && !newName.equals(name)) {
                name = newName;
            }
        }
        return name;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper
    public String mapFieldName(String owner, String name, String desc) {
        for (IRemapper remapper : this.remappers) {
            String newName = remapper.mapFieldName(owner, name, desc);
            if (newName != null && !newName.equals(name)) {
                name = newName;
            }
        }
        return name;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper, org.spongepowered.asm.util.ObfuscationUtil.IClassRemapper
    public String map(String typeName) {
        for (IRemapper remapper : this.remappers) {
            String newName = remapper.map(typeName);
            if (newName != null && !newName.equals(typeName)) {
                typeName = newName;
            }
        }
        return typeName;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper, org.spongepowered.asm.util.ObfuscationUtil.IClassRemapper
    public String unmap(String typeName) {
        for (IRemapper remapper : this.remappers) {
            String newName = remapper.unmap(typeName);
            if (newName != null && !newName.equals(typeName)) {
                typeName = newName;
            }
        }
        return typeName;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper
    public String mapDesc(String desc) {
        for (IRemapper remapper : this.remappers) {
            String newDesc = remapper.mapDesc(desc);
            if (newDesc != null && !newDesc.equals(desc)) {
                desc = newDesc;
            }
        }
        return desc;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper
    public String unmapDesc(String desc) {
        for (IRemapper remapper : this.remappers) {
            String newDesc = remapper.unmapDesc(desc);
            if (newDesc != null && !newDesc.equals(desc)) {
                desc = newDesc;
            }
        }
        return desc;
    }
}
