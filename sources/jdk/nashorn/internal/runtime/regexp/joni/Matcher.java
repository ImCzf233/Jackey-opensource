package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/Matcher.class */
public abstract class Matcher extends IntHolder {
    protected final Regex regex;
    protected final char[] chars;
    protected final int str;
    protected final int end;
    protected int msaStart;
    protected int msaOptions;
    protected final Region msaRegion;
    protected int msaBestLen;
    protected int msaBestS;
    protected int msaBegin;
    protected int msaEnd;
    int low;
    int high;

    protected abstract int matchAt(int i, int i2, int i3);

    public Matcher(Regex regex, char[] chars) {
        this(regex, chars, 0, chars.length);
    }

    public Matcher(Regex regex, char[] chars, int p, int end) {
        this.regex = regex;
        this.chars = chars;
        this.str = p;
        this.end = end;
        this.msaRegion = regex.numMem == 0 ? null : new Region(regex.numMem + 1);
    }

    public final Region getRegion() {
        return this.msaRegion;
    }

    public final int getBegin() {
        return this.msaBegin;
    }

    public final int getEnd() {
        return this.msaEnd;
    }

    protected final void msaInit(int option, int start) {
        this.msaOptions = option;
        this.msaStart = start;
        this.msaBestLen = -1;
    }

    public final int match(int at, int range, int option) {
        msaInit(option, at);
        int prev = EncodingHelper.prevCharHead(this.str, at);
        return matchAt(range, at, prev);
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00d8, code lost:
        if (r7.regex.dMax != 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00db, code lost:
        r7.low = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00e3, code lost:
        if (r13 == null) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ec, code lost:
        if (r7.low <= r11) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ef, code lost:
        r13.value = jdk.nashorn.internal.runtime.regexp.joni.EncodingHelper.prevCharHead(r11, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0103, code lost:
        if (r14 == (-1)) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0106, code lost:
        r1 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x010b, code lost:
        r1 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x010c, code lost:
        r13.value = jdk.nashorn.internal.runtime.regexp.joni.EncodingHelper.prevCharHead(r1, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0120, code lost:
        if (r7.regex.dMax == Integer.MAX_VALUE) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0123, code lost:
        r7.low = r0 - r7.regex.dMax;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0137, code lost:
        if (r7.low <= r11) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x013a, code lost:
        r7.low = jdk.nashorn.internal.runtime.regexp.joni.EncodingHelper.rightAdjustCharHeadWithPrev(r7.low, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0149, code lost:
        if (r13 == null) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0152, code lost:
        if (r13.value != (-1)) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x015a, code lost:
        if (r14 == (-1)) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x015d, code lost:
        r1 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0162, code lost:
        r1 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0164, code lost:
        r13.value = jdk.nashorn.internal.runtime.regexp.joni.EncodingHelper.prevCharHead(r1, r7.low);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0173, code lost:
        if (r13 == null) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x017b, code lost:
        if (r14 == (-1)) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x017e, code lost:
        r1 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0183, code lost:
        r1 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0184, code lost:
        r13.value = jdk.nashorn.internal.runtime.regexp.joni.EncodingHelper.prevCharHead(r1, r7.low);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x018e, code lost:
        r7.high = r0 - r7.regex.dMin;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x019d, code lost:
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean forwardSearchRange(char[] r8, int r9, int r10, int r11, int r12, jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder r13) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.regexp.joni.Matcher.forwardSearchRange(char[], int, int, int, int, jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder):boolean");
    }

    private boolean backwardSearchRange(char[] ch, int string, int e, int s, int range, int adjrange) {
        int p;
        int r = range + this.regex.dMin;
        int p2 = s;
        while (true) {
            p = this.regex.searchAlgorithm.searchBackward(this.regex, ch, r, adjrange, e, p2, s, r);
            if (p != -1) {
                if (this.regex.subAnchor != 0) {
                    switch (this.regex.subAnchor) {
                        case 2:
                            if (p == string) {
                                break;
                            } else {
                                int prev = EncodingHelper.prevCharHead(string, p);
                                if (EncodingHelper.isNewLine(ch, prev, e)) {
                                    break;
                                } else {
                                    p2 = prev;
                                    break;
                                }
                            }
                        case 32:
                            if (p != e && !EncodingHelper.isNewLine(ch, p, e)) {
                                p2 = EncodingHelper.prevCharHead(adjrange, p);
                                if (p2 != -1) {
                                    break;
                                } else {
                                    return false;
                                }
                            }
                            break;
                    }
                }
            } else {
                return false;
            }
        }
        if (this.regex.dMax != Integer.MAX_VALUE) {
            this.low = p - this.regex.dMax;
            this.high = p - this.regex.dMin;
            return true;
        }
        return true;
    }

    private boolean matchCheck(int upperRange, int s, int prev) {
        if (matchAt(this.end, s, prev) != -1 && !Option.isFindLongest(this.regex.options)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int search(int r9, int r10, int r11) {
        /*
            Method dump skipped, instructions count: 1167
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.regexp.joni.Matcher.search(int, int, int):int");
    }

    private boolean endBuf(int startp, int rangep, int minSemiEnd, int maxSemiEnd) {
        int start = startp;
        int range = rangep;
        if (maxSemiEnd - this.str < this.regex.anchorDmin) {
            return true;
        }
        if (range > start) {
            if (minSemiEnd - start > this.regex.anchorDmax) {
                start = minSemiEnd - this.regex.anchorDmax;
                if (start >= this.end) {
                    start = EncodingHelper.prevCharHead(this.str, this.end);
                }
            }
            if (maxSemiEnd - (range - 1) < this.regex.anchorDmin) {
                range = (maxSemiEnd - this.regex.anchorDmin) + 1;
            }
            if (start >= range) {
                return true;
            }
            return false;
        }
        if (minSemiEnd - range > this.regex.anchorDmax) {
            range = minSemiEnd - this.regex.anchorDmax;
        }
        if (maxSemiEnd - start < this.regex.anchorDmin) {
            start = maxSemiEnd - this.regex.anchorDmin;
        }
        if (range > start) {
            return true;
        }
        return false;
    }

    private int match(int s) {
        return s - this.str;
    }

    private int mismatch() {
        if (this.msaBestLen >= 0) {
            int s = this.msaBestS;
            return match(s);
        }
        return -1;
    }
}
