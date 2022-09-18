package org.apache.log4j;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.p006or.ObjectRenderer;
import org.apache.log4j.p006or.RendererMap;
import org.apache.log4j.spi.HierarchyEventListener;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RendererSupport;
import org.apache.log4j.spi.ThrowableRenderer;
import org.apache.log4j.spi.ThrowableRendererSupport;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/Hierarchy.class */
public class Hierarchy implements LoggerRepository, RendererSupport, ThrowableRendererSupport {
    Logger root;
    int thresholdInt;
    Level threshold;
    boolean emittedNoAppenderWarning = false;
    boolean emittedNoResourceBundleWarning = false;
    private ThrowableRenderer throwableRenderer = null;

    /* renamed from: ht */
    Hashtable f384ht = new Hashtable();
    private Vector listeners = new Vector(1);
    RendererMap rendererMap = new RendererMap();
    private LoggerFactory defaultFactory = new DefaultCategoryFactory();

    public Hierarchy(Logger root) {
        this.root = root;
        setThreshold(Level.ALL);
        this.root.setHierarchy(this);
    }

    public void addRenderer(Class classToRender, ObjectRenderer or) {
        this.rendererMap.put(classToRender, or);
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void addHierarchyEventListener(HierarchyEventListener listener) {
        if (this.listeners.contains(listener)) {
            LogLog.warn("Ignoring attempt to add an existent listener.");
        } else {
            this.listeners.addElement(listener);
        }
    }

    public void clear() {
        this.f384ht.clear();
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void emitNoAppenderWarning(Category cat) {
        if (!this.emittedNoAppenderWarning) {
            LogLog.warn(new StringBuffer().append("No appenders could be found for logger (").append(cat.getName()).append(").").toString());
            LogLog.warn("Please initialize the log4j system properly.");
            LogLog.warn("See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.");
            this.emittedNoAppenderWarning = true;
        }
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger exists(String name) {
        Object o = this.f384ht.get(new CategoryKey(name));
        if (o instanceof Logger) {
            return (Logger) o;
        }
        return null;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void setThreshold(String levelStr) {
        Level l = Level.toLevel(levelStr, (Level) null);
        if (l != null) {
            setThreshold(l);
        } else {
            LogLog.warn(new StringBuffer().append("Could not convert [").append(levelStr).append("] to Level.").toString());
        }
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void setThreshold(Level l) {
        if (l != null) {
            this.thresholdInt = l.level;
            this.threshold = l;
        }
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void fireAddAppenderEvent(Category logger, Appender appender) {
        if (this.listeners != null) {
            int size = this.listeners.size();
            for (int i = 0; i < size; i++) {
                HierarchyEventListener listener = (HierarchyEventListener) this.listeners.elementAt(i);
                listener.addAppenderEvent(logger, appender);
            }
        }
    }

    public void fireRemoveAppenderEvent(Category logger, Appender appender) {
        if (this.listeners != null) {
            int size = this.listeners.size();
            for (int i = 0; i < size; i++) {
                HierarchyEventListener listener = (HierarchyEventListener) this.listeners.elementAt(i);
                listener.removeAppenderEvent(logger, appender);
            }
        }
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Level getThreshold() {
        return this.threshold;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getLogger(String name) {
        return getLogger(name, this.defaultFactory);
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getLogger(String name, LoggerFactory factory) {
        CategoryKey key = new CategoryKey(name);
        synchronized (this.f384ht) {
            Object o = this.f384ht.get(key);
            if (o == null) {
                Logger logger = factory.makeNewLoggerInstance(name);
                logger.setHierarchy(this);
                this.f384ht.put(key, logger);
                updateParents(logger);
                return logger;
            } else if (o instanceof Logger) {
                return (Logger) o;
            } else if (o instanceof ProvisionNode) {
                Logger logger2 = factory.makeNewLoggerInstance(name);
                logger2.setHierarchy(this);
                this.f384ht.put(key, logger2);
                updateChildren((ProvisionNode) o, logger2);
                updateParents(logger2);
                return logger2;
            } else {
                return null;
            }
        }
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Enumeration getCurrentLoggers() {
        Vector v = new Vector(this.f384ht.size());
        Enumeration elems = this.f384ht.elements();
        while (elems.hasMoreElements()) {
            Object o = elems.nextElement();
            if (o instanceof Logger) {
                v.addElement(o);
            }
        }
        return v.elements();
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Enumeration getCurrentCategories() {
        return getCurrentLoggers();
    }

    @Override // org.apache.log4j.spi.RendererSupport
    public RendererMap getRendererMap() {
        return this.rendererMap;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getRootLogger() {
        return this.root;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public boolean isDisabled(int level) {
        return this.thresholdInt > level;
    }

    public void overrideAsNeeded(String override) {
        LogLog.warn("The Hiearchy.overrideAsNeeded method has been deprecated.");
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void resetConfiguration() {
        getRootLogger().setLevel(Level.DEBUG);
        this.root.setResourceBundle(null);
        setThreshold(Level.ALL);
        synchronized (this.f384ht) {
            shutdown();
            Enumeration cats = getCurrentLoggers();
            while (cats.hasMoreElements()) {
                Logger c = (Logger) cats.nextElement();
                c.setLevel(null);
                c.setAdditivity(true);
                c.setResourceBundle(null);
            }
        }
        this.rendererMap.clear();
        this.throwableRenderer = null;
    }

    public void setDisableOverride(String override) {
        LogLog.warn("The Hiearchy.setDisableOverride method has been deprecated.");
    }

    @Override // org.apache.log4j.spi.RendererSupport
    public void setRenderer(Class renderedClass, ObjectRenderer renderer) {
        this.rendererMap.put(renderedClass, renderer);
    }

    @Override // org.apache.log4j.spi.ThrowableRendererSupport
    public void setThrowableRenderer(ThrowableRenderer renderer) {
        this.throwableRenderer = renderer;
    }

    @Override // org.apache.log4j.spi.ThrowableRendererSupport
    public ThrowableRenderer getThrowableRenderer() {
        return this.throwableRenderer;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void shutdown() {
        Logger root = getRootLogger();
        root.closeNestedAppenders();
        synchronized (this.f384ht) {
            Enumeration cats = getCurrentLoggers();
            while (cats.hasMoreElements()) {
                Logger c = (Logger) cats.nextElement();
                c.closeNestedAppenders();
            }
            root.removeAllAppenders();
            Enumeration cats2 = getCurrentLoggers();
            while (cats2.hasMoreElements()) {
                Logger c2 = (Logger) cats2.nextElement();
                c2.removeAllAppenders();
            }
        }
    }

    private final void updateParents(Logger cat) {
        String name = cat.name;
        int length = name.length();
        boolean parentFound = false;
        int lastIndexOf = name.lastIndexOf(46, length - 1);
        while (true) {
            int i = lastIndexOf;
            if (i < 0) {
                break;
            }
            String substr = name.substring(0, i);
            CategoryKey key = new CategoryKey(substr);
            Object o = this.f384ht.get(key);
            if (o == null) {
                ProvisionNode pn = new ProvisionNode(cat);
                this.f384ht.put(key, pn);
            } else if (o instanceof Category) {
                parentFound = true;
                cat.parent = (Category) o;
                break;
            } else if (o instanceof ProvisionNode) {
                ((ProvisionNode) o).addElement(cat);
            } else {
                Exception e = new IllegalStateException(new StringBuffer().append("unexpected object type ").append(o.getClass()).append(" in ht.").toString());
                e.printStackTrace();
            }
            lastIndexOf = name.lastIndexOf(46, i - 1);
        }
        if (!parentFound) {
            cat.parent = this.root;
        }
    }

    private final void updateChildren(ProvisionNode pn, Logger logger) {
        int last = pn.size();
        for (int i = 0; i < last; i++) {
            Logger l = (Logger) pn.elementAt(i);
            if (!l.parent.name.startsWith(logger.name)) {
                logger.parent = l.parent;
                l.parent = logger;
            }
        }
    }
}
