package jdk.nashorn.internal.runtime;

import java.util.Collection;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/CodeInstaller.class */
public interface CodeInstaller {
    Context getContext();

    Class<?> install(String str, byte[] bArr);

    void initialize(Collection<Class<?>> collection, Source source, Object[] objArr);

    void verify(byte[] bArr);

    long getUniqueScriptId();

    void storeScript(String str, Source source, String str2, Map<String, byte[]> map, Map<Integer, FunctionInitializer> map2, Object[] objArr, int i);

    StoredScript loadScript(Source source, String str);

    CodeInstaller withNewLoader();

    boolean isCompatibleWith(CodeInstaller codeInstaller);
}
