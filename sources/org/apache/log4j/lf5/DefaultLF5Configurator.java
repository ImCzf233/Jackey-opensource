package org.apache.log4j.lf5;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/DefaultLF5Configurator.class */
public class DefaultLF5Configurator implements Configurator {
    static Class class$org$apache$log4j$lf5$DefaultLF5Configurator;

    private DefaultLF5Configurator() {
    }

    public static void configure() throws IOException {
        Class cls;
        if (class$org$apache$log4j$lf5$DefaultLF5Configurator == null) {
            cls = class$("org.apache.log4j.lf5.DefaultLF5Configurator");
            class$org$apache$log4j$lf5$DefaultLF5Configurator = cls;
        } else {
            cls = class$org$apache$log4j$lf5$DefaultLF5Configurator;
        }
        URL configFileResource = cls.getResource("/org/apache/log4j/lf5/config/defaultconfig.properties");
        if (configFileResource != null) {
            PropertyConfigurator.configure(configFileResource);
            return;
        }
        throw new IOException(new StringBuffer().append("Error: Unable to open the resource").append("/org/apache/log4j/lf5/config/defaultconfig.properties").toString());
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    @Override // org.apache.log4j.spi.Configurator
    public void doConfigure(InputStream inputStream, LoggerRepository repository) {
        throw new IllegalStateException("This class should NOT be instantiated!");
    }

    @Override // org.apache.log4j.spi.Configurator
    public void doConfigure(URL configURL, LoggerRepository repository) {
        throw new IllegalStateException("This class should NOT be instantiated!");
    }
}
