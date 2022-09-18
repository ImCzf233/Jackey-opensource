package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/MatcherFactory.class */
public abstract class MatcherFactory {
    static final MatcherFactory DEFAULT = new MatcherFactory() { // from class: jdk.nashorn.internal.runtime.regexp.joni.MatcherFactory.1
        @Override // jdk.nashorn.internal.runtime.regexp.joni.MatcherFactory
        public Matcher create(Regex regex, char[] chars, int p, int end) {
            return new ByteCodeMachine(regex, chars, p, end);
        }
    };

    public abstract Matcher create(Regex regex, char[] cArr, int i, int i2);
}
