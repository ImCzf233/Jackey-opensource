package org.spongepowered.asm.util;

import java.io.File;
import org.spongepowered.asm.mixin.Mixin;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/Constants.class */
public abstract class Constants {
    public static final String CTOR = "<init>";
    public static final String CLINIT = "<clinit>";
    public static final String IMAGINARY_SUPER = "super$";
    public static final String STRING = "Ljava/lang/String;";
    public static final String OBJECT = "Ljava/lang/Object;";
    public static final String CLASS = "Ljava/lang/Class;";
    public static final String SYNTHETIC_PACKAGE = "org.spongepowered.asm.synthetic";
    public static final char UNICODE_SNOWMAN = 9731;
    public static final String MIXIN_PACKAGE = Mixin.class.getPackage().getName();
    public static final String MIXIN_PACKAGE_REF = MIXIN_PACKAGE.replace('.', '/');
    public static final String DEBUG_OUTPUT_PATH = ".mixin.out";
    public static final File DEBUG_OUTPUT_DIR = new File(DEBUG_OUTPUT_PATH);

    private Constants() {
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/Constants$ManifestAttributes.class */
    public static abstract class ManifestAttributes {
        public static final String TWEAKER = "TweakClass";
        public static final String MAINCLASS = "Main-Class";
        public static final String MIXINCONFIGS = "MixinConfigs";
        public static final String TOKENPROVIDERS = "MixinTokenProviders";
        @Deprecated
        public static final String COMPATIBILITY = "MixinCompatibilityLevel";

        private ManifestAttributes() {
        }
    }
}
