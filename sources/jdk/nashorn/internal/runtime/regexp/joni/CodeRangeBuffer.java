package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/CodeRangeBuffer.class */
public final class CodeRangeBuffer implements Cloneable {
    private static final int INIT_MULTI_BYTE_RANGE_SIZE = 5;
    private static final int ALL_MULTI_BYTE_RANGE = Integer.MAX_VALUE;

    /* renamed from: p */
    int[] f285p;
    int used;

    public CodeRangeBuffer() {
        this.f285p = new int[5];
        writeCodePoint(0, 0);
    }

    public boolean isInCodeRange(int code) {
        int low = 0;
        int n = this.f285p[0];
        int high = n;
        while (low < high) {
            int x = (low + high) >> 1;
            if (code > this.f285p[(x << 1) + 2]) {
                low = x + 1;
            } else {
                high = x;
            }
        }
        return low < n && code >= this.f285p[(low << 1) + 1];
    }

    private CodeRangeBuffer(CodeRangeBuffer orig) {
        this.f285p = new int[orig.f285p.length];
        System.arraycopy(orig.f285p, 0, this.f285p, 0, this.f285p.length);
        this.used = orig.used;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("CodeRange");
        buf.append("\n  used: ").append(this.used);
        buf.append("\n  code point: ").append(this.f285p[0]);
        buf.append("\n  ranges: ");
        for (int i = 0; i < this.f285p[0]; i++) {
            buf.append("[").append(rangeNumToString(this.f285p[(i * 2) + 1])).append("..").append(rangeNumToString(this.f285p[(i * 2) + 2])).append("]");
            if (i > 0 && i % 6 == 0) {
                buf.append("\n          ");
            }
        }
        return buf.toString();
    }

    private static String rangeNumToString(int num) {
        return "0x" + Integer.toString(num, 16);
    }

    public void expand(int low) {
        int length = this.f285p.length;
        do {
            length <<= 1;
        } while (length < low);
        int[] tmp = new int[length];
        System.arraycopy(this.f285p, 0, tmp, 0, this.used);
        this.f285p = tmp;
    }

    public void ensureSize(int size) {
        int length;
        int length2 = this.f285p.length;
        while (true) {
            length = length2;
            if (length >= size) {
                break;
            }
            length2 = length << 1;
        }
        if (this.f285p.length != length) {
            int[] tmp = new int[length];
            System.arraycopy(this.f285p, 0, tmp, 0, this.used);
            this.f285p = tmp;
        }
    }

    private void moveRight(int from, int to, int n) {
        if (to + n > this.f285p.length) {
            expand(to + n);
        }
        System.arraycopy(this.f285p, from, this.f285p, to, n);
        if (to + n > this.used) {
            this.used = to + n;
        }
    }

    protected void moveLeft(int from, int to, int n) {
        System.arraycopy(this.f285p, from, this.f285p, to, n);
    }

    private void moveLeftAndReduce(int from, int to) {
        System.arraycopy(this.f285p, from, this.f285p, to, this.used - from);
        this.used -= from - to;
    }

    public void writeCodePoint(int pos, int b) {
        int u = pos + 1;
        if (this.f285p.length < u) {
            expand(u);
        }
        this.f285p[pos] = b;
        if (this.used < u) {
            this.used = u;
        }
    }

    public CodeRangeBuffer clone() {
        return new CodeRangeBuffer(this);
    }

