package jdk.internal.dynalink.support;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/NameCodec.class */
public class NameCodec {
    private static final char ESCAPE_CHAR = '\\';
    private static final char EMPTY_ESCAPE = '=';
    private static final String EMPTY_NAME;
    private static final char EMPTY_CHAR = 65279;
    private static final int MIN_ENCODING = 36;
    private static final int MAX_ENCODING = 93;
    private static final char[] ENCODING;
    private static final int MIN_DECODING = 33;
    private static final int MAX_DECODING = 125;
    private static final char[] DECODING;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NameCodec.class.desiredAssertionStatus();
        EMPTY_NAME = "\\=";
        ENCODING = new char[58];
        DECODING = new char[93];
        addEncoding('/', '|');
        addEncoding('.', ',');
        addEncoding(';', '?');
        addEncoding('$', '%');
        addEncoding('<', '^');
        addEncoding('>', '_');
        addEncoding('[', '{');
        addEncoding(']', '}');
        addEncoding(':', '!');
        addEncoding('\\', '-');
        DECODING[28] = 65279;
    }

    private NameCodec() {
    }

    public static String encode(String name) {
        char e;
        int l = name.length();
        if (l == 0) {
            return EMPTY_NAME;
        }
        StringBuilder b = null;
        int lastEscape = -1;
        for (int i = 0; i < l; i++) {
            int encodeIndex = name.charAt(i) - '$';
            if (encodeIndex >= 0 && encodeIndex < ENCODING.length && (e = ENCODING[encodeIndex]) != 0) {
                if (b == null) {
                    b = new StringBuilder(name.length() + 3);
                    if (name.charAt(0) != '\\' && i > 0) {
                        b.append(EMPTY_NAME);
                    }
                    b.append((CharSequence) name, 0, i);
                } else {
                    b.append((CharSequence) name, lastEscape + 1, i);
                }
                b.append('\\').append(e);
                lastEscape = i;
            }
        }
        if (b == null) {
            return name.toString();
        }
        if (!$assertionsDisabled && lastEscape == -1) {
            throw new AssertionError();
        }
        b.append((CharSequence) name, lastEscape + 1, l);
        return b.toString();
    }

    public static String decode(String name) {
        if (name.charAt(0) != '\\') {
            return name;
        }
        int l = name.length();
        if (l == 2 && name.charAt(1) == EMPTY_CHAR) {
            return "";
        }
        StringBuilder b = new StringBuilder(name.length());
        int lastEscape = -2;
        int i = -1;
        while (true) {
            int lastBackslash = i;
            int nextBackslash = name.indexOf(92, lastBackslash + 1);
            if (nextBackslash == -1 || nextBackslash == l - 1) {
                break;
            }
            int decodeIndex = name.charAt(nextBackslash + 1) - '!';
            if (decodeIndex >= 0 && decodeIndex < DECODING.length) {
                char d = DECODING[decodeIndex];
                if (d == EMPTY_CHAR) {
                    if (nextBackslash == 0) {
                        lastEscape = 0;
                    }
                } else if (d != 0) {
                    b.append((CharSequence) name, lastEscape + 2, nextBackslash).append(d);
                    lastEscape = nextBackslash;
                }
            }
            i = nextBackslash;
        }
        b.append((CharSequence) name, lastEscape + 2, l);
        return b.toString();
    }

    private static void addEncoding(char from, char to) {
        ENCODING[from - '$'] = to;
        DECODING[to - '!'] = from;
    }
}
