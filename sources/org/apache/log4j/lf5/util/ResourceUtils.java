package org.apache.log4j.lf5.util;

import java.io.InputStream;
import java.net.URL;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/util/ResourceUtils.class */
public class ResourceUtils {
    public static InputStream getResourceAsStream(Object object, Resource resource) {
        InputStream in;
        ClassLoader loader = object.getClass().getClassLoader();
        if (loader != null) {
            in = loader.getResourceAsStream(resource.getName());
        } else {
            in = ClassLoader.getSystemResourceAsStream(resource.getName());
        }
        return in;
    }

    public static URL getResourceAsURL(Object object, Resource resource) {
        URL url;
        ClassLoader loader = object.getClass().getClassLoader();
        if (loader != null) {
            url = loader.getResource(resource.getName());
        } else {
            url = ClassLoader.getSystemResource(resource.getName());
        }
        return url;
    }
}
