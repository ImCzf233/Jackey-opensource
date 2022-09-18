package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.TokenType;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/Lexer.class */
public class Lexer extends ScannerSupport {
    protected final ScanEnvironment env;
    protected final Syntax syntax;
    protected final Token token = new Token();

    public Lexer(ScanEnvironment env, char[] chars, int p, int end) {
        super(chars, p, end);
        this.env = env;
        this.syntax = env.syntax;
    }

    private int fetchRangeQuantifier() {
        int up;
        mark();
        boolean synAllow = this.syntax.allowInvalidInterval();
        if (!left()) {
            if (synAllow) {
                return 1;
            }
            throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_LEFT_BRACE);
        }
        if (!synAllow) {
            this.f289c = peek();
            if (this.f289c == 41 || this.f289c == 40 || this.f289c == 124) {
                throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_LEFT_BRACE);
            }
        }
        int low = scanUnsignedNumber();
        if (low < 0) {
            throw new SyntaxException(ErrorMessages.ERR_TOO_BIG_NUMBER_FOR_REPEAT_RANGE);
        }
        if (low > 100000) {
            throw new SyntaxException(ErrorMessages.ERR_TOO_BIG_NUMBER_FOR_REPEAT_RANGE);
        }
        boolean nonLow = false;
        if (this.f288p == this.f290_p) {
            if (this.syntax.allowIntervalLowAbbrev()) {
                low = 0;
                nonLow = true;
            } else {
                return invalidRangeQuantifier(synAllow);
            }
        }
        if (!left()) {
            return invalidRangeQuantifier(synAllow);
        }
        fetch();
        int ret = 0;
        if (this.f289c == 44) {
            int prev = this.f288p;
            up = scanUnsignedNumber();
            if (up < 0) {
                throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER_FOR_REPEAT_RANGE);
            }
            if (up > 100000) {
                throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER_FOR_REPEAT_RANGE);
            }
            if (this.f288p == prev) {
                if (nonLow) {
                    return invalidRangeQuantifier(synAllow);
                }
                up = -1;
            }
        } else if (nonLow) {
            return invalidRangeQuantifier(synAllow);
        } else {
            unfetch();
            up = low;
            ret = 2;
        }
        if (!left()) {
            return invalidRangeQuantifier(synAllow);
        }
        fetch();
        if (this.syntax.opEscBraceInterval()) {
            if (this.f289c != this.syntax.metaCharTable.esc) {
                return invalidRangeQuantifier(synAllow);
            }
            fetch();
        }
        if (this.f289c != 125) {
            return invalidRangeQuantifier(synAllow);
        }
        if (!QuantifierNode.isRepeatInfinite(up) && low > up) {
            throw new ValueException(ErrorMessages.ERR_UPPER_SMALLER_THAN_LOWER_IN_REPEAT_RANGE);
        }
        this.token.type = TokenType.INTERVAL;
        this.token.setRepeatLower(low);
        this.token.setRepeatUpper(up);
        return ret;
    }

    private int invalidRangeQuantifier(boolean synAllow) {
        if (synAllow) {
            restore();
            return 1;
        }
        throw new SyntaxException(ErrorMessages.ERR_INVALID_REPEAT_RANGE_PATTERN);
    }

    private int fetchEscapedValue() {
        if (!left()) {
            throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_ESCAPE);
        }
        fetch();
        switch (this.f289c) {
            case 67:
                if (this.syntax.op2EscCapitalCBarControl()) {
                    if (!left()) {
                        throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_CONTROL);
                    }
                    fetch();
                    if (this.f289c != 45) {
                        throw new SyntaxException(ErrorMessages.ERR_CONTROL_CODE_SYNTAX);
                    }
                    fetchEscapedValueControl();
                    break;
                } else {
                    fetchEscapedValueBackSlash();
                    break;
                }
            case 77:
                if (this.syntax.op2EscCapitalMBarMeta()) {
                    if (!left()) {
                        throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_META);
                    }
                    fetch();
                    if (this.f289c != 45) {
                        throw new SyntaxException(ErrorMessages.ERR_META_CODE_SYNTAX);
                    }
                    if (!left()) {
                        throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_META);
                    }
                    fetch();
                    if (this.f289c == this.syntax.metaCharTable.esc) {
                        this.f289c = fetchEscapedValue();
                    }
                    this.f289c = (this.f289c & 255) | 128;
                    break;
                } else {
                    fetchEscapedValueBackSlash();
                    break;
                }
            case 99:
                if (this.syntax.opEscCControl()) {
                    fetchEscapedValueControl();
                }
            default:
                fetchEscapedValueBackSlash();
                break;
        }
        return this.f289c;
    }

    private void fetchEscapedValueBackSlash() {
        this.f289c = this.env.convertBackslashValue(this.f289c);
    }

    private void fetchEscapedValueControl() {
        if (!left()) {
            throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_CONTROL);
        }
        fetch();
        if (this.f289c == 63) {
            this.f289c = 127;
            return;
        }
        if (this.f289c == this.syntax.metaCharTable.esc) {
            this.f289c = fetchEscapedValue();
        }
        this.f289c &= 159;
    }

    private void fetchTokenInCCFor_charType(boolean flag, int type) {
        this.token.type = TokenType.CHAR_TYPE;
        this.token.setPropCType(type);
        this.token.setPropNot(flag);
    }

    private void fetchTokenInCCFor_x() {
        if (!left()) {
            return;
        }
        int last = this.f288p;
        if (!peekIs(123) || !this.syntax.opEscXBraceHex8()) {
            if (this.syntax.opEscXHex2()) {
                int num = scanUnsignedHexadecimalNumber(2);
                if (num < 0) {
                    throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
                }
                if (this.f288p == last) {
                    num = 0;
                }
                this.token.type = TokenType.RAW_BYTE;
                this.token.setC(num);
                return;
            }
            return;
        }
        inc();
        int num2 = scanUnsignedHexadecimalNumber(8);
        if (num2 < 0) {
            throw new ValueException(ErrorMessages.ERR_TOO_BIG_WIDE_CHAR_VALUE);
        }
        if (left()) {
            int c2 = peek();
            if (EncodingHelper.isXDigit(c2)) {
                throw new ValueException(ErrorMessages.ERR_TOO_LONG_WIDE_CHAR_VALUE);
            }
        }
        if (this.f288p > last + 1 && left() && peekIs(125)) {
            inc();
            this.token.type = TokenType.CODE_POINT;
            this.token.setCode(num2);
            return;
        }
        this.f288p = last;
    }

    private void fetchTokenInCCFor_u() {
        if (!left()) {
            return;
        }
        int last = this.f288p;
        if (this.syntax.op2EscUHex4()) {
            int num = scanUnsignedHexadecimalNumber(4);
            if (num < 0) {
                throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
            }
            if (this.f288p == last) {
                num = 0;
            }
            this.token.type = TokenType.CODE_POINT;
            this.token.setCode(num);
        }
    }

    private void fetchTokenInCCFor_digit() {
        if (this.syntax.opEscOctal3()) {
            unfetch();
            int last = this.f288p;
            int num = scanUnsignedOctalNumber(3);
            if (num < 0) {
                throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
            }
            if (this.f288p == last) {
                num = 0;
            }
            this.token.type = TokenType.RAW_BYTE;
            this.token.setC(num);
        }
    }

    private void fetchTokenInCCFor_and() {
        if (this.syntax.op2CClassSetOp() && left() && peekIs(38)) {
            inc();
            this.token.type = TokenType.CC_AND;
        }
    }

    public final TokenType fetchTokenInCC() {
        if (!left()) {
            this.token.type = TokenType.EOT;
            return this.token.type;
        }
        fetch();
        this.token.type = TokenType.CHAR;
        this.token.setC(this.f289c);
        this.token.escaped = false;
        if (this.f289c == 93) {
            this.token.type = TokenType.CC_CLOSE;
        } else if (this.f289c == 45) {
            this.token.type = TokenType.CC_RANGE;
        } else if (this.f289c == this.syntax.metaCharTable.esc) {
            if (!this.syntax.backSlashEscapeInCC()) {
                return this.token.type;
            }
            if (!left()) {
                throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_ESCAPE);
            }
            fetch();
            this.token.escaped = true;
            this.token.setC(this.f289c);
            switch (this.f289c) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                    fetchTokenInCCFor_digit();
                    break;
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 69:
                case 70:
                case 71:
                case 73:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 84:
                case 85:
                case 86:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 101:
                case 102:
                case 103:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                case 110:
                case 111:
                case 112:
                case 113:
                case 114:
                case 116:
                case 118:
                default:
                    unfetch();
                    int num = fetchEscapedValue();
                    if (this.token.getC() != num) {
                        this.token.setCode(num);
                        this.token.type = TokenType.CODE_POINT;
                        break;
                    }
                    break;
                case 68:
                    fetchTokenInCCFor_charType(true, CharacterType.f309D);
                    break;
                case 72:
                    if (this.syntax.op2EscHXDigit()) {
                        fetchTokenInCCFor_charType(true, 11);
                        break;
                    }
                    break;
                case 83:
                    fetchTokenInCCFor_charType(true, CharacterType.f308S);
                    break;
                case 87:
                    fetchTokenInCCFor_charType(true, CharacterType.f310W);
                    break;
                case 100:
                    fetchTokenInCCFor_charType(false, CharacterType.f309D);
                    break;
                case 104:
                    if (this.syntax.op2EscHXDigit()) {
                        fetchTokenInCCFor_charType(false, 11);
                        break;
                    }
                    break;
                case 115:
                    fetchTokenInCCFor_charType(false, CharacterType.f308S);
                    break;
                case 117:
                    fetchTokenInCCFor_u();
                    break;
                case 119:
                    fetchTokenInCCFor_charType(false, CharacterType.f310W);
                    break;
                case 120:
                    fetchTokenInCCFor_x();
                    break;
            }
        } else if (this.f289c == 38) {
            fetchTokenInCCFor_and();
        }
        return this.token.type;
    }

    private void fetchTokenFor_repeat(int lower, int upper) {
        this.token.type = TokenType.OP_REPEAT;
        this.token.setRepeatLower(lower);
        this.token.setRepeatUpper(upper);
        greedyCheck();
    }

    private void fetchTokenFor_openBrace() {
        switch (fetchRangeQuantifier()) {
            case 0:
                greedyCheck();
                return;
            case 2:
                if (this.syntax.fixedIntervalIsGreedyOnly()) {
                    possessiveCheck();
                    return;
                } else {
                    greedyCheck();
                    return;
                }
            default:
                return;
        }
    }

    private void fetchTokenFor_anchor(int subType) {
        this.token.type = TokenType.ANCHOR;
        this.token.setAnchor(subType);
    }

    private void fetchTokenFor_xBrace() {
        if (!left()) {
            return;
        }
        int last = this.f288p;
        if (!peekIs(123) || !this.syntax.opEscXBraceHex8()) {
            if (this.syntax.opEscXHex2()) {
                int num = scanUnsignedHexadecimalNumber(2);
                if (num < 0) {
                    throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
                }
                if (this.f288p == last) {
                    num = 0;
                }
                this.token.type = TokenType.RAW_BYTE;
                this.token.setC(num);
                return;
            }
            return;
        }
        inc();
        int num2 = scanUnsignedHexadecimalNumber(8);
        if (num2 < 0) {
            throw new ValueException(ErrorMessages.ERR_TOO_BIG_WIDE_CHAR_VALUE);
        }
        if (left() && EncodingHelper.isXDigit(peek())) {
            throw new ValueException(ErrorMessages.ERR_TOO_LONG_WIDE_CHAR_VALUE);
        }
        if (this.f288p > last + 1 && left() && peekIs(125)) {
            inc();
            this.token.type = TokenType.CODE_POINT;
            this.token.setCode(num2);
            return;
        }
        this.f288p = last;
    }

    private void fetchTokenFor_uHex() {
        if (!left()) {
            return;
        }
        int last = this.f288p;
        if (this.syntax.op2EscUHex4()) {
            int num = scanUnsignedHexadecimalNumber(4);
            if (num < 0) {
                throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
            }
            if (this.f288p == last) {
                num = 0;
            }
            this.token.type = TokenType.CODE_POINT;
            this.token.setCode(num);
        }
    }

    private void fetchTokenFor_digit() {
        unfetch();
        int last = this.f288p;
        int num = scanUnsignedNumber();
        if (num >= 0 && num <= 1000 && this.syntax.opDecimalBackref() && (num <= this.env.numMem || num <= 9)) {
            if (this.syntax.strictCheckBackref() && (num > this.env.numMem || this.env.memNodes == null || this.env.memNodes[num] == null)) {
                throw new ValueException(ErrorMessages.ERR_INVALID_BACKREF);
            }
            this.token.type = TokenType.BACKREF;
            this.token.setBackrefRef(num);
        } else if (this.f289c == 56 || this.f289c == 57) {
            this.f288p = last;
            inc();
        } else {
            this.f288p = last;
            fetchTokenFor_zero();
        }
    }

    private void fetchTokenFor_zero() {
        if (!this.syntax.opEscOctal3()) {
            if (this.f289c != 48) {
                inc();
                return;
            }
            return;
        }
        int last = this.f288p;
        int num = scanUnsignedOctalNumber(this.f289c == 48 ? 2 : 3);
        if (num < 0) {
            throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
        }
        if (this.f288p == last) {
            num = 0;
        }
        this.token.type = TokenType.RAW_BYTE;
        this.token.setC(num);
    }

    private void fetchTokenFor_metaChars() {
        if (this.f289c == this.syntax.metaCharTable.anyChar) {
            this.token.type = TokenType.ANYCHAR;
        } else if (this.f289c == this.syntax.metaCharTable.anyTime) {
            fetchTokenFor_repeat(0, -1);
        } else if (this.f289c == this.syntax.metaCharTable.zeroOrOneTime) {
            fetchTokenFor_repeat(0, 1);
        } else if (this.f289c == this.syntax.metaCharTable.oneOrMoreTime) {
            fetchTokenFor_repeat(1, -1);
        } else if (this.f289c == this.syntax.metaCharTable.anyCharAnyTime) {
            this.token.type = TokenType.ANYCHAR_ANYTIME;
        }
    }

    public final TokenType fetchToken() {
        while (left()) {
            this.token.type = TokenType.STRING;
            this.token.backP = this.f288p;
            fetch();
            if (this.f289c == this.syntax.metaCharTable.esc && !this.syntax.op2IneffectiveEscape()) {
                if (!left()) {
                    throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_ESCAPE);
                }
                this.token.backP = this.f288p;
                fetch();
                this.token.setC(this.f289c);
                this.token.escaped = true;
                switch (this.f289c) {
                    case 39:
                        if (this.syntax.op2EscGnuBufAnchor()) {
                            fetchTokenFor_anchor(8);
                            break;
                        }
                        break;
                    case 40:
                        if (this.syntax.opEscLParenSubexp()) {
                            this.token.type = TokenType.SUBEXP_OPEN;
                            break;
                        }
                        break;
                    case 41:
                        if (this.syntax.opEscLParenSubexp()) {
                            this.token.type = TokenType.SUBEXP_CLOSE;
                            break;
                        }
                        break;
                    case 42:
                        if (this.syntax.opEscAsteriskZeroInf()) {
                            fetchTokenFor_repeat(0, -1);
                            break;
                        }
                        break;
                    case 43:
                        if (this.syntax.opEscPlusOneInf()) {
                            fetchTokenFor_repeat(1, -1);
                            break;
                        }
                        break;
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 58:
                    case 59:
                    case 61:
                    case 64:
                    case 67:
                    case 69:
                    case 70:
                    case 73:
                    case 74:
                    case 75:
                    case 76:
                    case 77:
                    case 78:
                    case 79:
                    case 80:
                    case 81:
                    case 82:
                    case 84:
                    case 85:
                    case 86:
                    case 88:
                    case 89:
                    case 91:
                    case 92:
                    case 93:
                    case 94:
                    case 95:
                    case 97:
                    case 99:
                    case 101:
                    case 102:
                    case 103:
                    case 105:
                    case 106:
                    case 107:
                    case 108:
                    case 109:
                    case 110:
                    case 111:
                    case 112:
                    case 113:
                    case 114:
                    case 116:
                    case 118:
                    case 121:
                    default:
                        unfetch();
                        int num = fetchEscapedValue();
                        if (this.token.getC() != num) {
                            this.token.type = TokenType.CODE_POINT;
                            this.token.setCode(num);
                            break;
                        } else {
                            this.f288p = this.token.backP + 1;
                            break;
                        }
                    case 48:
                        fetchTokenFor_zero();
                        break;
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                        fetchTokenFor_digit();
                        break;
                    case 60:
                        if (this.syntax.opEscLtGtWordBeginEnd()) {
                            fetchTokenFor_anchor(256);
                            break;
                        }
                        break;
                    case 62:
                        if (this.syntax.opEscLtGtWordBeginEnd()) {
                            fetchTokenFor_anchor(512);
                            break;
                        }
                        break;
                    case 63:
                        if (this.syntax.opEscQMarkZeroOne()) {
                            fetchTokenFor_repeat(0, 1);
                            break;
                        }
                        break;
                    case 65:
                        if (this.syntax.opEscAZBufAnchor()) {
                            fetchTokenFor_anchor(1);
                            break;
                        }
                        break;
                    case 66:
                        if (this.syntax.opEscBWordBound()) {
                            fetchTokenFor_anchor(128);
                            break;
                        }
                        break;
                    case 68:
                        if (this.syntax.opEscDDigit()) {
                            fetchTokenInCCFor_charType(true, CharacterType.f309D);
                            break;
                        }
                        break;
                    case 71:
                        if (this.syntax.opEscCapitalGBeginAnchor()) {
                            fetchTokenFor_anchor(4);
                            break;
                        }
                        break;
                    case 72:
                        if (this.syntax.op2EscHXDigit()) {
                            fetchTokenInCCFor_charType(true, 11);
                            break;
                        }
                        break;
                    case 83:
                        if (this.syntax.opEscSWhiteSpace()) {
                            fetchTokenInCCFor_charType(true, CharacterType.f308S);
                            break;
                        }
                        break;
                    case 87:
                        if (this.syntax.opEscWWord()) {
                            fetchTokenInCCFor_charType(true, CharacterType.f310W);
                            break;
                        }
                        break;
                    case 90:
                        if (this.syntax.opEscAZBufAnchor()) {
                            fetchTokenFor_anchor(16);
                            break;
                        }
                        break;
                    case 96:
                        if (this.syntax.op2EscGnuBufAnchor()) {
                            fetchTokenFor_anchor(1);
                            break;
                        }
                        break;
                    case 98:
                        if (this.syntax.opEscBWordBound()) {
                            fetchTokenFor_anchor(64);
                            break;
                        }
                        break;
                    case 100:
                        if (this.syntax.opEscDDigit()) {
                            fetchTokenInCCFor_charType(false, CharacterType.f309D);
                            break;
                        }
                        break;
                    case 104:
                        if (this.syntax.op2EscHXDigit()) {
                            fetchTokenInCCFor_charType(false, 11);
                            break;
                        }
                        break;
                    case 115:
                        if (this.syntax.opEscSWhiteSpace()) {
                            fetchTokenInCCFor_charType(false, CharacterType.f308S);
                            break;
                        }
                        break;
                    case 117:
                        fetchTokenFor_uHex();
                        break;
                    case 119:
                        if (this.syntax.opEscWWord()) {
                            fetchTokenInCCFor_charType(false, CharacterType.f310W);
                            break;
                        }
                        break;
                    case 120:
                        fetchTokenFor_xBrace();
                        break;
                    case 122:
                        if (this.syntax.opEscAZBufAnchor()) {
                            fetchTokenFor_anchor(8);
                            break;
                        }
                        break;
                    case 123:
                        if (this.syntax.opEscBraceInterval()) {
                            fetchTokenFor_openBrace();
                            break;
                        }
                        break;
                    case 124:
                        if (this.syntax.opEscVBarAlt()) {
                            this.token.type = TokenType.ALT;
                            break;
                        }
                        break;
                }
            } else {
                this.token.setC(this.f289c);
                this.token.escaped = false;
                if (this.f289c != 0 && this.syntax.opVariableMetaCharacters()) {
                    fetchTokenFor_metaChars();
                } else {
                    switch (this.f289c) {
                        case 9:
                        case 10:
                        case 12:
                        case 13:
                        case 32:
                            if (!Option.isExtend(this.env.option)) {
                                break;
                            } else {
                                break;
                            }
                        case 35:
                            if (!Option.isExtend(this.env.option)) {
                                break;
                            } else {
                                while (left()) {
                                    fetch();
                                    if (EncodingHelper.isNewLine(this.f289c)) {
                                        break;
                                    }
                                }
                                break;
                            }
                        case 36:
                            if (this.syntax.opLineAnchor()) {
                                fetchTokenFor_anchor(Option.isSingleline(this.env.option) ? 8 : 32);
                                break;
                            }
                            break;
                        case 40:
                            if (peekIs(63) && this.syntax.op2QMarkGroupEffect()) {
                                inc();
                                if (peekIs(35)) {
                                    fetch();
                                    while (left()) {
                                        fetch();
                                        if (this.f289c == this.syntax.metaCharTable.esc) {
                                            if (left()) {
                                                fetch();
                                            }
                                        } else if (this.f289c == 41) {
                                            break;
                                        }
                                    }
                                    throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_IN_GROUP);
                                }
                                unfetch();
                            }
                            if (this.syntax.opLParenSubexp()) {
                                this.token.type = TokenType.SUBEXP_OPEN;
                                break;
                            }
                            break;
                        case 41:
                            if (this.syntax.opLParenSubexp()) {
                                this.token.type = TokenType.SUBEXP_CLOSE;
                                break;
                            }
                            break;
                        case 42:
                            if (this.syntax.opAsteriskZeroInf()) {
                                fetchTokenFor_repeat(0, -1);
                                break;
                            }
                            break;
                        case 43:
                            if (this.syntax.opPlusOneInf()) {
                                fetchTokenFor_repeat(1, -1);
                                break;
                            }
                            break;
                        case 46:
                            if (this.syntax.opDotAnyChar()) {
                                this.token.type = TokenType.ANYCHAR;
                                break;
                            }
                            break;
                        case 63:
                            if (this.syntax.opQMarkZeroOne()) {
                                fetchTokenFor_repeat(0, 1);
                                break;
                            }
                            break;
                        case 91:
                            if (this.syntax.opBracketCC()) {
                                this.token.type = TokenType.CC_CC_OPEN;
                                break;
                            }
                            break;
                        case 94:
                            if (this.syntax.opLineAnchor()) {
                                fetchTokenFor_anchor(Option.isSingleline(this.env.option) ? 1 : 2);
                                break;
                            }
                            break;
                        case 123:
                            if (this.syntax.opBraceInterval()) {
                                fetchTokenFor_openBrace();
                                break;
                            }
                            break;
                        case 124:
                            if (this.syntax.opVBarAlt()) {
                                this.token.type = TokenType.ALT;
                                break;
                            }
                            break;
                    }
                }
            }
            return this.token.type;
        }
        this.token.type = TokenType.EOT;
        return this.token.type;
    }

    private void greedyCheck() {
        if (left() && peekIs(63) && this.syntax.opQMarkNonGreedy()) {
            fetch();
            this.token.setRepeatGreedy(false);
            this.token.setRepeatPossessive(false);
            return;
        }
        possessiveCheck();
    }

    private void possessiveCheck() {
        if (left() && peekIs(43) && ((this.syntax.op2PlusPossessiveRepeat() && this.token.type != TokenType.INTERVAL) || (this.syntax.op2PlusPossessiveInterval() && this.token.type == TokenType.INTERVAL))) {
            fetch();
            this.token.setRepeatGreedy(true);
            this.token.setRepeatPossessive(true);
            return;
        }
        this.token.setRepeatGreedy(true);
        this.token.setRepeatPossessive(false);
    }

    protected final void syntaxWarn(String message, char ch) {
        syntaxWarn(message.replace("<%n>", Character.toString(ch)));
    }

    protected final void syntaxWarn(String message) {
        this.env.reg.warnings.warn(message + ": /" + new String(this.chars, getBegin(), getEnd()) + "/");
    }
}
