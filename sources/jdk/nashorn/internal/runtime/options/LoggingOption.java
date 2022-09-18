package jdk.nashorn.internal.runtime.options;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/options/LoggingOption.class */
public class LoggingOption extends KeyValueOption {
    private final Map<String, LoggerInfo> loggers = new HashMap();

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/options/LoggingOption$LoggerInfo.class */
    public static class LoggerInfo {
        private final Level level;
        private final boolean isQuiet;

        LoggerInfo(Level level, boolean isQuiet) {
            this.level = level;
            this.isQuiet = isQuiet;
        }

        public Level getLevel() {
            return this.level;
        }

        public boolean isQuiet() {
            return this.isQuiet;
        }
    }

    public LoggingOption(String value) {
        super(value);
        initialize(getValues());
    }

    public Map<String, LoggerInfo> getLoggers() {
        return Collections.unmodifiableMap(this.loggers);
    }

    private void initialize(Map<String, String> logMap) throws IllegalArgumentException {
        boolean isQuiet;
        Level level;
        try {
            for (Map.Entry<String, String> entry : logMap.entrySet()) {
                String name = lastPart(entry.getKey());
                String levelString = entry.getValue().toUpperCase(Locale.ENGLISH);
                if ("".equals(levelString)) {
                    level = Level.INFO;
                    isQuiet = false;
                } else if ("QUIET".equals(levelString)) {
                    level = Level.INFO;
                    isQuiet = true;
                } else {
                    level = Level.parse(levelString);
                    isQuiet = false;
                }
                this.loggers.put(name, new LoggerInfo(level, isQuiet));
            }
        } catch (IllegalArgumentException | SecurityException e) {
            throw e;
        }
    }

    private static String lastPart(String packageName) {
        String[] parts = packageName.split("\\.");
        if (parts.length == 0) {
            return packageName;
        }
        return parts[parts.length - 1];
    }
}
