package org.apache.log4j.spi;

import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jdk.internal.dynalink.CallSiteDescriptor;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/spi/LocationInfo.class */
public class LocationInfo implements Serializable {
    transient String lineNumber;
    transient String fileName;
    transient String className;
    transient String methodName;
    public String fullInfo;
    private static Method getStackTraceMethod;
    private static Method getClassNameMethod;
    private static Method getMethodNameMethod;
    private static Method getFileNameMethod;
    private static Method getLineNumberMethod;
    static final long serialVersionUID = -1325822038990805636L;
    static boolean inVisualAge;
    static Class class$java$lang$Throwable;

    /* renamed from: sw */
    private static StringWriter f400sw = new StringWriter();

    /* renamed from: pw */
    private static PrintWriter f401pw = new PrintWriter(f400sw);

    /* renamed from: NA */
    public static final String f402NA = "?";
    public static final LocationInfo NA_LOCATION_INFO = new LocationInfo(f402NA, f402NA, f402NA, f402NA);

    static {
        Class cls;
        inVisualAge = false;
        try {
            inVisualAge = Class.forName("com.ibm.uvm.tools.DebugSupport") != null;
            LogLog.debug("Detected IBM VisualAge environment.");
        } catch (Throwable th) {
        }
        try {
            if (class$java$lang$Throwable == null) {
                cls = class$("java.lang.Throwable");
                class$java$lang$Throwable = cls;
            } else {
                cls = class$java$lang$Throwable;
            }
            getStackTraceMethod = cls.getMethod("getStackTrace", null);
            Class stackTraceElementClass = Class.forName("java.lang.StackTraceElement");
            getClassNameMethod = stackTraceElementClass.getMethod("getClassName", null);
            getMethodNameMethod = stackTraceElementClass.getMethod("getMethodName", null);
            getFileNameMethod = stackTraceElementClass.getMethod("getFileName", null);
            getLineNumberMethod = stackTraceElementClass.getMethod("getLineNumber", null);
        } catch (ClassNotFoundException e) {
            LogLog.debug("LocationInfo will use pre-JDK 1.4 methods to determine location.");
        } catch (NoSuchMethodException e2) {
            LogLog.debug("LocationInfo will use pre-JDK 1.4 methods to determine location.");
        }
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    public LocationInfo(Throwable t, String fqnOfCallingClass) {
        String s;
        int i;
        if (t == null || fqnOfCallingClass == null) {
            return;
        }
        if (getLineNumberMethod != null) {
            try {
                Object[] elements = (Object[]) getStackTraceMethod.invoke(t, null);
                String prevClass = f402NA;
                for (int i2 = elements.length - 1; i2 >= 0; i2--) {
                    String thisClass = (String) getClassNameMethod.invoke(elements[i2], null);
                    if (fqnOfCallingClass.equals(thisClass)) {
                        int caller = i2 + 1;
                        if (caller < elements.length) {
                            this.className = prevClass;
                            this.methodName = (String) getMethodNameMethod.invoke(elements[caller], null);
                            this.fileName = (String) getFileNameMethod.invoke(elements[caller], null);
                            if (this.fileName == null) {
                                this.fileName = f402NA;
                            }
                            int line = ((Integer) getLineNumberMethod.invoke(elements[caller], null)).intValue();
                            if (line < 0) {
                                this.lineNumber = f402NA;
                            } else {
                                this.lineNumber = String.valueOf(line);
                            }
                            StringBuffer buf = new StringBuffer();
                            buf.append(this.className);
                            buf.append(".");
                            buf.append(this.methodName);
                            buf.append("(");
                            buf.append(this.fileName);
                            buf.append(CallSiteDescriptor.TOKEN_DELIMITER);
                            buf.append(this.lineNumber);
                            buf.append(")");
                            this.fullInfo = buf.toString();
                            return;
                        }
                        return;
                    }
                    prevClass = thisClass;
                }
                return;
            } catch (IllegalAccessException ex) {
                LogLog.debug("LocationInfo failed using JDK 1.4 methods", ex);
            } catch (RuntimeException ex2) {
                LogLog.debug("LocationInfo failed using JDK 1.4 methods", ex2);
            } catch (InvocationTargetException ex3) {
                if ((ex3.getTargetException() instanceof InterruptedException) || (ex3.getTargetException() instanceof InterruptedIOException)) {
                    Thread.currentThread().interrupt();
                }
                LogLog.debug("LocationInfo failed using JDK 1.4 methods", ex3);
            }
        }
        synchronized (f400sw) {
            t.printStackTrace(f401pw);
            s = f400sw.toString();
            f400sw.getBuffer().setLength(0);
        }
        int ibegin = s.lastIndexOf(fqnOfCallingClass);
        if (ibegin == -1) {
            return;
        }
        if (ibegin + fqnOfCallingClass.length() < s.length() && s.charAt(ibegin + fqnOfCallingClass.length()) != '.' && (i = s.lastIndexOf(new StringBuffer().append(fqnOfCallingClass).append(".").toString())) != -1) {
            ibegin = i;
        }
        int ibegin2 = s.indexOf(Layout.LINE_SEP, ibegin);
        if (ibegin2 == -1) {
            return;
        }
        int ibegin3 = ibegin2 + Layout.LINE_SEP_LEN;
        int iend = s.indexOf(Layout.LINE_SEP, ibegin3);
        if (iend == -1) {
            return;
        }
        if (!inVisualAge) {
            int ibegin4 = s.lastIndexOf("at ", iend);
            if (ibegin4 == -1) {
                return;
            }
            ibegin3 = ibegin4 + 3;
        }
        this.fullInfo = s.substring(ibegin3, iend);
    }

    private static final void appendFragment(StringBuffer buf, String fragment) {
        if (fragment == null) {
            buf.append(f402NA);
        } else {
            buf.append(fragment);
        }
    }

    public LocationInfo(String file, String classname, String method, String line) {
        this.fileName = file;
        this.className = classname;
        this.methodName = method;
        this.lineNumber = line;
        StringBuffer buf = new StringBuffer();
        appendFragment(buf, classname);
        buf.append(".");
        appendFragment(buf, method);
        buf.append("(");
        appendFragment(buf, file);
        buf.append(CallSiteDescriptor.TOKEN_DELIMITER);
        appendFragment(buf, line);
        buf.append(")");
        this.fullInfo = buf.toString();
    }

    public String getClassName() {
        if (this.fullInfo == null) {
            return f402NA;
        }
        if (this.className == null) {
            int iend = this.fullInfo.lastIndexOf(40);
            if (iend == -1) {
                this.className = f402NA;
            } else {
                int iend2 = this.fullInfo.lastIndexOf(46, iend);
                int ibegin = 0;
                if (inVisualAge) {
                    ibegin = this.fullInfo.lastIndexOf(32, iend2) + 1;
                }
                if (iend2 == -1) {
                    this.className = f402NA;
                } else {
                    this.className = this.fullInfo.substring(ibegin, iend2);
                }
            }
        }
        return this.className;
    }

    public String getFileName() {
        if (this.fullInfo == null) {
            return f402NA;
        }
        if (this.fileName == null) {
            int iend = this.fullInfo.lastIndexOf(58);
            if (iend == -1) {
                this.fileName = f402NA;
            } else {
                int ibegin = this.fullInfo.lastIndexOf(40, iend - 1);
                this.fileName = this.fullInfo.substring(ibegin + 1, iend);
            }
        }
        return this.fileName;
    }

    public String getLineNumber() {
        if (this.fullInfo == null) {
            return f402NA;
        }
        if (this.lineNumber == null) {
            int iend = this.fullInfo.lastIndexOf(41);
            int ibegin = this.fullInfo.lastIndexOf(58, iend - 1);
            if (ibegin == -1) {
                this.lineNumber = f402NA;
            } else {
                this.lineNumber = this.fullInfo.substring(ibegin + 1, iend);
            }
        }
        return this.lineNumber;
    }

    public String getMethodName() {
        if (this.fullInfo == null) {
            return f402NA;
        }
        if (this.methodName == null) {
            int iend = this.fullInfo.lastIndexOf(40);
            int ibegin = this.fullInfo.lastIndexOf(46, iend);
            if (ibegin == -1) {
                this.methodName = f402NA;
            } else {
                this.methodName = this.fullInfo.substring(ibegin + 1, iend);
            }
        }
        return this.methodName;
    }
}
