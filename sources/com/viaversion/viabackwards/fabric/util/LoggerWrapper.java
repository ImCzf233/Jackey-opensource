package com.viaversion.viabackwards.fabric.util;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/fabric/util/LoggerWrapper.class */
public class LoggerWrapper extends Logger {
    private final org.apache.logging.log4j.Logger base;

    public LoggerWrapper(org.apache.logging.log4j.Logger logger) {
        super("logger", null);
        this.base = logger;
    }

    @Override // java.util.logging.Logger
    public void log(LogRecord record) {
        log(record.getLevel(), record.getMessage());
    }

    @Override // java.util.logging.Logger
    public void log(Level level, String msg) {
        if (level == Level.FINE) {
            this.base.debug(msg);
        } else if (level == Level.WARNING) {
            this.base.warn(msg);
        } else if (level == Level.SEVERE) {
            this.base.error(msg);
        } else if (level == Level.INFO) {
            this.base.info(msg);
        } else {
            this.base.trace(msg);
        }
    }

    @Override // java.util.logging.Logger
    public void log(Level level, String msg, Object param1) {
        if (level == Level.FINE) {
            this.base.debug(msg, param1);
        } else if (level == Level.WARNING) {
            this.base.warn(msg, param1);
        } else if (level == Level.SEVERE) {
            this.base.error(msg, param1);
        } else if (level == Level.INFO) {
            this.base.info(msg, param1);
        } else {
            this.base.trace(msg, param1);
        }
    }

    @Override // java.util.logging.Logger
    public void log(Level level, String msg, Object[] params) {
        log(level, MessageFormat.format(msg, params));
    }

    @Override // java.util.logging.Logger
    public void log(Level level, String msg, Throwable params) {
        if (level == Level.FINE) {
            this.base.debug(msg, params);
        } else if (level == Level.WARNING) {
            this.base.warn(msg, params);
        } else if (level == Level.SEVERE) {
            this.base.error(msg, params);
        } else if (level == Level.INFO) {
            this.base.info(msg, params);
        } else {
            this.base.trace(msg, params);
        }
    }
}
