package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/ScannerSupport.class */
abstract class ScannerSupport extends IntHolder implements ErrorMessages {
    protected final char[] chars;

    /* renamed from: p */
    protected int f288p;
    protected int stop;
    private int lastFetched;

    /* renamed from: c */
    protected int f289c;
    private final int begin;
    private final int end;

    /* renamed from: _p */
    protected int f290_p;
    private static final int INT_SIGN_BIT = Integer.MIN_VALUE;

    public ScannerSupport(char[] chars, int p, int end) {
        this.chars = chars;
        this.begin = p;
        this.end = end;
        reset();
    }

    public int getBegin() {
        return this.begin;
    }

    public int getEnd() {
        return this.end;
    }

    public final int scanUnsignedNumber() {
        int onum;
        int last = this.f289c;
        int num = 0;
        do {
            if (left()) {
                fetch();
                if (Character.isDigit(this.f289c)) {
                    onum = num;
                    num = (num * 10) + EncodingHelper.digitVal(this.f289c);
                } else {
                    unfetch();
                }
            }
            this.f289c = last;
            return num;
        } while (((onum ^ num) & Integer.MIN_VALUE) == 0);
        return -1;
    }

    public final int scanUnsignedHexadecimalNumber(int maxLength) {
        int onum;
        int last = this.f289c;
        int num = 0;
        int ml = maxLength;
        do {
            if (left()) {
                int i = ml;
                ml--;
                if (i != 0) {
                    fetch();
                    if (EncodingHelper.isXDigit(this.f289c)) {
                        onum = num;
                        int val = EncodingHelper.xdigitVal(this.f289c);
                        num = (num << 4) + val;
                    } else {
                        unfetch();
                    }
                }
            }
            this.f289c = last;
            return num;
        } while (((onum ^ num) & Integer.MIN_VALUE) == 0);
        return -1;
    }

    public final int scanUnsignedOctalNumber(int maxLength) {
        int last = this.f289c;
        int num = 0;
        int ml = maxLength;
        while (left()) {
            int i = ml;
            ml--;
            if (i == 0) {
                break;
            }
            fetch();
            if (Character.isDigit(this.f289c) && this.f289c < 56) {
                int onum = num;
                int val = EncodingHelper.odigitVal(this.f289c);
                num = (num << 3) + val;
                if (((onum ^ num) & Integer.MIN_VALUE) != 0) {
                    return -1;
                }
            } else {
                unfetch();
                break;
            }
        }
        this.f289c = last;
        return num;
    }

    public final void reset() {
        this.f288p = this.begin;
        this.stop = this.end;
    }

    public final void mark() {
        this.f290_p = this.f288p;
    }

    public final void restore() {
        this.f288p = this.f290_p;
    }

    public final void inc() {
        this.lastFetched = this.f288p;
        this.f288p++;
    }

    public final void fetch() {
        this.lastFetched = this.f288p;
        char[] cArr = this.chars;
        int i = this.f288p;
        this.f288p = i + 1;
        this.f289c = cArr[i];
    }

    protected int fetchTo() {
        this.lastFetched = this.f288p;
        char[] cArr = this.chars;
        int i = this.f288p;
        this.f288p = i + 1;
        return cArr[i];
    }

    public final void unfetch() {
        this.f288p = this.lastFetched;
    }

    public final int peek() {
        if (this.f288p < this.stop) {
            return this.chars[this.f288p];
        }
        return 0;
    }

    public final boolean peekIs(int ch) {
        return peek() == ch;
    }

    public final boolean left() {
        return this.f288p < this.stop;
    }
}
