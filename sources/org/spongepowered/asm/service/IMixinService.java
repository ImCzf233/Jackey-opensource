package org.spongepowered.asm.service;

import java.io.InputStream;
import java.util.Collection;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.util.ReEntranceLock;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/service/IMixinService.class */
public interface IMixinService {
    String getName();

    boolean isValid();

    void prepare();

    MixinEnvironment.Phase getInitialPhase();

    void init();

    void beginPhase();

    void checkEnv(Object obj);

    ReEntranceLock getReEntranceLock();

    IClassProvider getClassProvider();

    IClassBytecodeProvider getBytecodeProvider();

    Collection<String> getPlatformAgents();

    InputStream getResourceAsStream(String str);

    void registerInvalidClass(String str);

    boolean isClassLoaded(String str);

    String getClassRestrictions(String str);

    Collection<ITransformer> getTransformers();

    String getSideName();
}
