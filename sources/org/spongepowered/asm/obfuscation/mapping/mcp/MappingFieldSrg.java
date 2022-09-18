package org.spongepowered.asm.obfuscation.mapping.mcp;

import org.spongepowered.asm.obfuscation.mapping.common.MappingField;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/obfuscation/mapping/mcp/MappingFieldSrg.class */
public class MappingFieldSrg extends MappingField {
    private final String srg;

    public MappingFieldSrg(String srg) {
        super(getOwnerFromSrg(srg), getNameFromSrg(srg), null);
        this.srg = srg;
    }

    public MappingFieldSrg(MappingField field) {
        super(field.getOwner(), field.getName(), null);
        this.srg = field.getOwner() + "/" + field.getName();
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingField, org.spongepowered.asm.obfuscation.mapping.IMapping
    public String serialise() {
        return this.srg;
    }

    private static String getNameFromSrg(String srg) {
        if (srg == null) {
            return null;
        }
        int pos = srg.lastIndexOf(47);
        return pos > -1 ? srg.substring(pos + 1) : srg;
    }

    private static String getOwnerFromSrg(String srg) {
        int pos;
        if (srg != null && (pos = srg.lastIndexOf(47)) > -1) {
            return srg.substring(0, pos);
        }
        return null;
    }
}
