package org.apache.log4j.config;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.OptionHandler;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/config/PropertySetter.class */
public class PropertySetter {
    protected Object obj;
    protected PropertyDescriptor[] props;
    static Class class$org$apache$log4j$spi$OptionHandler;
    static Class class$java$lang$String;
    static Class class$org$apache$log4j$Priority;
    static Class class$org$apache$log4j$spi$ErrorHandler;

    public PropertySetter(Object obj) {
        this.obj = obj;
    }

    protected void introspect() {
        try {
            BeanInfo bi = Introspector.getBeanInfo(this.obj.getClass());
            this.props = bi.getPropertyDescriptors();
        } catch (IntrospectionException ex) {
            LogLog.error(new StringBuffer().append("Failed to introspect ").append(this.obj).append(": ").append(ex.getMessage()).toString());
            this.props = new PropertyDescriptor[0];
        }
    }

    public static void setProperties(Object obj, Properties properties, String prefix) {
        new PropertySetter(obj).setProperties(properties, prefix);
    }

    public void setProperties(Properties properties, String prefix) {
        Class cls;
        int len = prefix.length();
        Enumeration e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            if (key.startsWith(prefix) && key.indexOf(46, len + 1) <= 0) {
                String value = OptionConverter.findAndSubst(key, properties);
                String key2 = key.substring(len);
                if ((!"layout".equals(key2) && !"errorhandler".equals(key2)) || !(this.obj instanceof Appender)) {
                    PropertyDescriptor prop = getPropertyDescriptor(Introspector.decapitalize(key2));
                    if (prop != null) {
                        if (class$org$apache$log4j$spi$OptionHandler == null) {
                            cls = class$("org.apache.log4j.spi.OptionHandler");
                            class$org$apache$log4j$spi$OptionHandler = cls;
                        } else {
                            cls = class$org$apache$log4j$spi$OptionHandler;
                        }
                        if (cls.isAssignableFrom(prop.getPropertyType()) && prop.getWriteMethod() != null) {
                            OptionHandler opt = (OptionHandler) OptionConverter.instantiateByKey(properties, new StringBuffer().append(prefix).append(key2).toString(), prop.getPropertyType(), null);
                            PropertySetter setter = new PropertySetter(opt);
                            setter.setProperties(properties, new StringBuffer().append(prefix).append(key2).append(".").toString());
                            try {
                                prop.getWriteMethod().invoke(this.obj, opt);
                            } catch (IllegalAccessException ex) {
                                LogLog.warn(new StringBuffer().append("Failed to set property [").append(key2).append("] to value \"").append(value).append("\". ").toString(), ex);
                            } catch (RuntimeException ex2) {
                                LogLog.warn(new StringBuffer().append("Failed to set property [").append(key2).append("] to value \"").append(value).append("\". ").toString(), ex2);
                            } catch (InvocationTargetException ex3) {
                                if ((ex3.getTargetException() instanceof InterruptedException) || (ex3.getTargetException() instanceof InterruptedIOException)) {
                                    Thread.currentThread().interrupt();
                                }
                                LogLog.warn(new StringBuffer().append("Failed to set property [").append(key2).append("] to value \"").append(value).append("\". ").toString(), ex3);
                            }
                        }
                    }
                    setProperty(key2, value);
                }
            }
        }
        activate();
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    public void setProperty(String name, String value) {
        if (value == null) {
            return;
        }
        String name2 = Introspector.decapitalize(name);
        PropertyDescriptor prop = getPropertyDescriptor(name2);
        if (prop == null) {
            LogLog.warn(new StringBuffer().append("No such property [").append(name2).append("] in ").append(this.obj.getClass().getName()).append(".").toString());
            return;
        }
        try {
            setProperty(prop, name2, value);
        } catch (PropertySetterException ex) {
            LogLog.warn(new StringBuffer().append("Failed to set property [").append(name2).append("] to value \"").append(value).append("\". ").toString(), ex.rootCause);
        }
    }

