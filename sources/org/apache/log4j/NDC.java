package org.apache.log4j;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import org.apache.log4j.helpers.LogLog;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/NDC.class */
public class NDC {

    /* renamed from: ht */
    static Hashtable f385ht = new Hashtable();
    static int pushCounter = 0;
    static final int REAP_THRESHOLD = 5;

    private NDC() {
    }

    private static Stack getCurrentStack() {
        if (f385ht != null) {
            return (Stack) f385ht.get(Thread.currentThread());
        }
        return null;
    }

    public static void clear() {
        Stack stack = getCurrentStack();
        if (stack != null) {
            stack.setSize(0);
        }
    }

    public static Stack cloneStack() {
        Stack stack = getCurrentStack();
        if (stack == null) {
            return null;
        }
        return (Stack) stack.clone();
    }

    public static void inherit(Stack stack) {
        if (stack != null) {
            f385ht.put(Thread.currentThread(), stack);
        }
    }

    public static String get() {
        Stack s = getCurrentStack();
        if (s != null && !s.isEmpty()) {
            return ((DiagnosticContext) s.peek()).fullMessage;
        }
        return null;
    }

    public static int getDepth() {
        Stack stack = getCurrentStack();
        if (stack == null) {
            return 0;
        }
        return stack.size();
    }

    private static void lazyRemove() {
        if (f385ht == null) {
            return;
        }
        synchronized (f385ht) {
            int i = pushCounter + 1;
            pushCounter = i;
            if (i <= 5) {
                return;
            }
            pushCounter = 0;
            int misses = 0;
            Vector v = new Vector();
            Enumeration enumeration = f385ht.keys();
            while (enumeration.hasMoreElements() && misses <= 4) {
                Thread t = (Thread) enumeration.nextElement();
                if (t.isAlive()) {
                    misses++;
                } else {
                    misses = 0;
                    v.addElement(t);
                }
            }
            int size = v.size();
            for (int i2 = 0; i2 < size; i2++) {
                Thread t2 = (Thread) v.elementAt(i2);
                LogLog.debug(new StringBuffer().append("Lazy NDC removal for thread [").append(t2.getName()).append("] (").append(f385ht.size()).append(").").toString());
                f385ht.remove(t2);
            }
        }
    }

    public static String pop() {
        Stack stack = getCurrentStack();
        if (stack != null && !stack.isEmpty()) {
            return ((DiagnosticContext) stack.pop()).message;
        }
        return "";
    }

    public static String peek() {
        Stack stack = getCurrentStack();
        if (stack != null && !stack.isEmpty()) {
            return ((DiagnosticContext) stack.peek()).message;
        }
        return "";
    }

    public static void push(String message) {
        Stack stack = getCurrentStack();
        if (stack == null) {
            DiagnosticContext dc = new DiagnosticContext(message, null);
            Stack stack2 = new Stack();
            Thread key = Thread.currentThread();
            f385ht.put(key, stack2);
            stack2.push(dc);
        } else if (stack.isEmpty()) {
            DiagnosticContext dc2 = new DiagnosticContext(message, null);
            stack.push(dc2);
        } else {
            DiagnosticContext parent = (DiagnosticContext) stack.peek();
            stack.push(new DiagnosticContext(message, parent));
        }
    }

    public static void remove() {
        if (f385ht != null) {
            f385ht.remove(Thread.currentThread());
            lazyRemove();
        }
    }

    public static void setMaxDepth(int maxDepth) {
        Stack stack = getCurrentStack();
        if (stack != null && maxDepth < stack.size()) {
            stack.setSize(maxDepth);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:org/apache/log4j/NDC$DiagnosticContext.class */
    public static class DiagnosticContext {
        String fullMessage;
        String message;

        DiagnosticContext(String message, DiagnosticContext parent) {
            this.message = message;
            if (parent != null) {
                this.fullMessage = new StringBuffer().append(parent.fullMessage).append(' ').append(message).toString();
            } else {
                this.fullMessage = message;
            }
        }
    }
}
