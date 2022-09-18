package jdk.nashorn.api.scripting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import jdk.Exported;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Version;

@Exported
/* loaded from: Jackey Client b2.jar:jdk/nashorn/api/scripting/NashornScriptEngineFactory.class */
public final class NashornScriptEngineFactory implements ScriptEngineFactory {
    private static final String[] DEFAULT_OPTIONS = {"-doe"};
    private static final List<String> names = immutableList("nashorn", "Nashorn", "js", "JS", "JavaScript", "javascript", "ECMAScript", "ecmascript");
    private static final List<String> mimeTypes = immutableList("application/javascript", "application/ecmascript", "text/javascript", "text/ecmascript");
    private static final List<String> extensions = immutableList("js");

    public String getEngineName() {
        return (String) getParameter("javax.script.engine");
    }

    public String getEngineVersion() {
        return (String) getParameter("javax.script.engine_version");
    }

    public List<String> getExtensions() {
        return Collections.unmodifiableList(extensions);
    }

    public String getLanguageName() {
        return (String) getParameter("javax.script.language");
    }

    public String getLanguageVersion() {
        return (String) getParameter("javax.script.language_version");
    }

    public String getMethodCallSyntax(String obj, String method, String... args) {
        StringBuilder sb = new StringBuilder().append(obj).append('.').append(method).append('(');
        int len = args.length;
        if (len > 0) {
            sb.append(args[0]);
        }
        for (int i = 1; i < len; i++) {
            sb.append(',').append(args[i]);
        }
        sb.append(')');
        return sb.toString();
    }

    public List<String> getMimeTypes() {
        return Collections.unmodifiableList(mimeTypes);
    }

    public List<String> getNames() {
        return Collections.unmodifiableList(names);
    }

    public String getOutputStatement(String toDisplay) {
        return "print(" + toDisplay + ")";
    }

    public Object getParameter(String key) {
        boolean z = true;
        switch (key.hashCode()) {
            case -1073020410:
                if (key.equals("javax.script.engine_version")) {
                    z = true;
                    break;
                }
                break;
            case -1047659667:
                if (key.equals("javax.script.engine")) {
                    z = true;
                    break;
                }
                break;
            case -917703229:
                if (key.equals("javax.script.language")) {
                    z = true;
                    break;
                }
                break;
            case -852670884:
                if (key.equals("javax.script.language_version")) {
                    z = true;
                    break;
                }
                break;
            case -125973898:
                if (key.equals("javax.script.name")) {
                    z = false;
                    break;
                }
                break;
            case 1224369528:
                if (key.equals("THREADING")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return "javascript";
            case true:
                return "Oracle Nashorn";
            case true:
                return Version.version();
            case true:
                return "ECMAScript";
            case true:
                return "ECMA - 262 Edition 5.1";
            case true:
                return null;
            default:
                return null;
        }
    }

    public String getProgram(String... statements) {
        StringBuilder sb = new StringBuilder();
        for (String statement : statements) {
            sb.append(statement).append(';');
        }
        return sb.toString();
    }

    public ScriptEngine getScriptEngine() {
        try {
            return new NashornScriptEngine(this, DEFAULT_OPTIONS, getAppClassLoader(), null);
        } catch (RuntimeException e) {
            if (Context.DEBUG) {
                e.printStackTrace();
            }
            throw e;
        }
    }

    public ScriptEngine getScriptEngine(ClassLoader appLoader) {
        return newEngine(DEFAULT_OPTIONS, appLoader, null);
    }

    public ScriptEngine getScriptEngine(ClassFilter classFilter) {
        return newEngine(DEFAULT_OPTIONS, getAppClassLoader(), (ClassFilter) Objects.requireNonNull(classFilter));
    }

    public ScriptEngine getScriptEngine(String... args) {
        return newEngine((String[]) Objects.requireNonNull(args), getAppClassLoader(), null);
    }

    public ScriptEngine getScriptEngine(String[] args, ClassLoader appLoader) {
        return newEngine((String[]) Objects.requireNonNull(args), appLoader, null);
    }

    public ScriptEngine getScriptEngine(String[] args, ClassLoader appLoader, ClassFilter classFilter) {
        return newEngine((String[]) Objects.requireNonNull(args), appLoader, (ClassFilter) Objects.requireNonNull(classFilter));
    }

    private ScriptEngine newEngine(String[] args, ClassLoader appLoader, ClassFilter classFilter) {
        checkConfigPermission();
        try {
            return new NashornScriptEngine(this, args, appLoader, classFilter);
        } catch (RuntimeException e) {
            if (Context.DEBUG) {
                e.printStackTrace();
            }
            throw e;
        }
    }

    private static void checkConfigPermission() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission(Context.NASHORN_SET_CONFIG));
        }
    }

    private static List<String> immutableList(String... elements) {
        return Collections.unmodifiableList(Arrays.asList(elements));
    }

    private static ClassLoader getAppClassLoader() {
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        return ccl == null ? NashornScriptEngineFactory.class.getClassLoader() : ccl;
    }
}
