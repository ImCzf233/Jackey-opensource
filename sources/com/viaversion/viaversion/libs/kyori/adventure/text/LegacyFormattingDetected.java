package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.util.Nag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/LegacyFormattingDetected.class */
public final class LegacyFormattingDetected extends Nag {
    public LegacyFormattingDetected(final Component component) {
        super("Legacy formatting codes have been detected in a component - this is unsupported behaviour. Please refer to the Adventure documentation (https://docs.adventure.kyori.net) for more information. Component: " + component);
    }
}
