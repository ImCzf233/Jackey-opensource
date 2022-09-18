package jdk.nashorn.internal.runtime;

import java.security.CodeSource;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ScriptLoader.class */
public final class ScriptLoader extends NashornLoader {
    private static final String NASHORN_PKG_PREFIX = "jdk.nashorn.internal.";
    private final Context context;

    public Context getContext() {
        return this.context;
    }

    public ScriptLoader(Context context) {
        super(context.getStructLoader());
        this.context = context;
    }

    @Override // java.lang.ClassLoader
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        checkPackageAccess(name);
        return super.loadClass(name, resolve);
    }

    @Override // java.lang.ClassLoader
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        ClassLoader appLoader = this.context.getAppLoader();
        if (appLoader == null || name.startsWith(NASHORN_PKG_PREFIX)) {
            throw new ClassNotFoundException(name);
        }
        return appLoader.loadClass(name);
    }

    public synchronized Class<?> installClass(String name, byte[] data, CodeSource cs) {
        return defineClass(name, data, 0, data.length, (CodeSource) Objects.requireNonNull(cs));
    }
}
