package org.apache.log4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.config.PropertySetter;
import org.apache.log4j.helpers.FileWatchdog;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.p006or.RendererMap;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.OptionHandler;
import org.apache.log4j.spi.RendererSupport;
import org.apache.log4j.spi.ThrowableRenderer;
import org.apache.log4j.spi.ThrowableRendererSupport;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/PropertyConfigurator.class */
public class PropertyConfigurator implements Configurator {
    private LoggerRepository repository;
    static final String CATEGORY_PREFIX = "log4j.category.";
    static final String LOGGER_PREFIX = "log4j.logger.";
    static final String FACTORY_PREFIX = "log4j.factory";
    static final String ADDITIVITY_PREFIX = "log4j.additivity.";
    static final String ROOT_CATEGORY_PREFIX = "log4j.rootCategory";
    static final String ROOT_LOGGER_PREFIX = "log4j.rootLogger";
    static final String APPENDER_PREFIX = "log4j.appender.";
    static final String RENDERER_PREFIX = "log4j.renderer.";
    static final String THRESHOLD_PREFIX = "log4j.threshold";
    private static final String THROWABLE_RENDERER_PREFIX = "log4j.throwableRenderer";
    private static final String LOGGER_REF = "logger-ref";
    private static final String ROOT_REF = "root-ref";
    private static final String APPENDER_REF_TAG = "appender-ref";
    public static final String LOGGER_FACTORY_KEY = "log4j.loggerFactory";
    private static final String RESET_KEY = "log4j.reset";
    private static final String INTERNAL_ROOT_NAME = "root";
    static Class class$org$apache$log4j$spi$LoggerFactory;
    static Class class$org$apache$log4j$spi$ThrowableRenderer;
    static Class class$org$apache$log4j$Appender;
    static Class class$org$apache$log4j$Layout;
    static Class class$org$apache$log4j$spi$ErrorHandler;
    static Class class$org$apache$log4j$spi$Filter;
    protected Hashtable registry = new Hashtable(11);
    protected LoggerFactory loggerFactory = new DefaultCategoryFactory();

    public void doConfigure(String configFileName, LoggerRepository hierarchy) {
        Properties props = new Properties();
        FileInputStream istream = null;
        try {
            try {
                istream = new FileInputStream(configFileName);
                props.load(istream);
                istream.close();
                if (istream != null) {
                    try {
                        istream.close();
                    } catch (InterruptedIOException e) {
                        Thread.currentThread().interrupt();
                    } catch (Throwable th) {
                    }
                }
                doConfigure(props, hierarchy);
            } catch (Exception e2) {
                if ((e2 instanceof InterruptedIOException) || (e2 instanceof InterruptedException)) {
                    Thread.currentThread().interrupt();
                }
                LogLog.error(new StringBuffer().append("Could not read configuration file [").append(configFileName).append("].").toString(), e2);
                LogLog.error(new StringBuffer().append("Ignoring configuration file [").append(configFileName).append("].").toString());
                if (istream == null) {
                    return;
                }
                try {
                    istream.close();
                } catch (InterruptedIOException e3) {
                    Thread.currentThread().interrupt();
                } catch (Throwable th2) {
                }
            }
        } catch (Throwable th3) {
            if (istream != null) {
                try {
                    istream.close();
                } catch (InterruptedIOException e4) {
                    Thread.currentThread().interrupt();
                } catch (Throwable th4) {
                }
            }
            throw th3;
        }
    }

    public static void configure(String configFilename) {
        new PropertyConfigurator().doConfigure(configFilename, LogManager.getLoggerRepository());
    }

    public static void configure(URL configURL) {
        new PropertyConfigurator().doConfigure(configURL, LogManager.getLoggerRepository());
    }

    public static void configure(InputStream inputStream) {
        new PropertyConfigurator().doConfigure(inputStream, LogManager.getLoggerRepository());
    }

    public static void configure(Properties properties) {
        new PropertyConfigurator().doConfigure(properties, LogManager.getLoggerRepository());
    }

    public static void configureAndWatch(String configFilename) {
        configureAndWatch(configFilename, FileWatchdog.DEFAULT_DELAY);
    }

    public static void configureAndWatch(String configFilename, long delay) {
        PropertyWatchdog pdog = new PropertyWatchdog(configFilename);
        pdog.setDelay(delay);
        pdog.start();
    }

