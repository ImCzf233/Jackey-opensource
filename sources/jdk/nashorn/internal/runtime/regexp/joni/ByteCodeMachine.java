package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/ByteCodeMachine.class */
class ByteCodeMachine extends StackMachine {
    private int bestLen;

    /* renamed from: s */
    private int f283s = 0;
    private int range;
    private int sprev;
    private int sstart;
    private int sbegin;
    private final int[] code;

    /* renamed from: ip */
    private int f284ip;

    public ByteCodeMachine(Regex regex, char[] chars, int p, int end) {
        super(regex, chars, p, end);
        this.code = regex.code;
    }

    private boolean stringCmpIC(int caseFlodFlag, int s1p, IntHolder ps2, int mbLen, int textEnd) {
        int s1 = s1p;
        int s2 = ps2.value;
        int end1 = s1 + mbLen;
        while (s1 < end1) {
            int i = s1;
            s1++;
            char c1 = EncodingHelper.toLowerCase(this.chars[i]);
            int i2 = s2;
            s2++;
            char c2 = EncodingHelper.toLowerCase(this.chars[i2]);
            if (c1 != c2) {
                return false;
            }
        }
        ps2.value = s2;
        return true;
    }

    private void debugMatchBegin() {
        Config.log.println("match_at: str: " + this.str + ", end: " + this.end + ", start: " + this.sstart + ", sprev: " + this.sprev);
        Config.log.println("size: " + (this.end - this.str) + ", start offset: " + (this.sstart - this.str));
    }

