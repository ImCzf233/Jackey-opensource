package org.apache.log4j.jmx;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MBeanRegistration;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.RuntimeOperationsException;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/jmx/AbstractDynamicMBean.class */
public abstract class AbstractDynamicMBean implements DynamicMBean, MBeanRegistration {
    String dClassName;
    MBeanServer server;
    private final Vector mbeanList = new Vector();

    protected abstract Logger getLogger();

    public static String getAppenderName(Appender appender) {
        String name = appender.getName();
        if (name == null || name.trim().length() == 0) {
            name = appender.toString();
        }
        return name;
    }

    public AttributeList getAttributes(String[] attributeNames) {
        if (attributeNames == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("attributeNames[] cannot be null"), new StringBuffer().append("Cannot invoke a getter of ").append(this.dClassName).toString());
        }
        AttributeList resultList = new AttributeList();
        if (attributeNames.length == 0) {
            return resultList;
        }
        for (int i = 0; i < attributeNames.length; i++) {
            try {
                Object value = getAttribute(attributeNames[i]);
                resultList.add(new Attribute(attributeNames[i], value));
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (JMException e2) {
                e2.printStackTrace();
            }
        }
        return resultList;
    }

    public AttributeList setAttributes(AttributeList attributes) {
        if (attributes == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("AttributeList attributes cannot be null"), new StringBuffer().append("Cannot invoke a setter of ").append(this.dClassName).toString());
        }
        AttributeList resultList = new AttributeList();
        if (attributes.isEmpty()) {
            return resultList;
        }
        Iterator i = attributes.iterator();
        while (i.hasNext()) {
            Attribute attr = (Attribute) i.next();
            try {
                setAttribute(attr);
                String name = attr.getName();
                Object value = getAttribute(name);
                resultList.add(new Attribute(name, value));
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (JMException e2) {
                e2.printStackTrace();
            }
        }
        return resultList;
    }

    public void postDeregister() {
        getLogger().debug("postDeregister is called.");
    }

    public void postRegister(Boolean registrationDone) {
    }

    public ObjectName preRegister(MBeanServer server, ObjectName name) {
        getLogger().debug(new StringBuffer().append("preRegister called. Server=").append(server).append(", name=").append(name).toString());
        this.server = server;
        return name;
    }

    public void registerMBean(Object mbean, ObjectName objectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        this.server.registerMBean(mbean, objectName);
        this.mbeanList.add(objectName);
    }

    public void preDeregister() {
        getLogger().debug("preDeregister called.");
        Enumeration iterator = this.mbeanList.elements();
        while (iterator.hasMoreElements()) {
            ObjectName name = (ObjectName) iterator.nextElement();
            try {
                this.server.unregisterMBean(name);
            } catch (InstanceNotFoundException e) {
                getLogger().warn(new StringBuffer().append("Missing MBean ").append(name.getCanonicalName()).toString());
            } catch (MBeanRegistrationException e2) {
                getLogger().warn(new StringBuffer().append("Failed unregistering ").append(name.getCanonicalName()).toString());
            }
        }
    }
}
