package com.viaversion.viaversion.api.platform.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/platform/providers/ViaProviders.class */
public class ViaProviders {
    private final Map<Class<? extends Provider>, Provider> providers = new HashMap();
    private final List<Class<? extends Provider>> lonelyProviders = new ArrayList();

    public void require(Class<? extends Provider> provider) {
        this.lonelyProviders.add(provider);
    }

    public <T extends Provider> void register(Class<T> provider, T value) {
        this.providers.put(provider, value);
    }

    public <T extends Provider> void use(Class<T> provider, T value) {
        this.lonelyProviders.remove(provider);
        this.providers.put(provider, value);
    }

    public <T extends Provider> T get(Class<T> provider) {
        T t = (T) this.providers.get(provider);
        if (t != null) {
            return t;
        }
        if (this.lonelyProviders.contains(provider)) {
            throw new IllegalStateException("There was no provider for " + provider + ", one is required!");
        }
        return null;
    }
}