    private void debugMatchLoop() {
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Matcher
    protected final int matchAt(int r, int ss, int sp) {
        this.range = r;
        this.sstart = ss;
        this.sprev = sp;
        this.stk = 0;
        this.f284ip = 0;
        init();
        this.bestLen = -1;
        this.f283s = ss;
        int[] c = this.code;
        while (true) {
            this.sbegin = this.f283s;
            int i = this.f284ip;
            this.f284ip = i + 1;
            switch (c[i]) {
                case 0:
                    return finish();
                case 1:
                    if (!opEnd()) {
                        break;
                    } else {
                        return finish();
                    }
                case 2:
                    opExact1();
                    break;
                case 3:
                    opExact2();
                    break;
                case 4:
                    opExact3();
                    break;
                case 5:
                    opExact4();
                    break;
                case 6:
                    opExact5();
                    break;
                case 7:
                    opExactN();
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 69:
                default:
                    throw new InternalException(ErrorMessages.ERR_UNDEFINED_BYTECODE);
                case 14:
                    opExact1IC();
                    break;
                case 15:
                    opExactNIC();
                    break;
                case 16:
                    opCClass();
                    break;
                case 17:
                    opCClassMB();
                    break;
                case 18:
                    opCClassMIX();
                    break;
                case 19:
                    opCClassNot();
                    break;
                case 20:
                    opCClassMBNot();
                    break;
                case 21:
                    opCClassMIXNot();
                    break;
                case 22:
                    opCClassNode();
                    break;
                case 23:
                    opAnyChar();
                    break;
                case 24:
                    opAnyCharML();
                    break;
                case 25:
                    opAnyCharStar();
                    break;
                case 26:
                    opAnyCharMLStar();
                    break;
                case 27:
                    opAnyCharStarPeekNext();
                    break;
                case 28:
                    opAnyCharMLStarPeekNext();
                    break;
                case 29:
                    opWord();
                    break;
                case 30:
                    opNotWord();
                    break;
                case 31:
                    opWordBound();
                    break;
                case 32:
                    opNotWordBound();
                    break;
                case 33:
                    opWordBegin();
                    break;
                case 34:
                    opWordEnd();
                    break;
                case 35:
                    opBeginBuf();
                    break;
                case 36:
                    opEndBuf();
                    break;
                case 37:
                    opBeginLine();
                    break;
                case 38:
                    opEndLine();
                    break;
                case 39:
                    opSemiEndBuf();
                    break;
                case 40:
                    opBeginPosition();
                    break;
                case 41:
                    opBackRef1();
                    break;
                case 42:
                    opBackRef2();
                    break;
                case 43:
                    opBackRefN();
                    break;
                case 44:
                    opBackRefNIC();
                    break;
                case 45:
                    opBackRefMulti();
                    break;
                case 46:
                    opBackRefMultiIC();
                    break;
                case 47:
                    opBackRefAtLevel();
                    break;
                case 48:
                    opMemoryStart();
                    break;
                case 49:
                    opMemoryStartPush();
                    break;
                case 50:
                    opMemoryEndPush();
                    break;
                case 51:
                    opMemoryEndPushRec();
                    break;
                case 52:
                    opMemoryEnd();
                    break;
                case 53:
                    opMemoryEndRec();
                    break;
                case 54:
                    opFail();
                    break;
                case 55:
                    opJump();
                    break;
                case 56:
                    opPush();
                    break;
                case 57:
                    opPop();
                    break;
                case 58:
                    opPushOrJumpExact1();
                    break;
                case 59:
                    opPushIfPeekNext();
                    break;
                case 60:
                    opRepeat();
                    break;
                case 61:
                    opRepeatNG();
                    break;
                case 62:
                    opRepeatInc();
                    break;
                case 63:
                    opRepeatIncNG();
                    break;
                case 64:
                    opRepeatIncSG();
                    break;
                case 65:
                    opRepeatIncNGSG();
                    break;
                case 66:
                    opNullCheckStart();
                    break;
                case 67:
                    opNullCheckEnd();
                    break;
                case 68:
                    opNullCheckEndMemST();
                    break;
                case 70:
                    opPushPos();
                    break;
                case 71:
                    opPopPos();
                    break;
                case 72:
                    opPushPosNot();
                    break;
                case 73:
                    opFailPos();
                    break;
                case 74:
                    opPushStopBT();
                    break;
                case 75:
                    opPopStopBT();
                    break;
                case 76:
                    opLookBehind();
                    break;
                case 77:
                    opPushLookBehindNot();
                    break;
                case 78:
                    opFailLookBehindNot();
                    break;
            }
        }
    }

    private boolean opEnd() {
        int n = this.f283s - this.sstart;
        if (n > this.bestLen) {
            if (Option.isFindLongest(this.regex.options)) {
                if (n > this.msaBestLen) {
                    this.msaBestLen = n;
                    this.msaBestS = this.sstart;
                } else {
                    return endBestLength();
                }
            }
            this.bestLen = n;
            Region region = this.msaRegion;
            if (region != null) {
                int[] iArr = region.beg;
                int i = this.sstart - this.str;
                this.msaBegin = i;
                iArr[0] = i;
                int[] iArr2 = region.end;
                int i2 = this.f283s - this.str;
                this.msaEnd = i2;
                iArr2[0] = i2;
                for (int i3 = 1; i3 <= this.regex.numMem; i3++) {
                    if (this.repeatStk[this.memEndStk + i3] != -1) {
                        region.beg[i3] = BitStatus.bsAt(this.regex.btMemStart, i3) ? this.stack[this.repeatStk[this.memStartStk + i3]].getMemPStr() - this.str : this.repeatStk[this.memStartStk + i3] - this.str;
                        region.end[i3] = BitStatus.bsAt(this.regex.btMemEnd, i3) ? this.stack[this.repeatStk[this.memEndStk + i3]].getMemPStr() : this.repeatStk[this.memEndStk + i3] - this.str;
                    } else {
                        region.end[i3] = -1;
                        region.beg[i3] = -1;
                    }
                }
            } else {
                this.msaBegin = this.sstart - this.str;
                this.msaEnd = this.f283s - this.str;
            }
        } else {
            Region region2 = this.msaRegion;
            if (region2 != null) {
                region2.clear();
            } else {
                this.msaEnd = 0;
                this.msaBegin = 0;
            }
        }
        return endBestLength();
    }

    private boolean endBestLength() {
        if (Option.isFindCondition(this.regex.options)) {
            if (Option.isFindNotEmpty(this.regex.options) && this.f283s == this.sstart) {
                this.bestLen = -1;
                opFail();
                return false;
            } else if (Option.isFindLongest(this.regex.options) && this.f283s < this.range) {
                opFail();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    private void opExact1() {
        if (this.f283s < this.range) {
            int i = this.code[this.f284ip];
            char[] cArr = this.chars;
            int i2 = this.f283s;
            this.f283s = i2 + 1;
            if (i == cArr[i2]) {
                this.f284ip++;
                this.sprev = this.sbegin;
                return;
            }
        }
        opFail();
    }

    private void opExact2() {
        if (this.f283s + 2 > this.range) {
            opFail();
        } else if (this.code[this.f284ip] != this.chars[this.f283s]) {
            opFail();
        } else {
            this.f284ip++;
            this.f283s++;
            if (this.code[this.f284ip] != this.chars[this.f283s]) {
                opFail();
                return;
            }
            this.sprev = this.f283s;
            this.f284ip++;
            this.f283s++;
        }
    }

    private void opExact3() {
        if (this.f283s + 3 > this.range) {
            opFail();
        } else if (this.code[this.f284ip] != this.chars[this.f283s]) {
            opFail();
        } else {
            this.f284ip++;
            this.f283s++;
            if (this.code[this.f284ip] != this.chars[this.f283s]) {
                opFail();
                return;
            }
            this.f284ip++;
            this.f283s++;
            if (this.code[this.f284ip] != this.chars[this.f283s]) {
                opFail();
                return;
            }
            this.sprev = this.f283s;
            this.f284ip++;
            this.f283s++;
        }
    }

    private void opExact4() {
        if (this.f283s + 4 > this.range) {
            opFail();
        } else if (this.code[this.f284ip] != this.chars[this.f283s]) {
            opFail();
        } else {
            this.f284ip++;
            this.f283s++;
            if (this.code[this.f284ip] != this.chars[this.f283s]) {
                opFail();
                return;
            }
            this.f284ip++;
            this.f283s++;
            if (this.code[this.f284ip] != this.chars[this.f283s]) {
                opFail();
                return;
            }
            this.f284ip++;
            this.f283s++;
            if (this.code[this.f284ip] != this.chars[this.f283s]) {
                opFail();
                return;
            }
            this.sprev = this.f283s;
            this.f284ip++;
            this.f283s++;
        }
    }

    private void opExact5() {
        if (this.f283s + 5 > this.range) {
            opFail();
        } else if (this.code[this.f284ip] != this.chars[this.f283s]) {
            opFail();
        } else {
            this.f284ip++;
            this.f283s++;
            if (this.code[this.f284ip] != this.chars[this.f283s]) {
                opFail();
                return;
            }
            this.f284ip++;
            this.f283s++;
            if (this.code[this.f284ip] != this.chars[this.f283s]) {
                opFail();
                return;
            }
            this.f284ip++;
            this.f283s++;
            if (this.code[this.f284ip] != this.chars[this.f283s]) {
                opFail();
                return;
            }
            this.f284ip++;
            this.f283s++;
            if (this.code[this.f284ip] != this.chars[this.f283s]) {
                opFail();
                return;
            }
            this.sprev = this.f283s;
            this.f284ip++;
            this.f283s++;
        }
    }

    private void opExactN() {
        char c;
        char[] cArr;
        int i;
        int[] iArr = this.code;
        int i2 = this.f284ip;
        this.f284ip = i2 + 1;
        int tlen = iArr[i2];
        if (this.f283s + tlen > this.range) {
            opFail();
            return;
        }
        char[][] cArr2 = this.regex.templates;
        int[] iArr2 = this.code;
        int i3 = this.f284ip;
        this.f284ip = i3 + 1;
        char[] bs = cArr2[iArr2[i3]];
        int[] iArr3 = this.code;
        int i4 = this.f284ip;
        this.f284ip = i4 + 1;
        int ps = iArr3[i4];
        do {
            int i5 = tlen;
            tlen--;
            if (i5 > 0) {
                int i6 = ps;
                ps++;
                c = bs[i6];
                cArr = this.chars;
                i = this.f283s;
                this.f283s = i + 1;
            } else {
                this.sprev = this.f283s - 1;
                return;
            }
        } while (c == cArr[i]);
        opFail();
    }

    private void opExact1IC() {
        if (this.f283s < this.range) {
            int i = this.code[this.f284ip];
            char[] cArr = this.chars;
            int i2 = this.f283s;
            this.f283s = i2 + 1;
            if (i == EncodingHelper.toLowerCase(cArr[i2])) {
                this.f284ip++;
                this.sprev = this.sbegin;
                return;
            }
        }
        opFail();
    }

    private void opExactNIC() {
        char c;
        char[] cArr;
        int i;
        int[] iArr = this.code;
        int i2 = this.f284ip;
        this.f284ip = i2 + 1;
        int tlen = iArr[i2];
        if (this.f283s + tlen > this.range) {
            opFail();
            return;
        }
        char[][] cArr2 = this.regex.templates;
        int[] iArr2 = this.code;
        int i3 = this.f284ip;
        this.f284ip = i3 + 1;
        char[] bs = cArr2[iArr2[i3]];
        int[] iArr3 = this.code;
        int i4 = this.f284ip;
        this.f284ip = i4 + 1;
        int ps = iArr3[i4];
        do {
            int i5 = tlen;
            tlen--;
            if (i5 > 0) {
                int i6 = ps;
                ps++;
                c = bs[i6];
                cArr = this.chars;
                i = this.f283s;
                this.f283s = i + 1;
            } else {
                this.sprev = this.f283s - 1;
                return;
            }
        } while (c == EncodingHelper.toLowerCase(cArr[i]));
        opFail();
    }

    private boolean isInBitSet() {
        char c = this.chars[this.f283s];
        return c <= 255 && (this.code[this.f284ip + (c >>> BitSet.ROOM_SHIFT)] & (1 << c)) != 0;
    }

    private void opCClass() {
        if (this.f283s >= this.range || !isInBitSet()) {
            opFail();
            return;
        }
        this.f284ip += 8;
        this.f283s++;
        this.sprev = this.sbegin;
    }

    private boolean isInClassMB() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int tlen = iArr[i];
        if (this.f283s >= this.range) {
            return false;
        }
        int ss = this.f283s;
        this.f283s++;
        if (!EncodingHelper.isInCodeRange(this.code, this.f284ip, this.chars[ss])) {
            return false;
        }
        this.f284ip += tlen;
        return true;
    }

    private void opCClassMB() {
        if (this.f283s >= this.range || this.chars[this.f283s] <= 255) {
            opFail();
        } else if (!isInClassMB()) {
            opFail();
        } else {
            this.sprev = this.sbegin;
        }
    }

    private void opCClassMIX() {
        if (this.f283s >= this.range) {
            opFail();
            return;
        }
        if (this.chars[this.f283s] > 255) {
            this.f284ip += 8;
            if (!isInClassMB()) {
                opFail();
                return;
            }
        } else if (!isInBitSet()) {
            opFail();
            return;
        } else {
            this.f284ip += 8;
            int[] iArr = this.code;
            int i = this.f284ip;
            this.f284ip = i + 1;
            int tlen = iArr[i];
            this.f284ip += tlen;
            this.f283s++;
        }
        this.sprev = this.sbegin;
    }

    private void opCClassNot() {
        if (this.f283s >= this.range || isInBitSet()) {
            opFail();
            return;
        }
        this.f284ip += 8;
        this.f283s++;
        this.sprev = this.sbegin;
    }

    private boolean isNotInClassMB() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int tlen = iArr[i];
        if (this.f283s + 1 > this.range) {
            if (this.f283s >= this.range) {
                return false;
            }
            this.f283s = this.end;
            this.f284ip += tlen;
            return true;
        }
        int ss = this.f283s;
        this.f283s++;
        if (EncodingHelper.isInCodeRange(this.code, this.f284ip, this.chars[ss])) {
            return false;
        }
        this.f284ip += tlen;
        return true;
    }

    private void opCClassMBNot() {
        if (this.f283s >= this.range) {
            opFail();
        } else if (this.chars[this.f283s] <= 255) {
            this.f283s++;
            int[] iArr = this.code;
            int i = this.f284ip;
            this.f284ip = i + 1;
            int tlen = iArr[i];
            this.f284ip += tlen;
            this.sprev = this.sbegin;
        } else if (!isNotInClassMB()) {
            opFail();
        } else {
            this.sprev = this.sbegin;
        }
    }

    private void opCClassMIXNot() {
        if (this.f283s >= this.range) {
            opFail();
            return;
        }
        if (this.chars[this.f283s] > 255) {
            this.f284ip += 8;
            if (!isNotInClassMB()) {
                opFail();
                return;
            }
        } else if (isInBitSet()) {
            opFail();
            return;
        } else {
            this.f284ip += 8;
            int[] iArr = this.code;
            int i = this.f284ip;
            this.f284ip = i + 1;
            int tlen = iArr[i];
            this.f284ip += tlen;
            this.f283s++;
        }
        this.sprev = this.sbegin;
    }

    private void opCClassNode() {
        if (this.f283s >= this.range) {
            opFail();
            return;
        }
        Object[] objArr = this.regex.operands;
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        CClassNode cc = (CClassNode) objArr[iArr[i]];
        int ss = this.f283s;
        this.f283s++;
        if (!cc.isCodeInCCLength(this.chars[ss])) {
            opFail();
        } else {
            this.sprev = this.sbegin;
        }
    }

    private void opAnyChar() {
        if (this.f283s >= this.range) {
            opFail();
        } else if (EncodingHelper.isNewLine(this.chars[this.f283s])) {
            opFail();
        } else {
            this.f283s++;
            this.sprev = this.sbegin;
        }
    }

    private void opAnyCharML() {
        if (this.f283s >= this.range) {
            opFail();
            return;
        }
        this.f283s++;
        this.sprev = this.sbegin;
    }

    private void opAnyCharStar() {
        char[] ch = this.chars;
        while (this.f283s < this.range) {
            pushAlt(this.f284ip, this.f283s, this.sprev);
            if (EncodingHelper.isNewLine(ch, this.f283s, this.end)) {
                opFail();
                return;
            } else {
                this.sprev = this.f283s;
                this.f283s++;
            }
        }
        this.sprev = this.sbegin;
    }

    private void opAnyCharMLStar() {
        while (this.f283s < this.range) {
            pushAlt(this.f284ip, this.f283s, this.sprev);
            this.sprev = this.f283s;
            this.f283s++;
        }
        this.sprev = this.sbegin;
    }

    private void opAnyCharStarPeekNext() {
        char c = (char) this.code[this.f284ip];
        char[] ch = this.chars;
        while (this.f283s < this.range) {
            char b = ch[this.f283s];
            if (c == b) {
                pushAlt(this.f284ip + 1, this.f283s, this.sprev);
            }
            if (EncodingHelper.isNewLine(b)) {
                opFail();
                return;
            } else {
                this.sprev = this.f283s;
                this.f283s++;
            }
        }
        this.f284ip++;
        this.sprev = this.sbegin;
    }

    private void opAnyCharMLStarPeekNext() {
        char c = (char) this.code[this.f284ip];
        char[] ch = this.chars;
        while (this.f283s < this.range) {
            if (c == ch[this.f283s]) {
                pushAlt(this.f284ip + 1, this.f283s, this.sprev);
            }
            this.sprev = this.f283s;
            this.f283s++;
        }
        this.f284ip++;
        this.sprev = this.sbegin;
    }

    private void opWord() {
        if (this.f283s >= this.range || !EncodingHelper.isWord(this.chars[this.f283s])) {
            opFail();
            return;
        }
        this.f283s++;
        this.sprev = this.sbegin;
    }

    private void opNotWord() {
        if (this.f283s >= this.range || EncodingHelper.isWord(this.chars[this.f283s])) {
            opFail();
            return;
        }
        this.f283s++;
        this.sprev = this.sbegin;
    }

    private void opWordBound() {
        if (this.f283s == this.str) {
            if (this.f283s < this.range && EncodingHelper.isWord(this.chars[this.f283s])) {
                return;
            }
            opFail();
        } else if (this.f283s == this.end) {
            if (this.sprev < this.end && EncodingHelper.isWord(this.chars[this.sprev])) {
                return;
            }
            opFail();
        } else if (EncodingHelper.isWord(this.chars[this.f283s]) != EncodingHelper.isWord(this.chars[this.sprev])) {
        } else {
            opFail();
        }
    }

    private void opNotWordBound() {
        if (this.f283s == this.str) {
            if (this.f283s >= this.range || !EncodingHelper.isWord(this.chars[this.f283s])) {
                return;
            }
            opFail();
        } else if (this.f283s == this.end) {
            if (this.sprev >= this.end || !EncodingHelper.isWord(this.chars[this.sprev])) {
                return;
            }
            opFail();
        } else if (EncodingHelper.isWord(this.chars[this.f283s]) == EncodingHelper.isWord(this.chars[this.sprev])) {
        } else {
            opFail();
        }
    }

    private void opWordBegin() {
        if (this.f283s < this.range && EncodingHelper.isWord(this.chars[this.f283s]) && (this.f283s == this.str || !EncodingHelper.isWord(this.chars[this.sprev]))) {
            return;
        }
        opFail();
    }

    private void opWordEnd() {
        if (this.f283s != this.str && EncodingHelper.isWord(this.chars[this.sprev]) && (this.f283s == this.end || !EncodingHelper.isWord(this.chars[this.f283s]))) {
            return;
        }
        opFail();
    }

    private void opBeginBuf() {
        if (this.f283s != this.str) {
            opFail();
        }
    }

    private void opEndBuf() {
        if (this.f283s != this.end) {
            opFail();
        }
    }

    private void opBeginLine() {
        if (this.f283s == this.str) {
            if (Option.isNotBol(this.msaOptions)) {
                opFail();
            }
        } else if (EncodingHelper.isNewLine(this.chars, this.sprev, this.end) && this.f283s != this.end) {
        } else {
            opFail();
        }
    }

    private void opEndLine() {
        if (this.f283s == this.end) {
            if ((this.str == this.end || !EncodingHelper.isNewLine(this.chars, this.sprev, this.end)) && Option.isNotEol(this.msaOptions)) {
                opFail();
            }
        } else if (EncodingHelper.isNewLine(this.chars, this.f283s, this.end)) {
        } else {
            opFail();
        }
    }

    private void opSemiEndBuf() {
        if (this.f283s == this.end) {
            if ((this.str == this.end || !EncodingHelper.isNewLine(this.chars, this.sprev, this.end)) && Option.isNotEol(this.msaOptions)) {
                opFail();
            }
        } else if (EncodingHelper.isNewLine(this.chars, this.f283s, this.end) && this.f283s + 1 == this.end) {
        } else {
            opFail();
        }
    }

    private void opBeginPosition() {
        if (this.f283s != this.msaStart) {
            opFail();
        }
    }

    private void opMemoryStartPush() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        pushMemStart(mem, this.f283s);
    }

    private void opMemoryStart() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        this.repeatStk[this.memStartStk + mem] = this.f283s;
    }

