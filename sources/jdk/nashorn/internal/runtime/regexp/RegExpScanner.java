package jdk.nashorn.internal.runtime.regexp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.Scanner;
import jdk.nashorn.internal.runtime.BitVector;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/RegExpScanner.class */
final class RegExpScanner extends Scanner {
    private int negLookaheadLevel;
    private int negLookaheadGroup;
    private static final String NON_IDENT_ESCAPES = "$^*+(){}[]|\\.?-";
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Map<Character, Integer> expected = new HashMap();
    private final List<Capture> caps = new LinkedList();
    private final LinkedList<Integer> forwardReferences = new LinkedList<>();
    private boolean inCharClass = false;
    private boolean inNegativeClass = false;

    /* renamed from: sb */
    private final StringBuilder f281sb = new StringBuilder(this.limit);

    static {
        $assertionsDisabled = !RegExpScanner.class.desiredAssertionStatus();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/RegExpScanner$Capture.class */
    public static class Capture {
        private final int negLookaheadLevel;
        private final int negLookaheadGroup;

        Capture(int negLookaheadGroup, int negLookaheadLevel) {
            this.negLookaheadGroup = negLookaheadGroup;
            this.negLookaheadLevel = negLookaheadLevel;
        }

        boolean canBeReferencedFrom(int group, int level) {
            return this.negLookaheadLevel == 0 || (group == this.negLookaheadGroup && level >= this.negLookaheadLevel);
        }
    }

    private RegExpScanner(String string) {
        super(string);
        reset(0);
        this.expected.put(']', 0);
        this.expected.put('}', 0);
    }

    private void processForwardReferences() {
        Iterator<Integer> iterator = this.forwardReferences.descendingIterator();
        while (iterator.hasNext()) {
            int pos = iterator.next().intValue();
            int num = iterator.next().intValue();
            if (num > this.caps.size()) {
                StringBuilder buffer = new StringBuilder();
                octalOrLiteral(Integer.toString(num), buffer);
                this.f281sb.insert(pos, (CharSequence) buffer);
            }
        }
        this.forwardReferences.clear();
    }

    public static RegExpScanner scan(String string) {
        RegExpScanner scanner = new RegExpScanner(string);
        try {
            scanner.disjunction();
            scanner.processForwardReferences();
            if (scanner.position != string.length()) {
                String p = scanner.getStringBuilder().toString();
                throw new PatternSyntaxException(string, p, p.length() + 1);
            }
            return scanner;
        } catch (Exception e) {
            throw new PatternSyntaxException(e.getMessage(), string, scanner.position);
        }
    }

    final StringBuilder getStringBuilder() {
        return this.f281sb;
    }

    public String getJavaPattern() {
        return this.f281sb.toString();
    }

    public BitVector getGroupsInNegativeLookahead() {
        BitVector vec = null;
        for (int i = 0; i < this.caps.size(); i++) {
            Capture cap = this.caps.get(i);
            if (cap.negLookaheadLevel > 0) {
                if (vec == null) {
                    vec = new BitVector(this.caps.size() + 1);
                }
                vec.set(i + 1);
            }
        }
        return vec;
    }

    private boolean commit(int n) {
        switch (n) {
            case 1:
                this.f281sb.append(this.ch0);
                skip(1);
                return true;
            case 2:
                this.f281sb.append(this.ch0);
                this.f281sb.append(this.ch1);
                skip(2);
                return true;
            case 3:
                this.f281sb.append(this.ch0);
                this.f281sb.append(this.ch1);
                this.f281sb.append(this.ch2);
                skip(3);
                return true;
            default:
                if ($assertionsDisabled) {
                    return true;
                }
                throw new AssertionError("Should not reach here");
        }
    }

    private void restart(int startIn, int startOut) {
        reset(startIn);
        this.f281sb.setLength(startOut);
    }

    private void push(char ch) {
        this.expected.put(Character.valueOf(ch), Integer.valueOf(this.expected.get(Character.valueOf(ch)).intValue() + 1));
    }

    private void pop(char ch) {
        this.expected.put(Character.valueOf(ch), Integer.valueOf(Math.min(0, this.expected.get(Character.valueOf(ch)).intValue() - 1)));
    }

    private void disjunction() {
        while (true) {
            alternative();
            if (this.ch0 == '|') {
                commit(1);
            } else {
                return;
            }
        }
    }

    private void alternative() {
        do {
        } while (term());
    }

    private boolean term() {
        int startIn = this.position;
        int startOut = this.f281sb.length();
        if (assertion()) {
            return true;
        }
        if (atom()) {
            quantifier();
            return true;
        }
        restart(startIn, startOut);
        return false;
    }

    private boolean assertion() {
        int startIn = this.position;
        int startOut = this.f281sb.length();
        switch (this.ch0) {
            case '$':
            case '^':
                return commit(1);
            case '(':
                if (this.ch1 == '?' && (this.ch2 == '=' || this.ch2 == '!')) {
                    boolean isNegativeLookahead = this.ch2 == '!';
                    commit(3);
                    if (isNegativeLookahead) {
                        if (this.negLookaheadLevel == 0) {
                            this.negLookaheadGroup++;
                        }
                        this.negLookaheadLevel++;
                    }
                    disjunction();
                    if (isNegativeLookahead) {
                        this.negLookaheadLevel--;
                    }
                    if (this.ch0 == ')') {
                        return commit(1);
                    }
                }
                break;
            case '\\':
                if (this.ch1 == 'b' || this.ch1 == 'B') {
                    return commit(2);
                }
                break;
        }
        restart(startIn, startOut);
        return false;
    }

    private boolean quantifier() {
        if (quantifierPrefix()) {
            if (this.ch0 == '?') {
                commit(1);
                return true;
            }
            return true;
        }
        return false;
    }

    private boolean quantifierPrefix() {
        int startIn = this.position;
        int startOut = this.f281sb.length();
        switch (this.ch0) {
            case '*':
            case '+':
            case '?':
                return commit(1);
            case '{':
                commit(1);
                if (decimalDigits()) {
                    push('}');
                    if (this.ch0 == ',') {
                        commit(1);
                        decimalDigits();
                    }
                    if (this.ch0 == '}') {
                        pop('}');
                        commit(1);
                        return true;
                    }
                    restart(startIn, startOut);
                    return false;
                }
                break;
        }
        restart(startIn, startOut);
        return false;
    }

    private boolean atom() {
        int startIn = this.position;
        int startOut = this.f281sb.length();
        if (patternCharacter()) {
            return true;
        }
        if (this.ch0 == '.') {
            return commit(1);
        }
        if (this.ch0 == '\\') {
            commit(1);
            if (atomEscape()) {
                return true;
            }
        }
        if (characterClass()) {
            return true;
        }
        if (this.ch0 == '(') {
            commit(1);
            if (this.ch0 == '?' && this.ch1 == ':') {
                commit(2);
            } else {
                this.caps.add(new Capture(this.negLookaheadGroup, this.negLookaheadLevel));
            }
            disjunction();
            if (this.ch0 == ')') {
                commit(1);
                return true;
            }
        }
        restart(startIn, startOut);
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean patternCharacter() {
        if (atEOF()) {
            return false;
        }
        switch (this.ch0) {
            case '$':
            case '(':
            case ')':
            case '*':
            case '+':
            case '.':
            case '?':
            case '[':
            case '\\':
            case '^':
            case '|':
                return false;
            case ']':
            case '}':
                int n = this.expected.get(Character.valueOf(this.ch0)).intValue();
                if (n != 0) {
                    return false;
                }
                break;
            case '{':
                break;
            default:
                return commit(1);
        }
        if (!quantifierPrefix()) {
            this.f281sb.append('\\');
            return commit(1);
        }
        return false;
    }

    private boolean atomEscape() {
        return decimalEscape() || characterClassEscape() || characterEscape() || identityEscape();
    }

    private boolean characterEscape() {
        int startIn = this.position;
        int startOut = this.f281sb.length();
        if (controlEscape()) {
            return true;
        }
        if (this.ch0 == 'c') {
            commit(1);
            if (controlLetter()) {
                return true;
            }
            restart(startIn, startOut);
        }
        if (hexEscapeSequence() || unicodeEscapeSequence()) {
            return true;
        }
        restart(startIn, startOut);
        return false;
    }

    private boolean scanEscapeSequence(char leader, int length) {
        int startIn = this.position;
        int startOut = this.f281sb.length();
        if (this.ch0 != leader) {
            return false;
        }
        commit(1);
        for (int i = 0; i < length; i++) {
            char ch0l = Character.toLowerCase(this.ch0);
            if ((ch0l >= 'a' && ch0l <= 'f') || isDecimalDigit(this.ch0)) {
                commit(1);
            } else {
                restart(startIn, startOut);
                return false;
            }
        }
        return true;
    }

    private boolean hexEscapeSequence() {
        return scanEscapeSequence('x', 2);
    }

    private boolean unicodeEscapeSequence() {
        return scanEscapeSequence('u', 4);
    }

    private boolean controlEscape() {
        switch (this.ch0) {
            case 'f':
            case 'n':
            case 'r':
            case 't':
            case 'v':
                return commit(1);
            default:
                return false;
        }
    }

    private boolean controlLetter() {
        if ((this.ch0 < 'A' || this.ch0 > 'Z') && (this.ch0 < 'a' || this.ch0 > 'z')) {
            if (this.inCharClass) {
                if (!isDecimalDigit(this.ch0) && this.ch0 != '_') {
                    return false;
                }
            } else {
                return false;
            }
        }
        this.f281sb.setLength(this.f281sb.length() - 1);
        unicode(this.ch0 % ' ', this.f281sb);
        skip(1);
        return true;
    }

    private boolean identityEscape() {
        if (atEOF()) {
            throw new RuntimeException("\\ at end of pattern");
        }
        if (this.ch0 == 'c') {
            this.f281sb.append('\\');
        } else if (NON_IDENT_ESCAPES.indexOf(this.ch0) == -1) {
            this.f281sb.setLength(this.f281sb.length() - 1);
        }
        return commit(1);
    }

    private boolean decimalEscape() {
        int startIn = this.position;
        int startOut = this.f281sb.length();
        if (this.ch0 == '0' && !isOctalDigit(this.ch1)) {
            skip(1);
            this.f281sb.append("��");
            return true;
        } else if (isDecimalDigit(this.ch0)) {
            if (this.ch0 == '0') {
                if (this.inCharClass) {
                    int octalValue = 0;
                    while (isOctalDigit(this.ch0)) {
                        octalValue = ((octalValue * 8) + this.ch0) - 48;
                        skip(1);
                    }
                    unicode(octalValue, this.f281sb);
                    return true;
                }
                decimalDigits();
                return true;
            }
            int decimalValue = 0;
            while (isDecimalDigit(this.ch0)) {
                decimalValue = ((decimalValue * 10) + this.ch0) - 48;
                skip(1);
            }
            if (this.inCharClass) {
                this.f281sb.setLength(this.f281sb.length() - 1);
                octalOrLiteral(Integer.toString(decimalValue), this.f281sb);
                return true;
            } else if (decimalValue <= this.caps.size()) {
                Capture capture = this.caps.get(decimalValue - 1);
                if (!capture.canBeReferencedFrom(this.negLookaheadGroup, this.negLookaheadLevel)) {
                    this.f281sb.setLength(this.f281sb.length() - 1);
                    return true;
                }
                this.f281sb.append(decimalValue);
                return true;
            } else {
                this.f281sb.setLength(this.f281sb.length() - 1);
                this.forwardReferences.add(Integer.valueOf(decimalValue));
                this.forwardReferences.add(Integer.valueOf(this.f281sb.length()));
                return true;
            }
        } else {
            restart(startIn, startOut);
            return false;
        }
    }

    private boolean characterClassEscape() {
        switch (this.ch0) {
            case 'D':
            case 'W':
            case 'd':
            case 'w':
                return commit(1);
            case 'S':
                if (RegExpFactory.usesJavaUtilRegex()) {
                    this.f281sb.setLength(this.f281sb.length() - 1);
                    this.f281sb.append(this.inNegativeClass ? "&&[" : "[^").append(Lexer.getWhitespaceRegExp()).append(']');
                    skip(1);
                    return true;
                }
                return commit(1);
            case 's':
                if (RegExpFactory.usesJavaUtilRegex()) {
                    this.f281sb.setLength(this.f281sb.length() - 1);
                    if (this.inCharClass) {
                        this.f281sb.append(Lexer.getWhitespaceRegExp());
                    } else {
                        this.f281sb.append('[').append(Lexer.getWhitespaceRegExp()).append(']');
                    }
                    skip(1);
                    return true;
                }
                return commit(1);
            default:
                return false;
        }
    }

    private boolean characterClass() {
        int startIn = this.position;
        int startOut = this.f281sb.length();
        if (this.ch0 == '[') {
            try {
                this.inCharClass = true;
                push(']');
                commit(1);
                if (this.ch0 == '^') {
                    this.inNegativeClass = true;
                    commit(1);
                }
                if (classRanges() && this.ch0 == ']') {
                    pop(']');
                    commit(1);
                    if (this.position == startIn + 2) {
                        this.f281sb.setLength(this.f281sb.length() - 1);
                        this.f281sb.append("^\\s\\S]");
                    } else if (this.position == startIn + 3 && this.inNegativeClass) {
                        this.f281sb.setLength(this.f281sb.length() - 2);
                        this.f281sb.append("\\s\\S]");
                    }
                    return true;
                }
                this.inCharClass = false;
                this.inNegativeClass = false;
            } finally {
                this.inCharClass = false;
                this.inNegativeClass = false;
            }
        }
        restart(startIn, startOut);
        return false;
    }

    private boolean classRanges() {
        nonemptyClassRanges();
        return true;
    }

    private boolean nonemptyClassRanges() {
        int startIn = this.position;
        int startOut = this.f281sb.length();
        if (classAtom()) {
            if (this.ch0 == '-') {
                commit(1);
                if (classAtom() && classRanges()) {
                    return true;
                }
            }
            nonemptyClassRangesNoDash();
            return true;
        }
        restart(startIn, startOut);
        return false;
    }

    private boolean nonemptyClassRangesNoDash() {
        int startIn = this.position;
        int startOut = this.f281sb.length();
        if (classAtomNoDash()) {
            if (this.ch0 == '-') {
                commit(1);
                if (classAtom() && classRanges()) {
                    return true;
                }
            }
            nonemptyClassRangesNoDash();
            return true;
        } else if (classAtom()) {
            return true;
        } else {
            restart(startIn, startOut);
            return false;
        }
    }

    private boolean classAtom() {
        if (this.ch0 == '-') {
            return commit(1);
        }
        return classAtomNoDash();
    }

    private boolean classAtomNoDash() {
        if (atEOF()) {
            return false;
        }
        int startIn = this.position;
        int startOut = this.f281sb.length();
        switch (this.ch0) {
            case '-':
            case ']':
                return false;
            case '[':
                this.f281sb.append('\\');
                return commit(1);
            case '\\':
                commit(1);
                if (classEscape()) {
                    return true;
                }
                restart(startIn, startOut);
                return false;
            default:
                return commit(1);
        }
    }

    private boolean classEscape() {
        if (decimalEscape()) {
            return true;
        }
        if (this.ch0 != 'b') {
            return characterEscape() || characterClassEscape() || identityEscape();
        }
        this.f281sb.setLength(this.f281sb.length() - 1);
        this.f281sb.append('\b');
        skip(1);
        return true;
    }

    private boolean decimalDigits() {
        if (!isDecimalDigit(this.ch0)) {
            return false;
        }
        while (isDecimalDigit(this.ch0)) {
            commit(1);
        }
        return true;
    }

    private static void unicode(int value, StringBuilder buffer) {
        String hex = Integer.toHexString(value);
        buffer.append('u');
        for (int i = 0; i < 4 - hex.length(); i++) {
            buffer.append('0');
        }
        buffer.append(hex);
    }

    private static void octalOrLiteral(String numberLiteral, StringBuilder buffer) {
        int length = numberLiteral.length();
        int octalValue = 0;
        int pos = 0;
        while (pos < length && octalValue < 32) {
            char ch = numberLiteral.charAt(pos);
            if (!isOctalDigit(ch)) {
                break;
            }
            octalValue = ((octalValue * 8) + ch) - 48;
            pos++;
        }
        if (octalValue > 0) {
            buffer.append('\\');
            unicode(octalValue, buffer);
            buffer.append(numberLiteral.substring(pos));
            return;
        }
        buffer.append(numberLiteral);
    }

    private static boolean isOctalDigit(char ch) {
        return ch >= '0' && ch <= '7';
    }

    private static boolean isDecimalDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
}
