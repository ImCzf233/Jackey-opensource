package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/ByteCodePrinter.class */
class ByteCodePrinter {
    final int[] code;
    final int codeLength;
    final char[][] templates;
    Object[] operands;
    private static final String[] OpCodeNames = {"finish", AsmConstants.END, "exact1", "exact2", "exact3", "exact4", "exact5", "exactn", "exactmb2-n1", "exactmb2-n2", "exactmb2-n3", "exactmb2-n", "exactmb3n", "exactmbn", "exact1-ic", "exactn-ic", "cclass", "cclass-mb", "cclass-mix", "cclass-not", "cclass-mb-not", "cclass-mix-not", "cclass-node", "anychar", "anychar-ml", "anychar*", "anychar-ml*", "anychar*-peek-next", "anychar-ml*-peek-next", "word", "not-word", "word-bound", "not-word-bound", "word-begin", "word-end", "begin-buf", "end-buf", "begin-line", "end-line", "semi-end-buf", "begin-position", "backref1", "backref2", "backrefn", "backrefn-ic", "backref_multi", "backref_multi-ic", "backref_at_level", "mem-start", "mem-start-push", "mem-end-push", "mem-end-push-rec", "mem-end", "mem-end-rec", "fail", "jump", "push", "pop", "push-or-jump-e1", "push-if-peek-next", "repeat", "repeat-ng", "repeat-inc", "repeat-inc-ng", "repeat-inc-sg", "repeat-inc-ng-sg", "null-check-start", "null-check-end", "null-check-end-memst", "null-check-end-memst-push", "push-pos", "pop-pos", "push-pos-not", "fail-pos", "push-stop-bt", "pop-stop-bt", "look-behind", "push-look-behind-not", "fail-look-behind-not", "call", "return", "state-check-push", "state-check-push-or-jump", "state-check", "state-check-anychar*", "state-check-anychar-ml*", "set-option-push", "set-option"};
    private static final int[] OpCodeArgTypes = {0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, -1, -1, -1, -1, 4, 4, 4, 4, 4, 4, 0, 1, 1, 0, -1, -1, -1, -1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 1, 0, 0, 0, -1, -1, 0, 2, 0, -1, -1, 6, 6, 6, 5, 5};

    public ByteCodePrinter(Regex regex) {
        this.code = regex.code;
        this.codeLength = regex.codeLength;
        this.operands = regex.operands;
        this.templates = regex.templates;
    }

    public String byteCodeListToString() {
        return compiledByteCodeListToString();
    }

    private void pString(StringBuilder sb, int len, int s) {
        sb.append(CallSiteDescriptor.TOKEN_DELIMITER);
        sb.append(new String(this.code, s, len));
    }

    private void pLenString(StringBuilder sb, int len, int s) {
        sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(len).append(CallSiteDescriptor.TOKEN_DELIMITER);
        sb.append(new String(this.code, s, len));
    }

    private static void pLenStringFromTemplate(StringBuilder sb, int len, char[] tm, int idx) {
        sb.append(":T:").append(len).append(CallSiteDescriptor.TOKEN_DELIMITER);
        sb.append(tm, idx, len);
    }

