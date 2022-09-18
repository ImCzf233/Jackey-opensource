package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/OptMapInfo.class */
final class OptMapInfo {
    int value;

    /* renamed from: z */
    private static final int f286z = 32768;
    static final short[] ByteValTable = {5, 1, 1, 1, 1, 1, 1, 1, 1, 10, 10, 1, 1, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 4, 7, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 6, 5, 5, 5, 5, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 1};
    final MinMaxLen mmd = new MinMaxLen();
    final OptAnchorInfo anchor = new OptAnchorInfo();
    final byte[] map = new byte[256];

    public void clear() {
        this.mmd.clear();
        this.anchor.clear();
        this.value = 0;
        for (int i = 0; i < this.map.length; i++) {
            this.map[i] = 0;
        }
    }

    public void copy(OptMapInfo other) {
        this.mmd.copy(other.mmd);
        this.anchor.copy(other.anchor);
        this.value = other.value;
        System.arraycopy(other.map, 0, this.map, 0, other.map.length);
    }

    public void addChar(int c) {
        int c_ = c & 255;
        if (this.map[c_] == 0) {
            this.map[c_] = 1;
            this.value += positionValue(c_);
        }
    }

    public void addCharAmb(char[] chars, int p, int end, int caseFoldFlag) {
        addChar(chars[p]);
        char[] items = EncodingHelper.caseFoldCodesByString(caseFoldFlag & (-1073741825), chars[p]);
        for (char c : items) {
            addChar(c);
        }
    }

    public void select(OptMapInfo alt) {
        if (alt.value == 0) {
            return;
        }
        if (this.value == 0) {
            copy(alt);
            return;
        }
        int v1 = 32768 / this.value;
        int v2 = 32768 / alt.value;
        if (this.mmd.compareDistanceValue(alt.mmd, v1, v2) > 0) {
            copy(alt);
        }
    }

    public void altMerge(OptMapInfo other) {
        if (this.value == 0) {
            return;
        }
        if (other.value == 0 || this.mmd.max < other.mmd.max) {
            clear();
            return;
        }
        this.mmd.altMerge(other.mmd);
        int val = 0;
        for (int i = 0; i < 256; i++) {
            if (other.map[i] != 0) {
                this.map[i] = 1;
            }
            if (this.map[i] != 0) {
                val += positionValue(i);
            }
        }
        this.value = val;
        this.anchor.altMerge(other.anchor);
    }

    public static int positionValue(int i) {
        if (i < ByteValTable.length) {
            return ByteValTable[i];
        }
        return 4;
    }
}
