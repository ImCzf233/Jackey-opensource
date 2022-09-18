package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.constants.SyntaxProperties;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/Syntax.class */
public final class Syntax implements SyntaxProperties {

    /* renamed from: op */
    private final int f296op;
    private final int op2;
    private final int behavior;
    public final int options;
    public final MetaCharTable metaCharTable;
    public static final Syntax RUBY = new Syntax(2146948438, 736218, -2086665253, 0, new MetaCharTable(92, 0, 0, 0, 0, 0));
    public static final Syntax DEFAULT = RUBY;
    public static final Syntax ASIS = new Syntax(0, 1048576, 0, 0, new MetaCharTable(92, 0, 0, 0, 0, 0));
    public static final Syntax PosixBasic = new Syntax(92480006, 0, 0, 12, new MetaCharTable(92, 0, 0, 0, 0, 0));
    public static final Syntax PosixExtended = new Syntax(92476758, 0, -2139095033, 12, new MetaCharTable(92, 0, 0, 0, 0, 0));
    public static final Syntax Emacs = new Syntax(75704918, 32768, 4194304, 0, new MetaCharTable(92, 0, 0, 0, 0, 0));
    public static final Syntax Grep = new Syntax(27208358, 0, 5242880, 0, new MetaCharTable(92, 0, 0, 0, 0, 0));
    public static final Syntax GnuRegex = new Syntax(SyntaxProperties.GNU_REGEX_OP, 0, SyntaxProperties.GNU_REGEX_BV, 0, new MetaCharTable(92, 0, 0, 0, 0, 0));
    public static final Syntax Java = new Syntax(1073206614, 90231, -2136997813, 8, new MetaCharTable(92, 0, 0, 0, 0, 0));
    public static final Syntax Perl = new Syntax(2146948438, 196615, SyntaxProperties.GNU_REGEX_BV, 8, new MetaCharTable(92, 0, 0, 0, 0, 0));
    public static final Syntax PerlNG = new Syntax(2146948438, 197511, -2136997493, 8, new MetaCharTable(92, 0, 0, 0, 0, 0));
    public static final Syntax JAVASCRIPT = new Syntax(804771158, 24578, -2136997813, 8, new MetaCharTable(92, 0, 0, 0, 0, 0));

