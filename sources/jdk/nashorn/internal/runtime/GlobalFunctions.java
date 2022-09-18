package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import jdk.nashorn.internal.lookup.Lookup;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/GlobalFunctions.class */
public final class GlobalFunctions {
    public static final MethodHandle PARSEINT = findOwnMH("parseInt", Double.TYPE, Object.class, Object.class, Object.class);
    public static final MethodHandle PARSEINT_OI = findOwnMH("parseInt", Double.TYPE, Object.class, Object.class, Integer.TYPE);
    public static final MethodHandle PARSEINT_Z = Lookup.f248MH.dropArguments(Lookup.f248MH.dropArguments(Lookup.f248MH.constant(Double.TYPE, Double.valueOf(Double.NaN)), 0, Boolean.TYPE), 0, Object.class);
    public static final MethodHandle PARSEINT_I = Lookup.f248MH.dropArguments(Lookup.f248MH.identity(Integer.TYPE), 0, Object.class);
    public static final MethodHandle PARSEINT_O = findOwnMH("parseInt", Double.TYPE, Object.class, Object.class);
    public static final MethodHandle PARSEFLOAT = findOwnMH("parseFloat", Double.TYPE, Object.class, Object.class);
    public static final MethodHandle IS_NAN_I = Lookup.f248MH.dropArguments(Lookup.f248MH.constant(Boolean.TYPE, false), 0, Object.class);
    public static final MethodHandle IS_NAN_J = Lookup.f248MH.dropArguments(Lookup.f248MH.constant(Boolean.TYPE, false), 0, Object.class);
    public static final MethodHandle IS_NAN_D = Lookup.f248MH.dropArguments(Lookup.f248MH.findStatic(MethodHandles.lookup(), Double.class, "isNaN", Lookup.f248MH.type(Boolean.TYPE, Double.TYPE)), 0, Object.class);
    public static final MethodHandle IS_NAN = findOwnMH("isNaN", Boolean.TYPE, Object.class, Object.class);
    public static final MethodHandle IS_FINITE = findOwnMH("isFinite", Boolean.TYPE, Object.class, Object.class);
    public static final MethodHandle ENCODE_URI = findOwnMH("encodeURI", Object.class, Object.class, Object.class);
    public static final MethodHandle ENCODE_URICOMPONENT = findOwnMH("encodeURIComponent", Object.class, Object.class, Object.class);
    public static final MethodHandle DECODE_URI = findOwnMH("decodeURI", Object.class, Object.class, Object.class);
    public static final MethodHandle DECODE_URICOMPONENT = findOwnMH("decodeURIComponent", Object.class, Object.class, Object.class);
    public static final MethodHandle ESCAPE = findOwnMH("escape", String.class, Object.class, Object.class);
    public static final MethodHandle UNESCAPE = findOwnMH("unescape", String.class, Object.class, Object.class);
    public static final MethodHandle ANONYMOUS = findOwnMH("anonymous", Object.class, Object.class);
    private static final String UNESCAPED = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@*_+-./";

    private GlobalFunctions() {
    }

    public static double parseInt(Object self, Object string, Object rad) {
        return parseIntInternal(JSType.trimLeft(JSType.toString(string)), JSType.toInt32(rad));
    }

    public static double parseInt(Object self, Object string, int rad) {
        return parseIntInternal(JSType.trimLeft(JSType.toString(string)), rad);
    }

    public static double parseInt(Object self, Object string) {
        return parseIntInternal(JSType.trimLeft(JSType.toString(string)), 0);
    }