    private void opMemoryEndPush() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        pushMemEnd(mem, this.f283s);
    }

    private void opMemoryEnd() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        this.repeatStk[this.memEndStk + mem] = this.f283s;
    }

    private void opMemoryEndPushRec() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        int stkp = getMemStart(mem);
        pushMemEnd(mem, this.f283s);
        this.repeatStk[this.memStartStk + mem] = stkp;
    }

    private void opMemoryEndRec() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        this.repeatStk[this.memEndStk + mem] = this.f283s;
        int stkp = getMemStart(mem);
        if (BitStatus.bsAt(this.regex.btMemStart, mem)) {
            this.repeatStk[this.memStartStk + mem] = stkp;
        } else {
            this.repeatStk[this.memStartStk + mem] = this.stack[stkp].getMemPStr();
        }
        pushMemEndMark(mem);
    }

    private boolean backrefInvalid(int mem) {
        return this.repeatStk[this.memEndStk + mem] == -1 || this.repeatStk[this.memStartStk + mem] == -1;
    }

    private int backrefStart(int mem) {
        return BitStatus.bsAt(this.regex.btMemStart, mem) ? this.stack[this.repeatStk[this.memStartStk + mem]].getMemPStr() : this.repeatStk[this.memStartStk + mem];
    }

    private int backrefEnd(int mem) {
        return BitStatus.bsAt(this.regex.btMemEnd, mem) ? this.stack[this.repeatStk[this.memEndStk + mem]].getMemPStr() : this.repeatStk[this.memEndStk + mem];
    }

    private void backref(int mem) {
        char c;
        char[] cArr;
        int i;
        if (mem > this.regex.numMem || backrefInvalid(mem)) {
            opFail();
            return;
        }
        int pstart = backrefStart(mem);
        int pend = backrefEnd(mem);
        int n = pend - pstart;
        if (this.f283s + n > this.range) {
            opFail();
            return;
        }
        this.sprev = this.f283s;
        do {
            int i2 = n;
            n--;
            if (i2 > 0) {
                int i3 = pstart;
                pstart++;
                c = this.chars[i3];
                cArr = this.chars;
                i = this.f283s;
                this.f283s = i + 1;
            } else if (this.sprev < this.range) {
                while (this.sprev + 1 < this.f283s) {
                    this.sprev++;
                }
                return;
            } else {
                return;
            }
        } while (c == cArr[i]);
        opFail();
    }

    private void opBackRef1() {
        backref(1);
    }

    private void opBackRef2() {
        backref(2);
    }

    private void opBackRefN() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        backref(iArr[i]);
    }

    private void opBackRefNIC() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        if (mem > this.regex.numMem || backrefInvalid(mem)) {
            opFail();
            return;
        }
        int pstart = backrefStart(mem);
        int pend = backrefEnd(mem);
        int n = pend - pstart;
        if (this.f283s + n > this.range) {
            opFail();
            return;
        }
        this.sprev = this.f283s;
        this.value = this.f283s;
        if (!stringCmpIC(this.regex.caseFoldFlag, pstart, this, n, this.end)) {
            opFail();
            return;
        }
        this.f283s = this.value;
        while (this.sprev + 1 < this.f283s) {
            this.sprev++;
        }
    }

    private void opBackRefMulti() {
        int i;
        int i2;
        int[] iArr = this.code;
        int i3 = this.f284ip;
        this.f284ip = i3 + 1;
        int tlen = iArr[i3];
        int i4 = 0;
        while (true) {
            if (i4 >= tlen) {
                break;
            }
            int[] iArr2 = this.code;
            int i5 = this.f284ip;
            this.f284ip = i5 + 1;
            int mem = iArr2[i5];
            if (!backrefInvalid(mem)) {
                int pstart = backrefStart(mem);
                int pend = backrefEnd(mem);
                int n = pend - pstart;
                if (this.f283s + n > this.range) {
                    opFail();
                    return;
                }
                this.sprev = this.f283s;
                int swork = this.f283s;
                do {
                    int i6 = n;
                    n--;
                    if (i6 > 0) {
                        i = pstart;
                        pstart++;
                        i2 = swork;
                        swork++;
                    } else {
                        this.f283s = swork;
                        if (this.sprev < this.range) {
                            while (this.sprev + 1 < this.f283s) {
                                this.sprev++;
                            }
                        }
                        this.f284ip += (tlen - i4) - 1;
                    }
                } while (this.chars[i] == this.chars[i2]);
            }
            i4++;
        }
        if (i4 == tlen) {
            opFail();
        }
    }

    private void opBackRefMultiIC() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int tlen = iArr[i];
        int i2 = 0;
        while (true) {
            if (i2 >= tlen) {
                break;
            }
            int[] iArr2 = this.code;
            int i3 = this.f284ip;
            this.f284ip = i3 + 1;
            int mem = iArr2[i3];
            if (!backrefInvalid(mem)) {
                int pstart = backrefStart(mem);
                int pend = backrefEnd(mem);
                int n = pend - pstart;
                if (this.f283s + n > this.range) {
                    opFail();
                    return;
                }
                this.sprev = this.f283s;
                this.value = this.f283s;
                if (stringCmpIC(this.regex.caseFoldFlag, pstart, this, n, this.end)) {
                    this.f283s = this.value;
                    while (this.sprev + 1 < this.f283s) {
                        this.sprev++;
                    }
                    this.f284ip += (tlen - i2) - 1;
                }
            }
            i2++;
        }
        if (i2 == tlen) {
            opFail();
        }
    }

    private boolean memIsInMemp(int mem, int num, int mempp) {
        int memp = mempp;
        for (int i = 0; i < num; i++) {
            int i2 = memp;
            memp++;
            int m = this.code[i2];
            if (mem == m) {
                return true;
            }
        }
        return false;
    }

    private boolean backrefMatchAtNestedLevel(boolean ignoreCase, int caseFoldFlag, int nest, int memNum, int memp) {
        int pend = -1;
        int level = 0;
        for (int k = this.stk - 1; k >= 0; k--) {
            StackEntry e = this.stack[k];
            if (e.type == 2048) {
                level--;
            } else if (e.type == 2304) {
                level++;
            } else if (level != nest) {
                continue;
            } else if (e.type == 256) {
                if (memIsInMemp(e.getMemNum(), memNum, memp)) {
                    int pstart = e.getMemPStr();
                    if (pend != -1) {
                        if (pend - pstart > this.end - this.f283s) {
                            return false;
                        }
                        int p = pstart;
                        this.value = this.f283s;
                        if (ignoreCase) {
                            if (!stringCmpIC(caseFoldFlag, pstart, this, pend - pstart, this.end)) {
                                return false;
                            }
                        } else {
                            while (p < pend) {
                                int i = p;
                                p++;
                                char c = this.chars[i];
                                char[] cArr = this.chars;
                                int i2 = this.value;
                                this.value = i2 + 1;
                                if (c != cArr[i2]) {
                                    return false;
                                }
                            }
                        }
                        this.f283s = this.value;
                        return true;
                    }
                } else {
                    continue;
                }
            } else if (e.type == 33280 && memIsInMemp(e.getMemNum(), memNum, memp)) {
                pend = e.getMemPStr();
            }
        }
        return false;
    }

    private void opBackRefAtLevel() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int ic = iArr[i];
        int[] iArr2 = this.code;
        int i2 = this.f284ip;
        this.f284ip = i2 + 1;
        int level = iArr2[i2];
        int[] iArr3 = this.code;
        int i3 = this.f284ip;
        this.f284ip = i3 + 1;
        int tlen = iArr3[i3];
        this.sprev = this.f283s;
        if (backrefMatchAtNestedLevel(ic != 0, this.regex.caseFoldFlag, level, tlen, this.f284ip)) {
            while (this.sprev + 1 < this.f283s) {
                this.sprev++;
            }
            this.f284ip += tlen;
            return;
        }
        opFail();
    }

    private void opNullCheckStart() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        pushNullCheckStart(mem, this.f283s);
    }

    private void nullCheckFound() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        switch (iArr[i]) {
            case 55:
            case 56:
                this.f284ip++;
                return;
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            default:
                throw new InternalException(ErrorMessages.ERR_UNEXPECTED_BYTECODE);
            case 62:
            case 63:
            case 64:
            case 65:
                this.f284ip++;
                return;
        }
    }

    private void opNullCheckEnd() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        int isNull = nullCheck(mem, this.f283s);
        if (isNull != 0) {
            nullCheckFound();
        }
    }

    private void opNullCheckEndMemST() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        int isNull = nullCheckMemSt(mem, this.f283s);
        if (isNull != 0) {
            if (isNull == -1) {
                opFail();
            } else {
                nullCheckFound();
            }
        }
    }

    private void opJump() {
        this.f284ip += this.code[this.f284ip] + 1;
    }

    private void opPush() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int addr = iArr[i];
        pushAlt(this.f284ip + addr, this.f283s, this.sprev);
    }

    private void opPop() {
        popOne();
    }

    private void opPushOrJumpExact1() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int addr = iArr[i];
        if (this.f283s < this.range && this.code[this.f284ip] == this.chars[this.f283s]) {
            this.f284ip++;
            pushAlt(this.f284ip + addr, this.f283s, this.sprev);
            return;
        }
        this.f284ip += addr + 1;
    }

    private void opPushIfPeekNext() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int addr = iArr[i];
        if (this.f283s < this.range && this.code[this.f284ip] == this.chars[this.f283s]) {
            this.f284ip++;
            pushAlt(this.f284ip + addr, this.f283s, this.sprev);
            return;
        }
        this.f284ip++;
    }

    private void opRepeat() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        int[] iArr2 = this.code;
        int i2 = this.f284ip;
        this.f284ip = i2 + 1;
        int addr = iArr2[i2];
        this.repeatStk[mem] = this.stk;
        pushRepeat(mem, this.f284ip);
        if (this.regex.repeatRangeLo[mem] == 0) {
            pushAlt(this.f284ip + addr, this.f283s, this.sprev);
        }
    }

    private void opRepeatNG() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        int[] iArr2 = this.code;
        int i2 = this.f284ip;
        this.f284ip = i2 + 1;
        int addr = iArr2[i2];
        this.repeatStk[mem] = this.stk;
        pushRepeat(mem, this.f284ip);
        if (this.regex.repeatRangeLo[mem] == 0) {
            pushAlt(this.f284ip, this.f283s, this.sprev);
            this.f284ip += addr;
        }
    }

    private void repeatInc(int mem, int si) {
        StackEntry e = this.stack[si];
        e.increaseRepeatCount();
        if (e.getRepeatCount() < this.regex.repeatRangeHi[mem]) {
            if (e.getRepeatCount() >= this.regex.repeatRangeLo[mem]) {
                pushAlt(this.f284ip, this.f283s, this.sprev);
                this.f284ip = e.getRepeatPCode();
            } else {
                this.f284ip = e.getRepeatPCode();
            }
        }
        pushRepeatInc(si);
    }

    private void opRepeatInc() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        int si = this.repeatStk[mem];
        repeatInc(mem, si);
    }

    private void opRepeatIncSG() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        int si = getRepeat(mem);
        repeatInc(mem, si);
    }

    private void repeatIncNG(int mem, int si) {
        StackEntry e = this.stack[si];
        e.increaseRepeatCount();
        if (e.getRepeatCount() < this.regex.repeatRangeHi[mem]) {
            if (e.getRepeatCount() >= this.regex.repeatRangeLo[mem]) {
                int pcode = e.getRepeatPCode();
                pushRepeatInc(si);
                pushAlt(pcode, this.f283s, this.sprev);
                return;
            }
            this.f284ip = e.getRepeatPCode();
            pushRepeatInc(si);
        } else if (e.getRepeatCount() == this.regex.repeatRangeHi[mem]) {
            pushRepeatInc(si);
        }
    }

    private void opRepeatIncNG() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        int si = this.repeatStk[mem];
        repeatIncNG(mem, si);
    }

    private void opRepeatIncNGSG() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int mem = iArr[i];
        int si = getRepeat(mem);
        repeatIncNG(mem, si);
    }

    private void opPushPos() {
        pushPos(this.f283s, this.sprev);
    }

    private void opPopPos() {
        StackEntry e = this.stack[posEnd()];
        this.f283s = e.getStatePStr();
        this.sprev = e.getStatePStrPrev();
    }

    private void opPushPosNot() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int addr = iArr[i];
        pushPosNot(this.f284ip + addr, this.f283s, this.sprev);
    }

    private void opFailPos() {
        popTilPosNot();
        opFail();
    }

    private void opPushStopBT() {
        pushStopBT();
    }

    private void opPopStopBT() {
        stopBtEnd();
    }

    private void opLookBehind() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int tlen = iArr[i];
        this.f283s = EncodingHelper.stepBack(this.str, this.f283s, tlen);
        if (this.f283s == -1) {
            opFail();
        } else {
            this.sprev = EncodingHelper.prevCharHead(this.str, this.f283s);
        }
    }

    private void opPushLookBehindNot() {
        int[] iArr = this.code;
        int i = this.f284ip;
        this.f284ip = i + 1;
        int addr = iArr[i];
        int[] iArr2 = this.code;
        int i2 = this.f284ip;
        this.f284ip = i2 + 1;
        int tlen = iArr2[i2];
        int q = EncodingHelper.stepBack(this.str, this.f283s, tlen);
        if (q == -1) {
            this.f284ip += addr;
            return;
        }
        pushLookBehindNot(this.f284ip + addr, this.f283s, this.sprev);
        this.f283s = q;
        this.sprev = EncodingHelper.prevCharHead(this.str, this.f283s);
    }

    private void opFailLookBehindNot() {
        popTilLookBehindNot();
        opFail();
    }

    private void opFail() {
        if (this.stack == null) {
            this.f284ip = this.regex.codeLength - 1;
            return;
        }
        StackEntry e = pop();
        this.f284ip = e.getStatePCode();
        this.f283s = e.getStatePStr();
        this.sprev = e.getStatePStrPrev();
    }

    private int finish() {
        return this.bestLen;
    }
}
