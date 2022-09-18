package jdk.nashorn.internal.runtime.regexp.joni;

import java.util.Arrays;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/EncodingHelper.class */
public final class EncodingHelper {
    static final int NEW_LINE = 10;
    static final int RETURN = 13;
    static final int LINE_SEPARATOR = 8232;
    static final int PARAGRAPH_SEPARATOR = 8233;
    static final char[] EMPTYCHARS = new char[0];
    static final int[][] codeRanges = new int[15];

    public static int digitVal(int code) {
        return code - 48;
    }

    public static int odigitVal(int code) {
        return digitVal(code);
    }

    public static boolean isXDigit(int code) {
        return Character.isDigit(code) || (code >= 97 && code <= 102) || (code >= 65 && code <= 70);
    }

    public static int xdigitVal(int code) {
        if (Character.isDigit(code)) {
            return code - 48;
        }
        if (code >= 97 && code <= 102) {
            return (code - 97) + 10;
        }
        return (code - 65) + 10;
    }

    public static boolean isDigit(int code) {
        return code >= 48 && code <= 57;
    }

    public static boolean isWord(int code) {
        return ((1 << Character.getType(code)) & CharacterType.WORD_MASK) != 0;
    }

    public static boolean isNewLine(int code) {
        return code == 10 || code == 13 || code == LINE_SEPARATOR || code == PARAGRAPH_SEPARATOR;
    }

    public static boolean isNewLine(char[] chars, int p, int end) {
        return p < end && isNewLine(chars[p]);
    }

    public static int prevCharHead(int p, int s) {
        if (s <= p) {
            return -1;
        }
        return s - 1;
    }

    public static int rightAdjustCharHeadWithPrev(int s, IntHolder prev) {
        if (prev != null) {
            prev.value = -1;
        }
        return s;
    }

    public static int stepBack(int p, int sp, int np) {
        int s = sp;
        int n = np;
        while (s != -1) {
            int i = n;
            n--;
            if (i <= 0) {
                break;
            } else if (s <= p) {
                return -1;
            } else {
                s--;
            }
        }
        return s;
    }

    public static int mbcodeStartPosition() {
        return 128;
    }

    public static char[] caseFoldCodesByString(int flag, char c) {
        char c2;
        char[] codes2 = EMPTYCHARS;
        char upper = toUpperCase(c);
        if (upper != toLowerCase(upper)) {
            int count = 0;
            char ch = 0;
            do {
                char u = toUpperCase(ch);
                if (u == upper && ch != c) {
                    codes2 = count == 0 ? new char[1] : Arrays.copyOf(codes2, count + 1);
                    int i = count;
                    count++;
                    codes2[i] = ch;
                }
                c2 = ch;
                ch = (char) (ch + 1);
            } while (c2 < 65535);
            return codes2;
        }
        return codes2;
    }

    public static void applyAllCaseFold(int flag, ApplyCaseFold fun, Object arg) {
        int upper;
        int upper2;
        for (int c = 0; c < 65535; c++) {
            if (Character.isLowerCase(c) && (upper2 = toUpperCase(c)) != c) {
                ApplyCaseFold.apply(c, upper2, arg);
            }
        }
        for (int c2 = 0; c2 < 65535; c2++) {
            if (Character.isLowerCase(c2) && (upper = toUpperCase(c2)) != c2) {
                ApplyCaseFold.apply(upper, c2, arg);
            }
        }
    }

    public static char toLowerCase(char c) {
        return (char) toLowerCase((int) c);
    }

    public static int toLowerCase(int c) {
        if (c < 128) {
            return (65 > c || c > 90) ? c : c + 32;
        }
        int lower = Character.toLowerCase(c);
        return lower < 128 ? c : lower;
    }

    public static char toUpperCase(char c) {
        return (char) toUpperCase((int) c);
    }

    public static int toUpperCase(int c) {
        if (c < 128) {
            return (97 > c || c > 122) ? c : c - 32;
        }
        int upper = Character.toUpperCase(c);
        return upper < 128 ? c : upper;
    }

    public static int[] ctypeCodeRange(int ctype, IntHolder sbOut) {
        sbOut.value = 256;
        int[] range = null;
        if (ctype < codeRanges.length) {
            range = codeRanges[ctype];
            if (range == null) {
                range = new int[16];
                int rangeCount = 0;
                int lastCode = -2;
                for (int code = 0; code <= 65535; code++) {
                    if (isCodeCType(code, ctype)) {
                        if (lastCode < code - 1) {
                            if ((rangeCount * 2) + 2 >= range.length) {
                                range = Arrays.copyOf(range, range.length * 2);
                            }
                            range[(rangeCount * 2) + 1] = code;
                            rangeCount++;
                        }
                        int i = code;
                        lastCode = i;
                        range[rangeCount * 2] = i;
                    }
                }
                if ((rangeCount * 2) + 1 < range.length) {
                    range = Arrays.copyOf(range, (rangeCount * 2) + 1);
                }
                range[0] = rangeCount;
                codeRanges[ctype] = range;
            }
        }
        return range;
    }

    public static boolean isInCodeRange(int[] p, int offset, int code) {
        int low = 0;
        int n = p[offset];
        int high = n;
        while (low < high) {
            int x = (low + high) >> 1;
            if (code > p[(x << 1) + 2 + offset]) {
                low = x + 1;
            } else {
                high = x;
            }
        }
        return low < n && code >= p[((low << 1) + 1) + offset];
    }

    public static boolean isCodeCType(int code, int ctype) {
        switch (ctype) {
            case 0:
                return isNewLine(code);
            case 1:
                return ((1 << Character.getType(code)) & CharacterType.ALPHA_MASK) != 0;
            case 2:
                return code == 9 || Character.getType(code) == 12;
            case 3:
                int type = Character.getType(code);
                return ((1 << type) & CharacterType.CNTRL_MASK) != 0 || type == 0;
            case 4:
                return isDigit(code);
            case 5:
                switch (code) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                        return false;
                    default:
                        int type2 = Character.getType(code);
                        return ((1 << type2) & CharacterType.GRAPH_MASK) == 0 && type2 != 0;
                }
            case 6:
                return Character.isLowerCase(code);
            case 7:
                int type3 = Character.getType(code);
                return ((1 << type3) & CharacterType.PRINT_MASK) == 0 && type3 != 0;
            case 8:
                return ((1 << Character.getType(code)) & CharacterType.PUNCT_MASK) != 0;
            case 9:
                switch (code) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                        return true;
                    default:
                        return ((1 << Character.getType(code)) & CharacterType.SPACE_MASK) != 0 || code == 65279;
                }
            case 10:
                return Character.isUpperCase(code);
            case 11:
                return isXDigit(code);
            case 12:
                return ((1 << Character.getType(code)) & CharacterType.WORD_MASK) != 0;
            case 13:
                return ((1 << Character.getType(code)) & CharacterType.ALNUM_MASK) != 0;
            case 14:
                return code < 128;
            default:
                throw new RuntimeException("illegal character type: " + ctype);
        }
    }
}