    private static double parseIntInternal(String str, int rad) {
        int length = str.length();
        int radix = rad;
        if (length == 0) {
            return Double.NaN;
        }
        boolean negative = false;
        int idx = 0;
        char firstChar = str.charAt(0);
        if (firstChar < '0') {
            if (firstChar == '-') {
                negative = true;
            } else if (firstChar != '+') {
                return Double.NaN;
            }
            idx = 0 + 1;
        }
        boolean stripPrefix = true;
        if (radix != 0) {
            if (radix < 2 || radix > 36) {
                return Double.NaN;
            }
            if (radix != 16) {
                stripPrefix = false;
            }
        } else {
            radix = 10;
        }
        if (stripPrefix && idx + 1 < length) {
            char c1 = str.charAt(idx);
            char c2 = str.charAt(idx + 1);
            if (c1 == '0' && (c2 == 'x' || c2 == 'X')) {
                radix = 16;
                idx += 2;
            }
        }
        double result = 0.0d;
        boolean entered = false;
        while (idx < length) {
            int i = idx;
            idx++;
            int digit = fastDigit(str.charAt(i), radix);
            if (digit < 0) {
                break;
            }
            entered = true;
            result = (result * radix) + digit;
        }
        if (!entered) {
            return Double.NaN;
        }
        return negative ? -result : result;
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01fb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static double parseFloat(java.lang.Object r4, java.lang.Object r5) {
        /*
            Method dump skipped, instructions count: 547
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.GlobalFunctions.parseFloat(java.lang.Object, java.lang.Object):double");
    }

    public static boolean isNaN(Object self, Object number) {
        return Double.isNaN(JSType.toNumber(number));
    }

    public static boolean isFinite(Object self, Object number) {
        double value = JSType.toNumber(number);
        return !Double.isInfinite(value) && !Double.isNaN(value);
    }

    public static Object encodeURI(Object self, Object uri) {
        return URIUtils.encodeURI(self, JSType.toString(uri));
    }

    public static Object encodeURIComponent(Object self, Object uri) {
        return URIUtils.encodeURIComponent(self, JSType.toString(uri));
    }

    public static Object decodeURI(Object self, Object uri) {
        return URIUtils.decodeURI(self, JSType.toString(uri));
    }

    public static Object decodeURIComponent(Object self, Object uri) {
        return URIUtils.decodeURIComponent(self, JSType.toString(uri));
    }

    public static String escape(Object self, Object string) {
        String str = JSType.toString(string);
        int length = str.length();
        if (length == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < length; k++) {
            char ch = str.charAt(k);
            if (UNESCAPED.indexOf(ch) != -1) {
                sb.append(ch);
            } else if (ch < 256) {
                sb.append('%');
                if (ch < 16) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(ch).toUpperCase(Locale.ENGLISH));
            } else {
                sb.append("%u");
                if (ch < 4096) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(ch).toUpperCase(Locale.ENGLISH));
            }
        }
        return sb.toString();
    }

    public static String unescape(Object self, Object string) {
        String str = JSType.toString(string);
        int length = str.length();
        if (length == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        int k = 0;
        while (k < length) {
            char ch = str.charAt(k);
            if (ch != '%') {
                sb.append(ch);
            } else {
                if (k < length - 5 && str.charAt(k + 1) == 'u') {
                    try {
                        ch = (char) Integer.parseInt(str.substring(k + 2, k + 6), 16);
                        sb.append(ch);
                        k += 5;
                    } catch (NumberFormatException e) {
                    }
                }
                if (k < length - 2) {
                    try {
                        ch = (char) Integer.parseInt(str.substring(k + 1, k + 3), 16);
                        sb.append(ch);
                        k += 2;
                    } catch (NumberFormatException e2) {
                    }
                }
                sb.append(ch);
            }
            k++;
        }
        return sb.toString();
    }

    public static Object anonymous(Object self) {
        return ScriptRuntime.UNDEFINED;
    }

    private static int fastDigit(int ch, int radix) {
        int n = -1;
        if (ch >= 48 && ch <= 57) {
            n = ch - 48;
        } else if (radix > 10) {
            if (ch >= 97 && ch <= 122) {
                n = (ch - 97) + 10;
            } else if (ch >= 65 && ch <= 90) {
                n = (ch - 65) + 10;
            }
        }
        if (n < radix) {
            return n;
        }
        return -1;
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), GlobalFunctions.class, name, Lookup.f248MH.type(rtype, types));
    }
}
