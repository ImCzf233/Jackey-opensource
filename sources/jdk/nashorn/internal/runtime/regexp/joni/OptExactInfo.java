package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/OptExactInfo.class */
public final class OptExactInfo {
    static final int OPT_EXACT_MAXLEN = 24;
    boolean reachEnd;
    boolean ignoreCase;
    int length;
    private static final int COMP_EM_BASE = 20;
    final MinMaxLen mmd = new MinMaxLen();
    final OptAnchorInfo anchor = new OptAnchorInfo();
    final char[] chars = new char[24];

    public boolean isFull() {
        return this.length >= 24;
    }

    public void clear() {
        this.mmd.clear();
        this.anchor.clear();
        this.reachEnd = false;
        this.ignoreCase = false;
        this.length = 0;
    }

    public void copy(OptExactInfo other) {
        this.mmd.copy(other.mmd);
        this.anchor.copy(other.anchor);
        this.reachEnd = other.reachEnd;
        this.ignoreCase = other.ignoreCase;
        this.length = other.length;
        System.arraycopy(other.chars, 0, this.chars, 0, 24);
    }

    public void concat(OptExactInfo other) {
        if (!this.ignoreCase && other.ignoreCase) {
            if (this.length >= other.length) {
                return;
            }
            this.ignoreCase = true;
        }
        int p = 0;
        int end = 0 + other.length;
        int i = this.length;
        while (p < end && i + 1 <= 24) {
            int i2 = i;
            i++;
            int i3 = p;
            p++;
            this.chars[i2] = other.chars[i3];
        }
        this.length = i;
        this.reachEnd = p == end ? other.reachEnd : false;
        OptAnchorInfo tmp = new OptAnchorInfo();
        tmp.concat(this.anchor, other.anchor, 1, 1);
        if (!other.reachEnd) {
            tmp.rightAnchor = 0;
        }
        this.anchor.copy(tmp);
    }

    public void concatStr(char[] lchars, int pp, int end, boolean raw) {
        int p = pp;
        int i = this.length;
        while (p < end && i < 24 && i + 1 <= 24) {
            int i2 = i;
            i++;
            int i3 = p;
            p++;
            this.chars[i2] = lchars[i3];
        }
        this.length = i;
    }

    public void altMerge(OptExactInfo other, OptEnvironment env) {
        if (other.length == 0 || this.length == 0) {
            clear();
        } else if (!this.mmd.equal(other.mmd)) {
            clear();
        } else {
            int i = 0;
            while (i < this.length && i < other.length && this.chars[i] == other.chars[i]) {
                i++;
            }
            if (!other.reachEnd || i < other.length || i < this.length) {
                this.reachEnd = false;
            }
            this.length = i;
            this.ignoreCase |= other.ignoreCase;
            this.anchor.altMerge(other.anchor);
            if (!this.reachEnd) {
                this.anchor.rightAnchor = 0;
            }
        }
    }

    public void select(OptExactInfo alt) {
        int v1 = this.length;
        int v2 = alt.length;
        if (v2 == 0) {
            return;
        }
        if (v1 == 0) {
            copy(alt);
            return;
        }
        if (v1 <= 2 && v2 <= 2) {
            v2 = OptMapInfo.positionValue(this.chars[0] & 255);
            v1 = OptMapInfo.positionValue(alt.chars[0] & 255);
            if (this.length > 1) {
                v1 += 5;
            }
            if (alt.length > 1) {
                v2 += 5;
            }
        }
        if (!this.ignoreCase) {
            v1 *= 2;
        }
        if (!alt.ignoreCase) {
            v2 *= 2;
        }
        if (this.mmd.compareDistanceValue(alt.mmd, v1, v2) > 0) {
            copy(alt);
        }
    }

    public int compare(OptMapInfo m) {
        if (m.value <= 0) {
            return -1;
        }
        int ve = 20 * this.length * (this.ignoreCase ? 1 : 2);
        int vm = 200 / m.value;
        return this.mmd.compareDistanceValue(m.mmd, ve, vm);
    }
}
