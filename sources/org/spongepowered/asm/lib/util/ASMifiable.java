package org.spongepowered.asm.lib.util;

import java.util.Map;
import org.spongepowered.asm.lib.Label;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/ASMifiable.class */
public interface ASMifiable {
    void asmify(StringBuffer stringBuffer, String str, Map<Label, String> map);
}
