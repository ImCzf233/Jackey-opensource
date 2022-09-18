package jdk.nashorn.internal.runtime;

import kotlin.jvm.internal.CharCompanionObject;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/URIUtils.class */
public final class URIUtils {
    private static final String URI_UNESCAPED_NONALPHANUMERIC = "-_.!~*'()";
    private static final String URI_RESERVED = ";/?:@&=+$,#";

    private URIUtils() {
    }

    public static String encodeURI(Object self, String string) {
        return encode(self, string, false);
    }

    public static String encodeURIComponent(Object self, String string) {
        return encode(self, string, true);
    }

    public static String decodeURI(Object self, String string) {
        return decode(self, string, false);
    }

    public static String decodeURIComponent(Object self, String string) {
        return decode(self, string, true);
    }

    private static String encode(Object self, String string, boolean component) {
        char c;
        if (string.isEmpty()) {
            return string;
        }
        int len = string.length();
        StringBuilder sb = new StringBuilder();
        int k = 0;
        while (k < len) {
            char C = string.charAt(k);
            if (isUnescaped(C, component)) {
                sb.append(C);
            } else if (C >= 56320 && C <= 57343) {
                return error(string, k);
            } else {
                if (C < 55296 || C > 56319) {
                    c = C;
                } else {
                    k++;
                    if (k == len) {
                        return error(string, k);
                    }
                    char kChar = string.charAt(k);
                    if (kChar < 56320 || kChar > 57343) {
                        return error(string, k);
                    }
                    c = ((C - 55296) * 1024) + (kChar - CharCompanionObject.MIN_LOW_SURROGATE) + 65536;
                }
                try {
                    sb.append(toHexEscape(c));
                } catch (Exception e) {
                    throw ECMAErrors.uriError(e, "bad.uri", string, Integer.toString(k));
                }
            }
            k++;
        }
        return sb.toString();
    }

    private static String decode(Object self, String string, boolean component) {
        int minV;
        int V;
        int n;
        if (string.isEmpty()) {
            return string;
        }
        int len = string.length();
        StringBuilder sb = new StringBuilder();
        int k = 0;
        while (k < len) {
            char ch = string.charAt(k);
            if (ch != '%') {
                sb.append(ch);
            } else {
                int start = k;
                if (k + 2 >= len) {
                    return error(string, k);
                }
                int B = toHexByte(string.charAt(k + 1), string.charAt(k + 2));
                if (B < 0) {
                    return error(string, k + 1);
                }
                k += 2;
                if ((B & 128) == 0) {
                    char C = (char) B;
                    if (!component && URI_RESERVED.indexOf(C) >= 0) {
                        for (int j = start; j <= k; j++) {
                            sb.append(string.charAt(j));
                        }
                    } else {
                        sb.append(C);
                    }
                } else if ((B & 192) == 128) {
                    return error(string, k);
                } else {
                    if ((B & 32) == 0) {
                        n = 2;
                        V = B & 31;
                        minV = 128;
                    } else if ((B & 16) == 0) {
                        n = 3;
                        V = B & 15;
                        minV = 2048;
                    } else if ((B & 8) == 0) {
                        n = 4;
                        V = B & 7;
                        minV = 65536;
                    } else if ((B & 4) == 0) {
                        n = 5;
                        V = B & 3;
                        minV = 2097152;
                    } else if ((B & 2) == 0) {
                        n = 6;
                        V = B & 1;
                        minV = 67108864;
                    } else {
                        return error(string, k);
                    }
                    if (k + (3 * (n - 1)) >= len) {
                        return error(string, k);
                    }
                    for (int j2 = 1; j2 < n; j2++) {
                        int k2 = k + 1;
                        if (string.charAt(k2) != '%') {
                            return error(string, k2);
                        }
                        int B2 = toHexByte(string.charAt(k2 + 1), string.charAt(k2 + 2));
                        if (B2 < 0 || (B2 & 192) != 128) {
                            return error(string, k2 + 1);
                        }
                        V = (V << 6) | (B2 & 63);
                        k = k2 + 2;
                    }
                    if (V < minV || (V >= 55296 && V <= 57343)) {
                        V = Integer.MAX_VALUE;
                    }
                    if (V < 65536) {
                        char C2 = (char) V;
                        if (!component && URI_RESERVED.indexOf(C2) >= 0) {
                            for (int j3 = start; j3 != k; j3++) {
                                sb.append(string.charAt(j3));
                            }
                        } else {
                            sb.append(C2);
                        }
                    } else if (V > 1114111) {
                        return error(string, k);
                    } else {
                        int L = ((V - 65536) & 1023) + CharCompanionObject.MIN_LOW_SURROGATE;
                        int H = (((V - 65536) >> 10) & 1023) + 55296;
                        sb.append((char) H);
                        sb.append((char) L);
                    }
                }
            }
            k++;
        }
        return sb.toString();
    }

    private static int hexDigit(char ch) {
        char chu = Character.toUpperCase(ch);
        if (chu >= '0' && chu <= '9') {
            return chu - '0';
        }
        if (chu >= 'A' && chu <= 'F') {
            return (chu - 'A') + 10;
        }
        return -1;
    }

    private static int toHexByte(char ch1, char ch2) {
        int i1 = hexDigit(ch1);
        int i2 = hexDigit(ch2);
        if (i1 >= 0 && i2 >= 0) {
            return (i1 << 4) | i2;
        }
        return -1;
    }

    private static String toHexEscape(int u0) {
        int len;
        int u = u0;
        byte[] b = new byte[6];
        if (u <= 127) {
            b[0] = (byte) u;
            len = 1;
        } else {
            len = 2;
            int i = u;
            int i2 = 11;
            while (true) {
                int mask = i >>> i2;
                if (mask == 0) {
                    break;
                }
                len++;
                i = mask;
                i2 = 5;
            }
            for (int i3 = len - 1; i3 > 0; i3--) {
                b[i3] = (byte) (128 | (u & 63));
                u >>>= 6;
            }
            b[0] = (byte) ((((1 << (8 - len)) - 1) ^ (-1)) | u);
        }
        StringBuilder sb = new StringBuilder();
        for (int i4 = 0; i4 < len; i4++) {
            sb.append('%');
            if ((b[i4] & 255) < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(b[i4] & 255).toUpperCase());
        }
        return sb.toString();
    }

    private static String error(String string, int index) {
        throw ECMAErrors.uriError("bad.uri", string, Integer.toString(index));
    }

    private static boolean isUnescaped(char ch, boolean component) {
        if ('A' > ch || ch > 'Z') {
            if ('a' <= ch && ch <= 'z') {
                return true;
            }
            if (('0' <= ch && ch <= '9') || URI_UNESCAPED_NONALPHANUMERIC.indexOf(ch) >= 0) {
                return true;
            }
            return !component && URI_RESERVED.indexOf(ch) >= 0;
        }
        return true;
    }
}
