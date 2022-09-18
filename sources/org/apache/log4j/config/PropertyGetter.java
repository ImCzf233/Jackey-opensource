package org.apache.log4j.config;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.helpers.LogLog;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/config/PropertyGetter.class */
public class PropertyGetter {
    protected static final Object[] NULL_ARG = new Object[0];
    protected Object obj;
    protected PropertyDescriptor[] props;
    static Class class$java$lang$String;
    static Class class$org$apache$log4j$Priority;

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/config/PropertyGetter$PropertyCallback.class */
    public interface PropertyCallback {
        void foundProperty(Object obj, String str, String str2, Object obj2);
    }

    public PropertyGetter(Object obj) throws IntrospectionException {
        BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
        this.props = bi.getPropertyDescriptors();
        this.obj = obj;
    }

    public static void getProperties(Object obj, PropertyCallback callback, String prefix) {
        try {
            new PropertyGetter(obj).getProperties(callback, prefix);
        } catch (IntrospectionException ex) {
            LogLog.error(new StringBuffer().append("Failed to introspect object ").append(obj).toString(), ex);
        }
    }

    public void getProperties(PropertyCallback callback, String prefix) {
        for (int i = 0; i < this.props.length; i++) {
            Method getter = this.props[i].getReadMethod();
            if (getter != null && isHandledType(getter.getReturnType())) {
                String name = this.props[i].getName();
                try {
                    Object result = getter.invoke(this.obj, NULL_ARG);
                    if (result != null) {
                        callback.foundProperty(this.obj, prefix, name, result);
                    }
                } catch (IllegalAccessException e) {
                    LogLog.warn(new StringBuffer().append("Failed to get value of property ").append(name).toString());
                } catch (RuntimeException e2) {
                    LogLog.warn(new StringBuffer().append("Failed to get value of property ").append(name).toString());
                } catch (InvocationTargetException ex) {
                    if ((ex.getTargetException() instanceof InterruptedException) || (ex.getTargetException() instanceof InterruptedIOException)) {
                        Thread.currentThread().interrupt();
                    }
                    LogLog.warn(new StringBuffer().append("Failed to get value of property ").append(name).toString());
                }
            }
        }
    }

    protected boolean isHandledType(Class type) {
        Class cls;
        Class cls2;
        if (class$java$lang$String == null) {
            cls = class$("java.lang.String");
            class$java$lang$String = cls;
        } else {
            cls = class$java$lang$String;
        }
        if (!cls.isAssignableFrom(type) && !Integer.TYPE.isAssignableFrom(type) && !Long.TYPE.isAssignableFrom(type) && !Boolean.TYPE.isAssignableFrom(type)) {
            if (class$org$apache$log4j$Priority == null) {
                cls2 = class$("org.apache.log4j.Priority");
                class$org$apache$log4j$Priority = cls2;
            } else {
                cls2 = class$org$apache$log4j$Priority;
            }
            if (!cls2.isAssignableFrom(type)) {
                return false;
            }
        }
        return true;
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }
}
