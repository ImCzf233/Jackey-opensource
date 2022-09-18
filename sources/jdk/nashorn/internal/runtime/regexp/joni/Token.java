package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.constants.TokenType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/Token.class */
final class Token {
    TokenType type;
    boolean escaped;
    int backP;
    private int INT1;
    private int INT2;
    private int INT3;
    private int INT4;

    public int getC() {
        return this.INT1;
    }

    public void setC(int c) {
        this.INT1 = c;
    }

    public int getCode() {
        return this.INT1;
    }

    public void setCode(int code) {
        this.INT1 = code;
    }

    public int getAnchor() {
        return this.INT1;
    }

    public void setAnchor(int anchor) {
        this.INT1 = anchor;
    }

    public int getRepeatLower() {
        return this.INT1;
    }

    public void setRepeatLower(int lower) {
        this.INT1 = lower;
    }

    public int getRepeatUpper() {
        return this.INT2;
    }

    public void setRepeatUpper(int upper) {
        this.INT2 = upper;
    }

    public boolean getRepeatGreedy() {
        return this.INT3 != 0;
    }

    public void setRepeatGreedy(boolean greedy) {
        this.INT3 = greedy ? 1 : 0;
    }

    public boolean getRepeatPossessive() {
        return this.INT4 != 0;
    }

    public void setRepeatPossessive(boolean possessive) {
        this.INT4 = possessive ? 1 : 0;
    }

    public int getBackrefRef() {
        return this.INT2;
    }

    public void setBackrefRef(int ref1) {
        this.INT2 = ref1;
    }

    public int getPropCType() {
        return this.INT1;
    }

    public void setPropCType(int ctype) {
        this.INT1 = ctype;
    }

    public boolean getPropNot() {
        return this.INT2 != 0;
    }

    public void setPropNot(boolean not) {
        this.INT2 = not ? 1 : 0;
    }
}
