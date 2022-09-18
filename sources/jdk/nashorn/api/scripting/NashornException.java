package jdk.nashorn.api.scripting;

import java.util.ArrayList;
import java.util.List;
import jdk.Exported;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptObject;

@Exported
/* loaded from: Jackey Client b2.jar:jdk/nashorn/api/scripting/NashornException.class */
public abstract class NashornException extends RuntimeException {
    private static final long serialVersionUID = 1;
    private String fileName;
    private int line;
    private boolean lineAndFileNameUnknown;
    private int column;
    private Object ecmaError;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NashornException.class.desiredAssertionStatus();
    }

    public NashornException(String msg, String fileName, int line, int column) {
        this(msg, null, fileName, line, column);
    }

    public NashornException(String msg, Throwable cause, String fileName, int line, int column) {
        super(msg, cause == null ? null : cause);
        this.fileName = fileName;
        this.line = line;
        this.column = column;
    }

    public NashornException(String msg, Throwable cause) {
        super(msg, cause == null ? null : cause);
        this.column = -1;
        this.lineAndFileNameUnknown = true;
    }

    public final String getFileName() {
        ensureLineAndFileName();
        return this.fileName;
    }

    public final void setFileName(String fileName) {
        this.fileName = fileName;
        this.lineAndFileNameUnknown = false;
    }

    public final int getLineNumber() {
        ensureLineAndFileName();
        return this.line;
    }

    public final void setLineNumber(int line) {
        this.lineAndFileNameUnknown = false;
        this.line = line;
    }

    public final int getColumnNumber() {
        return this.column;
    }

    public final void setColumnNumber(int column) {
        this.column = column;
    }

    public static StackTraceElement[] getScriptFrames(Throwable exception) {
        String methodName;
        StackTraceElement[] frames = exception.getStackTrace();
        List<StackTraceElement> filtered = new ArrayList<>();
        for (StackTraceElement st : frames) {
            if (ECMAErrors.isScriptFrame(st)) {
                String className = "<" + st.getFileName() + ">";
                String methodName2 = st.getMethodName();
                if (methodName2.equals(CompilerConstants.PROGRAM.symbolName())) {
                    methodName = "<program>";
                } else {
                    methodName = stripMethodName(methodName2);
                }
                filtered.add(new StackTraceElement(className, methodName, st.getFileName(), st.getLineNumber()));
            }
        }
        return (StackTraceElement[]) filtered.toArray(new StackTraceElement[filtered.size()]);
    }

    private static String stripMethodName(String methodName) {
        String name = methodName;
        int nestedSeparator = name.lastIndexOf(CompilerConstants.NESTED_FUNCTION_SEPARATOR.symbolName());
        if (nestedSeparator >= 0) {
            name = name.substring(nestedSeparator + 1);
        }
        int idSeparator = name.indexOf(CompilerConstants.ID_FUNCTION_SEPARATOR.symbolName());
        if (idSeparator >= 0) {
            name = name.substring(0, idSeparator);
        }
        return name.contains(CompilerConstants.ANON_FUNCTION_PREFIX.symbolName()) ? "<anonymous>" : name;
    }

    public static String getScriptStackString(Throwable exception) {
        StringBuilder buf = new StringBuilder();
        StackTraceElement[] frames = getScriptFrames(exception);
        for (StackTraceElement st : frames) {
            buf.append("\tat ");
            buf.append(st.getMethodName());
            buf.append(" (");
            buf.append(st.getFileName());
            buf.append(':');
            buf.append(st.getLineNumber());
            buf.append(")\n");
        }
        int len = buf.length();
        if (len > 0) {
            if (!$assertionsDisabled && buf.charAt(len - 1) != '\n') {
                throw new AssertionError();
            }
            buf.deleteCharAt(len - 1);
        }
        return buf.toString();
    }

    protected Object getThrown() {
        return null;
    }

    public NashornException initEcmaError(ScriptObject global) {
        if (this.ecmaError != null) {
            return this;
        }
        Object thrown = getThrown();
        if (thrown instanceof ScriptObject) {
            setEcmaError(ScriptObjectMirror.wrap(thrown, global));
        } else {
            setEcmaError(thrown);
        }
        return this;
    }

    public Object getEcmaError() {
        return this.ecmaError;
    }

    public void setEcmaError(Object ecmaError) {
        this.ecmaError = ecmaError;
    }

    private void ensureLineAndFileName() {
        StackTraceElement[] stackTrace;
        if (this.lineAndFileNameUnknown) {
            for (StackTraceElement ste : getStackTrace()) {
                if (ECMAErrors.isScriptFrame(ste)) {
                    this.fileName = ste.getFileName();
                    this.line = ste.getLineNumber();
                    return;
                }
            }
            this.lineAndFileNameUnknown = false;
        }
    }
}
