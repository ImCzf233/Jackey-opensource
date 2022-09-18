package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;
import jdk.nashorn.internal.runtime.regexp.joni.constants.RegexState;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/Regex.class */
public final class Regex implements RegexState {
    int[] code;
    int codeLength;
    boolean stackNeeded;
    Object[] operands;
    int operandLength;
    int numMem;
    int numRepeat;
    int numNullCheck;
    int captureHistory;
    int btMemStart;
    int btMemEnd;
    int stackPopLevel;
    int[] repeatRangeLo;
    int[] repeatRangeHi;
    WarnCallback warnings;
    MatcherFactory factory;
    protected Analyser analyser;
    int options;
    final int caseFoldFlag;
    SearchAlgorithm searchAlgorithm;
    int thresholdLength;
    int anchor;
    int anchorDmin;
    int anchorDmax;
    int subAnchor;
    char[] exact;
    int exactP;
    int exactEnd;
    byte[] map;
    int[] intMap;
    int[] intMapBackward;
    int dMin;
    int dMax;
    char[][] templates;
    int templateNum;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Regex.class.desiredAssertionStatus();
    }

    public Regex(CharSequence cs) {
        this(cs.toString());
    }

    public Regex(String str) {
        this(str.toCharArray(), 0, str.length(), 0);
    }

    public Regex(char[] chars) {
        this(chars, 0, chars.length, 0);
    }

    public Regex(char[] chars, int p, int end) {
        this(chars, p, end, 0);
    }

    public Regex(char[] chars, int p, int end, int option) {
        this(chars, p, end, option, Syntax.RUBY, WarnCallback.DEFAULT);
    }

    public Regex(char[] chars, int p, int end, int option, Syntax syntax) {
        this(chars, p, end, option, 1073741824, syntax, WarnCallback.DEFAULT);
    }

    public Regex(char[] chars, int p, int end, int option, WarnCallback warnings) {
        this(chars, p, end, option, Syntax.RUBY, warnings);
    }

    public Regex(char[] chars, int p, int end, int option, Syntax syntax, WarnCallback warnings) {
        this(chars, p, end, option, 1073741824, syntax, warnings);
    }

    public Regex(char[] chars, int p, int end, int optionp, int caseFoldFlag, Syntax syntax, WarnCallback warnings) {
        int option;
        if ((optionp & 384) == 384) {
            throw new ValueException(ErrorMessages.ERR_INVALID_COMBINATION_OF_OPTIONS);
        }
        if ((optionp & 64) != 0) {
            int option2 = optionp | syntax.options;
            option = option2 & (-9);
        } else {
            option = optionp | syntax.options;
        }
        this.options = option;
        this.caseFoldFlag = caseFoldFlag;
        this.warnings = warnings;
        this.analyser = new Analyser(new ScanEnvironment(this, syntax), chars, p, end);
        this.analyser.compile();
        this.warnings = null;
    }

    public synchronized MatcherFactory compile() {
        if (this.factory == null && this.analyser != null) {
            new ArrayCompiler(this.analyser).compile();
            this.analyser = null;
        }
        if ($assertionsDisabled || this.factory != null) {
            return this.factory;
        }
        throw new AssertionError();
    }

    public Matcher matcher(char[] chars) {
        return matcher(chars, 0, chars.length);
    }

    public Matcher matcher(char[] chars, int p, int end) {
        MatcherFactory matcherFactory = this.factory;
        if (matcherFactory == null) {
            matcherFactory = compile();
        }
        return matcherFactory.create(this, chars, p, end);
    }

    public WarnCallback getWarnings() {
        return this.warnings;
    }

    public int numberOfCaptures() {
        return this.numMem;
    }

    void setupBMSkipMap() {
        char[] chars = this.exact;
        int p = this.exactP;
        int end = this.exactEnd;
        int len = end - p;
        if (len < 256) {
            if (this.map == null) {
                this.map = new byte[256];
            }
            for (int i = 0; i < 256; i++) {
                this.map[i] = (byte) len;
            }
            for (int i2 = 0; i2 < len - 1; i2++) {
                this.map[chars[p + i2] & 255] = (byte) ((len - 1) - i2);
            }
            return;
        }
        if (this.intMap == null) {
            this.intMap = new int[256];
        }
        for (int i3 = 0; i3 < len - 1; i3++) {
            this.intMap[chars[p + i3] & 255] = (len - 1) - i3;
        }
    }

    public void setExactInfo(OptExactInfo e) {
        if (e.length == 0) {
            return;
        }
        this.exact = e.chars;
        this.exactP = 0;
        this.exactEnd = e.length;
        if (e.ignoreCase) {
            this.searchAlgorithm = new SearchAlgorithm.SLOW_IC(this);
        } else if (e.length >= 2) {
            setupBMSkipMap();
            this.searchAlgorithm = SearchAlgorithm.f291BM;
        } else {
            this.searchAlgorithm = SearchAlgorithm.SLOW;
        }
        this.dMin = e.mmd.min;
        this.dMax = e.mmd.max;
        if (this.dMin != Integer.MAX_VALUE) {
            this.thresholdLength = this.dMin + (this.exactEnd - this.exactP);
        }
    }

    public void setOptimizeMapInfo(OptMapInfo m) {
        this.map = m.map;
        this.searchAlgorithm = SearchAlgorithm.MAP;
        this.dMin = m.mmd.min;
        this.dMax = m.mmd.max;
        if (this.dMin != Integer.MAX_VALUE) {
            this.thresholdLength = this.dMin + 1;
        }
    }

    public void setSubAnchor(OptAnchorInfo anc) {
        this.subAnchor |= anc.leftAnchor & 2;
        this.subAnchor |= anc.rightAnchor & 32;
    }

    public void clearOptimizeInfo() {
        this.searchAlgorithm = SearchAlgorithm.NONE;
        this.anchor = 0;
        this.anchorDmax = 0;
        this.anchorDmin = 0;
        this.subAnchor = 0;
        this.exact = null;
        this.exactEnd = 0;
        this.exactP = 0;
    }

    public String optimizeInfoToString() {
        StringBuilder s = new StringBuilder();
        s.append("optimize: ").append(this.searchAlgorithm.getName()).append("\n");
        s.append("  anchor:     ").append(OptAnchorInfo.anchorToString(this.anchor));
        if ((this.anchor & 24) != 0) {
            s.append(MinMaxLen.distanceRangeToString(this.anchorDmin, this.anchorDmax));
        }
        s.append("\n");
        if (this.searchAlgorithm != SearchAlgorithm.NONE) {
            s.append("  sub anchor: ").append(OptAnchorInfo.anchorToString(this.subAnchor)).append("\n");
        }
        s.append("dmin: ").append(this.dMin).append(" dmax: ").append(this.dMax).append("\n");
        s.append("threshold length: ").append(this.thresholdLength).append("\n");
        if (this.exact != null) {
            s.append("exact: [").append(this.exact, this.exactP, this.exactEnd - this.exactP).append("]: length: ").append(this.exactEnd - this.exactP).append("\n");
        } else if (this.searchAlgorithm == SearchAlgorithm.MAP) {
            int n = 0;
            for (int i = 0; i < 256; i++) {
                if (this.map[i] != 0) {
                    n++;
                }
            }
            s.append("map: n = ").append(n).append("\n");
            if (n > 0) {
                int c = 0;
                s.append("[");
                for (int i2 = 0; i2 < 256; i2++) {
                    if (this.map[i2] != 0) {
                        if (c > 0) {
                            s.append(", ");
                        }
                        c++;
                        s.append((char) i2);
                    }
                }
                s.append("]\n");
            }
        }
        return s.toString();
    }

    public int getOptions() {
        return this.options;
    }

    public String dumpTree() {
        if (this.analyser == null) {
            return null;
        }
        return this.analyser.root.toString();
    }

    public String dumpByteCode() {
        compile();
        return new ByteCodePrinter(this).byteCodeListToString();
    }
}
