package jdk.nashorn.internal.runtime;

import javax.script.ScriptException;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.codegen.CompilerConstants;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ECMAException.class */
public final class ECMAException extends NashornException {
    public static final CompilerConstants.Call CREATE = CompilerConstants.staticCallNoLookup(ECMAException.class, "create", ECMAException.class, Object.class, String.class, Integer.TYPE, Integer.TYPE);
    public static final CompilerConstants.FieldAccess THROWN = CompilerConstants.virtualField(ECMAException.class, "thrown", Object.class);
    private static final String EXCEPTION_PROPERTY = "nashornException";
    public final Object thrown;

    private ECMAException(Object thrown, String fileName, int line, int column) {
        super(ScriptRuntime.safeToString(thrown), asThrowable(thrown), fileName, line, column);
        this.thrown = thrown;
        setExceptionToThrown();
    }

    public ECMAException(Object thrown, Throwable cause) {
        super(ScriptRuntime.safeToString(thrown), cause);
        this.thrown = thrown;
        setExceptionToThrown();
    }

    public static ECMAException create(Object thrown, String fileName, int line, int column) {
        if (thrown instanceof ScriptObject) {
            Object exception = getException((ScriptObject) thrown);
            if (exception instanceof ECMAException) {
                ECMAException ee = (ECMAException) exception;
                if (ee.getThrown() == thrown) {
                    ee.setFileName(fileName);
                    ee.setLineNumber(line);
                    ee.setColumnNumber(column);
                    return ee;
                }
            }
        }
        return new ECMAException(thrown, fileName, line, column);
    }

    @Override // jdk.nashorn.api.scripting.NashornException
    public Object getThrown() {
        return this.thrown;
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String fileName = getFileName();
        int line = getLineNumber();
        int column = getColumnNumber();
        if (fileName != null) {
            sb.append(fileName);
            if (line >= 0) {
                sb.append(':');
                sb.append(line);
            }
            if (column >= 0) {
                sb.append(':');
                sb.append(column);
            }
            sb.append(' ');
        } else {
            sb.append("ECMAScript Exception: ");
        }
        sb.append(getMessage());
        return sb.toString();
    }

    public static Object getException(ScriptObject errObj) {
        if (errObj.hasOwnProperty(EXCEPTION_PROPERTY)) {
            return errObj.get(EXCEPTION_PROPERTY);
        }
        return null;
    }

    public static Object printStackTrace(ScriptObject errObj) {
        Object exception = getException(errObj);
        if (exception instanceof Throwable) {
            ((Throwable) exception).printStackTrace(Context.getCurrentErr());
        } else {
            Context.err("<stack trace not available>");
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object getLineNumber(ScriptObject errObj) {
        Object e = getException(errObj);
        if (e instanceof NashornException) {
            return Integer.valueOf(((NashornException) e).getLineNumber());
        }
        if (e instanceof ScriptException) {
            return Integer.valueOf(((ScriptException) e).getLineNumber());
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object getColumnNumber(ScriptObject errObj) {
        Object e = getException(errObj);
        if (e instanceof NashornException) {
            return Integer.valueOf(((NashornException) e).getColumnNumber());
        }
        if (e instanceof ScriptException) {
            return Integer.valueOf(((ScriptException) e).getColumnNumber());
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object getFileName(ScriptObject errObj) {
        Object e = getException(errObj);
        if (e instanceof NashornException) {
            return ((NashornException) e).getFileName();
        }
        if (e instanceof ScriptException) {
            return ((ScriptException) e).getFileName();
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static String safeToString(ScriptObject errObj) {
        Object name;
        Object msg;
        Object name2 = ScriptRuntime.UNDEFINED;
        try {
            name2 = errObj.get("name");
        } catch (Exception e) {
        }
        if (name2 == ScriptRuntime.UNDEFINED) {
            name = "Error";
        } else {
            name = ScriptRuntime.safeToString(name2);
        }
        Object msg2 = ScriptRuntime.UNDEFINED;
        try {
            msg2 = errObj.get("message");
        } catch (Exception e2) {
        }
        if (msg2 == ScriptRuntime.UNDEFINED) {
            msg = "";
        } else {
            msg = ScriptRuntime.safeToString(msg2);
        }
        if (((String) name).isEmpty()) {
            return (String) msg;
        }
        if (((String) msg).isEmpty()) {
            return (String) name;
        }
        return name + ": " + msg;
    }

    private static Throwable asThrowable(Object obj) {
        if (obj instanceof Throwable) {
            return (Throwable) obj;
        }
        return null;
    }

    private void setExceptionToThrown() {
        if (this.thrown instanceof ScriptObject) {
            ScriptObject sobj = (ScriptObject) this.thrown;
            if (!sobj.has(EXCEPTION_PROPERTY)) {
                sobj.addOwnProperty(EXCEPTION_PROPERTY, 2, this);
            } else {
                sobj.set(EXCEPTION_PROPERTY, this, 0);
            }
        }
    }
}
