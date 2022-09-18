package org.spongepowered.asm.mixin.extensibility;

import org.apache.logging.log4j.Level;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/extensibility/IMixinErrorHandler.class */
public interface IMixinErrorHandler {
    ErrorAction onPrepareError(IMixinConfig iMixinConfig, Throwable th, IMixinInfo iMixinInfo, ErrorAction errorAction);

    ErrorAction onApplyError(String str, Throwable th, IMixinInfo iMixinInfo, ErrorAction errorAction);

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/extensibility/IMixinErrorHandler$ErrorAction.class */
    public enum ErrorAction {
        NONE(Level.INFO),
        WARN(Level.WARN),
        ERROR(Level.FATAL);
        
        public final Level logLevel;

        ErrorAction(Level logLevel) {
            this.logLevel = logLevel;
        }
    }
}
