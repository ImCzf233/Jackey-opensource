package org.apache.log4j.varia;

import java.io.InputStream;
import java.net.URL;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/varia/ReloadingPropertyConfigurator.class */
public class ReloadingPropertyConfigurator implements Configurator {
    PropertyConfigurator delegate = new PropertyConfigurator();

    @Override // org.apache.log4j.spi.Configurator
    public void doConfigure(InputStream inputStream, LoggerRepository repository) {
    }

    @Override // org.apache.log4j.spi.Configurator
    public void doConfigure(URL url, LoggerRepository repository) {
    }
}
