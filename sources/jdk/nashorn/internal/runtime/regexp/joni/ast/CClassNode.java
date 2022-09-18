package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.BitSet;
import jdk.nashorn.internal.runtime.regexp.joni.CodeRangeBuffer;
import jdk.nashorn.internal.runtime.regexp.joni.EncodingHelper;
import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;
import jdk.nashorn.internal.runtime.regexp.joni.Syntax;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCSTATE;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCVALTYPE;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/CClassNode.class */
public final class CClassNode extends Node {
    private static final int FLAG_NCCLASS_NOT = 1;
    private static final int FLAG_NCCLASS_SHARE = 2;
    int flags;

    /* renamed from: bs */
    public final BitSet f297bs = new BitSet();
    public CodeRangeBuffer mbuf;
    private int ctype;
    private static final short[] AsciiCtypeTable = {16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16908, 16905, 16904, 16904, 16904, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 17028, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 31906, 31906, 31906, 31906, 31906, 31906, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 16800, 16800, 16800, 16800, 20896, 16800, 30946, 30946, 30946, 30946, 30946, 30946, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 16800, 16800, 16800, 16800, 16392, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/CClassNode$CCStateArg.class */
    public static final class CCStateArg {

        /* renamed from: v */
        public int f299v;

        /* renamed from: vs */
        public int f300vs;
        public boolean vsIsRaw;
        public boolean vIsRaw;
        public CCVALTYPE inType;
        public CCVALTYPE type;
        public CCSTATE state;
    }

    public void clear() {
        this.f297bs.clear();
        this.flags = 0;
        this.mbuf = null;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public int getType() {
        return 1;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String getName() {
        return "Character Class";
    }

    public boolean equals(Object other) {
        if (!(other instanceof CClassNode)) {
            return false;
        }
        CClassNode cc = (CClassNode) other;
        return this.ctype == cc.ctype && isNot() == cc.isNot();
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String toString(int level) {
        StringBuilder value = new StringBuilder();
        value.append("\n  flags: " + flagsToString());
        value.append("\n  bs: " + pad(this.f297bs, level + 1));
        value.append("\n  mbuf: " + pad(this.mbuf, level + 1));
        return value.toString();
    }

    public String flagsToString() {
        StringBuilder f = new StringBuilder();
        if (isNot()) {
            f.append("NOT ");
        }
        if (isShare()) {
            f.append("SHARE ");
        }
        return f.toString();
    }

    public boolean isEmpty() {
        return this.mbuf == null && this.f297bs.isEmpty();
    }

    public void addCodeRangeToBuf(int from, int to) {
        this.mbuf = CodeRangeBuffer.addCodeRangeToBuff(this.mbuf, from, to);
    }

    public void addCodeRange(ScanEnvironment env, int from, int to) {
        this.mbuf = CodeRangeBuffer.addCodeRange(this.mbuf, env, from, to);
    }

    public void addAllMultiByteRange() {
        this.mbuf = CodeRangeBuffer.addAllMultiByteRange(this.mbuf);
    }

    public void clearNotFlag() {
        if (isNot()) {
            this.f297bs.invert();
            this.mbuf = CodeRangeBuffer.notCodeRangeBuff(this.mbuf);
            clearNot();
        }
    }

    public void and(CClassNode other) {
        CodeRangeBuffer pbuf;
        boolean not1 = isNot();
        BitSet bsr1 = this.f297bs;
        CodeRangeBuffer buf1 = this.mbuf;
        boolean not2 = other.isNot();
        BitSet bsr2 = other.f297bs;
        CodeRangeBuffer buf2 = other.mbuf;
        if (not1) {
            BitSet bs1 = new BitSet();
            bsr1.invertTo(bs1);
            bsr1 = bs1;
        }
        if (not2) {
            BitSet bs2 = new BitSet();
            bsr2.invertTo(bs2);
            bsr2 = bs2;
        }
        bsr1.and(bsr2);
        if (bsr1 != this.f297bs) {
            this.f297bs.copy(bsr1);
            BitSet bitSet = this.f297bs;
        }
        if (not1) {
            this.f297bs.invert();
        }
        if (not1 && not2) {
            pbuf = CodeRangeBuffer.orCodeRangeBuff(buf1, false, buf2, false);
        } else {
            pbuf = CodeRangeBuffer.andCodeRangeBuff(buf1, not1, buf2, not2);
            if (not1) {
                pbuf = CodeRangeBuffer.notCodeRangeBuff(pbuf);
            }
        }
        this.mbuf = pbuf;
    }

    /* renamed from: or */
    public void m56or(CClassNode other) {
        CodeRangeBuffer pbuf;
        boolean not1 = isNot();
        BitSet bsr1 = this.f297bs;
        CodeRangeBuffer buf1 = this.mbuf;
        boolean not2 = other.isNot();
        BitSet bsr2 = other.f297bs;
        CodeRangeBuffer buf2 = other.mbuf;
        if (not1) {
            BitSet bs1 = new BitSet();
            bsr1.invertTo(bs1);
            bsr1 = bs1;
        }
        if (not2) {
            BitSet bs2 = new BitSet();
            bsr2.invertTo(bs2);
            bsr2 = bs2;
        }
        bsr1.m57or(bsr2);
        if (bsr1 != this.f297bs) {
            this.f297bs.copy(bsr1);
            BitSet bitSet = this.f297bs;
        }
        if (not1) {
            this.f297bs.invert();
        }
        if (not1 && not2) {
            pbuf = CodeRangeBuffer.andCodeRangeBuff(buf1, false, buf2, false);
        } else {
            pbuf = CodeRangeBuffer.orCodeRangeBuff(buf1, not1, buf2, not2);
            if (not1) {
                pbuf = CodeRangeBuffer.notCodeRangeBuff(pbuf);
            }
        }
        this.mbuf = pbuf;
    }

    public void addCTypeByRange(int ct, boolean not, int sbOut, int[] mbr) {
        int n = mbr[0];
        if (!not) {
            int i = 0;
            while (i < n) {
                for (int j = mbr[(i * 2) + 1]; j <= mbr[(i * 2) + 2]; j++) {
                    if (j >= sbOut) {
                        if (j >= mbr[(i * 2) + 1]) {
                            addCodeRangeToBuf(j, mbr[(i * 2) + 2]);
                            i++;
                        }
                        while (i < n) {
                            addCodeRangeToBuf(mbr[(2 * i) + 1], mbr[(2 * i) + 2]);
                            i++;
                        }
                        return;
                    }
                    this.f297bs.set(j);
                }
                i++;
            }
            for (int i2 = 0; i2 < n; i2++) {
                addCodeRangeToBuf(mbr[(2 * i2) + 1], mbr[(2 * i2) + 2]);
            }
            return;
        }
        int prev = 0;
        for (int i3 = 0; i3 < n; i3++) {
            for (int j2 = prev; j2 < mbr[(2 * i3) + 1]; j2++) {
                if (j2 >= sbOut) {
                    int prev2 = sbOut;
                    for (int i4 = 0; i4 < n; i4++) {
                        if (prev2 < mbr[(2 * i4) + 1]) {
                            addCodeRangeToBuf(prev2, mbr[(i4 * 2) + 1] - 1);
                        }
                        prev2 = mbr[(i4 * 2) + 2] + 1;
                    }
                    if (prev2 < Integer.MAX_VALUE) {
                        addCodeRangeToBuf(prev2, Integer.MAX_VALUE);
                        return;
                    }
                    return;
                }
                this.f297bs.set(j2);
            }
            prev = mbr[(2 * i3) + 2] + 1;
        }
        for (int j3 = prev; j3 < sbOut; j3++) {
            this.f297bs.set(j3);
        }
        int prev3 = sbOut;
        for (int i5 = 0; i5 < n; i5++) {
            if (prev3 < mbr[(2 * i5) + 1]) {
                addCodeRangeToBuf(prev3, mbr[(i5 * 2) + 1] - 1);
            }
            prev3 = mbr[(i5 * 2) + 2] + 1;
        }
        if (prev3 < Integer.MAX_VALUE) {
            addCodeRangeToBuf(prev3, Integer.MAX_VALUE);
        }
    }

    public void addCType(int ctp, boolean not, ScanEnvironment env, IntHolder sbOut) {
        int ct = ctp;
        switch (ct) {
            case CharacterType.f309D /* 260 */:
            case CharacterType.f308S /* 265 */:
            case CharacterType.f310W /* 268 */:
                ct ^= 256;
                if (env.syntax != Syntax.JAVASCRIPT || ct != 9) {
                    if (not) {
                        for (int c = 0; c < 256; c++) {
                            if ((AsciiCtypeTable[c] & (1 << ct)) == 0) {
                                this.f297bs.set(c);
                            }
                        }
                        addAllMultiByteRange();
                        return;
                    }
                    for (int c2 = 0; c2 < 256; c2++) {
                        if ((AsciiCtypeTable[c2] & (1 << ct)) != 0) {
                            this.f297bs.set(c2);
                        }
                    }
                    return;
                }
                break;
        }
        int[] ranges = EncodingHelper.ctypeCodeRange(ct, sbOut);
        if (ranges != null) {
            addCTypeByRange(ct, not, sbOut.value, ranges);
            return;
        }
        switch (ct) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                if (not) {
                    for (int c3 = 0; c3 < 256; c3++) {
                        if (!EncodingHelper.isCodeCType(c3, ct)) {
                            this.f297bs.set(c3);
                        }
                    }
                    addAllMultiByteRange();
                    return;
                }
                for (int c4 = 0; c4 < 256; c4++) {
                    if (EncodingHelper.isCodeCType(c4, ct)) {
                        this.f297bs.set(c4);
                    }
                }
                return;
            case 5:
            case 7:
                if (not) {
                    for (int c5 = 0; c5 < 256; c5++) {
                        if (!EncodingHelper.isCodeCType(c5, ct)) {
                            this.f297bs.set(c5);
                        }
                    }
                    return;
                }
                for (int c6 = 0; c6 < 256; c6++) {
                    if (EncodingHelper.isCodeCType(c6, ct)) {
                        this.f297bs.set(c6);
                    }
                }
                addAllMultiByteRange();
                return;
            case 12:
                if (!not) {
                    for (int c7 = 0; c7 < 256; c7++) {
                        if (EncodingHelper.isWord(c7)) {
                            this.f297bs.set(c7);
                        }
                    }
                    addAllMultiByteRange();
                    return;
                }
                for (int c8 = 0; c8 < 256; c8++) {
                    if (!EncodingHelper.isWord(c8)) {
                        this.f297bs.set(c8);
                    }
                }
                return;
            default:
                throw new InternalException(ErrorMessages.ERR_PARSER_BUG);
        }
    }

    public void nextStateClass(CCStateArg arg, ScanEnvironment env) {
        if (arg.state == CCSTATE.RANGE) {
            throw new SyntaxException(ErrorMessages.ERR_CHAR_CLASS_VALUE_AT_END_OF_RANGE);
        }
        if (arg.state == CCSTATE.VALUE && arg.type != CCVALTYPE.CLASS) {
            if (arg.type == CCVALTYPE.SB) {
                this.f297bs.set(arg.f300vs);
            } else if (arg.type == CCVALTYPE.CODE_POINT) {
                addCodeRange(env, arg.f300vs, arg.f300vs);
            }
        }
        arg.state = CCSTATE.VALUE;
        arg.type = CCVALTYPE.CLASS;
    }

    public void nextStateValue(CCStateArg arg, ScanEnvironment env) {
        switch (arg.state) {
            case VALUE:
                if (arg.type == CCVALTYPE.SB) {
                    if (arg.f300vs > 255) {
                        throw new ValueException(ErrorMessages.ERR_INVALID_CODE_POINT_VALUE);
                    }
                    this.f297bs.set(arg.f300vs);
                    break;
                } else if (arg.type == CCVALTYPE.CODE_POINT) {
                    addCodeRange(env, arg.f300vs, arg.f300vs);
                    break;
                }
                break;
            case RANGE:
                if (arg.inType == arg.type) {
                    if (arg.inType == CCVALTYPE.SB) {
                        if (arg.f300vs > 255 || arg.f299v > 255) {
                            throw new ValueException(ErrorMessages.ERR_INVALID_CODE_POINT_VALUE);
                        }
                        if (arg.f300vs > arg.f299v) {
                            if (env.syntax.allowEmptyRangeInCC()) {
                                arg.state = CCSTATE.COMPLETE;
                                break;
                            } else {
                                throw new ValueException(ErrorMessages.ERR_EMPTY_RANGE_IN_CHAR_CLASS);
                            }
                        } else {
                            this.f297bs.setRange(arg.f300vs, arg.f299v);
                        }
                    } else {
                        addCodeRange(env, arg.f300vs, arg.f299v);
                    }
                    arg.state = CCSTATE.COMPLETE;
                    break;
                } else if (arg.f300vs > arg.f299v) {
                    if (env.syntax.allowEmptyRangeInCC()) {
                        arg.state = CCSTATE.COMPLETE;
                        break;
                    } else {
                        throw new ValueException(ErrorMessages.ERR_EMPTY_RANGE_IN_CHAR_CLASS);
                    }
                } else {
                    this.f297bs.setRange(arg.f300vs, arg.f299v < 255 ? arg.f299v : 255);
                    addCodeRange(env, arg.f300vs, arg.f299v);
                    arg.state = CCSTATE.COMPLETE;
                }
                break;
            case COMPLETE:
            case START:
                arg.state = CCSTATE.VALUE;
                break;
        }
        arg.vsIsRaw = arg.vIsRaw;
        arg.f300vs = arg.f299v;
        arg.type = arg.inType;
    }

    public boolean isCodeInCCLength(int code) {
        boolean found;
        if (code > 255) {
            found = this.mbuf != null && this.mbuf.isInCodeRange(code);
        } else {
            found = this.f297bs.m58at(code);
        }
        if (isNot()) {
            return !found;
        }
        return found;
    }

    public boolean isCodeInCC(int code) {
        return isCodeInCCLength(code);
    }

    public void setNot() {
        this.flags |= 1;
    }

    public void clearNot() {
        this.flags &= -2;
    }

    public boolean isNot() {
        return (this.flags & 1) != 0;
    }

    public void setShare() {
        this.flags |= 2;
    }

    public void clearShare() {
        this.flags &= -3;
    }

    public boolean isShare() {
        return (this.flags & 2) != 0;
    }
}
