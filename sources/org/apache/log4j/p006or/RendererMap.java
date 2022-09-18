package org.apache.log4j.p006or;

import java.util.Hashtable;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.RendererSupport;

/* renamed from: org.apache.log4j.or.RendererMap */
/* loaded from: Jackey Client b2.jar:org/apache/log4j/or/RendererMap.class */
public class RendererMap {
    Hashtable map = new Hashtable();
    static ObjectRenderer defaultRenderer = new DefaultRenderer();
    static Class class$org$apache$log4j$or$ObjectRenderer;

    public static void addRenderer(RendererSupport repository, String renderedClassName, String renderingClassName) {
        Class cls;
        LogLog.debug(new StringBuffer().append("Rendering class: [").append(renderingClassName).append("], Rendered class: [").append(renderedClassName).append("].").toString());
        if (class$org$apache$log4j$or$ObjectRenderer == null) {
            cls = class$("org.apache.log4j.or.ObjectRenderer");
            class$org$apache$log4j$or$ObjectRenderer = cls;
        } else {
            cls = class$org$apache$log4j$or$ObjectRenderer;
        }
        ObjectRenderer renderer = (ObjectRenderer) OptionConverter.instantiateByClassName(renderingClassName, cls, null);
        if (renderer == null) {
            LogLog.error(new StringBuffer().append("Could not instantiate renderer [").append(renderingClassName).append("].").toString());
            return;
        }
        try {
            Class renderedClass = Loader.loadClass(renderedClassName);
            repository.setRenderer(renderedClass, renderer);
        } catch (ClassNotFoundException e) {
            LogLog.error(new StringBuffer().append("Could not find class [").append(renderedClassName).append("].").toString(), e);
        }
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    public String findAndRender(Object o) {
        if (o == null) {
            return null;
        }
        return get((Class) o.getClass()).doRender(o);
    }

    public ObjectRenderer get(Object o) {
        if (o == null) {
            return null;
        }
        return get((Class) o.getClass());
    }

    public ObjectRenderer get(Class clazz) {
        Class cls = clazz;
        while (true) {
            Class c = cls;
            if (c != null) {
                ObjectRenderer r = (ObjectRenderer) this.map.get(c);
                if (r != null) {
                    return r;
                }
                ObjectRenderer r2 = searchInterfaces(c);
                if (r2 == null) {
                    cls = c.getSuperclass();
                } else {
                    return r2;
                }
            } else {
                return defaultRenderer;
            }
        }
    }

    ObjectRenderer searchInterfaces(Class c) {
        ObjectRenderer r = (ObjectRenderer) this.map.get(c);
        if (r != null) {
            return r;
        }
        Class[] ia = c.getInterfaces();
        for (Class cls : ia) {
            ObjectRenderer r2 = searchInterfaces(cls);
            if (r2 != null) {
                return r2;
            }
        }
        return null;
    }

    public ObjectRenderer getDefaultRenderer() {
        return defaultRenderer;
    }

    public void clear() {
        this.map.clear();
    }

    public void put(Class clazz, ObjectRenderer or) {
        this.map.put(clazz, or);
    }
}