    public static CodeRangeBuffer addCodeRangeToBuff(CodeRangeBuffer pbufp, int fromp, int top) {
        int from = fromp;
        int to = top;
        CodeRangeBuffer pbuf = pbufp;
        if (from > to) {
            from = to;
            to = from;
        }
        if (pbuf == null) {
            pbuf = new CodeRangeBuffer();
        }
        int[] p = pbuf.f285p;
        int n = p[0];
        int low = 0;
        int bound = n;
        while (low < bound) {
            int x = (low + bound) >>> 1;
            if (from > p[(x * 2) + 2]) {
                low = x + 1;
            } else {
                bound = x;
            }
        }
        int high = low;
        int bound2 = n;
        while (high < bound2) {
            int x2 = (high + bound2) >>> 1;
            if (to >= p[(x2 * 2) + 1] - 1) {
                high = x2 + 1;
            } else {
                bound2 = x2;
            }
        }
        int incN = (low + 1) - high;
        if (n + incN > 10000) {
            throw new ValueException(ErrorMessages.ERR_TOO_MANY_MULTI_BYTE_RANGES);
        }
        if (incN != 1) {
            if (from > p[(low * 2) + 1]) {
                from = p[(low * 2) + 1];
            }
            if (to < p[((high - 1) * 2) + 2]) {
                to = p[((high - 1) * 2) + 2];
            }
        }
        if (incN != 0 && high < n) {
            int fromPos = 1 + (high * 2);
            int toPos = 1 + ((low + 1) * 2);
            int size = (n - high) * 2;
            if (incN > 0) {
                pbuf.moveRight(fromPos, toPos, size);
            } else {
                pbuf.moveLeftAndReduce(fromPos, toPos);
            }
        }
        int pos = 1 + (low * 2);
        pbuf.writeCodePoint(pos, from);
        pbuf.writeCodePoint(pos + 1, to);
        pbuf.writeCodePoint(0, n + incN);
        return pbuf;
    }

    public static CodeRangeBuffer addCodeRange(CodeRangeBuffer pbuf, ScanEnvironment env, int from, int to) {
        if (from > to) {
            if (env.syntax.allowEmptyRangeInCC()) {
                return pbuf;
            }
            throw new ValueException(ErrorMessages.ERR_EMPTY_RANGE_IN_CHAR_CLASS);
        }
        return addCodeRangeToBuff(pbuf, from, to);
    }

    protected static CodeRangeBuffer setAllMultiByteRange(CodeRangeBuffer pbuf) {
        return addCodeRangeToBuff(pbuf, EncodingHelper.mbcodeStartPosition(), Integer.MAX_VALUE);
    }

    public static CodeRangeBuffer addAllMultiByteRange(CodeRangeBuffer pbuf) {
        return setAllMultiByteRange(pbuf);
    }

    public static CodeRangeBuffer notCodeRangeBuff(CodeRangeBuffer bbuf) {
        CodeRangeBuffer pbuf = null;
        if (bbuf == null) {
            return setAllMultiByteRange(null);
        }
        int[] p = bbuf.f285p;
        int n = p[0];
        if (n <= 0) {
            return setAllMultiByteRange(null);
        }
        int pre = EncodingHelper.mbcodeStartPosition();
        int to = 0;
        for (int i = 0; i < n; i++) {
            int from = p[(i * 2) + 1];
            to = p[(i * 2) + 2];
            if (pre <= from - 1) {
                pbuf = addCodeRangeToBuff(pbuf, pre, from - 1);
            }
            if (to == Integer.MAX_VALUE) {
                break;
            }
            pre = to + 1;
        }
        if (to < Integer.MAX_VALUE) {
            pbuf = addCodeRangeToBuff(pbuf, to + 1, Integer.MAX_VALUE);
        }
        return pbuf;
    }

