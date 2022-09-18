package org.apache.log4j;

import org.apache.log4j.spi.LoggerFactory;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/DefaultCategoryFactory.class */
class DefaultCategoryFactory implements LoggerFactory {
    @Override // org.apache.log4j.spi.LoggerFactory
    public Logger makeNewLoggerInstance(String name) {
        return new Logger(name);
    }
}
