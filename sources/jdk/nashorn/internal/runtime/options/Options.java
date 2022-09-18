package jdk.nashorn.internal.runtime.options;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.PropertyPermission;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import jdk.nashorn.internal.runtime.QuotedStringTokenizer;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/options/Options.class */
public final class Options {
    private static final AccessControlContext READ_PROPERTY_ACC_CTXT;
    private final String resource;
    private final PrintWriter err;
    private final List<String> files;
    private final List<String> arguments;
    private final TreeMap<String, Option<?>> options;
    private static final String NASHORN_ARGS_PREPEND_PROPERTY = "nashorn.args.prepend";
    private static final String NASHORN_ARGS_PROPERTY = "nashorn.args";
    private static final String MESSAGES_RESOURCE = "jdk.nashorn.internal.runtime.resources.Options";
    private static ResourceBundle bundle;
    private static HashMap<Object, Object> usage;
    private static Collection<OptionTemplate> validOptions;
    private static OptionTemplate helpOptionTemplate;
    private static OptionTemplate definePropTemplate;
    private static String definePropPrefix;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Options.class.desiredAssertionStatus();
        READ_PROPERTY_ACC_CTXT = createPropertyReadAccCtxt();
        bundle = ResourceBundle.getBundle(MESSAGES_RESOURCE, Locale.getDefault());
        validOptions = new TreeSet();
        usage = new HashMap<>();
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            StringTokenizer st = new StringTokenizer(key, ".");
            String resource = null;
            String type = null;
            if (st.countTokens() > 0) {
                resource = st.nextToken();
            }
            if (st.countTokens() > 0) {
                type = st.nextToken();
            }
            if ("option".equals(type)) {
                String helpKey = null;
                String xhelpKey = null;
                String definePropKey = null;
                try {
                    helpKey = bundle.getString(resource + ".options.help.key");
                    xhelpKey = bundle.getString(resource + ".options.xhelp.key");
                    definePropKey = bundle.getString(resource + ".options.D.key");
                } catch (MissingResourceException e) {
                }
                boolean isHelp = key.equals(helpKey);
                boolean isXHelp = key.equals(xhelpKey);
                OptionTemplate t = new OptionTemplate(resource, key, bundle.getString(key), isHelp, isXHelp);
                validOptions.add(t);
                if (isHelp) {
                    helpOptionTemplate = t;
                }
                if (key.equals(definePropKey)) {
                    definePropPrefix = t.getName();
                    definePropTemplate = t;
                }
            } else if (resource != null && "options".equals(type)) {
                usage.put(resource, bundle.getObject(key));
            }
        }
    }

    private static AccessControlContext createPropertyReadAccCtxt() {
        Permissions perms = new Permissions();
        perms.add(new PropertyPermission("nashorn.*", "read"));
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, perms)});
    }

    public Options(String resource) {
        this(resource, new PrintWriter((OutputStream) System.err, true));
    }

    public Options(String resource, PrintWriter err) {
        this.resource = resource;
        this.err = err;
        this.files = new ArrayList();
        this.arguments = new ArrayList();
        this.options = new TreeMap<>();
        for (OptionTemplate t : validOptions) {
            if (t.getDefaultValue() != null) {
                String v = getStringProperty(t.getKey(), null);
                if (v != null) {
                    set(t.getKey(), createOption(t, v));
                } else if (t.getDefaultValue() != null) {
                    set(t.getKey(), createOption(t, t.getDefaultValue()));
                }
            }
        }
    }

    public String getResource() {
        return this.resource;
    }

    public String toString() {
        return this.options.toString();
    }

    private static void checkPropertyName(String name) {
        if (!((String) Objects.requireNonNull(name)).startsWith("nashorn.")) {
            throw new IllegalArgumentException(name);
        }
    }

    public static boolean getBooleanProperty(final String name, final Boolean defValue) {
        checkPropertyName(name);
        return ((Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() { // from class: jdk.nashorn.internal.runtime.options.Options.1
            @Override // java.security.PrivilegedAction
            public Boolean run() {
                try {
                    String property = System.getProperty(name);
                    if (property == null && defValue != null) {
                        return defValue;
                    }
                    return Boolean.valueOf(property != null && !"false".equalsIgnoreCase(property));
                } catch (SecurityException e) {
                    return false;
                }
            }
        }, READ_PROPERTY_ACC_CTXT)).booleanValue();
    }

    public static boolean getBooleanProperty(String name) {
        return getBooleanProperty(name, null);
    }

    public static String getStringProperty(final String name, final String defValue) {
        checkPropertyName(name);
        return (String) AccessController.doPrivileged(new PrivilegedAction<String>() { // from class: jdk.nashorn.internal.runtime.options.Options.2
            @Override // java.security.PrivilegedAction
            public String run() {
                try {
                    return System.getProperty(name, defValue);
                } catch (SecurityException e) {
                    return defValue;
                }
            }
        }, READ_PROPERTY_ACC_CTXT);
    }

    public static int getIntProperty(final String name, final int defValue) {
        checkPropertyName(name);
        return ((Integer) AccessController.doPrivileged(new PrivilegedAction<Integer>() { // from class: jdk.nashorn.internal.runtime.options.Options.3
            @Override // java.security.PrivilegedAction
            public Integer run() {
                try {
                    return Integer.getInteger(name, defValue);
                } catch (SecurityException e) {
                    return Integer.valueOf(defValue);
                }
            }
        }, READ_PROPERTY_ACC_CTXT)).intValue();
    }

    public Option<?> get(String key) {
        return this.options.get(key(key));
    }

    public boolean getBoolean(String key) {
        Option<?> option = get(key);
        if (option != null) {
            return ((Boolean) option.getValue()).booleanValue();
        }
        return false;
    }

    public int getInteger(String key) {
        Option<?> option = get(key);
        if (option != null) {
            return ((Integer) option.getValue()).intValue();
        }
        return 0;
    }

    public String getString(String key) {
        String value;
        Option<?> option = get(key);
        if (option != null && (value = (String) option.getValue()) != null) {
            return value.intern();
        }
        return null;
    }

    public void set(String key, Option<?> option) {
        this.options.put(key(key), option);
    }

    public void set(String key, boolean option) {
        set(key, new Option<>(Boolean.valueOf(option)));
    }

    public void set(String key, String option) {
        set(key, new Option<>(option));
    }

    public List<String> getArguments() {
        return Collections.unmodifiableList(this.arguments);
    }

    public List<String> getFiles() {
        return Collections.unmodifiableList(this.files);
    }

    public static Collection<OptionTemplate> getValidOptions() {
        return Collections.unmodifiableCollection(validOptions);
    }

    private String key(String shortKey) {
        String key;
        String str = shortKey;
        while (true) {
            key = str;
            if (!key.startsWith("-")) {
                break;
            }
            str = key.substring(1, key.length());
        }
        String key2 = key.replace("-", ".");
        String keyPrefix = this.resource + ".option.";
        if (key2.startsWith(keyPrefix)) {
            return key2;
        }
        return keyPrefix + key2;
    }

    public static String getMsg(String msgId, String... args) {
        try {
            String msg = bundle.getString(msgId);
            if (args.length == 0) {
                return msg;
            }
            return new MessageFormat(msg).format(args);
        } catch (MissingResourceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void displayHelp(IllegalArgumentException e) {
        if (e instanceof IllegalOptionException) {
            OptionTemplate template = ((IllegalOptionException) e).getTemplate();
            if (template.isXHelp()) {
                displayHelp(true);
            } else {
                this.err.println(((IllegalOptionException) e).getTemplate());
            }
        } else if (e != null && e.getMessage() != null) {
            this.err.println(getMsg("option.error.invalid.option", e.getMessage(), helpOptionTemplate.getShortName(), helpOptionTemplate.getName()));
            this.err.println();
        } else {
            displayHelp(false);
        }
    }

    public void displayHelp(boolean extended) {
        for (OptionTemplate t : validOptions) {
            if (extended || !t.isUndocumented()) {
                if (t.getResource().equals(this.resource)) {
                    this.err.println(t);
                    this.err.println();
                }
            }
        }
    }

    public void process(String[] args) {
        LinkedList<String> argList = new LinkedList<>();
        addSystemProperties(NASHORN_ARGS_PREPEND_PROPERTY, argList);
        processArgList(argList);
        if ($assertionsDisabled || argList.isEmpty()) {
            Collections.addAll(argList, args);
            processArgList(argList);
            if (!$assertionsDisabled && !argList.isEmpty()) {
                throw new AssertionError();
            }
            addSystemProperties(NASHORN_ARGS_PROPERTY, argList);
            processArgList(argList);
            if (!$assertionsDisabled && !argList.isEmpty()) {
                throw new AssertionError();
            }
            return;
        }
        throw new AssertionError();
    }

    private void processArgList(LinkedList<String> argList) {
        while (!argList.isEmpty()) {
            String arg = argList.remove(0);
            if (!arg.isEmpty()) {
                if ("--".equals(arg)) {
                    this.arguments.addAll(argList);
                    argList.clear();
                } else if (!arg.startsWith("-") || arg.length() == 1) {
                    this.files.add(arg);
                } else if (arg.startsWith(definePropPrefix)) {
                    String value = arg.substring(definePropPrefix.length());
                    int eq = value.indexOf(61);
                    if (eq != -1) {
                        System.setProperty(value.substring(0, eq), value.substring(eq + 1));
                    } else if (!value.isEmpty()) {
                        System.setProperty(value, "");
                    } else {
                        throw new IllegalOptionException(definePropTemplate);
                    }
                } else {
                    ParsedArg parg = new ParsedArg(arg);
                    if (parg.template.isValueNextArg()) {
                        if (argList.isEmpty()) {
                            throw new IllegalOptionException(parg.template);
                        }
                        parg.value = argList.remove(0);
                    }
                    if (parg.template.isHelp()) {
                        if (!argList.isEmpty()) {
                            try {
                                OptionTemplate t = new ParsedArg(argList.get(0)).template;
                                throw new IllegalOptionException(t);
                            } catch (IllegalArgumentException e) {
                                throw e;
                            }
                        }
                        throw new IllegalArgumentException();
                    } else if (parg.template.isXHelp()) {
                        throw new IllegalOptionException(parg.template);
                    } else {
                        set(parg.template.getKey(), createOption(parg.template, parg.value));
                        if (parg.template.getDependency() != null) {
                            argList.addFirst(parg.template.getDependency());
                        }
                    }
                }
            }
        }
    }

    private static void addSystemProperties(String sysPropName, List<String> argList) {
        String sysArgs = getStringProperty(sysPropName, null);
        if (sysArgs != null) {
            StringTokenizer st = new StringTokenizer(sysArgs);
            while (st.hasMoreTokens()) {
                argList.add(st.nextToken());
            }
        }
    }

    public OptionTemplate getOptionTemplateByKey(String shortKey) {
        String fullKey = key(shortKey);
        for (OptionTemplate t : validOptions) {
            if (t.getKey().equals(fullKey)) {
                return t;
            }
        }
        throw new IllegalArgumentException(shortKey);
    }

    public static OptionTemplate getOptionTemplateByName(String name) {
        for (OptionTemplate t : validOptions) {
            if (t.nameMatches(name)) {
                return t;
            }
        }
        return null;
    }

    private static Option<?> createOption(OptionTemplate t, String value) {
        String type = t.getType();
        boolean z = true;
        switch (type.hashCode()) {
            case -2076227591:
                if (type.equals("timezone")) {
                    z = true;
                    break;
                }
                break;
            case -1097462182:
                if (type.equals("locale")) {
                    z = true;
                    break;
                }
                break;
            case -1003964351:
                if (type.equals("keyvalues")) {
                    z = true;
                    break;
                }
                break;
            case -926053069:
                if (type.equals("properties")) {
                    z = true;
                    break;
                }
                break;
            case -891985903:
                if (type.equals("string")) {
                    z = false;
                    break;
                }
                break;
            case 107332:
                if (type.equals("log")) {
                    z = true;
                    break;
                }
                break;
            case 64711720:
                if (type.equals("boolean")) {
                    z = true;
                    break;
                }
                break;
            case 1958052158:
                if (type.equals("integer")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return new Option<>(value);
            case true:
                return new Option<>(TimeZone.getTimeZone(value));
            case true:
                return new Option<>(Locale.forLanguageTag(value));
            case true:
                return new KeyValueOption(value);
            case true:
                return new LoggingOption(value);
            case true:
                return new Option<>(Boolean.valueOf(value != null && Boolean.parseBoolean(value)));
            case true:
                try {
                    return new Option<>(Integer.valueOf(value == null ? 0 : Integer.parseInt(value)));
                } catch (NumberFormatException e) {
                    throw new IllegalOptionException(t);
                }
            case true:
                initProps(new KeyValueOption(value));
                return null;
            default:
                throw new IllegalArgumentException(value);
        }
    }

    private static void initProps(KeyValueOption kv) {
        for (Map.Entry<String, String> entry : kv.getValues().entrySet()) {
            System.setProperty(entry.getKey(), entry.getValue());
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/options/Options$IllegalOptionException.class */
    public static class IllegalOptionException extends IllegalArgumentException {
        private final OptionTemplate template;

        IllegalOptionException(OptionTemplate t) {
            this.template = t;
        }

        OptionTemplate getTemplate() {
            return this.template;
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/options/Options$ParsedArg.class */
    public static class ParsedArg {
        OptionTemplate template;
        String value;

        ParsedArg(String argument) {
            QuotedStringTokenizer st = new QuotedStringTokenizer(argument, "=");
            if (!st.hasMoreTokens()) {
                throw new IllegalArgumentException();
            }
            String token = st.nextToken();
            this.template = Options.getOptionTemplateByName(token);
            if (this.template == null) {
                throw new IllegalArgumentException(argument);
            }
            this.value = "";
            if (st.hasMoreTokens()) {
                while (st.hasMoreTokens()) {
                    this.value += st.nextToken();
                    if (st.hasMoreTokens()) {
                        this.value += ':';
                    }
                }
            } else if ("boolean".equals(this.template.getType())) {
                this.value = "true";
            }
        }
    }
}