    public static CodeRangeBuffer orCodeRangeBuff(CodeRangeBuffer bbuf1p, boolean not1p, CodeRangeBuffer bbuf2p, boolean not2p) {
        CodeRangeBuffer pbuf = null;
        CodeRangeBuffer bbuf1 = bbuf1p;
        CodeRangeBuffer bbuf2 = bbuf2p;
        boolean not1 = not1p;
        boolean not2 = not2p;
        if (bbuf1 == null && bbuf2 == null) {
            if (not1 || not2) {
                return setAllMultiByteRange(null);
            }
            return null;
        }
        if (bbuf2 == null) {
            not1 = not2;
            not2 = not1;
            bbuf1 = bbuf2;
            bbuf2 = bbuf1;
        }
        if (bbuf1 == null) {
            if (not1) {
                return setAllMultiByteRange(null);
            }
            if (!not2) {
                return bbuf2.clone();
            }
            return notCodeRangeBuff(bbuf2);
        }
        if (not1) {
            boolean tnot = not1;
            not1 = not2;
            not2 = tnot;
            CodeRangeBuffer tbuf = bbuf1;
            bbuf1 = bbuf2;
            bbuf2 = tbuf;
        }
        if (!not2 && !not1) {
            pbuf = bbuf2.clone();
        } else if (!not1) {
            pbuf = notCodeRangeBuff(bbuf2);
        }
        int[] p1 = bbuf1.f285p;
        int n1 = p1[0];
        for (int i = 0; i < n1; i++) {
            int from = p1[(i * 2) + 1];
            int to = p1[(i * 2) + 2];
            pbuf = addCodeRangeToBuff(pbuf, from, to);
        }
        return pbuf;
    }

    public static CodeRangeBuffer andCodeRange1(CodeRangeBuffer pbufp, int from1p, int to1p, int[] data, int n) {
        CodeRangeBuffer pbuf = pbufp;
        int from1 = from1p;
        int to1 = to1p;
        for (int i = 0; i < n; i++) {
            int from2 = data[(i * 2) + 1];
            int to2 = data[(i * 2) + 2];
            if (from2 < from1) {
                if (to2 < from1) {
                    continue;
                } else {
                    from1 = to2 + 1;
                }
            } else if (from2 <= to1) {
                if (to2 < to1) {
                    if (from1 <= from2 - 1) {
                        pbuf = addCodeRangeToBuff(pbuf, from1, from2 - 1);
                    }
                    from1 = to2 + 1;
                } else {
                    to1 = from2 - 1;
                }
            } else {
                from1 = from2;
            }
            if (from1 > to1) {
                break;
            }
        }
        if (from1 <= to1) {
            pbuf = addCodeRangeToBuff(pbuf, from1, to1);
        }
        return pbuf;
    }

    public static CodeRangeBuffer andCodeRangeBuff(CodeRangeBuffer bbuf1p, boolean not1p, CodeRangeBuffer bbuf2p, boolean not2p) {
        CodeRangeBuffer pbuf = null;
        CodeRangeBuffer bbuf1 = bbuf1p;
        CodeRangeBuffer bbuf2 = bbuf2p;
        boolean not1 = not1p;
        boolean not2 = not2p;
        if (bbuf1 == null) {
            if (not1 && bbuf2 != null) {
                return bbuf2.clone();
            }
            return null;
        } else if (bbuf2 == null) {
            if (not2) {
                return bbuf1.clone();
            }
            return null;
        } else {
            if (not1) {
                not1 = not2;
                not2 = not1;
                bbuf1 = bbuf2;
                bbuf2 = bbuf1;
            }
            int[] p1 = bbuf1.f285p;
            int n1 = p1[0];
            int[] p2 = bbuf2.f285p;
            int n2 = p2[0];
            if (!not2 && !not1) {
                for (int i = 0; i < n1; i++) {
                    int from1 = p1[(i * 2) + 1];
                    int to1 = p1[(i * 2) + 2];
                    for (int j = 0; j < n2; j++) {
                        int from2 = p2[(j * 2) + 1];
                        int to2 = p2[(j * 2) + 2];
                        if (from2 > to1) {
                            break;
                        }
                        if (to2 >= from1) {
                            int from = from1 > from2 ? from1 : from2;
                            int to = to1 < to2 ? to1 : to2;
                            pbuf = addCodeRangeToBuff(pbuf, from, to);
                        }
                    }
                }
            } else if (!not1) {
                for (int i2 = 0; i2 < n1; i2++) {
                    pbuf = andCodeRange1(pbuf, p1[(i2 * 2) + 1], p1[(i2 * 2) + 2], p2, n2);
                }
            }
            return pbuf;
        }
    }
}
