package com.viaversion.viaversion.api.configuration;

import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/configuration/ConfigurationProvider.class */
public interface ConfigurationProvider {
    void set(String str, Object obj);

    void saveConfig();

    void reloadConfig();

    Map<String, Object> getValues();
}
