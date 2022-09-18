package org.spongepowered.asm.lib.util;

import java.util.Map;
import org.spongepowered.asm.lib.Label;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/Textifiable.class */
public interface Textifiable {
    void textify(StringBuffer stringBuffer, Map<Label, String> map);
}
