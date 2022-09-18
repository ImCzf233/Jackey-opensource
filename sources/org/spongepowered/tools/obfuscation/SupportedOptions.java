package org.spongepowered.tools.obfuscation;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.spongepowered.tools.obfuscation.service.ObfuscationServices;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/SupportedOptions.class */
public final class SupportedOptions {
    public static final String TOKENS = "tokens";
    public static final String OUT_REFMAP_FILE = "outRefMapFile";
    public static final String DISABLE_TARGET_VALIDATOR = "disableTargetValidator";
    public static final String DISABLE_TARGET_EXPORT = "disableTargetExport";
    public static final String DISABLE_OVERWRITE_CHECKER = "disableOverwriteChecker";
    public static final String OVERWRITE_ERROR_LEVEL = "overwriteErrorLevel";
    public static final String DEFAULT_OBFUSCATION_ENV = "defaultObfuscationEnv";
    public static final String DEPENDENCY_TARGETS_FILE = "dependencyTargetsFile";

    private SupportedOptions() {
    }

    public static Set<String> getAllOptions() {
        ImmutableSet.Builder<String> options = ImmutableSet.builder();
        options.add(new String[]{TOKENS, "outRefMapFile", DISABLE_TARGET_VALIDATOR, DISABLE_TARGET_EXPORT, DISABLE_OVERWRITE_CHECKER, OVERWRITE_ERROR_LEVEL, DEFAULT_OBFUSCATION_ENV, DEPENDENCY_TARGETS_FILE});
        options.addAll(ObfuscationServices.getInstance().getSupportedOptions());
        return options.build();
    }
}
