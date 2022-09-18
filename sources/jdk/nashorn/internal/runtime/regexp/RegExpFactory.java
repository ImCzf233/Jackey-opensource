package jdk.nashorn.internal.runtime.regexp;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.options.Options;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/RegExpFactory.class */
public class RegExpFactory {
    private static final RegExpFactory instance;
    private static final String JDK = "jdk";
    private static final String JONI = "joni";
    private static final Map<String, RegExp> REGEXP_CACHE = Collections.synchronizedMap(new WeakHashMap());

    static {
        String impl = Options.getStringProperty("nashorn.regexp.impl", JONI);
        boolean z = true;
        switch (impl.hashCode()) {
            case 105073:
                if (impl.equals(JDK)) {
                    z = true;
                    break;
                }
                break;
            case 3268032:
                if (impl.equals(JONI)) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                instance = new JoniRegExp.Factory();
                return;
            case true:
                instance = new RegExpFactory();
                return;
            default:
                instance = null;
                throw new InternalError("Unsupported RegExp factory: " + impl);
        }
    }

    public RegExp compile(String pattern, String flags) throws ParserException {
        return new JdkRegExp(pattern, flags);
    }

    public static RegExp create(String pattern, String flags) {
        String key = pattern + "/" + flags;
        RegExp regexp = REGEXP_CACHE.get(key);
        if (regexp == null) {
            regexp = instance.compile(pattern, flags);
            REGEXP_CACHE.put(key, regexp);
        }
        return regexp;
    }

    public static void validate(String pattern, String flags) throws ParserException {
        create(pattern, flags);
    }

    public static boolean usesJavaUtilRegex() {
        return instance != null && instance.getClass() == RegExpFactory.class;
    }
}
