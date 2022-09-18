package org.apache.log4j.jmx;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InterruptedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;
import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.OptionHandler;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/jmx/LayoutDynamicMBean.class */
public class LayoutDynamicMBean extends AbstractDynamicMBean {
    private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
    private Vector dAttributes = new Vector();
    private String dClassName = getClass().getName();
    private Hashtable dynamicProps = new Hashtable(5);
    private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[1];
    private String dDescription = "This MBean acts as a management facade for log4j layouts.";
    private static Logger cat;
    private Layout layout;
    static Class class$org$apache$log4j$jmx$LayoutDynamicMBean;
    static Class class$org$apache$log4j$Level;
    static Class class$java$lang$String;
    static Class class$org$apache$log4j$Priority;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$jmx$LayoutDynamicMBean == null) {
            cls = class$("org.apache.log4j.jmx.LayoutDynamicMBean");
            class$org$apache$log4j$jmx$LayoutDynamicMBean = cls;
        } else {
            cls = class$org$apache$log4j$jmx$LayoutDynamicMBean;
        }
        cat = Logger.getLogger(cls);
    }

    public LayoutDynamicMBean(Layout layout) throws IntrospectionException {
        this.layout = layout;
        buildDynamicMBeanInfo();
    }

    private void buildDynamicMBeanInfo() throws IntrospectionException {
        Class<?> cls;
        String returnClassName;
        Constructor[] constructors = getClass().getConstructors();
        this.dConstructors[0] = new MBeanConstructorInfo("LayoutDynamicMBean(): Constructs a LayoutDynamicMBean instance", constructors[0]);
        BeanInfo bi = Introspector.getBeanInfo(this.layout.getClass());
        PropertyDescriptor[] pd = bi.getPropertyDescriptors();
        int size = pd.length;
        for (int i = 0; i < size; i++) {
            String name = pd[i].getName();
            Method readMethod = pd[i].getReadMethod();
            Method writeMethod = pd[i].getWriteMethod();
            if (readMethod != null) {
                Class returnClass = readMethod.getReturnType();
                if (isSupportedType(returnClass)) {
                    if (class$org$apache$log4j$Level == null) {
                        cls = class$("org.apache.log4j.Level");
                        class$org$apache$log4j$Level = cls;
                    } else {
                        cls = class$org$apache$log4j$Level;
                    }
                    if (returnClass.isAssignableFrom(cls)) {
                        returnClassName = "java.lang.String";
                    } else {
                        returnClassName = returnClass.getName();
                    }
                    this.dAttributes.add(new MBeanAttributeInfo(name, returnClassName, "Dynamic", true, writeMethod != null, false));
                    this.dynamicProps.put(name, new MethodUnion(readMethod, writeMethod));
                }
            }
        }
        MBeanParameterInfo[] params = new MBeanParameterInfo[0];
        this.dOperations[0] = new MBeanOperationInfo("activateOptions", "activateOptions(): add an layout", params, "void", 1);
    }

    private boolean isSupportedType(Class clazz) {
        Class cls;
        Class<?> cls2;
        if (clazz.isPrimitive()) {
            return true;
        }
        if (class$java$lang$String == null) {
            cls = class$("java.lang.String");
            class$java$lang$String = cls;
        } else {
            cls = class$java$lang$String;
        }
        if (clazz == cls) {
            return true;
        }
        if (class$org$apache$log4j$Level == null) {
            cls2 = class$("org.apache.log4j.Level");
            class$org$apache$log4j$Level = cls2;
        } else {
            cls2 = class$org$apache$log4j$Level;
        }
        if (clazz.isAssignableFrom(cls2)) {
            return true;
        }
        return false;
    }

    public MBeanInfo getMBeanInfo() {
        cat.debug("getMBeanInfo called.");
        MBeanAttributeInfo[] attribs = new MBeanAttributeInfo[this.dAttributes.size()];
        this.dAttributes.toArray(attribs);
        return new MBeanInfo(this.dClassName, this.dDescription, attribs, this.dConstructors, this.dOperations, new MBeanNotificationInfo[0]);
    }

    public Object invoke(String operationName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        if (operationName.equals("activateOptions") && (this.layout instanceof OptionHandler)) {
            OptionHandler oh = this.layout;
            oh.activateOptions();
            return "Options activated.";
        }
        return null;
    }

    @Override // org.apache.log4j.jmx.AbstractDynamicMBean
    protected Logger getLogger() {
        return cat;
    }

    public Object getAttribute(String attributeName) throws AttributeNotFoundException, MBeanException, ReflectionException {
        if (attributeName == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), new StringBuffer().append("Cannot invoke a getter of ").append(this.dClassName).append(" with null attribute name").toString());
        }
        MethodUnion mu = (MethodUnion) this.dynamicProps.get(attributeName);
        cat.debug(new StringBuffer().append("----name=").append(attributeName).append(", mu=").append(mu).toString());
        if (mu != null && mu.readMethod != null) {
            try {
                return mu.readMethod.invoke(this.layout, null);
            } catch (IllegalAccessException e) {
                return null;
            } catch (RuntimeException e2) {
                return null;
            } catch (InvocationTargetException e3) {
                if ((e3.getTargetException() instanceof InterruptedException) || (e3.getTargetException() instanceof InterruptedIOException)) {
                    Thread.currentThread().interrupt();
                    return null;
                }
                return null;
            }
        }
        throw new AttributeNotFoundException(new StringBuffer().append("Cannot find ").append(attributeName).append(" attribute in ").append(this.dClassName).toString());
    }

    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        Class cls;
        if (attribute == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), new StringBuffer().append("Cannot invoke a setter of ").append(this.dClassName).append(" with null attribute").toString());
        }
        String name = attribute.getName();
        Object value = attribute.getValue();
        if (name == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), new StringBuffer().append("Cannot invoke the setter of ").append(this.dClassName).append(" with null attribute name").toString());
        }
        MethodUnion mu = (MethodUnion) this.dynamicProps.get(name);
        if (mu != null && mu.writeMethod != null) {
            Object[] o = new Object[1];
            Class[] params = mu.writeMethod.getParameterTypes();
            Class cls2 = params[0];
            if (class$org$apache$log4j$Priority == null) {
                cls = class$("org.apache.log4j.Priority");
                class$org$apache$log4j$Priority = cls;
            } else {
                cls = class$org$apache$log4j$Priority;
            }
            if (cls2 == cls) {
                value = OptionConverter.toLevel((String) value, (Level) getAttribute(name));
            }
            o[0] = value;
            try {
                mu.writeMethod.invoke(this.layout, o);
                return;
            } catch (IllegalAccessException e) {
                cat.error("FIXME", e);
                return;
            } catch (RuntimeException e2) {
                cat.error("FIXME", e2);
                return;
            } catch (InvocationTargetException e3) {
                if ((e3.getTargetException() instanceof InterruptedException) || (e3.getTargetException() instanceof InterruptedIOException)) {
                    Thread.currentThread().interrupt();
                }
                cat.error("FIXME", e3);
                return;
            }
        }
        throw new AttributeNotFoundException(new StringBuffer().append("Attribute ").append(name).append(" not found in ").append(getClass().getName()).toString());
    }
}