    public int compiledByteCodeToString(StringBuilder sb, int bptr) {
        int bp;
        sb.append("[").append(OpCodeNames[this.code[bptr]]);
        int argType = OpCodeArgTypes[this.code[bptr]];
        if (argType == -1) {
            int bp2 = bptr + 1;
            switch (this.code[bptr]) {
                case 2:
                case 27:
                case 28:
                    bp = bp2 + 1;
                    pString(sb, 1, bp2);
                    break;
                case 3:
                    pString(sb, 2, bp2);
                    bp = bp2 + 2;
                    break;
                case 4:
                    pString(sb, 3, bp2);
                    bp = bp2 + 3;
                    break;
                case 5:
                    pString(sb, 4, bp2);
                    bp = bp2 + 4;
                    break;
                case 6:
                    pString(sb, 5, bp2);
                    bp = bp2 + 5;
                    break;
                case 7:
                    int len = this.code[bp2];
                    int bp3 = bp2 + 1;
                    int tm = this.code[bp3];
                    int bp4 = bp3 + 1;
                    int idx = this.code[bp4];
                    bp = bp4 + 1;
                    pLenStringFromTemplate(sb, len, this.templates[tm], idx);
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 23:
                case 24:
                case 25:
                case 26:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 74:
                case 75:
                case 78:
                case 79:
                case 80:
                default:
                    throw new InternalException("undefined code: " + this.code[bp2 - 1]);
                case 14:
                    pString(sb, 1, bp2);
                    bp = bp2 + 1;
                    break;
                case 15:
                    int len2 = this.code[bp2];
                    int bp5 = bp2 + 1;
                    int tm2 = this.code[bp5];
                    int bp6 = bp5 + 1;
                    int idx2 = this.code[bp6];
                    bp = bp6 + 1;
                    pLenStringFromTemplate(sb, len2, this.templates[tm2], idx2);
                    break;
                case 16:
                    BitSet bs = new BitSet();
                    System.arraycopy(this.code, bp2, bs.bits, 0, 8);
                    int n = bs.numOn();
                    bp = bp2 + 8;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(n);
                    break;
                case 17:
                case 20:
                    int len3 = this.code[bp2];
                    int bp7 = bp2 + 1;
                    int cod = this.code[bp7];
                    bp = bp7 + len3;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(cod).append(CallSiteDescriptor.TOKEN_DELIMITER).append(len3);
                    break;
                case 18:
                case 21:
                    BitSet bs2 = new BitSet();
                    System.arraycopy(this.code, bp2, bs2.bits, 0, 8);
                    int n2 = bs2.numOn();
                    int bp8 = bp2 + 8;
                    int len4 = this.code[bp8];
                    int bp9 = bp8 + 1;
                    int cod2 = this.code[bp9];
                    bp = bp9 + len4;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(n2).append(CallSiteDescriptor.TOKEN_DELIMITER).append(cod2).append(CallSiteDescriptor.TOKEN_DELIMITER).append(len4);
                    break;
                case 19:
                    BitSet bs3 = new BitSet();
                    System.arraycopy(this.code, bp2, bs3.bits, 0, 8);
                    int n3 = bs3.numOn();
                    bp = bp2 + 8;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(n3);
                    break;
                case 22:
                    CClassNode cc = (CClassNode) this.operands[this.code[bp2]];
                    bp = bp2 + 1;
                    int n4 = cc.f297bs.numOn();
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(cc).append(CallSiteDescriptor.TOKEN_DELIMITER).append(n4);
                    break;
                case 44:
                    int mem = this.code[bp2];
                    bp = bp2 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(mem);
                    break;
                case 45:
                case 46:
                    sb.append(" ");
                    int len5 = this.code[bp2];
                    bp = bp2 + 1;
                    for (int i = 0; i < len5; i++) {
                        int mem2 = this.code[bp];
                        bp++;
                        if (i > 0) {
                            sb.append(", ");
                        }
                        sb.append(mem2);
                    }
                    break;
                case 47:
                    int option = this.code[bp2];
                    int bp10 = bp2 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(option);
                    int level = this.code[bp10];
                    int bp11 = bp10 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(level);
                    sb.append(" ");
                    int len6 = this.code[bp11];
                    bp = bp11 + 1;
                    for (int i2 = 0; i2 < len6; i2++) {
                        int mem3 = this.code[bp];
                        bp++;
                        if (i2 > 0) {
                            sb.append(", ");
                        }
                        sb.append(mem3);
                    }
                    break;
                case 58:
                case 59:
                    int addr = this.code[bp2];
                    int bp12 = bp2 + 1;
                    sb.append(":(").append(addr).append(")");
                    pString(sb, 1, bp12);
                    bp = bp12 + 1;
                    break;
                case 60:
                case 61:
                    int mem4 = this.code[bp2];
                    int bp13 = bp2 + 1;
                    int addr2 = this.code[bp13];
                    bp = bp13 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(mem4).append(CallSiteDescriptor.TOKEN_DELIMITER).append(addr2);
                    break;
                case 76:
                    int len7 = this.code[bp2];
                    bp = bp2 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(len7);
                    break;
                case 77:
                    int addr3 = this.code[bp2];
                    int bp14 = bp2 + 1;
                    int len8 = this.code[bp14];
                    bp = bp14 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(len8).append(":(").append(addr3).append(")");
                    break;
                case 81:
                case 82:
                    int scn = this.code[bp2];
                    int bp15 = bp2 + 1;
                    int addr4 = this.code[bp15];
                    bp = bp15 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(scn).append(":(").append(addr4).append(")");
                    break;
            }
        } else {
            bp = bptr + 1;
            switch (argType) {
                case 1:
                    sb.append(":(").append(this.code[bp]).append(")");
                    bp++;
                    break;
                case 2:
                    sb.append(":(").append(this.code[bp]).append(")");
                    bp++;
                    break;
                case 3:
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(this.code[bp]);
                    bp++;
                    break;
                case 4:
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(this.code[bp]);
                    bp++;
                    break;
                case 5:
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(this.code[bp]);
                    bp++;
                    break;
                case 6:
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(this.code[bp]);
                    bp += 2;
                    break;
            }
        }
        sb.append("]");
        return bp;
    }

    private String compiledByteCodeListToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("code length: ").append(this.codeLength).append("\n");
        int ncode = 0;
        int bp = 0;
        int end = this.codeLength;
        while (bp < end) {
            ncode++;
            if (bp > 0) {
                sb.append(ncode % 5 == 0 ? "\n" : " ");
            }
            bp = compiledByteCodeToString(sb, bp);
        }
        sb.append("\n");
        return sb.toString();
    }
}
