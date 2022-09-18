package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/SearchAlgorithm.class */
public abstract class SearchAlgorithm {
    public static final SearchAlgorithm NONE = new SearchAlgorithm() { // from class: jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm.1
        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final String getName() {
            return "NONE";
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int search(Regex regex, char[] text, int textP, int textEnd, int textRange) {
            return textP;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int searchBackward(Regex regex, char[] text, int textP, int adjustText, int textEnd, int textStart, int s_, int range_) {
            return textP;
        }
    };
    public static final SearchAlgorithm SLOW = new SearchAlgorithm() { // from class: jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm.2
        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final String getName() {
            return "EXACT";
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int search(Regex regex, char[] text, int textP, int textEnd, int textRange) {
            char[] target = regex.exact;
            int targetP = regex.exactP;
            int targetEnd = regex.exactEnd;
            int end = textEnd - ((targetEnd - targetP) - 1);
            if (end > textRange) {
                end = textRange;
            }
            for (int s = textP; s < end; s++) {
                if (text[s] == target[targetP]) {
                    int p = s + 1;
                    int t = targetP + 1;
                    while (t < targetEnd) {
                        int i = p;
                        p++;
                        if (target[t] != text[i]) {
                            break;
                        }
                        t++;
                    }
                    if (t == targetEnd) {
                        return s;
                    }
                }
            }
            return -1;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int searchBackward(Regex regex, char[] text, int textP, int adjustText, int textEnd, int textStart, int s_, int range_) {
            char[] target = regex.exact;
            int targetP = regex.exactP;
            int targetEnd = regex.exactEnd;
            int s = textEnd - (targetEnd - targetP);
            if (s > textStart) {
                s = textStart;
            }
            while (s >= textP) {
                if (text[s] == target[targetP]) {
                    int p = s + 1;
                    int t = targetP + 1;
                    while (t < targetEnd) {
                        int i = p;
                        p++;
                        if (target[t] != text[i]) {
                            break;
                        }
                        t++;
                    }
                    if (t == targetEnd) {
                        return s;
                    }
                }
                s--;
            }
            return -1;
        }
    };

    /* renamed from: BM */
    public static final SearchAlgorithm f291BM = new SearchAlgorithm() { // from class: jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm.3
        private static final int BM_BACKWARD_SEARCH_LENGTH_THRESHOLD = 100;

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final String getName() {
            return "EXACT_BM";
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int search(Regex regex, char[] text, int textP, int textEnd, int textRange) {
            char[] target = regex.exact;
            int targetP = regex.exactP;
            int targetEnd = regex.exactEnd;
            int end = (textRange + (targetEnd - targetP)) - 1;
            if (end > textEnd) {
                end = textEnd;
            }
            int tail = targetEnd - 1;
            int s = (textP + (targetEnd - targetP)) - 1;
            if (regex.intMap == null) {
                while (s < end) {
                    int p = s;
                    for (int t = tail; text[p] == target[t]; t--) {
                        if (t == targetP) {
                            return p;
                        }
                        p--;
                    }
                    s += regex.map[text[s] & 255];
                }
                return -1;
            }
            while (s < end) {
                int p2 = s;
                for (int t2 = tail; text[p2] == target[t2]; t2--) {
                    if (t2 == targetP) {
                        return p2;
                    }
                    p2--;
                }
                s += regex.intMap[text[s] & 255];
            }
            return -1;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int searchBackward(Regex regex, char[] text, int textP, int adjustText, int textEnd, int textStart, int s_, int range_) {
            char[] target = regex.exact;
            int targetP = regex.exactP;
            int targetEnd = regex.exactEnd;
            if (regex.intMapBackward == null) {
                if (s_ - range_ < 100) {
                    return SLOW.searchBackward(regex, text, textP, adjustText, textEnd, textStart, s_, range_);
                }
                setBmBackwardSkip(regex, target, targetP, targetEnd);
            }
            int s = textEnd - (targetEnd - targetP);
            if (textStart < s) {
                s = textStart;
            }
            while (s >= textP) {
                int p = s;
                int t = targetP;
                while (t < targetEnd && text[p] == target[t]) {
                    p++;
                    t++;
                }
                if (t == targetEnd) {
                    return s;
                }
                s -= regex.intMapBackward[text[s] & 255];
            }
            return -1;
        }

        private void setBmBackwardSkip(Regex regex, char[] chars, int p, int end) {
            int[] skip;
            if (regex.intMapBackward == null) {
                skip = new int[256];
                regex.intMapBackward = skip;
            } else {
                skip = regex.intMapBackward;
            }
            int len = end - p;
            for (int i = 0; i < 256; i++) {
                skip[i] = len;
            }
            for (int i2 = len - 1; i2 > 0; i2--) {
                skip[chars[i2] & 255] = i2;
            }
        }
    };
    public static final SearchAlgorithm MAP = new SearchAlgorithm() { // from class: jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm.4
        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final String getName() {
            return "MAP";
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int search(Regex regex, char[] text, int textP, int textEnd, int textRange) {
            byte[] map = regex.map;
            for (int s = textP; s < textRange; s++) {
                if (text[s] > 255 || map[text[s]] != 0) {
                    return s;
                }
            }
            return -1;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int searchBackward(Regex regex, char[] text, int textP, int adjustText, int textEnd, int textStart, int s_, int range_) {
            byte[] map = regex.map;
            int s = textStart;
            if (s >= textEnd) {
                s = textEnd - 1;
            }
            while (s >= textP) {
                if (text[s] > 255 || map[text[s]] != 0) {
                    return s;
                }
                s--;
            }
            return -1;
        }
    };

    public abstract String getName();

    public abstract int search(Regex regex, char[] cArr, int i, int i2, int i3);

    public abstract int searchBackward(Regex regex, char[] cArr, int i, int i2, int i3, int i4, int i5, int i6);

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/SearchAlgorithm$SLOW_IC.class */
    public static final class SLOW_IC extends SearchAlgorithm {
        public SLOW_IC(Regex regex) {
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final String getName() {
            return "EXACT_IC";
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int search(Regex regex, char[] text, int textP, int textEnd, int textRange) {
            char[] target = regex.exact;
            int targetP = regex.exactP;
            int targetEnd = regex.exactEnd;
            int end = textEnd - ((targetEnd - targetP) - 1);
            if (end > textRange) {
                end = textRange;
            }
            for (int s = textP; s < end; s++) {
                if (lowerCaseMatch(target, targetP, targetEnd, text, s, textEnd)) {
                    return s;
                }
            }
            return -1;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int searchBackward(Regex regex, char[] text, int textP, int adjustText, int textEnd, int textStart, int s_, int range_) {
            char[] target = regex.exact;
            int targetP = regex.exactP;
            int targetEnd = regex.exactEnd;
            int s = textEnd - (targetEnd - targetP);
            if (s > textStart) {
                s = textStart;
            }
            while (s >= textP) {
                if (lowerCaseMatch(target, targetP, targetEnd, text, s, textEnd)) {
                    return s;
                }
                s = EncodingHelper.prevCharHead(adjustText, s);
            }
            return -1;
        }

        private static boolean lowerCaseMatch(char[] t, int tPp, int tEnd, char[] chars, int pp, int end) {
            int tP = tPp;
            int p = pp;
            while (tP < tEnd) {
                int i = tP;
                tP++;
                int i2 = p;
                p++;
                if (t[i] != EncodingHelper.toLowerCase(chars[i2])) {
                    return false;
                }
            }
            return true;
        }
    }
}