    public void setProperty(PropertyDescriptor prop, String name, String value) throws PropertySetterException {
        Method setter = prop.getWriteMethod();
        if (setter == null) {
            throw new PropertySetterException(new StringBuffer().append("No setter for property [").append(name).append("].").toString());
        }
        Class[] paramTypes = setter.getParameterTypes();
        if (paramTypes.length != 1) {
            throw new PropertySetterException("#params for setter != 1");
        }
        try {
            Object arg = convertArg(value, paramTypes[0]);
            if (arg == null) {
                throw new PropertySetterException(new StringBuffer().append("Conversion to type [").append(paramTypes[0]).append("] failed.").toString());
            }
            LogLog.debug(new StringBuffer().append("Setting property [").append(name).append("] to [").append(arg).append("].").toString());
            try {
                setter.invoke(this.obj, arg);
            } catch (IllegalAccessException ex) {
                throw new PropertySetterException(ex);
            } catch (RuntimeException ex2) {
                throw new PropertySetterException(ex2);
            } catch (InvocationTargetException ex3) {
                if ((ex3.getTargetException() instanceof InterruptedException) || (ex3.getTargetException() instanceof InterruptedIOException)) {
                    Thread.currentThread().interrupt();
                }
                throw new PropertySetterException(ex3);
            }
        } catch (Throwable t) {
            throw new PropertySetterException(new StringBuffer().append("Conversion to type [").append(paramTypes[0]).append("] failed. Reason: ").append(t).toString());
        }
    }

    protected Object convertArg(String val, Class type) {
        Class cls;
        Class cls2;
        Class cls3;
        Class cls4;
        if (val == null) {
            return null;
        }
        String v = val.trim();
        if (class$java$lang$String == null) {
            cls = class$("java.lang.String");
            class$java$lang$String = cls;
        } else {
            cls = class$java$lang$String;
        }
        if (cls.isAssignableFrom(type)) {
            return val;
        }
        if (Integer.TYPE.isAssignableFrom(type)) {
            return new Integer(v);
        }
        if (Long.TYPE.isAssignableFrom(type)) {
            return new Long(v);
        }
        if (Boolean.TYPE.isAssignableFrom(type)) {
            if ("true".equalsIgnoreCase(v)) {
                return Boolean.TRUE;
            }
            if ("false".equalsIgnoreCase(v)) {
                return Boolean.FALSE;
            }
            return null;
        }
        if (class$org$apache$log4j$Priority == null) {
            cls2 = class$("org.apache.log4j.Priority");
            class$org$apache$log4j$Priority = cls2;
        } else {
            cls2 = class$org$apache$log4j$Priority;
        }
        if (cls2.isAssignableFrom(type)) {
            return OptionConverter.toLevel(v, Level.DEBUG);
        }
        if (class$org$apache$log4j$spi$ErrorHandler == null) {
            cls3 = class$("org.apache.log4j.spi.ErrorHandler");
            class$org$apache$log4j$spi$ErrorHandler = cls3;
        } else {
            cls3 = class$org$apache$log4j$spi$ErrorHandler;
        }
        if (cls3.isAssignableFrom(type)) {
            if (class$org$apache$log4j$spi$ErrorHandler == null) {
                cls4 = class$("org.apache.log4j.spi.ErrorHandler");
                class$org$apache$log4j$spi$ErrorHandler = cls4;
            } else {
                cls4 = class$org$apache$log4j$spi$ErrorHandler;
            }
            return OptionConverter.instantiateByClassName(v, cls4, null);
        }
        return null;
    }

    protected PropertyDescriptor getPropertyDescriptor(String name) {
        if (this.props == null) {
            introspect();
        }
        for (int i = 0; i < this.props.length; i++) {
            if (name.equals(this.props[i].getName())) {
                return this.props[i];
            }
        }
        return null;
    }

    public void activate() {
        if (this.obj instanceof OptionHandler) {
            ((OptionHandler) this.obj).activateOptions();
        }
    }
}
