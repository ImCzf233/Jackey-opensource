package jdk.nashorn.internal.runtime.regexp;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.regexp.joni.Matcher;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import jdk.nashorn.internal.runtime.regexp.joni.Region;
import jdk.nashorn.internal.runtime.regexp.joni.Syntax;
import jdk.nashorn.internal.runtime.regexp.joni.exception.JOniException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/JoniRegExp.class */
public class JoniRegExp extends RegExp {
    private Regex regex;

    public JoniRegExp(String pattern, String flags) throws ParserException {
        super(pattern, flags);
        int option = 8;
        option = isIgnoreCase() ? 8 | 1 : option;
        option = isMultiline() ? (option & (-9)) | 64 : option;
        try {
            try {
                RegExpScanner parsed = RegExpScanner.scan(pattern);
                if (parsed != null) {
                    char[] javaPattern = parsed.getJavaPattern().toCharArray();
                    this.regex = new Regex(javaPattern, 0, javaPattern.length, option, Syntax.JAVASCRIPT);
                    this.groupsInNegativeLookahead = parsed.getGroupsInNegativeLookahead();
                }
            } catch (PatternSyntaxException e) {
                Pattern.compile(pattern, 0);
                throw e;
            }
        } catch (PatternSyntaxException | JOniException e2) {
            throwParserException("syntax", e2.getMessage());
        }
    }

    @Override // jdk.nashorn.internal.runtime.regexp.RegExp
    public RegExpMatcher match(String input) {
        if (this.regex == null) {
            return null;
        }
        return new JoniMatcher(input);
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/JoniRegExp$Factory.class */
    public static class Factory extends RegExpFactory {
        @Override // jdk.nashorn.internal.runtime.regexp.RegExpFactory
        public RegExp compile(String pattern, String flags) throws ParserException {
            return new JoniRegExp(pattern, flags);
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/JoniRegExp$JoniMatcher.class */
    class JoniMatcher implements RegExpMatcher {
        final String input;
        final Matcher joniMatcher;

        JoniMatcher(String input) {
            JoniRegExp.this = this$0;
            this.input = input;
            this.joniMatcher = this$0.regex.matcher(input.toCharArray());
        }

        @Override // jdk.nashorn.internal.runtime.regexp.RegExpMatcher
        public boolean search(int start) {
            return this.joniMatcher.search(start, this.input.length(), 0) > -1;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.RegExpMatcher
        public String getInput() {
            return this.input;
        }

        @Override // java.util.regex.MatchResult
        public int start() {
            return this.joniMatcher.getBegin();
        }

        @Override // java.util.regex.MatchResult
        public int start(int group) {
            return group == 0 ? start() : this.joniMatcher.getRegion().beg[group];
        }

        @Override // java.util.regex.MatchResult
        public int end() {
            return this.joniMatcher.getEnd();
        }

        @Override // java.util.regex.MatchResult
        public int end(int group) {
            return group == 0 ? end() : this.joniMatcher.getRegion().end[group];
        }

        @Override // java.util.regex.MatchResult
        public String group() {
            return this.input.substring(this.joniMatcher.getBegin(), this.joniMatcher.getEnd());
        }

        @Override // java.util.regex.MatchResult
        public String group(int group) {
            if (group == 0) {
                return group();
            }
            Region region = this.joniMatcher.getRegion();
            return this.input.substring(region.beg[group], region.end[group]);
        }

        @Override // java.util.regex.MatchResult
        public int groupCount() {
            Region region = this.joniMatcher.getRegion();
            if (region == null) {
                return 0;
            }
            return region.numRegs - 1;
        }
    }
}
