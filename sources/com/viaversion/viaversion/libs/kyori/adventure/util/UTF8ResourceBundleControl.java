package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/UTF8ResourceBundleControl.class */
public final class UTF8ResourceBundleControl extends ResourceBundle.Control {
    private static final UTF8ResourceBundleControl INSTANCE = new UTF8ResourceBundleControl();

    public static ResourceBundle.Control get() {
        return INSTANCE;
    }

    @Override // java.util.ResourceBundle.Control
    public ResourceBundle newBundle(final String baseName, final Locale locale, final String format, final ClassLoader loader, final boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        if (format.equals("java.properties")) {
            String bundle = toBundleName(baseName, locale);
            String resource = toResourceName(bundle, "properties");
            try {
                InputStream is = (InputStream) AccessController.doPrivileged(() -> {
                    URLConnection connection;
                    if (reload) {
                        URL url = loader.getResource(resource);
                        if (url != null && (connection = url.openConnection()) != null) {
                            connection.setUseCaches(false);
                            return connection.getInputStream();
                        }
                        return null;
                    }
                    return loader.getResourceAsStream(resource);
                });
                if (is != null) {
                    InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                    try {
                        PropertyResourceBundle propertyResourceBundle = new PropertyResourceBundle(isr);
                        isr.close();
                        return propertyResourceBundle;
                    } catch (Throwable th) {
                        try {
                            isr.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                return null;
            } catch (PrivilegedActionException e) {
                throw ((IOException) e.getException());
            }
        }
        return super.newBundle(baseName, locale, format, loader, reload);
    }
}