    public Syntax(int op, int op2, int behavior, int options, MetaCharTable metaCharTable) {
        this.f296op = op;
        this.op2 = op2;
        this.behavior = behavior;
        this.options = options;
        this.metaCharTable = metaCharTable;
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/Syntax$MetaCharTable.class */
    public static class MetaCharTable {
        public final int esc;
        public final int anyChar;
        public final int anyTime;
        public final int zeroOrOneTime;
        public final int oneOrMoreTime;
        public final int anyCharAnyTime;

        public MetaCharTable(int esc, int anyChar, int anyTime, int zeroOrOneTime, int oneOrMoreTime, int anyCharAnyTime) {
            this.esc = esc;
            this.anyChar = anyChar;
            this.anyTime = anyTime;
            this.zeroOrOneTime = zeroOrOneTime;
            this.oneOrMoreTime = oneOrMoreTime;
            this.anyCharAnyTime = anyCharAnyTime;
        }
    }

    protected boolean isOp(int opm) {
        return (this.f296op & opm) != 0;
    }

    public boolean opVariableMetaCharacters() {
        return isOp(1);
    }

    public boolean opDotAnyChar() {
        return isOp(2);
    }

    public boolean opAsteriskZeroInf() {
        return isOp(4);
    }

    public boolean opEscAsteriskZeroInf() {
        return isOp(8);
    }

    public boolean opPlusOneInf() {
        return isOp(16);
    }

    public boolean opEscPlusOneInf() {
        return isOp(32);
    }

    public boolean opQMarkZeroOne() {
        return isOp(64);
    }

    public boolean opEscQMarkZeroOne() {
        return isOp(128);
    }

    public boolean opBraceInterval() {
        return isOp(256);
    }

    public boolean opEscBraceInterval() {
        return isOp(512);
    }

    public boolean opVBarAlt() {
        return isOp(1024);
    }

    public boolean opEscVBarAlt() {
        return isOp(2048);
    }

    public boolean opLParenSubexp() {
        return isOp(4096);
    }

    public boolean opEscLParenSubexp() {
        return isOp(8192);
    }

    public boolean opEscAZBufAnchor() {
        return isOp(16384);
    }

    public boolean opEscCapitalGBeginAnchor() {
        return isOp(32768);
    }

    public boolean opDecimalBackref() {
        return isOp(65536);
    }

    public boolean opBracketCC() {
        return isOp(131072);
    }

    public boolean opEscWWord() {
        return isOp(262144);
    }

    public boolean opEscLtGtWordBeginEnd() {
        return isOp(524288);
    }

    public boolean opEscBWordBound() {
        return isOp(1048576);
    }

    public boolean opEscSWhiteSpace() {
        return isOp(2097152);
    }

    public boolean opEscDDigit() {
        return isOp(4194304);
    }

    public boolean opLineAnchor() {
        return isOp(8388608);
    }

    public boolean opPosixBracket() {
        return isOp(16777216);
    }

    public boolean opQMarkNonGreedy() {
        return isOp(33554432);
    }

    public boolean opEscControlChars() {
        return isOp(67108864);
    }

    public boolean opEscCControl() {
        return isOp(134217728);
    }

    public boolean opEscOctal3() {
        return isOp(SyntaxProperties.OP_ESC_OCTAL3);
    }

    public boolean opEscXHex2() {
        return isOp(SyntaxProperties.OP_ESC_X_HEX2);
    }

    public boolean opEscXBraceHex8() {
        return isOp(1073741824);
    }

    protected boolean isOp2(int opm) {
        return (this.op2 & opm) != 0;
    }

    public boolean op2EscCapitalQQuote() {
        return isOp2(1);
    }

    public boolean op2QMarkGroupEffect() {
        return isOp2(2);
    }

    public boolean op2OptionPerl() {
        return isOp2(4);
    }

    public boolean op2OptionRuby() {
        return isOp2(8);
    }

    public boolean op2PlusPossessiveRepeat() {
        return isOp2(16);
    }

    public boolean op2PlusPossessiveInterval() {
        return isOp2(32);
    }

    public boolean op2CClassSetOp() {
        return isOp2(64);
    }

    public boolean op2QMarkLtNamedGroup() {
        return isOp2(128);
    }

    public boolean op2EscKNamedBackref() {
        return isOp2(256);
    }

    public boolean op2EscGSubexpCall() {
        return isOp2(512);
    }

    public boolean op2AtMarkCaptureHistory() {
        return isOp2(1024);
    }

    public boolean op2EscCapitalCBarControl() {
        return isOp2(2048);
    }

    public boolean op2EscCapitalMBarMeta() {
        return isOp2(4096);
    }

    public boolean op2EscVVtab() {
        return isOp2(8192);
    }

    public boolean op2EscUHex4() {
        return isOp2(16384);
    }

    public boolean op2EscGnuBufAnchor() {
        return isOp2(32768);
    }

    public boolean op2EscPBraceCharProperty() {
        return isOp2(65536);
    }

    public boolean op2EscPBraceCircumflexNot() {
        return isOp2(131072);
    }

    public boolean op2EscHXDigit() {
        return isOp2(524288);
    }

    public boolean op2IneffectiveEscape() {
        return isOp2(1048576);
    }

    protected boolean isBehavior(int bvm) {
        return (this.behavior & bvm) != 0;
    }

    public boolean contextIndepRepeatOps() {
        return isBehavior(1);
    }

    public boolean contextInvalidRepeatOps() {
        return isBehavior(2);
    }

    public boolean allowUnmatchedCloseSubexp() {
        return isBehavior(4);
    }

    public boolean allowInvalidInterval() {
        return isBehavior(8);
    }

    public boolean allowIntervalLowAbbrev() {
        return isBehavior(16);
    }

    public boolean strictCheckBackref() {
        return isBehavior(32);
    }

    public boolean differentLengthAltLookBehind() {
        return isBehavior(64);
    }

    public boolean captureOnlyNamedGroup() {
        return isBehavior(128);
    }

    public boolean allowMultiplexDefinitionName() {
        return isBehavior(256);
    }

    public boolean fixedIntervalIsGreedyOnly() {
        return isBehavior(512);
    }

    public boolean notNewlineInNegativeCC() {
        return isBehavior(1048576);
    }

    public boolean backSlashEscapeInCC() {
        return isBehavior(2097152);
    }

    public boolean allowEmptyRangeInCC() {
        return isBehavior(4194304);
    }

    public boolean allowDoubleRangeOpInCC() {
        return isBehavior(8388608);
    }

    public boolean warnCCOpNotEscaped() {
        return isBehavior(16777216);
    }

    public boolean warnReduntantNestedRepeat() {
        return isBehavior(33554432);
    }
}
