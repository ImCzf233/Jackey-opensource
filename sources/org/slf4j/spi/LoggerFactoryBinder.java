package org.slf4j.spi;

import org.slf4j.ILoggerFactory;

/* loaded from: Jackey Client b2.jar:org/slf4j/spi/LoggerFactoryBinder.class */
public interface LoggerFactoryBinder {
    ILoggerFactory getLoggerFactory();

    String getLoggerFactoryClassStr();
}
