package jdk.nashorn.internal.runtime;

import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenStream;
import jdk.nashorn.internal.parser.TokenType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Debug.class */
public final class Debug {
    private Debug() {
    }

    public static String firstJSFrame(Throwable t) {
        StackTraceElement[] stackTrace;
        for (StackTraceElement ste : t.getStackTrace()) {
            if (ECMAErrors.isScriptFrame(ste)) {
                return ste.toString();
            }
        }
        return "<native code>";
    }

    public static String firstJSFrame() {
        return firstJSFrame(new Throwable());
    }

    public static String scriptStack() {
        return NashornException.getScriptStackString(new Throwable());
    }

    /* renamed from: id */
    public static String m67id(Object x) {
        return String.format("0x%08x", Integer.valueOf(System.identityHashCode(x)));
    }

    public static int intId(Object x) {
        return System.identityHashCode(x);
    }

    public static String stackTraceElementAt(int depth) {
        return new Throwable().getStackTrace()[depth + 1].toString();
    }

    public static String caller(int depth, int count, String... ignores) {
        String result = "";
        StackTraceElement[] callers = Thread.currentThread().getStackTrace();
        int c = count;
        for (int i = depth + 1; i < callers.length && c != 0; i++) {
            StackTraceElement element = callers[i];
            String method = element.getMethodName();
            int length = ignores.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    String ignore = ignores[i2];
                    if (method.compareTo(ignore) == 0) {
                        break;
                    }
                    i2++;
                } else {
                    result = result + (method + CallSiteDescriptor.TOKEN_DELIMITER + element.getLineNumber() + "                              ").substring(0, 30);
                    c--;
                    break;
                }
            }
        }
        return result.isEmpty() ? "<no caller>" : result;
    }

    public static void dumpTokens(Source source, Lexer lexer, TokenStream stream) {
        int k = 0;
        while (true) {
            if (k > stream.last()) {
                lexer.lexify();
            } else {
                long token = stream.get(k);
                TokenType type = Token.descType(token);
                System.out.println("" + k + ": " + Token.toString(source, token, true));
                k++;
                if (type == TokenType.EOF) {
                    return;
                }
            }
        }
    }
}