    public void doConfigure(Properties properties, LoggerRepository hierarchy) {
        this.repository = hierarchy;
        String value = properties.getProperty(LogLog.DEBUG_KEY);
        if (value == null) {
            value = properties.getProperty(LogLog.CONFIG_DEBUG_KEY);
            if (value != null) {
                LogLog.warn("[log4j.configDebug] is deprecated. Use [log4j.debug] instead.");
            }
        }
        if (value != null) {
            LogLog.setInternalDebugging(OptionConverter.toBoolean(value, true));
        }
        String reset = properties.getProperty(RESET_KEY);
        if (reset != null && OptionConverter.toBoolean(reset, false)) {
            hierarchy.resetConfiguration();
        }
        String thresholdStr = OptionConverter.findAndSubst(THRESHOLD_PREFIX, properties);
        if (thresholdStr != null) {
            hierarchy.setThreshold(OptionConverter.toLevel(thresholdStr, Level.ALL));
            LogLog.debug(new StringBuffer().append("Hierarchy threshold set to [").append(hierarchy.getThreshold()).append("].").toString());
        }
        configureRootCategory(properties, hierarchy);
        configureLoggerFactory(properties);
        parseCatsAndRenderers(properties, hierarchy);
        LogLog.debug("Finished configuring.");
        this.registry.clear();
    }

