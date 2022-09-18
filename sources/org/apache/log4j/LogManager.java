package org.apache.log4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.DefaultRepositorySelector;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.NOPLoggerRepository;
import org.apache.log4j.spi.RepositorySelector;
import org.apache.log4j.spi.RootLogger;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/LogManager.class */
public class LogManager {
    public static final String DEFAULT_CONFIGURATION_FILE = "log4j.properties";
    static final String DEFAULT_XML_CONFIGURATION_FILE = "log4j.xml";
    public static final String DEFAULT_CONFIGURATION_KEY = "log4j.configuration";
    public static final String CONFIGURATOR_CLASS_KEY = "log4j.configuratorClass";
    public static final String DEFAULT_INIT_OVERRIDE_KEY = "log4j.defaultInitOverride";
    private static Object guard = null;
    private static RepositorySelector repositorySelector;

    static {
        URL url;
        Hierarchy h = new Hierarchy(new RootLogger(Level.DEBUG));
        repositorySelector = new DefaultRepositorySelector(h);
        String override = OptionConverter.getSystemProperty(DEFAULT_INIT_OVERRIDE_KEY, null);
        if (override == null || "false".equalsIgnoreCase(override)) {
            String configurationOptionStr = OptionConverter.getSystemProperty(DEFAULT_CONFIGURATION_KEY, null);
            String configuratorClassName = OptionConverter.getSystemProperty(CONFIGURATOR_CLASS_KEY, null);
            if (configurationOptionStr == null) {
                url = Loader.getResource(DEFAULT_XML_CONFIGURATION_FILE);
                if (url == null) {
                    url = Loader.getResource(DEFAULT_CONFIGURATION_FILE);
                }
            } else {
                try {
                    url = new URL(configurationOptionStr);
                } catch (MalformedURLException e) {
                    url = Loader.getResource(configurationOptionStr);
                }
            }
            if (url != null) {
                LogLog.debug(new StringBuffer().append("Using URL [").append(url).append("] for automatic log4j configuration.").toString());
                try {
                    OptionConverter.selectAndConfigure(url, configuratorClassName, getLoggerRepository());
                    return;
                } catch (NoClassDefFoundError e2) {
                    LogLog.warn("Error during default initialization", e2);
                    return;
                }
            }
            LogLog.debug(new StringBuffer().append("Could not find resource: [").append(configurationOptionStr).append("].").toString());
            return;
        }
        LogLog.debug("Default initialization of overridden by log4j.defaultInitOverrideproperty.");
    }

    public static void setRepositorySelector(RepositorySelector selector, Object guard2) throws IllegalArgumentException {
        if (guard != null && guard != guard2) {
            throw new IllegalArgumentException("Attempted to reset the LoggerFactory without possessing the guard.");
        }
        if (selector == null) {
            throw new IllegalArgumentException("RepositorySelector must be non-null.");
        }
        guard = guard2;
        repositorySelector = selector;
    }

    private static boolean isLikelySafeScenario(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        String msg = stringWriter.toString();
        return msg.indexOf("org.apache.catalina.loader.WebappClassLoader.stop") != -1;
    }

    public static LoggerRepository getLoggerRepository() {
        if (repositorySelector == null) {
            repositorySelector = new DefaultRepositorySelector(new NOPLoggerRepository());
            guard = null;
            Exception ex = new IllegalStateException("Class invariant violation");
            if (isLikelySafeScenario(ex)) {
                LogLog.debug("log4j called after unloading, see http://logging.apache.org/log4j/1.2/faq.html#unload.", ex);
            } else {
                LogLog.error("log4j called after unloading, see http://logging.apache.org/log4j/1.2/faq.html#unload.", ex);
            }
        }
        return repositorySelector.getLoggerRepository();
    }

    public static Logger getRootLogger() {
        return getLoggerRepository().getRootLogger();
    }

    public static Logger getLogger(String name) {
        return getLoggerRepository().getLogger(name);
    }

    public static Logger getLogger(Class clazz) {
        return getLoggerRepository().getLogger(clazz.getName());
    }

    public static Logger getLogger(String name, LoggerFactory factory) {
        return getLoggerRepository().getLogger(name, factory);
    }

    public static Logger exists(String name) {
        return getLoggerRepository().exists(name);
    }

    public static Enumeration getCurrentLoggers() {
        return getLoggerRepository().getCurrentLoggers();
    }

    public static void shutdown() {
        getLoggerRepository().shutdown();
    }

    public static void resetConfiguration() {
        getLoggerRepository().resetConfiguration();
    }
}
