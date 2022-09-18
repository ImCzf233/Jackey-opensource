package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/Interface.class */
public @interface Interface {
    Class<?> iface();

    String prefix();

    boolean unique() default false;

    Remap remap() default Remap.ALL;

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/Interface$Remap.class */
    public enum Remap {
        ALL,
        FORCE(true),
        ONLY_PREFIXED,
        NONE;
        
        private final boolean forceRemap;

        Remap() {
            this(false);
        }

        Remap(boolean forceRemap) {
            this.forceRemap = forceRemap;
        }

        public boolean forceRemap() {
            return this.forceRemap;
        }
    }
}
