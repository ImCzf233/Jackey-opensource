package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/launch/platform/MainAttributes.class */
final class MainAttributes {
    private static final Map<URI, MainAttributes> instances = new HashMap();
    protected final Attributes attributes;

    private MainAttributes() {
        this.attributes = new Attributes();
    }

    private MainAttributes(File jar) {
        this.attributes = getAttributes(jar);
    }

    public final String get(String name) {
        if (this.attributes != null) {
            return this.attributes.getValue(name);
        }
        return null;
    }

    private static Attributes getAttributes(File jar) {
        Manifest manifest;
        if (jar == null) {
            return null;
        }
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jar);
            manifest = jarFile.getManifest();
        } catch (IOException e) {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e2) {
                }
            }
        } catch (Throwable th) {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e3) {
                    throw th;
                }
            }
            throw th;
        }
        if (manifest == null) {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e4) {
                }
            }
            return new Attributes();
        }
        Attributes mainAttributes = manifest.getMainAttributes();
        if (jarFile != null) {
            try {
                jarFile.close();
            } catch (IOException e5) {
            }
        }
        return mainAttributes;
    }

    /* renamed from: of */
    public static MainAttributes m28of(File jar) {
        return m27of(jar.toURI());
    }

    /* renamed from: of */
    public static MainAttributes m27of(URI uri) {
        MainAttributes attributes = instances.get(uri);
        if (attributes == null) {
            attributes = new MainAttributes(new File(uri));
            instances.put(uri, attributes);
        }
        return attributes;
    }
}
