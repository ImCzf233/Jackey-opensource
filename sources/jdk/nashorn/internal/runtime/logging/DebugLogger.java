package jdk.nashorn.internal.runtime.logging;

import java.io.PrintWriter;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.LoggingPermission;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.events.RuntimeEvent;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/logging/DebugLogger.class */
public final class DebugLogger {
    public static final DebugLogger DISABLED_LOGGER;
    private final java.util.logging.Logger logger;
    private final boolean isEnabled;
    private int indent;
    private static final int INDENT_SPACE = 4;
    private final boolean isQuiet;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !DebugLogger.class.desiredAssertionStatus();
        DISABLED_LOGGER = new DebugLogger("disabled", Level.OFF, false);
    }

    public DebugLogger(String loggerName, Level loggerLevel, boolean isQuiet) {
        this.logger = instantiateLogger(loggerName, loggerLevel);
        this.isQuiet = isQuiet;
        if ($assertionsDisabled || this.logger != null) {
            this.isEnabled = getLevel() != Level.OFF;
            return;
        }
        throw new AssertionError();
    }

    private static java.util.logging.Logger instantiateLogger(String name, final Level level) {
        final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(name);
        AccessController.doPrivileged(new PrivilegedAction<Void>() { // from class: jdk.nashorn.internal.runtime.logging.DebugLogger.1
            @Override // java.security.PrivilegedAction
            public Void run() {
                Handler[] handlers;
                for (Handler h : logger.getHandlers()) {
                    logger.removeHandler(h);
                }
                logger.setLevel(level);
                logger.setUseParentHandlers(false);
                Handler c = new ConsoleHandler();
                c.setFormatter(new Formatter() { // from class: jdk.nashorn.internal.runtime.logging.DebugLogger.1.1
                    @Override // java.util.logging.Formatter
                    public String format(LogRecord record) {
                        StringBuilder sb = new StringBuilder();
                        sb.append('[').append(record.getLoggerName()).append("] ").append(record.getMessage()).append('\n');
                        return sb.toString();
                    }
                });
                logger.addHandler(c);
                c.setLevel(level);
                return null;
            }
        }, createLoggerControlAccCtxt());
        return logger;
    }

    public Level getLevel() {
        return this.logger.getLevel() == null ? Level.OFF : this.logger.getLevel();
    }

    public PrintWriter getOutputStream() {
        return Context.getCurrentErr();
    }

    public static String quote(String str) {
        if (str.isEmpty()) {
            return "''";
        }
        char startQuote = 0;
        char endQuote = 0;
        char quote = 0;
        if (str.startsWith("\\") || str.startsWith("\"")) {
            startQuote = str.charAt(0);
        }
        if (str.endsWith("\\") || str.endsWith("\"")) {
            endQuote = str.charAt(str.length() - 1);
        }
        if (startQuote == 0 || endQuote == 0) {
            quote = startQuote == 0 ? endQuote : startQuote;
        }
        if (quote == 0) {
            quote = '\'';
        }
        return (startQuote == 0 ? quote : startQuote) + str + (endQuote == 0 ? quote : endQuote);
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public static boolean isEnabled(DebugLogger logger) {
        return logger != null && logger.isEnabled();
    }

    public void indent(int pos) {
        if (this.isEnabled) {
            this.indent += pos * 4;
        }
    }

    public void indent() {
        this.indent += 4;
    }

    public void unindent() {
        this.indent -= 4;
        if (this.indent < 0) {
            this.indent = 0;
        }
    }

    private static void logEvent(RuntimeEvent<?> event) {
        if (event != null) {
            Global global = Context.getGlobal();
            if (global.has("Debug")) {
                ScriptObject debug = (ScriptObject) global.get("Debug");
                ScriptFunction addRuntimeEvent = (ScriptFunction) debug.get("addRuntimeEvent");
                ScriptRuntime.apply(addRuntimeEvent, debug, event);
            }
        }
    }

    public boolean isLoggable(Level level) {
        return this.logger.isLoggable(level);
    }

    public void finest(String str) {
        log(Level.FINEST, str);
    }

    public void finest(RuntimeEvent<?> event, String str) {
        finest(str);
        logEvent(event);
    }

    public void finest(Object... objs) {
        log(Level.FINEST, objs);
    }

    public void finest(RuntimeEvent<?> event, Object... objs) {
        finest(objs);
        logEvent(event);
    }

    public void finer(String str) {
        log(Level.FINER, str);
    }

    public void finer(RuntimeEvent<?> event, String str) {
        finer(str);
        logEvent(event);
    }

    public void finer(Object... objs) {
        log(Level.FINER, objs);
    }

    public void finer(RuntimeEvent<?> event, Object... objs) {
        finer(objs);
        logEvent(event);
    }

    public void fine(String str) {
        log(Level.FINE, str);
    }

    public void fine(RuntimeEvent<?> event, String str) {
        fine(str);
        logEvent(event);
    }

    public void fine(Object... objs) {
        log(Level.FINE, objs);
    }

    public void fine(RuntimeEvent<?> event, Object... objs) {
        fine(objs);
        logEvent(event);
    }

    public void config(String str) {
        log(Level.CONFIG, str);
    }

    public void config(RuntimeEvent<?> event, String str) {
        config(str);
        logEvent(event);
    }

    public void config(Object... objs) {
        log(Level.CONFIG, objs);
    }

    public void config(RuntimeEvent<?> event, Object... objs) {
        config(objs);
        logEvent(event);
    }

    public void info(String str) {
        log(Level.INFO, str);
    }

    public void info(RuntimeEvent<?> event, String str) {
        info(str);
        logEvent(event);
    }

    public void info(Object... objs) {
        log(Level.INFO, objs);
    }

    public void info(RuntimeEvent<?> event, Object... objs) {
        info(objs);
        logEvent(event);
    }

    public void warning(String str) {
        log(Level.WARNING, str);
    }

    public void warning(RuntimeEvent<?> event, String str) {
        warning(str);
        logEvent(event);
    }

    public void warning(Object... objs) {
        log(Level.WARNING, objs);
    }

    public void warning(RuntimeEvent<?> event, Object... objs) {
        warning(objs);
        logEvent(event);
    }

    public void severe(String str) {
        log(Level.SEVERE, str);
    }

    public void severe(RuntimeEvent<?> event, String str) {
        severe(str);
        logEvent(event);
    }

    public void severe(Object... objs) {
        log(Level.SEVERE, objs);
    }

    public void severe(RuntimeEvent<?> event, Object... objs) {
        severe(objs);
        logEvent(event);
    }

    public void log(Level level, String str) {
        if (this.isEnabled && !this.isQuiet && this.logger.isLoggable(level)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.indent; i++) {
                sb.append(' ');
            }
            sb.append(str);
            this.logger.log(level, sb.toString());
        }
    }

    public void log(Level level, Object... objs) {
        if (this.isEnabled && !this.isQuiet && this.logger.isLoggable(level)) {
            StringBuilder sb = new StringBuilder();
            for (Object obj : objs) {
                sb.append(obj);
            }
            log(level, sb.toString());
        }
    }

    private static AccessControlContext createLoggerControlAccCtxt() {
        Permissions perms = new Permissions();
        perms.add(new LoggingPermission("control", null));
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, perms)});
    }
}