    @Override // org.apache.log4j.spi.Configurator
    public void doConfigure(InputStream inputStream, LoggerRepository hierarchy) {
        Properties props = new Properties();
        try {
            props.load(inputStream);
            doConfigure(props, hierarchy);
        } catch (IOException e) {
            if (e instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            LogLog.error(new StringBuffer().append("Could not read configuration file from InputStream [").append(inputStream).append("].").toString(), e);
            LogLog.error(new StringBuffer().append("Ignoring configuration InputStream [").append(inputStream).append("].").toString());
        }
    }

    @Override // org.apache.log4j.spi.Configurator
    public void doConfigure(URL configURL, LoggerRepository hierarchy) {
        Properties props = new Properties();
        LogLog.debug(new StringBuffer().append("Reading configuration from URL ").append(configURL).toString());
        InputStream istream = null;
        try {
            try {
                URLConnection uConn = configURL.openConnection();
                uConn.setUseCaches(false);
                istream = uConn.getInputStream();
                props.load(istream);
                if (istream != null) {
                    try {
                        istream.close();
                    } catch (InterruptedIOException e) {
                        Thread.currentThread().interrupt();
                    } catch (IOException e2) {
                    } catch (RuntimeException e3) {
                    }
                }
                doConfigure(props, hierarchy);
            } catch (Exception e4) {
                if ((e4 instanceof InterruptedIOException) || (e4 instanceof InterruptedException)) {
                    Thread.currentThread().interrupt();
                }
                LogLog.error(new StringBuffer().append("Could not read configuration file from URL [").append(configURL).append("].").toString(), e4);
                LogLog.error(new StringBuffer().append("Ignoring configuration file [").append(configURL).append("].").toString());
                if (istream == null) {
                    return;
                }
                try {
                    istream.close();
                } catch (InterruptedIOException e5) {
                    Thread.currentThread().interrupt();
                } catch (IOException e6) {
                } catch (RuntimeException e7) {
                }
            }
        } catch (Throwable th) {
            if (istream != null) {
                try {
                    istream.close();
                } catch (InterruptedIOException e8) {
                    Thread.currentThread().interrupt();
                } catch (IOException e9) {
                } catch (RuntimeException e10) {
                }
            }
            throw th;
        }
    }

    protected void configureLoggerFactory(Properties props) {
        Class cls;
        String factoryClassName = OptionConverter.findAndSubst(LOGGER_FACTORY_KEY, props);
        if (factoryClassName != null) {
            LogLog.debug(new StringBuffer().append("Setting category factory to [").append(factoryClassName).append("].").toString());
            if (class$org$apache$log4j$spi$LoggerFactory == null) {
                cls = class$("org.apache.log4j.spi.LoggerFactory");
                class$org$apache$log4j$spi$LoggerFactory = cls;
            } else {
                cls = class$org$apache$log4j$spi$LoggerFactory;
            }
            this.loggerFactory = (LoggerFactory) OptionConverter.instantiateByClassName(factoryClassName, cls, this.loggerFactory);
            PropertySetter.setProperties(this.loggerFactory, props, "log4j.factory.");
        }
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    void configureRootCategory(Properties props, LoggerRepository hierarchy) {
        String effectiveFrefix = ROOT_LOGGER_PREFIX;
        String value = OptionConverter.findAndSubst(ROOT_LOGGER_PREFIX, props);
        if (value == null) {
            value = OptionConverter.findAndSubst(ROOT_CATEGORY_PREFIX, props);
            effectiveFrefix = ROOT_CATEGORY_PREFIX;
        }
        if (value == null) {
            LogLog.debug("Could not find root logger information. Is this OK?");
            return;
        }
        Logger root = hierarchy.getRootLogger();
        synchronized (root) {
            parseCategory(props, root, effectiveFrefix, INTERNAL_ROOT_NAME, value);
        }
    }

    protected void parseCatsAndRenderers(Properties props, LoggerRepository hierarchy) {
        Class cls;
        Enumeration enumeration = props.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            if (key.startsWith(CATEGORY_PREFIX) || key.startsWith(LOGGER_PREFIX)) {
                String loggerName = null;
                if (key.startsWith(CATEGORY_PREFIX)) {
                    loggerName = key.substring(CATEGORY_PREFIX.length());
                } else if (key.startsWith(LOGGER_PREFIX)) {
                    loggerName = key.substring(LOGGER_PREFIX.length());
                }
                String value = OptionConverter.findAndSubst(key, props);
                Logger logger = hierarchy.getLogger(loggerName, this.loggerFactory);
                synchronized (logger) {
                    parseCategory(props, logger, key, loggerName, value);
                    parseAdditivityForLogger(props, logger, loggerName);
                }
            } else if (key.startsWith(RENDERER_PREFIX)) {
                String renderedClass = key.substring(RENDERER_PREFIX.length());
                String renderingClass = OptionConverter.findAndSubst(key, props);
                if (hierarchy instanceof RendererSupport) {
                    RendererMap.addRenderer((RendererSupport) hierarchy, renderedClass, renderingClass);
                }
            } else if (key.equals(THROWABLE_RENDERER_PREFIX) && (hierarchy instanceof ThrowableRendererSupport)) {
                if (class$org$apache$log4j$spi$ThrowableRenderer == null) {
                    cls = class$("org.apache.log4j.spi.ThrowableRenderer");
                    class$org$apache$log4j$spi$ThrowableRenderer = cls;
                } else {
                    cls = class$org$apache$log4j$spi$ThrowableRenderer;
                }
                ThrowableRenderer tr = (ThrowableRenderer) OptionConverter.instantiateByKey(props, THROWABLE_RENDERER_PREFIX, cls, null);
                if (tr == null) {
                    LogLog.error("Could not instantiate throwableRenderer.");
                } else {
                    PropertySetter setter = new PropertySetter(tr);
                    setter.setProperties(props, "log4j.throwableRenderer.");
                    ((ThrowableRendererSupport) hierarchy).setThrowableRenderer(tr);
                }
            }
        }
    }

    void parseAdditivityForLogger(Properties props, Logger cat, String loggerName) {
        String value = OptionConverter.findAndSubst(new StringBuffer().append(ADDITIVITY_PREFIX).append(loggerName).toString(), props);
        LogLog.debug(new StringBuffer().append("Handling log4j.additivity.").append(loggerName).append("=[").append(value).append("]").toString());
        if (value != null && !value.equals("")) {
            boolean additivity = OptionConverter.toBoolean(value, true);
            LogLog.debug(new StringBuffer().append("Setting additivity for \"").append(loggerName).append("\" to ").append(additivity).toString());
            cat.setAdditivity(additivity);
        }
    }

    void parseCategory(Properties props, Logger logger, String optionKey, String loggerName, String value) {
        LogLog.debug(new StringBuffer().append("Parsing for [").append(loggerName).append("] with value=[").append(value).append("].").toString());
        StringTokenizer st = new StringTokenizer(value, ",");
        if (!value.startsWith(",") && !value.equals("")) {
            if (!st.hasMoreTokens()) {
                return;
            }
            String levelStr = st.nextToken();
            LogLog.debug(new StringBuffer().append("Level token is [").append(levelStr).append("].").toString());
            if (Configurator.INHERITED.equalsIgnoreCase(levelStr) || Configurator.NULL.equalsIgnoreCase(levelStr)) {
                if (loggerName.equals(INTERNAL_ROOT_NAME)) {
                    LogLog.warn("The root logger cannot be set to null.");
                } else {
                    logger.setLevel(null);
                }
            } else {
                logger.setLevel(OptionConverter.toLevel(levelStr, Level.DEBUG));
            }
            LogLog.debug(new StringBuffer().append("Category ").append(loggerName).append(" set to ").append(logger.getLevel()).toString());
        }
        logger.removeAllAppenders();
        while (st.hasMoreTokens()) {
            String appenderName = st.nextToken().trim();
            if (appenderName != null && !appenderName.equals(",")) {
                LogLog.debug(new StringBuffer().append("Parsing appender named \"").append(appenderName).append("\".").toString());
                Appender appender = parseAppender(props, appenderName);
                if (appender != null) {
                    logger.addAppender(appender);
                }
            }
        }
    }

    Appender parseAppender(Properties props, String appenderName) {
        Class cls;
        Class cls2;
        Class cls3;
        Appender appender = registryGet(appenderName);
        if (appender != null) {
            LogLog.debug(new StringBuffer().append("Appender \"").append(appenderName).append("\" was already parsed.").toString());
            return appender;
        }
        String prefix = new StringBuffer().append(APPENDER_PREFIX).append(appenderName).toString();
        String layoutPrefix = new StringBuffer().append(prefix).append(".layout").toString();
        if (class$org$apache$log4j$Appender == null) {
            cls = class$("org.apache.log4j.Appender");
            class$org$apache$log4j$Appender = cls;
        } else {
            cls = class$org$apache$log4j$Appender;
        }
        Appender appender2 = (Appender) OptionConverter.instantiateByKey(props, prefix, cls, null);
        if (appender2 == null) {
            LogLog.error(new StringBuffer().append("Could not instantiate appender named \"").append(appenderName).append("\".").toString());
            return null;
        }
        appender2.setName(appenderName);
        if (appender2 instanceof OptionHandler) {
            if (appender2.requiresLayout()) {
                if (class$org$apache$log4j$Layout == null) {
                    cls3 = class$("org.apache.log4j.Layout");
                    class$org$apache$log4j$Layout = cls3;
                } else {
                    cls3 = class$org$apache$log4j$Layout;
                }
                Layout layout = (Layout) OptionConverter.instantiateByKey(props, layoutPrefix, cls3, null);
                if (layout != null) {
                    appender2.setLayout(layout);
                    LogLog.debug(new StringBuffer().append("Parsing layout options for \"").append(appenderName).append("\".").toString());
                    PropertySetter.setProperties(layout, props, new StringBuffer().append(layoutPrefix).append(".").toString());
                    LogLog.debug(new StringBuffer().append("End of parsing for \"").append(appenderName).append("\".").toString());
                }
            }
            String errorHandlerPrefix = new StringBuffer().append(prefix).append(".errorhandler").toString();
            String errorHandlerClass = OptionConverter.findAndSubst(errorHandlerPrefix, props);
            if (errorHandlerClass != null) {
                if (class$org$apache$log4j$spi$ErrorHandler == null) {
                    cls2 = class$("org.apache.log4j.spi.ErrorHandler");
                    class$org$apache$log4j$spi$ErrorHandler = cls2;
                } else {
                    cls2 = class$org$apache$log4j$spi$ErrorHandler;
                }
                ErrorHandler eh = (ErrorHandler) OptionConverter.instantiateByKey(props, errorHandlerPrefix, cls2, null);
                if (eh != null) {
                    appender2.setErrorHandler(eh);
                    LogLog.debug(new StringBuffer().append("Parsing errorhandler options for \"").append(appenderName).append("\".").toString());
                    parseErrorHandler(eh, errorHandlerPrefix, props, this.repository);
                    Properties edited = new Properties();
                    String[] keys = {new StringBuffer().append(errorHandlerPrefix).append(".").append(ROOT_REF).toString(), new StringBuffer().append(errorHandlerPrefix).append(".").append(LOGGER_REF).toString(), new StringBuffer().append(errorHandlerPrefix).append(".").append(APPENDER_REF_TAG).toString()};
                    for (Map.Entry entry : props.entrySet()) {
                        int i = 0;
                        while (i < keys.length && !keys[i].equals(entry.getKey())) {
                            i++;
                        }
                        if (i == keys.length) {
                            edited.put(entry.getKey(), entry.getValue());
                        }
                    }
                    PropertySetter.setProperties(eh, edited, new StringBuffer().append(errorHandlerPrefix).append(".").toString());
                    LogLog.debug(new StringBuffer().append("End of errorhandler parsing for \"").append(appenderName).append("\".").toString());
                }
            }
            PropertySetter.setProperties(appender2, props, new StringBuffer().append(prefix).append(".").toString());
            LogLog.debug(new StringBuffer().append("Parsed \"").append(appenderName).append("\" options.").toString());
        }
        parseAppenderFilters(props, appenderName, appender2);
        registryPut(appender2);
        return appender2;
    }

    private void parseErrorHandler(ErrorHandler eh, String errorHandlerPrefix, Properties props, LoggerRepository hierarchy) {
        Appender backup;
        boolean rootRef = OptionConverter.toBoolean(OptionConverter.findAndSubst(new StringBuffer().append(errorHandlerPrefix).append(ROOT_REF).toString(), props), false);
        if (rootRef) {
            eh.setLogger(hierarchy.getRootLogger());
        }
        String loggerName = OptionConverter.findAndSubst(new StringBuffer().append(errorHandlerPrefix).append(LOGGER_REF).toString(), props);
        if (loggerName != null) {
            Logger logger = this.loggerFactory == null ? hierarchy.getLogger(loggerName) : hierarchy.getLogger(loggerName, this.loggerFactory);
            eh.setLogger(logger);
        }
        String appenderName = OptionConverter.findAndSubst(new StringBuffer().append(errorHandlerPrefix).append(APPENDER_REF_TAG).toString(), props);
        if (appenderName != null && (backup = parseAppender(props, appenderName)) != null) {
            eh.setBackupAppender(backup);
        }
    }

    void parseAppenderFilters(Properties props, String appenderName, Appender appender) {
        Class cls;
        String filterPrefix = new StringBuffer().append(APPENDER_PREFIX).append(appenderName).append(".filter.").toString();
        int fIdx = filterPrefix.length();
        Hashtable filters = new Hashtable();
        Enumeration e = props.keys();
        String name = "";
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            if (key.startsWith(filterPrefix)) {
                int dotIdx = key.indexOf(46, fIdx);
                String filterKey = key;
                if (dotIdx != -1) {
                    filterKey = key.substring(0, dotIdx);
                    name = key.substring(dotIdx + 1);
                }
                Vector filterOpts = (Vector) filters.get(filterKey);
                if (filterOpts == null) {
                    filterOpts = new Vector();
                    filters.put(filterKey, filterOpts);
                }
                if (dotIdx != -1) {
                    String value = OptionConverter.findAndSubst(key, props);
                    filterOpts.add(new NameValue(name, value));
                }
            }
        }
        Enumeration g = new SortedKeyEnumeration(filters);
        while (g.hasMoreElements()) {
            String key2 = (String) g.nextElement();
            String clazz = props.getProperty(key2);
            if (clazz != null) {
                LogLog.debug(new StringBuffer().append("Filter key: [").append(key2).append("] class: [").append(props.getProperty(key2)).append("] props: ").append(filters.get(key2)).toString());
                if (class$org$apache$log4j$spi$Filter == null) {
                    cls = class$("org.apache.log4j.spi.Filter");
                    class$org$apache$log4j$spi$Filter = cls;
                } else {
                    cls = class$org$apache$log4j$spi$Filter;
                }
                Filter filter = (Filter) OptionConverter.instantiateByClassName(clazz, cls, null);
                if (filter != null) {
                    PropertySetter propSetter = new PropertySetter(filter);
                    Vector v = (Vector) filters.get(key2);
                    Enumeration filterProps = v.elements();
                    while (filterProps.hasMoreElements()) {
                        NameValue kv = (NameValue) filterProps.nextElement();
                        propSetter.setProperty(kv.key, kv.value);
                    }
                    propSetter.activate();
                    LogLog.debug(new StringBuffer().append("Adding filter of type [").append(filter.getClass()).append("] to appender named [").append(appender.getName()).append("].").toString());
                    appender.addFilter(filter);
                }
            } else {
                LogLog.warn(new StringBuffer().append("Missing class definition for filter: [").append(key2).append("]").toString());
            }
        }
    }

    void registryPut(Appender appender) {
        this.registry.put(appender.getName(), appender);
    }

    Appender registryGet(String name) {
        return (Appender) this.registry.get(name);
    }
}
