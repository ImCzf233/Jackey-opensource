package jdk.nashorn.internal.parser;

import jdk.nashorn.internal.runtime.Source;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/parser/Token.class */
public class Token {
    private Token() {
    }

    public static long toDesc(TokenType type, int position, int length) {
        return (position << 32) | (length << 8) | type.ordinal();
    }

    public static int descPosition(long token) {
        return (int) (token >>> 32);
    }

    public static long withDelimiter(long token) {
        TokenType tokenType = descType(token);
        switch (tokenType) {
            case STRING:
            case ESCSTRING:
            case EXECSTRING:
                int start = descPosition(token) - 1;
                int len = descLength(token) + 2;
                return toDesc(tokenType, start, len);
            default:
                return token;
        }
    }

    public static int descLength(long token) {
        return ((int) token) >>> 8;
    }

    public static TokenType descType(long token) {
        return TokenType.getValues()[((int) token) & 255];
    }

    public static long recast(long token, TokenType newType) {
        return (token & (-256)) | newType.ordinal();
    }

    public static String toString(Source source, long token, boolean verbose) {
        String result;
        TokenType type = descType(token);
        if (source != null && type.getKind() == TokenKind.LITERAL) {
            result = source.getString(token);
        } else {
            result = type.getNameOrType();
        }
        if (verbose) {
            int position = descPosition(token);
            int length = descLength(token);
            result = result + " (" + position + ", " + length + ")";
        }
        return result;
    }

    public static String toString(Source source, long token) {
        return toString(source, token, false);
    }

    public static String toString(long token) {
        return toString(null, token, false);
    }

    public static int hashCode(long token) {
        return (int) (token ^ (token >>> 32));
    }
}
